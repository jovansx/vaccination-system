import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtDecoderService } from '../autentification/services/jwt-decoder.service';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http: HttpClient, private jwtSercice : JwtDecoderService) { }

  getDoctorInfo(): Observable<string> {
    let id = this.jwtSercice.getIdFromToken();
    return this.http.get<string>('/doktori/basic-info/'+id, {responseType: 'text' as 'json'});
  }
}
