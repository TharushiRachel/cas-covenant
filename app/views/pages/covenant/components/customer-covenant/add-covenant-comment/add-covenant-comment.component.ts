import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { SessionStorage } from 'ngx-webstorage';
import { CovenantService } from 'src/app/views/pages/covenant/services/covenant.service';

@Component({
  selector: 'app-add-covenant-comment',
  templateUrl: './add-covenant-comment.component.html',
  styleUrls: ['./add-covenant-comment.component.scss']
})
export class AddCovenantCommentComponent implements OnInit {
  @Output() action: EventEmitter<any> = new EventEmitter();
  covenantSerialNumber: number;
  comment: string = '';
  nonComplianceCovenantId: number;
  addedBy: string;
  addedUserDisplayName: string;
  addedDate: string;

  @SessionStorage('facilityPaperID') facilityPaperId: any;

  constructor(
    public modalRef: MDBModalRef,
    public covenantService: CovenantService
  ) {}

  ngOnInit(): void {
    if (this.modalRef && this.modalRef.content) {
      this.comment = this.modalRef.content.comment || '';
      this.nonComplianceCovenantId = this.modalRef.content.nonComplianceCovenantId;
      if (this.modalRef.content.covenantSerialNumber != null) {
        this.covenantSerialNumber = this.modalRef.content.covenantSerialNumber;
      }
    }
  }

  saveComment() {
    if (!this.comment || !this.comment.trim()) {
      console.warn('Comment cannot be empty');
      return;
    }

    const payload: any = {
      serialNumber: this.covenantSerialNumber,
      facilityPaperId: this.facilityPaperId,
      comment: this.comment
    };

    if (this.nonComplianceCovenantId != null) {
      payload.nonComplianceCovenantId = this.nonComplianceCovenantId;
    }

    this.covenantService.addCommentToCovenant(payload).then(
      (response) => {
        this.action.emit(response);
        this.modalRef.hide();
      },
      (error) => {
        console.error('Error adding comment:', error);
      }
    );
  }

  closeModal() {
    this.modalRef.hide();
  }
}
