import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

@Component({
  selector: "app-show-esg-instructions",
  templateUrl: "./show-esg-instructions.component.html",
  styleUrls: ["./show-esg-instructions.component.scss"],
})
export class ShowEsgInstructionsComponent implements OnInit, OnDestroy {
  instructions: any[] = [
    "New businesses can be categorized under the 'New Development' criteria.",
    "Existing businesses can be categorized under the 'Existing Projects'.",
    "Additionally, risk category B and C projects should be screened for the location criteria.",
    "If the business is located in an environmentally and/or socially sensitive area, the risk category will be enhanced to B+.",
    "When the business is not located in an environmentally and/or socially sensitive area, you may proceed with the results given by the criteria of new development or existing projects.",
    "You may use 'Windows Key+Shift+S' to copy the screen, including the risk category, activity details, customer details to attach to 'CAS', and any other further references.",
  ];
  
  action: Subject<any> = new Subject<any>();

  constructor(private mdbModalRef: MDBModalRef) {}

  ngOnInit() {}
  ngOnDestroy(): void {
    this.action.unsubscribe();
  }

  onClose(): void {
    this.action.next(false);
    this.mdbModalRef.hide();
  }
}
