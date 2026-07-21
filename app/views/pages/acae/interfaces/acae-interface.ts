export interface IAcaePaperApproveRQ {
  accountNumber: string;
  referenceNumber: string;
  sanctionLimit: string;
  currentUsername: string;
}

export interface IACAEAccountTypeRQ {
  accountNumber: string;
  refNumber: string;
}

export interface IACAECurrentUserDTO {
  userName: string;
  userLevel: string;
  branch: string;
}