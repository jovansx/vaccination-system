import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(private _http: HttpClient) { }

  public sendZahtev(zahtevXml: string): Observable<void> {
    return this._http.post<void>('/zahtevi', zahtevXml);
  }
}
