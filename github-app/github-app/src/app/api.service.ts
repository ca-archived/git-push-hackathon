import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {TEMP_ACCESS_KEYS} from'./temp-access-keys';

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
    OAuthURL3: string = "https://api.github.com/user?access_token="; //GET

    OAuthServerOrigin: string = "http://localhost:4201";

    clientId: string = TEMP_ACCESS_KEYS.clientId;
    clientSecret: string = TEMP_ACCESS_KEYS.clientSecret;
    redirectUrl: string = TEMP_ACCESS_KEYS.redirectUrl;
    state: string = TEMP_ACCESS_KEYS.state;
    scope: string = "gist";

    redirect_code: string = "";
    redirect_state: string = "";

    access_token: string = TEMP_ACCESS_KEYS.access_token;

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
        var httpObj = this.http.get(
            /*
             this.OAuthServerOrigin + "/oauth2-post",
             new HttpParams()
             .set('client_id', this.clientId)
             //.set('client_secret', this.clientSecret) //added in servre
             .set('code', this.redirect_code)// default is empty
             .set('redirect_url', "http://localhost:4200")
             .set('state', this.state), // default is true
             {
             headers: new HttpHeaders({
             'Content-Type': 'application/x-www-form-urlencoded'
             })
             }*/
            this.OAuthServerOrigin + "/oauth2-post"
            + "?" + "client_id=" + this.clientId
            + "&" + "code=" + this.redirect_code
            + "&" + "redirect_url=" + "http://localhost:4200"
            + "&" + "state=" + this.state
        );
        httpObj.subscribe(
            res => {
                console.log(res);
            }, err => {
                console.log(err);
            }
        );

        //↓CORS eroor catched by angular httpClient error catch function
        /*
         var httpObj = this.http.post(
         this.OAuthURL2,
         new HttpParams()
         .set('client_id', this.clientId)
         .set('client_secret', this.clientSecret)
         .set('code', this.redirect_code)// default is empty
         .set('redirect_url', "http://localhost:4200")
         .set('state', this.state), // default is true
         {
         headers: new HttpHeaders({
         'Content-Type': 'application/x-www-form-urlencoded'
         })
         }
         )
         httpObj.subscribe(this.OAuth2Next, this.RequestError);
         */

        //↓CORS eroor catched by angular httpClient error catch function
        /*
         var postOAuthReq = new XMLHttpRequest();
         postOAuthReq.open("POST", this.OAuthURL2, true);//false = synchronized request
         postOAuthReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
         postOAuthReq.onreadystatechange = () => {
         if(this.readyState == postOAuthReq.DONE &&this.status == 200){
         console.log(this.responseText)
         }

         };

         postOAuthReq.send(
         'access_token='+ TEMP_ACCESS_KEYS.access_token
         +'&client_id='+ this.clientId
         +'&client_secret'+ this.clientSecret
         +'&code'+ this.redirect_code
         +'&redirect_url'+ "http://localhost:4200"
         +'&state'+ this.state
         );
         */

        /*
         const req = new HttpRequest(
         'POST'
         , this.OAuthURL2
         , new HttpParams()
         .set('client_id', this.clientId)
         .set('client_secret', this.clientSecret)
         .set('code', this.redirect_code)// default is empty
         .set('redirect_url', "http://localhost:4200")
         .set('state', this.state) // default is true,
         , {reportProgress: true});

         return this.http.request(req).pipe(
         map(event => "aaa"),
         tap(message => "bbb"),
         last(), // return last (completed) message to caller
         catchError()
         );

         */
    }

    OAuth2Next(res) {
        console.log("Response");
        console.log(res);
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
            + "?access_token=" + TEMP_ACCESS_KEYS.access_token
        );
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    GetGistDataReq() {
        console.log("GetGistDataReq");
        var httpObj = this.http.get(
            "https://api.github.com/"
            + "gists"
            + "?access_token=" + TEMP_ACCESS_KEYS.access_token
        );
        return httpObj;
    }

    GetPostGistReq(description, release, fileName, content) {
        console.log("GetPostGistReq");

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
                    'Authorization': 'token ' + TEMP_ACCESS_KEYS.access_token
                })
            }
        );
        return httpObj;
    }

    GithubApiOAuth_gist() {
        console.log("GithubApiOAuth_gist");
        var httpObj = this.http.get(
            "https://api.github.com/"
            + "gists"
            + "?access_token=" + TEMP_ACCESS_KEYS.access_token
        );
        httpObj.subscribe(this.GithubApiOAuth_gistNext, this.RequestError);
    }

    private GithubApiOAuth_gistNext(res) {
        console.log("GithubApiOAuth_gistNext");
        console.log("res");
        console.log(res);
        let gist_id: string = res["0"]["owner"]["login"];
        let gist_owner_name: string = res["0"]["id"];

        this.gist_id = gist_id;
        this.gist_owner_name = gist_owner_name;

        console.log("gist_id");
        console.log(gist_id);
        console.log("gist_owner_name");
        console.log(gist_owner_name);
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