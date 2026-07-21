import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-risk-confirmation-dialog",
  templateUrl: "./risk-confirmation-dialog.component.html",
  styleUrls: ["./risk-confirmation-dialog.component.scss"],
})
export class RiskConfirmationDialogComponent implements OnInit, OnDestroy {
  heading: string;
  message: string;
  isModified: boolean;
  node: any = null;

  action: Subject<any> = new Subject<any>();

  constructor(
    private readonly mdbModalRef: MDBModalRef,
    private readonly applicationService: ApplicationService
  ) {}

  ngOnInit() {}

  onClose(): void {
    this.action.next("C");
    this.mdbModalRef.hide();
  }

  onNoClick(): void {
    this.action.next("N");
    this.mdbModalRef.hide();
  }

  onYesClick(): void {
    this.action.next("Y");
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  isParent(node: any) {
    return node.type == Constants.riskRecordTypeConst.P;
  }

  isValidUser(node: any): boolean {
    var isValid: boolean = false;
    let loggedInUserName: string =
      this.applicationService.getLoggedInUserUserName();
    if (node.createdBy && node.modifiedBy) {
      isValid = node.modifiedBy != loggedInUserName;
    } else if (node.createdBy && !node.modifiedBy) {
      isValid = node.createdBy != loggedInUserName;
    }

    return isValid;
  }
}
