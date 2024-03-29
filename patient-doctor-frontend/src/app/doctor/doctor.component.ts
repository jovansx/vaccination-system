import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, SelectControlValueAccessor, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { convertResponseError } from '../error-converter.function';
import { AppointmentService } from '../services/appointment.service';
import { DoctorService } from '../services/doctor.service';
import { InteresovanjeService } from '../services/interesovanje.service';
import { PotvrdaService } from '../services/potvrda.service';
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
    izaberiVakcinu: new FormControl({ value: 'a', disabled: false }, [Validators.required,]),
    datumIzdavanjaVakcine: new FormControl({ value: '', disabled: true }, [Validators.required,]),
    nacinDavanjaVakcine: new FormControl({ value: 'IM', disabled: false }, [Validators.required,]),
    ekstremitetVakcine: new FormControl({ value: 'DR', disabled: false }, [Validators.required,]),
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
  document : any = undefined;
  doktor : any;
  firstVaccination : boolean = true;
  availableVaccines : string[] = [];
  saglasnost : string | undefined;
  sentDocumentsCounter : number = 0;
  pacijentId : string | undefined;

  serijaPrveVakcine : string = "";

  constructor( public validator: ValidatorService, private appointmentService: AppointmentService,
    private saglasnostService : SaglasnostService, private potvrdaService : PotvrdaService,
    private interesovanjeService : InteresovanjeService,
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
    this.document = undefined;
    this.termin = undefined;
    this.appointmentService.getCurrentAppointment().subscribe(
      (res: any) => {
        if(res == "") {
          this._toastr.info("Trenutno nema aktivnih termina!", "Paznja!")
          this.document = "Nema termina"
          return;
        }
        let response = this._xml_parser.parseXmlToObject(res);
        this.pacijentId = response.PACIJENT_ID[0];
        let redniBrojVakcinacije = response.REDNI_BROJ_SASTANKA[0];
        let date : String = response.TERMIN[0];
        this.termin = date.substring(11, 16);
        this.getCurrentSaglasnost(this.pacijentId as string, redniBrojVakcinacije as string);
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

  refresh() : void {
    this.getCurrentAppointment();
  }

    getCurrentSaglasnost(pacijentId : string, redniBrojVakcinacije : string) : void {
      this.saglasnostService.getSaglasnost(pacijentId+"_"+redniBrojVakcinacije).subscribe(
        (res: any) => {
          this.saglasnost = res;
          this.document = this._xml_parser.parseXmlToObject(res);
          if(this.document.EVIDENCIJA_O_VAKCINACIJI != undefined) {
            this.firstVaccination = false;
            this.getSerijuPrveVakcine();
          }
          else
            this.getAvailableVaccines(pacijentId);
          this.setValues()
        },
        (err: any) => {
          this._toastr.info("Trenutno nema saglasnosti!", "Paznja!");
          this.document = "Nema saglasnosti";
        }
      );
    }

  getSerijuPrveVakcine() {
    this.potvrdaService.getSerijuPrveVakcine(this.pacijentId+"_1").subscribe(
      (res: any) => {
       this.serijaPrveVakcine = res;
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

  getAvailableVaccines(pacijentId : string) : void {
    this.interesovanjeService.getInteresovanje(pacijentId).subscribe(
      (res: any) => {
        let interesovanje = this._xml_parser.parseXmlToObject(res);
        let vaccinessArray: any[]= interesovanje.VAKCINE[0].VAKCINA;
        vaccinessArray.forEach(vaccine => {
          if(vaccine.$.NAZIVVAKCINE !== "Sputnik V (Gamaleya istrazivacki centar)")
            this.availableVaccines.push(vaccine.$.NAZIVVAKCINE);
          else
            this.availableVaccines.push("Sputnik V");
        });
        if(this.availableVaccines[0] === "Bilo koja")
          this.vaccinattionForm.controls["izaberiVakcinu"].setValue("Pfizer-BioNTech");
        else
          this.vaccinattionForm.controls["izaberiVakcinu"].setValue(this.availableVaccines[0]);
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

  ifVaccineInAvailableVaccines(vaccineName: string) : boolean {
    if(this.availableVaccines.includes(vaccineName))
      return true;
    return false;
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
    if(this.document.PACIJENT[0].SOCIJALNA_ZASTITA[0].SEDISTE !== undefined)
      this.vaccinattionForm.controls['sedisteSocZastite'].setValue(this.document.PACIJENT[0].SOCIJALNA_ZASTITA[0].SEDISTE[0].NAZIV[0]+" "+this.document.PACIJENT[0].SOCIJALNA_ZASTITA[0].SEDISTE[0].OPSTINA[0])
    if(this.document.PACIJENT[0].IZJAVA_SAGLASNOSTI[0].SAGLASNOST[0] === 'true')
      this.vaccinattionForm.controls['saglasnost'].setValue("IMA")
    else 
      this.vaccinattionForm.controls['saglasnost'].setValue("NEMA")
    this.vaccinattionForm.controls['nazivLeka'].setValue(this.document.PACIJENT[0].IZJAVA_SAGLASNOSTI[0].NAZIV_LEKA[0])

// 2 KOLONA
    if(!this.firstVaccination) {
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
    if(this.firstVaccination)
      this.vaccinattionForm.controls['odlukaKomisije'].setValue("NE")
    else {
      this.vaccinattionForm.controls['kontraindikacije'].setValue(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].KONTRAINDIKACIJE[0].DIJAGNOZA[0])
      if(this.document.EVIDENCIJA_O_VAKCINACIJI[0].VAKCINE[0].ODLUKA_KOMISIJE[0] === 'true') {
        this.vaccinattionForm.controls['odlukaKomisije'].setValue("DA")
      } else {
        this.vaccinattionForm.controls['odlukaKomisije'].setValue("NE")
      }
    }
    this.vaccinattionForm.controls['datumIzdavanjaVakcine'].setValue(this.getCurrentDate())
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

getDocumentId() : string {
  let parts = this.document.$.ABOUT.split("/")
  return parts[parts.length - 1];
}

didntShowUp() : void {
  this.saglasnostService.deleteCurrentSaglasnost(this.getDocumentId()).subscribe(
    (res: any) => {
      this.getCurrentAppointment();
      this._toastr.success("You have successfully processed vaccination.", "Congratulations!")
    },
    (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
  );
}

getProizvodjac() : string {
  if(this.vaccinattionForm.controls['izaberiVakcinu'].value === "Pfizer-BioNTech" || this.vaccinattionForm.controls['izabranaVakcina'].value === "Pfizer-BioNTech" )
    return "Americki proizvodjac";
  else if(this.vaccinattionForm.controls['izaberiVakcinu'].value === "Sputnik V" || this.vaccinattionForm.controls['izabranaVakcina'].value === "Sputnik V")
    return "Ruski proizvodjac";
  else if(this.vaccinattionForm.controls['izaberiVakcinu'].value === "Sinopharm" || this.vaccinattionForm.controls['izabranaVakcina'].value === "Sinopharm")
    return "Kineski proizvodjac";
  else if(this.vaccinattionForm.controls['izaberiVakcinu'].value === "AstraZeneca" || this.vaccinattionForm.controls['izabranaVakcina'].value === "AstraZeneca")
    return "Britanski proizvodjac";
  else if(this.vaccinattionForm.controls['izaberiVakcinu'].value === "Moderna" || this.vaccinattionForm.controls['izabranaVakcina'].value === "Moderna")
    return "Britanski proizvodjac";
  return ""
}

sendDocuments() : void {
  if(this.vaccinattionForm.invalid){
    this._toastr.info("Forma nije validno popunjena!", "Paznja!")
    return;
  }
  let novaSaglasnost = "";
  let odlukaKomisijeString = this.vaccinattionForm.controls['odlukaKomisije'].value === "DA" ? "true" : "false";
  if(this.firstVaccination) {
    let evidencija = `<evidencija_o_vakcinaciji>
      <zdravstvena_ustanova>${this.vaccinattionForm.controls['zdravstvenaUstanova'].value}</zdravstvena_ustanova>
      <vakcinacijski_punkt>${this.vaccinattionForm.controls['punkt'].value}</vakcinacijski_punkt>
      <lekar>
          <ime>${this.vaccinattionForm.controls['imeLekara'].value}</ime>
          <prezime>${this.vaccinattionForm.controls['prezimeLekara'].value}</prezime>
          <telefon>${this.vaccinattionForm.controls['fiksniTelefonLekara'].value}</telefon>
      </lekar>
      <vakcine>
          <vakcina>
              <naziv>${this.vaccinattionForm.controls['izaberiVakcinu'].value}</naziv>
              <datum_izdavanja>${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}</datum_izdavanja>
              <nacin_davanja>${this.vaccinattionForm.controls['nacinDavanjaVakcine'].value}</nacin_davanja>
              <ekstremitet>${this.vaccinattionForm.controls['ekstremitetVakcine'].value}</ekstremitet>
              <serija>${this.vaccinattionForm.controls['serijaVakcine'].value}</serija>
              <proizvodjac>${this.getProizvodjac()}</proizvodjac>
              <nezeljena_reakcija>Temperatura</nezeljena_reakcija>
          </vakcina>
          <kontraindikacije>
              <datum_utvrdjivanja>${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}</datum_utvrdjivanja>
              <dijagnoza>${this.vaccinattionForm.controls['kontraindikacije'].value}</dijagnoza>
          </kontraindikacije>
          <odluka_komisije>${odlukaKomisijeString}</odluka_komisije>
      </vakcine>
    </evidencija_o_vakcinaciji>
    </saglasnost_za_imunizaciju>`;
    novaSaglasnost = this.saglasnost?.replace("</saglasnost_za_imunizaciju>", evidencija) as string
  } else {
    let evidencija = `</vakcina>
    <vakcina>
      <naziv>${this.vaccinattionForm.controls['izabranaVakcina'].value}</naziv>
      <datum_izdavanja>${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}</datum_izdavanja>
      <nacin_davanja>${this.vaccinattionForm.controls['nacinDavanjaVakcine'].value}</nacin_davanja>
      <ekstremitet>${this.vaccinattionForm.controls['ekstremitetVakcine'].value}</ekstremitet>
      <serija>${this.vaccinattionForm.controls['serijaVakcine'].value}</serija>
      <proizvodjac>${this.getProizvodjac()}</proizvodjac>
      <nezeljena_reakcija>Temperatura</nezeljena_reakcija>
    </vakcina>
  <kontraindikacije>
    <datum_utvrdjivanja>${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}</datum_utvrdjivanja>
    <dijagnoza>${this.vaccinattionForm.controls['kontraindikacije'].value}</dijagnoza>
  </kontraindikacije>
  <odluka_komisije>${odlukaKomisijeString}</odluka_komisije>
  </vakcine>
  </evidencija_o_vakcinaciji>
  </saglasnost_za_imunizaciju>`;
    novaSaglasnost = this.saglasnost?.replace("</vakcina>", evidencija) as string;
    let parts : string[] = novaSaglasnost.split("<kontraindikacije>")
    parts[0] += "<kontraindikacije>";
    novaSaglasnost = parts[0] + parts[1];
  }
  this.sendSaglasnost(novaSaglasnost);
  
  let novaPotvrda = "";

  if(this.firstVaccination) {
    novaPotvrda = `<?xml version="1.0" encoding="UTF-8"?>
    <potvrda_o_vakcinaciji 
        xmlns="http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:t="http://www.akatsuki.org/tipovi"
        xsi:schemaLocation="http://www.akatsuki.org potvrda_o_izvrsenoj_vakcinaciji.xsd
        http://www.akatsuki.org/tipovi tipovi.xsd"
        datum_izdavanja="${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}"
        xmlns:pred="http://www.akatsuki.org/rdf/examples/predicate/"
        about="http://www.akatsuki.org/potvrde/${this.pacijentId+"_1"}">
        <primalac>
            <t:ime property="pred:ime" datatype="xs:string">${this.vaccinattionForm.controls['ime'].value}</t:ime>
            <t:prezime property="pred:prezime" datatype="xs:string">${this.vaccinattionForm.controls['prezime'].value}</t:prezime>
            <t:id_broj property="pred:id_broj" datatype="xs:string">${this.vaccinattionForm.controls['idBroj'].value}</t:id_broj>
            <t:pol property="pred:pol" datatype="xs:string">${this.capitalizeFirstLetter(this.vaccinattionForm.controls['pol'].value)}</t:pol>
            <t:datum_rodjenja>${this.vaccinattionForm.controls['datumRodjenja'].value}</t:datum_rodjenja>
        </primalac>
        <primljene_vakcine>
            <doza broj="1">
                <datum_davanja>${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}</datum_davanja>
                <serija>${this.vaccinattionForm.controls['serijaVakcine'].value}</serija>
            </doza>
        </primljene_vakcine>
        <zdravstvena_ustanova property="pred:zdravstvena_ustanova" datatype="xs:string">${this.vaccinattionForm.controls['zdravstvenaUstanova'].value}</zdravstvena_ustanova>
        <naziv_vakcine property="pred:vakcina" datatype="xs:string">${this.vaccinattionForm.controls['izaberiVakcinu'].value}</naziv_vakcine>
        <qr_code>url</qr_code>
    </potvrda_o_vakcinaciji>`;
  } else {
    novaPotvrda = `<?xml version="1.0" encoding="UTF-8"?>
    <potvrda_o_vakcinaciji 
        xmlns="http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:t="http://www.akatsuki.org/tipovi"
        xsi:schemaLocation="http://www.akatsuki.org potvrda_o_izvrsenoj_vakcinaciji.xsd
        http://www.akatsuki.org/tipovi tipovi.xsd"
        datum_izdavanja="${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}"
        xmlns:pred="http://www.akatsuki.org/rdf/examples/predicate/"
        about="http://www.akatsuki.org/potvrde/${this.pacijentId+"_2"}">
        <primalac>
            <t:ime property="pred:ime" datatype="xs:string">${this.vaccinattionForm.controls['ime'].value}</t:ime>
            <t:prezime property="pred:prezime" datatype="xs:string">${this.vaccinattionForm.controls['prezime'].value}</t:prezime>
            <t:id_broj property="pred:id_broj" datatype="xs:string">${this.vaccinattionForm.controls['idBroj'].value}</t:id_broj>
            <t:pol property="pred:pol" datatype="xs:string">${this.capitalizeFirstLetter(this.vaccinattionForm.controls['pol'].value)}</t:pol>
            <t:datum_rodjenja>${this.vaccinattionForm.controls['datumRodjenja'].value}</t:datum_rodjenja>
        </primalac>
        <primljene_vakcine>
            <doza broj="1">
                <datum_davanja>${this.vaccinattionForm.controls['datumIzdavanja'].value}</datum_davanja>
                <serija>${this.serijaPrveVakcine}</serija>
            </doza>
            <doza broj="2">
                <datum_davanja>${this.vaccinattionForm.controls['datumIzdavanjaVakcine'].value}</datum_davanja>
                <serija>${this.vaccinattionForm.controls['serijaVakcine'].value}</serija>
            </doza>
        </primljene_vakcine>
        <zdravstvena_ustanova property="pred:zdravstvena_ustanova" datatype="xs:string">${this.vaccinattionForm.controls['zdravstvenaUstanova'].value}</zdravstvena_ustanova>
        <naziv_vakcine property="pred:vakcina" datatype="xs:string">${this.vaccinattionForm.controls['izabranaVakcina'].value}</naziv_vakcine>
        <qr_code>url</qr_code>
    </potvrda_o_vakcinaciji>`;
  }
  this.sendPotvrdu(novaPotvrda);
  }

  capitalizeFirstLetter(text : string) {
    text = text.toLowerCase();
    return text.charAt(0).toUpperCase() + text.slice(1);
  }

  sendPotvrdu(porvrda: string) {
    this.potvrdaService.sendPotvrdu(porvrda).subscribe(
      (res: any) => {
        this.sentDocumentsCounter++;
        if(this.sentDocumentsCounter == 2) {
          this.sentDocumentsCounter = 0;
          this.getCurrentAppointment();
          this._toastr.success("You have successfully processed vaccination.", "Congratulations!")
        }
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

  sendSaglasnost(novaSaglasnost: string) : void {
    this.saglasnostService.updateSaglasnost(novaSaglasnost).subscribe(
      (res: any) => {
        this.sentDocumentsCounter++;
        if(this.sentDocumentsCounter == 2) {
          this.sentDocumentsCounter = 0;
          this.getCurrentAppointment();
          this._toastr.success("You have successfully processed vaccination.", "Congratulations!")
        }
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }



}
