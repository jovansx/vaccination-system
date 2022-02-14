import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PretragaService {

  constructor(private _http: HttpClient) { }

  basicSearch(searchInput: string): Observable<string> {
    return this._http.get<string>(`/pretraga/${searchInput}`, {responseType: 'text' as 'json'});
  }

  interesovanjeAdvencedSearch(metaIme: string | undefined, metaPrezime: string | undefined, metaIdBroj: string | undefined, metaLokacija: string | undefined) {
    let paramsObj = {}
    if(metaIme !== undefined)
      paramsObj['ime'] = metaIme;
    if(metaPrezime !== undefined)
      paramsObj['prezime'] = metaPrezime;
    if(metaIdBroj !== undefined)
      paramsObj['id_broj'] = metaIdBroj;
    if(metaLokacija !== undefined)
      paramsObj['lokacija'] = metaLokacija;
    let searchParams = new URLSearchParams(paramsObj);
    return this._http.get<string>(`/pretraga/interesovanje?${searchParams.toString()}`, {responseType: 'text' as 'json'});
  }

  saglasnostAdvencedSearch(metaIme: string | undefined, metaPrezime: string | undefined, metaIdBroj: string | undefined, metaLokacija: string | undefined, metaPol: string | undefined) {
    let paramsObj = {}
    if(metaIme !== undefined)
      paramsObj['ime'] = metaIme;
    if(metaPrezime !== undefined)
      paramsObj['prezime'] = metaPrezime;
    if(metaIdBroj !== undefined)
      paramsObj['id_broj'] = metaIdBroj;
    if(metaLokacija !== undefined)
      paramsObj['lokacija'] = metaLokacija;
    if(metaPol !== undefined)
      paramsObj['pol'] = metaPol;
    let searchParams = new URLSearchParams(paramsObj);
    return this._http.get<string>(`/pretraga/saglasnosti?${searchParams.toString()}`, {responseType: 'text' as 'json'});
  }

  digitalniAdvencedSearch(metaIme: string | undefined, metaPrezime: string | undefined, metaIdBroj: string | undefined, metaPol: string | undefined) {
    let paramsObj = {}
    if(metaIme !== undefined)
      paramsObj['ime'] = metaIme;
    if(metaPrezime !== undefined)
      paramsObj['prezime'] = metaPrezime;
    if(metaIdBroj !== undefined)
      paramsObj['id_broj'] = metaIdBroj;
    if(metaPol !== undefined)
      paramsObj['pol'] = metaPol;
    let searchParams = new URLSearchParams(paramsObj);
    return this._http.get<string>(`/pretraga/sertifikat?${searchParams.toString()}`, {responseType: 'text' as 'json'});
  }
  zahtevAdvencedSearch(metaIme: string | undefined, metaPrezime: string | undefined, metaIdBroj: string | undefined, metaPol: string | undefined) {
    let paramsObj = {}
    if(metaIme !== undefined)
      paramsObj['ime'] = metaIme;
    if(metaPrezime !== undefined)
      paramsObj['prezime'] = metaPrezime;
    if(metaIdBroj !== undefined)
      paramsObj['id_broj'] = metaIdBroj;
    if(metaPol !== undefined)
      paramsObj['pol'] = metaPol;
    let searchParams = new URLSearchParams(paramsObj);
    return this._http.get<string>(`/pretraga/zahtev?${searchParams.toString()}`, {responseType: 'text' as 'json'});
  }
  potvrdaAdvencedSearch(metaIme: string | undefined, metaPrezime: string | undefined, metaIdBroj: string | undefined) {
    let paramsObj = {}
    if(metaIme !== undefined)
      paramsObj['ime'] = metaIme;
    if(metaPrezime !== undefined)
      paramsObj['prezime'] = metaPrezime;
    if(metaIdBroj !== undefined)
      paramsObj['id_broj'] = metaIdBroj;
    let searchParams = new URLSearchParams(paramsObj);
    return this._http.get<string>(`/pretraga/potvrda?${searchParams.toString()}`, {responseType: 'text' as 'json'});
  }

}
