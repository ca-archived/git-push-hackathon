import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  // messages = { ["contributor", "text" } , { , } , ... ]
  messages : {contributor:string, text:string}[] = [];

  constructor() { }

  add_message(message : {contributor:string, text:string} ) {
    this.messages.push(message);
  }
}