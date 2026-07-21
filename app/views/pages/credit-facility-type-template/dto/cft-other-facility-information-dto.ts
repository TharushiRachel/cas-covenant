export class CftOtherFacilityInformationDto {

  cftOtherFacilityInfoID: string;
  creditFacilityTemplate: any;
  otherFacilityInfoName: string;
  otherFacilityInfoCode: string;
  otherFacilityInfoFieldType: string;
  defaultValue: string;
  displayOrder: number;
  description: string;
  mandatory: string;
  status: string;

  constructor(otherFacilityInformationDto) {
    otherFacilityInformationDto = otherFacilityInformationDto || {};
    this.cftOtherFacilityInfoID = otherFacilityInformationDto.cftOtherFacilityInfoID || '';
    this.creditFacilityTemplate = otherFacilityInformationDto.creditFacilityTemplate || '';
    this.otherFacilityInfoName = otherFacilityInformationDto.otherFacilityInfoName || '';
    this.description = otherFacilityInformationDto.description || '';
    this.otherFacilityInfoCode = otherFacilityInformationDto.otherFacilityInfoCode || '';
    this.otherFacilityInfoFieldType = otherFacilityInformationDto.otherFacilityInfoFieldType || '';
    this.defaultValue = otherFacilityInformationDto.defaultValue || '';
    this.displayOrder = otherFacilityInformationDto.displayOrder || 0;
    this.mandatory = otherFacilityInformationDto.mandatory || '';
    this.status = otherFacilityInformationDto.status || '';
  }


}
