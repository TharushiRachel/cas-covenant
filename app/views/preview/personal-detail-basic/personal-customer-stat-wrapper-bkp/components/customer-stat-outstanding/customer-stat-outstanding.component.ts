import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {CurrencyPipe} from "@angular/common";
import * as moment from 'moment';
import {CasCustomerService} from "../../../../../../core/service/data/cas-customer.service";

@Component({
  selector: 'app-customer-stat-outstanding-bkp',
  templateUrl: './customer-stat-outstanding.component.html',
  styleUrls: ['./customer-stat-outstanding.component.scss']
})
export class CustomerStatOutstandingComponentBkp implements OnInit, OnDestroy {

  onCustomerStatisticChangeSub = new Subscription();
  accountStatResponse: any = [];
  accStatResp: any = [];
  @Input('customerDetails') customerDetails: any = {};
  isDataAvailable: boolean = true;
  date = moment().format('DD-MM-YYYY');

  constructor(private currencyPipe: CurrencyPipe,
              private casCustomerService: CasCustomerService) {
  }

  ngOnInit() {

    this.casCustomerService.getCustomerAccountStatistic(this.customerDetails).subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.accountStatResponse = res.accountStatResponseDTOArrayList;
        this.accStatResp = res.accStatResponsesDTOArrayList;
        if (this.accStatResp ? !this.accStatResp.length : !this.accStatResp) {
          this.isDataAvailable = false;
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.onCustomerStatisticChangeSub.unsubscribe();
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

}
