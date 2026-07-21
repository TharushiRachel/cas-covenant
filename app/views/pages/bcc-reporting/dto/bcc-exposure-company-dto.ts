export class BccExposureCompanyDTO {
  bccExposureCompanyID;
  boardCreditCommitteePaperID;
  existingExposureDirect;
  existingExposureIndirect;
  existingExposureTotal;
  additionalExposureDirect;
  additionalExposureIndirect;
  additionalExposureTotal;
  totalExposureDirect;
  totalExposureIndirect;
  totalExposureTotal;
  type;
  exposureSecuredBy;
  approvedFSV;
  againstApprovedFSV;
  againstTotalExposure;
  status;

  constructor(dto) {
    this.bccExposureCompanyID = dto.bccExposureCompanyID || '';
    this.boardCreditCommitteePaperID = dto.boardCreditCommitteePaperID || '';
    this.existingExposureDirect = dto.existingExposureDirect || '';
    this.existingExposureIndirect = dto.existingExposureIndirect || '';
    this.existingExposureTotal = dto.existingExposureTotal || '';
    this.additionalExposureDirect = dto.additionalExposureDirect || '';
    this.additionalExposureIndirect = dto.additionalExposureIndirect || '';
    this.additionalExposureTotal = dto.additionalExposureTotal || '';
    this.totalExposureDirect = dto.totalExposureDirect || '';
    this.totalExposureIndirect = dto.totalExposureIndirect || '';
    this.totalExposureTotal = dto.totalExposureTotal || '';
    this.type = dto.type || '';
    this.exposureSecuredBy = dto.exposureSecuredBy || '';
    this.approvedFSV = dto.approvedFSV || '';
    this.againstApprovedFSV = dto.againstApprovedFSV || '';
    this.againstTotalExposure = dto.againstTotalExposure || '';
    this.status = dto.status || '';
  }


}
