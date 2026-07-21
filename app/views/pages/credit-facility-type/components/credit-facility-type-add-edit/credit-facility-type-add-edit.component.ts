import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CreditFacilityTypeUpdateDto} from "../../dto/credit-facility-type-update-dto";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {CreditFacilityTypeAddEditService} from "../../services/credit-facility-type-add-edit.service";
import * as _ from 'lodash';
import {AppUtils} from "../../../../../shared/app.utils";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../core/setting/constants";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-credit-facility-type-add-edit',
  templateUrl: './credit-facility-type-add-edit.component.html',
  styleUrls: ['./credit-facility-type-add-edit.component.scss']
})
export class CreditFacilityTypeAddEditComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any;
  creditFacilityTypeUpdateDTO: CreditFacilityTypeUpdateDto = new CreditFacilityTypeUpdateDto({});
  onSelectedCreditFacilityTypeChangeSub: Subscription = new Subscription();
  onFormValuChange: Subscription = new Subscription();

  pageType: String = 'new';
  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  approveStatus = Constants.approveStatusConst;


  constructor(
    private formBuilder: FormBuilder,
    private urlEncodeService: UrlEncodeService,
    private creditFacilityTypeAddEditService: CreditFacilityTypeAddEditService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {

    this.formErrors = {
      facilityTypeName: {},
      description: {},
      status: {}
    };

    this.onSelectedCreditFacilityTypeChangeSub = this.creditFacilityTypeAddEditService.onSelectedCreditFacilityTypeChange
      .subscribe(type => {
        if (_.isEmpty(type)) {
          this.pageType = 'new';
          this.creditFacilityTypeUpdateDTO = new CreditFacilityTypeUpdateDto({});
        } else {
          this.pageType = 'edit';
          this.creditFacilityTypeUpdateDTO = new CreditFacilityTypeUpdateDto(type);
        }
        this.componentForm = this.createCreditFacilityTypeForm();
        this.onFormValuChange.unsubscribe();
        this.onFormValuChange = this.componentForm.valueChanges
          .subscribe(form => {
            this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
          })
      })
  }

  ngOnDestroy(): void {
    this.onSelectedCreditFacilityTypeChangeSub.unsubscribe();
    this.onFormValuChange.unsubscribe();
  }

  createCreditFacilityTypeForm() {
    return this.formBuilder.group({
      facilityTypeName: [this.creditFacilityTypeUpdateDTO.facilityTypeName, [Validators.required, Validators.maxLength(200)]],
      description: [this.creditFacilityTypeUpdateDTO.description, [Validators.required]],
      status: [{
        value: this.creditFacilityTypeUpdateDTO.status,
        disabled: this.pageType == 'new'
      }, [Validators.required]]
    })
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty
  }

  isApproveOrRejectValid() {
    return this.creditFacilityTypeUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING
      && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }

  saveUpdate() {
    let saveData = Object.assign({}, this.creditFacilityTypeUpdateDTO, this.componentForm.getRawValue(),
      {
        approveStatus: this.creditFacilityTypeUpdateDTO.approveStatus,
        approvedBy: this.creditFacilityTypeUpdateDTO.approvedBy,
        approvedDateStr: this.creditFacilityTypeUpdateDTO.approvedDateStr
      });
    this.creditFacilityTypeAddEditService.saveUpdateCreditFacilityType(saveData);
  }

  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.creditFacilityTypeUpdateDTO.creditFacilityTypeID},
      {approveStatus: this.approveStatus.APPROVED});
    this.creditFacilityTypeAddEditService.approveOrRejectCreditFacilityType(data);
  }

  reject() {

    let data = Object.assign({},
      {approveRejectDataID: this.creditFacilityTypeUpdateDTO.creditFacilityTypeID},
      {approveStatus: this.approveStatus.REJECTED});
    this.creditFacilityTypeAddEditService.approveOrRejectCreditFacilityType(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.creditFacilityTypeUpdateDTO.modifiedBy ? this.creditFacilityTypeUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.creditFacilityTypeUpdateDTO.createdBy ? this.creditFacilityTypeUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }

}
