import {Component, OnInit, Renderer2 , ElementRef} from '@angular/core';
import {ApiService} from '../api.service';
import {Observable} from 'rxjs';

@Component({
    selector: 'app-all-gist',
    templateUrl: './all-gist.component.html',
    styleUrls: ['./all-gist.component.css']
})
export class AllGistComponent implements OnInit {

    constructor(
        private apiService: ApiService,
        private renderer: Renderer2,
        private elementRef: ElementRef,
    ) {}

    gist_id : string;
    gist_owner_name : string;

    ngOnInit() {
        //this.apiService.GithubApiTest();
        //this.apiService.GithubApiOAuthTest();
        //this.apiService.GithubApiOAuth_gist()

        //this.appendGistScript();

        var getGistDataObsbles = this.apiService.createGetGistDataObserval();
        getGistDataObsbles.subscribe(this.handleGistData);

    }

    private handleGistData(res) {
        console.log("handleGistData");
        console.log("res");
        console.log(res);
        let gist_id: string = res["0"]["id"];
        let gist_owner_name: string = res["0"]["owner"]["login"];

        this.gist_id = gist_id;
        this.gist_owner_name = gist_owner_name;

        console.log("gist_id");
        console.log(gist_id);
        console.log("gist_owner_name");
        console.log(gist_owner_name);

        //this.appendGistScript();

        console.log("appendGistScript");

        const gist_script = document.createElement('gh-gist');
        gist_script.setAttribute("src", "https://gist.github.com/" + this.gist_owner_name + "/" + this.gist_id + ".js");
        var div = document.getElementById('gist_area');
        div.parentNode.insertBefore(gist_script, div.nextSibling);
    }
    /*
    appendGistScript() {
        console.log("appendGistScript");
        var gist_script = this.renderer.createElement("script");
        gist_script.setAttribute("src", "https://gist.github.com/" + this.gist_owner_name + "/" + this.gist_id + ".js");
        var div = document.getElementById('gist_area');
        div.parentNode.insertBefore(gist_script, div.nextSibling);
    }
    */


}
