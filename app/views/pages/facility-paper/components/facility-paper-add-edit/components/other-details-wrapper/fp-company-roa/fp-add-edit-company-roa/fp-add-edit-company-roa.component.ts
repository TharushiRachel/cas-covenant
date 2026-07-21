import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FacilityPaperAddEditService} from "../../../../../../services/facility-paper-add-edit.service";
import * as _ from "lodash";
import {NumberValidator} from "../../../../../../../../../shared/validators/number.validator";
import {AppUtils} from "../../../../../../../../../shared/app.utils";

@Component({
  selector: 'app-fp-add-edit-company-roa',
  templateUrl: './fp-add-edit-company-roa.component.html',
  styleUrls: ['./fp-add-edit-company-roa.component.scss']
})
export class FpAddEditCompanyRoaComponent implements OnInit, OnDestroy {


  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any;
  action: Subject<any> = new Subject<any>();
  companyROA: any = {};
  facilityPaper: any = {};
  isCooperate = false;
  onFormValueChange: Subscription = new Subscription();


  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef
  ) {
  }


  ngOnInit() {
    this.formErrors = {
      description: {}
    };

    this.componentForm = this.createForm();

    this.onFormValueChange.unsubscribe();
    this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChange.unsubscribe();
  }

  createForm() {
    this.companyROA = this.content.companyROA;
    this.facilityPaper = this.content.facilityPaper;
    this.isCooperate = this.facilityPaper.isCooperate == 'Y';

    let status = this.companyROA.status || 'ACT';
    return this.formBuilder.group({
      description: [this.companyROA.description, [Validators.required, NumberValidator.isPercentageValue]],
      comment: [this.companyROA.comment, []],
    })
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  onAdd() {
    if (this.content.facilityPaper) {
      let fpCompanyROA = Object.assign({},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {
          fpCompanyRoaDTOList: [Object.assign({},
            this.content.companyROA, {
              description: this.componentForm.controls.description.value,
              comment: this.componentForm.controls.comment.value,
              status: 'ACT'
            })]
        });

      this.action.next(fpCompanyROA);
      this.facilityPaperAddEditService.saveFpCompanyRoa(fpCompanyROA);
      this.mdbModalRef.hide();
    }
  }

  isNewCompanyROA() {
    return _.isEmpty(this.companyROA);
  }

  onRemove() {
    if (this.content.facilityPaper) {
      let fpCompanyROA = Object.assign({},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {
          fpCompanyRoaDTOList: [Object.assign({},
            this.content.companyROA, {
              description: this.componentForm.controls.description.value,
              comment: this.componentForm.controls.comment.value,
              status: 'INA'
            })]
        });

      this.action.next(fpCompanyROA);
      this.facilityPaperAddEditService.saveFpCompanyRoa(fpCompanyROA);
      this.mdbModalRef.hide();
    }
  }
}
