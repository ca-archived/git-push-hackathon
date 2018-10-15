import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api.service';
import {GistService} from '../gist-single/gist.service';

import {GistItem} from'../gist-single/gist-item';

@Component({
    selector: 'app-all-gist',
    templateUrl: './all-gist.component.html',
    styleUrls: ['./all-gist.component.css']
})
export class AllGistComponent implements OnInit {

    gists : GistItem[];

    constructor(
        private apiService: ApiService,
        private gistService : GistService,
    ) {}

    ngOnInit() {
        this.gists = this.gistService.getGists();
    }


}
