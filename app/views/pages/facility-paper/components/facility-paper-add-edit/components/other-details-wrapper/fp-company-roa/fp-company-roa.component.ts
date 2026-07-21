import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../../../../core/setting/constants";
import {FacilityPaperAddEditService} from "../../../../../services/facility-paper-add-edit.service";
import {FpAddEditCompanyRoaComponent} from "./fp-add-edit-company-roa/fp-add-edit-company-roa.component";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import * as _ from 'lodash';

@Component({
  selector: 'app-fp-company-roa',
  templateUrl: './fp-company-roa.component.html',
  styleUrls: ['./fp-company-roa.component.scss']
})
export class FpCompanyRoaComponent implements OnInit, OnDestroy {

  @Input('facilityPaper') facilityPaper: any = {};

  modalRef: MDBModalRef;
  companyROA: any = {};
  updatedFacilityPaper: any = {};
  onDirectorDetailUpdate: Subscription = new Subscription();
  statusConst = Constants.statusConst;
  status = Constants.status;
  civilStatus = Constants.civilStatus;
  civilStatusConst = Constants.civilStatusConst;

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  isCooperate: boolean = false;

  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
  ) {
  }

  ngOnInit() {
    this.updatedFacilityPaper = this.facilityPaper;
    this.isCooperate = this.facilityPaper.isCooperate == 'Y';
    this.onDirectorDetailUpdate = this.facilityPaperAddEditService.onFPCompanyROAChange
      .subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          this.updatedFacilityPaper = data;
        }
      })
  }

  ngOnDestroy(): void {
    this.onDirectorDetailUpdate.unsubscribe();
  }


  openModalAddEditCompanyROA(facilityPaper, companyROA?) {
    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityPaper}
      ]
    };

    this.modalRef = this.mdbModalService.show(FpAddEditCompanyRoaComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-70-p audit-modal-margin-center',
      containerClass: '',
      animated: true,
      data: {
        heading: "comming dto",
        content: {
          facilityPaper: facilityPaper,
          companyROA: companyROA
        },
      }
    });
    this.modalRef.content.action.subscribe((result: any) => {
      this.companyROA = result;
    });

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

}
