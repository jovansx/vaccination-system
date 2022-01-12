export interface IPodnosilac {
    ime: string;
    prezime: string;
    idBroj: string;
    pol: string;
    datumRodjenja: string;
}

export class Podnosilac implements IPodnosilac {
    ime: string;
    prezime: string;
    idBroj: string;
    pol: string;
    datumRodjenja: string;

constructor(ime: string,
    prezime: string,
    idBroj: string,
    pol: string,
    datumRodjenja: string) {
    this.ime = ime;
    this.prezime = prezime;
    this.idBroj = idBroj;
    this.pol = pol;
    this.datumRodjenja = datumRodjenja;
}
}

export interface IVakcina {
    naziv: string;
    proizvodjac: string;
    serija: string;
    datum: string;
    zdravstvenaUstanova: string;
}

export class Vakcina implements IVakcina {
    naziv: string;
    proizvodjac: string;
    serija: string;
    datum: string;
    zdravstvenaUstanova: string;

    constructor(naziv: string,
        proizvodjac: string,
        serija: string,
        datum: string,
        zdravstvenaUstanova: string) {
        this.naziv = naziv;
        this.proizvodjac = proizvodjac;
        this.serija = serija;
        this.datum = datum;
        this.zdravstvenaUstanova = zdravstvenaUstanova;
    }
}

export interface IZahtev {
    razlogPodnosenja: string;
    podnosilac: IPodnosilac;
    vakcine: IVakcina[];
    isValid: boolean;
}

export class Zahtev implements IZahtev {
    razlogPodnosenja: string;
    podnosilac: IPodnosilac;
    vakcine: IVakcina[];
    isValid: boolean;

    constructor(razlogPodnosenja: string, podnosilac: IPodnosilac, vakcine: IVakcina[], isValid: boolean) {
        this.razlogPodnosenja = razlogPodnosenja;
        this.podnosilac = podnosilac;
        this.vakcine = vakcine;
        this.isValid = isValid;
    }
}