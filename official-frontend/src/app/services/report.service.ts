import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  
  constructor(private _http: HttpClient) { }

  getAll(): Observable<string> {
    return this._http.get<string>('/izvestaji', {responseType: 'text' as 'json'});
  }

  getReportForPeriod(periodOd: string, periodDo: string): Observable<string> {
    return this._http.get<string>('/izvestaji/' + periodOd + "/" + periodDo, {responseType: 'text' as 'json'});
  }

  getById(id: string): Observable<string> {
    return this._http.get<string>('/izvestaji/' + id, {responseType: 'text' as 'json'});
  }

  saveReport(xmlIzvestajOImunizaciji: string) {
    return this._http.post<string>('/izvestaji', xmlIzvestajOImunizaciji, {responseType: 'text' as 'json'});
  }
}
