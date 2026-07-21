import { Component, OnInit } from "@angular/core";
import { CommitteeService } from "../../../service/committee.service";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { AppUtils } from "src/app/shared/app.utils";
import { Constants } from "src/app/core/setting/constants";
import * as _ from "lodash";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Component({
  selector: "app-save-committee-type",
  templateUrl: "./save-committee-type.component.html",
  styleUrls: ["./save-committee-type.component.scss"],
})
export class SaveCommitteeTypeComponent implements OnInit {
  formData: any = {
    committeeId: 0,
    committeeTypeName: "",
    committeeDescription: "",
    status: "",
    isSystem: 0,
  };
  componentForm: FormGroup;
  formErrors: any;
  onSelectedTypeChangeSub: Subscription = new Subscription();
  onFormValuChange: Subscription = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  pageType: String = "new";

  optionsSelect = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];

  systemOptionsSelect = [
    { value: 0, label: "No" },
    { value: 1, label: "Yes" },
  ];

  constructor(
    private formBuilder: FormBuilder,
    private committeeService: CommitteeService,
    private router: Router,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.formErrors = {
      committeeTypeName: {},
      committeeDescription: {},
      status: {},
    };

    this.onSelectedTypeChangeSub =
      this.committeeService.onSelectedTypeChange.subscribe((type) => {
        if (_.isEmpty(type)) {
          this.pageType = "new";
        } else {
          this.pageType = "edit";
          this.formData = type;
        }

        this.componentForm = this.createCommitteTypeForm();
        this.onFormValuChange.unsubscribe();
        this.onFormValuChange = this.componentForm.valueChanges.subscribe(
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
    this.onSelectedTypeChangeSub.unsubscribe();
    this.onFormValuChange.unsubscribe();
  }

  createCommitteTypeForm() {
    return this.formBuilder.group({
      committeeTypeName: [
        this.formData.committeeTypeName,
        [Validators.required],
      ],
      committeeDescription: [
        this.formData.committeeDescription,
        [Validators.required],
      ],
      status: [this.formData.status, [Validators.required]],
      isSystem: [this.formData.isSystem],
    });
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  saveType() {
    let saveData = Object.assign(
      {},
      this.formData,
      this.componentForm.getRawValue()
    );

    this.committeeService.saveCommitteeType(saveData).then(
      (res: any) => {
        this.alertService.showToaster(
          "Committee type has been saved successfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );

        setTimeout(() => {
          this.loadCommitteType();
        }, 1500);
      },
      (err: any) => {
        this.alertService.showToaster(
          "Failed to save committee type.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    );
  }

  loadCommitteType() {
    this.router.navigate(["/committee/type"]);
  }
}
