import {Component, OnInit} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ConfirmationDialogComponent} from "../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {ApfCreateApplicationFormComponent} from "../support-components/apf-create-application-form/apf-create-application-form.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormCreateService} from "../../services/application-form-create.service";
import {UrlEncodeService} from "../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {NicValidator} from "../../../../../../shared/validators/nic.validator";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {IdentificationNumberValidator} from "../../../../../../shared/validators/identification-number.validator";
import {AlertService} from "../../../../../../core/service/common/alert.service";
import {ShowCribHistoryComponent} from "../../../../../../shared/components/show-crib-history/show-crib-history.component";

@Component({
  selector: 'app-application-form-create-base',
  templateUrl: './application-form-create-base.component.html',
  styleUrls: ['./application-form-create-base.component.scss']
})
export class ApplicationFormCreateBaseComponent implements OnInit {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  modalRef: MDBModalRef;
  selectedTabIndex: any = 0;
  applicationForm: any = {};
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;
  identityOptionSelect = Constants.customerCribIdentificationTypeOptionsSelect;
  customerIdentificationType = Constants.customerIdentificationType;
  cribSearchForm: FormGroup;
  formErrors: any = {};

  cribTabIndex = 0;
  riskRateIndex = 1;

  constructor(private mdbModalService: MDBModalService,
              private applicationFormCreateService: ApplicationFormCreateService,
              private urlEncodeService: UrlEncodeService,
              private router: Router,
              private formBuilder: FormBuilder,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.formErrors = {
      identificationNumber: {},
      identificationType: {},
    };

    this.cribSearchForm = this.createFrom();
  }

  createFrom() {
    this.cribSearchForm = this.formBuilder.group({
      identificationNumber: ['', [Validators.required, NicValidator.isValidNICInput]],
      identificationType: [this.customerIdentificationType.NIC, [Validators.required]],
    });
    this.cribSearchForm.setValidators(IdentificationNumberValidator.validateIdentificationNumber);

    return this.cribSearchForm;
  }

  searchCribDetails() {

    let identificationType = this.cribSearchForm.controls.identificationType.value;
    let identificationNumber = this.cribSearchForm.controls.identificationNumber.value;

    this.modalRef = this.mdbModalService.show(ShowCribHistoryComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-dialog-scrollable',
      containerClass: '',
      animated: false,
      data: {
        content: this.cribSearchForm.getRawValue(),
        enableActions: true,
        heading: "Crib Report",
        message: "Do you want to view the Crib Report for " + identificationNumber + " ? This service will add charges",
      }
    });

    if (identificationType == this.customerIdentificationType.NIC) {
      let retailCribRQ = {
        identificationType: this.customerIdentificationType.NIC,
        identificationNumber: identificationNumber,
      };

      this.modalRef.content.action.subscribe((data: any) => {
        if (data && data.proceedWithNewCribReport) {
          this.applicationFormCreateService.getRetailCribReport(retailCribRQ).then((response: any) => {
            if (response) {
              this.showCreateApplicationFormModal(retailCribRQ.identificationNumber, retailCribRQ.identificationType, response, null);
            }
          }).catch(e => {
            this.alertService.showToaster(e, SETTINGS.TOASTER_MESSAGES.error);
            console.log(e);
          });
        }
      });

    } else {

      let corporateCribRQ = {
        identificationType: this.customerIdentificationType.BRC,
        identificationNumber: identificationNumber,
        REGNo: identificationNumber,
      };

      this.modalRef.content.action.subscribe((data: any) => {
        if (data && data.proceedWithNewCribReport) {
          this.applicationFormCreateService.getCorporateCribReport(corporateCribRQ).then((response: any) => {
            if (response) {
              this.showCreateApplicationFormModal(identificationNumber, identificationType, response, null);
            }
          }).catch(e => {
            this.alertService.showToaster(e, SETTINGS.TOASTER_MESSAGES.error);
            console.log(e);
          });
        }
      });
    }

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        if (data.proceedWithPreviousCribReport) {
          this.showCreateApplicationFormModal(identificationNumber, identificationType, data.pdfReport, data.previousCribDate);
        }
      }
    });
  }

  showCreateApplicationFormModal(identificationNumber, identificationType, CribData?, cribDate?) {
    this.modalRef = this.mdbModalService.show(ApfCreateApplicationFormComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        header: 'CRIB Details',
        htmlContent: CribData,
        cribDate: cribDate,
        identificationNumber: identificationNumber,
        identificationType: identificationType,
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.applicationFormCreateService.draftApplicationForm(data).subscribe((res: any) => {
          if (res.applicationFormID) {
            this.selectedApplicationFormID = this.urlEncodeService.encode(res.applicationFormID);
            this.router.navigate(['/application-form/add-edit']);
          }
        })
      }
    });
  }

  skipCribReport() {

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center ',
      containerClass: '',
      animated: false,
      data: {
        heading: "Draft Application",
        message: "Do you want to create Application without Crib Report for " + this.cribSearchForm.controls.identificationNumber.value + " ?",
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.showCreateApplicationFormModal(this.cribSearchForm.controls.identificationNumber.value, this.cribSearchForm.controls.identificationType.value, null);
      }
    });
  }

}
