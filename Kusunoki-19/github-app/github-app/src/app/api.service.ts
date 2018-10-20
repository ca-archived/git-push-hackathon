import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import {Observable, throwError} from 'rxjs';
import {catchError, last, map, tap} from 'rxjs/operators';
import {
    HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';


@Injectable({
    providedIn: 'root'
})
export class ApiService {

    constructor(private http: HttpClient) {
    }

    OAuthURL1: string = "https://github.com/login/oauth/authorize"; //GET user redirected here
    OAuthURL2: string = "https://github.com/login/oauth/access_token"; //POST

    access_token: string = "";
    APP_CLIENT_ID: string = "tachimoka01"; //used to identfy in OAuth server of this app
    OAuthServerOrigin: string = "http://localhost:4201";

    clientId: string = "0bc6d4e0794201162940";
    redirectUrl: string = "";
    state: string = "koukakukidoutai1234tachikomaiscute";//random string
    scope: string = "gist";

    redirect_code: string = "";
    redirect_state: string = "";

    gist_id: string = "";
    gist_owner_name: string = "";

    httpOptions = {
        header: new HttpHeaders({
            'Content-Type': 'application/json',
        })
    };


    OnInit() {
    }

    GetTest() {
        let TEST_URL = "http://httpbin.org/ip";
        //TEST_URL のhttp request用のオブジェクトを作成
        var httpObj = this.http.get(TEST_URL);
        //subscribeに登録されている処理(API処理も含む)を実行後、ApiNextを実行。
        //エラーならApiErrorを実行
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    PostTest() {
        let TEST_URL = "http://httpbin.org/post";
        var reqBody = {test: "test_text"};
        console.log(reqBody);

        var httpObj = this.http.post(TEST_URL, reqBody);
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    OAuth2() {
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

    private GetAccessTokenNext(res) {
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
        console.log("error between client and my OAuth server");
        console.log(err);
    }

    GithubApiTest() {
        console.log("GithubApiTest");
        var httpObj = this.http.get(
            "https://api.github.com/users/Kusunoki-19",
        );
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    GithubApiOAuthTest() {
        console.log("GithubApiOAuthTest");
        var httpObj = this.http.get(
            "https://api.github.com/"
            + "?access_token=" + this.access_token
        );
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    GetAllGistDataReq() {
        console.log("GetAllGistDataReq");
        var httpObj = this.http.get(
            "https://api.github.com"
            + "/gists"
            + "?access_token=" + this.access_token
        );
        return httpObj;
    }

    GetAllGistDataReq_byUser(userName: string) {
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
        console.log("Response");
        console.log(res);
    }

    private RequestError(error) {
        console.log("Error");
        console.log(error);
    }
}