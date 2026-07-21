import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {FacilityRepaymentUpdateDto} from "../../../../../../../dto/facility-repayment-update-dto";
import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
import * as _ from "lodash";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {SETTINGS} from "../../../../../../../../../../core/setting/commons.settings";

@Component({
  selector: 'app-fp-facility-repayment',
  templateUrl: './fp-facility-repayment.component.html',
  styleUrls: ['./fp-facility-repayment.component.scss']
})
export class FpFacilityRepaymentComponent implements OnInit, OnDestroy {

  heading: string;
  content: any;

  facilityData: any = {};
  facilityPaper: any = {};

  onFPFacilityChangeSub = new Subscription();

  isNewForm: boolean = true;
  yearVal: number = 0;
  monthVal: number = 0;
  totalVal: number = 0;
  componentForm: FormGroup;
  facilityRepaymentUpdateDTO: FacilityRepaymentUpdateDto = new FacilityRepaymentUpdateDto({});

  repaymentTypeOptions = Constants.facilityRepaymentSelect;
  paymentFrequencyOptions = Constants.paymentFrequencySelect;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  constructor(
    private formBuilder: FormBuilder,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    public  mdbModalRef: MDBModalRef
  ) {
  }


  ngOnInit() {

    this.facilityData = this.content.facilityData;
    this.facilityPaper = this.content.facilityPaper;

    this.onFPFacilityChangeSub = this.facilityPaperAddEditService.onFPFacilityChange.subscribe(
      data => {
        if (data) {
          this.mdbModalRef.hide();
        }
      }
    );

    if (!_.isEmpty(this.facilityData.facilityRepaymentDTO)) {
      this.facilityRepaymentUpdateDTO = new FacilityRepaymentUpdateDto(this.facilityData.facilityRepaymentDTO);
      this.isNewForm = false;
    }
    this.componentForm = this.createRepaymentForm();

  }

  isValid() {
    return this.componentForm.valid
  }

  isDirty() {
    return this.componentForm.dirty
  }

  ngOnDestroy(): void {
    this.onFPFacilityChangeSub.unsubscribe();
  }

  createRepaymentForm() {


    return this.formBuilder.group({
      year: [{
        disabled: this.content.isPreviewMode,
        value: Math.round(this.facilityRepaymentUpdateDTO.loanTerm / 12)
      },
        [Validators.required, Validators.pattern("^[0-9]*$")]],
      month: [
        {
          disabled: this.content.isPreviewMode,
          value: this.facilityRepaymentUpdateDTO.loanTerm % 12
        },
        [Validators.required, Validators.pattern("^[0-9]*$")]],
      repaymentType: [{
        disabled: this.content.isPreviewMode,
        value: this.facilityRepaymentUpdateDTO.repaymentType
      },
        [Validators.required]],
      paymentFrequency: [
        {
          disabled: this.content.isPreviewMode,
          value: this.facilityRepaymentUpdateDTO.paymentFrequency
        },
        [Validators.required]],
      paymentDetail: [
        {
          disabled: this.content.isPreviewMode,
          value: this.facilityRepaymentUpdateDTO.paymentDetail
        },
        Validators.required],
      downPayment: [
        {
          disabled: this.content.isPreviewMode,
          value: this.facilityRepaymentUpdateDTO.downPayment
        },
        Validators.required],
      repaymentComment: [
        {
          disabled: this.content.isPreviewMode,
          value: this.facilityRepaymentUpdateDTO.repaymentComment
        },
        Validators.required]
    })
  }


  submit() {
    if (this.isValid()) {
      this.yearVal = this.componentForm.controls.year.value * 12;
      this.monthVal = this.componentForm.controls.month.value;
      this.totalVal = this.yearVal + this.monthVal;

      let repaymentDto = Object.assign({},
        this.componentForm.getRawValue(),
        {facilityRepaymentID: this.facilityRepaymentUpdateDTO.facilityRepaymentID ? this.facilityRepaymentUpdateDTO.facilityRepaymentID : null},
        {facilityID: this.facilityData.facilityID},
        {facilityPaperID: this.facilityPaper.facilityPaperID},
        {loanTerm: this.totalVal});

      this.facilityPaperAddEditService.saveOrUpdateFacilityRepayment(repaymentDto);
    }
  }

}
