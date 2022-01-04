import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtDecoderService } from '../autentification/services/jwt-decoder.service';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private _http: HttpClient, private _jwt: JwtDecoderService) { }

  public declareCurrentForm(idBroj: string): Observable<string> {
    return this._http.get<string>(`/pacijenti/trenutna-forma/${idBroj}`, {responseType: 'text' as 'json'});
  }

  public getPatientDetailsForInteresovanje(): Observable<string> {
    let idBroj = this._jwt.getIdFromToken();
    return this._http.get<string>(`/pacijenti/interesovanje-detalji/${idBroj}`, {responseType: 'text' as 'json'});
  }

  public getPatientDetailsForSaglasnost(): Observable<string> {
    let idBroj = this._jwt.getIdFromToken();
    return this._http.get<string>(`/pacijenti/saglasnost-detalji/${idBroj}`, {responseType: 'text' as 'json'});
  }

  public getPatientDetailsForZahtev(): Observable<string> {
    let idBroj = this._jwt.getIdFromToken();
    return this._http.get<string>(`/pacijenti/zahtev-detalji/${idBroj}`, {responseType: 'text' as 'json'});
  }
}
