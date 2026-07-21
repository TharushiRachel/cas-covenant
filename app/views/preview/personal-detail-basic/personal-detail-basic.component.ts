import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../core/setting/constants";
import {filter} from 'lodash'
import {CurrencyPipe} from "@angular/common";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ConfirmationDialogComponent} from "../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { SmeServiceService } from '../../pages/sme/service/sme-service.service';

@Component({
  selector: 'app-personal-detail-basic',
  templateUrl: './personal-detail-basic.component.html',
  styleUrls: ['./personal-detail-basic.component.scss']
})
export class PersonalDetailBasicComponent implements OnInit {

  @Input('customer') customer: any = {};
  @Input('updateButtonIsEnabled') updateButtonIsEnabled: boolean = false;
  @Input('facilityPaper') facilityPaper: any;
  @Output('updateCasCustomerData') updateCasCustomerData = new EventEmitter();
  modalRef: MDBModalRef;
  confirmationModalRef: MDBModalRef;

  civilStatus = Constants.civilStatus;
  customerIdentificationType = Constants.customerIdentificationType;
  employmentConst = Constants.employStatusConst;
  constitutionConst = Constants.constitutionConst;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  customerIdentificationTypeConst = Constants.customerIdentificationTypeConst;
  basicInformationType = Constants.basicInformationType;
  constitution = Constants.constitution;
  employStatus = Constants.employStatus;
  addressType = Constants.addressType;
  status = Constants.status;
  statusConst = Constants.statusConst;

  customerTelephoneDTOList = [];
  customerIdentificationDTOList = [];
  customerAddressDTOList = [];
    turnover : any;

  constructor(public currencyPipe: CurrencyPipe,
              private mdbModalService: MDBModalService,
            private SmeService: SmeServiceService) {
  }

  ngOnInit() {
    this.customerTelephoneDTOList = filter(this.customer.casCustomerTelephoneDTOList, (tel) => tel.status == this.statusConst.ACT);
    this.customerIdentificationDTOList = filter(this.customer.casCustomerIdentificationDTOList, (identity) => identity.status == this.statusConst.ACT);
    this.customerAddressDTOList = filter(this.customer.casCustomerAddressDTOList, (address) => address.status == this.statusConst.ACT);

    this.getTurnover();
  }

  isSelfEmployed() {
    return this.customer.employment == this.employmentConst.SELF_EMPLOYED;
  }

  isPersonalCustomer() {
    return this.customer.type == this.basicInformationTypeConst.PERSONAL;
  }

  isBusinessCustomer() {
    return this.customer.type == this.basicInformationTypeConst.BUSINESS;
  }

  isPrivateLTD() {
    return this.customer.constitution == this.constitutionConst.PRIVATE_LTD;
  }

  isCorporateCustomer() {
    return this.customer.type == this.basicInformationTypeConst.CORPORATE;
  }

  setCurrencyFormatValue(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  getTooltipForSyncButton() {
    return `${this.customer.customerFinancialID ? 'Update' : 'Sync'} With Finacle Details`;
  }

  getLabelNameForOfficialAddress() {
    if (this.isCorporateCustomer() || this.isBusinessCustomer()) {
      return 'Registered Address'
    }
    return "Official Address";
  }

  updateWithFinacleData(data) {
    let searchRQ = {
      identificationType: data.identificationType,
      identificationNumber: data.identificationNumber,
      casCustomerID: this.customer.casCustomerID
    };

    this.confirmationModalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p modal-margin-center",
      containerClass: '',
      animated: true,
      data: {
        heading: "Sync Details With Fiancle Data",
        message: `Do you want to ${this.customer.customerFinancialID ? 'update' : 'sync'} customer details with ${this.customerIdentificationType[searchRQ.identificationType]}  ${searchRQ.identificationNumber} ?`,
      }
    });
    this.confirmationModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.updateCasCustomerData.emit(searchRQ);
      }
    });
  }

  getTurnover() {
    const payload: any = {
      custId: this.customer.customerFinancialID,
      facilityPaperId: null
    };
    if (this.facilityPaper && this.facilityPaper.currentFacilityPaperStatus === "APPROVED") {
      payload.facilityPaperId = this.facilityPaper.facilityPaperID;
    }
    
    this.SmeService.smeCustomerTurnover(payload).then((response: any) => {
      console.log("turnover response", response);
      if (response && response.status !== "Failure") {
        this.turnover = {
          currencyCode: response.currencyCode,
          turnover: response.turnover
        };
      } else {
        this.turnover = null;
      }
    }).catch((error) => {
      console.error("turnover api error", error);
      this.turnover = null;
    });
  }

}
