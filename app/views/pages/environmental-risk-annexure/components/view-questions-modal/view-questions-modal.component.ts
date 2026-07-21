import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

@Component({
  selector: "app-view-questions-modal",
  templateUrl: "./view-questions-modal.component.html",
  styleUrls: ["./view-questions-modal.component.scss"],
})
export class ViewQuestionsModalComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  annex: any;

  annexureName: string = "";
  questions: any[] = [];

  constructor(private readonly mdbModalRef: MDBModalRef) {}

  ngOnInit() {
    if (this.annex) {
      this.annexureName = this.annex.name;
      this.questions = this.annex.questions;
    }
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }
}
