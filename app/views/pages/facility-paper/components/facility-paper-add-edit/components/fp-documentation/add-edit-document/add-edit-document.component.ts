import { Component, OnDestroy, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { Constants } from "../../../../../../../../core/setting/constants";
import { Subject, Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { CacheService } from "../../../../../../../../core/service/data/cache.service";
import { AlertService } from "../../../../../../../../core/service/common/alert.service";
import {
  IMyOptions,
  MDBModalRef,
  MDBModalService,
} from "ng-uikit-pro-standard";
import { CurrencyPipe } from "@angular/common";
import { MasterDataService } from "../../../../../../../../core/service/data/master-data.service";
import { AppUtils } from "../../../../../../../../shared/app.utils";
import { NumberValidator } from "../../../../../../../../shared/validators/number.validator";
import { NumberToWordsPipe } from "../../../../../../../../shared/pipes/number-to-words.pipe";
import * as _ from "lodash";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { isEmpty } from "lodash";

@Component({
  selector: "add-edit-document",
  templateUrl: "./add-edit-document.component.html",
  styleUrls: ["./add-edit-document.component.scss"],
})
export class AddEditDocumentComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  facilityPaper: any = {};
  selectedDocumentElement: any = {};
  inputTagElementList = [];
  fpDocumentElementDTOList = [];
  fpDocumentElementDTOListAll = [];
  selectedDocumentContent: any = "";
  initialDocumentContent: any = "";
  facilityDocumentList = [];
  enableEditDraftDoc = false;
  formErrors: any;
  componentForm: FormGroup;
  mainComponentForm: FormGroup;
  regexp = /{\[.*?\]}/g;
  replaceRegexp = /<(.*?)>/g;
  formDynamicFields = [];
  mainFormDynamicFields = [];
  formDataObj = {};
  formDataObjTemp = {};
  mainFormDataObj = {};
  inputMaxChars = 82;
  fpSecurityDocumentDTOList = [];
  draftedFpSecurityDocumentDTOList = [];
  draftedFpSecurityDocumentTagValueList = {};
  commonOptionList: any[] = [];
  leasingActClausesList: any[] = [];
  facilityTypeOptionList: any[] = [];
  facilityTypeTag = "FACILITY_TYPE";
  loggedInUserDisplayName = "";
  securityDocumentHistoryDTOList = [];
  securityDocumentHistoryActionList = [];
  showHistoryTable = false;
  showSubDynamicForm = false;
  showMainDynamicForm = false;
  creditCalculatorResponse = new Subscription();
  creditCalculatorData: any = [];
  creditScheduleData: any = [];
  slvData: any = [];
  paymentDetailsObject: any = [];
  stipulatedLossValueObject: any = [];
  content: any = {};
  onFormChangeSub = new Subscription();
  facilitySecurityDTOList: any = [];
  commaSeperatedNumberInput = 0;
  datePickerOptions: IMyOptions = {
    dateFormat: "dd-mm-yyyy",
    closeAfterSelect: true,
  };
  allBankOptions: any = {};

  action: Subject<any> = new Subject<any>();
  covenantsList: any[] = [];
  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private cacheService: CacheService,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private mdbModalService: MDBModalService,
    public mdbModalRef: MDBModalRef,
    private currencyPipe: CurrencyPipe,
    private numberToWordsPipe: NumberToWordsPipe,
    private masterDataService: MasterDataService
  ) {}

  ngOnInit() {
    this.facilityPaper = this.content.facilityPaper;
    this.enableEditDraftDoc = this.content.enableEditDraftDoc;
    this.selectedDocumentElement = this.content.selectedDocumentElement;
    this.loggedInUserDisplayName = this.content.loggedInUserDisplayName;
    this.showHistoryTable = this.content.showHistoryTable;

    if (this.showHistoryTable) {
      this.securityDocumentHistoryDTOList =
        this.content.securityDocumentHistoryDTOList;
      this.createActionHistoryLog(this.securityDocumentHistoryDTOList);
    } else {
      if (this.enableEditDraftDoc) {
        this.showMainDynamicForm = false;
        this.showSubDynamicForm = true;
        this.facilityDocumentList = this.content.facilityDocumentList;
        this.initialDocumentContent = _.filter(
          this.facilityDocumentList,
          (ad) => ad.elementID == this.selectedDocumentElement.elementID
        )[0].documentContent;
        this.getInputTagElementList(this.initialDocumentContent);
        this.componentForm = this.createEditDraftFormFields();

        if (this.selectedDocumentElement.facilityID !== null) {
          this.getCusApplicableCovenantList(
            this.selectedDocumentElement.facilityID
          );
        }
      } else {
        this.showMainDynamicForm = true;
        this.draftedFpSecurityDocumentDTOList =
          this.content.draftedFpSecurityDocumentDTOList;
        this.facilityTypeOptionList = this.content.facilityTypeOptionList;
        this.selectedDocumentContent =
          this.selectedDocumentElement.documentContent;
        this.getInputTagElementList(this.selectedDocumentContent);
        this.mainComponentForm = this.createMainDynamicFormFields(
          this.inputTagElementList
        );
      }
    }
  }

  createActionHistoryLog(securityDocumentHistoryDTOList) {
    let user = "";
    let branchDept = "";
    let actionDate = "";
    let action = "";
    let comment = "";
    let documentName = "";
    let documentStatus = "";

    _.forEach(securityDocumentHistoryDTOList, (ad) => {
      documentName = ad.documentName;
      documentStatus = ad.documentStatus;

      if (ad.documentStatus == "APPROVE" && ad.printedBy != null) {
        user = ad.printedByDisplayName;
        branchDept = ad.printedByDiv;
        actionDate = ad.printedDateStr;
        comment = "";
        action = "Printed by " + user;
        documentStatus = "PRINT";
        this.securityDocumentHistoryActionList.push({
          documentName: documentName,
          user: user,
          branchDept: branchDept,
          actionDate: actionDate,
          action: action,
          comment: comment,
          documentStatus: documentStatus,
        });
      } else {
        switch (ad.documentStatus) {
          case "DRAFT":
            user = ad.savedByDisplayName;
            branchDept = ad.savedByDiv;
            actionDate = ad.savedDateStr;
            comment = "";
            action = "Drafted by " + user;
            break;
          case "SUBMIT":
            user = ad.savedByDisplayName;
            branchDept = ad.savedByDiv;
            actionDate = ad.savedDateStr;
            comment = "";
            action = "Submitted by " + user;
            break;
          case "DELETE":
            user = ad.savedByDisplayName;
            branchDept = ad.savedByDiv;
            actionDate = ad.savedDateStr;
            comment = "";
            action = "Deleted by " + user;
            break;
          case "APPROVE":
            user = ad.authByDisplayName;
            branchDept = ad.authByDiv;
            actionDate = ad.authDateStr;
            comment = "";
            action = "Recommended & Forwarded by " + user;
            break;
          case "RETURN":
            user = ad.authByDisplayName;
            branchDept = ad.authByDiv;
            actionDate = ad.authDateStr;
            comment = ad.returnComment;
            action = "Returned by " + user;
            break;
        }
        this.securityDocumentHistoryActionList.push({
          documentName: documentName,
          user: user,
          branchDept: branchDept,
          actionDate: actionDate,
          action: action,
          comment: comment,
          documentStatus: documentStatus,
        });
      }
    });
  }

  createEditDraftFormFields() {
    let label = "";
    let isDisabled = false;

    _.sortBy(this.selectedDocumentElement.fpSecurityDocumentTagDataDTOList, [
      "tagOrder",
    ]).forEach((tagData) => {
      label = _.startCase(_.toLower(tagData.tag.replace(/_/g, " ")));
      //let tagValue;
      let tagValue: any = [];
      if (tagData.fieldType == "DATE") {
        if (tagData.tag == "LETTER_DATE_FORMAT_1") {
          tagData.tagValue = this.formatDateToDefault(tagData.tagValue);
        } else {
          if (tagData.tagValue != null) {
            if (tagData.tagValue.toString().trim() == "") {
              tagValue = tagData.tagValue.toString().trim();
            }
          } else {
            tagValue = tagData.tagValue;
          }
        }
      } else {
        if (tagData.fieldType == "SELECT") {
          // if (tagData.tag == "INCOME_SOURCE"){
          let casVariable = _.filter(
            this.inputTagElementList,
            (ad) => ad.fieldLabel == tagData.tag
          )[0].casVariableName;
          let splitField = casVariable.split("|");
          for (let i = 0; i < splitField.length; i++) {
            tagValue.push({
              value: splitField[i],
              label: splitField[i],
            });
          }
          //  }
        } else {
          tagValue = tagData.tagValue;
        }

        /* if (tagData.tag == "UPFRONT_RENTALS"){
                tagValue = tagValue.replace("1<sup>st</sup>","1st");
              }*/
      }

      if (tagData.tag != this.facilityTypeTag) {
        this.formDynamicFields.push({
          key: tagData.tag,
          fieldLabel: label,
          fieldType: tagData.fieldType,
          //   fieldType: tagData.fieldType == "SELECT" ? "TEXT" : tagData.fieldType,
          fieldValue: tagValue,
          tagType: tagData.tagType,
          tagID: tagData.tagID,
          tagOrder: tagData.tagOrder,
          tag: tagData.tag,
          securityDocumentID: tagData.securityDocumentID,
        });

        //Ignore User input optional
        if (
          tagData.tagType != "UO" &&
          tagData.tagType != "HO" &&
          tagData.tagType != "CO" &&
          tagData.tagValue != "" &&
          tagData.tagValue != null
        ) {
          this.formErrors = { ...this.formErrors, [tagData.tag]: {} };
        }
        this.formDataObj[tagData.tag] = new FormControl(
          { value: tagData.tagValue, disabled: isDisabled },
          tagData.tagType == "UO" ||
          tagData.tagType == "HO" ||
          tagData.tagType == "CO" ||
          tagValue == "" ||
          tagValue == null
            ? null
            : [Validators.required]
        );
      }
    });

    this.componentForm = new FormGroup(this.formDataObj);
    this.onFormChangeSub.unsubscribe();
    this.onFormChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(
        this.componentForm,
        this.formErrors
      );
    });
    return this.componentForm;
  }

  getDraftedDocumentTagListForFacility(facilityData) {
    _.forEach(this.draftedFpSecurityDocumentDTOList, (doc) => {
      if (doc.facilityID == facilityData.facilityID) {
        _.forEach(doc.fpSecurityDocumentTagDataDTOList, (tag) => {
          if (tag.tagValue != null && tag.tagType != "T") {
            this.draftedFpSecurityDocumentTagValueList = {
              ...this.draftedFpSecurityDocumentTagValueList,
              [tag.tag]: {
                tagValue: tag.tagValue,
              },
            };
          }
        });
      }
    });
  }

  async createSubDynamicFormFields(event) {
    let label = "";
    let selectedFacilityID = event.facilityID;
    let selectedDocumentName = event.documentName;
    let facilityData: any = {};
    this.formDataObj = {};
    this.formDataObjTemp = {};
    this.formDynamicFields = [];
    this.mainComponentForm.disable();

    if (selectedFacilityID !== null) {
      this.getCusApplicableCovenantList(selectedFacilityID);
    }

    facilityData = _.filter(
      this.facilityPaper.approvedFacilityDTOList,
      (f) => f.facilityID == selectedFacilityID
    )[0];
    this.getDraftedDocumentTagListForFacility(facilityData);
    this.facilitySecurityDTOList = facilityData.facilitySecurityDTOList || [];

    if (event.facilityName != "Loan") {
      await this.getCreditCalculatorData(facilityData);
    }

    //if ((selectedDocumentName == "Lease Agreement") || (selectedDocumentName == "Offer Letter") || (selectedDocumentName == "Acceptance Receipt")){
    // await this.getCreditCalculatorData(facilityData);
    //}

    _.forEach(this.inputTagElementList, (element) => {
      this.formDataObjTemp = {
        ...this.formDataObjTemp,
        [element.fieldLabel]: {
          fieldLabel: element.fieldLabel,
          fieldType: element.fieldType,
          tagType: element.tagType,
          casVariableName: element.casVariableName,
        },
      };
    });

    for (const prop of Object.keys(this.formDataObjTemp)) {
      let casVariable = "";
      let casData: any = [];
      let isDisabled = false;
      let draftedTagValue: any = {};

      if (this.formDataObjTemp[prop].tagType == "T") {
        casData =
          this.facilityPaper.fpRefNumber +
          "_F" +
          facilityData.displayOrder +
          "_" +
          _.replace(this.selectedDocumentElement.elementName, " ", "_");
      }

      draftedTagValue =
        this.draftedFpSecurityDocumentTagValueList[
          this.formDataObjTemp[prop].fieldLabel
        ];

      if (draftedTagValue !== undefined) {
        if (this.formDataObjTemp[prop].fieldType == "SELECT") {
          /* if (this.formDataObjTemp[prop].fieldLabel == "UPFRONT_RENTALS"){
                  draftedTagValue.tagValue = draftedTagValue.tagValue.replace("1<sup>st</sup>","1st");
              }*/

          casData.push({
            value: draftedTagValue.tagValue,
            label: draftedTagValue.tagValue,
          });
        } else {
          if (this.formDataObjTemp[prop].fieldType == "DATE") {
            if (draftedTagValue.tagValue.toString().trim() == "") {
              casData = draftedTagValue.tagValue.toString().trim();
            } else {
              casData = draftedTagValue.tagValue;
            }
          } else {
            casData = draftedTagValue.tagValue;
          }

          if (
            this.formDataObjTemp[prop].fieldLabel == "LETTER_DATE_FORMAT_1" &&
            casData != null
          ) {
            casData = this.formatDateToDefault(casData);
          }
        }
      } else {
        if (
          this.formDataObjTemp[prop].tagType == "U" ||
          this.formDataObjTemp[prop].tagType == "UO"
        ) {
          //UO- User input Optional
          casVariable = this.formDataObjTemp[prop].casVariableName;

          if (
            this.formDataObjTemp[prop].tagType == "UO" &&
            this.formDataObjTemp[prop].fieldType == "TEXT"
          ) {
            casData = casVariable;
          }

          if (this.formDataObjTemp[prop].fieldType == "SELECT") {
            let splitField = casVariable.split("|");
            for (let i = 0; i < splitField.length; i++) {
              casData.push({
                value: splitField[i],
                label: splitField[i],
              });
            }
          }
        }

        if (
          this.formDataObjTemp[prop].tagType == "C" ||
          this.formDataObjTemp[prop].tagType == "CO"
        ) {
          casVariable = this.formDataObjTemp[prop].casVariableName;
          // if (casVariable == "getSystemDate"){
          //   casData = this.constructSystemDateOutput(prop)[0];
          // }else{
          let splitField = casVariable.split("|");
          for (let i = 0; i < splitField.length; i++) {
            if (i == 0) {
              if (splitField[i] == "paymentDetailsObject") {
                casData = this.paymentDetailsObject;
              } else if (splitField[i] == "stipulatedLossValueObject") {
                casData = this.stipulatedLossValueObject;
              } else {
                casData = this.facilityPaper[splitField[i]];
              }
            } else {
              if (splitField[i - 1] == "approvedFacilityDTOList") {
                casData = _.filter(
                  casData,
                  (ad) => ad.facilityID == selectedFacilityID
                );
              }
              if (splitField[i] == "INDIRECT") {
                if (casData !== undefined && casData.length > 0) {
                  casData = _.filter(
                    casData,
                    (ad) => ad[splitField[i + 1]] == splitField[i + 2]
                  );
                  if (casData.length > 0) {
                    casData = casData[0][splitField[i + 3]];
                  } else {
                    casData = "";
                  }
                  break;
                } else {
                  casData = "";
                  break;
                }
              } else {
                let casFieldLabel = this.formDataObjTemp[prop].fieldLabel;
                let casDataValue = "";
                const j1Fields = [
                  "CUSTOMER_NIC_J1",
                  "NAME_WITH_INITIALS_J1",
                  "CUSTOMER_FULL_NAME_J1",
                  "CUSTOMER_ADDRESS_1_J1",
                  "CUSTOMER_ADDRESS_2_J1",
                  "CUSTOMER_CITY_J1",
                  "TITLE_J1",
                  "SALUTATION_J1",
                ];
                const j2Fields = [
                  "CUSTOMER_NIC_J2",
                  "NAME_WITH_INITIALS_J2",
                  "CUSTOMER_FULL_NAME_J2",
                  "CUSTOMER_ADDRESS_1_J2",
                  "CUSTOMER_ADDRESS_2_J2",
                  "CUSTOMER_CITY_J2",
                  "TITLE_J2",
                  "SALUTATION_J2",
                ];
                const j3Fields = [
                  "CUSTOMER_NIC_J3",
                  "NAME_WITH_INITIALS_J3",
                  "CUSTOMER_FULL_NAME_J3",
                  "CUSTOMER_ADDRESS_1_J3",
                  "CUSTOMER_ADDRESS_2_J3",
                  "CUSTOMER_CITY_J3",
                  "TITLE_J3",
                  "SALUTATION_J3",
                ];

                let jntIndex = 0;

                if (j1Fields.includes(casFieldLabel)) {
                  jntIndex = 1;
                } else if (j2Fields.includes(casFieldLabel)) {
                  jntIndex = 2;
                } else if (j3Fields.includes(casFieldLabel)) {
                  jntIndex = 3;
                } else {
                  jntIndex = 0;
                }

                if (
                  Array.isArray(casData) &&
                  casData[jntIndex] &&
                  casData[jntIndex][splitField[i]] !== undefined
                ) {
                  casDataValue = casData[jntIndex][splitField[i]];
                }
                if (splitField[i] == "identificationNumber" && casData[0]) {
                  casDataValue = casData[0][splitField[i]];
                }
                casData = casDataValue;
              }
            }
          }
          // }
        }
      }

      if (casData !== undefined) {
        let fieldLabel = this.formDataObjTemp[prop].fieldLabel;

        if (fieldLabel == "CUSTOMER_ACCOUNT_NO") {
          if (casData != null) {
            casData = casData.replace(/\d{4}(?=.)/g, "$& ");
          }
        }

        const capitalizeFields = [
          "CUSTOMER_NIC",
          "TITLE",
          "NAME_WITH_INITIALS",
          "CUSTOMER_FULL_NAME",
          "CUSTOMER_ADDRESS_1",
          "CUSTOMER_ADDRESS_2",
          "CUSTOMER_CITY",
          "SALUTATION",
          "CUSTOMER_NIC_J1",
          "TITLE_J1",
          "NAME_WITH_INITIALS_J1",
          "CUSTOMER_FULL_NAME_J1",
          "CUSTOMER_ADDRESS_1_J1",
          "CUSTOMER_ADDRESS_2_J1",
          "CUSTOMER_CITY_J1",
          "SALUTATION_J1",
          "CUSTOMER_NIC_J2",
          "TITLE_J2",
          "NAME_WITH_INITIALS_J2",
          "CUSTOMER_FULL_NAME_J2",
          "CUSTOMER_ADDRESS_1_J2",
          "CUSTOMER_ADDRESS_2_J2",
          "CUSTOMER_CITY_J2",
          "SALUTATION_J2",
          "CUSTOMER_NIC_J3",
          "TITLE_J3",
          "NAME_WITH_INITIALS_J3",
          "CUSTOMER_FULL_NAME_J3",
          "CUSTOMER_ADDRESS_1_J3",
          "CUSTOMER_ADDRESS_2_J3",
          "CUSTOMER_CITY_J3",
          "SALUTATION_J3",
          "PLACE_WHERE_KEPT_ADDRESS_1",
          "PLACE_WHERE_KEPT_ADDRESS_2",
          "PLACE_WHERE_KEPT_CITY",
        ];

        if (capitalizeFields.includes(fieldLabel)) {
          if (casData != null && casData.length != 0) {
            casData = casData.replace(/\w+/g, _.capitalize);
          }
        }

        label = this.formDataObjTemp[prop].fieldLabel.replace(/_/g, " ");
        label = _.startCase(_.toLower(label));

        if (prop == "FACILITY_AMOUNT") {
          casData = this.currencyPipe.transform(casData, "", "");
        }

        if (prop == "BRANCH_NAME") {
          casData = this.getBranchName(this.facilityPaper.branchCode);
        }

        this.formDynamicFields.push({
          key: prop,
          fieldLabel: label,
          fieldType: this.formDataObjTemp[prop].fieldType,
          fieldValue: casData,
          tagType: this.formDataObjTemp[prop].tagType,
        });

        if (
          this.formDataObjTemp[prop].tagType != "UO" &&
          this.formDataObjTemp[prop].tagType != "HO" &&
          this.formDataObjTemp[prop].tagType != "CO"
        ) {
          //Ignore User input optional //UO-User Input Optional/ HO- Hidden Input Optional / CO- CAS Input Optional
          this.formErrors = { ...this.formErrors, [prop]: {} };
        }

        if (this.formDataObjTemp[prop].fieldType == "SELECT") {
          // this.formDataObj[prop] = new FormControl({value:casData[0].value, disabled:isDisabled}, [Validators.required]);
          this.formDataObj[prop] = new FormControl(
            { value: "", disabled: isDisabled },
            [Validators.required]
          );
        } else {
          this.formDataObj[prop] = new FormControl(
            { value: casData, disabled: isDisabled },
            this.formDataObjTemp[prop].tagType == "UO" ||
            this.formDataObjTemp[prop].tagType == "HO" ||
            this.formDataObjTemp[prop].tagType == "CO"
              ? null
              : [Validators.required]
          );
        }
      }
    }

    this.componentForm = new FormGroup(this.formDataObj);
    this.onFormChangeSub.unsubscribe();
    this.onFormChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formErrors = AppUtils.getFormErrors(
        this.componentForm,
        this.formErrors
      );
    });
    this.showSubDynamicForm = true;
  }

  createMainDynamicFormFields(inputTagElementList) {
    let label = "";
    label = _.startCase(_.toLower(this.facilityTypeTag.replace(/_/g, " ")));
    this.mainFormDynamicFields.push({
      key: this.facilityTypeTag,
      fieldLabel: label,
      fieldType: "SELECT",
      fieldValue: this.facilityTypeOptionList,
      tagType: "U",
    });

    this.mainFormDataObj[this.facilityTypeTag] = new FormControl(
      { value: "", disabled: false },
      [Validators.required]
    );
    this.mainComponentForm = new FormGroup(this.mainFormDataObj);
    return this.mainComponentForm;
  }

  getInputTagElementList(selectedDocumentContent) {
    let matchAll = selectedDocumentContent.matchAll(this.regexp);
    matchAll = Array.from(matchAll);

    for (let i = 0; i < matchAll.length; i++) {
      let initialTagID = matchAll[i][0];
      let tagID = initialTagID.replace(this.replaceRegexp, "");
      let tagContent = tagID.substring(2, tagID.length - 2);
      let splitArr = tagContent.split(":");
      let tagType = splitArr[0];
      let fieldLabel = splitArr[1];
      let fieldType = splitArr[2];
      let casVariableName = splitArr[3];
      this.inputTagElementList.push({
        initialTagID: initialTagID,
        tagID: tagID,
        tagType: tagType,
        fieldLabel: fieldLabel,
        fieldType: fieldType,
        casVariableName: casVariableName,
      });
    }
  }

  saveInitialDocumentFields() {
    let updatedDocumentContent = this.bindCovenantToContent(
      this.selectedDocumentContent
    );
    let formDataValue = this.componentForm.getRawValue();
    let mainFormDataValue = this.mainComponentForm.getRawValue();
    let securityDocumentTagData = [];
    let saveDocumentName =
      "Facility " +
      mainFormDataValue[this.facilityTypeTag] +
      " - " +
      this.selectedDocumentElement.elementName +
      " ( " +
      this.facilityTypeOptionList[0].creditFacilityName +
      " )";
    //let repValue:any = "";
    //let repTag= "";
    let repValue: any = "";
    let repTag: any = "";
    let repTagType = ""; //-- C-CAS Data /U-User Input/ UO - User Input Optional
    let repTagLabel = "";
    let secDocID = null;
    let facilityID = null;
    let tagOrder = 0;
    let mainTag = "";
    let repaymentClause = "";

    for (const tag of Object.keys(mainFormDataValue)) {
      securityDocumentTagData.push(
        Object.assign(
          {},
          { tagOrder: 0 },
          { tag: tag },
          { fieldType: "TEXT" },
          { tagValue: mainFormDataValue[tag] },
          { tagType: "C" }
        )
      );

      _.forEach(this.facilityTypeOptionList, (ad) => {
        if (mainFormDataValue[tag] == ad.value) {
          facilityID = ad.facilityID;
        }
      });
    }

    _.forEach(this.inputTagElementList, (tag) => {
      repTagType = tag.tagType;
      tagOrder = tagOrder + 1;
      // repValue = (tag.fieldType == "AMOUNT" ? this.currencyPipe.transform(formDataValue[tag.fieldLabel],"","") : formDataValue[tag.fieldLabel]);
      repTag = tag.initialTagID;
      repTagLabel = tag.fieldLabel;

      if (
        repTagLabel == "DAY" ||
        repTagLabel == "MONTH_NAME" ||
        repTagLabel == "YEAR_IN_WORDS"
      ) {
        repValue = this.constructDateOutput(
          _.filter(
            securityDocumentTagData,
            (ad) => ad.tag == "AGREEMENT_DATE"
          )[0].tagValue,
          repTagLabel
        );
      } else {
        repValue = formDataValue[tag.fieldLabel];

        if (repTagLabel == "REPAYMENT_METHOD") {
          if (repValue == "EQUATED") {
            repaymentClause =
              "In " +
              formDataValue["REPAYMENT_PERIOD_IN_MONTHS"] +
              " equated monthly installments of Rs." +
              formDataValue["MONTHLY_INSTALMENT"] +
              " each (inclusive of interest) payable on the 05<sup>th</sup> day of each month. (The above installment has been calculated at the prevailing interest rate and the installment will be varied with the subsequent rate revisions if any)";
          }

          if (repValue == "EQUAL WITH FINAL INSTALMENT") {
            repaymentClause =
              "In " +
              formDataValue["REPAYMENT_PERIOD_IN_MONTHS"] +
              " equal monthly installments of Rs." +
              formDataValue["MONTHLY_INSTALMENT"] +
              " each and the final installment of Rs." +
              formDataValue["FINAL_INSTALMENT"] +
              " together with interest payable on the 05<sup>th</sup> day of each month";
          }

          if (repValue == "EQUAL WITHOUT FINAL INSTALMENT") {
            repaymentClause =
              "In " +
              formDataValue["REPAYMENT_PERIOD_IN_MONTHS"] +
              " monthly installments of Rs." +
              formDataValue["MONTHLY_INSTALMENT"] +
              " each together with interest payable on the 05<sup>th</sup> day of each month";
          }
          formDataValue["REPAYMENT"] = repaymentClause;
        }

        if (repTagLabel == "BRANCH_NAME") {
          repValue = _.startCase(_.toLower(repValue));
        }
        /*if (repTagLabel == "UPFRONT_RENTALS"){
                      repValue = repValue.replace("1st","1<sup>st</sup>");
                   }*/

        if (repTagLabel == "LETTER_DATE_FORMAT_1") {
          if (
            repValue != null &&
            repValue.length != 0 &&
            repValue != "NaN-NaN-NaN"
          ) {
            repValue = this.formatDate(repValue);
          } else {
            repValue = "                               ";
          }
        }

        if (
          tag.fieldType == "DATE" &&
          ((Array.isArray(repValue) && repValue.length == 0) ||
            repValue == null)
        ) {
          // repValue = "                                          ";
          /*if(repTagLabel == "LETTER_DATE"){
                        // repValue = "                               ";
                      repValue = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; // Non-breaking spaces

                      }else{
                        repValue = ".............................";
                      }*/
          repValue = ".............................";
        }

        if (
          repTagLabel == "NEW_REGISTRATION_NUMBER" &&
          ((Array.isArray(repValue) && repValue.length == 0) ||
            repValue == null ||
            repValue == "")
        ) {
          repValue = ".............................";
        }

        if (repTagType == "CO" && repValue == undefined) {
          repValue = "                        ";
        }
      }

      if (Array.isArray(repValue) || repValue == undefined) {
        repValue = "";
      }

      // if(repValue != ""){
      securityDocumentTagData.push(
        Object.assign(
          {},
          { tagOrder: tagOrder },
          { tag: repTagLabel },
          { tagValue: repValue },
          { fieldType: tag.fieldType },
          { tagType: repTagType }
        )
      );
      //   }

      // Remove clauses from Offer Letter Document
      const clausesToRemoveDocs = [
        "Offer Letter",
        "Guarantee Bond",
        "Joint Borrower Consent",
        "Lease Agreement",
      ];
      if (
        clausesToRemoveDocs.includes(this.selectedDocumentElement.elementName)
      ) {
        updatedDocumentContent = this.removeClausesInDocument(
          repTagLabel,
          repValue,
          updatedDocumentContent
        );
      }

      /*  if (this.selectedDocumentElement.elementName == "Offer Letter" || this.selectedDocumentElement.elementName == "Guarantee Bond"
                   || this.selectedDocumentElement.elementName == "Joint Borrower Consent" || this.selectedDocumentElement.elementName == "Lease Agreement" ){
                    updatedDocumentContent = this.removeClausesInDocument(repTagLabel,repValue,updatedDocumentContent);
                 }*/

      //replace tags
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        repTag,
        repValue
      );
    });

    if (this.selectedDocumentElement.fpSecurityDocumentDTOList.length > 0) {
      _.forEach(
        this.selectedDocumentElement.fpSecurityDocumentDTOList,
        (ad) => {
          if (
            saveDocumentName == ad.documentName &&
            ad.documentStatus != "DELETE"
          ) {
            secDocID = ad.securityDocumentID;
          }
        }
      );
    }

    securityDocumentTagData = _.uniqBy(securityDocumentTagData, "tag");

    let securityDocumentData = Object.assign(
      {},
      { facilityPaperID: this.facilityPaper.facilityPaperID },
      {
        creditFacilityTemplateID:
          this.selectedDocumentElement.creditFacilityTemplateID,
      },
      { creditFacilityName: this.selectedDocumentElement.creditFacilityName },
      { elementID: this.selectedDocumentElement.elementID },
      { securityDocumentID: secDocID },
      { facilityID: facilityID },
      { documentName: saveDocumentName },
      { savedByDisplayName: this.loggedInUserDisplayName },
      { documentStatus: "DRAFT" },
      { returnComment: null },
      { documentContent: updatedDocumentContent },
      { fpSecurityDocumentTagDataDTOList: securityDocumentTagData }
    );

    this.facilityPaperAddEditService
      .saveOrUpdateSecurityDocument(securityDocumentData)
      .then((data: any) => {
        data = Object.assign({}, data);
        this.action.next(data);
      });

    this.mdbModalRef.hide();
  }

  formatDate(inputDate) {
    const [day, month, year] = inputDate.split("-").map(Number);
    const months = [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December",
    ];
    const dayWithSuffix = this.getOrdinalDay(day);
    const monthName = months[month - 1];
    //Date Format -> Ex: 09th December 2024
    const formatDate = `${dayWithSuffix} ${monthName} ${year}`;
    return formatDate;
  }

  formatDateToDefault(inputDate) {
    const cleanDateString = inputDate.replace(/<[^>]+>/g, "");
    const cleanDateStr = cleanDateString.replace(/\d+(st|nd|rd|th)/, (match) =>
      match.slice(0, -2)
    );
    const date = new Date(cleanDateStr);
    //Default Date Format -> dd-mm-yyyy
    const defaultFormatDate = `${date.getDate().toString().padStart(2, "0")}-${(
      date.getMonth() + 1
    )
      .toString()
      .padStart(2, "0")}-${date.getFullYear()}`;
    return defaultFormatDate;
  }

  removeClausesInDocument(repTagLabel, repValue, updatedDocumentContent) {
    let htmlClauseToRemove = "";
    let initialDocumentContent;
    let optionalClauseCount = 15;
    let optionalClauseLabel = "";
    let optionalClauseTag = "";

    if (this.enableEditDraftDoc) {
      initialDocumentContent = this.initialDocumentContent;
    } else {
      initialDocumentContent = this.selectedDocumentContent;
    }

    //Samachara - Offer Letter - handle disbursed stage clauses
    if (repTagLabel == "STAGE_COUNT") {
      /* if (repValue == "00"){
               htmlClauseToRemove = initialDocumentContent.match('<span id="stage-clause">' + "(.*?)" +  '</li></span>')[1];
               updatedDocumentContent = updatedDocumentContent.replaceAll(htmlClauseToRemove, "");
            }else{*/
      for (let i = parseInt(repValue) + 1; i <= 3; i++) {
        optionalClauseLabel = "STAGE_0" + i;
        optionalClauseTag = "stage-0" + i;
        htmlClauseToRemove = updatedDocumentContent.match(
          '<div id="' + optionalClauseTag + '">' + "(.*?)" + "</div>"
        )[1];
        updatedDocumentContent = updatedDocumentContent.replaceAll(
          htmlClauseToRemove,
          ""
        );
      }
      /*   if (repValue == "02" || repValue == "03" ){
                  htmlClauseToRemove = updatedDocumentContent.match('<div id="draw-down-clause">' + "(.*?)" +  '</div>')[1];
                  updatedDocumentContent = updatedDocumentContent.replaceAll(htmlClauseToRemove, "");
              }*/
      //  }
    }

    // Offer Letter
    //Remove  optional clauses if the user didn't enter any value
    for (let i = 1; i <= optionalClauseCount; i++) {
      optionalClauseLabel = "OPTIONAL_CLAUSE_" + i;
      optionalClauseTag = "optional-clause-" + i;
      if (
        repTagLabel == optionalClauseLabel &&
        (repValue == "" || repValue == null)
      ) {
        htmlClauseToRemove = initialDocumentContent.match(
          '<span id="' + optionalClauseTag + '">' + "(.*?)" + "</li></span>"
        )[1];
        updatedDocumentContent = updatedDocumentContent.replaceAll(
          htmlClauseToRemove,
          ""
        );
      }
    }
    // Remove previous offer letter dates clause if the user didn't enter any value for PREVIOUS_OFFER_LETTER_DATES
    if (
      repTagLabel == "PREVIOUS_OFFER_LETTER_DATES" &&
      (repValue == "" || repValue == null)
    ) {
      htmlClauseToRemove = initialDocumentContent.match(
        '<p id="previous-offers-clause">' + "(.*?)" + "</p>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
    }

    //Remove Optional Security Statements if the user didn't enter any valuw for SECURITY_STATEMENT
    /* for (let i=2; i<=4; i++){
            optionalClauseLabel = "SECURITY_STATEMENT_" + i;
            optionalClauseTag = "optional-security-" + i;
            if (repTagLabel == optionalClauseLabel && (repValue == "" || repValue == null)){
              htmlClauseToRemove = initialDocumentContent.match('<span id="' + optionalClauseTag +'">' + "(.*?)" +  '</tr></span>')[1];
              updatedDocumentContent = updatedDocumentContent.replaceAll(htmlClauseToRemove, "");
            }
          }*/

    // Remove absolute owner details  if the user didn't enter any value for ABSOLUTE_OWNER_NAME and ABSOLUTE_OWNER_ADDRESS
    if (
      repTagLabel == "ABSOLUTE_OWNER_NAME" &&
      (repValue == "" || repValue == null)
    ) {
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="absolute-owner-details">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
    }
    //Remove commitment clauses when income level is salaried

    if (repTagLabel == "INCOME_SOURCE") {
      if (repValue == "Salaried") {
        updatedDocumentContent = this.clearElementContent(
          "business-cus",
          updatedDocumentContent
        );
      }
      if (repValue == "Business") {
        updatedDocumentContent = this.clearElementContent(
          "salaried-cus",
          updatedDocumentContent
        );
      }
    }

    //Guarantee Bond
    if (
      repTagLabel == "GUARANTOR_I_FULL_NAME" &&
      (repValue == "" || repValue == null)
    ) {
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-I-details">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-I-witness">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
    }

    if (
      repTagLabel == "GUARANTOR_II_FULL_NAME" &&
      (repValue == "" || repValue == null)
    ) {
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-II-details">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-II-witness">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
    }

    if (
      repTagLabel == "GUARANTOR_III_FULL_NAME" &&
      (repValue == "" || repValue == null)
    ) {
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-III-details">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-III-witness">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
    }

    if (
      repTagLabel == "GUARANTOR_IV_FULL_NAME" &&
      (repValue == "" || repValue == null)
    ) {
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-IV-details">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-IV-witness">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
    }

    if (
      repTagLabel == "GUARANTOR_V_FULL_NAME" &&
      (repValue == "" || repValue == null)
    ) {
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-V-details">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
      htmlClauseToRemove = initialDocumentContent.match(
        '<span id="guarantor-V-witness">' + "(.*?)" + "</span><br>"
      )[1];
      updatedDocumentContent = updatedDocumentContent.replaceAll(
        htmlClauseToRemove,
        ""
      );
    }

    //Joint Borrower Consent Letter
    updatedDocumentContent = this.removeJointAddressClause(
      repTagLabel,
      repValue,
      initialDocumentContent,
      updatedDocumentContent
    );

    return updatedDocumentContent;
  }

  removeJointAddressClause(
    repTagLabel,
    repValue,
    initialDocumentContent,
    updatedDocumentContent
  ) {
    const jointTags = ["SHOW_MAIN_BORROWER", "SHOW_JOINT_BORROWER"];
    let htmlClauseToRemove = "";
    let matchResult = "";
    if (jointTags.includes(repTagLabel) && repValue === "No") {
      matchResult = initialDocumentContent.match(
        '<span id="ADDRESS_' +
          repTagLabel +
          '">' +
          "(.*?)" +
          "</span><span></span>"
      );
      if (matchResult && matchResult[1]) {
        htmlClauseToRemove = matchResult[1];
        updatedDocumentContent = updatedDocumentContent.replaceAll(
          htmlClauseToRemove,
          ""
        );
      }

      matchResult = initialDocumentContent.match(
        '<span id="CLAUSE_' +
          repTagLabel +
          '">' +
          "(.*?)" +
          "</span><span></span>"
      );
      if (matchResult && matchResult[1]) {
        htmlClauseToRemove = matchResult[1];
        updatedDocumentContent = updatedDocumentContent.replaceAll(
          htmlClauseToRemove,
          ""
        );
      }

      matchResult = initialDocumentContent.match(
        '<span id="SIGNATURE_' +
          repTagLabel +
          '">' +
          "(.*?)" +
          "</span><span></span>"
      );
      if (matchResult && matchResult[1]) {
        htmlClauseToRemove = matchResult[1];
        updatedDocumentContent = updatedDocumentContent.replaceAll(
          htmlClauseToRemove,
          ""
        );
      }
    }

    return updatedDocumentContent;
  }

  saveDraftDocumentFields() {
    let formDataValue = this.componentForm.getRawValue();
    let updatedDocumentContent = this.initialDocumentContent;
    let securityDocumentTagData = [];
    let repValue = "";
    let repTag = "";
    let tagID = 0;
    let tagOrder = 0;
    let securityDocumentID = 0;
    let tagType = "";
    let tagLabel = "";
    let fieldType = "";
    let repaymentClause = "";

    for (const tag of Object.keys(formDataValue)) {
      tagID = _.filter(this.formDynamicFields, (ad) => ad.tag == tag)[0].tagID;
      tagOrder = _.filter(this.formDynamicFields, (ad) => ad.tag == tag)[0]
        .tagOrder;
      tagType = _.filter(this.formDynamicFields, (ad) => ad.tag == tag)[0]
        .tagType;
      securityDocumentID = _.filter(
        this.formDynamicFields,
        (ad) => ad.tag == tag
      )[0].securityDocumentID;
      tagLabel = _.filter(this.formDynamicFields, (ad) => ad.tag == tag)[0].tag;
      fieldType = _.filter(this.formDynamicFields, (ad) => ad.tag == tag)[0]
        .fieldType;

      if (tag != this.facilityTypeTag) {
        repTag =
          _.filter(this.inputTagElementList, (ad) => ad.fieldLabel == tag)
            .length > 0
            ? _.filter(
                this.inputTagElementList,
                (ad) => ad.fieldLabel == tag
              )[0].initialTagID
            : null;
        if (
          tagLabel == "DAY" ||
          tagLabel == "MONTH_NAME" ||
          tagLabel == "YEAR_IN_WORDS"
        ) {
          repValue = this.constructDateOutput(
            _.filter(
              securityDocumentTagData,
              (ad) => ad.tag == "AGREEMENT_DATE"
            )[0].tagValue,
            tagLabel
          );
        } else {
          repValue = formDataValue[tag];

          if (tagLabel == "BRANCH_NAME") {
            repValue = _.startCase(_.toLower(repValue));
          }
          /*if (tagLabel == "UPFRONT_RENTALS"){
                     repValue = repValue.replace("1st","1<sup>st</sup>");
                  }*/
          //  if (tag.fieldType == "DATE" && ((Array.isArray(repValue) && repValue.length == 0) || (repValue == null))){
          if (tagLabel == "LETTER_DATE_FORMAT_1") {
            if (
              repValue != null &&
              repValue.length != 0 &&
              repValue != "NaN-NaN-NaN"
            ) {
              repValue = this.formatDate(repValue);
            } else {
              repValue = "                               ";
            }
          }

          if (
            fieldType == "DATE" &&
            ((Array.isArray(repValue) && repValue.length == 0) ||
              repValue == null)
          ) {
            // repValue = "                                          ";
            /* if(tagLabel == "LETTER_DATE"){
                     //   repValue = "                               ";
                      repValue = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; // Non-breaking spaces
                     }else{
                       repValue = ".............................";
                     }*/
            repValue = ".............................";
          }

          if (
            tagLabel == "NEW_REGISTRATION_NUMBER" &&
            ((Array.isArray(repValue) && repValue.length == 0) ||
              repValue == null ||
              repValue == "")
          ) {
            repValue = ".............................";
          }

          if (tagLabel == "REPAYMENT_METHOD") {
            if (repValue == "EQUATED") {
              repaymentClause =
                "In " +
                formDataValue["REPAYMENT_PERIOD_IN_MONTHS"] +
                " equated monthly installments of Rs." +
                formDataValue["MONTHLY_INSTALMENT"] +
                " each (inclusive of interest) payable on the 05<sup>th</sup> day of each month. (The above installment has been calculated at the prevailing interest rate and the installment will be varied with the subsequent rate revisions if any)";
            }

            if (repValue == "EQUAL WITH FINAL INSTALMENT") {
              repaymentClause =
                "In " +
                formDataValue["REPAYMENT_PERIOD_IN_MONTHS"] +
                " equal monthly installments of Rs." +
                formDataValue["MONTHLY_INSTALMENT"] +
                " each and the final installment of Rs." +
                formDataValue["FINAL_INSTALMENT"] +
                " together with interest payable on the 05<sup>th</sup> day of each month";
            }

            if (repValue == "EQUAL WITHOUT FINAL INSTALMENT") {
              repaymentClause =
                "In " +
                formDataValue["REPAYMENT_PERIOD_IN_MONTHS"] +
                " equal monthly installments of Rs." +
                formDataValue["MONTHLY_INSTALMENT"] +
                " each together with interest payable on the 05<sup>th</sup> day of each month";
            }
            formDataValue["REPAYMENT"] = repaymentClause;
          }

          if (tagType == "CO" && repValue == undefined) {
            repValue = "                        ";
          }
        }
        updatedDocumentContent = this.removeClausesInDocument(
          tagLabel,
          repValue,
          updatedDocumentContent
        );
        if (repTag !== null) {
          updatedDocumentContent = updatedDocumentContent.replaceAll(
            repTag,
            repValue
          );
        }
      } else {
        repValue = _.filter(this.formDynamicFields, (ad) => ad.tag == tag)[0]
          .fieldValue;
      }

      //    updatedDocumentContent = this.removeClausesInDocument(tagLabel,repValue,updatedDocumentContent);

      securityDocumentTagData.push(
        Object.assign(
          {},
          { tagID: tagID },
          { tagValue: repValue },
          { tagOrder: tagOrder },
          { fieldType: fieldType == null ? "TEXT" : fieldType },
          { tagType: tagType },
          { securityDocumentID: securityDocumentID },
          { tag: tag }
        )
      );
    }

    let securityDocumentData = Object.assign(
      {},
      { facilityPaperID: this.facilityPaper.facilityPaperID },
      {
        creditFacilityTemplateID:
          this.selectedDocumentElement.creditFacilityTemplateID,
      },
      { creditFacilityName: this.selectedDocumentElement.creditFacilityName },
      { elementID: this.selectedDocumentElement.elementID },
      { securityDocumentID: securityDocumentID },
      { facilityID: this.selectedDocumentElement.facilityID },
      { documentName: this.selectedDocumentElement.documentName },
      { savedByDisplayName: this.loggedInUserDisplayName },
      { documentStatus: "DRAFT" },
      { returnComment: null },
      { documentContent: this.bindCovenantToContent(updatedDocumentContent) },
      { fpSecurityDocumentTagDataDTOList: securityDocumentTagData }
    );

    this.facilityPaperAddEditService
      .saveOrUpdateSecurityDocument(securityDocumentData)
      .then((data: any) => {
        data = Object.assign({}, data);
        this.action.next(data);
      });
    this.mdbModalRef.hide();
  }

  constructDateOutput(inputDateStr, propName) {
    let casData: any;
    if (inputDateStr.toString().trim() != "") {
      let dateStrArray = inputDateStr.split("-");
      let inputDate = new Date(
        dateStrArray[2] + "/" + dateStrArray[1] + "/" + dateStrArray[0]
      );
      if (propName == "MONTH_NAME") {
        casData = inputDate.toLocaleString("default", { month: "long" });
      }
      if (propName == "YEAR_IN_WORDS") {
        casData = this.numberToWordsPipe.transform(inputDate.getFullYear());
      }
      if (propName == "DAY") {
        casData = this.getOrdinalDay(inputDate.getDate());
      }
    } else {
      casData = "..............................";
    }

    return casData;
  }

  saveDocumentFields() {
    if (this.enableEditDraftDoc) {
      this.saveDraftDocumentFields();
    } else {
      this.saveInitialDocumentFields();
    }
  }

  ngOnDestroy(): void {
    this.creditCalculatorResponse.unsubscribe();
    this.onFormChangeSub.unsubscribe();
  }

  async getCreditCalculatorData(facilityData) {
    let dataArray = [];
    let fpOtherInfoDataDTOList = [];
    let fpRentalDataDTOList = [];
    fpOtherInfoDataDTOList =
      _.sortBy(
        _.cloneDeep(facilityData.facilityOtherFacilityInformationDTOList),
        ["displayOrder"]
      ) || [];
    fpRentalDataDTOList =
      _.cloneDeep(facilityData.facilityRentalInformationDTOList) || [];

    /* this.fpOtherInfoDataDTOList = _.sortBy(_.cloneDeep(facilityData.facilityOtherFacilityInformationDTOList), ['displayOrder']) || [];
        this.fpRentalDataDTOList = _.cloneDeep(facilityData.facilityRentalInformationDTOList) || [];*/

    _.forEach(fpOtherInfoDataDTOList, (item) => {
      let object = {
        code: item["otherFacilityInfoCode"],
        value: item["otherInfoData"],
      };
      if (!_.isEmpty(object.value)) {
        dataArray.push(object);
      }
    });

    let calculatorType = "Normal";
    if (fpRentalDataDTOList.length > 0) {
      calculatorType = "Structured";
    }

    let data = {
      facilityType: facilityData["facilityType"],
      data: dataArray,
      calculatorType: calculatorType,
      rentalData: fpRentalDataDTOList,
    };

    await this.facilityPaperAddEditService
      .getCreditCalculatorData(data)
      .then((res: any) => {
        this.creditCalculatorData = res["systemOutputResponseData"];
        this.creditScheduleData = res["creditScheduleResponseData"];
        this.slvData = res["stipulatedLossValueResponseData"];
        this.constructPaymentDetailsObject(
          this.creditCalculatorData,
          facilityData
        );
        this.constructStipulatedLossValueObject(this.slvData);
      });
  }

  constructStipulatedLossValueObject(slvData) {
    let stipulatedLossValueDetails = {};
    let fromMonth = 0;
    let toMonth = 6;
    let slvAmount: any;
    let rowCount = 1;
    let labelForMonth = "";
    let labelForAmount = "";
    // let hashSpaceTag1 = "&emsp;&emsp;&emsp;&ensp;-&emsp;&emsp;&emsp;";
    //  let hashSpaceTag2 = "&emsp;&emsp;&emsp;-&emsp;&emsp;&emsp;";
    let hashSpaceTag1 = "               -            ";
    let hashSpaceTag2 = "             -            ";

    let slvMonthFormat = "";
    let actualPeriod = Number(this.paymentDetailsObject[0].period);
    let maximumPeriod = 60;

    for (let i = 1; i <= maximumPeriod; i += 6) {
      if (i != 1) {
        fromMonth = i;
        toMonth = toMonth + 6;
      }
      if (i == maximumPeriod + 1) {
        i = maximumPeriod;
      }

      if (i < actualPeriod) {
        slvAmount = Number(
          _.filter(slvData, (slv) => slv.term == i)[0].stipulatedLossValue
        );
        slvAmount = this.currencyPipe.transform(slvAmount, "", "");
      } else {
        slvAmount = "";
      }

      labelForMonth = "slvMonthNoRow" + rowCount;
      labelForAmount = "slvAmountRow" + rowCount;

      if (fromMonth == 0 || fromMonth == 7) {
        slvMonthFormat = fromMonth + hashSpaceTag1 + toMonth;
      } else {
        slvMonthFormat = fromMonth + hashSpaceTag2 + toMonth;
      }
      stipulatedLossValueDetails = {
        ...stipulatedLossValueDetails,
        [labelForMonth]: slvMonthFormat,
        [labelForAmount]: slvAmount,
      };
      rowCount++;
    }
    this.stipulatedLossValueObject.push(stipulatedLossValueDetails);
  }

  getAmountInWords(inputAmount) {
    let inputAmountInWords = "";
    let inputAmountWithCommas = this.currencyPipe.transform(
      inputAmount,
      "",
      ""
    );
    let inputAmountWithoutCommas = inputAmountWithCommas.replace(/,/g, "");
    let inputAmountString = Number(inputAmountWithoutCommas).toLocaleString(
      "fullwide",
      { useGrouping: false }
    );
    let inputAmountWithoutCents = inputAmountString.split(".")[0];
    let centsInInputAmount = inputAmountWithoutCommas.split(".")[1];
    let inputAmountWithoutCentsInWords = _.upperCase(
      this.numberToWordsPipe.transform(inputAmountWithoutCents)
    );
    let centsInInputAmountInWords = _.upperCase(
      this.numberToWordsPipe.transform(centsInInputAmount)
    );
    if (centsInInputAmount === undefined) {
      inputAmountInWords = inputAmountWithoutCentsInWords;
    } else {
      if (centsInInputAmount == "00" || centsInInputAmount == "0") {
        inputAmountInWords = inputAmountWithoutCentsInWords;
      } else {
        inputAmountInWords =
          inputAmountWithoutCentsInWords +
          " AND CENTS " +
          centsInInputAmountInWords;
      }
    }

    return inputAmountInWords;
  }

  constructPaymentDetailsObject(creditCalculatorData, facilityData) {
    let paymentDetails = {};
    let fromMonth = 0;
    let toMonth = 1;
    let noOfMonths = 0;
    let rentalPerMonth = 0;
    let amount = 0;
    let totalAmount = 0;
    let deposit = 0;
    let costOfInitialPayment = 0;
    let monthlyRentalClause = "";
    let securityStatement = "";
    let leaseCapitalInWords = "";
    let sanctionAmountInWords = "";
    let upfrontRentals = "";
    let rental = Number(
      _.filter(creditCalculatorData, (cd) => cd.code == "OUT_007")[0].value
    );
    let upfronts = _.filter(
      creditCalculatorData,
      (cd) => cd.code == "CFT_OFI24"
    )[0].value;
    let period = _.filter(
      creditCalculatorData,
      (cd) => cd.code == "CFT_OFI23"
    )[0].value;
    let defaultRate = Number(
      _.filter(creditCalculatorData, (cd) => cd.code == "CFT_OFI25")[0].value
    );
    let supplierName = _.filter(
      creditCalculatorData,
      (cd) => cd.code == "CFT_OFI29"
    )[0].value;
    // let leaseAmount = _.filter(creditCalculatorData, (cd) => cd.code == "CFT_OFI22")[0].value;
    let leaseCapital = _.filter(
      creditCalculatorData,
      (cd) => cd.code == "OUT_001"
    )[0].value;
    let leaseCapitalWithCommas = this.currencyPipe.transform(
      leaseCapital,
      "",
      ""
    );

    let sanctionAmount = _.filter(
      creditCalculatorData,
      (cd) => cd.code == "OUT_004"
    )[0].value;
    let sanctionAmountWithCommas = this.currencyPipe.transform(
      sanctionAmount,
      "",
      ""
    );
    let securityAmount =
      this.facilitySecurityDTOList.length > 0
        ? this.facilitySecurityDTOList[0].securityAmount
        : "";
    let securityStatement2 =
      facilityData.facilitySecurityDTOList[1] == null
        ? ""
        : facilityData.facilitySecurityDTOList[1].securityDetail;
    let securityStatement3 =
      facilityData.facilitySecurityDTOList[2] == null
        ? ""
        : facilityData.facilitySecurityDTOList[2].securityDetail;

    leaseCapitalInWords = this.getAmountInWords(leaseCapital);
    sanctionAmountInWords = this.getAmountInWords(sanctionAmount);

    if (upfronts == 1) {
      upfrontRentals = "First Month Rental Only";
    } else {
      if (upfronts == 2) {
        upfrontRentals = "First and Last Month Rentals Only";
      } else {
        upfrontRentals =
          "First and Last " +
          this.numberToWordsPipe.transform(upfronts - 1) +
          "Month Rentals Only";
      }
    }

    noOfMonths = toMonth - fromMonth;
    amount = rental * noOfMonths;
    totalAmount = totalAmount + amount;
    costOfInitialPayment = rental * upfronts;
    monthlyRentalClause =
      this.currencyPipe.transform(rental, "", "") + " X " + (period - upfronts);

    paymentDetails = {
      ...paymentDetails,
      upfronts: upfronts,
      prepayments: "01 + 0" + (upfronts - 1),
      period: period,
      defaultRate: defaultRate + 2,
      supplierName: supplierName,
      monthlyRentalClause: monthlyRentalClause,
      costOfInitialPayment: this.currencyPipe.transform(
        costOfInitialPayment,
        "",
        ""
      ),
      //  leaseAmount:this.currencyPipe.transform(leaseAmount,"",""),
      leaseCapital: leaseCapitalWithCommas,
      leaseCapitalInWords: leaseCapitalInWords,
      sanctionAmount: sanctionAmountWithCommas,
      sanctionAmountInWords: sanctionAmountInWords,
      securityAmount: this.currencyPipe.transform(securityAmount, "", ""),
      monthNoRow1: fromMonth + "-" + toMonth,
      noOfMonthsRow1: noOfMonths,
      rentalPerMonthRow1: this.currencyPipe.transform(rental, "", ""),
      amountRow1: this.currencyPipe.transform(amount, "", ""),
      rental: this.currencyPipe.transform(rental, "", ""),
    };

    if (upfronts == 1) {
      noOfMonths = period - upfronts;
      fromMonth = toMonth + 1;
      toMonth = noOfMonths + 1;
      amount = rental * noOfMonths;
      totalAmount = totalAmount + amount;
      paymentDetails = {
        ...paymentDetails,
        monthNoRow2: fromMonth + "-" + toMonth,
        noOfMonthsRow2: noOfMonths,
        totalAmount: this.currencyPipe.transform(totalAmount, "", ""),
        amountRow2: this.currencyPipe.transform(amount, "", ""),
        rentalPerMonthRow2: this.currencyPipe.transform(rental, "", ""),
        deposit: deposit,
        upfrontRentals: upfrontRentals,
        securityStatement:
          "Lease agreement for Rs." +
          this.currencyPipe.transform(totalAmount, "", "") +
          " together with 01 upfront rental of Rs." +
          this.currencyPipe.transform(rental, "", ""),
        securityStatement2: securityStatement2,
        securityStatement3: securityStatement3,
      };
    } else {
      noOfMonths = period - upfronts;
      fromMonth = toMonth + 1;
      toMonth = noOfMonths + 1;
      amount = rental * noOfMonths;
      totalAmount = totalAmount + amount;
      paymentDetails = {
        ...paymentDetails,
        monthNoRow2: fromMonth + "-" + toMonth,
        noOfMonthsRow2: noOfMonths,
        amountRow2: this.currencyPipe.transform(amount, "", ""),
        rentalPerMonthRow2: this.currencyPipe.transform(rental, "", ""),
      };

      fromMonth = toMonth + 1;
      toMonth = period;
      noOfMonths = upfronts - 1;
      amount = rental * noOfMonths;
      totalAmount = totalAmount + amount;
      paymentDetails = {
        ...paymentDetails,
        monthNoRow3:
          fromMonth == toMonth ? fromMonth : fromMonth + "-" + toMonth,
        noOfMonthsRow3: noOfMonths,
        amountRow3: this.currencyPipe.transform(amount, "", ""),
        totalAmount: this.currencyPipe.transform(totalAmount, "", ""),
        rentalPerMonthRow3: this.currencyPipe.transform(rental, "", ""),
        deposit: this.currencyPipe.transform(rental, "", ""),
        upfrontRentals: upfrontRentals,
        securityStatement:
          "Lease agreement for Rs. " +
          this.currencyPipe.transform(totalAmount, "", "") +
          " together with 0" +
          upfronts +
          " upfront rentals totaling to  Rs. " +
          this.currencyPipe.transform(rental * upfronts, "", ""),
        securityStatement2: securityStatement2,
        securityStatement3: securityStatement3,
      };
    }
    this.paymentDetailsObject.push(paymentDetails);
  }

  isValid() {
    return this.componentForm.valid;
  }

  getOrdinalDay(day) {
    //  let dateNew = new Date(), day = dateNew.getDate(), ordinal = 'th';
    let ordinal = "th";
    const paddedDay = day.toString().padStart(2, "0"); // zero-pad to 2 digits
    if (day == "2" || day == "22") ordinal = "nd";
    if (day == "3" || day == "23") ordinal = "rd";
    if (day == "21" || day == "1" || day == "31") ordinal = "st";
    return paddedDay + "" + "<sup>" + ordinal + "</sup>";
  }

  numberOnly(event): boolean {
    const charCode = event.which ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES
    );
    let branch = AppUtils.getBranchFromBranchCode(
      this.allBankOptions,
      branchCode
    );
    let branchName = "";
    if (!isEmpty(branch)) {
      branchName = branch.branchName;
    }
    return branchName;
  }

  getCusApplicableCovenantList(facilityId: any) {
    if (
      this.facilityPaper !== null &&
      this.facilityPaper.fpRefNumber !== null
    ) {
      this.facilityPaperAddEditService
        .getCustomerApplicableCovenantList(
          this.facilityPaper.fpRefNumber,
          facilityId
        )
        .then((data: any[]) => {
          this.covenantsList = data;
        });
    }
  }

  bindCovenantToContent(content: any): any {
    let elementId: string = "sd-covn-list";

    const parser = new DOMParser();
    const doc = parser.parseFromString(content, "text/html");

    const targetOLElement = doc.getElementById(elementId);
    if (!targetOLElement) return content;
    let count: number = targetOLElement.childElementCount;
    if (count <= 1) {
      const newItems: HTMLLIElement[] = this.covenantsList.map((cov) => {
        const li = document.createElement("li");
        li.style.paddingTop = "10pt";
        li.textContent = cov.covenant_Description;
        return li;
      });

      this.toReversed(newItems).forEach((li: any) => {
        targetOLElement.insertBefore(li, targetOLElement.firstChild);
      });

      return doc.documentElement.outerHTML;
    }
    return content;
  }

  toReversed(data: any[]): any[] {
    return data.reverse();
  }

  clearElementContent(id: string, htmlString: string): string {
    const parser = new DOMParser();
    const content = parser.parseFromString(htmlString, "text/html");

    const element = content.getElementById(id) as HTMLElement;
    if (element !== null) {
      element.innerHTML = "";
    }

    return content.documentElement.outerHTML;
  }
}
