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

  public getZahtevXHTML(id: string): Observable<string> {
    return this._http.get<string>('/zahtevi/xhtml/'+id, {responseType: 'text' as 'json'});
  }

  public getZahtevPDF(id: string): Observable<string> {
    return this._http.get<string>('/zahtevi/pdf/'+id, {responseType: 'arraybuffer' as 'json'});
  }

  public getZahtevJSON(id: string): Observable<string> {
    return this._http.get<string>('/zahtevi/metadata/json/'+id);
  }
}
