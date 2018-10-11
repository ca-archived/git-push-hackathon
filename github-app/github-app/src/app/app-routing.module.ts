import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommandPanelComponent } from './command-panel/command-panel.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MenuComponent } from './menu/menu.component';
import { MessageComponent } from './message/message.component';
import {AllGistComponent} from "./all-gist/all-gist.component";
import {PostGistComponent} from "./post-gist/post-gist.component";

const routes: Routes = [
    { path: '', component: CommandPanelComponent },
    { path: 'command-panel', component: DashboardComponent },
    { path: 'all-gist', component: AllGistComponent },
    { path: 'post-gist', component: PostGistComponent }
];

@NgModule({
  exports: [ RouterModule ],
  imports: [ RouterModule.forRoot(routes) ]
})

export class AppRoutingModule { }
