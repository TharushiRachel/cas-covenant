import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfAddEditDocumentsComponent} from "../../support-components/apf-add-edit-documents/apf-add-edit-documents.component";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-documents-screen',
  templateUrl: './apf-documents-screen.component.html',
  styleUrls: ['./apf-documents-screen.component.scss']
})
export class ApfDocumentsScreenComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  applicationFrom: any;
  onApplicationFormChange = new Subscription();
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;

  constructor(private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,
              private applicationService: ApplicationService,) {
  }

  ngOnInit() {

    this.onApplicationFormChange = this.applicationFormAddEditService.onApplicationFormChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationFrom = data;
      }
    });

  }

  openModalDocumentDetails(documentDTO?) {

    this.modalRef = this.mdbModalService.show(ApfAddEditDocumentsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: 'right',
      animated: false,
      data: {
        heading: "",
        content: {documentDTO},
      }
    });
  }

  isAbleToEdit() {
    return this.applicationFrom.assignUserID == this.applicationService.getLoggedInUserUserID()
      && (
        this.applicationFrom.currentApplicationFormStatus == this.applicationFormStatusConst.DRAFT
        || this.applicationFrom.currentApplicationFormStatus == this.applicationFormStatusConst.RETURNED
        || this.applicationFrom.currentApplicationFormStatus == this.applicationFormStatusConst.IN_PROGRESS
      );
  }

  ngOnDestroy(): void {
    this.onApplicationFormChange.unsubscribe();
  }

}
