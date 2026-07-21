export interface EndpointConfig {
  url: string;
  type: string;
  pathvarible?: string;
  headerParam: {
    showLoading: boolean;
    showToast: boolean;
    skipAuth: boolean;
    isFileUpload?: boolean,
  };
}
