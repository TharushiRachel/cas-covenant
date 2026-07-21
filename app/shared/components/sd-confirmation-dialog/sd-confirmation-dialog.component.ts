import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

@Component({
  selector: "app-sd-confirmation-dialog",
  templateUrl: "./sd-confirmation-dialog.component.html",
  styleUrls: ["./sd-confirmation-dialog.component.scss"],
})
export class SdConfirmationDialogComponent implements OnInit, OnDestroy {
  heading: string;
  message: string;
  action: Subject<any> = new Subject<any>();
  isSDConfirmation: boolean = false;

  constructor(private mdbModalRef: MDBModalRef) {}

  ngOnInit() {}

  onNoClick(): void {
    this.action.next(false);
    this.mdbModalRef.hide();
  }

  onYesClick(): void {
    this.action.next(true);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }
}
