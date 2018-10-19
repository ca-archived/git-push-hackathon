using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Text.RegularExpressions;

using Newtonsoft.Json;

namespace oauth_server
{
    class Program
    {


        public static class GlobalVals
        {
            /*
             * Program.csクラス内で使用する変数を定義
             */

            public const string SERVER_ORIGIN = "http://localhost:4201/";
            public const string CLIENT_ORIGIN = "http://localhost:4200/";

            public const string CLIENT_SECRET = "473798ee1cdead1152b7a66e164e7bb1b873f049";
            public const string OAUTH_URL2 = "https://github.com/login/oauth/access_token";
        }

        static string parseUrlParamsToJsonText(string urlParamsText)
        {
            /*
             * url クエリ文字列を、Json形式の文字列に変換する
             * 例　
             * "param1=AAA&param2=BBB" 
             * --> "{{ "param1" : "AAA" },{ "param2" : "BBB"}}"
             */
            string jsonText = "";
            Dictionary<string, string> jsonDict = new Dictionary<string, string>();

            string[] paramTexts = urlParamsText.Split('&');
            foreach (string paramText in paramTexts)
            {
                string[] paramPair = paramText.Split('=');

                if (paramPair[1] == null) paramPair[1] = "";
                jsonDict.Add(paramPair[0], paramPair[1]);
            }

            jsonText = JsonConvert.SerializeObject(jsonDict);
            return jsonText;
        }

        static void responseToClient(HttpListenerResponse res, string resBody)
        {
            /*
             * アクセスしたクライアントへのresponse処理部分
             */
            try
            {
                res.StatusCode = 200;
                byte[] content = Encoding.UTF8.GetBytes(resBody);
                res.OutputStream.Write(content, 0, content.Length);
            }
            catch (Exception ex)
            {
                res.StatusCode = 500; // 404 でも良いのだがここは雑に 500 にまとめておく
                byte[] content = Encoding.UTF8.GetBytes(ex.Message);
                res.OutputStream.Write(content, 0, content.Length);
            }
            res.Close();
        }

        static string apiPost(string url, Dictionary<string, string> reqParams)
        {
            /*
             * apiを投げる処理
             * url       : apiを投げる先
             * reqParams : POSTリクエストの際に送信するパラメータ(Dictionary)
             */
            string bodyText = "";

            if (reqParams.Count != 0)
            {
                //リクエスト本体 Dict -> url query 形式
                foreach (KeyValuePair<string, string> reqParam in reqParams)
                {
                    bodyText += reqParam.Key + "=" + reqParam.Value + "&";
                }
                bodyText.Remove(bodyText.Length - 1); //最後の文字を削除=余分な"&"
            }
            //リクエスト本体 text -> ascii codes
            byte[] bodyCodes = Encoding.ASCII.GetBytes(bodyText);

            ServicePointManager.SecurityProtocol
                = SecurityProtocolType.Tls
                | SecurityProtocolType.Tls11
                | SecurityProtocolType.Tls12;

            //reqestオブジェクト作成
            url += "?" + bodyText;
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            request.Method = "POST";
            request.ContentType = "application/x-www-form-urlencoded";
            request.Accept = "application/x-www-form-urlencoded";
            request.ContentLength = bodyCodes.Length;


            //(HttpWebRequest).GetRequestStream : 要求データを書きこむために使用するStreamオブジェクトを取得する
            using (Stream reqStream = request.GetRequestStream())
            {
                reqStream.Write(bodyCodes, 0, bodyCodes.Length);
            }
            //api.github.comサーバーに要求し、応答を取得 --> return
            try
            {
                WebResponse response = request.GetResponse();
                string resText;
                using (Stream responseStream = response.GetResponseStream())
                {
                    using (StreamReader reader = new StreamReader(responseStream))
                    {
                        resText = reader.ReadToEnd();
                    }
                }
                return resText;
            }
            catch (WebException e)
            {
                Console.WriteLine("API POST Error : " + e.Message);
                return "";
            }
        }

        static void originAccess(HttpListenerRequest req, HttpListenerResponse res)
        {
            /*
             * ディレクトリなしでアクセス
             */
            string contentStr = "{message :\"This server is for OAuth API test\"}";
            res.ContentType = "application/json";
            responseToClient(res, contentStr);
        }

        static void notFoundAccess(HttpListenerRequest req, HttpListenerResponse res)
        {
            /*
             * クライアントがディレクトリのないところを参照
             */
            string contentStr = "{error_message :\"404 not Found\"}";
            res.ContentType = "application/json";
            responseToClient(res, contentStr);
        }

        static void OAuth2Post(HttpListenerRequest req, HttpListenerResponse res, Dictionary<string, string> reqParams)
        {
            /*
             * api.github.comサーバーにOAuthのaccess_tokenをcodeと引き換えに要求
             */
            reqParams.Add("client_secret", GlobalVals.CLIENT_SECRET);

            string OAuthResText
                = apiPost(GlobalVals.OAUTH_URL2, reqParams);//api.github.comにPOST

            string resJsonText = parseUrlParamsToJsonText(OAuthResText);

            res.ContentType = "application/json";
            res.AddHeader("Access-Control-Allow-Origin", "*");

            responseToClient(res, resJsonText);
        }

        static void OAuthServer()
        {
            HttpListener listener = new HttpListener();
            listener.Prefixes.Add(GlobalVals.SERVER_ORIGIN); // "http://localhost:xxxx/"
            listener.Start();

            //req.RawUrl --> /directry-01_A/bb?param1=asdf&param2=&redirect_uri=//localhost:4201

            //match "directry-01_A/bb"
            Regex dirReg =
                new Regex(@"(?<=\/)([a-z]|[A-Z]|[0-9]|-|_|/)+(?=\?)");

            //match "param1=asdf&param2=&redirect_uri=//localhost:4201"
            Regex paramsReg=
                new Regex(@"(?<=\?).*$");

            while (true)
            {
                Console.WriteLine("Access waiting : ");

                //アクセスがあるまでlistener.GetContext() でとまる
                HttpListenerContext context = listener.GetContext();
                HttpListenerRequest req = context.Request;
                HttpListenerResponse res = context.Response;

                Console.WriteLine("Access   in: " + req.RawUrl);

                //requested directory --> ["directory1", "directory2", ...]
                Match dirMatch = dirReg.Match(req.RawUrl);
                string[] reqDirs = dirMatch.ToString().Split('/');

                //requested parameters --> { {"" : ""} , { "" : "" }, ..}
                Match paramMatch = paramsReg.Match(req.RawUrl);
                Dictionary<string, string> reqParams = new Dictionary<string, string>();
                string[] paramTexts = paramMatch.ToString().Split('&');
                foreach (string paramText in paramTexts)
                {
                    string[] paramPair = paramText.Split('=');
                    if (paramPair[1] == null) paramPair[1] = "";
                    reqParams.Add(paramPair[0], paramPair[1]);
                }

                Console.WriteLine("directry in: ");
                foreach (string dir in reqDirs)
                {
                    Console.WriteLine("\t" + dir);
                }
                Console.WriteLine("params {key : value} : ");
                foreach (KeyValuePair<string, string> reqParam in reqParams)
                {
                    Console.WriteLine("\t{ " + reqParam.Key + " : " + reqParam.Value + " }");
                }


                //アクセスディレクトリで処理を分岐
                if (reqDirs[0] == null)
                {
                    originAccess(req, res);
                }
                else if (reqDirs[0] == "oauth2-post")
                {
                    OAuth2Post(req, res, reqParams);
                }
                else
                {
                    notFoundAccess(req, res);
                }
            }
        }

        static void Main(string[] args)
        {
            OAuthServer();
        }
    }
}
