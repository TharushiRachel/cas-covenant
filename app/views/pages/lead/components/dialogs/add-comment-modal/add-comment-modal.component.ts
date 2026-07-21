import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

@Component({
  selector: "app-add-comment-modal",
  templateUrl: "./add-comment-modal.component.html",
  styleUrls: ["./add-comment-modal.component.scss"],
})
export class AddCommentModalComponent implements OnInit {
  heading: string;
  message: string;
  actionName: string;
  action: Subject<any> = new Subject<any>();

  comment: string = "";

  constructor(private readonly mdbModalRef: MDBModalRef) {}

  ngOnInit() {}

  onNoClick(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  handleSave() {
    this.action.next(this.comment);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }
}
