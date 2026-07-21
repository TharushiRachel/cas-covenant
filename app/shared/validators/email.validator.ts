import {AbstractControl, ValidationErrors} from '@angular/forms';

export class EmailValidator {
  public static validateMultipleEmails(control: AbstractControl): ValidationErrors | null {
    let inputValue: string = control.value;
    if (inputValue != '') {
      let splitEmails = inputValue.split(',');
      if (splitEmails.length > 0) {
        for (let i = 0; i < splitEmails.length; i++) {
          if (!EmailValidator.isValidEmail(splitEmails[i].trim())) {
            return {
              invalidEmailInput: true
            };
          }
        }
      } else {
        return {
          invalidEmailInput: true
        };
      }
    }
    return null;
  }

  public static validateEmail(control: AbstractControl): ValidationErrors | null {
    let inputValue: string = control.value;
    if (inputValue != '' && inputValue != null) {
      if (!EmailValidator.isValidEmail(inputValue)) {
        return {
          invalidEmailInput: true
        };
      }
    }
    return null;
  }

  // private static isValidEmail(email: string): boolean {
  //   return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i
  //     .test(email);
  // }

	private static isValidEmail(email: string): boolean {
		return /^(([^<>()[\]\\.,;:#\s@\"]+(\.[^<>()[\]\\.,;:#\s@\"]+)*)|(\".+\"))[a-zA-Z0-9]@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i
			.test(email);
	}

}
