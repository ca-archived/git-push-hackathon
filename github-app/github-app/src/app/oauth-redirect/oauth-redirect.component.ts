import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-oauth-redirect',
  templateUrl: './oauth-redirect.component.html',
  styleUrls: ['./oauth-redirect.component.css']
})
export class OauthRedirectComponent implements OnInit {
  message : string = "";

  constructor(
      private apiService : ApiService,
      private route: ActivatedRoute
  ) {    }

  ngOnInit() {
    this.apiService.redirect_code = this.route.snapshot.queryParams["code"];
    this.apiService.redirect_state = this.route.snapshot.queryParams["state"];
    console.log(this.apiService.redirect_code);
    console.log(this.apiService.redirect_state);

    if(this.apiService.redirect_code == "") {
      this.message += "OAuth認証に失敗しました(codeパラメータが存在しない)\n";
      return;
    }
    if(this.apiService.redirect_state != this.apiService.state) {
      this.message += "OAuth認証に失敗しました(stateパラメータの不一致)\n";
      return;
    }
    if((this.apiService.redirect_code != "")
        && (this.apiService.redirect_state == this.apiService.state)) {
      this.message += "OAuth認証の第一段階に成功しました\n";
    }
    this.apiService.OAuth2();


  }
}
