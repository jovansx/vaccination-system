import { Component, OnInit } from '@angular/core';
import { IVakcina, IZahtev, Podnosilac, Vakcina, Zahtev } from '../models/request.model';

@Component({
  selector: 'app-request-page',
  templateUrl: './request-page.component.html',
  styleUrls: ['./request-page.component.css']
})
export class RequestPageComponent implements OnInit {
  zahtevi: IZahtev[] = [];
  zahtevSelected: IZahtev;
  selectedOptions: IZahtev[];
  constructor() { }

  ngOnInit(): void {
    this.getAllZahtevi()
    let vakcine: IVakcina[] = []
    vakcine.push(new Vakcina("","","","",""))
    vakcine.push(new Vakcina("","","","",""))
    this.zahtevSelected = new Zahtev(new Podnosilac("Jeelna","","","",""), vakcine)
  }

  getAllZahtevi() {
    let podnosilac = new Podnosilac("Jelena", "Stojanovic", "0510999805068", "Zenski", "05-10-1999");
    let podnosilac2 = new Podnosilac("Jovan", "Simic", "5510934505068", "Zenski", "30-01-1999");
    let podnosilac3 = new Podnosilac("Aleksandar", "Buljevic", "0510999805068", "Zenski", "15-10-1999");
    let vakcine: IVakcina[] = []
    let v1 = new Vakcina("Sinopharm", "BIONTECH MANUFACTURING", "1", "2021-09-09", "Promenada u Novom Sadu");
    let v2 = new Vakcina("Sinopharm", "BIONTECH MANUFACTURING", "2", "2021-10-09", "Promenada u Novom Sadu");
    vakcine.push(v1);
    vakcine.push(v2);
    this.zahtevi.push(new Zahtev(podnosilac, vakcine))
    this.zahtevi.push(new Zahtev(podnosilac2, vakcine))
    this.zahtevi.push(new Zahtev(podnosilac3, vakcine))
  }

  searchRequests() {

  }

  onNgModelChange(event: any) {
    this.zahtevSelected = event[0]
  }

  //imam iz zahteva: podnosilac (ime, prezime, idbr, pol, datumrodjenja)
  // treba mi: 
  //vakcinacija (iz potvrde o vakcinaciji - primljene vakcine, naziv vakcine, datum_izdavanja, zdr ustanova)
  //testovi - hardkodovano
  //qr code - kao sto su aca i simic

  // prikazi: podnosilac
  // primljene vakcine
  // zdravsvtena ustanova

}
