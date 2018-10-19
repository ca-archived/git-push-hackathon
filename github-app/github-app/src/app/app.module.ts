import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';

import { FormsModule }      from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MenuComponent} from './menu/menu.component';
import {CommandPanelComponent} from './command-panel/command-panel.component';
import { AllGistComponent } from './all-gist/all-gist.component';
import { AllGistUserComponent } from './all-gist-user/all-gist-user.component';
import { PostGistComponent } from './post-gist/post-gist.component';
import { OauthComponent } from './oauth/oauth.component';
import { OauthRedirectComponent } from './oauth-redirect/oauth-redirect.component';

import { GistSingleComponent } from './gist-single/gist-single.component';
import { GistHtmlComponent } from './gist-single/gist-html.component';
import { GistDirective } from './gist-single/gist.directive';
import { GistService } from './gist-single/gist.service';

import { GistModule } from '@sgbj/angular-gist';


@NgModule({
    declarations: [
        AppComponent,
        DashboardComponent,
        MenuComponent,
        CommandPanelComponent,
        AllGistComponent,
        AllGistUserComponent,
        PostGistComponent,
        OauthComponent,
        OauthRedirectComponent,

        GistSingleComponent,
        GistHtmlComponent,
        GistDirective,
        AllGistUserComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        GistModule
    ],
    entryComponents: [
        GistHtmlComponent,
    ],
    providers: [GistService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
