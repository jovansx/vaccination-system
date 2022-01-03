import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './angular-material/angular-material.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

import { JwtDecoderService } from './autentification/services/jwt-decoder.service';
import { AuthService } from './autentification/services/auth.service';
import { HeaderInterceptorService } from './autentification/services/header-interceptor.service';
import { RegistrationComponent } from './registration/registration.component';
import { HeaderComponent } from './header/header.component';
import { PatientSubmitComponent } from './patient/patient-submit/patient-submit.component';
import { PatientDocumentsComponent } from './patient/patient-documents/patient-documents.component';
import { DoctorComponent } from './doctor/doctor.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    PatientDocumentsComponent,
    HeaderComponent,
    PatientSubmitComponent,
    DoctorComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    FlexLayoutModule
  ],
  providers: [
    JwtDecoderService,
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: HeaderInterceptorService, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
