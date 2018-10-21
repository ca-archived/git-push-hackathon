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

        {name : "HOME"                 , parent:""     , url:"/"},
        {name : "OAuth認証"           , parent:""     , url:"/oauth"},
        {name : "Gist"                 , parent:""     , url:""},
        {name : "All gist(だれでも!)", parent:"Gist", url:"/all-gist"},
        {name : "All gist(User指定)" , parent:"Gist", url:"/all-gist-user"},
        {name : "post gist"           , parent:"Gist", url:"/post-gist"},
    ];

    ngOnInit() {
    }

}
