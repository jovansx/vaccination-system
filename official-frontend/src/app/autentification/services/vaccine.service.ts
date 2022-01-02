import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VaccineService {

  constructor(private _http: HttpClient) { }

  getAll(): Observable<string> {
    return this._http.get<string>('/vakcine');
  }
}