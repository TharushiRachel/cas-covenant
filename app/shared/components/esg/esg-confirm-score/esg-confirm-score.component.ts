import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-esg-confirm-score",
  templateUrl: "./esg-confirm-score.component.html",
  styleUrls: ["./esg-confirm-score.component.scss"],
})
export class EsgConfirmScoreComponent implements OnInit, OnDestroy {
  heading: string = "";
  message: string = "";
  action: Subject<any> = new Subject<any>();

  isApproval: boolean = false;
  score: string = "";
  riskRatingOptions: any[] = Constants.esgRiskRatingList;

  constructor(private readonly mdbModalRef: MDBModalRef) {}

  ngOnInit() {
    if (this.isApproval) {
      this.heading = "Finalized ESG Risk Rating";
      this.message =
        "Do you want to finalized this selected risk rating? If not provide the rate,";
    } else {
      this.heading = "Finalized ESG Risk Rate Edit";
      this.message = "Do you want to edit this finalized risk rate?";
    }
  }

  onNoClick(): void {
    let request: any = {
      score: this.score,
      isApprove: false,
    };
    this.action.next(request);
    this.mdbModalRef.hide();
  }

  onYesClick(): void {
    let request: any = {
      score: this.score,
      isApprove: true,
    };
    this.action.next(request);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  getButtonTxt() {
    if (this.isApproval) {
      if (this.score) {
        return "Finalized with New Rate";
      }
      return "Finalized";
    }

    return "Yes";
  }

  onClear() {
    this.score = "";
  }
}
