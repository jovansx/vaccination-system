import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentProxyService {

  constructor(private http: HttpClient) { }

  public getInteresovanjeXHTML(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/interesovanja/xhtml/' + id, {responseType: 'text' as 'json'});
  }

  public getInteresovanjePDF(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/interesovanja/pdf/' + id, {responseType: 'arraybuffer' as 'json'});
  }

  public getZahtevXHTML(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/zahtevi/xhtml/' + id, {responseType: 'text' as 'json'});
  }

  public getZahtevPDF(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/zahtevi/pdf/' + id, {responseType: 'arraybuffer' as 'json'});
  }

  public getPotvrdaXHTML(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/potvrde/xhtml/' + id, {responseType: 'text' as 'json'});
  }

  public getPotvrdaPDF(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/potvrde/pdf/' + id, {responseType: 'arraybuffer' as 'json'});
  }

  public getSaglasnostXHTML(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/saglasnosti/xhtml/' + id, {responseType: 'text' as 'json'});
  }

  public getSaglasnostPDF(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/saglasnosti/pdf/' + id, {responseType: 'arraybuffer' as 'json'});
  }

  public getSertifikatXHTML(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/digitalni-sertifikati/xhtml/' + id, {responseType: 'text' as 'json'});
  }

  public getSertifikatPDF(id: string): Observable<string> {
    return this.http.get<string>('/document-proxy/digitalni-sertifikati/pdf/' + id, {responseType: 'arraybuffer' as 'json'});
  }
}
