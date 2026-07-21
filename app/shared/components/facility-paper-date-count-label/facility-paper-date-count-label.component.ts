import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../core/setting/constants";

@Component({
  selector: 'app-facility-paper-date-count-label',
  templateUrl: './facility-paper-date-count-label.component.html',
  styleUrls: ['./facility-paper-date-count-label.component.scss']
})
export class FacilityPaperDateCountLabelComponent implements OnInit {

  @Input('facilityPaper') facilityPaper: any;
  @Input('tooltipContent') tooltipContent: any;
  title;
  label;
  borderColor;
  currentFacilityPaperStatus = Constants.facilityPaperStatusConst;

  constructor() {
  }

  ngOnInit() {
    let paperReviewStatus = this.facilityPaper.currentFacilityPaperStatus;

    if (this.facilityPaper.daysDiff > 12) {
      this.borderColor = '#D84662'
    } else {
      this.borderColor = '#7FDB8E'
    }

    switch (paperReviewStatus) {

      case  this.currentFacilityPaperStatus.REJECTED : {
        this.title = this.tooltipContent ? this.tooltipContent : "Days From " + this.facilityPaper.createdDateStr;
        break;
      }
      case  this.currentFacilityPaperStatus.APPROVED : {
        this.title = this.tooltipContent ? this.tooltipContent : "Days From " + this.facilityPaper.createdDateStr;
        break;
      }

      case  this.currentFacilityPaperStatus.CANCEL: {
        this.title = this.tooltipContent ? this.tooltipContent : "Days From " + this.facilityPaper.canceledDateStr;
        break;
      }

      case  this.currentFacilityPaperStatus.IN_PROGRESS : {
        this.title = this.tooltipContent ? this.tooltipContent : "Days From " + this.facilityPaper.inProgressDateStr;
        break;
      }

      case  this.currentFacilityPaperStatus.DRAFT : {
        this.title = this.tooltipContent ? this.tooltipContent : "Days From " + this.facilityPaper.createdDateStr;
        break;
      }

      default : {
        this.label = "";
        this.title = this.tooltipContent ? this.tooltipContent : "";
      }

    }
  }

}
