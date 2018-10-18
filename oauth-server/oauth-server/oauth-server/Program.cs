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

            public const string SERVER_ORIGIN = "http://localhost:4201/";
            public const string CLIENT_ORIGIN = "http://localhost:4200/";

            public const string STEP2_REDIRECT_URL = SERVER_ORIGIN + "/step2-redirect";
            public const string CLIENT_ID = "0bc6d4e0794201162940";
            public const string CLIENT_SECRET = "473798ee1cdead1152b7a66e164e7bb1b873f049";
            public const string OAUTH_URL2 = "https://github.com/login/oauth/access_token";
            public static string code = "";
            public const string STATE = "";

            public static string access_token = "";
        }

        static string apiPost(string url, Dictionary<string, string> reqParams)
        {
            string bodyText = "";

            if (reqParams.Count != 0)
            {
                bodyText += "?";
                //リクエスト本体 Dict -> url query 形式
                foreach (KeyValuePair<string, string> reqParam in reqParams)
                {
                    bodyText += reqParam.Key + "=" + reqParam.Value + "&";
                }
                bodyText.Remove(bodyText.Length - 1); //最後の文字を削除=余分な"&"
            }
            //リクエスト本体 text -> ascii codes(json形式)
            byte[] bodyAscii = Encoding.ASCII.GetBytes(bodyText);

            //reqestオブジェクト作成
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            request.Method = "POST";
            request.ContentType = "application/x-www-form-urlencoded";
            request.Accept = "application/x-www-form-urlencoded";
            request.ContentLength = bodyAscii.Length;

            //(HttpWebRequest).GetRequestStream : 要求データを書きこむために使用するStreamオブジェクトを取得する
            using (Stream reqStream = request.GetRequestStream())
            {
                reqStream.Write(bodyAscii, 0, bodyAscii.Length);
            }

            try
            {
                WebResponse response = request.GetResponse(); //api.github.comサーバーに要求
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
            string contentStr = "accesse in domain url";
            res.ContentType = "text/html";
            // ファイル内容を出力
            try
            {
                res.StatusCode = 200;
                byte[] content = File.ReadAllBytes(contentStr);
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

        static void notFoundAccess(HttpListenerRequest req, HttpListenerResponse res)
        {
            string contentStr = "404 Not Found Page";
            res.ContentType = "text/plain";
            // ファイル内容を出力
            try
            {
                res.StatusCode = 200;
                byte[] content = File.ReadAllBytes(contentStr);
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

        static void OAuth2Post(HttpListenerRequest req, HttpListenerResponse res, Dictionary<string, string> reqParams)
        {
            /*
             * api.github.comサーバーにOAuthのaccess_tokenをcodeと引き換えに要求
            */
            string OAuthResText = "";
            reqParams.Add("client_secret", GlobalVals.CLIENT_SECRET);
            OAuthResText = apiPost(GlobalVals.OAUTH_URL2, reqParams);//api.github.comにPOST

            res.ContentType = "application/x-www-form-urlencoded";

            // ファイル内容を出力
            try
            {
                res.StatusCode = 200;
                byte[] content = File.ReadAllBytes(OAuthResText);
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

        static void OAuthServer()
        {
            HttpListener listener = new HttpListener();
            listener.Prefixes.Add(GlobalVals.SERVER_ORIGIN); // "http://localhost:xxxx/"
            listener.Start();

            //req.RawUrl --> /directry-01A?param1=asdf&param2=

            //match "directry-01A"
            Regex dirReg =
                new Regex("(?<=/)([a-z]|[A-Z]|[0-9]|-)+");

            //match "param1=asdf" and "param2="
            Regex paramsReg =
                new Regex("(?<=[?|&])([a-z]|[A-Z]|[0-9]|-|_)+=([a-z]|[A-Z]|[0-9]|-|_|/|:)*");
                //new Regex("(?<=[?|&])([a-z]|[A-Z]|[0-9]|-)+=([a-z]|[A-Z]|[0-9]|-)*");

            while (true)
            {
                //アクセスがあるまでlistener.GetContext() でとまる
                Console.WriteLine("Access waiting : ");
                HttpListenerContext context = listener.GetContext();
                HttpListenerRequest req = context.Request;
                HttpListenerResponse res = context.Response;
                //res.ContentType イメージ: image/拡張子 html: text/html　にする

                // URL (ここには "/" とか "/index.html" 等が入ってくる)
                string dir_params = req.RawUrl;

                MatchCollection dirMatches = dirReg.Matches(req.RawUrl);
                MatchCollection paramMatches = paramsReg.Matches(req.RawUrl);

                string[] reqDirs =
                    new string[0];

                Dictionary<string, string> reqParams = new Dictionary<string, string>();

                // MatchesObject --> [ "" , "" , ..]
                foreach (Match dirMatch in dirMatches)
                {
                    int len = reqDirs.Length;
                    Array.Resize(ref reqDirs, len + 1);
                    reqDirs[len] = dirMatch.Value;
                }

                // MatchesObject --> { {"" : ""} , { "" : "" }, ..}
                foreach (Match paramMatch in paramMatches)
                {
                    string[] param_pair
                        = paramMatch.Value.Split('=');

                    //paramの値がないときの処理
                    if (param_pair[1] == null) param_pair[1] = "";

                    reqParams.Add(param_pair[0], param_pair[1]);
                }

                Console.WriteLine("Access   in: " + req.RawUrl);
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
