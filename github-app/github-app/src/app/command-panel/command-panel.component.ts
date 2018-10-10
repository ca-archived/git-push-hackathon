import { Component, OnInit} from '@angular/core';
import { ApiService } from '../api.service';

@Component({
    selector: 'app-command-panel',
    templateUrl: './command-panel.component.html',
    styleUrls: ['./command-panel.component.css']
})
export class CommandPanelComponent implements OnInit {
    //URL = "https://github.com/login/oauth/authorize";
    URL = "http://httpbin.org/ip";

    constructor(
        private apiService : ApiService
    ) {    }

    ngOnInit() {
        console.log("command-panel OnInit");
        this.apiService.GetTest();
        this.apiService.PostTest();
    }

}
