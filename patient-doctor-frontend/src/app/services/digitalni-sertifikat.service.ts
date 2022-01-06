import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DigitalniSertifikatService {

  constructor(private _http: HttpClient) { }

  public getSertifikatXHTML(id: string): Observable<string> {
    return this._http.get<string>('/digitalni-sertifikati/xhtml/'+id, {responseType: 'text' as 'json'});
  }

  public getSertifikatPDF(id: string): Observable<string> {
    return this._http.get<string>('/digitalni-sertifikati/pdf/'+id, {responseType: 'arraybuffer' as 'json'});
  }
}
