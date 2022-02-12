import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { ToastrModule } from 'ngx-toastr';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './angular-material/angular-material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './autentification/services/auth.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HeaderInterceptorService } from './autentification/services/header-interceptor.service';
import { VaccinePageComponent } from './vaccine-page/vaccine-page.component';
import { HeaderComponent } from './header/header.component';
import { VaccineService } from './services/vaccine.service';
import { ReportPageComponent } from './report-page/report-page.component';
import { SingleReportPageComponent } from './single-report-page/single-report-page.component';
import { ReportInfoPanelComponent } from './report-info-panel/report-info-panel.component';
import { ProgressBarDialogComponent } from './utils/progress-bar-dialog/progress-bar-dialog.component';
import { RequestPageComponent } from './request-page/request-page.component';
import { SearchPageComponent } from './search-page/search-page.component';
import { DocumentCardComponent } from './utils/document-card/document-card.component'

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    VaccinePageComponent,
    HeaderComponent,
    ReportPageComponent,
    SingleReportPageComponent,
    ReportInfoPanelComponent,
    ProgressBarDialogComponent,
    RequestPageComponent,
    SearchPageComponent,
    DocumentCardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    FlexLayoutModule
  ],
  providers: [
    AuthService,
    VaccineService,
    { provide: HTTP_INTERCEPTORS, useClass: HeaderInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
