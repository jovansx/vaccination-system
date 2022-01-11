import { Component, Input, OnInit } from '@angular/core';
import { saveAs } from 'file-saver';
import { Toast, ToastrService } from 'ngx-toastr';
import { convertResponseError } from 'src/app/error-converter.function';
import { DigitalniSertifikatService } from 'src/app/services/digitalni-sertifikat.service';
import { InteresovanjeService } from 'src/app/services/interesovanje.service';
import { PotvrdaService } from 'src/app/services/potvrda.service';
import { SaglasnostService } from 'src/app/services/saglasnost.service';
import { XmlConverterService } from 'src/app/services/xml-converter.service';
import { ZahtevService } from 'src/app/services/zahtev.service';

import { environment as env } from '../../../environments/environment';


@Component({
  selector: 'app-document-card',
  templateUrl: './document-card.component.html',
  styleUrls: ['./document-card.component.scss']
})
export class DocumentCardComponent implements OnInit {

  @Input()
  dokument: any;
  constructor(private interesovanjeService : InteresovanjeService,private saglasnostService : SaglasnostService,
    private potvdaService : PotvrdaService,
    private zahtevService : ZahtevService,
    private digitalniService : DigitalniSertifikatService, private _toastr : ToastrService) { }

  ngOnInit(): void {
  }

  getTypeOfDocument() : string {
    if(this.dokument.NAZIVDOKUMENTA[0] === "Interesovanje za vakcinacijom")
      return "interesovanja";
    else if(this.dokument.NAZIVDOKUMENTA[0] === "Saglasnost o imunizaciji 1" || this.dokument.NAZIVDOKUMENTA[0] === "Saglasnost o imunizaciji 2")
      return "saglasnosti";
    else if(this.dokument.NAZIVDOKUMENTA[0] === "Potvrda o vakcinaciji 1" || this.dokument.NAZIVDOKUMENTA[0] === "Potvrda o vakcinaciji 2")
      return "potvrde";
    else if(this.dokument.NAZIVDOKUMENTA[0] === "Zahtev za sertifikat")
      return "zahtevi";
    return "digitalni-sertifikati";
  }

  display(typeOfDoc : string) : void {
    window.open(`${env.apiUrl}/${this.getTypeOfDocument()}/${typeOfDoc}/${this.dokument.IDDOKUMENTA}`, "_blank")
  }
  
  downloadMetadata(typeOfDoc: string) {
    let filename = this.getTypeOfDocument() + `-${this.dokument.IDDOKUMENTA}-METADATA`;
    let type = "";

    if(typeOfDoc === "json") {
      filename+=".json";
      type = "application/json;charset=utf-8";

      this.getDocumentMetadataJSON(type, filename);
    } else {
     console.log("NISTA JOS")
    }
  }

  
  getDocumentMetadataJSON(type : string, filename : string) {
    if(this.getTypeOfDocument() === "interesovanja") {
      this.interesovanjeService.getInteresovanjeJSON(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "saglasnosti") {
      this.saglasnostService.getSaglasnostJSON(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "potvrde") {
      this.potvdaService.getPotvrdaJSON(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "zahtevi") {
      this.zahtevService.getZahtevJSON(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else {
      this.digitalniService.getSertifikatJSON(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    }
  }
  
  download(typeOfDoc : string) : void {
    let filename = this.getTypeOfDocument() + `-${this.dokument.IDDOKUMENTA}`;
    let type = "";

    if(typeOfDoc === "pdf") {
      filename+=".pdf";
      type = "application/pdf;charset=utf-8";

      this.getDocumentPDF(type, filename);
    } else {
      filename+=".html";
      type = "text/html;charset=utf-8";

      this.getDocumentHTML(type, filename);
    }
  }

  getDocumentPDF(type : string, filename : string) {
    if(this.getTypeOfDocument() === "interesovanja") {
      this.interesovanjeService.getInteresovanjePDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "saglasnosti") {
      this.saglasnostService.getSaglasnostPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "potvrde") {
      this.potvdaService.getPotvrdaPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "zahtevi") {
      this.zahtevService.getZahtevPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else {
      this.digitalniService.getSertifikatPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    }
  }

  getDocumentHTML(type : string, filename : string) {
    if(this.getTypeOfDocument() === "interesovanja") {
      this.interesovanjeService.getInteresovanjeXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          console.log(res)
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "saglasnosti") {
      this.saglasnostService.getSaglasnostXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "potvrde") {
      this.potvdaService.getPotvrdaXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "zahtevi") {
      this.zahtevService.getZahtevXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else {
      this.digitalniService.getSertifikatXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    }
  }

  onReturnedDocument(res : any, type : string, filename : string) : void {
    let blob = new Blob([res], { type: type });
    saveAs(blob, filename);
  }


}

