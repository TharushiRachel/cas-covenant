import {AbstractControl, ValidationErrors} from '@angular/forms';

export class MobileNumberValidator {
  public static validateMultipleNumbers(control: AbstractControl): ValidationErrors | null {
    let inputValue: string = control.value;
    if (inputValue != '') {
      let splitNumbers = inputValue.split(',');
      if (splitNumbers.length > 0) {
        for (let i = 0; i < splitNumbers.length; i++) {
          if (!MobileNumberValidator.isValidMobileNumber(splitNumbers[i].trim())) {
            return {
              invalidMobileInput: true
            };
          }
        }
      } else {
        return {
          invalidMobileInput: true
        };
      }
    }
    return null;
  }

  public static validateMobileNumber(control: AbstractControl): ValidationErrors | null {
    let inputValue: string = control.value;
    if (inputValue != '' && inputValue != null) {
      let number = inputValue.trim();
      if (number.length < 10 || !MobileNumberValidator.isValidMobileNumber(number)) {
        return {
          invalidMobileInput: true
        };
      }
    }
    return null;
  }

  private static isValidMobileNumber(input: string): boolean {
    return input.match(/^(?:[+\d].*\d|\d)$/) != null;
  }

}
