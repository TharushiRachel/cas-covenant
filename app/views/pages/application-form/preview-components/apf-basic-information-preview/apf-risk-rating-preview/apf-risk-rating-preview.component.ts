import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";


@Component({
  selector: 'app-apf-risk-rating-preview',
  templateUrl: './apf-risk-rating-preview.component.html',
  styleUrls: ['./apf-risk-rating-preview.component.scss']
})
export class ApfRiskRatingPreviewComponent implements OnInit {

  @Input() basicInformation;
  yesNoConst = Constants.yesNo;
  ratingModel = Constants.ratingModel;

  constructor() {
  }

  ngOnInit() {
  }

}
