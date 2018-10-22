import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api.service';
import {GistService} from '../gist-single/gist.service';

import {GistItem} from'../gist-single/gist-item';

@Component({
    selector: 'app-all-user-gist',
    templateUrl: './all-gist-user.component.html',
    styleUrls: ['./all-gist-user.component.css']
})
export class AllGistUserComponent implements OnInit {

    gists: GistItem[];
    readLimit: number = 5;
    page: number = 1;
    userName: string = "Kusunoki-19";
    constructor(private apiService: ApiService,
                private gistService: GistService) {
    }

    ngOnInit() {
        this.gistService.getGistItems_byUser(this.page, this.readLimit, this.userName);
        this.gists = this.gistService.getGists(); //connect gistService.gistItem -- this.gists
    }

    reloadGists() {
        this.gistService.getGistItems_byUser(this.page, this.readLimit, this.userName);
        this.gists = this.gistService.getGists(); //connect gistService.gistItem -- this.gists
    }


}
