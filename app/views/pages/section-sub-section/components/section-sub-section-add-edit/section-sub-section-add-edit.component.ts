import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import * as _ from "lodash";
import { AppUtils } from "../../../../../shared/app.utils";
import { SectionSubSectionUpdateDto } from "../../dto/section-sub-section-update-dto";
import { SectionSubSectionAddEditService } from "../../services/section-sub-section-add-edit.service";
import { Constants } from "../../../../../core/setting/constants";
import { SETTINGS } from "../../../../../core/setting/commons.settings";
import { ApplicationService } from "../../../../../core/service/application/application.service";

@Component({
  selector: "app-section-sub-section-add-edit",
  templateUrl: "./section-sub-section-add-edit.component.html",
  styleUrls: ["./section-sub-section-add-edit.component.scss"],
})
export class SectionSubSectionAddEditComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  formErrors: any;
  selectedUpdateDTO: SectionSubSectionUpdateDto =
    new SectionSubSectionUpdateDto({});
  onSelectedItemChange: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();

  pageType: string = "new";

  optionsSelect = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];

  approveStatus = Constants.approveStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  systemPrivileges: any = {};
  privilegeCategories: any = [];
  checkedPrivilegeIDs: any = [];

  constructor(
    private formBuilder: FormBuilder,
    private addEditService: SectionSubSectionAddEditService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.formErrors = {
      upcSectionName: {},
      upcSectionDescription: {},
      status: {},
    };

    this.onSelectedItemChange =
      this.addEditService.onSelectedUPCSectionChange.subscribe((item) => {
        if (_.isEmpty(item)) {
          this.pageType = "new";
          this.selectedUpdateDTO = new SectionSubSectionUpdateDto({});
        } else {
          this.pageType = "edit";
          this.selectedUpdateDTO = new SectionSubSectionUpdateDto(item);
        }

        this.componentForm = this.createRoleForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges.subscribe(
          (form) => {
            this.formErrors = AppUtils.getFormErrors(
              this.componentForm,
              this.formErrors
            );
          }
        );
      });
  }

  ngOnDestroy(): void {
    this.onSelectedItemChange.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  createRoleForm() {
    return this.formBuilder.group({
      upcSectionName: [
        this.selectedUpdateDTO.upcSectionName,
        [Validators.required, Validators.maxLength(200)],
      ],
      upcSectionDescription: [
        this.selectedUpdateDTO.upcSectionDescription,
        [Validators.maxLength(250)],
      ],
      status: [
        {
          value: this.selectedUpdateDTO.status,
          disabled: this.pageType == "new",
        },
        [Validators.required],
      ],
    });
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  isApproveOrRejectValid() {
    return (
      this.selectedUpdateDTO.approveStatus ==
        Constants.approveStatusConst.PENDING &&
      this.pageType == "edit" &&
      !this.isModifiedOrCreatedByLoggedInUser()
    );
  }

  saveUpdate() {
    let formData = this.componentForm.getRawValue();

    let saveData = Object.assign({}, this.selectedUpdateDTO, formData, {
      upcSectionName: formData.upcSectionName.trim(),
      approveStatus: this.selectedUpdateDTO.approveStatus,
      approvedBy: this.selectedUpdateDTO.approvedBy,
      approvedDateStr: this.selectedUpdateDTO.approvedDateStr,
    });
    
    this.addEditService.saveUpdateUPCSection(saveData);
  }

  approve() {
    let data = Object.assign(
      {},
      { approveRejectDataID: this.selectedUpdateDTO.upcSectionID },
      { approveStatus: this.approveStatus.APPROVED }
    );
    this.addEditService.approveOrRejectUPCSection(data);
  }

  reject() {
    let data = Object.assign(
      {},
      { approveRejectDataID: this.selectedUpdateDTO.upcSectionID },
      { approveStatus: this.approveStatus.REJECTED }
    );
    this.addEditService.approveOrRejectUPCSection(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.selectedUpdateDTO.modifiedBy
      ? this.selectedUpdateDTO.modifiedBy ==
          this.applicationService.getLoggedInUserUserName()
      : this.selectedUpdateDTO.createdBy
      ? this.selectedUpdateDTO.createdBy ==
        this.applicationService.getLoggedInUserUserName()
      : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }
}
