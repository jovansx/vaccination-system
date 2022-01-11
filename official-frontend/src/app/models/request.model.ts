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
    podnosilac: IPodnosilac;
    vakcine: IVakcina[];
}

export class Zahtev implements IZahtev {
    podnosilac: IPodnosilac;
    vakcine: IVakcina[];

    constructor(podnosilac: IPodnosilac, vakcine: IVakcina[]) {
        this.podnosilac = podnosilac;
        this.vakcine = vakcine;
    }
}