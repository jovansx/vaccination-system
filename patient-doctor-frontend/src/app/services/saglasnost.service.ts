import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SaglasnostService {

  constructor(private http: HttpClient) { }

  getCurrentSaglasnost(id : String): Observable<string> {
    return this.http.get<string>('/saglasnosti/by-patient-id/'+id, {responseType: 'text' as 'json'});
  }

  deleteCurrentSaglasnost(id: string): Observable<void> {
    return this.http.delete<void>('/saglasnosti/'+id);
  }

}