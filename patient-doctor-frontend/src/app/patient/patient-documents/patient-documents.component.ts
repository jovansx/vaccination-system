import { Component, OnInit } from '@angular/core';
import { DocumentIdModel } from 'src/app/models/document-id.model';
import { PatientService } from 'src/app/services/patient.service';
import { XmlConverterService } from 'src/app/services/xml-converter.service';

@Component({
  selector: 'app-patient-documents',
  templateUrl: './patient-documents.component.html',
  styleUrls: ['./patient-documents.component.scss']
})
export class PatientDocumentsComponent implements OnInit {

  showMessage: boolean = false;
  lista: any[] | null = null;
  constructor(private _patient_service: PatientService, private _xml_parser: XmlConverterService) { }

  ngOnInit(): void {
    this._patient_service.getPatientDocuments().subscribe(
      (res: any) => {
        let response = this._xml_parser.parseXmlToObject(res);
        this.lista = response.DOKUMENTI;
        this.showMessage = false;
      }, 
      (err: any) => {
        this.showMessage = true;
      }
    );
  }

}
