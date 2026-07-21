import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {CommentDialogComponent} from "../../../../../../../../shared/components/comment-dialog/comment-dialog.component";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import * as _ from "lodash";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-apf-crib-screen',
  templateUrl: './apf-crib-screen.component.html',
  styleUrls: ['./apf-crib-screen.component.scss']
})
export class ApfCribScreenComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationFormCurrentStatus = Constants.applicationFormCurrentStatus;
  onApplicationFormChangeSub = new Subscription();
  applicationForm: any = {};

  constructor(private mdbModalService: MDBModalService,
              public applicationService: ApplicationService,
              private applicationFormAddEditService: ApplicationFormAddEditService) {
  }

  ngOnInit() {
    this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
      }
    });
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

  createApplicationForm(currentApplicationFormStatus) {
    this.modalRef = this.mdbModalService.show(CommentDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: '',
      animated: false,
      data: {
        heading: "",
        actionName: this.applicationFormCurrentStatus[currentApplicationFormStatus],
      }
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        let createdUserDisplayName = this.applicationService.getLoggedInUserDisplayName();
        let createdUserID = this.applicationService.getLoggedInUserUserID();
        let branchCode = this.applicationService.getLoggedInUserDivCode();

        let afCommentDTO = {
          ...result,
          currentApplicationFormStatus,
          createdUserDisplayName,
          createdUserID
        };

        let afCommentDTOList = [afCommentDTO];

        let applicationForm = {
          branchCode,
          createdUserDisplayName,
          currentApplicationFormStatus,
          createdUserID,
          afCommentDTOList,
        };

        this.applicationFormAddEditService.draftApplicationForm(applicationForm);
      }
    });
  }

}
