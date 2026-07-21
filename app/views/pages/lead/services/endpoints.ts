import { Config } from "src/app/core/setting/config";
import { EndpointConfig } from "src/app/shared/interfaces/EndpointConfig";

export class LEAD_SETTINGS {
  public static readonly ENDPOINTS = {
    saveComprehensiveLead: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/saveComprehensiveLead`,
      type: "POST",
    }),

    saveRelatedPartiesLead: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/related-parties`,
      type: "POST",
    }),

    saveIncomeSourceLead: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/income-sources`,
      type: "POST",
    }),

    savePartiesLead: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/parties`,
      type: "POST",
    }),

    saveFacilitiesLead: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/facilities`,
      type: "POST",
    }),

    getIndividualCribDetails: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getAAContextPath()}/searchIndividualCrib`,
      type: "POST",
    }),

    getCompanyCribDetails: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getAAContextPath()}/searchCompanyCrib`,
      type: "POST",
    }),

    getIndividualLeasingAA: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `/cas/api/lead/getIndividualLeasingAA`,
      type: "POST",
    }),
    getLeadById: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/getLeadById`,
      type: "GET",
    }),

    deactivateParty: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/deactivateParty`,
      type: "POST",
    }),
    deactivateIncomeSource: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/deactivateIncomeSource`,
      type: "POST",
    }),
    deactivateRelatedParty: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/deactivateRelatedParty`,
      type: "POST",
    }),
    deactivateFacility: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/deactivateFacility`,
      type: "POST",
    }),

    getDigitalApplication: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `/cas/api/lead/getDigitalizedApplication`,
      type: "GET",
    }),

    getDigitalApplications: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/getDigitalApplications`,
      type: "GET",
    }),

    getDigitalApplicationById: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/getDigitalApplicationById`,
      type: "GET",
    }),

    saveDigitalApplication: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/saveOrUpdateDigitalApplication`,
      type: "POST",
    }),

    saveApplication: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/saveApplication`,
      type: "POST",
    }),

    getLeadStatus: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/getLeadStatus`,
      type: "GET",
    }),

    saveComprehensiveLeadComment: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/saveOrUpdateComment`,
      type: "POST",
    }),

    saveComprehensiveLeadDocument: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/saveComprehensiveLeadDocument`,
      type: "POST",
    }),

    uploadLeadDocument: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
        isFileUpload: true,
      },
      url: `${Config.getLeadContextPath()}/uploadLeadDocument`,
      type: "POST",
    }),

    deleteLeadDocument: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/deleteLeadDocument`,
      type: "GET",
    }),

    viewLeadDocument: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/viewLeadDocument`,
      type: "GET",
    }),

    deleteDigitalApplication: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/digitalApplication`,
      type: "GET",
    }),

    getLeasingJourneyValidation: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `/cas/api/advanceAnalytics/getLeasingJourneyValidation`,
      type: "POST",
    }),

    getDigitalApplicationContentWithApplicants: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },

      url: `/cas/api/lead/getDigitalizedApplication`,
      type: "POST",
    }),

    leadStatusHistory: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getLeadContextPath()}/leadStatusHistory`,
      type: "GET",
    }),
  };
}

function createEndpoint(item: EndpointConfig) {
  return item;
}
