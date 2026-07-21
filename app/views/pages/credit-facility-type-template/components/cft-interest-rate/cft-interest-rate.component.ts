import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {CftInterestRateUpdateDto} from "../../dto/cft-interest-Rate-update-dto";
import {NumberValidator} from "../../../../../shared/validators/number.validator";
import {AppUtils} from "../../../../../shared/app.utils";
import {Constants} from "../../../../../core/setting/constants";

@Component({
  selector: 'app-cft-interest-rate',
  templateUrl: './cft-interest-rate.component.html',
  styleUrls: ['./cft-interest-rate.component.scss']
})
export class CftInterestRateComponent implements OnInit, OnDestroy {

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];
  yesNo = [
    {value: 'Y', label: 'Yes'},
    {value: 'N', label: 'No'},
  ];
  subCategoryInterestRates = [
    {value: Constants.interestRateValueConst.EFFECTIVE_RATE, label: Constants.interestRateLabelConst.EFFECTIVE_RATE},
    {value: Constants.interestRateValueConst.REDUCING_RATE, label: Constants.interestRateLabelConst.REDUCING_RATE},
    {value: Constants.interestRateValueConst.FLAT_RATE, label: Constants.interestRateLabelConst.FLAT_RATE},
    {value: Constants.interestRateValueConst.OTHER_RATE, label: Constants.interestRateLabelConst.OTHER_RATE},
  ];

  formErrors: any;
  componentForm: FormGroup;

  onFormValueChange: Subscription = new Subscription();
  action: Subject<any> = new Subject<any>();

  cftInterestRateUpdateDto: CftInterestRateUpdateDto = new CftInterestRateUpdateDto({});

  constructor(
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef
  ) {
  }

  ngOnInit() {

    this.formErrors = {
      rateName: {},
      rateCode: {},
      value: {},
      isDefault: {},
      status: {},
      interestRatingSubCategory: {},
      isEditable: {}
    };

    this.componentForm = this.createCftInterestRateForm();
    this.onFormValueChange.unsubscribe();

    this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChange.unsubscribe();
  }

  createCftInterestRateForm() {
    return this.formBuilder.group({
      creditFacilityTemplateID: this.cftInterestRateUpdateDto.creditFacilityTemplateID,
      rateName: [this.cftInterestRateUpdateDto.rateName, [Validators.required]],
      rateCode: [this.cftInterestRateUpdateDto.rateCode, [Validators.required]],
      value: [this.cftInterestRateUpdateDto.value, [Validators.required, NumberValidator.isPercentageValue]],
      isDefault: [this.cftInterestRateUpdateDto.isDefault ? this.cftInterestRateUpdateDto.isDefault : 'N', [Validators.required]],
      status: [this.cftInterestRateUpdateDto.status ? this.cftInterestRateUpdateDto.status : 'ACT', [Validators.required]],
      interestRatingSubCategory: [this.cftInterestRateUpdateDto.interestRatingSubCategory ? this.cftInterestRateUpdateDto.interestRatingSubCategory : Constants.interestRateValueConst.EFFECTIVE_RATE, [Validators.required]],
      isEditable: [this.cftInterestRateUpdateDto.isEditable ? this.cftInterestRateUpdateDto.isEditable : 'N', [Validators.required]]
    })
  }

  onAdd() {
    if (this.componentForm.valid) {
      let data = Object.assign({},
        this.cftInterestRateUpdateDto,
        this.componentForm.getRawValue()
      );
      this.action.next(data);
      this.mdbModalRef.hide();
    }
  }
}
