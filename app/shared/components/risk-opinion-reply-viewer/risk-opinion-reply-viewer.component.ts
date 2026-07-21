import {Component, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-risk-opinion-reply-viewer',
  templateUrl: './risk-opinion-reply-viewer.component.html',
  styleUrls: ['./risk-opinion-reply-viewer.component.scss']
})
export class RiskOpinionReplyViewerComponent implements OnInit {

  riskComment: string;
  riskReply: string;
  createdUserName: string;
  createdRiskReplyUserName: string;
  createdDateStr: Date;
  createdDate: Date;
  modifiedUserName: string;
  modifiedDateStr: Date;
  modifiedRiskReplyUserName: string;
  modifiedDate: Date;
  divCode: string


  constructor(public mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {
  }

  getContent(data) {
    // console.log(data);
    return `<pre class="credit-risk-comment-pre-tag">${data || '-'}</pre>`;
  }


}
