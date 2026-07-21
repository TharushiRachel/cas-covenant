export interface LeadCompPartiesDTO {
    relatedPartyId?: number
    compLeadId: number
    mainParty: number
    relatedParty: number
    noRelationship: boolean
    relationshipDescription: string,
    share: string,
}
