import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, SelectControlValueAccessor, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { convertResponseError } from '../error-converter.function';
import { AppointmentService } from '../services/appointment.service';
import { DoctorService } from '../services/doctor.service';
import { SaglasnostService } from '../services/saglasnost.service';
import { ValidatorService } from '../services/validator.service';
import { XmlConverterService } from '../services/xml-converter.service';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.scss']
})
export class DoctorComponent implements OnInit {

  vaccinattionForm: FormGroup = new FormGroup({
    // 3 KOLONA
    fiksniTelefonLekara: new FormControl({ value: '', disabled: true }, [Validators.required, Validators.pattern("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}")]),
    imeLekara: new FormControl({ value: '', disabled: true }, [Validators.required,]),
    prezimeLekara: new FormControl({ value: '', disabled: true }, [Validators.required]),
    zdravstvenaUstanova: new FormControl({ value: '', disabled: true }, [Validators.required]),
    punkt: new FormControl({ value: '', disabled: true }, [Validators.required, Validators.pattern("[0-9]+")]),

    // 4 KOLONA
    datumIzdavanjaVakcine: new FormControl({ value: '', disabled: true }, [Validators.required,]),
    nacinDavanjaVakcine: new FormControl({ value: '', disabled: false }, [Validators.required,]),
    ekstremitetVakcine: new FormControl({ value: '', disabled: false }, [Validators.required,]),
    serijaVakcine: new FormControl({ value: '', disabled: false }, [Validators.required, Validators.pattern("[0-9]+")]),
    kontraindikacije: new FormControl({ value: '', disabled: false }, []),
    odlukaKomisije: new FormControl({ value: "", disabled: false }, [Validators.required]),
    
    // 2 KOLONA
    datumIzdavanja: new FormControl({ value: '', disabled: true }, [Validators.required,]),
    nacinDavanja: new FormControl({ value: '', disabled: true }, [Validators.required,]),
    ekstremitet: new FormControl({ value: '', disabled: true }, [Validators.required,]),
    izabranaVakcina: new FormControl({ value: '', disabled: true }, [Validators.required,]),


    // 1 KOLONA
    ime: new FormControl({ value: '', disabled: true }, [Validators.required,]),
    prezime: new FormControl({ value: '', disabled: true }, [Validators.required]),
    imeOca: new FormControl({ value: '', disabled: true }, [Validators.required]),
    pol: new FormControl({ value: '', disabled: true }, [Validators.required]),  
    idBroj: new FormControl({ value: '', disabled: true }, [Validators.required]),
    drzavljanstvo: new FormControl({ value: '', disabled: true }, [Validators.required]),
    mobilniTelefon: new FormControl({ value: '', disabled: true }, [Validators.required, Validators.pattern("[0-9]{10}")]),
    fiksniTelefon: new FormControl({ value: '', disabled: true }, [Validators.required, Validators.pattern("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}")]),
    mestoRodjenja: new FormControl({ value: '', disabled: true }, [Validators.required]),
    datumRodjenja: new FormControl({ value: '', disabled: true }, [Validators.required]),
    opstina: new FormControl({ value: '', disabled: true }, [Validators.required]),
    mestoStanovanja: new FormControl({ value: '', disabled: true }, [Validators.required]),
    ulica: new FormControl({ value: '', disabled: true }, [Validators.required]),
    brojKuce: new FormControl({ value: '', disabled: true }, [Validators.required]),
    email: new FormControl({ value: '', disabled: true }, [Validators.required]),
    radniStatus: new FormControl({ value: '', disabled: true }, [Validators.required]),
    zanimanje: new FormControl({ value: '', disabled: true }, [Validators.required]),
    socijalnaZastita: new FormControl({ value: '', disabled: true }, [Validators.required]),
    sedisteSocZastite: new FormControl({ value: '', disabled: true }, [Validators.required]),
    saglasnost: new FormControl({ value: '', disabled: true }, [Validators.required]),
    nazivLeka: new FormControl({ value: '', disabled: true }, [Validators.required]),
  });

  termin : String | undefined;
  document : any;
  doktor : any;
  firstVaccination : boolean = true;

  constructor( public validator: ValidatorService, private appointmentService: AppointmentService,
    private saglasnostService : SaglasnostService,
      private _xml_parser: XmlConverterService,
      private doctorService : DoctorService, private _toastr: ToastrService) { 
    validator.setForm(this.vaccinattionForm);
  }

  ngOnInit(): void {
    this.getDoctorInfo()
    this.getCurrentAppointment()
  }

  getDoctorInfo() : void {
    this.doctorService.getDoctorInfo().subscribe(
      (res: any) => {
        this.doktor = this._xml_parser.parseXmlToObject(res);
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

  getCurrentAppointment() : void {
    this.appointmentService.getCurrentAppointment().subscribe(
      (res: any) => {
        if(res == null) {
          this._toastr.info("Trenutno nema aktivnih termina!", "Attention!")
          this.document = false
          return;
        }
        let response = this._xml_parser.parseXmlToObject(res);
        let pacijentId = response.PACIJENT_ID[0];
        let date : String = response.TERMIN[0];
        this.termin = date.substring(11, 16);
        this.getCurrentSaglasnost(pacijentId);
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

    getCurrentSaglasnost(pacijentId : String) : void {
      this.saglasnostService.getCurrentSaglasnost(pacijentId).subscribe(
        (res: any) => {
          this.document = this._xml_parser.parseXmlToObject(res);
          this.setValues()
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    }

  setValues() {
    if(this.document == undefined || this.document == null)
      return
// 1 KOLONA
    this.vaccinattionForm.controls['ime'].setValue(this.document.PACIJENT[0].IME[0]["_"])
    this.vaccinattionForm.controls['imeOca'].setValue(this.document.PACIJENT[0].IME_RODITELJA[0])
    this.vaccinattionForm.controls['prezime'].setValue(this.document.PACIJENT[0].PREZIME[0]["_"])
    this.vaccinattionForm.controls['pol'].setValue(this.document.PACIJENT[0].POL[0]["_"].toUpperCase())
    if(this.document.PACIJENT[0].DRZAVLJANSTVO[0].SRPSKO != undefined) {
      this.vaccinattionForm.controls['drzavljanstvo'].setValue('SRPSKO')
      this.vaccinattionForm.controls['idBroj'].setValue(this.document.PACIJENT[0].DRZAVLJANSTVO[0].SRPSKO[0].ID_BROJ[0]["_"])
    }
    if(this.document.PACIJENT[0].DRZAVLJANSTVO[0].STRANO != undefined) {
      this.vaccinattionForm.controls['drzavljanstvo'].setValue('STRANO')
      this.vaccinattionForm.controls['idBroj'].setValue(this.document.PACIJENT[0].DRZAVLJANSTVO[0].STRANO[0].ID_BROJ[0]["_"])
    }
    this.vaccinattionForm.controls['mobilniTelefon'].setValue(this.document.PACIJENT[0].MOBILNI_TELEFON[0])
    this.vaccinattionForm.controls['fiksniTelefon'].setValue(this.document.PACIJENT[0].FIKSNI_TELEFON[0])
    this.vaccinattionForm.controls['mestoRodjenja'].setValue(this.document.PACIJENT[0].RODJENJE[0].MESTO_RODJENJA[0])
    this.vaccinattionForm.controls['datumRodjenja'].setValue(this.document.PACIJENT[0].RODJENJE[0].DATUM_RODJENJA[0])
    this.vaccinattionForm.controls['opstina'].setValue(this.document.PACIJENT[0].PREBIVALISTE[0].OPSTINA[0]["_"])
    this.vaccinattionForm.controls['mestoStanovanja'].setValue(this.document.PACIJENT[0].PREBIVALISTE[0].MESTO_STANOVANJA[0])
    this.vaccinattionForm.controls['ulica'].setValue(this.document.PACIJENT[0].PREBIVALISTE[0].ADRESA[0].ULICA[0])
    this.vaccinattionForm.controls['brojKuce'].setValue(this.document.PACIJENT[0].PREBIVALISTE[0].ADRESA[0].BROJ[0])
    this.vaccinattionForm.controls['email'].setValue(this.document.PACIJENT[0].EMAIL[0])
    this.vaccinattionForm.controls['radniStatus'].setValue(this.document.PACIJENT[0].RADNI_STATUS[0])
    this.vaccinattionForm.controls['zanimanje'].setValue(this.document.PACIJENT[0].ZANIMANJE[0])
    if(this.document.PACIJENT[0].SOCIJALNA_ZASTITA[0].KORISNIK[0] === 'true')
      this.vaccinattionForm.controls['socijalnaZastita'].setValue("IMA")
    else 
      this.vaccinattionForm.controls['socijalnaZastita'].setValue("NEMA")
    if(this.document.PACIJENT[0].DRZAVLJANSTVO[0].STRANO != undefined) {
      this.vaccinattionForm.controls['drzavljanstvo'].setValue('STRANO')
      this.vaccinattionForm.controls['idBroj'].setValue(this.document.PACIJENT[0].DRZAVLJANSTVO[0].STRANO[0].ID_BROJ[0]["_"])
    }
    this.vaccinattionForm.controls['sedisteSocZastite'].setValue(this.document.PACIJENT[0].SOCIJALNA_ZASTITA[0].SEDISTE[0].NAZIV[0]+" "+this.document.PACIJENT[0].SOCIJALNA_ZASTITA[0].SEDISTE[0].OPSTINA[0])
    if(this.document.PACIJENT[0].IZJAVA_SAGLASNOSTI[0].SAGLASNOST[0] === 'true')
      this.vaccinattionForm.controls['saglasnost'].setValue("IMA")
    else 
      this.vaccinattionForm.controls['saglasnost'].setValue("NEMA")
    this.vaccinattionForm.controls['nazivLeka'].setValue(this.document.PACIJENT[0].IZJAVA_SAGLASNOSTI[0].NAZIV_LEKA[0])

// 2 KOLONA
  if(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].VAKCINA.length != 0) {
    this.firstVaccination = false;
    this.vaccinattionForm.controls['izabranaVakcina'].setValue(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].VAKCINA[0].NAZIV[0])
    this.vaccinattionForm.controls['datumIzdavanja'].setValue(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].VAKCINA[0].DATUM_IZDAVANJA[0])
    this.vaccinattionForm.controls['nacinDavanja'].setValue(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].VAKCINA[0].NACIN_DAVANJA[0])
    this.vaccinattionForm.controls['ekstremitet'].setValue(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].VAKCINA[0].EKSTREMITET[0])
  }

// 3 KOLONA
    this.vaccinattionForm.controls['imeLekara'].setValue(this.doktor.IME[0])
    this.vaccinattionForm.controls['prezimeLekara'].setValue(this.doktor.PREZIME[0])
    this.vaccinattionForm.controls['fiksniTelefonLekara'].setValue(this.doktor.FIKSNI_TELEFON[0])
    this.vaccinattionForm.controls['zdravstvenaUstanova'].setValue(this.doktor.ZDRAVSTVENA_USTANOVA[0])
    this.vaccinattionForm.controls['punkt'].setValue(this.doktor.PUNKT[0])

// 4 KOLONA
  if(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].ODLUKA_KOMISIJE[0] === 'true') {
    this.vaccinattionForm.controls['odlukaKomisije'].setValue("DA")
  } else {
    this.vaccinattionForm.controls['odlukaKomisije'].setValue("NE")
  }
  this.vaccinattionForm.controls['datumIzdavanjaVakcine'].setValue(this.getCurrentDate())
  this.vaccinattionForm.controls['kontraindikacije'].setValue(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].KONTRAINDIKACIJE[0].DIJAGNOZA[0])
}



getCurrentDate() {
  let today = new Date();
  let date = today.getFullYear()+'-'+(this.setZeroIfNeed((today.getMonth()+1)+""))+'-'+this.setZeroIfNeed(today.getDate()+"");
  return date;
}

setZeroIfNeed(number : String) : String {
  if(number.length == 1)
    return "0"+number;
  return number;
}

}
