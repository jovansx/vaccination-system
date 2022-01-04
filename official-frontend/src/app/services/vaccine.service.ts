import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IVaccine } from "../models/vaccine.model";

@Injectable({
  providedIn: 'root'
})
export class VaccineService {

  constructor(private _http: HttpClient) { }

  getAll(): Observable<string> {
    return this._http.get<string>('/vakcine', {responseType: 'text' as 'json'});
  }

  updateAmount(element: IVaccine) {
    let xmlUpdateAmount = `
      <updateVaccine>
        <type>` + element.type
                +
        `</type>
        <serialNumber>` + element.series
                        +
        `</serialNumber>
        <amount>` + element.amount + 
        `</amount>
      </updateVaccine>
    `
    return this._http.put('/vakcine', xmlUpdateAmount, {
      headers: new HttpHeaders({
        'Content-Type':  'application/xml',
        'Accept':  'application/xml', 
        'Response-Type': 'text'
      })
    }).toPromise()
  }
}