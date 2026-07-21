export interface CribRequest {
  applicationNumber: string;
  applicationDate: string;
  fullName: string;
  gender: string;
  creditFacilityType: string;
  creditFacilityCurrency: string;
  reportDate: string;
  creditFacilityAmountDTO: CreditFacilityAmount;
  idNumberDTOList: IDNumber[];
  inquiryReason: string;
}

export interface CreditFacilityAmount {
  value: number;
  currency: string;
  localValue: string;
}

export interface IDNumber {
  idNumberType: string;
  idNumber: string;
}
