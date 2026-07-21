import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
import * as _ from "lodash";

@Component({
  selector: 'app-personal-detail-bank-bkp',
  templateUrl: './personal-detail-bank.component.html',
  styleUrls: ['./personal-detail-bank.component.scss']
})
export class PersonalDetailBankComponentBkp implements OnInit {

  @Input('customer') customer: any = {};
  tableColumnsBankDetails = ['Account No', 'Status', 'Load'];
  status = Constants.status;
  statusConst = Constants.statusConst;

  fpCustomerAccountList = "fpCustomerAccountList";
  resizedCustomerAccountList = [];
  count = 0;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {
  }

  ngOnInit() {
    if (this.customer.casCustomerBankDetailsDTOList.length > 3) {
      for (this.count = 0; this.count < 3; this.count++) {
        this.resizedCustomerAccountList.push(this.customer.casCustomerBankDetailsDTOList[this.count])
      }
    } else {
      if (this.customer.casCustomerBankDetailsDTOList.length > 0) {
        for (this.count = 0; this.count < this.customer.casCustomerBankDetailsDTOList.length; this.count++) {
          this.resizedCustomerAccountList.push(this.customer.casCustomerBankDetailsDTOList[this.count])
        }
      }
    }
  }

  loadFacilities(accountNo, financialNo) {

    let data = Object.assign({},
      {customerFinancialID: financialNo},
      {bankAccountNumber: accountNo});

    this.facilityPaperAddEditService.getFacilityDetails(data);
  }

  onLoadResizedList(listFromEvent) {
    if (listFromEvent.outputArrayType === this.fpCustomerAccountList) {
      this.resizedCustomerAccountList = [];
      _.forEach(listFromEvent.outputArray, item => {
        this.resizedCustomerAccountList.push(item)
      })

    }
  }

}
