import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SupportDocumentUpdateDto} from "../../dto/support-document-update-dto";
import {Subscription} from "rxjs";
import {SupportDocumentAddEditService} from "../../services/support-document-add-edit.service";
import * as _ from 'lodash';
import {AppUtils} from "../../../../../shared/app.utils";
import {Constants} from "../../../../../core/setting/constants";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-support-document-add-edit',
  templateUrl: './support-document-add-edit.component.html',
  styleUrls: ['./support-document-add-edit.component.scss']
})
export class SupportDocumentAddEditComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any;
  supportDocumentUpdateDTO: SupportDocumentUpdateDto = new SupportDocumentUpdateDto({});
  onSelectedSupportDocumentChangeSub: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();

  pageType: String = 'new';

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  approveStatus = Constants.approveStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  constructor(
    private formBuilder: FormBuilder,
    private supportDocumentAddEditService: SupportDocumentAddEditService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {

    this.formErrors = {
      documentName: {},
      description: {},
      supportDocumentType: {},
      status: {}
    };

    this.onSelectedSupportDocumentChangeSub = this.supportDocumentAddEditService.onSupportDocChange
      .subscribe(doc => {
        if (_.isEmpty(doc)) {
          this.pageType = 'new';
          this.supportDocumentUpdateDTO = new SupportDocumentUpdateDto({})
        } else {
          this.pageType = 'edit';
          this.supportDocumentUpdateDTO = new SupportDocumentUpdateDto(doc);
        }
        this.componentForm = this.createSupportingDocForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges
          .subscribe(form => {
            this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
          })

      })
  }

  ngOnDestroy(): void {

    this.onFormValueChange.unsubscribe();
    this.onSelectedSupportDocumentChangeSub.unsubscribe();
  }

  createSupportingDocForm() {
    return this.formBuilder.group({
      documentName: [this.supportDocumentUpdateDTO.documentName, [Validators.required, Validators.maxLength(200)]],
      description: [this.supportDocumentUpdateDTO.description, [Validators.required]],
      supportDocumentType: [this.supportDocumentUpdateDTO.supportDocumentType, [Validators.required]],
      status: [{value: this.supportDocumentUpdateDTO.status, disabled: this.pageType == 'new'}, [Validators.required]]
    })
  }

  isApproveOrRejectValid() {
    return this.supportDocumentUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  saveUpdate() {
    let data = Object.assign({}, this.supportDocumentUpdateDTO, this.componentForm.getRawValue(),
      {
        approveStatus: this.supportDocumentUpdateDTO.approveStatus,
        approvedBy: this.supportDocumentUpdateDTO.approvedBy,
        approvedDateStr: this.supportDocumentUpdateDTO.approvedDateStr
      });
    this.supportDocumentAddEditService.saveUpdateSupportingDoc(data);
  }

  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.supportDocumentUpdateDTO.supportingDocID},
      {approveStatus: this.approveStatus.APPROVED});
    this.supportDocumentAddEditService.approveOrRejectSupportingDoc(data);
  }

  reject() {

    let data = Object.assign({},
      {approveRejectDataID: this.supportDocumentUpdateDTO.supportingDocID},
      {approveStatus: this.approveStatus.REJECTED});
    this.supportDocumentAddEditService.approveOrRejectSupportingDoc(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.supportDocumentUpdateDTO.modifiedBy ? this.supportDocumentUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.supportDocumentUpdateDTO.createdBy ? this.supportDocumentUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }

}
