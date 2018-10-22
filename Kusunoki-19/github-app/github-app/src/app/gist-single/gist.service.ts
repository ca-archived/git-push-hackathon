import {Injectable} from '@angular/core';
import {GistHtmlComponent} from './gist-html.component';
import {GistItem} from'./gist-item';
import {ApiService} from "../api.service";

@Injectable()

export class GistService {

    gistItems: GistItem[] = [];

    constructor(private apiService: ApiService,) {
    }

    getGistItems(page: number = 1, readLimit: number = 10) {

        this.gistItems = [];
        if (page < 1) page = 1;

        var getGistObsbles = this.apiService.GetAllGistDataReq();
        getGistObsbles.subscribe(
            res => {
                try {
                    // Data handling start ///////////////////////////////////////////////
                    console.log("GistService Response");
                    console.log(res);
                    let fileName: string = "";
                    let count: number = 0;
                    let gist_count : number = 0;

                    for (let res_key in res) {
                        if (readLimit * (page - 1) > count) { //表示ページまで行っていない
                            count++;
                            continue;
                        }
                        if (readLimit * page <= count) { //表示ページを超えた
                            count++;
                            break;
                        }
                        if (!(res.hasOwnProperty(res_key))) { //Gistがない
                            break;
                        }
                        if (res[res_key].hasOwnProperty("files")) { //keyからファイル名を取得
                            for (let key in res[res_key]["files"]) fileName = key;
                        } else { //無い時は無し
                            fileName = "";
                        }
                        console.log("file name :" + fileName);
                        //コンポーネントの情報を入れる
                        this.gistItems[gist_count] = new GistItem(GistHtmlComponent,
                            {
                                id: res[res_key]["id"],
                                owner_name: res[res_key]["owner"]["login"],
                                file_name: fileName
                            });
                        gist_count++; //Item数
                        count++; //page カウント
                    }
                    // Data handling end///////////////////////////////////////////////
                } catch (error) {
                    console.log("response data handling error");
                    console.log(error);
                }
            },
            error => {
                console.log("http request error (here is subscribe callback)");
                console.log(error);
            });
    }

    getGistItems_byUser(page: number = 1,readLimit: number = 10, userName: string = "Kusunoki-19") {
        this.gistItems = [];
        var getGistObsbles = this.apiService.GetAllGistDataReq_byUser(userName);
        getGistObsbles.subscribe(
            res => {
                try {
                    // Data handling start ///////////////////////////////////////////////
                    console.log("GistService Response");
                    console.log(res);
                    let fileName: string = "";
                    let count: number = 0;
                    let gist_count : number = 0;

                    for (let res_key in res) {
                        if (readLimit * (page - 1) > count) { //表示ページまで行っていない
                            count++;
                            continue;
                        }
                        if (readLimit * page <= count) { //表示ページを超えた
                            count++;
                            break;
                        }
                        if (!(res.hasOwnProperty(res_key))) { //Gistがない
                            break;
                        }
                        if (res[res_key].hasOwnProperty("files")) { //keyからファイル名を取得
                            for (let key in res[res_key]["files"]) fileName = key;
                        } else { //無い時は無し
                            fileName = "";
                        }
                        console.log("file name :" + fileName);
                        //コンポーネントの情報を入れる
                        this.gistItems[gist_count] = new GistItem(GistHtmlComponent,
                            {
                                id: res[res_key]["id"],
                                owner_name: res[res_key]["owner"]["login"],
                                file_name: fileName
                            });
                        gist_count++; //Item数
                        count++; //page カウント
                    }
                    // Data handling end///////////////////////////////////////////////
                } catch (error) {
                    console.log("response data handling error");
                    console.log(error);
                }
            },
            error => {
                console.log("http request error (here is subscribe callback)");
                console.log(error);
            });
    }

    getGists() {
        return this.gistItems;
    }


}