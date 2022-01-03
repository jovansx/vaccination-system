import { Component, OnInit } from '@angular/core';
import { IVaccine, Vaccine } from '../models/vaccine.model';
import { VaccineService } from '../services/vaccine.service';
import { XmlConverterService } from '../services/xml-converter.service';

@Component({
  selector: 'app-vaccine-page',
  templateUrl: './vaccine-page.component.html',
  styleUrls: ['./vaccine-page.component.css']
})
export class VaccinePageComponent implements OnInit {
  displayedColumns: string[] = ['name', 'series', 'manufacturer', 'sideEffects', 'amount', 'edit'];
  vaccines: IVaccine[] = []

  constructor(private vaccineService: VaccineService, private _xml_parser: XmlConverterService) { }

  ngOnInit(): void {
    this.getAllVaccines()
  }

  async saveChanges(element: IVaccine) {
    await this.vaccineService.updateAmount(element)
    this.getAllVaccines()
  }

  getAllVaccines() {
    this.vaccineService.getAll()
      .subscribe(res => {
        let response = this._xml_parser.parseXmlToObject(res);
        let vaccinesArray: IVaccine[] = []
        response.VACCINES.forEach((element: { NAME: string[]; TYPE: string[]; SERIES: number[]; MANUFACTURER: string[]; SIDEEFFECT: string[]; AMOUNT: number[]; }) => {
          vaccinesArray.push(new Vaccine(element.NAME[0], element.TYPE[0],element.SERIES[0],element.MANUFACTURER[0], element.SIDEEFFECT[0], element.AMOUNT[0]))
        });
        this.vaccines = [...vaccinesArray]
      })
  }
}
