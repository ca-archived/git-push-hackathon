import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

    constructor() {
    }

    menuItems: string[] = [
        "gist",
        "view All gist",
        "view gist by id",
        "post gist",
    ];

    ngOnInit() {
    }

}
