import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../autentification/services/auth.service';
import { ValidatorService } from '../services/validator.service';
import { convertResponseError } from 'src/app/error-converter.function';
import { ToastrService } from 'ngx-toastr';
import { XmlConverterService } from '../services/xml-converter.service';
import { JwtDecoderService } from '../autentification/services/jwt-decoder.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide: boolean = true;
  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(private _auth: AuthService, public validator: ValidatorService, private _toastr: ToastrService,
    private _xml_parser: XmlConverterService, private _jwt: JwtDecoderService, 
    private _router: Router) { 
    validator.setForm(this.loginForm);
  }

  ngOnInit(): void {
  }

  loginUser(): void {
    if (this.loginForm.invalid) {
      return;
    }
    this._auth.loginUser(this.loginForm.value).subscribe(
      (res: any) => {
        let response = this._xml_parser.parseXmlToObject(res);
        localStorage.setItem('token', response.JWTTOKEN[0]);
        this._router.navigate(['/home/vaccines']);
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

}
