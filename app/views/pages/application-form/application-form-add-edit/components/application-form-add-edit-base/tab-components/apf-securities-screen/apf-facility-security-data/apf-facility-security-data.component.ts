import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {ApplicationService} from "../../../../../../../../../core/service/application/application.service";
import {CurrencyPipe} from "@angular/common";
import * as _ from "lodash";
import {ConfirmationDialogComponent} from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import {AppUtils} from "../../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-apf-facility-security-data',
  templateUrl: './apf-facility-security-data.component.html',
  styleUrls: ['./apf-facility-security-data.component.scss']
})
export class ApfFacilitySecurityDataComponent implements OnInit {

  @Input('facilityData') facilityData: any = {};
  @Input("lastIndex") lastIndex;
  @Input("currentIndex") currentIndex;
  @Output("setTabIndex") setTabIndex: EventEmitter<number> = new EventEmitter();
  @Output("onAddEditSecurityDetails") onAddEditSecurityDetails = new EventEmitter();
  @Output("toCommonSecurityContent") toCommonSecurityContent: EventEmitter<number> = new EventEmitter();
  @Output("addSecurityThroughFacility") addSecurityThroughFacility: EventEmitter<any> = new EventEmitter();

  modalRef: MDBModalRef;
  count: number = 0;
  organizedSecurityList: any[] = [];
  yesNoConst = Constants.yesNoConst;
  totalIndividualSecuritiesCashAmount = 0;
  currency = '';

  constructor(private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private applicationService: ApplicationService,
              private currencyPipe: CurrencyPipe,) {
  }

  ngOnInit() {

    let commonSecurities = [];
    let individualSecurities = [];
    this.facilityData.afSecurityDTOList.forEach(security => {
      if (security.isCommonSecurity == Constants.yesNoConst.Y) {
        commonSecurities.push(security);
      } else {
        individualSecurities.push(security);
      }
    });
    this.organizedSecurityList = _.concat(_.sortBy(_.uniqBy(individualSecurities, 'securityID'), 'securityID'), _.sortBy(_.uniqBy(commonSecurities, 'securityID'), 'securityID'));
    _.uniqBy(individualSecurities, 'securityID').forEach(e => {
      this.totalIndividualSecuritiesCashAmount = this.totalIndividualSecuritiesCashAmount + e.cashAmount;
      this.currency = e.securityCurrency;
    });

  }

  onRemoveFacilitySecurity(facilityData, securityItem) {

    let linkedActiveFacilitiesCount = 0;
    securityItem.afFacilitySecurityDTOS.forEach(data => {
      if (data.status == Constants.statusConst.ACT) {
        linkedActiveFacilitiesCount++;
      }
    });

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        message: "Do you want to remove security " + securityItem.securityCode + " from facility " + facilityData.facilityRefCode + " ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let facilitySecurityID = null;

        securityItem.afFacilitySecurityDTOS.forEach((facilitySecurity: any) => {
          if (facilitySecurity.facilityID == facilityData.facilityID) {
            facilitySecurityID = facilitySecurity.facilitySecurityID;
          }
        });

        let facilitySecurityDTO = {
          facilityID: facilityData.facilityID,
          securityID: securityItem.securityID,
          facilitySecurityID: facilitySecurityID,
          isCashSecurity: securityItem.isCashSecurity == 'Y' ? 'Y' : 'N',
          isAddedFacility: 'N',
          status: 'INA'
        };

        let removeObj = Object.assign({},
          {
            securityID: securityItem.securityID ? securityItem.securityID : null,
            securityDetail: securityItem.securityDetail ? securityItem.securityDetail : null,
            applicationFormID: facilityData.applicationFormID ? facilityData.applicationFormID : null,
            securityCode: securityItem.securityCode ? securityItem.securityCode : null,
            securityAmount: this.getValue(securityItem.securityAmount),
            cashAmount: this.getValue(securityItem.cashAmount),
            isCashSecurity: securityItem.isCashSecurity == 'Y' ? 'Y' : 'N',
            securityCurrency: securityItem.securityCurrency,
            status: securityItem.isCommonSecurity == 'Y' ? Constants.statusConst.ACT : Constants.statusConst.INA,
            isCommonSecurity: linkedActiveFacilitiesCount >= 3 ? 'Y' : 'N',
            facilityID: facilityData.facilityID,
            afFacilitySecurityDTOS: [facilitySecurityDTO]
          }
        );
        if (facilitySecurityID) {
          this.applicationFormAddEditService.saveUpdateFacilitySecurity(removeObj);
        }
      }
    });
  }

  showPreviousTab($event) {
    let index = this.currentIndex;
    if (index != 0) {
      this.setTabIndex.emit(--index)
    }
  }

  showNextTab($event) {
    let index = this.currentIndex;
    if (index != this.lastIndex) {
      this.setTabIndex.emit(++index)
    }
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '')
    }
  }

  toContent(id) {
    this.toCommonSecurityContent.emit(id);
  }

  addSecurity(facilityData, securityItem?, allSecurityItems?) {
    let emitObject = Object.assign({}, {facilityData: facilityData}, {securityItem: securityItem}, {allSecurityItems: allSecurityItems});
    this.addSecurityThroughFacility.emit(emitObject);
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, '');
    }
    return amount;
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, '', '', '1.3-3')
    }
  }

  getMillionValue(value) {
    return AppUtils.getMillionValue(value);
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

}
