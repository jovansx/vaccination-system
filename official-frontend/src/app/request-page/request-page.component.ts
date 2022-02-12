import { Component, OnInit } from '@angular/core';
import { IVakcina, IZahtev, Podnosilac, Vakcina, Zahtev } from '../models/request.model';
import { RequestService } from '../services/request.service';
import { XmlConverterService } from '../services/xml-converter.service';
import { format } from 'date-fns'
import { makeDigitalniSertifikatXml } from '../utils/utils';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { ProgressBarDialogComponent } from '../utils/progress-bar-dialog/progress-bar-dialog.component';

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
  isZahtevSelected: boolean = false;

  constructor(private requestService: RequestService, private _xml_parser: XmlConverterService, private _toastr: ToastrService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.getAllNeodobreniZahtevi()
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
    this.isZahtevSelected = true;
  }

  acceptRequest() {
    const dialogRef = this.dialog.open(ProgressBarDialogComponent, {
      height: '60px',
      width: '30%',
    });
    let digitalniSertifikatXml = makeDigitalniSertifikatXml(this.zahtevSelected);
    this.requestService.createRequest(digitalniSertifikatXml)
      .subscribe((res) => {
        this._toastr.success("Uspesno kreiran digitalni sertifikat sa id-jem " + res);
        dialogRef.close();
        this.isZahtevSelected = false;
        this.getAllNeodobreniZahtevi();
      }, (err) => {
        this._toastr.error(err.error.message);
        dialogRef.close();
        this.isZahtevSelected = false;
      })
  }

  rejectRequest() {
    const dialogRef = this.dialog.open(ProgressBarDialogComponent, {
      height: '60px',
      width: '30%',
    });
    let id = this.zahtevSelected.podnosilac.idBroj
    this.requestService.rejectRequest(id)
      .subscribe(() => {
        this._toastr.success("Zahtev za sertifikat sa id-jem " + id + " je odbijen.");
        dialogRef.close();
        this.isZahtevSelected = false;
        this.getAllNeodobreniZahtevi();
      }, (err) => {
        this._toastr.error(err.error.message);
        dialogRef.close();
        this.isZahtevSelected = false;
      })
  }
}
