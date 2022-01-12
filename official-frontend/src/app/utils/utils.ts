import { format } from "date-fns";
import { IzvestajOImunizaciji } from "../models/report.model";
import { IZahtev } from "../models/request.model";


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

export const makeDigitalniSertifikatXml = (zahtev: IZahtev) => {
    let datumIVremeIzdavanja = format(new Date(), "yyyy-MM-dd'T'HH:mm:ss");

    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <digitalni_sertifikat 
        xmlns="http://www.akatsuki.org/digitalni_sertifikat"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:t="http://www.akatsuki.org/tipovi"
        xsi:schemaLocation="http://www.akatsuki.org digitalni_sertifikat.xsd
        http://www.akatsuki.org/tipovi tipovi.xsd"
        datum_i_vreme_izdavanja="` + datumIVremeIzdavanja
        +`" broj_sertifikata="2352"
        xmlns:pred="http://www.akatsuki.org/rdf/examples/predicate/"
        about="http://www.akatsuki.org/digitalni-sertifikati/`
      + zahtev.podnosilac.idBroj +
      `">
        <primalac>
            <t:ime property="pred:ime" datatype="xs:string">` + zahtev.podnosilac.ime
            +`</t:ime>
            <t:prezime property="pred:prezime" datatype="xs:string">` + zahtev.podnosilac.prezime
            +`</t:prezime>
            <t:id_broj property="pred:id_broj" datatype="xs:string">` + zahtev.podnosilac.idBroj
            +`</t:id_broj>
            <t:pol property="pred:pol" datatype="xs:string">` + zahtev.podnosilac.pol
            +`</t:pol>
            <t:datum_rodjenja>` + zahtev.podnosilac.datumRodjenja
            +`</t:datum_rodjenja>
        </primalac>
        <vakcinacija>
            <doza broj="1">
                <tip nazivVakcine="` + zahtev.vakcine[0].naziv
                +`"></tip>
                <proizvodjac_i_serija>` + zahtev.vakcine[0].proizvodjac + " " + zahtev.vakcine[0].serija
                +`</proizvodjac_i_serija>
                <datum>` + zahtev.vakcine[0].datum
                +`</datum>
                <zdravstvena_ustanova>` + zahtev.vakcine[0].zdravstvenaUstanova
                +`</zdravstvena_ustanova>
            </doza>
            <doza broj="2">
                <tip nazivVakcine="` + zahtev.vakcine[1].naziv
                +`"></tip>
                <proizvodjac_i_serija>` + zahtev.vakcine[1].proizvodjac + " " + zahtev.vakcine[1].serija
                +`</proizvodjac_i_serija>
                <datum>` + zahtev.vakcine[1].datum
                +`</datum>
                <zdravstvena_ustanova>` + zahtev.vakcine[1].zdravstvenaUstanova
                +`</zdravstvena_ustanova>
            </doza>
        </vakcinacija>
        <testovi>
            <test>
                <vrsta_uzorka>N/A</vrsta_uzorka>
                <proizvodjac>f</proizvodjac>
                <datum_uzorkovanja>2001-10-26T21:32:52</datum_uzorkovanja>
                <datum_izdavanja_rezultata>2001-10-26T21:32:52</datum_izdavanja_rezultata>
                <rezultat>N/A</rezultat>
                <laboratorija>N/A</laboratorija>
            </test>
            <test>
                <vrsta_uzorka>N/A</vrsta_uzorka>
                <proizvodjac>N/A</proizvodjac>
                <datum_uzorkovanja>2001-10-26T21:32:52</datum_uzorkovanja>
                <datum_izdavanja_rezultata>2001-10-26T21:32:52</datum_izdavanja_rezultata>
                <rezultat>N/A</rezultat>
                <laboratorija>N/A</laboratorija>
            </test>
            <test>
                <vrsta_uzorka>N/A</vrsta_uzorka>
                <proizvodjac>N/A</proizvodjac>
                <datum_uzorkovanja>2001-10-26T21:32:52</datum_uzorkovanja>
                <datum_izdavanja_rezultata>2001-10-26T21:32:52</datum_izdavanja_rezultata>
                <rezultat>N/A</rezultat>
                <laboratorija>N/A</laboratorija>
            </test>
        </testovi>
      <qr_code>url</qr_code>
    </digitalni_sertifikat>`

    return xml;
}