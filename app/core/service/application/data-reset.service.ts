import {Injectable} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../setting/commons.settings";

@Injectable()
export class DataResetService {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_ROLE_ID)
  selectedRoleID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_USER_ID)
  selectedUserID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_SYSTEM_PARAM_ID)
  selectedSystemParamID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_UPC_SECTION_ID)
  selectedUPCSectionID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_SUPPORTING_DOC_ID)
  selectedSupporingDocID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_UPC_TEMPLATE_ID)
  selectedItemID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_UPM_GROUP_ID)
  selectedUpmGroupID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_USER_DA_ID)
  selectedUserDaID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_WORKFLOW_TEMPLATE_ID)
  selectedworkFlowID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CREDIT_FACILITY_TEMPLATE_ID)
  selectedCreditFacilityTemplateID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_CREDIT_FACILITY_TYPE_ID)
  selectedCreditFacilityTypeID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_AGENT_ID)
  selectedAgentID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_ASSIGNED_USER_ID)
  selectedAssignUserID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_REVIEW)
  selectedFacilityPaperIDForReview;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING)
  selectedFacilityPaperIDForBccReport;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_TOPIC_ID)
  selectedApplicationFormTopicID;

  constructor() {
  }

  resetData() {
    this.selectedRoleID = null;
    this.selectedUserID = null;
    this.selectedSystemParamID = null;
    this.selectedLeadID = null;
    this.selectedUPCSectionID = null;
    this.selectedSupporingDocID = null;
    this.selectedItemID = null;
    this.selectedUpmGroupID = null;
    this.selectedUserDaID = null;
    this.selectedworkFlowID = null;
    this.selectedCreditFacilityTemplateID = null;
    this.selectedCreditFacilityTypeID = null;
    this.selectedAgentID = null;
    this.selectedAssignUserID = null;
    this.selectedFacilityPaperIDForReview = null;
    this.selectedFacilityPaperIDForBccReport = null;
    this.selectedApplicationFormID = null;
    this.selectedApplicationFormTopicID = null;

  }

}
