import {AbstractControl, ValidationErrors} from '@angular/forms';

export class StringValidator {
  public static validateLeadingSpace(control: AbstractControl): ValidationErrors | null {
    if (control.value < 0) {
      return {
        positiveNumberError: true
      };
    }
    return null;
  }

  public static noSpace(control: AbstractControl): ValidationErrors | null {
    if (/\s/.test(control.value)) {
      return {
        noSpace: true
      };
    }
    return null;
  }

}
