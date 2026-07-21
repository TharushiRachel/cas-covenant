import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../core/setting/constants";

@Component({
  selector: 'app-facility-paper-reviewer-label',
  templateUrl: './facility-paper-reviewer-label.component.html',
  styleUrls: ['./facility-paper-reviewer-label.component.scss']
})
export class FacilityPaperReviewerLabelComponent implements OnInit {

  @Input('facilityPaper') facilityPaper: any;
  title;
  label;
  backgroundColor;
  facilityPaperReviewStatusConst = Constants.paperReviewStatusConst;

  constructor() {
  }

  ngOnInit() {
    let facilityPaperReviewStatus = this.facilityPaper.paperReviewStatus;

    switch (facilityPaperReviewStatus) {

      case  this.facilityPaperReviewStatusConst.REJECTED : {
        this.label = "R";
        this.title = "Review Rejected by " + this.facilityPaper.reviewUserDisplayName;
        this.backgroundColor = '#cc000073';
        break;
      }
      case  this.facilityPaperReviewStatusConst.APPROVED : {
        this.label = "A";
        this.title = "Review Approved by " + this.facilityPaper.reviewUserDisplayName;
        this.backgroundColor = '#007e338a';
        break;
      }

      case  this.facilityPaperReviewStatusConst.SAVED:
      case  this.facilityPaperReviewStatusConst.ACTION_REQUIRED : {
        this.label = "P";
        this.title = "Review Pending";
        this.backgroundColor = '#ffbb33a6';
        break;
      }

      case  this.facilityPaperReviewStatusConst.REPLIED : {
        this.label = "R";
        this.title = "Replied to Reviewer " + this.facilityPaper.reviewUserDisplayName;
        this.backgroundColor = '#ffbb33a6';
        break;
      }

      default : {
        this.label = "";
        this.title = "";
        this.backgroundColor = "";
      }

    }
  }

}
