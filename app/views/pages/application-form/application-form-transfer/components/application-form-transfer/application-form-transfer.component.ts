import {Component,HostListener, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {ApplicationFormTransferService} from "../../services/application-form-transfer.service";
import {Subscription} from "rxjs";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../core/setting/constants";
import {ConfirmationDialogComponent} from "../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {CommonForwardComponent} from "../../../../../../shared/components/common-forward/common-forward.component";
import {ApplicationService} from "../../../../../../core/service/application/application.service";
import {Router} from "@angular/router";
import {AppUtils} from "../../../../../../shared/app.utils";

@Component({
  selector: 'app-application-form-transfer',
  templateUrl: './application-form-transfer.component.html',
  styleUrls: ['./application-form-transfer.component.scss']
})
export class ApplicationFormTransferComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;
  modalRef: MDBModalRef;
  applicationForm: any = {};
  onApplicationFormChangeSub = new Subscription();
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationFormStatus = Constants.applicationFormCurrentStatus;
  applicationFormRoutingStatusConst = Constants.facilityRoutingStatusConst;
  scrWidth: any;

  @HostListener('window:resize', ['$event'])
  getScreenSize(event?) {
    this.scrWidth = window.innerWidth;
  }

  constructor(private applicationFormTransferService: ApplicationFormTransferService,
              private applicationService: ApplicationService,
              private mdbModalService: MDBModalService,
              private router: Router) {
  }

  ngOnInit() {
    if (this.selectedApplicationFormID) {
      this.applicationFormTransferService.getApplicationFormByID();
    }
    this.onApplicationFormChangeSub = this.applicationFormTransferService.onApplicationFormChange.subscribe((data: any) => {
      this.applicationForm = data;
    })

  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

  showTransferButton(applicationForm) {
    return applicationForm.currentApplicationFormStatus == this.applicationFormStatusConst.IN_PROGRESS ||
      applicationForm.currentApplicationFormStatus == this.applicationFormStatusConst.RETURNED
  }

  clickStatusChange(applicationForm, applicationFormStatus) {
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
          heading: "Application Form Transfer : " + applicationForm.afRefNumber,
          message: `Do you want to transfer this Application ${applicationForm.assignUserDisplayName ? 'from ' + applicationForm.assignUserDisplayName : ''} ? `
        }
      });
      this.modalRef.content.action.subscribe((response: any) => {
        if (response) {
          this.showCommonForwardComponent(applicationForm, applicationFormStatus);
        }
      });
    }

    async showCommonForwardComponent(applicationForm, applicationFormStatus) {

      let routingStatus: any;
      routingStatus = this.applicationFormRoutingStatusConst.NEXT;

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
          heading: "Application Form Transfer : " + applicationForm.afRefNumber,
          actionMessage: 'Transfer',
          isTransfer: true,
          commentCacheKey: this.applicationForm.afRefNumber + this.applicationFormStatusConst[applicationFormStatus] + this.applicationService.getLoggedInUserUserID(),
          content: {
            returnUserList: [],
            branchCode: applicationForm.branchCode,
            createdUser: applicationForm.createdBy,
            currentAssignUser: applicationForm.assignUser,
            workflowTemplateID: applicationForm.workflowTemplateID,
            relatedDivCodes: [applicationForm.branchCode]
          },
        }
      });

      this.modalRef.content.action.subscribe((data: any) => {

        if (data) {
          let applicationFormStatusChangeRQ = {
           applicationFormID: applicationForm.applicationFormID,
           afRefNumber: applicationForm.afRefNumber,
           assignDepartmentCode: data.assignDepartmentCode,
           afAssignDepartmentDTOList: data.assignDepartmentDTOList,
           actionMessage: data.actionMessage,
           afCommentDTO: data.remarkData,
           applicationFormStatus: applicationFormStatus,
           forwardType: data.forwardType,
           updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
           routingStatus: routingStatus,
          };

          if (data.assignedUser) {
            applicationFormStatusChangeRQ = Object.assign({}, applicationFormStatusChangeRQ, {
                      assignUserID: data.assignedUser.userID,
                      assignADUserID: data.assignedUser.adUserID,
                      assignUser: data.assignedUser.adUserID,
                      assignUserDisplayName: data.assignedUser.assignUserDisplayName,
                      assignUserUpmID: data.assignedUser.userID,
                      assignUserDivCode: data.assignedUser.divCode,
                      assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
            });
          } else {
            applicationFormStatusChangeRQ = Object.assign({}, applicationFormStatusChangeRQ, {
                        assignUserID: null,
                        assignADUserID: null,
                        assignUser: null,
                        assignUserDisplayName: null,
                        assignUserUpmID: null,
                        assignedUserDivCode: null,
                        assignUserUpmGroupCode: null,
            })
          }


          this.applicationFormTransferService.updateApplicationForm(AppUtils.trim(applicationFormStatusChangeRQ));
         // this.router.navigate(['/application-forms/dashboard']);

         this.router.navigate(['/application-form-transfer']);
        }
      });
    }

    getColour(applicationFormStatus) {
        switch (applicationFormStatus) {
          case this.applicationFormStatusConst.DRAFT:
            return {color: '#ffbb33a6'};
          case this.applicationFormStatusConst.PAPER_CREATED:
          case this.applicationFormStatusConst.IN_PROGRESS:
            return {color: '#0099cc94'};
          case this.applicationFormStatusConst.RETURNED:
            return {color: '#007e338a'};
          case this.applicationFormStatusConst.DECLINED:
            return {color: '#cc0000a6'};
        }
      }

}



