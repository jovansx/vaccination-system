import { Routes } from "@angular/router";
import { LoginComponent } from "../login/login.component";
import { VaccinePageComponent } from "../vaccine-page/vaccine-page.component";

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home/vaccines',
    component: VaccinePageComponent
  }
]