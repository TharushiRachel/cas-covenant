export class SDConstants {
  public static readonly CCDU_DIV_CODE: string = "869";
  public static readonly CCPU_DIV_CODE: string = "822";

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
}

export interface Option {
  label: string;
  value: any;
}

export interface DocumentTemplateData {
  creditFacilityName: string;
  creditFacilityTemplateID: number;
  facilityTypeName: string;
  facilities: Facility[];
  documentElements: DocumentElement[];
  securityDocuments: SavedDocument[];
}

export interface CreditFacilityTemplateDTO {
  creditFacilityTypeID: number;
  creditFacilityTemplateID: number;
  creditFacilityName: string;
  facilityTypeName: string;
}

export interface Facility {
  facilityID: number;
  facilityRefCode: string;
  facilityCurrency: string;
  facilityAmount: number;
  creditFacilityName: string;
  creditFacilityTemplateID: number;
  facilityTypeName: string;
  tabTitle: string;
  displayOrder: number;
  pendingDocuments: DocumentElement[];
  draftedDocuments: DocumentElement[];
  submittedDocuments: DocumentElement[];
  returnedDocuments: DocumentElement[];
  recommandedDocuments: DocumentElement[];
}

export interface DocumentElement {
  elementID: number;
  elementName: string;
  creditFacilityTemplateID: number;
  creditFacilityName: string;
  documentFileName: string;
  facilityTags: any[];
  isDisabled: boolean;
}

export interface SavedDocument {
  creditFacilityTemplateID: number;
  creditFacilityName: string;
  facilityID: number;
  elementID: number;
  documentStatus: string;
}

export interface FacilityTemplate {
  creditFacilityTemplateID: number;
  creditFacilityName: string;
  facilityTypeName: string;
  securityDocumentID: number;
  facilities: Facility[];
  documentElements: DocumentElement[];
  selectedTabIndex: number;
  documentContent: string;
  documentName: string;
  expanded: boolean;
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
  isTag: boolean;
  hasMainEelement: boolean;
  error: string;
  disabled: boolean;
  displayOrder: number;
}

export interface DocumentTag {
  tagId: number;
  tag: string;
  tagValue: string;
}

export function newFacility(): Facility {
  return {
    facilityID: 0,
    facilityRefCode: "",
    facilityCurrency: "",
    facilityAmount: 0,
    creditFacilityName: "",
    creditFacilityTemplateID: 0,
    facilityTypeName: "",
    tabTitle: "",
    displayOrder: 0,
    pendingDocuments: [],
    draftedDocuments: [],
    submittedDocuments: [],
    returnedDocuments: [],
    recommandedDocuments: [],
  };
}

export function newFacilityTemplate(): FacilityTemplate {
  return {
    creditFacilityTemplateID: 0,
    creditFacilityName: "",
    facilityTypeName: "",
    facilities: [],
    documentElements: [],
    securityDocumentID: 0,
    documentContent: "",
    documentName: "",
    selectedTabIndex: 0,
    expanded: false,
  };
}

export function newDocumentElement(): DocumentElement {
  return {
    elementID: 0,
    elementName: "",
    documentFileName: "",
    creditFacilityTemplateID: 0,
    creditFacilityName: "",
    isDisabled: false,
    facilityTags: [],
  };
}