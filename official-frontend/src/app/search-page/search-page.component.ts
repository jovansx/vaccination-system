import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { convertResponseError } from '../error-converter.function';
import { PretragaService } from '../services/pretraga.service';
import { XmlConverterService } from '../services/xml-converter.service';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  searchInput: string = "";
  selectedType : string = "InteresovanjeZaVakcinaciju";
  lista: any[] = [];

  metaIme : string | undefined = undefined;
  metaPrezime : string | undefined = undefined;
  metaIdBroj : string | undefined = undefined;
  metaLokacija : string | undefined = undefined;
  metaPol : string | undefined = undefined;
  


  constructor(private pretragaService: PretragaService, private toastr: ToastrService, private xmlParser: XmlConverterService) { }

  ngOnInit(): void {
  }

  resetData() : void {
    this.lista = []
    this.metaIme = undefined;
    this.metaPrezime = undefined;
    this.metaIdBroj = undefined;
    this.metaLokacija = undefined;
    this.metaPol = undefined;
  }

  checkboxChanged(name : string) : void {
    if(name === "ime") {
      if(this.metaIme === undefined)
        this.metaIme = "";
      else
        this.metaIme = undefined;
    } else if(name === "prezime") {
      if(this.metaPrezime === undefined)
        this.metaPrezime = "";
      else
        this.metaPrezime = undefined;
    } else if(name === "id_broj") {
      if(this.metaIdBroj === undefined)
        this.metaIdBroj = "";
      else
        this.metaIdBroj = undefined;
    } else if(name === "lokacija") {
      if(this.metaLokacija === undefined)
        this.metaLokacija = "";
      else
        this.metaLokacija = undefined;
    } else if(name === "pol") {
      if(this.metaPol === undefined)
        this.metaPol = "Musko";
      else
        this.metaPol = undefined;
    }


  }

  submitBasicSearch(): void {
    this.lista = [];
    this.pretragaService.basicSearch(this.searchInput).subscribe(
      (res: any) => {
        let response = this.xmlParser.parseXmlToObject(res);

        if(response.INTERESOVANJA[0].IDBROJ) {
          response.INTERESOVANJA[0].IDBROJ.forEach(i => {
            let int = {
              IDDOKUMENTA: i,
              NAZIVDOKUMENTA: "Interesovanje za vakcinacijom"
            }
            this.lista.push(int);
          });
        }
        if(response.POTVRDE[0].IDBROJ) {
          response.POTVRDE[0].IDBROJ.forEach(i => {
            let parts = i.split("_");
            let int = {
              IDDOKUMENTA: i,
              NAZIVDOKUMENTA: `Potvrda o vakcinaciji ${parts[1]}`
            }
            this.lista.push(int);
          });
        }
        if(response.SAGLASNOSTI[0].IDBROJ) {
          response.SAGLASNOSTI[0].IDBROJ.forEach(i => {
            let parts = i.split("_");
            let int = {
              IDDOKUMENTA: i,
              NAZIVDOKUMENTA: `Saglasnost o imunizaciji ${parts[1]}`
            }
            this.lista.push(int);
          });
        }
        if(response.SAGLASNOSTI[0].IDBROJ) {
          response.SERTIFIKATI[0].IDBROJ.forEach(i => {
            let int = {
              IDDOKUMENTA: i,
              NAZIVDOKUMENTA: "Digitalni sertifikat"
            }
            this.lista.push(int);
          });
        }
        if(response.SAGLASNOSTI[0].IDBROJ) {
          response.ZAHTEVI[0].IDBROJ.forEach(i => {
            let int = {
              IDDOKUMENTA: i,
              NAZIVDOKUMENTA: "Zahtev za sertifikat"
            }
            this.lista.push(int);
          });
        }

        if(this.lista.length === 0)
          this.toastr.warning("Document does not exist!")

      },
      (err: any) => this.toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

  submitAdvencedSearch() : void {
    this.lista = [];

    if(this.selectedType === "InteresovanjeZaVakcinaciju") {
      this.pretragaService.interesovanjeAdvencedSearch(this.metaIme, this.metaPrezime, this.metaIdBroj, this.metaLokacija).subscribe(
        (res: any) => {
          let response = this.xmlParser.parseXmlToObject(res);
          if(response.INTERESOVANJE !== undefined) {
            response.INTERESOVANJE.forEach(i => {
              let int = {
                IDDOKUMENTA: i.IDBROJ,
                PARENTTO: i.PARENTTO,
                NAZIVDOKUMENTA: "Interesovanje za vakcinacijom"
              }
              this.lista.push(int);
            });
          }
          if(this.lista.length === 0)
            this.toastr.warning("Document does not exist!")
        },
        (err: any) => this.toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.selectedType === "SaglasnostOVakcinaciji") {
      this.pretragaService.saglasnostAdvencedSearch(this.metaIme, this.metaPrezime, this.metaIdBroj, this.metaLokacija, this.metaPol).subscribe(
        (res: any) => {
          let response = this.xmlParser.parseXmlToObject(res);
          if(response.SAGLASNOST !== undefined) {
            response.SAGLASNOST.forEach(i => {
              let int = {
                IDDOKUMENTA: i.IDBROJ,
                PARENTTO: i.PARENTTO,
                NAZIVDOKUMENTA: "Saglasnost o imunizaciji " + i.IDBROJ[0].split("_")[1]
              }
              this.lista.push(int);
            });
          }
          if(this.lista.length === 0)
            this.toastr.warning("Document does not exist!")
        },
        (err: any) => this.toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.selectedType === "PotvrdaOVakcinaciji") {
      this.pretragaService.potvrdaAdvencedSearch(this.metaIme, this.metaPrezime, this.metaIdBroj).subscribe(
        (res: any) => {
          let response = this.xmlParser.parseXmlToObject(res);
          if(response.POTVRDA !== undefined) {
            response.POTVRDA.forEach(i => {
              let int = {
                IDDOKUMENTA: i.IDBROJ,
                PARENTTO: i.PARENTTO,
                NAZIVDOKUMENTA: "Potvrda o vakcinaciji " + i.IDBROJ[0].split("_")[1]
              }
              this.lista.push(int);
            });
          }
          if(this.lista.length === 0)
            this.toastr.warning("Document does not exist!")
        },
        (err: any) => this.toastr.error(convertResponseError(err), "Don't exist!")
      );
    }  else if(this.selectedType === "ZahtevZaZeleniSertifikat") {
      this.pretragaService.zahtevAdvencedSearch(this.metaIme, this.metaPrezime, this.metaIdBroj, this.metaPol).subscribe(
        (res: any) => {
          let response = this.xmlParser.parseXmlToObject(res);
          if(response.ZAHTEV !== undefined) {
            response.ZAHTEV.forEach(i => {
              let int = {
                IDDOKUMENTA: i.IDBROJ,
                PARENTTO: i.PARENTTO,
                NAZIVDOKUMENTA: "Zahtev za sertifikat"
              }
              this.lista.push(int);
            });
          }
          if(this.lista.length === 0)
            this.toastr.warning("Document does not exist!")
        },
        (err: any) => this.toastr.error(convertResponseError(err), "Don't exist!")
      );
    }  else if(this.selectedType === "DigitalniZeleniSertifikat") {
      this.pretragaService.digitalniAdvencedSearch(this.metaIme, this.metaPrezime, this.metaIdBroj, this.metaPol).subscribe(
        (res: any) => {
          let response = this.xmlParser.parseXmlToObject(res);
          if(response.SERTIFIKAT !== undefined) {
            response.SERTIFIKAT.forEach(i => {
              let int = {
                IDDOKUMENTA: i.IDBROJ,
                PARENTTO: undefined,
                NAZIVDOKUMENTA: "digitalni-sertifikati"
              }
              this.lista.push(int);
            });
          }
          if(this.lista.length === 0)
            this.toastr.warning("Document does not exist!")
        },
        (err: any) => this.toastr.error(convertResponseError(err), "Don't exist!")
      );
    }


  }
}
