import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MenuComponent } from './menu/menu.component';
import {AllGistComponent} from "./all-gist/all-gist.component";
import {AllGistUserComponent} from "./all-gist-user/all-gist-user.component";
import {PostGistComponent} from "./post-gist/post-gist.component";
import {OauthComponent} from "./oauth/oauth.component";
import {OauthRedirectComponent} from "./oauth-redirect/oauth-redirect.component";

const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'oauth', component: OauthComponent },
    { path: 'oauth-redirect', component: OauthRedirectComponent },
    { path: 'all-gist', component: AllGistComponent },
    { path: 'all-gist-user', component: AllGistUserComponent },
    { path: 'post-gist', component: PostGistComponent }
];

@NgModule({
  exports: [ RouterModule ],
  imports: [ RouterModule.forRoot(routes) ]
})

export class AppRoutingModule { }
