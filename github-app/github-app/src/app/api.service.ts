import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    //URL = "https://github.com/login/oauth/authorize";

    constructor(private http: HttpClient) {
    }

    GetTest() {
        console.log("GetTest");
        let TEST_URL = "http://httpbin.org/ip";
        //TEST_URL のhttp request用のオブジェクトを作成
        var httpObj = this.http.get(TEST_URL);
        //subscribeに登録されている処理(API処理も含む)を実行後、ApiNextを実行。
        //エラーならApiErrorを実行
        httpObj.subscribe(this.GetNext, this.GetError);
    }

    Get(url: string) {
        var httpObj = this.http.get(url);
        httpObj.subscribe(this.GetNext, this.GetError);
    }

    private GetNext(response) {
        console.log("GetRes");
        console.log(response);
    }

    private GetError(error) {
        console.log(error);
    }

    PostTest() {
        console.log("PostTest");
        let TEST_URL = "http://httpbin.org/post";
        var reqBody = {test: "test_text"};
        console.log(reqBody);

        var httpObj = this.http.post(TEST_URL, reqBody);
        httpObj.subscribe(this.PostNext, this.PostError);
    }

    Post(url: string, reqBody: {[index: string]: string}[]) {
        var httpObj = this.http.post(url, reqBody);
        httpObj.subscribe(this.PostNext, this.PostError);
    }

    private PostNext(response) {
        console.log("PostRes");
        console.log(response);
    }

    private PostError(error) {
        console.log(error);
    }


}
