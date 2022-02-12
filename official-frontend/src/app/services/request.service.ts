import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private _http: HttpClient) { }

  getAllNeodobreniZahtevi(): Observable<string> {
    return this._http.get<string>('/izdavanje-dzs/zahtevi', {responseType: 'text' as 'json'});
  }

  createRequest(digitalniSertifikatXml: string) {
    return this._http.post<string>('/izdavanje-dzs', digitalniSertifikatXml)
  }

  rejectRequest(id: string) {
    return this._http.delete<string>('/izdavanje-dzs/' + id);
  }
}
