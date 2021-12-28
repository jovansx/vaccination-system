import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Credentials } from '../models/credentials.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _http: HttpClient, private _router: Router) { }

  public loginUser(userCredentials: Credentials): Observable<string> {
    let headers = new HttpHeaders({'Content-Type': 'application/xml'});
    const xmlLogin: string = 
    `<?xml version="1.0" encoding="UTF-8"?>
    <login>
        <email>
            ${userCredentials.email}
        </email>
        <password>
            ${userCredentials.password}
        </password>
    </login>`;
    return this._http.post<string>('/authenticate', xmlLogin, {headers});
  }
}
