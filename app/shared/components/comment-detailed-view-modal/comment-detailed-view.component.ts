import {Component, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-comment-detailed-view',
  templateUrl: './comment-detailed-view.component.html',
  styleUrls: ['./comment-detailed-view.component.scss']
})
export class CommentDetailedViewComponent implements OnInit {

  heading: string;
  comment: any;
  commentedBy: any;
  commentedDate: any;

  constructor(private mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {
  }


  onNoClick() {
    this.mdbModalRef.hide();
  }

}
