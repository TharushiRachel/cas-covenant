import { SETTINGS } from "./commons.settings";

export class MenuConfig {
  public model: any = [];

  constructor() {
    this.model = [
      {
        id: "home",
        title: "Home",
        type: "item",
        icon: "fas fa-tachometer-alt",
        url: SETTINGS.PAGES.home,
        privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_VIEW_HOME,
        isExternal: false,
        // 'badge': {
        //   'id': 'assigned_facility_paper_count',
        //   'title': '00',
        //   'bg': '#3f729b',
        //   'fg': '#FFFFFF'
        // }
      },
      {
        id: "committee-paper",
        title: "Committee Paper",
        //  'type': 'group',
        icon: "fas fa-file-invoice",
        type: "item",
        //  'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_VIEW_COMMITTEE_PAPER,
        privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_VIEW_BCC_PAPER,
        url: SETTINGS.PAGES.committeePaperDashboard,
        // 'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_FACILITY_PAPER_VIEW + ','
        //          + SETTINGS.PRIVILEGES.ICAS_SETTINGS_PAPER_REVIEW_VIEW,
        /*'children': [
             {
               'id': 'committee-paper-dashboard',
               'title': 'Dashboard',
               'type': 'item',
               'icon': 'fas fa-tachometer-alt',
               'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_VIEW_COMMITTEE_PAPER,
               'url': SETTINGS.PAGES.committeePaperDashboard,
             },

           ]*/
      },
      {
        id: "customer-360",
        title: "Customer 360",
        type: "item",
        icon: "fas fa-street-view",
        privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_CUSTOMER_360_VIEW,
        url: SETTINGS.PAGES.customer360,
        isExternal: false,
      },
      {
        id: "acae",
        title: "ACAE",
        type: "group",
        icon: "fas fa-chart-line",
        privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_ACAE_VIEW,
        isExternal: false,
        children: [
          {
            id: "acae-dashboard",
            title: "Dashboard",
            type: "item",
            icon: "fas fa-tachometer-alt",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_ACAE_VIEW,
            url: SETTINGS.PAGES.acaeDashboard,
          },
          {
            id: "acae-date-range-inquiry",
            title: "Date Range Inquiry",
            type: "item",
            icon: "fas fa-calendar-minus",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_ACAE_INQUIRY_BY_DATE_RANGE,
            url: SETTINGS.PAGES.acaeInquiryByDateRange,
          },
          {
            id: "acae-search",
            title: "Search",
            type: "item",
            icon: "fas fa-search",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_ACAE_SEARCH,
            url: SETTINGS.PAGES.acaeSearch,
          },
          {
            id: "acae-status-inquiry",
            title: "Status Inquiry",
            type: "item",
            icon: "fas fa-comments",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_ACAE_STATUS_INQUIRY,
            url: SETTINGS.PAGES.acaeStatusInquiry,
          },
          {
            id: "acae-details-transfer-search",
            title: "Transfer",
            type: "item",
            icon: "fas fa-random",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_ACAE_TRANSFER_OPTION,
            url: SETTINGS.PAGES.acaeTrasnferOption,
          },
        ],
      },
      {
        id: "leadMenu",
        title: "Lead",
        type: "group",
        icon: "fas fa-receipt",
        privileges:
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_VIEW +
          "," +
          // + SETTINGS.PRIVILEGES.ICAS_SETTINGS_MY_BRANCH_LEAD_VIEW + ','
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_ADD,
        isExternal: false,
        children: [
          {
            id: "lead-dashboard",
            title: "Dashboard",
            type: "item",
            icon: "fas fa-tachometer-alt",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_VIEW,
            url: SETTINGS.PAGES.leadDashboard,
          },
          {
            id: "create",
            title: "Create",
            type: "item",
            icon: "fas fa-user-plus",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_ADD,
            url: SETTINGS.PAGES.leadCreate,
            badge: {
              id: "branch_lead_pending_count",
              title: "00",
              bg: "#3f729b",
              fg: "#FFFFFF",
            },
          },
          {
            id: "comprehensive-create",
            title: "Comprehensive Lead",
            type: "item",
            icon: "fas fa-user-plus",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_COMPREHENSIVE_CREATE,
            url: SETTINGS.PAGES.leadComprehensiveCreate,
            badge: {
              id: "branch_lead_pending_count",
              title: "00",
              bg: "#3f729b",
              fg: "#FFFFFF",
            },
          },
          {
            id: "search ",
            title: "Search ",
            type: "item",
            icon: "fas fa-search",
            url: SETTINGS.PAGES.leadSearch,
            badge: {
              id: "branch_lead_pending_count",
              title: "00",
              bg: "#3f729b",
              fg: "#FFFFFF",
            },
          },
          /* {
            'id': 'leads',
            'title': 'My Leads',
            'type': 'item',
            'icon': 'fas fa-cog',
            'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_VIEW,
            'url': SETTINGS.PAGES.leads,

          },
          {
            'id': 'my-branch-leads',
            'title': 'My Branch Leads',
            'type': 'item',
            'icon': 'fas fa-cogs',
            'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_MY_BRANCH_LEAD_VIEW,
            'url': SETTINGS.PAGES.myBranchLeads,
            'badge': {
              'id': 'branch_lead_pending_count',
              'title': '00',
              'bg': '#3f729b',
              'fg': '#FFFFFF'
            }
          },*/
        ],
      },

      {
        id: "application-from",
        title: "Application Form",
        type: "group",
        icon: "fas fa-address-card",
        privileges:
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FORM_VIEW +
          "," +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FORM_CREATE,
        isExternal: false,
        children: [
          {
            id: "application-form-dashboard",
            title: "Dashboard",
            type: "item",
            icon: "fas fa-tachometer-alt",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FORM_VIEW,
            url: SETTINGS.PAGES.applicationFormDashboard,
          },
          {
            id: "create",
            title: "Create",
            type: "item",
            icon: "fas fa-user-plus",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FORM_CREATE,
            url: SETTINGS.PAGES.applicationFormCreate,
          },
          {
            id: "search",
            title: "Search",
            type: "item",
            icon: "fas fa-search",
            url: SETTINGS.PAGES.applicationFormSearch,
          },
          {
            id: "transfer",
            title: "Transfer",
            type: "item",
            icon: "fas fa-random",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_TRANSFER_APPLICATION_FORM,
            url: SETTINGS.PAGES.applicationFormTransfer,
          },
          /*{
            'id': 'inbox',
            'title': 'Inbox',
            'type': 'item',
            'icon': 'fas fa-archive',
            'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FORM_VIEW,
            'url': SETTINGS.PAGES.applicationFormInbox
          },

          {
            'id': 'branchApplicationForms',
            'title': 'Branch Applications',
            'type': 'item',
            'icon': 'fas fa-building',
            'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FORM_VIEW,
            'url': SETTINGS.PAGES.branchApplicationForms
          },

          {
            'id': 'copy',
            'title': 'Copy',
            'type': 'item',
            'icon': 'fas fa-copy',
            'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FROM_COPY_ENABLED,
            'url': SETTINGS.PAGES.applicationFormCopy
          },*/

          // {
          //   'id': 'applicationForms',
          //   'title': 'Application Forms',
          //   'type': 'item',
          //   'icon': 'fas fa-clipboard-list',
          //   'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_FORM_VIEW,
          //   'url': SETTINGS.PAGES.applicationForms
          // },
          //
          // {
          //   'id': 'applicationForm-transfer',
          //   'title': 'Transfer Application Form',
          //   'type': 'item',
          //   'icon': 'fas fa-random',
          //   'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_TRANSFER_FACILITY_PAPER,
          //   'url': SETTINGS.PAGES.applicationFormTransfer
          // },
        ],
      },
      {
        id: "facility-paper-menu",
        title: "Facility Paper",
        type: "group",
        icon: "fas fa-file-invoice",
        privileges:
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_FACILITY_PAPER_VIEW +
          "," +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_PAPER_REVIEW_VIEW,
        isExternal: false,
        children: [
          /*{
            'id': 'facility-paper',
            'title': 'Facility Papers',
            'type': 'item',
            'icon': 'fas fa-newspaper',
            'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_FACILITY_PAPER_VIEW,
            'url': SETTINGS.PAGES.facilityPaper,
          },*/
          {
            id: "facility-paper-search",
            title: "Search",
            type: "item",
            icon: "fas fa-search",
            url: SETTINGS.PAGES.facilityPaperSearch,
          },

          {
            id: "facilityReview",
            title: "Paper Review Summary",
            type: "item",
            icon: "fas fa-poll",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_PAPER_REVIEW_VIEW,
            url: SETTINGS.PAGES.facilityReview,
          },

          {
            id: "paper-transfer",
            title: "Paper Transfer",
            type: "item",
            icon: "fas fa-random",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_TRANSFER_FACILITY_PAPER,
            url: SETTINGS.PAGES.facilityPaperTransfer,
          },
        ],
      },

      {
        id: "bccReporting",
        title: "BCC/EAC Reporting",
        type: "item",
        icon: "fas fa-money-check",
        privileges:
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_BCC_PAPER_VIEW +
          "," +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_FACILITY_PAPER_GENERATE_BCC_PAPER,
        url: SETTINGS.PAGES.bccReporting,
        isExternal: false,
      },

      /*{
        'id': 'my-facility-papers',
        'title': 'My Facility Papers',
        'type': 'item',
        'icon': 'fas fa-scroll',
        'privileges': '',
        'url': SETTINGS.PAGES.myFacilityPapers,
        'badge': {
          'id': 'assigned_facility_paper_count',
          'title': '00',
          'bg': '#f07a24',
          'fg': '#FFFFFF'
        }
      },*/
      {
        id: "system",
        title: "System",
        type: "group",
        icon: "fas fa-crosshairs",
        privileges:
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_ROLE_VIEW +
          "," +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_AGENT_VIEW +
          "," +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_UPM_GROUP_VIEW +
          "," +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_USER_DA_VIEW +
          "," +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_SUPPORTING_DOC_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_WORKFLOW_TEMPLATE_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_CREDIT_FACILITY_TYPE_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_CREDIT_FACILITY_TEMPLATE_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_UPC_SECTION_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_TOPIC_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_TOPIC_CONFIG_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_UPC_TEMPLATE_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_COMMITTEE_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_COMMITTEE_TYPE_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_COMMITTEE_POOL_VIEW +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_ENVIROMENTAL_RISK_TOOL +
          "" +
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_ENVIROMENTAL_RISK_ANNEXURE,
        isExternal: false,
        children: [
          {
            id: "agents",
            title: "Agents",
            type: "item",
            icon: "fas fa-users",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_AGENT_VIEW,
            url: SETTINGS.PAGES.agents,
          },

          {
            id: "roles",
            title: "Roles",
            type: "item",
            icon: "fas fa-dice-d20",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_ROLE_VIEW,
            url: SETTINGS.PAGES.roles,
          },
          {
            id: "upm-group",
            title: "UPM Group",
            type: "item",
            icon: "fas fa-vector-square",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_UPM_GROUP_VIEW,
            url: SETTINGS.PAGES.upmGroup,
          },
          {
            id: "user-delegated-authorities",
            title: "User DA",
            type: "item",
            icon: "fas fa-assistive-listening-systems",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_USER_DA_VIEW,
            url: SETTINGS.PAGES.userDelegatedAuthority,
          },
          {
            id: "support-documents",
            title: "Supporting Document",
            type: "item",
            icon: "fas fa-book-open",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_SUPPORTING_DOC_VIEW,
            url: SETTINGS.PAGES.supportDocuments,
          },
          {
            id: "workflow-template",
            title: "Workflow Template",
            type: "item",
            icon: "fas fa-box",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_WORKFLOW_TEMPLATE_VIEW,
            url: SETTINGS.PAGES.workflowTemplate,
          },
          {
            id: "credit-facility-types",
            title: "Facility Type",
            type: "item",
            icon: "fas fa-money-bill",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_CREDIT_FACILITY_TYPE_VIEW,
            url: SETTINGS.PAGES.creditFacilityTypes,
          },
          {
            id: "credit-facility-type-templates",
            title: "Facility Template",
            type: "item",
            icon: "fas fa-pallet",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_CREDIT_FACILITY_TEMPLATE_VIEW,
            url: SETTINGS.PAGES.creditFacilityTypeTemplates,
          },
          {
            id: "section-sub-section",
            title: "UPC Section",
            type: "item",
            icon: "fas fa-map",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_UPC_SECTION_VIEW,
            url: SETTINGS.PAGES.upcSetions,
          },
          {
            id: "upc-template",
            title: "UPC Template",
            type: "item",
            icon: "fas fa-drafting-compass",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_UPC_TEMPLATE_VIEW,
            url: SETTINGS.PAGES.upcTemplate,
          },
          {
            id: "application-form-topics",
            title: "Application Topics",
            type: "item",
            icon: "fab fa-autoprefixer",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_TOPIC_VIEW,
            url: SETTINGS.PAGES.applicationFormTopics,
          },
          {
            id: "application-topics-config",
            title: "Application Topics Config",
            type: "item",
            icon: "fab fa-codepen",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_APPLICATION_TOPIC_CONFIG_VIEW,
            url: SETTINGS.PAGES.applicationFormTopicsConfig,
          },
          {
            id: "committee-pool",
            title: "Committee Pool",
            type: "item",
            icon: "fas fa-users",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_COMMITTEE_POOL_VIEW,
            url: SETTINGS.PAGES.committeePool,
          },
          {
            id: "committee-type",
            title: "Committee Type",
            type: "item",
            icon: "fas fa-users",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_COMMITTEE_TYPE_VIEW,
            url: SETTINGS.PAGES.committeeType,
          },
          {
            id: "committee",
            title: "Committee",
            type: "item",
            icon: "fas fa-users",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_COMMITTEE_VIEW,
            url: SETTINGS.PAGES.committee,
          },
          {
            id: "environmental-risk-tool",
            title: "ESG Tool",
            type: "item",
            icon: "fas fa-chart-line",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_ENVIROMENTAL_RISK_TOOL,
            url: SETTINGS.PAGES.environmentalRiskTool,
          },
          {
            id: "environmental-risk-annexure",
            title: "ESG Annexure",
            type: "item",
            icon: "fas fa-chart-line",
            privileges:
              SETTINGS.PRIVILEGES.ICAS_SETTINGS_ENVIROMENTAL_RISK_ANNEXURE,
            url: SETTINGS.PAGES.environmentalRiskAnnexure,
          },
          {
            id: "deviation",
            title: "Deviation",
            type: "item",
            icon: "fas fa-chart-line",
            url: SETTINGS.PAGES.diviation,
          },
          {
            id: "deviation-types",
            title: "Deviation Types",
            type: "item",
            icon: "fas fa-chart-line",
            url: SETTINGS.PAGES.diviationType,
          },
        ],
      },

      {
        id: "audit",
        title: "Audit",
        type: "item",
        icon: "fas fa-file-alt",
        privileges: SETTINGS.PRIVILEGES.ICAS_WEB_AUDIT_VIEW,
        url: SETTINGS.PAGES.audit,
      },
      {
        id: "da",
        title: "DA",
        type: "group",
        icon: "fas fa-street-view",
        privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_USER_DATABLE_VIEW,
        isExternal: false,
        children: [
          {
            id: "da-table",
            title: "DA Table",
            type: "item",
            icon: "fas fa-tachometer-alt",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_USER_DATABLE_VIEW,
            url: SETTINGS.PAGES.da,
          },
          // {
          //   'id': 'da-designation',
          //   'title': 'DA Designation',
          //    'type': 'item',
          //    'icon': 'fas fa-tachometer-alt',
          //   'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_USER_DATABLE_VIEW,
          //   'url': SETTINGS.PAGES.daDesignation,
          // }
        ],
      },

      {
        id: "covering-approval",
        title: "Covering Approval",
        type: "group",
        icon: "fas fa-wallet",
        privileges:
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_VIEW +
          "," +
          // + SETTINGS.PRIVILEGES.ICAS_SETTINGS_MY_BRANCH_LEAD_VIEW + ','
          SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_ADD,
        children: [
          {
            id: "covering-approval",
            title: "Dashboard",
            type: "item",
            icon: "fas fa-tachometer-alt",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_VIEW,
            url: SETTINGS.PAGES.coveringApproval,
          },
          // {
          //   'id': 'create',
          //   'title': 'Create',
          //   'type': 'item',
          //   'icon': 'fas fa-user-plus',
          //   'privileges': SETTINGS.PRIVILEGES.ICAS_SETTINGS_LEAD_ADD,
          //   'url': SETTINGS.PAGES.coveringApproval,
          //   'badge': {
          //     'id': 'branch_lead_pending_count',
          //     'title': '00',
          //     'bg': '#3f729b',
          //     'fg': '#FFFFFF'
          //   }
          // },
        ],
      },
      {
        id: "cas-v1",
        title: "CAS V1",
        type: "group",
        icon: "fas fa-file-invoice",
        privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_FACILITY_PAPER_VIEW,
        isExternal: false,
        children: [
          {
            id: "paper",
            title: "Facility Paper",
            type: "item",
            icon: "fas fa-tachometer-alt",
            privileges: SETTINGS.PRIVILEGES.ICAS_SETTINGS_FACILITY_PAPER_VIEW,
            url: SETTINGS.PAGES.casv1Paper,
          },
        ],
      },
    ];
  }
}
