import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    OAuthURL1: string = "https://github.com/login/oauth/authorize"; //GET user redirected here
    OAuthURL2: string = "https://github.com/login/oauth/access_token"; //POST
    OAuthURL3: string = "https://api.github.com/user?access_token="; //GET

    clientId: string = "0bc6d4e0794201162940";
    clientSecret: string = "473798ee1cdead1152b7a66e164e7bb1b873f049";
    redirectUrl: string = "http://localhost:4200";
    state: string = "alkjsoivuejngiausy"; //random文字列ってこれでいいの？

    redirect_code: string = "";
    redirect_state: string = "";

    access_token : string ="xxx";

    temp: string = "";


    httpOptions = {
        header: new HttpHeaders({
            'Content-Type': 'application/json',
        })
    };

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


    private RequestNext(res) {
        console.log("Response");
        console.log(res);
    }

    private RequestError(error) {
        console.log("Error");
        console.log(error);
    }


}
