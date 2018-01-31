import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule }    from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { SubmissionListComponent } from './submission-list/submission-list.component';
import { SubmissionViewComponent } from './submission-view/submission-view.component';
import { PagenotfoundComponent} from './pagenotfound/pagenotfound.component'; 
import { SubmissionService } from './services/submission-service';
import { PartySelectionComponentComponent } from './party-selection-component/party-selection-component.component';
import { PartyViewComponentComponent } from './party-view-component/party-view-component.component';
import { Globals } from './app-properties';
import { PartyService } from './services/party-service';
import { ModalService } from './modal-window/modal.service';
import { ModalComponent } from './modal-window/modal.component';
import { IdentityTypeListComponent } from './identity-type-list/identity-type-list.component';
import { IdentityTypeViewComponent } from './identity-type-view/identity-type-view.component';
import { IdentityTypeService } from './services/identitytype-service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SubmissionListComponent,
    SubmissionViewComponent,
    PagenotfoundComponent,
    PartySelectionComponentComponent,
    PartyViewComponentComponent,
    ModalComponent,
    IdentityTypeListComponent,
    IdentityTypeViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    JsonpModule,
  ],
  providers: [
    SubmissionService,
    PartyService,
    IdentityTypeService,
    ModalService,
    Globals
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
