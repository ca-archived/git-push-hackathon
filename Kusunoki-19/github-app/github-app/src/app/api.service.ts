import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';


@Injectable({
    providedIn: 'root'
})
export class ApiService {

    constructor(
        private http: HttpClient,
        private cookieService: CookieService
    ) {
        this.GetAcquiredAccessToken_inCookie();
    }

    OAuthURL1: string = "https://github.com/login/oauth/authorize"; //GET user redirected here
    OAuthURL2: string = "https://github.com/login/oauth/access_token"; //POST

    access_token: string = "initial"; //set from cookie always
    OAuthServerOrigin: string = "http://localhost:4201";

    clientId: string = "YOUT_CLIENT_ID";//アプリケーションのclient_idを入れてください
    redirectUrl: string = "";
    state: string = "koukakukidoutai1234tachikomaiscute";//random string
    scope: string = "gist";

    redirect_code: string = "";
    redirect_state: string = ""

    isDelToken : boolean = false;

    OnInit() {
    }

    GetTest() {
        /*
         * get test by using public api
         */
        let TEST_URL = "http://httpbin.org/ip";
        //TEST_URL のhttp request用のオブジェクトを作成
        var httpObj = this.http.get(TEST_URL);
        //subscribeに登録されている処理(API処理も含む)を実行後、ApiNextを実行。
        //エラーならApiErrorを実行
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    PostTest() {
        /*
         * post test by using public api
         */
        let TEST_URL = "http://httpbin.org/post";
        var reqBody = {test: "test_text"};
        console.log(reqBody);

        var httpObj = this.http.post(TEST_URL, reqBody);
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    OAuth2() {
        /*
         * OAuth Step2 : Request POST request to my OAuth server
         * and getting access_token
         * if getting access_token is succeded , save this access_token in cookie
         */
        console.log("OAuth2");
        var httpObj = this.http.get(
            this.OAuthServerOrigin + "/oauth2-post"
            + "?" + "client_id=" + this.clientId
            + "&" + "code=" + this.redirect_code
            + "&" + "redirect_url=" + "http://localhost:4200"
            + "&" + "state=" + this.state
        );
        httpObj.subscribe(
            res => {
                console.log("res");
                console.log(res);
                if (res.hasOwnProperty("access_token")) {
                    this.access_token = res["access_token"];
                    this.cookieService.set("access_token", this.access_token); //memory in cookie
                    console.log("access_token is : " + this.access_token);
                }
                if (res.hasOwnProperty("error")) {
                    console.log("error between my OAuth server and Github server: ");
                    this.access_token = "error";
                    console.log(res["error"]);
                }
            },
            err => {
                console.log("error between client and my OAuth server");
                console.log(err);
            }
        );
    }

    GetAcquiredAccessToken() {
        /*
         * サーバーからaccess_tokenを取得
         */
        console.log("GetAcquiredAccessToken");
        var httpObj = this.http.get(
            this.OAuthServerOrigin + "/get-token"
        );
        httpObj.subscribe(
            res => {
                console.log("res");
                console.log(res);
                if (res.hasOwnProperty("access_token")) {
                    this.access_token = res["access_token"];
                    console.log("access_token is : " + this.access_token);
                }
                if (res.hasOwnProperty("error")) {
                    console.log("error between my OAuth server and Github server: ");
                    this.access_token = "error";
                    console.log(res["error"]);
                }
            },
            err => {
                console.log("error between client and my OAuth server");
                console.log(err);
            }
        );
    }


    GetAcquiredAccessToken_inCookie(){
        /*
         * cookieからaccess_tokenを取得
         */
        console.log("GetAcquiredAccessToken_byCookie");
        this.access_token = this.cookieService.get("access_token");
        console.log("access_token is : " + this.access_token);
    }

    DelAcquiredAccessToken_inCookie(){
        /*
         * cookieからaccess_tokenを削除
         */
        console.log("DelAcquiredAccessToken_inCookie");
        this.cookieService.set("access_token", "initial"); //delete access token
        //this.cookieService.deleteAll();
        this.access_token = "initial";
        console.log("access_token in cookie : "+this.cookieService.get("access_token"));
        this.isDelToken = true;
    }

    private GetAccessTokenNext(res) {
        /*
         * a next function of a subscribe
         */
        console.log("res");
        console.log(res);
        if (res.hasOwnProperty("access_token")) {
            this.access_token = res["access_token"];
            console.log("access_token is : " + this.access_token);
        }
        if (res.hasOwnProperty("error")) {
            console.log("error between my OAuth server and Github server: ");
            this.access_token = "error";
            console.log(res["error"]);
        }
    }

    private BetweenMyOAuthServerErr(err) {
        /*
         * a error function of a subscribe
         */
        console.log("error between client and my OAuth server");
        console.log(err);
    }

    GithubApiTest() {
        /*
         * Github API test by using my username
         */
        console.log("GithubApiTest");
        var httpObj = this.http.get(
            "https://api.github.com/users/Kusunoki-19",
        );
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    GetTokenCheckReq() {
        /*
         * Github API test by using present access_token
         */
        console.log("GithubApiOAuthTest");
        var theObservable = this.http.get(
            "https://api.github.com"
            + "/gists"
            + "?access_token=" + this.access_token
        );
        return theObservable;

    }

    GetAllGistDataReq() {
        /*
         * return observable object to use to get gist request
         */
        console.log("GetAllGistDataReq");
        var httpObj = this.http.get(
            "https://api.github.com"
            + "/gists"
            + "?access_token=" + this.access_token
        );
        return httpObj;
    }

    GetAllGistDataReq_byUser(userName: string) {
        /*
         * return observable object to use to get gist request ordered by username
         */
        console.log("GetAllGistDataReq_byUser");
        if (userName == "" || null) {
            userName = "Kusunoki-19";
        }
        console.log("GetGistDataReq");
        var httpObj = this.http.get(
            "https://api.github.com"
            + "/users/" + userName
            + "/gists"
            + "?access_token=" + this.access_token
        );
        return httpObj;
    }

    GetPostGistReq(description, release, fileName, content) {
        /*
         * return observable object to use to post gist
         */
        console.log("GetPostGistReq");
        console.log("now access_token is : " + this.access_token);
        var httpObj = this.http.post(
            "https://api.github.com/"
            + "gists"
            , JSON.stringify(
                {
                    'description': description,
                    'public': release,
                    'files': {
                        [fileName]: {'content': content}
                    }
                }
            )
            , {
                headers: new HttpHeaders({
                    'Content-Type': 'application/json',
                    'Authorization': 'token ' + this.access_token
                })
            }
        );
        return httpObj;
    }

    private RequestNext(res) {
        /*
         * a next function of any subscribe method
         */
        console.log("Response");
        console.log(res);
    }

    private RequestError(error) {
        /*
         * a error function of any subscribe method
         */
        console.log("Error");
        console.log(error);
    }
}