import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credentials } from '../models/credentials.model';
import { Router } from '@angular/router';
import { JwtDecoderService } from './jwt-decoder.service';
import { RegistrationDetails } from '../models/registration-details.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private _http: HttpClient, private _router: Router, private _jwt: JwtDecoderService) {}

  public loginUser(userCredentials: Credentials): Observable<string> {
    let headers = new HttpHeaders({'Content-Type': 'application/xml'});
    const xmlRegistration: string = 
    `<?xml version="1.0" encoding="UTF-8"?>
    <login>
        <email>
            ${userCredentials.email}
        </email>
        <password>
            ${userCredentials.password}
        </password>
    </login>`;
    return this._http.post<string>('/authenticate', xmlRegistration, {headers});
  }

  public registerUser(userDetails: RegistrationDetails): Observable<void> {
    let headers = new HttpHeaders({'Content-Type': 'application/xml'});

    const xmlRegistration: string = 
    `<pacijent>
      <ime>${userDetails.ime}</ime>
      <prezime>${userDetails.prezime}</prezime>
      <id_broj>${userDetails.idBroj}</id_broj>
      <fiksni_telefon>${userDetails.fiksniTelefon}</fiksni_telefon>
      <email>${userDetails.email}</email>
      <sifra>${userDetails.sifra}</sifra>
      <tip>PACIJENT</tip>
      <pol>${userDetails.pol}</pol>
      <datum_rodjenja>${userDetails.datumRodjenja}</datum_rodjenja>
      <tip_drzavljanstva>${userDetails.drzavljanstvo}</tip_drzavljanstva>
      <lokacija>${userDetails.opstina}</lokacija>
      <mesto_stanovanja>${userDetails.mestoStanovanja}</mesto_stanovanja>
      <mobilni_telefon>${userDetails.mobilniTelefon}</mobilni_telefon>
      <ime_roditelja>${userDetails.imeOca}</ime_roditelja>
      <mesto_rodjenja>${userDetails.mestoRodjenja}</mesto_rodjenja>
      <radni_status>${userDetails.radniStatus}</radni_status>
      <zanimanje>${userDetails.zanimanje}</zanimanje>
      <ulica>${userDetails.ulica}</ulica>
      <brojKuce>${userDetails.brojKuce}</brojKuce>
    </pacijent>`;
    return this._http.post<void>('/pacijenti', xmlRegistration, {headers});
  }

  public logoutUser(): void {
    localStorage.removeItem('token');
    this._router.navigate(['/login']);
  }

  public isUserLoggedIn(): boolean {
    let loggedIn : boolean = !this._jwt.isTokenExpired();
    if(!loggedIn)
     localStorage.removeItem('token');
    return loggedIn;
  }
}
