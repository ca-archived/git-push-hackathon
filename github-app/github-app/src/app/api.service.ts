import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient, HttpHeaders ,HttpParams} from '@angular/common/http';
import {GitHubApiParam1, GitHubApiParam2} from'./github_api_param';

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    OAuthURL1: string = "https://github.com/login/oauth/authorize"; //GET
    //OAuthURL2: string = "https://github.com/login/oauth/access_token"; //POST
    OAuthURL2: string = "https://github.com/login?return_to=%2Flogin%2Foauth%2Fauthorize"; //POST
    OAuthURL3: string = "https://api.github.com/user?access_token="; //GET

    clientId: string = "0bc6d4e0794201162940";
    clientSecret: string = "473798ee1cdead1152b7a66e164e7bb1b873f049";
    redirectUrl: string = "http://localhost:4200";
    state: string = "alkjsoivuejngiausy"; //random文字列ってこれでいいの？

    httpOptions = {
        header: new HttpHeaders({
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': "*",
            'Access-Control-Allow-Origin': "http://localhost:4200",
            'Origin': "http://localhost:4200"
        })
    };
    githubApiParam1: GitHubApiParam1;
    githubApiParam1 = {
        client_id: this.clientId,
        redirect_url: this.redirectUrl,
        scope: "", // default is empty
        state: this.state,
        allow_singup: false // default is true
    }

    githubApiParam2: GitHubApiParam2;
    gitHubApiParam2 = {
        client_id: this.clientId,
        client_secret: this.clientSecret,
        code: "", // OAuth1のレスポンスに入っているので、後で代入する
        redirect_url: this.redirectUrl,
        state: this.state
    }

    constructor(private http: HttpClient) {
    }

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

    OAuth1() {
        var httpObj = this.http.get("https://github.com/login/oauth/authorize",
            {
                headers: new HttpHeaders({
                    'Content-Type': 'application/json'
                }),
                params: new HttpParams({
                    client_id: this.clientId,
                    redirect_url: this.redirectUrl,
                    scope: "", // default is empty
                    state: this.state,
                    allow_singup: false // default is true
                })
            });
        console.log(this.githubApiParam1);
        httpObj.subscribe(this.OAuth1Next, this.RequestError);
    }

    OAuth1Next(res: GitHubApiParam1) {
        console.log("Response");
        console.log(res);
        this.githubApiParam2["code"] = res["code"];
    }

    OAuth2() {
        var httpObj = this.http.post(this.OAuthURL2, this.githubApiParam2, this.httpOptions);
        console.log(this.githubApiParam2);
        httpObj.subscribe(this.RequestNext, this.RequestError);
    }

    OAuth3() {

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
