import { Component, Input, OnInit } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { Subscription } from 'rxjs';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { FacilityPaperAddEditService } from '../../pages/facility-paper/services/facility-paper-add-edit.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { DateService } from 'src/app/core/service/application/date.service';
import { PrivilegeService } from 'src/app/core/service/authentication/privilege.service';
import { CurrencyPipe } from '@angular/common';
import * as _ from "lodash";

@Component({
  selector: 'app-preview-director-details',
  templateUrl: './preview-director-details.component.html',
  styleUrls: ['./preview-director-details.component.scss']
})
export class PreviewDirectorDetailsComponent implements OnInit {

  @Input("facilityPaper") facilityPaper: any = {};

  modalRef: MDBModalRef;
  directorDetail: any = {};
  //updatedFacilityPaper: any = {};
  shareHolderDetail: any = {};
  // onDirectorDetailUpdate: Subscription = new Subscription();
  // onShareHolderDetailUpdate: Subscription = new Subscription();
  tableColumns = [];
  statusConst = Constants.statusConst;
  status = Constants.status;
  civilStatus = Constants.civilStatus;
  civilStatusConst = Constants.civilStatusConst;

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  constructor(
    private mdbModalService: MDBModalService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private dateService: DateService,
    private privilegeService: PrivilegeService,
    private currencyPipe: CurrencyPipe
  ) { }

  ngOnInit() {

    if (
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT
      )
    ) {
      this.tableColumns = [
        "Name",
        "Director/Shareholder",
        "Age",
        "NIC",
        "Share Holding (%)",
      ];
    } else {
      this.tableColumns = [
        "Name",
        "Director/Shareholder",
        "Age",
        "NIC",
        "Share Holding (%)",
      ];
    }

    // this.onDirectorDetailUpdate =
    //   this.facilityPaperAddEditService.onFPCompanyDirectorsChange.subscribe(
    //     (data: any) => {
    //       if (!_.isEmpty(data)) {
    //         //this.updatedFacilityPaper = data;
    //         this.facilityPaper = data;

    //       }
    //     }
    //   );

    // this.onShareHolderDetailUpdate =
    //   this.facilityPaperAddEditService.onShareHolderDetailsChange.subscribe(
    //     (data: any) => {
    //       if (!_.isEmpty(data)) {
    //         //this.updatedFacilityPaper = data;
    //         this.facilityPaper = data;
    //       }
    //     }
    //   );
  }

  ngOnDestroy(): void {
    // this.onDirectorDetailUpdate.unsubscribe();
    // this.onShareHolderDetailUpdate.unsubscribe();
  }

  getCalculatedAge(dob) {
    return this.dateService.getDateDifference(dob, "years");
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
        this.applicationService.getLoggedInUserUserID() &&
      this.applicationService.getLoggedInUserUPMGroupCode() < 71
    ) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED
    );
  }

  isRejected() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED
    );
  }

  getFormattedValue(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "");
    }
  }

  calculateTotalShareHolding() {
    let shareholderPercentage =
      this.facilityPaper.fpShareHolderDetailDTOList.reduce(
        (total, shareholder) => total + shareholder.shareHolding,
        0
      );
    let directorPercentage =
      this.facilityPaper.fpDirectorDetailDTOList.reduce(
        (total, director) => total + director.shareHolding,
        0
      );

    return shareholderPercentage + directorPercentage;
  }

}
