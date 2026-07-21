import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import * as moment from "moment";
import { IMyOptions, MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { LeadComprehensiveService } from "../../services/lead-comprehensive.service";
import { CurrencyPipe } from "@angular/common";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { Constants } from "src/app/core/setting/constants";
import * as Utils from "../../interfaces/utils";
import { newDocumentInputField, SDConstants } from "../../interfaces/utils";
import { AdvanceAnalyticsService } from "../../services/advance-analytics.service";

@Component({
  selector: "app-digital-application-modal",
  templateUrl: "./digital-application-modal.component.html",
  styleUrls: ["./digital-application-modal.component.scss"],
})
export class DigitalApplicationModalComponent implements OnInit {
  modalRef: MDBModalRef;
  action: Subject<any> = new Subject<any>();
  content: any = {};
  selectedDocumentElement: Utils.DocumentInputField;
  datePickerOptions: IMyOptions = {
    dateFormat: "yyyy-mm-dd",
    closeAfterSelect: true,
  };
  selectedApplicantIds: any;

  facilityPaper: any = {};
  leadId: number = 0;
  selectedApplicationId: number = 0;
  selectedDocumentContent: any = "";
  documentInputFields: Utils.DocumentInputField[] = [];
  applicantCount = 1;
  compLeadId: number = 0;

  addressType: Utils.Option[] = [
    { value: "Own", label: "Own" },
    { value: "Rented", label: "Rented" },
    { value: "Other", label: "Other" },
  ];

  nationality: Utils.Option[] = [
    { value: "Sri Lankan", label: "Sri Lankan" },
    { value: "Non-Sri Lankan", label: "Non-Sri Lankan" },
    { value: "PR Holder", label: "PR Holder" },
    { value: "Sri Lankan Dual Citizen", label: "Sri Lankan Dual Citizen" },
  ];

  civilStatus: Utils.Option[] = [
    { value: "Married ", label: "Married" },
    { value: "Un-married", label: "Un-married" },
    { value: "Other", label: "Other" },
  ];

  yesNo: Utils.Option[] = [
    { value: Constants.yesNo.Y, label: Constants.yesNo.Y },
    { value: Constants.yesNo.N, label: Constants.yesNo.N },
  ];

  avgSalaryGrowth: Utils.Option[] = [
    { value: "More than 10% growth", label: "More than 10% growth" },
    { value: "More than 5% growth", label: "More than 5% growth" },
    { value: "Up to 5% growth", label: "Up to 5% growth" },
    {
      value: "Positive growth in at least 1 yr",
      label: "Positive growth in at least 1 yr",
    },
    {
      value: "Negative growth in both yrs",
      label: "Negative growth in both yrs",
    },
  ];

  preferedLoanDedDate: Utils.Option[] = [
    { value: "26th of each month", label: "26th of each month" },
    { value: "04th of each month", label: "04th of each month" },
  ];

  consentAgreeDisagree: Utils.Option[] = [
    { value: "Agree", label: "Agree" },
    { value: "Disagree", label: "Disagree" },
  ];

  employmentType: Utils.Option[] = [
    { value: "Permanent", label: "Permanent" },
    { value: "Contract", label: "Contract" },
    { value: "Probation ", label: "Probation " },
  ];


  annexureOptions: Utils.Option[] = [
    { value: "Annexure I – For Motor quotation request", label: "Annexure I – For Motor quotation request" },
    { value: "Annexure II – for Fire quotation request", label: "Annexure II – for Fire quotation request" },
    { value: "Annexure III – For DTAP quotation request", label: "Annexure III – For DTAP quotation request" },
    { value: "Annexure IV – For Marine quotation request", label: "Annexure IV – For Marine quotation request" },
  ];

  isPluralApplicants = false;
  selectedDocumentStatus: string = "";

  agreeDisagreeField: Utils.DocumentInputField | null = null;
  annexuresField: Utils.DocumentInputField | null = null;
  accountNoField: Utils.DocumentInputField | null = null;
  loanRequiredField: Utils.DocumentInputField | null = null;
  insuranceAmountField: Utils.DocumentInputField | null = null;
  insuranceCompaniesField: Utils.DocumentInputField | null = null;

  showAssetsLBSection = false;
  showMotorVehicleSection = false;
  showFacilityDetailsSection = false;
  showSecurotyToBeOfferedSection = false;
  backendFacilitiesLoaded = false;

  //assert
  assetsLB: Utils.AssetLB[] = [];
  assetForm: Utils.AssetLB = {
    description: "",
    floorAreaExtent: "",
    ownedBy: "",
    marketValue: "",
    mortgages: "",
  };
  readonly assetOrder: Utils.SectionKey[] = ["LB", "MV", "STD", "LIP"];

  //Repayment options
  repaymentOptions: Array<{ value: string; label: string }> = [
    { value: 'Fixed installments', label: 'Fixed installments' },
    { value: 'Reducing installments', label: 'Reducing installments' },
    { value: 'Step Up', label: 'Step Up' },
    { value: 'Residual', label: 'Residual' },

  ];

  readonly INS_CONSENT_IDS = {
    agreeDisagree: "IU-Insurance-Consent-Agree-Disagree",
    annexures: "IU-Insurance-Consent-Annexures",
    accountNo: "IU-Insurance-Consent-Account-No",
    loanRequired: "IU-Loan-Is-Required",
    amount: "IU-Insurance-Amount",
    companies: "IU-Insurance-Companies",
  };

  readonly SECTION10_IDS = {
    judgment: "IU-Section10-ANY-JUDGEMENTS",
    accountNo: "IU-Section10-Account-No",
    customerName: "IU-Section10-Customer-Name",
    isPep: "IU-Section10-Is-PEP",
    pepDescription: "IU-Section10-PEP-Description",
  };

  readonly OFFICE_USE_IDS = {
    to: "IU-Proposed-Facilities-To",
    irregularCrib: "IU-Irregular-CRIB-Records-Comments",
    amendment: "IU-Proposed-Amendment",
    immFamilyBankTeam: "IU-Is-Immediate-family-member-Bank-Team-member",
    relatedParty: "IU-Is-Applicant-related-party",
    bmComments: "IU-Comments-and-recommendation-by-Branch-Manager",
    bmName: "IU-Branch-Manager-Name",
    bmDate: "IU-Branch-Manager-Section-Date",
    rmComments: "IU-Comments-and-recommendation-by-RM-or-SRM",
    rmName: "IU-SRM-or-RM-Name",
    rmDate: "IU-SRM-or-RM-Section-Date",
  };

  readonly SECURITY_TO_OFFERED_IDS = {
    toBeOffered: "IU-Security-To-Be-Offered",
    location: "IU-Security-to-be-offered-location",
    plan: "IU-Security-to-be-offered-plan-no",
    lotNo: "IU-Security-to-be-offered-lot-no",
    extent: "IU-Security-to-be-offered-extent",
    ownership: "IU-Security-to-be-offered-ownership",
    nameOfLand: "IU-Security-to-be-offered-name-of-land",
    floorArea: "IU-Security-to-be-offered-floor-area",
    marketValue: "IU-Security-to-be-offered-present-market-value"
  }

  securityToBeOfferedErros = { floorArea: '', marketValue: '' };

  //motor vehicle
  motorVehicle: Utils.MotorVehicleRow[] = [];
  motorForm: Utils.MotorVehicleRow = {
    typeOfVehicleAndRegisterNO: "",
    presentOwner: "",
    marketValue: "",
    mortgages: "",
  };

  // Shares/TBills/Deposits
  sharesTbd: Utils.SharesTBillsDepositsRow[] = [];
  sharesForm: Utils.SharesTBillsDepositsRow = {
    type: "",
    nameOfCompany: "",
    valueOfBills: "",
    assigments: "",
  };

  // Life Insurance Policies
  lifeIns: Utils.LifeInsuranceRow[] = [];
  lifeForm: Utils.LifeInsuranceRow = {
    nameOfPolicyHolder: "",
    nameOfCompany: "",
    policyNo: "",
    faceValue: "",
    premiumFrequency: "",
    assigments: "",
  };

  assetErrors: Record<Utils.SectionKey, Record<string, string>> = {
    LB: {},
    MV: {},
    STD: {},
    LIP: {},
  };

  // Facility Details
  facilityRows: Utils.FacilityDetailRow[] = [];
  facilityForm: Utils.FacilityDetailRow = {
    typeOfFacilityRequired: "",
    purposeOfFacility: "",
    amountOfFaclity: "",
    numberOfYearsRequired: "",
    repaymentOption: "",
    preferedLoanDedDate: "",
    applicantsContribution: "",
    advancePayement: "",
  };
  facilityErrors = {
    amountOfFaclity: '',
    numberOfYearsRequired: '',
    applicantsContribution: '',
  };

  //Security Offered
  securityRows: Utils.SecurityOfferedRow[] = [];
  securityForm: Utils.SecurityOfferedRow = {
    description: "",
    floorAreaExtent: "",
    ownedBy: "",
    marketValue: "",
    mortgages: "",
  };
  securityToBeOfferedField: Utils.DocumentInputField | null = null;
  securityErrors = {
    marketValue: '',
  };

  //MONTHLY INCOME & EXPENDITURE
  section6Applicants: Utils.Section6ApplicantRow[] = [];
  showSection6 = false;
  section6Errors: any = {
    Income: {},
    Expenditure: {}
  };

  section7Applicants: Utils.Section7ApplicantRow[] = [];
  showSection7 = false;

  //BANK ACCOUNTS  (SAMPATH / OTHER )
  sampathForm: Utils.SampathBankAccountRow = {
    bankAndBranch: "",
    accountType: "",
    accountNo: "",
    dateOpened: "",
    presentBalance: "",
    accountHolderName: "",
  };
  sampathAccounts: Utils.SampathBankAccountRow[] = [];
  sampathErrors = { accountNo: '', presentBalance: '' };

  otherForm: Utils.OtherBankAccountRow = {
    bankAndBranch: "",
    accountType: "",
    accountNo: "",
    dateOpened: "",
    presentBalance: "",
    accountHolderName: "",
  };
  otherAccounts: Utils.OtherBankAccountRow[] = [];
  otherBankErrors = { accountNo: '', presentBalance: '' };

  showSampathAccountsSection = false;
  showOtherBankAccountsSection = false;

  liabilitiesForm: Utils.ExistingLiabilityRow = {
    creditorName: "",
    referenceNo: "",
    repaymentPeriod: "",
    security: "",
    amountBorrowed: "",
    balancePayableNow: "",
    balancePeriodToComplete: "",
    borrowedBy: "",
    purpose: "",
  };
  liabilitiesErrors = { amountBorrowed: '', balancePayableNow: '' };
  existingLiabilities: Utils.ExistingLiabilityRow[] = [];

  showExistingLiabilitiesSection = false;

  section10JudgmentField: Utils.DocumentInputField | null = null;
  section10AccountNoField: Utils.DocumentInputField | null = null;
  section10CustomerNameField: Utils.DocumentInputField | null = null;

  section10IsPepField: Utils.DocumentInputField | null = null;
  section10PepDescriptionField: Utils.DocumentInputField | null = null;

  proposedFacilities: Utils.ProposedFacilityRow[] = [];
  showProposedFacilitiesSection = false;

  private readonly S7_STORE_ID = 'S7_DATA_JSON';

  constructor(
    private readonly leadComprehensiveService: LeadComprehensiveService,
    public mdbModalRef: MDBModalRef,
    private readonly currencyPipe: CurrencyPipe,
    private readonly applicationService: ApplicationService,
    public cdr: ChangeDetectorRef,
    private readonly advanceAnalyticsService: AdvanceAnalyticsService,
  ) { }

  ngOnInit() {
    const payload = (this as any).content || {};
    this.applicantCount = payload.applicantCount ? payload.applicantCount : 1;
    this.isPluralApplicants = this.applicantCount > 1;
    this.leadId = payload.leadId ? payload.leadId : 0;
    this.compLeadId = payload.compLeadId ? payload.compLeadId : 0;

    this.selectedApplicationId = payload.selectedApplicationId
      ? payload.selectedApplicationId
      : 0;
    this.selectedDocumentContent =
      payload.selectedDocumentContent || payload.selectedDocumentTemplate || "";

    this.selectedDocumentStatus = payload.selectedDocumentStatus || "";
    this.selectedDocumentElement = {
      ...(payload.selectedDocumentElement || {}),
    };

    if (!this.selectedDocumentContent) {
      this.showSection6 = false;
      this.showSection7 = false;
      return;
    }

    const parser = new DOMParser();
    const doc = parser.parseFromString(
      this.selectedDocumentContent,
      "text/html",
    );

    this.showFacilityDetailsSection = !!doc.getElementById('FACILITY_DETAILS_BODY');
    this.showProposedFacilitiesSection = !!doc.getElementById('PROPOSED_FACILITIES_BODY');

    if (this.showProposedFacilitiesSection) {
      this.hydrateProposedFacilitiesFromDoc(doc);
    }

    if (this.showFacilityDetailsSection && (!this.facilityRows || this.facilityRows.length === 0)) {
      this.hydrateFacilityRowsFromDoc(doc);
    }

    this.showSection6 =
      !!doc.querySelector(".section6-wrap") ||
      !!doc.querySelector("#APPLICANTS_TABLE");

    this.showSection7 =
      !!doc.querySelector('.section7-space') ||
      !!doc.querySelector('[id^="IU-Section7-"]');

    this.showFacilityDetailsSection = !!doc.getElementById(
      "FACILITY_DETAILS_BODY",
    );

    this.showSecurotyToBeOfferedSection = !!(
      doc.getElementById('IU-Security-To-Be-Offered') ||
      doc.getElementById('IU-Security-to-be-offered-location') ||
      doc.getElementById('IU-Security-to-be-offered-plan-no') ||
      doc.getElementById('IU-Security-to-be-offered-lot-no') ||
      doc.getElementById('IU-Security-to-be-offered-extent') ||
      doc.getElementById('IU-Security-to-be-offered-ownership') ||
      doc.getElementById('IU-Security-to-be-offered-name-of-land') ||
      doc.getElementById('IU-Security-to-be-offered-floor-area') ||
      doc.getElementById('IU-Security-to-be-offered-present-market-value')
    );

    this.showSampathAccountsSection = !!doc.getElementById(
      "SAMPATH_BANK_ACCOUNTS_BODY",
    );
    this.showOtherBankAccountsSection = !!doc.getElementById(
      "OTHER_BANK_ACCOUNTS_BODY",
    );
    this.showExistingLiabilitiesSection = !!doc.getElementById(
      "EXISTING_LIABILITIES_BODY",
    );

    const count = Math.max(1, this.getApplicantsCountFromTemplate(doc));

    this.section6Applicants = Array.from({ length: count }, (_, i) => ({
      applicantIndex: i + 1,
      income: {
        professionEmployment: "",
        business: "",
        industry: "",
        agriculture: "",
        interest: "",
        dividends: "",
        rentLease: "",
        commissions: "",
        others: "",
        total: "",
      },
      expenditure: {
        houseRent: "",
        householdExpenses: "",
        clothing: "",
        childrensEducation: "",
        personal: "",
        traveling: "",
        medical: "",
        loanRepayment: "",
        creditCardPayment: "",
        ratesAndTaxes: "",
        electricityFuel: "",
        insurance: "",
        dependents: "",
        others: "",
        total: "",
      },
    }));

    this.section7Applicants = Array.from({ length: count }, (_, i) => ({
      applicantIndex: i + 1,
      year1: {
        yearLabel: "",
        statutoryIncome: "",
        assessableIncome: "",
        taxableIncome: "",
        taxPaid: "",
      },
      year2: {
        yearLabel: "",
        statutoryIncome: "",
        assessableIncome: "",
        taxableIncome: "",
        taxPaid: "",
      },
      year3: {
        yearLabel: "",
        statutoryIncome: "",
        assessableIncome: "",
        taxableIncome: "",
        taxPaid: "",
      },
    }));

    this.hydrateSectionDataFromHtml(doc);

    this.prepareDocumentInputs(this.selectedDocumentContent);
    this.normalizeAllMultiSelectValues();
    this.normalizeOverseasDisplayOrder();
    this.hydrateConsentFields();
    this.hydrateSection7FromDoc(doc);
    this.hydrateSection6FromFields();
    this.recomputeSection6TotalsInModel();
    this.primeSection6SurplusFields();
    this.hydrateSection10Fields();
    this.rebindConsentAndDeclarationFields();

    if (!this.hasSection7Values()) {
      this.readSection7FromJsonStore(doc);
    }

    if (!this.showSection7 && this.section7Applicants.length) {
      this.showSection7 = true;
    }

    if (!this.hasSection7Values()) {
      let ok = this.readSection7FromJsonStore(doc);
    }


    Object.values(this.assetSections).forEach((sec) => {
      sec.showFlag = !!doc.getElementById(sec.bodyId);
    });

    this.cdr.detectChanges();
  }

  getDefualtDocumentName() {
    return "";
  }

  prepareDocumentInputs(content: any) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, "text/html");

    let inputElements: any[] = [];
    Utils.SDConstants.elementIdTypes.forEach((element: string) => {
      const elements: any = doc.querySelectorAll(`[id^="${element}-"`);

      if (elements !== null && elements.length > 0) {
        inputElements = [...inputElements, ...elements];
      }
    });

    inputElements.forEach((element: HTMLElement) => {
      const inputId: string = element.id;

      if (/^V-/.test(inputId)) return;

      const elementValueFeild: HTMLElement = doc.getElementById(
        this.getReplaceValue(inputId),
      );
      const hasValueFeild: boolean =
        elementValueFeild !== undefined && elementValueFeild !== null;

      const isRequired: boolean =
        element.getAttribute("aria-required") === "true";
      const inputType: string = element.getAttribute("itemprop");

      const mainElement: HTMLElement = doc.getElementById(
        this.getMainEelementId(inputId),
      );

      let feildValue: any = this.getElementValue(
        doc,
        inputId,
        this.getIDProp(inputId),
        hasValueFeild,
      );

      let isSelection: boolean =
        inputType === Utils.SDConstants.inputMode.SELECT ||
        inputType === Utils.SDConstants.inputMode.SELECT_TEXT ||
        inputType === Utils.SDConstants.inputMode.MULTI_SELECT;

      const displayOrder = element.getAttribute("itemid");

      let inputLabel: string =
        hasValueFeild && isSelection
          ? this.getCustomizeLable(inputId)
          : hasValueFeild
            ? element.innerHTML
            : this.getCustomizeLable(inputId);

      let inputValue: any =
        inputType === Utils.SDConstants.inputMode.DATE
          ? this.getFormattedDate(feildValue)
          : feildValue;

      let computedValue: any;
      if (inputType === Utils.SDConstants.inputMode.MULTI_SELECT) {
        computedValue = this.toArray(
          this.preparedValue(inputLabel, inputValue),
        );
      } else {
        computedValue = this.preparedValue(inputLabel, inputValue);
      }

      let feild: Utils.DocumentInputField = {
        id: inputId,
        label: inputLabel,
        inputType: inputType,
        value: inputValue,
        isRequired: isRequired,
        valueId: hasValueFeild
          ? inputId.replace(this.getIDProp(inputId), "V-")
          : inputId,
        selectOptions: isSelection
          ? this.getElementSelectOptions(this.getCustomizeLable(inputId))
          : [],
        showInput: !inputId.includes(
          `-${Utils.SDConstants.elementIdType.DPL}-`,
        ),
        hasMainEelement: mainElement !== undefined && mainElement !== null,
        error: "",
        disabled: this.isDisabledInput(inputId),
        displayOrder: displayOrder ? parseInt(displayOrder) : 0,
      };
      this.documentInputFields.push(feild);
    });

    this.documentInputFields = this.documentInputFields.map((f: any) => {
      if (f.inputType === Utils.SDConstants.inputMode.MULTI_SELECT) {
        // If already array, leave it; if string, split to array; if null, set []
        if (!Array.isArray(f.value)) {
          const s = (f.value == null ? '' : ('' + f.value)).trim();
          f.value = s ? s.split(/[;,]/).map(x => x.trim()).filter(Boolean) : [];
        }
      }
      return f;
    });

    this.documentInputFields = this.documentInputFields.sort(
      (a: Utils.DocumentInputField, b: Utils.DocumentInputField) =>
        a.displayOrder - b.displayOrder,
    );

    const rank = (id: string) => id.startsWith('IU-') ? 0 : id.startsWith('IS-') ? 1 : 2;
    this.documentInputFields.sort((a, b) => rank(a.id) - rank(b.id) || (a.displayOrder - b.displayOrder));

    const keepByValueId = new Map<string, Utils.DocumentInputField>();
    const deduped: Utils.DocumentInputField[] = [];
    for (const f of this.documentInputFields) {
      const key = f.valueId || f.id;
      if (!keepByValueId.has(key)) {
        keepByValueId.set(key, f);
        deduped.push(f);
      }
    }
    this.documentInputFields = deduped;

  }

  preparedValue(label: string, value: any) {
    return value;
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
    hasValueFeild: boolean,
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
      case "Current Applicant I Address":
      case "Current Applicant II Address":
        return this.addressType;
      case "Nationality Applicant I":
      case "Nationality Applicant II":
        return this.nationality;
      case "Insurance Consent Agree Disagree":
        return this.consentAgreeDisagree;
      case "Insurance Consent Annexures":
        return this.annexureOptions;
      case "Loan Is Required":
        return this.yesNo;
      case "Is Immediate family member Bank Team member":
      case "Is Applicant related party":
      case "Is Employed Residing in overseas Contact Person Name In Sri Lanka":
        return this.yesNo;
      default:
        // Address type for any numeric applicant
        if (/^Current Applicant \d+ Address(?: Type)?$/i.test(prop))
          return this.addressType;
        if (/^Applicant \d+ Current Address(?: Type)?$/i.test(prop))
          return this.addressType;

        // Nationality (multi-select) for any numeric applicant
        if (/^Nationality Applicant \d+$/i.test(prop)) return this.nationality;
        if (/^Applicant \d+ Nationality$/i.test(prop)) return this.nationality;

        if (/^Current Applicant \d+ Is Pep(?: Type)?$/i.test(prop))
          return this.yesNo;
        if (/^Applicant \d+ Is Pep(?: Type)?$/i.test(prop)) return this.yesNo;
        if (/^Applicant \d+ Is Employed Residing in overseas Contact Person Name In Sri Lanka(?: Type)?$/i.test(prop)) return this.yesNo;

        if (/^Employment Applicant \d+ Employment Type(?: Type)?$/i.test(prop)) return this.employmentType;

        if (/^Applicant \d+ Civil Status(?: Type)?$/i.test(prop))
          return this.civilStatus;

        if (
          /^Employment Applicant \d+ Avg Salary Growth in Percentage(?: Type)?$/i.test(prop)
        )
          return this.avgSalaryGrowth;
        return [];
    }
  }

  getValue(amount: any) {
    if (isNaN(amount)) {
      return amount.replace(/,/g, "");
    }
    return amount;
  }

  setCurrencyFormatValue(feild: Utils.DocumentInputField) {
    if (feild !== null && feild.value !== null) {
      if (!isNaN(feild.value) || !isNaN(this.getValue(feild.value))) {
        this.documentInputFields = this.documentInputFields.map(
          (d: Utils.DocumentInputField) => ({
            ...d,
            value:
              d.id === feild.id
                ? this.currencyPipe.transform(this.getValue(d.value), "", "")
                : d.value,
            error: d.id === feild.id ? "" : d.error,
          }),
        );
      } else {
        this.documentInputFields = this.documentInputFields.map(
          (d: Utils.DocumentInputField) => ({
            ...d,
            error: d.id === feild.id ? "Invalid amount." : d.error,
          }),
        );
      }
    }

    this.cdr.detectChanges();
  }

  handleSave() {
    const request = this.getPreparedRequest(SDConstants.documentStatusConst.DRAFT);
    const payload = this.buildPayload(SDConstants.documentStatusConst.DRAFT);

    // 1) Save the HTML digital document
    this.leadComprehensiveService
      .saveDigitalApplication(request)
      .then((resp1: any) => {
        const savedDoc = resp1.response ? resp1.response : resp1;

        // 2) Save structured application (ApplicationDTO)
        return this.leadComprehensiveService
          .saveApplication(this.compLeadId, payload)
          .then((resp2: any) => {
            const savedApp = resp2.response ? resp2.response : resp2;

            return { savedDoc, savedApp };
          });
      })
      .then(({ savedDoc, savedApp }) => {
        // Emit one event to parent/view
        this.action.next({
          type: "SAVED",
          saved: savedDoc,          // your digital application saved record
          application: savedApp,    // your ApplicationDTO saved record
        });

        this.mdbModalRef.hide();
      })
      .catch((err) => {
        console.error("Save failed:", err);
        // Optionally show toaster
        // this.alertService.showToaster("Save failed", SETTINGS.TOASTER_MESSAGES.error);
      });
  }

  // handlePrint() {
  //   let request: any = this.getPreparedRequest(
  //     SDConstants.documentStatusConst.PRINT
  //   );

  //   this.leadComprehensiveService
  //     .saveDigitalApplication(request)
  //     .then((data: any) => {
  //       if (data !== null) {
  //         this.previewDocument(request.documentContent);
  //         this.action.next(data);
  //         this.mdbModalRef.hide();
  //       }
  //     });
  // }

  getMasterValueId(duplicateId: string) {
    const str = duplicateId;
    const index = str.indexOf("-DPL-");
    const result = index !== -1 ? str.substring(0, index) : str;
    return result ? result : "";
  }

  getMasterValue(element: Utils.DocumentInputField) {
    const masterValueId: string = this.getMasterValueId(element.id);
    const feild: Utils.DocumentInputField = this.documentInputFields.find(
      (f: Utils.DocumentInputField) => f.valueId === masterValueId,
    );
    return feild !== undefined && feild !== null ? feild.value : "";
  }

  isSaveDisabled() {
    return this.documentInputFields
      .filter((feild: Utils.DocumentInputField) => feild.showInput)
      .some(
        (feild: Utils.DocumentInputField) =>
          (feild.isRequired && (feild.value === null || feild.value === "")) ||
          feild.error !== "",
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

  getValueById(id: string) {
    let inpuElement: Utils.DocumentInputField = this.documentInputFields.find(
      (element: Utils.DocumentInputField) => element.id === id,
    );
    return inpuElement !== undefined && inpuElement !== null
      ? inpuElement.value
      : "";
  }

  toArray(v: any): string[] {
    if (Array.isArray(v)) return v;
    if (typeof v === 'string') {
      return v.split(/[;,]/).map(s => s.trim()).filter(Boolean);
    }
    return [];
  }

  previewDocument(updateDocumentContent: any) {
    let documentName: string = "Digital Application Form";
    let printContents, popupWin;
    printContents = updateDocumentContent;
    popupWin = window.open("", "_blank", "top=0,left=0,height=80%,width=auto");
    popupWin.document.open();

    popupWin.document.write(`
                <html>
                  <head>
                    <title>${documentName}</title>
                  </head>
                  <body onload=" window.print();" onafterprint="window.close()">${printContents}</body>
                </html>`);

    popupWin.document.close();
  }

  getPreparedRequest(status: any) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(
      this.selectedDocumentContent,
      "text/html",
    );

    this.syncConsentAndDeclarationToCanonical();

    this.documentInputFields.forEach((element: Utils.DocumentInputField) => {

      const isSelect = element.inputType === Utils.SDConstants.inputMode.SELECT
        || element.inputType === 'selectText'
        || element.inputType === Utils.SDConstants.inputMode.MULTI_SELECT;

      if (!isSelect && element.inputType !== Utils.SDConstants.inputMode.DATE) {
        let targetOLElement = doc.getElementById(element.valueId);
        if (!targetOLElement) targetOLElement = this.ensureValueNode(doc, element.valueId);
        if (element.showInput) {
          targetOLElement.innerHTML = element.value;
        } else {
          targetOLElement.innerHTML = this.getMasterValue(element);
        }
      } else if (element.inputType === Utils.SDConstants.inputMode.DATE) {
        let targetOLElement = doc.getElementById(element.valueId);
        if (!targetOLElement) targetOLElement = this.ensureValueNode(doc, element.valueId);
        if (element.showInput) {
          targetOLElement.innerHTML = element.value
            ? this.formatDateOrdinalUpper(element.value)
            : element.value;
        } else {
          let value: any = this.getMasterValue(element);
          targetOLElement.innerHTML = value
            ? this.formatDateOrdinalUpper(value)
            : value;
        }
      } else {
        let targetOLElement = doc.getElementById(element.valueId);
        if (!targetOLElement) targetOLElement = this.ensureValueNode(doc, element.valueId);
        const valForDisplay = this.stringifyTagValue(element.value); // joins arrays with ", "
        targetOLElement.innerHTML = valForDisplay;

        switch (element.id) {
          case /^IS-Current-Applicant-\d+-Address$/i.test(element.id)
            ? element.id
            : "":
            {

              const match = element.id.match(/^IS-Current-Applicant-(\d+)-Address$/i);
              if (match) {
                const num = match[1];
                const container = doc.getElementById(`Applicant-${num}-Other-Container`);
                if (container) {
                  container.style.display = (element.value === 'Other') ? 'block' : 'none';
                }
              }

            }
            break;

          default:
            break;
        }
      }
    });

    this.applyConditionalContainers(doc);
    this.toggleOverseasContactBlocks(doc);

    Object.values(this.assetSections).forEach((sec) => {
      const body = doc.getElementById(sec.bodyId);
      if (body) {
        body.innerHTML = this.constructRows(sec.key);
      }
      if (sec.totalId) {
        const totalEl = doc.getElementById(sec.totalId);
        if (totalEl) {
          totalEl.innerHTML = this.escapeHtml(
            this.formatCurrency(this.computeTotal(sec.key)),
          );
        }
      }
    });

    const count = this.getApplicantsCountFromTemplate(doc);
    const IWe = count > 1 ? "We" : "I";
    const MeUs = count > 1 ? "us" : "me";
    const MyOur = count > 1 ? "our" : "my";

    this.isPluralApplicants = count > 1;

    const iWeEl = doc.getElementById("IS-DPL-Insurance-Consent-IWe");
    const meUsEl = doc.getElementById("IS-DPL-Insurance-Consent-MeUs");
    const myOurEl = doc.getElementById("IS-DPL-Insurance-Consent-MyOur");

    const setText = (id: string, v: string) => {
      const el = doc.getElementById(id);
      if (el) el.innerHTML = v;
    };

    setText("IS-DPL-Section10-IWe", IWe);
    setText("IS-DPL-Section10-MeUs", MeUs);
    setText("IS-DPL-Section10-MyOur", MyOur);

    if (iWeEl) iWeEl.innerHTML = IWe;
    if (meUsEl) meUsEl.innerHTML = MeUs;
    if (myOurEl) myOurEl.innerHTML = MyOur;

    [
      "IS-DPL-Insurance-Consent-IWe-2",
      "IS-DPL-Insurance-Consent-IWe-3",
    ].forEach((id) => {
      const el = doc.getElementById(id);
      if (el) el.innerHTML = IWe;
    });

    [
      "IS-DPL-Insurance-Consent-MeUs-2",
      "IS-DPL-Insurance-Consent-MeUs-3",
    ].forEach((id) => {
      const el = doc.getElementById(id);
      if (el) el.innerHTML = MeUs;
    });

    [
      "IS-DPL-Section10-IWe-2",
      "IS-DPL-Section10-IWe-3",
      "IS-DPL-Section10-IWe-4",
      "IS-DPL-Section10-IWe-5",
    ].forEach((id) => setText(id, IWe));

    [
      "IS-DPL-Section10-MyOur-2",
      "IS-DPL-Section10-MyOur-3",
      "IS-DPL-Section10-MyOur-4",
    ].forEach((id) => setText(id, MyOur));

    const fdBody = doc.getElementById("FACILITY_DETAILS_BODY");
    if (fdBody) {
      fdBody.innerHTML = this.constructFacilityRows();
    }

    //remove 
    const secBody = doc.getElementById("SECURITY_OFFERED_BODY");
    if (secBody) {
      secBody.innerHTML = this.constructSecurityRows();
    }

    // --- Section 8.1 Sampath Bank Accounts ---
    const sbBody = doc.getElementById("SAMPATH_BANK_ACCOUNTS_BODY");
    if (sbBody) {
      sbBody.innerHTML = this.constructSection8Rows(this.sampathAccounts);
    }

    // --- Section 8.2 Other Bank Accounts ---
    const obBody = doc.getElementById("OTHER_BANK_ACCOUNTS_BODY");
    if (obBody) {
      obBody.innerHTML = this.constructSection8Rows(this.otherAccounts);
    }

    // --- Section 9: Existing Liabilities ---
    const elBody = doc.getElementById("EXISTING_LIABILITIES_BODY");
    if (elBody) {
      elBody.innerHTML = this.constructExistingLiabilitiesRows(
        this.existingLiabilities,
      );
    }

    const tot = this.computeExistingLiabilitiesTotals();
    const amountCell = doc.getElementById("EXISTING_LIABILITIES_AMOUNT_TOTAL");
    const balanceCell = doc.getElementById("EXISTING_LIABILITIES_BALANCE_TOTAL");
    if (amountCell) amountCell.innerHTML = this.escapeHtml(this.formatCurrency(tot.amountBorrowed));
    if (balanceCell) balanceCell.innerHTML = this.escapeHtml(this.formatCurrency(tot.balancePayableNow));

    // --- For Office Use Only — Proposed Facilities ---
    const pfBody = doc.getElementById("PROPOSED_FACILITIES_BODY");
    if (pfBody) {
      pfBody.innerHTML = this.constructProposedFacilitiesRows();
    }

    //total market value
    const totalCell = doc.getElementById("ASSETS_LB_TOTAL");
    if (totalCell) {
      const total = this.computeTotalMarketValue();
      totalCell.innerHTML = this.escapeHtml(this.formatCurrency(total));
    }

    this.syncSection6ToFields();
    this.computeSection6Totals(doc);

    let count7 = this.section7Applicants ? this.section7Applicants.length : 0;
    for (let a = 0; a < count7; a++) {
      let row = this.section7Applicants[a];
      let idx = row.applicantIndex;

      this.writeSection7YearLabel(
        doc,
        1,
        (row.year1.yearLabel || "").trim(),
        idx,
      );
      this.writeSection7YearLabel(
        doc,
        2,
        (row.year2.yearLabel || "").trim(),
        idx,
      );
      this.writeSection7YearLabel(
        doc,
        3,
        (row.year3.yearLabel || "").trim(),
        idx,
      );

      this.writeSection7Metric(
        doc,
        1,
        idx,
        "Statutory-Income",
        (row.year1.statutoryIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        1,
        idx,
        "Assessable-Income",
        (row.year1.assessableIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        1,
        idx,
        "Taxable-Income",
        (row.year1.taxableIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        1,
        idx,
        "Tax-Paid",
        (row.year1.taxPaid || "").trim(),
      );

      this.writeSection7Metric(
        doc,
        2,
        idx,
        "Statutory-Income",
        (row.year2.statutoryIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        2,
        idx,
        "Assessable-Income",
        (row.year2.assessableIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        2,
        idx,
        "Taxable-Income",
        (row.year2.taxableIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        2,
        idx,
        "Tax-Paid",
        (row.year2.taxPaid || "").trim(),
      );

      this.writeSection7Metric(
        doc,
        3,
        idx,
        "Statutory-Income",
        (row.year3.statutoryIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        3,
        idx,
        "Assessable-Income",
        (row.year3.assessableIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        3,
        idx,
        "Taxable-Income",
        (row.year3.taxableIncome || "").trim(),
      );
      this.writeSection7Metric(
        doc,
        3,
        idx,
        "Tax-Paid",
        (row.year3.taxPaid || "").trim(),
      );
    }

    this.writeSection7JsonStore(doc);

    let updatedDocumentContent: string =
      this.applyPronouns(doc.documentElement.outerHTML, this.applicantCount);
    let request: any = {
      digitalApplicationID: this.selectedApplicationId,
      leadID: this.leadId,
      documentStatus: status,
      actionComment: "",
      createdBy: this.applicationService.getLoggedInUserDisplayName(),
      createdDate: new Date().toISOString(),
      modifiedBy: this.applicationService.getLoggedInUserDisplayName(),
      modifiedDate: new Date().toISOString(),
      documentContent: updatedDocumentContent,
      compPartyIds: this.content.selectedApplicantIds,
      compLeadId: this.compLeadId
    };
    return request;
  }

  findOtherField(
    baseField: Utils.DocumentInputField,
  ): Utils.DocumentInputField | null {
    const otherId = this.buildOtherId(baseField.id);
    const found = this.documentInputFields.find(function (f) {
      return f.id === otherId;
    });
    return found || null;
  }

  buildOtherId(baseId: string): string {
    return baseId + "-Other";
  }

  assetSections: Record<Utils.SectionKey, Utils.AssetSection<any>> = {
    LB: {
      key: "LB",
      title: "3.1 LANDS/BUILDINGS",
      bodyId: "ASSETS_LB_BODY",
      totalId: "ASSETS_LB_TOTAL",
      sumKey: "marketValue",
      columns: [
        { key: "description", label: "Asset Description" },
        { key: "floorAreaExtent", label: "Floor area, extent etc.." },
        { key: "ownedBy", label: "Owned by" },
        {
          key: "marketValue",
          label: "Market Value (LKR)",
          type: "currency",
          style: "text-align: right;",
        },
        { key: "mortgages", label: "Mortgages (if any)" },
      ],
      showFlag: false,
      formModel: this.assetForm,
      rows: this.assetsLB,
    },
    MV: {
      key: "MV",
      title: "3.2 MOTOR VEHICLES",
      bodyId: "MOTOR_VEHICLE_BODY",
      totalId: "MOTOR_VEHICLE_TOTAL",
      sumKey: "marketValue",
      columns: [
        {
          key: "typeOfVehicleAndRegisterNO",
          label: "Type of Vehicle & Registration No.",
        },
        { key: "presentOwner", label: "Present Owner" },
        {
          key: "marketValue",
          label: "Market Value (LKR)",
          type: "currency",
          style: "text-align: right;",
        },
        { key: "mortgages", label: "Mortgages (if any)" },
      ],
      showFlag: false,
      formModel: this.motorForm,
      rows: this.motorVehicle,
    },
    STD: {
      key: "STD",
      title: "3.3 SHARES / TREASURY BILLS / DEPOSITS",
      bodyId: "SHARES_TBILLS_DEPOSITS_BODY",
      totalId: "SHARES_TBILLS_DEPOSITS_TOTAL",
      sumKey: "valueOfBills",
      columns: [
        { key: "type", label: "Type" },
        { key: "nameOfCompany", label: "Name of Company" },
        {
          key: "valueOfBills",
          label: "Value of Treasury Bills / Shares / Deposits (LKR)",
          type: "currency",
          style: "text-align: right;",
        },
        { key: "assigments", label: "Assignments (if any)" },
      ],
      showFlag: false,
      formModel: this.sharesForm,
      rows: this.sharesTbd,
    },
    LIP: {
      key: "LIP",
      title: "3.4 LIFE INSURANCE POLICIES",
      bodyId: "LIFE_INS_POLICIES_BODY",
      totalId: "LIFE_INS_POLICIES_TOTAL",
      sumKey: "faceValue",
      columns: [
        { key: "nameOfPolicyHolder", label: "Name of policy Holder" },
        { key: "nameOfCompany", label: "Name of Company" },
        { key: "policyNo", label: "Policy No." },
        {
          key: "faceValue",
          label: "Face Value (LKR)",
          type: "currency",
          style: "text-align: right;",
        },
        { key: "premiumFrequency", label: "Premium Rs. & Frequency" },
        { key: "assigments", label: "Assignments (if any)" },
      ],
      showFlag: false,
      formModel: this.lifeForm,
      rows: this.lifeIns,
    },
  };

  stringifyTagValue(v: any): string {
    if (Array.isArray(v)) return v.join(", ");
    return v == null ? "" : String(v);
  }

  getApplicantsCountFromTemplate(doc: Document): number {
    // 1) Preferred: count applicant columns in the APPLICANTS_TABLE header
    const table = doc.getElementById('APPLICANTS_TABLE');
    if (table) {
      const headerThs = table.querySelectorAll('thead tr th');
      const n = Math.max(0, headerThs.length - 1); // first th is blank label column
      if (n > 0) return n;
    }

    // 2) Fallback: scan IDs for "Applicant-<n>"
    const els = Array.from(doc.querySelectorAll('[id]')) as HTMLElement[];
    let maxNum = 0;

    els.forEach(el => {
      const m = el.id.match(/Applicant-(\d+)/i);
      if (m) {
        const n = parseInt(m[1], 10);
        if (!isNaN(n)) maxNum = Math.max(maxNum, n);
      }
    });

    return maxNum || 1;
  }

  get assetSectionsOrdered(): Utils.AssetSection<any>[] {
    return this.assetOrder
      .map((k) => this.assetSections[k])
      .filter((sec) => !!sec && sec.showFlag);
  }

  addRow<K extends Utils.SectionKey>(key: K) {
    const sec = this.assetSections[key];
    if (!sec || !sec.formModel || !sec.columns || sec.columns.length === 0) {
      return;
    }

    const fm = sec.formModel;

    // Require either first column OR owner column (if present) to be non-empty
    const firstKey = sec.columns[0].key;
    const ownerCol = sec.columns.find((c) =>
      (c.label || "").toLowerCase().includes("owner"),
    );
    const ownerKey = ownerCol ? ownerCol.key : firstKey;

    const first = (fm[firstKey] || "").toString().trim();
    const owner = (fm[ownerKey] || "").toString().trim();
    if (!first && !owner) {
      return;
    }

    // Build new row, normalizing currency fields
    const newRow: any = {};
    sec.columns.forEach((col) => {
      const v = fm[col.key];
      newRow[col.key] =
        col.type === "currency"
          ? this.toNumber(v)
          : (v || "").toString().trim();
    });

    sec.rows.push(newRow);

    // Reset the form model keys used by this section
    sec.columns.forEach((col) => {
      fm[col.key] = "";
    });

    this.cdr.detectChanges();
  }

  removeRow<K extends Utils.SectionKey>(key: K, index: number) {
    const sec = this.assetSections[key];
    if (!sec || !Array.isArray(sec.rows)) return;
    if (index < 0 || index >= sec.rows.length) return;

    sec.rows.splice(index, 1);
    this.cdr.detectChanges();
  }

  constructRows<K extends Utils.SectionKey>(key: K): string {
    const sec = this.assetSections[key];
    return sec.rows
      .map((row: any) => {
        const tds = sec.columns
          .map((col) => {
            const raw = row[col.key];
            const text =
              col.type === "currency"
                ? this.formatCurrency(raw)
                : this.escapeHtml(raw);
            const style = col.style
              ? ` style="height: 20px; white-space: pre-wrap; ${col.style}"`
              : ' style="height: 20px; white-space: pre-wrap;"';
            return `<td${style}>${text}</td>`;
          })
          .join("");
        return `<tr>${tds}</tr>`;
      })
      .join("");
  }

  computeTotal<K extends Utils.SectionKey>(key: K): number {
    const sec = this.assetSections[key];
    if (!sec.sumKey) return 0;
    return sec.rows.reduce(
      (sum, row: any) => sum + this.toNumber(row[sec.sumKey!]),
      0,
    );
  }

  // Format market value with CurrencyPipe for display in the document
  formatCurrency(amount: number | string): string {
    const n = typeof amount === "string" ? +amount : amount;
    if (isNaN(n)) return String(amount || "");
    return this.currencyPipe.transform(n, "", "") || String(n);
  }

  escapeHtml(s: string): string {
    if (s == null) return "";
    return String(s)
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;");
  }

  // Sum all market values
  computeTotalMarketValue(): number {
    return this.assetsLB.reduce((sum, row) => {
      const mv =
        typeof row.marketValue === "string"
          ? +(row.marketValue.replace(/,/g, "").trim() || "0")
          : row.marketValue || 0;
      return sum + (isNaN(mv) ? 0 : mv);
    }, 0);
  }

  addFacilityRow() {
    const f = this.facilityForm;

    const valid = [f.typeOfFacilityRequired, f.purposeOfFacility].some(
      (v) => v != null && String(v).trim().length > 0,
    );
    if (!valid) return;

    // normalize amount
    let amt: any = f.amountOfFaclity;
    if (typeof amt === "string") amt = amt.replace(/,/g, "").trim();
    const amountNum = amt === "" ? 0 : isNaN(+amt) ? 0 : +amt;

    this.facilityRows.push({
      typeOfFacilityRequired: (f.typeOfFacilityRequired || "").trim(),
      purposeOfFacility: (f.purposeOfFacility || "").trim(),
      amountOfFaclity: amountNum,
      numberOfYearsRequired: (f.numberOfYearsRequired || "").trim(),
      repaymentOption: (f.repaymentOption || "").trim(),
      preferedLoanDedDate: (f.preferedLoanDedDate || "").trim(),
      applicantsContribution: (f.applicantsContribution || "").trim(),
      advancePayement: (f.advancePayement || "").trim(),
    });

    // reset form
    this.facilityForm = {
      typeOfFacilityRequired: "",
      purposeOfFacility: "",
      amountOfFaclity: "",
      numberOfYearsRequired: "",
      repaymentOption: "",
      preferedLoanDedDate: "",
      applicantsContribution: "",
      advancePayement: "",
    };
    this.syncProposedFacilitiesFromSection4();
    this.cdr.detectChanges();
  }

  removeFacilityRow(index: number) {
    if (index < 0 || index >= this.facilityRows.length) return;
    this.facilityRows.splice(index, 1);
    this.syncProposedFacilitiesFromSection4();
    this.cdr.detectChanges();
  }

  private hydrateFacilityRowsFromDoc(doc: Document): void {
    const body = doc.getElementById('FACILITY_DETAILS_BODY');
    if (!body) return;

    const rows = Array.from(body.querySelectorAll('tr'));
    // Map each row’s <td> to the Section‑4 model fields
    this.facilityRows = rows.map(tr => {
      const tds = tr.querySelectorAll('td');

      // First 4 columns come from backend; keep them read-only in UI
      const typeOfFacilityRequired = (tds[0].textContent || '').trim();
      const purposeOfFacility = (tds[1].textContent || '').trim();
      const amountStr = (tds[2].textContent || '').trim();
      const numberOfYearsRequired = (tds[3].textContent || '').trim();

      // Remaining 4 are editable; prefill if server ever adds values
      const repaymentOption = (tds[4].textContent || '').trim();
      const preferedLoanDedDate = (tds[5].textContent || '').trim();
      const applicantsContribution = (tds[6].textContent || '').trim();
      const advancePayement = (tds[7].textContent || '').trim();

      return {
        typeOfFacilityRequired,
        purposeOfFacility,
        amountOfFaclity: this.toNumberSafe(amountStr),   // modal already has toNumberSafe
        numberOfYearsRequired,
        repaymentOption,
        preferedLoanDedDate,
        applicantsContribution,
        advancePayement,
      };
    });

    // Keep the "Proposed Facilities" table synced from Section 4 (existing method)
    this.syncProposedFacilitiesFromSection4();  // preserves interestRate per your code
    this.cdr.detectChanges();
  }

  private hydrateProposedFacilitiesFromDoc(doc: Document): void {
    const body = doc.getElementById('PROPOSED_FACILITIES_BODY');
    if (!body) return;

    const rows = Array.from(body.querySelectorAll('tr'));
    this.proposedFacilities = rows.map(tr => {
      const tds = tr.querySelectorAll('td');
      return {
        typeOfFacilityRequired: (tds[0].textContent || '').trim(),
        amountOfFaclity: this.toNumberSafe((tds[1].textContent || '').trim()),
        purposeOfFacility: (tds[2].textContent || '').trim(),
        interestRate: (tds[3].textContent || '').trim(), //interest rate
        repaymentOption: (tds[4].textContent || '').trim(),
      };
    });

    this.cdr.detectChanges();
  }

  constructFacilityRows(): string {
    return this.facilityRows
      .map(
        (row) => `
    <tr>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(row.typeOfFacilityRequired)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(row.purposeOfFacility)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(this.formatCurrency(row.amountOfFaclity))}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(row.numberOfYearsRequired)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(row.repaymentOption)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(row.preferedLoanDedDate)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(row.applicantsContribution)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(row.advancePayement)}</td>
    </tr>
  `,
      )
      .join("");
  }

  isSection5Field(id: string): boolean {
    return id === "IU-Security-To-Be-Offered";
  }

  constructSecurityRows(): string {
    return this.securityRows
      .map(
        (r) => `
    <tr>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.description)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.floorAreaExtent)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.ownedBy)}</td>
      <td style="height:20px; white-space: pre-wrap; text-align:right;">${this.escapeHtml(this.formatCurrency(r.marketValue))}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.mortgages)}</td>
    </tr>
  `,
      )
      .join("");
  }

  isInsuranceConsentFieldId(id: string): boolean {
    return Object.values(this.INS_CONSENT_IDS).includes(id);
  }

  coerceMultiArray(f: Utils.DocumentInputField | null) {
    if (
      f &&
      f.inputType === Utils.SDConstants.inputMode.MULTI_SELECT &&
      !Array.isArray(f.value)
    ) {
      const s = (f.value || '').toString();   // replaced ?? with ||
      f.value = s
        ? s.split(/[;,]/).map(x => x.trim()).filter(Boolean)
        : [];
    }
  }

  hydrateConsentFields(): void {
    let parser = new DOMParser();
    let doc = parser.parseFromString(this.selectedDocumentContent, "text/html");

    let ensure = (iuId: string, kind: string): Utils.DocumentInputField => {
      let f = this.getFieldById(iuId);
      let vId = iuId.replace(/^IU-/, 'V-');
      let vEl = doc.getElementById(vId);
      let iuEl = doc.getElementById(iuId);

      if (f) {
        // --- Refresh if current value is empty ---
        let current = (f.value == null ? '' : ('' + f.value)).trim();
        if (!current) {
          let s = '';
          if (vEl && vEl.textContent) s = (vEl.textContent || '').trim();
          else if (iuEl && iuEl.textContent) s = (iuEl.textContent || '').trim();
          if (s) f.value = s;
        }
        // Point valueId to the node that exists (prefer V-)
        if (vEl) f.valueId = vId;
        else if (iuEl) f.valueId = iuId;

        // Hydrate select options if needed
        if (kind === 'select' || kind === 'multiSelect') {
          f.selectOptions = f.selectOptions && f.selectOptions.length
            ? f.selectOptions
            : this.getElementSelectOptions(this.getCustomizeLable(iuId));
        }
        return f;
      }

      // --- Create missing field ---
      let nf = this.createConsentFieldFromDoc(doc, iuId, kind);
      if (kind === 'select' || kind === 'multiSelect') {
        nf.selectOptions = this.getElementSelectOptions(this.getCustomizeLable(iuId));
      }
      return nf;
    };

    this.agreeDisagreeField = ensure(this.INS_CONSENT_IDS.agreeDisagree, 'select');
    this.annexuresField = ensure(this.INS_CONSENT_IDS.annexures, 'multiSelect');
    this.coerceMultiArray(this.annexuresField);

    this.accountNoField = ensure(this.INS_CONSENT_IDS.accountNo, 'text');
    this.loanRequiredField = ensure(this.INS_CONSENT_IDS.loanRequired, 'select');
    this.insuranceAmountField = ensure(this.INS_CONSENT_IDS.amount, 'decimal');
    this.insuranceCompaniesField = ensure(this.INS_CONSENT_IDS.companies, 'text');
  }

  isSection10Field(id: string): boolean {
    return id.startsWith("IU-Section10-");
  }

  hydrateSection10Fields(): void {
    let parser = new DOMParser();
    let doc = parser.parseFromString(this.selectedDocumentContent, "text/html");

    this.section10AccountNoField =
      this.getFieldById(this.SECTION10_IDS.accountNo) ||
      this.createFieldFromDoc(doc, this.SECTION10_IDS.accountNo);

    this.section10CustomerNameField =
      this.getFieldById(this.SECTION10_IDS.customerName) ||
      this.createFieldFromDoc(doc, this.SECTION10_IDS.customerName);

    this.section10JudgmentField =
      this.getFieldById(this.SECTION10_IDS.judgment) ||
      this.createFieldFromDoc(doc, this.SECTION10_IDS.judgment);

    this.section10IsPepField =
      this.getFieldById(this.SECTION10_IDS.isPep) ||
      this.createConsentFieldFromDoc(doc, this.SECTION10_IDS.isPep, 'select');
    if (this.section10IsPepField) {
      this.section10IsPepField.selectOptions = this.yesNo;
    }

    // PEP description
    this.section10PepDescriptionField =
      this.getFieldById(this.SECTION10_IDS.pepDescription) ||
      this.createFieldFromDoc(doc, this.SECTION10_IDS.pepDescription);

  }

  getFieldById(id: string): Utils.DocumentInputField | null {
    return this.documentInputFields.find((f) => f.id === id) || null;
  }

  // Parse number from a V-
  readNumberFromV(doc: Document, id: string): number {
    const el = doc.getElementById(id);
    const raw = el ? (el.innerHTML || "").replace(/,/g, "").trim() : "";
    const n = +raw;
    return isNaN(n) ? 0 : n;
  }

  // Write a number to V-
  writeTotalToV(doc: Document, id: string, value: number): void {
    const el = doc.getElementById(id);
    if (el) {
      el.innerHTML = this.escapeHtml(this.formatCurrency(value));
    }
  }

  computeSection6Totals(doc: Document): void {
    const count = this.getApplicantsCountFromTemplate(doc);

    const INCOME_KEYS_EXCEPT_OTHERS = [
      "Profession-Employment",
      "Business",
      "Industry",
      "Agriculture",
      "Interest",
      "Dividends",
      "Rent-Lease",
      "Commissions",
    ];
    const EXPENDITURE_KEYS_EXCEPT_OTHERS = [
      "House-rent",
      "Household-expenses",
      "Clothing",
      "Childrens-education",
      "Personal",
      "Traveling",
      "Medical",
      "Loan-repayment",
      "Credit-Card-Payment",
      "Rates-and-Taxes",
      "Electricity-Fuel",
      "Insurance",
      "Dependents",
    ];

    for (let i = 1; i <= count; i++) {
      // Income total
      let incomeSum = 0;
      for (const k of INCOME_KEYS_EXCEPT_OTHERS) {
        incomeSum += this.readNumberPreferringV(doc, `IU-Income-Applicant-${i}-${k}`);
      }
      // Expenditure total
      let expSum = 0;
      for (const k of EXPENDITURE_KEYS_EXCEPT_OTHERS) {
        expSum += this.readNumberPreferringV(doc, `IU-Expenditure-Applicant-${i}-${k}`);
      }

      const surplus = incomeSum - expSum;

      const ensure = (id: string) => this.ensureValueNode(doc, id);
      ensure(`IU-Income-Applicant-${i}-Total`).innerHTML =
        this.escapeHtml(this.formatCurrency(incomeSum));
      ensure(`IU-Expenditure-Applicant-${i}-Total`).innerHTML =
        this.escapeHtml(this.formatCurrency(expSum));
      ensure(`IU-Section6-Applicant-${i}-Surplus`).innerHTML =
        this.escapeHtml(this.formatCurrency(surplus));

    }
  }

  // Identify any Section 6 field
  isSection6Field(id: string): boolean {
    return /^IU-(Income|Expenditure)-Applicant-\d+-/.test(id);
  }

  isSection6TotalField(id: string): boolean {
    return /^IU-(Income|Expenditure)-Applicant-\d+-Total$/i.test(id);
  }

  isSection6SurplusField(id: string): boolean {
    return /^IU-Section6-Applicant-\d+-Surplus$/i.test(id);
  }

  getApplicantSurplus(a: Utils.Section6ApplicantRow): number {
    const inc = this.toNumberSafe(a.income.total);
    const exp = this.toNumberSafe(a.expenditure.total);
    return inc - exp;
  }

  private primeSection6SurplusFields(): void {
    const count = this.section6Applicants ? this.section6Applicants.length : 0;
    for (let i = 1; i <= count; i++) {
      const row = this.section6Applicants.find(r => r.applicantIndex === i);
      if (!row) continue;

      const surplus = this.getApplicantSurplus(row);
      const f = this.getFieldById(`IU-Section6-Applicant-${i}-Surplus`);
      if (f) {
        f.value = this.formatCurrencyStr(surplus);
      }
    }
  }

  // Live recompute totals for the modal table
  recomputeSection6TotalsInModal(): void {
    // Group fields by applicant & kind
    const byKey: Record<string, number> = {}; //sum

    const addValue = (
      kind: "Income" | "Expenditure",
      i: number,
      v: any,
      include: boolean,
    ) => {
      if (!include) return; // skip Others
      const num =
        typeof v === "string" ? +(v.replace(/,/g, "").trim() || "0") : +v || 0;
      const k = `${kind}-${i}`;
      byKey[k] = (byKey[k] || 0) + (isNaN(num) ? 0 : num);
    };

    // Sum all rows except "Others"
    this.documentInputFields.forEach((f) => {
      const m = f.id.match(
        /^IU-(Income|Expenditure)-Applicant-(\d+)-([A-Za-z-]+)$/,
      );
      if (!m) return;
      const kind = m[1] as "Income" | "Expenditure";
      const i = +m[2];
      const row = m[3];
      const isOthers = /^(Others)$/i.test(row);
      if (!this.isSection6TotalField(f.id)) {
        addValue(kind, i, f.value, !isOthers);
      }
    });

    // Write totals back into the matching total DocumentInputFields
    this.documentInputFields = this.documentInputFields.map((d) => {
      const m = d.id.match(/^IU-(Income|Expenditure)-Applicant-(\d+)-Total$/);
      if (!m) return d;
      const kind = m[1] as "Income" | "Expenditure";
      const i = +m[2];
      const k = `${kind}-${i}`;
      const total = byKey[k] || 0;
      return {
        ...d,
        value: this.formatCurrency(total), // show grouped number in modal
      };
    });

    this.cdr.detectChanges();
  }

  idFor(kind: "Income" | "Expenditure", i: number, key: string): string {
    const prefix = `IU-${kind}-Applicant-${i}-`;
    switch (kind) {
      case "Income":
        const incomeMap: Record<string, string> = {
          professionEmployment: "Profession-Employment",
          business: "Business",
          industry: "Industry",
          agriculture: "Agriculture",
          interest: "Interest",
          dividends: "Dividends",
          rentLease: "Rent-Lease",
          commissions: "Commissions",
          others: "Others",
          total: "Total",
        };
        return prefix + incomeMap[key];
      case "Expenditure":
        const expMap: Record<string, string> = {
          houseRent: "House-rent",
          householdExpenses: "Household-expenses",
          clothing: "Clothing",
          childrensEducation: "Childrens-education",
          personal: "Personal",
          traveling: "Traveling",
          medical: "Medical",
          loanRepayment: "Loan-repayment",
          creditCardPayment: "Credit-Card-Payment",
          ratesAndTaxes: "Rates-and-Taxes",
          electricityFuel: "Electricity-Fuel",
          insurance: "Insurance",
          dependents: "Dependents",
          others: "Others",
          total: "Total",
        };
        return prefix + expMap[key];
    }
  }

  hydrateSection6FromFields(): void {
    this.section6Applicants.forEach(
      ({ applicantIndex, income, expenditure }) => {
        // Income fields
        (Object.keys(income) as (keyof Utils.Section6Income)[]).forEach((k) => {
          const id = this.idFor("Income", applicantIndex, k);
          const f = this.documentInputFields.find((df) => df.id === id);
          if (f && typeof f.value === "string") income[k] = f.value;
        });
        // Expenditure fields
        (
          Object.keys(expenditure) as (keyof Utils.Section6Expenditure)[]
        ).forEach((k) => {
          const id = this.idFor("Expenditure", applicantIndex, k);
          const f = this.documentInputFields.find((df) => df.id === id);
          if (f && typeof f.value === "string") expenditure[k] = f.value;
        });
      },
    );
    // compute totals once
    this.recomputeSection6TotalsInModel();
    this.syncSection6ToFields();
  }

  syncSection6ToFields(): void {
    // Push model values to the matching documentInputFields
    this.section6Applicants.forEach(
      ({ applicantIndex, income, expenditure }) => {
        // Income
        (Object.keys(income) as (keyof Utils.Section6Income)[]).forEach((k) => {
          const id = this.idFor("Income", applicantIndex, k);
          const df = this.documentInputFields.find((d) => d.id === id);
          if (df) {
            df.value = income[k];
          }
        });
        // Expenditure
        (
          Object.keys(expenditure) as (keyof Utils.Section6Expenditure)[]
        ).forEach((k) => {
          const id = this.idFor("Expenditure", applicantIndex, k);
          const df = this.documentInputFields.find((d) => d.id === id);
          if (df) {
            df.value = expenditure[k];
          }
        });
      },
    );
  }

  readNumberPreferringV(doc: Document, iuId: string): number {
    const vId = iuId.replace(/^IU-/, 'V-');
    const read = (id: string) => {
      const el = doc.getElementById(id);
      const raw = el ? (el.innerHTML || el.textContent || '').replace(/,/g, '').trim() : '';
      const n = +raw;
      return isNaN(n) ? 0 : n;
    };
    const v = read(vId);
    if (v !== 0) return v;
    return read(iuId);
  }

  toNumberSafe(v: string): number {
    const n = +(v || "").toString().replace(/,/g, "").trim();
    return isNaN(n) ? 0 : n;
  }

  formatCurrencyStr(n: number | string): string {
    if (typeof n === "string") n = this.toNumberSafe(n);
    return this.currencyPipe.transform(n, "", "") || String(n);
  }

  recomputeSection6TotalsInModel(): void {
    this.section6Applicants = this.section6Applicants.map((row) => {
      // Income total
      const incSum =
        this.toNumberSafe(row.income.professionEmployment) +
        this.toNumberSafe(row.income.business) +
        this.toNumberSafe(row.income.industry) +
        this.toNumberSafe(row.income.agriculture) +
        this.toNumberSafe(row.income.interest) +
        this.toNumberSafe(row.income.dividends) +
        this.toNumberSafe(row.income.rentLease) +
        this.toNumberSafe(row.income.commissions);
      row.income.total =
        this.currencyPipe.transform(incSum, "", "") || String(incSum);

      // Expenditure total
      const expSum =
        this.toNumberSafe(row.expenditure.houseRent) +
        this.toNumberSafe(row.expenditure.householdExpenses) +
        this.toNumberSafe(row.expenditure.clothing) +
        this.toNumberSafe(row.expenditure.childrensEducation) +
        this.toNumberSafe(row.expenditure.personal) +
        this.toNumberSafe(row.expenditure.traveling) +
        this.toNumberSafe(row.expenditure.medical) +
        this.toNumberSafe(row.expenditure.loanRepayment) +
        this.toNumberSafe(row.expenditure.creditCardPayment) +
        this.toNumberSafe(row.expenditure.ratesAndTaxes) +
        this.toNumberSafe(row.expenditure.electricityFuel) +
        this.toNumberSafe(row.expenditure.insurance) +
        this.toNumberSafe(row.expenditure.dependents);
      row.expenditure.total =
        this.currencyPipe.transform(expSum, "", "") || String(expSum);

      return row;
    });

    // After recompute, push to fields
    this.syncSection6ToFields();
    this.cdr.detectChanges();
  }

  onSection6AmountBlur(
    appIndex: number,
    kind: "Income" | "Expenditure",
    key: string,
  ): void {
    const row = this.section6Applicants.find(
      (r) => r.applicantIndex === appIndex,
    );
    if (!row) return;

    const val =
      kind === "Income"
        ? (row.income as any)[key]
        : (row.expenditure as any)[key];

    // format currency if parsable
    const num = this.toNumberSafe(val);
    const formatted = this.currencyPipe.transform(num, "", "") || String(num);
    if (kind === "Income") (row.income as any)[key] = formatted;
    else (row.expenditure as any)[key] = formatted;

    // recompute totals
    this.recomputeSection6TotalsInModel();
  }

  idForSection7(
    appIdx: number,
    yearIdx: 1 | 2 | 3,
    key: keyof Utils.Section7YearData,
  ): string {
    if (key === "yearLabel") {
      return `IU-Section7-Year-${yearIdx}-Label-Applicant-${appIdx}`;
    }
    const map: Record<keyof Utils.Section7YearData, string> = {
      yearLabel: "Label",
      statutoryIncome: "Statutory-Income",
      assessableIncome: "Assessable-Income",
      taxableIncome: "Taxable-Income",
      taxPaid: "Tax-Paid",
    };
    return `IU-Section7-Year-${yearIdx}-Applicant-${appIdx}-${map[key]}`;
  }

  onSection7Blur(
    appIdx: number,
    yearIdx: 1 | 2 | 3,
    key: keyof Utils.Section7YearData,
  ): void {
    const a = this.section7Applicants.find((r) => r.applicantIndex === appIdx);
    if (!a) return;
    const year = yearIdx === 1 ? a.year1 : yearIdx === 2 ? a.year2 : a.year3;

    if (key === "yearLabel") {
    } else {
      year[key] = this.formatCurrencyStr(year[key]);
    }
    this.syncSection7ToFields();
  }

  setIUValue(id: string, value: string): void {
    const df = this.documentInputFields.find((d) => d.id === id);
    if (df) df.value = value;
  }

  syncSection7ToFields(): void {
    this.section7Applicants.forEach((a) => {
      // Year 1 label
      this.setIUValue(`IU-Section7-Year-1-Label`, a.year1.yearLabel);
      this.setIUValue(
        `IU-Section7-Year-1-Label-Applicant-${a.applicantIndex}`,
        a.year1.yearLabel,
      );

      // Year 1 metrics
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 1, "statutoryIncome"),
        a.year1.statutoryIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 1, "assessableIncome"),
        a.year1.assessableIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 1, "taxableIncome"),
        a.year1.taxableIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 1, "taxPaid"),
        a.year1.taxPaid,
      );

      // Year 2 label
      this.setIUValue(`IU-Section7-Year-2-Label`, a.year2.yearLabel);
      this.setIUValue(
        `IU-Section7-Year-2-Label-Applicant-${a.applicantIndex}`,
        a.year2.yearLabel,
      );
      // Year 2 metrics
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 2, "statutoryIncome"),
        a.year2.statutoryIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 2, "assessableIncome"),
        a.year2.assessableIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 2, "taxableIncome"),
        a.year2.taxableIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 2, "taxPaid"),
        a.year2.taxPaid,
      );

      // Year 3 label
      this.setIUValue(`IU-Section7-Year-3-Label`, a.year3.yearLabel);
      this.setIUValue(
        `IU-Section7-Year-3-Label-Applicant-${a.applicantIndex}`,
        a.year3.yearLabel,
      );
      // Year 3 metrics
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 3, "statutoryIncome"),
        a.year3.statutoryIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 3, "assessableIncome"),
        a.year3.assessableIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 3, "taxableIncome"),
        a.year3.taxableIncome,
      );
      this.setIUValue(
        this.idForSection7(a.applicantIndex, 3, "taxPaid"),
        a.year3.taxPaid,
      );
    });

    this.cdr.detectChanges();
  }

  isSection7Field(id: string): boolean {
    return /^IU-Section7-Year-(1|2|3)-(?:Label(?:-Applicant-[^-]+)?|Applicant-[^-]+)(?:-|$)/.test(
      id,
    );
  }

  writeSection7Metric(
    doc: Document,
    year: 1 | 2 | 3,
    appIdx: number,
    metric:
      | "Statutory-Income"
      | "Assessable-Income"
      | "Taxable-Income"
      | "Tax-Paid",
    value: string,
  ): void {
    let exactId =
      "IU-Section7-Year-" + year + "-Applicant-" + appIdx + "-" + metric;
    let el = doc.getElementById(exactId);
    if (el) {
      el.innerHTML = value;
    }

    let selector =
      '[id^="IU-Section7-Year-' + year + '-Applicant-"][id$="-' + metric + '"]';
    let nodeList = doc.querySelectorAll(selector) as NodeListOf<HTMLElement>;
    for (let i = 0; i < nodeList.length; i++) {
      nodeList[i].innerHTML = value;
    }
  }

  writeSection7YearLabel(
    doc: Document,
    year: 1 | 2 | 3,
    label: string,
    appIdx: number,
  ): void {
    let gl = doc.getElementById("IU-Section7-Year-" + year + "-Label");
    if (gl) gl.innerHTML = label;

    let sel = '[id^="IU-Section7-Year-' + year + '-Label-Applicant-"]';
    let list = doc.querySelectorAll(sel) as NodeListOf<HTMLElement>;
    for (let i = 0; i < list.length; i++) list[i].innerHTML = label;

    let exact = doc.getElementById(
      "IU-Section7-Year-" + year + "-Label-Applicant-" + appIdx,
    );
    if (exact) exact.innerHTML = label;
  }

  addSampathRow(): void {
    if (!this.canAddSampathRow()) return;
    const f = this.sampathForm;
    const ok =
      (f.bankAndBranch || "").trim().length > 0 ||
      (f.accountNo || "").trim().length > 0;
    if (!ok) return;

    const v: any = f.presentBalance;
    const n =
      typeof v === "string" ? +(v.replace(/,/g, "").trim() || "0") : v || 0;

    this.sampathAccounts.push({
      bankAndBranch: (f.bankAndBranch || "").trim(),
      accountType: (f.accountType || "").trim(),
      accountNo: (f.accountNo || "").trim(),
      dateOpened: (f.dateOpened || "").trim(),
      presentBalance: isNaN(n) ? 0 : n,
      accountHolderName: (f.accountHolderName || "").trim(),
    });

    // reset form
    this.sampathForm = {
      bankAndBranch: "",
      accountType: "",
      accountNo: "",
      dateOpened: "",
      presentBalance: "",
      accountHolderName: "",
    };
    this.cdr.detectChanges();
  }

  removeSampathRow(index: number): void {
    if (index < 0 || index >= this.sampathAccounts.length) return;
    this.sampathAccounts.splice(index, 1);
    this.cdr.detectChanges();
  }

  constructSection8Rows(
    rows: Array<Utils.SampathBankAccountRow | Utils.OtherBankAccountRow>,
  ): string {
    return rows
      .map(
        (r) => `
    <tr>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.bankAndBranch)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.accountType)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.accountNo)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.dateOpened)}</td>
      <td style="height:20px; white-space: pre-wrap; text-align:right;">${this.escapeHtml(this.formatCurrency(r.presentBalance))}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.accountHolderName)}</td>
    </tr>
  `,
      )
      .join("");
  }

  addLiabilityRow(): void {
    if (!this.canAddLiabilityRow()) return;
    const f = this.liabilitiesForm;

    const ok =
      (f.creditorName || "").trim().length > 0 ||
      (f.referenceNo || "").trim().length > 0;
    if (!ok) return;

    const toNum = (v: any) => {
      if (typeof v === "string") {
        const n = +(v.replace(/,/g, "").trim() || "0");
        return isNaN(n) ? 0 : n;
      }
      return v || 0;
    };

    this.existingLiabilities.push({
      creditorName: (f.creditorName || "").trim(),
      referenceNo: (f.referenceNo || "").trim(),
      repaymentPeriod: (f.repaymentPeriod || "").trim(),
      security: (f.security || "").trim(),
      amountBorrowed: toNum(f.amountBorrowed),
      balancePayableNow: toNum(f.balancePayableNow),
      balancePeriodToComplete: (f.balancePeriodToComplete || "").trim(),
      borrowedBy: (f.borrowedBy || "").trim(),
      purpose: (f.purpose || "").trim(),
    });

    // reset form
    this.liabilitiesForm = {
      creditorName: "",
      referenceNo: "",
      repaymentPeriod: "",
      security: "",
      amountBorrowed: "",
      balancePayableNow: "",
      balancePeriodToComplete: "",
      borrowedBy: "",
      purpose: "",
    };

    this.cdr.detectChanges();
  }

  removeLiabilityRow(index: number): void {
    if (index < 0 || index >= this.existingLiabilities.length) return;
    this.existingLiabilities.splice(index, 1);
    this.cdr.detectChanges();
  }

  constructExistingLiabilitiesRows(rows: Utils.ExistingLiabilityRow[]): string {
    return rows
      .map(
        (r) => `
    <tr>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.creditorName)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.referenceNo)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.repaymentPeriod)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.security)}</td>
      <td style="height:20px; white-space: pre-wrap; text-align:right;">${this.escapeHtml(this.formatCurrency(r.amountBorrowed))}</td>
      <td style="height:20px; white-space: pre-wrap; text-align:right;">${this.escapeHtml(this.formatCurrency(r.balancePayableNow))}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.balancePeriodToComplete)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.borrowedBy)}</td>
      <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.purpose)}</td>
    </tr>
  `,
      )
      .join("");
  }

  private computeExistingLiabilitiesTotals(): { amountBorrowed: number; balancePayableNow: number } {
    const rows = this.existingLiabilities || [];
    let amountBorrowed = 0;
    let balancePayableNow = 0;
    rows.forEach(r => {
      amountBorrowed += this.toNumberSafe(String(r.amountBorrowed));
      balancePayableNow += this.toNumberSafe(String(r.balancePayableNow));
    });
    return { amountBorrowed, balancePayableNow };
  }

  get existingLiabilitiesSum(): { amountBorrowed: number; balancePayableNow: number } {
    return this.computeExistingLiabilitiesTotals();
  }

  constructProposedFacilitiesRows(): string {
    return this.proposedFacilities
      .map(r => `
      <tr>
        <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.typeOfFacilityRequired)}</td>
        <td style="height:20px; white-space: pre-wrap; ">${this.escapeHtml(this.formatCurrency(r.amountOfFaclity))}</td>
        <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.purposeOfFacility)}</td>
        <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.interestRate)}</td>   <!-- Interest Rate -->
        <td style="height:20px; white-space: pre-wrap;">${this.escapeHtml(r.repaymentOption)}</td>
      </tr>
    `)
      .join('');
  }

  syncProposedFacilitiesFromSection4(): void {
    const existing = this.proposedFacilities || [];
    this.proposedFacilities = (this.facilityRows || []).map((r, idx) => {
      const prev = existing[idx];
      return {
        typeOfFacilityRequired: (r.typeOfFacilityRequired || '').trim(),
        amountOfFaclity: r.amountOfFaclity,
        purposeOfFacility: (r.purposeOfFacility || '').trim(),
        // Preserve interest rate if user already typed in Proposed table
        interestRate: prev ? prev.interestRate : '',
        // Repayment comes from Section 4 live
        repaymentOption: (r.repaymentOption || '').trim(),
      };
    });
  }

  validateFacilityNumericInline(row: any, key: 'applicantsContribution'): void {
    const n = this.toNumberSafe(row[key]);
    if (!isNaN(n)) {
      row[key] = this.formatCurrencyStr(n);
    }
  }

  isOfficeUseField(id: string): boolean {
    return Object.values(this.OFFICE_USE_IDS).includes(id);
  }

  isSecurityToBeOffered(id: string): boolean {
    return Object.values(this.SECURITY_TO_OFFERED_IDS).includes(id);
  }

  getField(id: string): Utils.DocumentInputField | null {
    let list = this.documentInputFields || [];
    for (let i = 0; i < list.length; i++) {
      if (list[i] && list[i].id === id) return list[i];
    }
    return null;
  }

  getVal(id: string): string {
    let f = this.getField(id);
    return f ? this.trimStr(f.value) : "";
  }

  getArr(id: string): string[] {
    let f = this.getField(id);
    if (!f) return [];
    let v = f.value;
    if (Array.isArray(v)) return v as string[];
    let s = this.trimStr(v);
    if (!s) return [];
    return s
      .split(",")
      .map(function (x) {
        return x.trim();
      })
      .filter(function (x) {
        return !!x;
      });
  }

  trimStr(v: any): string {
    return v == null ? "" : String(v).trim();
  }

  toNumber(v: number | string): number {
    if (typeof v === "number") return isNaN(v) ? 0 : v;
    const n = +String(v || "")
      .replace(/,/g, "")
      .trim();
    return isNaN(n) ? 0 : n;
  }

  toNum(v: any): number {
    if (v == null) return 0;
    if (typeof v === "number") return isNaN(v) ? 0 : v;
    let s = String(v).replace(/,/g, "").trim();
    let n = +s;
    return isNaN(n) ? 0 : n;
  }

  toISODate(v: any): string {
    if (v == null) return "";
    let s = String(v).trim();
    if (!s) return "";
    let d = (window as any).moment
      ? (window as any).moment(
        s,
        [
          "YYYY-MM-DD",
          "DD-MM-YYYY",
          "DD/MM/YYYY",
          "DD MMM YYYY",
          "Do MMMM YYYY",
          "MMMM DD, YYYY",
        ],
        true,
      )
      : null;
    return d && d.isValid ? (d.isValid() ? d.format("YYYY-MM-DD") : s) : s;
  }

  buildApplicantsPayload(): any[] {
    const parser = new DOMParser();
    const doc = parser.parseFromString(
      this.selectedDocumentContent,
      "text/html",
    );
    const count = this.getApplicantsCountFromTemplate(doc);

    const out: any[] = [];
    for (let i = 1; i <= count; i++) {
      const row = (this.section6Applicants || []).find(
        (r) => r && r.applicantIndex === i,
      );

      // Fallbacks if row not present
      const income =
        row && row.income
          ? row.income
          : {
            professionEmployment: "",
            business: "",
            industry: "",
            agriculture: "",
            interest: "",
            dividends: "",
            rentLease: "",
            commissions: "",
            others: "",
            total: "",
          };
      const exp =
        row && row.expenditure
          ? row.expenditure
          : {
            houseRent: "",
            householdExpenses: "",
            clothing: "",
            childrensEducation: "",
            personal: "",
            traveling: "",
            medical: "",
            loanRepayment: "",
            creditCardPayment: "",
            ratesAndTaxes: "",
            electricityFuel: "",
            insurance: "",
            dependents: "",
            others: "",
            total: "",
          };

      // --- Section 1 & 2 basic info from DOM by ID ---
      const base = {
        fullName: this.getVal("IS-Applicant-" + i + "-Name"),
        dob: this.toISODate(this.getVal("IS-Applicant-" + i + "-DOB")),
        nic: this.getVal("IS-Applicant-" + i + "-NIC"),
        permanentAddress1: this.getVal(
          "IS-Applicant-" + i + "-Permanent-Address-Line1",
        ),
        permanentAddress2: this.getVal(
          "IS-Permanent-Address-Line2-Applicant-" + i,
        ),
        currentAddressType: this.getVal(
          "IS-Current-Applicant-" + i + "-Address",
        ),
        yearsAtAddress: this.getVal(
          "IS-Applicant-" + i + "-Years-Of-Stay-In-Current-Address",
        ),
        currentAddressOther: this.getVal(
          "IS-Applicant-" + i + "-Permanent-Address-Other",
        ),
        communicationAddress: this.getVal(
          "IS-Applicant-" + i + "-Communication-Address",
        ),
        residencePhone: this.getVal("IS-Applicant-" + i + "-Residence-Phone"),
        mobilePhone: this.getVal("IS-Applicant-" + i + "-Mobile"),
        officePhone: this.getVal("IS-Applicant-" + i + "-Office-Phone"),
        email: this.getVal("IS-Applicant-" + i + "-Email"),
        nationality: this.getArr("IS-Nationality-Applicant-" + i),
        isContactPerson: this.getVal(
          "IS-Applicant-" + i + "-Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka",
        ),
        contactPersonName: this.getVal(
          "IS-Applicant-" + i + "-Contact-Person-Name",
        ),
        contactPersonMobile: this.getVal(
          "IS-Applicant-" + i + "-Contact-Person-Mobile",
        ),
        contactPersonRelationship: this.getVal(
          "IS-Applicant-" + i + "-Contact-Person-Relationship",
        ),
        isPep: this.getVal("IS-Applicant-" + i + "-Is-Pep"),
        pepDescription: this.getVal("IS-Applicant-" + i + "-Pep-Description"),
        highestAcademicQualification: this.getVal(
          "IS-Applicant-" + i + "-Highest-Academic-Qualification",
        ),
        professionalQualification: this.getVal(
          "IS-Applicant-" + i + "-Professional-Qualification",
        ),
        civilStatus: this.getVal("IS-Applicant-" + i + "-Civil-Status"),
        civilStatusDescription: this.getVal(
          "IS-Applicant-" + i + "-Civil-Status-Description",
        ),
        noOfChildren: this.getVal("IS-Applicant-" + i + "-No-Of-Children-and-Other-Dependents"),

        employmentDetails: [
          {
            nameOfEmployer: this.getVal(
              "IU-Employment-Applicant-" + i + "-Name-of-Employer",
            ),
            employmentType: this.getVal(
              "IU-Employment-Applicant-" + i + "-Employment-Type",
            ),
            noOfYearsWithPresentEmployer: this.getVal(
              "IU-Employment-Applicant-" + i + "-Years-With-Present-Employer",
            ),
            avgSalaryGrowth: this.getVal(
              "IU-Employment-Applicant-" + i + "-Avg-Salary-Growth",
            ),
            avgSalaryGrowthInPercentage: this.getVal(
              "IU-Employment-Applicant-" + i + "-Avg-Salary-Growth-in-Percentage",
            ),
            addressOfTheEmployer: this.getVal(
              "IU-Employment-Applicant-" + i + "-Address-of-Employer",
            ),
            telephoneNo: this.getVal(
              "IU-Employment-Applicant-" + i + "-Telephone-No",
            ),
            natureOfBusiness: this.getVal(
              "IU-Employment-Applicant-" + i + "-Nature-of-Business",
            ),
            designation: this.getVal(
              "IU-Employment-Applicant-" + i + "-Designation",
            ),
            previousEmployerName: this.getVal(
              "IU-Employment-Applicant-" + i + "-Previous-Employer-Name",
            ),
            previousEmploymentPeriod: this.getVal(
              "IU-Employment-Applicant-" + i + "-Previous-Employment-Period",
            ),
            previousDesignation: this.getVal(
              "IU-Employment-Applicant-" + i + "-Previous-Designation",
            ),
          },
        ],

        // ---------- Section 6 from model ----------
        income: [
          {
            professionEmployment: this.toNum(income.professionEmployment),
            business: this.toNum(income.business),
            industry: this.toNum(income.industry),
            agriculture: this.toNum(income.agriculture),
            interest: this.toNum(income.interest),
            dividends: this.toNum(income.dividends),
            rentLease: this.toNum(income.rentLease),
            commissions: this.toNum(income.commissions),
            others: this.toNum(income.others),
          },
        ],
        expenditures: [
          {
            houseRent: this.toNum(exp.houseRent),
            householdExpenses: this.toNum(exp.householdExpenses),
            clothing: this.toNum(exp.clothing),
            childrensEducation: this.toNum(exp.childrensEducation),
            personal: this.toNum(exp.personal),
            traveling: this.toNum(exp.traveling),
            medical: this.toNum(exp.medical),
            loanRepayment: this.toNum(exp.loanRepayment),
            creditCardPayment: this.toNum(exp.creditCardPayment),
            ratesAndTaxes: this.toNum(exp.ratesAndTaxes),
            electricityFuel: this.toNum(exp.electricityFuel),
            insurance: this.toNum(exp.insurance),
            dependents: this.toNum(exp.dependents),
            others: this.toNum(exp.others),
          },
        ],

        // ---------- Section 7 ----------
        incomeTaxLast3Years: function (
          aRow: Utils.Section7ApplicantRow | undefined,
        ) {
          function yr(y: 1 | 2 | 3) {
            const yy = y === 1 ? aRow.year1 : y === 2 ? aRow.year2 : aRow.year3;
            return {
              year: yy ? ("" + (yy.yearLabel || "")).trim() : "",
              statutoryIncome: yy ? this.toNum(yy.statutoryIncome) : 0,
              assessableIncome: yy ? this.toNum(yy.assessableIncome) : 0,
              taxableIncome: yy ? this.toNum(yy.taxableIncome) : 0,
              taxPaid: yy ? this.toNum(yy.taxPaid) : 0,
            };
          }
          return [yr.call(this, 1), yr.call(this, 2), yr.call(this, 3)];
        }.call(
          this,
          (this.section7Applicants || []).find(
            (a) => a && a.applicantIndex === i,
          ),
        ),
      };

      out.push(base);
    }
    return out;
  }

  buildAssetsPayload(): any[] {
    let landBuilding = (this.assetsLB || []).map((r) => ({
      assertDescription: this.trimStr(r && r.description),
      assertFloorAreaExtent: this.trimStr(r && r.floorAreaExtent),
      assertOwnedBy: this.trimStr(r && r.ownedBy),
      assertMarketValue: this.toNum(r && r.marketValue),
      assertMortgages: this.trimStr(r && r.mortgages),
    }));
    let motorVehicle = (this.motorVehicle || []).map((r) => ({
      mvTypeOfVehicleAndRegisterNO: this.trimStr(
        r && r.typeOfVehicleAndRegisterNO,
      ),
      mvPresentOwner: this.trimStr(r && r.presentOwner),
      mvMarketValue: this.toNum(r && r.marketValue),
      mvMortgages: this.trimStr(r && r.mortgages),
    }));
    let sharesTBillsDeposit = (this.sharesTbd || []).map((r) => ({
      stdType: this.trimStr(r && r.type),
      stdNameOfCompany: this.trimStr(r && r.nameOfCompany),
      stdValueOfBills: this.toNum(r && r.valueOfBills),
      stdAssigments: this.trimStr(r && r.assigments),
    }));
    let lifeInsurancePolicies = (this.lifeIns || []).map((r) => ({
      lipNameOfPolicyHolder: this.trimStr(r && r.nameOfPolicyHolder),
      lipNameOfCompany: this.trimStr(r && r.nameOfCompany),
      lipPolicyNo: this.trimStr(r && r.policyNo),
      lipFaceValue: this.toNum(r && r.faceValue),
      lipPremiumFrequency: this.trimStr(r && r.premiumFrequency),
      lipAssigments: this.trimStr(r && r.assigments),
    }));

    return [
      {
        landBuilding: landBuilding,
        motorVehicle: motorVehicle,
        sharesTBillsDeposit: sharesTBillsDeposit,
        lifeInsurancePolicies: lifeInsurancePolicies,
      },
    ];
  }

  buildFacilityPayload(): any[] {
    let toText = this.getVal("IU-Proposed-Facilities-To");
    let rows = this.facilityRows || [];
    let props = this.proposedFacilities || [];

    let out: any[] = [];
    for (let i = 0; i < rows.length; i++) {
      let r = rows[i];
      out.push({
        typeOfFacilityRequired: this.trimStr(r && r.typeOfFacilityRequired),
        purposeOfFacility: this.trimStr(r && r.purposeOfFacility),
        amountOfFaclity: this.toNum(r && r.amountOfFaclity),
        numberOfYearsRequired: this.trimStr(r && r.numberOfYearsRequired),
        repaymentOption: this.trimStr(r && r.repaymentOption),
        preferedLoanDedDate: this.trimStr(r && r.preferedLoanDedDate),
        applicantsContribution: this.trimStr(r && r.applicantsContribution),
        advancePayement: this.trimStr(r && r.advancePayement),
        facilityTo: toText,
        interestRate: this.trimStr(props[i] && props[i].interestRate),
      });
    }
    return out;
  }

  buildSecurityPayload(): any[] {
    // Read values directly from the IU- fields (already hydrated into documentInputFields)
    const desc = this.getVal("IU-Security-To-Be-Offered");
    const loc = this.getVal("IU-Security-to-be-offered-location");
    const planNo = this.getVal("IU-Security-to-be-offered-plan-no");
    const lotNo = this.getVal("IU-Security-to-be-offered-lot-no");
    const extent = this.getVal("IU-Security-to-be-offered-extent");
    const owner = this.getVal("IU-Security-to-be-offered-ownership");
    const land = this.getVal("IU-Security-to-be-offered-name-of-land");
    const area = this.getVal("IU-Security-to-be-offered-floor-area");
    const pmvStr = this.getVal("IU-Security-to-be-offered-present-market-value");

    return [{
      stboSecurityToBeOfferedField: desc,
      stboLocation: loc,
      stboPlanNo: planNo,
      stboLotNo: lotNo,
      stboExtent: extent,
      stboOwnership: owner,
      stboNameOfLand: land,
      stboFloorAreaSqft: this.toNum(area),
      stboPresentMarketValue: this.toNum(pmvStr)
    }];
  }

  buildInsuranceConsentPayload(): any[] {
    let agreeDisagree = this.agreeDisagreeField
      ? this.trimStr(this.agreeDisagreeField.value)
      : this.getVal(this.INS_CONSENT_IDS.agreeDisagree);
    let annex = this.annexuresField
      ? Array.isArray(this.annexuresField.value)
        ? this.annexuresField.value
        : this.getArr(this.INS_CONSENT_IDS.annexures)
      : this.getArr(this.INS_CONSENT_IDS.annexures);
    let accNo = this.accountNoField
      ? this.trimStr(this.accountNoField.value)
      : this.getVal(this.INS_CONSENT_IDS.accountNo);
    let loanReq = this.loanRequiredField
      ? this.trimStr(this.loanRequiredField.value)
      : this.getVal(this.INS_CONSENT_IDS.loanRequired);
    let insAmt = this.insuranceAmountField
      ? this.toNum(this.insuranceAmountField.value)
      : this.toNum(this.getVal(this.INS_CONSENT_IDS.amount));
    let insComp = this.insuranceCompaniesField
      ? this.trimStr(this.insuranceCompaniesField.value)
      : this.getVal(this.INS_CONSENT_IDS.companies);

    return [
      {
        isAgreeDisagree: agreeDisagree,
        selectedAnnexures: annex,
        icAccountNo: accNo,
        isLoanRequired: loanReq,
        insuranceAmount: insAmt,
        insuranceCompanies: insComp,
      },
    ];
  }

  buildBankAccountsPayload(): any[] {
    let sampath = (this.sampathAccounts || []).map((r) => ({
      isSampathBankAccount: Constants.yesNoConst.Y,
      bankAndBranch: this.trimStr(r && r.bankAndBranch),
      accountType: this.trimStr(r && r.accountType),
      accountNo: this.trimStr(r && r.accountNo),
      dateOpened: this.toISODate(r && r.dateOpened),
      presentBalance: this.toNum(r && r.presentBalance),
      accountHolderName: this.trimStr(r && r.accountHolderName),
    }));
    let other = (this.otherAccounts || []).map((r) => ({
      isSampathBankAccount: Constants.yesNoConst.N,
      bankAndBranch: this.trimStr(r && r.bankAndBranch),
      accountType: this.trimStr(r && r.accountType),
      accountNo: this.trimStr(r && r.accountNo),
      dateOpened: this.toISODate(r && r.dateOpened),
      presentBalance: this.toNum(r && r.presentBalance),
      accountHolderName: this.trimStr(r && r.accountHolderName),
    }));
    return sampath.concat(other);
  }

  buildExistingLiabilitiesPayload(): any[] {
    let rows = this.existingLiabilities || [];
    return rows.map((r) => ({
      creditorName: this.trimStr(r && r.creditorName),
      referenceNo: this.trimStr(r && r.referenceNo),
      repaymentPeriod: this.trimStr(r && r.repaymentPeriod),
      security: this.trimStr(r && r.security),
      amountBorrowed: this.toNum(r && r.amountBorrowed),
      balancePayableNow: this.toNum(r && r.balancePayableNow),
      balancePeriodToComplete: this.trimStr(r && r.balancePeriodToComplete),
      borrowedBy: this.trimStr(r && r.borrowedBy),
      purpose: this.trimStr(r && r.purpose),
    }));
  }

  buildDeclarationPayload(): any[] {
    let accountNo = this.section10AccountNoField
      ? this.trimStr(this.section10AccountNoField.value)
      : this.getVal("IU-Section10-Account-No");
    let customerName = this.section10CustomerNameField
      ? this.trimStr(this.section10CustomerNameField.value)
      : this.getVal("IU-Section10-Customer-Name");
    let judgement = this.section10JudgmentField
      ? this.trimStr(this.section10JudgmentField.value)
      : this.getVal("IU-Section10-ANY-JUDGEMENTS");
    let isPep = this.section10IsPepField
      ? this.trimStr(this.section10IsPepField.value)
      : this.getVal(this.SECTION10_IDS.isPep);
    let pepDescription = this.section10PepDescriptionField
      ? this.trimStr(this.section10PepDescriptionField.value)
      : this.getVal(this.SECTION10_IDS.pepDescription);

    return [
      {
        accountNo: accountNo,
        customerName: customerName,
        judgement: judgement,
        isPep,
        pepDescription,
      },
    ];
  }

  buildOtherDetailPayload(): any[] {
    return [
      {
        commentsOnCRIB: this.getVal("IU-Irregular-CRIB-Records-Comments"),
        proposedAmendment: this.getVal("IU-Proposed-Amendment"),
        isFamilyMemberOfSB: this.getVal(
          "IU-Is-Immediate-family-member-Bank-Team-member",
        ),
        isRelatedParty: this.getVal("IU-Is-Applicant-related-party"),

        commentsByBM: this.getVal(
          "IU-Comments-and-recommendation-by-Branch-Manager",
        ),
        commentsByBMName: this.getVal("IU-Branch-Manager-Name"),
        commentsByBMDate: this.toISODate(
          this.getVal("IU-Branch-Manager-Section-Date"),
        ),

        commentsBySRM: this.getVal(
          "IU-Comments-and-recommendation-by-RM-or-SRM",
        ),
        commentsBySRMName: this.getVal("IU-SRM-or-RM-Name"),
        commentsBySRMDate: this.toISODate(
          this.getVal("IU-SRM-or-RM-Section-Date"),
        ),
      },
    ];
  }

  buildPayload(status: string): any {
    let createdBy =
      this.applicationService &&
        (this.applicationService as any).getLoggedInUserName
        ? (this.applicationService as any).getLoggedInUserName()
        : "";
    let fp = this.facilityPaper || {};
    let branchCode = fp.branchCode || "";
    let branchName = fp.branchName || "";

    const accountNoText = this.getVal("IS-Account-No");
    const branchText = this.getVal("IS-Branch");

    // If you want to also override branchName with what user typed, do it here:
    if (branchText) branchName = branchText;

    // Use a field or today date
    let appDate = this.getVal("IS-Letter-Date-Format-1");
    if (!appDate) {
      let today = new Date();
      // Keep ISO for backend
      let yyyy = today.getFullYear();
      let mm = ("0" + (today.getMonth() + 1)).slice(-2);
      let dd = ("0" + today.getDate()).slice(-2);
      appDate = yyyy + "-" + mm + "-" + dd;
    }

    return {
      createdBy: createdBy,
      leadrefNo: fp.leadrefNo || "",
      applicationType: "INDIVIDUAL",
      branchCode: branchCode,
      branchName: branchName,
      accountNo: accountNoText,
      branch: branchText,
      applicationDate: appDate,
      status: status,

      applicantDetail: this.buildApplicantsPayload(),
      applicantAsset: this.buildAssetsPayload(),
      facilityDetail: this.buildFacilityPayload(),
      securityToBeOffered: this.buildSecurityPayload(),
      insuranceConsent: this.buildInsuranceConsentPayload(),
      bankAccounts: this.buildBankAccountsPayload(),
      existingLiabilities: this.buildExistingLiabilitiesPayload(),
      declarationAndConsent: this.buildDeclarationPayload(),
      otherDetail: this.buildOtherDetailPayload(),
    };
  }

  applyPronouns(html: string, count: number): string {
    if (!html) return html;

    const IWe = count > 1 ? "We" : "I";
    const MeUs = count > 1 ? "us" : "me";
    const MyOur = count > 1 ? "our" : "my";

    const doc = new DOMParser().parseFromString(html, "text/html");
    const set = (id: string, v: string) => {
      const el = doc.getElementById(id);
      if (el) el.innerHTML = v;
    };

    // Insurance Consent
    set("IS-DPL-Insurance-Consent-MyOur", MyOur);
    ["IS-DPL-Insurance-Consent-IWe-2", "IS-DPL-Insurance-Consent-IWe-3"].forEach(id => set(id, IWe));
    ["IS-DPL-Insurance-Consent-MeUs-2", "IS-DPL-Insurance-Consent-MeUs-3"].forEach(id => set(id, MeUs));

    // Section 10
    [
      "IS-DPL-Section10-IWe",
      "IS-DPL-Section10-IWe-2", "IS-DPL-Section10-IWe-3", "IS-DPL-Section10-IWe-4", "IS-DPL-Section10-IWe-5",
      "IS-DPL-Section10-IWe-6", "IS-DPL-Section10-IWe-7", "IS-DPL-Section10-IWe-8", "IS-DPL-Section10-IWe-9",
      "IS-DPL-Section10-IWe-10", "IS-DPL-Section10-IWe-11", "IS-DPL-Section10-IWe-12"
    ].forEach(id => set(id, IWe));

    ["IS-DPL-Section10-MeUs", "IS-DPL-Section10-MeUs-1"].forEach(id => set(id, MeUs));

    [
      "IS-DPL-Section10-MyOur",
      "IS-DPL-Section10-MyOur-2", "IS-DPL-Section10-MyOur-3", "IS-DPL-Section10-MyOur-4",
      "IS-DPL-Section10-MyOur-5", "IS-DPL-Section10-MyOur-6", "IS-DPL-Section10-MyOur-7"
    ].forEach(id => set(id, MyOur));

    return doc.documentElement.outerHTML;
  }

  private normalizeYesNo(v: any): 'Y' | 'N' | '' {
    const s = (v || '').toString().trim().toLowerCase();
    if (!s) return '';
    if (s === 'y' || s === 'yes' || s === 'true') return 'Y';
    if (s === 'n' || s === 'no' || s === 'false') return 'N';
    return '';
  }

  // detect dependent field types from IDs 

  private matchAddressOtherField(id: string): number | null {
    const m = id.match(/^IS-Applicant-(\d+)-Permanent-Address-Other$/i);
    return m ? parseInt(m[1], 10) : null;
  }

  private matchPepDescriptionField(id: string): number | null {
    const m = id.match(/^IS-Applicant-(\d+)-Pep-Description$/i);
    return m ? parseInt(m[1], 10) : null;
  }

  private matchCivilStatusDescField(id: string): number | null {
    const m = id.match(/^IS-Applicant-(\d+)-Civil-Status-Description$/i);
    return m ? parseInt(m[1], 10) : null;
  }

  private matchOverseasControllerField(id: string): number | null {
    const m = id.match(/^IS-Applicant-(\d+)-Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka$/i);
    return m ? parseInt(m[1], 10) : null;
  }

  // Matches any dependent input (Name/Mobile/Relationship); returns applicant index if matches
  private matchOverseasDependentField(id: string): number | null {
    const m = id.match(/^IS-Applicant-(\d+)-Contact-Person-(Name|Mobile|Relationship)$/i);
    return m ? parseInt(m[1], 10) : null;
  }

  // Build controller id from applicant index
  private overseasControllerId(i: number): string {
    return `IS-Applicant-${i}-Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka`;
  }

  //  controller field IDs 

  private addressTypeId(i: number): string {
    return `IS-Current-Applicant-${i}-Address`;
  }

  private pepSelectId(i: number): string {
    return `IS-Applicant-${i}-Is-PEP`;
  }

  private civilStatusId(i: number): string {
    return `IS-Applicant-${i}-Civil-Status`;
  }

  /**
   * One visibility function for all conditional dependent fields.
   * Use this in the modal template *ngIf.
   */
  isConditionalFieldVisible(fieldId: string): boolean {
    // 1) Address "Other" field
    const addrIdx = this.matchAddressOtherField(fieldId);
    if (addrIdx) {
      const sel = this.documentInputFields.find(f => f.id === this.addressTypeId(addrIdx));
      const value = (sel.value || '').toString().trim();
      return value === 'Other';
    }

    // 2) PEP description field (show only if Is-Pep == Yes/Y)
    const pepIdx = this.matchPepDescriptionField(fieldId);
    if (pepIdx) {
      const sel = this.documentInputFields.find(f => f.id === this.pepSelectId(pepIdx));
      const yn = this.normalizeYesNo(sel.value);
      return yn === 'Y';
    }

    // 3) Civil status description (show only if Civil Status == Other)
    const civilIdx = this.matchCivilStatusDescField(fieldId);
    if (civilIdx) {
      const sel = this.documentInputFields.find(f => f.id === this.civilStatusId(civilIdx));
      const value = (sel.value || '').toString().trim().toLowerCase();
      return value === 'other';
    }

    // 4) NEW — Overseas contact dependents
    const ovIdx = this.matchOverseasDependentField(fieldId);
    if (ovIdx) {
      const ctrlId = this.overseasControllerId(ovIdx);
      const ctrl = this.documentInputFields.find(f => f.id === ctrlId);
      const yn = this.normalizeYesNo(ctrl && ctrl.value);
      // Show dependents only if controller is Yes
      return yn === 'Y';
    }

    // default: visible
    return true;
  }

  onSelectChange(feild: any, $event: any) {
    feild.value = $event;

    // Address-type controller
    const addrMatch = feild.id.match(/^IS-Current-Applicant-(\d+)-Address$/i);
    if (addrMatch) {
      const i = parseInt(addrMatch[1], 10);
      const otherId = `IS-Applicant-${i}-Permanent-Address-Other`;
      const otherField = this.documentInputFields.find(f => f.id === otherId);
      if (otherField && (feild.value || '').toString().trim() !== 'Other') {
        otherField.value = '';
      }
      this.cdr.detectChanges();
      return;
    }

    // PEP controller
    const pepMatch = feild.id.match(/^IS-Applicant-(\d+)-Is-Pep$/i);
    if (pepMatch) {
      const i = parseInt(pepMatch[1], 10);
      const descId = `IS-Applicant-${i}-Pep-Description`;
      const descField = this.documentInputFields.find(f => f.id === descId);
      const yn = this.normalizeYesNo(feild.value);
      if (descField && yn !== 'Y') {
        descField.value = '';
      }
      this.cdr.detectChanges();
      return;
    }

    // Civil status controller
    const civilMatch = feild.id.match(/^IS-Applicant-(\d+)-Civil-Status$/i);
    if (civilMatch) {
      const i = parseInt(civilMatch[1], 10);
      const descId = `IS-Applicant-${i}-Civil-Status-Description`;
      const descField = this.documentInputFields.find(f => f.id === descId);
      const v = (feild.value || '').toString().trim().toLowerCase();
      if (descField && v !== 'other') {
        descField.value = '';
      }
      this.cdr.detectChanges();
      return;
    }

    // ---  Overseas controller ---
    const ovMatch = feild.id.match(/^IS-Applicant-(\d+)-Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka$/i);
    if (ovMatch) {
      const i = parseInt(ovMatch[1], 10);
      const yn = this.normalizeYesNo(feild.value);

      // When No, clear the dependents so they don't reappear later
      if (yn !== 'Y') {
        const depIds = [
          `IS-Applicant-${i}-Contact-Person-Name`,
          `IS-Applicant-${i}-Contact-Person-Mobile`,
          `IS-Applicant-${i}-Contact-Person-Relationship`,
        ];
        depIds.forEach(id => {
          const dep = this.documentInputFields.find(f => f.id === id);
          if (dep) dep.value = '';
        });
      }

      this.cdr.detectChanges();
      return;
    }


    // default
    this.cdr.detectChanges();
  }

  private toggleOverseasContactBlocks(doc: Document): void {
    const count = this.getApplicantsCountFromTemplate(doc); // you already have this helper
    for (let i = 1; i <= count; i++) {
      // The Yes/No element (it might be IS- or V- after you write; handle both)
      const yesNoEl =
        doc.getElementById(`V-Applicant-${i}-Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka`) ||
        doc.getElementById(`IS-Applicant-${i}-Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka`) ||
        doc.getElementById(`IU-Applicant-${i}-Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka`);

      const val = (yesNoEl && (yesNoEl.textContent || '').trim()) || '';
      const container = doc.getElementById(`Applicant-${i}-Overseas-Contact-Container`);
      if (container) {
        // Show block only when user answered "Yes"
        container.style.display = /^yes$/i.test(val) ? 'block' : 'none';
      }
    }
  }

  private applyConditionalContainers(doc: Document): void {
    const count = this.getApplicantsCountFromTemplate(doc);

    for (let i = 1; i <= count; i++) {
      // ----- Address Other container -----
      const addrSel = doc.getElementById(`IS-Current-Applicant-${i}-Address`);
      const addrVal = (addrSel.innerHTML || '').trim();
      const addrContainer = doc.getElementById(`Applicant-${i}-Other-Container`);
      if (addrContainer) {
        addrContainer.style.display = (addrVal === 'Other') ? 'block' : 'none';
      }

      // ----- PEP Description container (.specify-box) -----
      // const pepSel = doc.getElementById(`IS-Applicant-${i}-Is-PEP`);
      // const pepVal = (pepSel.innerHTML || '').trim();
      // const pepYn = this.normalizeYesNo(pepVal);

      // const pepDesc = doc.getElementById(`IS-Applicant-${i}-PEP-Description`);
      // const pepBox = pepDesc.closest('.specify-box') as HTMLElement | null;
      // if (pepBox) {
      //   pepBox.style.display = (pepYn === 'Y') ? 'block' : 'none';
      // }

      // ----- Civil Status Description container -----
      const civilSel = doc.getElementById(`IS-Applicant-${i}-Civil-Status`);
      const civilVal = (civilSel.innerHTML || '').trim().toLowerCase();

      const civilDesc = doc.getElementById(`IS-Applicant-${i}-Civil-Status-Description`);
      const civilWrap = civilDesc.parentElement as HTMLElement | null; // the div wrapper
      if (civilWrap) {
        civilWrap.style.display = (civilVal === 'other') ? 'block' : 'none';
      }
    }
  }

  private cellText(el?: Element): string {
    return el ? (el.textContent || '').trim() : '';
  }
  private cellNumber(el?: Element): number {
    const s = this.cellText(el).replace(/,/g, '');
    const n = +s;
    return isNaN(n) ? 0 : n;
  }

  private hydrateAssetsFromDoc(doc: Document): void {
    (['LB', 'MV', 'STD', 'LIP'] as Utils.SectionKey[]).forEach((k) => {
      const sec = this.assetSections[k];
      const body = doc.getElementById(sec.bodyId);
      // reset
      sec.rows.length = 0;
      if (!body) return;

      Array.from(body.querySelectorAll('tr')).forEach((tr) => {
        const tds = Array.from(tr.querySelectorAll('td'));
        const row: any = {};
        sec.columns.forEach((col, idx) => {
          row[col.key] = col.type === 'currency'
            ? this.cellNumber(tds[idx])
            : this.cellText(tds[idx]);
        });
        sec.rows.push(row);
      });
    });
  }

  private hydrateFacilityFromDoc(doc: Document): void {
    const body = doc.getElementById('FACILITY_DETAILS_BODY');
    this.facilityRows = [];
    if (!body) return;

    Array.from(body.querySelectorAll('tr')).forEach((tr) => {
      const td = tr.querySelectorAll('td');
      this.facilityRows.push({
        typeOfFacilityRequired: this.cellText(td[0]),
        purposeOfFacility: this.cellText(td[1]),
        amountOfFaclity: this.cellNumber(td[2]),
        numberOfYearsRequired: this.cellText(td[3]),
        repaymentOption: this.cellText(td[4]),
        preferedLoanDedDate: this.cellText(td[5]),
        applicantsContribution: this.cellText(td[6]),
        advancePayement: this.cellText(td[7]),
      });
    });

    // keep proposed facilities card in sync
    this.syncProposedFacilitiesFromSection4();
  }

  private hydrateBankAccountsFromDoc(doc: Document): void {
    // 8.1 – Sampath
    const sb = doc.getElementById('SAMPATH_BANK_ACCOUNTS_BODY');
    this.sampathAccounts = [];
    if (sb) {
      Array.from(sb.querySelectorAll('tr')).forEach((tr) => {
        const td = tr.querySelectorAll('td');
        this.sampathAccounts.push({
          bankAndBranch: this.cellText(td[0]),
          accountType: this.cellText(td[1]),
          accountNo: this.cellText(td[2]),
          dateOpened: this.cellText(td[3]),
          presentBalance: this.cellNumber(td[4]),
          accountHolderName: this.cellText(td[5]),
        });
      });
    }

    // 8.2 – Other
    const ob = doc.getElementById('OTHER_BANK_ACCOUNTS_BODY');
    this.otherAccounts = [];
    if (ob) {
      Array.from(ob.querySelectorAll('tr')).forEach((tr) => {
        const td = tr.querySelectorAll('td');
        this.otherAccounts.push({
          bankAndBranch: this.cellText(td[0]),
          accountType: this.cellText(td[1]),
          accountNo: this.cellText(td[2]),
          dateOpened: this.cellText(td[3]),
          presentBalance: this.cellNumber(td[4]),
          accountHolderName: this.cellText(td[5]),
        });
      });
    }
  }

  private hydrateExistingLiabilitiesFromDoc(doc: Document): void {
    const body = doc.getElementById('EXISTING_LIABILITIES_BODY');
    this.existingLiabilities = [];
    if (!body) return;

    Array.from(body.querySelectorAll('tr')).forEach((tr) => {
      const td = tr.querySelectorAll('td');
      this.existingLiabilities.push({
        creditorName: this.cellText(td[0]),
        referenceNo: this.cellText(td[1]),
        repaymentPeriod: this.cellText(td[2]),
        security: this.cellText(td[3]),
        amountBorrowed: this.cellNumber(td[4]),
        balancePayableNow: this.cellNumber(td[5]),
        balancePeriodToComplete: this.cellText(td[6]),
        borrowedBy: this.cellText(td[7]),
        purpose: this.cellText(td[8]),
      });
    });
  }

  private hydrateSectionDataFromHtml(doc: Document): void {
    this.hydrateAssetsFromDoc(doc);
    this.hydrateFacilityFromDoc(doc);
    this.hydrateBankAccountsFromDoc(doc);
    this.hydrateExistingLiabilitiesFromDoc(doc);
  }

  private readText(doc: Document, id: string): string {
    const el = doc.getElementById(id);
    return el ? (el.textContent || '').trim() : '';
  }

  private readSection7Year(
    doc: Document, i: number, y: 1 | 2 | 3
  ): { yearLabel: string; statutoryIncome: string; assessableIncome: string; taxableIncome: string; taxPaid: string } {

    const label =
      this.readText(doc, `IU-Section7-Year-${y}-Label-Applicant-${i}`) ||
      this.readText(doc, `IU-Section7-Year-${y}-Label`);

    return {
      yearLabel: label,
      statutoryIncome: this.readTextPreferringV(doc, `IU-Section7-Year-${y}-Applicant-${i}-Statutory-Income`),
      assessableIncome: this.readTextPreferringV(doc, `IU-Section7-Year-${y}-Applicant-${i}-Assessable-Income`),
      taxableIncome: this.readTextPreferringV(doc, `IU-Section7-Year-${y}-Applicant-${i}-Taxable-Income`),
      taxPaid: this.readTextPreferringV(doc, `IU-Section7-Year-${y}-Applicant-${i}-Tax-Paid`),
    };
  }

  private hydrateSection7FromDoc(doc: Document): void {
    const count = Math.max(1, this.getApplicantsCountFromTemplate(doc));
    this.section7Applicants = [];

    for (let i = 1; i <= count; i++) {
      const y1 = this.readSection7Year(doc, i, 1);
      const y2 = this.readSection7Year(doc, i, 2);
      const y3 = this.readSection7Year(doc, i, 3);
      this.section7Applicants.push({
        applicantIndex: i,
        year1: y1,
        year2: y2,
        year3: y3
      });
    }

    this.syncSection7ToFields();
    this.cdr.detectChanges();
  }

  private normalizeAllMultiSelectValues(): void {
    this.documentInputFields.forEach(f => {
      if (f.inputType === Utils.SDConstants.inputMode.MULTI_SELECT) {
        if (!Array.isArray(f.value)) {
          const s = (f.value || '').toString();
          f.value = s
            ? s.split(/[;,]/).map(x => x.trim()).filter(Boolean)
            : [];
        }
      }
    });
  }

  private normalizeOverseasDisplayOrder(): void {
    this.documentInputFields = this.documentInputFields.map(f => {
      const m = f.id.match(/^IS-Applicant-(\d+)-(.*)$/i);
      const idx = m ? parseInt(m[1], 10) : 0;
      if (/Is-Employed-Residing-in-overseas-Contact-Person-Name-In-Sri-Lanka$/i.test(f.id)) {
        f.displayOrder = 160000 + idx * 100 + 10;
      } else if (/Contact-Person-Name$/i.test(f.id)) {
        f.displayOrder = 160000 + idx * 100 + 20;
      } else if (/Contact-Person-Mobile$/i.test(f.id)) {
        f.displayOrder = 160000 + idx * 100 + 30;
      } else if (/Contact-Person-Relationship$/i.test(f.id)) {
        f.displayOrder = 160000 + idx * 100 + 40;
      }
      return f;
    });
    this.documentInputFields.sort((a, b) => a.displayOrder - b.displayOrder);
  }

  private writeSection7JsonStore(doc: Document): void {
    try {
      const payload = JSON.stringify(this.section7Applicants || []);
      let node = doc.getElementById(this.S7_STORE_ID) as HTMLScriptElement | null;

      if (!node) {
        node = doc.createElement('script');
        node.id = this.S7_STORE_ID;
        node.type = 'application/json';
        doc.body.appendChild(node);
      }

      node.textContent = payload;
    } catch (e) {
      console.warn('[DigitalAppModal] Failed to write S7 JSON store', e);
    }
  }

  private readSection7FromJsonStore(doc: Document): boolean {
    try {
      let el = doc.getElementById(this.S7_STORE_ID) as HTMLScriptElement | null;
      if (!el || !el.textContent) return false;

      let arr: any[];
      try {
        arr = JSON.parse(el.textContent);
      } catch (parseErr) {
        return false;
      }
      if (!arr || !arr.length) return false;

      let safe = function (y: any): any {
        y = y || {};
        return {
          yearLabel: (y.yearLabel != null ? String(y.yearLabel) : ""),
          statutoryIncome: (y.statutoryIncome != null ? String(y.statutoryIncome) : ""),
          assessableIncome: (y.assessableIncome != null ? String(y.assessableIncome) : ""),
          taxableIncome: (y.taxableIncome != null ? String(y.taxableIncome) : ""),
          taxPaid: (y.taxPaid != null ? String(y.taxPaid) : "")
        };
      };

      this.section7Applicants = (arr || []).map(function (r: any, idx: number) {
        r = r || {};
        return {
          applicantIndex: (r.applicantIndex != null ? r.applicantIndex : (idx + 1)),
          year1: safe(r.year1),
          year2: safe(r.year2),
          year3: safe(r.year3),
        };
      });

      this.showSection7 = true;
      this.syncSection7ToFields();
      this.cdr.detectChanges();
      return true;
    } catch (e) {
      console.warn('[DigitalAppModal] Failed to parse S7 JSON store', e);
      return false;
    }
  }

  private hasSection7Values(): boolean {
    if (!this.section7Applicants || !this.section7Applicants.length) return false;
    for (let i = 0; i < this.section7Applicants.length; i++) {
      let a = this.section7Applicants[i]; if (!a) continue;
      if (
        (a.year1 && (a.year1.statutoryIncome || a.year1.assessableIncome || a.year1.taxableIncome || a.year1.taxPaid)) ||
        (a.year2 && (a.year2.statutoryIncome || a.year2.assessableIncome || a.year2.taxableIncome || a.year2.taxPaid)) ||
        (a.year3 && (a.year3.statutoryIncome || a.year3.assessableIncome || a.year3.taxableIncome || a.year3.taxPaid))
      ) { return true; }
    }
    return false;
  }

  private readTextPreferringV(doc: Document, iuId: string): string {
    let vId = iuId.replace(/^IU-/, 'V-');
    let el = doc.getElementById(vId);
    let s = el ? (el.textContent || '').trim() : '';
    if (s) return s;

    el = doc.getElementById(iuId);
    return el ? (el.textContent || '').trim() : '';
  }

  private createFieldFromDoc(doc: Document, iuId: string): Utils.DocumentInputField {
    let vId = iuId.replace(/^IU-/, 'V-');
    let vEl = doc.getElementById(vId);
    let iuEl = doc.getElementById(iuId);

    let value = '';
    if (vEl && vEl.textContent) value = (vEl.textContent || '').trim();
    else if (iuEl && iuEl.textContent) value = (iuEl.textContent || '').trim();

    let field: Utils.DocumentInputField = {
      id: iuId,
      label: this.getCustomizeLable(iuId),
      inputType: 'text',
      value: value,
      isRequired: false,
      valueId: vEl ? vId : (iuEl ? iuId : vId),
      selectOptions: [],
      showInput: true,
      hasMainEelement: false,
      error: '',
      disabled: false,
      displayOrder: 0
    };

    this.documentInputFields.push(field);
    return field;
  }

  private createConsentFieldFromDoc(doc: Document, iuId: string, inputType: string): Utils.DocumentInputField {
    let vId = iuId.replace(/^IU-/, 'V-');
    let vEl = doc.getElementById(vId);
    let iuEl = doc.getElementById(iuId);

    let value = '';
    if (vEl && vEl.textContent) value = (vEl.textContent || '').trim();
    else if (iuEl && iuEl.textContent) value = (iuEl.textContent || '').trim();

    let f: Utils.DocumentInputField = {
      id: iuId,
      label: this.getCustomizeLable(iuId),
      inputType: inputType,   // 'text' | 'select' | 'multiSelect' | 'decimal'
      value: value,
      isRequired: false,
      valueId: vEl ? vId : (iuEl ? iuId : vId),
      selectOptions: [],
      showInput: true,
      hasMainEelement: false,
      error: '',
      disabled: false,
      displayOrder: 0
    };
    // let save loop persist it
    this.documentInputFields.push(f);
    return f;
  }

  private ensureValueNode(doc: Document, id: string): HTMLElement {
    var el = doc.getElementById(id);
    if (el) return el;

    // If the exact target doesn't exist, create it.
    // We keep it simple and append to <body>; the printing template will still find it by id.
    el = doc.createElement('span');
    el.id = id;
    doc.body.appendChild(el);
    return el;
  }

  onFacilityCellChange(): void {
    this.syncProposedFacilitiesFromSection4();
    this.cdr.detectChanges();
  }

  isNationalityFieldId(id: string): boolean {
    // Matches "IU-Nationality-Applicant-1" or "IS-Nationality-Applicant-2"
    return /^I[US]-Nationality-Applicant-\d+$/i.test(id)
      || /Nationality Applicant \d+/i.test(id.replace(/-/g, ' '));
  }

  private toStrArray(v: any): string[] {
    if (Array.isArray(v)) return v.map(x => (x == null ? '' : '' + x).trim()).filter(Boolean);
    if (v == null) return [];
    const s = '' + v;
    return s
      .split(/[;,]/)
      .map(x => x.trim())
      .filter(Boolean);
  }

  toggleMultiOption(feild: any, optValue: string, checked: boolean): void {
    const current = Array.isArray(feild.value) ? feild.value.slice() : this.toStrArray(feild.value);
    const sri = 'Sri Lankan';
    const non = 'Non-Sri Lankan';

    if (checked) {
      // add the value
      if (current.indexOf(optValue) === -1) current.push(optValue);

      // mutual exclusivity for Sri vs Non-Sri
      if (optValue === sri) {
        feild.value = current.filter(v => v !== non);
        return;
      }
      if (optValue === non) {
        feild.value = current.filter(v => v !== sri);
        return;
      }

      feild.value = current;
    } else {
      feild.value = current.filter(v => v !== optValue);
    }
  }

  isChecked(feild: any, optValue: string): boolean {
    const arr = Array.isArray(feild.value) ? feild.value : this.toStrArray(feild.value);
    return arr.indexOf(optValue) > -1;
  }

  isYes(field: Utils.DocumentInputField | null): boolean {
    return field ? this.normalizeYesNo(field.value) === 'Y' : false;
  }

  // --- Generic numeric validator ---
  private validateNumberField(
    value: any,
    fieldLabel: string,
    opts?: { allowEmpty?: boolean }
  ): string {
    const allowEmpty = !!(opts && opts.allowEmpty);

    const s = (value || '').toString().replace(/,/g, '').trim();
    if (!s) {
      return allowEmpty ? '' : `${fieldLabel} is required`;
    }

    const n = +s;
    if (isNaN(n)) {
      return `${fieldLabel} must be a number`;
    }

    return '';
  }

  // Facility section validation
  validateFacilityNumeric(field: 'amountOfFaclity' | 'numberOfYearsRequired' | 'applicantsContribution'): void {
    const labels = {
      amountOfFaclity: 'Amount of facility requested',
      numberOfYearsRequired: 'No. of years required for repayment',
      applicantsContribution: 'Applicant’s contribution (Equity)',
    } as const;
    const msg = this.validateNumberField((this.facilityForm as any)[field], labels[field]);
    this.facilityErrors[field] = msg;
    this.cdr.detectChanges();
  }

  canAddFacilityRow(): boolean {
    const f = this.facilityForm;
    const minimalOk =
      (f.typeOfFacilityRequired || '').toString().trim().length > 0 ||
      (f.purposeOfFacility || '').toString().trim().length > 0;

    const numericOk = Object.values(this.facilityErrors).every(e => !e);

    return minimalOk && numericOk;
  }

  //Asset
  private readonly numericColsBySection: Record<Utils.SectionKey, string[]> = {
    LB: ['marketValue'],
    MV: ['marketValue'],
    STD: ['valueOfBills'],
    LIP: ['faceValue', 'premiumFrequency'],
  };

  onAssetInputChange(key: Utils.SectionKey, col: { key: string; label: string; type?: string }, value: string): void {
    const mustBeNumeric = (this.numericColsBySection[key] || []).includes(col.key) || col.type === 'currency';
    if (mustBeNumeric) {
      this.assetErrors[key][col.key] = this.validateNumberField(value, col.label);
    } else {
      this.assetErrors[key][col.key] = '';
    }
    (this.assetSections[key].formModel as any)[col.key] = value;
    this.cdr.detectChanges();
  }

  canAddAssetRow(key: Utils.SectionKey): boolean {
    const sec = this.assetSections[key];
    if (!sec || !sec.formModel || !sec.columns || sec.columns.length === 0) return false;

    // require either first column OR owner column to be non-empty
    const firstKey = sec.columns[0].key;
    const ownerCol = sec.columns.find(c => (c.label || '').toLowerCase().includes('owner'));
    const ownerKey = ownerCol ? ownerCol.key : firstKey;
    const fm = sec.formModel as any;
    const first = (fm[firstKey] || '').toString().trim();
    const owner = (fm[ownerKey] || '').toString().trim();
    const minimalOk = !!first || !!owner;

    // numeric errors for this section must all be empty
    const errs = this.assetErrors[key] || {};
    const numericOk = Object.values(errs).every(e => !e);

    return minimalOk && numericOk;
  }

  //MONTHLY INCOME & EXPENDITURE
  validateSection6Input(appIndex: number, kind: 'Income' | 'Expenditure', field: string, value: any) {
    const cleaned = (value || '').toString().replace(/,/g, '').trim();
    let msg = '';

    if (cleaned && isNaN(+cleaned)) {
      msg = `${field.replace(/([A-Z])/g, ' $1')} must be numeric`;
    }

    if (!this.section6Errors[kind][appIndex]) {
      this.section6Errors[kind][appIndex] = {};
    }

    this.section6Errors[kind][appIndex][field] = msg;

    if (!msg) {
      this.onSection6AmountBlur(appIndex, kind, field);
    }

    this.cdr.detectChanges();
  }

  isSection6FieldInvalid(
    appIndex: number,
    kind: 'Income' | 'Expenditure',
    field: string
  ): boolean {
    return (
      this.section6Errors &&
      this.section6Errors[kind] &&
      this.section6Errors[kind][appIndex] &&
      this.section6Errors[kind][appIndex][field] &&
      this.section6Errors[kind][appIndex][field].length > 0
    );
  }

  getSection6Error(
    appIndex: number,
    kind: 'Income' | 'Expenditure',
    field: string
  ): string {
    return (
      (this.section6Errors &&
        this.section6Errors[kind] &&
        this.section6Errors[kind][appIndex] &&
        this.section6Errors[kind][appIndex][field]) ||
      ''
    );
  }

  // --- Generic validators ---
  validateDigitsOnly(value: any, label: string): string {
    const s = (value || '').toString().trim();
    if (!s) return `${label} is required`;
    return /^\d+$/.test(s) ? '' : `${label} must contain digits only`;
  }

  validateNumeric(value: any, label: string): string {
    const s = (value || '').toString().replace(/,/g, '').trim();
    if (!s) return `${label} is required`;
    return isNaN(+s) ? `${label} must be numeric` : '';
  }

  // --- 8.1 Sampath ---
  validateSampath(field: 'accountNo' | 'presentBalance'): void {
    if (field === 'accountNo') {
      this.sampathErrors.accountNo = this.validateDigitsOnly(this.sampathForm.accountNo, 'Account No.');
    } else {
      this.sampathErrors.presentBalance = this.validateNumeric(this.sampathForm.presentBalance, 'Present Balance');
    }
    this.cdr.detectChanges();
  }

  canAddSampathRow(): boolean {
    const f = this.sampathForm;
    const minimalOk =
      (f.bankAndBranch || '').toString().trim().length > 0 &&
      (f.accountNo || '').toString().trim().length > 0;
    const numericOk = !this.sampathErrors.accountNo && !this.sampathErrors.presentBalance;
    return minimalOk && numericOk;
  }

  //--- 9 Existing Liabilities ---
  validateLiability(field: 'amountBorrowed' | 'balancePayableNow'): void {
    const label = field === 'amountBorrowed' ? 'Amount Borrowed' : 'Balance Payable Now';
    const value = (this.liabilitiesForm as any)[field];
    this.liabilitiesErrors[field] = this.validateNumeric(value, label);
    this.cdr.detectChanges();
  }

  canAddLiabilityRow(): boolean {
    const f = this.liabilitiesForm;
    const minimalOk =
      (f.creditorName || '').toString().trim().length > 0 &&
      (f.referenceNo || '').toString().trim().length > 0;
    const numericOk = !this.liabilitiesErrors.amountBorrowed && !this.liabilitiesErrors.balancePayableNow;
    return minimalOk && numericOk;
  }

  // validate security to be offered
  validateSecrityTobeOffered(field: 'floorArea' | 'marketValue', value: any): void {
    const label = field === 'floorArea' ? 'Floor Area' : 'Market Value';
    const error = this.validateNumeric(value, label);
    this.securityToBeOfferedErros[field] = error;
  }

  private rebindConsentAndDeclarationFields(): void {
    // Insurance Consent 
    const ic = this.INS_CONSENT_IDS;
    const rb = (id: string) => this.getFieldById(id);

    this.agreeDisagreeField = rb(ic.agreeDisagree) || this.agreeDisagreeField;
    this.annexuresField = rb(ic.annexures) || this.annexuresField;
    this.accountNoField = rb(ic.accountNo) || this.accountNoField;
    this.loanRequiredField = rb(ic.loanRequired) || this.loanRequiredField;
    this.insuranceAmountField = rb(ic.amount) || this.insuranceAmountField;
    this.insuranceCompaniesField = rb(ic.companies) || this.insuranceCompaniesField;

    // Section 10
    const s10 = this.SECTION10_IDS;
    this.section10JudgmentField = rb(s10.judgment) || this.section10JudgmentField;
    this.section10AccountNoField = rb(s10.accountNo) || this.section10AccountNoField;
    this.section10CustomerNameField = rb(s10.customerName) || this.section10CustomerNameField;
    this.section10IsPepField = rb(s10.isPep) || this.section10IsPepField;
    this.section10PepDescriptionField = rb(s10.pepDescription) || this.section10PepDescriptionField;
  }

  private syncConsentAndDeclarationToCanonical(): void {
    const copy = (src: Utils.DocumentInputField | null, id: string) => {
      if (!src) return;
      const canonical = this.getFieldById(id);
      if (canonical) {
        // Normalize multi-selects once (same logic you already use elsewhere)
        if (Array.isArray(src.value) && src.inputType === Utils.SDConstants.inputMode.MULTI_SELECT) {
          // ensure it's an array of strings
          this.coerceMultiArray(src);
        }
        canonical.value = src.value;
        canonical.showInput = true; // forces save loop to use the field's value
      }
    };

    const ic = this.INS_CONSENT_IDS;
    copy(this.agreeDisagreeField, ic.agreeDisagree);
    copy(this.annexuresField, ic.annexures);
    copy(this.accountNoField, ic.accountNo);
    copy(this.loanRequiredField, ic.loanRequired);
    copy(this.insuranceAmountField, ic.amount);
    copy(this.insuranceCompaniesField, ic.companies);

    const s10 = this.SECTION10_IDS;
    copy(this.section10JudgmentField, s10.judgment);
    copy(this.section10AccountNoField, s10.accountNo);
    copy(this.section10CustomerNameField, s10.customerName);
    copy(this.section10IsPepField, s10.isPep);
    copy(this.section10PepDescriptionField, s10.pepDescription);
  }

}
