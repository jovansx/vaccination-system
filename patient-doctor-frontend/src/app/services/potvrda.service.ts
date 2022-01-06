import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PotvrdaService {

  constructor(private _http: HttpClient) { }

  sendPotvrdu(potvrdaXml: string): Observable<void> {
    return this._http.post<void>('/potvrde', potvrdaXml);
  }

  getSerijuPrveVakcine(pacijentId: string | undefined) : Observable<string> {
    return this._http.get<string>('/potvrde/serie-of-first-vacine/'+pacijentId, {responseType: 'text' as 'json'});

  }

  public getPotvrdaXHTML(id: string): Observable<string> {
    return this._http.get<string>('/potvrde/xhtml/'+id, {responseType: 'text' as 'json'});
  }

  public getPotvrdaPDF(id: string): Observable<string> {
    return this._http.get<string>('/potvrde/pdf/'+id, {responseType: 'arraybuffer' as 'json'});
  }

}
