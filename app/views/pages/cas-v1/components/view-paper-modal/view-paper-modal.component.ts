import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { FacilityPaperTabsComponent } from "../facility-paper-tabs/facility-paper-tabs.component";

@Component({
  selector: "app-view-paper-modal",
  templateUrl: "./view-paper-modal.component.html",
  styleUrls: ["./view-paper-modal.component.scss"],
})
export class ViewPaperModalComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  facilityPaper: any = null;
  customer: any = {
    accNo: "",
    fullName: "",
    refNo: "",
    facilityDate: "",
    statusText: "",
  };

  @ViewChild(FacilityPaperTabsComponent, { static: true })
  child!: FacilityPaperTabsComponent;

  constructor(private mdbModalRef: MDBModalRef) {}

  ngOnInit() {}

  onClose() {
    this.action.next(null);
    this.child.clearTabData();
    this.mdbModalRef.hide();
  }
}
