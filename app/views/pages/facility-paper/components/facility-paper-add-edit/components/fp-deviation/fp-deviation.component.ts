import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { Deviation } from "./utils";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Subscription } from "rxjs";

@Component({
  selector: "app-fp-deviation",
  templateUrl: "./fp-deviation.component.html",
  styleUrls: ["./fp-deviation.component.scss"],
})
export class FpDeviationComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper: any = {};
  @Input("isDeviationTableEditable") isDeviationTableEditable: boolean = true;
  initialDeviations: Deviation[] = [];
  deviations: Deviation[] = [];
  isTableShow: boolean = true;
  modalRefForwardConfirmation: MDBModalRef;
  isSaveButtonDissabled: boolean = true;
  isShowRefreshButton: boolean = false;

  isLeaseFacilityInclude: boolean = false;

  onFPFacilityChangeSub: Subscription = new Subscription();
  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private mdbModalService: MDBModalService,
    private alertService: AlertService,
  ) {}

  ngOnInit() {
    this.onFPFacilityChangeSub =
      this.facilityPaperAddEditService.onFPFacilityChange.subscribe(
        (data: any) => {
          if (data !== null && data.facilityDTOList !== null) {
            this.isLeaseFacilityInclude = data.facilityDTOList.some(
              (d: any) => d.facilityType === "Lease",
            );
          }
        },
      );

      if(this.isLeaseFacilityInclude){
        this.getDeviationsByFpId();
      }
  }

  ngOnDestroy(): void {
    this.onFPFacilityChangeSub.unsubscribe();
  }

  getDeviationsByFpId() {
    this.facilityPaperAddEditService
      .getCompDeviations(this.facilityPaper.facilityPaperID)
      .then((res: any[]) => {
        if (res !== null && res.length > 0) {
          this.deviations = res.map((d) => ({
            ...d,
          }));
          this.isTableShow = true;

          this.isShowRefreshButton = this.deviations.some(
            (item) => (item.divComment || "").trim().length > 0,
          );
        } else {
          this.isTableShow = false;
        }
      });
  }

  onIsCheckedCCPUChange(id: number, event: any) {
    this.deviations = this.deviations.map((d: Deviation) =>
      d.deviationId === id ? { ...d, checked: !d.checked, divComment: "" } : d,
    );

    if (this.isSameDeviations()) {
      this.isSaveButtonDissabled = true;
    } else {
      if (
        !this.deviations.some(
          (item) => (item.divComment || "").trim().length > 0,
        ) &&
        this.initialDeviations.length == 0
      ) {
        this.isSaveButtonDissabled = true;
      } else {
        this.isSaveButtonDissabled = false;
      }
    }
  }

  saveDeviations() {
    this.deviations = this.deviations.map((d) => ({
      ...d,
      divComment: d.checked ? d.divComment : "",
    }));

    this.facilityPaperAddEditService
      .saveDeviations(this.deviations)
      .then((res: any[]) => {
        if (res !== null && res.length > 0) {
          this.deviations = res.map((d) => ({
            ...d,
          }));
          this.isShowRefreshButton = true;
        }
      })
      .catch((err) => {
        this.alertService.showToaster(
          "Saving Faild",
          SETTINGS.TOASTER_MESSAGES.error,
        );
      });
  }

  isSameDeviations() {
    if (this.areDeviationArraysEqual(this.initialDeviations, this.deviations)) {
      return true;
    } else {
      return false;
    }
  }

  isDeviationEqual(a: Deviation, b: Deviation): boolean {
    return (
      a.deviationId == b.deviationId &&
      a.checked == b.checked &&
      a.divComment == b.divComment
    );
  }

  areDeviationArraysEqual(a: Deviation[], b: Deviation[]): boolean {
    if (a === b) return true;
    if (!a || !b) return false;
    if (a.length !== b.length) return false;

    const bMap = new Map<number, Deviation>();
    for (const item of b) {
      bMap.set(item.deviationId, item);
    }

    for (const itemA of a) {
      const itemB = bMap.get(itemA.deviationId);
      if (!itemB) return false;
      if (!this.isDeviationEqual(itemA, itemB)) return false;
    }

    return true;
  }

  onCommentChange() {
    if (!this.isSameDeviations()) {
      this.isSaveButtonDissabled = false;
    }
  }

  refresh() {
    let heading = "REFRESH DEVIATIONS CONFIRMATION";
    this.modalRefForwardConfirmation = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center",
        containerClass: "",
        animated: true,
        data: {
          heading: heading,
          approveRejectType: status,
          message: `This Will delete saved deviations with comments. Do you want to continue ?`,
        },
      },
    );

    this.modalRefForwardConfirmation.content.action.subscribe(
      (approveStatus) => {
        if (approveStatus) {
          this.refreshDeviationsByFpId();
          this.isShowRefreshButton = false;
          this.isSaveButtonDissabled = true;
        }
      },
    );
  }

  refreshDeviationsByFpId() {
    this.facilityPaperAddEditService
      .refreshCompDeviations(this.facilityPaper.facilityPaperID)
      .then((res: any[]) => {
        if (res !== null && res.length > 0) {
          this.deviations = res.map((d) => ({
            ...d,
          }));

          this.isTableShow = true;
        } else {
          this.isTableShow = false;
        }
      });
  }
}
