import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import * as _ from "lodash";
import { isEmpty } from "lodash";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-facility-changes-modal",
  templateUrl: "./facility-changes-modal.component.html",
  styleUrls: ["./facility-changes-modal.component.scss"],
})
export class FacilityChangesModalComponent implements OnInit, OnDestroy {
  @ViewChild("cmntInput", { static: false }) cmntInput: ElementRef | any;
  facility: any;
  facilityPaper: any;
  isSecurity: boolean = false;

  commentText: any = "";
  parentCmntId: number = 0;
  selectedId: number = 0;

  commentHistory: any[] = [];

  facilityId: number = 0;
  fcSecurities: any[] = [];
  selectedFCSecurity: number = 0;
  currentFlag: any = "";
  loggedInUser: any = "";
  loggedInWC: number = 0;
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

    this.currentFlag = this.isSecurity
      ? Constants.fusTraceFlag.FACSE
      : Constants.fusTraceFlag.FAC;

    this.setFacilitySecurities();

    if (this.getUnReadCommentsCount() > 0 && this.isCommentable()) {
      this.viewComments();
    }

    this.onFPFacilitiesChangeSub =
      this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
        (data: any) => {
          if (!isEmpty(data)) {
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

    var filteredComments: any[] = comments.filter(
      (c: any) => c.flag == this.currentFlag
    );

    this.commentHistory =
      this.facilityPaperAddEditService.sortCommentHistory(filteredComments);
  }

  getUnReadCommentsCount(): number {
    var comments: any[] = this.facility.fusTraceList
      ? this.facility.fusTraceList
      : [];

    var filteredComments: any[] = comments.filter(
      (c: any) => c.flag == this.currentFlag
    );

    return filteredComments.filter(
      (cmnt: any) =>
        cmnt.isView == 0 &&
        cmnt.createdBy != this.loggedInUser &&
        cmnt.createdUserWC > 50
    ).length;
  }

  setFacilitySecurities() {
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
          }))
        : [];

    this.fcSecurities = this.fcSecurities.concat(result);
  }

  viewComments() {
    var payload: any = {
      mainKey: this.facilityId,
      subKey: 0,
      flag: this.currentFlag,
    };

    this.facilityPaperAddEditService
      .saveFusTraceView(payload)
      .subscribe((res: any) => {
        this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
          this.facilityPaper,
          Constants.fusTraceFlag.FAC
        );
      });
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
      this.facilityPaper
    );
  }

  setParentCommentId(item: any) {
    if (item.id) {
      this.parentCmntId = item.id;
      this.selectedFCSecurity = item.subKey ? item.subKey : 0;
      this.cmntInput.nativeElement.focus();
    }
  }

  setCommentToEdit(item: any) {
    if (!_.isEmpty(item)) {
      this.selectedId = item.id ? item.id : 0;
      this.commentText = item.comment ? item.comment : "";
      this.selectedFCSecurity = item.subKey ? item.subKey : 0;
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
      if (this.isSecurity) {
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

  saveComment() {
    let commentRQ = {
      id: this.selectedId,
      comment: this.commentText,
      mainKey: this.facility.facilityID,
      subKey: this.selectedFCSecurity,
      parentRecId: this.parentCmntId,
      flag: this.isSecurity
        ? Constants.fusTraceFlag.FACSE
        : Constants.fusTraceFlag.FAC,
      createdUserWC: this.applicationService.getLoggedInUserUPMGroupCode()
        ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
        : 0,
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
    this.selectedFCSecurity = 0;
    this.selectedId = 0;
  }

  close() {
    this.clearComment();
    this.mdbModalRef.hide();
  }

  isActionEnabled(item: any) {
    var isEnabled: boolean = false;
    if (item) {
      isEnabled =
        item.isView == 0 &&
        item.createdBy == this.applicationService.getLoggedInUserUserName();
    }

    return isEnabled;
  }

  isCommentable() {
    var currentStatus: any = this.facilityPaper.currentFacilityPaperStatus
      ? this.facilityPaper.currentFacilityPaperStatus
      : "";

    return (
      this.facilityPaper.currentAssignUser == this.loggedInUser &&
      (currentStatus == Constants.facilityPaperStatusConst.IN_PROGRESS ||
        currentStatus == Constants.facilityPaperStatusConst.CANCEL)
    );
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

  isCmntDisabled() {
    var isDisabled: boolean = false;

    isDisabled = this.parentCmntId == 0 && this.selectedId == 0;

    return isDisabled;
  }

  refreshComments() {
    this.getFacilityComments();
  }
}
