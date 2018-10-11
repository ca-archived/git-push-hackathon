import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

    constructor() {
    }

    menuItems: {name : string, parent: string, url: string}[] = [
        {name : "gist" , parent:"", url:""},
        {name : "view All gist", parent:"gist", url:"/all-gist"},
        {name : "post gist", parent:"gist", url:"/post-gist"},
    ];

    ngOnInit() {
    }

}
