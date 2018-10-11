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
        {name : "HOME" , parent:"", url:"/"},
        {name : "Gist" , parent:"", url:""},
        {name : "view All gist", parent:"Gist", url:"/all-gist"},
        {name : "post gist", parent:"Gist", url:"/post-gist"},
    ];

    ngOnInit() {
    }

}
