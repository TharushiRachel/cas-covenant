import {FormGroup, ValidationErrors} from '@angular/forms';
import * as _ from 'lodash';

export class PasswordValidator {
  public static validateSamePassword(control: FormGroup): ValidationErrors | null {
    const pw = control.get('password').value;
    const pwConfirm = control.get('passwordConfirm').value;

    if (!_.isEmpty(pw) && pw != pwConfirm) {
      control.get('passwordConfirm').setErrors({passwordMistMatch: true}, {emitEvent: true});
      return {
        passwordMistMatch: true
      };
    } else {
      if (!_.isEmpty(pw)) {
        control.get('password').setErrors(null, {emitEvent: true});
      }
      control.get('passwordConfirm').setErrors(null, {emitEvent: true});
    }
    return null;
  }
}

export class PasswordPatternValidator {

  public static validatePasswordPattern(control: FormGroup): ValidationErrors | null {
    const pw = control.get('password').value;

    if (!_.isEmpty(pw)) {

      let passwordMinLength = 8;
      let passwordMaxLength = 50;
      let hasNumber = /\d/.test(pw);
      let hasUpper = /[A-Z]/.test(pw);
      let hasLower = /[a-z]/.test(pw);
      let reg = new RegExp('(?=.*[ !"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~])');
      let hasSpecialCharacter = reg.test(pw);

      if (!hasNumber) {

        control.get('password').setErrors({needNumber: true}, {emitEvent: true});
        return {
          needNumber: true
        }
      }
      if (!hasUpper) {
        control.get('password').setErrors({needUpperCase: true}, {emitEvent: true});
        return {
          needUpperCase: true
        }
      }

      if (!hasLower) {
        control.get('password').setErrors({needLowerCase: true}, {emitEvent: true});
        return {
          needLowerCase: true
        }
      }

      if (!hasSpecialCharacter) {
        control.get('password').setErrors({needSpecialCharacter: true}, {emitEvent: true});
        return {
          needSpecialCharacter: true
        }
      }

      if (pw.toString().length < passwordMinLength) {
        control.get('password').setErrors({
          needMoreLength: true,
          passwordMinLength: passwordMinLength
        }, {emitEvent: true});
        return {
          needMoreLength: true
        }
      }

      if (pw.toString().length > passwordMaxLength) {
        control.get('password').setErrors({
          needLessThan: true,
          passwordMaxLength: passwordMaxLength
        }, {emitEvent: true});
        return {
          needLessThan: true
        }
      }

    } else {
      if (!_.isEmpty(pw)) {
        control.get('password').setErrors(null, {emitEvent: true});
      }
    }
    return null;
  }
}
