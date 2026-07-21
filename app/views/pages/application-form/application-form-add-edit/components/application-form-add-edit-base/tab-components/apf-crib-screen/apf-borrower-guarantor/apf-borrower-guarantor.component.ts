import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {ConfirmationDialogComponent} from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {ApfAddEditBorrowerGuarantorComponent} from "../../../support-components/apf-add-edit-borrower-guarantor/apf-add-edit-borrower-guarantor.component";
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-apf-borrower-guarantor',
  templateUrl: './apf-borrower-guarantor.component.html',
  styleUrls: ['./apf-borrower-guarantor.component.scss']
})
export class ApfBorrowerGuarantorComponent implements OnInit {

  @Input() basicInformation;
  @Input() applicationForm;
  financialObligationForm: FormGroup;
  yesNoConst = Constants.yesNoConst;
  statusConst = Constants.statusConst;
  modalRef: MDBModalRef;
  formErrors: any = {};
  isDisabled: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private mdbModalService: MDBModalService,
              private currencyPipe: CurrencyPipe,) {
  }

  ngOnInit() {

    this.formErrors = {
      burrowerGuarantorPast: {},
    };

    this.financialObligationForm = this.createFrom();

  }

  createFrom() {
    this.financialObligationForm = this.formBuilder.group({
      burrowerGuarantorPast: [{
        value: this.basicInformation.isBorrowerOrGuarantor,
        disabled: this.isDisabled
      }, [Validators.required]]
    });
    return this.financialObligationForm;
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

  openModalBorrowerGuarantor($event, borrowerGuarantor?) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfAddEditBorrowerGuarantorComponent, {
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
        borrowerGuarantor: borrowerGuarantor ? borrowerGuarantor : {},
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.applicationFormAddEditService.saveOrUpdateBorrowerGuarantor(data);
      }
    });
  }

  remove($event, data) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let activeCount = 0;
    let isBorrowerOrGuarantor = this.yesNoConst.Y;

    this.basicInformation.afBorrowerGuarantorDTOList.forEach((e: any) => {
      if (e.status == this.statusConst.ACT) {
        activeCount++;
      }
    });

    if (activeCount >= 2) {
      isBorrowerOrGuarantor = this.yesNoConst.Y;
    } else {
      isBorrowerOrGuarantor = this.yesNoConst.N;
    }

    let removeData = Object.assign({}, data, this.applicationForm, {
      status: Constants.statusConst.INA,
      isBorrowerOrGuarantor: isBorrowerOrGuarantor
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
        heading: "Confirm Remove ",
        message: "Do you want to remove this record ?",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.applicationFormAddEditService.saveOrUpdateBorrowerGuarantor(removeData);
      }
    });
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

}
