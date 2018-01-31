import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Application } from '../Application';
import { SubmissionService } from '../services/submission-service';
import { Submission } from '../models/submission';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-submission-list',
  templateUrl: './submission-list.component.html',
  styleUrls: ['./submission-list.component.css',
  '../app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SubmissionListComponent implements OnInit {
 
  partyId: string;
  submissions = [];
  constructor(private route: ActivatedRoute,private router: Router,private submissionService: SubmissionService) { }

  getSubmissions(){
    this.submissionService.getSubmissions(this.partyId)
    .subscribe(
      submissions => {
        this.submissions = submissions,
        console.log(this.submissions)
      });
      err => console.log(err);
  }

  ngOnInit() {
    var sub = new Submission();
    this.partyId = this.route.snapshot.paramMap.get('partyId');
    this.getSubmissions();
  }

}
