import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router';
import { SubmissionService } from '../services/submission-service';
import { Submission, Data, SubmissionStatus } from '../models/submission';


@Component({
  selector: 'app-submission-view',
  templateUrl: './submission-view.component.html',
  styleUrls: ['./submission-view.component.css',
  '../app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SubmissionViewComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router,private submissionService: SubmissionService) { }
  submissionId:string;
  partyId:string;
  submission:Submission;
  data:Data[];
  submissionMessage:string;

  getSubmission(){
    this.submissionService.getSubmission(this.submissionId)
    .subscribe(
      submission => {
        this.submission = submission, //Bind to view
        console.log(this.submission)
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
      });
      err => this.submissionMessage = "Error submitting request, please try again later";
  }

  ngOnInit() {
    this.submissionId = this.route.snapshot.paramMap.get('submissionId');
    this.partyId = this.route.snapshot.paramMap.get('partyId');
    this.getSubmission();
  }

}
