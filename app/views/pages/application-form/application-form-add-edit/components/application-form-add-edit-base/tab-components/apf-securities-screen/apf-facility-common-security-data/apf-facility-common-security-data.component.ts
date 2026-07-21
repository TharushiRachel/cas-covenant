import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../../../../core/setting/constants";
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-apf-facility-common-security-data',
  templateUrl: './apf-facility-common-security-data.component.html',
  styleUrls: ['./apf-facility-common-security-data.component.scss']
})
export class ApfFacilityCommonSecurityDataComponent implements OnInit {

  @Input('commonFacilitySecurityList') commonFacilitySecurityList: any = [];
  @Output("moveUp") moveUp: EventEmitter<number> = new EventEmitter();
  yesNoConst = Constants.yesNoConst;
  totalCommonSecuritiesCashAmount = 0;
  currency = '';


  constructor(private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.commonFacilitySecurityList.forEach(e => {
      this.totalCommonSecuritiesCashAmount = this.totalCommonSecuritiesCashAmount + e.cashAmount;
      this.currency = e.securityCurrency
    })

  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  moveUpWindow() {
    this.moveUp.emit();
  }

}
