import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CurrencyPipe} from "@angular/common";
import * as _ from "lodash";
import {Constants} from "../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-facilities-common-securities',
  templateUrl: './apf-facilities-common-securities.component.html',
  styleUrls: ['./apf-facilities-common-securities.component.scss']
})
export class ApfFacilitiesCommonSecuritiesComponent implements OnInit {

  @Input('commonFacilitySecurityList') commonFacilitySecurityList: any = [];
  @Input('facilities') facilities: any = [];
  commonFacilities: any = [];
  @Output("moveUp") moveUp: EventEmitter<number> = new EventEmitter();
  totalCommonSecuritiesCashAmount = 0;
  currency = '';

  constructor(private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.commonFacilitySecurityList.forEach(security => {
      this.totalCommonSecuritiesCashAmount = this.totalCommonSecuritiesCashAmount + security.cashAmount;
      this.currency = security.securityCurrency;
      security.afFacilityDTOS = [];
      security.afFacilitySecurityDTOS.forEach(data => {
        if (data.status == 'ACT') {
          let facility = _.find(this.facilities, {facilityID: data.facilityID});
          security.afFacilityDTOS.push(facility);
        }
      });
      this.commonFacilities.push(security);
    });
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  moveUpWindow() {
    this.moveUp.emit();
  }

  getTotalCashAmount(facilities) {
    let securityCashMap = {};
    let securityCashTotal = 0;


    facilities.forEach(e => {
      e.afSecurityDTOList.forEach(s => {
        if (s.isCashSecurity === Constants.yesNoConst.Y) {
          securityCashMap[s.securityID] = s.cashAmount
        }
      })
    });

    for (const [key, value] of Object.entries(securityCashMap)) {
      securityCashTotal = securityCashTotal + (+value);
    }

    return securityCashTotal;
  }

}
