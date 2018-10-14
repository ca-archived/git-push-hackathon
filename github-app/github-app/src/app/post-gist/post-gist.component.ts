import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-post-gist',
  templateUrl: './post-gist.component.html',
  styleUrls: ['./post-gist.component.css']
})
export class PostGistComponent implements OnInit {

  constructor(
      private apiService : ApiService
  ) { }

  ngOnInit() {
  }

}
