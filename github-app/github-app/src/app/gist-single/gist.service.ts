import {Injectable} from '@angular/core';
import { GistHtmlComponent } from './gist-html.component';
import { GistItem} from'./gist-item';
import {ApiService} from "../api.service";

@Injectable()

export class GistService {

    constructor(private apiService: ApiService,) {
        let i : number = 0;
        var getGistObsbles = this.apiService.createGetGistDataObserval();
        getGistObsbles.subscribe(res => {
            for (i = 0; i < res.length ; i++) { //this response has length propaerty if succeded
                this.gistItems[i] = new GistItem(GistHtmlComponent, {id: res[i]["id"], owner_name: res[i]["owner"]["login"]});
            }
        });
    }

    gistItems : GistItem[] = [];

    getGists() {
        return this.gistItems ;
    }

}