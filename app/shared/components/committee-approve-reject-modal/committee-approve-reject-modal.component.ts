import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-committee-approve-reject-modal",
  templateUrl: "./committee-approve-reject-modal.component.html",
  styleUrls: ["./committee-approve-reject-modal.component.scss"],
})
export class CommitteeApproveRejectModalComponent implements OnInit {
  action: Subject<any> = new Subject<any>();

  approveStatus: any = Constants.approveStatusText;
  approveStatusConst: any = Constants.approveStatusConst;
  status: any = "";
  committeeName: string = "";
  comment: any = "";

  constructor(
    public mdbModalRef: MDBModalRef,
    private alertService: AlertService
  ) {}

  ngOnInit() {}

  save() {
    if (this.status == Constants.approveStatusConst.REJECTED) {
      if (this.comment) {
        this.action.next({ status: 1, comment: this.comment });
      } else {
        this.alertService.showToaster(
          "Please provide the reject reason.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      }
    } else {
      this.action.next({ status: 1, comment: "" });
    }
  }

  close() {
    this.action.next({ status: 0, comment: "" });
    this.mdbModalRef.hide();
  }
}
