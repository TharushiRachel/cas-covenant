import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {CurrencyService} from "../../../../../../../../core/service/common/currency.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-bcc-strengths',
  templateUrl: './bcc-strengths.component.html',
  styleUrls: ['./bcc-strengths.component.scss']
})
export class BccStrengthsComponent implements OnInit, OnDestroy {

  @Input() strengths: FormGroup;

  financialStabilityOptionSelect = Constants.financialStabilityOptionSelect;
  financialYearsOptionSelect;
  formErrors: any;
  formChangeSub = new Subscription();

  constructor(public currencyService: CurrencyService) {

  }

  ngOnInit() {
    this.financialYearsOptionSelect = this.generateFinancialYears();
  }

  generateFinancialYears() {
    let list = [];
    let startingYear = new Date().getFullYear() - 15;
    for (let i = 0; i < 30; i++) {
      startingYear = startingYear + 1;
      let value = startingYear.toString() + "/" + (startingYear + 1).toString();
      list.push({value: value, label: value});
    }
    return list;
  }

  ngOnDestroy(): void {
    this.formChangeSub.unsubscribe();
  }

  validateNumber(event){
    NumberValidator.validateNumber(event);
  }

}
