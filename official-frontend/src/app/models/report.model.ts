export interface IIzvestajOImunizacijiPeriod {
    id: string;
    period: string;
}

export class IzvestajOImunizacijiPeriod implements IIzvestajOImunizacijiPeriod {
    id: string;
    period: string;

    constructor(id: string, period: string) {
        this.id = id;
        this.period = period;
    }
}

export interface IIzvestajOImunizaciji {
    periodOd: string;
    periodDo: string;
    brojInteresovanja: number;
    primljenoSertifikata: number;
    izdatoSertifikata: number;
    ukupnoIzdato: number;
    doza1: number;
    doza2: number;
    pfizer: number;
    sputnikV: number;
    sinopharm: number;
    astraZeneca: number;
    moderna: number;
}

export class IzvestajOImunizaciji implements IIzvestajOImunizaciji {
    periodOd: string;
    periodDo: string;
    brojInteresovanja: number;
    primljenoSertifikata: number;
    izdatoSertifikata: number;
    ukupnoIzdato: number;
    doza1: number;
    doza2: number;
    pfizer: number;
    sputnikV: number;
    sinopharm: number;
    astraZeneca: number;
    moderna: number;
    
    constructor(
        periodOd: string,
        periodDo: string,
        brojInteresovanja: number,
        primljenoSertifikata: number,
        izdatoSertifikata: number,
        ukupnoIzdato: number,
        doza1: number,
        doza2: number,
        pfizer: number,
        sputnikV: number,
        sinopharm: number,
        astraZeneca: number,
        moderna: number) {
            this.periodOd = periodOd;
            this.periodDo = periodDo;
            this.brojInteresovanja = brojInteresovanja;
            this.primljenoSertifikata = primljenoSertifikata;
            this.izdatoSertifikata = izdatoSertifikata;
            this.ukupnoIzdato = ukupnoIzdato;
            this.doza1 = doza1;
            this.doza2 = doza2;
            this.pfizer = pfizer;
            this.sputnikV = sputnikV;
            this.sinopharm = sinopharm;
            this.astraZeneca = astraZeneca;
            this.moderna = moderna;
    }
}