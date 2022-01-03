import { Routes } from '@angular/router';
import { DoctorComponent } from '../doctor/doctor.component';
import { LoginComponent } from '../login/login.component';
import { PatientDocumentsComponent } from '../patient/patient-documents/patient-documents.component';
import { PatientSubmitComponent } from '../patient/patient-submit/patient-submit.component';
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
    path: 'home/patient/documents',
    component: PatientDocumentsComponent,
  },
  {
    path: 'home/patient/submit',
    component: PatientSubmitComponent,
  },
  {
    path: 'home/doctor',
    component: DoctorComponent,
  },
    { path: '**', redirectTo: '/login' },
];
