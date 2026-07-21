import { Component, Input, OnDestroy, OnInit, Output, EventEmitter } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { Subscription } from 'rxjs';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { DateService } from 'src/app/core/service/application/date.service';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { Constants } from 'src/app/core/setting/constants';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import * as _ from 'lodash';
import { CommentWithViewOptionsDialogComponent } from 'src/app/shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component';
import { AppUtils } from 'src/app/shared/app.utils';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ca-details-comment',
  templateUrl: './ca-details-comment.component.html',
  styleUrls: ['./ca-details-comment.component.scss']
})
export class CaDetailsCommentComponent implements OnInit {
  comments: any[] = [];
  // @Input("commentDetails") commentDetails: any ;
  @Input() commentDetails: any;

  constructor(
    private coveringApprovalService: CoveringApprovalService,
    private datePipe: DatePipe,

  ) { }


  ngOnInit() {

    // Ensure commentDetails is defined and has covAppId
    if (this.commentDetails && this.commentDetails.covAppId) {
      const covAppId = this.commentDetails.covAppId;
      this.coveringApprovalService.getCOVCommentById(covAppId).then(datas => {
        this.comments = datas.sort((a, b) => new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime());
      });
    } else {
      console.warn('commentDetails or covAppId is not available.');
    }
  }

  formatDate(date: string): string {
    return this.datePipe.transform(date, 'medium');
  }


}
