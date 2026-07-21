import { CacheService } from "../../../../../../../../core/service/data/cache.service";
import {
  Component,
  Input,
  OnDestroy,
  OnInit,
  SimpleChange,
} from "@angular/core";
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { CurrencyPipe } from "@angular/common";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { PrivilegeService } from "../../../../../../../../core/service/authentication/privilege.service";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../../core/setting/constants";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { NumberValidator } from "../../../../../../../../shared/validators/number.validator";
import { AppUtils } from "../../../../../../../../shared/app.utils";
import * as _ from "lodash";
import { ConfirmationDialogComponent } from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { MnCurrencyService } from "../../../../../../../../core/service/common/mn-currency.service";
import { CommonPopupWithTinyMceEditorComponent } from "../../../../../../../../shared/components/common-popup-with-tiny-mce-editor/common-popup-with-tiny-mce-editor.component";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-fp-security-summery",
  templateUrl: "./fp-security-summery.component.html",
  styleUrls: ["./fp-security-summery.component.scss"],
})
export class FpSecuritySummeryComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  @Input("facilityPaper") facilityPaper: any = {};
  @Input("isPreviewMode") isPreviewMode: boolean;

  onFPaperSecSummeryChangeSub = new Subscription();
  onSecuritySummeryFormChangeSub = new Subscription();
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilitySecuritySummaryTypeGroupConst =
    Constants.facilitySecuritySummaryTypeGroupConst;
  facilitySecuritySummaryType =
    Constants.facilitySecuritySummaryTypeConst.INDIVIDUAL;
  hasPrivilege = false;
  isDisabled = false;

  componentForm: FormGroup;
  firstClassSecuritySummaryTopicsDTOs: FormArray;
  otherSecuritySummaryTopicsDTOs: FormArray;
  defaultSecuritySummaryTopicsDTOs: FormArray;
  fpSecuritySummeryDTO: any = {};

  securitySummaryTopics: any[] = [];
  firstClassSecuritySummaryTopics: any[] = [];
  optionsFirstClassSecuritySummaryTopics: any[] = [];
  otherSecuritySummaryTopics: any[] = [];
  optionsOtherSecuritySummaryTopics: any[] = [];
  defaultSecuritySummaryTopics: any[] = [];
  optionsDefaultSecuritySummaryTopics: any[] = [];

  subTotalFirstClassCompany: number = 0;
  subTotalFirstClassCompanyPercentage;
  subTotalFirstClassGroup: number = 0;
  subTotalFirstClassGroupPercentage;
  subTotalFirstClassPlusOthersCompany: number = 0;
  subTotalFirstClassPlusOthersCompanyPercentage;
  subTotalFirstClassPlusOthersGroup: number = 0;
  subTotalFirstClassPlusOthersGroupPercentage;
  totalCompany: number = 0;
  totalCompanyPercentage;
  totalGroup: number = 0;
  totalGroupPercentage;

  subTotalOtherCompany: number = 0;
  subTotalOtherCompanyPercentage;
  subTotalOtherCompanyGroup: number = 0;
  subTotalOtherCompanyGroupPercentage;

  subTotalClean: number = 0;
  subTotalCleanPercentage;
  subTotalCleanGroup: number = 0;
  subTotalCleanGroupPercentage;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private privilegeService: PrivilegeService,
    private formBuilder: FormBuilder,
    private currencyPipe: CurrencyPipe,
    public mnCurrencyService: MnCurrencyService,
    private applicationService: ApplicationService,
    private cacheService: CacheService,
    private mdbModalService: MDBModalService
  ) {
    this.componentForm = this.createForm();
  }

  ngOnInit() {
    this.securitySummaryTopics =
      this.cacheService.getData(
        Constants.masterDataKey.CAS_SECURITY_SUMMARY_TOPICS
      ) || [];
    this.securitySummaryTopics.forEach((data) => {
      switch (data.securityTypeGroup) {
        case this.facilitySecuritySummaryTypeGroupConst.FIRST_CLASS:
          this.firstClassSecuritySummaryTopics.push(data);
          break;
        case this.facilitySecuritySummaryTypeGroupConst.OTHER:
          this.otherSecuritySummaryTopics.push(data);
          break;
        case this.facilitySecuritySummaryTypeGroupConst.DEFAULT:
          this.defaultSecuritySummaryTopics.push(data);
          break;
      }
    });

    this.hasPrivilege = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_EDIT
    );
    this.isDisabled = !this.isAbleToUpdate();

    this.onFPaperSecSummeryChangeSub =
      this.facilityPaperAddEditService.onFPaperSecSummeryChange.subscribe(
        (facilityPaper: any) => {
          if (!_.isEmpty(facilityPaper)) {
            this.facilityPaper = facilityPaper;
            this.fpSecuritySummeryDTO = facilityPaper.fpSecuritySummeryDTO
              ? facilityPaper.fpSecuritySummeryDTO
              : { facilityPaperID: this.facilityPaper.facilityPaperID };
            this.facilitySecuritySummaryType = this.fpSecuritySummeryDTO
              .facilitySecuritySummaryType
              ? this.fpSecuritySummeryDTO.facilitySecuritySummaryType
              : Constants.facilitySecuritySummaryTypeConst.INDIVIDUAL;
            this.fpSecuritySummeryDTO.facilityPaperID =
              this.facilityPaper.facilityPaperID;

            this.subTotalFirstClassCompany =
              this.fpSecuritySummeryDTO.companySubTotalOne;
            this.subTotalFirstClassPlusOthersCompany =
              this.fpSecuritySummeryDTO.companySubTotalTwo;
            this.totalCompany = this.fpSecuritySummeryDTO.companyTotal;
            this.subTotalOtherCompany =
              this.fpSecuritySummeryDTO.companySubTotalThree;
            this.subTotalClean = this.fpSecuritySummeryDTO.companySubTotalFour;

            this.subTotalFirstClassGroup =
              this.fpSecuritySummeryDTO.groupSubTotalOne;
            this.subTotalFirstClassPlusOthersGroup =
              this.fpSecuritySummeryDTO.groupSubTotalTwo;
            this.totalGroup = this.fpSecuritySummeryDTO.groupTotal;
            this.subTotalOtherCompanyGroup =
              this.fpSecuritySummeryDTO.groupSubTotalThree;
            this.subTotalCleanGroup =
              this.fpSecuritySummeryDTO.groupSubTotalFour;

            this.calculateTotalPercentages();
            this.componentForm = this.createForm();

            this.setInitialAddedSecurityTopics(this.fpSecuritySummeryDTO);
            this.updateFormSubscription();
          }
        }
      );
  }

  ngOnChanges(changes: SimpleChange) {
    if (changes["isPreviewMode"]) {
      this.isPreviewMode = changes["isPreviewMode"].currentValue;
      this.isDisabled = this.isPreviewMode;
      this.createForm();
      this.updateFormSubscription();
      this.setInitialAddedSecurityTopics(
        this.facilityPaper.fpSecuritySummeryDTO
          ? this.facilityPaper.fpSecuritySummeryDTO
          : {}
      );
    }
  }

  calculateTotalPercentages() {
    if (this.totalCompany > 0) {
      this.subTotalFirstClassCompanyPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalFirstClassCompany) /
            this.getFloatValue(this.totalCompany)) *
            100,
          2
        ).toFixed(2) + "%";
      this.subTotalOtherCompanyPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalOtherCompany) /
            this.getFloatValue(this.totalCompany)) *
            100,
          2
        ).toFixed(2) + "%";
      this.subTotalCleanPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalClean) /
            this.getFloatValue(this.totalCompany)) *
            100,
          2
        ).toFixed(2) + "%";
      this.subTotalFirstClassPlusOthersCompanyPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalFirstClassPlusOthersCompany) /
            this.getFloatValue(this.totalCompany)) *
            100,
          2
        ).toFixed(2) + "%";
      this.totalCompanyPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.totalCompany) /
            this.getFloatValue(this.totalCompany)) *
            100,
          2
        ).toFixed(2) + "%";
    }

    if (this.totalGroup > 0) {
      this.subTotalFirstClassGroupPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalFirstClassGroup) /
            this.getFloatValue(this.totalGroup)) *
            100,
          2
        ).toFixed(2) + "%";
      this.subTotalOtherCompanyGroupPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalOtherCompanyGroup) /
            this.getFloatValue(this.totalGroup)) *
            100,
          2
        ).toFixed(2) + "%";
      this.subTotalCleanGroupPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalCleanGroup) /
            this.getFloatValue(this.totalGroup)) *
            100,
          2
        ).toFixed(2) + "%";
      this.subTotalFirstClassPlusOthersGroupPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.subTotalFirstClassPlusOthersGroup) /
            this.getFloatValue(this.totalGroup)) *
            100,
          2
        ).toFixed(2) + "%";
      this.totalGroupPercentage =
        AppUtils.roundUp(
          (this.getFloatValue(this.totalGroup) /
            this.getFloatValue(this.totalGroup)) *
            100,
          2
        ).toFixed(2) + "%";
    }
  }

  ngOnDestroy(): void {
    this.onFPaperSecSummeryChangeSub.unsubscribe();
    this.onSecuritySummeryFormChangeSub.unsubscribe();
  }

  createSecuritySummaryRow(securityTopic, facilityPaper, index?): FormGroup {
    return this.formBuilder.group({
      facilityPaperID: [facilityPaper.facilityPaperID],
      fpSecuritySummaryID: [facilityPaper.fpSecuritySummaryID],
      fpSecuritySummaryTopicID: [securityTopic.fpSecuritySummaryTopicID],
      displayOrder: [
        securityTopic.displayOrder ? securityTopic.displayOrder : index,
      ],
      securityType: [securityTopic.securityType],
      securityTypeGroup: [securityTopic.securityTypeGroup],
      companyValue: [
        {
          value: this.getCurrencyFormat(securityTopic.companyValue),
          disabled: this.isDisabled,
        },
      ],
      companyPercentage: [
        { value: securityTopic.companyPercentage, disabled: true },
      ],
      groupValue: [
        {
          value: this.getCurrencyFormat(securityTopic.groupValue),
          disabled: this.isDisabled,
        },
      ],
      groupPercentage: [securityTopic.groupPercentage],
      status: [securityTopic.status],

      // otherCompanyValue : [{value: this.getCurrencyFormat(securityTopic.otherCompanyValue), disabled: this.isDisabled}],
    });
  }

  createForm() {
    this.componentForm = this.formBuilder.group({
      firstClassSecuritySummaryTopicsDTOs: this.formBuilder.array(
        this.createSecurityTypeFormArray(
          this.facilitySecuritySummaryTypeGroupConst.FIRST_CLASS
        )
      ),
      defaultSecuritySummaryTopicsDTOs: this.formBuilder.array(
        this.createSecurityTypeFormArray(
          this.facilitySecuritySummaryTypeGroupConst.DEFAULT
        )
      ),
      otherSecuritySummaryTopicsDTOs: this.formBuilder.array(
        this.createSecurityTypeFormArray(
          this.facilitySecuritySummaryTypeGroupConst.OTHER
        )
      ),
      firstClassSecurityType: [""],
      otherSecurityType: [""],
      defaultSecurityType: [""],
      limitSummery: [
        {
          value: this.fpSecuritySummeryDTO.limitSummery,
          disabled: !this.hasPrivilege || this.isDisabled,
        },
      ],
    });

    return this.componentForm;
  }

  createSecurityTypeFormArray(securityTypeGroup) {
    let securityTopics = [];
    if (this.fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS) {
      this.fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS.forEach(
        (securityTopic, index) => {
          if (securityTopic.securityTypeGroup == securityTypeGroup) {
            securityTopics.push(
              this.createSecuritySummaryRow(
                securityTopic,
                this.facilityPaper,
                index
              )
            );
          }
        }
      );
    }
    return securityTopics;
  }

  addFirstClassSecuritySummaryTopicFormRow(securityType) {
    this.firstClassSecuritySummaryTopicsDTOs = this.componentForm.get(
      "firstClassSecuritySummaryTopicsDTOs"
    ) as FormArray;
    let securitySummaryTopic = {
      securityType: securityType,
      securityTypeGroup: this.facilitySecuritySummaryTypeGroupConst.FIRST_CLASS,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      fpSecuritySummaryID: this.facilityPaper.fpSecuritySummaryID,
      companyValue: null,
      groupValue: null,
      // otherCompanyValue : null
    };
    this.firstClassSecuritySummaryTopicsDTOs.push(
      this.createSecuritySummaryRow(
        securitySummaryTopic,
        this.facilityPaper,
        this.firstClassSecuritySummaryTopicsDTOs.length + 1
      )
    );
  }

  removeFirstClassSecuritySummaryTopicFormRow(item, index) {
    if (!_.isEmpty(item.value)) {
      let value = item.value.securityType;
      let option = _.find(this.firstClassSecuritySummaryTopics, {
        securityType: value,
      });
      this.optionsFirstClassSecuritySummaryTopics.push(option);
    }
    this.firstClassSecuritySummaryTopicsDTOs = this.componentForm.get(
      "firstClassSecuritySummaryTopicsDTOs"
    ) as FormArray;
    this.firstClassSecuritySummaryTopicsDTOs.removeAt(index);
  }

  addOtherSecuritySummaryTopicFormRow(securityType) {
    this.otherSecuritySummaryTopicsDTOs = this.componentForm.get(
      "otherSecuritySummaryTopicsDTOs"
    ) as FormArray;
    let securitySummaryTopic = {
      securityType: securityType,
      securityTypeGroup: this.facilitySecuritySummaryTypeGroupConst.OTHER,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      fpSecuritySummaryID: this.facilityPaper.fpSecuritySummaryID,
      companyValue: null,
      groupValue: null,
      // otherCompanyValue : null
    };
    this.otherSecuritySummaryTopicsDTOs.push(
      this.createSecuritySummaryRow(
        securitySummaryTopic,
        this.facilityPaper,
        this.otherSecuritySummaryTopicsDTOs.length + 1
      )
    );
  }

  removeOtherSecuritySummaryTopicFormRow(item, index) {
    if (!_.isEmpty(item.value)) {
      let value = item.value.securityType;
      let option = _.find(this.otherSecuritySummaryTopics, {
        securityType: value,
      });
      this.optionsOtherSecuritySummaryTopics.push(option);
    }
    this.otherSecuritySummaryTopicsDTOs = this.componentForm.get(
      "otherSecuritySummaryTopicsDTOs"
    ) as FormArray;
    this.otherSecuritySummaryTopicsDTOs.removeAt(index);
  }

  addDefaultSecuritySummaryTopicFormRow(securityType) {
    this.defaultSecuritySummaryTopicsDTOs = this.componentForm.get(
      "defaultSecuritySummaryTopicsDTOs"
    ) as FormArray;
    let securitySummaryTopic = {
      securityType: securityType,
      securityTypeGroup: this.facilitySecuritySummaryTypeGroupConst.DEFAULT,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      fpSecuritySummaryID: this.facilityPaper.fpSecuritySummaryID,
      companyValue: null,
      groupValue: null,
      // otherCompanyValue: null
    };
    this.defaultSecuritySummaryTopicsDTOs.push(
      this.createSecuritySummaryRow(
        securitySummaryTopic,
        this.facilityPaper,
        this.defaultSecuritySummaryTopicsDTOs.length + 1
      )
    );
  }

  removeDefaultSecuritySummaryTopicFormRow(item, index) {
    if (!_.isEmpty(item.value)) {
      let value = item.value.securityType;
      let option = _.find(this.defaultSecuritySummaryTopics, {
        securityType: value,
      });
      this.optionsDefaultSecuritySummaryTopics.push(option);
    }
    this.defaultSecuritySummaryTopicsDTOs = this.componentForm.get(
      "defaultSecuritySummaryTopicsDTOs"
    ) as FormArray;
    this.defaultSecuritySummaryTopicsDTOs.removeAt(index);
  }

  isValid() {
    return this.componentForm.valid;
  }

  saveUpdateSecuritySummery() {
    let fpSecuritySummaryTopicDTOS = [
      ...this.createSecuritySummaryTopicsSaveObject(
        this.componentForm.getRawValue().defaultSecuritySummaryTopicsDTOs
      ),
      ...this.createSecuritySummaryTopicsSaveObject(
        this.componentForm.getRawValue().otherSecuritySummaryTopicsDTOs
      ),
      ...this.createSecuritySummaryTopicsSaveObject(
        this.componentForm.getRawValue().firstClassSecuritySummaryTopicsDTOs
      ),
    ];

    let saveData = Object.assign(
      {},
      this.fpSecuritySummeryDTO,
      { facilitySecuritySummaryType: this.facilitySecuritySummaryType },
      { fpSecuritySummaryTopicDTOS: fpSecuritySummaryTopicDTOS },
      {
        companySubTotalOne: this.subTotalFirstClassCompany,
        groupSubTotalOne: this.subTotalFirstClassGroup,
        companySubTotalTwo: this.subTotalFirstClassPlusOthersCompany,
        groupSubTotalTwo: this.subTotalFirstClassPlusOthersGroup,
        companyTotal: this.totalCompany,
        groupTotal: this.totalGroup,
        companySubTotalThree: this.subTotalOtherCompany,
        groupSubTotalThree: this.subTotalOtherCompanyGroup,
        companySubTotalFour: this.subTotalClean,
        groupSubTotalFour: this.subTotalCleanGroup,
      },
      { limitSummery: this.componentForm.getRawValue().limitSummery }
    );
    this.facilityPaperAddEditService.saveUpdateSecuritySummery(saveData);
  }

  removeDefaultSecuritySummaryTopic(item, index) {
    let fpSecuritySummary = item.value;
    fpSecuritySummary.status = Constants.statusConst.INA;
    this.confirmRemoveDefaultSecuritySummaryTopic(fpSecuritySummary, index);
  }

  confirmRemoveDefaultSecuritySummaryTopic(fpSecuritySummaryTopic, index) {
    this.modalRef = this.showConfirmationDialog(
      fpSecuritySummaryTopic.securityType
    );
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.removeDefaultSecuritySummaryTopicFormRow(
          fpSecuritySummaryTopic,
          index
        );
        this.updateSecuritySummaryTopic(fpSecuritySummaryTopic);
      }
    });
  }

  removeOtherSecuritySummaryTopic(item, index) {
    let fpSecuritySummary = item.value;
    fpSecuritySummary.status = Constants.statusConst.INA;
    this.confirmRemoveOtherSecuritySummaryTopic(fpSecuritySummary, index);
  }

  confirmRemoveOtherSecuritySummaryTopic(fpSecuritySummaryTopic, index) {
    this.modalRef = this.showConfirmationDialog(
      fpSecuritySummaryTopic.securityType
    );
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.removeOtherSecuritySummaryTopicFormRow(
          fpSecuritySummaryTopic,
          index
        );
        this.updateSecuritySummaryTopic(fpSecuritySummaryTopic);
      }
    });
  }

  removeFirstClassSecuritySummaryTopic(item, index) {
    let fpSecuritySummary = item.value;
    fpSecuritySummary.status = Constants.statusConst.INA;
    this.confirmRemoveFirstClassSecuritySummaryTopic(fpSecuritySummary, index);
  }

  confirmRemoveFirstClassSecuritySummaryTopic(fpSecuritySummaryTopic, index) {
    this.modalRef = this.showConfirmationDialog(
      fpSecuritySummaryTopic.securityType
    );
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.removeFirstClassSecuritySummaryTopicFormRow(
          fpSecuritySummaryTopic,
          index
        );
        this.updateSecuritySummaryTopic(fpSecuritySummaryTopic);
      }
    });
  }

  showConfirmationDialog(topic) {
    return (this.modalRef = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Remove Security Summary Type",
          message: "Do you want to remove " + topic + " ?",
        },
      }
    ));
  }

  updateSecuritySummaryTopic(fpSecuritySummaryTopic) {
    let updateDate = Object.assign(
      {},
      this.fpSecuritySummeryDTO,
      {
        fpSecuritySummaryTopicDTOS: [
          this.createTopicSaveObject(fpSecuritySummaryTopic),
        ],
      },
      {
        companySubTotalOne: this.subTotalFirstClassCompany,
        groupSubTotalOne: this.subTotalFirstClassGroup,
        companySubTotalTwo: this.subTotalFirstClassPlusOthersCompany,
        groupSubTotalTwo: this.subTotalFirstClassPlusOthersGroup,
        companyTotal: this.totalCompany,
        groupTotal: this.totalGroup,
        companySubTotalThree: this.subTotalOtherCompany,
        groupSubTotalThree: this.subTotalOtherCompanyGroup,
        companySubTotalFour: this.subTotalClean,
        groupSubTotalFour: this.subTotalCleanGroup,
      }
    );

    this.facilityPaperAddEditService.updateSecuritySummaryTopic(updateDate);
  }

  validateNumber(event) {
    NumberValidator.validateNumber(event);
  }

  getFloatValue(value): number {
    let formattedValue = this.getValue(value ? value : "");
    return AppUtils.getFloatValue(formattedValue);
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, "", "", "1.3-3");
  }

  getValue(amount) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  createSecuritySummaryTopicsSaveObject(securitySummaryTopics) {
    let topics = [];
    securitySummaryTopics.forEach((data) => {
      topics.push({
        ...data,
        companyValue: this.getValue(data.companyValue),
        companyPercentage: data.companyPercentage,
        groupValue: this.getValue(data.groupValue),
        groupPercentage: data.groupPercentage,
        //otherCompanyValue : this.getValue(data.otherCompanyValue)
      });
    });
    return topics;
  }

  createTopicSaveObject(securitySummaryTopic) {
    return {
      ...securitySummaryTopic,
      companyValue: this.getValue(securitySummaryTopic.companyValue),
      companyPercentage: securitySummaryTopic.companyPercentage,
      groupValue: this.getValue(securitySummaryTopic.groupValue),
      groupPercentage: securitySummaryTopic.groupPercentage,
    };
  }

  setFacilityGroupSecurity() {
    this.facilitySecuritySummaryType =
      Constants.facilitySecuritySummaryTypeConst.GROUP;
  }

  setFacilityIndividualSecurity() {
    this.facilitySecuritySummaryType =
      Constants.facilitySecuritySummaryTypeConst.INDIVIDUAL;
  }

  isGroupSecuritySummary() {
    return (
      this.facilitySecuritySummaryType ===
      Constants.facilitySecuritySummaryTypeConst.GROUP
    );
  }

  updateFormSubscription() {
    this.onSecuritySummeryFormChangeSub.unsubscribe();
    this.onSecuritySummeryFormChangeSub =
      this.componentForm.valueChanges.subscribe((res) => {
        let firstClassCompanyTotal: number = 0;
        let firstClassGroupTotal: number = 0;

        let otherCompanyTotal: number = 0;
        let otherGroupTotal: number = 0;

        let defaultCompanyTotal: number = 0;
        let defaultGroupTotal: number = 0;

        res["firstClassSecuritySummaryTopicsDTOs"].forEach((data: any) => {
          if (this.getFloatValue(data.companyValue)) {
            firstClassCompanyTotal =
              firstClassCompanyTotal + this.getFloatValue(data.companyValue);
          }
          if (this.getFloatValue(data.groupValue)) {
            firstClassGroupTotal =
              firstClassGroupTotal + this.getFloatValue(data.groupValue);
          }
        });

        res["otherSecuritySummaryTopicsDTOs"].forEach((data: any) => {
          if (this.getFloatValue(data.companyValue)) {
            otherCompanyTotal =
              otherCompanyTotal + this.getFloatValue(data.companyValue);
          }
          if (this.getFloatValue(data.groupValue)) {
            otherGroupTotal =
              otherGroupTotal + this.getFloatValue(data.groupValue);
          }
        });

        res["defaultSecuritySummaryTopicsDTOs"].forEach((data: any) => {
          if (this.getFloatValue(data.companyValue)) {
            defaultCompanyTotal =
              defaultCompanyTotal + this.getFloatValue(data.companyValue);
          }
          if (this.getFloatValue(data.groupValue)) {
            defaultGroupTotal =
              defaultGroupTotal + this.getFloatValue(data.groupValue);
          }
        });

        this.subTotalFirstClassCompany = this.getFloatValue(
          firstClassCompanyTotal
        );
        this.subTotalOtherCompany = this.getFloatValue(otherCompanyTotal);
        this.subTotalClean = this.getFloatValue(defaultCompanyTotal);
        this.subTotalFirstClassPlusOthersCompany =
          this.getFloatValue(firstClassCompanyTotal) +
          this.getFloatValue(otherCompanyTotal);
        this.totalCompany =
          this.getFloatValue(firstClassCompanyTotal) +
          this.getFloatValue(otherCompanyTotal) +
          this.getFloatValue(defaultCompanyTotal);

        this.subTotalFirstClassGroup = this.getFloatValue(firstClassGroupTotal);
        this.subTotalOtherCompanyGroup = this.getFloatValue(otherGroupTotal);
        this.subTotalCleanGroup = this.getFloatValue(defaultGroupTotal);
        this.subTotalFirstClassPlusOthersGroup =
          this.getFloatValue(firstClassGroupTotal) +
          this.getFloatValue(otherGroupTotal);
        this.totalGroup =
          this.getFloatValue(firstClassGroupTotal) +
          this.getFloatValue(otherGroupTotal) +
          this.getFloatValue(defaultGroupTotal);

        this.calculateTotalPercentages();
      });

    this.onSecuritySummeryFormChangeSub = this.componentForm
      .get("firstClassSecurityType")
      .valueChanges.subscribe((res) => {
        if (!_.isEmpty(res)) {
          this.addFirstClassSecuritySummaryTopicFormRow(res);
          this.optionsFirstClassSecuritySummaryTopics =
            this.getSecuritySummaryOptions(
              this.optionsFirstClassSecuritySummaryTopics,
              res
            );
        }
      });

    this.onSecuritySummeryFormChangeSub = this.componentForm
      .get("otherSecurityType")
      .valueChanges.subscribe((res) => {
        if (!_.isEmpty(res)) {
          this.addOtherSecuritySummaryTopicFormRow(res);
          this.optionsOtherSecuritySummaryTopics =
            this.getSecuritySummaryOptions(
              this.optionsOtherSecuritySummaryTopics,
              res
            );
        }
      });

    this.onSecuritySummeryFormChangeSub = this.componentForm
      .get("defaultSecurityType")
      .valueChanges.subscribe((res) => {
        if (!_.isEmpty(res)) {
          this.addDefaultSecuritySummaryTopicFormRow(res);
          this.optionsDefaultSecuritySummaryTopics =
            this.getSecuritySummaryOptions(
              this.optionsDefaultSecuritySummaryTopics,
              res
            );
        }
      });
  }

  getGroupPercentage(form, controlName, type) {
    const amount = this.mnCurrencyService.getAmountFromFormattedString(
      form.getRawValue()[controlName]
    );
    let percentage;

    if (type == "COMPANY" && this.totalCompany > 0) {
      percentage =
        AppUtils.roundUp(
          (this.getFloatValue(amount) / this.getFloatValue(this.totalCompany)) *
            100,
          2
        ).toFixed(2) + "%";
    } else if (type == "GROUP" && this.totalGroup > 0) {
      percentage =
        AppUtils.roundUp(
          (this.getFloatValue(amount) / this.getFloatValue(this.totalGroup)) *
            100,
          2
        ).toFixed(2) + "%";
    }
    return percentage;
  }

  isAbleToUpdate() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !=
        this.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !=
        this.facilityPaperStatusConst.REJECTED &&
      ((this.facilityPaper.createdBy ==
        this.applicationService.getLoggedInUserUserName() &&
        this.facilityPaper.currentFacilityPaperStatus ==
          this.facilityPaperStatusConst.DRAFT) ||
        (this.facilityPaper.currentAssignUserID ==
          this.applicationService.getLoggedInUserUserID() &&
          this.hasPrivilege)) &&
      !this.isPreviewMode
    );
  }

  setInitialAddedSecurityTopics(fpSecuritySummeryDTO) {
    this.optionsFirstClassSecuritySummaryTopics =
      this.firstClassSecuritySummaryTopics;
    this.optionsOtherSecuritySummaryTopics = this.otherSecuritySummaryTopics;
    this.optionsDefaultSecuritySummaryTopics =
      this.defaultSecuritySummaryTopics;

    if (!_.isEmpty(fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS)) {
      fpSecuritySummeryDTO.fpSecuritySummaryTopicDTOS.forEach(
        (securityTopic, index) => {
          switch (securityTopic.securityTypeGroup) {
            case this.facilitySecuritySummaryTypeGroupConst.FIRST_CLASS:
              this.optionsFirstClassSecuritySummaryTopics =
                this.getSecuritySummaryOptions(
                  this.optionsFirstClassSecuritySummaryTopics,
                  securityTopic.securityType
                );
              break;
            case this.facilitySecuritySummaryTypeGroupConst.OTHER:
              this.optionsOtherSecuritySummaryTopics =
                this.getSecuritySummaryOptions(
                  this.optionsOtherSecuritySummaryTopics,
                  securityTopic.securityType
                );
              break;
            case this.facilitySecuritySummaryTypeGroupConst.DEFAULT:
              this.optionsDefaultSecuritySummaryTopics =
                this.getSecuritySummaryOptions(
                  this.optionsDefaultSecuritySummaryTopics,
                  securityTopic.securityType
                );
              break;
          }
        }
      );
    }
  }

  addContent($event, content?) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(
      CommonPopupWithTinyMceEditorComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-lg",
        containerClass: "",
        animated: false,
        data: {
          isSaveAndCloseEnabled: true,
          content: {
            header: "Summary Remark",
            dataToEdit: content ? content : "",
          },
        },
      }
    );

    this.modalRef.content.action.subscribe((data: any) => {
      this.componentForm.patchValue({ limitSummery: data });
      this.saveUpdateSecuritySummery();
    });
  }

  getSecuritySummaryOptions(securitySummaryTopics, topic) {
    return _.reject(securitySummaryTopics, ["securityType", topic]);
  }

  getFirstClassOptions() {
    return this.optionsFirstClassSecuritySummaryTopics;
  }

  getOtherOptions() {
    return this.optionsOtherSecuritySummaryTopics;
  }

  getDefaultOptions() {
    return this.optionsDefaultSecuritySummaryTopics;
  }

  onTyping(event) {
    this.componentForm.get("limitSummery").setValue(event);
  }

  getDataWithPreTag(data: string) {
    return `<pre class="data-with-pre-tag">${data || "-"}</pre>`;
  }
}
