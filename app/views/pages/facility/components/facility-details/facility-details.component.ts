import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {FacilityUpdateDto} from "../../dto/facility-update-dto";
import {Subscription} from "rxjs";
import {FacilityDetailsService} from "../../services/facility-details.service";
import * as _ from 'lodash';
import {Constants} from "../../../../../core/setting/constants";


@Component({
  selector: 'app-facility-details',
  templateUrl: './facility-details.component.html',
  styleUrls: ['./facility-details.component.scss']
})
export class FacilityDetailsComponent implements OnInit, OnDestroy {

  selectedFacility: any = {};
  componentForm: FormGroup;
  formErrors: any;
  facilityUpdateDto: FacilityUpdateDto = new FacilityUpdateDto({});
  onFacilityChangesub = new Subscription();

  status = Constants.status;
  yesNo = Constants.yesNo;
  approveStatus = Constants.approveStatus;

  tableColumnsForFacilityDocument = ['Supporting Document', 'Mandatory', 'Display Order', 'Remark', 'Status'];
  tableColumnsForFacilityInterestRate = ['Rate Code', 'Value', 'Is Default', 'Status'];
  tableColumnsForFacilityPurposeofAdvance = ['Purpose of Advance', 'Reference Description', 'Reference Code', 'Status'];
  tableColumnsForFacilitySecurity = ['Security Detail', 'Security Amount', 'Status'];

  constructor(
    private formBuilder: FormBuilder,
    private facilityDetailsService: FacilityDetailsService
  ) {
  }

  ngOnInit() {
    this.onFacilityChangesub = this.facilityDetailsService.onSelectedFacilityChannge
      .subscribe(facility => {
        if (!(_.isEmpty(facility))) {
          this.selectedFacility = facility;
          this.facilityUpdateDto = new FacilityUpdateDto(facility);
        }
      });

    this.componentForm = this.createFacilityForm();

  }

  ngOnDestroy(): void {
  }


  createFacilityForm() {
    return this.formBuilder.group({
      creditFacilityTemplateID: [{value: this.facilityUpdateDto.creditFacilityTemplateID, disabled: true}],
      creditFacilityTemplateDTO: [{value: this.facilityUpdateDto.creditFacilityTemplateDTO, disabled: true}],
      facilityPaperID: [{value: this.facilityUpdateDto.facilityPaperID, disabled: true}],
      facilityCurrency: [{value: this.facilityUpdateDto.facilityCurrency, disabled: true}],
      disbursementAccNumber: [{value: this.facilityUpdateDto.disbursementAccNumber, disabled: true}],
      facilityAmount: [{value: this.facilityUpdateDto.facilityAmount, disabled: true}],
      isCooperate: [{value: this.facilityUpdateDto.isCooperate, disabled: true}],
      outstandingAmount: [{value: this.facilityUpdateDto.outstandingAmount, disabled: true}],
      sectorID: [{value: this.facilityUpdateDto.subSectorID, disabled: true}],
      subSectorID: [{value: this.facilityUpdateDto.subSectorID, disabled: true}],
      purposeOfAdvance: [{value: this.facilityUpdateDto.purposeOfAdvance, disabled: true}],
      purpose: [{value: this.facilityUpdateDto.purpose, disabled: true}],
      facilityTypeDTO: [{value: this.facilityUpdateDto.facilityTypeDTO, disabled: true}],
      facilityType: [{value: this.facilityUpdateDto.facilityType, disabled: true}],
      isOneOff: [{value: this.facilityUpdateDto.isOneOff, disabled: true}],
      repayment: [{value: this.facilityUpdateDto.repayment, disabled: true}],
      condition: [{value: this.facilityUpdateDto.condition, disabled: true}],
      isNew: [{value: this.facilityUpdateDto.isNew, disabled: true}],
      displayOrder: [{value: this.facilityUpdateDto.displayOrder, disabled: true}],
      remark: [{value: this.facilityUpdateDto.remark, disabled: true}],
      status: [{value: this.facilityUpdateDto.status, disabled: true}],
      facilityDocumentList: [{value: this.facilityUpdateDto.facilityDocumentList, disabled: true}],
      facilityInterestRateList: [{value: this.facilityUpdateDto.facilityInterestRateList, disabled: true}],
      facilityPurposeOfAdvanceList: [{value: this.facilityUpdateDto.facilityPurposeOfAdvanceList, disabled: true}],
      facilitySecurityDTOList: [{value: this.facilityUpdateDto.facilitySecurityDTOList, disabled: true}],
    })
  }
}
