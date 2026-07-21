import { Component, OnInit, Input } from "@angular/core";
import { FacilityPaperAddEditService } from "../../pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-preview-scorecard",
  templateUrl: "./preview-scorecard.component.html",
  styleUrls: ["./preview-scorecard.component.scss"],
})
export class PreviewScorecardComponent implements OnInit {
  evaluations = [];
  @Input() facilityPaperID: number;
  score: any;
  dataLoaded: boolean = false;
  customerEvaluationId: number;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.getCustomerEvaluation();
  }

  getCustomerEvaluation() {
    this.facilityPaperAddEditService
      .getCustomerMaxEvaluationForm(this.facilityPaperID)
      .then((data: any) => {
        this.evaluations = data;

        this.facilityPaperAddEditService
          .getCustomerMaxEvaluationId(this.facilityPaperID)
          .then((data1: any) => {
            this.customerEvaluationId = data1.customerEvaluationId;

            // this.facilityPaperAddEditService
            //   .getEvaluationScore(this.customerEvaluationId)
            //   .then((data1: any) => {
            //     this.score = data1;
            //   });
            if(this.customerEvaluationId != null){
              this.facilityPaperAddEditService
              .getEvaluationScore(this.customerEvaluationId)
              .then((data1: any) => {
                this.score = data1;
              });
            }
          });
      });
  }
}
