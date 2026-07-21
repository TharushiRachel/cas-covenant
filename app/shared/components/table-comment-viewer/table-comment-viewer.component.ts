import {Component, Input, OnInit} from '@angular/core';
import {CommentDetailedViewComponent} from "../comment-detailed-view-modal/comment-detailed-view.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-table-comment-viewer',
  templateUrl: './table-comment-viewer.component.html',
  styleUrls: ['./table-comment-viewer.component.scss']
})
export class TableCommentViewerComponent implements OnInit {

  @Input('commentDto') commentDto: any;
  @Input('toolTipDescription') toolTipDescription: string;

  modalRef: MDBModalRef;
  isTruncated: boolean;
  comment: string;

  constructor(private mdbModalService: MDBModalService) {
  }

  ngOnInit() {
    this.comment = this.truncateString(this.commentDto.comment.replace(/\n/g, ''), 50);
  }

  truncateString(str, num) {
    if (str.length >= num) {
      this.isTruncated = true;
      return str.slice(0, num) + '...';
    }
    return str;
  }

  getContent(data) {
    return `<pre class="custom-pre-tag">${data || '-'}</pre>`;
  }


  openModalViewComment() {
    const initialState = {
      list: [
        {"tag": 'Count', "value": this.commentDto}
      ]
    };
    this.modalRef = this.mdbModalService.show(CommentDetailedViewComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: '',
      animated: true,
      data: {
        heading: "Comment",
        comment: this.getContent(this.commentDto.comment),
        commentedDate: this.commentDto.commentedTimeStr,
        commentedBy: this.commentDto.createdUserDisplayName
      }
    });
  }
}
