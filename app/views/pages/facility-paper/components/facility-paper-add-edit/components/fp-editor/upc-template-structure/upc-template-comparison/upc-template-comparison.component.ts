import { Component, OnInit, ViewChild } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { UPCTemplateCommentSectionComponent } from "./upc-template-comment-section/upc-template-comment-section.component";

@Component({
  selector: "app-upc-template-comparison",
  templateUrl: "./upc-template-comparison.component.html",
  styleUrls: ["./upc-template-comparison.component.scss"],
})
export class UPCTemplateComparisonComponent implements OnInit {
  selectedTabIndex: any = 0;
  content: any;
  nodeData: any;
  commentTabIndex: Number = 0;
  comparisonTabIndex: Number = 1;

  @ViewChild("child", { static: false })
  child: UPCTemplateCommentSectionComponent;

  constructor(public upcTemplateComparisonModelRef: MDBModalRef) {}

  ngOnInit() {
    this.nodeData = this.content.initialState.node;
  }

  onCloseModel() {
    this.upcTemplateComparisonModelRef.hide();
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  refreshComments() {
    if (this.child) {
      this.child.getCommentHistory();
    }
  }
}
