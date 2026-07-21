import { Component, OnDestroy, OnInit } from "@angular/core";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Subscription } from "rxjs";
import * as moment from "moment";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-fp-md-assistance-cmnt-view",
  templateUrl: "./fp-md-assistance-cmnt-view.component.html",
  styleUrls: ["./fp-md-assistance-cmnt-view.component.scss"],
})
export class FpMdAssistanceCmntViewComponent implements OnInit, OnDestroy {
  comments: any[] = [];

  onMDReviewCommentsChangeSub: Subscription = new Subscription();

  constructor(
    private readonly applicationService: ApplicationService,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
  ) {}

  ngOnInit() {
    let wc: number = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode(),
    );

    this.onMDReviewCommentsChangeSub =
      this.facilityPaperAddEditService.onMDReviewCommentsChange.subscribe(
        (data: any[]) => {
          if (data !== null) {
            this.comments = data
              .map((d: any) => ({
                ...d,
                date: moment(d.createdDate).format("Do MMMM YYYY HH:mm:s"),
                commentedBy: d.createdUserDisplayName,
                isActionEnabled:
                  d.createdUserWC !== wc && d.isView === Constants.yesNoConst.N,
              }))
              .sort((a: any, b: any) => b.fpCommentID - a.fpCommentID);
          }
        },
      );
  }

  ngOnDestroy(): void {
    this.onMDReviewCommentsChangeSub.unsubscribe();
  }

  viewComment(comment: any) {
    if (comment !== null) {
      let saveData: any = {
        fpCommentID: comment ? comment.fpCommentID : null,
        facilityPaperId: comment.facilityPaperId,
      };

      this.facilityPaperAddEditService
        .markAsViewMDComments(saveData)
        .then((comments: any[]) => {});
    }
  }
}
