import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { JwtDecoderService } from 'src/app/autentification/services/jwt-decoder.service';
import { convertResponseError } from 'src/app/error-converter.function';
import { InteresovanjeService } from 'src/app/services/interesovanje.service';
import { PatientService } from 'src/app/services/patient.service';
import { SaglasnostService } from 'src/app/services/saglasnost.service';
import { ValidatorService } from 'src/app/services/validator.service';
import { XmlConverterService } from 'src/app/services/xml-converter.service';
import { ZahtevService } from 'src/app/services/zahtev.service';

@Component({
  selector: 'app-patient-submit',
  templateUrl: './patient-submit.component.html',
  styleUrls: ['./patient-submit.component.scss']
})
export class PatientSubmitComponent implements OnInit {
  formType: string = ""; // Moguce opcije: interesovanje, saglasnost-1, saglasnost-2, zahtev za zeleni, nista
  dobrovoljniDavalac: boolean = true;

  interesovanjeForm: FormGroup;
  saglasnostForm: FormGroup;
  korisnikSocZas: FormGroup;
  inputForm: FormGroup = new FormGroup({
    nazivSedista: new FormControl('', [Validators.required]),
    opstina: new FormControl('', [Validators.required]),
    nazivLeka: new FormControl('', [Validators.required]),
  });
  patient: any;
  idBroj: string | null;
  evidencijaOVakcinaciji: string = "";
  zahtevForm: FormGroup = new FormGroup({
    razlogZahteva: new FormControl(''),
    ime: new FormControl({value:"", disabled: true}),
    prezime: new FormControl({value:"", disabled: true}),
    lokacija: new FormControl({value:"", disabled: true}),
    datumRodjenja: new FormControl({value:"", disabled: true}),
    pol: new FormControl({value:"", disabled: true}),
  });

  constructor(private _patient_service: PatientService, private _toastr: ToastrService, private _fb: FormBuilder,
              private _interesovanja_service: InteresovanjeService, private _xml_parser: XmlConverterService, private _jwt: JwtDecoderService,
              public validator: ValidatorService, private _saglasnost_service: SaglasnostService, private _zahtev_service: ZahtevService) { 
    this.idBroj = this._jwt.getIdFromToken();
    this.interesovanjeForm = _fb.group({
      phizer: false,
      sputnik: false,
      sinopharm: false,
      astra: false,
      moderna: false,
      biloKoja: false,
    });
    this.saglasnostForm = _fb.group({
      saglasan: false,
    });
    this.korisnikSocZas = _fb.group({
      korisnik: false,
    });
    this.validator.setForm(this.inputForm);
  }

  ngOnInit(): void {
    this._declareCurrentForm();
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
        about="http://www.akatsuki.org/interesovanja/${this.idBroj}"
        datumPodnosenja="${this._getCurrentDate()}"
        >
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

  private _getCurrentDate() {
    let today = new Date();
    let date = today.getFullYear()+'-'+(this._setZeroIfNeed((today.getMonth()+1)+""))+'-'+this._setZeroIfNeed(today.getDate()+"");
    return date;
  }
  
  private _setZeroIfNeed(number : String) : String {
    if(number.length == 1)
      return "0"+number;
    return number;
  }

  public submitSaglasnost(): void {
    if (this.korisnikSocZas.controls['korisnik'].value && this.inputForm.invalid) return;
    if (!this.korisnikSocZas.controls['korisnik'].value && this.inputForm.controls['nazivLeka'].value === "") return;

    let currentDate = this._getCurrentDate();
    let sediste = "";
    if (this.korisnikSocZas.controls['korisnik'].value) {
      sediste =
      `
      <sediste>
        <naziv>${this.inputForm.controls['nazivSedista'].value}</naziv>
        <opstina>${this.inputForm.controls['opstina'].value}</opstina>
      </sediste>
      `;
    }

    let drzavljanstvo = `
    <srpsko>
      <id_broj property="pred:id_broj" datatype="xs:string">${this.idBroj}</id_broj>
    </srpsko>
    `;
    if (this.patient.drzavljanstvo != 'srpsko') {
      drzavljanstvo =
      `
      <strano>
        <naziv>${this.patient.nazivDrzavljanstva}</naziv>
        <id_broj property="pred:id_broj" datatype="xs:string">${this.idBroj}</id_broj>
      </strano>
      `;
    }
    let index = this.formType === 'saglasnost-1' ? "1" : "2";
    const saglasnostXml: string = 
    `<?xml version="1.0" encoding="UTF-8"?>
    <saglasnost_za_imunizaciju xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.akatsuki.org/saglasnost_za_imunizaciju"
        xmlns:t="http://www.akatsuki.org/tipovi"
        xsi:schemaLocation="http://www.akatsuki.org saglasnost_za_imunizaciju.xsd
        http://www.akatsuki.org/tipovi tipovi.xsd"
        xmlns:pred="http://www.akatsuki.org/rdf/examples/predicate/"
        about="http://www.akatsuki.org/saglasnosti/${this.idBroj}_${index}">
        <pacijent datum_popunjavanja="${currentDate}">
            <drzavljanstvo>
                ${drzavljanstvo}
            </drzavljanstvo>
            <prezime property="pred:prezime" datatype="xs:string">${this.patient.prezime}</prezime>
            <ime property="pred:ime" datatype="xs:string">${this.patient.ime}</ime>
            <ime_roditelja>${this.patient.imeRoditelja}</ime_roditelja>
            <pol property="pred:pol" datatype="xs:string">${this.patient.pol}</pol>
            <rodjenje>
                <datum_rodjenja>${this.patient.datumRodjenja}</datum_rodjenja>
                <mesto_rodjenja>${this.patient.mestoRodjenja}</mesto_rodjenja>
            </rodjenje>
            <prebivaliste>
                <adresa>
                    <ulica>${this.patient.ulica}</ulica>
                    <broj>${this.patient.brojKuce}</broj>
                </adresa>
                <mesto_stanovanja>${this.patient.mestoStanovanja}</mesto_stanovanja>
                <opstina property="pred:lokacija" datatype="xs:string">${this.patient.lokacija}</opstina>
            </prebivaliste>
            <fiksni_telefon>${this.patient.fiksniTelefon}</fiksni_telefon>
            <mobilni_telefon>${this.patient.mobilniTelefon}</mobilni_telefon>
            <email>${this.patient.email}</email>
            <radni_status>${this.patient.radniStatus}</radni_status>
            <zanimanje>${this.patient.zanimanje}</zanimanje>
            <socijalna_zastita>
                <korisnik>${this.korisnikSocZas.controls['korisnik'].value}</korisnik>
                ${sediste}
            </socijalna_zastita>
            <izjava_saglasnosti>
                <saglasnost>true</saglasnost>
                <naziv_leka>${this.inputForm.controls['nazivLeka'].value}</naziv_leka>
            </izjava_saglasnosti>
        </pacijent>
        ${this.formType === "saglasnost-2" && this.evidencijaOVakcinaciji !== "" ? this.evidencijaOVakcinaciji : ""}
    </saglasnost_za_imunizaciju>
    `;

    this._saglasnost_service.sendSaglasnost(saglasnostXml).subscribe(
      () => {
        this._declareCurrentForm();
        this._toastr.success("Uspesno ste podneli saglasnost!");
      }, 
      (err: any) => {
        this._toastr.error(convertResponseError(err), "Ne bi trebalo da se dogodi!")
      }
    );
  }

  public submitZahtev(): void {

    const zahtevXml: string = 
    `<?xml version="1.0" encoding="UTF-8"?>
    <zahtev_za_sertifikat xmlns="http://www.akatsuki.org/zahtev_za_sertifikat"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:t="http://www.akatsuki.org/tipovi"
        xmlns:pred="http://www.akatsuki.org/rdf/examples/predicate/"
        xsi:schemaLocation="http://www.akatsuki.org zahtev_za_sertifikat.xsd
        http://www.akatsuki.org/tipovi tipovi.xsd"
        datum="${this._getCurrentDate()}" mesto="${this.patient.lokacija}"
        odobren="false"
        about="http://www.akatsuki.org/zahtevi/${this.idBroj}">
        <podnosilac>
            <t:ime property="pred:ime" datatype="xs:string">${this.patient.ime}</t:ime>
            <t:prezime property="pred:prezime" datatype="xs:string">${this.patient.prezime}</t:prezime>
            <t:id_broj property="pred:id_broj" datatype="xs:string">${this.idBroj}</t:id_broj>
            <t:pol property="pred:pol" datatype="xs:string">${this.patient.pol}</t:pol>
            <t:datum_rodjenja>${this.patient.datumRodjenja}</t:datum_rodjenja>
        </podnosilac>
        <razlog_podnosenja_zahteva>${this.zahtevForm.controls['razlogZahteva'].value}</razlog_podnosenja_zahteva>
    </zahtev_za_sertifikat>
    `;

    this._zahtev_service.sendZahtev(zahtevXml).subscribe(
      () => {
        this._declareCurrentForm();
        this._toastr.success("Uspesno ste podneli zahtev!");
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
      }, 
      (err: any) => {
        this._toastr.error(convertResponseError(err), "Ne bi trebalo da se dogodi!")
      }
    );
  }

  private _getPatientDetailsForSaglasnost(): void {
    if (this.formType === "saglasnost-2") {
      this._saglasnost_service.getSaglasnost(this.idBroj + "_1").subscribe(
        (res: any) => {
          let evidencija: string = res.split("</pacijent>")[1];
          let cutIndexPosition = evidencija.indexOf("</saglasnost_za_imunizaciju>");
          this.evidencijaOVakcinaciji = evidencija.substring(0, cutIndexPosition);
        }, 
        (err: any) => {
          this._toastr.error(convertResponseError(err), "Ne bi trebalo da se dogodi!")
        }
      );
    }

    this._patient_service.getPatientDetailsForSaglasnost().subscribe(
      (res: any) => {
        let response = this._xml_parser.parseXmlToObject(res);
        this.patient = {
          brojKuce: response.BROJKUCE[0],
          datumRodjenja: response.DATUMRODJENJA[0],
          ime: response.IME[0],
          imeRoditelja: response.IMERODITELJA[0],
          prezime: response.PREZIME[0],
          email: response.EMAIL[0],
          fiksniTelefon: response.FIKSNITELEFON[0],
          mobilniTelefon: response.MOBILNITELEFON[0],
          lokacija: response.LOKACIJA[0],
          drzavljanstvo: response.TIPDRZAVLJANSTVA[0],
          nazivDrzavljanstva: response?.NAZIVDRZAVLJANSTVA !== undefined ? response.NAZIVDRZAVLJANSTVA[0] : undefined,
          mestoRodjenja: response.MESTORODJENJA[0],
          mestoStanovanja: response.MESTOSTANOVANJA[0],
          pol: response.POL[0],
          radniStatus: response.RADNISTATUS[0],
          ulica: response.ULICA[0],
          zanimanje: response.ZANIMANJE[0],
        }
      }, 
      (err: any) => {
        this._toastr.error(convertResponseError(err), "Ne bi trebalo da se dogodi!")
      }
    );
  }

  private _getPatientDetailsForZahtev(): void {
    this._patient_service.getPatientDetailsForZahtev().subscribe(
      (res: any) => {
        let response = this._xml_parser.parseXmlToObject(res);
        this.patient = {
          ime: response.IME[0],
          prezime: response.PREZIME[0],
          lokacija: response.LOKACIJA[0],
          datumRodjenja: response.DATUMRODJENJA[0],
          pol: response.POL[0]
        }
        this.zahtevForm.patchValue(this.patient);
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
        if (this.formType == "interesovanje")
          this._getPatientDetailsForInteresovanje();
        else if (this.formType === "saglasnost-1" || this.formType === "saglasnost-2")
          this._getPatientDetailsForSaglasnost();
        else if (this.formType === "zahtev") 
          this._getPatientDetailsForZahtev();
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
