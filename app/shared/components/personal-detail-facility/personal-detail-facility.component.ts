import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {CurrencyPipe} from "@angular/common";
import {AppUtils} from "../../../shared/app.utils";
import {CasCustomerService} from "../../../core/service/data/cas-customer.service";
import {ApplicationService} from "../../../core/service/application/application.service";

@Component({
  selector: 'app-personal-detail-facility',
  templateUrl: './personal-detail-facility.component.html',
  styleUrls: ['./personal-detail-facility.component.scss']
})
export class PersonalDetailFacilityComponent implements OnInit, OnDestroy {

  //@Input('leadUpdateDTO') leadUpdateDTO: any = {};
 //  @Input('applicationForm') applicationForm: any = {};
   @Input('FormData') formData: any = {};
   @Input('FormType') formType: string = '';

  facilityDetails: any = [];
  leadCustomerFacilities = "apfCustomerFacilities";
  resizedLeadCustomerFacilitiesList: any = [];
  onCustomerFacilitiesChange = new Subscription();
  isDataAvailable: boolean = true;
  customerBankDetailsDTOList = [];

  count = 0;
  pageSize = 10;

  tableColumns = ['Facility Name', 'Account No', 'Sanction Limit', 'Clear Balance Amount', 'Account Currency Code', 'Sub Classification User',
    'Scheme Code', 'Scheme Desc', 'Clean Emer Advn', 'Acct Poa As Ewc Type', 'Customer Reltn Code'];

  constructor(private currencyPipe: CurrencyPipe,
              private casCustomerService: CasCustomerService,
              private applicationService: ApplicationService
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

     this.casCustomerService.getCustomerFacilityDetailsByAccountNumber(this.getCustomerDetails(this.customerBankDetailsDTOList)).subscribe((response: any) => {
          if (!_.isEmpty(response)) {
            if (response.advancedPortfolioDTOS) {
              this.facilityDetails = response.advancedPortfolioDTOS;
              if (this.facilityDetails ? !this.facilityDetails.length : !this.facilityDetails) {
                this.isDataAvailable = false
              }
            }
            this.resizedLeadCustomerFacilitiesList = this.facilityDetails.slice(0, this.pageSize);
          }
        });
  }

  ngOnDestroy(): void {
    this.onCustomerFacilitiesChange.unsubscribe();
  }

  onLoadResizedList(listFromEvent) {
    this.resizedLeadCustomerFacilitiesList = listFromEvent.outputArray;
  }

  startCaseFormat(value) {
    return AppUtils.startCaseFormat(value);
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  getCustomerDetails(customerBankDetailsDTOList) {
    let customerDetails: any = {};

    customerBankDetailsDTOList.forEach(bankDetails => {
      if (bankDetails.bankAccountNumber) {
        customerDetails.accno = bankDetails.bankAccountNumber;
      }
    });
   /* customerDetails.cumm = customer.customerFinancialID;
    customerDetails.valType = 'A';
    if (customerDetails.cumm) {
      customerDetails.valType = 'C';
    } else {
      customerDetails.valType = 'A';
    }*/
    customerDetails.userId = this.applicationService.getLoggedInUserUserID();
    customerDetails.aduser = this.applicationService.getLoggedInUserUserName();
    customerDetails.refId = this.applicationService.getLoggedInUserUserName();

    return customerDetails;
  }

}
