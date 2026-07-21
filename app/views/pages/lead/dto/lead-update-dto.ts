import {Constants} from "../../../../core/setting/constants";

export class LeadUpdateDto {
  leadID;
  customerID;
  customerName;
  leadName;
  leadRefNumber;
  name;
  accountNumber;
  mobileNo;
  dateOfBirthStr;
  address;
  civilStatus;
  identificationType;
  identificationNumber;
  nationality;
  branchCode;
  branchName;
  preferredBranch;
  leadStatus;
  assignUserID;
  leadFacilityDetailDTOList;
  leadDocumentDTOList;
  leadActionDTOList;
  leadCommentDTOList;
  leadType;
  externalLead;
  createdBy;

  allowFinacleData;
  allowCrib;
  allowKalypto;
  customerBankAccountNumber;

  isExistingCustomer;
  typeOfBusiness;
  designation;
  leadCreationType;
  contactPerson;
  email;
  submitted;
  customerBankDetailsDTOList;
  leadFsType;
  assignUserDisplayName;
  createdUserDisplayName;
  modifiedDateStr;
  createdDateStr;
  externalAppDescription;
  externalAppRefNumber;

  constructor(leadUpdateDto) {
    leadUpdateDto = leadUpdateDto || {};
    this.leadID = leadUpdateDto.leadID || '';
    this.customerID = leadUpdateDto.customerID || '';
    this.customerName = leadUpdateDto.customerName || '';
    this.leadName = leadUpdateDto.leadName || '';
    this.leadRefNumber = leadUpdateDto.leadRefNumber || '';
    this.name = leadUpdateDto.name || '';
    this.accountNumber = leadUpdateDto.accountNumber || '';
    this.mobileNo = leadUpdateDto.mobileNo || '';
    this.dateOfBirthStr = leadUpdateDto.dateOfBirthStr || '';
    this.civilStatus = leadUpdateDto.civilStatus || null;
    this.address = leadUpdateDto.address || '';
    this.identificationNumber = leadUpdateDto.identificationNumber || '';
    this.identificationType = leadUpdateDto.identificationType || '';
    this.nationality = leadUpdateDto.nationality || '';
    this.branchCode = leadUpdateDto.branchCode || '';
    this.branchName = leadUpdateDto.branchName || '';
    this.preferredBranch = leadUpdateDto.preferredBranch || '';
    this.leadStatus = leadUpdateDto.leadStatus || 'PENDING';
    this.assignUserID = leadUpdateDto.assignUserID || '';
    this.leadType = leadUpdateDto.leadType || Constants.leadTypeConst.INTERNAL;
    this.externalLead = leadUpdateDto.externalLead || false;
    this.createdBy = leadUpdateDto.createdBy || '';
    this.allowFinacleData = leadUpdateDto.allowFinacleData || false;
    this.allowCrib = leadUpdateDto.allowCrib || false;
    this.allowKalypto = leadUpdateDto.allowKalypto || false;
    this.customerBankAccountNumber = leadUpdateDto.customerBankAccountNumber || '';
    this.leadFacilityDetailDTOList = leadUpdateDto.leadFacilityDetailDTOList || [];
    this.leadDocumentDTOList = leadUpdateDto.leadDocumentDTOList || [];
    this.leadCommentDTOList = leadUpdateDto.leadCommentDTOList || [];
    this.leadActionDTOList = leadUpdateDto.leadActionDTOList || [];
    this.isExistingCustomer = leadUpdateDto.isExistingCustomer || 'N';
    this.typeOfBusiness = leadUpdateDto.typeOfBusiness || '';
    this.designation = leadUpdateDto.designation || '';
    this.leadCreationType = leadUpdateDto.leadCreationType || 'PERSONAL';
    this.contactPerson = leadUpdateDto.contactPerson || '';
    this.email = leadUpdateDto.email || '';
    this.submitted = leadUpdateDto.submitted || 'N';
    this.customerBankDetailsDTOList = leadUpdateDto.customerBankDetailsDTOList || [];
    this.leadFsType = leadUpdateDto.leadFsType || '';
    this.assignUserDisplayName = leadUpdateDto.assignUserDisplayName || '';
    this.createdUserDisplayName = leadUpdateDto.createdUserDisplayName || '';
    this.createdDateStr = leadUpdateDto.createdDateStr || '';
    this.modifiedDateStr = leadUpdateDto.modifiedDateStr || '';
    this.externalAppDescription = leadUpdateDto.externalAppDescription || '';
    this.externalAppRefNumber = leadUpdateDto.externalAppRefNumber || '';
  }

  isInternalLead() {
    return this.leadType == Constants.leadTypeConst.INTERNAL;
  }

  isExternalLead() {
    return this.leadType == Constants.leadTypeConst.EXTERNAL;
  }

  isSubmitted() {
    return this.submitted === 'Y';
  }

}
