export class CftInterestRateUpdateDto {

  cftInterestRateID;
  creditFacilityTemplateID;
  rateName;
  rateCode;
  value;
  isDefault;
  status;
  interestRatingSubCategory;
  isEditable;

  constructor(cftInterestRateUpdateDto) {
    cftInterestRateUpdateDto = cftInterestRateUpdateDto || {};
    this.cftInterestRateID = cftInterestRateUpdateDto.cftInterestRateID || '';
    this.creditFacilityTemplateID = cftInterestRateUpdateDto.creditFacilityTemplateID || '';
    this.rateName = cftInterestRateUpdateDto.rateName || '';
    this.rateCode = cftInterestRateUpdateDto.rateCode || '';
    this.value = cftInterestRateUpdateDto.value || '';
    this.isDefault = cftInterestRateUpdateDto.isDefault || '';
    this.status = cftInterestRateUpdateDto.status || '';
    this.interestRatingSubCategory = cftInterestRateUpdateDto.interestRatingSubCategory || '';
    this.isEditable = cftInterestRateUpdateDto.isEditable || '';
  }
}
