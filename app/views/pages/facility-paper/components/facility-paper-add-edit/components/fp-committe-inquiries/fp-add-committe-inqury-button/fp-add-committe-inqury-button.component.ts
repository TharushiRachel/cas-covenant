import { Component, Input, OnInit } from '@angular/core';
import { FpAddCommitteeInquiryComponent } from '../fp-add-committee-inquiry/fp-add-committee-inquiry.component';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';

@Component({
  selector: 'app-fp-add-committe-inqury-button',
  templateUrl: './fp-add-committe-inqury-button.component.html',
  styleUrls: ['./fp-add-committe-inqury-button.component.scss']
})
export class FpAddCommitteInquryButtonComponent implements OnInit {

  @Input("facilityPaper") facilityPaper;
  isCommitteePaper: boolean = false;
  isMember: boolean = null;
  editMode: boolean = false;
  modalRef: MDBModalRef;

  constructor(private mdbModalService: MDBModalService,
    private applicationService: ApplicationService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,) { }

  ngOnInit() {
    this.isCommitteeMember();
  }

  openAddCommitteInquires($event: Event): void {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.editMode = false; // add mode
    this.modalRef = this.mdbModalService.show(FpAddCommitteeInquiryComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      containerClass: "",
      animated: false,
      class: "modal-width-85-p modal-dialog-scrollable",
      data: {
        mode: "inquiry",
        facilityPaperID: this.facilityPaper.facilityPaperID,
        facilityPaper: this.facilityPaper,
        isEdit: false,
      },
    });

    this.modalRef.content.action.subscribe((savedInquiry: any) => {
      if (savedInquiry) {
        this.modalRef.hide();
      }
    });
  }
  isCommitteeMember(): void {
    this.facilityPaperAddEditService.getCommitteeUsers(this.facilityPaper.facilityPaperID)
      .subscribe({
        next: (res: any[]) => {
          const loggedInUser = this.applicationService.getLoggedInUserUserName();
          this.isMember = (res && res.some(user => user.userName === loggedInUser)) || false;
        },
        error: (err) => {
          this.isMember = false;
        },
      });
  }

  showCommitteeButton() {
    return this.isMember === true;
  }

}
