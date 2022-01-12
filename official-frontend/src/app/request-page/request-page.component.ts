import { Component, OnInit } from '@angular/core';
import { IVakcina, IZahtev, Podnosilac, Vakcina, Zahtev } from '../models/request.model';
import { RequestService } from '../services/request.service';
import { XmlConverterService } from '../services/xml-converter.service';

@Component({
  selector: 'app-request-page',
  templateUrl: './request-page.component.html',
  styleUrls: ['./request-page.component.css']
})
export class RequestPageComponent implements OnInit {
  zahtevi: IZahtev[] = [];
  zahtevSelected: IZahtev;
  selectedOptions: IZahtev[];
  allRequestsLoaded: boolean = true;

  constructor(private requestService: RequestService, private _xml_parser: XmlConverterService) { }

  ngOnInit(): void {
    this.getAllNeodobreniZahtevi()
    let vakcine: IVakcina[] = []
    vakcine.push(new Vakcina("","","","",""))
    vakcine.push(new Vakcina("","","","",""))
    this.zahtevSelected = new Zahtev("", new Podnosilac("Jeelna","","","",""), vakcine, true)
  }

  getAllNeodobreniZahtevi() {
    this.allRequestsLoaded = false;
    this.zahtevi = [];
    this.requestService.getAllNeodobreniZahtevi()
      .subscribe(res => {
        let response = this._xml_parser.parseXmlToObject(res);
        console.log(response)
        // if(response.NEODOBRENZAHTEVDTOLIST !== undefined) {
          response.NEODOBRENZAHTEVDTOLIST.forEach((element: any) => {
            let podnosilac = new Podnosilac(element.IME[0], element.PREZIME[0], element.IDBROJ[0], element.POL[0], element.DATUMRODJENJA[0]);
            let vakcine: IVakcina[] = []
            let isValid: boolean = true;
            if(element.DRUGAPOTVRDADTO[0] !== '') {
              let nazivVakcine = element.DRUGAPOTVRDADTO[0].NAZIVVAKCINE[0]
              let proizvodjac = ''
              if(nazivVakcine === 'Moderna') {
                proizvodjac = 'Kineska'
              }
              let v1 = new Vakcina(element.DRUGAPOTVRDADTO[0].NAZIVVAKCINE[0], proizvodjac, element.DRUGAPOTVRDADTO[0].DOZA1SERIJA[0], element.DRUGAPOTVRDADTO[0].DOZA1DATUM[0], element.DRUGAPOTVRDADTO[0].ZDRAVSTVENAUSTANOVA[0]);
              let v2 = new Vakcina(element.DRUGAPOTVRDADTO[0].NAZIVVAKCINE[0], proizvodjac, element.DRUGAPOTVRDADTO[0].DOZA2SERIJA[0], element.DRUGAPOTVRDADTO[0].DOZA2DATUM[0], element.DRUGAPOTVRDADTO[0].ZDRAVSTVENAUSTANOVA[0]);
              vakcine.push(v1);
              vakcine.push(v2);
            } else {
              vakcine.push(new Vakcina("","","","",""));
              vakcine.push(new Vakcina("","","","",""));
              isValid = false;
            }
            
            this.zahtevi.push(new Zahtev(element.RAZLOGPODNOSENJA[0], podnosilac, vakcine, isValid));
          });
        // }
        this.allRequestsLoaded = true;
      })
  }

  searchRequests() {

  }

  onNgModelChange(event: any) {
    this.zahtevSelected = event[0]
  }
}
