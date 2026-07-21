export class CftCustomFacilityInformationDto{

  cftCustomFacilityInfoID: string;
  creditFacilityTemplate: any;
  customFacilityInfoName: string;
  customFacilityInfoCode: string;
  fieldType: string;
  description: string;
  mandatory: string;
  status: string;
  displayOrder: number; 


  constructor(customFacilityInformationDto){
    customFacilityInformationDto = customFacilityInformationDto || {};
    this.cftCustomFacilityInfoID = customFacilityInformationDto.cftCustomFacilityInfoID || '';
    this.creditFacilityTemplate = customFacilityInformationDto.creditFacilityTemplate || '';
    this.customFacilityInfoName = customFacilityInformationDto.customFacilityInfoName || '';
    this.customFacilityInfoCode = customFacilityInformationDto.customFacilityInfoCode || '';
    this.fieldType = customFacilityInformationDto.fieldType || '';
    this.description = customFacilityInformationDto.description || '';
    this.mandatory = customFacilityInformationDto.mandatory || '';
    this.status = customFacilityInformationDto.status || '';
    this.displayOrder = customFacilityInformationDto.displayOrder || 0;
  }
}