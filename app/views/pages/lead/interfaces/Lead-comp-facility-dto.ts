export interface LeadCompFacilityDTO {
    compFacilityId?: number;
    compLeadId: number;
    facilityGroupId: number;
    facilityTemplateId: number;
    facilityDescription: string;
    requestedTenure: number;
    leaseRental: number;
    processingFee: number;
    effectiveRate: number;
    leaseAmount: number;
    repaymentMode: string;
    upfront: number;
    insurance?: number;
    validityOfOffer?: string;
    model: string;
    make: string;
    createdBy: string;
    facilityType: string
}