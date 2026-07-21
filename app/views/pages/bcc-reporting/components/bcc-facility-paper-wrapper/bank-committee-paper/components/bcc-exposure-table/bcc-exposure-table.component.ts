import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {CurrencyService} from "../../../../../../../../core/service/common/currency.service";
import {Subscription} from "rxjs";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-bcc-exposure-table',
  templateUrl: './bcc-exposure-table.component.html',
  styleUrls: ['./bcc-exposure-table.component.scss']
})
export class BccExposureTableComponent implements OnInit, OnDestroy {

  @Input() exposureGroup: FormGroup;
  bccExposureCompanyForm: FormGroup;
  bccExposureGroupForm: FormGroup;
  bccExposureCompanyFormErrors: any;
  bccExposureGroupFormErrors: any;
  formChangeSub = new Subscription();

  constructor(public currencyService: CurrencyService) {
  }

  ngOnInit() {

    this.bccExposureCompanyForm = this.exposureGroup.controls['bccExposureCompanyDTO'] as FormGroup;
    this.bccExposureGroupForm = this.exposureGroup.controls['bccExposureGroupDTO'] as FormGroup;

    this.bccExposureCompanyFormErrors = {
      existingExposureDirect: {},
      existingExposureIndirect: {},
      existingExposureTotal: {},
      additionalExposureDirect: {},
      additionalExposureIndirect: {},
      additionalExposureTotal: {},
      totalExposureDirect: {},
      totalExposureIndirect: {},
      totalExposureTotal: {},
      type: {},
      exposureSecuredBy: {},
      approvedFSV: {},
      againstApprovedFSV: {},
      againstTotalExposure: {},
    };

    this.bccExposureGroupFormErrors = {
      existingExposureDirect: {},
      existingExposureIndirect: {},
      existingExposureTotal: {},
      additionalExposureDirect: {},
      additionalExposureIndirect: {},
      additionalExposureTotal: {},
      totalExposureDirect: {},
      totalExposureIndirect: {},
      totalExposureTotal: {},
      type: {},
      exposureSecuredBy: {},
      approvedFSV: {},
      againstApprovedFSV: {},
      againstTotalExposure: {},
    };


    this.formChangeSub.unsubscribe();
    this.formChangeSub = this.exposureGroup.get('bccExposureCompanyDTO').valueChanges.subscribe((form) => {
      this.bccExposureCompanyFormErrors = AppUtils.getFormErrors(this.bccExposureCompanyForm, this.bccExposureCompanyFormErrors);
    });

    this.formChangeSub = this.exposureGroup.get('bccExposureGroupDTO').valueChanges.subscribe((form) => {
      this.bccExposureGroupFormErrors = AppUtils.getFormErrors(this.bccExposureGroupForm, this.bccExposureGroupFormErrors);
    });

  }

  ngOnDestroy(): void {
    this.formChangeSub.unsubscribe();
  }

  validateNumber(event){
    NumberValidator.validateNumber(event);
  }

}
