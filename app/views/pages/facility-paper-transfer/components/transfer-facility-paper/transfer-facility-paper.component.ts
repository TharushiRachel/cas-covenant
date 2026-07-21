import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FacilityPaperTransferService} from "../../services/facility-paper-transfer.service";
import {Constants} from "../../../../../core/setting/constants";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {CommonForwardComponent} from "../../../../../shared/components/common-forward/common-forward.component";
import {AppUtils} from "../../../../../shared/app.utils";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {ConfirmationDialogComponent} from "../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-transfer-facility-paper',
  templateUrl: './transfer-facility-paper.component.html',
  styleUrls: ['./transfer-facility-paper.component.scss']
})
export class TransferFacilityPaperComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  onFacilityPaperChangeSubs = new Subscription();
  facilityPaper: any = {};
  facilityStatusConst = Constants.facilityPaperStatusConst;
  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityRoutingStatus = Constants.facilityRoutingStatus;
  facilityRoutingStatusConst = Constants.facilityRoutingStatusConst;

  upcTemplateList = [];
  fpUpcSectionData: any = [];
  onTreeUpdateChangeSub = new Subscription();
  onUpcTemplateLoadChangeSub = new Subscription();

  constructor(private facilityPaperTransferService: FacilityPaperTransferService,
              private applicationService: ApplicationService,
              private mdbModalService: MDBModalService,
              private router: Router) {
  }

  ngOnInit() {
    this.onFacilityPaperChangeSubs = this.facilityPaperTransferService.onFacilityPaperChange
      .subscribe((fp: any) => {
        this.facilityPaper = fp;
      });

    this.onUpcTemplateLoadChangeSub = this.facilityPaperTransferService.onUpcTemplateListLoad
      .subscribe((data: any) => {
        this.upcTemplateList = data;
      });

    this.onTreeUpdateChangeSub = this.facilityPaperTransferService.onFpUpcSectionChange
      .subscribe((data: any) => {
        this.fpUpcSectionData = data.fpUpcSectionDataDTOList;
      });

  }

  ngOnDestroy(): void {
    this.onUpcTemplateLoadChangeSub.unsubscribe();
    this.onTreeUpdateChangeSub.unsubscribe();
  }

  getColour(facilityStatus) {
    switch (facilityStatus) {
      case this.facilityStatusConst.DRAFT:
        return {color: '#ffbb33a6'};
      case this.facilityStatusConst.IN_PROGRESS:
        return {color: '#0099cc94'};
      case this.facilityStatusConst.APPROVED:
        return {color: '#007e338a'};
      case this.facilityStatusConst.CANCEL:
        return {color: '#cc000073'};
      case this.facilityStatusConst.REJECTED:
        return {color: '#cc0000a6'};
    }
  }

  showTransferButton(facilityPaper) {
    return facilityPaper.currentFacilityPaperStatus == this.facilityStatusConst.IN_PROGRESS ||
      facilityPaper.currentFacilityPaperStatus == this.facilityStatusConst.CANCEL ||
      facilityPaper.currentFacilityPaperStatus == this.facilityStatusConst.DRAFT
  }

  clickStatusChange(facilityPaper, paperStatus) {

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-40-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Paper Transfer : " + facilityPaper.fpRefNumber,
        message: `Do you want to transfer this facility paper ${facilityPaper.assignUserDisplayName ? 'from ' + facilityPaper.assignUserDisplayName : ''} ? `
      }
    });
    this.modalRef.content.action.subscribe((response: any) => {
      if (response) {
        this.showCommonForwardComponent(facilityPaper, paperStatus);
      }
    });
  }

  async showCommonForwardComponent(facilityPaper, facilityPaperStatus) {

    let routingStatus: any;

    routingStatus = this.facilityRoutingStatusConst.NEXT;

    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: 'right',
      animated: true,
      data: {
        showUsersOnlyOption: false,
        showDivisionOnlyOption: false,
        heading: "Paper Transfer : " + facilityPaper.fpRefNumber,
        actionMessage: 'Transfer',
        isTransfer: true,
        commentCacheKey: this.facilityPaper.fpRefNumber + this.facilityStatusConst[facilityPaperStatus] + this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: [],
          branchCode: facilityPaper.branchCode,
          createdUser: facilityPaper.createdBy,
          currentAssignUser: facilityPaper.currentAssignUser,
          workflowTemplateID: facilityPaper.workflowTemplateID,
          relatedDivCodes: [facilityPaper.branchCode, facilityPaper.createdUserBranchCode]
        },
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {

        let facilityPaperStatusChangeRQ = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          assignDepartmentCode: data.assignDepartmentCode,
          fpAssignDepartmentDTOList: data.assignDepartmentDTOList,
          actionMessage: data.actionMessage,
          updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          fpCommentDTO: {...data.remarkData, currentFacilityPaperStatus: facilityPaperStatus},
          facilityPaperStatus: facilityPaperStatus,
          forwardType: data.forwardType,
          routingStatus: routingStatus,
        };

        if (data.assignedUser) {
          facilityPaperStatusChangeRQ = Object.assign({}, facilityPaperStatusChangeRQ, {
            assignUserID: data.assignedUser.userID,
            assignADUserID: data.assignedUser.adUserID,
            assignUser: data.assignedUser.adUserID,
            assignUserDisplayName: data.assignedUser.assignUserDisplayName,
            assignUserUpmID: data.assignedUser.userID,
            assignUserDivCode: data.assignedUser.divCode,
            assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
            authorityLevel: data.assignedUser.adUserID
          });
        } else {
          facilityPaperStatusChangeRQ = Object.assign({}, facilityPaperStatusChangeRQ, {
            assignUserID: null,
            assignADUserID: null,
            assignUser: null,
            assignUserDisplayName: null,
            assignUserUpmID: null,
            assignUserDivCode: null,
            assignUserUpmGroupCode: null,
          })
        }
        this.facilityPaperTransferService.updateFacilityPaper(AppUtils.trim(facilityPaperStatusChangeRQ));
        this.router.navigate(['/facility-paper-transfer']);
      }
    });
  }

}
