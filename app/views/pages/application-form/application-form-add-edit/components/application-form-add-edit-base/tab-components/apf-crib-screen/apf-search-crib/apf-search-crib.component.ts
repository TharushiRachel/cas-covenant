import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormAddEditService} from "../../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {HtmlContentViewerComponent} from "../../../../../../../../../shared/components/html-content-viewer/html-content-viewer.component";
import {NicValidator} from "../../../../../../../../../shared/validators/nic.validator";
import {ApfAddEditCribReportsComponent} from "../../../support-components/apf-add-edit-crib-reports/apf-add-edit-crib-reports.component";
import {Constants} from "../../../../../../../../../core/setting/constants";
import {IdentificationNumberValidator} from "../../../../../../../../../shared/validators/identification-number.validator";
import {AlertService} from "../../../../../../../../../core/service/common/alert.service";
import {SETTINGS} from "../../../../../../../../../core/setting/commons.settings";
import {ShowCribHistoryComponent} from "../../../../../../../../../shared/components/show-crib-history/show-crib-history.component";

@Component({
  selector: 'app-apf-search-crib',
  templateUrl: './apf-search-crib.component.html',
  styleUrls: ['./apf-search-crib.component.scss']
})
export class ApfSearchCribComponent implements OnInit, OnDestroy {

  onApplicationFormChangeSub = new Subscription();
  identityOptionSelect = Constants.customerCribIdentificationTypeOptionsSelect;
  customerIdentificationType = Constants.customerIdentificationType;
  cribSearchForm: FormGroup;
  modalRef: MDBModalRef;
  applicationForm: any = {};
  formErrors: any = {};
  isDisabled: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private alertService: AlertService,
              private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {

    this.formErrors = {
      identificationNumber: {},
      identificationType: {},
    };
    this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
      }
    });
    this.cribSearchForm = this.createFrom();
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

  createFrom() {
    this.cribSearchForm = this.formBuilder.group({
      identificationNumber: [{
        value: this.applicationForm.identificationNumber,
        disabled: this.isDisabled
      }, [Validators.required, NicValidator.isValidNICInput]],
      identificationType: ['NIC', [Validators.required]],
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
        content: {identificationType, identificationNumber},
        enableActions: true,
        heading: "Crib Report",
        message: "Do you want to view the Crib Report for " + identificationNumber + " ? This service will add charges",
      }
    });

    if (this.cribSearchForm.controls.identificationType.value == this.customerIdentificationType.NIC) {
      let retailCribRQ = {
        identificationType: identificationType,
        identificationNumber: this.cribSearchForm.controls.identificationNumber.value,
      };

      this.modalRef.content.action.subscribe((data: any) => {
          if (data && data.proceedWithNewCribReport) {
            this.applicationFormAddEditService.getRetailCribReport(retailCribRQ).then((response: any) => {
              if (response) {
                this.showCribReport(retailCribRQ.identificationNumber, identificationType, response);
              }
            }).catch(e => {
                this.alertService.showToaster(e, SETTINGS.TOASTER_MESSAGES.error);
              }
            );
          }
        }
      );
    } else {

      let corporateCribRQ = {
        identificationType: this.customerIdentificationType.BRC,
        identificationNumber: identificationNumber,
        REGNo: identificationNumber,
      };

      this.modalRef.content.action.subscribe((data: any) => {
          if (data && data.proceedWithNewCribReport) {
            this.applicationFormAddEditService.getCorporateCribReport(corporateCribRQ).then((response: any) => {
              if (response) {
                this.showCribReport(identificationNumber, identificationType, response, null);
              }
            }).catch(e => {
                this.alertService.showToaster(e, SETTINGS.TOASTER_MESSAGES.error);
              }
            );
          }
        }
      );
    }

    this.modalRef.content.action.subscribe((data: any) => {
      if (data && data.proceedWithPreviousCribReport) {
        this.showCribReport(identificationNumber, identificationType, data.pdfReport, data.previousCribDate);
      }
    });

  }

  showCribReport(identificationNumber, identificationType, htmlContent?, cribDate?) {
    this.modalRef = this.mdbModalService.show(ApfAddEditCribReportsComponent, {
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
        htmlContent: htmlContent,
        cribDate: cribDate,
        identificationNumber: identificationNumber,
        identificationType: identificationType,
        applicationForm: this.applicationForm
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.applicationFormAddEditService.saveOrUpdateCribReports(data);
      }
    });

  }

  viewInitiateCribReport(data) {
    this.modalRef = this.mdbModalService.show(HtmlContentViewerComponent, {
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
        htmlContent: data,
      }
    });
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

}
