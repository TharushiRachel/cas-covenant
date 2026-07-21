import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs";
import {CftVitalQuestionUpdateDto} from "../../dto/cft-vital-question-update-dto";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {AppUtils} from "../../../../../shared/app.utils";

@Component({
  selector: 'app-cft-vital-question',
  templateUrl: './cft-vital-question.component.html',
  styleUrls: ['./cft-vital-question.component.scss']
})
export class CftVitalQuestionComponent implements OnInit, OnDestroy {

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];
  yesNo = [
    {value: 'Y', label: 'Yes'},
    {value: 'N', label: 'No'},
  ];

  formErrors: any;
  componentForm: FormGroup;

  onFormValueChange: Subscription = new Subscription();
  action: Subject<any> = new Subject<any>();
  result: Subject<any>;

  cftVitalQuestionUpdateDto: CftVitalQuestionUpdateDto = new CftVitalQuestionUpdateDto({});

  constructor(private formBuilder: FormBuilder,
              public  mdbModalRef: MDBModalRef) {

  }

  ngOnInit() {

    this.formErrors = {
      vitalInfoName: {},
      mandatory: {},
      displayOrder: {},
      status: {}
    };

    this.componentForm = this.createCftVitalQuestionForm();
    this.onFormValueChange.unsubscribe();

    this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFormValueChange.unsubscribe();
  }

  createCftVitalQuestionForm() {
    return this.formBuilder.group({
      cftVitalInfoID: this.cftVitalQuestionUpdateDto.cftVitalInfoID,
      creditFacilityTemplate: [this.cftVitalQuestionUpdateDto.creditFacilityTemplate],
      vitalInfoName: [this.cftVitalQuestionUpdateDto.vitalInfoName, [Validators.required]],
      mandatory: [this.cftVitalQuestionUpdateDto.mandatory ? this.cftVitalQuestionUpdateDto.mandatory: 'N' , [Validators.required]],
      displayOrder: [this.cftVitalQuestionUpdateDto.displayOrder, [Validators.required]],
      status: [this.cftVitalQuestionUpdateDto.status ? this.cftVitalQuestionUpdateDto.status : 'ACT', [Validators.required]]
    })
  }

  onAdd() {
    let data = Object.assign({},
      new CftVitalQuestionUpdateDto(this.componentForm.getRawValue())
    );
    this.action.next(data);
    this.mdbModalRef.hide();
  }

}
