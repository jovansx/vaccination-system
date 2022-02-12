import { Routes } from "@angular/router";
import { LoginComponent } from "../login/login.component";
import { ReportPageComponent } from "../report-page/report-page.component";
import { RequestPageComponent } from "../request-page/request-page.component";
import { SearchPageComponent } from "../search-page/search-page.component";
import { SingleReportPageComponent } from "../single-report-page/single-report-page.component";
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
  },
  {
    path: 'home/reports',
    component: ReportPageComponent
  },
  {
    path: 'home/reports/:id',
    component: SingleReportPageComponent
  },
  {
    path: 'home/search',
    component: SearchPageComponent
  },
  {
    path: 'home/requests',
    component: RequestPageComponent
  }
]