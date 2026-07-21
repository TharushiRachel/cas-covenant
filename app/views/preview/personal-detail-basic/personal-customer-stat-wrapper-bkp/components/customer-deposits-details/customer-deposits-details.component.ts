import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import * as _ from "lodash";
import {CurrencyPipe} from "@angular/common";
import * as moment from 'moment';
import {AppUtils} from "../../../../../../shared/app.utils";
import {Subscription} from "rxjs";
import {CasCustomerService} from "../../../../../../core/service/data/cas-customer.service";

@Component({
  selector: 'app-customer-deposits-details-bkp',
  templateUrl: './customer-deposits-details.component.html',
  styleUrls: ['./customer-deposits-details.component.scss']
})
export class CustomerDepositsDetailsComponentBkp implements OnInit, OnDestroy {

  @Input('customerDetails') customerDetails: any = {};
  customerDepositDetails: any[] = [];
  sanclimTotal: number = 0;
  clrBalAmtTotal: number = 0;
  isDataAvailable: boolean = true;
  date = moment().format('DD-MM-YYYY');
  onCustomerDepositDetailsChangeSubs = new Subscription();

  constructor(private currencyPipe: CurrencyPipe,
              private casCustomerService: CasCustomerService) {
  }

  ngOnInit() {

    this.casCustomerService.getCustomerDepositDetails(this.customerDetails).subscribe((response: any) => {
      if (!_.isEmpty(response)) {
        this.customerDepositDetails = response.depositDetailDTOS;
        if (this.customerDepositDetails) {
          this.customerDepositDetails.forEach(data => {
            this.sanclimTotal = this.sanclimTotal + parseFloat(data.sanclim);
            this.clrBalAmtTotal = this.clrBalAmtTotal + parseFloat(data.clrBalAmt);

          })
        }
      }

      if (this.customerDepositDetails ? !this.customerDepositDetails.length : !this.customerDepositDetails) {
        this.isDataAvailable = false;
      }

    });
  }

  ngOnDestroy(): void {
    this.onCustomerDepositDetailsChangeSubs.unsubscribe();
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  startCaseFormat(value) {
    return AppUtils.startCaseFormat(value);
  }

}
