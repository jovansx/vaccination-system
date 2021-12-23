import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { convertResponseError } from 'src/app/error-converter.function';
import { AuthService } from '../autentification/services/auth.service';
import { ValidatorService } from '../services/validator.service';
@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  hide: boolean = true;
  registrationForm: FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    mobilePhone: new FormControl('', [Validators.required, Validators.pattern("[0-9]{10}")]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(private _auth: AuthService, private _router: Router,
     private _toastr: ToastrService, public validator: ValidatorService) {
       validator.setForm(this.registrationForm);
     }

  ngOnInit(): void {}

  registerUser(): void {
    if (this.registrationForm.invalid) {
      return;
    }
    this._auth.registerUser(this.registrationForm.value).subscribe(
      () => {
        this._router.navigate(['/login']);
        this._toastr.success("You have been successfully registered.", "Success!")
      },
      (err: any) => this._toastr.error(convertResponseError(err), "Don't exist!")
    );
  }
}
