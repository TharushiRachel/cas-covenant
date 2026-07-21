export class FacilityUpdateDto{
  creditFacilityTemplateID;
  creditFacilityTemplateDTO;
  facilityPaperID;
  facilityCurrency;
  disbursementAccNumber;
  facilityAmount;
  isCooperate;
  outstandingAmount;
  sectorID;
  subSectorID;
  purposeOfAdvance;
  purpose;
  facilityTypeDTO;
  facilityType;
  isOneOff;
  repayment;
  condition;
  isNew;
  displayOrder;
  remark;
  status;
  facilityDocumentList;
  facilityInterestRateList;
  facilityPurposeOfAdvanceList;
  facilitySecurityDTOList;

  constructor(dto){
    dto = dto || {};
    this.facilityPaperID = dto.facilityPaperID || '';
    this.creditFacilityTemplateID = dto.creditFacilityTemplateID || '';
    this.creditFacilityTemplateDTO = dto.creditFacilityTemplateUpdateDTO || {};
    this.facilityCurrency = dto.facilityCurrency || '';
    this.disbursementAccNumber = dto.disbursementAccNumber || '';
    this.facilityAmount = dto.facilityAmount || '';
    this.isCooperate = dto.isCooperate || '';
    this.outstandingAmount = dto.outstandingAmount || '';
    this.sectorID = dto.sectorID || '';
    this.subSectorID = dto.subSectorID || '';
    this.purposeOfAdvance = dto.purposeOfAdvance || '';
    this.facilityTypeDTO = dto.facilityTypeDTO || '';
    this.facilityType = dto.facilityTypeName || '';
    this.isOneOff = dto.isOneOff || '';
    this.repayment = dto.repayment || '';
    this.condition = dto.condition || '';
    this.isNew = dto.isNew || '';
    this.displayOrder = dto.displayOrder || '';
    this.remark = dto.remark || '';
    this.status = dto.status || '';
    this.facilityDocumentList = dto.facilityDocumentList || [];
    this.facilityInterestRateList = dto.facilityInterestRateList || [];
    this.facilityPurposeOfAdvanceList = dto.facilityPurposeOfAdvanceList || [];
    this.facilitySecurityDTOList = dto.facilitySecurityDTOList || [];

  }
}
