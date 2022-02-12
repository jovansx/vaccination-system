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
  lista: any[] = [];
  constructor(private pretragaService: PretragaService, private toastr: ToastrService, private xmlParser: XmlConverterService) { }

  ngOnInit(): void {
  }

  submitBasicSearch(): void {
    this.lista = [];
    this.pretragaService.basicSearch(this.searchInput).subscribe(
      (res: any) => {
        let response = this.xmlParser.parseXmlToObject(res);
        response.INTERESOVANJA[0].IDBROJ.forEach(i => {
          let int = {
            IDDOKUMENTA: i,
            NAZIVDOKUMENTA: "Interesovanje za vakcinacijom"
          }
          this.lista.push(int);
        });
        response.POTVRDE[0].IDBROJ.forEach(i => {
          let parts = i.split("_");
          let int = {
            IDDOKUMENTA: i,
            NAZIVDOKUMENTA: `Potvrda o vakcinaciji ${parts[1]}`
          }
          this.lista.push(int);
        });
        response.SAGLASNOSTI[0].IDBROJ.forEach(i => {
          let parts = i.split("_");
          let int = {
            IDDOKUMENTA: i,
            NAZIVDOKUMENTA: `Saglasnost o imunizaciji ${parts[1]}`
          }
          this.lista.push(int);
        });
        response.SERTIFIKATI[0].IDBROJ.forEach(i => {
          let int = {
            IDDOKUMENTA: i,
            NAZIVDOKUMENTA: "Digitalni sertifikat"
          }
          this.lista.push(int);
        });
        response.ZAHTEVI[0].IDBROJ.forEach(i => {
          let int = {
            IDDOKUMENTA: i,
            NAZIVDOKUMENTA: "Zahtev za sertifikat"
          }
          this.lista.push(int);
        });
      },
      (err: any) => this.toastr.error(convertResponseError(err), "Don't exist!")
    );
  }
}
