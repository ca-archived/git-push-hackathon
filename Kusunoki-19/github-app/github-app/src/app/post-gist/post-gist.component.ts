import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api.service';
import {FormControl, FormGroup} from '@angular/forms'

@Component({
    selector: 'app-post-gist',
    templateUrl: './post-gist.component.html',
    styleUrls: ['./post-gist.component.css']
})
export class PostGistComponent implements OnInit {

    //0 --> not post yet
    //1 --> post is success
    //-1 -> post is failure
    postingGistSucceded : number = 0;

    postGistForm = new FormGroup({
        description: new FormControl(''),
        release: new FormControl(''),
        fileName: new FormControl(''),
        content: new FormControl(''),
    });

    constructor(private apiService: ApiService) {
        //this.apiService.GetAcquiredAccessToken();
        this.apiService.GetAcquiredAccessToken_byCookie();
    }


    ngOnInit() {
    }

    onSubmit() {
        var postGistObsbles = this.apiService.GetPostGistReq(
            this.postGistForm["value"]["description"],
            this.postGistForm["value"]["release"],
            this.postGistForm["value"]["fileName"],
            this.postGistForm["value"]["content"]
        );
        postGistObsbles.subscribe(
            res => {
                try {
                    console.log("Posting the Gist is succeded");
                    console.log(res);
                    this.postingGistSucceded = 1;
                } catch (error) {
                    console.log("response data handling error");
                    console.log(error);
                    this.postingGistSucceded = -1;
                }
            },
            error=> {
                console.log("http request error (here is subscribe callback)");
                console.log(error);
                this.postingGistSucceded = -1;
            }
        );

    }

}
