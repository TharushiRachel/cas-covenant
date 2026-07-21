import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../../core/setting/constants";
import {FormGroup} from "@angular/forms";
import {CurrencyService} from "../../../../../../../../core/service/common/currency.service";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {Subscription} from "rxjs";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-bcc-cost-of-funds',
  templateUrl: './bcc-cost-of-funds.component.html',
  styleUrls: ['./bcc-cost-of-funds.component.scss']
})
export class BccCostOfFundsComponent implements OnInit, OnDestroy {

  @Input() costOfFunds: FormGroup;
  monthsOptions = Constants.monthOptionSelect;
  yearOptions = this.generateYears();
  formErrors: any;
  formChangeSub = new Subscription();

  constructor(public currencyService: CurrencyService) {
  }

  ngOnInit() {

    this.formErrors = {
      monthlyCostOfFundsLkr: {},
      monthlyCostOfFundsFcy: {},
      cumulativeCostOfFundsLkr: {},
      cumulativeCostOfFundsFcy: {},
      incrementalCostOfFundsLkr: {},
      incrementalCostOfFundsFcy: {},
    };

    this.formChangeSub.unsubscribe();
    this.formChangeSub = this.costOfFunds.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.costOfFunds, this.formErrors);
    });

  }

  generateYears() {
    let year = new Date().getFullYear();
    let min = year - 10;
    let max = year + 10;
    let years = [];
    for (var i = min; i <= max; i++) {
      years.push({value: i, label: i});
    }
    return years;
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

  ngOnDestroy(): void {
    this.formChangeSub.unsubscribe();
  }


}
