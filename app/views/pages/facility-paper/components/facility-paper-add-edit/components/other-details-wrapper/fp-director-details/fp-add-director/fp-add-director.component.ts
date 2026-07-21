import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subject, Subscription} from "rxjs/Rx";
import {IMyDate, IMyOptions, MDBModalRef} from "ng-uikit-pro-standard";
import * as _ from "lodash";
import {FacilityPaperAddEditService} from "../../../../../../services/facility-paper-add-edit.service";
import {DateService} from "../../../../../../../../../core/service/application/date.service";
import {NumberValidator} from "../../../../../../../../../shared/validators/number.validator";
import {AppUtils} from "../../../../../../../../../shared/app.utils";
import {NicValidator} from "../../../../../../../../../shared/validators/nic.validator";

@Component({
  selector: 'app-fp-add-director',
  templateUrl: './fp-add-director.component.html',
  styleUrls: ['./fp-add-director.component.scss']
})
export class FpAddDirectorComponent implements OnInit, OnDestroy {


  heading: string;
  content: any;
  componentForm: FormGroup;
  shareholderComponentForm: FormGroup;
  formErrors: any;
  action: Subject<any> = new Subject<any>();
  onDirectorDetailUpdate: Subscription = new Subscription();
  updatedFacilityPaper: any = {};
  directorData: any = {};
  calculatedAge: number;
  shareholderData: any = {};
  isDirector: boolean = false;
  isShareholder: boolean = false;

  onFormDOBChangeSub: Subscription = new Subscription();

  directorShareholderList = [
    {value: 'DIRECTOR', label: 'DIRECTOR'},
    {value: 'SHAREHOLDER', label: 'SHAREHOLDER'}
  ];

  optionsCivilStatusSelect: any = [
    {value: 'MARRIED', label: 'Married'},
    {value: 'SINGLE', label: 'Single'},
    {value: 'UNKNOWN', label: 'Cannot Specify'},
  ];

  disableSinceDate: IMyDate = {
    year: new Date().getFullYear() - 18,
    month: new Date().getMonth() + 1,
    day: new Date().getDate(),
  };

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: 1950,
    maxYear: 2050
  };

  myDatePickerOptionsForBirthDay: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: new Date().getFullYear() - 120,
    maxYear: new Date().getFullYear(),
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
    disableSince: this.disableSinceDate,
  };

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private dateService: DateService,
    private formBuilder: FormBuilder,
    public  mdbModalRef: MDBModalRef
  ) {
    this.formErrors = {
      directorName: {},
      nic: {},
      dateOfBirthStr: {},
      shareHolderName: {},
      shareHolding: {},
    }
  }


  ngOnInit() {
    this.componentForm = this.createDirectorDetailForm();
    this.shareholderComponentForm = this.createShareHolderDetailForm();
    this.onFormDOBChangeSub = this.componentForm.controls['dateOfBirthStr'].valueChanges.subscribe(dob => {
      if (dob) {
        this.calculateAge(dob);
      }
    });

    this.onDirectorDetailUpdate.unsubscribe();
    this.onDirectorDetailUpdate = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

    if (!_.isEmpty(this.directorData)) {
      this.isShareholder = false;
      this.isDirector = true;
    }
    if (!_.isEmpty(this.shareholderData)){
      this.isShareholder = true;
      this.isDirector = false;
    }
  }

  ngOnDestroy(): void {
    this.onFormDOBChangeSub.unsubscribe();
    this.onDirectorDetailUpdate.unsubscribe();
  }

  createDirectorDetailForm() {
    this.directorData = this.content.directorData;
    let status = this.directorData.status || 'ACT';

    if (this.directorData.dateOfBirthStr) {
      this.calculateAge(this.directorData.dateOfBirthStr);
    }

    return this.formBuilder.group({
      directorName: [this.directorData.directorName, [Validators.required]],
      fullName: [this.directorData.fullName],
      address: [this.directorData.address],
      civilStatus: [this.directorData.civilStatus],
      nic: [this.directorData.nic, [NicValidator.isValidNICInput]],
      shareHolding: [this.directorData.shareHolding, [Validators.max(100), NumberValidator.isPercentageValue]],
      dateOfBirthStr: [this.directorData.dateOfBirthStr],
      status: [status, [Validators.required]]
    })
  }

  createShareHolderDetailForm() {
    this.shareholderData = this.content.shareholderData;

    let status = this.shareholderData.status || 'ACT';

    return this.formBuilder.group({
      shareHolderName: [this.shareholderData.shareHolderName, [Validators.required]],
      shareHolding: [this.shareholderData.shareHolding, [Validators.max(100), NumberValidator.isPercentageValue, Validators.required]],
      status: [status, [Validators.required]]
    })
  }

  calculateAge(dob: string) {
    this.calculatedAge = this.dateService.getDateDifference(dob, 'years');
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  onAdd() {
    let directorStatus = this.content.directorData.status || 'ACT';
    if (this.content.facilityPaper) {
      let fpDirector = Object.assign({},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {
          fpDirectorDetailDTOList: [Object.assign({},
            this.content.directorData, {
              directorName: this.componentForm.controls.directorName.value,
              fullName: this.componentForm.controls.fullName.value,
              address: this.componentForm.controls.address.value,
              civilStatus: this.componentForm.controls.civilStatus.value,
              nic: this.componentForm.controls.nic.value,
              shareHolding: this.componentForm.controls.shareHolding.value,
              dateOfBirthStr: this.componentForm.controls.dateOfBirthStr.value,
              status: 'ACT'
            })]
        });

      this.action.next(fpDirector);
      this.facilityPaperAddEditService.saveFpDirectordetails(fpDirector);
      this.mdbModalRef.hide();
    }
  }

  isNewDirector() {
    return _.isEmpty(this.directorData);
  }

  isNewShareholder() {
    return _.isEmpty(this.shareholderData);
  }

  onRemoveDirector() {
    if (this.content.facilityPaper) {
      let fpDirector = Object.assign({},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {
          fpDirectorDetailDTOList: [Object.assign({},
            this.content.directorData, {
              directorName: this.componentForm.controls.directorName.value,
              fullName: this.componentForm.controls.fullName.value,
              address: this.componentForm.controls.address.value,
              civilStatus: this.componentForm.controls.civilStatus.value,
              dateOfBirthStr: this.componentForm.controls.dateOfBirthStr.value,
              status: 'INA'
            })]
        });

      this.action.next(fpDirector);
      this.facilityPaperAddEditService.saveFpDirectordetails(fpDirector);
      this.mdbModalRef.hide();
    }
  }

  onChangeDirectorShareholder($event) {

    if ($event.value === this.directorShareholderList[1].value) {
      this.isShareholder = true;
      this.isDirector = false;
    } else if ($event.value === this.directorShareholderList[0].value) {
      this.isShareholder = false;
      this.isDirector = true;
    } else {
      this.isShareholder = false;
      this.isDirector = false;
    }
  }

  onAddShareholder() {
    if (this.content.facilityPaper) {
      let fpShareholder = Object.assign({},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {
          fpShareHolderDetailDTOList: [Object.assign({},
            this.content.shareholderData, {
              shareHolderName: this.shareholderComponentForm.controls.shareHolderName.value,
              shareHolding: this.shareholderComponentForm.controls.shareHolding.value,
              status: 'ACT'
            })]
        });

      this.action.next(fpShareholder);
      this.facilityPaperAddEditService.saveFpShareholderdetails(fpShareholder);
      this.mdbModalRef.hide();
    }
  }

  onRemoveShareholder() {
    if (this.content.facilityPaper) {
      let fpShareholder = Object.assign({},
        {facilityPaperID: this.content.facilityPaper.facilityPaperID},
        {
          fpShareHolderDetailDTOList: [Object.assign({},
            this.content.shareholderData, {
              shareHolderName: this.shareholderComponentForm.controls.shareHolderName.value,
              shareHolding: this.shareholderComponentForm.controls.shareHolding.value,
              status: 'INA'
            })]
        });

      this.action.next(fpShareholder);
      this.facilityPaperAddEditService.saveFpShareholderdetails(fpShareholder);
      this.mdbModalRef.hide();
    }
  }
}
