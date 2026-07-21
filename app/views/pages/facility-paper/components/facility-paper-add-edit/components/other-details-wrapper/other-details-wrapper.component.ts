import {Component, Input, OnInit} from '@angular/core';
import { FacilityPaperAddEditService } from '../../../../services/facility-paper-add-edit.service';

@Component({
  selector: 'app-other-details-wrapper',
  templateUrl: './other-details-wrapper.component.html',
  styleUrls: ['./other-details-wrapper.component.scss']
})
export class OtherDetailsWrapperComponent implements OnInit {

  @Input('facilityPaper') facilityPaper: any = {};

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
  ) { }

  ngOnInit() {
  }

  isCooperateFacilityPaper() {
    return this.facilityPaper.isCooperate == 'Y'
  }

}
