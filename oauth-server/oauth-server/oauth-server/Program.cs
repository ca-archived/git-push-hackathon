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
            public const string OAUTH_URL2 = "0bc6d4e0794201162940";
            public static string code = "";
            public const string STATE = "";

            public static string access_token = "";
        }

        class PostReqBody
        {
            public string client_id { get; set; }            //PostReqBody.name
            public string client_secret { get; set; }            //PostReqBody.name
            public string code { get; set; }            //PostReqBody.name
            public string redirect_url { get; set; }            //PostReqBody.name
            public string state { get; set; }
        }

        static string apiPost(string url, PostReqBody reqBody)
        {
            //リクエスト本体 Dict -> text(json形式)
            string bodyText = JsonConvert.SerializeObject(reqBody);

            //リクエスト本体 text(json形式) -> ascii codes(json形式)
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

        static void OAuth2Post(HttpListenerRequest req, HttpListenerResponse res)
        {
            /*
             * api.github.comサーバーにOAuthのaccess_tokenをcodeと引き換えに要求
            */
            PostReqBody reqBody;
            reqBody = new PostReqBody();

            reqBody.client_id = GlobalVals.CLIENT_ID;
            reqBody.client_secret = GlobalVals.CLIENT_SECRET;
            reqBody.code = GlobalVals.code;
            reqBody.redirect_url = GlobalVals.STEP2_REDIRECT_URL;
            reqBody.state = GlobalVals.STATE;

            string OAuthResText = "";
            OAuthResText = apiPost(GlobalVals.OAUTH_URL2, reqBody);

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
                new Regex("(?<=[?|&])([a-z]|[A-Z]|[0-9]|-)+=([a-z]|[A-Z]|[0-9]|-)*");
            
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

                string[] dirTexts = 
                    new string [0];

                Dictionary<string, string>[] paramTexts = 
                    new Dictionary<string, string>[0];

                // MatchesObject --> [ "" , "" , ..]
                foreach (Match dirMatch in dirMatches)
                {
                    int len = dirTexts.Length;
                    Array.Resize(ref dirTexts, len + 1);
                    dirTexts[len] = dirMatch.Value;
                }

                // MatchesObject --> [ {"" : ""} , { "" : "" }, ..]
                foreach (Match paramMatch in paramMatches)
                {
                    int len = paramTexts.Length;
                    string[] param_pair 
                        = paramMatch.Value.Split('=');

                    //paramの値がないときの処理
                    if (param_pair[1] == null) param_pair[1] = "";

                    Array.Resize(ref paramTexts, len + 1);
                    paramTexts[len] = 
                        new Dictionary<string,string>(){ {param_pair[0],param_pair[1]} };
                }

                Console.WriteLine("Access in \t: " + req.RawUrl);
                Console.WriteLine("directry in \t: ");
                foreach (string dir in dirTexts)
                {
                    Console.WriteLine("\t" +dir + "\n");
                }
                Console.WriteLine("params is \t: ");
                foreach (Dictionary<string,string> param in paramTexts)
                {
                    Console.WriteLine("\t" + param + "\n");
                }


                if (dirTexts[0] == "/")
                {
                    originAccess(req, res);
                }
                else if (dirTexts[0] == "/oauth2-post")
                {
                    OAuth2Post(req, res);
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
