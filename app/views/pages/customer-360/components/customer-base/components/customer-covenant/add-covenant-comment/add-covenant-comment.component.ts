import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { SessionStorage } from 'ngx-webstorage';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'app-add-covenant-comment',
  templateUrl: './add-covenant-comment.component.html',
  styleUrls: ['./add-covenant-comment.component.scss']
})
export class AddCovenantCommentComponent implements OnInit {
  @Output() action: EventEmitter<any> = new EventEmitter(); 
  covenantSerialNumber: number;
  comment: string;
  nonComplianceCovenantId: number;
  addedBy: string;
  addedUserDisplayName: string;
  addedDate: string;

  @SessionStorage('facilityPaperID') facilityPaperId: any;

  constructor(
    public modalRef: MDBModalRef,
    public facilityPaperAddEditService: FacilityPaperAddEditService,
  ) {}

  ngOnInit(): void {
    if (this.modalRef && this.modalRef.content) {
      this.comment = this.modalRef.content.comment;
      this.nonComplianceCovenantId = this.modalRef.content.nonComplianceCovenantId; // <-- assign here
    console.log("this.nonComplianceCovenantId", this.nonComplianceCovenantId);
      
    }
  }

  saveComment() {
    if (this.comment.trim()) {
      const payload = {
        serialNumber: this.covenantSerialNumber,
        facilityPaperId: this.facilityPaperId,
        comment: this.comment,
        ...(this.nonComplianceCovenantId != null && { nonComplianceCovenantId: this.nonComplianceCovenantId })
      };
      // Call the service method
      this.facilityPaperAddEditService.addCommentToCovenant(payload).then(
        (response) => {
          console.log('Comment added successfully:', response);
          this.action.emit(response); // Emit the response back to the parent component
          this.modalRef.hide(); // Close the modal
        },
        (error) => {
          console.error('Error adding comment:', error);
        }
      );
    } else {
      console.warn('Comment cannot be empty');
    }
  }

  closeModal() {
    this.modalRef.hide();
  }
}
