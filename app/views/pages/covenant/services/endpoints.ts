import { Config } from "src/app/core/setting/config";
import { EndpointConfig } from "src/app/shared/interfaces/EndpointConfig";

export class COVENANT_SETTINGS {
  public static readonly ENDPOINTS = {
    saveCustomerCovenant: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/saveCustomerCovenant`,
      type: "POST",
    }),

    updateCustomerCovenant: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/updateCustomerCovenant`,
      type: "POST",
    }),

    getAllCustomerCovenant: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/getAllCustomerCovenant`,
      type: "GET",
    }),

    saveFacilityCovenants: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/saveFacilityCovenants`,
      type: "POST",
    }),

    updateFacilityCovenant: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/updateFacilityCovenant`,
      type: "POST",
    }),

    getAllFacilityCovenant: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/getAllFacilityCovenant`,
      type: "GET",
    }),

    getCovenantsDetails: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/getCovenantsDetails`,
      type: "POST",
    }),

    getCovenantList: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/getCovenantList`,
      type: "POST",
    }),

    addCommentToCovenant: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: false,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/addCommentToCovenant`,
      type: "POST",
    }),

    getCovenantCommentList: createEndpoint({
      headerParam: {
        showLoading: true,
        showToast: true,
        skipAuth: false,
      },
      url: `${Config.getCovenantContextPath()}/covenant/getCovenantCommentList`,
      type: "GET",
    }),
  };
}

function createEndpoint(item: EndpointConfig) {
  return item;
}
