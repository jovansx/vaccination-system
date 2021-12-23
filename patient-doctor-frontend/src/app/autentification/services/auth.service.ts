import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credentials } from '../models/credentials.model';
import { Router } from '@angular/router';
import { JwtDecoderService } from './jwt-decoder.service';
import { RegistrationDetails } from '../models/registration-details.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private _http: HttpClient, private _router: Router, private _jwt: JwtDecoderService) {}

  public loginUser(userCredentials: Credentials): Observable<string> {
    return this._http.post<string>('/authenticate', userCredentials);
  }

  public registerUser(userCredentials: RegistrationDetails): Observable<string> {
    return this._http.post<string>('/register-user', userCredentials);
  }

  public logoutUser(): void {
    localStorage.removeItem('token');
    this._router.navigate(['/login']);
  }

  public isUserLoggedIn(): boolean {
    let loggedIn : boolean = !this._jwt.isTokenExpired();
    if(!loggedIn)
     localStorage.removeItem('token');
    return loggedIn;
  }
}
