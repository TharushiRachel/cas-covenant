import {AbstractControl, ValidationErrors} from "@angular/forms";
import {AppUtils} from "../app.utils";


export class NicValidator {
  public static isValidNICInput(control: AbstractControl): ValidationErrors | null {
    if (control.value != null) {
      if (!AppUtils.isNic(control.value)) {
        return {
          isNICError: true
        };
      }
    }
    return null;
  }
}
