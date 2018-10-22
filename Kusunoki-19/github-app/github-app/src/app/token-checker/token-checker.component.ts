import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api.service';

@Component({
    selector: 'app-token-checker',
    templateUrl: './token-checker.component.html',
    styleUrls: ['./token-checker.component.css']
})
export class TokenCheckerComponent implements OnInit {

    // 0 --> not check yet
    // 1 --> token is ok
    //-1 --> token isn't ok
    tokenIsOK: number = 0;

    constructor(private apiService: ApiService) {
    }

    ngOnInit() {
        var tokenCheker = this.apiService.GetTokenCheckReq();
        tokenCheker.subscribe(
            res => {
                console.log("token checker : returned reponse");
                console.log(res);
                this.tokenIsOK = 1;
            },
            err => {
                console.log("token checker : returned error");
                console.log(err);
                this.tokenIsOK =-1;
            }
        )
    }

}
