import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  getCurrentAppointment(): Observable<string> {
    return this.http.get<string>('/appointments/current', {responseType: 'text' as 'json'});
  }


}
