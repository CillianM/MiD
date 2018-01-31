import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SubmissionViewComponent } from './submission-view/submission-view.component';
import { PagenotfoundComponent} from './pagenotfound/pagenotfound.component'; 
import { PartyViewComponentComponent } from './party-view-component/party-view-component.component';
import { PartySelectionComponentComponent } from './party-selection-component/party-selection-component.component';
import { IdentityTypeViewComponent } from './identity-type-view/identity-type-view.component';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'party', component: PartySelectionComponentComponent },
  {
    path: 'party/:partyId/identity/:identityId',
    component: IdentityTypeViewComponent
  },
  {
    path: 'party/:partyId',
    component: PartyViewComponentComponent
  },
  {
    path: 'party/:partyId/submission/:submissionId',
    component: SubmissionViewComponent
  },
  { 
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  { path: '**', component: PagenotfoundComponent }
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}