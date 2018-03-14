import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router';
import { SubmissionService } from '../services/submission-service';
import { Submission, Data, SubmissionData, SubmissionStatus } from '../models/submission';
import { Party } from '../models/party';
import { PartyService } from '../services/party-service';
import { DomSanitizer } from '@angular/platform-browser';
import { Pipe } from '@angular/core';



@Component({
  selector: 'app-submission-view',
  templateUrl: './submission-view.component.html',
  styleUrls: ['./submission-view.component.css',
  '../app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SubmissionViewComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private submissionService: SubmissionService,
    private partyService: PartyService
    ) {}
  submissionId:string;
  partyId:string;
  submission:Submission;
  submissionData: SubmissionData;
  data:Data[];
  submissionMessage:string;
  party:Party;

  getSubmission(){
    this.submissionService.getSubmission(this.submissionId,this.partyId)
    .subscribe(
      submission => {
        this.submission = submission, //Bind to view
        this.submissionData = JSON.parse(this.submission.data);
        console.log(this.submission)
        console.log(this.submissionData)
        this.getData();
      });
      err => console.log(err);
  }

  getData(){
    //TODO implement parsing of json in data section
  }

  acceptRequest(){
    this.submission.status = SubmissionStatus.ACCEPTED;
    this.submissionService.updateSubmission(this.submissionId,this.submission)
    .subscribe(
      submission => {
        this.submission = submission,
        console.log(this.submission)
        this.submissionMessage = "Request submitted successfully";
        this.router.navigateByUrl('/party/' + this.partyId);
      });
      err => this.submissionMessage = "Error submitting request, please try again later";
  }

  rejectRequest(){
    this.submission.status = SubmissionStatus.REJECTED;
    this.submissionService.updateSubmission(this.submissionId,this.submission)
    .subscribe(
      submission => {
        this.submission = submission, 
        console.log(this.submission)
        this.submissionMessage = "Request submitted successfully";
        this.router.navigateByUrl('/party/' + this.partyId);
      });
      err => this.submissionMessage = "Error submitting request, please try again later";
  }

  getParty(){
    this.partyService.getParty(this.partyId)
    .subscribe(
      party => {
        this.party = party,
        console.log(this.party)
      });
      err => console.log(err);
  }

  ngOnInit() {
    this.submissionId = this.route.snapshot.paramMap.get('submissionId');
    this.partyId = this.route.snapshot.paramMap.get('partyId');
    this.getSubmission();
    this.getParty();
  }

}

@Pipe({name: 'safeHtml'})
export class SafeHtml {
  constructor(private sanitizer:DomSanitizer){}

  transform(html) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(html);
  }
}
