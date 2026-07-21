import { CurrencyPipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { AppUtils } from 'src/app/shared/app.utils';
import { CustomerRatingsDto } from 'src/app/views/pages/facility-paper/dto/customer-ratings-dto';

@Component({
  selector: 'app-fp-view-das',
  templateUrl: './fp-view-das.component.html',
  styleUrls: ['./fp-view-das.component.scss']
})
export class FpViewDasComponent implements OnInit, OnDestroy {

  type = "view"

  heading: string;
  content: any;
  facilityPaper: any = {};
  data: any = {}

  riskGrade: string = "-"
  firstClassSecurityTotal: string = "-"
  otherSecurityTotal: string = "-"
  cleanTotal: string = "-"
  grandTotal: string = "-"

  groupFirstClassSecurityTotal: string = "-"
  groupOtherSecurityTotal: string = "-"
  groupCleanTotal: string = "-"
  groupGrandTotal: string = "-"

  modalRef: MDBModalRef;
 committeeTable='committee'
  individualTable = 'individual'

  isCommitteeOpen: boolean = false;
  isIndividualOpen: boolean = false;

  customerRatingsDTO: CustomerRatingsDto = new CustomerRatingsDto({});

  constructor(
    public mdbModalRef: MDBModalRef,
    private mdbModalService: MDBModalService,
    private currencyPipe: CurrencyPipe
  ) { }


  ngOnInit() {
    this.facilityPaper = this.content.facilityPaper;
    this.getRiskGrade();
    this.getCompanyExpousre()
    this.getGroupExpousre()
  }



  ngOnDestroy(): void { }

  getRiskGrade() {

    if (this.facilityPaper != null && this.facilityPaper.casCustomerDTOList[0] != null && this.facilityPaper.casCustomerDTOList[0].customerRatingsDTOList[0] != null
      && this.facilityPaper.casCustomerDTOList[0].customerRatingsDTOList[0].riskGrading) {
      this.riskGrade = this.facilityPaper.casCustomerDTOList[0].customerRatingsDTOList[0].riskGrading;
    }

  }

  getCompanyExpousre() {
    if (this.facilityPaper.fpSecuritySummeryDTO != null && (this.facilityPaper.fpSecuritySummeryDTO.companySubTotalOne != 0 ||
      this.facilityPaper.fpSecuritySummeryDTO.companySubTotalTwo != 0 || this.facilityPaper.fpSecuritySummeryDTO.companyTotal != 0)) {
      this.firstClassSecurityTotal = this.facilityPaper.fpSecuritySummeryDTO.companySubTotalOne.toString()
      this.otherSecurityTotal = (this.facilityPaper.fpSecuritySummeryDTO.companySubTotalTwo - this.facilityPaper.fpSecuritySummeryDTO.companySubTotalOne).toString()
      this.cleanTotal = (this.facilityPaper.fpSecuritySummeryDTO.companyTotal - this.facilityPaper.fpSecuritySummeryDTO.companySubTotalTwo).toString()
      this.grandTotal = this.facilityPaper.fpSecuritySummeryDTO.companyTotal.toString()

    }
  }

  getPercentage(type: string, amount: string): string {
    let percentage;
    if (amount != "-") {
      if (type == "C") {
        percentage = AppUtils.roundUp(((this.getFloatValue(amount) / this.getFloatValue(this.grandTotal)) * 100), 2).toFixed(2) + '%';
      } else {
        percentage = AppUtils.roundUp(((this.getFloatValue(amount) / this.getFloatValue(this.groupGrandTotal)) * 100), 2).toFixed(2) + '%';
      }
    } else {
      percentage = "-"
    }

    return percentage;
  }

  getCurrencyFormat(amount) {
    if (amount == "-") {
      return amount;
    } else {
      return this.currencyPipe.transform(amount, '', '', '1.3-3');
    }
  }

  getFloatValue(value): number {
    let formattedValue = this.getValue(value ? value : '');
    return AppUtils.getFloatValue(formattedValue);
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

  getGroupExpousre() {
    if (this.facilityPaper.fpSecuritySummeryDTO != null && this.facilityPaper.fpSecuritySummeryDTO.facilitySecuritySummaryType == "GROUP" &&
      (this.facilityPaper.fpSecuritySummeryDTO.groupSubTotalOne != 0 || this.facilityPaper.fpSecuritySummeryDTO.groupSubTotalTwo != 0 ||
      this.facilityPaper.fpSecuritySummeryDTO.groupTotal != 0)
    )  {
      this.groupFirstClassSecurityTotal = this.facilityPaper.fpSecuritySummeryDTO.groupSubTotalOne.toString()
      this.groupOtherSecurityTotal = (this.facilityPaper.fpSecuritySummeryDTO.groupSubTotalTwo - this.facilityPaper.fpSecuritySummeryDTO.groupSubTotalOne).toString()
      this.groupCleanTotal = (this.facilityPaper.fpSecuritySummeryDTO.groupTotal - this.facilityPaper.fpSecuritySummeryDTO.groupSubTotalTwo).toString()
      this.groupGrandTotal = this.facilityPaper.fpSecuritySummeryDTO.groupTotal.toString()

    }
  }


  toggleAccordion(accordionType: string) {
    if (accordionType === 'committee') {
      this.isCommitteeOpen = !this.isCommitteeOpen;
    } else if (accordionType === 'individual') {
      this.isIndividualOpen = !this.isIndividualOpen;
    }
  }
  
}
