import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router';
import { SubmissionService } from '../services/submission-service';
import { Submission, Data, SubmissionStatus } from '../models/submission';


@Component({
  selector: 'app-submission-view',
  templateUrl: './submission-view.component.html',
  styleUrls: ['./submission-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SubmissionViewComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router,private submissionService: SubmissionService) { }
  id:string;
  submission:Submission;
  data:Data[];
  submissionMessage:string;

  getSubmission(){
    this.submissionService.getSubmission(this.id)
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
    this.submissionService.updateSubmission(this.id,this.submission)
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
    this.submissionService.updateSubmission(this.id,this.submission)
    .subscribe(
      submission => {
        this.submission = submission, 
        console.log(this.submission)
        this.submissionMessage = "Request submitted successfully";
      });
      err => this.submissionMessage = "Error submitting request, please try again later";
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getSubmission();
    console.log(this.id);
  }

}
