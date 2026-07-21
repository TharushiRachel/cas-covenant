import {Component, OnDestroy, OnInit} from '@angular/core';
import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {CacheService} from "../../../../../../../../../../core/service/data/cache.service";
import {PersonalCribUpdateDto} from "../../../../../../../dto/personal-crib-update-dto";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {Subject, Subscription} from "rxjs";
import {AppUtils} from "../../../../../../../../../../shared/app.utils";
import {FileValidator} from "../../../../../../../../../../shared/validators/file.validator";
import {FileUploadError} from "../../../../../../../../../../shared/dto/file-upload-error";
import {SETTINGS} from "../../../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../../../core/service/common/alert.service";
import * as _ from "lodash";
import {ApplicationService} from "../../../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-personal-crib-add-edit',
  templateUrl: './personal-crib-add-edit.component.html',
  styleUrls: ['./personal-crib-add-edit.component.scss']
})
export class PersonalCribAddEditComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;
  fileToUpload: File = null;
  fileUploadError: FileUploadError = new FileUploadError();

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate() + 1,
  };

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 120,
    maxYear: new Date().getFullYear(),
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: this.disableSinceDate,
  };

  optionscribStatusSelect = [
    {value: 'NOT_ENTERED', label: 'Not Entered'},
    {value: 'PENDING', label: 'Pending'},
    {value: 'NO_IRREGULAR_ADVANCES', label: 'No Irregular Advances'},
    {value: 'REPORTED_AS_IRREGULAR', label: 'Reported as Irregular (Refer Comments)'},
  ];

  componentForm: FormGroup;
  formErrors: any = {};
  supportingDocs = [];

  result: Subject<any>;
  onDocumentNameChangeSub: Subscription = new Subscription();
  onFormValueChangeSub: Subscription = new Subscription();

  personalcribUpdateDTO: PersonalCribUpdateDto = new PersonalCribUpdateDto({});

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private cacheService: CacheService,
    private alertService: AlertService
  ) {
  }

  ngOnInit() {
    this.formErrors = {
      //documentName: [''],
      cribStatus: [''],
      cribIssueDateStr: ['']
    };

    if (!_.isEmpty(this.content.cribItem)) {
      this.personalcribUpdateDTO = new PersonalCribUpdateDto(this.content.cribItem)
    }

    this.supportingDocs = this.cacheService.getData(Constants.masterDataKey.CAS_SUPPORTING_DOCs);
    // this.result = new BehaviorSubject(this.supportingDocs);

    this.componentForm = this.createCribForm();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
    //
    // this.onDocumentNameChangeSub = this.componentForm.controls.documentName.valueChanges
    //   .subscribe((value: any) => {
    //     this.result.next(this.filter(value))
    //   });
  }

  ngOnDestroy(): void {
    this.onDocumentNameChangeSub.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
  }

  createCribForm() {
    return this.formBuilder.group({
      //documentName: [this.personalcribUpdateDTO.documentName, [Validators.required]],
      cribStatus: [this.personalcribUpdateDTO.cribStatus, [Validators.required]],
      cribIssueDateStr: [this.personalcribUpdateDTO.cribIssueDateStr, [Validators.required]],
      remarks: [this.personalcribUpdateDTO.remarks]
    })
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.supportingDocs.filter((item: any) => item.documentName.toLowerCase().includes(filterValue));
  }

  isValidUpload() {
    return this.fileToUpload != null && !this.fileUploadError.hasError;
  }

  selectFile(event) {
    this.fileToUpload = event.target.files[0];
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);
  }

  addCribDocument() {
    let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocs, "CRIB");
    this.fileUploadError = FileValidator.isValidFile(this.fileToUpload);

    if (this.fileUploadError.hasError) {
      this.alertService.showToaster(this.fileUploadError.errorMessage, SETTINGS.TOASTER_MESSAGES.error);
    } else {
      let rowData = Object.assign({},
        this.personalcribUpdateDTO,
        this.componentForm.getRawValue(),
        {casCustomerCribDetailsID: this.personalcribUpdateDTO.casCustomerCribDetailsID ? this.personalcribUpdateDTO.casCustomerCribDetailsID : null},
        {casCustomerID: this.content.casCustomerID},
        {docStorageDTO: {}},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {supportingDocID: doc.supportingDocID},
        {documentName: doc.documentName},
        {
          status: 'ACT',
          uploadedUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          uploadedDivCode: this.applicationService.getLoggedInUserDivCode()
        });

      rowData.docStorageDTO = {
        fileName: this.fileToUpload.name,
      };

      let formData = new FormData();
      formData.append("uploadingFile", this.fileToUpload, this.fileToUpload['Name']);
      formData.append("uploadRequestData", JSON.stringify(rowData));

      this.facilityPaperAddEditService.uploadFpCribDocuments(formData);
      // this.alertService.showToaster("Facility document uploaded successfully", SETTINGS.TOASTER_MESSAGES.success)
      this.mdbModalRef.hide();
    }
  }

}
