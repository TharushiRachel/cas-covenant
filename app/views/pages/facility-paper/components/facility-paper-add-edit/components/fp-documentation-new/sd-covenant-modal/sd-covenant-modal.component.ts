import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";

@Component({
  selector: "app-sd-covenant-modal",
  templateUrl: "./sd-covenant-modal.component.html",
  styleUrls: ["./sd-covenant-modal.component.scss"],
})
export class SdCovenantModalComponent implements OnInit {
  modalRef: MDBModalRef;
  action: Subject<any> = new Subject<any>();
  content: any = {};

  fpRefNumber: any = "";
  facilityId: number = 0;
  covenantsList: any[] = [];
  selectedCovenantsList: any[] = [];

  constructor(
    public mdbModalRef: MDBModalRef,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly alertService: AlertService
  ) {}

  ngOnInit() {
    this.getCovenantList();
  }

  onNoClick() {
    this.mdbModalRef.hide();
  }

  getCovenantList() {
    if (this.fpRefNumber !== null) {
      this.facilityPaperAddEditService
        .getAllCovenants(this.fpRefNumber, this.facilityId)
        .then((data: any[]) => {
          this.covenantsList = data
            .map((d: any, i: number) => ({
              ...d,
              id: d.covenant_Code,
              name: d.covenant_Description,
              isSelected: this.isCovenantExist(d),
              displayOrder: this.isCovenantExist(d)
                ? this.getPrevCovenantIndex(d.covenant_Description)
                : this.selectedCovenantsList.length + 1,
              applicableType: d.applicableType !== null ? Constants.covenantApplicableType[d.applicableType] : "",
            }))
            .sort((a: any, b: any) => a.displayOrder - b.displayOrder);
        });
    }
  }

  isCovenantExist(cov: any) {
    return this.selectedCovenantsList.some(
      (sc: any) => sc === cov.covenant_Description
    );
  }

  getPrevCovenantIndex(value: string) {
    let index = this.selectedCovenantsList.indexOf(value);
    return index === 0 ? index + 1 : index;
  }

  listOrderChanged(list: any[]) {
    this.covenantsList = list.map((l: any, i: number) => ({
      ...l,
      displayOrder: i + 1,
    }));
  }

  handleCovenantSelect(e: any, code: any) {
    this.covenantsList = this.covenantsList.map((c: any) => ({
      ...c,
      isSelected: c.id === code ? !c.isSelected : c.isSelected,
    }));
  }

  handleSave() {
    let selectedItems: any[] = this.covenantsList
      .filter((d: any) => d.isSelected)
      .sort((a: any, b: any) => a.displayOrder - b.displayOrder);

    if (selectedItems.length > 0) {
      this.action.next(selectedItems);
      this.mdbModalRef.hide();
    } else {
      this.alertService.showToaster(
        "There are no selected covenants.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }
}
