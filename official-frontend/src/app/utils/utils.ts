import { IzvestajOImunizaciji } from "../models/report.model";


export const makeReportFromResponse = (response: any): IzvestajOImunizaciji => {
    let periodOd = response.PERIOD[0].OD;
    let periodDo = response.PERIOD[0].DO;
    let brojInteresovanja = response.DOKUMENTI[0].INTERESOVANJE_ZA_IMUNIZACIJU[0]._;
    let primljenoZelenihSertifikata = response.DOKUMENTI[0].ZELENI_SERTIFIKAT[0].PRIMLJENO[0]._;
    let izdatoZelenihSertifikata = response.DOKUMENTI[0].ZELENI_SERTIFIKAT[0].IZDATO[0]._;
    
    let ukupnoIzdatoVakcina = response.DOZE_VAKCINA[0].$.IZDATO;
    let kolicinaDoze1 = response.DOZE_VAKCINA[0].RASPODELA_PO_DOZAMA[0].DOZA[0].$.KOLICINA
    let kolicinaDoze2 = response.DOZE_VAKCINA[0].RASPODELA_PO_DOZAMA[0].DOZA[1].$.KOLICINA
    let pfizer = response.DOZE_VAKCINA[0].RASPODELA_PO_PROIZVODJACIMA[0].PROIZVODJAC[0].$.KOLICINA
    let sputnikV = response.DOZE_VAKCINA[0].RASPODELA_PO_PROIZVODJACIMA[0].PROIZVODJAC[1].$.KOLICINA
    let sinopharm = response.DOZE_VAKCINA[0].RASPODELA_PO_PROIZVODJACIMA[0].PROIZVODJAC[2].$.KOLICINA
    let astraZeneca = response.DOZE_VAKCINA[0].RASPODELA_PO_PROIZVODJACIMA[0].PROIZVODJAC[3].$.KOLICINA
    let moderna = response.DOZE_VAKCINA[0].RASPODELA_PO_PROIZVODJACIMA[0].PROIZVODJAC[4].$.KOLICINA

    return new IzvestajOImunizaciji(periodOd,periodDo,brojInteresovanja, primljenoZelenihSertifikata, izdatoZelenihSertifikata, ukupnoIzdatoVakcina,
        kolicinaDoze1, kolicinaDoze2, pfizer, sputnikV, sinopharm, astraZeneca, moderna);
}