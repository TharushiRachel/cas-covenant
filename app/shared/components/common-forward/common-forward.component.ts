import { Component, OnDestroy, OnInit } from "@angular/core";
import { Constants } from "../../../core/setting/constants";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subject, Subscription } from "rxjs";
import * as _ from "lodash";
import { ApplicationService } from "../../../core/service/application/application.service";
import { MasterDataService } from "../../../core/service/data/master-data.service";
import { CacheService } from "../../../core/service/data/cache.service";
import { AppUtils } from "../../app.utils";
import { SETTINGS } from "../../../core/setting/commons.settings";
import { CommentCacheService } from "../../../core/service/data/comment-cache.service";
import { AlertService } from "../../../core/service/common/alert.service";
import { SmeServiceService } from "src/app/views/pages/sme/service/sme-service.service";

@Component({
  selector: "app-common-forward",
  templateUrl: "./common-forward.component.html",
  styleUrls: ["./common-forward.component.scss"],
})
export class CommonForwardComponent implements OnInit, OnDestroy {
  heading: any;
  commentCacheKey: string;
  actionMessage: any;
  returnUserList: any[] = [];
  isForward: boolean = false;
  isReturn: boolean = false;
  isESG: boolean = false;
  isCommitteePaperForward: boolean = false;
  isTransfer: boolean = false;
  showUsersOnlyOption: true;
  showDivisionOnlyOption: true;
  content: any = {};
  action: Subject<any> = new Subject<any>();
  forwardTypeConst = Constants.ForwardTypeConst;
  forwardType = Constants.ForwardType;
  selectedForwardType = Constants.ForwardTypeConst.DIRECT_USER;
  componentForm: FormGroup;
  formError: any;
  onUpmGroupCodeChangeSub = new Subscription();
  onDepartmentGroupChangeSub: Subscription = new Subscription();
  onFormChangeSub = new Subscription();
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  defaultWorkflowUpmGroupCode = Constants.defaultWorkflowUpmGroupCode;
  defaultWorkflowUpmGroupsName = Constants.defaultWorkflowUpmGroupsName;
  applicationSecurityWorkClass = Constants.applicationSecurityWorkClass;
  userGroups: any[] = [];
  userOption: any[] = [];
  userGroupOptions: any[] = [];
  userDetails: any[] = [];
  departmentGroupOptions: any[] = [];
  defaultApplicationFormForwardUPMGroups: any[] = [];
  assistants: any[] = [];
  allBranches: any[] = [];
  committeeTypeList: any[] = [];
  committeeList: any[] = [];
  committeeUserList: any[] = [];
  committeeLevelList: any[] = [];
  assignedUser: any = {};
  committeePaperStatusConst = Constants.committeePaperStatusConst;
  casBranchDepartments = [];
  riskDepartment: any = {};
  loggedInUserBranchDepartment;
  bccInquirerWorkClass: number = 0;
  loggedInUserWorkClass: number = 0;
  insuranceMessage: String | null = null;
  isQuestionsSubmitted: boolean = false; // State to track if questions are submitted
  isQuestionsVisible: boolean = false; // Flag to control visibility of <app-sme-questions>
  questionsAndAnswers: any[] = []; // Array to store filtered questions and answers
  isQuestionsLoading: boolean = true;

  constructor(
    public mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private cacheService: CacheService,
    private alertService: AlertService,
    private masterDataService: MasterDataService,
    private applicationService: ApplicationService,
    private commentCacheService: CommentCacheService,
    private smeService: SmeServiceService,
  ) {}

  ngOnInit() {
    this.allBranches = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES,
    );
    this.assistants = this.cacheService.getData(
      Constants.masterDataKey.CAS_APPLICATION_USER_ASSISTANTS,
    );
    this.loggedInUserBranchDepartment = AppUtils.getBranchFromBranchCode(
      this.allBranches,
      this.applicationService.getLoggedInUserDivCode(),
    );

    this.casBranchDepartments = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCH_DEPARTMENT_LIST,
    );
    this.riskDepartment = AppUtils.getCasBranchDepartment(
      this.casBranchDepartments,
      SETTINGS.BRANCH_DEPARTMENT_LIST.RISK_MANAGEMENT_AND_COMPLIENCE,
    );
    this.defaultApplicationFormForwardUPMGroups = this.masterDataService
      .getSystemParameter(
        Constants.systemParamKey.DEFAULT_APPLICATION_FORM_FORWARDING_UPM_GROUPS,
      )
      .split(",");

    this.committeeTypeList = this.cacheService.getData(
      Constants.masterDataKey.CAS_COMMITTEE_TYPE_LIST,
    );
    this.committeeList = this.cacheService.getData(
      Constants.masterDataKey.CAS_COMMITTEE_LIST,
    );
    this.committeeLevelList = this.cacheService.getData(
      Constants.masterDataKey.CAS_COMMITTEE_LEVEL_LIST,
    );
    this.bccInquirerWorkClass = this.cacheService.getData(
      Constants.masterDataKey.CAS_BCC_INQUIRER_WORK_CLASS,
    );
    this.loggedInUserWorkClass =
      this.applicationService.getLoggedInUserUPMGroupCode();

    this.returnUserList = this.content.returnUserList;
    this.formError = {
      userGroup: {},
      user: {},
      upmUserGroups: {},
      departmentGroup: {},
      comment: {},
      userConfirmation: {},
    };

    if (this.isReturn && this.returnUserList.length > 0) {
      this.userOption = [];
      let userList = [];
      _.forEach(this.returnUserList, (user) => {
        if (!_.isNull(user.assignUserID)) {
          let branch = AppUtils.getBranchFromBranchCode(
            this.allBranches,
            user.assignUserDivCode,
          );
          userList.push({
            value: user.assignUserID,
            label: user.assignUserDisplayName
              ? user.assignUserDisplayName +
                `${branch ? " - " + branch.branchName : ""}`
              : "No Users",
          });
        }
      });
      this.userOption = userList;
    }

    let loggedInUserWorkFlowRQ = {
      loggedInUserUpmGroupCode:
        this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      workFlowTemplateID: this.content.workflowTemplateID,
    };

    this.applicationService
      .getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(
        loggedInUserWorkFlowRQ,
      )
      .then((response) => {
        this.userGroups = [];
        this.userGroupOptions = [];

        if (this.assistants && this.assistants.length > 0) {
          this.userGroupOptions.push({
            value: this.defaultWorkflowUpmGroupCode.ASSISTANT,
            label: this.defaultWorkflowUpmGroupsName.ASSISTANT,
          });
        }

        this.userGroups = response;
        _.forEach(this.userGroups, (group) => {
          this.userGroupOptions.push({
            value: group.groupCode,
            label: group.referenceName,
          });
        });

        //Show only BCC user group for MD when the facility paper is marked as a committee paper
        if (this.content.isCommittee == "Y") {
          if (this.loggedInUserWorkClass == this.bccInquirerWorkClass) {
            this.userGroupOptions = [];
            if (this.committeeTypeList && this.committeeTypeList.length > 0) {
              _.forEach(this.committeeTypeList, (committee) => {
                if (committee.committeeTypeName == "BCC") {
                  this.userGroupOptions.push({
                    value: committee.committeeTypeId,
                    label: committee.committeeTypeName,
                  });
                }
              });
            }
          }
          //Show other committees except BCC for Chief Managers (72) & AGMs (73)
          //when the facility paper is marked as a committee paper
          if (
            this.loggedInUserWorkClass ==
              this.applicationSecurityWorkClass.CM ||
            this.loggedInUserWorkClass == this.applicationSecurityWorkClass.AGM
          ) {
            this.userGroupOptions = [];
            if (this.committeeTypeList && this.committeeTypeList.length > 0) {
              _.forEach(this.committeeTypeList, (committee) => {
                if (committee.committeeTypeName != "BCC") {
                  this.userGroupOptions.push({
                    value: committee.committeeTypeId,
                    label: committee.committeeTypeName,
                  });
                }
              });
              _.forEach(this.userGroups, (group) => {
                this.userGroupOptions.push({
                  value: group.groupCode,
                  label: group.referenceName,
                });
              });
            }
          }
        }
      });

    _.forEach(this.allBranches, (branch) => {
      if (!_.isNull(branch.branchCode)) {
        this.departmentGroupOptions.push({
          value: branch.branchCode,
          label: branch.branchName ? branch.branchName : "No Departments",
        });
      }
    });

    this.createComponentForm();

    this.onUpmGroupCodeChangeSub.unsubscribe();
    this.onUpmGroupCodeChangeSub =
      this.componentForm.controls.userGroup.valueChanges.subscribe(
        (value: any) => {
          if (value) {
            if (this.selectedForwardType == this.forwardTypeConst.DIRECT_USER) {
              this.getEligibleUsers(value);
            }
          }
          this.componentForm.controls.user.setValidators([Validators.required]);
          this.componentForm.controls.user.reset();
          this.componentForm.updateValueAndValidity();
        },
      );

    this.onFormChangeSub =
      this.componentForm.controls.forwardType.valueChanges.subscribe(
        (value: any) => {
          this.selectedForwardType = value;
          switch (value) {
            case this.forwardTypeConst.DIRECT_USER: {
              this.componentForm.controls.userGroup.setValidators([
                Validators.required,
              ]);
              this.componentForm.controls.userGroup.reset();

              this.componentForm.controls.user.setValidators([
                Validators.required,
              ]);
              this.componentForm.controls.user.reset();

              this.componentForm.controls.upmUserGroups.setValidators(null);
              this.componentForm.controls.upmUserGroups.reset();

              this.componentForm.controls.departmentGroup.setValidators(null);
              this.componentForm.controls.departmentGroup.reset();

              this.componentForm.controls.isUsersOnly.enable();
              this.componentForm.controls.isDivisionOnly.enable();

              if (this.isRiskDepartmentUserLogged()) {
                this.componentForm.get("isUsersOnly").setValue(false);
                this.componentForm.get("isPublic").setValue(false);
                this.componentForm.get("isDivisionOnly").setValue(true);
              }

              break;
            }

            case this.forwardTypeConst.SAME_SOL_USER_GROUP: {
              this.componentForm.controls.userGroup.setValidators(null);
              this.componentForm.controls.userGroup.reset();

              this.componentForm.controls.user.setValidators(null);
              this.componentForm.controls.user.reset();

              this.componentForm.controls.upmUserGroups.enable();
              this.componentForm.controls.upmUserGroups.setValidators([
                Validators.required,
              ]);
              this.componentForm.controls.upmUserGroups.reset();

              this.componentForm.controls.departmentGroup.setValidators([
                Validators.required,
              ]);
              this.componentForm.controls.departmentGroup.setValue(
                this.applicationService.getLoggedInUserDivCode(),
              );
              this.componentForm.controls.departmentGroup.disable();

              this.componentForm.controls.isUsersOnly.enable();
              this.componentForm.get("isUsersOnly").setValue(false);
              this.componentForm.controls.isUsersOnly.disable();
              this.componentForm.controls.isDivisionOnly.enable();

              if (this.isRiskDepartmentUserLogged()) {
                this.componentForm.get("isPublic").setValue(false);
                this.componentForm.get("isDivisionOnly").setValue(true);
              }

              break;
            }

            case this.forwardTypeConst.OTHER_SOL_USER_GROUP: {
              this.componentForm.controls.userGroup.setValidators(null);
              this.componentForm.controls.userGroup.reset();

              this.componentForm.controls.user.setValidators(null);
              this.componentForm.controls.user.reset();

              this.componentForm.controls.upmUserGroups.enable();
              this.componentForm.controls.upmUserGroups.setValidators([
                Validators.required,
              ]);
              this.componentForm.controls.upmUserGroups.setValue([
                ...this.defaultApplicationFormForwardUPMGroups,
              ]);
              this.componentForm.controls.upmUserGroups.disable();

              this.componentForm.controls.departmentGroup.enable();
              this.componentForm.controls.departmentGroup.setValidators([
                Validators.required,
              ]);
              this.componentForm.controls.departmentGroup.reset();

              this.componentForm.controls.isUsersOnly.enable();
              this.componentForm.controls.isDivisionOnly.enable();
              this.componentForm.get("isUsersOnly").setValue(false);
              this.componentForm.get("isPublic").setValue(true);
              this.componentForm.get("isDivisionOnly").setValue(false);
              this.componentForm.controls.isUsersOnly.disable();
              this.componentForm.controls.isDivisionOnly.disable();

              break;
            }
          }
          this.componentForm.updateValueAndValidity();
        },
      );

    this.onFormChangeSub =
      this.componentForm.controls.isUsersOnly.valueChanges.subscribe((data) => {
        if (data) {
          this.componentForm.get("isDivisionOnly").setValue(false);
          this.componentForm.get("isPublic").setValue(false);
        } else {
          this.componentForm.get("isPublic").setValue(true);
        }
      });

    this.onFormChangeSub =
      this.componentForm.controls.isDivisionOnly.valueChanges.subscribe(
        (data) => {
          if (data) {
            this.componentForm.get("isUsersOnly").setValue(false);
            this.componentForm.get("isPublic").setValue(false);
          } else {
            this.componentForm.get("isPublic").setValue(true);
          }
        },
      );

    this.onFormChangeSub =
      this.componentForm.controls.user.valueChanges.subscribe((value: any) => {
        if (value) {
          if (!_.isNumber(value) && value.substring(0, 2) == "CA") {
            //  console.log("Forwarding facility paper for Committee Approval...");
            this.isCommitteePaperForward = true;
          } else {
            this.assignedUser = AppUtils.getFacilityPaperAssignedUserFromUserID(
              this.userDetails,
              value,
            );

            if (
              _.isEmpty(this.assignedUser) &&
              this.isReturn &&
              this.returnUserList.length > 0
            ) {
              //Set Assign user and user group from return list and
              let returnUser = AppUtils.getUserFromAssignUserID(
                this.returnUserList,
                value,
              );

              if (!_.isEmpty(returnUser)) {
                this.assignedUser = Object.assign(
                  {},
                  {
                    firstName: returnUser.assignUserDisplayName,
                    userID: returnUser.assignUserID,
                    adUserID: returnUser.assignUser,
                    divCode: returnUser.assignUserDivCode,
                  },
                );
                this.componentForm.controls.userGroup.setValue(
                  returnUser.assignUserUpmGroupCode,
                  {
                    onlySelf: true,
                    emitEvent: false,
                  },
                );
              }
            }

            this.componentForm.controls.isUsersOnly.enable();
            this.componentForm.controls.isDivisionOnly.enable();

            if (!_.isEmpty(this.assignedUser)) {
              if (
                this.assignedUser.divCode !=
                this.applicationService.getLoggedInUserDivCode()
              ) {
                this.componentForm.get("isUsersOnly").setValue(false);
                this.componentForm.get("isDivisionOnly").setValue(false);
                this.componentForm.get("isPublic").setValue(true);
                this.componentForm.controls.isUsersOnly.disable();
                this.componentForm.controls.isDivisionOnly.disable();
              } else {
                this.componentForm.controls.isUsersOnly.enable();
                this.componentForm.controls.isDivisionOnly.enable();
                if (this.isRiskDepartmentUserLogged()) {
                  this.componentForm.get("isUsersOnly").setValue(false);
                  this.componentForm.get("isPublic").setValue(false);
                  this.componentForm.get("isDivisionOnly").setValue(true);
                }
              }
            }
          }
        } else {
          this.assignedUser = null;
          this.componentForm.setErrors({ invalid: true });
        }
      });

    this.onFormChangeSub =
      this.componentForm.controls.comment.valueChanges.subscribe((data) => {
        this.commentCacheService.cacheComment(this.commentCacheKey, data);
      });

    this.checkQuestionsVisibility();
  }

  createComponentForm() {
    this.componentForm = this.formBuilder.group({
      userGroup: [""],
      user: [""],
      upmUserGroups: [""],
      departmentGroup: [""],
      comment: [
        this.commentCacheService.commentDataCache[this.commentCacheKey]
          ? this.commentCacheService.commentDataCache[this.commentCacheKey]
          : "",
        [Validators.required, Validators.maxLength(4000)],
      ],
      forwardType: [this.forwardTypeConst.DIRECT_USER, [Validators.required]],
      isUsersOnly: [false],
      isDivisionOnly: [false],
      isPublic: [true],
      userConfirmation: [false, [Validators.required]],
    });

    if (this.isRiskDepartmentUserLogged()) {
      this.componentForm.get("isUsersOnly").setValue(false);
      this.componentForm.get("isPublic").setValue(false);
      this.componentForm.get("isDivisionOnly").setValue(true);
    }

    if (
      !this.applicationService.getIsAssistant() &&
      this.isReturn &&
      this.returnUserList.length > 0
    ) {
      let initialReturnUser = this.returnUserList[0];
      this.componentForm
        .get("userGroup")
        .setValue(initialReturnUser.assignUserUpmGroupCode);
      this.componentForm.get("user").setValue(initialReturnUser.assignUserID);
      this.assignedUser.firstName = initialReturnUser.assignUserDisplayName;
      this.assignedUser.userID = initialReturnUser.assignUserID;
      this.assignedUser.adUserID = initialReturnUser.assignUser;
      this.assignedUser.divCode = initialReturnUser.assignUserDivCode;

      if (
        this.assignedUser.divCode !=
        this.applicationService.getLoggedInUserDivCode()
      ) {
        this.componentForm.get("isUsersOnly").setValue(false);
        this.componentForm.get("isDivisionOnly").setValue(false);
        this.componentForm.get("isPublic").setValue(true);
        this.componentForm.controls.isUsersOnly.disable();
        this.componentForm.controls.isDivisionOnly.disable();
      }
    }

    if (this.applicationService.getIsAssistant()) {
      this.assignedUser.userID =
        this.applicationService.getLoggedInCASUserSupervisorUserID();
      this.assignedUser.divCode =
        this.applicationService.getLoggedInCASUserSupervisorDivCode();
      this.assignedUser.adUserID =
        this.applicationService.getLoggedInCASUserSupervisorAdUserID();
      this.assignedUser.firstName =
        this.applicationService.getLoggedInCASUserSupervisorDisplayName();
      this.assignedUser.upmGroupCode =
        this.applicationService.getLoggedInCASUserSupervisorUPMGroupCode();

      this.userOption = [
        {
          value: this.assignedUser.assignUserID,
          label: this.assignedUser.firstName,
        },
      ];

      this.componentForm.get("isPublic").setValue(false);
      this.componentForm.get("isUsersOnly").setValue(true);
      this.componentForm.get("isDivisionOnly").setValue(false);

      this.componentForm
        .get("userGroup")
        .setValue(
          this.applicationService.getLoggedInCASUserSupervisorUPMGroupCode(),
        );
      this.componentForm
        .get("user")
        .setValue(this.applicationService.getLoggedInCASUserSupervisorUserID());

      this.componentForm.controls.isPublic.disable();
      this.componentForm.controls.isDivisionOnly.disable();
      this.componentForm.controls.isUsersOnly.disable();
      this.componentForm.controls.forwardType.disable();
      this.componentForm.controls.user.disable();
      this.componentForm.controls.userGroup.disable();
    }
  }

  forward() {
    let actionMessage;
    let updateRequest = {};

    let remarkData = {
      comment: this.insuranceMessage
        ? this.componentForm.controls.comment.value +
          "\n" +
          this.insuranceMessage
        : this.componentForm.controls.comment.value,
      createdUserID: this.applicationService.getLoggedInUserUserID(),
      createdUser: this.applicationService.getLoggedInUserUserName(),
      createdUserDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
      createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
      isUsersOnly: this.componentForm.controls.isUsersOnly.value ? "Y" : "N",
      isDivisionOnly: this.componentForm.controls.isDivisionOnly.value
        ? "Y"
        : "N",
      isPublic: this.componentForm.controls.isPublic.value ? "Y" : "N",
    };

    switch (this.selectedForwardType) {
      case this.forwardTypeConst.DIRECT_USER: {
        let userControlValue = this.componentForm.controls.user.value;
        if (this.isCommitteePaperForward) {
          // if (userControlValue.substring(0,2) == "CA"){
          let inputCommitteeData = userControlValue.split("/");
          let committeePaperDTOList = [];
          let currentRegLevelID = 0;
          let currentAltLevelID = 0;
          let currentAltRecommendedCount = 0;
          let currentRegRecommendedCount = 0;

          if (inputCommitteeData[1] == "BCC") {
            // actionMessage =
            //   "Forwarded to " + inputCommitteeData[1] + " Committee";
            actionMessage = "Forwarded to Board Credit Committee";
            committeePaperDTOList.push({
              committeeType: inputCommitteeData[1],
              committeeID: inputCommitteeData[2],
              committeeName: inputCommitteeData[3],
              committeeTypeID: inputCommitteeData[4],
              currentRegLevelID: currentRegLevelID,
              currentAltLevelID: currentAltLevelID,
              currentAltRecommendedCount: currentAltRecommendedCount,
              currentRegRecommendedCount: currentRegRecommendedCount,
            });

            updateRequest = Object.assign(
              {},
              updateRequest,
              {
                actionMessage: actionMessage,
                forwardType: this.selectedForwardType,
                assignDepartmentCode: inputCommitteeData[0],
                currentAssignUser: inputCommitteeData[1],
                currentCommitteePaperStatus:
                  this.committeePaperStatusConst.FORWARDED,
                assignUserDisplayName: inputCommitteeData[3],
              },
              { committeePaperDTOList: committeePaperDTOList },
              {
                remarkData: {
                  ...remarkData,
                  actionMessage: actionMessage,
                },
              },
            );
          } else {
            let selectedCommitteeLevelList = [];
            let selectedCommitteeUserList: any[] = [];

            actionMessage = "Forwarded to " + inputCommitteeData[3];
            selectedCommitteeLevelList = _.filter(
              this.committeeLevelList,
              (committeeLevel) =>
                committeeLevel.committeeId == inputCommitteeData[2] &&
                committeeLevel.pathType == inputCommitteeData[5],
            );

            if (selectedCommitteeLevelList.length != 0) {
              if (inputCommitteeData[5] == "REG") {
                currentRegLevelID = _.maxBy(
                  selectedCommitteeLevelList,
                  "levelId",
                ).levelId;
              } else {
                currentAltLevelID = _.maxBy(
                  selectedCommitteeLevelList,
                  "levelId",
                ).levelId;
              }
              committeePaperDTOList.push({
                committeeType: inputCommitteeData[1],
                committeeID: inputCommitteeData[2],
                committeeName: inputCommitteeData[3],
                committeeTypeID: inputCommitteeData[4],
                currentRegLevelID: currentRegLevelID,
                currentAltLevelID: currentAltLevelID,
                currentAltRecommendedCount: currentAltRecommendedCount,
                currentRegRecommendedCount: currentRegRecommendedCount,
                currentPath: inputCommitteeData[5],
              });

              updateRequest = Object.assign(
                {},
                updateRequest,
                {
                  actionMessage: actionMessage,
                  forwardType: this.selectedForwardType,
                  assignDepartmentCode: inputCommitteeData[0],
                  currentAssignUser: inputCommitteeData[1],
                  /* currentAssignUser:
                    inputCommitteeData[1] + "-" + inputCommitteeData[2],*/
                  currentCommitteePaperStatus:
                    this.committeePaperStatusConst.FORWARDED,
                  assignUserDisplayName: inputCommitteeData[3],
                },
                { committeePaperDTOList: committeePaperDTOList },
                {
                  remarkData: {
                    ...remarkData,
                    actionMessage: actionMessage,
                  },
                },
              );
            } else {
              this.alertService.showToaster(
                "Selected Committee Is Invalid",
                SETTINGS.TOASTER_MESSAGES.error,
              );
            }
          }
        } else {
          let assignUserDisplayName;
          if (!_.isEmpty(this.assignedUser)) {
            assignUserDisplayName = this.assignedUser.firstName
              ? this.assignedUser.lastName
                ? this.assignedUser.firstName
                    .concat(" ")
                    .concat(this.assignedUser.lastName)
                : this.assignedUser.firstName
              : this.assignedUser.lastName
                ? this.assignedUser.lastName
                : null;
            actionMessage = `${this.getActionMessageForComment()} to ${
              this.assignedUser.firstName
                ? this.assignedUser.lastName
                  ? this.assignedUser.firstName
                      .concat(" ")
                      .concat(this.assignedUser.lastName)
                  : this.assignedUser.firstName
                : this.assignedUser.lastName
                  ? this.assignedUser.lastName
                  : null
            }`;
          } else {
            this.alertService.showToaster(
              "Selected User Is Invalid",
              SETTINGS.TOASTER_MESSAGES.warning,
            );
          }

          updateRequest = Object.assign(
            {},
            updateRequest,
            {
              assignedUser: {
                ...this.assignedUser,
                assignUserDisplayName: assignUserDisplayName,
                assignUserUpmGroupCode: this.componentForm.controls.userGroup
                  .value
                  ? this.componentForm.controls.userGroup.value
                  : this.assignedUser.upmGroupCode,
              },
            },
            {
              actionMessage: actionMessage,
              forwardType: this.selectedForwardType,
            },
            {
              remarkData: {
                ...remarkData,
                actionMessage: actionMessage,
                assignedUserID: this.assignedUser
                  ? this.assignedUser.userID
                  : null,
                assignedUser: this.assignedUser
                  ? this.assignedUser.adUserID
                  : null,
                assignedUserDisplayName: assignUserDisplayName,
                assignDepartmentCode: this.assignedUser
                  ? this.assignedUser.divCode
                  : null,
              },
            },
          );
        }
        break;
      }

      case this.forwardTypeConst.SAME_SOL_USER_GROUP: {
        let assignDepartmentDTOList = [];
        let userGroupNames = [];
        let assignDepartmentCode =
          this.applicationService.getLoggedInUserDivCode();
        let department = _.find(
          this.departmentGroupOptions,
          (b) => b.value == assignDepartmentCode,
        );
        let assignUPMUserGroups =
          this.componentForm.controls.upmUserGroups.value;

        assignUPMUserGroups.forEach((data: any) => {
          let userGroup = _.find(this.userGroupOptions, (b) => b.value == data);
          if (department && department.value) {
            assignDepartmentDTOList.push({
              divCode: department.value,
              departmentName: department.label,
              userGroupUPMCode: userGroup.value,
              userGroupName: userGroup.label,
            });
            userGroupNames.push(userGroup.label);
          }
        });

        actionMessage = `${this.getActionMessageForComment()} to ${this.getUserGroupNames(
          userGroupNames,
        )} user group${userGroupNames.length > 1 ? "s" : ""} of ${
          department.label
        }`;

        updateRequest = Object.assign(
          {},
          updateRequest,
          { assignedUser: null },
          {
            actionMessage: actionMessage,
            forwardType: this.selectedForwardType,
            assignDepartmentCode: department.value,
          },
          { assignDepartmentDTOList: assignDepartmentDTOList },
          {
            remarkData: {
              ...remarkData,
              actionMessage: actionMessage,
              assignDepartmentCode: department.value,
            },
          },
        );
        break;
      }

      case this.forwardTypeConst.OTHER_SOL_USER_GROUP: {
        let userGroupNames = [];
        let assignDepartmentDTOList = [];
        let assignDepartmentCode =
          this.componentForm.controls.departmentGroup.value;
        let department = _.find(
          this.departmentGroupOptions,
          (b) => b.value == assignDepartmentCode,
        );

        if (_.isEmpty(department)) {
          this.alertService.showToaster(
            "Selected Department Is Invalid",
            SETTINGS.TOASTER_MESSAGES.warning,
          );
        }

        if (
          this.riskDepartment &&
          this.riskDepartment.branchDepartmentDivCode == department.value
        ) {
          this.defaultApplicationFormForwardUPMGroups = _.union(
            this.masterDataService
              .getSystemParameter(
                Constants.systemParamKey
                  .DEFAULT_APPLICATION_FORM_FORWARDING_UPM_GROUPS,
              )
              .split(","),
            ["10", "20"],
          ); //todo need to move constants when Assistant going live
        } else {
          this.defaultApplicationFormForwardUPMGroups = this.masterDataService
            .getSystemParameter(
              Constants.systemParamKey
                .DEFAULT_APPLICATION_FORM_FORWARDING_UPM_GROUPS,
            )
            .split(",");
        }

        this.defaultApplicationFormForwardUPMGroups.forEach((data) => {
          let userGroup = _.find(this.userGroupOptions, (b) => b.value == data);
          if (department && department.value) {
            assignDepartmentDTOList.push({
              divCode: department.value,
              departmentName: department.label,
              userGroupUPMCode: userGroup.value,
              userGroupName: userGroup.label,
            });
            userGroupNames.push(userGroup.label);
          }
        });

        actionMessage = `${this.getActionMessageForComment()} to ${this.getUserGroupNames(
          userGroupNames,
        )} user group${userGroupNames.length > 1 ? "s" : ""} of ${
          department.label
        }`;

        updateRequest = Object.assign(
          {},
          updateRequest,
          { assignedUser: null },
          {
            actionMessage: actionMessage,
            forwardType: this.selectedForwardType,
            assignDepartmentCode: department.value,
          },
          { assignDepartmentDTOList: assignDepartmentDTOList },
          {
            remarkData: {
              ...remarkData,
              actionMessage: actionMessage,
              assignDepartmentCode: department.value,
            },
          },
        );
        break;
      }
    }
    if (this.updateRequestValidator(updateRequest)) {
      this.action.next(AppUtils.trim(updateRequest));
      this.commentCacheService.expireCommentCacheData(this.commentCacheKey);
      this.mdbModalRef.hide();
    }
  }

  async getEligibleUsers(groupCode: any) {
    let eligibleUsers = [];
    let requiredDivCodes = [];
    let loggedInGroupCode: number =
      this.applicationService.getLoggedInUserUPMGroupCode()
        ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
        : 0;
    if (this.isESG && loggedInGroupCode == 50) {
      requiredDivCodes.push(SETTINGS.ESG_DIV_CODE.toString());
    } else {
      requiredDivCodes.push(this.content.branchCode);
      requiredDivCodes.push(this.applicationService.getLoggedInUserDivCode());

      this.content.relatedDivCodes.forEach((divCode: any) => {
        if (divCode) {
          requiredDivCodes.push(divCode);
        }
      });
    }

    let uniqueDivCodes = [...new Set(requiredDivCodes)];

    for (const divCode of uniqueDivCodes) {
      let users: [] =
        await this.applicationService.getUserDetailListFormBranchAuthorityLevel(
          {
            solId: divCode,
            roleId: groupCode,
            appCode: "",
          },
        );
      if (users && Array.isArray(users)) {
        eligibleUsers = [...users, ...eligibleUsers];
      }
    }

    if (groupCode === this.defaultWorkflowUpmGroupCode.ASSISTANT) {
      if (this.assistants && Array.isArray(this.assistants)) {
        eligibleUsers = [...this.assistants, ...eligibleUsers];
      }
    }

    this.userDetails = _.uniqBy(eligibleUsers, (i) => i.userID);

    this.userOption = [];
    let userList = [];
    _.forEach(_.sortBy(this.userDetails, ["firstName"]), (user) => {
      if (!_.isNull(user.userID)) {
        let branch = AppUtils.getBranchFromBranchCode(
          this.allBranches,
          user.divCode,
        );
        let returnUser;
        if (this.returnUserList.length > 0 && this.isReturn) {
          returnUser = AppUtils.getUserFromAssignUserID(
            this.returnUserList,
            user.userID,
          );
        }

        if (!_.isEmpty(returnUser)) {
          userList.unshift({
            value: user.userID,
            label: user.userID
              ? "(R) : " +
                user.firstName +
                "  " +
                user.lastName +
                `${branch ? " - " + branch.branchName : ""}`
              : "No Users",
          });
        } else {
          userList.push({
            value: user.userID,
            label: user.userID
              ? user.firstName +
                "  " +
                user.lastName +
                `${branch ? " - " + branch.branchName : ""}`
              : "No Users",
          });
        }
      }
    });

    if (
      this.loggedInUserWorkClass == this.bccInquirerWorkClass ||
      this.loggedInUserWorkClass == this.applicationSecurityWorkClass.CM ||
      this.loggedInUserWorkClass == this.applicationSecurityWorkClass.AGM
    ) {
      if (this.content.isCommittee == "Y") {
        if (this.committeeList && Array.isArray(this.committeeList)) {
          _.forEach(this.committeeList, (committee) => {
            if (groupCode == committee.committeeTypeId) {
              let committeeUserValue =
                "CA" +
                "/" +
                committee.committeeTypeName +
                "/" +
                committee.committeeId +
                "/" +
                committee.committeeName +
                "/" +
                committee.committeeTypeId +
                "/" +
                committee.currentPath;
              userList.push({
                value: committeeUserValue,
                label: committee.committeeName,
              });
              if (committee.committeeTypeName == "BCC") {
                this.componentForm.controls.user.setValue(committeeUserValue);
              }
            }
          });
        }
      }
    }

    this.userOption = userList;
  }

  getUserGroupNames(userGroupNames) {
    if (userGroupNames.length > 0) {
      return userGroupNames.toString();
    }
    return "";
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.userOption.filter((item: any) =>
      item.value.toLowerCase().includes(filterValue),
    );
  }

  isValid() {
    return this.componentForm.valid;
  }

  ngOnDestroy(): void {
    this.onUpmGroupCodeChangeSub.unsubscribe();
    this.onFormChangeSub.unsubscribe();
    this.onDepartmentGroupChangeSub.unsubscribe();
  }

  isRiskDepartmentUserLogged() {
    return (
      this.riskDepartment &&
      this.riskDepartment.branchDepartmentDivCode ==
        this.applicationService.getLoggedInUserDivCode()
    );
  }

  filterBranchNameValueChange(value: string): string[] {
    if (value) {
      const filterValue = value.toLowerCase();
      return this.allBranches.filter((item: any) =>
        item.branchName.toLowerCase().includes(filterValue),
      );
    } else {
      return this.allBranches;
    }
  }

  updateRequestValidator(request) {
    if (!_.isEmpty(request)) {
      switch (this.selectedForwardType) {
        case this.forwardTypeConst.DIRECT_USER: {
          if (
            request.assignedUser &&
            request.assignedUser.userID &&
            request.assignedUser.adUserID &&
            request.assignedUser.divCode
          ) {
            if (request.assignedUser.adUserID == "AA") {
              this.alertService.showToaster(
                "Selected User Is Invalid",
                SETTINGS.TOASTER_MESSAGES.warning,
              );
              return false;
            }
            return true;
          }
          if (request.assignDepartmentCode == "CA") {
            return true;
          } else {
            this.alertService.showToaster(
              "Selected User Is Invalid",
              SETTINGS.TOASTER_MESSAGES.warning,
            );
            return false;
          }
        }

        case this.forwardTypeConst.OTHER_SOL_USER_GROUP:
        case this.forwardTypeConst.SAME_SOL_USER_GROUP: {
          if (
            request.assignDepartmentDTOList &&
            request.assignDepartmentDTOList.length > 0
          ) {
            let divCodeValidation = false;
            request.assignDepartmentDTOList.forEach((data) =>
              data.divCode
                ? (divCodeValidation = true)
                : (divCodeValidation = false),
            );
            if (!divCodeValidation) {
              this.alertService.showToaster(
                "Selected Department Is Invalid",
                SETTINGS.TOASTER_MESSAGES.warning,
              );
            }
            return divCodeValidation;
          } else {
            this.alertService.showToaster(
              "Selected Department Is Invalid",
              SETTINGS.TOASTER_MESSAGES.warning,
            );
            return false;
          }
        }
      }
    } else {
      this.alertService.showToaster(
        "Invalid Request",
        SETTINGS.TOASTER_MESSAGES.warning,
      );
      return false;
    }
  }

  getActionMessageForComment() {
    if (this.isForward) {
      return "Forwarded";
    }

    if (this.isReturn) {
      return "Returned";
    }

    if (this.isTransfer) {
      return "Transferred";
    }
  }

  isLoggedInUserManager() {
    if (
      this.loggedInUserWorkClass ==
      Constants.applicationSecurityWorkClass.MANAGER
    ) {
      return true;
    } else {
      return false;
    }
  }

  isLoggedInUserLegalOfficer() {
    if (
      this.loggedInUserWorkClass == Constants.applicationSecurityWorkClass.LO
    ) {
      return true;
    } else {
      return false;
    }
  }

  isRiskUser(): boolean {
    return (
      this.applicationService.getLoggedInUserDivCode().toString() ===
      SETTINGS.ESG_DIV_CODE.toString()
    );
  }

  showSecurityDocumentConfirmation() {
    if (
      (this.isLoggedInUserManager() || this.isLoggedInUserLegalOfficer()) &&
      !this.isRiskUser()
    ) {
      if (
        this.content.isCommittee == Constants.yesNoConst.Y &&
        !this.isESG &&
        !this.isReturn
      ) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  isUserConfirmed() {
    if (this.showSecurityDocumentConfirmation()) {
      return this.componentForm.controls.userConfirmation.value;
    } else {
      return true;
    }
  }

  onQuestionsSubmitted(submitted: boolean) {
    this.isQuestionsSubmitted = submitted; // Update state when questions are submitted
  }

  private checkQuestionsVisibility() {
    const userWorkClass = this.applicationService.getLoggedInUserUPMGroupCode();
    let loggedInDiv:string = this.applicationService.getLoggedInUserDivCode();

    console.log("Logged-in User Work Class:", userWorkClass);

    if (loggedInDiv !== SETTINGS.RISK_DIV_CODE) {
      this.smeService
        .getAllQuestionsAndAnswers()
        .then((response: any) => {
          this.isQuestionsLoading = false;

          if (!response || !Array.isArray(response)) {
            console.error(
              "Invalid response from getAllQuestionsAndAnswers:",
              response,
            );
            this.isQuestionsVisible = false;
            return;
          }

          // Filter questions based on userWorkClass
          this.questionsAndAnswers = response.filter((question: any) => {
            // Ensure smeQuestionConfigDTOList exists and is an array
            if (
              !question.smeQuestionConfigDTOList ||
              !Array.isArray(question.smeQuestionConfigDTOList)
            ) {
              console.warn(
                `Question ${question.smeQuestionId} has no valid smeQuestionConfigDTOList.`,
              );
              return false; // Exclude questions without valid configs
            }

            // Check if any config matches the userWorkClass
            const hasPermission = question.smeQuestionConfigDTOList.some(
              (config: any) => {
                const matchesWorkClass =
                  String(config.workClass) === String(userWorkClass);
                const isVisible = config.isShow === "Y";

                return matchesWorkClass && isVisible;
              },
            );

            return hasPermission;
          });

          // Set visibility based on filtered questions
          this.isQuestionsVisible = this.questionsAndAnswers.length > 0;

          console.log("Filtered Questions:", this.questionsAndAnswers);
          console.log("Is Questions Visible:", this.isQuestionsVisible);
        })
        .catch((error: any) => {
          console.error("Error fetching questions:", error);
          this.isQuestionsVisible = false;
          this.isQuestionsLoading = false;
        });
    } else {
      this.isQuestionsLoading = false;
      this.isQuestionsVisible = false;
    }
  }

  smeQuestionShow(): boolean {
    if (
      this.heading == "Forward Facility Paper" ||
      this.heading == "Approve Facility Paper"
    ) {
      return this.isQuestionsVisible && !this.isQuestionsSubmitted;
    }
    //return this.isQuestionsVisible && !this.isQuestionsSubmitted;

    if(this.applicationService.getLoggedInUserDivCode() == '874'){
      this.isQuestionsVisible = false;
      this.isQuestionsLoading = false;
    }
  }
}
