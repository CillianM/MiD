import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Application } from '../Application';
import { SubmissionService } from '../services/submission-service';
import { Submission } from '../models/submission';


@Component({
  selector: 'app-submission-list',
  templateUrl: './submission-list.component.html',
  styleUrls: ['./submission-list.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SubmissionListComponent implements OnInit {
 
  submissions = [];
  constructor(private submissionService: SubmissionService) { }

  getSubmissions(){
    this.submissionService.getSubmissions()
    .subscribe(
      submissions => {
        this.submissions = submissions, //Bind to view
        console.log(this.submissions)
        console.log(this.submissions[0])
      });
      err => console.log(err);
  }

  ngOnInit() {
    var sub = new Submission();
    sub.id = "test";
    sub.date = "test";
    this.submissions.push(sub);
    console.log(this.submissions)
    this.getSubmissions();
  }

}
