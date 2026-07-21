import { ShowMessageDto } from "../dto/show-message-dto";

export class SETTINGS {
  public static PUBLIC_KEY = "Rt5Wx%4d";
  public static ACCESS_TOKEN = "Eop_ubF";
  public static REFRESH_TOKEN = "Xer_09s";
  public static LOGGED_USER_ENC = "Zedij7&";
  public static LOGGED_AGENT_ENC = "XeFr5Yfv";
  public static USER_PRIVILEGES = "C4d4%rd";
  public static DATE_FORMAT = "DD/MM/YYYY";
  public static DATE_TIME_FORMAT = "DD/MM/YYYY HH:mm";

  public static BASE_IMAGE_URL =
    "https://s3.ap-southeast-1.amazonaws.com/cas-dev/";

  public static UPLOAD_IMAGE_DEFAULT_SIZE_MB = 5;

  public static UPLOAD_IMAGE_SIZE_LIMITS_MB = {
    xlsb: 10,
  };
  public static UPLOAD_IMAGE_ALLOWED_EXTENSIONS = [
    "jpg",
    "png",
    "jpeg",
    "docx",
    "pdf",
    "doc",
    "xlsx",
    "xlsb",
    "xls",
    "wps",
    "txt",
    "odt",
    "ods",
    "csv",
  ];
  public static TOKEN_EXPIRATION_DURATION_IN_MINUTES = 20;
  public static RISK_DIV_CODE = "874";
  public static ESG_DIV_CODE = 874;
  public static CCPU_DIV_CODE = "822";
  public static SALES_PERSON_WC = 9;

  public static TOASTER_MESSAGES = {
    success: "SUCCESS",
    error: "ERROR",
    warning: "WARNING",
    info: "INFO",
    custom: "CUSTOM",
  };

  public static PAGES = {
    home: "/my-facility-papers",
    customer360: "/customer-360",
    acaeDashboard: "/acae/dashboard",
    acaeSearch: "/acae/search",
    acaeStatusInquiry: "/acae/status-inquiry",
    acaeInquiryByDateRange: "/acae/inquiry-by-date-range",
    acaeMonitor: "/acae/monitor",
    acaeTrasnferOption: "/acae/transfer",
    roles: "/roles",
    userDelegatedAuthority: "/user-delegated-authorities",
    supportDocuments: "/support-documents",
    creditFacilityTypes: "/credit-facility-types",
    creditFacilityTypeTemplates: "/credit-facility-type-templates",
    upcTemplate: "/upc-template",
    upcSetions: "/section-sub-section",
    upmGroup: "/upm-group",
    workflowTemplate: "/workflow-template",
    leads: "/leads",
    myBranchLeads: "/my-branch-leads",
    audit: "/audit",
    facility: "/facility",
    facilityPaperTransfer: "/facility-paper-transfer",
    facilityPaper: "/facility-paper",
    myFacilityPapers: "/my-facility-papers",
    facilityReview: "/facility-review",
    bccReporting: "/bcc-reporting",
    agents: "/agents",
    applicationFormCreate: "/application-form/create",
    applicationFormCopy: "/application-form/copy",
    applicationFormInbox: "/application-form/inbox",
    applicationForms: "/application-forms",
    applicationFormTopicsConfig: "/application-form-topic/config",
    applicationFormTopics: "/application-form-topics",
    branchApplicationForms: "/application-form/branch",
    applicationFormTransfer: "/application-form-transfer",
    leadCreate: "/leads/create",
    leadComprehensiveCreate: "/leads/comprehensive-create",
    leadSearch: "/leads/search",
    leadDashboard: "/leads/dashboard",
    applicationFormDashboard: "/application-forms/dashboard",
    applicationFormSearch: "/application-form/search",
    facilityPaperSearch: "/facility-paper/search",
    committee: "/committee/all",
    committeePool: "/committee/pool",
    committeeType: "/committee/type",
    committeePaperDashboard: "/committee-paper/dashboard",
    da: "/da/da-table",
    daDesignation: "/da/da-designation",
    coveringApproval: "/covering-approval",
    casv1: "/cas-v1",
    casv1Paper: "/cas-v1/paper",
    environmentalRiskTool: "/environmental-risk-tool",
    environmentalRiskAnnexure: "/environmental-risk-annexure",
    diviationType: "/diviation/deviation-types",
    diviation: "/diviation",
  };

  public static PRIVILEGES = {
    ADMIN_USER_DELEGATED_AUTHORITY_APPROVE_OR_REJECT:
      "ADMIN.USER.DELEGATED.AUTHORITY.APPROVE.OR.REJECT",
    ADMIN_MASTER_DATA_APPROVE_OR_REJECT: "ADMIN.MASTER.DATA.APPROVE.OR.REJECT",
    ADMIN_CUSTOMER_PERSONAL_DETAIL_UPDATE:
      "ADMIN.CUSTOMER.PERSONAL.DETAIL.UPDATE",

    ICAS_SETTINGS_USER_DA_VIEW: "ICAS.SETTINGS.USER.DA.VIEW",
    ICAS_SETTINGS_USER_DA_ADD: "ICAS.SETTINGS.USER.DA.ADD",
    ICAS_SETTINGS_USER_DA_EDIT: "ICAS.SETTINGS.USER.DA.EDIT",

    ICAS_SETTINGS_ROLE_VIEW: "ICAS.SETTINGS.ROLE.VIEW",
    ICAS_SETTINGS_ROLE_ADD: "ICAS.SETTINGS.ROLE.ADD",
    ICAS_SETTINGS_ROLE_EDIT: "ICAS.SETTINGS.ROLE.EDIT",
    ICAS_WEB_AUDIT_VIEW: "ICAS.SETTINGS.WEB.AUDIT.VIEW",

    ICAS_SETTINGS_LEAD_VIEW: "ICAS.SETTINGS.LEAD.VIEW",
    ICAS_SETTINGS_MY_BRANCH_LEAD_VIEW: "ICAS.SETTINGS.LEAD.MY.BRANCH.VIEW",
    ICAS_SETTINGS_LEAD_CUSTOMER_VIEW: "ICAS.SETTINGS.LEAD.CUSTOMER.VIEW",
    ICAS_SETTINGS_LEAD_ADD: "ICAS.SETTINGS.LEAD.ADD",
    ICAS_SETTINGS_LEAD_COMPREHENSIVE_CREATE:
      "ICAS.SETTINGS.LEAD.COMPREHENSIVE.CREATE",
    ICAS_SETTINGS_LEAD_EDIT: "ICAS.SETTINGS.LEAD.EDIT",
    ICAS_SETTINGS_INTERNAL_LEAD_ACCEPT: "ICAS.SETTINGS.INTERNAL.LEAD.ACCEPT",
    ICAS_SETTINGS_INTERNAL_LEAD_DECLINE: "ICAS.SETTINGS.INTERNAL.LEAD.DECLINE",
    ICAS_SETTINGS_EXTERNAL_LEAD_APPROVE_DECLINE:
      "ICAS.SETTINGS.EXTERNAL.LEAD.APPROVE.DECLINE",

    ICAS_SETTINGS_FACILITY_PAPER_CREDIT_RISK_COMMENT_ADD:
      "ICAS.SETTINGS.FACILITY.PAPER.CREDIT.RISK.COMMENT.ADD",

    ICAS_SETTINGS_FACILITY_PAPER_VIEW: "ICAS.SETTINGS.FACILITY.PAPER.VIEW",
    ICAS_SETTINGS_FACILITY_PAPER_ADD: "ICAS.SETTINGS.FACILITY.PAPER.ADD",
    ICAS_SETTINGS_FACILITY_PAPER_EDIT: "ICAS.SETTINGS.FACILITY.PAPER.EDIT",
    ICAS_SETTINGS_FACILITY_PAPER_GENERATE_BCC_PAPER:
      "ICAS.SETTINGS.FACILITY.PAPER.GENERATE.BCC.PAPER",

    ICAS_SETTINGS_CREDIT_FACILITY_TYPE_VIEW:
      "ICAS.SETTINGS.CREDIT.FACILITY.TYPE.VIEW",
    ICAS_SETTINGS_CREDIT_FACILITY_TYPE_ADD:
      "ICAS.SETTINGS.CREDIT.FACILITY.TYPE.ADD",
    ICAS_SETTINGS_CREDIT_FACILITY_TYPE_EDIT:
      "ICAS.SETTINGS.CREDIT.FACILITY.TYPE.EDIT",

    ICAS_SETTINGS_CREDIT_FACILITY_TEMPLATE_VIEW:
      "ICAS.SETTINGS.CREDIT.FACILITY.TEMPLATE.VIEW",
    ICAS_SETTINGS_CREDIT_FACILITY_TEMPLATE_ADD:
      "ICAS.SETTINGS.CREDIT.FACILITY.TEMPLATE.ADD",
    ICAS_SETTINGS_CREDIT_FACILITY_TEMPLATE_EDIT:
      "ICAS.SETTINGS.CREDIT.FACILITY.TEMPLATE.EDIT",

    ICAS_SETTINGS_SUPPORTING_DOC_VIEW: "ICAS.SETTINGS.SUPPORTING.DOC.VIEW",
    ICAS_SETTINGS_SUPPORTING_DOC_ADD: "ICAS.SETTINGS.SUPPORTING.DOC.ADD",
    ICAS_SETTINGS_SUPPORTING_DOC_EDIT: "ICAS.SETTINGS.SUPPORTING.DOC.EDIT",

    ICAS_SETTINGS_UPC_SECTION_VIEW: "ICAS.SETTINGS.UPC.SECTION.VIEW",
    ICAS_SETTINGS_UPC_SECTION_ADD: "ICAS.SETTINGS.UPC.SECTION.ADD",
    ICAS_SETTINGS_UPC_SECTION_EDIT: "ICAS.SETTINGS.UPC.SECTION.EDIT",

    ICAS_SETTINGS_UPC_TEMPLATE_VIEW: "ICAS.SETTINGS.UPC.TEMPLATE.VIEW",
    ICAS_SETTINGS_UPC_TEMPLATE_ADD: "ICAS.SETTINGS.UPC.TEMPLATE.ADD",
    ICAS_SETTINGS_UPC_TEMPLATE_EDIT: "ICAS.SETTINGS.UPC.TEMPLATE.EDIT",

    ICAS_SETTINGS_UPM_GROUP_VIEW: "ICAS.SETTINGS.UPM.GROUP.VIEW",
    ICAS_SETTINGS_UPM_GROUP_ADD: "ICAS.SETTINGS.UPM.GROUP.ADD",
    ICAS_SETTINGS_UPM_GROUP_EDIT: "ICAS.SETTINGS.UPM.GROUP.EDIT",

    ICAS_SETTINGS_WORKFLOW_TEMPLATE_VIEW:
      "ICAS.SETTINGS.WORKFLOW.TEMPLATE.VIEW",
    ICAS_SETTINGS_WORKFLOW_TEMPLATE_ADD: "ICAS.SETTINGS.WORKFLOW.TEMPLATE.ADD",
    ICAS_SETTINGS_WORKFLOW_TEMPLATE_EDIT:
      "ICAS.SETTINGS.WORKFLOW.TEMPLATE.EDIT",

    ICAS_SETTINGS_AGENT_VIEW: "ICAS.SETTINGS.AGENT.VIEW",
    ICAS_SETTINGS_AGENT_ADD: "ICAS.SETTINGS.AGENT.ADD",
    ICAS_SETTINGS_AGENT_EDIT: "ICAS.SETTINGS.AGENT.EDIT",
    ICAS_SETTINGS_FACILITY_PAPER_COPY_ENABLED:
      "ICAS.SETTINGS.FACILITY.PAPER.COPY.ENABLED",

    ICAS_SETTINGS_PAPER_REVIEW_VIEW: "ICAS.SETTINGS.PAPER.REVIEW.VIEW",
    ICAS_SETTINGS_BCC_PAPER_VIEW: "ICAS.SETTINGS.BCC.PAPER.VIEW",
    ICAS_SETTINGS_BCC_PAPER_EDIT: "ICAS.SETTINGS.BCC.PAPER.EDIT",

    ICAS_SETTINGS_BRANCH_LEAD_ACCEPT: "ICAS.SETTINGS.BRANCH.LEAD.ACCEPT",
    ICAS_SETTINGS_BRANCH_LEAD_DECLINE: "ICAS.SETTINGS.BRANCH.LEAD.DECLINE",

    ICAS_SETTINGS_VIEW_FULL_PAPER_DEFAULT:
      "ICAS.SETTINGS.VIEW.FULL.PAPER.DEFAULT",
    ICAS_SETTINGS_CUSTOMER_360_VIEW: "ICAS.SETTINGS.CUSTOMER.360.VIEW",

    ICAS_SETTINGS_TRANSFER_FACILITY_PAPER:
      "ICAS.SETTINGS.TRANSFER.FACILITY.PAPER",
    ICAS_SETTINGS_TRANSFER_APPLICATION_FORM:
      "ICAS.SETTINGS.TRANSFER.APPLICATION.FORM",

    ICAS_SETTINGS_APPLICATION_TOPIC_VIEW:
      "ICAS.SETTINGS.APPLICATION.TOPIC.VIEW",
    ICAS_SETTINGS_APPLICATION_TOPIC_ADD: "ICAS.SETTINGS.APPLICATION.TOPIC.ADD",
    ICAS_SETTINGS_APPLICATION_TOPIC_EDIT:
      "ICAS.SETTINGS.APPLICATION.TOPIC.EDIT",

    ICAS_SETTINGS_APPLICATION_TOPIC_CONFIG_VIEW:
      "ICAS.SETTINGS.APPLICATION.TOPIC.CONFIG.VIEW",
    ICAS_SETTINGS_APPLICATION_TOPIC_CONFIG_EDIT:
      "ICAS.SETTINGS.APPLICATION.TOPIC.CONFIG.EDIT",

    ICAS_SETTINGS_APPLICATION_FORM_VIEW: "ICAS.SETTINGS.APPLICATION.FORM.VIEW",
    ICAS_SETTINGS_APPLICATION_FORM_EDIT: "ICAS.SETTINGS.APPLICATION.FORM.EDIT",
    ICAS_SETTINGS_APPLICATION_FORM_CREATE:
      "ICAS.SETTINGS.APPLICATION.FORM.CREATE",

    ICAS_SETTINGS_CUSTOMER_REFRESH: "ICAS.SETTINGS.CUSTOMER.REFRESH",
    ICAS_SETTINGS_APPLICATION_FROM_COPY_ENABLED:
      "ICAS.SETTINGS.APPLICATION.FORM.COPY.ENABLED",
    ICAS_SETTINGS_APPLICATION_FORM_SCRUTINIZER:
      "ICAS.SETTINGS.APPLICATION.FORM.SCRUTINIZER",
    ICAS_SETTINGS_FACILITY_PAPER_SCRUTINIZER:
      "ICAS.SETTINGS.FACILITY.PAPER.SCRUTINIZER",
    ICAS_SETTINGS_UPLOAD_SUPPORTING_DOCUMENTS:
      "ICAS.SETTINGS.UPLOAD.SUPPORTING.DOCUMENTS",
    ICAS_SETTINGS_OTHER_BRANCH_DEPARTMENT_CLUSTER_FORWARDING:
      "ICAS.SETTINGS.OTHER.BRANCH.DEPARTMENT.CLUSTER.FORWARDING",
    ICAS_SETTINGS_PAPER_PRINTING: "ICAS.SETTINGS.PAPER.PRINTING",
    ICAS_SETTINGS_APPROVE_FACILITY_PAPER:
      "ICAS.SETTINGS.APPROVE.FACILITY.PAPER",
    ICAS_SETTINGS_ACAE_VIEW: "ICAS.SETTINGS.ACAE.VIEW",
    ICAS_SETTINGS_ACAE_INQUIRY_BY_DATE_RANGE:
      "ICAS.SETTINGS.ACAE.INQUIRY.BY.DATE.RANGE",
    ICAS_SETTINGS_ACAE_SEARCH: "ICAS.SETTINGS.ACAE.SEARCH",
    ICAS_SETTINGS_ACAE_STATUS_INQUIRY: "ICAS.SETTINGS.ACAE.STATUS.INQUIRY",
    ICAS_SETTINGS_ACAE_TRANSFER_OPTION: "ICAS.SETTINGS.ACAE.TRANSFER.OPTION",

    ICAS_SETTINGS_COMMITTEE_VIEW: "ICAS.SETTINGS.COMMITTEE.VIEW",
    ICAS_SETTINGS_COMMITTEE_TYPE_VIEW: "ICAS.SETTINGS.COMMITTEE.TYPE.VIEW",
    ICAS_SETTINGS_COMMITTEE_POOL_VIEW: "ICAS.SETTINGS.COMMITTEE.POOL.VIEW",
    ICAS_SETTINGS_VIEW_COMMITTEE_PAPER: "ICAS.SETTINGS.VIEW.COMMITTEE.PAPER",
    ICAS_SETTINGS_VIEW_BCC_PAPER: "ICAS.SETTINGS.VIEW.BCC.PAPER",
    ICAS_SETTINGS_VIEW_HOME: "ICAS.SETTINGS.VIEW.HOME",
    ICAS_SETTINGS_USER_DATABLE_VIEW: "ICAS.SETTINGS.USER.DA.VIEW",
    ICAS_SETTINGS_COVERING_APPROVAL_CREATE:
      "ICAS.SETTINGS.COVERING.APPROVAL.CREATE",
    ICAS_SETTINGS_FACILITY_PAPER_UPC_EDIT:
      "ICAS.SETTINGS.FACILITY.PAPER.UPC.EDIT",
    ICAS_SETTINGS_ENVIROMENTAL_RISK_TOOL:
      "ICAS.SETTINGS.ENVIROMENTAL.RISK.TOOL",
    ICAS_SETTINGS_ENVIROMENTAL_RISK_ANNEXURE:
      "ICAS.SETTINGS.ENVIROMENTAL.RISK.ANNEXURE",
  };

  public static ASSISTANT_RESTRICTED_PRIVILEGES = [
    SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPROVE_FACILITY_PAPER,
  ];

  public static BRANCH_DEPARTMENT_LIST = {
    RISK_MANAGEMENT_AND_COMPLIENCE: "RISK.MANAGEMENT.AND.COMPLIENCE",
  };

  public static DEFAULT_VALUE = {
    listValue: 0,
  };

  public static KEYS = {
    PUBLIC_KEY:
      "-----BEGIN PUBLIC KEY-----\n" +
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpBBsjh/8fbZWPR870+LBZMPAs\n" +
      "mwedRN6urKpwqBItbKLtCpKC4UH9mR9cKYov/s5O7sp/U/0b0EDmWXbceeshwOCS\n" +
      "3mSkuOfA37YBE2DB8f6kXd6ceUjg8NseAXEy9UjkzMx+UYUWDfCK0tMhHoDlnBKl\n" +
      "Cljm3AA37P1UL8j0qwIDAQAB\n" +
      "-----END PUBLIC KEY-----",

    SECRET: "Tr33!",
  };

  public static readonly CUS_APPLICABLE_COUNT = 7;

  public static STORAGE = {
    SELECTED_ROLE_ID: "ZZxdGgO_",
    SELECTED_USER_ID: "HUuiubo0",
    SELECTED_SYSTEM_PARAM_ID: "ehdh736",
    SELECTED_USER_DA_ID: "SSrmia0",
    SELECTED_CREDIT_FACILITY_TYPE_ID: "SSgbhdrf2",
    SELECTED_CREDIT_FACILITY_TEMPLATE_ID: "SSheduih3",
    SELECTED_SUPPORTING_DOC_ID: "SSnhaa1",
    SELECTED_UPC_SECTION_ID: "#@asd13dgskfm",
    SELECTED_UPC_TEMPLATE_ID: "#%DSA23@#$m",
    SELECTED_UPM_GROUP_ID: "#%Dasd213@#$m",
    SELECTED_WORKFLOW_TEMPLATE_ID: "#%JKHu88@#$m",
    SELECTED_LEAD_ID: "SStrjdh)5",
    SELECTED_FACILITY_ID: "sshuhd73",
    SELECTED_FACILITY_PAPER_ID: "Tr%4dv*6h",
    SELECTED_TRANSFER_FACILITY_PAPER_ID: "Tr%-08998Uie",
    SELECTED_FP_CUSTOMER_ID: "ggduhdu883",
    SELECTED_AGENT_ID: "E_44#fGtbHGg",
    SELECTED_FP_ASSIGNED_USER_ID: "CAS_fps^!3#tuiE",
    SELECTED_FACILITY_PAPER_ID_FOR_REVIEW: "CAS_bsnh$34$Uie",
    SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING: "CAS_bs^7h%4$Uie",
    SELECTED_APPLICATION_FORM_ID: "ggSDWDDu883Uie",
    SELECTED_APPLICATION_FORM_TOPIC_ID: "I34fE$HT%64Uie",
    SELECTED_FINACLE_ID: "sshuhd74",
    SELECTED_CUSTOMER_ID: "sshuhd76",
    SELECTED_COMMITTEE_ID: "sshuhdcid",
    SELECTED_COMMITTEE_FETCH_TYPE: "sshuhdcidft",
    FACILITY_PAPER_BY_ID: "dhgHKS@#5GSJFHDwq",
    RISK_DIV_CODE: "HJ@#%DDHdsb^&v",
    SELECTED_COV_FORM_ID: "absjhjk",
    COMMENTS_KEY: "comments",
    SELECTED_COV_ID: "CaDasqy38",
    SELECTED_COV_FORM: "iweud%a",
    SELECTED_TRANSACTION_ID: "catrnid",
    SELECTED_TRAN_DATE: "catrndate",
    SELECTED_COMMITTEE_INQUIRY_TYPE: "ggduhdu883",
  };

  public static ENDPOINTS = {
    loadPublicKey: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: true,
      },
      url: "/cas/api/public_key",
      type: "GET",
    },

    login: {
      mockUrl: false,
      headerParam: {
        showLoading: true,
        showToast: true,
        excludeError: [401],
        skipAuth: true,
      },
      url: "/cas/login",
      type: "POST",
    },

    merchantLogin: {
      mockUrl: false,
      headerParam: {
        showLoading: true,
        showToast: true,
        excludeError: [401],
        skipAuth: true,
      },
      url: "/cas/merchant-login",
      type: "POST",
    },

    getSystemParams: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: true,
      },
      url: "/cas/api/home/system-params",
      type: "GET",
    },

    expireUserCache: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/security/log-out",
      type: "POST",
    },

    clearUserCache: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: true,
      },
      url: "/cas/api/security/clear-user-cache",
      type: "POST",
    },

    getUPMDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/security/getUPMDetails",
      type: "GET",
    },

    getUPMDetailsById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/security/getUPMDetailsById",
      type: "GET",
    },

    refreshToken: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/getToken",
      type: "POST",
    },

    getPagedRoles: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/role/getPagedRoles",
      type: "POST",
    },

    getRoleUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/role/getRoleUpdateDTO",
      type: "GET",
    },

    getSystemPrivileges: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/role/getSystemPrivileges",
      type: "GET",
    },

    getRoles: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/role/getRoles",
      type: "GET",
    },

    saveOrUpdateRole: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/role/saveOrUpdateRole",
      type: "POST",
    },

    getPagedUsers: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/user/getPagedUsers",
      type: "POST",
    },

    getUserUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/user/getUserUpdateDTO",
      type: "GET",
    },
    getMerchantUserUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/merchantUser/getMerchantUserUpdateDTO",
      type: "GET",
    },
    getDriverUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/driver/getDriverUpdateDTO",
      type: "GET",
    },

    saveOrUpdateUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/user/saveOrUpdateUser",
      type: "POST",
    },

    getConstants: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: true,
      },
      url: "/cas/api/home/getConstants",
      type: "GET",
    },

    getApplicationProperties: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: true,
      },
      url: "/cas/api/home/getApplicationProperties",
      type: "GET",
    },

    updateUserPassword: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/user/updateUserPassword",
      type: "POST",
    },

    uploadImageCommon: {
      headerParam: {
        showLoading: true,
        showToast: false,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/storage/uploadImage",
      type: "POST",
    },
    getPagedUserDAs: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/userDA/getPagedUserDA",
      type: "POST",
    },

    getUserDaUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/userDA/getUserDaUpdateDTO",
      type: "GET",
    },

    saveOrUpdateUserDa: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/userDA/saveOrUpdateUserDa",
      type: "POST",
    },

    getPagedSupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/supportDoc/getPagedSupportingDoc",
      type: "POST",
    },
    getSupportingDocUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/supportDoc/getSupportingDocUpdateDTO",
      type: "GET",
    },

    saveOrUpdateSupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/supportDoc/saveOrUpdateSupportingDoc",
      type: "POST",
    },

    getPagedCreditFacilityTypes: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/getPagedCreditFacilityTypes",
      type: "POST",
    },

    getCreditFacilityTypeUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/getCreditFacilityTypeUpdateDTO",
      type: "GET",
    },

    getAllCftInterestRateDTOS: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/getAllCftInterestRateDTOS",
      type: "GET",
    },

    getApprovedFacilityTypeList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/getApprovedFacilityTypeList",
      type: "POST",
    },

    saveOrUpdateCreditFacilityType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/saveOrUpdateCreditFacilityType",
      type: "POST",
    },
    getPagedCreditFacilityTemplates: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/getPagedCreditFacilityTemplates",
      type: "POST",
    },
    getCreditFacilityTemplateUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/getCreditFacilityTemplateUpdateDTO",
      type: "GET",
    },
    saveOrUpdateCreditFacilityTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/saveOrUpdateCreditFacilityTemplate",
      type: "POST",
    },
    getPagedUPCSections: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/upcSection/getPagedUPCSections",
      type: "POST",
    },

    getUPCSectionUpdateDTO: {
      headerParam: {
        skipAuth: false,
        showLoading: true,
        showToast: true,
      },
      url: "/cas/api/upcSection/getUPCSectionUpdateDTO",
      type: "GET",
    },

    saveOrUpdateUPCSection: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/upcSection/saveOrUpdateUPCSection",
      type: "POST",
    },
    getPagedUPCTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/upcTemplate/getPagedUPCTemplate",
      type: "POST",
    },
    getUPCTemplateUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/upcTemplate/getUPCTemplateUpdateDTO",
      type: "GET",
    },

    saveOrUpdateUPCTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/upcTemplate/saveOrUpdateUPCTemplate",
      type: "POST",
    },
    getPagedUPMGroup: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/workflow/getPagedUPMGroup",
      type: "POST",
    },
    getUPMGroupDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/workflow/getUPMGroupDTO",
      type: "GET",
    },
    saveOrUpdateUPMGroup: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/workflow/saveOrUpdateUPMGroup",
      type: "POST",
    },
    getPagedWorkflowTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/workflow/getPagedWorkflowTemplate",
      type: "POST",
    },
    getWorkflowTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/workflow/getWorkflowTemplate",
      type: "GET",
    },

    saveOrUpdateWorkflowTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/workflow/saveOrUpdateWorkflowTemplate",
      type: "POST",
    },

    getApprovedSupportingDocList: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/supportDoc/getApprovedSupportingDocList",
      type: "GET",
    },

    getApprovedGlobalSupportingDocList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/supportDoc/getApprovedGlobalSupportingDocList",
      type: "GET",
    },

    getPagedLeads: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/lead/getPagedLeadDetail",
      type: "POST",
    },

    getPagedFacilityPaperReviewSummary: {
      headerParam: {
        showLoading: true,
        showToast: true,
        // showMessage: (new ShowMessageDto().getDefaultSearch()),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedFacilityPaperReviewSummary",
      type: "POST",
    },

    getPagedFacilitiesForReview: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedFacilitiesForReview",
      type: "POST",
    },

    getLeadUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getLeadByID",
      type: "GET",
    },

    getLeadByRefNumber: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getLead",
      type: "GET",
    },

    saveOrUpdateLead: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/lead/saveOrUpdateLead",
      type: "POST",
    },
    getApprovedCreditFacilityTemplateList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getApprovedCreditFacilityTemplates",
      type: "GET",
    },
    saveUploadedSupportingDocuments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/lead/uploadLeadDocument",
      type: "POST",
    },
    getActiveCustomersList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getActiveCustomers",
      type: "GET",
    },

    getBranchPendingLeadCount: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/lead/getBranchPendingLeadCount",
      type: "POST",
    },
    updateLeadStatusOrAssignee: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/lead/updateLeadStatusOrAssignee",
      type: "POST",
    },
    getBranchList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: false,
      },
      url: "/cas/api/masterData/getBranchList",
      type: "POST",
    },
    getPagedAuditDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/audit/getPagedAuditDetails",
      type: "POST",
    },

    getFacilityDetailResponses: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getFacilityDetailResponses",
      type: "POST",
    },

    searchCustomerFrom360: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/searchCustomerFrom360",
      type: "POST",
    },
    getPagedFacilityPaperSummary: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getPagedFacilityPaperSummary",
      type: "POST",
    },
    getKalyptoDetail: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getKalyptoDetail",
      type: "POST",
    },
    getCustomerPagedLeadDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getCustomerPagedLeadDTO",
      type: "POST",
    },

    downloadDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isBlob: true,
        isFileDownload: true,
      },
      url: "/cas/api/storage/downloadDoc",
      type: "GET",
    },

    getFacilityTypes: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/getFacilityTypes",
      type: "GET",
    },

    approveOrRejectUserDa: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/userDA/approveOrRejectUserDa",
      type: "POST",
    },

    approveOrRejectCreditFacilityTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/approveOrRejectCreditFacilityTemplate",
      type: "POST",
    },

    approveOrRejectCreditFacilityType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/creditFacility/approveOrRejectCreditFacilityType",
      type: "POST",
    },
    approveOrRejectSupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/supportDoc/approveOrRejectSupportingDoc",
      type: "POST",
    },
    approveOrRejectRole: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/role/approveRoleDTO",
      type: "POST",
    },
    getAllApprovedUPMGroups: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/workflow/getAllApprovedUPMGroups",
      type: "GET",
    },

    getAllowApprovedUPMGroupsForLoginUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/workflow/getAllowApprovedUPMGroupsForLoginUser",
      type: "POST",
    },

    getAllPurposeOfAdvanced: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getAllPurposeOfAdvanced",
      type: "GET",
    },
    getAllSectorData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getAllSectorData",
      type: "POST",
    },

    getAllSubSectorData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getAllSubSectorData",
      type: "GET",
    },

    getAllUpcSectionData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getAllUpcSectionData",
      type: "GET",
    },

    approveOrRejectWorkflowTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/workflow/approveOrRejectWorkflowTemplate",
      type: "POST",
    },

    approveOrRejectUPMGroup: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/workflow/approveOrRejectUPMGroup",
      type: "POST",
    },

    approveOrRejectUPCTemplate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/upcTemplate/approveOrRejectUPCTemplate",
      type: "POST",
    },

    approveOrRejectUPCSection: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/upcSection/approveOrRejectUPCSection",
      type: "POST",
    },

    getPagedFacility: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facility/getPagedFacility",
      type: "POST",
    },

    getFacilityUpdateDto: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facility/getFacilityUpdateDto",
      type: "GET",
    },

    getPagedFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedFacilityPaper",
      type: "POST",
    },

    getPagedMyFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedMyFacilityPaper",
      type: "POST",
    },
    getDashboardCount: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getDashboardCount",
      type: "POST",
    },

    draftFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/draftFacilityPaper",
      type: "POST",
    },

    draftFacilityPaperByLead: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/draftFacilityPaperByLead",
      type: "POST",
    },

    saveOrUpdateFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateFacilityPaper",
      type: "POST",
    },
    updateFacilityPaperExposure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateFacilityPaperExposure",
      type: "POST",
    },
    calculateFacilityPaperExposure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/calculateFacilityPaperExposure",
      type: "POST",
    },
    saveOrUpdateFPFacility: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateFPFacility",
      type: "POST",
    },
    updateFPFacilityDisplayOrderAndStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateFPFacilityDisplayOrderAndStatus",
      type: "POST",
    },
    replicateFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getSuccessfullyCopyMessage(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/replicateFacilityPaper",
      type: "POST",
    },
    getFacilityPaperByID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFacilityPaperByID",
      type: "GET",
    },

    updateFpUpdateEmailSubscribeState: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/notification/updateFpUpdateEmailSubscribeState",
      type: "GET",
    },

    getPagedCustomers: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getPagedCustomers",
      type: "POST",
    },

    getCustomerByID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerByID",
      type: "GET",
    },

    getCustomerDTOListByIDs: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerDTOListByIDs",
      type: "POST",
    },

    addEditCasCustomer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/addEditCasCustomer",
      type: "POST",
    },

    addEditDirectorDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/addEditDirectorDetails",
      type: "POST",
    },
    addEditCompanyRao: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/addEditCompanyRao",
      type: "POST",
    },

    saveOrUpdateCustomerDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/customer/saveOrUpdateCustomerDTO",
      type: "POST",
    },

    uploadFacilityPaperDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/uploadFacilityPaperDocument",
      type: "POST",
    },

    uploadFpBccDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/fbBcc/uploadFpBccDocument",
      type: "POST",
    },

    deactivateFpFacilitySupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/deactivateFpFacilitySupportingDoc",
      type: "POST",
    },
    deactivateFpBccDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/fbBcc/deactivateFpBccDoc",
      type: "POST",
    },
    deactivateFpCreditRiskDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/deactivateFpCreditRiskDoc",
      type: "POST",
    },
    addEditCustomerOtherBankDetail: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/addEditCustomerOtherBankDetail",
      type: "POST",
    },
    uploadFPCustomerCribDetail: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/uploadFPCustomerCribDetail",
      type: "POST",
    },
    deactivateCribSupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/deactivateCribSupportingDoc",
      type: "POST",
    },
    uploadFacilityDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facility/uploadFacilityDocument",
      type: "POST",
    },
    deactivateFacilitySupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facility/deactivateFacilitySupportingDoc",
      type: "POST",
    },
    saveOrUpdateFacilityRepayment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facility/saveOrUpdateFacilityRepayment",
      type: "POST",
    },
    getAllWorkFlowTemplates: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getAllWorkFlowTemplates",
      type: "GET",
    },
    getUpmGroupByWorkFlowTemplateID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getUpmGroupByWorkFlowTemplateID",
      type: "GET",
    },

    getUserDetailListFormBranchAuthorityLevel: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getUserDetailListFormBranchAuthorityLevel",
      type: "POST",
    },

    updateFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateFacilityPaper",
      type: "POST",
    },

    addEditComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/addEditComment",
      type: "POST",
    },

    approveOrRejectFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/approveOrRejectFacilityPaper",
      type: "POST",
    },

    addEditCreditRiskComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/addEditCreditRiskComment",
      type: "POST",
    },

    addNewCreditRiskComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/addNewCreditRiskComment",
      type: "POST",
    },

    getRemarkDtoList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getRemarkDtoList",
      type: "GET",
    },

    getUserDAByLoggedInUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getUserDAByLoggedInUser",
      type: "GET",
    },

    getUserUPMDetailList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getUserUPMDetails",
      type: "POST",
    },

    getActiveApprovedUpcTemplateDtoList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getActiveApprovedUpcTemplateDtoList",
      type: "GET",
    },

    addEditUPCSectionData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/addEditUPCSectionData",
      type: "POST",
    },
    getAssignedFacilityPaperCount: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getAssignedFacilityPaperCount",
      type: "POST",
    },
    removeUpcSectionData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultRemove(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/removeUpcSectionData",
      type: "POST",
    },

    saveOrUpdateSecuritySummery: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateSecuritySummery",
      type: "POST",
    },

    getPagedAgents: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/agent/getPagedAgents",
      type: "POST",
    },

    getAgentUpdateDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/agent/getAgentUpdateDTO",
      type: "GET",
    },

    addAgent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/agent/addAgent",
      type: "POST",
    },

    updateAgent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/agent/updateAgent",
      type: "POST",
    },

    getCorporateComprehensive: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCorporateComprehensive",
      type: "POST",
    },

    getCustomerDetailFromBank: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerDetailFromBank",
      type: "POST",
    },

    getConsumerComprehensive: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getConsumerComprehensive",
      type: "POST",
    },

    saveUpdateFacilitySecurity: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveUpdateFacilitySecurity",
      type: "POST",
    },

    getUpmDetailsByAdUserIdAndAppCode: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/agent/getUpmDetailsByAdUserIdAndAppCode",
      type: "POST",
    },

    updateLead: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/saveOrUpdateLead",
      type: "POST",
    },

    submitLead: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/lead/submitLead",
      type: "POST",
    },

    agentUpdateUserPassword: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/agent/updateUserPassword",
      type: "POST",
    },

    getBCCPaperByFacilityPaperByID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/getBCCPaperByFacilityPaperByID",
      type: "POST",
    },

    updateBCCPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/updateBCCPaper",
      type: "POST",
    },

    createBCCPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/createBCCPaper",
      type: "POST",
    },

    saveOrUpdateCompanyDirectorDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/saveOrUpdateCompanyDirectorDetails",
      type: "POST",
    },

    saveOrUpdateRiskRatingYear: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/saveOrUpdateRiskRatingYear",
      type: "POST",
    },

    getPagedFacilityPaperDTOForBCC: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/getPagedFacilityPaperDTOForBCC",
      type: "POST",
    },

    getBCCReport: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/getBCCReport",
      type: "POST",
    },

    saveOrUpdateBccFacilities: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/saveOrUpdateBccFacilities",
      type: "POST",
    },

    isAbleToReturnFacilityPaperToAgent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/isAbleToReturnFacilityPaperToAgent",
      type: "POST",
    },

    getPagedCustomersForJoiningParties: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getPagedCustomersForJoiningParties",
      type: "POST",
    },

    getCustomerAccountStatisticResponse: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerAccountStatistic",
      type: "POST",
    },

    getCasStatResponse: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCasStat",
      type: "POST",
    },

    getCustomerDepositDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerDepositDetails",
      type: "POST",
    },

    getCustomerFacilityDetailsByAccountNumber: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerFacilityDetailsByAccountNumber",
      type: "POST",
    },

    saveOrUpdateBCCCustomerCribDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/saveOrUpdateBCCCustomerCribDetails",
      type: "POST",
    },

    getActiveSecuritySummaryTopics: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getActiveSecuritySummaryTopics",
      type: "GET",
    },

    getFPDirectReturnUsersList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFPDirectReturnUsersList",
      type: "POST",
    },

    getRetailCribReport: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/getRetailCribReport",
      type: "POST",
    },

    refreshCustomerDetailFromBank: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/refreshCustomerDetailFromBank",
      type: "POST",
    },

    getPagedFacilityPaperForTransfer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedFacilityPaperForTransfer",
      type: "POST",
    },

    saveOrUpdateApplicationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateApplicationForm",
      type: "POST",
    },

    draftApplicationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/draftApplicationForm",
      type: "POST",
    },

    getPagedApplicationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getPagedApplicationForm",
      type: "POST",
    },

    getApplicationFormByID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getApplicationFormByID",
      type: "GET",
    },

    saveOrUpdateAFBasicDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateAFBasicDetails",
      type: "POST",
    },

    uploadApplicationFormDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/uploadApplicationFormDocument",
      type: "POST",
    },

    deactivateApplicationFormSupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/deactivateApplicationFormSupportingDoc",
      type: "POST",
    },

    saveOrUpdateOwnershipDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateOwnershipDetails",
      type: "POST",
    },

    saveOrUpdateAFFacility: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateAFFacility",
      type: "POST",
    },

    getPagedAFSubTopics: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/applicationFormSubTopic/getPagedAFSubTopics",
      type: "POST",
    },

    getAFSubTopicByID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationFormSubTopic/getAFSubTopicByID",
      type: "GET",
    },

    saveOrUpdateAFSubTopic: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationFormSubTopic/saveOrUpdateAFTopic",
      type: "POST",
    },

    approveAFSubTopic: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationFormSubTopic/approveAFSubTopic",
      type: "POST",
    },

    getPagedAFTopics: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/applicationFormTopic/getPagedAFTopics",
      type: "POST",
    },

    getAFTopicByID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationFormTopic/getAFTopicByID",
      type: "GET",
    },

    saveOrUpdateAFTopic: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationFormTopic/saveOrUpdateAFTopic",
      type: "POST",
    },

    approveAFTopic: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationFormTopic/approveAFTopic",
      type: "POST",
    },

    getApprovedApplicationFormTopics: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getApprovedApplicationFormTopics",
      type: "GET",
    },

    saveOrUpdateApplicationFormTopics: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateApplicationFormTopics",
      type: "POST",
    },

    deactivateApplicationFormTopic: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/deactivateApplicationFormTopic",
      type: "POST",
    },

    saveUpdateApplicationFormFacilitySecurity: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveUpdateFacilitySecurity",
      type: "POST",
    },

    uploadApplicationTopicConfigFile: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationTopicConfig/uploadApplicationTopicConfigFile",
      type: "POST",
    },

    getApplicationTopicConfigs: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationTopicConfig/getApplicationTopicConfigs",
      type: "GET",
    },

    saveOrUpdateCribReports: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateCribReports",
      type: "POST",
    },

    saveOrUpdateOptionalCribReports: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateOptionalCribReports",
      type: "POST",
    },

    saveOrUpdateCribAttachments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/saveOrUpdateCribAttachments",
      type: "POST",
    },

    deactivateAFCribAttachment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/deactivateAFCribAttachment",
      type: "POST",
    },

    saveOrUpdateOtherBankDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateOtherBankDetails",
      type: "POST",
    },

    removeOtherBankDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/removeOtherBankDetails",
      type: "POST",
    },

    saveOrUpdateRiskRate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateRiskRate",
      type: "POST",
    },

    draftFacilityPaperByApplicationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/draftFacilityPaperByApplicationForm",
      type: "POST",
    },

    updateApplicationFormStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/updateApplicationFormStatus",
      type: "POST",
    },

    getActiveApprovedUpcSectionListByTemplateID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/upcSection/getActiveApprovedUpcSectionListByTemplateID",
      type: "POST",
    },

    saveOrUpdateFinancialObligations: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/saveOrUpdateFinancialObligations",
      type: "POST",
    },

    updateBCCPDFReport: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/updateBCCPDFReport",
      type: "POST",
    },

    getAFReturnUsersList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getAFReturnUsersList",
      type: "POST",
    },

    updateAFFacilityDisplayOrderAndStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/updateAFFacilityDisplayOrderAndStatus",
      type: "POST",
    },

    saveOrUpdateBorrowerGuarantor: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/saveOrUpdateBorrowerGuarantor",
      type: "POST",
    },

    updateFacilityPaperOutstandingDate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateFacilityPaperOutstandingDate",
      type: "POST",
    },

    getFacilityPaperHistoryForLead: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/lead/getFacilityPaperHistoryForLead",
      type: "POST",
    },

    getCorporateCribReport: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/getCorporateCribReport",
      type: "POST",
    },

    uploadAFFacilityDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/uploadAFFacilityDocument",
      type: "POST",
    },

    deactivateAFFacilityDocuments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/deactivateAFFacilityDocuments",
      type: "POST",
    },

    getFacilityPaperHistory: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFacilityPaperHistory",
      type: "POST",
    },

    getRetailCribReportFromCasDB: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/getCribReportFromCasDB",
      type: "POST",
    },

    replicateApplicationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/replicateApplicationForm",
      type: "POST",
    },

    getCopyPagedApplicationForms: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getCopyPagedApplicationForms",
      type: "POST",
    },

    getInboxPagedApplicationForms: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getInboxPagedApplicationForms",
      type: "POST",
    },

    getPagedBranchApplicationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getPagedBranchApplicationForm",
      type: "POST",
    },

    getApplicationFormsForTransfer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/getApplicationFormsForTransfer",
      type: "POST",
    },

    saveOrUpdateAFComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/applicationForm/saveOrUpdateAFComment",
      type: "POST",
    },

    getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode",
      type: "POST",
    },

    addEditCreditRiskReply: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/addEditCreditRiskReply",
      type: "POST",
    },

    getCasActiveBranchDepartmentList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getCasActiveBranchDepartmentList",
      type: "GET",
    },

    draftFacilityPaperWithNonFinacleCustomer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/draftFacilityPaperWithNonFinacleCustomer",
      type: "POST",
    },

    addEditNonFinacleCasCustomer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/addEditNonFinacleCasCustomer",
      type: "POST",
    },

    removeFPJoningParties: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/removeFPJoningParties",
      type: "POST",
    },

    updateCasCustomerDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getSuccessfullyCopyMessage(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateCasCustomerDTO",
      type: "POST",
    },

    getPagedFacilityPaperHistoryWithUPCTemplateDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedFacilityPaperHistoryWithUPCTemplateDetails",
      type: "POST",
    },

    copyUPCSectionData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/copyUPCSectionData",
      type: "POST",
    },

    getReviewCommentFromFPID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getReviewCommentFromFpID",
      type: "POST",
    },

    getOwnApprovedPagedFacilityPapers: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getOwnApprovedPagedFacilityPapers",
      type: "POST",
    },

    saveOrUpdateReviewComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateReviewComment",
      type: "POST",
    },

    getReviewCommentFromFPIDAndUpmID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getReviewCommentFromFPIDAndUpmID",
      type: "POST",
    },

    saveOrUpdateCribReport: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateCribReport",
      type: "POST",
    },

    getAssistants: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getAssistants",
      type: "POST",
    },

    getCreditCalculatorData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/calculator/getCreditCalculatorData",
      type: "POST",
    },

    calculateLastRentalValue: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/calculateLastRentalValue",
      type: "POST",
    },

    getCreditCalculatedFacilitiesESBResponseStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCreditCalculatedFacilitiesESBResponseStatus",
      type: "POST",
    },

    saveOrUpdateCustomerRatings: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/customerRatings/saveOrUpdateCustomerRatings",
      type: "POST",
    },

    getLeadDashboardCount: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getLeadDashboardCount",
      type: "POST",
    },

    getPagedLeadDashboard: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getPagedLeadDashboard",
      type: "POST",
    },

    getApplicationFormDashboardCount: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getApplicationFormDashboardCount",
      type: "POST",
    },

    getPagedApplicationFormDashboard: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getPagedApplicationFormDashboard",
      type: "POST",
    },

    getPagedSearchApplicationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getPagedSearchApplicationForm",
      type: "POST",
    },

    getSearchedFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getSearchedFacilityPaper",
      type: "POST",
    },

    //new
    getCurrentAssignUserDivCode: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCurrentAssignedUserDivCode",
      type: "GET",
    },

    //new
    getRiskDivCode: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getRiskDivCode",
      type: "GET",
    },

    getRiskCommentList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getRiskCommentList",
      type: "GET",
    },

    saveOrUpdateLeadComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/lead/saveOrUpdateLeadComment",
      type: "POST",
    },

    deactivateLeadSupportingDoc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/lead/deactivateLeadFormSupportingDoc",
      type: "POST",
    },

    getTranDetForCashFlow: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getTranDetForCashFlow",
      type: "POST",
    },
    getCovenantList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getCovenantList",
      type: "POST",
    },

    saveCustomerCovenantDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        showMessage: new ShowMessageDto().getDefaultAdded(),
      },
      url: "/cas-covenant/api/covenant/saveCustomerCovenant",
      type: "POST",
    },

    getCovenantListByFpRefNumber: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getCovenantListByFpRefNumber",
      type: "POST",
    },

    getCovenantResponse: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getCovenantsByFpRefNumber",
      type: "POST",
    },

    getCIDList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCIFDetails",
      type: "GET",
    },

    getCustomerEvaluationListById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerEvaluationListById",
      type: "GET",
    },

    deleteCovenants: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        showMessage: new ShowMessageDto().getDefaultRemove(),
      },
      url: "/cas/api/covenant/deleteCovenant",
      type: "GET",
    },

    getApplicationCovenantListByFpRefNumber: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getApplicationCovenantListByFpRefNumber",
      type: "GET",
    },

    deleteApplicationCovenants: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        showMessage: new ShowMessageDto().getDefaultRemove(),
      },
      url: "/cas/api/facilityPaper/deleteApplicationCovenant",
      type: "GET",
    },

    saveApplicationCovenantDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        showMessage: new ShowMessageDto().getDefaultAdded(),
      },
      url: "/cas/api/covenant/saveApplicationCovenantDetails",
      type: "POST",
    },

    getFacilityList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facility/getFacilityList",
      type: "GET",
    },
    uploadCreditRiskDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/uploadCreditRiskDocument",
      type: "POST",
    },

    getCustomerEvaluationListByCIFId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerEvaluationListByCIFId",
      type: "GET",
    },

    getEvaluationScore: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getEvaluationScore",
      type: "GET",
    },

    saveOrUpdateCIFID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateCIFID",
      type: "POST",
    },

    deleteEvaluation: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/deleteCustomerEvaluation",
      type: "DELETE",
    },

    getCustomerMaxEvaluationForm: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/findEvaluationById2",
      type: "DELETE",
    },

    getCustomerEvaluationId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/findEvaluationById",
      type: "GET",
    },
    deactivateFpCreditRiskComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/deactivateFpCreditRiskComment",
      type: "POST",
    },

    getFacilityDocumentElementList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFacilityDocumentElementList",
      type: "GET",
    },

    saveOrUpdateSecurityDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateSecurityDocument",
      type: "POST",
    },

    getSecurityDocumentHistory: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getSecurityDocumentHistory",
      type: "GET",
    },

    getSecurityDocumentElements: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getSecurityDocumentElements",
      type: "GET",
    },

    getSecurityDocumentSubmitDiv: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getSecurityDocumentSubmitDiv",
      type: "GET",
    },

    getSecurityDocumentSubmitWorkClass: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getSecurityDocumentSubmitWorkClass",
      type: "GET",
    },

    updateCustomerCovenant: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        showMessage: new ShowMessageDto().getDefaultUpdated(),
      },
      url: "/cas/api/covenant/updateCovenant",
      type: "POST",
    },

    deactivateBccPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultRemove(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/bccPaper/deactivateBccPaper",
      type: "POST",
    },

    addEditShareHolderDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/addEditShareHolderDetails",
      type: "POST",
    },

    findCustomerCovenantById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getCustomerCovenantByID",
      type: "GET",
    },

    updateFacilityCovenant: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        showMessage: new ShowMessageDto().getDefaultUpdated(),
      },
      url: "/cas/api/facilityPaper/updateFacilityCovenant",
      type: "POST",
    },

    findFacilityCovenantById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getApplicationCovenantByID",
      type: "GET",
    },

    getApplicationCovenantByFacilityID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getApplicationCovenantByFacilityID",
      type: "GET",
    },

    getFacilityCovenantList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getFacilityCovenantList",
      type: "GET",
    },
    getACAERecordsByStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/getACAEPagedRecordsByStatus",
      type: "POST",
    },
    getNoLoadingACAERecordsByStatus: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/getACAEPagedRecordsByStatus",
      type: "POST",
    },

    getAllACAERecordsByStatus: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/getAllACAERecordsByStatus",
      type: "POST",
    },

    getACAEStatusCount: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/acae/getACAEStatusCount",
      type: "POST",
    },

    getNoLoadingACAEStatusCount: {
      headerParam: {
        showLoading: false,
        showToast: true,
        showMessage: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/acae/getACAEStatusCount",
      type: "POST",
    },
    getEligibilityForwardACAEBatch: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/getEligibilityForwardACAEBatch",
      type: "POST",
    },

    saveACAEBulkComments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/saveBulkComments",
      type: "POST",
    },

    getACAEPaperUserWiseSummary: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/getACAEPaperUserWiseSummary",
      type: "POST",
    },
    forwardACAEBatch: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/forwardACAEBatch",
      type: "POST",
    },
    getAllowedLimit: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getAllowedLimit",
      type: "POST",
    },

    getACAECustomerDetails: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAECustomerDetails",
      type: "POST",
    },

    getAccountBalanceDetails: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getAccountBalanceDetails",
      type: "POST",
    },

    getACAEOutstanding: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAEOutstanding",
      type: "POST",
    },

    getFinacleIdFromAccountNumber: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getFinacleIdFromAccountNumber",
      type: "POST",
    },

    getACAERelatedAccounts: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAERelatedAccounts",
      type: "POST",
    },

    getACAELoanAccounts: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAELoanAccounts",
      type: "POST",
    },

    getACAEBalanceAfterPayment: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAEBalanceAfterPayment",
      type: "POST",
    },

    getACAEUserComments: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAEUserComments",
      type: "POST",
    },

    getACAEActiveComment: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAEActiveComment",
      type: "POST",
    },

    getACAESeniorUserLOV: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAESeniorUserLOV",
      type: "POST",
    },

    getACAERejectUserLOV: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAERejectUserLOV",
      type: "POST",
    },

    approveACAEPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/approveACAEPaper",
      type: "POST",
    },

    getCurrentUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getCurrentUser",
      type: "POST",
    },

    forwardACAEPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/forwardACAEPaper",
      type: "POST",
    },
    toBeResubmittedACAEPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/toBeResubmittedACAEPaper",
      type: "POST",
    },

    rejectACAEPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/rejectACAEPaper",
      type: "POST",
    },
    saveACAEComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/saveACAEComment",
      type: "POST",
    },
    getACAELowerOrHigherUserGroupLOV: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAELowerOrHigherUserGroupLOV",
      type: "POST",
    },

    isACAEAttended: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/isACAEAttended",
      type: "POST",
    },

    getPreviousUsers: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getPreviousUsers",
      type: "POST",
    },
    updateEscalationDays: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/updateEscalationDays",
      type: "POST",
    },

    getACAEDateRangeInquiry: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/date-range-inquiry/getACAEDateRangeInquiry",
      type: "POST",
    },

    getInquiryByDateRange: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/status-inquiry/getInquiryByDateRange",
      type: "POST",
    },

    getInquiryByResubmittedACAE: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/status-inquiry/getInquiryByResubmittedACAE",
      type: "POST",
    },

    getInquiryBySolIds: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/status-inquiry/getInquiryBySolIds",
      type: "POST",
    },

    getACAEStatusInquiryByDateRange: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/status-inquiry/getInquiryByDateRange",
      type: "POST",
    },
    getACAEStatusInquiryBySolIds: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/status-inquiry/getInquiryBySolIds",
      type: "POST",
    },
    getACAEStatusInquiryByResubmitted: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/status-inquiry/getInquiryByResubmittedACAE",
      type: "POST",
    },
    getACAERecordsForTransfer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/transfer/getACAERecordsForTransfer",
      type: "POST",
    },

    forwardTransferOption: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/transfer/forwardTransferOption",
      type: "POST",
    },
    getACAETransferUserList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/transfer/getTransferUserList",
      type: "POST",
    },
    getExternalSiteDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/externalSite/getExternalSiteDetails",
      type: "GET",
    },
    getACAELoanAccountsDetails: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/customer/getCustomerFacilityDetailsByAccountNumber",
      type: "POST",
    },
    getACAERangeInquiry: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getACAERangeInquiry",
      type: "POST",
    },
    getBasicACAEOutstanding: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getBasicACAEOutstanding",
      type: "POST",
    },
    getAdvanceACAEOutstanding: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getAdvanceACAEOutstanding",
      type: "POST",
    },

    getDAClearBalance: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/acae/paper/getDAClearBalance",
      type: "GET",
    },

    getDocumentContent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getDocumentSectionContent",
      type: "GET",
    },

    saveUserPool: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/saveUserPool",
      type: "POST",
    },

    getPagedFacilityPaperDTOForBCCForUserName: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/getPagedFacilityPaperDTOForBCCForUserName",
      type: "POST",
    },

    getUserPool: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/getUserPool",
      type: "GET",
    },

    savePoolUserStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/saveUserPool",
      type: "POST",
    },

    changeAssignUserBCCPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultUpdated(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/bccPaper/changeAssignUserBCCPaper",
      type: "POST",
    },

    duplicateFpFacilities: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/copyFacilities",
      type: "POST",
    },

    updateFacilityPaperType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: "",
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/updateFacilityPaperType",
      type: "POST",
    },
    approveOrRejectUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/approveOrRejectUserPool",
      type: "POST",
    },

    saveCommitteType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/saveCommitteeType",
      type: "POST",
    },

    updateCommitteType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/updateCommitteeType",
      type: "POST",
    },

    getCommitteeType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/getCommitteeTypes",
      type: "GET",
    },

    getCommittees: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/getCommittees",
      type: "GET",
    },

    getCommitteeById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/getCommitteeById",
      type: "GET",
    },

    saveCommitte: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/saveSubCommittee",
      type: "POST",
    },

    saveApproveRejectCommitte: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/approveOrRejectCommittee",
      type: "POST",
    },

    getCommitteComments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/committee/getCommitteeCommentList",
      type: "GET",
    },

    getCommitteeLevels: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getCommitteeLevels",
      type: "GET",
    },

    getPagedCommitteePaperDashboard: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedCommitteePaperDashboard",
      type: "POST",
    },

    getCommitteePaperDashboardCount: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCommitteePaperDashboardCount",
      type: "POST",
    },

    updateCommitteeStatusHistory: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateCommitteeStatusHistory",
      type: "POST",
    },

    getBCCPaperDashboardCount: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getBCCPaperDashboardCount",
      type: "POST",
    },

    getPagedBCCPaperDashboard: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getPagedBCCPaperDashboard",
      type: "POST",
    },

    getBCCEntererWorkClass: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getBCCEntererWorkClass",
      type: "GET",
    },

    getBCCAuthorizerWorkClass: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getBCCAuthorizerWorkClass",
      type: "GET",
    },

    getBCCInquirerWorkClass: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/masterData/getBCCInquirerWorkClass",
      type: "GET",
    },

    updateBccDTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateBccDTO",
      type: "POST",
    },

    getFPUsersInvolved: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFPUsersInvolved",
      type: "GET",
    },

    getFPCommitteeSignatureList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFPCommitteeSignatureList",
      type: "GET",
    },

    getUPCTemplateComparisonActiveVersion: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/upcTemplateComparison/getUPCTemplateComparisonActiveVersion",
      type: "POST",
    },

    saveUPCTemplateComparisonComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/upcTemplateComparison/saveUPCTemplateComparisonComment",
      type: "POST",
    },
    getUPCTemplateComparisonActiveComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/upcTemplateComparison/getUPCTemplateComparisonActiveComment",
      type: "POST",
    },

    getUPCTemplateComparisonByDate: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/upcTemplateComparison/getUPCTemplateComparisonByDate",
      type: "POST",
    },

    getFacilityCommentsById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFusTraceDataById",
      type: "POST",
    },

    saveFusTrace: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveFusTrace",
      type: "POST",
    },

    saveViewComments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveViewFusTrace",
      type: "POST",
    },

    getUPCTemplateComparisonCommentHistory: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/upcTemplateComparison/getUPCTemplateComparisonCommentHistory",
      type: "POST",
    },

    getUPCSectionHistoryDataById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/upcTemplateComparison/getUPCSectionHistoryDataById",
      type: "POST",
    },

    getUPCSectionDataById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getUPCSectionDataBySectionId",
      type: "GET",
    },

    getUPCSectionsDataByFPId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getUPCSectionsDataByFPId",
      type: "GET",
    },

    getFusTracesByFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFusTracesByFacilityPaper",
      type: "GET",
    },

    deleteFusTrace: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/deleteFusTrace",
      type: "POST",
    },

    getDADetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getAllDATempDesignationDetails",
      type: "POST",
    },

    getDAMasterDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getAllDADesignationDetails",
      type: "POST",
    },

    approveOrRejectDAUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/saveDADetailsToTemp",
      type: "POST",
    },

    updateDAUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/updateDADetails",
      type: "POST",
    },

    saveDAUser: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      // url: "/cas/api/committee/saveUserPool",
      type: "POST",
    },

    addDataDaTable: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/createAndUpdateDALimits",
      type: "POST",
    },

    getApprovedDesignations: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getAllDALimitsAndDesignations",
      type: "POST",
    },

    getApprovedDAData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getAllDALimits",
      type: "POST",
    },

    getPendingDAData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getAllTempDALimits",
      type: "POST",
    },

    approveRejectRiskValues: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/authorizedDALimits",
      type: "POST",
    },

    saveDADetailsToMaster: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/saveDADetailsToMaster",
      type: "POST",
    },

    getAllIndividualDesignationCode: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getAllIndividualDesignationCode",
      type: "POST",
    },
    getAllCommitteeDesignationsWithCode: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/getAllCommitteeDesignationsWithCode",
      type: "GET",
    },

    addNewDesignation: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/saveDADetailsToMaster",
      type: "POST",
    },

    getApprovedPendingDAData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/admin/getAllDALimitsWithApproveStatus",
      type: "POST",
    },

    getAllDAUsers: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/userDA/getAllDAUsers",
      type: "GET",
    },

    changeDAOrder: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/das/changeDisplayOrder",
      type: "POST",
    },

    getCommitteeButtonEnableData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCommitteeButtonEnableData",
      type: "GET",
    },
    sendCAEmail: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/sendCAEmail",
      type: "POST",
    },

    updateCOVStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/updateCoveringApproval",
      type: "POST",
    },

    getPagedCoveringApprovals: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSearch(),
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/getPagedCoveringApprovalDashboard",
      type: "POST",
    },

    getPagedCoveringApprovalDashboard: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/getPagedCoveringApprovalDashboard",
      type: "POST",
    },

    getCoveringApprovalDashboardCount: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/getCoveringApprovalDashboardCount",
      type: "POST",
    },

    getTransDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/loadTranDetails",
      type: "POST",
    },

    getCustomerBankDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/loadCustomerBankDetails",
      type: "POST",
    },

    getPendingCoveringApprovals: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/getPendingCoveringApprovals",
      type: "POST",
    },

    draftApplicationFormCA: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/draftCoveringApproval",
      type: "POST",
    },

    getCOVCommentById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/getComments",
      type: "GET",
    },

    getCoveringApprovalByID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/getCoveringApprovalByID",
      type: "GET",
    },

    getCOVReturnUsersList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/coveringApproval/getDirectReturnUsersList",
      type: "GET",
    },

    forwardBCCDocsByFpBccId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/fbBcc/forwardBCCDocs",
      type: "POST",
    },

    authorizeBCCDocsByFpBccId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/fbBcc/authorizeBCCDocs",
      type: "POST",
    },
    getFinacleExOutLimits: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/finacleData",
      type: "POST",
    },
    getWatchlistStatus: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getWatchlistStatus",
      type: "POST",
    },
    getFinacleGuaranteeVolumes: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getGuaranteeVolumes",
      type: "POST",
    },
    getFinacleGuaranteeVolumesFinancial: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getGuaranteeVolumesFinancialYear",
      type: "POST",
    },
    getFinacleGuaranteeVolumesDB: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getGuaranteeVolumesDB",
      type: "POST",
    },
    saveGuaranteeVolumes: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/saveGuaranteeVolumes",
      type: "POST",
    },
    getTTTurnoverAccounts: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getTTTAccounts",
      type: "GET",
    },
    getExportTOFinancial: {
      headerParam: {
        showLoading: false,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getExportTurnoverFinancial",
      type: "POST",
    },
    saveExportTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/saveExportTurnOvers",
      type: "POST",
    },
    getExportTOCalender: {
      headerParam: {
        showLoading: false,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getExportTurnoverCalender",
      type: "POST",
    },
    getInsuranceDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getInsuranceDetails",
      type: "POST",
    },
    refreshInsuranceDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/refreshInsuranceDetails",
      type: "POST",
    },
    getInsuranceDetailsDB: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getInsuranceDetailsDB",
      type: "POST",
    },
    isExistKalyptoData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/isExistKalyptoData",
      type: "POST",
    },
    saveKalyptoService: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/saveKalypto",
      type: "POST",
    },
    getKalyptoFromIntegrationService: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/getKalyptoFromIntegrationService",
      type: "POST",
    },
    getKalyptoValueService: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/getKalyptoValues",
      type: "POST",
    },

    saveNewKalyptoService: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/saveNewKalypto",
      type: "POST",
    },
    refreshKalyptoValues: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/refreshKalyptoValues",
      type: "POST",
    },
    saveEditedKalypto: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/saveEditedKalypto",
      type: "POST",
    },
    isAddedNewKalypto: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/kalypto/isAddedNewKalypto",
      type: "POST",
    },

    getUserApplicationDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/externalSite/getUserApplicationDetails",
      type: "GET",
    },

    updateFacilityPaperYearType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: "",
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/updateFacilityPaperYearType",
      type: "POST",
    },

    hasExpiredInsurance: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/hasExpiredItem",
      type: "POST",
    },
    searchIndividualCrib: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/searchIndividualCrib",
      type: "POST",
    },
    searchIndividualCribContinue: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/searchIndividualCribContinue",
      type: "POST",
    },
    searchCompanyCrib: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/searchCompanyCrib",
      type: "POST",
    },
    searchCompanyCribContinue: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/searchCompanyCribContinue",
      type: "POST",
    },
    getCustomReport: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/getCustomReport",
      type: "POST",
    },
    getCustomReportByToken: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/getCustomReportByToken",
      type: "POST",
    },
    getCribReportPDF: {
      headerParam: {
        showLoading: false,
        showToast: false,
        skipAuth: false,
      },
      url: "/cas/api/cribReport/getCribReportPDF",
      type: "POST",
    },
    uploadFPCustomerCribDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/uploadFPCustomerCribDetails",
      type: "POST",
    },

    updateCribReport: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/facilityPaper/updateCribReport",
      type: "POST",
    },

    deleteCribReport: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/deleteCribReport",
      type: "POST",
    },

    getCribHistoryByCustomer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCribHistoryByCustomer",
      type: "GET",
    },

    saveCribSearch: {
      headerParam: {
        showLoading: false,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: "/cas/api/cribReport/saveCribSearch",
      type: "POST",
    },

    getExportTODB: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getExportTurnOversDB",
      type: "POST",
    },

    bCCPaperSubmission: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/bCCPaperSubmission",
      type: "POST",
    },

    getImportTODB: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getImportTurnOversDB",
      type: "POST",
    },
    getImportTOFinancial: {
      headerParam: {
        showLoading: false,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getImportTurnoverFinacal",
      type: "POST",
    },
    getImportTOCalender: {
      headerParam: {
        showLoading: false,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/getImportTurnoverCalender",
      type: "POST",
    },
    saveImportTO: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/finacle/saveImportTurnOvers",
      type: "POST",
    },

    getCovenantDetailsFromFinacle: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getCovenantsDetails",
      type: "POST",
    },

    getCompReportData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/getCompReportData",
      type: "POST",
    },

    getLimitNodeData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/getLimitNodeData",
      type: "POST",
    },

    refreshGroupExposureDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getGroupExposure",
      type: "POST",
    },
    getGroupExposureDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getGroupExposureDetails",
      type: "POST",
    },
    calculateGroupExposure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/calculateGroupExposure",
    },
    getComparableContent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/bccPaper/getComparableContent",
      type: "POST",
    },

    getSSOProperties: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: true,
      },
      url: "/cas/api/home/getSSOProperties",
      type: "GET",
    },
    updateGroupExposure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/facilityPaper/updateGroupExposure",
    },
    getCommissionData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/bccPaper/getCommissionData",
    },
    getCommissionLoanAccounts: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/bccPaper/getCommissionLoanAccounts",
    },
    downloadDocs: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/supporting-docs-zip",
      type: "POST",
    },

    getProductGroups: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/bccPaper/getProductGroups",
    },

    getCasV1CustomerByAcc: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/casv1/getCustomerDetails",
      type: "GET",
    },

    getFacilityPaperDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/casv1/getFacilityPaperDetails",
      type: "POST",
    },

    getCasV1AttachmentByRef: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/casv1/getAttachmentDetails",
      type: "POST",
    },

    getSections: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/casv1/getSections",
      type: "POST",
    },

    getCasV1CommentsByRef: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/casv1/getComments",
      type: "POST",
    },

    saveCommitteeInquiry: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveUpdateCommitteeInquiry",
      type: "POST",
    },

    getCommitteeInquiryByFacilityPaperId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCommitteeInquiryByFacilityPaperId",
      type: "GET",
    },

    statusUpdateCommitteeInquiry: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/statusUpdateCommitteeInquiry",
      type: "POST",
    },

    getCommitteeUsers: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCommitteeUsers",
      type: "GET",
    },

    getCusApplicableCovenantList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getCusApplicableCovenantList",
      type: "GET",
    },
 
    getRiskCategories: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "GET",
      url: "/cas/api/environmentalRisk/getRiskCategories",
    },

    saveTempRiskCategory: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/environmentalRisk/saveTempRiskCategory",
    },

    approveRejectRiskCategories: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/environmentalRisk/approveRejectCategory",
    },

    getAnnexes: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "GET",
      url: "/cas/api/annexes/getAnnexes",
    },

    getAnnexeById: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "GET",
      url: "/cas/api/annexes/getAnnexeById",
    },

    saveAnnex: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/annexes/saveAnnex",
    },

    approveRejectAnnex: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/annexes/approveRejectAnnex",
    },

    getEnvironmentalRiskTree: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "GET",
      url: "/cas/api/environmentalRisk/getEnvironmentalRiskTree",
    },

    saveEnvironmentalRiskCategory: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/applicationForm/saveEnvironmentalRiskCategory",
    },

    getAnnexureById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        showMessage: new ShowMessageDto().getDefaultAdded(),
      },
      url: "/cas/api/esg/getAnnexureByAnnexureId",
      type: "GET",
    },

    saveEsgAnnexure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/saveAnnexureAnswer",
      type: "POST",
    },

    getAnnexureByPaperID: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/getAnnexureByPaperID",
      type: "POST",
    },

    getAnnexureByAnnexureDataId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/applicationForm/getAnnexureByAnnexureDataId",
      type: "GET",
    },

    updateDataToAnnexure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/updateDataToAnnexure",
      type: "POST",
    },

    deleteAnnexure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/deleteAnnexure",
      type: "POST",
    },

    getAnnexureList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/getAnnexureList",
      type: "GET",
    },

    refreshAnnexure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/refreshAnnexure",
      type: "POST",
    },

    addEsgAtttachment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/addAttachments",
      type: "POST",
    },

    getEsgAttachments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/getAttachments",
      type: "POST",
    },

    getAttachmentById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/getAttachmentById",
      type: "POST",
    },

    updateESGAttachment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/updateAttachment",
      type: "POST",
    },

    deleteESGAttachment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/esg/deleteAttachment",
      type: "POST",
    },

    saveFPEnvironmentalRiskCategory: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/facilityPaper/saveEnvironmentalRiskCategory",
    },

    getFPAnnexureById: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getAnnexureByAnnexureId",
      type: "GET",
    },

    saveEsgFPAnnexure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveAnnexureAnswer",
      type: "POST",
    },

    getAnnexureByFPId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getAnnexureByFPId",
      type: "GET",
    },

    getFPAnnexureByAnnexureDataId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getAnnexureByAnnexureDataId",
      type: "GET",
    },

    updateDataToFPAnnexure: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/updateDataToAnnexure",
      type: "POST",
    },

    getFPAnnexureList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getAnnexureList",
      type: "GET",
    },

    removeAFEnvironmentalRisk: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/applicationForm/removeAFEnvironmentalRisk",
    },

    removeFPEnvironmentalRisk: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/facilityPaper/removeFPEnvironmentalRisk",
    },

    approvedFPEnvironmentalRisk: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/facilityPaper/approvedFPEnvironmentalRisk",
    },

    approveAFEnvironmentalRisk: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/applicationForm/approveAFEnvironmentalRisk",
    },

    getFPEnvironmentalRiskOpinion: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "GET",
      url: "/cas/api/facilityPaper/getFPEnvironmentalRiskOpinion",
    },

    saveFPEnvironmentalRiskOpinion: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/facilityPaper/saveFPEnvironmentalRiskOpinion",
    },

    saveFPEnvironmentalRiskReply: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/facilityPaper/saveFPEnvironmentalRiskOpinionReply",
    },

    getAFEnvironmentalRiskOpinion: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "GET",
      url: "/cas/api/applicationForm/getAFEnvironmentalRiskOpinion",
    },

    saveAFEnvironmentalRiskOpinion: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/applicationForm/saveAFEnvironmentalRiskOpinion",
    },

    saveAFEnvironmentalRiskReply: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/applicationForm/saveAFEnvironmentalRiskOpinionReply",
    },

    removeFPEnvironmentalRiskOpinion: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/facilityPaper/removeFPEnvironmentalRiskOpinion",
    },

    removeAFEnvironmentalRiskOpinion: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/applicationForm/removeAFEnvironmentalRiskOpinion",
    },

    getTempAnnexeById: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "GET",
      url: "/cas/api/annexes/getTempAnnexeById",
    },

    deleteTempAnnexeById: {
      headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      type: "POST",
      url: "/cas/api/annexes/deleteTempAnnexeById",
    },

    getWalletShare: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getWalletShares",
      type: "GET",
    },

    saveWalletShare: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveWalletShare",
      type: "POST",
    },

    getAllQuestionsAndAnswers: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/sme/getAllQuestionsAndAnswers",
      type: "GET",
    },

    saveOrUpdateAnswer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/sme/saveOrUpdateAnswer",
      type: "POST",
    },

    getSmeAnswer: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/sme/getAnswer",
      type: "GET",
    },

    getAnswerList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/sme/getAnswerList",
      type: "GET",
    },

    getUPCByFacilityPaper: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getUpcSectionData",
      type: "GET",
    },

    getFacilityTemplateDocumentElements: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getFacilityTemplateDocumentElements",
      type: "GET",
    },

    saveSecurityDocument: {
      headerParam: {
        showLoading: true,
        showToast: true,
        showMessage: new ShowMessageDto().getDefaultSaveUpdate(),
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveSecurityDocument",
      type: "POST",
    },

    getSecurityDocumentContent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getSecurityDocumentContent",
      type: "POST",
    },

    getSecurityDocumentHistoryData: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getSecurityDocumentHistoryData",
      type: "GET",
    },

    getSDCovenantList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getSDCovenantList",
      type: "GET",
    },

    getDocumentTags: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getDocumentTags",
      type: "GET",
    },

    getInPrincipleSanctionLetterContent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/lead/getInPrincipleSanctionLetterContent",
      type: "GET",
    },

    getDeviationTypes: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/deviation/getAllDeviationTypes",
      type: "GET",
    },

    saveDeviationType: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/deviation/saveDeviationType",
      type: "PSOT",
    },

    getDeviations: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getDeviations",
      type: "GET",
    },

    getCompDeviations: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCompDeviations",
      type: "GET",
    },

    saveDeviation: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveOrUpdateCompDeviations",
      type: "POST",
    },

    getAllDeviations: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/deviation/getAllDeviations",
      type: "GET",
    },

    saveDeviations: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/deviation/saveDeviation",
      type: "PSOT",
    },
    authorizeDeviation: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/deviation/authorizedDeviation",
      type: "PSOT",
    },

    getDigitalFormApplicationContent: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getDigitalFormApplicationContent",
      type: "PSOT",
    },

    saveMdAssistanceComment: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveMdAssistanceComment",
      type: "POST",
    },

    markAsViewMDComments: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/markAsViewMDComments",
      type: "POST",
    },

    refreshCompDeviations: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/refreshCompDeviations",
      type: "GET",
    },

    getCustomerClassification: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCustomerClassification",
      type: "GET",
    },

    saveCustomerClassification: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/saveClassification",
      type: "POST",
    },

    addCommentToCovenant: {
       headerParam: {
        showLoading: true,
        showToast: false,
        showMessage: false,
        skipAuth: false,
      },
      url: "/cas/api/covenant/addCommentToCovenant",
      type: "POST",
    },

    getCovenantCommentList: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false
      },
      url: "/cas/api/covenant/getCovenantCommentList",
      type: "GET",
    },

    getCustomerBankAccountDetails: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/facilityPaper/getCustomerBankDetails",
      type: "POST",
    },

    smeCustomerTurnover: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/sme/smeCustomerTurnover",
      type: "POST",
    },

    saveOrUpdateAcctIdWithFacilityId: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/saveOrUpdateAcctIdWithFacilityId",
      type: "POST",
    },

    getAllExistingFacilityCovenants: {
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: "/cas/api/covenant/getAllExistingFacilityCovenants",
      type: "GET",
    },
  };
}
