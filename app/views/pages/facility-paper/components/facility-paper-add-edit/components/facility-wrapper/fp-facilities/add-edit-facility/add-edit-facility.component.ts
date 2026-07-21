import { Component, Input, OnDestroy, OnInit, ViewChild } from "@angular/core";
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { FacilityPaperAddEditService } from "../../../../../../services/facility-paper-add-edit.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import * as _ from "lodash";
import { Constants } from "../../../../../../../../../core/setting/constants";
import { CacheService } from "../../../../../../../../../core/service/data/cache.service";
import { Subject, Subscription } from "rxjs";
import { NumberValidator } from "../../../../../../../../../shared/validators/number.validator";
import { AppUtils } from "../../../../../../../../../shared/app.utils";
import { CurrencyPipe } from "@angular/common";
import { AlertService } from "../../../../../../../../../core/service/common/alert.service";
import { SETTINGS } from "../../../../../../../../../core/setting/commons.settings";
import { MasterDataService } from "../../../../../../../../../core/service/data/master-data.service";
import { ConfirmationDialogComponent } from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { CftCustomFacilityInformationDto } from "src/app/views/pages/credit-facility-type-template/dto/cft-custom-facility-information-dto";
import { AddEditCustomFacilityDialogComponent } from "./add-edit-custom-facility-dialog/add-edit-custom-facility-dialog.component";
import { CustomerLimitsOutstandingDataComponent } from "../finacle-data/customer-limits-outstanding-data/customer-limits-outstanding-data.component";
import { FinacleDataStructureComponent } from "../finacle-data/customer-limits-outstanding-data/finacle-data-structure/finacle-data-structure.component";
import * as moment from "moment";

@Component({
  selector: "app-add-edit-facility",
  templateUrl: "./add-edit-facility.component.html",
  styleUrls: ["./add-edit-facility.component.scss"],
})
export class AddEditFacilityComponent implements OnInit, OnDestroy {
  @Input() checkedNewItems: any[];
  @ViewChild(FinacleDataStructureComponent, { static: true })
  finacleDataStructureComponent!: FinacleDataStructureComponent;
  currencyTypesOpt = Constants.currencyTypesOpt;
  modalRef: MDBModalRef;

  heading: string;
  content: any;
  componentForm: FormGroup;
  formErrors: any;
  facility: any = {};
  facilityPaper: any = {};
  facilityTypeList = [];

  creditFacilityGroups = [];
  creditFacilityGroupWiseTemplateMap = {};
  creditFacilityTemplateList: any = [];
  creditFacilityTemplateOptionList: any[] = [];
  creditFacilityInterestRates: any[] = [];
  creditFacilityInterestRateMap = {};
  purposeOfAdvancedList: any = [];
  purposeOfAdvancedOptionList: any = [];
  purposeOfAdvancedMap: any = {};
  parentFacility: any = {};
  sectorList: any[] = [];

  dropdownOptionsForNotMandatoryFeilds: { value: string; label: string }[] = [];
  nonMandatoryFields: CftCustomFacilityInformationDto[] = [];
  selectedNonMandatoryFields: CftCustomFacilityInformationDto[] = [];

  nonMandatoryCustomInfoNames: string[] = [];
  selectedNonMandatoryCustomInfo: any[];

  subSectorList: any[] = [];
  sectorOptionList: any[] = [];
  subSectorOptionList: any[] = [];
  cashFlowGenerationSectorList: any[] = [];
  subSectorMap: any = {};
  selectedSubSectorMap: any = {};
  creditFacilityList: any[] = [];
  creditFacilityOptionList: any[] = [];
  yesNo = Constants.yesNo;
  yesNoConst = Constants.yesNoConst;
  currencyTypesConst = Constants.currencyTypesConst;
  inputFieldValueTypeConst = Constants.inputFieldValueTypeConst;
  status = Constants.statusConst;

  fpInterestList: any[] = [];
  fpVitalInfoDataDTOList: any[] = [];
  fpCftOtherInfoDTOList: any[] = [];
  fpCftCustomInfoDTOList: any[] = [];
  vitalInfoContolNames: any[] = [];
  otherInfoControlNames: any[] = [];
  customInfoControlNames: any[] = [];
  editField: string;
  isValueUpdated: boolean = false;
  showCondition: boolean = false;
  showPurpose: boolean = false;
  showRemark: boolean = false;
  showRentalData: boolean = false;
  showRepayment: boolean = false;
  showCalculator: boolean = false;
  showNonMandatory: boolean = false;
  isLoad: any[];
  isFacilityOutstandingAmountValidationEnabled: boolean = false;
  tableColumnsForFacilityInterestRate = [
    "Rate Name",
    "Rate Code",
    "Value (%)",
    "Comment",
  ];
  tableColumnsForRentalData = ["No. of Rentals", "Rental Amount", "+"];
  tableColumnsForOtherInfoData = ["Name", "Value"];
  tableColumnsForCustomInfoData = ["Name", "Value"];

  action: Subject<any> = new Subject<any>();
  onFPFacilityChangeSub = new Subscription();

  onFormValueChangeSub = new Subscription();
  onFormChangeSub = new Subscription();
  onFormCFTChangeSub = new Subscription();
  isNewValueChangeSubs = new Subscription();

  editRental: String;
  facilityRentalInformationDTOList: FormArray;
  directorDetail: any = {};
  config: any = {};
  checkedItems: any[] = [];
  customInfoCurrentCheckedItems: any[] = [];

  conditionOptions = Constants.conditionTypes;
  countryOptions = Constants.countries;
  isOtherSelected: boolean = false;
  isTextArea: boolean = false;
  dropDownArr: any[] = [];
  selectedValue: string;

  isOpendLoanLimiModal: boolean = false;

  accountNumberOfFacility: string | null = null;

  finacaleData: any;

  facilityId: number = 0;
  commentHistory: any[] = [];
  isSecurity: boolean = false;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private cacheService: CacheService,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    public mdbModalRef: MDBModalRef,
    public currencyPipe: CurrencyPipe,
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
      outstandingAmount: {},
      sectorID: {},
      subSectorID: {},
      cashFlowGenerationSectorID: {},
      purposeOfAdvance: {},
      parentFacility: {},
      purpose: {},
      isOneOff: {},
      repayment: {},
      facilityRefCode: {},
      condition: {},
      isNew: {},
      remark: {},
      status: {},
    };
  }

  isNewFacility() {
    let isNew: boolean = false;

    if (_.isEmpty(this.facility)) {
      isNew = true;
    } else if (!this.facility.facilityID) {
      isNew = true;
    } else {
      isNew = false;
    }

    return isNew;
  }

  ngOnInit() {
    this.populateNonMandatoryCustomInfoNames();

    this.facility = this.content.facility;

    this.facilityPaper = this.content.facilityPaper;
    this.creditFacilityList = this.content.creditFacilityList;
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
    this.fpCftOtherInfoDTOList =
      _.sortBy(
        _.cloneDeep(this.facility.facilityOtherFacilityInformationDTOList),
        ["displayOrder"]
      ) || [];
    this.fpCftCustomInfoDTOList =
      _.sortBy(_.cloneDeep(this.facility.facilityCustomInfoDataDTOList), [
        "displayOrder",
      ]) || [];

    //New started
    this.checkedItems.forEach((checkedItem: any) => {
      // Assuming cftCustomFacilityInfoID is the property you want to match
      const existingItemIndex = this.fpCftCustomInfoDTOList.findIndex(
        (item) =>
          item.cftCustomFacilityInfoID === checkedItem.cftCustomFacilityInfoID
      );

      if (existingItemIndex !== -1) {
        // Update the existing item
        this.fpCftCustomInfoDTOList[existingItemIndex] = checkedItem;
      } else {
        // Add the checkedItem if it doesn't exist in fpCftCustomInfoDTOList
        this.fpCftCustomInfoDTOList.push(checkedItem);
      }
    });
    //New ended

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
            this.componentForm.controls.outstandingAmount.clearValidators();
          } else {
            this.componentForm.controls.outstandingAmount.setValidators([
              Validators.required,
              NumberValidator.maxLengthOfNumber(18),
            ]);
          }

          this.componentForm.controls.outstandingAmount.updateValueAndValidity({
            onlySelf: false,
            emitEvent: true,
          });
        }
      );

    this.onFormCFTChangeSub = this.componentForm.controls[
      "creditFacilityTemplateID"
    ].valueChanges.subscribe((creditFacilityTemplateID) => {
      this.customInfoControlNames.forEach((controlName: string) => {
        this.componentForm.removeControl(controlName);
      });
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

    this.populateNonMandatoryFields();

    this.facilityId = this.facility.facilityID;
    if (this.facility) {
      this.setFaciltyComments(this.facility);
    }
    this.accountNumberOfFacility = this.facility.loanLimitId;
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

    let isNew = this.facility.isNew == this.yesNoConst.Y;
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
      directFacility: [this.facility.directFacility == this.yesNoConst.Y, []],
      isOneOff: [this.facility.isOneOff == this.yesNoConst.Y, []],
      seriesOfLoans: [this.facility.seriesOfLoans == this.yesNoConst.Y, []],
      revolving: [this.facility.revolving == this.yesNoConst.Y, []],
      reduction: [this.facility.reduction == this.yesNoConst.Y, []],
      enhancement: [this.facility.enhancement == this.yesNoConst.Y, []],
      facilityCurrency: [
        this.facility.facilityCurrency
          ? this.facility.facilityCurrency
          : this.currencyTypesConst.LKR,
        Validators.required,
      ],
      facilityAmount: [
        this.getCurrencyFormat(this.facility.facilityAmount),
        [
          Validators.required,
          NumberValidator.maxLengthOfNumber(18),
          NumberValidator.isCommaSeparatedValue,
        ],
      ],
      outstandingAmount: [
        this.getCurrencyFormat(this.facility.outstandingAmount),
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
      sectorID: [this.facility.sectorID, [Validators.required]],
      subSectorID: [this.facility.subSectorID, [Validators.required]],
      cashFlowGenerationSectorID: [
        this.facility.cashFlowGenerationSectorID,
        [Validators.required],
      ],
      purposeOfAdvance: [this.facility.purposeOfAdvance],
      status: [status, [Validators.required]],
      facilityRentalInformationDTOList: this.formBuilder.array(
        this.createRentalDataFormArray()
      ),
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

    if (
      this.facility &&
      !_.isEmpty(this.facility.facilityCustomInfoDataDTOList)
    ) {
      let customInfoDataControlConfig = {};
      _.forEach(
        this.facility.facilityCustomInfoDataDTOList,
        (customInfoData) => {
          let isMandatory =
            Constants.yesNoConst.Y == customInfoData.mandatory
              ? [Validators.required]
              : [];
          customInfoDataControlConfig[
            "customInfo-" + customInfoData.cftCustomFacilityInfoID
          ] = [customInfoData.customInfoData, [...isMandatory]];
        }
      );

      controlConfig = Object.assign(
        {},
        controlConfig,
        customInfoDataControlConfig
      );
    }

    if (
      this.facility &&
      !_.isEmpty(this.facility.facilityOtherFacilityInformationDTOList)
    ) {
      let otherInfoDataControlConfig = {};
      _.forEach(
        this.facility.facilityOtherFacilityInformationDTOList,
        (otherInfoData) => {
          if (otherInfoData.cftOtherFacilityInfoID) {
            let validators =
              this.getOtherFacilityInfoValidations(otherInfoData);
            otherInfoDataControlConfig[
              "otherInfo-" + otherInfoData.cftOtherFacilityInfoID
            ] = [otherInfoData.otherInfoData, validators];
          }
        }
      );

      controlConfig = Object.assign(
        {},
        controlConfig,
        otherInfoDataControlConfig
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
    }
  }

  updateUserComment(id: number, property: string, event: any) {
    const newValue = event.target.value;
    this.fpInterestList[id][property] = newValue;
    this.isValueUpdated = true;
  }

  changeValue(id: number, property: string, event: any) {
    this.editField = event.target.textContent;
    this.editRental = event.target.textContent;
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

    if (!_.isEmpty(this.fpCftOtherInfoDTOList)) {
      _.map(this.fpCftOtherInfoDTOList, (otherInfoData) => {
        if (otherInfoData.cftOtherFacilityInfoID) {
          otherInfoData.otherInfoData =
            rawData["otherInfo-" + otherInfoData.cftOtherFacilityInfoID];
          delete rawData["otherInfo-" + otherInfoData.cftOtherFacilityInfoID];
        }
      });
    }

    if (!_.isEmpty(this.fpCftCustomInfoDTOList)) {
      _.map(this.fpCftCustomInfoDTOList, (customInfoData) => {
        if (customInfoData.cftCustomFacilityInfoID) {
          customInfoData.customInfoData =
            rawData["customInfo-" + customInfoData.cftCustomFacilityInfoID];
          delete rawData[
            "customInfo-" + customInfoData.cftCustomFacilityInfoID
          ];
        }
      });
    }
    return rawData;
  }

  setCurrencyFormatValue(control) {
    const amount = this.getValue(this.getFormRawData()[control]);
    this.componentForm.patchValue({
      [control]: this.currencyPipe.transform(amount, "", ""),
    });
  }

  setCurrencyFormatValueForOtherInfo($event, item) {
    let formControlName = "otherInfo-" + item.cftOtherFacilityInfoID;
    if (
      item.otherFacilityInfoFieldType == this.inputFieldValueTypeConst.CURRENCY
    ) {
      const amount = this.getValue($event.target.value);
      this.componentForm.patchValue({
        [formControlName]: this.currencyPipe.transform(amount, "", ""),
      });
    }
  }

  patchFormControllerValue(form, controlName) {
    const amount = this.getValue(form.getRawValue()[controlName]);
    form.patchValue({
      [controlName]: this.getCurrencyFormat(amount),
    });
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, "", "");
  }

  onAddEdit() {
    let facilityData = this.getFacilityData();
    facilityData.loanLimitId = this.accountNumberOfFacility;
    this.action.next(facilityData);
    this.facilityPaperAddEditService.saveOrUpdateFPFacility(
      facilityData,
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

      if (cfTemplate.showCondition == this.yesNoConst.Y) {
        this.componentForm.addControl(
          "condition",
          new FormControl(this.facility.condition, [Validators.required])
        );
        this.showCondition = true;
      } else {
        this.componentForm.removeControl("condition");
        this.showCondition = false;
      }

      if (cfTemplate.showPurpose == this.yesNoConst.Y) {
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

      if (cfTemplate.showRemark == this.yesNoConst.Y) {
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

      if (
        cfTemplate.showRepayment == this.yesNoConst.Y &&
        cfTemplate.showCalculator == this.yesNoConst.N
      ) {
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

      if (cfTemplate.showRentalData == this.yesNoConst.Y) {
        this.showRentalData = true;
      } else {
        this.showRentalData = false;
      }

      if (isNew) {
        // This is fpInterest and fpVitalInfoDataDTOList List is loaded when only adding new Facility
        this.fpInterestList =
          _.cloneDeep(cfTemplate.cftInterestRateDTOList) || [];
        this.fpVitalInfoDataDTOList =
          _.cloneDeep(cfTemplate.cftVitalInfoDTOList) || [];

        this.fpCftOtherInfoDTOList =
          _.sortBy(_.cloneDeep(cfTemplate.cftOtherFacilityInfoDTOList), [
            "displayOrder",
          ]) || [];

        this.fpCftCustomInfoDTOList =
          _.sortBy(_.cloneDeep(cfTemplate.cftCustomFacilityInfoDTOList), [
            "displayOrder",
          ]) || [];

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

        //here
        _.forEach(this.fpCftOtherInfoDTOList, (otherInfoData: any) => {
          let validators = this.getOtherFacilityInfoValidations(otherInfoData);
          if (otherInfoData.cftOtherFacilityInfoID) {
            this.componentForm.addControl(
              "otherInfo-" + otherInfoData.cftOtherFacilityInfoID,
              new FormControl(otherInfoData.defaultValue, validators)
            );
            this.otherInfoControlNames.push(
              "otherInfo-" + otherInfoData.cftOtherFacilityInfoID
            );
          }
        });

        _.forEach(this.fpCftCustomInfoDTOList, (customInfoData) => {
          let isMandatory =
            Constants.yesNoConst.Y == customInfoData.mandatory
              ? [Validators.required]
              : [];
          this.componentForm.addControl(
            "customInfo-" + customInfoData.cftCustomFacilityInfoID,
            new FormControl("", isMandatory)
          );
          this.customInfoControlNames.push(
            "customInfo-" + customInfoData.cftCustomFacilityInfoID
          );

          if (customInfoData.mandatory == "N") {
            this.dropdownOptionsForNotMandatoryFeilds.push(
              customInfoData.customFacilityInfoName
            );
          }
        });
      }
    } else {
      this.fpVitalInfoDataDTOList = [];
      this.fpInterestList = [];
      this.fpCftOtherInfoDTOList = [];
      this.fpCftCustomInfoDTOList = [];
    }

    return this.componentForm;
  }

  createRentalDataFormArray() {
    let rentalDataArray = [];
    if (
      this.facility &&
      this.facility.facilityRentalInformationDTOList &&
      this.facility.facilityRentalInformationDTOList.length > 0
    ) {
      this.facility.facilityRentalInformationDTOList.forEach((data) => {
        rentalDataArray.push(this.createRentalDetails(data));
      });
    } else {
      rentalDataArray = [];
    }
    return rentalDataArray;
  }

  createRentalDetails(data): FormGroup {
    return this.formBuilder.group({
      facilityRentalInformationID: [data.facilityRentalInformationID],
      noOfRentals: [data.noOfRentals],
      rentalAmount: [
        data.rentalAmount
          ? this.getCurrencyFormat(data.rentalAmount)
          : data.rentalAmount,
      ],
      status: [data.status ? data.status : this.status.ACT],
    });
  }

  addRental() {
    this.facilityRentalInformationDTOList = this.componentForm.get(
      "facilityRentalInformationDTOList"
    ) as FormArray;
    let rentalObject = this.validatePeriod();
    this.facilityRentalInformationDTOList.push(
      this.createRentalDetails(rentalObject)
    );
  }

  removeItem(item, index) {
    let rentalData = item.value ? item.value : {};
    if (rentalData.facilityRentalInformationID) {
      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Remove Rental Data",
          message: "Do you want to remove " + rentalData.rentalAmount + " ?",
        },
      });

      this.modalRef.content.action.subscribe((result: boolean) => {
        if (result) {
          item.patchValue(
            { status: Constants.statusConst.INA },
            { onlySelf: true, emitEvent: true }
          );
        }
      });
    } else {
      this.removeRentalFromRow(index);
    }
  }

  removeRentalFromRow(index) {
    this.facilityRentalInformationDTOList = this.componentForm.get(
      "facilityRentalInformationDTOList"
    ) as FormArray;
    this.facilityRentalInformationDTOList.removeAt(index);
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

  validatePeriod() {
    let sumOfRentals: number = +0;
    this.facilityRentalInformationDTOList.controls.forEach((data) => {
      sumOfRentals += +data.value.noOfRentals;
    });

    this.getFormRawData();
    let period: number = 0;
    this.fpCftOtherInfoDTOList.forEach((i) => {
      if (i.outputCode == "OUT002") {
        period = i.otherInfoData;
      }
    });

    let rentalObject = {};
    if (sumOfRentals > 0) {
      let noOfRentals: number = period - sumOfRentals;
      let rentalAmount = 0;
      // if (noOfRentals == 1) {
      //   rentalAmount = 89025.91;
      //   //get last rental value
      // }

      rentalObject = {
        noOfRentals: noOfRentals,
        rentalAmount: rentalAmount,
      };
    }

    return rentalObject;
  }

  calculateLastRentalValue() {
    let facilityData = this.getFacilityData();
    this.facilityPaperAddEditService.calculateLastRentalValue(facilityData);
  }

  getFacilityData() {
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

    fpFacility.facilityAmount = this.getValue(formData.facilityAmount);

    if (this.markedAsNewFacility()) {
      fpFacility.outstandingAmount = 0;
    } else {
      fpFacility.outstandingAmount = this.getValue(formData.outstandingAmount);

      if (this.isFacilityOutstandingAmountValidationEnabled) {
        if (
          parseFloat(fpFacility.outstandingAmount) <
          parseFloat(fpFacility.facilityAmount)
        ) {
          this.alertService.showToaster(
            "Outstanding Amount should be lesser than the Previous Amount",
            SETTINGS.TOASTER_MESSAGES.warning
          );
          return;
        }
      }
    }

    fpFacility.isNew = formData.isNew ? this.yesNoConst.Y : this.yesNoConst.N;
    fpFacility.isAnnual = formData.isAnnual
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.isAdditional = formData.isAdditional
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.isTermsAmended = formData.isTermsAmended
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.isReStructure = formData.isReStructure
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.isReSchedule = formData.isReSchedule
      ? this.yesNoConst.Y
      : this.yesNoConst.N;

    fpFacility.isOneOff = formData.isOneOff
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.directFacility = formData.directFacility
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.seriesOfLoans = formData.seriesOfLoans
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.revolving = formData.revolving
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.reduction = formData.reduction
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.enhancement = formData.enhancement
      ? this.yesNoConst.Y
      : this.yesNoConst.N;
    fpFacility.isCooperate = this.content.facilityPaper.isCooperate;

    fpFacility.facilityInterestRateList = this.fpInterestList;
    fpFacility.facilityVitalInfoDataDTOList = this.fpVitalInfoDataDTOList;
    fpFacility.facilityCustomInfoDataDTOList = this.fpCftCustomInfoDTOList;

    fpFacility.facilityOtherFacilityInformationDTOList =
      this.fpCftOtherInfoDTOList.map((data) => {
        return Object.assign(
          {},
          { ...data },
          { otherInfoData: this.getValue(data.otherInfoData) }
        );
      });

    fpFacility.facilityOtherFacilityInformationDTOList.forEach((i) => {
      if (i.outputCode == "OUT001") {
        if (fpFacility.isNew == this.yesNoConst.Y) {
          i.otherInfoData = this.getValue(fpFacility.facilityAmount);
        } else if (fpFacility.isNew == this.yesNoConst.N) {
          i.otherInfoData = this.getValue(fpFacility.outstandingAmount);
        }
      }
    });

    fpFacility.facilityRentalInformationDTOList = this.componentForm
      .getRawValue()
      .facilityRentalInformationDTOList.map((data, i) => {
        return Object.assign(
          {},
          { ...data },
          { rentalAmount: this.getValue(data.rentalAmount) },
          { displayOrder: i }
        );
      });

    return fpFacility;
  }

  getOtherFacilityInfoValidations(data) {
    let validators: any[] = [];
    if (data.mandatory == Constants.yesNoConst.Y) {
      validators.push(Validators.required);
    }

    switch (data.otherFacilityInfoFieldType) {
      case this.inputFieldValueTypeConst.NUMBER: {
        validators.push(NumberValidator.isNumber);
        break;
      }
      case this.inputFieldValueTypeConst.CURRENCY: {
        validators.push(NumberValidator.isCommaSeparatedValue);
        break;
      }
      case this.inputFieldValueTypeConst.PERCENTAGE: {
        validators.push(NumberValidator.isPercentageValue);
        break;
      }
      default:
        break;
    }
    return validators;
  }

  isEditable(item) {
    return item.isEditable ? item.isEditable == this.yesNoConst.Y : true;
  }

  showNonMandatoryItems() {
    this.showNonMandatory = !this.showNonMandatory;
  }

  populateNonMandatoryFields() {
    this.nonMandatoryFields = this.fpCftCustomInfoDTOList.filter(
      (item) => item.mandatory !== "Y"
    );
  }

  populateNonMandatoryCustomInfoNames() {
    this.fpCftCustomInfoDTOList.forEach((customInfoData) => {
      if (customInfoData.mandatory !== Constants.yesNoConst.Y) {
        let isMandatory = Validators.required;
        this.componentForm.addControl(
          "customInfo-" + customInfoData.cftCustomFacilityInfoID,
          new FormControl("", isMandatory)
        );
        this.customInfoControlNames.push(
          "customInfo-" + customInfoData.cftCustomFacilityInfoID
        );

        // Add the customFacilityInfoName to your dropdownOptionsForNotMandatoryFeilds array
        this.dropdownOptionsForNotMandatoryFeilds.push(
          customInfoData.customFacilityInfoName
        );
      }
    });
  }

  AddNotMandatoryFeilds() {
    let x: any[];

    if (!this.isNewFacility()) {
      x = this.facility.facilityCustomInfoDataDTOList
        .filter(
          (data) =>
            !_.isEmpty(data.customInfoData) &&
            data.mandatory == Constants.yesNoConst.N
        )
        .map((data) => data.cftCustomFacilityInfoID);
    } else if (this.facility.facilityCustomInfoDataDTOList) {
      x = this.facility.facilityCustomInfoDataDTOList.map(
        (data) => data.cftCustomFacilityInfoID
      );
    } else {
      x = null;
    }

    if (!_.isEmpty(this.customInfoCurrentCheckedItems)) {
      x = x.concat(this.customInfoCurrentCheckedItems);
    }

    this.modalRef = this.mdbModalService.show(
      AddEditCustomFacilityDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class:
          "modal-custom-facility-info modal-width-30-p modal-dialog-scrollable  modal-lg ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Add feilds",
          content: this.fpCftCustomInfoDTOList
            .filter(
              (customInfoData) =>
                customInfoData.mandatory == Constants.yesNoConst.N
            )
            .map((customInfoData) => {
              let isMandatory = Validators.required;
              this.componentForm.addControl(
                "customInfo-" + customInfoData.cftCustomFacilityInfoID,
                new FormControl("", isMandatory)
              );
              this.customInfoControlNames.push(
                "customInfo-" + customInfoData.cftCustomFacilityInfoID
              );

              return {
                customFacilityInfoName: customInfoData.customFacilityInfoName,
                cftCustomFacilityInfoID: customInfoData.cftCustomFacilityInfoID,
                facilityID: customInfoData.facilityID,
              };
            }),
          dropdownOptionsForNotMandatoryFeilds:
            this.dropdownOptionsForNotMandatoryFeilds,
          checkedNewItems: x ? x : null,
        },
      }
    );

    this.modalRef.content.action.subscribe((checkedItems: any) => {
      if (checkedItems) {
        this.isLoad = checkedItems;

        this.facility.facilityCustomInfoDataDTOList = checkedItems;
        this.checkedItems = checkedItems;

        this.customInfoCurrentCheckedItems = checkedItems.map(
          (data) => data.cftCustomFacilityInfoID
        );
      }
    });
  }

  isCheckedEmpty(customInfoData) {
    if (customInfoData == null) {
      return false;
    }
    // else if (!_.isEmpty(customInfoData)) {
    //   return true;
    // }
    else if (customInfoData == "") {
      return false;
    } else {
      return true;
    }
  }

  dropDown(item) {
    if (item.customFacilityInfoCode == "VEHICLE_STATUS") {
      return true;
    } else if (item.customFacilityInfoCode == "COUNTRY_OF_ORIGIN") {
      return true;
    } else if (item.customFacilityInfoCode == "TYPE_OF_VEHICLE") {
      return true;
    } else if (item.customFacilityInfoCode == "PERIOD_SINCE_REGISTRATION") {
      return true;
    } else {
      return false;
    }
  }

  customFacilityDropdown(item) {
    const otherOption = { value: "Other", text: "Other" };

    if (item.customFacilityInfoCode == "VEHICLE_STATUS") {
      return Constants.conditionTypes;
    } else if (item.customFacilityInfoCode == "COUNTRY_OF_ORIGIN") {
      return Constants.countries;
    } else if (item.customFacilityInfoCode == "TYPE_OF_VEHICLE") {
      return Constants.vehicleTypes;
    } else if (item.customFacilityInfoCode == "PERIOD_SINCE_REGISTRATION") {
      return Constants.PeriodSinceDateOfRegistration;
    } else {
      return null;
    }
  }

  onSelectedValueChange($event) {
    if ($event.covenant_Description === "Other") {
      this.isOtherSelected = true;
    } else {
      this.isOtherSelected = false;
    }
  }

  asdd(event, item: any) {
    const indexToRemove = this.dropDownArr
      .map((item) => item.id)
      .findIndex((x) => x === "customInfo-" + item.cftCustomFacilityInfoID);

    if (indexToRemove !== -1) {
      this.dropDownArr.splice(indexToRemove, 1);
    }

    this.dropDownArr.push({
      id: "customInfo-" + item.cftCustomFacilityInfoID,
      value: event.value,
    });

    if (event.value === "Other") {
      this.isTextArea = true;
    } else {
      this.isTextArea = false;
    }

    return true;
  }

  // isTextAreaVisible(item:any) {
  //   if (!_.isEmpty(this.dropDownArr.filter(items => (items.id === 'customInfo-' +item.cftCustomFacilityInfoID)))) {
  //     if (this.dropDownArr.filter(items => (items.id === 'customInfo-' +item.cftCustomFacilityInfoID)).map(item =>item.value)[0] ==='Other') {
  //       return true;
  //     } else {
  //       return false;
  //     }

  //   }else {
  //     return false;
  //   }

  // }

  asdde(selectedItem: any, item: any) {
    this.selectedValue = selectedItem;

    // Add logic to enable or disable the input field based on the selected value
    if (selectedItem === "Other") {
      this.componentForm
        .get("customInfo-" + item.cftCustomFacilityInfoID)
        .enable();
    } else {
      this.componentForm
        .get("customInfo-" + item.cftCustomFacilityInfoID)
        .disable();
    }
  }

  setExOutAmmounts(loandData: any) {
    this.isOpendLoanLimiModal = loandData.isOpendLoanLimiModal;

    this.finacaleData = loandData.finacaleData;
    this.accountNumberOfFacility = loandData.id;
    if (this.accountNumberOfFacility !== null) {
      let outstandingAmount = this.getCurrencyFormat(
        loandData.outstandingAmount ? loandData.outstandingAmount : 0
      );
      let existingAmount = this.getCurrencyFormat(
        loandData.grantedAmount ? loandData.grantedAmount : 0
      );

      let currencyCode = loandData.currencyType
        ? loandData.currencyType
        : this.facility.facilityCurrency;

      if (currencyCode) {
        this.componentForm.get("facilityCurrency").setValue(currencyCode);
      }
      if (loandData.loanType == "loan") {
        this.componentForm.get("facilityAmount").setValue(outstandingAmount);
        this.componentForm.get("outstandingAmount").setValue(outstandingAmount);
      } else {
        this.componentForm.get("facilityAmount").setValue(existingAmount);
        this.componentForm.get("outstandingAmount").setValue(existingAmount);
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
