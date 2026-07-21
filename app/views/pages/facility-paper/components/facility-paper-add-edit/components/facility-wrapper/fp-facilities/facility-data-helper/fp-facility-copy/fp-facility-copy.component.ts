import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'app-fp-facility-copy',
  templateUrl: './fp-facility-copy.component.html',
  styleUrls: ['./fp-facility-copy.component.scss']
})
export class FpFacilityCopyComponent implements OnInit {

  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any;
  facilityData: any = {};

  constructor(
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
  ) {
    this.formErrors = {
      noOfCopies: {},
    }
  }

  ngOnInit() {
    this.componentForm = this.createFacilityCopyForm();
  }

  createFacilityCopyForm() {
    this.facilityData = this.content.facilityData;

    return this.formBuilder.group({
      noOfCopies: ['', [Validators.required, Validators.max(10)]],
      facilityID: [this.facilityData.facilityID],
      facilityPaperID: [this.facilityData.facilityPaperID],
    })
  }

  onAdd() {

    let fpCopy = Object.assign({},
      {facilityPaperID: this.facilityData.facilityPaperID,
        facilityID: this.facilityData.facilityID,
        noOfCopies: this.componentForm.controls.noOfCopies.value}
        );

    this.facilityPaperAddEditService.duplicateFpFacilities(fpCopy);
    this.mdbModalRef.hide();

  }

  keyPressNumbers(event) {

    var charCode = (event.which) ? event.which : event.keyCode;
    if ((charCode < 49 || charCode > 57)) {
      return false;
    } else {
      return true;
    }
  }

}
