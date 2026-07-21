import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import * as _ from "lodash";
import * as moment from "moment";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-upc-template-history-comment-view",
  templateUrl: "./upc-template-history-comment-view.component.html",
  styleUrls: ["./upc-template-history-comment-view.component.scss"],
})
export class UpcTemplateHistoryCommentViewComponent implements OnInit {
  upcTemplateCommentHistoryList: [] = [];
  templateData = [];
  content: any;
  latestHistoryId: Number = 0;
  commentHistory = [];

  constructor(
    public upcTemplateHistoryCommentModalRef: MDBModalRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private alertService: AlertService,
    private ref: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.templateData = this.content.templateData;

    this.getLatestHistoryId();
    this.getCommentHistory();
  }

  getLatestHistoryId() {
    let request = {
      fpUPCSectionDataId: this.templateData["fpUpcSectionDataID"],
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
          console.error(error);
          this.alertService.showToaster(
            "Please contact system administrator",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
  }

  getCommentHistory() {
    let request: any = {
      mainKey: this.templateData["fpUPCSectionDataId"],
      subKey: this.templateData["fpUPCSectionDataHistoryID"],
      condition: Constants.fusTraceFlag.UPCT,
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

            this.commentHistory =
              this.facilityPaperAddEditService.sortCommentHistory(result);
          }
          this.ref.detectChanges();
        },
        (error) => {
          this.alertService.showToaster(
            "Please contact system administrator",
            SETTINGS.TOASTER_MESSAGES.error
          );
        }
      );
  }

  onCloseModel() {
    this.upcTemplateHistoryCommentModalRef.hide();
  }
}
