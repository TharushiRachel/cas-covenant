export interface IncomeSource {
  compPartyId: number,
  incomeType: string; // kept flexible to match `data.creationType`
  considerForRepayment: string;
}