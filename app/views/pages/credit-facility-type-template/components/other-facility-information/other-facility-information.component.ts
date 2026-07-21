import {Component, OnInit} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {CftOtherFacilityInformationDto} from "../../dto/cft-other-facility-information-dto";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {AppUtils} from "../../../../../shared/app.utils";

@Component({
  selector: 'app-other-facility-information',
  templateUrl: './other-facility-information.component.html',
  styleUrls: ['./other-facility-information.component.scss']
})
export class OtherFacilityInformationComponent implements OnInit {

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

  otherFacilityInfoDto: CftOtherFacilityInformationDto = new CftOtherFacilityInformationDto({});

  constructor(private formBuilder: FormBuilder,
              public  mdbModalRef: MDBModalRef) {
  }

  ngOnInit() {
    this.formErrors = {
      otherFacilityInfoName: {},
      mandatory: {},
      status: {},
      displayOrder: {},
      defaultValue: {},
      otherFacilityInfoFieldType: {},
      description: {},
      otherFacilityInfoCode: {},
    };

    this.componentForm = this.createOtherFacilityInfoForm();
    this.onFormValueChange.unsubscribe();

    this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

  }

  ngOnDestroy(): void {
    this.onFormValueChange.unsubscribe();
  }

  createOtherFacilityInfoForm() {
    return this.formBuilder.group({
      cftOtherFacilityInfoID: this.otherFacilityInfoDto.cftOtherFacilityInfoID,
      creditFacilityTemplate: [this.otherFacilityInfoDto.creditFacilityTemplate],
      otherFacilityInfoName: [this.otherFacilityInfoDto.otherFacilityInfoName, [Validators.required]],
      description: [this.otherFacilityInfoDto.description, [Validators.required]],
      otherFacilityInfoCode: [this.otherFacilityInfoDto.otherFacilityInfoCode, [Validators.required]],
      otherFacilityInfoFieldType: [this.otherFacilityInfoDto.otherFacilityInfoFieldType
        ? this.otherFacilityInfoDto.otherFacilityInfoFieldType : this.inputFieldValueTypeConst.NUMBER, [Validators.required]],
      defaultValue: [this.otherFacilityInfoDto.defaultValue],
      displayOrder: [{value: this.otherFacilityInfoDto.displayOrder, disabled: true}],
      mandatory: [this.otherFacilityInfoDto.mandatory ? this.otherFacilityInfoDto.mandatory : this.yesNoConst.N, [Validators.required]],
      status: [this.otherFacilityInfoDto.status ? this.otherFacilityInfoDto.status : this.statusConst.ACT, [Validators.required]]
    })
  }

  onAdd() {
    let data = Object.assign({},
      new CftOtherFacilityInformationDto(this.componentForm.getRawValue())
    );
    this.action.next(data);
    this.mdbModalRef.hide();
  }

}
