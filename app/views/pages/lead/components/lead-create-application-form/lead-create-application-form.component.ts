import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription } from "rxjs";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { IMyOptions, MDBModalRef } from "ng-uikit-pro-standard";
import { AppUtils } from "../../../../../shared/app.utils";
import { Constants } from "../../../../../core/setting/constants";
import { ApplicationService } from "../../../../../core/service/application/application.service";
import * as moment from 'moment';
import * as _ from "lodash"

@Component({
  selector: 'app-lead-create-application-form',
  templateUrl: './lead-create-application-form.component.html',
  styleUrls: ['./lead-create-application-form.component.scss']
})
export class LeadCreateApplicationForm implements OnInit, OnDestroy {

  onFormValueChangeSub = new Subscription();
  onApplicationFormChangeSub = new Subscription();
  action: Subject<any> = new Subject<any>();
  results: Subject<any>;

  applicationCreateForm: FormGroup;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  // cribStatusConst = Constants.cribStatusConst;
  // cribStatus = Constants.cribStatus;
  basicInformation = Constants.basicInformationType;
  identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  basicInformationType = Constants.basicInformationType;
  leadFsTypeConst = Constants.leadFsTypeConst;
  leadFsType = Constants.leadFsType;
  customerIdentificationTypeConst = Constants.customerIdentificationTypeConst;

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  formErrors: any = {};
  isDisabled: boolean = false;
  templateOption: any[] = [];
  allWorkFlowTemplates = [];
  allOptions = [];
  heading: string;
  message: string;
  htmlContent: any;
  // cribDate: any;
  applicationForm: any = {};
  identificationNumber: string;
  identificationType: string;
  leadID: string;
  isCompLead: boolean = false;

  /* optionsCribStatusSelect = [
     {value: this.cribStatusConst.NOT_ENTERED, label: this.cribStatus.NOT_ENTERED},
     {value: this.cribStatusConst.PENDING, label: this.cribStatus.PENDING},
     {value: this.cribStatusConst.NO_IRREGULAR_ADVANCES, label: this.cribStatus.NO_IRREGULAR_ADVANCES},
     {value: this.cribStatusConst.REPORTED_AS_IRREGULAR, label: this.cribStatus.REPORTED_AS_IRREGULAR},
     {value: this.cribStatusConst.SERVICE_NOT_AVAILABLE, label: this.cribStatus.SERVICE_NOT_AVAILABLE},
     {value: this.cribStatusConst.SKIP_CRIB_REPORT, label: this.cribStatus.SKIP_CRIB_REPORT},
   ];*/

  constructor(private mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    public applicationService: ApplicationService) {
  }

  ngOnInit() {
    this.formErrors = {
      comment: {},
      //cribStatus: {},
      // CRIBDate: {},
      identificationType: {},
      type: {},
      applicationFormFsType: {},
    };
    console.log("")
    this.createForm();
    this.loadIdOption();

    this.onFormValueChangeSub.unsubscribe();
    this.onFormValueChangeSub = this.applicationCreateForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.applicationCreateForm, this.formErrors);
    });
  }

  loadIdOption() {
    if (this.isCompLead) {
      this.identityOptionSelect = Constants.identificationTypeOptionsSelect;
    } else {
      this.identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;
    }
  }

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  createApplicationForm(currentApplicationFormStatus): void {


    let afCommentDTOList = [{
      comment: this.applicationCreateForm.getRawValue().comment,
      createdUserID: this.applicationService.getLoggedInUserUserID(),
      createdUser: this.applicationService.getLoggedInUserUserName(),
      createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
      createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      currentApplicationFormStatus,
      isUsersOnly: 'N',
      isDivisionOnly: 'N',
      isPublic: 'Y',
    }];

    /* let afCribReportDTOList = [{
       identificationNo: this.applicationCreateForm.getRawValue().identificationNumber,
       identificationType: this.applicationCreateForm.getRawValue().identificationType,
       cribDateStr: this.applicationCreateForm.getRawValue().CRIBDate,
       reportContent: this.htmlContent,
       cribStatus: this.applicationCreateForm.getRawValue().cribStatus,
       remark: this.applicationCreateForm.getRawValue().comment,
       status: Constants.statusConst.ACT
     }];*/

    let basicInformationDTO = {
      identificationNo: this.applicationCreateForm.getRawValue().identificationNumber,
      identificationType: this.applicationCreateForm.getRawValue().identificationType,
      status: Constants.statusConst.ACT,
      type: this.applicationCreateForm.getRawValue().type
      //   afCribReportDTOList: afCribReportDTOList
    };

    let basicInformationDTOList = [basicInformationDTO];

    let applicationFormInitData = Object.assign({}, {
      leadID: this.leadID,
      fsType: this.applicationCreateForm.getRawValue().leadFsType,
      createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      createdUserID: this.applicationService.getLoggedInUserUserID(),
      branchCode: this.applicationService.getLoggedInUserDivCode(),
      assignUserID: this.applicationService.getLoggedInUserUserID(),
      assignUser: this.applicationService.getLoggedInUserUserName(),
      assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      assignUserUpmID: this.applicationService.getLoggedInUserUserID(),
      assignUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      assignUserDivCode: this.applicationService.getLoggedInUserDivCode(),
      formType: this.applicationCreateForm.getRawValue().type,
      identificationNumber: this.applicationCreateForm.getRawValue().identificationNumber,
      afCommentDTOList,
      currentApplicationFormStatus,
      basicInformationDTOList: basicInformationDTOList,
      remark: this.applicationCreateForm.getRawValue().comment,
    });

    this.action.next(AppUtils.trim(applicationFormInitData));
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
    this.onApplicationFormChangeSub.unsubscribe();
  }

  isValid() {
    return this.applicationCreateForm && this.applicationCreateForm.valid;
  }

  isDirty() {
    return this.applicationCreateForm && this.applicationCreateForm.dirty;
  }

  createForm() {

    let disableCribFields = _.isEmpty(this.htmlContent);

    this.applicationCreateForm = this.formBuilder.group({
      identificationType: [{ value: this.identificationType, disabled: true }],
      identificationNumber: [{ value: this.identificationNumber, disabled: true }],
      comment: ['', [Validators.required]],
      /* cribStatus: [{
         value: disableCribFields ? 'SKIP_CRIB_REPORT' : '',
         disabled: disableCribFields
       }, [Validators.required]],
       CRIBDate: [{value: this.cribDate ? this.cribDate : moment().format('DD/MM/YYYY'), disabled: false}],*/
      // type: [this.identificationType ? this.identificationType == this.customerIdentificationTypeConst.BRC ? this.basicInformationTypeConst.BUSINESS : this.basicInformationTypeConst.PERSONAL : this.basicInformationTypeConst.PERSONAL, [Validators.required]],
      leadFsType: [{ value: this.leadFsType, disabled: true }, [Validators.required]],
      type: [{ value: this.identificationType ? this.identificationType == this.customerIdentificationTypeConst.BRC ? this.basicInformationTypeConst.BUSINESS : this.basicInformationTypeConst.PERSONAL : this.basicInformationTypeConst.PERSONAL, disabled: true }, [Validators.required]],
    });
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allOptions.filter((item: any) => item.branchName.toLowerCase().includes(filterValue));
  }

  moveUp() {
    document.getElementById("form-div").scrollIntoView({
      behavior: 'smooth',
      block: 'start',
      inline: 'start'
    }
    );
  }

  /* showCribReportHtmlContent() {
      return !_.isEmpty(this.htmlContent);
    }*/
}
