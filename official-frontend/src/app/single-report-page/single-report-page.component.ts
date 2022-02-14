import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-single-report-page',
  templateUrl: './single-report-page.component.html',
  styleUrls: ['./single-report-page.component.css']
})
export class SingleReportPageComponent implements OnInit {
  period: string;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      let id = params['id']
      this.period = id.split("_").join(" - ");
    })
  }

}
