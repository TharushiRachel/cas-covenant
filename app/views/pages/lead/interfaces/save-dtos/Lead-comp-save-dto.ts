
// Enums / literal types for constrained fields
type YesNo = 'Y' | 'N';
type CreationType = 'PERSONAL' | 'BUSINESS' | 'OTHER';
type LeadType = 'EXTERNAL' | 'INTERNAL';

export interface SaveObj {
  compLeadId?: number | null
  leadId?: number,
  leadName: string;
  preferredBranch: string;
  creationType: string;
  createdBy: string;
  createdUserWorkClass: string;
  parties: Party[];
  leadPurpose: string;
  saveLeadDTO: SaveLeadDTO;
}

export interface Party {
  compPartyId: number
  compLeadId: number | null;

  creationType: CreationType | string; // kept flexible to match `data.creationType`
  partyType: string;
  accountNumber: string | null;
  civilStatus: string | null;
  dateOfBirth: string | Date | null; // if you pass Date, serialize to ISO later
  email: string | null;
  title: string;
  personalName: string;
  mobileNumber: string | null;
  createdBy: string;
  addresses: Address[];
  identifications: Identification[];
  considerCrib: string;
  considerAA: string;
  gender: string;
  contactPerson: string,
  designation: string,
  typeOfBusiness: string,
}


interface Address {
  address1: string;
  address2: string;
  city: string;
}

interface Identification {
  identificationType: string;
  identificationNumber: string | null;
}

interface SaveLeadDTO {
  leadId?: number | null,
  branchCode: string;
  // name: string;
  // nicNumber: string;
  branchName: string;
  customerBankAccountNumber: string;
  leadType: string;
  assignUserID: number;
  submitted: string;
  createdUserDisplayName: string;
  assignUserDisplayName: string;
  applicationFormID: number;
  createdDate: string;           // ISO string
  createdBy: string;
  lastModifiedDate: string;      // ISO string
  modifiedBy: string;
  isCompLead: string;
  leadCreationType: string;
}

