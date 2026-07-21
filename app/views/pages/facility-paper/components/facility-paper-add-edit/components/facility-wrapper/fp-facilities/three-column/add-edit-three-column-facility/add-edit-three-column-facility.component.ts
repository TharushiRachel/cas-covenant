import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { Constants } from "../../../../../../../../../../core/setting/constants";
import { BehaviorSubject, Subject, Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../../../../services/facility-paper-add-edit.service";
import { CacheService } from "../../../../../../../../../../core/service/data/cache.service";
import { AlertService } from "../../../../../../../../../../core/service/common/alert.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { CurrencyPipe } from "@angular/common";
import { MasterDataService } from "../../../../../../../../../../core/service/data/master-data.service";
import { AppUtils } from "../../../../../../../../../../shared/app.utils";
import { NumberValidator } from "../../../../../../../../../../shared/validators/number.validator";
import * as _ from "lodash";
import { SETTINGS } from "../../../../../../../../../../core/setting/commons.settings";
import { CustomerLimitsOutstandingDataComponent } from "../../finacle-data/customer-limits-outstanding-data/customer-limits-outstanding-data.component";
import { FinacleDataStructureComponent } from "../../finacle-data/customer-limits-outstanding-data/finacle-data-structure/finacle-data-structure.component";
import * as moment from "moment";

@Component({
  selector: "app-add-edit-three-column-facility",
  templateUrl: "./add-edit-three-column-facility.component.html",
  styleUrls: ["./add-edit-three-column-facility.component.scss"],
})
export class AddEditThreeColumnFacilityComponent implements OnInit, OnDestroy {
  @ViewChild(FinacleDataStructureComponent, { static: true })
  finacleDataStructureComponent!: FinacleDataStructureComponent;
  currencyTypesOpt = Constants.currencyTypesOpt;

  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any;
  facility: any = {};
  facilityPaper: any = {};

  facilityTypeList: any[] = [];
  creditFacilityGroups: any[] = [];
  creditFacilityGroupWiseTemplateMap = {};
  creditFacilityTemplateList: any[] = [];
  creditFacilityTemplateOptionList: any[] = [];
  creditFacilityInterestRates: any[] = [];
  creditFacilityInterestRateMap = {};
  purposeOfAdvancedList: any[] = [];
  purposeOfAdvancedOptionList: any = [];
  purposeOfAdvancedMap: any = {};
  parentFacility: any = {};

  sectorList: any[] = [];
  subSectorList: any[] = [];
  sectorOptionList: any[] = [];
  subSectorOptionList: any[] = [];
  cashFlowGenerationSectorList: any[] = [];

  subSectorMap: any = {};
  selectedSubSectorMap: any = {};
  creditFacilityList: any[] = [];
  creditFacilityOptionList: any[] = [];

  yesNo = Constants.yesNo;
  currencyTypesConst = Constants.currencyTypesConst;
  fpInterestList: any[] = [];
  fpVitalInfoDataDTOList: any[] = [];
  fpCftOtherFacilityInfoDTOList: any[] = [];
  vitalInfoContolNames: any[] = [];
  otherInfoControlNames: any[] = [];
  editField: string;
  isValueUpdated: boolean = false;
  showCondition: boolean = false;
  showPurpose: boolean = false;
  showRemark: boolean = false;
  showRepayment: boolean = false;
  isFacilityOutstandingAmountValidationEnabled: boolean = false;

  selectedFacilityGroupId: number = 0;
  selectedFacilityTempId: number = 0;

  tableColumnsForFacilityInterestRate = [
    "Rate Name",
    "Rate Code",
    "Value (%)",
    "Comment",
  ];

  action: Subject<any> = new Subject<any>();
  onFPFacilityChangeSub = new Subscription();
  onFormValueChangeSub = new Subscription();
  onFormChangeSub = new Subscription();
  onFormCFTChangeSub = new Subscription();
  isNewValueChangeSubs = new Subscription();
  modalRef: MDBModalRef;
  selectedLoanData: any;
  isOpendLoanLimiModal: boolean = false;
  accountNumberOfFacility: string | null;
  finacaleData: any;

  selectedTabIndex: any;
  facilityGroupChangeSubs = new Subscription();

  facilityId: number = 0;
  commentHistory: any[] = [];
  isSecurity: boolean = false;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private cacheService: CacheService,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    public mdbModalRef: MDBModalRef,
    private currencyPipe: CurrencyPipe,
    private masterDataService: MasterDataService,
    private mdbModalService: MDBModalService,
    private toastr: AlertService
  ) {
    this.formErrors = {
      creditFacilityTypeID: {},
      creditFacilityTemplateID: {},
      facilityCurrency: {},
      disbursementAccNumber: {},
      facilityAmount: {},
      existingAmount: {},
      originalAmount: {},
      outstandingAmount: {},
      sectorID: {},
      subSectorID: {},
      purposeOfAdvance: {},
      cashFlowGenerationSectorID: {},
      parentFacility: {},
      purpose: {},
      isOneOff: {},
      repayment: {},
      facilityRefCode: {},
      condition: {},
      isNew: {},
      remark: {},
      status: {},
      exchangeRate: {},
    };
    this.accountNumberOfFacility = null;
  }

  isNewFacility() {
    return _.isEmpty(this.facility);
  }

  ngOnInit() {
    this.facility = this.content.facility;
    this.facilityPaper = this.content.facilityPaper;
    this.creditFacilityList = this.content.creditFacilityList;
    this.selectedTabIndex = this.content.selectedTabIndex;
    this.isFacilityOutstandingAmountValidationEnabled =
      this.masterDataService.getSystemParameter(
        Constants.systemParamKey.FACILITY_OUTSTANDING_AMOUNT_VALIDATION_ENABLED
      );

    this.onFPFacilityChangeSub =
      this.facilityPaperAddEditService.onFPFacilityChange.subscribe(
        (data: any) => {
          if (data) {
            this.mdbModalRef.hide();
          }
        }
      );

    this.facilityTypeList =
      this.cacheService.getData(
        Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES
      ) || [];
    _.forEach(this.facilityTypeList, (cfType) => {
      if (
        cfType.status == "ACT" ||
        this.facility.creditFacilityTypeID == cfType.creditFacilityTypeID
      )
        this.creditFacilityGroups.push({
          value: cfType.creditFacilityTypeID,
          label: cfType.facilityTypeName,
        });
    });

    this.creditFacilityTemplateList =
      this.cacheService.getData(
        Constants.masterDataKey.CAS_CREDIT_FACILITY_TEMPLATES
      ) || [];
    _.forEach(this.creditFacilityTemplateList, (cfTemplate) => {
      if (
        cfTemplate.status == "ACT" ||
        this.facility.creditFacilityTemplateID ==
          cfTemplate.creditFacilityTemplateID
      ) {
        if (
          this.creditFacilityGroupWiseTemplateMap[
            cfTemplate.creditFacilityTypeID
          ] == undefined
        ) {
          this.creditFacilityGroupWiseTemplateMap[
            cfTemplate.creditFacilityTypeID
          ] = [];
        }

        this.creditFacilityGroupWiseTemplateMap[
          cfTemplate.creditFacilityTypeID
        ] = [
          ...this.creditFacilityGroupWiseTemplateMap[
            cfTemplate.creditFacilityTypeID
          ],
          cfTemplate,
        ];
      }
    });

    this.creditFacilityTemplateOptionList = [];
    let templateList =
      this.creditFacilityGroupWiseTemplateMap[
        this.facility.creditFacilityTypeID
      ];

    _.forEach(templateList, (cfTemplate) => {
      this.creditFacilityTemplateOptionList.push({
        value: cfTemplate.creditFacilityTemplateID,
        label: cfTemplate.creditFacilityName,
      });
    });

    let creditFacilityGroupCopy = _.cloneDeep(this.creditFacilityGroups);
    this.creditFacilityGroups.forEach((creditFacilityGroup) => {
      let templateList =
        this.creditFacilityGroupWiseTemplateMap[creditFacilityGroup.value];

      if (!templateList || templateList.length === 0) {
        _.remove(
          creditFacilityGroupCopy,
          (i) => i.value === creditFacilityGroup.value
        );
      }
    });

    this.creditFacilityGroups = creditFacilityGroupCopy;

    this.sectorList =
      this.cacheService.getData(Constants.masterDataKey.CAS_SECTOR_DATA) || [];
    _.forEach(this.sectorList, (sectorData) => {
      this.sectorOptionList.push({
        value: sectorData.sectorID,
        label: sectorData.referenceCode + "-" + sectorData.referenceDescription,
      });
    });
    if (
      this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA)
    ) {
      this.subSectorList =
        this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA)
          .subSectorDTOList || [];
      this.subSectorMap =
        this.cacheService.getData(Constants.masterDataKey.CAS_SUB_SECTOR_DATA)
          .sectorWiseSubSectorMap || {};
    }

    _.forEach(this.subSectorList, (data) => {
      this.subSectorOptionList.push({
        value: data.subSectorID,
        label: data.referenceDescription,
      });
    });

    this.cashFlowGenerationSectorList = this.subSectorOptionList;

    this.purposeOfAdvancedList =
      this.cacheService.getData(
        Constants.masterDataKey.CAS_PURPOSE_OF_ADVANCED
      ) || [];
    _.forEach(this.purposeOfAdvancedList, (pusposeOfAdvanced) => {
      this.purposeOfAdvancedOptionList.push({
        value: pusposeOfAdvanced.referenceCode,
        label:
          pusposeOfAdvanced.referenceCode +
          "-" +
          pusposeOfAdvanced.referenceDescription,
      });
      this.purposeOfAdvancedMap[pusposeOfAdvanced.referenceCode] =
        pusposeOfAdvanced;
    });

    this.creditFacilityInterestRates =
      this.cacheService.getData(
        Constants.masterDataKey.CAS_CREDIT_FACILITY_INTEREST_RATES
      ) || [];
    _.forEach(this.creditFacilityInterestRates, (cftInterestRate) => {
      this.creditFacilityInterestRateMap[cftInterestRate.cftInterestRateID] =
        cftInterestRate.rateName;
    });
    this.fpInterestList =
      _.cloneDeep(this.facility.facilityInterestRateList) || [];
    this.fpVitalInfoDataDTOList =
      _.cloneDeep(this.facility.facilityVitalInfoDataDTOList) || [];
    this.fpCftOtherFacilityInfoDTOList =
      _.cloneDeep(this.facility.facilityOtherInfoDataList) || [];

    _.forEach(this.creditFacilityList, (data: any, index: number) => {
      if (data.facilityID != this.facility.facilityID) {
        var label: string =
          (index + 1).toString() +
          ". " +
          data.creditFacilityTemplateDTO.creditFacilityName +
          (data.facilityRefCode ? " - " + data.facilityRefCode : "");

        this.creditFacilityOptionList.push({
          value: data.facilityID,
          label: label,
        });
      }
    });

    this.componentForm = this.createForm();

    this.onFormChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.subSectorOptionList = [];
      let subSectorList = this.subSectorMap[form.sectorID] || [];
      _.forEach(subSectorList, (data) => {
        this.subSectorOptionList.push({
          value: data.subSectorID,
          label: data.referenceDescription,
        });
      });

      this.creditFacilityTemplateOptionList = [];
      let creditFacilityTypeID = form.creditFacilityTypeID
        ? form.creditFacilityTypeID
        : this.facility.creditFacilityTypeID;
      let templateList =
        this.creditFacilityGroupWiseTemplateMap[creditFacilityTypeID];
      _.forEach(templateList, (cfTemplate) => {
        this.creditFacilityTemplateOptionList.push({
          value: cfTemplate.creditFacilityTemplateID,
          label: cfTemplate.creditFacilityName,
        });
      });

      this.formErrors = AppUtils.getFormErrors(
        this.componentForm,
        this.formErrors
      );
    });

    this.onFormChangeSub = this.componentForm
      .get("sectorID")
      .valueChanges.subscribe((sectorID) => {
        let subSectorList = this.subSectorMap[sectorID] || [];
        if (sectorID && subSectorList && subSectorList.length > 0) {
          this.componentForm
            .get("subSectorID")
            .setValue(subSectorList[0].subSectorID);
        }
      });

    this.onFormChangeSub = this.componentForm
      .get("creditFacilityTypeID")
      .valueChanges.subscribe((creditFacilityTypeID) => {
        this.selectedFacilityGroupId = creditFacilityTypeID
          ? creditFacilityTypeID
          : 0;

        let templateList =
          this.creditFacilityGroupWiseTemplateMap[creditFacilityTypeID] || [];
        if (creditFacilityTypeID && templateList && templateList.length > 0) {
          this.componentForm
            .get("creditFacilityTemplateID")
            .setValue(templateList[0].creditFacilityTemplateID);
        }
      });

    this.isNewValueChangeSubs =
      this.componentForm.controls.isNew.valueChanges.subscribe(
        (isNew: boolean) => {
          if (isNew) {
            this.componentForm.controls.sectorID.setValidators([
              Validators.required,
            ]);
            this.componentForm.controls.subSectorID.setValidators([
              Validators.required,
            ]);
            this.componentForm.controls.cashFlowGenerationSectorID.setValidators(
              [Validators.required]
            );
            this.componentForm.controls.purposeOfAdvance.setValidators([
              Validators.required,
            ]);

            this.componentForm.controls.existingAmount.clearValidators();
            this.componentForm.controls.outstandingAmount.clearValidators();
          } else {
            this.componentForm.controls.sectorID.clearValidators();
            this.componentForm.controls.subSectorID.clearValidators();
            this.componentForm.controls.cashFlowGenerationSectorID.clearValidators();
            this.componentForm.controls.purposeOfAdvance.clearValidators();

            this.componentForm.controls.outstandingAmount.setValidators([
              Validators.required,
              NumberValidator.maxLengthOfNumber(18),
            ]);
            this.componentForm.controls.existingAmount.setValidators([
              Validators.required,
              NumberValidator.maxLengthOfNumber(18),
            ]);
          }

          this.componentForm.controls.sectorID.updateValueAndValidity({
            onlySelf: false,
            emitEvent: true,
          });
          this.componentForm.controls.subSectorID.updateValueAndValidity({
            onlySelf: false,
            emitEvent: true,
          });
          this.componentForm.controls.cashFlowGenerationSectorID.updateValueAndValidity(
            {
              onlySelf: false,
              emitEvent: true,
            }
          );
          this.componentForm.controls.purposeOfAdvance.updateValueAndValidity({
            onlySelf: false,
            emitEvent: true,
          });

          this.componentForm.controls.outstandingAmount.updateValueAndValidity({
            onlySelf: false,
            emitEvent: true,
          });
        }
      );

    this.onFormCFTChangeSub = this.componentForm.controls[
      "creditFacilityTemplateID"
    ].valueChanges.subscribe((creditFacilityTemplateID) => {
      this.selectedFacilityTempId = creditFacilityTemplateID
        ? creditFacilityTemplateID
        : 0;

      this.componentForm = this.createDynamicFormFields(
        creditFacilityTemplateID,
        true
      );
    });

    this.onFormChangeSub =
      this.componentForm.controls.enhancement.valueChanges.subscribe(
        (enhancement) => {
          if (enhancement) {
            this.componentForm.get("reduction").setValue(false);
          }
        }
      );

    this.onFormChangeSub =
      this.componentForm.controls.reduction.valueChanges.subscribe(
        (reduction) => {
          if (reduction) {
            this.componentForm.get("enhancement").setValue(false);
          }
        }
      );

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe(
      (form) => {
        this.formErrors = AppUtils.getFormErrors(
          this.componentForm,
          this.formErrors
        );
      }
    );

    // const loanLimitSub = this.facilityPaperAddEditService.loanLimitsList.subscribe(data=>{
    //   this.finacleData = data;
    // })
    this.accountNumberOfFacility = this.facility.loanLimitId;

    this.facilityId = this.facility.facilityID;
    if (this.facility) {
      this.setFaciltyComments(this.facility);
    }
  }

  ngOnDestroy(): void {
    this.onFPFacilityChangeSub.unsubscribe();
    this.onFormChangeSub.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
    this.onFormCFTChangeSub.unsubscribe();
    this.isNewValueChangeSubs.unsubscribe();
  }

  createForm() {
    this.facility = this.content.facility;
    let status = this.facility.status || "ACT";

    let purposeOfAdvanced: any = {};
    if (this.facility && this.facility.facilityPurposeOfAdvanceList) {
      purposeOfAdvanced = this.facility.facilityPurposeOfAdvanceList[0] || {};
    }

    let isNew = this.facility.isNew == "Y";
    let isAnnual = this.facility.isAnnual == Constants.yesNoConst.Y;
    let isAdditional = this.facility.isAdditional == Constants.yesNoConst.Y;
    let isTermsAmended = this.facility.isTermsAmended == Constants.yesNoConst.Y;
    let isReStructure = this.facility.isReStructure == Constants.yesNoConst.Y;
    let isReSchedule = this.facility.isReSchedule == Constants.yesNoConst.Y;

    let controlConfig = {
      creditFacilityTypeID: [
        {
          value: this.facility.creditFacilityTypeID,
          disabled: !!this.facility.facilityID,
        },
        [Validators.required],
      ],
      creditFacilityTemplateID: [
        {
          value: this.facility.creditFacilityTemplateID,
          disabled: !!this.facility.facilityID,
        },
        [Validators.required],
      ],
      parentFacilityID: [this.facility.parentFacilityID],
      facilityRefCode: [
        this.facility.facilityRefCode,
        [Validators.required, Validators.maxLength(50)],
      ],
      isNew: [isNew, []],
      isAnnual: [isAnnual, []],
      isAdditional: [isAdditional, []],
      isTermsAmended: [isTermsAmended, []],
      isReStructure: [isReStructure, []],
      isReSchedule: [isReSchedule, []],
      directFacility: [this.facility.directFacility == "Y", []],
      isOneOff: [this.facility.isOneOff == "Y", []],
      seriesOfLoans: [this.facility.seriesOfLoans == "Y", []],
      revolving: [this.facility.revolving == "Y", []],
      reduction: [this.facility.reduction == "Y", []],
      enhancement: [this.facility.enhancement == "Y", []],
      facilityCurrency: [
        this.facility.facilityCurrency
          ? this.facility.facilityCurrency
          : this.currencyTypesConst.LKR,
        Validators.required,
      ],
      exchangeRate: [
        this.getCurrencyFormat(
          this.facility.exchangeRate ? this.facility.exchangeRate : 0
        ),
        [
          NumberValidator.maxLengthOfNumber(18),
          NumberValidator.isCommaSeparatedValue,
        ],
      ],
      facilityAmount: [
        this.getCurrencyFormat(
          this.facility.facilityAmount
            ? AppUtils.getMillionValue(this.facility.facilityAmount)
            : 0
        ),
        [
          Validators.required,
          NumberValidator.maxLengthOfNumber(18),
          NumberValidator.isCommaSeparatedValue,
        ],
      ],
      existingAmount: [
        this.getCurrencyFormat(
          this.facility.existingAmount
            ? AppUtils.getMillionValue(this.facility.existingAmount)
            : 0
        ),
        [
          Validators.required,
          NumberValidator.maxLengthOfNumber(18),
          NumberValidator.isCommaSeparatedValue,
        ],
      ],
      originalAmount: [
        this.getCurrencyFormat(
          this.facility.originalAmount
            ? AppUtils.getMillionValue(this.facility.originalAmount)
            : 0
        ),
        [
          Validators.required,
          NumberValidator.maxLengthOfNumber(18),
          NumberValidator.isCommaSeparatedValue,
        ],
      ],
      outstandingAmount: [
        this.getCurrencyFormat(
          this.facility.outstandingAmount
            ? AppUtils.getMillionValue(this.facility.outstandingAmount)
            : 0
        ),
        [
          !isNew ? Validators.required : NumberValidator.maxLengthOfNumber(18),
          NumberValidator.maxLengthOfNumber(18),
          NumberValidator.isCommaSeparatedValue,
        ],
      ],
      disbursementAccNumber: [
        this.facility.disbursementAccNumber,
        [Validators.maxLength(100), NumberValidator.isCommaSeparatedValue],
      ],
      sectorID: [this.facility.sectorID],
      subSectorID: [this.facility.subSectorID],
      cashFlowGenerationSectorID: [this.facility.cashFlowGenerationSectorID],
      purposeOfAdvance: [this.facility.purposeOfAdvance],
      status: [status, [Validators.required]],
    };

    if (
      this.facility &&
      !_.isEmpty(this.facility.facilityVitalInfoDataDTOList)
    ) {
      let vitalInfoDataControlConfig = {};
      _.forEach(this.facility.facilityVitalInfoDataDTOList, (vitalInfoData) => {
        let isMandatory =
          Constants.yesNoConst.Y == vitalInfoData.mandatory
            ? [Validators.required]
            : [];
        vitalInfoDataControlConfig[
          "vitalInfo-" + vitalInfoData.cftVitalInfoID
        ] = [vitalInfoData.vitalInfoData, [...isMandatory]];
      });

      controlConfig = Object.assign(
        {},
        controlConfig,
        vitalInfoDataControlConfig
      );
    }

    this.componentForm = this.formBuilder.group(controlConfig);
    this.componentForm = this.createDynamicFormFields(
      this.facility.creditFacilityTemplateID,
      false
    );

    return this.componentForm;
  }

  updateList(id: number, property: string, event: any) {
    const newValue = _.toNumber(event.target.textContent);
    const preValue = this.fpInterestList[id][property];
    this.isValueUpdated = false;

    if (_.isFinite(newValue) && preValue != newValue) {
      this.fpInterestList[id][property] = newValue;
      this.isValueUpdated = true;

      //update comment
      // var newCmnt: string = newValue.toString() + "% payable";
      // this.fpInterestList[id]["userComment"] = newCmnt;
    }
  }

  updateUserComment(id: number, property: string, event: any) {
    const newValue = event.target.value;
    this.fpInterestList[id][property] = newValue;
    this.isValueUpdated = true;
  }

  changeValue(id: number, property: string, event: any) {
    this.editField = event.target.textContent;
  }

  getFormRawData() {
    let rawData = this.componentForm.getRawValue();

    if (!_.isEmpty(this.fpVitalInfoDataDTOList)) {
      _.map(this.fpVitalInfoDataDTOList, (vitalInfoData) => {
        vitalInfoData.vitalInfoData =
          rawData["vitalInfo-" + vitalInfoData.cftVitalInfoID];
        delete rawData["vitalInfo-" + vitalInfoData.cftVitalInfoID];
      });
    }

    return rawData;
  }

  setCurrencyFormatValue(control) {
    const amount = this.getValue(this.getFormRawData()[control]);
    this.componentForm.patchValue({
      [control]: this.currencyPipe.transform(amount, "", "", "1.3-3"),
    });
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, "", "", "1.3-3");
  }

  onAddEdit() {
    let facilityPaperList = this.facilityPaper.facilityDTOList || [];
    let displayOrder =
      this.facility.displayOrder || facilityPaperList.length + 1;
    let formData = this.getFormRawData();
    let fpFacility = Object.assign(
      {},
      this.facility,
      { displayOrder: displayOrder },
      formData,
      { facilityPaperID: this.content.facilityPaper.facilityPaperID }
    );

    fpFacility.exchangeRate = this.getValue(formData.exchangeRate)
      ? this.getValue(formData.exchangeRate)
      : 0;

    fpFacility.facilityAmount = this.getValue(formData.facilityAmount)
      ? AppUtils.getMillionToRupeeValue(this.getValue(formData.facilityAmount))
      : 0;

    fpFacility.originalAmount = this.getValue(formData.originalAmount)
      ? AppUtils.getMillionToRupeeValue(this.getValue(formData.originalAmount))
      : 0;

    if (this.markedAsNewFacility()) {
      fpFacility.outstandingAmount = 0;
      fpFacility.existingAmount = 0;
    } else {
      fpFacility.outstandingAmount = this.getValue(formData.outstandingAmount)
        ? AppUtils.getMillionToRupeeValue(
            this.getValue(formData.outstandingAmount)
          )
        : 0;

      fpFacility.existingAmount = this.getValue(formData.existingAmount)
        ? AppUtils.getMillionToRupeeValue(
            this.getValue(formData.existingAmount)
          )
        : 0;

      if (this.isFacilityOutstandingAmountValidationEnabled) {
        if (
          parseFloat(fpFacility.outstandingAmount) <
          parseFloat(fpFacility.facilityAmount)
        ) {
          this.alertService.showToaster(
            "Outstanding Amount should be lesser than the Previous Amount.",
            SETTINGS.TOASTER_MESSAGES.warning
          );
          return;
        }
      }

      if (formData.reduction) {
        if (fpFacility.existingAmount < fpFacility.facilityAmount) {
          this.alertService.showToaster(
            "Proposed Amount should be lesser than the Existing Amount.",
            SETTINGS.TOASTER_MESSAGES.warning
          );
          return;
        }
      }

      if (formData.enhancement) {
        if (fpFacility.existingAmount > fpFacility.facilityAmount) {
          this.alertService.showToaster(
            "Proposed Amount should be greater than or equal to Existing Amount.",
            SETTINGS.TOASTER_MESSAGES.warning
          );
          return;
        }
      }
    }

    fpFacility.isNew = formData.isNew ? "Y" : "N";
    fpFacility.isAnnual = formData.isAnnual ? "Y" : "N";
    fpFacility.isAdditional = formData.isAdditional ? "Y" : "N";
    fpFacility.isTermsAmended = formData.isTermsAmended ? "Y" : "N";
    fpFacility.isReStructure = formData.isReStructure ? "Y" : "N";
    fpFacility.isReSchedule = formData.isReSchedule ? "Y" : "N";
    fpFacility.isOneOff = formData.isOneOff ? "Y" : "N";
    fpFacility.directFacility = formData.directFacility ? "Y" : "N";
    fpFacility.seriesOfLoans = formData.seriesOfLoans ? "Y" : "N";
    fpFacility.revolving = formData.revolving ? "Y" : "N";
    fpFacility.reduction = formData.reduction ? "Y" : "N";
    fpFacility.enhancement = formData.enhancement ? "Y" : "N";
    fpFacility.isCommittee = this.content.facilityPaper.isCommittee;

    fpFacility.facilityInterestRateList = this.fpInterestList;
    fpFacility.facilityVitalInfoDataDTOList = this.fpVitalInfoDataDTOList;
    fpFacility.facilityOtherInfoDataList = this.fpCftOtherFacilityInfoDTOList;
    fpFacility.loanLimitId = this.accountNumberOfFacility;
    this.action.next(fpFacility);
    this.facilityPaperAddEditService.saveOrUpdateFPFacility(
      fpFacility,
      this.facilityPaper
    );
  }

  onRemove() {
    let fpFacility = Object.assign({}, this.facility, { status: "INA" });
    this.action.next(fpFacility);
    this.facilityPaperAddEditService.saveOrUpdateFPFacility(
      fpFacility,
      this.facilityPaper
    );
  }

  markedAsNewFacility() {
    return this.componentForm && this.componentForm.controls.isNew.value;
  }

  isValid() {
    return this.componentForm.valid;
  }

  createDynamicFormFields(creditFacilityTemplateID, isNew) {
    if (creditFacilityTemplateID) {
      let cfTemplate = _.find(
        this.creditFacilityTemplateList,
        (cfTemplate) =>
          cfTemplate.creditFacilityTemplateID == creditFacilityTemplateID
      );

      if (cfTemplate.showCondition == "Y") {
        this.componentForm.addControl(
          "condition",
          new FormControl(this.facility.condition, [Validators.required])
        );
        this.showCondition = true;
      } else {
        this.componentForm.removeControl("condition");
        this.showCondition = false;
      }

      if (cfTemplate.showPurpose == "Y") {
        this.componentForm.addControl(
          "purpose",
          new FormControl(this.facility.purpose, [
            Validators.required,
            Validators.maxLength(2000),
          ])
        );
        this.showPurpose = true;
      } else {
        this.componentForm.removeControl("purpose");
        this.showPurpose = false;
      }

      if (cfTemplate.showRemark == "Y") {
        this.componentForm.addControl(
          "remark",
          new FormControl(this.facility.remark, [
            Validators.required,
            Validators.maxLength(2000),
          ])
        );
        this.showRemark = true;
      } else {
        this.componentForm.removeControl("remark");
        this.showRemark = false;
      }

      if (cfTemplate.showRepayment == "Y") {
        this.componentForm.addControl(
          "repayment",
          new FormControl(this.facility.repayment, [
            Validators.required,
            Validators.maxLength(2000),
          ])
        );
        this.showRepayment = true;
      } else {
        this.componentForm.removeControl("repayment");
        this.showRepayment = false;
      }

      if (isNew) {
        // This is fpInterest and fpVitalInfoDataDTOList List is loaded when only adding new Facility
        this.fpInterestList =
          _.cloneDeep(cfTemplate.cftInterestRateDTOList) || [];
        this.fpVitalInfoDataDTOList =
          _.cloneDeep(cfTemplate.cftVitalInfoDTOList) || [];
        this.fpCftOtherFacilityInfoDTOList =
          _.cloneDeep(cfTemplate.cftOtherFacilityInfoDTOList) || [];

        _.forEach(this.vitalInfoContolNames, (controlName) => {
          this.componentForm.removeControl(controlName);
        });
        this.vitalInfoContolNames = [];

        _.forEach(this.otherInfoControlNames, (controlName) => {
          this.componentForm.removeControl(controlName);
        });
        this.otherInfoControlNames = [];

        _.forEach(this.fpVitalInfoDataDTOList, (vitalInfoData) => {
          let isMandatory =
            Constants.yesNoConst.Y == vitalInfoData.mandatory
              ? Validators.required
              : null;
          this.componentForm.addControl(
            "vitalInfo-" + vitalInfoData.cftVitalInfoID,
            new FormControl("", isMandatory)
          );
          this.vitalInfoContolNames.push(
            "vitalInfo-" + vitalInfoData.cftVitalInfoID
          );
        });

        _.forEach(this.fpCftOtherFacilityInfoDTOList, (otherInfoData) => {
          let isMandatory =
            Constants.yesNoConst.Y == otherInfoData.mandatory
              ? Validators.required
              : null;
          this.componentForm.addControl(
            "otherInfo-" + otherInfoData.otherFacilityInfoID,
            new FormControl("", isMandatory)
          );
          this.otherInfoControlNames.push(
            "otherInfo-" + otherInfoData.otherFacilityInfoID
          );
        });
      }
    } else {
      this.fpVitalInfoDataDTOList = [];
      this.fpInterestList = [];
      this.fpCftOtherFacilityInfoDTOList = [];
    }
    return this.componentForm;
  }

  isExchangeRateEnabled() {
    // return (
    //   this.componentForm &&
    //   this.componentForm.controls.facilityCurrency.value &&
    //   this.componentForm.controls.facilityCurrency.value != "LKR"
    // );
    return false;
  }

  setExOutAmmounts(loandData: any) {
    this.isOpendLoanLimiModal = loandData.isOpendLoanLimiModal;
    //  console.log("loand data in setEXout", loandData);
    this.accountNumberOfFacility = loandData.id;
    this.finacaleData = loandData.finacaleData;
    if (this.accountNumberOfFacility !== null) {
      let outstandingAmount = this.getCurrencyFormat(
        loandData.outstandingAmount
          ? AppUtils.getMillionValue(loandData.outstandingAmount)
          : 0
      );
      let existingAmount = this.getCurrencyFormat(
        loandData.grantedAmount
          ? AppUtils.getMillionValue(loandData.grantedAmount)
          : 0
      );
      // console.log("currency", loandData);
      let currencyCode = loandData.currencyType
        ? loandData.currencyType
        : null;

      if (currencyCode){
      this.componentForm.get("facilityCurrency").setValue(currencyCode);
    }
    

        if (loandData.loanType == "loan"){
          
          
          this.componentForm.get("existingAmount").setValue(outstandingAmount);
          this.componentForm.get("outstandingAmount").setValue(outstandingAmount);
          this.componentForm.get("facilityAmount").setValue(outstandingAmount);
        }
        else
        {
          this.componentForm.get("existingAmount").setValue(existingAmount);
          this.componentForm.get("outstandingAmount").setValue(outstandingAmount);
          this.componentForm.get("facilityAmount").setValue(existingAmount);
        
        }

      if(this.isOriginalAmountEnable()){
        this.componentForm.get("originalAmount").setValue(existingAmount);
      }
    }
  }

  // updateSelectedBy(data, loanLimitIds) {
  //   const limitsMap = new Map<string, number>();
  //   loanLimitIds.forEach((limit) => {
  //     limitsMap.set(limit.loanLimitId, limit.facilityID);
  //   });
  //   if (data.limits.length === 0) {
  //     data.limits = [];
  //   } else {
  //     data.limits.forEach((loan) => {
  //       const facilityId = limitsMap.get(loan.id) || null;
  //       loan.selectedBy = facilityId;
  //     });
  //   }
  //   if (data.loans.length === 0) {
  //     data.loans = [];
  //   } else {
  //     data.loans.forEach((loan) => {
  //       const facilityId = limitsMap.get(loan.id) || null;
  //       loan.selectedBy = facilityId;
  //     });
  //   }
  //   return data;
  // }

  isOriginalAmountEnable() {
    var isEnabled: boolean = false;

    var facilityGroup: any =
      this.creditFacilityGroups.length > 0
        ? this.creditFacilityGroups.find(
            (cfg: any) => cfg.value == this.selectedFacilityGroupId
          )
        : null;
    var currentFacilityGroup: string = facilityGroup ? facilityGroup.label : "";

    var facilityTemplate: any =
      this.creditFacilityTemplateOptionList.length > 0
        ? this.creditFacilityTemplateOptionList.find(
            (cft: any) => cft.value == this.selectedFacilityTempId
          )
        : null;
    var currentFacilityTemplate: string = facilityTemplate
      ? facilityTemplate.label
      : "";

    if (currentFacilityGroup == "Lease") {
      isEnabled = true;
    } else {
      if (
        currentFacilityGroup == "Loan" &&
        currentFacilityTemplate == "Term Loan"
      ) {
        isEnabled = true;
      }
    }

    return isEnabled;
  }

  setFaciltyComments(facility: any) {
    var comments: any[] = facility.fusTraceList ? facility.fusTraceList : [];

    var filteredComments: any[] = comments.filter(
      (c: any) => c.flag == Constants.fusTraceFlag.FAC
    );
    this.commentHistory =
      this.facilityPaperAddEditService.sortCommentHistory(filteredComments);
  }

  getRefCodeLength(): number {
    return this.componentForm.controls.facilityRefCode.value
      ? this.componentForm.controls.facilityRefCode.value.length
      : 0;
  }
}
