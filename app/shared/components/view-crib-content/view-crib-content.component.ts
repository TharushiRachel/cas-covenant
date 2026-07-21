import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

@Component({
  selector: "app-view-crib-content",
  templateUrl: "./view-crib-content.component.html",
  styleUrls: ["./view-crib-content.component.scss"],
})
export class ViewCribContentComponent implements OnInit {
  action: Subject<any> = new Subject<any>();

  content: any = "";

  constructor(private mdbModalRef: MDBModalRef) {}

  ngOnInit() {}

  onNoClick(): void {
    this.action.next(false);
    this.mdbModalRef.hide();
  }
}
