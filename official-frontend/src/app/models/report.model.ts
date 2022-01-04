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