import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SaglasnostService {

  constructor(private _http: HttpClient) { }

  public sendSaglasnost(saglasnostXml: string): Observable<void> {
    return this._http.post<void>('/saglasnosti', saglasnostXml);
  }

  public getSaglasnost(id: string): Observable<string> {
    return this._http.get<string>(`/saglasnosti/${id}`, {responseType: 'text' as 'json'});
  }
}
