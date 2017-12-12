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

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SubmissionListComponent,
    SubmissionViewComponent,
    PagenotfoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    JsonpModule,
  ],
  providers: [
    SubmissionService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
