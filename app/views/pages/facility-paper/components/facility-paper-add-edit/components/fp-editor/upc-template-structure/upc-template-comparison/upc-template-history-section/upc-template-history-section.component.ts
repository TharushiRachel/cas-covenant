import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import { MDBModalService } from "ng-uikit-pro-standard";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import { UpcTemplateHistoryCommentViewComponent } from "../upc-template-history-comment-view/upc-template-history-comment-view.component";
import * as _ from "lodash";
import * as moment from "moment";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-upc-template-history-section",
  templateUrl: "./upc-template-history-section.component.html",
  styleUrls: ["./upc-template-history-section.component.scss"],
})
export class UpcTemplateHistorySectionComponent implements OnInit {
  comparisonDateContent = [];
  historyContent = [];
  latestHistoryId: Number = 0;
  fpUpcSectionData: any = [];
  isButtonClicked: boolean = false;

  @Input("nodeData") nodeData: any;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private ref: ChangeDetectorRef,
    private alertService: AlertService,
    private mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    this.fpUpcSectionData = this.nodeData ? this.nodeData : {};
    if (
      this.nodeData.currentFPStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
      this.nodeData.currentFPStatus ==
        Constants.facilityPaperStatusConst.CANCEL ||
      this.nodeData.currentFPStatus ==
        Constants.facilityPaperStatusConst.APPROVED
    ) {
      this.getUPCTemplateComparisonByDate(this.fpUpcSectionData);
    }
    this.ref.detectChanges();
  }

  openUPCCommentModal(templateData: any) {
    this.mdbModalService.show(UpcTemplateHistoryCommentViewComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Director Details",
        content: {
          templateData: templateData,
        },
      },
    });
  }

  getUPCTemplateComparisonByDate(requestData: any) {
    let request = {
      fpUPCSectionDataId: requestData.fpUpcSectionDataID,
      facilityPaperId: requestData.facilityPaperID,
      upcSectionId: requestData.upcSectionID,
    };
    this.facilityPaperAddEditService
      .getUPCTemplateComparisonByDateService(request)
      .subscribe(
        (data: any) => {
          if (_.isEmpty(data)) {
            this.comparisonDateContent = [];
          } else {
            this.comparisonDateContent = data;
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

  toggleUPCTemplateContent(createdDate: any) {
    this.historyContent = [];
    let data = [];
    data = this.comparisonDateContent.filter(
      (dateData) => dateData["createdDate"] === createdDate
    );

    if (data[0]["historyList"]) {
      var result: any[] = data[0]["historyList"].map((r: any, i: number) => ({
        fpUPCSectionDataHistoryID: r.fpUPCSectionDataHistoryID,
        fpUPCSectionDataId: r.fpUPCSectionDataId,
        data: r.data,
        status: r.status,
        comment: r.comment,
        createdBy: r.createdBy,
        modifiedUserDisplayName: r.modifiedUserDisplayName,
        date: moment(r.modifiedDate).format("MMMM Do YYYY, h:mm:ss a"),
      }));
      this.historyContent = result;
    } else {
      this.historyContent = [];
    }
    this.isButtonClicked = true;
    this.ref.detectChanges();
  }
}
