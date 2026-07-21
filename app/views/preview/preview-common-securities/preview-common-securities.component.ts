import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CurrencyPipe} from "@angular/common";
import {Constants} from "../../../core/setting/constants";
import * as _ from "lodash";
import { AppUtils } from 'src/app/shared/app.utils';

@Component({
  selector: 'app-preview-common-securities',
  templateUrl: './preview-common-securities.component.html',
  styleUrls: ['./preview-common-securities.component.scss']
})
export class PreviewCommonSecuritiesComponent implements OnInit {

  @Input('commonFacilitySecurityList') commonFacilitySecurityList: any = [];
  @Input('facilityPaper') facilityPaper: any = {};
  @Output("moveUp") moveUp: EventEmitter<number> = new EventEmitter();

  currencyViseTotalIndividualCashAmount = {};
  commonCashAmounts = [];

  currencyViseTotalCashAmount = {};
  cashSecurities = [];
  cashAmounts = [];
  isCommittee:boolean = false;

  constructor(private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    if(this.facilityPaper){
      this.isCommittee = this.facilityPaper.isCommittee == Constants.yesNoConst.Y;
    }
    
    //the following calculation for common securities
    this.commonFacilitySecurityList.forEach(e => {
      if (e.isCashSecurity == Constants.yesNoConst.Y) {
        if (_.isNumber(this.currencyViseTotalIndividualCashAmount[e.securityCurrency])) {
          this.currencyViseTotalIndividualCashAmount[e.securityCurrency] = this.currencyViseTotalIndividualCashAmount[e.securityCurrency] + e.cashAmount;
        } else {
          this.currencyViseTotalIndividualCashAmount[e.securityCurrency] = e.cashAmount ? e.cashAmount : 0;
        }
      }
    });

    this.commonCashAmounts = [];
    Object.entries(this.currencyViseTotalIndividualCashAmount).forEach(([key, value]) => {
      this.commonCashAmounts.push({currency: key, amount: value})
    });

    //the following calculation for all the securities
    this.facilityPaper.facilityDTOList.forEach(e => {
      e.facilitySecurityDTOList.forEach(s => {
        if (s.isCashSecurity === Constants.yesNoConst.Y) {
          this.cashSecurities.push(s);
        }
      })
    });

    _.uniqBy(this.cashSecurities, 'facilitySecurityID').forEach((e: any) => {
      if (_.isNumber(this.currencyViseTotalCashAmount[e.securityCurrency])) {
        this.currencyViseTotalCashAmount[e.securityCurrency] = this.currencyViseTotalCashAmount[e.securityCurrency] + e.cashAmount;
      } else {
        this.currencyViseTotalCashAmount[e.securityCurrency] = e.cashAmount ? e.cashAmount : 0;
      }
    });

    this.cashAmounts = [];
    Object.entries(this.currencyViseTotalCashAmount).forEach(([key, value]) => {
      this.cashAmounts.push({currency: key, amount: value})
    })
  }

  getFormattedValue(amount:any) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '', '1.3-3')
    }
  }
  
  getMillionValue(value:any) {
    return AppUtils.getMillionValue(value);
  }
  
  moveUpWindow() {
    this.moveUp.emit();
  }
}
