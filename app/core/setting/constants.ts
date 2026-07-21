export class Constants {
  public static status = {
    ACT: "Active",
    INA: "Inactive",
    RMV: "Remove",
  };

  public static statusConst = {
    ACT: "ACT",
    INA: "INA",
    RMV: "RMV",
  };

  public static approveStatus = {
    APPROVED: "Approved",
    PENDING: "Pending",
    REJECTED: "Rejected",
    PENDING_RMV: "Remove Pending",
  };

  public static facilityPaperStatus = {
    PENDING: "Pending",
    ASSIGNED: "Assigned",
    PROCESSING: "Processing",
    PROCESSED: "Processed",
    CLOSED: "Closed",
  };

  public static applicationFormCurrentStatus = {
    IN_PROGRESS: "In Progress",
    DECLINED: "Declined",
    DRAFT: "Draft",
    RETURNED: "Returned",
    PAPER_CREATED: "Paper Created",
  };

  public static applicationFormActionStatus = {
    IN_PROGRESS: "In Progress",
    DECLINED: "Decline",
    DRAFT: "Draft",
    RETURNED: "Return",
    PAPER_CREATED: "Create Paper",
  };

  public static applicationFormCurrentStatusConst = {
    IN_PROGRESS: "IN_PROGRESS",
    DECLINED: "DECLINED",
    DRAFT: "DRAFT",
    RETURNED: "RETURNED",
    PAPER_CREATED: "PAPER_CREATED",
  };
  public static applicationFormStatusChangeHeading = {
    IN_PROGRESS: "Forward",
    RETURNED: "Return",
  };

  public static leadStatusConst = {
    PENDING: "PENDING",
    SUBMITTED: "SUBMITTED",
    APPROVED: "APPROVED",
    ACCEPTED: "ACCEPTED",
    RETURNED: "RETURNED",
    CLOSED: "CLOSED",
    DECLINED: "DECLINED",
    APPLICATION_CREATED: "APPLICATION_CREATED",
    PAPER_CREATED: "PAPER_CREATED",
  };

  public static leadStatus = {
    PENDING: "Pending",
    SUBMITTED: "Submitted",
    APPROVED: "Approved",
    ACCEPTED: "Accepted",
    RETURNED: "Returned",
    CLOSED: "Closed",
    DECLINED: "Declined",
    APPLICATION_CREATED: "Application Created",
    APPLICATION_DECLINED: "Application Declined",
    PAPER_CREATED: "Paper Created",
  };

  public static leadActionConst = {
    SUBMIT: "Submit",
    PENDING: "Pending",
    APPROVED: "Approve",
    ACCEPTED: "Accept",
    RETURNED: "Return",
    CLOSED: "Close",
    DECLINED: "Decline",
    APPLICATION_CREATED: "Application Created",
    PAPER_CREATED: "Paper Create",
  };

  public static leadTypeConst = {
    INTERNAL: "INTERNAL",
    EXTERNAL: "EXTERNAL",
  };

  public static leadType = {
    INTERNAL: "Internal",
    EXTERNAL: "External",
  };

  public static leadCreationTypeConst = {
    BUSINESS: "BUSINESS",
    PERSONAL: "PERSONAL",
    CORPORATE: "CORPORATE",
  };

  public static leadCreationType = {
    BUSINESS: "Business",
    PERSONAL: "Personal",
    CORPORATE: "Corporate",
  };

  public static leadCompCreationTypeTypeConst = {
    BUSINESS: "BUSINESS",
    PERSONAL: "PERSONAL",
  };

  public static personalRelationShipElements = [
    { value: "SPAUSE", label: "Spouse" },
    { value: "GARDIAN", label: "Gardian" },
    { value: "SON_DAUGHTER", label: "Son/Daughter" },
  ];

  public static businessRelationShipElements = [
    { value: "SOUL_PROPRITER", label: "Owner" },
    { value: "PARTNERSHIP", label: "Partner" },
    { value: "LIMITED_LIABILITY", label: "Director" },
  ];

  public static allRelationShipElements = [
    { value: "Individual", label: "Individual" },
    { value: "SPAUSE", label: "Spouse" },
    { value: "GARDIAN", label: "Gardian" },
    { value: "SON_DAUGHTER", label: "Son/Daughter" },
    { value: "SOUL_PROPRITER", label: "Owner" },
    { value: "PARTNERSHIP", label: "Partner" },
    { value: "LIMITED_LIABILITY", label: "Director" },
    { value: "Owner", label: "Owner" },
    { value: "Partner", label: "Partner" },
    { value: "Director", label: "Director" },
  ];

  public static readonly leadCompBorrowerTypeConst = {
    BORROWER: "BORROWER",
    NON_BORROWER: "NON_BORROWER",
  };

  public static leadCompIncomeTypeConst = {
    No_INCOME: "No_INCOME",
  };

  public static paperReviewStatusConst = {
    APPROVED: "APPROVED",
    REJECTED: "REJECTED",
    SAVED: "SAVED",
    ACTION_REQUIRED: "ACTION_REQUIRED",
    REPLIED: "REPLIED",
  };

  public static paperReviewStatus = {
    APPROVED: "Approved",
    REJECTED: "Rejected",
    SAVED: "Saved",
    ACTION_REQUIRED: "Action Required",
    REPLIED: "Replied",
  };

  public static facilitySecuritySummaryType = {
    INDIVIDUAL: "Individual",
    GROUP: "Group",
  };

  public static facilitySecuritySummaryTypeConst = {
    INDIVIDUAL: "INDIVIDUAL",
    GROUP: "GROUP",
  };

  public static facilitySecuritySummaryTypeGroupConst = {
    FIRST_CLASS: "FIRST_CLASS",
    OTHER: "OTHER",
    DEFAULT: "DEFAULT",
  };

  public static acaeReportTypeConst = {
    CURRENT: "Current",
    HISTORY: "History",
  };

  public static currencyTypesConst = {
    LKR: "LKR",
    USD: "USD",
    EUR: "EUR",
    GBP: "GBP",
    SGD: "SGD",
    INR: "INR",
  };

  public static defaultWorkflowUpmGroupsConstants = {
    MD: "MD",
    ASSISTANT: "ASSISTANT",
  };

  public static defaultWorkflowUpmGroupsName = {
    MD: "Managing Director",
    ASSISTANT: "Assistant",
  };

  public static defaultWorkflowUpmGroupCode = {
    MD: "80",
    ASSISTANT: "79",
  };

  public static currencyTypesOpt = [
    { value: "LKR", label: "LKR" },
    { value: "USD", label: "USD" },
    { value: "EUR", label: "EUR" },
    { value: "GBP", label: "GBP" },
    { value: "SGD", label: "SGD" },
    { value: "INR", label: "INR" },
  ];

  public static civilStatus = {
    MARRIED: "Married",
    SINGLE: "Single",
    UNKNOWN: "Cannot Specify",
  };

  public static civilStatusConst = {
    MARRIED: "MARRIED",
    SINGLE: "SINGLE",
    UNKNOWN: "UNKNOWN",
  };

  public static applicationFormTopicPage = {
    REPAYMENT: "Repayment",
    ASSETS: "Assets",
    EXECUTIVE_SUMMARY: "Executive Summary",
  };

  public static applicationFormTopicPageConst = {
    REPAYMENT: "REPAYMENT",
    ASSETS: "ASSETS",
    EXECUTIVE_SUMMARY: "EXECUTIVE_SUMMARY",
  };

  public static approveStatusConst = {
    APPROVED: "APPROVED",
    PENDING: "PENDING",
    REJECTED: "REJECTED",
    PENDING_RMV: "PENDING_RMV",
  };

  public static approveStatusText = {
    APPROVED: "Approve",
    REJECTED: "Reject",
  };

  public static gender = {
    M: "Male",
    F: "Female",
  };

  public static genderConst = {
    M: "M",
    F: "F",
  };

  public static title = {
    MR: "Mr",
    MRS: "Mrs",
    MS: "Ms",
    DR: "Dr",
  };

  public static titleConst = {
    MR: "MR",
    MRS: "MRS",
    MS: "MS",
    DR: "DR",
  };

  public static yesNo = {
    Y: "Yes",
    N: "No",
  };

  public static yesNoConst = {
    Y: "Y",
    N: "N",
  };

  public static financialStabilityOptionSelect = [
    { value: "AUDITED", label: "Audited" },
    { value: "UNAUDITED", label: "Unaudited" },
    { value: "MANAGEMENT", label: "Management" },
  ];

  public static monthOptionSelect = [
    { value: "JANUARY", label: "January" },
    { value: "FEBRUARY", label: "February" },
    { value: "MARCH", label: "March" },
    { value: "APRIL", label: "April" },
    { value: "MAY", label: "May" },
    { value: "JUNE", label: "June" },
    { value: "JULY", label: "July" },
    { value: "AUGUST", label: "August" },
    { value: "SEPTEMBER", label: "September" },
    { value: "OCTOBER", label: "October" },
    { value: "NOVEMBER", label: "November" },
    { value: "DECEMBER", label: "December" },
  ];

  public static BCCPaperType = {
    BCC: "BCC/EAC REPORTING",
    BCCC: "BCC/EAC COVERING",
    EEBCC: "BCC/EAC EXPOSURE ENHANCEMENT",
    BRPTR: "BOARD RELATED PARTY TRANSACTION REVIEW COMMITTEE",
    BRPGG: "BOARD RELATED PARTY TRANSACTION REVIEW COMMITTEE GOOD GOVERNANCE",
  };

  public static BCCPaperTypeConst = {
    BCC: "BCC",
    BCCC: "BCCC",
    EEBCC: "EEBCC",
    BRPTR: "BRPTR",
    BRPGG: "BRPGG",
  };

  public static CRCPaperType = {
    ROF: "Risk Opinion Format",
    DF: "Descriptive Format",
    RF1: "Review Format 1",
    RF2: "Review Format 2",
  };

  public static CRCPaperTypeConst = {
    ROF: "ROF",
    DF: "DF",
    RF1: "RF1",
    RF2: "RF2",
  };

  public static ConstitutionType = {
    CHAIRMAN: "Chairman",
    MANAGING_DIRECTOR: "Managing Director",
    DIRECTOR: "Director",
    PARTNER: "Partner",
  };

  public static ConstitutionTypeConst = {
    CHAIRMAN: "CHAIRMAN",
    MANAGING_DIRECTOR: "MANAGING_DIRECTOR",
    DIRECTOR: "DIRECTOR",
    PARTNER: "PARTNER",
  };

  public static constitution = {
    PRIVATE_LTD: "Private LTD",
    PARTNERSHIP: "Partnership",
    PROPRIETORSHIP: "Proprietorship",
    OTHER: "Other",
    PUBLIC_LTD: "Public Ltd",
    CORPORATION: "Corporation",
  };

  public static constitutionConst = {
    PRIVATE_LTD: "PRIVATE_LTD",
    PARTNERSHIP: "PARTNERSHIP",
    PROPRIETORSHIP: "PROPRIETORSHIP",
    OTHER: "OTHER",
    PUBLIC_LTD: "PUBLIC_LTD",
    CORPORATION: "CORPORATION",
  };

  public static BCCFacilityTypeConst = {
    PROPOSED: "PROPOSED",
    EXISTING: "EXISTING",
  };

  public static BCCFacilityType = {
    PROPOSED: "PROPOSED FACILITY",
    EXISTING: "EXISTING FACILITY",
  };

  public static BCCPaperRecommendation = {
    RECOMMENDED_BY_GRO: "Recommended By GRO",
    NOT_RECOMMENDED_BY_GRO: "Not Recommended By GRO",
    CONDITION_OF_IMPOSED:
      "Recommended By GRO Subject to Fulfillment of Conditions Imposed",
  };

  public static BCCPaperRecommendationConst = {
    RECOMMENDED_BY_GRO: "RECOMMENDED_BY_GRO",
    NOT_RECOMMENDED_BY_GRO: "NOT_RECOMMENDED_BY_GRO",
    CONDITION_OF_IMPOSED: "CONDITION_OF_IMPOSED",
  };

  public static bccRiskRatingScoreOptionSelect = [
    { value: "A+", label: "A+" },
    { value: "A ", label: "A" },
    { value: "A-", label: "A-" },
    { value: "B+", label: "B+" },
    { value: "B ", label: "B" },
    { value: "B-", label: "B-" },
    { value: "C+", label: "C+" },
    { value: "C ", label: "C" },
    { value: "C-", label: "C-" },
    { value: "D ", label: "D" },
  ];

  public static financialObligations = {
    BORROWER: "Borrower",
    GUARANTOR: "Guarantor",
    INDEMNITOR: "Indemnitor",
  };

  public static financialObligationsConst = {
    BORROWER: "BORROWER",
    GUARANTOR: "GUARANTOR",
    INDEMNITOR: "INDEMNITOR",
  };

  public static cribStatus = {
    NOT_ENTERED: "Not Entered",
    PENDING: "Pending",
    NO_IRREGULAR_ADVANCES: "No Irregular Advances",
    REPORTED_AS_IRREGULAR: "Reported as Irregular (Refer Comments)",
    SERVICE_NOT_AVAILABLE: "Crib Service Unavailable",
    SKIP_CRIB_REPORT: "Skip Crib Report",
  };

  public static cribStatusConst = {
    NOT_ENTERED: "NOT_ENTERED",
    PENDING: "PENDING",
    NO_IRREGULAR_ADVANCES: "NO_IRREGULAR_ADVANCES",
    REPORTED_AS_IRREGULAR: "REPORTED_AS_IRREGULAR",
    SERVICE_NOT_AVAILABLE: "SERVICE_NOT_AVAILABLE",
    SKIP_CRIB_REPORT: "SKIP_CRIB_REPORT",
  };

  public static basicInformationType = {
    PERSONAL: "Personal",
    BUSINESS: "Business",
    CORPORATE: "Corporate",
  };

  public static basicInformationTypeConst = {
    PERSONAL: "PERSONAL",
    BUSINESS: "BUSINESS",
    CORPORATE: "CORPORATE",
  };

  public static employStatus = {
    EMPLOYED: "Employed",
    SELF_EMPLOYED: "Self Employed",
  };

  public static employStatusConst = {
    EMPLOYED: "EMPLOYED",
    SELF_EMPLOYED: "SELF_EMPLOYED",
  };

  public static privilegeCodes = {};

  public static masterDataKey = {
    CAS_CONSTANTS: "CAS_CONSTANTS_data",
    CAS_BRANCHES: "CAS_Branches_data",
    CAS_PURPOSE_OF_ADVANCED: "CAS_PURPOSE_OF_ADVANCED_DATA",
    CAS_SECTOR_DATA: "CAS_SECTOR_DATA",
    CAS_SUB_SECTOR_DATA: "CAS_SUB_SECTOR_DATA",
    CAS_CUSTOMERS: "CAS_CUSTOMERS_data",
    CAS_SUPPORTING_DOCs: "CAS_SUPPORING_DOCs_data",
    // CAS_GLOBAL_SUPPORTING_DOCs: 'CAS_GLOBAL_SUPPORING_DOCs_data',
    CAS_CREDIT_FACILITY_TEMPLATES: "CAS_CREDIT_FACILITY_TEMPLATES_data",
    CAS_CREDIT_FACILITY_TYPES: "CAS_CREDIT_FACILITY_TYPES_data",
    CAS_CREDIT_FACILITY_INTEREST_RATES: "CAS_CREDIT_FACILITY_INTEREST_RATES",
    CAS_WORKFLOW_TEMPLATES: "CAS_WORKFLOW_TEMPLATES_data",
    CAS_SECURITY_SUMMARY_TOPICS: "CAS_SECURITY_SUMMARY_TOPICS_data",
    CAS_APPLICATION_FORM_TOPICS: "CAS_APPLICATION_FORM_TOPICS_data",
    CAS_BRANCH_DEPARTMENT_LIST: "CAS_BRANCH_DEPARTMENT_LIST", // This is only for use for specific Functionality on Cas Application and Do not user for select drop downs
    CAS_APPLICATION_USER_ASSISTANTS: "CAS_APPLICATION_USER_ASSISTANTS_data",
    CAS_COMMITTEE_TYPE_LIST: " CAS_COMMITTEE_TYPE_LIST_data",
    CAS_COMMITTEE_LIST: "CAS_COMMITTEE_LIST_data",
    CAS_COMMITTEE_LEVEL_LIST: "CAS_COMMITTEE_LEVEL_LIST_data",
    //   CAS_COMMITTEE_USER_LIST:'CAS_COMMITTEE_USER_LIST_data',
    CAS_SECURITY_DOCUMENT_SUBMIT_DIV: "CAS_SECURITY_DOCUMENT_SUBMIT_DIV_data",
    CAS_SECURITY_DOCUMENT_SUBMIT_WORK_CLASS:
      "CAS_SECURITY_DOCUMENT_SUBMIT_WORK_CLASS_data",
    CAS_BCC_ENTERER_WORK_CLASS: "CAS_BCC_ENTERER_WORK_CLASS_data",
    CAS_BCC_AUTHORIZER_WORK_CLASS: "CAS_BCC_AUTHORIZER_WORK_CLASS_data",
    CAS_BCC_INQUIRER_WORK_CLASS: "CAS_BCC_INQUIRER_WORK_CLASS_data",
  };

  public static systemParameterTypes = {
    B: "Boolean",
    D: "Decimal",
    S: "String",
  };

  public static systemParamKey = {
    ACTIVE_DIRECTORY_ENABLED: "CAS_1",
    UPM_ENABLED: "CAS_2",
    TINYMCE_ENABLED: "CAS_3",
    FACILITY_OUTSTANDING_AMOUNT_VALIDATION_ENABLED: "CAS_4",
    BRANCH_LEVEL_UPM_GROUP_CODE_VALUES: "CAS_5",
    IS_CUSTOMER_DIRECT_SEARCH_FROM_BANK: "CAS_6",
    IS_TOTAL_CHILD_FACILITY_AMOUNT_VALIDATION_IS_ENABLED: "CAS_7",
    BRANCH_SOL_ID_LIMIT: "CAS_8",
    DEFAULT_APPLICATION_FORM_FORWARDING_UPM_GROUPS: "CAS_9",
    DIV_CODE_IGNORED_UPM_GROUPS: "CAS_11",
  };

  public static statusOptionsSelect = [
    { value: null, label: "All" },
    { value: "ACT", label: "Active" },
    { value: "INA", label: "Inactive" },
  ];

  public static approveStatusOptionsSelect = [
    { value: "APPROVED", label: "Approved" },
    { value: "PENDING", label: "Pending" },
    { value: "REJECTED", label: "Rejected" },
  ];

  public static customerIdentificationType = {
    NIC: "NIC",
    BRC: "BRC",
    BRN: "BRN",
    DL: "Driving License",
    PP: "Passport",
  };

  public static addressType = {
    COMMUNICATION: "Communication",
    PERMANENT: "Permanent",
    PERMENENT: "Permanent",
    RESIDENTIAL: "Residential",
    OTHER: "Other",
  };

  public static addressTypeConst = {
    COMMUNICATION: "COMMUNICATION",
    PERMANENT: "PERMANENT",
    PERMENENT: "PERMENENT",
    RESIDENTIAL: "RESIDENTIAL",
    OTHER: "OTHER",
  };

  public static contactNumberType = {
    MOBILE: "Mobile",
    LAND: "Land",
  };

  public static contactNumberTypeConst = {
    MOBILE: "MOBILE",
    LAND: "LAND",
  };

  public static customerIdentificationTypeConst = {
    NIC: "NIC",
    BRC: "BRC",
    PP: "PP",
    OTHER: "OTHER",
  };

  public static identificationTypeConst = {
    NEW_NIC: "NEW_NIC",
    OLD_NIC: "OLD_NIC",
    BRC: "BRC",
    PP: "PP",
    OTHER: "OTHER",
  };

  public static facilityPaperStatusToAuthorityLevel = {
    IN_PROGRESS: "In Progress",
    REJECTED: "Decline",
    APPROVED: "Approved",
    DRAFT: "Draft",
    // PENDING: 'Pending',
    // REMOVED: 'Removed',
    CANCEL: "Returned",
    RECOMMENDED: "Recommended",
    // REJECTED: 'Rejected',
    SUBMITTED: "Submitted",
    DECLINED: "Declined",
    COMMENTED: "Commented",
  };

  public static facilityPaperStatusConst = {
    IN_PROGRESS: "IN_PROGRESS",
    REJECTED: "REJECTED",
    APPROVED: "APPROVED",
    DRAFT: "DRAFT",
    // PENDING: 'PENDING',
    // REMOVED: 'REMOVED',
    CANCEL: "CANCEL",
  };

  public static facilityPaperStatusChangeHeading = {
    IN_PROGRESS: "Forward",
    CANCEL: "Return",
  };

  public static facilityPaperStatus_ = {
    IN_PROGRESS: "In Progress",
    REJECTED: "Rejected",
    APPROVED: "Approved",
    DRAFT: "Draft",
    // PENDING: 'Pending',
    // REMOVED: 'Removed',
    CANCEL: "Cancel",
  };

  public static facilityPaperDashboardStatus = {
    IN_PROGRESS: "In Progress",
    REJECTED: "Declined",
    APPROVED: "Approved",
    DRAFT: "Inbox",
    // PENDING: 'Pending',
    // REMOVED: 'Removed',
    CANCEL: "Returned",
  };

  public static bccFacilityPaperStatusConst = {
    IN_PROGRESS: "IN_PROGRESS",
    APPROVED: "APPROVED",
  };

  public static dateRange = {
    THIS_MONTH: "This Month",
    LAST_30_DAYS: "Last 30 Days",
    CUSTOM: "Custom Date Range",
  };

  public static dateRangeConst = {
    THIS_MONTH: "THIS_MONTH",
    LAST_30_DAYS: "LAST_30_DAYS",
    CUSTOM: "CUSTOM",
  };

  public static facilityRoutingStatus = {
    REASSIGN: "Re Assign",
    RECOMMENDED: "Recommended",
    CLOSED: "Closed",
    BACK: "Back",
    NEXT: "Next",
  };

  public static facilityRoutingStatusConst = {
    REASSIGN: "REASSIGN",
    RECOMMENDED: "RECOMMENDED",
    CLOSED: "CLOSED",
    BACK: "BACK",
    NEXT: "NEXT",
  };

  public static facilityReturnType = {
    ADVANCE: "ADVANCE",
    DIRECT: "DIRECT",
  };

  public static applicationFormForwardType = {
    DIRECT: "DIRECT",
    CLUSTER: "CLUSTER",
  };

  public static ForwardType = {
    DIRECT_USER: "Direct User",
    SAME_SOL_USER_GROUP: "User Group",
    OTHER_SOL_USER_GROUP: "Other Branch/Department",
  };

  public static ForwardTypeConst = {
    DIRECT_USER: "DIRECT_USER",
    SAME_SOL_USER_GROUP: "SAME_SOL_USER_GROUP",
    OTHER_SOL_USER_GROUP: "OTHER_SOL_USER_GROUP",
  };

  public static remarkStatus = {
    IN_PROGRESS: "In Progress",
    REJECTED: "Rejected",
    APPROVED: "Approved",
    REASSIGN: "Re Assign",
    RECOMMENDED: "Recommended",
    CLOSED: "Closed",
    BACK: "Back",
    NEXT: "Next",
  };

  public static customerIdentificationTypeOptionsSelect = [
    { value: "NIC", label: "National Identity Card" },
    // {value: 'DL', label: 'Driving License'},
    { value: "BRC", label: "Business Registration Number" },
    { value: "PP", label: "Passport" },
  ];

  public static readonly identificationTypeOptionsSelect = [
    { value: "OLD_NIC", label: "Old National Identity Card" },
    { value: "NEW_NIC", label: "New National Identity Card" },
    { value: "PP", label: "Passport" },
    { value: "BRC", label: "Business Registration Number" },
    { value: "OTHER", label: "Other" },
  ];

  public static readonly leadCompPersoanlIdSelect = [
    { value: "OLD_NIC", label: "Old National Identity Card" },
    { value: "NEW_NIC", label: "New National Identity Card" },
  ];

  public static readonly leadCompBusinessIdSelect = [
    { value: "BRC", label: "Business Registration Number" },
  ];

  public static leadCompConst = {
    OLD_NIC: "OLD_NIC",
    NEW_NIC: "NEW_NIC",
    BRC: "BRC",
  };

  public static customerCribIdentificationTypeOptionsSelect = [
    { value: "NIC", label: "National Identity Card" },
    { value: "BRC", label: "Business Registration Number" },
  ];

  public static dateRangeOptionsSelect = [
    {
      value: Constants.dateRangeConst.THIS_MONTH,
      label: Constants.dateRange.THIS_MONTH,
    },
    {
      value: Constants.dateRangeConst.LAST_30_DAYS,
      label: Constants.dateRange.LAST_30_DAYS,
    },
    {
      value: Constants.dateRangeConst.CUSTOM,
      label: Constants.dateRange.CUSTOM,
    },
  ];

  public static facilityRepaymentSelect = [
    { value: "EMI", label: "EMI" },
    { value: "RB", label: "Reducing Balance" },
    { value: "BP", label: "Bullet Payment" },
  ];

  public static paymentFrequencySelect = [
    { value: "MONTHLY", label: "Monthly" },
    { value: "SEMI_MONTHLY", label: "Semi Monthly" },
    { value: "BI_WEEKLY", label: "Bi-Weekly" },
    { value: "WEEKLY", label: "Weekly" },
    { value: "DAILY", label: "Daily" },
    { value: "BI_MONTHLY", label: "Bi-Monthly" },
    { value: "QUARTERLY", label: "Quarterly" },
    { value: "SEMI_ANNUAL", label: "Semi Annual" },
    { value: "ANNUAL", label: "Annual" },
  ];

  public static addressSelect = [
    { value: "COMMUNICATION", label: "Communication" },
    { value: "PERMANENT", label: "Permanent" },
    { value: "RESIDENTIAL", label: "Residential" },
  ];

  public static webAuditMainCategory = {
    DOCUMENT: "Document",
    LEAD: "Lead",
    MASTER_DATA: "Master Data",
    FACILITY_PAPER: "Facility Paper",
    FACILITY: "Facility",
    OTHER: "Other",
  };

  public static webAuditSubCategory = {
    ROLE_ADD: "Role Add",
    ROLE_EDIT: "Role Edit",
    SUPPORTING_DOC_ADD: "Add Supporting Document",
    SUPPORTING_DOC_EDIT: "Edit Supporting Document",
    LEAD_ADD: "Add Lead",
    LEAD_EDIT: "Edit Lead",
    LEAD_DOC_ADD: "Edit Lead Document",
    LEAD_DOC_EDIT: "Edit Lead Document",
    LEAD_STATUS_EDIT: "Edit Lead Status",
    DOC_STORAGE_ADD: "Add Documents Storage",
    DOC_STORAGE_EDIT: "Edit Documents Storage",
  };

  public static ratingModel = {
    LARGE_CORPORATE: "Large Corporate (LC)",
    MIDDLE_CORPORATE: "Middle Corporate (MC)",
    SMALL_AND_MEDIUM_ENTERPRISES: "Small and Medium Enterprises (SME)",
    FINANCIAL_INSTITUTION: "Financial Institutions (FI) – (Banks/ NBFC)",
    PROJECT_FINANCE: "Project Finance (SPF) – (SPF/ RE)",
    PERSONAL_ADVANCES_RISK_RATING_SCORE_CARD:
      "Personal Advances Risk Rating Score Card (PA)",
    CASH_MARGIN: "Cash Margin ( CM )",
    STAFF_LOANS: "Staff Loans (SL)",
    CREDIT_CARDS_RATING: "Credit Cards rating (CC)",
  };

  public static webAuditMainCategoryOptionSelect = [
    { value: "LEAD", label: "Lead" },
    { value: "FACILITY_PAPER", label: "Facility Paper" },
    { value: "FACILITY", label: "Facility" },
    { value: "CUSTOMER", label: "Customer" },
    { value: "SUPPORTING_DOC", label: "Supporting Document" },
    { value: "CREDIT_FACILITY_GROUP", label: "Credit Facility Group" },
    { value: "USER_DA", label: "User Da" },
    { value: "CREDIT_FACILITY_TEMPLATE", label: "Credit Facility Template" },
    { value: "UPM_GROUP", label: "Upm Group" },
    { value: "WORK_FLOW_TEMPLATE", label: "Work Flow Template" },
    { value: "UPC_SECTION", label: "Upc Section" },
    { value: "UPC_TEMPLATE", label: "Upc Template" },
    { value: "CFT_INTEREST_RATE", label: "Cft Interest Rate" },
    { value: "CFT_SUPPORTING_DOC", label: "Cft Supporting Document" },
  ];

  //TODO need to refactor this
  //TODO label should be user readable
  public static webAuditSubCategoryOptionSelect = [
    { value: "SUPPORTING_DOC_ADD", label: "Add Supporting Document" },
    { value: "SUPPORTING_DOC_EDIT", label: "Edit Supporting Document" },
    { value: "LEAD_ADD", label: "Add Lead" },
    { value: "LEAD_EDIT", label: "Edit Lead" },
    { value: "LEAD_DOC_ADD", label: "Add Lead Document" },
    { value: "LEAD_DOC_EDIT", label: "Edit Lead Document" },
    { value: "LEAD_STATUS_EDIT", label: "Edit Lead Status" },
    { value: "DOC_STORAGE_ADD", label: "Add Documents Storage" },
    { value: "DOC_STORAGE_EDIT", label: "Edit Documents Storage" },
  ];

  public static leadSubCategotyoptionSelect = [
    { value: "LEAD_ADD", label: "Add Lead" },
    { value: "LEAD_EDIT", label: "Edit Lead" },
    { value: "LEAD_DOC_ADD", label: "Add Lead Document" },
    { value: "LEAD_DOC_EDIT", label: "Edit Lead Document" },
    { value: "LEAD_STATUS_EDIT", label: "Edit Lead Status" },
  ];

  public static facilityPaperSubCategorySelect = [
    { value: "FACILITY_PAPER_ADD", label: "Add Facility Paper" },
    { value: "FACILITY_PAPER_EDIT", label: "Edit Facility Paper" },
    { value: "FACILITY_PAPER_DOC_ADD", label: "Add Facility Paper document" },
    { value: "FACILITY_PAPER_DOC_EDIT", label: "Edit Facility Paper document" },
    {
      value: "FACILITY_PAPER_CUSTOMER_DOC_ADD",
      label: "Add Facility Paper customer document",
    },
    {
      value: "FACILITY_PAPER_CUSTOMER_DOC_EDIT",
      label: "Edit Facility Paper customer document",
    },
    {
      value: "FACILITY_PAPER_CRIB_DETAIL_ADD",
      label: "Add Facility Paper customer crib detail",
    },
    {
      value: "FACILITY_PAPER_CRIB_DETAIL_EDIT",
      label: "Edit Facility Paper customer crib detail",
    },
    {
      value: "FACILITY_PAPER_DIRECTOR_DETAIL_ADD",
      label: "Edit Facility Paper document",
    },
    {
      value: "FACILITY_PAPER_CUSTOMER_DOC_ADD",
      label: "Add Facility Paper director detail",
    },
    {
      value: "FACILITY_PAPER_DIRECTOR_DETAIL_EDIT",
      label: "Edit Facility Paper customer crib detail",
    },
    {
      value: "FACILITY_PAPER_COMMENT_ADD",
      label: "Add Facility Paper comment",
    },
    {
      value: "FACILITY_PAPER_COMMENT_EDIT",
      label: "Edit Facility Paper comment",
    },
    {
      value: "FACILITY_PAPER_OTHER_BANK_FACILITY_ADD",
      label: "Add Facility Paper other bank facility",
    },
    {
      value: "FACILITY_PAPER_OTHER_BANK_FACILITY_EDIT",
      label: "Edit Facility Paper other bank facility",
    },
    {
      value: "FACILITY_PAPER_CUSTOMER_ADD",
      label: "Add Facility Paper customer",
    },
    {
      value: "FACILITY_PAPER_CUSTOMER_EDIT",
      label: "Edit Facility Paper customer",
    },
    {
      value: "FACILITY_PAPER_UPC_SECTION_DATA_ADD",
      label: "Add Facility Paper Upc section data",
    },
    {
      value: "FACILITY_PAPER_UPC_SECTION_DATA_EDIT",
      label: "Edit Facility Paper Upc section data",
    },
    { value: "REPLICATE_FACILITY_PAPER", label: "Replicate Facility Paper" },
  ];

  public static customerSubCategorySelect = [];

  public static supportingDocSubCategorySelect = [
    { value: "SUPPORTING_DOC_ADD", label: "Add Supporting Document " },
    { value: "SUPPORTING_DOC_EDIT", label: "Edit Supporting Document" },
  ];

  public static creditFacilityGroupSubCategorySelect = [
    { value: "CREDIT_FACILITY_GROUP_ADD", label: "Add Credit Facility Type " },
    { value: "CREDIT_FACILITY_GROUP_EDIT", label: "Edit Credit Facility Type" },
  ];

  public static userDaSubCategorySelect = [
    { value: "USER_DA_ADD", label: "Add User Delegated Authority" },
    { value: "USER_DA_EDIT", label: "Edit User Delegated Authority" },
  ];

  public static creditFacilityTemplateSubCategorySelect = [
    { value: "CREDIT_FACILITY_TYPE_ADD", label: "Add Credit Facility Type" },
    { value: "CREDIT_FACILITY_TYPE_EDIT", label: "Edit Credit Facility Type" },
  ];

  public static creditFacilityTemplatesApproveStatusConst = {
    PENDING: "PENDING",
    APPROVED: "APPROVED",
    REJECTED: "REJECTED",
  };

  public static upmGroupSubCategorySelect = [
    { value: "UPM_GROUP_ADD", label: "Add Upm Group" },
    { value: "UPM_GROUP_EDIT", label: "Edit Upm Group" },
  ];

  public static workFlowSubCategorySelect = [
    { value: "WORK_FLOW_TEMPLATE_ADD", label: "Add Work Flow Template" },
    { value: "WORK_FLOW_TEMPLATE_EDIT", label: "Edit Work Flow Template" },
  ];

  public static upcSectionSubCategorySelect = [
    { value: "UPC_SECTION_ADD", label: "Add Upc section" },
    { value: "UPC_SECTION_EDIT", label: "Edit Upc section" },
  ];

  public static upcTemplateSubCategorySelect = [
    { value: "UPC_TEMPLATE_ADD", label: "Add Upc Template" },
    { value: "UPC_TEMPLATE_EDIT", label: "Edit Upc Template" },
  ];

  public static cftInterestRateSubCategorySelect = [
    { value: "CFT_INTEREST_RATE_ADD", label: "Add CFT Interest Rate" },
    { value: "CFT_INTEREST_RATE_EDIT", label: "Edit CFT Interest Rate" },
  ];

  public static cftSupportingDocSubCategorySelect = [
    { value: "CFT_SUPPORTING_DOC_ADD", label: "Add CFT Supporting Doc" },
    { value: "CFT_SUPPORTING_DOC_EDIT", label: "Edit CFT Supporting Doc" },
  ];

  public static facilitySubCategorySelect = [
    { value: "FACILITY_ADD", label: "Add Facility" },
    { value: "FACILITY_EDIT", label: "Edit Facility" },
  ];

  public static committeePoolSubCategorySelect = [
    { value: "COMMITTEE_POOL_ADD", label: "Add Committee Pool " },
    { value: "COMMITTEE_POOL_EDIT", label: "Edit Committee Pool" },
  ];

  public static genderSelect = [
    { value: "MALE", label: "Male" },
    { value: "FEMALE", label: "Female" },
  ];

  public static inputFieldValueType = {
    TEXT: "Text",
    NUMBER: "Number",
    CURRENCY: "Currency",
    DATE: "Date",
    PERCENTAGE: "Percentage",
  };

  public static inputFieldValueTypeConst = {
    TEXT: "TEXT",
    NUMBER: "NUMBER",
    CURRENCY: "CURRENCY",
    DATE: "DATE",
    PERCENTAGE: "PERCENTAGE",
  };

  public static UserUPMGroupValue = {
    MANAGER_BRANCH_DEPARTMENT: 50,
  };

  public static optionsSelectTitleOpt = [
    { value: Constants.titleConst.DR, label: Constants.title.DR },
    { value: Constants.titleConst.MR, label: Constants.title.MR },
    { value: Constants.titleConst.MRS, label: Constants.title.MRS },
    { value: Constants.titleConst.MS, label: Constants.title.MS },
  ];

  public static optionsCivilStatusSelectOpt: any = [
    { value: "", label: "" },
    {
      value: Constants.civilStatusConst.MARRIED,
      label: Constants.civilStatus.MARRIED,
    },
    {
      value: Constants.civilStatusConst.SINGLE,
      label: Constants.civilStatus.SINGLE,
    },
    {
      value: Constants.civilStatusConst.UNKNOWN,
      label: Constants.civilStatus.UNKNOWN,
    },
  ];

  public static optionsEmploymentSelectOpt: any = [
    { value: "", label: "" },
    {
      value: Constants.employStatusConst.EMPLOYED,
      label: Constants.employStatus.EMPLOYED,
    },
    {
      value: Constants.employStatusConst.SELF_EMPLOYED,
      label: Constants.employStatus.SELF_EMPLOYED,
    },
  ];

  public static optionsBusinessSelectConstitutionOpt: any = [
    {
      value: Constants.constitutionConst.PRIVATE_LTD,
      label: Constants.constitution.PRIVATE_LTD,
    },
    {
      value: Constants.constitutionConst.PARTNERSHIP,
      label: Constants.constitution.PARTNERSHIP,
    },
    {
      value: Constants.constitutionConst.PROPRIETORSHIP,
      label: Constants.constitution.PROPRIETORSHIP,
    },
    {
      value: Constants.constitutionConst.OTHER,
      label: Constants.constitution.OTHER,
    },
  ];

  public static optionsCorporateSelectConstitutionOpt: any = [
    {
      value: Constants.constitutionConst.PUBLIC_LTD,
      label: Constants.constitution.PUBLIC_LTD,
    },
    {
      value: Constants.constitutionConst.CORPORATION,
      label: Constants.constitution.CORPORATION,
    },
    {
      value: Constants.constitutionConst.OTHER,
      label: Constants.constitution.OTHER,
    },
  ];

  public static optionsFacilityPaperStatusSelectOpt: any = [
    {
      value: Constants.facilityPaperStatusConst.IN_PROGRESS,
      label: Constants.facilityPaperStatusToAuthorityLevel.IN_PROGRESS,
    },
    {
      value: Constants.facilityPaperStatusConst.REJECTED,
      label: Constants.facilityPaperStatusToAuthorityLevel.REJECTED,
    },
    {
      value: Constants.facilityPaperStatusConst.APPROVED,
      label: Constants.facilityPaperStatusToAuthorityLevel.APPROVED,
    },
    {
      value: Constants.facilityPaperStatusConst.DRAFT,
      label: Constants.facilityPaperStatusToAuthorityLevel.DRAFT,
    },
    {
      value: Constants.facilityPaperStatusConst.CANCEL,
      label: Constants.facilityPaperStatusToAuthorityLevel.CANCEL,
    },
  ];

  public static optionsInputFieldValueTypeSelectOpt: any = [
    {
      value: Constants.inputFieldValueTypeConst.NUMBER,
      label: Constants.inputFieldValueType.NUMBER,
    },
    {
      value: Constants.inputFieldValueTypeConst.CURRENCY,
      label: Constants.inputFieldValueType.CURRENCY,
    },
    {
      value: Constants.inputFieldValueTypeConst.PERCENTAGE,
      label: Constants.inputFieldValueType.PERCENTAGE,
    },
    {
      value: Constants.inputFieldValueTypeConst.TEXT,
      label: Constants.inputFieldValueType.TEXT,
    },
    {
      value: Constants.inputFieldValueTypeConst.DATE,
      label: Constants.inputFieldValueType.DATE,
    },
  ];

  public static unlimitedDALevelsUser: any = ["80"];

  public static keyCodes = {
    TAB: 9,
    ENTER: 13,
    ARROW_UP: 38,
    ARROW_DOWN: 40,
    ARROW_RIGHT: 39,
    ARROW_LEFT: 37,
  };

  public static interestRateValueConst = {
    EFFECTIVE_RATE: "EFFECTIVE",
    REDUCING_RATE: "REDUCING",
    FLAT_RATE: "FLAT",
    OTHER_RATE: "OTHER",
  };

  public static interestRateLabelConst = {
    EFFECTIVE_RATE: "Effective Rate",
    REDUCING_RATE: "Reducing Rate",
    FLAT_RATE: "Flat Rate",
    OTHER_RATE: "Other Rate",
  };

  public static esbCreditCalculatorResponseStatusConst = {
    SUCCESS: "SUCCESS",
    FAILED: "Failed",
  };

  public static leadDashboardStatusConst = {
    INBOX: "INBOX",
    IN_PROGRESS: "IN_PROGRESS",
    ACCEPTED: "ACCEPTED",
    DECLINED: "DECLINED",
    RETURNED: "RETURNED",
    PAPER_APPROVED: "PAPER_APPROVED",
  };

  public static leadDashboardStatus = {
    INBOX: "Inbox",
    IN_PROGRESS: "In Progress",
    ACCEPTED: "Accepted",
    DECLINED: "Declined",
    RETURNED: "Returned",
    PAPER_APPROVED: "Paper Approved",
  };

  public static leadInboxStatusConst = {
    DRAFT: "DRAFT",
    RETURNED_TO_ME: "RETURNED_TO_ME",
    RECEIVED_TO_ME: "RECEIVED_TO_ME",
  };

  public static leadInProgressStatusConst = {
    IN_PROGRESS: "IN_PROGRESS",
  };

  public static leadDeclinedStatusConst = {
    DECLINED_BY_ME: "DECLINED_BY_ME",
    DECLINED_BY_OTHERS: "DECLINED_BY_OTHERS",
  };

  public static leadReturnedStatusConst = {
    RETURNED_BY_ME: "RETURNED_BY_ME",
    RETURNED_BY_OTHERS: "RETURNED_BY_OTHERS",
  };

  public static leadAcceptedStatusConst = {
    APPLICATION_CREATED: "APPLICATION_CREATED",
    APPLICATION_RETURNED: "APPLICATION_RETURNED",
    APPLICATION_DECLINED: "APPLICATION_DECLINED",
    PAPER_CREATED: "PAPER_CREATED",
    PAPER_RETURNED: "PAPER_RETURNED",
    PAPER_DECLINED: "PAPER_DECLINED",
  };

  public static applicationFormInboxStatusConst = {
    DRAFT: "DRAFT",
    RETURNED_TO_ME: "RETURNED_TO_ME",
    RECEIVED_TO_ME: "RECEIVED_TO_ME",
  };

  public static applicationFormInProgressStatusConst = {
    IN_PROGRESS: "IN_PROGRESS",
  };

  public static applicationFormDeclinedStatusConst = {
    DECLINED_BY_ME: "DECLINED_BY_ME",
    DECLINED_BY_OTHERS: "DECLINED_BY_OTHERS",
  };

  public static applicationFormReturnedStatusConst = {
    RETURNED_BY_ME: "RETURNED_BY_ME",
    RETURNED_BY_OTHERS: "RETURNED_BY_OTHERS",
  };

  public static applicationFormAcceptedStatusConst = {
    PAPER_CREATED: "PAPER_CREATED",
    PAPER_RETURNED: "PAPER_RETURNED",
    PAPER_DECLINED: "PAPER_DECLINED",
  };

  public static applicationFormDashboardStatusConst = {
    INBOX: "INBOX",
    IN_PROGRESS: "IN_PROGRESS",
    ACCEPTED: "ACCEPTED",
    DECLINED: "DECLINED",
    RETURNED: "RETURNED",
    PAPER_APPROVED: "PAPER_APPROVED",
  };

  public static applicationFormDashboardStatus = {
    INBOX: "Inbox",
    IN_PROGRESS: "In Progress",
    ACCEPTED: "Accepted",
    DECLINED: "Declined",
    RETURNED: "Returned",
    PAPER_APPROVED: "Paper Approved",
  };

  public static committeePaperStatus = {
    FORWARDED: "Forwarded",
    RECOMMENDED: "Recommended",
    RETURNED: "Returned",
    COMMITTEE_APPROVED: "Committee Approved",
    DECLINED: "Declined",
    COMMENTED: "Commented",
  };

  public static committeePaperStatusConst = {
    FORWARDED: "FORWARDED",
    RECOMMENDED: "RECOMMENDED",
    RETURNED: "RETURNED",
    COMMITTEE_APPROVED: "COMMITTEE_APPROVED",
    DECLINED: "DECLINED",
    COMMENTED: "COMMENTED",
  };

  public static committeePaperDashboardStatusConst = {
    INBOX: "INBOX",
    IN_PROGRESS: "IN_PROGRESS",
    RETURNED: "RETURNED",
    COMMITTEE_APPROVED: "COMMITTEE_APPROVED",
    DECLINED: "DECLINED",
  };

  public static committeePaperDashboardStatus = {
    INBOX: "Inbox",
    IN_PROGRESS: "In Progress",
    RETURNED: "Returned",
    COMMITTEE_APPROVED: "Approved",
    DECLINED: "Declined",
  };

  public static bccPaperStatus = {
    APPROVED: "Approved",
    DECLINED: "Declined",
  };

  public static bccPaperStatusConst = {
    APPROVED: "APPROVED",
    DECLINED: "DECLINED",
  };

  public static bccWorkFlowStatus = {
    FORWARDED: "Forwarded",
    DRAFT: "Draft",
    PENDING: "Pending",
    APPROVED: "Approved",
    REJECTED: "Rejected",
    AUTH_PENDING_DOCS: "Authorization Pending Documents",
  };

  public static bccWorkFlowStatusConst = {
    FORWARDED: "FORWARDED",
    DRAFT: "DRAFT",
    PENDING: "PENDING",
    APPROVED: "APPROVED",
    REJECTED: "REJECTED",
    AUTH_PENDING_DOCS: "AUTH_PENDING_DOCS",
    REJECTED_DOCS: "REJECTED_DOCS",
  };

  public static bccPaperDashboardStatusConst = {
    INBOX: "INBOX",
    IN_PROGRESS: "IN_PROGRESS",
    APPROVED: "APPROVED",
    DECLINED: "DECLINED",
  };

  public static bccPaperDashboardStatus = {
    INBOX: "Inbox",
    IN_PROGRESS: "In Progress",
    APPROVED: "Approved",
    DECLINED: "Declined",
  };
  /* public static committeePaperInboxStatusConst = {
      DRAFT: 'DRAFT',
      RETURNED_TO_ME: 'RETURNED_TO_ME',
      RECEIVED_TO_ME: 'RECEIVED_TO_ME',
    };

    public static committeePaperInProgressStatusConst = {
      IN_PROGRESS: 'IN_PROGRESS',
    };*/

  public static applicationFormStatusConst = {
    DRAFT: "DRAFT",
    IN_PROGRESS: "IN_PROGRESS",
    RETURNED: "RETURNED",
    DECLINED: "DECLINED",
    PAPER_CREATED: "PAPER_CREATED",
  };

  public static applicationFormStatus = {
    DRAFT: "Draft",
    IN_PROGRESS: "In Progress",
    RETURNED: "Returned",
    DECLINED: "Declined",
    PAPER_CREATED: "Paper Created",
  };

  public static leadFsTypeConst = {
    LEASE: "LEASE",
    ONEOFF: "ONEOFF",
    TOD: "TOD",
    OTHER: "OTHER",
  };

  public static leadFsType = {
    LEASE: "Lease",
    ONEOFF: "One-off",
    TOD: "Temporary Overdraft",
    OTHER: "Other",
  };

  public static applicationFormFsTypeConst = {
    LEASE: "LEASE",
    ONEOFF: "ONEOFF",
    TOD: "TOD",
    OTHER: "OTHER",
  };

  public static applicationFormFsType = {
    LEASE: "Lease",
    ONEOFF: "One-off",
    TOD: "Temporary Overdraft",
    OTHER: "Other",
  };

  public static covenantFreaquencyTypes = [
    { value: "N", label: "No frequency" },
    { value: "D", label: "Daily" },
    { value: "W", label: "Weekly" },
    { value: "M", label: "Monthly" },
    { value: "F", label: "Fortnightly" },
    { value: "Q", label: "Quarterly" },
    { value: "H", label: "Half Yearly" },
    { value: "Y", label: "Yearly" },
  ];

  public static acaeStatusConst = {
    NEW: "NEW",
    DRAFT: "DRAFT",
    FORWARDED: "FORWARDED",
    FORWARDED_TO_ME: "FORWARDED_TO_ME",
    TO_BE_RESUBMIT: "TO_BE_RESUBMIT",
    RESUBMITED_TO_ME: "RESUBMITED_TO_ME",
    RETURNED: "RETURNED",
    RETURNED_TO_ME: "RETURNED_TO_ME",
    TRANSFERED_TO_ME: "TRANSFERED_TO_ME",
    APPROVED: "APPROVED",
  };

  public static acaeStatusLabel = {
    NEW: "Inbox",
    FORWARDED: "In Progress",
    APPROVED: "Approved",
    RETURNED: "Returned",
    TO_BE_RESUBMIT: "To Be Resubmit",
  };

  public static acaeStatusNo = {
    NEW: 0,
    DRAFT: 14,
    FORWARDED: 1,
    FORWARD_TO_ME: 2,
    RETURNED: 3,
    RETURN_TO_ME: 9,
    TO_BE_RESUBMIT: 7,
    RESUBMIT_TO_ME: 5,
    TRANSFER_TO_ME: 6,
    APPROVED: 4,
  };

  public static acaeCounts = {
    newCount: 0,
    returnCount: 0,
    approvedCount: 0,
    toBeResubmitCount: 0,
    forwardCount: 0,
    newACAECount: 0,
    rejectCount: 0,
    returnedCount: 0,
  };

  public static toastMessageTimeout = {
    SHORT: 3000,
    MEDIUM: 10000,
    LONG: 20000,
  };

  public static applicationSecurityWorkClass = {
    ENTERER: 10,
    SUPERVISOR: 20,
    MANAGER: 50,
    RM: 71,
    CM: 72,
    AGM: 73,
    DGM: 74,
    MD: 80,
    LO: 64, //Legal Officer
    SDE: 81, //BCC - Secretariate Enterer
    SDA: 82, //BCC - Secretariate Authorizer
  };

  public static escalateDates = [
    { value: "7", label: "7" },
    { value: "6", label: "6" },
    { value: "5", label: "5" },
    { value: "4", label: "4" },
    { value: "3", label: "3" },
    { value: "2", label: "2" },
    { value: "1", label: "1" },
  ];

  public static documentTypes = {
    CREDIT_RISK_DOCUMENT: 1,
  };

  public static conditionTypes = [
    { value: "Registered", label: "Registered" },
    { value: "Unregistered", label: "Unregistered" },
    { value: "Brand New", label: "Brand New" },
    { value: "Other", label: "Other" },
  ];

  public static countries = [
    { value: "Japan", label: "Japan" },
    { value: "China", label: "China" },
    { value: "Korea", label: "Korea" },
    { value: "Malaysia", label: "Malaysia" },
    { value: "India", label: "India" },
    { value: "Europe", label: "Europe" },
    { value: "Sri Lanka", label: "Sri Lanka" },
    { value: "Other", label: "Other" },
  ];

  public static vehicleTypes = [
    { value: "Passenger", label: "Passenger" },
    { value: "Light Trucks & Single Cab", label: "Light Trucks & Single Cab" },
    { value: "Commercial", label: "Commercial" },
    { value: "Other", label: "Other" },
  ];

  public static PeriodSinceDateOfRegistration = [
    { value: "Brand New/Unregistered", label: "Brand New/Unregistered" },
    {
      value: "Registered within 1st year of the 1st registration",
      label: "Registered within 1st year of the 1st registration",
    },
    {
      value: "Registered (after 01 year from the 1st registration)",
      label: "Registered (after 01 year from the 1st registration)",
    },
    { value: "Other", label: "Other" },
  ];

  public static committeePath = {
    REG: "Regular",
    ALT: "Alternative",
  };

  public static committeePathConst = {
    REG: "REG",
    ALT: "ALT",
  };

  public static committeeStatus = {
    DRAFT: "Draft",
    SUBMITTED: "Submitted",
    NEW: "New",
    UPDATE: "Update",
    DELETE: "Delete",
  };

  public static committeeStatusConst = {
    DRAFT: "DRAFT",
    SUBMITTED: "SUBMITTED",
    NEW: "NEW",
    UPDATE: "UPDATE",
    DELETE: "DELETE",
  };

  public static committeeSignatureUsers = {
    RB: 50,
    SNR_MGR: 71,
    RM: 50,
    CM: 72,
    AGM: 73,
    SNR_DGM: 74,
    MD: 80,
    LO: 64,
    GCRO: 71,
  };

  public static committeeSignatureDesignation = {
    RB: "RELATIONSHIP/BRANCH",
    SNR_MGR: "SNR.MGR",
    RM: "RELATIONSHIP MANAGER",
    CM: "CM",
    AGM: "AGM",
    SNR_DGM: "SNR.DGM",
    MD: "MANAGING DIRECTOR",
    LO: "LEGAL OFFICER",
    GCRO: "CHIEF RISK OFFICER",
  };

  public static fusTraceFlag = {
    FAC: "FAC",
    FACSE: "FACSE",
    FACCSE: "FACCSE",
    UPCT: "UPCT",
    UPCTALL: "UPCTALL",
    ALL: "ALL",
  };

  public static fusTraceStatus = {
    ACT: "ACT",
    INA: "INA",
  };

  public static templateSectionList = [
    {
      sectionName: "CUSTOMER PROFILE",
      headingCount: 3,
      lableCount: 75,
      tableCount: 8,
    },
    {
      sectionName: "WALLET SHARE",
      headingCount: 0,
      lableCount: 84,
      tableCount: 2,
    },
    {
      sectionName: "FINANCIAL HIGHLIGHTS",
      headingCount: 0,
      lableCount: 106,
      tableCount: 2,
    },
    {
      sectionName: "FACILITIES",
      headingCount: 0,
      lableCount: 30,
      tableCount: 3,
    },
    {
      sectionName: "Facility Utilization",
      headingCount: 0,
      lableCount: 20,
      tableCount: 1,
    },
    {
      sectionName: "Import / Export / Bank Guarantee Turnover",
      headingCount: 0,
      lableCount: 16,
      tableCount: 1,
    },
    {
      sectionName: "PROFITABILITY / GROSS INCOME",
      headingCount: 0,
      lableCount: 27,
      tableCount: 2,
    },
    {
      sectionName: "Analysis of Cash Flow",
      headingCount: 0,
      lableCount: 27,
      tableCount: 1,
    },
    {
      sectionName:
        "OPERATING & FINANCIAL RISKS / MITIGANTS AND INDUSTRY ANALYSIS",
      headingCount: 0,
      lableCount: 15,
      tableCount: 3,
    },
    {
      sectionName: "DATA ACCURACY",
      headingCount: 0,
      lableCount: 4,
      tableCount: 1,
    },
    {
      sectionName: "RECOMMENDATION",
      headingCount: 0,
      lableCount: 3,
      tableCount: 0,
    },
    { sectionName: "ANNEXES", headingCount: 0, lableCount: 12, tableCount: 1 },
    {
      sectionName: "Review Charges",
      headingCount: 0,
      lableCount: 4,
      tableCount: 1,
    },
  ];

  public static covCurrentStatusConst = {
    IN_PROGRESS: "IN_PROGRESS",
    REJECTED: "REJECTED",
    DRAFT: "DRAFT",
    RETURNED: "RETURNED",
    PAPER_CREATED: "PAPER_CREATED",
    APPROVED: "APPROVED",
    CANCEL: "CANCEL",
    DELETE: "DELETE",
  };
  public static covStatusChangeHeading = {
    IN_PROGRESS: "Forward",
    RETURNED: "Return",
    CANCEL: "Cancel",
  };
  public static covActionStatus = {
    IN_PROGRESS: "In Progress",
    DECLINED: "Decline",
    DRAFT: "Draft",
    RETURNED: "Return",
    PAPER_CREATED: "Create Paper",
  };
  public static coveringApprovalDashboardStatusConst = {
    INBOX: "INBOX",
    IN_PROGRESS: "IN_PROGRESS",
    APPROVED: "APPROVED",
    REJECTED: "REJECTED",
    CANCEL: "CANCEL",
    RETURN: "RETURN",
  };

  public static coveringApprovalDashboardStatus = {
    INBOX: "Inbox",
    IN_PROGRESS: "In Progress",
    APPROVED: "Approved",
    REJECTED: "Rejected",
    CANCEL: "Cancel",
    RETURN: "Return",
  };

  public static coveringApprovalInboxSubStatusConst = {
    DRAFT: "DRAFT",
    RETURNED_TO_ME: "RETURNED_TO_ME",
    RECEIVED_TO_ME: "RECEIVED_TO_ME",
  };
  public static coveringApprovalForwardedStatusConst = {
    FORWARDED: "FORWARDED",
    PAPER_APPROVED: "PAPER_APPROVED",
  };
  public static coveringApprovalCancelStatusConst = {
    RETURNED_BY_ME: "RETURNED_BY_ME",
    RETURNED_BY_OTHERS: "RETURNED_BY_OTHERS",
  };

  public static coveringApprovalReturnedStatusConst = {
    RETURNED: "RETURNED",
    NOT_APPROVED: "NOT_APPROVED",
  };

  public static componentFormCurrentStatusConst = {
    IN_PROGRESS: "IN_PROGRESS",
    DECLINED: "DECLINED",
    DRAFT: "DRAFT",
    RETURNED: "RETURNED",
    PAPER_CREATED: "PAPER_CREATED",
    DELETED: "DELETED",
  };
  public static coveringApprovalStatusConst = {
    PENDING: "PENDING",
    SUBMITTED: "SUBMITTED",
    APPROVED: "APPROVED",
    ACCEPTED: "ACCEPTED",
    RETURNED: "RETURNED",
    CLOSED: "CLOSED",
    DECLINED: "DECLINED",
    APPLICATION_CREATED: "APPLICATION_CREATED",
    PAPER_CREATED: "PAPER_CREATED",
    IN_PROGRESS: "IN_PROGRESS ",
    CANCEL: "CANCEL",
    DRAFT: "DRAFT",
    REJECTED: "REJECTED",
    RETURN: "RETURN",
  };
  public static coveringApprovalStatus = {
    PENDING: "Pending",
    SUBMITTED: "Submitted",
    APPROVED: "Approved",
    ACCEPTED: "Accepted",
    RETURNED: "Returned",
    CLOSED: "Closed",
    DECLINED: "Declined",
    APPLICATION_CREATED: "Application Created",
    APPLICATION_DECLINED: "Application Declined",
    PAPER_CREATED: "Paper Created",
  };

  public static coveringApprovalTrandetails = {
    reqID: "CAS_0001",
    prtsrlS: "1",
    prtsrlE: "2",
    schm_type: "ODA",
  };

  public static readonly customerType = {
    BUSINESS: "Business",
    PERSONAL: "Personal",
  };

  public static readonly customerTypeList: any[] = [
    { label: "Personal", value: "Personal" },
    { label: "Business", value: "Business" },
  ];

  public static riskRatingList = [
    {
      value: "A",
      label: "A",
    },
    {
      value: "A+",
      label: "A+",
    },
    {
      value: "A-",
      label: "A-",
    },
    {
      value: "B",
      label: "B",
    },
    {
      value: "B+",
      label: "B+",
    },
    {
      value: "B-",
      label: "B-",
    },
    {
      value: "C",
      label: "C",
    },
    {
      value: "C+",
      label: "C+",
    },
    {
      value: "C-",
      label: "C-",
    },
    {
      value: "D",
      label: "D",
    },
  ];

  public static interestRateCodeType = {
    AWPLR: "AWPLR",
    SOFR: "SOFR",
    FIXED: "FIXED",
    VARIABLE: "VARIABLE",
  };

  public static interestRateCodeList = [
    {
      parentType: "AWPLR",
      value: "AWPH",
      label: "AWPH",
    },
    {
      parentType: "AWPLR",
      value: "AWPL",
      label: "AWPL",
    },
    {
      parentType: "AWPLR",
      value: "AWPM",
      label: "AWPM",
    },
    {
      parentType: "AWPLR",
      value: "AWPW",
      label: "AWPW",
    },
    {
      parentType: "SOFR",
      value: "SOF1M",
      label: "SOF1M",
    },
    {
      parentType: "SOFR",
      value: "SOF3M",
      label: "SOF3M",
    },
    {
      parentType: "SOFR",
      value: "SOF6M",
      label: "SOF6M",
    },
    {
      parentType: "SOFR",
      value: "SOF1Y",
      label: "SOF1Y",
    },
  ];

  public static covenantComplianceTypes = [
    { value: "N", label: "Non Compliance" },
    { value: "Y", label: "Compliance" },
  ];

  public static recordStatusConst = {
    DRAFT: "DRAFT",
    SUBMITTED: "SUBMITTED",
    NEW: "NEW",
    UPDATE: "UPDATE",
    DELETE: "DELETE",
  };

  public static esgAnnexureTypeConst = {
    A1: "A1",
    A2: "A2",
    A3: "A3",
    A4: "A4",
    A5: "A5",
  };

  public static esgAnnexureType = {
    A1: "Annexure I",
    A2: "Annexure II",
    A3: "Annexure III",
    A4: "Annexure IV",
    A5: "Annexure V",
  };

  public static riskRecordType = {
    P: "Parent",
    C: "Child",
  };

  public static riskRecordTypeConst = {
    P: "P",
    C: "C",
  };

  public static annexStatusConst = {
    NEW: "NEW",
    UPDATE: "UPDATE",
    DELETE: "DELETE",
    DRAFT: "DRAFT",
    SUBMITTED: "SUBMITTED",
  };

  public static readonly answerType = {
    MCQ_WITH_SINGLE: "MCQ With Single Answer",
    MCQ_WITH_MULTIPLE: "MCQ With Multiple Answer",
    DESCRIPTIVE: "Descriptive Answer",
    DESCRIPTIVE_WITH_SUB_QUESTION: "Descriptive Answer With Sub Question",
    DESCRIPTIVE_WITH_MCQ_QUESTION: "Descriptive Answer With MCQ Question",
  };

  public static readonly answerTypeConst = {
    MCQ_WITH_SINGLE: "MCQ_WITH_SINGLE",
    MCQ_WITH_MULTIPLE: "MCQ_WITH_MULTIPLE",
    DESCRIPTIVE: "DESCRIPTIVE",
    DESCRIPTIVE_WITH_SUB_QUESTION: "DESCRIPTIVE_WITH_SUB_QUESTION",
    DESCRIPTIVE_WITH_MCQ_QUESTION: "DESCRIPTIVE_WITH_MCQ_QUESTION",
  };

  public static readonly esgRiskRatingList = [
    {
      value: "A",
      label: "A",
    },
    {
      value: "B",
      label: "B",
    },
    {
      value: "B+",
      label: "B+",
    },
    {
      value: "C",
      label: "C",
    },
  ];

  public static inquiryStatus = {
    REQ_DRAFTED: "REQ_DRAFTED",
    REQ_SUBMITTED: "REQ_SUBMITTED",
    RES_DRAFTED: "RES_DRAFTED",
    RES_SUBMITTED: "RES_SUBMITTED",
    RES_CONFIRMED: "RES_CONFIRMED",
    REQ_CLOSED: "REQ_CLOSED",
  };

  public static inquiryStatusConst = {
    REQ_DRAFTED: "INQUIRY DRAFTED",
    REQ_SUBMITTED: "INQUIRY SUBMITTED",
    RES_DRAFTED: "RESPONSE DRAFTED",
    RES_SUBMITTED: "RESPONSE SUBMITTED",
    RES_CONFIRMED: "RESPONSE CONFIRMED",
    REQ_CLOSED: "INQUIRY CLOSED",
  };

  public static readonly annexureNumberConst = {
    I: 1,
    II: 2,
    III: 3,
    IV: 4,
    V: 5,
    VI: 6,
    VII: 7,
    VIII: 8,
    IX: 9,
    X: 10,
    XI: 11,
    XII: 12,
    XIII: 13,
    XIV: 14,
    XV: 15,
  };

  public static readonly covenantApplicableType = {
    ABU: "Applicable for Business Unit",
    AC: "Applicable for Customer",
  };

  public static readonly covenantApplicableTypeConst = {
    ABU: "ABU",
    AC: "AC",
  };

  public static readonly AACustomerType = {
    ETB: "ETB",
    NTB: "NTB",
  };

  public static readonly leaseCribType = {
    INDIVIDUAL: "INDIVIDUAL",
    JOINT: "JOINT",
    SOLE_PROP: "SOLE_PROP",
    PARTNERSHIP: "PARTNERSHIP",
    LL: "LL",
  };
  public static readonly identificationTypes = {
    NEW_NIC: "NEW_NIC",
    OLD_NIC: "OLD_NIC",
    PP: "PP",
    BRC: "BRC",
    OTHER: "OTHER",
  };

  public static readonly identificationTypeDescription = {
    NEW_NIC: "New National Identity Card",
    OLD_NIC: "Old National Identity Card",
    PP: "Passport",
    BRC: "Business Registration Number",
    OTHER: "Other",
  };

  public static compTemplateSectionList = ["Background"];

  public static leadPuposeList = [
    { value: "INDIVIDUAL", label: "Individual" },
    { value: "INDIVIDUAL_JOINT", label: "Individual Joint" },
    { value: "SOUL_PROPRITER", label: "Sole Proprietor/Owner" },
    { value: "PARTNERSHIP", label: "Partnership" },
    { value: "LIMITED_LIABILITY", label: "Limited Liability" },
  ];

  public static readonly leadPuposeConst = {
    INDIVIDUAL: "INDIVIDUAL",
    INDIVIDUAL_JOINT: "INDIVIDUAL_JOINT",
    SOUL_PROPRITER: "SOUL_PROPRITER",
    PARTNERSHIP: "PARTNERSHIP",
    LIMITED_LIABILITY: "LIMITED_LIABILITY",
  };
}
