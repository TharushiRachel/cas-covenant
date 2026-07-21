export class BoardCreditCommitteePaperDTO {
  boardCreditCommitteePaperID;
  bccRefNumber;
  facilityPaperID;
  paperType;
  branchCode;
  createdUserDisplayName;
  createdUserName;
  upmID;
  customerName;
  startedCapital;

  businessProfile;

  interestCover;
  gearing;
  netInterestMargin;
  grossNPL;
  marketPosition;
  businessManagementStrength;
  securityCover;
  financialStability;
  auditorName;

  financialYearOne;
  financialYearOneFinancial;
  financialYearTwo;
  financialYearTwoFinancial;

  incomeAmountYearOne;
  incomeGrowthYearOne;
  incomeAmountYearTwo;
  incomeGrowthYearTwo;
  profitAfterTaxAmountYearOne;
  profitAfterTaxGrowthYearOne;
  profitAfterTaxAmountYearTwo;
  profitAfterTaxGrowthYearTwo;

  costOfFundMonth;
  costOfFundYear;
  monthlyCostOfFundsLkr;
  monthlyCostOfFundsFcy;
  cumulativeCostOfFundsLkr;
  cumulativeCostOfFundsFcy;
  incrementalCostOfFundsLkr;
  incrementalCostOfFundsFcy;
  recommendation;
  recommendationRemark;

  justification;
  riskBasedPricing;

  status;
  bccFacilityDTOS = [];
  bccCompanyDirectorDTOS = [];
  bccRiskRatingYearDTOS = [];
  bccCustomerCribDetailDTOS = [];
  existingOutstandingAtDate;
  proposedOutstandingAtDate;
  proposedFacilityTotal;
  existingFacilityTotal;
  existingPlusProposedTotal;

  facilityDateOfApproval;
  commonSecurityText;
  note;
  bccExposureGroupDTO;
  bccExposureCompanyDTO;

  exceptions;
  officerOne;
  officerTwo;
  officerThree;
  officerFour;
  officerOneDesignation;
  officerTwoDesignation;
  officerThreeDesignation;
  officerFourDesignation;
  eacMemberOne;
  eacMemberTwo;
  eacMemberThree;
  eacMemberFour;
  eacMemberOneDesignation;
  eacMemberTwoDesignation;
  eacMemberThreeDesignation;
  eacMemberFourDesignation;
  pdfReport;
  currentAssignUser;
  assignUserDisplayName;
  currentAssignUserDivCode;
  isSubmitted;

  constructor(dto:any) {
    dto = dto || {};
    this.boardCreditCommitteePaperID = dto.boardCreditCommitteePaperID || "";
    this.bccRefNumber = dto.bccRefNumber || "";
    this.facilityPaperID = dto.facilityPaperID || "";
    this.paperType = dto.paperType || "";
    this.branchCode = dto.branchCode || "";
    this.createdUserDisplayName = dto.createdUserDisplayName || "";
    this.createdUserName = dto.createdUserName || "";
    this.upmID = dto.upmID || "";

    this.customerName = dto.customerName || "";
    this.startedCapital = dto.startedCapital || "";

    this.businessProfile = dto.businessProfile || "";

    this.interestCover = dto.interestCover || "";
    this.gearing = dto.gearing || "";
    this.netInterestMargin = dto.netInterestMargin || "";
    this.grossNPL = dto.grossNPL || "";
    this.marketPosition = dto.marketPosition || "";
    this.businessManagementStrength = dto.businessManagementStrength || "";
    this.securityCover = dto.securityCover || "";
    this.financialStability = dto.financialStability || "";
    this.auditorName = dto.auditorName || "";

    this.financialYearOne = dto.financialYearOne || "";
    this.financialYearOneFinancial = dto.financialYearOneFinancial || "";
    this.financialYearTwo = dto.financialYearTwo || "";
    this.financialYearTwoFinancial = dto.financialYearTwoFinancial || "";

    this.incomeAmountYearOne = dto.incomeAmountYearOne || "";
    this.incomeGrowthYearOne = dto.incomeGrowthYearOne || "";
    this.incomeAmountYearTwo = dto.incomeAmountYearTwo || "";
    this.incomeGrowthYearTwo = dto.incomeGrowthYearTwo || "";
    this.profitAfterTaxAmountYearOne = dto.profitAfterTaxAmountYearOne || "";
    this.profitAfterTaxGrowthYearOne = dto.profitAfterTaxGrowthYearOne || "";
    this.profitAfterTaxAmountYearTwo = dto.profitAfterTaxAmountYearTwo || "";
    this.profitAfterTaxGrowthYearTwo = dto.profitAfterTaxGrowthYearTwo || "";

    this.costOfFundMonth = dto.costOfFundMonth || "";
    this.costOfFundYear = dto.costOfFundYear || "";
    this.monthlyCostOfFundsLkr = dto.monthlyCostOfFundsLkr || "";
    this.monthlyCostOfFundsFcy = dto.monthlyCostOfFundsFcy || "";
    this.cumulativeCostOfFundsLkr = dto.cumulativeCostOfFundsLkr || "";
    this.cumulativeCostOfFundsFcy = dto.cumulativeCostOfFundsFcy || "";
    this.incrementalCostOfFundsLkr = dto.incrementalCostOfFundsLkr || "";
    this.incrementalCostOfFundsFcy = dto.incrementalCostOfFundsFcy || "";
    this.recommendation = dto.recommendation || null;
    this.recommendationRemark = dto.recommendationRemark || null;
    this.justification = dto.justification || "";
    this.riskBasedPricing = dto.riskBasedPricing || "";
    this.bccFacilityDTOS = dto.bccFacilityDTOS || [];
    this.bccCompanyDirectorDTOS = dto.bccCompanyDirectorDTOS || [];
    this.bccRiskRatingYearDTOS = dto.bccRiskRatingYearDTOS || [];
    this.bccCustomerCribDetailDTOS = dto.bccCustomerCribDetailDTOS || [];

    this.existingOutstandingAtDate = dto.existingOutstandingAtDate || "";
    this.proposedOutstandingAtDate = dto.proposedOutstandingAtDate || "";
    this.proposedFacilityTotal = dto.proposedFacilityTotal || "";
    this.existingFacilityTotal = dto.existingFacilityTotal || "";
    this.existingPlusProposedTotal = dto.existingPlusProposedTotal || "";
    this.facilityDateOfApproval = dto.facilityDateOfApproval || "";
    this.commonSecurityText = dto.commonSecurityText || "";
    this.note = dto.note || "";

    this.bccExposureGroupDTO = dto.bccExposureGroupDTO || {};
    this.bccExposureCompanyDTO = dto.bccExposureCompanyDTO || {};
    this.exceptions = dto.exceptions || "";

    this.officerOne = dto.officerOne || "";
    this.officerTwo = dto.officerTwo || "";
    this.officerThree = dto.officerThree || "";
    this.officerFour = dto.officerFour || "";
    this.officerOneDesignation = dto.officerOneDesignation || "";
    this.officerTwoDesignation = dto.officerTwoDesignation || "";
    this.officerThreeDesignation = dto.officerThreeDesignation || "";
    this.officerFourDesignation = dto.officerFourDesignation || "";

    this.eacMemberOne = dto.eacMemberOne || "";
    this.eacMemberTwo = dto.eacMemberTwo || "";
    this.eacMemberThree = dto.eacMemberThree || "";
    this.eacMemberFour = dto.eacMemberFour || "";
    this.eacMemberOneDesignation = dto.eacMemberOneDesignation || "";
    this.eacMemberTwoDesignation = dto.eacMemberTwoDesignation || "";
    this.eacMemberThreeDesignation = dto.eacMemberThreeDesignation || "";
    this.eacMemberFourDesignation = dto.eacMemberFourDesignation || "";
    this.pdfReport = dto.pdfReport || "";

    this.status = dto.status || "ACT";
    this.currentAssignUser = dto.currentAssignUser || "";
    this.assignUserDisplayName = dto.assignUserDisplayName || "";
    this.currentAssignUserDivCode = dto.currentAssignUserDivCode || "";
    this.isSubmitted = dto.isSubmitted || "N";
  }
}
