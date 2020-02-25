import { observable, action } from "mobx";
import { GoogleLoginResponse } from "react-google-login";

export class GoogleOAuthStore {
  @observable public response?: GoogleLoginResponse;

  @action public setResponse(res: GoogleLoginResponse) {
    this.response = res;
  }
}
