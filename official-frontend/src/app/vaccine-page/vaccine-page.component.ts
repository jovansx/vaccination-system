import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { IVaccine, Vaccine } from '../models/vaccine.model';
import { VaccineService } from '../services/vaccine.service';
import { XmlConverterService } from '../services/xml-converter.service';
import { ProgressBarDialogComponent } from '../utils/progress-bar-dialog/progress-bar-dialog.component';

@Component({
  selector: 'app-vaccine-page',
  templateUrl: './vaccine-page.component.html',
  styleUrls: ['./vaccine-page.component.css']
})
export class VaccinePageComponent implements OnInit {
  displayedColumns: string[] = ['name', 'series', 'manufacturer', 'sideEffects', 'amount', 'edit'];
  vaccines: IVaccine[] = []
  loaded: boolean = true;

  constructor(private vaccineService: VaccineService, private _xml_parser: XmlConverterService, public dialog: MatDialog,
    private _toastr: ToastrService) { }

  ngOnInit(): void {
    this.getAllVaccines()
  }

  sleep(ms: any) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  async saveChanges(element: IVaccine) {
    const dialogRef = this.dialog.open(ProgressBarDialogComponent, {
      height: '60px',
      width: '30%',
    });
    await this.vaccineService.updateAmount(element)
    dialogRef.close();
    this._toastr.success("Uspesno izmenjena kolicina vakcine!")
    this.getAllVaccines()
  }

  getAllVaccines() {
    this.loaded = false;
    this.vaccineService.getAll()
      .subscribe(res => {
        let response = this._xml_parser.parseXmlToObject(res);
        let vaccinesArray: IVaccine[] = []
        response.VACCINES.forEach((element: { NAME: string[]; TYPE: string[]; SERIES: number[]; MANUFACTURER: string[]; SIDEEFFECT: string[]; AMOUNT: number[]; }) => {
          vaccinesArray.push(new Vaccine(element.NAME[0], element.TYPE[0],element.SERIES[0],element.MANUFACTURER[0], element.SIDEEFFECT[0], element.AMOUNT[0]))
        });
        this.vaccines = [...vaccinesArray]
        this.loaded = true;
      })
  }
}
