import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-submission-view',
  templateUrl: './submission-view.component.html',
  styleUrls: ['./submission-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SubmissionViewComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router) { }
  id:string;

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    console.log(this.id);
  }

}
