import { Component, Input, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { Subject } from 'rxjs';
import { LeadComprehensiveService } from '../../services/lead-comprehensive.service';

@Component({
  selector: 'app-digital-applicant-picker-modal',
  templateUrl: './digital-applicant-picker-modal.component.html',
  styleUrls: ['./digital-applicant-picker-modal.component.scss']
})
export class DigitalApplicantPickerModalComponent implements OnInit {

  modalRef: MDBModalRef;
  action = new Subject<{ selectedIds: number[] }>();

  loading = false;
  selected = new Set<number>();

  @Input() compLeadId!: number;
  @Input() parties: any[] = [];

  constructor(
    public mdbModalRef: MDBModalRef
  ) { }

  ngOnInit() {
    this.parties = Array.isArray(this.parties) ? this.parties : [];
  }


  toggle(id: number, checked: boolean): void {
    checked ? this.selected.add(id) : this.selected.delete(id);
  }

  onNext(): void {
    if (this.selected.size === 0) return;
    this.action.next({ selectedIds: Array.from(this.selected) });
    this.mdbModalRef.hide();
  }

  onCancel(): void {
    this.mdbModalRef.hide();
  }


}
