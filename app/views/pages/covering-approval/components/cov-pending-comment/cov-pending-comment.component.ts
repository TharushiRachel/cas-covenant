import { Component, Input, OnInit } from '@angular/core';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { DatePipe } from '@angular/common';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-cov-pending-comment',
  templateUrl: './cov-pending-comment.component.html',
  styleUrls: ['./cov-pending-comment.component.scss']
})
export class CovPendingCommentComponent implements OnInit {

  comments: any[] = [];
  //@Input("commentDetails") commentDetails: any ;
  @Input() commentDetails: any;

  constructor(
    private coveringApprovalService: CoveringApprovalService,
    private datePipe: DatePipe,
    public mdbModalRef: MDBModalRef,
  ) { }

  ngOnInit() {
    const covAppId = this.commentDetails.covAppId;
    if (covAppId) {
      this.coveringApprovalService.getCOVCommentById(covAppId).then(datas => {
        this.comments = datas.sort((a, b) => new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime());
      });
    }
  }

  formatDate(date: string): string {
    return this.datePipe.transform(date, 'medium');
  }

}
