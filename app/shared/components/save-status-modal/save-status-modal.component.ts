import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-save-status-modal",
  templateUrl: "./save-status-modal.component.html",
  styleUrls: ["./save-status-modal.component.scss"],
})
export class SaveStatusModalComponent implements OnInit {
  action: Subject<any> = new Subject<any>();

  activestatus: any = Constants.status;
  activestatusConst: any = Constants.statusConst;
  status: any = "";
  userDisplayName: string = "";

  constructor(public mdbModalRef: MDBModalRef) {}

  ngOnInit() {}

  save() {
    this.action.next(1);
  }

  close() {
    this.action.next(0);
    this.mdbModalRef.hide();
  }
}
