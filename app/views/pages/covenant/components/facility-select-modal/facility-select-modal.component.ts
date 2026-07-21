import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'facility-select-modal',
  templateUrl: './facility-select-modal.component.html',
  styleUrls: ['./facility-select-modal.component.scss']
})
export class FacilitySelectModalComponent implements OnInit {
  @Output() facilitySelected = new EventEmitter<{ acctId: string, facility: any }>();
  @Input() facilities: any[] = [];
  @Input() accountId: string | undefined;
  @Input() facilityPaperId: string | undefined;
  @Input() recordId: any;

  constructor(
    public mdbModalRef: MDBModalRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit(): void {}

  selectFacility(facility: any) {

    console.log("facility****", facility);
    const data = {
      acctId: this.accountId,
      facilityId: facility.facilityID,
      facilityPaperId: this.facilityPaperId,
      id: this.recordId,
    };
    this.facilityPaperAddEditService.saveOrUpdateAcctIdWithFacilityId(data)
      .then(() => {
        this.facilitySelected.emit({ acctId: this.accountId || '', facility });
        this.mdbModalRef.hide();
      })
      .catch(() => {
        alert('Failed to save selection.');
      });
  }
}
