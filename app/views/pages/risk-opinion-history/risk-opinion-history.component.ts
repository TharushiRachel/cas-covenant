import { Component, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { FacilityPaperAddEditService } from '../facility-paper/services/facility-paper-add-edit.service';
import { Subscription } from 'rxjs';
import { Constants } from 'src/app/core/setting/constants';
import {uniqBy} from "lodash";


@Component({
  selector: 'app-risk-opinion-history',
  templateUrl: './risk-opinion-history.component.html',
  styleUrls: ['./risk-opinion-history.component.scss']
})
export class RiskOpinionHistoryComponent implements OnInit {

  previousLockedComments = [{
    commentedTimeStr: "",
    createdBy:"",
    createdDateStr:"",
    createdUserName:"",
    creditRiskComment:"",
    facilityPaperFormStatus:"",
    facilityPaperID:"",
    fpCreditRiskCommentID:"",
    isLocked:false,
    isValidComment:false,
    modifiedBy:"",
    modifiedDateStr:"",
    modifiedUserName:"",
    rev:"",
    revType:"",
    status:"",
    upmID:"",
    upmprivilegeCode:"",
  }];

  onCreditRiskCommentListHistory = new Subscription();

  constructor(public mdbModalRef: MDBModalRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService) { }

  ngOnInit() {
    this.facilityPaperAddEditService.getRiskCommentList().then((data: any) => {
    });

    this.facilityPaperAddEditService.getRiskCommentList()
    .then((data: any) => {
      if (data) {
        this.previousLockedComments = data;

        let responseList = data.fpCreditRiskCommentHistory ? data.fpCreditRiskCommentHistory : [];
        responseList.forEach((data: any) => {
          if (data.isLocked === Constants.yesNoConst.Y && data.isValidComment === Constants.yesNoConst.N) {

          }
        })
      }
    });
  }


  getContent(data) {
    return `<pre class="credit-risk-comment-pre-tag">${data || '-'}</pre>`;
  }

}
