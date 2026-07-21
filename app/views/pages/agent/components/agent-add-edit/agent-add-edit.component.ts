import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {AlertService} from "../../../../../core/service/common/alert.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../shared/app.utils";
import {AgentAddEditService} from "../../services/agent-add-edit.service";
import {AgentDto} from "../../dtos/agent-dto";
import {PasswordPatternValidator, PasswordValidator} from "../../validators/password.validator";

@Component({
  selector: 'app-agent-add-edit',
  templateUrl: './agent-add-edit.component.html',
  styleUrls: ['./agent-add-edit.component.scss']
})
export class AgentAddEditComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  statusConst = Constants.statusConst;
  status = Constants.status;

  componentForm: FormGroup;
  formErrors: any;

  pageType: string = 'new';
  isNew: boolean;

  agentDTO: AgentDto;

  onFormValueChangeSub = new Subscription();
  onSelectedAgentChangeSubs = new Subscription();

  title = Constants.title;
  titleConst = Constants.titleConst;

  genderConst = Constants.genderConst;
  gender = Constants.gender;

  optionsSelectGender = [
    {value: this.genderConst.F, label: this.gender.F},
    {value: this.genderConst.M, label: this.gender.M},
  ];

  optionsSelectTitle = [
    {value: this.titleConst.DR, label: this.title.DR},
    {value: this.titleConst.MR, label: this.title.MR},
    {value: this.titleConst.MRS, label: this.title.MRS},
    {value: this.titleConst.MS, label: this.title.MS},
  ];

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private cacheService: CacheService,
    private alertService: AlertService,
    private urlEncodeService: UrlEncodeService,
    private agentAddEditService: AgentAddEditService,
  ) {
  }

  ngOnInit() {

    this.formErrors = {
      userName: {},
      mobileNumber: {},
      title: {},
      gender: {},
      firstName: {},
      lastName: {},
      supervisorADUserID: {},
      nic: {},
      remark: {},
      password: {},
      passwordConfirm: {},
      email: {},
    };

    this.onSelectedAgentChangeSubs = this.agentAddEditService.onSelectedAgentChange
      .subscribe(lead => {
        if (_.isEmpty(lead)) {
          this.pageType = 'new';
          this.agentDTO = new AgentDto({});
        } else {
          this.pageType = 'edit';
          this.agentDTO = new AgentDto(lead);
        }


        this.isNew = this.pageType === 'new';

        this.componentForm = this.createLeadForm(this.isNew);
        if (this.isNew) {
          this.componentForm.setValidators([PasswordValidator.validateSamePassword, PasswordPatternValidator.validatePasswordPattern]);
        }

        this.onFormValueChangeSub.unsubscribe();
        this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
          this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
        });
      });
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onSelectedAgentChangeSubs.unsubscribe();
  }

  createLeadForm(isNew) {

    let group: any = {
      userName: [this.agentDTO.userDTO.userName, [Validators.required, Validators.maxLength(100)]],
      firstName: [this.agentDTO.userDTO.firstName, [Validators.required, Validators.maxLength(100)]],
      lastName: [this.agentDTO.userDTO.lastName, [Validators.required, Validators.maxLength(100)]],
      supervisorADUserID: [this.agentDTO.supervisorADUserID],
      title: [this.agentDTO.userDTO.title],
      gender: [this.agentDTO.userDTO.gender ? this.agentDTO.userDTO.gender : this.genderConst.M],
      nic: [this.agentDTO.nic, [Validators.maxLength(20)]],
      remark: [this.agentDTO.remark, [Validators.maxLength(4000)]],
      mobileNumber: [this.agentDTO.mobileNumber, [Validators.pattern("^((\\+94-?)|0)?[0-9]{10}$")]],
      email: [this.agentDTO.userDTO.email, [Validators.email, Validators.maxLength(200)]]
    };

    if (isNew) {
      group = {
        ...group,
        password: ['', [Validators.required, Validators.maxLength(50)]],
        passwordConfirm: ['', [Validators.required, Validators.maxLength(50)]],
      };
    }

    return this.formBuilder.group(group);
  }

  isValid() {
    return this.componentForm && this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm && this.componentForm.dirty;
  }

  getUserName() {
    let formData = this.componentForm.getRawValue();
    if (formData.userName.startsWith("ag_")) {
      return formData.userName;
    } else {
      return 'ag_' + formData.userName;
    }
  }

  saveUpdate() {
    let saveData: any = Object.assign({}, this.agentDTO);
    let formData = this.componentForm.getRawValue();

    saveData.userDTO.userName = this.getUserName();
    saveData.userDTO.firstName = formData.firstName;
    saveData.userDTO.lastName = formData.lastName;
    saveData.userDTO.title = formData.title;
    saveData.userDTO.gender = formData.gender;
    saveData.userDTO.email = formData.email;

    saveData.supervisorADUserID = formData.supervisorADUserID;
    saveData.nic = formData.nic;
    saveData.remark = formData.remark;
    saveData.mobileNumber = formData.mobileNumber;

    if (!saveData.agentID) {
      saveData.userDTO.password = formData.password;
      saveData.userDTO.status = this.statusConst.ACT;
      saveData.status = this.statusConst.ACT;
      this.agentAddEditService.addAgent(AppUtils.trim(saveData));
    } else {
      this.agentAddEditService.updateAgent(AppUtils.trim(saveData));
    }
  }

}
