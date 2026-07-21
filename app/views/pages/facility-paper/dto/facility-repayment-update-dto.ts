export class FacilityRepaymentUpdateDto {
  facilityRepaymentID;
  facilityID;
  facilityPaperID;
  loanTerm;
  repaymentType;
  paymentFrequency;
  paymentDetail;
  downPayment;
  repaymentComment;

  constructor(dto){
    dto = dto || {};
    this.facilityRepaymentID = dto.facilityRepaymentID || '';
    this.facilityID = dto.facilityID || '';
    this.facilityPaperID =  dto.facilityPaperID || '';
    this.loanTerm = dto.loanTerm || '';
    this.repaymentType = dto.repaymentType || '';
    this.paymentFrequency = dto.paymentFrequency || '';
    this.paymentDetail = dto.paymentDetail || '';
    this.downPayment = dto.downPayment || '';
    this.repaymentComment = dto.repaymentComment || '';
  }
}
