export interface RelatedParty {
    relatedPartyId: number;
    compLeadId: number; // kept flexible to match `data.creationType`
    mainPartnerId: number;
    relatedPartnerId: number;
    relationshipDescription: string;
    considerCrib: string
    considerAdvanceAnalysis: string
    share: number,
}
