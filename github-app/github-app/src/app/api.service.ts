import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    //URL = "https://github.com/login/oauth/authorize";
    URL = "http://httpbin.org/ip";

    constructor(private http: HttpClient) {
    }

    GetTest() {
        //http.getをするためのオブジェクトを生成
        var httpObj = this.http.get(this.URL);
        //成功時・失敗時の動作を指定する。
        httpObj.subscribe(this.getSuccess, this.getError);
    }

    //http.getが成功した時に走るメソッド
    getSuccess(response) {
        console.log(response);
    }

    //http.getが失敗した時に走るメソッド
    getError(error) {
        console.log(error);
    }


}
