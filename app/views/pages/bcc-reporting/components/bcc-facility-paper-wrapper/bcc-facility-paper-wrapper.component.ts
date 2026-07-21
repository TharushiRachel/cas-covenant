import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {BccReportingService} from "../../services/bcc-reporting.service";
import {GenerateBccDialogComponent} from "../../../../../shared/components/generate-bcc-dialog/generate-bcc-dialog.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {PrivilegeService} from "../../../../../core/service/authentication/privilege.service";
import { isEmpty } from 'lodash';
import * as _ from 'lodash';

@Component({
  selector: 'app-bcc-facility-paper-wrapper',
  templateUrl: './bcc-facility-paper-wrapper.component.html',
  styleUrls: ['./bcc-facility-paper-wrapper.component.scss']
})
export class BccFacilityPaperWrapperComponent implements OnInit, OnDestroy {
  uniquePageName = 'BccFacilityPaperWrapperComponent-#feTbtsdw';
  modalRef: MDBModalRef;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING)
  selectedFacilityPaperID;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  facilityStatusConst = Constants.facilityPaperStatusConst;
  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
  selectedTabIndex: any = 0;
  facilityPaper: any = {};
  onFacilityPaperChangeSubs = new Subscription();
  onFPFacilitiesChangeSub = new Subscription();
  onBCCPaperRemoveChangeSub = new Subscription();
  isAbleToEditBCCPaper = false;
  // bccPaperFormIndex = 0;
  printPreviewIndex = 0;

  onCustomerListChangeSub = new Subscription();
  fpCustomerList: any = [];
  primaryCustomer;
  joiningPartners: any = [];
  primaryCustomerID;

  constructor(private bccReportingService: BccReportingService,
              private mdbModalService: MDBModalService,
              private applicationService: ApplicationService,
              private privilegeService: PrivilegeService,
  ) {

  }

  ngOnInit() {

    this.isAbleToEditBCCPaper = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_GENERATE_BCC_PAPER) || this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_BCC_PAPER_EDIT);

    this.onBCCPaperRemoveChangeSub =  this.bccReportingService.onBCCPaperRemoveChange.subscribe(response => {
      if (!_.isEmpty(response)) {
        this.facilityPaper = response;
      }
    })

    this.onFacilityPaperChangeSubs = this.bccReportingService.onFacilityPaperChange.subscribe(
      response => {
        this.facilityPaper = response;
      }
    );
  }

  ngOnDestroy(): void {
    this.onFacilityPaperChangeSubs.unsubscribe();
    this.onFPFacilitiesChangeSub.unsubscribe();
    this.onBCCPaperRemoveChangeSub.unsubscribe();
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
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


  isBccPaperCreated(facilityPaper) {
    return facilityPaper.isBccCreated == Constants.yesNoConst.Y;
  }

  onCreateBCCPaper() {
    this.modalRef = this.mdbModalService.show(GenerateBccDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-45-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Create Board Credit Committee (BCC/EAC) Report",
        message: "",
      }
    });

    this.modalRef.content.action.subscribe((response: any) => {
      if (response) {
        let paperType = response.paperType;
        let obj = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          paperType: paperType,
          branchCode: this.facilityPaper.branchCode,
          createdUserDisplayName: this.applicationService.getLoggedInUser().displayName,
          createdUserName: this.applicationService.getLoggedInUser().userName,
          upmID: this.applicationService.getLoggedInUser().userID,
          status: Constants.statusConst.ACT,
          assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
        };
        this.bccReportingService.createBCCPaper(obj);
      }
    });

  }


}
