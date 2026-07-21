export interface LeadCompBorrowerDTO {
  compPartyId: number | null;
  compLeadId: number | null;
  creationType: string;
  partyType: string;
  accountNumber: string;
  civilStatus: string;
  dateOfBirth: string;
  title: string;
  personalName: string;
  email: string;
  mobileNumber: string;
  identifications: Identifications[];
  address1: string;
  address2: string;
  city: string;
  contactPerson: string
  designation: string
  typeOfBusiness: string
  gender: string;
  isBusiness: boolean;
  addresses: Address[];
  isCreationTypeEnable: boolean
}

export interface Identifications {
  compPartyId?: string | null,
  identificationType: string;
  identificationNumber: string;
  identificationId?: string | null,
  modifiedBy?: string | null,
  createdDate?: string | null,
  modifiedDate?: string | null,
  createdBy: string;
}

export interface Address {
  address1: string;
  address2: string;
  city: string;
  compPartyId: number;
  addressesId: number;
}

export interface CribIdentification {
  identificationType: string;
  identificationNumber: string;
  isCribCheck: boolean;
}

export interface BorrowerCrib {
  id: number;
  personalName: string;
  gender: string;
  identifications: CribIdentification[];
  creationType: string;
  customerType: string;
  borrowerType: string;
  isAAEligible: boolean;
  inquiryReason: string;
}

export interface LeadStatus {
  status: string;
  createdBy: string;
  assignUserId: string;
  leadRefNumber: string;
}

export interface AnalyticsDecision {
  decision: string;
  decisionStatus: string;
  channel: string;
  createdBy: string;
  createdDate: Date;
  createdDateStr: string;
  facilityData: string;
}

export interface LeaseJourneyRequestDTO {
  leadId: number;
  leadRef: string;
  journeyType: string;
  facility: FacilityRequestDTO;
  company: BorrowerRequestDTO[];
  individuals: BorrowerRequestDTO[];
}

export interface FacilityRequestDTO {
  facilityType: string;
  facilityAmount: number;
  tenure: string;
}

export interface BorrowerRequestDTO {
  name: string;
  type: string;
  principal: BorrowerPrincipalRequestDTO[];
  cribRequests: CRIBRequestDTO[];
}

export interface CRIBRequestDTO {
  identityDoc: string;
  identityRef: string;
  reportJson: any;
}

export interface BorrowerPrincipalRequestDTO {
  relationship: string;
  identityDoc: string;
  identityRef: string;
  share: string;
}
