import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommandPanelComponent } from './command-panel/command-panel.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MenuComponent } from './menu/menu.component';
import {AllGistComponent} from "./all-gist/all-gist.component";
import {PostGistComponent} from "./post-gist/post-gist.component";
import {OauthComponent} from "./oauth/oauth.component";
import {OauthRedirectComponent} from "./oauth-redirect/oauth-redirect.component";

const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'command-panel', component: CommandPanelComponent },
    { path: 'oauth', component: OauthComponent },
    { path: 'oauth-redirect', component: OauthRedirectComponent },
    { path: 'all-gist', component: AllGistComponent },
    { path: 'post-gist', component: PostGistComponent }
];

@NgModule({
  exports: [ RouterModule ],
  imports: [ RouterModule.forRoot(routes) ]
})

export class AppRoutingModule { }
