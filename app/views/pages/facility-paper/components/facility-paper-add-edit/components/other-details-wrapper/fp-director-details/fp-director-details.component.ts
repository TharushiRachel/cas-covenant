import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Constants } from "../../../../../../../../core/setting/constants";
import { Subscription } from "rxjs/Rx";
import { FpAddDirectorComponent } from "./fp-add-director/fp-add-director.component";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";
import { DateService } from "../../../../../../../../core/service/application/date.service";
import { PrivilegeService } from "../../../../../../../../core/service/authentication/privilege.service";
import { CurrencyPipe } from "@angular/common";
import * as _ from "lodash";

@Component({
  selector: "app-fp-director-details",
  templateUrl: "./fp-director-details.component.html",
  styleUrls: ["./fp-director-details.component.scss"],
})
export class FpDirectorDetailsComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper: any = {};

  modalRef: MDBModalRef;
  directorDetail: any = {};
  //updatedFacilityPaper: any = {};
  shareHolderDetail: any = {};
  onDirectorDetailUpdate: Subscription = new Subscription();
  onShareHolderDetailUpdate: Subscription = new Subscription();
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
  ) {}

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
        "Action",
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

    this.onDirectorDetailUpdate =
      this.facilityPaperAddEditService.onFPCompanyDirectorsChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            //this.updatedFacilityPaper = data;
            this.facilityPaper.fpDirectorDetailDTOList = data.fpDirectorDetailDTOList; 
          }
        }
      );

    this.onShareHolderDetailUpdate =
      this.facilityPaperAddEditService.onShareHolderDetailsChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            //this.updatedFacilityPaper = data;
            this.facilityPaper.fpShareHolderDetailDTOList = data.fpShareHolderDetailDTOList;
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.onDirectorDetailUpdate.unsubscribe();
    this.onShareHolderDetailUpdate.unsubscribe();
  }

  openModalAddDirectorDetails(facilityPaper, directorData?, shareholderData?) {
    const initialState = {
      list: [{ tag: "Count", value: facilityPaper }],
    };

    this.modalRef = this.mdbModalService.show(FpAddDirectorComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: false,
      data: {
        heading: "Add/Edit Director Details",
        content: {
          facilityPaper: facilityPaper,
          directorData: directorData,
          shareholderData: shareholderData,
        },
      },
    });
    this.modalRef.content.action.subscribe((result: any) => {
      this.directorDetail = result;
      // console.log("customer from modal" + this.directorDetail.fullName)
    });
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
