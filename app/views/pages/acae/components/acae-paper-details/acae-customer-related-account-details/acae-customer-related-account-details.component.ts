import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import * as _ from "lodash";
import { Subscription } from "rxjs";
import { CurrencyPipe } from "@angular/common";
import * as moment from 'moment';
import { CasCustomerService } from "../../../../../../core/service/data/cas-customer.service";

@Component({
  selector: 'app-acae-customer-related-account-details-bkp',
  templateUrl: './acae-customer-related-account-details.component.html',
  styleUrls: ['./acae-customer-related-account-details.component.scss']
})
export class ACAECustomerRealatedAccountComponentBkp implements OnInit, OnDestroy {

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
    // this.getRealatedAccountDetails(this.customerDetails)
  }

  ngOnChanges(changes: SimpleChanges): void {
    let cusDetails = changes["customerDetails"]["currentValue"]
    this.getRealatedAccountDetails(cusDetails)
  }

  getRealatedAccountDetails(customerDetails: any) {
    let request = Object.assign({}, customerDetails);
    this.casCustomerService.getCustomerAccountStatistic(request).subscribe((res: any) => {
      if (!_.isEmpty(res)) {
        this.accountStatResponse = res.accountStatResponseDTOArrayList;
        this.accStatResp = res.accStatResponsesDTOArrayList;
        if (this.accStatResp ? !this.accStatResp.length : !this.accStatResp) {
          this.isDataAvailable = false;
        } else {
          this.isDataAvailable = true
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
