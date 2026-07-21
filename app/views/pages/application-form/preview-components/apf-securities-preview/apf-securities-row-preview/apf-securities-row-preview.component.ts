import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../shared/app.utils";
import {CurrencyPipe} from "@angular/common";
import * as _ from "lodash";

@Component({
  selector: 'app-apf-securities-row-preview',
  templateUrl: './apf-securities-row-preview.component.html',
  styleUrls: ['./apf-securities-row-preview.component.scss']
})
export class ApfSecuritiesRowPreviewComponent implements OnInit {

  @Input("securities") securities: any[] = [];
  yesNoConst = Constants.yesNoConst;
  organizedSecurityList: any[] = [];
  currency = '';
  totalIndividualSecuritiesCashAmount = 0;

  constructor(private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {

    let commonSecurities = [];
    let individualSecurities = [];
    this.securities.forEach(security => {
      if (security.isCommonSecurity == Constants.yesNoConst.Y) {
        commonSecurities.push(security);
      } else {
        individualSecurities.push(security);
      }
    });

    this.organizedSecurityList = _.concat(_.sortBy(_.uniqBy(individualSecurities, 'securityID'), 'securityID'), _.sortBy(_.uniqBy(commonSecurities, 'securityID'), 'securityID'));
    _.uniqBy(individualSecurities, 'securityID').forEach(e => {
      this.totalIndividualSecuritiesCashAmount = this.totalIndividualSecuritiesCashAmount + e.cashAmount;
      this.currency = e.securityCurrency;
    });

  }

  toContent(id) {
    document.getElementById(id).scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      }
    );
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '', '1.3-3')
    }
  }

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

}
