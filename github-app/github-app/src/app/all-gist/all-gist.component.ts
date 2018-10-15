import {Component, OnInit, Renderer2} from '@angular/core';
import {ApiService} from '../api.service';
import {GistService} from '../gist-single/gist.service';
import {GistData} from '../GistData';
import {Observable} from 'rxjs';

import {GistItem} from'../gist-single/gist-item';

@Component({
    selector: 'app-all-gist',
    templateUrl: './all-gist.component.html',
    styleUrls: ['./all-gist.component.css']
})
export class AllGistComponent implements OnInit {

    gistData: GistData = {
        id: "init", owner_name: "init"
    };
    temp: string = "init";

    gists : GistItem[];

    constructor(private apiService: ApiService,
                private renderer: Renderer2,
                private gistService : GistService,
    ) {
    }


    ngOnInit() {
        //this.apiService.GithubApiTest();
        //this.apiService.GithubApiOAuthTest();
        //this.apiService.GithubApiOAuth_gist()

        //this.appendGistScript();

        //this.GetGistData();

        this.gists = this.gistService.getGists();

    }

    GetGistData(): void {

        console.log("this.temp");
        console.log(this.temp);
        var getGistDataObsbles = this.apiService.createGetGistDataObserval();
        getGistDataObsbles.subscribe(res => {
            this.gistData =
            {"id": res["0"]["id"], "owner_name": res["0"]["owner"]["login"]};
            this.appendGistScript();
        });

    }

    appendGistScript() {
        /*
        console.log("appendGistScript");
        const gist_script = document.createElement('gh-gist');
        gist_script.setAttribute("src", "https://gist.github.com/" + this.gistData.owner_name + "/" + this.gistData.id + ".js");
        var div = document.getElementById('gist_area');
        div.parentNode.insertBefore(gist_script, div.nextSibling);
        */

        console.log("appendGistScript");
        const gist_script = document.createElement('app-gist-single');
        //gist_script.setAttribute("[gist]", "this.gistData");
        var div = document.getElementById('gist_area');
        div.parentNode.insertBefore(gist_script, div.nextSibling);
    }


}
