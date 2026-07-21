import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {ApplicationService} from "../../../core/service/application/application.service";
import {AppUtils} from "../../../shared/app.utils";
import * as moment from 'moment';

@Component({
  selector: 'app-personal-customer-stat-wrapper',
  templateUrl: './personal-customer-stat-wrapper.component.html',
  styleUrls: ['./personal-customer-stat-wrapper.component.scss']
})
export class PersonalCustomerStatWrapperComponent implements OnInit, OnDestroy {
  //@Input('customer') customer: any = {};
 // @Input('leadUpdateDTO') leadUpdateDTO: any = {};

  @Input('FormData') formData: any = {};
  @Input('FormType') formType: string = '';
  @Input('facilityPaper') facilityPaper: any ={};

  selectedTabIndex: any = 0;
  componentForm: FormGroup;
  bankAccountNumbers: any[] = [];
  customerBankDetailsDTOList: any[]  = [];
  formErrors: any;
  customerDetails: any = {};
  toDate = moment().subtract(1, "months").endOf('month').format('DD-MM-YYYY');
  fromDate = moment().subtract(12, "months").startOf('month').format('DD-MM-YYYY');
  insuranceRQ:any;
  constructor(private applicationService: ApplicationService) {
  }

  ngOnInit() {
    if (this.formType === 'APPLICATION_FORM') {
      this.customerBankDetailsDTOList =  this.formData.afCustomerDTO.afCustomerBankDetailsDTOList;
    }
    if (this.formType === 'LEAD') {
      this.customerBankDetailsDTOList = this.formData.customerBankDetailsDTOList;
      this.customerDetails.accno = this.formData.customerBankAccountNumber;

    }
    if (this.formType === 'FACILITY_PAPER') {
      this.customerBankDetailsDTOList = this.formData.casCustomerBankDetailsDTOList;
    }
    this.customerBankDetailsDTOList.forEach(bankDetails => {
      if (bankDetails.bankAccountNumber) {
        this.customerDetails.accno = bankDetails.bankAccountNumber;
      }
    });

    this.customerDetails.cumm = this.formData.customerFinancialID;

    if (this.formType === 'APPLICATION_FORM') {
      this.customerDetails.cumm = this.formData.afCustomerDTO.customerFinancialID;
    }

    if(this.formType === 'COVERING'){
      console.log("covering form data",this.formData)
      this.customerDetails.cumm = this.formData.customerFinancialID;
      this.customerDetails.accno = this.formData.accountNumber;
    }

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
    this.insuranceRQ = {facilityPaperID:this.facilityPaper.facilityPaperID , customerFinacleId: this.formData.customerFinancialID}
    
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  ngOnDestroy(): void {
  }


  getCustomerDetails(){
    
      if(this.insuranceRQ.customerFinacleId && this.insuranceRQ.facilityPaperID ){
      
        return this.insuranceRQ
      }
      else{
        this.insuranceRQ.customerFinacleId = null
        this.insuranceRQ.facilityPaperID = null
      }
      
    }
}
