import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { JwtDecoderService } from 'src/app/autentification/services/jwt-decoder.service';
import { convertResponseError } from 'src/app/error-converter.function';
import { InteresovanjeService } from 'src/app/services/interesovanje.service';
import { PatientService } from 'src/app/services/patient.service';
import { XmlConverterService } from 'src/app/services/xml-converter.service';

@Component({
  selector: 'app-patient-submit',
  templateUrl: './patient-submit.component.html',
  styleUrls: ['./patient-submit.component.scss']
})
export class PatientSubmitComponent implements OnInit {
  formType: string = "interesovanje"; // Moguce opcije: interesovanje, saglasnost, zahtev za zeleni
  dobrovoljniDavalac: boolean = true;

  interesovanjeForm: FormGroup;
  patient: any;
  idBroj: string | null;

  constructor(private _patient_service: PatientService, private _toastr: ToastrService, private _fb: FormBuilder,
              private _interesovanja_service: InteresovanjeService, private _xml_parser: XmlConverterService, private _jwt: JwtDecoderService) { 
    this.idBroj = this._jwt.getIdFromToken();
    this.interesovanjeForm = _fb.group({
      phizer: false,
      sputnik: false,
      sinopharm: false,
      astra: false,
      moderna: false,
      biloKoja: false,
    });
  }

  ngOnInit(): void {
    this._getPatientDetailsForInteresovanje();
  }

  public submitInteresovanje(): void {
    let counter: number = 0;
    let vaccines: string = '';
    Object.keys(this.interesovanjeForm.controls).forEach(key => {
      if (this.interesovanjeForm.controls[key].value === false) {
        counter++;
      } else {
        vaccines += `<vakcina nazivVakcine="${this._getCheckboxValue(key)}"/>`;
      }
    });
    if (counter === 6) {
      this._toastr.warning("Morate da izaberete bar jednu vrednost!");
      return;
    }

    const interesovanjeXml: string = 
    `<?xml version="1.0" encoding="UTF-8"?>
    <interesovanje 
        xmlns="http://www.akatsuki.org/interesovanje"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:t="http://www.akatsuki.org/tipovi"
        xsi:schemaLocation="http://www.akatsuki.org interesovanje.xsd
        http://www.akatsuki.org/tipovi tipovi.xsd"
        xmlns:pred="http://www.akatsuki.org/rdf/examples/predicate/"
        about="http://www.akatsuki.org/interesovanja/${this.idBroj}">
        <drzavljanstvo>${this.patient.drzavljanstvo}</drzavljanstvo>
        <podnosilac>
            <t:ime property="pred:ime" datatype="xs:string">${this.patient.ime}</t:ime>
            <t:prezime property="pred:prezime" datatype="xs:string">${this.patient.prezime}</t:prezime>
            <t:id_broj property="pred:id_broj" datatype="xs:string">${this.idBroj}</t:id_broj>
            <t:email>${this.patient.email}</t:email>
            <t:fiksni_telefon>${this.patient.fiksniTelefon}</t:fiksni_telefon>
            <t:mobilni_telefon>${this.patient.mobilniTelefon}</t:mobilni_telefon>
            <t:lokacija property="pred:lokacija" datatype="xs:string">${this.patient.lokacija}</t:lokacija>
        </podnosilac>
        <vakcine>
            ${vaccines}
        </vakcine>
        <dobrovoljni_davalac_krvi>${this.dobrovoljniDavalac}</dobrovoljni_davalac_krvi>
    </interesovanje>
    `;

    this._interesovanja_service.sendInteresovanje(interesovanjeXml).subscribe(
      () => {
        this._declareCurrentForm();
        this._toastr.success("Uspesno ste podneli interesovanje!");
      }, 
      (err: any) => {
        this._toastr.error(convertResponseError(err), "Ne bi trebalo da se dogodi!")
      }
    );
  }

  private _getPatientDetailsForInteresovanje(): void {
    this._patient_service.getPatientDetailsForInteresovanje().subscribe(
      (res: any) => {
        let response = this._xml_parser.parseXmlToObject(res);
        this.patient = {
          ime: response.IME[0],
          prezime: response.PREZIME[0],
          email: response.EMAIL[0],
          fiksniTelefon: response.FIKSNITELEFON[0],
          mobilniTelefon: response.MOBILNITELEFON[0],
          lokacija: response.LOKACIJA[0],
          drzavljanstvo: response.DRZAVLJANSTVO[0],
        }
        this._declareCurrentForm();
      }, 
      (err: any) => {
        this._toastr.error(convertResponseError(err), "Ne bi trebalo da se dogodi!")
      }
    );
  }

  private _declareCurrentForm(): void {
    if (this.idBroj == null) return;

    this._patient_service.declareCurrentForm(this.idBroj).subscribe(
      (res: any) => {
        let response = this._xml_parser.parseXmlToObject(res);
        this.formType = response.DOKUMENT[0];
      }, 
      (err: any) => {
        this._toastr.error(convertResponseError(err), "Ne bi trebalo da se dogodi!")
      }
    );
  }

  private _getCheckboxValue(key: string): string {
    switch (key) {
      case "phizer": return "Pfizer-BioNTech";
      case "sputnik": return "Sputnik V (Gamaleya istrazivacki centar)";
      case "sinopharm": return "Sinopharm";
      case "astra": return "AstraZeneca";
      case "moderna": return "Moderna";
      default: return "Bilo koja";
    }
  }
}
