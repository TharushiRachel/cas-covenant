import { Component, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";

@Component({
  selector: "app-attachment-detail-modal",
  templateUrl: "./attachment-detail-modal.component.html",
  styleUrls: ["./attachment-detail-modal.component.scss"],
})
export class AttachmentDetailModalComponent implements OnInit {
  heading: string = "";
  lastModifiedDate: string = "";
  fileContent: string = "";
  
  constructor(public modalRef: MDBModalRef) {}

  ngOnInit() {}
}
