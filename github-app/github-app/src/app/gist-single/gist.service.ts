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
                    console.log("GistService Response");
                    console.log(res);
                    for (let count = 0; count < res.length && count < readLimit; count) {
                        //this response has length propaerty if succeded
                        this.gistItems[count] = new GistItem( GistHtmlComponent ,
                            {
                                id: res[count]["id"],
                                owner_name: res[count]["owner"]["login"]
                            });
                        count++;
                    }
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