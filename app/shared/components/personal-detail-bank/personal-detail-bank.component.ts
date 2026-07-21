import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../core/setting/constants";
//import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
//import {LeadUpdateDto} from "../../dto/lead-update-dto";
import * as _ from "lodash";

@Component({
  selector: 'app-personal-detail-bank',
  templateUrl: './personal-detail-bank.component.html',
  styleUrls: ['./personal-detail-bank.component.scss']
})
export class PersonalDetailBankComponent implements OnInit {

  //@Input('applicationForm') applicationForm: any = {};

  @Input('FormData') formData: any = {};
  @Input('FormType') formType: string = '';



  tableColumnsBankDetails = ['Account No', 'Status', 'Currency Code', 'Branch Code', 'Scheme Code', 'Scheme Type'];
  status = Constants.status;
  statusConst = Constants.statusConst;

 // apfCustomerAccountList = "apfCustomerAccountList";
  resizedCustomerAccountList = [];
  customerBankDetailsDTOList = [];
  count = 0;

  constructor(
    //private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {
  }



  ngOnInit() {

    if (this.formType === 'APPLICATION_FORM') {
      this.customerBankDetailsDTOList =  this.formData.basicInformationDTOList[0].afCustomerDTO.afCustomerBankDetailsDTOList;
    }
    if (this.formType === 'LEAD') {
      this.customerBankDetailsDTOList = this.formData.customerBankDetailsDTOList;
    }
    if (this.formType === 'FACILITY_PAPER') {
      this.customerBankDetailsDTOList = this.formData.casCustomerBankDetailsDTOList;
    }

    if (this.customerBankDetailsDTOList.length > 3) {
      for (this.count = 0; this.count < 3; this.count++) {
        this.resizedCustomerAccountList.push(this.customerBankDetailsDTOList[this.count])
      }
    } else {
      if (this.customerBankDetailsDTOList.length > 0) {
        for (this.count = 0; this.count < this.customerBankDetailsDTOList.length; this.count++) {
          this.resizedCustomerAccountList.push(this.customerBankDetailsDTOList[this.count])
        }
      }
    }
  }

 /* loadFacilities(accountNo, financialNo) {

    let data = Object.assign({},
      {customerFinancialID: financialNo},
      {bankAccountNumber: accountNo});

    this.facilityPaperAddEditService.getFacilityDetails(data);
  }*/

  onLoadResizedList(listFromEvent) {
    //if (listFromEvent.outputArrayType === this.apfCustomerAccountList) {
      this.resizedCustomerAccountList = [];
      _.forEach(listFromEvent.outputArray, item => {
        this.resizedCustomerAccountList.push(item)
      })

   // }
  }

}
