import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MessageComponent} from './message/message.component';
import {MenuComponent} from './menu/menu.component';
import {CommandPanelComponent} from './command-panel/command-panel.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import { AllGistComponent } from './all-gist/all-gist.component';
import { PostGistComponent } from './post-gist/post-gist.component';
import { OauthComponent } from './oauth/oauth.component';
import { OauthRedirectComponent } from './oauth-redirect/oauth-redirect.component';

import { GistModule } from '@sgbj/angular-gist';

@NgModule({
    declarations: [
        AppComponent,
        DashboardComponent,
        MessageComponent,
        MenuComponent,
        CommandPanelComponent,
        AllGistComponent,
        PostGistComponent,
        OauthComponent,
        OauthRedirectComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        GistModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
