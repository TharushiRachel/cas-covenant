export class UpcTemplateDto {
  upcTemplateID;
  templateName;
  description;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;
  upcLabel;
  upcLabelFontColor;
  upcLabelBackgroundColor;
  upcTemplateDataDTOList;
  emails: string;

  constructor(upcTemplateDto) {
    upcTemplateDto = upcTemplateDto || {};
    this.upcTemplateID = upcTemplateDto.upcTemplateID || "";
    this.templateName = upcTemplateDto.templateName || "";
    this.description = upcTemplateDto.description || "";
    this.status = upcTemplateDto.status || "ACT";
    this.approveStatus = upcTemplateDto.approveStatus || "PENDING";
    this.approvedDateStr = upcTemplateDto.approvedDateStr || "";
    this.approvedBy = upcTemplateDto.approvedBy || "";
    this.createdDateStr = upcTemplateDto.createdBy || "";
    this.createdBy = upcTemplateDto.createdBy || "";
    this.modifiedDateStr = upcTemplateDto.modifiedDateStr || "";
    this.modifiedBy = upcTemplateDto.modifiedBy || "";
    this.upcLabel = upcTemplateDto.upcLabel || "";
    this.upcLabelFontColor = upcTemplateDto.upcLabelFontColor || "";
    this.upcLabelBackgroundColor = upcTemplateDto.upcLabelBackgroundColor || "";
    this.upcTemplateDataDTOList = upcTemplateDto.upcTemplateDataDTOList || [];
    this.emails = upcTemplateDto.emails || "";
  }
}
