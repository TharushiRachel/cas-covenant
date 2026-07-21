import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {FacilityPaperAddEditService} from "../../../../../services/facility-paper-add-edit.service";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {Subscription} from "rxjs/Rx";
import * as _ from "lodash";
import {NumberValidator} from "../../../../../../../../shared/validators/number.validator";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {CurrencyPipe} from "@angular/common";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";
import {Constants} from "../../../../../../../../core/setting/constants";
import {PrivilegeService} from "../../../../../../../../core/service/authentication/privilege.service";

@Component({
  selector: 'app-fp-total-exposure-data',
  templateUrl: './fp-total-exposure-data.component.html',
  styleUrls: ['./fp-total-exposure-data.component.scss']
})
export class FpTotalExposureDataComponent implements OnInit, OnDestroy {

  facilityPaper: any = {};
  exposureData: any = {};
  onBaseFacilityPaperChangeSub = new Subscription();
  onCalculateFacilityPaperExposureChangeSub = new Subscription();
  onCalculateFacilityPaperExposureSubj = new Subscription();
  equalLoginUserAndAssignUser = false;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  yesNoConst = Constants.yesNoConst;
  hasPrivilege = false;
  componentForm: FormGroup;
  formErrors: any = {};
  onFormValueChangeSub = new Subscription();
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  constructor(private facilityPaperAddEditService: FacilityPaperAddEditService,
              private formBuilder: FormBuilder,
              private privilegeService: PrivilegeService,
              private currencyPipe: CurrencyPipe,
              private applicationService: ApplicationService,) {
  }

  ngOnInit() {
    this.hasPrivilege = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT);

    this.formErrors = {
      totalDirectExposurePrevious: {},
      totalDirectExposureNew: {},
      totalIndirectExposurePrevious: {},
      totalIndirectExposureNew: {},
      totalExposurePrevious: {},
      totalExposureNew: {},
      addTotalExposureToGroup: {},
      groupTotalDirectExposurePrevious: {},
      groupTotalDirectExposureNew: {},
      groupTotalIndirectExposurePrevious: {},
      groupTotalIndirectExposureNew: {},
      groupTotalExposurePrevious: {},
      groupTotalExposureNew: {},
    };

    this.onBaseFacilityPaperChangeSub = this.facilityPaperAddEditService.onBaseFacilityPaperChange
      .subscribe(baseFacility => {
        if (baseFacility) {
          this.facilityPaper = baseFacility;
          this.exposureData = this.facilityPaper;

          this.isEqualLoginAndAssignUser();
        }
      });

    this.componentForm = this.createForm();
    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
    });

    this.onCalculateFacilityPaperExposureChangeSub = this.facilityPaperAddEditService.onCalculateFacilityPaperExposureChange
      .subscribe((exposure : any) => {
        if (!_.isEmpty(exposure)) {
          if(this.facilityPaper.facilityPaperID == exposure.facilityPaperID) { 
            this.exposureData = Object.assign({}, this.exposureData, exposure);
            this.componentForm.setValue({
              totalDirectExposurePrevious: this.getCurrencyFormat(this.exposureData.totalDirectExposurePrevious),
              totalDirectExposureNew: this.getCurrencyFormat(this.exposureData.totalDirectExposureNew),
              totalIndirectExposurePrevious: this.getCurrencyFormat(this.exposureData.totalIndirectExposurePrevious),
              totalIndirectExposureNew: this.getCurrencyFormat(this.exposureData.totalIndirectExposureNew),
              totalExposurePrevious: this.getCurrencyFormat(this.exposureData.totalExposurePrevious),
              totalExposureNew: this.getCurrencyFormat(this.exposureData.totalExposureNew),
              addTotalExposureToGroup: 'Y',
              groupTotalDirectExposurePrevious: this.getCurrencyFormat(this.exposureData.groupTotalDirectExposurePrevious),
              groupTotalDirectExposureNew: this.getCurrencyFormat(this.exposureData.groupTotalDirectExposureNew),
              groupTotalIndirectExposurePrevious: this.getCurrencyFormat(this.exposureData.groupTotalIndirectExposurePrevious),
              groupTotalIndirectExposureNew: this.getCurrencyFormat(this.exposureData.groupTotalIndirectExposureNew),
              groupTotalExposurePrevious: this.getCurrencyFormat(this.exposureData.groupTotalExposurePrevious),
              groupTotalExposureNew: this.getCurrencyFormat(this.exposureData.groupTotalExposureNew),
            }, {
              onlySelf: true,
              emitEvent: true
            });
          }
        }

      });

    this.onCalculateFacilityPaperExposureSubj = this.facilityPaperAddEditService.onCalculateFacilityPaperExposureSubj
      .subscribe((response: any) => {
        
        this.facilityPaperAddEditService.onCalculateFacilityPaperExposureChange.next(response);
        this.exposureData = Object.assign({}, this.exposureData, response, {addTotalExposureToGroup: 'Y'});
        this.facilityPaperAddEditService.updateFacilityPaperExposure(this.exposureData);
      });

    this.isEqualLoginAndAssignUser();
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onBaseFacilityPaperChangeSub.unsubscribe();
    this.onCalculateFacilityPaperExposureChangeSub.unsubscribe();
    this.onCalculateFacilityPaperExposureSubj.unsubscribe();
  }

  recalculate() {
    let data = {
      facilityPaperID: this.facilityPaper.facilityPaperID,
      isCommittee: this.yesNoConst.N
    };
    this.facilityPaperAddEditService.calculateFacilityPaperExposure(data);
  }

  createForm() {
    let isDisabled = !this.isAbelToEdit();
    return this.formBuilder.group({
      totalDirectExposurePrevious: [{
        value: this.getCurrencyFormat(this.exposureData.totalDirectExposurePrevious),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      totalDirectExposureNew: [{
        value: this.getCurrencyFormat(this.exposureData.totalDirectExposureNew),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      totalIndirectExposurePrevious: [{
        value: this.getCurrencyFormat(this.exposureData.totalIndirectExposurePrevious),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      totalIndirectExposureNew: [{
        value: this.getCurrencyFormat(this.exposureData.totalIndirectExposureNew),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      totalExposurePrevious: [{
        value: this.getCurrencyFormat(this.exposureData.totalExposurePrevious),
        disabled: isDisabled
      },
        [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      totalExposureNew: [{
        value: this.getCurrencyFormat(this.exposureData.totalExposureNew),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      addTotalExposureToGroup: [{value: this.exposureData.addTotalExposureToGroup, disabled: isDisabled}, []],
      groupTotalDirectExposurePrevious: [{
        value: this.getCurrencyFormat(this.exposureData.groupTotalDirectExposurePrevious),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      groupTotalDirectExposureNew: [{
        value: this.getCurrencyFormat(this.exposureData.groupTotalDirectExposureNew),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      groupTotalIndirectExposurePrevious: [{
        value: this.getCurrencyFormat(this.exposureData.groupTotalIndirectExposurePrevious),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      groupTotalIndirectExposureNew: [{
        value: this.getCurrencyFormat(this.exposureData.groupTotalIndirectExposureNew),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      groupTotalExposurePrevious: [{
        value: this.getCurrencyFormat(this.exposureData.groupTotalExposurePrevious),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]],
      groupTotalExposureNew: [{
        value: this.getCurrencyFormat(this.exposureData.groupTotalExposureNew),
        disabled: isDisabled
      }, [NumberValidator.maxLengthOfNumber(20), NumberValidator.greaterThanOrEqualToZero]]
    })
  }

  getValue(amount, contorl) {
    if (isNaN(amount)) {
      let amountAsNumeric = amount.replace(/,/g, '');
      if (isNaN(amountAsNumeric)) {
        this.componentForm.get(contorl).setErrors(NumberValidator.greaterThanOrEqualToZero);
        this.formErrors[contorl].invalidInput = true;
      } else {
        this.componentForm.get(contorl).setErrors(null);
        this.formErrors[contorl].invalidInput = false;
      }
      return amountAsNumeric
    }
    return amount;
  }

  updateFacilityPaperExposure() {
    let facilityPaperData = Object.assign({}, this.exposureData, this.facilityPaper, this.componentForm.getRawValue());
    facilityPaperData.totalDirectExposurePrevious = this.getValue(facilityPaperData.totalDirectExposurePrevious, 'totalDirectExposurePrevious');
    facilityPaperData.totalDirectExposureNew = this.getValue(facilityPaperData.totalDirectExposureNew, 'totalDirectExposureNew');
    facilityPaperData.totalIndirectExposurePrevious = this.getValue(facilityPaperData.totalIndirectExposurePrevious, 'totalIndirectExposurePrevious');
    facilityPaperData.totalIndirectExposureNew = this.getValue(facilityPaperData.totalIndirectExposureNew, 'totalIndirectExposureNew');
    facilityPaperData.totalExposurePrevious = this.getValue(facilityPaperData.totalExposurePrevious, 'totalExposurePrevious');
    facilityPaperData.totalExposureNew = this.getValue(facilityPaperData.totalExposureNew, 'totalExposureNew');
    facilityPaperData.groupTotalDirectExposurePrevious = this.getValue(facilityPaperData.groupTotalDirectExposurePrevious, 'groupTotalDirectExposurePrevious');
    facilityPaperData.groupTotalDirectExposureNew = this.getValue(facilityPaperData.groupTotalDirectExposureNew, 'groupTotalDirectExposureNew');
    facilityPaperData.groupTotalIndirectExposurePrevious = this.getValue(facilityPaperData.groupTotalIndirectExposurePrevious, 'groupTotalIndirectExposurePrevious');
    facilityPaperData.groupTotalIndirectExposureNew = this.getValue(facilityPaperData.groupTotalIndirectExposureNew, 'groupTotalIndirectExposureNew');
    facilityPaperData.groupTotalExposurePrevious = this.getValue(facilityPaperData.groupTotalExposurePrevious, 'groupTotalExposurePrevious');
    facilityPaperData.groupTotalExposureNew = this.getValue(facilityPaperData.groupTotalExposureNew, 'groupTotalExposureNew');

    if (this.isValid()) {
      this.facilityPaperAddEditService.updateFacilityPaperExposure(facilityPaperData);
    }

  }

  setCurrencyFormatValue(control) {
    const amount = this.componentForm.getRawValue()[control] ? this.componentForm.getRawValue()[control].replace(/,/g, '') : '';
    this.componentForm.patchValue({
      [control]: this.currencyPipe.transform(amount, '', '')
    });
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '');
  }

  isValid = () => this.componentForm.valid;

  isEqualLoginAndAssignUser() {
    if (this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID()) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.APPROVED;
  }

  isRejected() {
    return this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.REJECTED;
  }

  isAbelToEdit() {
    return (this.facilityPaper.currentFacilityPaperStatus != this.facilityPaperStatusConst.APPROVED
      && this.facilityPaper.currentFacilityPaperStatus != this.facilityPaperStatusConst.REJECTED)
      && (
        (this.facilityPaper.createdBy == this.applicationService.getLoggedInUserUserName()
          && this.facilityPaper.currentFacilityPaperStatus == this.facilityPaperStatusConst.DRAFT
        )
        ||
        (this.facilityPaper.currentAssignUserID == this.applicationService.getLoggedInUserUserID()
          && this.hasPrivilege)
      );
  }

}
