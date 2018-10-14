import {Component, OnInit, Renderer2, ElementRef} from '@angular/core';
import {ApiService} from '../api.service';
import {GistData} from '../GistData';
import {Observable} from 'rxjs';

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

    constructor(private apiService: ApiService,
                private renderer: Renderer2,
                private elementRef: ElementRef,) {
    }


    ngOnInit() {
        //this.apiService.GithubApiTest();
        //this.apiService.GithubApiOAuthTest();
        //this.apiService.GithubApiOAuth_gist()

        //this.appendGistScript();

        this.GetGistData();

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
