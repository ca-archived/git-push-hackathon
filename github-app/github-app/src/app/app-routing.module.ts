import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommandPanelComponent } from './command-panel/command-panel.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MenuComponent } from './menu/menu.component';
import { MessageComponent } from './message/message.component';

const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'command-panel', component: CommandPanelComponent }
];

@NgModule({
  exports: [ RouterModule ],
  imports: [ RouterModule.forRoot(routes) ]
})

export class AppRoutingModule { }
