import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import * as _ from "lodash";
import * as moment from "moment";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-upc-template-comment-section",
  templateUrl: "./upc-template-comment-section.component.html",
  styleUrls: ["./upc-template-comment-section.component.scss"],
})
export class UPCTemplateCommentSectionComponent implements OnInit {
  comparisonTableContent: any[] = [];
  comparisonDateContent: any[] = [];
  historyContent: any[] = [];
  commentHistory: any[] = [];
  commentText: string = "";
  latestHistoryId: Number = 0;
  fpUpcSectionData: any = {};
  parentCmntId: number = 0;
  isLoaded: boolean = false;
  selectedId: number = 0;
  loggedInUser: any = "";
  loggedInWC: number = 0;

  htmlContent: string = "";

  isCollapsed: boolean = false;

  @Input("nodeData") nodeData: any = [];
  @Output("onCloseModel") onCloseModel = new EventEmitter();

  @ViewChild("cmntInput", { static: false }) cmntInput: ElementRef | any;

  constructor(
    private alertService: AlertService,
    private applicationService: ApplicationService,
    private ref: ChangeDetectorRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.loggedInUser = this.applicationService.getLoggedInUserUserName();
    this.loggedInWC = this.applicationService.getLoggedInUserUPMGroupCode()
      ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
      : 0;

    this.fpUpcSectionData = this.nodeData ? this.nodeData : {};
    
    this.htmlContent =
      this.nodeData && this.nodeData.content ? this.nodeData.content : "";

    if (
      this.nodeData.currentFPStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
      this.nodeData.currentFPStatus == Constants.facilityPaperStatusConst.CANCEL
    ) {
      this.getCommentHistory();
      this.getLatestHistoryId();
    }

    this.ref.detectChanges();
  }

  public getCommentHistory() {
    let request = {
      mainKey: this.fpUpcSectionData.fpUpcSectionDataID,
      condition: Constants.fusTraceFlag.UPCTALL,
      flag: Constants.fusTraceFlag.UPCT,
      status: Constants.fusTraceStatus.ACT,
    };
    this.facilityPaperAddEditService
      .getUPCTemplateCommentHistoryListService(request)
      .subscribe(
        (data: any) => {
          if (_.isEmpty(data)) {
            this.commentHistory = [];
          } else {
            var result: any[] = data.map((r: any, i: number) => ({
              ...r,
              addedBy: r.createdUserDisplayName,
              date: moment(r.createdDate).format("MMMM Do YYYY, h:mm:ss a"),
              childCmnt: [],
            }));

            if (
              this.getUnReadCommentsCount(result) > 0 &&
              this.isCommentable()
            ) {
              this.viewComments();
            }

            this.commentHistory =
              this.facilityPaperAddEditService.sortCommentHistory(result);
            this.ref.detectChanges();
          }
        },
        (error) => {
          this.alertService.showToaster(
            "Please contact system administrator",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
  }

  getLatestHistoryId() {
    let request = {
      fpUPCSectionDataId: this.fpUpcSectionData.fpUpcSectionDataID,
    };
    this.facilityPaperAddEditService
      .getLatestHistoryIdService(request)
      .subscribe(
        (data: any) => {
          if (_.isEmpty(data)) {
            this.latestHistoryId = 0;
          } else {
            this.latestHistoryId = data[0];
          }
        },
        (error) => {
          this.alertService.showToaster(
            "Please contact system administrator",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
  }

  addComment() {
    if (this.commentText == "") {
      this.alertService.showToaster(
        "Please add a comment!",
        SETTINGS.TOASTER_MESSAGES.error
      );
      return;
    } else {
      let commentRQ = {
        id: this.selectedId,
        mainKey: this.fpUpcSectionData.fpUpcSectionDataID,
        subKey: this.latestHistoryId,
        comment: this.commentText,
        flag: Constants.fusTraceFlag.UPCT,
        parentRecId: this.parentCmntId,
        userDisplayName: this.applicationService.getUserDisplayName(),
        createdUserWC: this.applicationService.getLoggedInUserUPMGroupCode()
          ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
          : 0,
        createdUserDesignation:
          this.applicationService.getLoggedInUserDesignation(),
      };

      this.facilityPaperAddEditService
        .saveUPCTemplateComparisonCommentService(commentRQ)
        .subscribe(
          (response: any) => {
            if (response == true || response.result == true) {
              this.alertService.showToaster(
                "Comment has been saved successfully.",
                SETTINGS.TOASTER_MESSAGES.success
              );
              this.getCommentHistory();
              this.ref.detectChanges();
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
  }

  setParentCommentId(id: any) {
    if (id) {
      this.parentCmntId = id;
      this.cmntInput.nativeElement.focus();
    }
  }

  setCommentToEdit(item: any) {
    if (!_.isEmpty(item)) {
      this.selectedId = item.id ? item.id : 0;
      this.commentText = item.comment ? item.comment : "";
    }
  }

  clearComment() {
    this.commentText = "";
    this.parentCmntId = 0;
    this.selectedId = 0;
  }

  getUnReadCommentsCount(data: any[]): number {
    return data.filter(
      (cmnt: any) =>
        cmnt.isView == 0 &&
        cmnt.createdBy != this.loggedInUser &&
        (this.loggedInWC > 50
          ? cmnt.createdUserWC <= 50
          : cmnt.createdUserWC > 50)
    ).length;
  }

  viewComments() {
    var data: any = {
      mainKey: this.nodeData.fpUpcSectionDataID,
      subKey: 0,
      flag: Constants.fusTraceFlag.UPCT,
    };

    this.facilityPaperAddEditService
      .saveFusTraceView(data)
      .subscribe((res: any) => {
        this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
          this.nodeData,
          Constants.fusTraceFlag.UPCT
        );
      });
  }

  isActionEnabled(item: any) {
    var isEnabled: boolean = false;
    if (item) {
      isEnabled = item.isView == 0 && item.createdBy == this.loggedInUser;
    }

    return isEnabled;
  }

  isHigherWCUser() {
    var isValidDC = this.nodeData.assignDepartmentCode == "CA";

    var isAssignerValid = this.nodeData.currentAssignUser != "BCC";

    return (
      isAssignerValid &&
      this.loggedInWC >= 71 &&
      ((this.nodeData.currentFPWC != 0 && this.nodeData.currentFPWC < 80) ||
        isValidDC) &&
      (this.nodeData.currentFPStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
        this.nodeData.currentFPStatus ==
          Constants.facilityPaperStatusConst.CANCEL)
    );
  }

  isCommentable() {
    return (
      this.nodeData.currentAssignUser == this.loggedInUser &&
      (this.nodeData.currentFPStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
        this.nodeData.currentFPStatus ==
          Constants.facilityPaperStatusConst.CANCEL)
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

          this.getCommentHistory();
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

  isCmntDisabled() {
    var isDisabled: boolean = false;

    if (this.loggedInWC > 50) {
      isDisabled =
        this.parentCmntId == 0 &&
        this.selectedId == 0 &&
        this.commentHistory.some(
          (c: any) =>
            c.isView == 0 &&
            c.flag == Constants.fusTraceFlag.UPCT &&
            c.createdBy == this.loggedInUser
        );
    } else {
      isDisabled = this.parentCmntId == 0 && this.selectedId == 0;
    }
    return isDisabled;
  }

  close() {
    this.onCloseModel.emit();
  }

  handleCollapsed() {
    this.isCollapsed = !this.isCollapsed;
  }
}
