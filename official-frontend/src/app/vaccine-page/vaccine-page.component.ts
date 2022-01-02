import { Component, OnInit } from '@angular/core';
export interface Vaccine {
  name: string;
  code: string;
  series: number;
  manufacturer: string;
  sideEffects: string;
  amount: number;
}

const ELEMENT_DATA: Vaccine[] = [
  {name: 'Hydrogen', code: 'PFIZER', series: 1, manufacturer: 'Britanski proizvodjac', sideEffects: 'Temperatura', amount: 203},
  {name: 'Helium', code: 'SPUTNIK_V', series: 1, manufacturer: 'Britanski proizvodjac', sideEffects: 'Temperatura', amount: 536},
  {name: 'Lithium', code: 'SINOPHARM', series: 1, manufacturer: 'Britanski proizvodjac', sideEffects: 'Temperatura', amount: 358},
  {name: 'Beryllium', code: 'ASTRA_ZENECA', series: 1, manufacturer: 'Britanski proizvodjac', sideEffects: 'Temperatura', amount: 1204},
  {name: 'Boron', code: 'MODERNA', series: 1, manufacturer: 'Britanski proizvodjac', sideEffects: 'Temperatura', amount: 2243}
];

@Component({
  selector: 'app-vaccine-page',
  templateUrl: './vaccine-page.component.html',
  styleUrls: ['./vaccine-page.component.css']
})
export class VaccinePageComponent implements OnInit {
  displayedColumns: string[] = ['name', 'series', 'manufacturer', 'sideEffects', 'amount', 'edit'];
  dataSource = ELEMENT_DATA;
  constructor() { }

  ngOnInit(): void {
  }

  saveChanges(element: any): void {
    console.log(element)
  }
}
