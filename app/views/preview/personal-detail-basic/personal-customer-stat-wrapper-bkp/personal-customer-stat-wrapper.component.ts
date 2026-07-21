import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {ApplicationService} from "../../../../core/service/application/application.service";
import {AppUtils} from "../../../../shared/app.utils";
import * as moment from 'moment';

@Component({
  selector: 'app-personal-customer-stat-wrapper-bkp',
  templateUrl: './personal-customer-stat-wrapper.component.html',
  styleUrls: ['./personal-customer-stat-wrapper.component.scss']
})
export class PersonalCustomerStatWrapperComponentBkp implements OnInit, OnDestroy {
  @Input('customer') customer: any = {};
  selectedTabIndex: any = 0;
  componentForm: FormGroup;
  bankAccountNumbers: any[] = [];
  formErrors: any;
  customerDetails: any = {};
  toDate = moment().subtract(1, "months").endOf('month').format('DD-MM-YYYY');
  fromDate = moment().subtract(12, "months").startOf('month').format('DD-MM-YYYY');

  constructor(private applicationService: ApplicationService) {
  }

  ngOnInit() {
    this.customer.casCustomerBankDetailsDTOList.forEach(bankDetails => {
      if (bankDetails.bankAccountNumber) {
        this.customerDetails.accno = bankDetails.bankAccountNumber;
      }
    });
    this.customerDetails.cumm = this.customer.customerFinancialID;
    if (this.customerDetails.cumm) {
      this.customerDetails.valType = 'C';
    } else {
      this.customerDetails.valType = 'A';
    }

    this.customerDetails.userId = this.applicationService.getLoggedInUserUserID();
    this.customerDetails.aduser = this.applicationService.getLoggedInUserUserName();
    this.customerDetails.refId = this.applicationService.getLoggedInUserUserName();
    this.customerDetails.fromdate = this.fromDate;
    this.customerDetails.todate = this.toDate;
    this.customerDetails = AppUtils.trim(this.customerDetails);
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  ngOnDestroy(): void {
  }

}
