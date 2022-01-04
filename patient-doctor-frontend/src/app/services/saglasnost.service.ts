import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SaglasnostService {

  constructor(private _http: HttpClient) { }

  getCurrentSaglasnost(id : String): Observable<string> {
    return this._http.get<string>('/saglasnosti/by-patient-id/'+id, {responseType: 'text' as 'json'});
  }

  deleteCurrentSaglasnost(id: string): Observable<void> {
    return this._http.delete<void>('/saglasnosti/'+id);
  }

  public sendSaglasnost(saglasnostXml: string): Observable<void> {
    return this._http.post<void>('/saglasnosti', saglasnostXml);
  }

  public updateSaglasnost(saglasnostXml: string): Observable<void> {
    return this._http.put<void>('/saglasnosti', saglasnostXml);
  }

}