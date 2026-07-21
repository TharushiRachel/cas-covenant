import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserDelegateAuthorityUpdateDto} from "../../dto/user-delegate-authority-update-dto";
import {Subscription} from "rxjs";
import {UserDelegatedAuthorityAddEditService} from "../../services/user-delegated-authority-add-edit.service";
import * as _ from 'lodash';
import {AppUtils} from "../../../../../shared/app.utils";
import {Constants} from "../../../../../core/setting/constants";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {CurrencyService} from "../../../../../core/service/common/currency.service";
import {NumberValidator} from "../../../../../shared/validators/number.validator";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-user-delegated-authority-add-edit',
  templateUrl: './user-delegated-authority-add-edit.component.html',
  styleUrls: ['./user-delegated-authority-add-edit.component.scss']
})
export class UserDelegatedAuthorityAddEditComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any;
  userDelegatedAuthorityUpdateDTO: UserDelegateAuthorityUpdateDto = new UserDelegateAuthorityUpdateDto({});
  onSelectedUserDaChangeSub: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();

  pageType: string = 'new';

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  approveStatus = Constants.approveStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  constructor(private formBuilder: FormBuilder,
              private userDelegatedAuthorityAddEditService: UserDelegatedAuthorityAddEditService,
              public currencyService: CurrencyService,
              private applicationService: ApplicationService) {
  }

  ngOnInit() {

    this.formErrors = {
      userName: {},
      maxAmount: {},
      cleanAmount:{},
      description: {},
      status: {}
    };


    this.onSelectedUserDaChangeSub = this.userDelegatedAuthorityAddEditService.onSelectedUserDaChange
      .subscribe(userDA => {
        if (_.isEmpty(userDA)) {
          this.pageType = 'new';
          this.userDelegatedAuthorityUpdateDTO = new UserDelegateAuthorityUpdateDto({});
        } else {
          this.pageType = 'edit';
          this.userDelegatedAuthorityUpdateDTO = new UserDelegateAuthorityUpdateDto(userDA);
        }
        this.componentForm = this.createUserDaForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges
          .subscribe(form => {
            this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
          })
      })
  }

  ngOnDestroy(): void {
    this.onSelectedUserDaChangeSub.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  isApproveOrRejectValid() {
    return this.userDelegatedAuthorityUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }


  createUserDaForm() {
    return this.formBuilder.group({
      userName: [this.userDelegatedAuthorityUpdateDTO.userName, [Validators.required, Validators.maxLength(200)]],
      maxAmount: [this.currencyService.getFormattedAmount(this.userDelegatedAuthorityUpdateDTO.maxAmount), [Validators.required, NumberValidator.isCommaSeparatedValue]],
      cleanAmount: [this.currencyService.getFormattedAmount(this.userDelegatedAuthorityUpdateDTO.cleanAmount), [Validators.required, NumberValidator.isCommaSeparatedValue]],
      description: [this.userDelegatedAuthorityUpdateDTO.description, [Validators.required]],
      status: [{
        value: this.userDelegatedAuthorityUpdateDTO.status,
        disabled: this.pageType == 'new'
      }, [Validators.required]]
    })
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  saveUpdate() {
    let saveData = Object.assign({}, this.userDelegatedAuthorityUpdateDTO, this.componentForm.getRawValue(),
      {
        approveStatus: this.userDelegatedAuthorityUpdateDTO.approveStatus,
        approvedBy: this.userDelegatedAuthorityUpdateDTO.approvedBy,
        approvedDateStr: this.userDelegatedAuthorityUpdateDTO.approvedDateStr
      });

    saveData.maxAmount = this.currencyService.getAmountFromFormattedString(saveData.maxAmount);
    saveData.cleanAmount  = this.currencyService.getAmountFromFormattedString(saveData.cleanAmount);

    this.userDelegatedAuthorityAddEditService.saveUpdateUserDa(saveData);
  }

  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.userDelegatedAuthorityUpdateDTO.userDaID},
      {approveStatus: this.approveStatus.APPROVED});
    this.userDelegatedAuthorityAddEditService.approveOrRejectUserDa(data);
  }

  reject() {

    let data = Object.assign({},
      {approveRejectDataID: this.userDelegatedAuthorityUpdateDTO.userDaID},
      {approveStatus: this.approveStatus.REJECTED});
    this.userDelegatedAuthorityAddEditService.approveOrRejectUserDa(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.userDelegatedAuthorityUpdateDTO.modifiedBy ? this.userDelegatedAuthorityUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.userDelegatedAuthorityUpdateDTO.createdBy ? this.userDelegatedAuthorityUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }

}
