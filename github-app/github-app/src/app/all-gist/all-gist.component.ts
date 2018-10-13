import { Component, OnInit ,  Renderer2 } from '@angular/core';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-all-gist',
  templateUrl: './all-gist.component.html',
  styleUrls: ['./all-gist.component.css']
})
export class AllGistComponent implements OnInit {

  constructor(
      private apiService : ApiService,
      private renderer : Renderer2
  ) { }

  ngOnInit() {
    //this.apiService.GithubApiTest();
    this.apiService.GithubApiOAuthTest();
    this.apiService.GithubApiOAuth_gist().then(this.appendGistScript);

    //this.appendGistScript();

  }

  appendGistScript() {
    var gist_script = this.renderer.createElement("script");
    console.log("debug point");
    console.log("gist_owner_name");
    console.log(this.apiService.gist_owner_name);
    gist_script.setAttribute("src", "https://gist.github.com/"+this.apiService.gist_owner_name+"/"+this.apiService.gist_id+".js");
    var div = document.getElementById('gist_area');
    div.parentNode.insertBefore(gist_script, div.nextSibling);
  }


}
