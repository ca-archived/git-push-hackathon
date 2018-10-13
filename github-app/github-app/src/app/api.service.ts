import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {TEMP_ACCESS_KEYS} from'./temp-access-keys';

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    constructor(
        private http: HttpClient
    ) {    }

    OAuthURL1: string = "https://github.com/login/oauth/authorize"; //GET user redirected here
    OAuthURL2: string = "https://github.com/login/oauth/access_token"; //POST
    OAuthURL3: string = "https://api.github.com/user?access_token="; //GET

    clientId: string = TEMP_ACCESS_KEYS.clientId;
    clientSecret: string = TEMP_ACCESS_KEYS.clientSecret;
    redirectUrl: string = TEMP_ACCESS_KEYS.redirectUrl;
    state: string = TEMP_ACCESS_KEYS.state; //random文字列ってこれでいいの？
    scope : string = "gist";

    redirect_code: string = "";
    redirect_state: string = "";

    access_token: string = TEMP_ACCESS_KEYS.access_token;

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
        );
        httpObj.subscribe(this.OAuth2Next, this.RequestError);
    }

    OAuth2Next(res) {
        console.log("Response");
        console.log(res);
    }

    OAuth3() {

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
            +"?access_token=" + TEMP_ACCESS_KEYS.access_token
        );
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }
    GithubApiOAuth_gist() {
        console.log("GithubApiOAuth_gist");
        var httpObj = this.http.get(
            "https://api.github.com/"
            +"gists"
            +"?access_token=" + TEMP_ACCESS_KEYS.access_token
        );
        httpObj.subscribe(this.RequestNext, this.RequestError);
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
