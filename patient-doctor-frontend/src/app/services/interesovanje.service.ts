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
}
