import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidatorService {

  form: FormGroup | null = null;

  constructor() {}

  public setForm(form: FormGroup): void {
    this.form = form;
  }

  validateRequired(control: string): string {
    if (this.form?.get(control)?.hasError('required')) {
      return 'You must enter a value';
    }
    return '';
  }

  validateEmail(control: string): string {
    if (this.form?.get(control)?.hasError('required')) {
      return 'You must enter a value';
    }
    if (this.form?.get(control)?.hasError('email')) {
      return 'Entered email is not valid';
    }
    return '';
  }

  validateMobilePhone(control: string): string {
    if (this.form?.get(control)?.hasError('required')) {
      return 'You must enter a value!';
    }
    if (this.form?.get(control)?.hasError('pattern')) {
      return 'Enter 10 digits!';
    }
    return '';
  }
}
