import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import * as _ from "lodash";
import {CurrencyPipe} from "@angular/common";
import {Subscription} from "rxjs";
import * as moment from 'moment';
import {AppUtils} from "../../../../../shared/app.utils";
import {CasCustomerService} from "../../../../../core/service/data/cas-customer.service";

@Component({
  selector: 'app-customer-advance-details',
  templateUrl: './customer-advance-details.component.html',
  styleUrls: ['./customer-advance-details.component.scss']
})
export class CustomerAdvanceDetailsComponent implements OnInit, OnDestroy {

  @Input('customerDetails') customerDetails: any = {};
  onCustomerFacilitiesChange = new Subscription();
  guaranteeSummary: any[] = [];
  detailGuarantees: any[] = [];
  facilities: any[] = [];
  totalSanctionLimit: number = 0;
  totalOutstandingAmount: number = 0;
  isDataAvailable: boolean = true;
  date = moment().format('DD-MM-YYYY');


  constructor(private currencyPipe: CurrencyPipe,
              private casCustomerService: CasCustomerService) {
  }

  ngOnInit() {

    this.casCustomerService.getCustomerFacilityDetailsByAccountNumber(this.customerDetails).subscribe((response: any) => {
      if (!_.isEmpty(response)) {
        this.facilities = response.advancedPortfolioDTOS;
        if (this.facilities ? !this.facilities.length : !this.facilities) {
          this.isDataAvailable = false
        }

        if (this.facilities) {
          this.facilities.forEach(facilities => {
            this.totalOutstandingAmount = this.totalOutstandingAmount + parseFloat(facilities.outstandingAomunt);
            this.totalSanctionLimit = this.totalSanctionLimit + parseFloat(facilities.sanctionLimit);
          })
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.onCustomerFacilitiesChange.unsubscribe();
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  startCaseFormat(value) {
    return AppUtils.startCaseFormat(value);
  }


}
