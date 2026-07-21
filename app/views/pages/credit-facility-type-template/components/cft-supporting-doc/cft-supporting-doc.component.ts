import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {CftSupportingDocUpdateDto} from "../../dto/cft-supporting-doc-update-dto";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {CreditFacilityTemplateAddEditService} from "../../services/credit-facility-template-add-edit.service";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {Constants} from "../../../../../core/setting/constants";
import {AppUtils} from "../../../../../shared/app.utils";

@Component({
  selector: 'app-cft-supporting-doc',
  templateUrl: './cft-supporting-doc.component.html',
  styleUrls: ['./cft-supporting-doc.component.scss']
})
export class CftSupportingDocComponent implements OnInit, OnDestroy {

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
  supportingDocList = [];

  onFormValueChange: Subscription = new Subscription();
  onLoadSupportingDocListChangeSub: Subscription = new Subscription();
  onSupportDocumentsNameChange: Subscription = new Subscription();
  action: Subject<any> = new Subject<any>();
  result: Subject<any>;

  cftSupportingDocUpdateDTO: CftSupportingDocUpdateDto = new CftSupportingDocUpdateDto({});

  constructor(
    private creditFacilityTemplateAddEditService: CreditFacilityTemplateAddEditService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private cacheService: CacheService,
  ) {
  }

  ngOnInit() {

    this.formErrors = {
      documentName: {},
      mandatory: {},
      status: {}
    };

    this.supportingDocList = this.cacheService.getData(Constants.masterDataKey.CAS_SUPPORTING_DOCs);
    this.result = new BehaviorSubject(this.supportingDocList);

    this.onLoadSupportingDocListChangeSub = this.creditFacilityTemplateAddEditService.onLoadSupportingDocListChange
      .subscribe((response: any) => {
        this.supportingDocList = response;
      });
    this.componentForm = this.createCftSupportDocumentForm();
    this.onFormValueChange.unsubscribe();
    this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

    this.onSupportDocumentsNameChange = this.componentForm.controls.documentName.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value))
      });
  }

  ngOnDestroy(): void {
    this.onLoadSupportingDocListChangeSub.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.supportingDocList.filter((item: any) => item.documentName.toLowerCase().includes(filterValue));
  }

  createCftSupportDocumentForm() {
    return this.formBuilder.group({
      creditFacilityTemplateID: this.cftSupportingDocUpdateDTO.creditFacilityTemplateID,
      documentName: [this.cftSupportingDocUpdateDTO.supportingDocID, Validators.required],
      mandatory: [this.cftSupportingDocUpdateDTO.mandatory ? this.cftSupportingDocUpdateDTO.mandatory : 'N' , [Validators.required]],
      status: [this.cftSupportingDocUpdateDTO.status ? this.cftSupportingDocUpdateDTO.status : 'ACT' , Validators.required]
    })
  }

  onAdd() {

    let doc = AppUtils.getSupportingDocFromDocumentName(this.supportingDocList, this.componentForm.controls.documentName.value);

    if (this.componentForm.valid) {
      let data = Object.assign({},
        this.cftSupportingDocUpdateDTO,
        this.componentForm.getRawValue(),
        {supportingDocID: doc.supportingDocID ? doc.supportingDocID : null},
        {documentName: doc.documentName ? doc.documentName : ""},
      );
      this.action.next(data);
      this.mdbModalRef.hide();
    }
  }
}
