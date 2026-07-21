import { Component, OnInit } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { Subscription } from 'rxjs';
import { Constants } from 'src/app/core/setting/constants';
import { RiskOpinionHistoryViewerComponent } from 'src/app/shared/components/risk-opinion-history-viewer/risk-opinion-history-viewer.component';
import { RiskOpinionReplyViewerComponent } from 'src/app/shared/components/risk-opinion-reply-viewer/risk-opinion-reply-viewer.component';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'app-credit-risk-opinion-history-new',
  templateUrl: './credit-risk-opinion-history-new.component.html',
  styleUrls: ['./credit-risk-opinion-history-new.component.scss']
})
export class CreditRiskOpinionHistoryNewComponent implements OnInit {
  modalRef: MDBModalRef;

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

  constructor(private facilityPaperAddEditService: FacilityPaperAddEditService,
              private mdbModalService: MDBModalService,
              ) { }

  ngOnInit() {

    this.facilityPaperAddEditService.getRiskCommentList()
    .then((data: any) => {
      if (data) {
        this.previousLockedComments = data;

        // let responseList = data.fpCreditRiskCommentHistory ? data.fpCreditRiskCommentHistory : [];
        // responseList.forEach((data: any) => {
        //   if (data.isLocked === Constants.yesNoConst.Y && data.isValidComment === Constants.yesNoConst.N) {

        //   }
        // })
      }
    });
  }

  getContent(data) {
    return `<pre class="credit-risk-comment-pre-tag">${data || '-'}</pre>`;
  }

  view(data) {
    
    this.modalRef = this.mdbModalService.show(RiskOpinionHistoryViewerComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-90-p modal-dialog-scrollable  modal-xl',
      containerClass: '',
      animated: true,
      data: {
        riskComment: data.creditRiskComment,
        createdUserName: data.createdUserName,

        createdDateStr: data.createdDateStr,

        modifiedUserName: data.modifiedUserName,
        modifiedDateStr: data.modifiedDateStr,
      }
    });
  }




}
