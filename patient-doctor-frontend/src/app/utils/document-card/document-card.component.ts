import { Component, Input, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { convertResponseError } from 'src/app/error-converter.function';
import { InteresovanjeService } from 'src/app/services/interesovanje.service';
import { XmlConverterService } from 'src/app/services/xml-converter.service';

import { environment as env } from '../../../environments/environment';


@Component({
  selector: 'app-document-card',
  templateUrl: './document-card.component.html',
  styleUrls: ['./document-card.component.scss']
})
export class DocumentCardComponent implements OnInit {

  @Input()
  dokument: any;
  constructor() { }

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

  displayPdf() : void {
    window.open(`${env.apiUrl}/${this.getTypeOfDocument()}/pdf/${this.dokument.IDDOKUMENTA}`, "_blank")
  }

  displayHtml() : void {
    window.open(`${env.apiUrl}/${this.getTypeOfDocument()}/xhtml/${this.dokument.IDDOKUMENTA}`, "_blank")
  }

}

