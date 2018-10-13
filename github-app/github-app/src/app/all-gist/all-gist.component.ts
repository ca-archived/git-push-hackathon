import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-all-gist',
  templateUrl: './all-gist.component.html',
  styleUrls: ['./all-gist.component.css']
})
export class AllGistComponent implements OnInit {

  constructor(
      private apiService : ApiService
  ) { }

  ngOnInit() {
    console.log("GithubApiTest")
    this.apiService.GithubApiTest();
  }

}
