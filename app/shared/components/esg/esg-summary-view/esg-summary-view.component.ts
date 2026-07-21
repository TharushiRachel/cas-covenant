import {
  Component,
  Input,
  OnDestroy,
  OnInit,
} from "@angular/core";
import * as moment from "moment";
import { Subscription } from "rxjs";
import { EsgService } from "src/app/core/service/common/esg.service";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-esg-summary-view",
  templateUrl: "./esg-summary-view.component.html",
  styleUrls: ["./esg-summary-view.component.scss"],
})
export class EsgSummaryViewComponent implements OnInit, OnDestroy {
  @Input() facilityPaper: any;
  @Input() isCommitteePaper:boolean = false;

  completedAnnexures: any[] = [];
  selectedRiskCategories: any[] = [];
  approvedESGScore: string = "";
  buComment: string = "";
  opinions: any[] = [];

  onESGRiskScoreChangeSub: Subscription = new Subscription();
  onESGChangeSub: Subscription = new Subscription();
  onESGRiskOpinionChangeSub: Subscription = new Subscription();

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly esgService: EsgService
  ) {}

  ngOnInit() {
    if (this.facilityPaper !== null) {
      this.facilityPaperAddEditService.getFPEnvironmentalRiskOpinion(
        this.facilityPaper.facilityPaperID
      );

      this.approvedESGScore =
        this.facilityPaper.approvedESGScore !== null
          ? this.facilityPaper.approvedESGScore
          : "";
    }
    this.onESGChangeSub = this.esgService.onESGChange.subscribe(
      (annexures: any[]) => {
        if (annexures.length > 0) {
          this.completedAnnexures = annexures;
          let introAnnexure: any = this.completedAnnexures.find(
            (a: any) => a.name.toLowerCase() === "introduction"
          );
          if (introAnnexure !== undefined && introAnnexure !== null) {
            if (
              introAnnexure.questions.length > 0 &&
              introAnnexure.questions[0].answers.length > 0
            ) {
              this.buComment = introAnnexure.questions[0].answers[0].userAnswer;
            }
          }
        } else {
          this.completedAnnexures = [];
        }
      }
    );

    this.onESGRiskScoreChangeSub =
      this.facilityPaperAddEditService.onESGRiskScoreChange.subscribe(
        (res: any) => {
          if (res && res.length > 0) {
            this.selectedRiskCategories = res;
          } else {
            this.selectedRiskCategories = [];
          }
        }
      );

    this.onESGRiskOpinionChangeSub =
      this.facilityPaperAddEditService.onESGRiskOpinionChange.subscribe(
        (res: any[]) => {
          this.opinions = this.sortOpinions(res);
        }
      );
  }

  ngOnDestroy(): void {
    this.onESGChangeSub.unsubscribe();
    this.onESGRiskScoreChangeSub.unsubscribe();
    this.onESGRiskOpinionChangeSub.unsubscribe();
  }

  sortOpinions(dataList: any[]) {
    dataList.sort(
      (a: { riskOpinionId: number }, b: { riskOpinionId: number }) =>
        b.riskOpinionId - a.riskOpinionId
    );
    return dataList.map((d: any, i: number) => ({
      ...d,
      tabTitle:
        i == 0
          ? "Current ESG Risk Opinion and Reply"
          : `History ESG Risk Opinion by ${moment(d.createdDate).format(
              "MMMM Do YYYY"
            )}`,
      isActive: i == 0 && d.riskOpinionReply == null,
      isReplyEditable: i == 0 && d.riskOpinionReply !== null,
    }));
  }

  getRiskGradingBg(score: string) {
    switch (score) {
      case "A":
        return "#dc3545";
      case "B":
        return "#E4D84C";
      case "B+":
        return "#FFBF00";
      case "C":
        return "#00ff00";
      default:
        return "#b7b7b7";
    }
  }

  getActionBy(item: any) {
    if (item.modifiedBy !== null && item.modifiedDate !== null) {
      return `Modified By ${item.modifiedBy} on ${moment(
        item.modifiedDate
      ).format("MMM Do YYYY, h:mm:ss a")}`;
    }
    return `Added By ${item.createdBy} on ${moment(item.createdDate).format(
      "MMM Do YYYY, h:mm:ss a"
    )}`;
  }
}
