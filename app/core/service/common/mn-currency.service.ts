import {Injectable} from '@angular/core';
import {CurrencyPipe} from "@angular/common";

@Injectable()
export class MnCurrencyService {

  constructor(private currencyPipe: CurrencyPipe,) {
  }

  getFormattedAmount(amount) {
    return this.currencyPipe.transform(amount ? amount : 0, '', '', '1.3-3');
  }

  getAmountFromFormattedString(formattedStr) {
    if (isNaN(formattedStr)) {
      return formattedStr.replace(/,/g, '');
    }
    return formattedStr;
  }

  patchFormControllerValue(form, controlName) {
    const amount = this.getAmountFromFormattedString(form.getRawValue()[controlName]);
    form.patchValue({
      [controlName]: this.getFormattedAmount(amount)
    });
  }

}
