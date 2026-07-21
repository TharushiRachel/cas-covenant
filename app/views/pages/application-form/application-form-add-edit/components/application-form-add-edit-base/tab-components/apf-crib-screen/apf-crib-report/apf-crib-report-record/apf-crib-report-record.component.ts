import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {HtmlContentViewerComponent} from "../../../../../../../../../../shared/components/html-content-viewer/html-content-viewer.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormAddEditService} from "../../../../../../services/application-form-add-edit.service";
import {ApfAddEditCribReportsComponent} from "../../../../support-components/apf-add-edit-crib-reports/apf-add-edit-crib-reports.component";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NicValidator} from "../../../../../../../../../../shared/validators/nic.validator";
import {IdentificationNumberValidator} from "../../../../../../../../../../shared/validators/identification-number.validator";
import {SETTINGS} from "../../../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../../../core/service/common/alert.service";
import {ShowCribHistoryComponent} from "../../../../../../../../../../shared/components/show-crib-history/show-crib-history.component";

@Component({
  selector: 'app-apf-crib-report-record',
  templateUrl: './apf-crib-report-record.component.html',
  styleUrls: ['./apf-crib-report-record.component.scss']
})
export class ApfCribReportRecordComponent implements OnInit {
  @Input() basicInformation;
  @Input() applicationForm;
  optionalCribSearchForm: FormGroup;
  formErrors: any = {};
  isDisabled: boolean = false;

  cribStatusConst = Constants.cribStatusConst;
  cribStatus = Constants.cribStatus;
  modalRef: MDBModalRef;
  identityOptionSelect = Constants.customerCribIdentificationTypeOptionsSelect;
  customerIdentificationType = Constants.customerIdentificationType;


  constructor(private mdbModalService: MDBModalService,
              private alertService: AlertService,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.formErrors = {
      identificationNumber: {},
      identificationType: {},
    };

    this.optionalCribSearchForm = this.createFrom();
  }

  createFrom() {
    this.optionalCribSearchForm = this.formBuilder.group({
      identificationNumber: [{
        value: this.applicationForm.identificationNumber,
        disabled: this.isDisabled
      }, [Validators.required, NicValidator.isValidNICInput]],
      identificationType: [this.customerIdentificationType.NIC, [Validators.required]],
    });
    this.optionalCribSearchForm.setValidators(IdentificationNumberValidator.validateIdentificationNumber);

    return this.optionalCribSearchForm;
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


  searchCribDetails() {

    let identificationType = this.optionalCribSearchForm.controls.identificationType.value;
    let identificationNumber = this.optionalCribSearchForm.controls.identificationNumber.value;

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

    if (identificationType == this.customerIdentificationType.NIC) {

      let retailCribRQ = {
        identificationType: this.customerIdentificationType.NIC,
        identificationNumber: identificationNumber,
      };

      this.modalRef.content.action.subscribe((data: any) => {
          if (data && data.proceedWithNewCribReport) {
            this.applicationFormAddEditService.getRetailCribReport(retailCribRQ).then((response: any) => {
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
    } else {

      let corporateCribRQ = {
        identificationType: this.customerIdentificationType.BRC,
        identificationNumber: identificationNumber,
        REGNo: identificationNumber,
        customerName: ''
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

  showCribReport(identificationNumber, identificationType, htmlContent, previousCribDate) {
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
        cribDate: previousCribDate,
        identificationNumber: identificationNumber,
        identificationType: identificationType,
        applicationForm: this.applicationForm
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let optionalCribData = {...data, basicInformationID: this.basicInformation.basicInformationID};
        this.applicationFormAddEditService.saveOrUpdateOptionalCribReports(optionalCribData);
      }
    });

  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

  viewCribReportContent(status) {
    return !(status == this.cribStatusConst.SERVICE_NOT_AVAILABLE || status == this.cribStatusConst.SKIP_CRIB_REPORT);
  }

}
