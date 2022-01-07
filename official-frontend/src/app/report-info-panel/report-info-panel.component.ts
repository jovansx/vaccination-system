import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { IIzvestajOImunizaciji, IzvestajOImunizaciji } from '../models/report.model';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { combineLatest, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ReportService } from '../services/report.service';
import { XmlConverterService } from '../services/xml-converter.service';
import { makeReportFromResponse } from '../utils';

@Component({
  selector: 'app-report-info-panel',
  templateUrl: './report-info-panel.component.html',
  styleUrls: ['./report-info-panel.component.css']
})
export class ReportInfoPanelComponent implements OnInit {

  @Input() izvestajOImunizaciji: IIzvestajOImunizaciji = new IzvestajOImunizaciji("","",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
  @Input() loaded: boolean = true;
  @Input() componentPart: boolean = false;

  constructor(private route: ActivatedRoute, private reportService: ReportService, private _xml_parser: XmlConverterService) { }

  ngOnInit(): void {
    if(!this.componentPart) {
      this.loaded = false;
      this.route.params
        .pipe(
          switchMap(params => {
            return of(params['id']);
          }),
          switchMap(id => {
            return this.reportService.getById(id)
          })
        )
        .subscribe((res) => {
          let response = this._xml_parser.parseXmlToObject(res);
          this.izvestajOImunizaciji = makeReportFromResponse(response);
          this.loaded = true;
        })
    }
  }

}
