import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { FormControl , FormGroup} from '@angular/forms'

@Component({
  selector: 'app-post-gist',
  templateUrl: './post-gist.component.html',
  styleUrls: ['./post-gist.component.css']
})
export class PostGistComponent implements OnInit {

  postGistForm = new FormGroup({
    description : new FormControl(''),
    release : new FormControl(''),
    fileName : new FormControl(''),
    content: new FormControl(''),
  });

  constructor(
      private apiService : ApiService
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    this.apiService.GetPostGistReq();

  }

}
