import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from "@angular/core";
import { Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { AddEditFacilityComponent } from "./add-edit-facility/add-edit-facility.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { PrivilegeService } from "../../../../../../../../core/service/authentication/privilege.service";
import { UpdateFacilityOrderComponent } from "./update-facility-order/update-facility-order.component";
import { cloneDeep, uniqBy } from "lodash";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../../core/setting/constants";
import { AddEditThreeColumnFacilityComponent } from "./three-column/add-edit-three-column-facility/add-edit-three-column-facility.component";
import { UpdateOutstandingDateComponent } from "./update-outstanding-date/update-outstanding-date.component";

@Component({
  selector: "app-fp-facilities",
  templateUrl: "./fp-facilities.component.html",
  styleUrls: ["./fp-facilities.component.scss"],
})
export class FpFacilitiesComponent implements OnInit, OnChanges, OnDestroy {
  @Input("facilityPaper") facilityPaper: any = {};
  @Input("itemHasRemoved") itemHasRemoved: boolean;
  @Input("itemHasAdded") itemHasAdded: boolean;
  onFPFacilitiesChangeSub = new Subscription();
  onCreditCalculatedFacilitiesESBResponseStatusChangeSub = new Subscription();
  modalRef: MDBModalRef;

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  selectedTabIndex: number = 0;

  isPreviewMode: boolean = true;

  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  creditScheduleESBResponseStatusList: any = [];
  creditFacilityList: any[] = [];
  updatedFacilityPaper: any = {};
  commonFacilitySecurityList: any[] = [];

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private mdbModalService: MDBModalService,
    private privilegeService: PrivilegeService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    if (
      this.facilityPaper &&
      (this.facilityPaper.currentFacilityPaperStatus ==
        Constants.facilityPaperStatusConst.IN_PROGRESS ||
        this.facilityPaper.currentFacilityPaperStatus ==
          Constants.facilityPaperStatusConst.CANCEL)
    ) {
      this.facilityPaperAddEditService.getFusTracesByFacilityPaper(
        this.facilityPaper,
        Constants.fusTraceFlag.FAC
      );
    }

    this.onFPFacilitiesChangeSub =
      this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
        (data: any) => {
          if (data) {
            this.updatedFacilityPaper = Object.assign(
              {},
              this.facilityPaper,
              data
            );
          }
          this.creditFacilityList = [];
          this.creditFacilityList =
            cloneDeep(this.updatedFacilityPaper.facilityDTOList) || [];
          this.creditFacilityList = this.creditFacilityList.sort((a, b) => {
            return a.displayOrder > b.displayOrder
              ? 1
              : b.displayOrder > a.displayOrder
              ? -1
              : 0;
          });

          // The Following implementation is to get the common securities of each facility
          let commonSecurities: any[] = [];
          this.creditFacilityList.forEach((facility) => {
            facility.facilitySecurityDTOList.forEach((security) => {
              if (security.isCommonSecurity == Constants.yesNoConst.Y) {
                commonSecurities.push(security);
              }
            });
          });
          this.commonFacilitySecurityList = uniqBy(
            commonSecurities,
            "facilitySecurityID"
          );
          // Above commonFacilitySecurityList has all common securities of all facilities
        }
      );

    if (
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT
      )
    ) {
      this.isPreviewMode = false;
    }

    this.onCreditCalculatedFacilitiesESBResponseStatusChangeSub =
      this.facilityPaperAddEditService.onCreditCalculatedFacilitiesESBResponseStatusChange.subscribe(
        (data: any) => {
          this.creditScheduleESBResponseStatusList = data;
        }
      );
    this.isEqualLoginAndAssignUser();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["itemHasRemoved"]) {
      if (this.itemHasRemoved) {
        this.setTabIndex(0);
      }
    }
    if (changes["itemHasAdded"]) {
      if (this.itemHasAdded) {
        this.setTabIndex(this.selectedTabIndex);
      }
    }
  }

  openModalAddEdit($event?, facility?, selectedTabIndex?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    facility = facility || {};
    selectedTabIndex = selectedTabIndex ? selectedTabIndex : 0;
    const initialState = {
      list: [{ tag: "Count", value: facility }],
    };

    if (this.facilityPaper.isCommittee == "Y") {
      this.modalRef = this.mdbModalService.show(
        AddEditThreeColumnFacilityComponent,
        {
          initialState,
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: "modal-dialog-scrollable",
          containerClass: "",
          animated: true,
          data: {
            heading: "Add/ Edit Facility",
            content: {
              facility: facility,
              facilityPaper: this.facilityPaper,
              creditFacilityList: this.creditFacilityList,
              selectedTabIndex: selectedTabIndex
            },
          },
        }
      );
    } else {
      this.modalRef = this.mdbModalService.show(AddEditFacilityComponent, {
        initialState,
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-dialog-scrollable",
        containerClass: "",
        animated: true,
        data: {
          heading: "Add/ Edit Facility",
          content: {
            facility: facility,
            facilityPaper: this.facilityPaper,
            creditFacilityList: this.creditFacilityList,
          },
        },
      });
    }

    this.modalRef.content.action.subscribe((result: any) => {
      this.selectedTabIndex = selectedTabIndex;
    });
  }

  setTabIndex($event) {
    this.selectedTabIndex = $event;
  }

  toggleViewMode($event) {
    this.selectedTabIndex = 0;
    this.isPreviewMode = !this.isPreviewMode;
  }

  updateFacilityOrder($event?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(UpdateFacilityOrderComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-80-p modal-dialog-scrollable",
      containerClass: "",
      animated: true,
      data: {
        heading: "Update Facility Order",
        content: {
          facilityPaper: this.facilityPaper,
          creditFacilityList: this.creditFacilityList,
        },
      },
    });
    this.modalRef.content.action.subscribe((result: any) => {
      // console.log(result);
    });
  }

  updateOutstandingDate($event?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(UpdateOutstandingDateComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-40-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Update Outstanding Date",
        content: {
          facilityPaper: this.facilityPaper,
        },
      },
    });
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.facilityPaperAddEditService.updateFacilityPaperOutstandingDate(
          data
        );
      }
    });
  }

  ngOnDestroy(): void {
    this.onFPFacilitiesChangeSub.unsubscribe();
    this.onCreditCalculatedFacilitiesESBResponseStatusChangeSub.unsubscribe();
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      this.isPreviewMode = true;
      return false;
    }
  }

  isApproveStatus() {
    let isApproved = false;
    isApproved =
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED;

    if (isApproved) {
      this.isPreviewMode = true;
    }
    return isApproved;
  }

  isRejected() {
    let isRejected = false;
    isRejected =
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED;

    if (isRejected) {
      this.isPreviewMode = true;
    }
    return isRejected;
  }

  toCommonSecurityContent($event) {
    document.getElementById($event).scrollIntoView({
      behavior: "smooth",
      block: "center",
      inline: "center",
    });
  }

  moveUp() {
    document.getElementById("fp-f-prev-mod").scrollIntoView({
      behavior: "smooth",
      block: "center",
      inline: "center",
    });
  }
}
