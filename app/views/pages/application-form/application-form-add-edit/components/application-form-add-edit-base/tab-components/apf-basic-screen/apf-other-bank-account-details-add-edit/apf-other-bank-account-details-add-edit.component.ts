import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import * as _ from "lodash";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {ConfirmationDialogComponent} from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfAddEditOtherBankDetailsComponent} from "../../../support-components/apf-add-edit-other-bank-details/apf-add-edit-other-bank-details.component";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";

@Component({
  selector: 'app-apf-other-bank-account-details-add-edit',
  templateUrl: './apf-other-bank-account-details-add-edit.component.html',
  styleUrls: ['./apf-other-bank-account-details-add-edit.component.scss']
})
export class ApfOtherBankAccountDetailsAddEditComponent implements OnInit {
  modalRef: MDBModalRef;
  @Input() basicInformation;
  @Input() applicationForm;
  isDisabled: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {
  }

  openModalOtherBankDetails($event, otherBankDetails?) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.modalRef = this.mdbModalService.show(ApfAddEditOtherBankDetailsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Other Bank Account Details",
        basicInformation: this.basicInformation,
        applicationForm: this.applicationForm,
        otherBankDetail: otherBankDetails ? otherBankDetails : {},
      }
    });
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.applicationFormAddEditService.saveOrUpdateOtherBankDetails(data);
      }
    });
  }

  deactivateAFCribAttachment($event, data) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    if (!_.isEmpty(data)) {

      data.status = Constants.statusConst.INA;

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
          heading: "Confirm Remove Other Bank Details",
          message: "Do you want to remove this record ?",
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.applicationFormAddEditService.removeOtherBankDetails(data);
        }
      });
    }
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

}
