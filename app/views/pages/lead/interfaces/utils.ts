export class SDConstants {
  public static readonly CCDU_DIV_CODE: string = "869";
  public static readonly CCPU_DIV_CODE: string = "822";

  public static readonly DOTTED_TXT_IDS: string[] = [
    "IU-Agreement-Date",
    "IU-Letter-Date",
    "IU-Invoice-Date",
    "IU-Commencing-Date",
  ];

  public static readonly documentStatusConst = {
    PENDING: "PENDING",
    DRAFT: "DRAFT",
    SUBMIT: "SUBMIT",
    RETURN: "RETURN",
    PRINT: "PRINT",
    APPROVE: "APPROVE",
    RECOMMEND_RETURN: "RECOMMEND_RETURN",
  };

  public static readonly elementIdType = {
    I: "I",
    IS: "IS",
    IST: "IST",
    IU: "IU",
    IB: "IB",
    DPL: "DPL",
    V: "V",
  };

  public static readonly elementIdTypes = [
    SDConstants.elementIdType.I,
    SDConstants.elementIdType.IS,
    SDConstants.elementIdType.IST,
    SDConstants.elementIdType.IU,
  ];

  public static readonly inputMode = {
    SELECT: "select",
    SELECT_TEXT: "selectText",
    MULTI_SELECT: "multiSelect",
    DATE: "date",
    TEXT: "text",
    TEXTAREA: "textarea",
    DECIMAL: "decimal",
  };

  public static readonly inputModeConst = {
    select: "SELECT",
    selectText: "SELECT_TEXT",
    date: "DATE",
    text: "TEXT",
    textarea: "TEXTAREA",
    decimal: "DECIMAL",
  };

  public static readonly inquiryReason = {
    IR1: "EvaluatingOfABorrowerForANewCreditFacility",
    IR2: "ReviewAsAPartnerProprietorForANewCreditFacility",
    IR3: "ReviewAsADirectorForANewCreditFacility",
  };

  public static readonly relationship = {
    SOUL_PROP_OWNER: "Sole Proprietor/Owner",
    PARTNER: "Partnership",
    DIRECTOR: "Director",
    SPAUSE: "Spouse",
    GARDIAN: "Gardian",
    OWNER: "Owner",
    INDIVIDUAL: "Individual",
  };

  public static readonly identificationTypeConst = {
    OLD_NIC: "OldNIC",
    NEW_NIC: "NewNIC",
    BRC: "BRC",
  };

  public static readonly analyticsDecision = {
    APPROVED: "Approved",
    REJECTED: "Rejected",
  };

  public static readonly companyCribType = {
    SOUL_PROPRITER: "SoleProprietor",
    PARTNERSHIP: "Partnership",
    LIMITED_LIABILITY: "LimitedLiability",
  };

  public static titleConst = {
    MR: "MR",
    MRS: "MRS",
    MS: "MS",
    DR: "DR",
  };

  public static titleConstList = [
    { value: "MR", label: "Mr" },
    { value: "MRS", label: "Mrs" },
    { value: "MS", label: "Ms" },
    { value: "DR", label: "Dr" },
  ];
}

export interface Option {
  label: string;
  value: any;
}

export interface DocumentInputField {
  id: string;
  label: string;
  inputType: string;
  value: any;
  isRequired: boolean;
  valueId: string;
  selectOptions: any[];
  showInput: boolean;
  hasMainEelement: boolean;
  error: string;
  disabled: boolean;
  displayOrder: number;
}

// 3.1 Lands/Buildings (already in your code)
export interface AssetLB {
  description: string;
  floorAreaExtent: string; // floor area / extent etc.
  ownedBy: string;
  marketValue: number | string;
  mortgages: string;
}

// 3.2 Motor Vehicles
export interface MotorVehicleRow {
  typeOfVehicleAndRegisterNO: string;
  presentOwner: string; // floor area / extent etc.
  marketValue: number | string;
  mortgages: string;
}

// 3.3 Shares / TBills / Deposits
export interface SharesTBillsDepositsRow {
  type: string;
  nameOfCompany: string; // Share / T-Bill / Deposit
  valueOfBills: number | string;
  assigments: string;
}

// 3.4 Life Insurance Policies
export interface LifeInsuranceRow {
  nameOfPolicyHolder: string;
  nameOfCompany: string;
  policyNo: number | string;
  faceValue: number | string;
  premiumFrequency: string;
  assigments: string;
}

export interface AssetSection<RowT> {
  key: SectionKey;
  title: string;
  bodyId: string;
  totalId?: string;
  sumKey?: keyof RowT;
  columns: ColumnDef[];
  showFlag: boolean;
  formModel: any;
  rows: RowT[];
}

// ---- Section 4: Facility Details ----
export interface FacilityDetailRow {
  typeOfFacilityRequired: string;
  purposeOfFacility: string;
  amountOfFaclity: number | string;
  numberOfYearsRequired: string;
  repaymentOption: string;
  preferedLoanDedDate: string;
  applicantsContribution: string;
  advancePayement: string;
}

export interface SecurityOfferedRow {
  description: string;
  floorAreaExtent: string;
  ownedBy: string;
  marketValue: number | string;
  mortgages: string;
}

// ---- Section 6 data model (per applicant) ----
export interface Section6Income {
  professionEmployment: string;
  business: string;
  industry: string;
  agriculture: string;
  interest: string;
  dividends: string;
  rentLease: string;
  commissions: string;
  others: string;
  total: string;
}

export interface Section6Expenditure {
  houseRent: string;
  householdExpenses: string;
  clothing: string;
  childrensEducation: string;
  personal: string;
  traveling: string;
  medical: string;
  loanRepayment: string;
  creditCardPayment: string;
  ratesAndTaxes: string;
  electricityFuel: string;
  insurance: string;
  dependents: string;
  others: string;
  total: string;
}
export interface Section6ApplicantRow {
  applicantIndex: number;
  income: Section6Income;
  expenditure: Section6Expenditure;
}

// ---- Section 7 data model ----
export interface Section7YearData {
  yearLabel: string;
  statutoryIncome: string;
  assessableIncome: string;
  taxableIncome: string;
  taxPaid: string;
}
export interface Section7ApplicantRow {
  applicantIndex: number;
  year1: Section7YearData;
  year2: Section7YearData;
  year3: Section7YearData;
}

// ---- Section 8 rows ----
export interface SampathBankAccountRow {
  id?: string;
  disabled?: Boolean;
  bankAndBranch: string;
  accountType: string;
  accountNo: string;
  dateOpened: string;
  presentBalance: number | string;
  accountHolderName: string;
}

export interface OtherBankAccountRow {
  id?: string;
  disabled?: boolean;
  bankAndBranch: string;
  accountType: string;
  accountNo: string;
  dateOpened: string;
  presentBalance: number | string;
  accountHolderName: string;
}

// ---- Section 9 rows ----
export interface ExistingLiabilityRow {
  creditorName: string;
  referenceNo: string;
  repaymentPeriod: string;
  security: string;
  amountBorrowed: number | string;
  balancePayableNow: number | string;
  balancePeriodToComplete: string;
  borrowedBy: string;
  purpose: string;
}

//proposed facility
export interface ProposedFacilityRow {
  typeOfFacilityRequired: string;
  amountOfFaclity: number | string;
  purposeOfFacility: string;
  repaymentOption: string;
  interestRate: string;
}

export type ColumnDef = {
  key: string;
  label?: string;
  type?: "text" | "currency";
  style?: string;
};

export type SectionKey = "LB" | "MV" | "STD" | "LIP";

export function newDocumentInputField(): DocumentInputField {
  return {
    id: "",
    label: "",
    inputType: "text",
    value: "",
    isRequired: false,
    valueId: "",
    selectOptions: [],
    showInput: false,
    hasMainEelement: false,
    error: "",
    disabled: true,
    displayOrder: 0,
  };
}
