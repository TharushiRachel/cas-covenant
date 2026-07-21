import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { FacilityPaperAddEditService } from "../../../../../../services/facility-paper-add-edit.service";
import { FacilitySecurityUpdateDto } from "../../../../../../dto/facility-security-update-dto";
import { MDBModalRef } from "ng-uikit-pro-standard";
import * as _ from "lodash";
import { Subject, Subscription } from "rxjs";
import { CurrencyPipe } from "@angular/common";
import { NumberValidator } from "../../../../../../../../../shared/validators/number.validator";
import { AppUtils } from "../../../../../../../../../shared/app.utils";
import { AlertService } from "../../../../../../../../../core/service/common/alert.service";
import { SETTINGS } from "../../../../../../../../../core/setting/commons.settings";
import { Constants } from "../../../../../../../../../core/setting/constants";
import { CurrencyService } from "../../../../../../../../../core/service/common/currency.service";
import * as moment from "moment";

@Component({
  selector: "app-fp-facility-data-security",
  templateUrl: "./fp-facility-data-security.component.html",
  styleUrls: ["./fp-facility-data-security.component.scss"],
})
export class FpFacilityDataSecurityComponent implements OnInit, OnDestroy {
  action: Subject<any> = new Subject<any>();
  isEmpty: boolean = false;
  forRemove: boolean = false;
  content: any = {};
  componentForm: FormGroup;
  onFormValueChangeSub = new Subscription();
  formErrors: any = {};
  facilitySecurityUpdateDTO: FacilitySecurityUpdateDto =
    new FacilitySecurityUpdateDto({});
  creditFacilityList: any[] = [];
  onFPFacilitiesChangeSub = new Subscription();
  updatedFacilityPaper: any = {};
  isCommonSecurity: boolean = false;
  facilitySecurities: any[] = [];
  currencyTypesOpt = Constants.currencyTypesOpt;
  facilityFacilitySecurityDTOS: FormArray;
  isCommittee: boolean = false;
  commentHistory: any[] = [];
  facilityId: number = 0;
  fcSecurityId: number = 0;

  optionsSelect = [
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];

  constructor(
    private formBuilder: FormBuilder,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    public mdbModalRef: MDBModalRef,
    private currencyPipe: CurrencyPipe,
    public currencyService: CurrencyService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    if (!_.isEmpty(this.content.securityItem)) {
      this.facilitySecurityUpdateDTO = new FacilitySecurityUpdateDto(
        this.content.securityItem
      );
      this.isEmpty = false;

      this.facilityId =
        this.content.securityItem.facilityDTOS &&
        this.content.securityItem.facilityDTOS.length > 0
          ? this.content.securityItem.facilityDTOS[0].facilityID
          : 0;
      this.fcSecurityId = this.content.securityItem.facilitySecurityID;
      if (this.content.facilityData) {
        this.setFaciltyComments(this.content.facilityData);
      }
    } else if (!_.isEmpty(this.content.facilityData)) {
      this.isEmpty = true;
    } else {
      this.isEmpty = true;
    }

    if (!_.isEmpty(this.content.allSecurityItems)) {
      _.sortBy(this.content.allSecurityItems, ["facilitySecurityID"]).forEach(
        (security) => {
          this.facilitySecurities.push({
            value: security.facilitySecurityID,
            label: security.securityCode,
          });
        }
      );
    }

    this.formErrors = {
      securityCode: {},
      cashAmount: {},
      securityDetail: {},
      securityAmount: {},
      securityCurrency: {},
      facilitySecurityType: {},
    };

    this.onFPFacilitiesChangeSub =
      this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
        (data: any) => {
          if (data) {
            this.updatedFacilityPaper = data;

            this.isCommittee =
              this.updatedFacilityPaper.isCommittee == Constants.yesNoConst.Y;
          }
          this.creditFacilityList = [];
          this.creditFacilityList =
            this.updatedFacilityPaper.facilityDTOList || [];
          this.creditFacilityList = _.sortBy(this.creditFacilityList, [
            "displayOrder",
          ]);
          this.createForm();
        }
      );

    this.onFormValueChangeSub.unsubscribe();

    this.onFormValueChangeSub = this.componentForm
      .get("facilitySecurityID")
      .valueChanges.subscribe((facilitySecurityID) => {
        if (facilitySecurityID) {
          this.content.allSecurityItems.forEach((facilitySecurity) => {
            if (facilitySecurity.facilitySecurityID == facilitySecurityID) {
              this.facilitySecurityUpdateDTO = new FacilitySecurityUpdateDto(
                facilitySecurity
              );
              this.componentForm
                .get("securityCode")
                .setValue(facilitySecurity.securityCode);
              this.componentForm
                .get("isCashSecurity")
                .setValue(facilitySecurity.isCashSecurity == "Y");
              this.componentForm
                .get("cashAmount")
                .setValue(
                  this.currencyPipe.transform(
                    this.isCommittee
                      ? this.getMillionValue(facilitySecurity.cashAmount)
                      : facilitySecurity.cashAmount,
                    "",
                    "",
                    "0.2-3"
                  )
                );
              this.componentForm
                .get("securityDetail")
                .setValue(facilitySecurity.securityDetail);
              this.componentForm
                .get("securityAmount")
                .setValue(
                  this.currencyPipe.transform(
                    this.isCommittee
                      ? this.getMillionValue(facilitySecurity.securityAmount)
                      : facilitySecurity.securityAmount,
                    "",
                    "",
                    "0.2-3"
                  )
                );
              this.componentForm
                .get("securityCurrency")
                .setValue(facilitySecurity.securityCurrency);
              this.removeThenRecreateFacilities();
              this.isEmpty = false;
            }
          });
        } else {
          this.facilitySecurityUpdateDTO = new FacilitySecurityUpdateDto({});
          this.componentForm.get("securityCode").reset("");
          this.componentForm.get("isCashSecurity").reset("");
          this.componentForm.get("cashAmount").reset("");
          this.componentForm.get("securityDetail").reset("");
          this.componentForm.get("securityAmount").reset("");
          this.componentForm
            .get("securityCurrency")
            .reset(this.currencyTypesOpt[0].value);
          let facilityFacilitySecurityFormArray = this.componentForm.get(
            "facilityFacilitySecurityDTOS"
          ) as FormArray;
          facilityFacilitySecurityFormArray.clear();
          this.removeThenRecreateFacilities();
          this.isEmpty = true;
        }
      });

    this.onFormValueChangeSub = this.componentForm
      .get("isCashSecurity")
      .valueChanges.subscribe((value: any) => {
        if (value) {
          this.componentForm.controls.cashAmount.setValidators([
            Validators.required,
            Validators.min(0.1),
          ]);
          this.componentForm.controls.cashAmount.reset();
        } else {
          this.componentForm.controls.cashAmount.setValidators(null);
          this.componentForm.controls.cashAmount.reset();
        }
      });

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe(
      (form) => {
        this.formErrors = AppUtils.getFormErrors(
          this.componentForm,
          this.formErrors
        );
      }
    );
  }

  createForm() {
    let isSelectingSecurityEnabled =
      !_.isEmpty(this.content.allSecurityItems) &&
      this.content.allSecurityItems.length > 0;
    this.componentForm = this.formBuilder.group({
      facilitySecurityID: [
        { value: "", disabled: !isSelectingSecurityEnabled },
      ],
      securityCode: [
        this.facilitySecurityUpdateDTO.securityCode,
        [Validators.required, Validators.maxLength(4000)],
      ],
      securityDetail: [
        this.facilitySecurityUpdateDTO.securityDetail,
        [Validators.required, Validators.maxLength(4000)],
      ],
      securityAmount: [
        this.currencyPipe.transform(
          this.isCommittee
            ? AppUtils.getMillionValue(
                this.facilitySecurityUpdateDTO.securityAmount
              )
            : this.facilitySecurityUpdateDTO.securityAmount,
          "",
          "",
          this.isCommittee ? "1.3-3" : "0.2-3"
        ),
        [
          Validators.required,
          NumberValidator.maxLengthOfNumber(18),
          NumberValidator.isCommaSeparatedValue,
          Validators.min(0),
        ],
      ],
      cashAmount: [
        this.currencyPipe.transform(
          this.isCommittee
            ? AppUtils.getMillionValue(
                this.facilitySecurityUpdateDTO.cashAmount
              )
            : this.facilitySecurityUpdateDTO.cashAmount,
          "",
          "",
          this.isCommittee ? "1.3-3" : "0.2-3"
        ),
      ],
      isCashSecurity: [
        this.facilitySecurityUpdateDTO.isCashSecurity
          ? this.facilitySecurityUpdateDTO.isCashSecurity == "Y"
          : null,
      ],
      securityCurrency: [
        this.facilitySecurityUpdateDTO.securityCurrency
          ? this.facilitySecurityUpdateDTO.securityCurrency
          : this.content.facilityData.facilityCurrency
          ? this.content.facilityData.facilityCurrency
          : this.currencyTypesOpt[0].value,
      ],
      facilityFacilitySecurityDTOS: this.formBuilder.array(
        this.createFacilityFacilitySecurityGroup()
      ),
    });

    if (this.facilitySecurityUpdateDTO.isCashSecurity == "Y") {
      this.componentForm.controls.cashAmount.setValidators([
        Validators.required,
        Validators.min(0.1),
      ]);
    }
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onFPFacilitiesChangeSub.unsubscribe();
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

  isDisabled(form) {
    return form.getRawValue().isAddedFacility;
  }

  isCashSecurity() {
    return this.componentForm.controls.isCashSecurity.value;
  }

  getCurrencyFormat(amount) {
    return this.isCommittee ? this.currencyPipe.transform(amount, "", "1.3-3") : this.currencyPipe.transform(amount, "", "", "");
  }

  setCurrencyFormatValue(control) {
    const amount = this.getValue(this.componentForm.getRawValue()[control]);
    this.componentForm.patchValue({
      [control]: this.isCommittee ? this.currencyPipe.transform(amount, "", "", "1.3-3") : this.currencyPipe.transform(amount, "", "", "0.2-3"),
    });
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  removeThenRecreateFacilities() {
    this.componentForm.removeControl("facilityFacilitySecurityDTOS");
    this.componentForm.addControl(
      "facilityFacilitySecurityDTOS",
      this.formBuilder.array(this.createFacilityFacilitySecurityGroup())
    );
  }

  createFacilityFacilitySecurityGroup() {
    let facilityFacilitySecurities = [];
    this.creditFacilityList.forEach((facility) => {
      let facilityFacilitySecurity = Object.assign(
        {},
        _.find(this.facilitySecurityUpdateDTO.facilityFacilitySecurityDTOS, {
          facilityID: facility.facilityID,
        })
      );
      facilityFacilitySecurities.push(
        this.createFacilitySecurity(facilityFacilitySecurity, facility)
      );
    });
    return facilityFacilitySecurities;
  }

  createFacilitySecurity(facilityFacilitySecurity, facility): FormGroup {
    return this.formBuilder.group({
      facilitySecuritySecurityID: [
        facilityFacilitySecurity.facilitySecuritySecurityID
          ? facilityFacilitySecurity.facilitySecuritySecurityID
          : null,
      ],
      facilityRefCode: [
        {
          value: ` ${facility.displayOrder} . ${
            facility.creditFacilityTemplateDTO.creditFacilityName
          } ( ${
            facility.facilityCurrency
          }  ${this.getFormattedThreeDecimalValues(
            this.getMillionValue(facility.facilityAmount)
          )}) Mn`,
          disabled: true,
        },
      ],
      isAddedFacility: [
        facilityFacilitySecurity.facilitySecuritySecurityID
          ? facilityFacilitySecurity.status == "ACT"
          : facility.facilityID == this.content.facilityData.facilityID,
      ],
      isCashSecurity: [facilityFacilitySecurity.isCashSecurity ? "Y" : "N"],
      facilitySecurityAmount: [
        facilityFacilitySecurity.facilitySecurityAmount
          ? this.getCurrencyFormat(
              facilityFacilitySecurity.facilitySecurityAmount
            )
          : null,
      ],
      status: [
        facilityFacilitySecurity.status
          ? facilityFacilitySecurity.status
          : Constants.statusConst.ACT,
      ],
      facilityID: [
        facilityFacilitySecurity.facilityID
          ? facilityFacilitySecurity.facilityID
          : facility.facilityID,
      ],
      facilitySecurityID: [this.facilitySecurityUpdateDTO.facilitySecurityID],
    });
  }

  saveUpdateFacilitySecurity() {
    let facilityFacilitySecurities: [] =
      this.componentForm.getRawValue().facilityFacilitySecurityDTOS;
    let facilityFacilitySecurityDtos = [];
    let selectedFacilities = 0;
    let facilityViceSecurityTotal = 0;

    facilityFacilitySecurities.forEach((security: any) => {
      if (security.isAddedFacility) {
        selectedFacilities += 1;
      }
      if (security.facilitySecurityAmount) {
        facilityViceSecurityTotal += parseFloat(
          this.isCommittee
            ? AppUtils.getMillionToRupeeValue(
                this.getValue(security.facilitySecurityAmount)
              )
            : this.getValue(security.facilitySecurityAmount)
        );
      }
      let facilitySecurityData = Object.assign(
        {},
        security,
        { isAddedFacility: security.isAddedFacility ? "Y" : "N" },
        { status: security.isAddedFacility ? "ACT" : "INA" },
        // {facilitySecurityAmount: this.getValue(security.facilitySecurityAmount)},
        {
          facilitySecurityAmount: this.componentForm.controls.isCashSecurity
            .value
            ? this.isCommittee
              ? AppUtils.getMillionToRupeeValue(
                  this.getValue(this.componentForm.controls.cashAmount.value)
                )
              : this.getValue(this.componentForm.controls.cashAmount.value)
            : this.isCommittee
            ? AppUtils.getMillionToRupeeValue(
                this.getValue(this.componentForm.controls.securityAmount.value)
              )
            : this.getValue(this.componentForm.controls.securityAmount.value),
        },
        {
          isCashSecurity: this.componentForm.controls.isCashSecurity.value
            ? "Y"
            : "N",
        }
      );
      facilityFacilitySecurityDtos.push(facilitySecurityData);
    });

    let data = Object.assign(
      {},
      {
        facilitySecurityID: this.facilitySecurityUpdateDTO.facilitySecurityID
          ? this.facilitySecurityUpdateDTO.facilitySecurityID
          : null,
        securityDetail: this.componentForm.controls.securityDetail.value,
        facilityPaperID: this.updatedFacilityPaper.facilityPaperID
          ? this.updatedFacilityPaper.facilityPaperID
          : null,
        securityCode: this.componentForm.controls.securityCode.value,
        isCashSecurity: this.componentForm.controls.isCashSecurity.value
          ? "Y"
          : "N",
        securityAmount: this.isCommittee
          ? AppUtils.getMillionToRupeeValue(
              this.getValue(this.componentForm.controls.securityAmount.value)
            )
          : this.getValue(this.componentForm.controls.securityAmount.value),
        cashAmount: this.isCommittee
          ? AppUtils.getMillionToRupeeValue(
              this.getValue(this.componentForm.controls.cashAmount.value)
            )
          : this.getValue(this.componentForm.controls.cashAmount.value),
        securityCurrency: this.getValue(
          this.componentForm.controls.securityCurrency.value
        ),
        status: "ACT",
        isCommonSecurity: selectedFacilities > 1 ? "Y" : "N",
        facilityFacilitySecurityDTOS: facilityFacilitySecurityDtos,
      }
    );

    if (
      parseFloat(this.getValue(this.componentForm.controls.cashAmount.value)) >
      parseFloat(
        this.getValue(this.componentForm.controls.securityAmount.value)
      )
    ) {
      this.alertService.showToaster(
        "Security Amount Exceeded!",
        SETTINGS.TOASTER_MESSAGES.warning,
        { timeOut: 5000 }
      );
    } else if (selectedFacilities == 0) {
      this.alertService.showToaster(
        "No Facilities Included",
        SETTINGS.TOASTER_MESSAGES.warning,
        { timeOut: 5000 }
      );
    } else {
      this.facilityPaperAddEditService.saveUpdateFacilitySecurity(data,this.updatedFacilityPaper);
      this.mdbModalRef.hide();
    }
  }

  getFormattedThreeDecimalValues(amount) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  getMillionValue(value: any) {
    return AppUtils.getMillionValue(value);
  }

  setFaciltyComments(facility: any) {
    var comments: any[] = facility.fusTraceList ? facility.fusTraceList : [];

    var filteredComments: any[] = comments.filter(
      (c: any) => c.flag == Constants.fusTraceFlag.FACSE
    );
    this.commentHistory =
      this.facilityPaperAddEditService.sortCommentHistory(filteredComments);
  }
}
