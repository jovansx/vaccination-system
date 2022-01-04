import { Component, OnInit } from '@angular/core';
import { IIzvestajOImunizacijiPeriod, IzvestajOImunizacijiPeriod } from '../models/report.model';
import { ReportService } from '../services/report.service';
import { XmlConverterService } from '../services/xml-converter.service';

@Component({
  selector: 'app-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: ['./report-page.component.css']
})
export class ReportPageComponent implements OnInit {
  dateTo = ""
  reportsPeriod: IIzvestajOImunizacijiPeriod[] = []

  constructor(private reportService: ReportService, private _xml_parser: XmlConverterService) { }

  ngOnInit(): void {
  }

  getAllReports() {
    this.reportService.getAll()
      .subscribe(res => {
        let response = this._xml_parser.parseXmlToObject(res);
        response.IZVESTAJPERIOD.forEach((element: { ID: string; PERIOD: string; }) => {
          this.reportsPeriod.push(new IzvestajOImunizacijiPeriod(element.ID, element.PERIOD));
        });
      })
  }
}
