import {FormGroup, ValidationErrors} from '@angular/forms';
import * as _ from 'lodash';
import {AppUtils} from "../app.utils";
import {Constants} from "../../core/setting/constants";

export class IdentificationNumberValidator {


  public static validateIdentificationNumber(control: FormGroup): ValidationErrors | null {
    const customerIdentificationTypeConst = Constants.customerIdentificationTypeConst;
    const identificationType = control.get('identificationType').value;
    const identificationNumber = control.get('identificationNumber').value;

    if (!_.isEmpty(identificationType) && !_.isEmpty(identificationNumber)) {

      if (identificationType == customerIdentificationTypeConst.NIC) {
        if (!AppUtils.isNic(identificationNumber)) {
          control.get('identificationNumber').setErrors({invalidNic: true}, {emitEvent: true});
          return null;
        } else {
          control.get('identificationNumber').setErrors(null, {emitEvent: true});
          return null;
        }
      }

      if (identificationType == customerIdentificationTypeConst.BRC) {
        // if (!AppUtils.isBRN(identificationNumber)) {
        if (!(identificationNumber && identificationNumber.trim() != "")) {
          control.get('identificationNumber').setErrors({invalidBrn: true}, {emitEvent: true});
          return null;
        } else {
          control.get('identificationNumber').setErrors(null, {emitEvent: true});
          return null;
        }
      }

      if (identificationType == customerIdentificationTypeConst.PP) {
        // if (!AppUtils.isPassport(identificationNumber)) {
        if (!(identificationNumber && identificationNumber.trim() != "")) {
          control.get('identificationNumber').setErrors({invalidPassport: true}, {emitEvent: true});
          return {
            invalidPassport: true
          };
        } else {
          control.get('identificationNumber').setErrors(null, {emitEvent: true});
          return null;
        }
      }

    } else {

      if (!_.isEmpty(identificationNumber)) {
        control.get('identificationNumber').setErrors(null, {emitEvent: true});
      }
    }
    return null;
  }
}
