export interface IVaccine {
    name: string;
    type: string;
    series: number;
    manufacturer: string;
    sideEffects: string;
    amount: number;
  }
  
export class Vaccine implements IVaccine {
    name: string;
    type: string;
    series: number;
    manufacturer: string;
    sideEffects: string;
    amount: number;

    constructor(
        name: string,
        type: string,
        series: number,
        manufacturer: string,
        sideEffects: string,
        amount: number) {
        this.name = name;
        this.type = type;
        this.series = series;
        this.manufacturer = manufacturer;
        this.sideEffects = sideEffects;
        this.amount = amount;
        }
}
  