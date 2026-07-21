import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../core/setting/constants";
import {RiskOpinionReplyViewerComponent} from "../../../shared/components/risk-opinion-reply-viewer/risk-opinion-reply-viewer.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from '../../pages/facility-paper/services/facility-paper-add-edit.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { Data } from '../../pages/application-topic/components/application-topic-add-edit/application-form-topic-add-data/application-form-topic-add-data.component';
import { AppUtils } from 'src/app/shared/app.utils';
import { Subscription } from 'rxjs';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import * as _ from 'lodash';


@Component({
  selector: 'app-preview-risk-comments',
  templateUrl: './preview-risk-comments.component.html',
  styleUrls: ['./preview-risk-comments.component.scss']
})
export class PreviewRiskCommentsComponent implements OnInit {

  @Input("riskCommentList") riskCommentList: any = [];
  activeRiskComment;
  creditRiskReply;
  previousLockedComments: any = [];
  modalRef: MDBModalRef;

  casBranchDepartments = [];
  riskDepartment: any = {};

  isLoading = false;
  isLoad = true;


  onCreditRiskCommentListChange = new Subscription();

  constructor(private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private cacheService: CacheService,) { }



ngOnInit() {
  // this.riskCommentList.forEach((data: any) => {

  //   this.facilityPaperAddEditService.getFacilityPaperByIDT().then((data1 : any) =>{
  //     this.facilityPaperAddEditService.getRiskDivCode().then((data2: any)=>{
  //       if(data1 == data2){
  //         if(this.applicationService.getLoggedInUserDivCode() == data2){

  //           this.isLoad = true;
  //         }
  //         else if (this.facilityPaperAddEditService.getFacilityPaperByIDT() != data2){
  //           this.isLoad = false;
  //         }
  //         else{
  //           this.isLoad = false;
  //         }
  //       }

  //       if(this.isLoad == true){
  //         this.isLoad = true;

  //         if (data.isValidComment === Constants.yesNoConst.Y) {
  //           this.activeRiskComment = data;
  //           this.creditRiskReply = data.fpCreditRiskReplyDTO;
  //         }
  //       }
  //     })
  //   })


  //   if (data.isLocked === Constants.yesNoConst.Y && data.isValidComment === Constants.yesNoConst.N && data.version > 1) {
  //     this.previousLockedComments.push(data);
  //   }
  // })

      if (!_.isEmpty(this.riskCommentList)) {
        this.previousLockedComments = this.riskCommentList.previousLockedComments;
        this.activeRiskComment = this.riskCommentList.activeRiskComment;
        if (!_.isEmpty(this.riskCommentList.activeRiskComment)) {
          this.creditRiskReply = this.riskCommentList.activeRiskComment.fpCreditRiskReplyDTO;
        } else {
          this.creditRiskReply = null;
        }
        this.isLoad = this.riskCommentList.load
        this.isLoading = this.riskCommentList.loadHistory
        
      } else {
        this.previousLockedComments = [];
        this.activeRiskComment = null;
        this.creditRiskReply = null;        
      }

}


  getCommentWithPreTag(comment) {
    return `<pre class="credit-risk-comment-pre-tag">${comment || '-'}</pre>`
  }

  view(data) {
    this.modalRef = this.mdbModalService.show(RiskOpinionReplyViewerComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        riskComment: data.creditRiskComment,
        riskReply: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.replyComment : '',

        createdUserName: data.createdUserName,
        createdRiskReplyUserName: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.createdUserName: '',

        createdDateStr: data.createdDateStr,
        createdDate: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.createdDateStr: '',

        modifiedUserName: data.modifiedUserName,
        modifiedDateStr: data.modifiedDateStr,

        modifiedRiskReplyUserName: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.modifiedUserName: '',
        modifiedDate: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.modifiedDateStr: '',

        divCode: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.createdDivCode:'',
      }
    });
  }

}
