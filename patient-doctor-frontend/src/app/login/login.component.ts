import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { convertResponseError } from 'src/app/error-converter.function';
import { AuthService } from '../autentification/services/auth.service';
import { JwtDecoderService } from '../autentification/services/jwt-decoder.service';
import { ValidatorService } from '../services/validator.service';
import { XmlConverterService } from '../services/xml-converter.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  hide: boolean = true;
  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(private _auth: AuthService, private _router: Router, private _jwt: JwtDecoderService,
     private _toastr: ToastrService, public validator: ValidatorService, private _xml_parser: XmlConverterService) {
       validator.setForm(this.loginForm);
     }

  ngOnInit(): void {}

  loginUser(): void {
    if (this.loginForm.invalid) {
      return;
    }
    this._auth.loginUser(this.loginForm.value).subscribe(
      (res: any) => {
        let response = this._xml_parser.parseXmlToObject(res);
        localStorage.setItem('token', response.JWTTOKEN[0]);
        const type = this._jwt.getTypeFromToken();
        if (type === 'DOKTOR') {
          this._router.navigate(['/home/doctor']);
        } else if (type === 'PACIJENT') {
          this._router.navigate(['/home/patient/documents']);
        } else {
          this._router.navigate(['/login']);
        }
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }
}
