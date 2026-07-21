import { LeadCompBorrowerDTO } from "./Lead-comp-borrower-dto";

export interface LeadCompDetailsDTO {
    leadId: number,
    leadName: string,
    parties: LeadCompBorrowerDTO[]
    saveLeadDTO: LeadCompDTO[]
}

export interface LeadCompDTO {
    branchCode: string;
    branchName: string;
    customerBankAccountNumber: string;
    leadType: 'EXTERNAL' | 'INTERNAL'; // restrict to known values if applicable
    assignUserID: number;
    submitted: 'Y' | 'N'; // restrict to Yes/No
    createdUserDisplayName: string;
    assignUserDisplayName: string;
    applicationFormID: number;
    createdDate: string; // ISO date string
    createdBy: string;
    lastModifiedDate: string; // ISO date string
    modifiedBy: string;
}

export interface LabelValue {
    value: number;
    label: string;
}

export interface SLabelValue {
    value: string;
    label: string;
}