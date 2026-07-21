import { Component, Input, OnInit } from '@angular/core';
import { CustomerBankDetails } from '../interface/customer-bank-details';
import { CoveringApprovalService } from 'src/app/views/pages/covering-approval/services/covering-approval.service';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  selector: 'app-view-covenant-account-details',
  templateUrl: './view-covenant-account-details.component.html',
  styleUrls: ['./view-covenant-account-details.component.scss']
})
export class ViewCovenantAccountDetailsComponent implements OnInit {

  //@Input() acctId: string;
  bankDetails: CustomerBankDetails;
  content: any;

  constructor(
    private coveringApprovalService: CoveringApprovalService,
    public mdbModalRef: MDBModalRef,
    private currencyPipe: CurrencyPipe,
    private datePipe: DatePipe
  ) { }

  ngOnInit() {
    if (this.content.acctId) {
      this.coveringApprovalService.getCustomerBankDetails(this.content.acctId).then((res) => {
        console.log("res", res); 
        this.bankDetails = res;     
      });
    }
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  formatToDateString(dateStr: string): string | null {
    if (!dateStr) return null;
    return dateStr.split(' ')[0] || null; // returns '16-08-2005'
  }
  
}
