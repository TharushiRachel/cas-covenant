import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfAddEditFinancialObligationsComponent} from "../../../support-components/apf-add-edit-financial-obligations/apf-add-edit-financial-obligations.component";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import {CurrencyPipe} from "@angular/common";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {ConfirmationDialogComponent} from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: 'app-apf-financial-obligations',
  templateUrl: './apf-financial-obligations.component.html',
  styleUrls: ['./apf-financial-obligations.component.scss']
})
export class ApfFinancialObligationsComponent implements OnInit, OnDestroy {
  @Input() basicInformation;
  @Input() applicationForm;
  modalRef: MDBModalRef;
  financialObligations = Constants.financialObligations;
  formErrors: any = {};
  isDisabled: boolean = false;
  identificationTypeViceFinancialObligationMap = {};
  liabilities: any = [];
  identificationGroups = [];
  identificationTypeViceTotalValuesMap = {};

  constructor(private formBuilder: FormBuilder,
              private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private currencyPipe: CurrencyPipe,) {
  }

  ngOnInit() {
    this.liabilities = this.basicInformation.afFinancialObligationDTOList;
    this.identificationTypeViceFinancialObligationMap = {};
    this.liabilities.forEach((financialObligation: any) => {
      if (this.identificationTypeViceFinancialObligationMap[financialObligation.identificationNumber] == undefined) {
        this.identificationTypeViceFinancialObligationMap[financialObligation.identificationNumber] = [];
        this.identificationGroups.push({
          identificationNumber: financialObligation.identificationNumber,
          identificationType: financialObligation.identificationType
        });
      }
      this.identificationTypeViceFinancialObligationMap[financialObligation.identificationNumber].push(financialObligation);
    });

    this.identificationGroups.forEach((data: any) => {
      let originalAmountTotal = 0;
      let presentOutstandingTotal = 0;
      let arrearsTotal = 0;
      this.identificationTypeViceFinancialObligationMap[data.identificationNumber].forEach((financialObligations: any) => {
        originalAmountTotal = originalAmountTotal + financialObligations.originalAmount;
        presentOutstandingTotal = presentOutstandingTotal + financialObligations.presentOutstanding;
        arrearsTotal = arrearsTotal + financialObligations.arrears;
        if (this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber] == undefined) {
          this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber] = {
            originalAmountTotal: 0,
            presentOutstandingTotal: 0,
            arrearsTotal: 0
          }
        }
        this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber].originalAmountTotal = originalAmountTotal;
        this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber].presentOutstandingTotal = presentOutstandingTotal;
        this.identificationTypeViceTotalValuesMap[financialObligations.identificationNumber].arrearsTotal = arrearsTotal;
      })
    });
  }

  ngOnDestroy(): void {
  }

  openModalFinancialObligation($event, financialObligation?) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfAddEditFinancialObligationsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: 'right',
      animated: false,
      data: {
        heading: "",
        basicInformation: this.basicInformation,
        applicationForm: this.applicationForm,
        financialObligation: financialObligation ? financialObligation : {},
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.applicationFormAddEditService.saveOrUpdateFinancialObligations(data);
      }
    });
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  remove($event, data) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let removeData = Object.assign({}, data, this.applicationForm, {status: Constants.statusConst.INA});
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
        heading: "Confirm Remove ",
        message: "Do you want to remove this Financial Obligation ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.applicationFormAddEditService.saveOrUpdateFinancialObligations(removeData);
      }
    });
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }
}
