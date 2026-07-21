import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

@Component({
  selector: "app-sd-info-dialog",
  templateUrl: "./sd-info-dialog.component.html",
  styleUrls: ["./sd-info-dialog.component.scss"],
})
export class SdInfoDialogComponent implements OnInit {
  heading: string;
  message: string;
  tags: string[];
  action: Subject<any> = new Subject<any>();

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
