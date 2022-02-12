import { Component, Input, OnInit } from '@angular/core';
import { saveAs } from 'file-saver';
import { Toast, ToastrService } from 'ngx-toastr';
import { convertResponseError } from 'src/app/error-converter.function';
import { DocumentProxyService } from 'src/app/services/document-proxy.service';
import { XmlConverterService } from 'src/app/services/xml-converter.service';

import { environment as env } from '../../../environments/environment';


@Component({
  selector: 'app-document-card',
  templateUrl: './document-card.component.html',
  styleUrls: ['./document-card.component.css']
})
export class DocumentCardComponent implements OnInit {

  @Input()
  dokument: any;
  constructor(private documentProxy: DocumentProxyService, private _toastr : ToastrService) { }

  ngOnInit(): void {
  }

  getTypeOfDocument() : string {
    if(this.dokument.NAZIVDOKUMENTA === "Interesovanje za vakcinacijom")
      return "interesovanja";
    else if(this.dokument.NAZIVDOKUMENTA === "Saglasnost o imunizaciji 1" || this.dokument.NAZIVDOKUMENTA === "Saglasnost o imunizaciji 2")
      return "saglasnosti";
    else if(this.dokument.NAZIVDOKUMENTA === "Potvrda o vakcinaciji 1" || this.dokument.NAZIVDOKUMENTA === "Potvrda o vakcinaciji 2")
      return "potvrde";
    else if(this.dokument.NAZIVDOKUMENTA === "Zahtev za sertifikat")
      return "zahtevi";
    return "digitalni-sertifikati";
  }

  display(typeOfDoc : string) : void {
    window.open(`${env.apiUrl}/document-proxy/${this.getTypeOfDocument()}/${typeOfDoc}/${this.dokument.IDDOKUMENTA}`, "_blank")
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
      this.documentProxy.getInteresovanjePDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "saglasnosti") {
      this.documentProxy.getSaglasnostPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "potvrde") {
      this.documentProxy.getPotvrdaPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "zahtevi") {
      this.documentProxy.getZahtevPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else {
      this.documentProxy.getSertifikatPDF(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(res, type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    }
  }

  getDocumentHTML(type : string, filename : string) {
    if(this.getTypeOfDocument() === "interesovanja") {
      this.documentProxy.getInteresovanjeXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          console.log(res)
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "saglasnosti") {
      this.documentProxy.getSaglasnostXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "potvrde") {
      this.documentProxy.getPotvrdaXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else if(this.getTypeOfDocument() === "zahtevi") {
      this.documentProxy.getZahtevXHTML(this.dokument.IDDOKUMENTA).subscribe(
        (res: any) => {
          this.onReturnedDocument(JSON.stringify(res), type, filename)
        },
        (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
      );
    } else {
      this.documentProxy.getSertifikatXHTML(this.dokument.IDDOKUMENTA).subscribe(
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

