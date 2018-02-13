import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule }    from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { SubmissionViewComponent, SafeHtml } from './submission-view/submission-view.component';
import { PagenotfoundComponent} from './pagenotfound/pagenotfound.component'; 
import { SubmissionService } from './services/submission-service';
import { PartySelectionComponentComponent } from './party-selection-component/party-selection-component.component';
import { PartyViewComponentComponent } from './party-view-component/party-view-component.component';
import { Globals } from './app-properties';
import { PartyService } from './services/party-service';
import { IdentityTypeViewComponent } from './identity-type-view/identity-type-view.component';
import { IdentityTypeService } from './services/identitytype-service';
import { RequestService } from './services/request-service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SubmissionViewComponent,
    PagenotfoundComponent,
    PartySelectionComponentComponent,
    PartyViewComponentComponent,
    IdentityTypeViewComponent,
    SafeHtml
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    JsonpModule
  ],
  providers: [
    SubmissionService,
    PartyService,
    IdentityTypeService,
    RequestService,
    Globals
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
