import { Injectable } from '@angular/core';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class OauthService {

  constructor(private messageService: MessageService) { }

  OnInit() {
    this.messageService.add_message({contributor : "oauth service", text : "service excuted"});
  }
  getOAuth() {

  }

}
