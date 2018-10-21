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

        {name : "> HOME"                 , parent:""     , url:"/"},
        {name : "> OAuth 認証"           , parent:""     , url:"/oauth"},
        {name : "Gists"                   , parent:""     , url:""},
        {name : "> All Gist"            , parent:"Gists", url:"/all-gist"},
        {name : "> All Gist (User指定)", parent:"Gists", url:"/all-gist-user"},
        {name : "> Post Gist"           , parent:"Gists", url:"/post-gist"},
    ];

    ngOnInit() {
    }

}
