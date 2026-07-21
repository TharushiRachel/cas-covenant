import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";

@Component({
  selector: "app-compare-data-modal",
  templateUrl: "./compare-data-modal.component.html",
  styleUrls: ["./compare-data-modal.component.scss"],
})
export class CompareDataModalComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  selectedAnnex: any = null;
  pendingAnnex: any = null;

  questions: any[] = [];
  pendingQuestions: any[] = [];

  selectedTabIndex: number = 0;
  constructor(
    private readonly mdbModalRef: MDBModalRef,
    private readonly applicationService: ApplicationService
  ) {}

  ngOnInit() {
    if (this.selectedAnnex) {
      this.questions = this.selectedAnnex.questions;
      this.pendingAnnex = this.selectedAnnex.pendingRec;
      this.pendingQuestions = this.selectedAnnex.pendingRec.questions;
    }
  }

  onTabSelect(index: number) {
    this.selectedTabIndex = index;
  }

  onClose(): void {
    this.action.next("C");
    this.mdbModalRef.hide();
  }

  approve() {
    this.action.next("A");
    this.mdbModalRef.hide();
  }

  reject() {
    this.action.next("R");
    this.mdbModalRef.hide();
  }

  isValidUser(annex: any): boolean {
    annex = annex.pendingRec ? annex.pendingRec : annex;
    var isValid: boolean = false;
    let loggedInUserName: string =
      this.applicationService.getLoggedInUserUserName();
    if (annex.createdBy && annex.modifiedBy) {
      isValid = annex.modifiedBy != loggedInUserName;
    } else if (annex.createdBy && !annex.modifiedBy) {
      isValid = annex.createdBy != loggedInUserName;
    }

    return isValid;
  }
}
