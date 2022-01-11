import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InteresovanjeService {

  constructor(private _http: HttpClient) { }

  public sendInteresovanje(interesovanjeXml: string): Observable<void> {
    return this._http.post<void>('/interesovanja', interesovanjeXml);
  }

  public getInteresovanje(id: string): Observable<string> {
    return this._http.get<string>('/interesovanja/'+id, {responseType: 'text' as 'json'});
  }

  public getInteresovanjeJSON(id: string): Observable<string> {
    return this._http.get<string>('/interesovanja/metadata/json/'+id);
  }

  public getInteresovanjeXHTML(id: string): Observable<string> {
    return this._http.get<string>('/interesovanja/xhtml/'+id, {responseType: 'text' as 'json'});
  }

  public getInteresovanjePDF(id: string): Observable<string> {
    return this._http.get<string>('/interesovanja/pdf/'+id, {responseType: 'arraybuffer' as 'json'});
  }

}
