import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import * as _ from "lodash";
import * as moment from "moment";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-upc-template-view-comment",
  templateUrl: "./upc-template-view-comment.component.html",
  styleUrls: ["./upc-template-view-comment.component.scss"],
})
export class UpcTemplateViewCommentComponent implements OnInit {
  historyContent: any[] = [];
  commentHistory: any[] = [];
  commentText: string = "";
  latestHistoryId: Number = 0;
  fpUpcSectionData: any = {};
  @Input("nodeData") nodeData: any = [];

  constructor(
    private alertService: AlertService,
    private ref: ChangeDetectorRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.fpUpcSectionData = this.nodeData ? this.nodeData : null;
    if (
      this.nodeData.currentFPStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
      this.nodeData.currentFPStatus == Constants.facilityPaperStatusConst.CANCEL
    ) {
      this.getCommentHistory();
    }
    this.ref.detectChanges();
  }

  getCommentHistory() {
    let request = {
      mainKey: this.fpUpcSectionData.fpUpcSectionDataID,
      condition: Constants.fusTraceFlag.UPCTALL,
      flag: Constants.fusTraceFlag.UPCT,
      status: Constants.fusTraceStatus.ACT,
      subKey: 0,
    };
    this.facilityPaperAddEditService
      .getUPCTemplateCommentHistoryListService(request)
      .subscribe(
        (data: any) => {
          if (_.isEmpty(data)) {
            this.commentHistory = [];
          } else {
            var result: any[] = data.map((r: any, i: number) => ({
              id: r.id,
              parentRecId: r.parentRecordId,
              comment: r.comment,
              createdBy: r.createdUserDisplayName,
              date: moment(r.createdDate).format("MMMM Do YYYY, h:mm:ss a"),
              childCmnt: [],
              isView: r.isView,
            }));
            this.commentHistory = this.sortCommentHistory(result);

            this.ref.detectChanges();
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

  sortCommentHistory(data: any[]): any[] {
    var result: any[] = [];

    // merge child comments with parent comment
    data.forEach((comment: any) => {
      comment.childCmnt = data
        .filter((d: any) => d.parentRecId == comment.id)
        .sort((a: any, b: any) => b.id - a.id);
    });

    //filter child comments
    result = data
      .filter((d: any) => d.parentRecId == 0)
      .sort((a: any, b: any) => b.id - a.id);

    return result;
  }
}
