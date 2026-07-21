export class CftVitalQuestionUpdateDto {

  cftVitalInfoID;
  creditFacilityTemplate;
  vitalInfoName;
  mandatory;
  displayOrder;
  status;

  constructor(cftVitalQuestionUpdateDto) {
    cftVitalQuestionUpdateDto = cftVitalQuestionUpdateDto || {};
    this.cftVitalInfoID = cftVitalQuestionUpdateDto.cftVitalInfoID || '';
    this.creditFacilityTemplate = cftVitalQuestionUpdateDto.creditFacilityTemplate || '';
    this.vitalInfoName = cftVitalQuestionUpdateDto.vitalInfoName || '';
    this.mandatory = cftVitalQuestionUpdateDto.mandatory || '';
    this.displayOrder = cftVitalQuestionUpdateDto.displayOrder || 0;
    this.status = cftVitalQuestionUpdateDto.status || '';
  }

}
