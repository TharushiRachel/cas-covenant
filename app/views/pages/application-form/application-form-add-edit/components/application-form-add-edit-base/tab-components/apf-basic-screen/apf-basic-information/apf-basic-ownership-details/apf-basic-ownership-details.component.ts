import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfAddEditOwnershipDetailsComponent} from "../../../../support-components/apf-add-edit-ownership-details/apf-add-edit-ownership-details.component";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import * as _ from "lodash";
import {ConfirmationDialogComponent} from "../../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {ApplicationFormAddEditService} from "../../../../../../services/application-form-add-edit.service";

@Component({
  selector: 'app-apf-basic-ownership-details',
  templateUrl: './apf-basic-ownership-details.component.html',
  styleUrls: ['./apf-basic-ownership-details.component.scss']
})
export class ApfBasicOwnershipDetailsComponent implements OnInit {

  @Input() basicInformation: any = {};

  civilStatus = Constants.civilStatus;
  customerIdentificationType = Constants.customerIdentificationType;
  ConstitutionType = Constants.ConstitutionType;
  yesNo = Constants.yesNo;

  modalRef: MDBModalRef;
  ownershipDetailsForm: FormGroup;
  isDisabled: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,) {
  }

  ngOnInit() {
  }

  openModalOwnerShipDetails(basicInformation, ownershipDetails) {

    this.modalRef = this.mdbModalService.show(ApfAddEditOwnershipDetailsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: 'right',
      animated: false,
      data: {
        heading: "comming dto",
        content: {
          basicInformation: basicInformation,
          ownershipDetails: ownershipDetails
        },
      }
    });
  }

  removeOwnerShipDetails($event, item) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    if (!_.isEmpty(item)) {

      item.status = Constants.statusConst.INA;

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
          heading: "Confirm Remove Ownership Details",
          message: "Do you want to remove this record ?",
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.applicationFormAddEditService.saveOrUpdateOwnershipDetails(item);
        }
      });
    }
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

}
