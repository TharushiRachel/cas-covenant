import {
  Component,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ApplicationService } from "src/app/core/service/application/application.service";

import { Subscription } from "rxjs";
import { Constants } from "src/app/core/setting/constants";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import * as _ from "lodash";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { CacheService } from "src/app/core/service/data/cache.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { CurrencyPipe } from "@angular/common";
import { Router } from "@angular/router";
import { ACAEPaperService } from "src/app/views/pages/acae/services/acae-paper.service";
import { ACAESharedService } from "src/app/views/pages/acae/services/acae-shared.service";
import { PoolUpdateDto } from "../../../dto/pool-update-dto";
import { CommitteeService } from "../../../service/committee.service";

@Component({
  selector: "app-committee-pool-add-edit",
  templateUrl: "./committee-pool-add-edit.component.html",
  styleUrls: ["./committee-pool-add-edit.component.scss"],
})
export class CommitteePoolAddEditComponent
  implements OnInit, OnChanges, OnDestroy
{
  componentForm: FormGroup;
  formErrors: any;
  optionsSelect = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];
  poolUpdateDTO: PoolUpdateDto = new PoolUpdateDto({});
  onSelectedPoolChangeSub: Subscription = new Subscription();
  onFormValuChange: Subscription = new Subscription();
  pageType: String = "new";
  tempUserLOV: any[];
  onForwardUserGroupChangeSub: Subscription = new Subscription();
  userList: any[] = [];
  userGroup: any[] = [];

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  approveStatus = Constants.approveStatusConst;

  //new started
  content: any;

  constructor(
    private formBuilder: FormBuilder,
    private applicationService: ApplicationService,
    public acaeDetailsEditStatusModelRef: MDBModalRef,
    private acaePaperService: ACAEPaperService,
    private alertService: AlertService,
    public acaeSharedService: ACAESharedService,
    private committeeService: CommitteeService,
    private router: Router
  ) {
    this.formErrors = {
      directorName: {},
      nic: {},
      dateOfBirthStr: {},
    };
  }
  status: any;

  formError: any = {};

  userPool: any;
  statusConst = Constants.statusConst;

  ngOnInit(): void {
    this.formError = {
      rejectUserGroup: {},
      rejectUser: {},
    };

    this.componentForm = this.loadInitailforwardACAEForm();
    this.componentForm.controls.userGroup.enable();
    this.loadCAEForwardUserGroupLOV();
    this.onForwardUserGroupChangeSub =
      this.componentForm.controls.userGroup.valueChanges.subscribe(
        (value: any) => {
          this.getforwardUserGroupValue(value);
          this.componentForm.controls.userName.setValidators([
            Validators.required,
          ]);
          this.componentForm.controls.userName.reset();
          this.componentForm.updateValueAndValidity();
        }
      );
  }

  ngOnChanges(changes: SimpleChanges): void {}

  loadInitailforwardACAEForm() {
    return this.formBuilder.group({
      userGroup: [""],
      userName: [""],
    });
  }

  loadCAEForwardUserGroupLOV() {
    this.userList = [];
    var loggedInUserWorkFlowByHighLevelRQ = {
      loggedInUserUpmGroupCode: "71",
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      level: "HIGH",
    };
    this.acaePaperService
      .getACAEForwardUserGroupLOVService(loggedInUserWorkFlowByHighLevelRQ)
      .subscribe(
        (response: any) => {
          if (response) {
            this.userGroup = response.map(
              (item: { groupCode: any; referenceName: any }) => ({
                value: item.groupCode,
                label: item.referenceName,
                referenceName: item.referenceName,
              })
            );
          }
        },
        (error) => {
          console.error(error);
          this.alertService.showToaster(
            "Please contact system administrator",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
  }

  getforwardUserGroupValue = async (value: any) => {
    this.userList = [];
    this.userList = await this.loadForwardOrRejectedLOV(value);
  };

  async loadForwardOrRejectedLOV(value: any) {
    let eligibleUsers = [];
    let requiredDivCodes = [];

    requiredDivCodes.push(this.applicationService.getLoggedInUserDivCode());

    let uniqueDivCodes = [...new Set(requiredDivCodes)];

    for (const divCode of uniqueDivCodes) {
      let users: [] =
        await this.applicationService.getUserDetailListFormBranchAuthorityLevel(
          {
            solId: divCode,
            roleId: value,
            appCode: "CAS",
          }
        );
      if (users && Array.isArray(users)) {
        eligibleUsers = [...users, ...eligibleUsers];
      }
    }
    //get unique records
    this.tempUserLOV = _.uniqBy(eligibleUsers, (i) => i.userID);

    let userList = [];
    _.forEach(_.sortBy(this.tempUserLOV, ["firstName"]), async (user) => {
      if (!_.isNull(user.userID)) {
        // let branch = await AppUtils.getBranchFromBranchCode(this.allBranches, user.divCode);
        // get user details that except login user
        if (this.applicationService.getLoggedInUserUserID() != user.userID) {
          userList.push({
            key: user.adUserID + "-" + user.firstName + "-" + user.lastName,
            value: user.adUserID + "-" + user.firstName + " " + user.lastName,
            label: user.userID
              ? user.firstName + " " + user.lastName
              : "No Users",
          });
        }
      }
    });
    return userList;
  }

  savePoolUser() {
    let { userName, userGroup } = this.componentForm.getRawValue();
    let [usernamePart, userDisplayNamePart] = userName.split("-");
    let selectedGroup = this.userGroup.find(
      (group) => group.value === userGroup
    );
    let data = {
      userId: 0,
      userName: usernamePart,
      userDisplayName: userDisplayNamePart,
      groupCode: userGroup,
      poolId: 1,
      referenceName: selectedGroup ? selectedGroup.referenceName : "",
      userStatus: this.statusConst.ACT,
    };

    this.committeeService.savePool(data).then(
      (data: any) => {
        this.alertService.showToaster(
          "Pool user has been saved successfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );

        setTimeout(() => {
          this.loadPoolTablePage();
        }, 1500);
      },
      (err: any) => {
        this.alertService.showToaster(
          "Failed to save pool user.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    );
  }

  loadPoolTablePage() {
    this.router.navigate(["/committee/pool"]);
  }

  ngOnDestroy(): void {
    this.onSelectedPoolChangeSub.unsubscribe();
    this.onFormValuChange.unsubscribe();
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.poolUpdateDTO.modifiedBy
      ? this.poolUpdateDTO.modifiedBy ==
          this.applicationService.getLoggedInUserUserName()
      : this.poolUpdateDTO.createdBy
      ? this.poolUpdateDTO.createdBy ==
        this.applicationService.getLoggedInUserUserName()
      : false;
  }
}
