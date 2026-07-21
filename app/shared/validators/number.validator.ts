import {AbstractControl, FormControl, ValidationErrors, ValidatorFn} from "@angular/forms";
import * as _ from 'lodash';
import {AppUtils} from "../app.utils";

export class NumberValidator {
  public static positiveNumber(control: AbstractControl): ValidationErrors | null {
    if (control.value < 0) {
      return {
        positiveNumberError: true
      };
    }
    return null;
  }

  public static greaterThanZero(control: AbstractControl): ValidationErrors | null {
    if (control.value != null) {
      if (control.value <= 0) {
        return {
          greaterThanZeroError: true
        };
      }
    }

    return null;
  }

  public static greaterThanOrEqualToZero(control: AbstractControl): ValidationErrors | null {
    if (control.value != null) {
      if (control.value < 0) {
        return {
          greaterThanOrEqualZeroError: true
        };
      }
    }

    return null;
  }

  public static maxLength(params: any = {}): ValidationErrors | null {

    return (control: FormControl): { [key: string]: string } => {

      let val: number = control.value;

      // console.log('length', control.value.length);
      // console.log(params);
      return null;
    };
  }

  public static maxLengthOfNumber(params: any = {}): ValidatorFn | null {

    return (control: FormControl): ValidationErrors | null => {

      let val: number = control.value;

      if (control.value != null && control.value != '') {
        let lengthOfVal = (control.value + "").length;
        if (lengthOfVal > params) {
          return {
            hasLengthError: true
          };
        }
      }

      return null;
    };
  }

  public static mobileNumber(control: AbstractControl): ValidationErrors | null {
    let value = control.value;
    if (_.isEmpty(value)) {
      return null;
    }
    let filter = /^\d*(?:\.\d{1,2})?$/;
    if (!filter.test(value) || value.length != 10) {
      return {
        notAValidMobileNumber: true
      }
    }

    return null;
  }

  public static noDecimalPlacesValidator(control: AbstractControl): ValidationErrors | null {
    if (control.value != null && control.value != '') {
      let decimalPlaces = AppUtils.getDecimalPlaces(control.value);
      if (decimalPlaces > 0) {
        return {
          hasDecimalPlacesError: true
        };
      }
    }

    return null;
  }

  public static decimalPlacesValidator(params: any = {}): ValidationErrors | null {

    return (control: FormControl): ValidationErrors | null => {
      if (control.value != null && control.value != '') {
        let decimalPlaces = AppUtils.getDecimalPlaces(control.value);
        if (decimalPlaces > params) {
          return {
            decimalPlacesError: {
              noOfDecimalPlaces: params
            }
          }
        }
      }
      return null;
    };
  }

  public static noOfDecimalPlacesValidator(params: any = {}): ValidatorFn | null {

    return (control: FormControl): ValidationErrors | null => {
      if (control.value != null && control.value != '') {
        let decimalPlaces = AppUtils.getDecimalPlaces(control.value);
        if (decimalPlaces > params) {
          return {
            decimalPlacesError: {
              noOfDecimalPlaces: params
            }
          }
        }
      }
      return null;
    };
  }

  public static isCommaSeparatedValue(control: AbstractControl): ValidationErrors | null {
    if (control.value != null) {
      let reg = new RegExp('^[-+]?(?:[0-9]+,)*[0-9]+(?:\\.[0-9]+)?$');
      let isCommaSeparatedValue = reg.test(control.value);
      if (!isCommaSeparatedValue && control.value.length > 0) {
        return {
          isCommaSeparatedValueError: true
        };
      }
    }
    return null;
  }

  public static isPercentageValue(control: AbstractControl): ValidationErrors | null {
    if (control.value != null) {
      let reg = new RegExp('^[0-9]\\d*(\\.\\d+)?$');
      let iValidValue = reg.test(control.value);
      if (!iValidValue || control.value < 0 || control.value > 100) {
        return {
          percentageValueError: true
        };
      }
    }
    return null;
  }

  public static isPercentageValueWithMinus(control: AbstractControl): ValidationErrors | null {
    if (control.value != null) {
      let reg = new RegExp('^[-0-9]\\d*(\\.\\d+)?$');
      let iValidValue = reg.test(control.value);
      if (!iValidValue || control.value > 100 || control.value < -100) {
        return {
          percentageValueError: true
        };
      }
    }
    return null;
  }

  public static isNumber(control: AbstractControl): ValidationErrors | null {
    if (control.value != null) {
      let reg = new RegExp('^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$');
      let iValidValue = reg.test(control.value);
      if (!iValidValue) {
        return {
          isNumberError: true
        };
      }
    }
    return null;
  }

  public static validateNumber(event) {
    const keyCode = event.keyCode;

    const excludedKeys = [8, 9, 13, 37, 39, 46, 190, 188, 110];

    if (!((keyCode >= 48 && keyCode <= 57) ||
      (keyCode >= 96 && keyCode <= 105) ||
      (excludedKeys.includes(keyCode)))) {
      event.preventDefault();
    }
  }

}
