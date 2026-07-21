import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import * as _ from "lodash";
import { Subscription } from "rxjs";
import { Constants } from "src/app/core/setting/constants";
import { CommitteeService } from "../../../service/committee.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { LocalStorage } from "ngx-webstorage";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { CommitteeApproveRejectModalComponent } from "src/app/shared/components/committee-approve-reject-modal/committee-approve-reject-modal.component";
import { element } from "protractor";

@Component({
  selector: "app-committee-add-edit",
  templateUrl: "./committee-add-edit.component.html",
  styleUrls: ["./committee-add-edit.component.scss"],
})
export class CommitteeAddEditComponent implements OnInit {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_COMMITTEE_FETCH_TYPE)
  selectedCommitteeFetchType;

  selectedCommittee: any = {};

  selectedUsers: any[] = [];
  types: any[] = [];
  poolUsers: any[] = [];
  reviewUsers: any[] = [];
  comments: any[] = [];

  isCommentsShow: boolean = false;

  formData: any = {
    committeeId: 0,
    parentRecordID: 0,
    committeeName: "",
    committeeTypeId: "",
    delegatedAuthority: "",
    caLevelDTOList: [],
    reviewer: "",
    status: "",
    approveStatus: "",
    committeeStatus: "",
    userDisplayName: "",
    currentPath: "",
  };

  staticFormData: any;

  committePaths: any[] = [
    {
      id: 1,
      path: "Regular Path",
      pathType: Constants.committeePathConst.REG,
      users: [],
      levels: [],
      savedLevels: [],
      error: "",
    },
    {
      id: 2,
      path: "Alternative Path",
      pathType: Constants.committeePathConst.ALT,
      users: [],
      levels: [],
      savedLevels: [],
      error: "",
    },
  ];

  userTypeOptions = [
    { value: "Any", label: "Any" },
    { value: "All", label: "All" },
  ];

  optionsSelect = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];

  formErrors: any = {
    committeeName: "",
    committeeTypeId: "",
    delegatedAuthority: "",
    reviewer: "",
  };

  onSelectedCommitteeChangeSub: Subscription = new Subscription();

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  pageType: String = "new";
  tableType: String = "";
  isActionEnabled: boolean = false;
  loggedInUserName: any = "";

  modalRef: MDBModalRef;

  constructor(
    private committeeService: CommitteeService,
    private router: Router,
    private mdbModalService: MDBModalService,
    private alertService: AlertService,
    private urlEncodeService: UrlEncodeService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.loggedInUserName = this.applicationService.getLoggedInUserUserName();

    this.committeeService.getCommitteeUpdateDTO();

    this.onSelectedCommitteeChangeSub =
      this.committeeService.onSelectedCommitteeChange.subscribe((committee) => {
        this.selectedCommittee = committee;
        if (_.isEmpty(committee)) {
          this.pageType = "new";
          this.tableType = "TEMP";

          this.formData = {
            ...this.formData,
            status: this.statusConst.ACT,
            userDisplayName:
              this.applicationService.getLoggedInUserDisplayName(),
            currentPath: Constants.committeePathConst.REG,
          };

          this.staticFormData = { ...this.formData };
        } else {
          this.pageType = "edit";
          this.tableType = this.urlEncodeService.decode(
            this.selectedCommitteeFetchType
          );

          this.isActionEnabled = this.isEnabled();
          this.setFormData(this.selectedCommittee);
        }

        this.getAllTypes();
        this.getPoolUsers();
      });
  }

  ngOnDestroy(): void {
    this.onSelectedCommitteeChangeSub.unsubscribe();
  }

  isFormDataEqual() {
    return (
      JSON.stringify(this.formData) === JSON.stringify(this.staticFormData)
    );
  }

  isEnabled(): boolean {
    return (
      this.selectedCommittee.approveStatus ==
        this.approvedStatusConst.PENDING &&
      this.selectedCommittee.committeeStatus ==
        Constants.committeeStatusConst.SUBMITTED &&
      this.isValidUser(
        this.selectedCommittee.createdBy,
        this.selectedCommittee.modifiedBy
      )
    );
  }

  isDisabled(): boolean {
    return (
      this.selectedCommittee.approveStatus ==
        this.approvedStatusConst.PENDING &&
      this.selectedCommittee.committeeStatus ==
        Constants.committeeStatusConst.SUBMITTED
    );
  }

  isValidUser(createdBy: any, modifiedBy: any): boolean {
    var isValid: boolean = false;

    if (createdBy && modifiedBy) {
      isValid = modifiedBy != this.loggedInUserName;
    } else if (createdBy && !modifiedBy) {
      isValid = createdBy != this.loggedInUserName;
    }

    return isValid;
  }

  getAllTypes() {
    this.committeeService.getCommitteeType().then((data: any) => {
      if (data) {
        this.types = data
          .filter(
            (tf: any) => tf.status == this.statusConst.ACT && tf.isSystem == 0
          )
          .map((t: any) => ({
            ...t,
            value: t.committeeTypeId,
            label: t.committeeTypeName,
          }));
      }
    });
  }

  getPoolUsers() {
    this.committeeService.getUserPool().then((data: any) => {
      if (data) {
        this.poolUsers = data
          .sort((a: any, b: any) => b.groupCode - a.groupCode)
          .map((u: any) => ({
            value: u.userName,
            label: u.userDisplayName,
            isSelected: false,
            groupCode: u.groupCode,
            userStatus: u.userStatus,
            userId: u.userId,
          }));

        this.committePaths.forEach((element: any) => {
          if (element.id == 1) {
            element.users = this.poolUsers;
          } else {
            element.users = this.poolUsers.filter(
              (u: any) =>
                u.groupCode != Constants.applicationSecurityWorkClass.MD
            );
          }
        });

        this.setPathData(this.formData.caLevelDTOList);
        this.setReviewUsers();
      }
    });
  }

  setFormData(committee: any) {
    this.formData = {
      ...this.formData,
      committeeId: committee.committeeId,
      parentRecordID: committee.parentRecordID
        ? committee.parentRecordID
        : committee.committeeId,
      committeeName: committee.committeeName ? committee.committeeName : "",
      committeeTypeId: committee.committeeTypeId
        ? committee.committeeTypeId
        : "",
      delegatedAuthority: committee.delegatedAuthority
        ? committee.delegatedAuthority
        : "",
      caLevelDTOList: committee.caLevelDTOList ? committee.caLevelDTOList : [],
      reviewer: committee.reviewer ? committee.reviewer : "",
      status: committee.status ? committee.status : "",
      approveStatus: "",
      committeeStatus: committee.committeeStatus
        ? committee.committeeStatus
        : "",
      userDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      currentPath: committee.currentPath
        ? committee.currentPath
        : Constants.committeePathConst.REG,
    };

    this.staticFormData = {
      ...this.formData,
    };
  }

  setPathData(data: any[]) {
    if (data.length > 0) {
      var regLevel: any[] = data.filter(
        (d: any) => d.pathType == Constants.committeePathConst.REG
      );
      var altLevel: any[] = data.filter(
        (d: any) => d.pathType == Constants.committeePathConst.ALT
      );

      this.committePaths.forEach((path: any) => {
        if (path.id == 1) {
          path.levels = this.getLevelObjects(path, regLevel);
          path.savedLevels = this.getLevelObjects(path, regLevel);

          if (path.levels) {
            path.levels.forEach((level: any) => {
              if (level.selectedUsers) {
                this.handlePathUsersWithLevel(
                  1,
                  level.selectedUsers,
                  level.levelId
                );
              }
            });
          }
        }

        if (path.id == 2) {
          path.levels = this.getLevelObjects(path, altLevel);
          path.savedLevels = this.getLevelObjects(path, altLevel);

          if (path.levels) {
            path.levels.forEach((level: any) => {
              if (level.selectedUsers) {
                this.handlePathUsersWithLevel(
                  2,
                  level.selectedUsers,
                  level.levelId
                );
              }
            });
          }
        }
      });
    }
  }

  getLevelObjects(path: any, levels: any[]): any[] {
    return levels.map((l: any, i: number) => ({
      levelConfigId: l.levelConfigId ? l.levelConfigId : 0,
      id: this.generateRandomNumber() + i,
      users: l.caUserDTOList && l.caUserDTOList.length <= 0 ? path.users : [],
      selectedUsers: l.caUserDTOList
        ? l.caUserDTOList.map((u: any) => u.userName)
        : [],
      prevUsers: l.caUserDTOList
        ? l.caUserDTOList.map((u: any) => u.userName)
        : [],
      userSelectType: l.combination ? l.combination : "",
      noOfUser: l.userCount ? l.userCount : 0,
      savedUsers: l.caUserDTOList
        ? l.caUserDTOList.map((u: any) => ({
            userConfigId: u.userConfigId,
            userName: u.userName,
          }))
        : [],
      error: "",
      actionStatus: Constants.committeeStatusConst.UPDATE,
    }));
  }

  handlePathUsersWithLevel(pathId: any, levelUsers: any[], levelId: any) {
    levelUsers.forEach((user: any) => {
      this.handlePathPoolUser(pathId, levelId, user, true, false, true);
    });
  }

  getPath(id: any) {
    return this.committePaths.find((cp: any) => cp.id == id);
  }

  addLevel(pathId: any) {
    //filter committee path
    this.committePaths
      .filter((cp: any) => cp.id == pathId)
      .forEach((element: any) => {
        //check available users
        if (element.users.some((u: any) => u.isSelected == false)) {
          var levels: any[] = element.levels;

          //add new level to path
          var levelObj: any = {
            levelConfigId: 0,
            id: this.generateRandomNumber(),
            users: element.users.filter((u: any) => u.isSelected == false),
            selectedUsers: [],
            prevUsers: [],
            userSelectType: "",
            noOfUser: 0,
            savedUsers: [],
            error: "",
            actionStatus: Constants.committeeStatusConst.NEW,
          };
          levels.push(levelObj);

          this.updateFormData(false);
        } else {
          this.alertService.showToaster(
            "There are no committee pool users are available for new level.",
            SETTINGS.TOASTER_MESSAGES.warning
          );
        }
      });
  }

  removeLevel(pathId: any, levelId: any, users: any[]) {
    //remove level from path
    this.committePaths = this.committePaths.map((cp: any) => ({
      ...cp,
      levels:
        cp.id == pathId
          ? cp.levels.filter((l: any) => l.id != levelId)
          : cp.levels,
    }));

    //unselect removed level users
    if (users) {
      users.forEach((element: any) => {
        this.handlePathPoolUser(pathId, levelId, element, false, true, false);
      });
    }

    this.updateFormData(false);
  }

  handleUser(pathId: any, levelId: any, ids: any[], prevIds: any[]) {
    //set selected users
    this.setLevelPrevUsers(pathId, levelId, ids);

    //mark selected users
    ids.forEach((element: any) => {
      this.handlePathPoolUser(pathId, levelId, element, true, true, false);
    });

    prevIds.forEach((element: any) => {
      // compare previous and current selected users and update users
      if (!ids.some((id: any) => id == element)) {
        this.handlePathPoolUser(pathId, levelId, element, false, true, false);
      }
    });
  }

  handleUserSelectType(pathId: any, levelId: any, e: any) {
    var userSpecifiedType: any = e.target.value;
    var isAllSelected: boolean = userSpecifiedType == "All";

    this.committePaths
      .filter((p: any) => p.id == pathId)
      .forEach((element: any) => {
        var levels: any[] = element.levels ? element.levels : [];
        var level: any = levels.find((l: any) => l.id == levelId);

        level.userSelectType = userSpecifiedType;
        level.noOfUser = isAllSelected ? level.selectedUsers.length : 0;
      });

    this.updateFormData(false);
  }

  handleUserCount(pathId: any, levelId: any, selectedUsers: any[], e: any) {
    var count: any = e.target.value;

    if (count <= selectedUsers.length) {
      this.committePaths
        .filter((p: any) => p.id == pathId)
        .forEach((element: any) => {
          var levels: any[] = element.levels ? element.levels : [];
          var level: any = levels.find((l: any) => l.id == levelId);

          level.noOfUser = count;
        });
    } else {
      this.alertService.showToaster(
        "Provided count is invalid.",
        SETTINGS.TOASTER_MESSAGES.warning
      );
    }

    this.updateFormData(false);
  }

  setLevelPrevUsers(pathId: any, levelId: any, users: any[]) {
    this.committePaths
      .filter((cp: any) => cp.id == pathId)
      .forEach((element: any) => {
        element.levels
          .filter((l: any) => l.id == levelId)
          .forEach((level: any) => {
            level.prevUsers = users;
          });
      });
  }

  handlePathPoolUser(
    pathId: any,
    levelId: any,
    userId: any,
    isSelected: boolean,
    isClear: boolean,
    isLoad: boolean
  ) {
    this.committePaths
      .filter((cp: any) => cp.id == pathId)
      .forEach((element: any) => {
        if (element.users) {
          element.users = element.users.map((pu: any) => ({
            ...pu,
            isSelected: pu.value == userId ? isSelected : pu.isSelected,
          }));
        }
      });

    this.setReviewUsers();
    this.setLevelUsers(pathId, levelId, isClear, isLoad);
  }

  setLevelUsers(pathId: any, levelId: any, isClear: boolean, isLoad: boolean) {
    this.committePaths
      .filter((cp: any) => cp.id == pathId)
      .forEach((element: any) => {
        element.levels.forEach((level: any) => {
          var selectedUsers: any[] = this.getSelectedUsers(level.selectedUsers);

          level.users = this.getLevelUsers(pathId, selectedUsers);

          if (level.id == levelId && isClear) {
            level.userSelectType = "";
            level.noOfUser = 0;
          }
        });
      });

    this.updateFormData(isLoad);
  }

  getLevelUsers(pathId: any, prevUsers: any[]): any[] {
    var users: any[] = [];
    var availableUsers: any[] = [];

    var path: any = this.getPath(pathId);

    if (path && path.users) {
      availableUsers = path.users.filter((u: any) => u.isSelected == false);
    }

    users = prevUsers.concat(availableUsers);
    return users;
  }

  getSelectedUsers(ids: any[]): any[] {
    var result: any[] = [];

    ids.forEach((userId: any) => {
      result.push(this.getUser(userId));
    });

    return result;
  }

  getUser(userId: any) {
    return this.poolUsers.find((u: any) => u.value == userId);
  }

  setReviewUsers() {
    this.reviewUsers = this.getReviewUsers();
  }

  getReviewUsers(): any[] {
    var result: any[] = [];

    this.poolUsers.forEach((user: any) => {
      if (
        !result.some((r: any) => r.userId == user.userId) &&
        this.isUserAvailable(user)
      ) {
        result.push(user);
      }
    });

    return result;
  }

  isUserAvailable(user: any): boolean {
    var regUsers: any[] = this.getPath(1) ? this.getPath(1).users : [];
    var altUsers: any[] = this.getPath(2) ? this.getPath(2).users : [];

    var isAvailable: boolean = false;
    isAvailable =
      regUsers.some(
        (r: any) => r.userId == user.userId && r.isSelected == true
      ) ||
      altUsers.some(
        (r: any) => r.userId == user.userId && r.isSelected == true
      );

    return !isAvailable;
  }

  saveCommittee() {
    this.committePaths = this.committePaths.map((p: any, i: number) => ({
      ...p,
      error:
        i == 0 && p.levels.length <= 0
          ? "Please provide the path levels."
          : i == 1 && this.isMDExist() && p.levels.length <= 0
          ? "Please provide the alternative path levels."
          : "",
      levels: p.levels.map((l: any) => ({
        ...l,
        error:
          l.selectedUsers.length <= 0
            ? "Please provide the users for this level."
            : !l.userSelectType
            ? "Specifed users type is required."
            : l.noOfUser <= 0
            ? "Specifed user count is required."
            : l.selectedUsers.length < l.noOfUser
            ? "Specifed users less than or equal to selected users."
            : "",
      })),
    }));

    var levelList: any[] = this.getLevelData();

    this.formData = {
      ...this.formData,
      committeeName:this.formData.committeeName ? this.formData.committeeName.replace(/[\/\\]/g, "-") : "" ,
      committeeStatus: Constants.committeeStatusConst.SUBMITTED,
      approveStatus: Constants.approveStatusConst.PENDING,
      committeeTypeName: this.getTypeName(this.formData.committeeTypeId),
      status: this.formData.status,
      caLevelDTOList: levelList.map((level: any) => ({
        ...level,
        actionStatus:
          level.pathType == Constants.committeePathConst.ALT &&
          !this.isMDExist()
            ? Constants.committeeStatusConst.DELETE
            : level.actionStatus,
      })),
      tableType: this.tableType,
    };

    if (
      this.formData.committeeName &&
      parseInt(this.formData.committeeTypeId) > 0 &&
      parseInt(this.formData.delegatedAuthority) > 0 &&
      this.isNumber(this.formData.delegatedAuthority) &&
      this.levelErrors().length == 0 &&
      this.levelUserErrors().length == 0
    ) {
      this.committeeService.saveCommittee(this.formData).then(
        (res: any) => {
          this.alertService.showToaster(
            "Committee has been saved successfully.",
            SETTINGS.TOASTER_MESSAGES.success
          );

          setTimeout(() => {
            this.loadCommitte();
          }, 1500);
        },
        (err: any) => {
          this.alertService.showToaster(
            "An error occurd while save the data.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
    } else {
      this.formErrors = {
        committeeName: !this.formData.committeeName
          ? "Committee name is required."
          : "",
        committeeTypeId: !this.formData.committeeTypeId
          ? "Committee type is required."
          : "",
        delegatedAuthority:
          this.formData.delegatedAuthority == null &&
          !this.isNumber(this.formData.delegatedAuthority)
            ? "Invalid format."
            : !this.formData.delegatedAuthority
            ? "Delegated authority is required."
            : "",
      };
      setTimeout(() => {
        this.formErrors = {
          committeeName: "",
          committeeTypeId: "",
          delegatedAuthority: "",
        };

        this.committePaths = this.committePaths.map((p: any) => ({
          ...p,
          error: "",
          levels: p.levels.map((l: any) => ({
            ...l,
            error: "",
          })),
        }));
      }, 2000);
    }
  }

  checkPathLevelCount() {
    var isValid: boolean = false;

    var regLevels: any[] = this.committePaths[0].levels;
    var altLevels: any[] = this.committePaths[1].levels;

    isValid = regLevels.length > altLevels.length;
    return isValid;
  }

  getDeletedLevelUsers(levels: any[]) {
    var result: any[] = [];
    levels.forEach((level: any) => {
      if (level.actionStatus == Constants.committeeStatusConst.DELETE) {
        result = result.concat(level.caUserDTOList);
      } else {
        var filteredUser: any[] = level.caUserDTOList.filter(
          (u: any) => u.actionStatus == Constants.committeeStatusConst.DELETE
        );
        result = result.concat(filteredUser);
      }
    });
    return result;
  }

  draftCommittee() {
    var levelList: any[] = this.getLevelData();
    this.formData = {
      ...this.formData,
      committeeTypeId: this.formData.committeeTypeId
        ? this.formData.committeeTypeId
        : 0,
      committeeName:this.formData.committeeName ? this.formData.committeeName.replace(/[\/\\]/g, "-") : "", 
      committeeStatus: Constants.committeeStatusConst.DRAFT,
      approveStatus: Constants.approveStatusConst.PENDING,
      committeeTypeName: this.getTypeName(this.formData.committeeTypeId),
      status: this.formData.status,
      caLevelDTOList: levelList,
      tableType: this.tableType,
      deletedLevelUsers: this.getDeletedLevelUsers(levelList),
    };

    if (this.formData.committeeName) {
      this.committeeService.saveCommittee(this.formData).then(
        (res: any) => {
          this.alertService.showToaster(
            "Committee has been drafted successfully.",
            SETTINGS.TOASTER_MESSAGES.success
          );
          setTimeout(() => {
            this.loadCommitte();
          }, 1500);
        },
        (err: any) => {
          this.alertService.showToaster(
            "An error occurd while draft the data.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
    } else {
      this.formErrors = {
        ...this.formErrors,
        committeeName: !this.formData.committeeName
          ? "Committee name is required."
          : "",
      };

      setTimeout(() => {
        this.formErrors = {
          committeeName: "",
          committeeTypeId: "",
          delegatedAuthority: "",
        };
      }, 2000);
    }
  }

  getTypeName(typeId: any) {
    var typeName: any = "";

    var type: any = this.types.find((t: any) => t.committeeTypeId == typeId);
    typeName = type ? type.committeeTypeName : "";

    return typeName;
  }

  getLevelData(): any[] {
    var levels: any[] = [];

    this.committePaths.forEach((path: any) => {
      if (path.levels && path.levels.length > 0) {
        path.levels.forEach((level: any, li: number) => {
          var levelUsers: any[] = level.selectedUsers
            ? level.selectedUsers
            : [];

          var levelDto: any = {
            levelConfigId: level.levelConfigId ? level.levelConfigId : 0,
            levelId: li + 1,
            combination: level.userSelectType,
            userCount: level.noOfUser,
            status: Constants.statusConst.ACT,
            pathType: path.pathType,
            actionStatus: level.levelConfigId
              ? Constants.committeeStatusConst.UPDATE
              : Constants.committeeStatusConst.NEW,
            caUserDTOList: levelUsers.map((lu: any) => ({
              committeeId: this.formData.committeeId
                ? this.formData.committeeId
                : 0,
              levelConfigId: level.levelConfigId ? level.levelConfigId : 0,
              userConfigId: this.getLevelUserData(level.savedUsers, lu)
                ? this.getLevelUserData(level.savedUsers, lu).userConfigId
                : 0,
              pathType: path.pathType,
              userId: this.getUser(lu).userId,
              userName: lu,
              userStatus: Constants.statusConst.ACT,
              actionStatus: this.getLevelUserData(level.savedUsers, lu)
                ? Constants.committeeStatusConst.UPDATE
                : Constants.committeeStatusConst.NEW,
            })),
          };

          levels.push(levelDto);

          if (level.levelConfigId && level.levelConfigId != 0) {
            var deletedUsers: any[] = [];
            level.savedUsers.forEach((user: any) => {
              if (!level.selectedUsers.some((u: any) => u == user.userName)) {
                deletedUsers.push({
                  levelConfigId: level.levelConfigId,
                  userConfigId: user.userConfigId,
                  actionStatus: Constants.committeeStatusConst.DELETE,
                });
              }
            });
            levelDto.caUserDTOList = deletedUsers.concat(
              levelDto.caUserDTOList
            );
          }
        });
      }

      if (path.savedLevels && path.savedLevels.length > 0) {
        path.savedLevels.forEach((level: any) => {
          if (
            !path.levels.some(
              (l: any) =>
                l.levelConfigId != 0 && l.levelConfigId == level.levelConfigId
            )
          ) {
            levels.push({
              ...level,
              caUserDTOList: level.savedUsers,
              actionStatus: Constants.committeeStatusConst.DELETE,
            });
          }
        });
      }
    });
    return levels;
  }

  getLevelUserData(users: any[], userName: any) {
    return users.find((u: any) => u.userName == userName);
  }

  generateRandomNumber() {
    var epoch = new Date().getTime();
    return epoch;
  }

  isNumber(value: any): boolean {
    const regex = RegExp(/^(?=\d)(?!0(?![.]))(?:\d{0,100})(?:[.]\d{1,20})?$/);
    return regex.test(value);
  }

  isMDExist(): boolean {
    var result: any[] = [];

    if (this.getPath(1)) {
      var levels: any[] = this.getPath(1).levels;
      levels.forEach((element: any) => {
        if (
          element.selectedUsers.some(
            (u: any) =>
              this.getUser(u).groupCode ==
              Constants.applicationSecurityWorkClass.MD
          )
        ) {
          result.push(element);
        }
      });
    }
    return result.length > 0;
  }

  levelErrors(): any[] {
    var errors: any[] = [];

    this.committePaths.forEach((element: any, i: number) => {
      var levels: any[] = element.levels ? element.levels : [];

      if (i == 0 && levels.length <= 0) {
        errors.push("Erorr");
      }

      if (i == 1 && this.isMDExist() && levels.length <= 0) {
        errors.push("Erorr");
      }
    });

    return errors;
  }

  levelUserErrors(): any[] {
    var errors: any[] = [];

    this.committePaths.forEach((element: any) => {
      var levels: any[] = element.levels ? element.levels : [];

      levels.forEach((level: any) => {
        if (level.error != "") {
          errors.push(level.error);
        }
      });
    });

    return errors;
  }

  handleApproveStatus(currentStatus: any) {
    this.modalRef = this.mdbModalService.show(
      CommitteeApproveRejectModalComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-md",
        containerClass: "",
        animated: false,
        data: {
          status: currentStatus,
          committeeName: this.formData.committeeName,
        },
      }
    );

    this.modalRef.content.action.subscribe((result: any) => {
      if (
        result.status == 1 &&
        currentStatus == Constants.approveStatusConst.APPROVED
      ) {
        this.approveCommittee();
        this.modalRef.hide();
      }

      if (
        result.status == 1 &&
        currentStatus == Constants.approveStatusConst.REJECTED
      ) {
        this.rejectCommittee(result.comment);
        this.modalRef.hide();
      }
    });
  }

  approveCommittee() {
    var levelList: any[] = this.getLevelData();

    var submitData: any = {
      ...this.formData,
      caLevelDTOList: levelList,
      status: this.formData.status,
      approveStatus: Constants.approveStatusConst.APPROVED,
    };

    this.committeeService.saveApproveRejectCommittee(submitData).then(
      (res: any) => {
        this.alertService.showToaster(
          "Committee has been approved successfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );

        setTimeout(() => {
          this.loadCommitte();
        }, 1500);
      },
      (err: any) => {
        this.alertService.showToaster(
          "Failed to approve committee.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    );
  }

  rejectCommittee(comment: any) {
    var submitData: any = {
      ...this.formData,
      status: Constants.statusConst.INA,
      approveStatus: Constants.approveStatusConst.REJECTED,
      comment: comment,
    };

    this.committeeService.saveApproveRejectCommittee(submitData).then(
      (res: any) => {
        this.alertService.showToaster(
          "Committee has been rejected successfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );

        setTimeout(() => {
          this.loadCommitte();
        }, 1500);
      },
      (err: any) => {
        this.alertService.showToaster(
          "Failed to reject committee.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    );
  }

  getComments() {
    this.committeeService
      .getCommitteeComments(this.formData.committeeId, this.tableType)
      .then(
        (res: any) => {
          if (res) {
            this.isCommentsShow = true;
            this.comments = res;
          } else {
            this.isCommentsShow = false;
          }
        },
        (err: any) => {
          this.alertService.showToaster(
            "Failed to fetch comments",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
  }

  loadCommitte() {
    this.router.navigate(["/committee/all"]);
  }

  updateFormData(isLoad: any) {
    this.formData = {
      ...this.formData,
      caLevelDTOList: this.getLevelData(),
    };

    if (isLoad) {
      this.staticFormData = {
        ...this.staticFormData,
        caLevelDTOList: this.getLevelData(),
      };
    }
  }

  isPathEnabled(id: number): boolean {
    var isEnabled: boolean = true;

    if (id == 2 && !this.isMDExist()) {
      isEnabled = false;
    }

    return isEnabled;
  }
}
