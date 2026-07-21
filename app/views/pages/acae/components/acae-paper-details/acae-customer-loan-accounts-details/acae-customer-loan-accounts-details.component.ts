import { Component, Input, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import * as _ from "lodash";
import { Subscription } from "rxjs";
import { CurrencyPipe } from "@angular/common";
import { AppUtils } from 'src/app/shared/app.utils';
import { ACAEPaperService } from '../../../services/acae-paper.service';

@Component({
  selector: 'app-acae-customer-loan-accounts-details-bkp',
  templateUrl: './acae-customer-loan-accounts-details.component.html',
  styleUrls: ['./acae-customer-loan-accounts-details.component.scss']
})
export class ACAECustomerLoanAccountComponentBkp implements OnInit, OnDestroy {

  @Input('customerDetails') customerDetails: any = {};
  onCustomerFacilitiesChange = new Subscription();
  facilities: any[] = [];
  totalSanctionLimit: number = 0;
  totalOutstandingAmount: number = 0;
  isDataAvailable: boolean = true;
  date: string;
  loanAccountLoading: boolean = false;

  constructor(
    private currencyPipe: CurrencyPipe,
    private acaePaperService: ACAEPaperService
  ) {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges): void {
    let cusDetails = changes["customerDetails"]["currentValue"]
    this.getLoanAccountDetails(cusDetails)
  }

  getLoanAccountDetails(customerDetails: any) {
    this.loanAccountLoading = true;
    this.facilities = [];
    this.acaePaperService.getACAELoanAccountsDetails(customerDetails).subscribe((response: any) => {
      if (!_.isEmpty(response)) {
        this.facilities = response.advancedPortfolioDTOS;
        if (this.facilities ? !this.facilities.length : !this.facilities) {
        }
        if (this.facilities) {
          this.facilities.forEach(facilities => {
            this.totalOutstandingAmount = this.totalOutstandingAmount + parseFloat(facilities.outstandingAomunt);
            this.totalSanctionLimit = this.totalSanctionLimit + parseFloat(facilities.sanctionLimit);
          });
        }
        if (this.facilities.length > 0) {
          this.isDataAvailable = true;
        } else {
          this.isDataAvailable = false;
        }
      } else {
        this.isDataAvailable = false;
      }
      this.loanAccountLoading = false;
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
