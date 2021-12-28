import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../autentification/services/auth.service';
import { ValidatorService } from '../services/validator.service';
import { convertResponseError } from 'src/app/error-converter.function';
import { ToastrService } from 'ngx-toastr';

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

  constructor(private _auth: AuthService, public validator: ValidatorService, private _toastr: ToastrService) { 
    validator.setForm(this.loginForm);
  }

  ngOnInit(): void {
    console.log("bla")
  }

  loginUser(): void {
    if (this.loginForm.invalid) {
      return;
    }
    this._auth.loginUser(this.loginForm.value).subscribe(
      (res: any) => {
        localStorage.setItem('token', res.token);
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }

}
