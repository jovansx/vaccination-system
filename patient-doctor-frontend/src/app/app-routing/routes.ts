import { Routes } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { PatientComponent } from '../patient/patient.component';
import { RegistrationComponent } from '../registration/registration.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },
  {
    path: 'home/patient',
    component: PatientComponent,
  },
    { path: '**', redirectTo: '/login' },
];
