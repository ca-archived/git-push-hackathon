import {Injectable} from '@angular/core';
import {GistHtmlComponent} from './gist-html.component';
import {GistItem} from'./gist-item';
import {ApiService} from "../api.service";

@Injectable()

export class GistService {

    gistItems: GistItem[] = [];

    constructor(private apiService: ApiService,) {
    }

    getGistItems(readLimit : number = 10 ){
        this.gistItems = [];
        var getGistObsbles = this.apiService.GetAllGistDataReq();
        getGistObsbles.subscribe(
            res => {
                try {
                    // Data handling start ///////////////////////////////////////////////
                    console.log("GistService Response");
                    console.log(res);
                    let fileName : string = "";
                    for (let count in res) {
                        if(count >= readLimit) break;
                        //this response has length propaerty if succeded

                        if(res[count].hasOwnProperty("files")) {
                            for(let key in res[count]["files"]) fileName = key;
                        } else{
                            fileName ="";
                        }
                        console.log("file name :" + fileName);

                        this.gistItems[count] = new GistItem( GistHtmlComponent ,
                            {
                                id: res[count]["id"],
                                owner_name: res[count]["owner"]["login"],
                                file_name : fileName
                            });
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

    getGistItems_byUser(readLimit : number = 10 , userName : string = "Kusunoki-19"){
        this.gistItems = [];
        var getGistObsbles = this.apiService.GetAllGistDataReq_byUser(userName);
        getGistObsbles.subscribe(
            res => {
                try {
                    // Data handling start ///////////////////////////////////////////////
                    console.log("GistService Response");
                    console.log(res);
                    let fileName : string = "";
                    let count_int : number = 0;
                    for (let count in res) {
                        if(count_int >= readLimit) break;

                        if(res[count].hasOwnProperty("files")) {
                            for(let key in res[count]["files"]) fileName = key;
                        } else{
                            fileName ="";
                        }
                        this.gistItems[count] = new GistItem( GistHtmlComponent ,
                            {
                                id: res[count]["id"],
                                owner_name: res[count]["owner"]["login"],
                                file_name : fileName
                            });
                        count_int++;
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