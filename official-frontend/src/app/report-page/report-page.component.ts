import { Component, OnInit } from '@angular/core';
import { IIzvestajOImunizacijiPeriod, IzvestajOImunizaciji, IzvestajOImunizacijiPeriod } from '../models/report.model';
import { ReportService } from '../services/report.service';
import { XmlConverterService } from '../services/xml-converter.service';
import { FormControl } from '@angular/forms';
import { format } from 'date-fns'
import { makeReportFromResponse } from '../utils';

@Component({
  selector: 'app-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: ['./report-page.component.css']
})
export class ReportPageComponent implements OnInit {
  
  reportsPeriod: IIzvestajOImunizacijiPeriod[] = []
  dateFrom = new FormControl(new Date());
  dateTo = new FormControl(new Date());
  xmlIzvestajOImunizaciji: string = ""
  izvestajOImunizaciji: IzvestajOImunizaciji = new IzvestajOImunizaciji(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
  loaded: boolean = true;
  allReportsLoaded: boolean = true;

  constructor(private reportService: ReportService, private _xml_parser: XmlConverterService) { }

  ngOnInit(): void {
  }

  async getAllReports() {
    this.reportsPeriod = []
    this.allReportsLoaded = false;
    this.reportService.getAll()
      .subscribe(res => {
        let response = this._xml_parser.parseXmlToObject(res);
        response.IZVESTAJPERIOD.forEach((element: { ID: string; PERIOD: string; }) => {
          this.reportsPeriod.push(new IzvestajOImunizacijiPeriod(element.ID[0], element.PERIOD[0]));
        });
        this.allReportsLoaded = true;
        console.log(this.reportsPeriod)
      })
  }

  getReportForPeriod() {
    this.loaded = false;
    let dateFromString = format(this.dateFrom.value, 'yyyy-MM-dd')
    let dateToString = format(this.dateTo.value, 'yyyy-MM-dd')
    this.reportService.getReportForPeriod(dateFromString, dateToString)
      .subscribe(res => {
        this.xmlIzvestajOImunizaciji = res
        let response = this._xml_parser.parseXmlToObject(res);
        this.izvestajOImunizaciji = makeReportFromResponse(response);
        this.loaded = true;
        console.log(this.izvestajOImunizaciji)
      })
  }
}
