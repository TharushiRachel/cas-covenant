import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { Subject, Subscription } from 'rxjs';
import {Constants} from "../../../../../core/setting/constants";
import { CftCustomFacilityInformationDto } from '../../dto/cft-custom-facility-information-dto';
import {AppUtils} from "../../../../../shared/app.utils";


@Component({
  selector: 'app-custom-facility-information',
  templateUrl: './custom-facility-information.component.html',
  styleUrls: ['./custom-facility-information.component.scss']
})
export class CustomFacilityInformationComponent implements OnInit {

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];
  yesNo = [
    {value: 'Y', label: 'Yes'},
    {value: 'N', label: 'No'},
  ];

  optionSelectInputFieldType = Constants.optionsInputFieldValueTypeSelectOpt;
  inputFieldValueTypeConst = Constants.inputFieldValueTypeConst;
  statusConst = Constants.statusConst;
  yesNoConst = Constants.yesNoConst;

  formErrors: any;
  componentForm: FormGroup;

  onFormValueChange: Subscription = new Subscription();
  action: Subject<any> = new Subject<any>();
  result: Subject<any>;

  customFacilityInforDto: CftCustomFacilityInformationDto = new CftCustomFacilityInformationDto({});

  constructor(
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef) { }

  ngOnInit() {
    this.formErrors = {
      customFacilityInfoName: {},
      customFacilityInfoCode: {},
      fieldType: {},
      description: {},
      mandatory: {},
      status: {},
      displayOrder: {},
    };

    this.componentForm = this.createCustomFacilityInfoForm();
    
    this.onFormValueChange.unsubscribe();

    this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChange.unsubscribe();
  }

  createCustomFacilityInfoForm(){
    return this.formBuilder.group({
      cftCustomFacilityInfoID: this.customFacilityInforDto.cftCustomFacilityInfoID,
      creditFacilityTemplate: [this.customFacilityInforDto.creditFacilityTemplate],
      customFacilityInfoName: [this.customFacilityInforDto.customFacilityInfoName, [Validators.required]],
      customFacilityInfoCode: [this.customFacilityInforDto.customFacilityInfoCode, [Validators.required]],
      fieldType: [this.customFacilityInforDto.fieldType
        ? this.customFacilityInforDto.fieldType : this.inputFieldValueTypeConst.NUMBER, [Validators.required]],
      description: [this.customFacilityInforDto.description, [Validators.required]],
      mandatory: [this.customFacilityInforDto.mandatory ? this.customFacilityInforDto.mandatory : this.yesNoConst.N, [Validators.required]],
      status: [this.customFacilityInforDto.status ? this.customFacilityInforDto.status : this.statusConst.ACT, [Validators.required]],
      displayOrder: [this.customFacilityInforDto.displayOrder, [Validators.required]],
    })
  }

  onAdd() {
    let data = Object.assign({},
      new CftCustomFacilityInformationDto(this.componentForm.getRawValue())
    );
    this.action.next(data);
    this.mdbModalRef.hide();
    }
}
