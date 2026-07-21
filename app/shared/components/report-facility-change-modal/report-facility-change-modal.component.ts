import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import * as _ from "lodash";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-report-facility-change-modal",
  templateUrl: "./report-facility-change-modal.component.html",
  styleUrls: ["./report-facility-change-modal.component.scss"],
})
export class ReportFacilityChangeModalComponent implements OnInit, OnDestroy {
  @ViewChild("cmntInput", { static: false }) cmntInput: ElementRef | any;
  facility: any;
  commentText: any = "";
  isFcSecurity: boolean = false;
  parentCmntId: number = 0;
  selectedId: number = 0;

  componentData: any = {
    facilityPaper: {},
    creditFacilityList: [],
    creditFacility: {},
    creditScheduleESBResponseStatusList: [],
    commonFacilitySecurityList: [],
    isCommittee: false,
  };

  commentHistory: any[] = [];
  facilityId: number = 0;
  fcSecurities: any[] = [];
  selectedFCSecurity: number = 0;
  commonFacilitySecurityList: any[] = [];
  loggedInUser: any = "";
  loggedInWC: number = 0;
  securityCmntEnabled: boolean = true;
  isCollapsed: boolean = false;

  onFPFacilitiesChangeSub = new Subscription();

  constructor(
    public mdbModalRef: MDBModalRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private alertService: AlertService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.facilityId = this.facility.facilityID;
    this.loggedInUser = this.applicationService.getLoggedInUserUserName();
    this.loggedInWC = this.applicationService.getLoggedInUserUPMGroupCode()
      ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
      : 0;
    this.onFPFacilitiesChangeSub =
      this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            var selectedFacility: any = data.facilityDTOList
              ? data.facilityDTOList.find(
                  (f: any) => f.facilityID == this.facilityId
                )
              : null;
            if (selectedFacility) {
              this.setFaciltyComments(selectedFacility);
            }
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onFPFacilitiesChangeSub.unsubscribe();
  }

  setFaciltyComments(facility: any) {
    var comments: any[] = facility.fusTraceList ? facility.fusTraceList : [];
    this.commentHistory =
      this.facilityPaperAddEditService.sortCommentHistory(comments);

    if (this.getUnReadCommentsCount(comments) > 0 && this.isCommentable()) {
      this.viewComments();
    }

    this.setFacilitySecurities();
  }

  setFacilitySecurities() {
    var defualtArr: any[] = [{ value: 0, label: "Choose...", disabled: true }];

    var result: any[] =
      this.facility.facilitySecurityDTOList &&
      this.facility.facilitySecurityDTOList.length > 0
        ? this.facility.facilitySecurityDTOList.map((fcse: any, i: number) => ({
            value: fcse.facilitySecurityID,
            label:
              i +
              1 +
              ". " +
              (fcse.isCommonSecurity == Constants.yesNoConst.Y
                ? "Common Security"
                : "Security"),
            disabled: this.commentHistory.some(
              (ch: any) =>
                ch.subKey == fcse.facilitySecurityID &&
                ch.isView == 0 &&
                ch.createdBy == this.loggedInUser
            ),
          }))
        : [];

    this.fcSecurities = defualtArr.concat(result);
  }

  getFacilityComments() {
    var payload: any = {
      mainKey: this.facilityId,
      subKey: 0,
      flag: Constants.fusTraceFlag.ALL,
      status: Constants.statusConst.ACT,
    };
    this.facilityPaperAddEditService.getCommentsByFacilityId(
      payload,
      this.componentData.facilityPaper
    );
  }

  getUnReadCommentsCount(comments: any[]): number {
    var filteredComments: any[] = comments.filter(
      (c: any) =>
        c.flag == Constants.fusTraceFlag.FAC ||
        c.flag == Constants.fusTraceFlag.FACSE
    );

    return filteredComments.filter(
      (cmnt: any) =>
        cmnt.isView == 0 &&
        cmnt.createdBy != this.loggedInUser &&
        cmnt.createdUserWC <= 50
    ).length;
  }

  viewComments() {
    var data: any = {
      mainKey: this.facilityId,
      subKey: 0,
      flag: Constants.fusTraceFlag.ALL,
    };

    this.facilityPaperAddEditService
      .saveFusTraceView(data)
      .subscribe((res: any) => {
        this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
          this.componentData.facilityPaper,
          Constants.fusTraceFlag.FAC
        );
      });
  }

  setParentCommentId(item: any) {
    if (item.id) {
      this.parentCmntId = item.id;
      this.selectedFCSecurity = item.subKey ? item.subKey : 0;
      this.isFcSecurity = item.flag == Constants.fusTraceFlag.FACSE;
      this.cmntInput.nativeElement.focus();
    }
  }

  securityCheckChange(e: any) {
    if (e && !e.checked) {
      this.selectedFCSecurity = 0;
      this.commentText = "";
      this.selectedId = 0;
    }
  }

  handleComment() {
    if (this.commentText == "") {
      this.alertService.showToaster(
        "Please add a comment.",
        SETTINGS.TOASTER_MESSAGES.error
      );
      return;
    } else {
      if (this.isFcSecurity) {
        if (this.selectedFCSecurity <= 0) {
          this.alertService.showToaster(
            "Please select a security.",
            SETTINGS.TOASTER_MESSAGES.error
          );
          return;
        } else {
          this.saveComment();
        }
      } else {
        this.saveComment();
      }
    }
  }

  setCommentToEdit(item: any) {
    if (!_.isEmpty(item)) {
      this.selectedId = item.id ? item.id : 0;
      this.commentText = item.comment ? item.comment : "";
      this.isFcSecurity = item.flag == Constants.fusTraceFlag.FACSE;
      this.selectedFCSecurity = item.subKey ? item.subKey : 0;
    }
  }

  getEnableSecuritesCount() {
    return this.fcSecurities.filter((s: any) => s.disabled == false).length;
  }

  isCheckBoxDisabled() {
    return this.getEnableSecuritesCount() == 0 && this.selectedFCSecurity == 0;
  }

  saveComment() {
    let commentRQ = {
      id: this.selectedId,
      comment: this.commentText,
      mainKey: this.facility.facilityID,
      subKey: this.selectedFCSecurity,
      parentRecId: this.parentCmntId,
      flag: this.isFcSecurity
        ? Constants.fusTraceFlag.FACSE
        : Constants.fusTraceFlag.FAC,
      createdUserWC: this.loggedInWC,
      createdUserDesignation:
        this.applicationService.getLoggedInUserDesignation(),
    };

    this.facilityPaperAddEditService.saveCRComment(commentRQ).subscribe(
      (res: any) => {
        if (res) {
          this.alertService.showToaster(
            "Comment has been saved successfully.",
            SETTINGS.TOASTER_MESSAGES.success
          );

          this.getFacilityComments();
          this.clearComment();
        } else {
          this.alertService.showToaster(
            "An error occurrd. Please try again later.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      },
      (error) => {
        this.alertService.showToaster(
          "An error occurrd. Please try again later.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    );
  }

  removeComment(selectedId: any) {
    let commentRQ = {
      id: selectedId,
    };

    this.facilityPaperAddEditService.deleteCRComment(commentRQ).subscribe(
      (res: any) => {
        if (res) {
          this.alertService.showToaster(
            "Comment has been removed successfully.",
            SETTINGS.TOASTER_MESSAGES.success
          );

          this.getFacilityComments();
          this.clearComment();
        } else {
          this.alertService.showToaster(
            "An error occurrd. Please try again later.",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      },
      (error) => {
        this.alertService.showToaster(
          "An error occurrd. Please try again later.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    );
  }

  clearComment() {
    this.commentText = "";
    this.parentCmntId = 0;
    this.isFcSecurity = false;
    this.selectedFCSecurity = 0;
    this.selectedId = 0;
    this.securityCmntEnabled = true;
  }

  close() {
    this.clearComment();
    this.mdbModalRef.hide();
  }

  isActionEnabled(item: any) {
    var isEnabled: boolean = false;
    if (item) {
      isEnabled = item.isView == 0 && item.createdBy == this.loggedInUser;
    }

    return isEnabled;
  }

  isHigherWCUser() {
    var currentStatus: any =
      this.componentData.facilityPaper &&
      this.componentData.facilityPaper.currentFacilityPaperStatus
        ? this.componentData.facilityPaper.currentFacilityPaperStatus
        : "";

    var isValidWC: boolean =
      this.componentData.facilityPaper.assignUserUpmGroupCode != null &&
      parseInt(this.componentData.facilityPaper.assignUserUpmGroupCode) < 80;

    var isValidDC =
      this.componentData.facilityPaper.assignDepartmentCode == "CA";

    var isAssignerValid =
      this.componentData.facilityPaper.currentAssignUser != "BCC";

    return (
      isAssignerValid &&
      this.loggedInWC >= 71 &&
      (isValidWC || isValidDC) &&
      (currentStatus == Constants.facilityPaperStatusConst.IN_PROGRESS ||
        currentStatus == Constants.facilityPaperStatusConst.CANCEL)
    );
  }

  isCommentable() {
    var currentStatus: any =
      this.componentData.facilityPaper &&
      this.componentData.facilityPaper.currentFacilityPaperStatus
        ? this.componentData.facilityPaper.currentFacilityPaperStatus
        : "";

    return (
      this.componentData.facilityPaper.currentAssignUser == this.loggedInUser &&
      (currentStatus == Constants.facilityPaperStatusConst.IN_PROGRESS ||
        currentStatus == Constants.facilityPaperStatusConst.CANCEL)
    );
  }

  isCmntDisabled() {
    var isDisabled: boolean = false;

    isDisabled =
      this.parentCmntId == 0 &&
      this.selectedId == 0 &&
      this.commentHistory.some(
        (c: any) =>
          c.isView == 0 &&
          c.flag == Constants.fusTraceFlag.FAC &&
          c.createdBy == this.loggedInUser
      );

    return isDisabled;
  }

  isReplyEnabled(item: any) {
    var isEnabled: boolean = false;
    if (item) {
      isEnabled =
        item.createdBy != this.loggedInUser &&
        item.childCmnt.length == 0 &&
        this.loggedInWC <= 50;
    }

    return isEnabled;
  }

  handleSecurityChange() {
    var securityCmnt: any = this.commentHistory.find(
      (c: any) =>
        c.subKey == this.selectedFCSecurity &&
        c.createdBy == this.loggedInUser &&
        c.isView == 0
    );

    if (securityCmnt != null && securityCmnt != undefined) {
      this.setCommentToEdit(securityCmnt);
    } else {
      this.securityCmntEnabled = this.commentHistory.some(
        (c: any) =>
          c.subKey == this.selectedFCSecurity &&
          c.createdBy == this.loggedInUser &&
          c.isView == 1
      );
      this.commentText = "";
    }
  }

  toCommonSecurityContent($event: any) {
    document.getElementById($event).scrollIntoView({
      behavior: "smooth",
      block: "center",
      inline: "center",
    });
  }

  moveUp() {
    document.getElementById("fp-f-prev-mod").scrollIntoView({
      behavior: "smooth",
      block: "center",
      inline: "center",
    });
  }

  refreshComments() {
    this.getFacilityComments();
  }

  handleCollapsed() {
    this.isCollapsed = !this.isCollapsed;
  }
}
