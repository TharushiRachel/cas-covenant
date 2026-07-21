import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { Constants } from "../../../core/setting/constants";
import { cloneDeep, uniqBy } from "lodash";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ReportFacilityChangeModalComponent } from "src/app/shared/components/report-facility-change-modal/report-facility-change-modal.component";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { FacilityPaperAddEditService } from "../../pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-preview-facilities",
  templateUrl: "./preview-facilities.component.html",
  styleUrls: ["./preview-facilities.component.scss"],
})
export class PreviewFacilitiesComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper: any;
  @Input("creditScheduleESBResponseStatusList")
  creditScheduleESBResponseStatusList: [];
  onFPFacilitiesChangeSub = new Subscription();
  creditFacilityList: any[] = [];
  commonFacilitySecurityList: any[] = [];

  modalRef: MDBModalRef;
  onBaseFacilityPaperChangeSub: Subscription = new Subscription();
  isCommitteePaper: boolean = false;
  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private applicationService: ApplicationService,
    private mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    if (
      this.facilityPaper &&
      this.isHigherWCUser() &&
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
            this.facilityPaper = Object.assign({}, this.facilityPaper, data);
          }
          this.creditFacilityList = [];
          this.creditFacilityList =
            cloneDeep(this.facilityPaper.facilityDTOList) || [];
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
        }
      );

    // Above commonFacilitySecurityList has all common securities of all facilities

    this.onBaseFacilityPaperChangeSub =
      this.facilityPaperAddEditService.onBaseFacilityPaperChange.subscribe(
        (data: any) => {
          this.isCommitteePaper = data.isCommittee === Constants.yesNoConst.Y;
          this.facilityPaper = {
            ...this.facilityPaper,
            isCommittee: data.isCommittee,
          };
        }
      );
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

  ngOnDestroy(): void {
    this.onFPFacilitiesChangeSub.unsubscribe();
    this.onBaseFacilityPaperChangeSub.unsubscribe();
  }

  isHide(): boolean {
    var wc: any = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode()
    );

    return (
      wc == Constants.applicationSecurityWorkClass.ENTERER ||
      wc == Constants.applicationSecurityWorkClass.SUPERVISOR ||
      wc == Constants.applicationSecurityWorkClass.MANAGER
    );
  }

  isHigherWCUser() {
    var wc: any = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode()
    );

    return wc >= 71;
  }

  getUnReadCommentsCount(facility: any): number {
    var preCmnts: any[] = facility.fusTraceList ? facility.fusTraceList : [];

    return preCmnts.filter(
      (cmnt: any) =>
        (cmnt.flag == Constants.fusTraceFlag.FAC ||
          cmnt.flag == Constants.fusTraceFlag.FACSE) &&
        cmnt.isView == 0 &&
        cmnt.createdBy != this.applicationService.getLoggedInUserUserName()
    ).length;
  }

  openReportModal(creditFacility: any) {
    this.modalRef = this.mdbModalService.show(
      ReportFacilityChangeModalComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-dialog-scrollable modal-width-95-m",
        containerClass: "",
        animated: true,
        data: {
          heading: "Report Facility Changes",
          facility: creditFacility,
          componentData: {
            facilityPaper: this.facilityPaper,
            creditFacilityList: this.creditFacilityList,
            creditFacility: creditFacility,
            creditScheduleESBResponseStatusList:
              this.creditScheduleESBResponseStatusList,
            commonFacilitySecurityList: this.commonFacilitySecurityList,
          },
          content: {},
        },
      }
    );
  }
}
