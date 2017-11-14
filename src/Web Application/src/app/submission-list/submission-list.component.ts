import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core'


@Component({
  selector: 'app-submission-list',
  templateUrl: './submission-list.component.html',
  styleUrls: ['./submission-list.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SubmissionListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
