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
      return 'Morate uneti vrednost!';
    }
    return '';
  }

  validateEmail(control: string): string {
    if (this.form?.get(control)?.hasError('required')) {
      return 'Morate uneti vrednost1';
    }
    if (this.form?.get(control)?.hasError('email')) {
      return 'Email nije validan!';
    }
    return '';
  }

  validatePattern(control: string, phoneFormat: string): string {
    if (this.form?.get(control)?.hasError('required')) {
      return 'Morate uneti vrednost!';
    }
    if (this.form?.get(control)?.hasError('pattern')) {
      return `Zahtevani format ${phoneFormat}`;
    }
    return '';
  }
}
