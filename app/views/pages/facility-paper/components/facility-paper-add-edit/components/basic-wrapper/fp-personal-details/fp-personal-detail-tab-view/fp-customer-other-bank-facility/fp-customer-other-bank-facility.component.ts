import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import {CacheService} from "../../../../../../../../../../core/service/data/cache.service";
import {FpOtherBankUpdateDto} from "../../../../../../../dto/fp-other-bank-update-dto";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import * as _ from "lodash";
import {SETTINGS} from "../../../../../../../../../../core/setting/commons.settings";
import {AppUtils} from "../../../../../../../../../../shared/app.utils";
import {NumberValidator} from "../../../../../../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-fp-customer-other-bank-facility',
  templateUrl: './fp-customer-other-bank-facility.component.html',
  styleUrls: ['./fp-customer-other-bank-facility.component.scss']
})
export class FpCustomerOtherBankFacilityComponent implements OnInit, OnDestroy {

  public maturityDateOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    firstDayOfWeek: 'mo',
    closeAfterSelect: true,
  };

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate() + 1,
  };

  disbursementDateOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 120,
    maxYear: new Date().getFullYear(),
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: this.disableSinceDate,
  };
  isEmpty: boolean = false;

  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any = {};
  facilityTypes = [];
  result: Subject<any>;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onFacilityTypeChangeSub: Subscription = new Subscription();
  otherBankFacilityUpdateDTO: FpOtherBankUpdateDto = new FpOtherBankUpdateDto({});

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef,
    private cacheService: CacheService,
  ) {
  }

  ngOnInit() {

    this.facilityTypes = this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES);
    this.result = new BehaviorSubject(this.facilityTypes);

    if (!_.isEmpty(this.content.otherFacilityItem)) {
      this.otherBankFacilityUpdateDTO = new FpOtherBankUpdateDto(this.content.otherFacilityItem)
    } else {
      this.isEmpty = true;
      this.otherBankFacilityUpdateDTO = new FpOtherBankUpdateDto({});
    }

    this.formErrors = {
      bankName: {},
      branchName: {},
      facilityAmount: {},
      outstandingAmount: {},
      facilityType: {},
      disbursementDate: {},
      maturityDate: {},
      securities: {}
    };

    this.componentForm = this.formBuilder.group({
      bankName: [this.otherBankFacilityUpdateDTO.bankName, Validators.required],
      branchName: [this.otherBankFacilityUpdateDTO.branchName, Validators.required],
      facilityAmount: [this.otherBankFacilityUpdateDTO.facilityAmount, [Validators.required, NumberValidator.isNumber]],
      outstandingAmount: [this.otherBankFacilityUpdateDTO.outstandingAmount, [Validators.required, NumberValidator.isNumber]],
      facilityType: [this.otherBankFacilityUpdateDTO.facilityType, Validators.required],
      disbursementDate: [this.otherBankFacilityUpdateDTO.disbursementDate, Validators.required],
      maturityDate: [this.otherBankFacilityUpdateDTO.maturityDate, Validators.required],
      securities: [this.otherBankFacilityUpdateDTO.securities, Validators.required]
    });

    this.onFacilityTypeChangeSub = this.componentForm.controls.facilityType.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value))
      });

    this.onFacilityTypeChangeSub.unsubscribe();
    this.onFacilityTypeChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });
  }

  ngOnDestroy(): void {
    this.onFacilityTypeChangeSub.unsubscribe();
  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.facilityTypes.filter((item: any) => item.facilityTypeName.toLowerCase().includes(filterValue));
  }

  onAddOtherBankFacility($event) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let bankFacility = Object.assign({},
      {casCustomerID: this.content.casCustomerID},
      {facilityPaperID: this.content.facilityPaper.facilityPaperID},
      {
        casCustomerOtherBankFacilityDTOList: [Object.assign({},
          {
            fpCustomerOtherBankFacilityID: this.otherBankFacilityUpdateDTO.fpCustomerOtherBankFacilityID,
            casCustomerID: this.content.casCustomerID,
            bankName: this.componentForm.controls.bankName.value,
            branchName: this.componentForm.controls.branchName.value,
            facilityAmount: this.componentForm.controls.facilityAmount.value,
            outstandingAmount: this.componentForm.controls.outstandingAmount.value,
            facilityType: this.componentForm.controls.facilityType.value,
            disbursementDateStr: this.componentForm.controls.disbursementDate.value,
            maturityDateStr: this.componentForm.controls.maturityDate.value,
            securities: this.componentForm.controls.securities.value,
            status: 'ACT'
          }
        )]
      }
    );

    this.facilityPaperAddEditService.saveOtherBankDetails(bankFacility);
    this.mdbModalRef.hide();
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  onRemoveOtherBankFacility($event) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let bankFacility = Object.assign({},
      {casCustomerID: this.content.casCustomerID},
      {facilityPaperID: this.content.facilityPaper.facilityPaperID},
      {
        casCustomerOtherBankFacilityDTOList: [Object.assign({},
          {
            fpCustomerOtherBankFacilityID: this.otherBankFacilityUpdateDTO.fpCustomerOtherBankFacilityID,
            casCustomerID: this.content.casCustomerID,
            bankName: this.componentForm.controls.bankName.value,
            branchName: this.componentForm.controls.branchName.value,
            facilityAmount: this.componentForm.controls.facilityAmount.value,
            outstandingAmount: this.componentForm.controls.outstandingAmount.value,
            facilityType: this.componentForm.controls.facilityType.value,
            disbursementDateStr: this.componentForm.controls.disbursementDate.value,
            maturityDateStr: this.componentForm.controls.maturityDate.value,
            securities: this.componentForm.controls.securities.value,
            status: 'INA'
          }
        )]
      }
    );
    this.facilityPaperAddEditService.saveOtherBankDetails(bankFacility);
    this.mdbModalRef.hide();
  }
}
