export class FacilityReviewsSearchFormDTO {

  customerName: string;
  fpRefNumber: string;
  paperReviewStatusList: any[];
  currentAssignedUserID: number;

  constructor(dto: any) {
    this.customerName = dto.customerName || '';
    this.fpRefNumber = dto.fpRefNumber || '';
    this.paperReviewStatusList = dto.paperReviewStatusList || [];
    this.currentAssignedUserID = dto.currentAssignedUserID || null;
  }
}


