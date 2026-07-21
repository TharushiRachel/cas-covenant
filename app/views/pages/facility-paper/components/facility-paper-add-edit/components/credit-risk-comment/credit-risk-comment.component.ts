import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {FacilityPaperAddEditService} from "../../../../services/facility-paper-add-edit.service";
import {Subscription} from "rxjs";
import {CommonPopupWithTinyMceEditorComponent} from "../../../../../../../shared/components/common-popup-with-tiny-mce-editor/common-popup-with-tiny-mce-editor.component";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../core/setting/constants";
import {AppUtils} from "../../../../../../../shared/app.utils";
import {CacheService} from "../../../../../../../core/service/data/cache.service";
import {RiskOpinionReplyViewerComponent} from "../../../../../../../shared/components/risk-opinion-reply-viewer/risk-opinion-reply-viewer.component";
import * as _ from "lodash";

import { Router } from '@angular/router';
import { RiskOpinionHistoryComponent } from 'src/app/views/pages/risk-opinion-history/risk-opinion-history.component';
import { LocalStorage } from 'ngx-webstorage';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';



@Component({
  selector: 'app-credit-risk-comment',
  templateUrl: './credit-risk-comment.component.html',
  styleUrls: ['./credit-risk-comment.component.scss']
})
export class CreditRiskCommentComponent implements OnInit, OnDestroy {

  @ViewChild('model,', {static:false}) model: RiskOpinionHistoryComponent

  @Input('facilityPaper') facilityPaper: any = {};
  modalRef: MDBModalRef;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  listType = "fpCreditRiskComment";
  activeRiskComment;
  creditRiskReply;

  createdUserName;
  createdDateStr;

  modifiedUserName
  modifiedDateStr;

  createdRiskReplyUserName;
  createdDate;

  modifiedRiskReplyUserName;
  modifiedDate;

  divCode;

  data1: any;
  data2: any;
  facilityPaperByIDT: any;

  remarkList = [];
  previousLockedComments = [];
  casBranchDepartments = [];
  riskDepartment: any = {};
  isLoading = false;
  isLoad: boolean = false;

  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  onCreditRiskCommentListChange = new Subscription();
  onSaveOrUpdateFpCreditRiskCommentListChange = new Subscription();
  onAddEditCreditRiskReplyChange = new Subscription();
  onRefreshFacilityPaperByID = new Subscription();

  constructor(private mdbModalService: MDBModalService,
              private facilityPaperAddEditService: FacilityPaperAddEditService,
              private applicationService: ApplicationService,
              private cacheService: CacheService,
              private router: Router,
              private urlEncodeService: UrlEncodeService,) {
  }

  ngOnInit() {

    this.casBranchDepartments = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCH_DEPARTMENT_LIST);
    this.riskDepartment = AppUtils.getCasBranchDepartment(this.casBranchDepartments, SETTINGS.BRANCH_DEPARTMENT_LIST.RISK_MANAGEMENT_AND_COMPLIENCE);

    // this.onCreditRiskCommentListChange = this.facilityPaperAddEditService.onCreditRiskCommentListChange
    //   .subscribe((data: any) => {
    //     if (data) {
    //       this.previousLockedComments = [];
    //       let responseList = data.fpCreditRiskCommentList ? data.fpCreditRiskCommentList : [];
    //       responseList.forEach((data: any) => {

    //         this.facilityPaperAddEditService.getFacilityPaperByIDT().then((data1: any) =>{

    //           this.facilityPaperAddEditService.getRiskDivCode().then((data2: any) =>{
    //           if(data1 == data2){

    //             if(this.applicationService.getLoggedInUserDivCode()==data2){
    //               this.isLoad = true;
    //             }

    //             else if(this.facilityPaperAddEditService.getFacilityPaperByIDT() != data2){
    //               this.isLoad = false;
    //             }

    //             else{
    //               this.isLoad = false;
    //             }

    //            }


    //             if(this.isLoad == true){
    //               this.isLoad = true;

    //                 if (data.isValidComment === Constants.yesNoConst.Y) {
    //                   this.activeRiskComment = data;
    //                   this.creditRiskReply = data.fpCreditRiskReplyDTO;
    //                   this.createdUserName = data;
    //                   this.createdRiskReplyUserName = data.fpCreditRiskReplyDTO;
    //                   this.createdDateStr = data;
    //                   this.createdDate = data.fpCreditRiskReplyDTO;
    //                   this.modifiedUserName = data;
    //                   this.modifiedDateStr = data;
    //                   this.modifiedRiskReplyUserName = data.fpCreditRiskReplyDTO;
    //                   this.modifiedDate = data.fpCreditRiskReplyDTO;
    //                   this.divCode = data.fpCreditRiskReplyDTO;
    //                 }
    //              }

    //           })
    //         });


    //         if (data.isLocked === Constants.yesNoConst.Y && data.isValidComment === Constants.yesNoConst.N && data.version > 1) {
    //           this.previousLockedComments.push(data);
    //         }
    //       })
    //     }
    //   });

    this.onCreditRiskCommentListChange = this.facilityPaperAddEditService.onCreditRiskCommentListChange
      .subscribe((data: any) => {
        if (data) {
          this.LoadData(data);
        }
      });

    this.onRefreshFacilityPaperByID = this.facilityPaperAddEditService.onRefreshFacilityPaperByID
      .subscribe((data: any) => {
        if (data) {
          this.LoadData(data);
        }
      });


    this.isEqualLoginAndAssignUser();


  //checking the risk dept div code : if 874 then load the page
  this.facilityPaperAddEditService.getRiskDivCode().then((data: any) =>{

    if(this.applicationService.getLoggedInUserDivCode() == data){
      this.isLoading = true;
      this.onCreditRiskCommentListChange = this.facilityPaperAddEditService.onCreditRiskCommentListChange
    .subscribe((data: any) => {
      if (data) {
        this.previousLockedComments = [];
        let responseList = data.fpCreditRiskCommentList ? data.fpCreditRiskCommentList : [];
        responseList.forEach((data: any) => {
          if (data.isValidComment === Constants.yesNoConst.Y) {
            this.activeRiskComment = data;
            this.creditRiskReply = data.fpCreditRiskReplyDTO;
          }
          if (data.isLocked === Constants.yesNoConst.Y && data.isValidComment === Constants.yesNoConst.N && data.version > 1) {
            this.previousLockedComments.push(data);
          }
        })
      }
    });


    this.onSaveOrUpdateFpCreditRiskCommentListChange = this.facilityPaperAddEditService.onSaveOrUpdateFpCreditRiskCommentListChange
    .subscribe((data: any) => {
      if (data) {
        let responseList = data.fpCreditRiskCommentList ? data.fpCreditRiskCommentList : [];
        if (responseList.length) {
          this.previousLockedComments = [];
          responseList.forEach((data: any) => {
            if (data.isValidComment === Constants.yesNoConst.Y) {
              this.activeRiskComment = data;
              this.creditRiskReply = data.fpCreditRiskReplyDTO;
            }
            if (data.isLocked === Constants.yesNoConst.Y && data.isValidComment === Constants.yesNoConst.N && data.version > 1) {
              this.previousLockedComments.push(data);
            }
          })

        }

      }
    });


    }
  });

  if (this.isEqualLoginAndAssignUser()) {

    this.onAddEditCreditRiskReplyChange = this.facilityPaperAddEditService.onAddEditCreditRiskReplyChange
    .subscribe((data: any) => {
      if (data) {
        let responseList = data.fpCreditRiskCommentList ? data.fpCreditRiskCommentList : [];

        if (responseList.length) {
          responseList.forEach((data: any) => {
            if (data.isValidComment === Constants.yesNoConst.Y) {
              this.activeRiskComment = data;
              this.creditRiskReply = data.fpCreditRiskReplyDTO;
            }
          })
        }
      }
    });

  }

    this.facilityPaperAddEditService.refreshFacilityPaperByID();

}

  openAddCommentModal($event, riskComment, content?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(CommonPopupWithTinyMceEditorComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-lg',
      containerClass: '',
      animated: false,
      data: {
        isSaveAndCloseEnabled: true,
        content: {
          header: "Risk Opinion",
          dataToEdit: content ? content : '',
          facilityPaper: this.facilityPaper
        },
      }
    });

    this.modalRef.content.action.subscribe((commentContent: any) => {

      if (this.activeRiskComment) {
        if (this.activeRiskComment.isLocked) {
          if (this.activeRiskComment.isLocked == Constants.yesNoConst.N) {
            riskComment.fpCreditRiskCommentID =this.activeRiskComment.fpCreditRiskCommentID
          }
        }

      }
      this.saveOrUpdateFpRiskComment(riskComment, commentContent);
    });
  }

  openAddReplyModal($event, riskReply, content?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(CommonPopupWithTinyMceEditorComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-lg',
      containerClass: '',
      animated: false,
      data: {
        isSaveAndCloseEnabled: true,
        content: {
          header: "Reply For Opinion",
          dataToEdit: content ? content : '',
          facilityPaper: this.facilityPaper,
        },
      }
    });

    this.modalRef.content.action.subscribe((replyContent: any) => {
      if (this.creditRiskReply) {
          riskReply.fpCreditRiskReplyID =this.creditRiskReply.fpCreditRiskReplyID
      }
      this.saveOrUpdateFpRiskReply(riskReply, replyContent);
    });
  }

  view(data) {
    this.modalRef = this.mdbModalService.show(RiskOpinionReplyViewerComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable  modal-lg',
      containerClass: '',
      animated: true,
      data: {
        riskComment: data.creditRiskComment,
        riskReply: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.replyComment: '',

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

  ngOnDestroy(): void {
    this.onCreditRiskCommentListChange.unsubscribe();
    this.onSaveOrUpdateFpCreditRiskCommentListChange.unsubscribe();
    this.onAddEditCreditRiskReplyChange.unsubscribe();
    this.onRefreshFacilityPaperByID.unsubscribe();
  }

  isEqualLoginAndAssignUser() {
    if (this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID()) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED;
  }

  isRejected() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.REJECTED;
  }

  isAbleToEditRiskComment(data) {
    return data && data.isLocked == Constants.yesNoConst.N &&
      this.riskDepartment.branchDepartmentDivCode == this.applicationService.getLoggedInUserDivCode()
  }

  isAbleToAddReplyOnRiskComment(data) {
    return data.isLocked == Constants.yesNoConst.Y && _.isEmpty(this.creditRiskReply) && !this.isRiskUserLoggedIn();
  }

  isAbleToEditRiskReply(data) {
    return data.createdDivCode == this.applicationService.getLoggedInUserDivCode();
  }

  isRiskUserLoggedIn() {
    return this.riskDepartment.branchDepartmentDivCode ? this.riskDepartment.branchDepartmentDivCode == this.applicationService.getLoggedInUserDivCode() : false;
  }

  // new
  isOtherUserLoggedIn(){
    const divCode = "874";
    if(this.applicationService.getLoggedInUserDivCode() == divCode){
      this.isLoading = true;
    }
    else{
      this.isLoading=false;
    }
    // console.log(this.isLoading);
    // console.log(this.applicationService.getLoggedInUserDivCode());
  }

  //new
  // isOtherUserLoggedIn(){
  //   const valueToCheck = 874;
  //   const result = this.applicationService.getLoggedInUserDivCode();
  //   if(this.applicationService.getLoggedInUserDivCode() == '874'){
  //     this.isLoading = true;
  //   }
  // }



  saveOrUpdateFpRiskComment(riskComment, commentContent) {
    let data = Object.assign({}, {
      facilityPaperID: this.facilityPaper.facilityPaperID,
      userName: this.applicationService.getLoggedInUserDisplayName(),
      upmID: this.applicationService.getLoggedInUserUserID(),
      fpCreditRiskCommentDTOS: [Object.assign({}, {
          fpCreditRiskCommentID: riskComment.fpCreditRiskCommentID,
          facilityPaperID: this.facilityPaper.facilityPaperID,
          upmprivilegeCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          creditRiskComment: commentContent,
          commentedBy: this.applicationService.getLoggedInUserDisplayName(),
          status: 'ACT',
          isLocked: 'N',
        }
      )]
    });
    this.facilityPaperAddEditService.saveOrUpdateFpRiskComment(AppUtils.trim(data));
  }

  saveOrUpdateFpRiskReply(riskReply, replyContent) {
    let data = Object.assign({}, {
      facilityPaperID: this.facilityPaper.facilityPaperID,
      fpCreditRiskCommentID: this.activeRiskComment.fpCreditRiskCommentID,
      fpCreditRiskReplyID: riskReply.fpCreditRiskReplyID,
      createdUserName: this.applicationService.getLoggedInUserDisplayName(),
      modifiedUserName: this.applicationService.getLoggedInUserDisplayName(),
      createdDivCode: this.applicationService.getLoggedInUserDivCode(),
      replyComment: replyContent,
      status: Constants.statusConst.ACT,
    });
    this.facilityPaperAddEditService.addEditCreditRiskReply(AppUtils.trim(data))
  }

  getCommentWithPreTag(comment) {
    return `<pre class="credit-risk-comment-pre-tag">${comment || '-'}</pre>`
  }

  openDialog() {
    // this.router.navigate(['history']);
    this.modalRef = this.mdbModalService.show(RiskOpinionHistoryComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-85-p modal-dialog-scrollable  modal-lg',
      containerClass: '',
      animated: true,
      // data: {
      //   riskComment: data.creditRiskComment,
      //   riskReply: data.fpCreditRiskReplyDTO ? data.fpCreditRiskReplyDTO.replyComment : '',
      // }
    });
};


  LoadData (data) {

    // this.data1 = this.urlEncodeService.decode(localStorage.getItem(SETTINGS.STORAGE.FACILITY_PAPER_BY_ID));
    // this.data2 = this.urlEncodeService.decode(localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE));
    
    

    if (data) {

      if (localStorage.getItem(SETTINGS.STORAGE.FACILITY_PAPER_BY_ID)) {
        this.data1 = localStorage.getItem(SETTINGS.STORAGE.FACILITY_PAPER_BY_ID).replace(/[^\w\s]/gi, "");
      }
      if (localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE)) {
        this.data2 = localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE).replace(/[^\w\s]/gi, "");
      }

      this.previousLockedComments = [];
      this.activeRiskComment = null;
      this.creditRiskReply = null;
      
      let responseList = data.fpCreditRiskCommentList ? data.fpCreditRiskCommentList : [];

      let getLoggedInUserDivCode: String = this.applicationService.getLoggedInUserDivCode();
      this.facilityPaperByIDT = this.facilityPaperAddEditService.getFacilityPaperByIDT();

        if(getLoggedInUserDivCode == this.data2){
          this.isLoad = true;
          
        } else if ((this.facilityPaperByIDT !== getLoggedInUserDivCode) && (this.facilityPaperByIDT == this.data2)) {
        
          this.isLoad = false;
        } else if(this.facilityPaper.currentAssignUserDivCode !== this.data2){
          this.isLoad = true;
        }

        else{
          this.isLoad = false;
        }


      responseList.forEach((data: any) => {
            if(this.isLoad == true){
              this.isLoad = true;

                if (data.isValidComment === Constants.yesNoConst.Y) {
                  this.activeRiskComment = data;
                  this.creditRiskReply = data.fpCreditRiskReplyDTO;
                  this.createdUserName = data;
                  this.createdRiskReplyUserName = data.fpCreditRiskReplyDTO;
                  this.createdDateStr = data;
                  this.createdDate = data.fpCreditRiskReplyDTO;
                  this.modifiedUserName = data;
                  this.modifiedDateStr = data;
                  this.modifiedRiskReplyUserName = data.fpCreditRiskReplyDTO;
                  this.modifiedDate = data.fpCreditRiskReplyDTO;
                  this.divCode = data.fpCreditRiskReplyDTO;
                }
             }


        if (data.isLocked === Constants.yesNoConst.Y && data.isValidComment === Constants.yesNoConst.N && data.version > 1) {
          this.previousLockedComments.push(data);
        }
      })
    }


  }

  
  isCreditRiskUser () {

    if (localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE)) {
      if (this.applicationService.getLoggedInUserDivCode() === localStorage.getItem(SETTINGS.STORAGE.RISK_DIV_CODE).replace(/[^\w\s]/gi, "")) {
        return true;
      }
    } else {
      return false;
    }
    return false;
  }

}
