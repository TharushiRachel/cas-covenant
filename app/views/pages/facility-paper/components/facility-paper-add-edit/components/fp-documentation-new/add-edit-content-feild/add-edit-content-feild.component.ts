import { CurrencyPipe } from "@angular/common";
import { ChangeDetectorRef, Component, OnInit } from "@angular/core";
import * as _ from "lodash";
import { MDBModalRef, IMyOptions } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { FacilityPaperAddEditService } from "src/app/views/pages/facility-paper/services/facility-paper-add-edit.service";
import {
  DocumentElement,
  DocumentInputField,
  FacilityTemplate,
  SDConstants,
  Option,
  Facility,
  DocumentTag,
} from "../utils";
import * as moment from "moment";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import { element } from "protractor";

@Component({
  selector: "app-add-edit-content-feild",
  templateUrl: "./add-edit-content-feild.component.html",
  styleUrls: ["./add-edit-content-feild.component.scss"],
})
export class AddEditContentFeildComponent implements OnInit {
  modalRef: MDBModalRef;
  action: Subject<any> = new Subject<any>();
  content: any = {};
  datePickerOptions: IMyOptions = {
    dateFormat: "yyyy-mm-dd",
    closeAfterSelect: true,
  };

  facilityPaper: any = {};
  selectedDocumentTemplate: FacilityTemplate;
  selectedDocumentContent: any = "";
  selectedDocumentElement: DocumentElement;
  documentInputFields: DocumentInputField[] = [];

  isContentRefreshed: boolean = false;

  incomeSources: Option[] = [
    { value: "Salaried", label: "Salaried" },
    { value: "Business", label: "Business" },
  ];

  acountTypes: Option[] = [
    { value: "Savings", label: "Savings" },
    { value: "Current", label: "Current" },
  ];

  leasingAct56Clauses: Option[] = [
    {
      value:
        "the duly authorized Attorney of the Lessor, Sampath Bank PLC has set his/her hand hereunto",
      label:
        "the duly authorized Attorney of the Lessor, Sampath Bank PLC has set his/her hand hereunto",
    },
    {
      value:
        "the said lessor, SAMPATH BANK PLC has cause its common seal to be affixed hereunto",
      label:
        "the said lessor, SAMPATH BANK PLC has cause its common seal to be affixed hereunto",
    },
  ];

  yesNo: Option[] = [
    { value: Constants.yesNo.Y, label: Constants.yesNo.Y },
    { value: Constants.yesNo.N, label: Constants.yesNo.N },
  ];

  stageCount: Option[] = [
    { value: "01", label: "01" },
    { value: "02", label: "02" },
    { value: "03", label: "03" },
  ];

  repaymentMethods: Option[] = [
    { value: "EQUATED", label: "EQUATED" },
    {
      value: "EQUAL WITH FINAL INSTALMENT",
      label: "EQUAL WITH FINAL INSTALMENT",
    },
    {
      value: "EQUAL WITHOUT FINAL INSTALMENT",
      label: "EQUAL WITHOUT FINAL INSTALMENT",
    },
  ];

  selectedFacility: Facility;
  securityDocumentID: number = 0;
  selectedDocumentStatus: string = "";
  documentTags: DocumentTag[] = [];
  dotedText: string = "...............................";

  constructor(
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    public mdbModalRef: MDBModalRef,
    private readonly currencyPipe: CurrencyPipe,
    private readonly applicationService: ApplicationService,
    public cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.facilityPaper = this.content.facilityPaper;
    this.isContentRefreshed = this.content.isContentRefreshed;
    this.selectedDocumentTemplate = this.content.selectedDocumentTemplate;
    this.selectedDocumentStatus = this.content.selectedDocumentStatus;
    this.selectedDocumentElement = {
      ...this.content.selectedDocumentElement,
      documentName: this.content.selectedDocumentElement.documentFileName
        ? this.content.selectedDocumentElement.documentFileName.replaceAll(
            "-",
            " "
          )
        : "",
    };
    this.selectedFacility = this.content.selectedFacility;
    this.selectedDocumentContent =
      this.selectedDocumentTemplate.documentContent;
    this.documentTags = this.content.documentTags;

    this.prepareDocumentInputs(this.selectedDocumentContent);
  }

  getDefualtDocumentName() {
    return (
      "Facility " +
      this.selectedFacility.displayOrder +
      " - " +
      this.selectedDocumentElement.elementName +
      " ( " +
      this.selectedDocumentTemplate.creditFacilityName +
      " )"
    );
  }

  prepareDocumentInputs(content: any) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, "text/html");

    let inputElements: any[] = [];
    SDConstants.elementIdTypes.forEach((element: string) => {
      const elements: any = doc.querySelectorAll(`[id^="${element}-"`);

      if (elements !== null && elements.length > 0) {
        inputElements = [...inputElements, ...elements];
      }
    });

    inputElements.forEach((element: HTMLElement) => {
      const inputId: string = element.id;

      const elementValueFeild: HTMLElement = doc.getElementById(
        this.getReplaceValue(inputId)
      );
      const hasValueFeild: boolean =
        elementValueFeild !== undefined && elementValueFeild !== null;

      const isRequired: boolean =
        element.getAttribute("aria-required") === "true";
      const inputType: string = element.getAttribute("itemprop");

      const mainElement: HTMLElement = doc.getElementById(
        this.getMainEelementId(inputId)
      );

      let feildValue: any = this.getElementValue(
        doc,
        inputId,
        this.getIDProp(inputId),
        hasValueFeild
      );

      let isSelection: boolean =
        inputType === SDConstants.inputMode.SELECT ||
        inputType === SDConstants.inputMode.SELECT_TEXT;

      const displayOrder = element.getAttribute("itemid");

      const isTag: boolean =
        !this.showPrintButton() &&
        (inputId.includes(SDConstants.elementIdType.IST) ||
          inputId.includes(SDConstants.elementIdType.IU) ||
          inputId.includes(SDConstants.elementIdType.IB));

      let inputLabel: string =
        hasValueFeild && isSelection
          ? this.getCustomizeLable(inputId)
          : hasValueFeild
          ? element.innerHTML
          : this.getCustomizeLable(inputId);

      let inputValue: any =
        inputType === SDConstants.inputMode.DATE
          ? this.getFormattedDate(feildValue)
          : feildValue;
      
      let feild: DocumentInputField = {
        id: inputId,
        label: inputLabel,
        inputType: inputType,
        value: this.preparedValue(inputId, inputLabel, inputValue),
        isRequired: isRequired,
        valueId: hasValueFeild
          ? inputId.replace(this.getIDProp(inputId), "V-")
          : inputId,
        selectOptions: isSelection
          ? this.getElementSelectOptions(this.getCustomizeLable(inputId))
          : [],
        showInput: !inputId.includes(`-${SDConstants.elementIdType.DPL}-`),
        isTag: isTag,
        hasMainEelement: mainElement !== undefined && mainElement !== null,
        error: "",
        disabled: this.showPrintButton() && !this.isDisabledInput(inputId),
        displayOrder: displayOrder ? parseInt(displayOrder) : 0,
      };
      this.documentInputFields.push(feild);
    });

    this.documentInputFields = this.documentInputFields.sort(
      (a: DocumentInputField, b: DocumentInputField) =>
        a.displayOrder - b.displayOrder
    );
  }

  preparedValue(inputId: string, label: string, value: any) {
    let tagName: string = label.replace(/ /g, "-");
    let tag: DocumentTag = this.documentTags.find(
      (t: DocumentTag) => t.tag === tagName
    );
    let tagValue = tag !== undefined && tag !== null ? tag.tagValue : "";
    if (this.isContentRefreshed) {
      let isTagValue: boolean =
        inputId.includes(SDConstants.elementIdType.IST) ||
        inputId.includes(SDConstants.elementIdType.IU);

      return isTagValue ? tagValue : value;
    }
    return tagValue &&
      (this.selectedDocumentTemplate.securityDocumentID === null ||
        this.selectedDocumentTemplate.securityDocumentID === 0)
      ? tagValue
      : value;
  }

  isDisabledInput(id: string): boolean {
    const keywords = ["Letter Date Format 1", "Document Save Name"];
    const normalizedId = id.replace(/-/g, " ");

    return keywords.some((keyword) => normalizedId.includes(keyword));
  }

  getCustomizeLable(id: string): string {
    let label: string = "";
    const splits: string[] = id.split("-");
    splits.shift();
    label = splits.join(" ");
    return label;
  }

  getMainEelementId(elementId: string) {
    let split: string[] = elementId.split("-");
    if (split.length > 0) {
      split[0] = "M";
      return split.join("-");
    }
    return "";
  }

  getReplaceValue(elementId: string) {
    const splits: string[] = elementId.split("-");

    if (splits.length > 0 && splits[0] === "I") {
      return elementId.replace("I-", "V-");
    } else if (splits.length > 0 && splits[0] === "IU") {
      return elementId.replace("IU-", "V-");
    } else if (splits.length > 0 && splits[0] === "IS") {
      return elementId.replace("IS-", "V-");
    } else if (splits.length > 0 && splits[0] === "IST") {
      return elementId.replace("IST-", "V-");
    } else if (splits.length > 0 && splits[0] === "IB") {
      return elementId.replace("IB-", "V-");
    } else {
      return elementId;
    }
  }

  getIDProp(elementId: string) {
    const splits: string[] = elementId.split("-");
    if (splits.length > 0 && splits[0] === "I") {
      return "I-";
    } else if (splits.length > 0 && splits[0] === "IU") {
      return "IU-";
    } else if (splits.length > 0 && splits[0] === "IS") {
      return "IS-";
    } else if (splits.length > 0 && splits[0] === "IST") {
      return "IST-";
    } else if (splits.length > 0 && splits[0] === "IB") {
      return "IB-";
    } else {
      return splits.length > 0 ? splits[0] : "";
    }
  }

  getElementValue(
    doc: Document,
    inputId: string,
    idProp: string,
    hasValueFeild: boolean
  ): string {
    let value: string = "";
    let valueElement: HTMLElement = hasValueFeild
      ? doc.getElementById(inputId.replace(idProp, "V-"))
      : doc.getElementById(inputId);
    if (valueElement !== undefined && valueElement !== null) {
      value = valueElement.innerHTML;
      value = value.replace(/\s+/g, " ").trim();
    }
    return !value.startsWith("....") ? value : "";
  }

  getFormattedDate(value: any) {
    if (value) {
      return moment(value, "Do MMMM YYYY").format("YYYY-MM-DD");
    }

    return value;
  }

  getSelectElementValue(doc: Document, elementId: string): string {
    let value: string = "";
    let valueElement: HTMLElement = doc.getElementById(elementId);
    if (valueElement !== undefined && valueElement !== null) {
      let valueAttr = valueElement.getAttribute("aria-label");
      value = valueAttr !== undefined && valueAttr !== null ? valueAttr : "";
    }
    return value;
  }

  getElementSelectOptions(prop: string) {
    switch (prop) {
      case "Income Source":
        return this.incomeSources;
      case "Account Type":
        return this.acountTypes;
      case "Leasing Act 56 Clause":
        return this.leasingAct56Clauses;
      case "Show Joint Borrower":
      case "Show Main Borrower":
        return this.yesNo;
      case "Stage Count":
        return this.stageCount;
      case "Repayment Method":
        return this.repaymentMethods;
      default:
        break;
    }
  }

  getValue(amount: any) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  setCurrencyFormatValue(feild: DocumentInputField) {
    if (feild !== null && feild.value !== null) {
      if (!isNaN(feild.value) || !isNaN(this.getValue(feild.value))) {
        this.documentInputFields = this.documentInputFields.map(
          (d: DocumentInputField) => ({
            ...d,
            value:
              d.id === feild.id
                ? this.currencyPipe.transform(this.getValue(d.value), "", "")
                : d.value,
            error: d.id === feild.id ? "" : d.error,
          })
        );
      } else {
        this.documentInputFields = this.documentInputFields.map(
          (d: DocumentInputField) => ({
            ...d,
            error: d.id === feild.id ? "Invalid amount." : d.error,
          })
        );
      }
    }

    this.cdr.detectChanges();
  }

  handleSave() {
    let securityDocumentData: any = this.getPreparedRequest(
      SDConstants.documentStatusConst.DRAFT
    );
    this.facilityPaperAddEditService
      .saveSecurityDocument(securityDocumentData)
      .then((data: any) => {
        if (data !== null) {
          data = Object.assign({}, data);
          this.action.next(data);
          this.mdbModalRef.hide();
        }
      });
  }

  getMasterValueId(duplicateId: string) {
    const str = duplicateId;
    const index = str.indexOf("-DPL-");
    const result = index !== -1 ? str.substring(0, index) : str;
    return result ? result : "";
  }

  getMasterValue(element: DocumentInputField) {
    const masterValueId: string = this.getMasterValueId(element.id);
    const feild: DocumentInputField = this.documentInputFields.find(
      (f: DocumentInputField) => f.valueId === masterValueId
    );
    return feild !== undefined && feild !== null ? feild.value : "";
  }

  isSaveDisabled() {
    return this.documentInputFields
      .filter((feild: DocumentInputField) => feild.showInput)
      .some(
        (feild: DocumentInputField) =>
          (feild.isRequired && (feild.value === null || feild.value === "")) ||
          feild.error !== ""
      );
  }

  isBranchUser(): boolean {
    return (
      parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) <= 50 &&
      this.applicationService.getLoggedInUserDivCode() ===
        this.facilityPaper.branchCode
    );
  }

  handlePrint() {
    let securityDocumentData: any = this.getPreparedRequest(
      SDConstants.documentStatusConst.PRINT
    );

    this.facilityPaperAddEditService
      .saveSecurityDocument(securityDocumentData)
      .then((data: any) => {
        if (data !== null) {
          this.previewDocument(securityDocumentData.documentContent);
          this.action.next(data);
          this.mdbModalRef.hide();
        }
      });
  }

  previewDocument(updateDocumentContent: any) {
    const templates = [
      "Offer Letter",
      "Samachara Loan With Facility",
      "Samachara Loan Without Facility",
    ];
    let fileName: string =
      this.selectedDocumentElement.documentFileName.replace(/-/g, " ");

    let documentName: string = this.selectedDocumentTemplate.documentName
      ? this.selectedDocumentTemplate.documentName
      : fileName;

    const matchDocName = templates.some((template) =>
      fileName.includes(template)
    );
    let department: string =
      this.selectedFacility.facilityTypeName === "Lease" ? "CCPU" : "CCDU";

    let printContents, popupWin;
    printContents = updateDocumentContent;
    popupWin = window.open("", "_blank", "top=0,left=0,height=80%,width=auto");
    popupWin.document.open();

    if (matchDocName) {
      popupWin.document.write(`
                 <html>
                    <head>
                      <script defer src="assets/js/polyfill.js"></script>
                      <script>
                        window.PagedConfig = {auto:false}
                        window.addEventListener('load', () => {window.PagedPolyfill.preview().then(() => {window.print();});});
                      </script>
                      <style type="text/css">
                           @page {size: A4;  margin-bottom: 1.5cm; margin-top: 3.5cm;
                           <!-- @bottom-right { content: "CCDU | Page "counter(page)" of " counter(pages); }} -->
                           .template-footer {position: fixed; bottom:4cm; width: 100%; display:table;}
                           .template-footer p {page-break-before: always;font-size: 10pt;}
                           .template-footer p::before {content: "CCDU | Page "counter(page)" of " counter(pages);display: block;text-align: right; margin-right: 2.5cm;}
                           .pagebreak {page-break-before: always; }
                      </style>
                        <title>${documentName}</title>
                    </head>
                    <body onafterprint="window.close()">${printContents}</body>
                  </html>`);
    } else {
      popupWin.document.write(`
                <html>
                  <head>
                    <title>${documentName}</title>
                  </head>
                  <body onload=" window.print();" onafterprint="window.close()">${printContents}</body>
                </html>`);
    }

    popupWin.document.close();
  }

  showPrintButton() {
    return (
      this.isBranchUser() &&
      this.selectedDocumentStatus === SDConstants.documentStatusConst.APPROVE
    );
  }

  getUpperOrdinalSuffix(day: number) {
    const lastTwo = day % 100;
    if (lastTwo >= 11 && lastTwo <= 13) return "th";
    const last = day % 10;
    if (last === 1) return "st";
    if (last === 2) return "nd";
    if (last === 3) return "rd";
    return "th";
  }

  formatDateOrdinalUpper(value: any) {
    const momentDate = moment(value).format("YYYY-MM-DD");
    const day = moment(momentDate).date();

    const dayPadded = moment(momentDate).format("DD");
    const suffix = this.getUpperOrdinalSuffix(day);
    const month = moment(momentDate).format("MMMM");
    const year = moment(momentDate).format("YYYY");
    return `${dayPadded}<sup>${suffix}</sup> ${month} ${year}`;
  }

  getPreparedRequest(status: any) {
    let secDocID: number = this.selectedDocumentTemplate.securityDocumentID
      ? this.selectedDocumentTemplate.securityDocumentID
      : 0;
    let saveDocumentName = this.getDefualtDocumentName();

    const parser = new DOMParser();
    const doc = parser.parseFromString(
      this.selectedDocumentContent,
      "text/html"
    );

    let documentTags: any[] = [];

    this.documentInputFields.forEach((element: DocumentInputField) => {
      let dottedValue: any =
        element.value !== null && element.value !== ""
          ? element.value
          : this.dotedText;
      let dottedId: string = element.id;
      if (
        element.inputType !== SDConstants.inputMode.SELECT &&
        element.inputType !== SDConstants.inputMode.DATE
      ) {
        const targetOLElement = doc.getElementById(element.valueId);

        if (element.showInput) {
          targetOLElement.innerHTML = element.value;
        } else {
          let masterValue: any = this.getMasterValue(element);
          targetOLElement.innerHTML = masterValue;
          dottedValue =
            masterValue !== null && masterValue !== ""
              ? masterValue
              : this.dotedText;
          dottedId = this.getMasterValueId(element.id);
        }

        if (element.id === "IS-Repayment") {
          targetOLElement.innerHTML = this.constructRepayment();
        }

        //set dotted text area
        if (SDConstants.DOTTED_TXT_IDS.includes(element.id)) {
          targetOLElement.innerHTML = dottedValue;
        }
      } else if (element.inputType === SDConstants.inputMode.DATE) {
        const targetOLElement = doc.getElementById(element.valueId);
        if (element.showInput) {
          let eleValue: any = element.value
            ? this.formatDateOrdinalUpper(element.value)
            : element.value;
          dottedValue = eleValue ? eleValue : this.dotedText;
          targetOLElement.innerHTML = eleValue;
        } else {
          let masterValue: any = this.getMasterValue(element);
          let dateMasterValue: any =
            masterValue !== null && masterValue !== ""
              ? this.formatDateOrdinalUpper(masterValue)
              : masterValue;
          dottedValue =
            dateMasterValue !== null && dateMasterValue !== ""
              ? dateMasterValue
              : this.dotedText;
          dottedId = this.getMasterValueId(element.id);
          targetOLElement.innerHTML = dateMasterValue;
        }

        //set dotted text area
        if (SDConstants.DOTTED_TXT_IDS.includes(dottedId)) {
          targetOLElement.innerHTML = dottedValue;
        }
      } else {
        const targetOLElement = doc.getElementById(element.valueId);
        targetOLElement.innerHTML = element.value;
        switch (element.id) {
          case "IU-Income-Source":
            let businessEl: HTMLElement = doc.getElementById("Business");
            let salariedEl: HTMLElement = doc.getElementById("Salaried");
            if (
              businessEl !== undefined &&
              businessEl !== null &&
              salariedEl !== undefined &&
              salariedEl !== null
            ) {
              if (element.value === "Business") {
                businessEl.style.display = "block";
                salariedEl.style.display = "none";
              }

              if (element.value === "Salaried") {
                businessEl.style.display = "none";
                salariedEl.style.display = "block";
              }
            }
            break;

          case "IU-Show-Main-Borrower":
            let addressMainCus: HTMLElement = doc.getElementById(
              "ADDRESS_SHOW_MAIN_BORROWER"
            );
            let clauseMainCus: HTMLElement = doc.getElementById(
              "CLAUSE_SHOW_MAIN_BORROWER"
            );
            let signatureMainCus: HTMLElement = doc.getElementById(
              "SIGNATURE_SHOW_MAIN_BORROWER"
            );

            if (
              addressMainCus !== undefined &&
              addressMainCus !== null &&
              clauseMainCus !== undefined &&
              clauseMainCus !== null &&
              signatureMainCus !== undefined &&
              signatureMainCus !== null
            ) {
              if (element.value === Constants.yesNo.Y) {
                addressMainCus.style.display = "block";
                clauseMainCus.style.display = "inline";
                signatureMainCus.style.display = "inline";
              }

              if (element.value === Constants.yesNo.N) {
                addressMainCus.style.display = "none";
                clauseMainCus.style.display = "none";
                signatureMainCus.style.display = "none";
              }
            }
            break;

          case "IU-Show-Joint-Borrower":
            let addressJointCus: HTMLElement = doc.getElementById(
              "ADDRESS_SHOW_JOINT_BORROWER"
            );
            let clauseJointCus: HTMLElement = doc.getElementById(
              "CLAUSE_SHOW_JOINT_BORROWER"
            );
            let signatureJointCus: HTMLElement = doc.getElementById(
              "SIGNATURE_SHOW_JOINT_BORROWER"
            );
            if (
              addressJointCus !== undefined &&
              addressJointCus !== null &&
              clauseJointCus !== undefined &&
              clauseJointCus !== null &&
              signatureJointCus !== undefined &&
              signatureJointCus !== null
            ) {
              if (element.value === Constants.yesNo.Y) {
                addressJointCus.style.display = "block";
                clauseJointCus.style.display = "inline";
                signatureJointCus.style.display = "inline";
              }

              if (element.value === Constants.yesNo.N) {
                addressJointCus.style.display = "none";
                clauseJointCus.style.display = "none";
                signatureJointCus.style.display = "none";
              }
            }
            break;
          case "IU-Stage-Count":
            let stage1: HTMLElement = doc.getElementById("stage-01");
            let stage2: HTMLElement = doc.getElementById("stage-02");
            let stage3: HTMLElement = doc.getElementById("stage-03");

            if (element.value === "01") {
              stage1.style.display = "table-row";
              stage2.style.display = "none";
              stage3.style.display = "none";
            }
            if (element.value === "02") {
              stage1.style.display = "table-row";
              stage2.style.display = "table-row";
              stage3.style.display = "none";
            }
            if (element.value === "03") {
              stage1.style.display = "table-row";
              stage2.style.display = "table-row";
              stage3.style.display = "table-row";
            }
            break;
          default:
            break;
        }
      }

      if (element.hasMainEelement) {
        const mainElement: HTMLElement = doc.getElementById(
          this.getMainEelementId(element.id)
        );
        if (mainElement !== undefined && mainElement !== null) {
          mainElement.style.display =
            element.value === "" || element.value === "N/A" || element.value === "0.00"
              ? "none"
              : "table-row";
        }
      }

      if (
        element.id === "IS-Document-Save-Name" &&
        element.value !== null &&
        element.value !== ""
      ) {
        saveDocumentName = element.value;
      }

      if (
        element.isTag &&
        !element.id.includes(`-${SDConstants.elementIdType.DPL}-`) &&
        element.value !== null
      ) {
        documentTags.push({
          facilityPaperId: this.facilityPaper.facilityPaperID,
          tag: element.label.replace(/ /g, "-"),
          tagValue: element.value,
          fieldType: SDConstants.inputModeConst[element.inputType],
        });
      }
    });

    let updatedDocumentContent: string = doc.documentElement.outerHTML;

    let isDraft: boolean = status === SDConstants.documentStatusConst.DRAFT;

    let securityDocumentData: any = {
      securityDocumentID: secDocID,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      creditFacilityTemplateID:
        this.selectedDocumentTemplate.creditFacilityTemplateID,
      creditFacilityName: this.selectedDocumentTemplate.creditFacilityName,
      elementID: this.selectedDocumentElement.elementID,
      facilityID: this.selectedFacility.facilityID,
      documentName:
        isDraft && saveDocumentName !== null && saveDocumentName !== ""
          ? saveDocumentName
          : this.selectedDocumentTemplate.documentName,
      documentStatus: status,
      actionComment: "",
      documentContent: updatedDocumentContent,
      documentTags: isDraft ? documentTags : [],
    };

    return securityDocumentData;
  }

  constructRepayment() {
    let repaymentClause = "";
    let repaymentMethod = this.getValueById("IU-Repayment-Method");
    let repaymentPeriodInMonths = this.getValueById(
      "IU-Repayment-Period-In-Months"
    );
    let monthlyInstalment = this.getValueById("IU-Monthly-Instalment");
    let finalInstalment = this.getValueById("IU-Final-Instalment");

    switch (repaymentMethod) {
      case "EQUATED":
        repaymentClause =
          "In " +
          repaymentPeriodInMonths +
          " equated monthly installments of Rs." +
          monthlyInstalment +
          " each (inclusive of interest) payable on the 05<sup>th</sup> day of each month. (The above installment has been calculated at the prevailing interest rate and the installment will be varied with the subsequent rate revisions if any)";
        return repaymentClause;
      case "EQUAL WITH FINAL INSTALMENT":
        repaymentClause =
          "In " +
          repaymentPeriodInMonths +
          " equal monthly installments of Rs." +
          monthlyInstalment +
          " each and the final installment of Rs." +
          finalInstalment +
          " together with interest payable on the 05<sup>th</sup> day of each month";

        return repaymentClause;
      case "EQUAL WITHOUT FINAL INSTALMENT":
        repaymentClause =
          "In " +
          repaymentPeriodInMonths +
          " equal monthly installments of Rs." +
          monthlyInstalment +
          " each together with interest payable on the 05<sup>th</sup> day of each month";
        return repaymentClause;
      default:
        return repaymentClause;
    }
  }

  getValueById(id: string) {
    let inpuElement: DocumentInputField = this.documentInputFields.find(
      (element: DocumentInputField) => element.id === id
    );
    return inpuElement !== undefined && inpuElement !== null
      ? inpuElement.value
      : "";
  }

  isPrintSaveDisabled() {
    let editableInputs: DocumentInputField[] = this.documentInputFields.filter(
      (d: DocumentInputField) => !d.disabled
    );
    const keyword = "Letter Date Format 1";
    let dateInput: DocumentInputField = editableInputs.find(
      (d: DocumentInputField) => d.id.replace(/-/g, " ").includes(keyword)
    );

    return this.selectedDocumentElement.elementName === "Offer Letter" &&
      dateInput !== undefined &&
      dateInput !== null
      ? dateInput.value === null || dateInput.value === ""
      : false;
  }
}
