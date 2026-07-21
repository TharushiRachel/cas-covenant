package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bccpaper.BoardCreditCommitteePaper;
import com.itechro.cas.model.dto.finacle.CompReportingData;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BCCPrintPreviewDTO extends BoardCreditCommitteePaperDTO {


    public BCCPrintPreviewDTO() {
    }

    public BCCPrintPreviewDTO(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        super(boardCreditCommitteePaper);
    }

    public List<BccFacilityDTO> getProposedBccFacilityDTOS() {
        return getBccFacilityDTOS().stream().filter(facility -> facility.getBccFacilityType().equals(DomainConstants.BCCFacilityType.PROPOSED)).collect(Collectors.toList());
    }

    public List<BccFacilityDTO> getExistingBccFacilityDTOS() {
        return getBccFacilityDTOS().stream().filter(facility -> facility.getBccFacilityType().equals(DomainConstants.BCCFacilityType.EXISTING)).collect(Collectors.toList());
    }

    public String getRecommendationValue() {
        if (this.getRecommendation() != null) {
            return this.getRecommendation().getDescription();
        } else {
            return "Not Mentioned";
        }
    }

    public String getCommonSecurityTextValue() {
        if (StringUtils.isNotEmpty(super.getCommonSecurityText())) {
            return super.getCommonSecurityText();
        } else {
            return "No Common Securities Mentioned";
        }
    }

    public String getNoteValue() {
        if (super.getNote() != null) {
            return super.getNote();
        } else {
            return "No special notes Mentioned";
        }
    }

    @Override
    public String getExceptions() {
        if (super.getExceptions() == null) {
            return "No Exceptions";
        }
        return super.getExceptions();
    }


    @Override
    public BccExposureCompanyDTO getBccExposureCompanyDTO() {
        if (super.getBccExposureCompanyDTO() != null) {
            return super.getBccExposureCompanyDTO();
        }
        return new BccExposureCompanyDTO();
    }

    @Override
    public BccExposureGroupDTO getBccExposureGroupDTO() {
        if (super.getBccExposureGroupDTO() != null) {
            return super.getBccExposureGroupDTO();
        }
        return new BccExposureGroupDTO();
    }

    public String getFormattedAmount(Object unFormattedAmount) {
        if (unFormattedAmount != null) {
            DecimalFormat df = new DecimalFormat("#,###,###,###,##0.000");
            return getFormattedFigure(unFormattedAmount);
        } else {
            return null;
        }
    }

    public String getFormattedMnAmount(Object unFormattedAmount) {
        if (unFormattedAmount != null) {
            DecimalFormat df = new DecimalFormat("#,###,###,###,##0.000");
            return df.format(unFormattedAmount);
        } else {
            return null;
        }
    }

    public boolean showCribStatus() {
        return !super.getBccCustomerCribDetailDTOS().isEmpty();
    }

    public boolean showInterestCover() {
        return StringUtils.isNotBlank(super.getInterestCover());
    }

    public boolean showGearing() {
        return StringUtils.isNotBlank(super.getGearing());
    }

    public boolean showNetInterestMargin() {
        return StringUtils.isNotBlank(super.getNetInterestMargin());
    }

    public boolean showGrossNPL() {
        return StringUtils.isNotBlank(super.getGrossNPL());
    }

    public boolean showRecommendationRemark() {
        return StringUtils.isNotBlank(super.getRecommendationRemark());
    }

    public boolean showOfficerOne() {
        return StringUtils.isNotBlank(super.getOfficerOne());
    }

    public boolean showOfficerTwo() {
        return StringUtils.isNotBlank(super.getOfficerTwo());
    }

    public boolean showOfficerThree() {
        return StringUtils.isNotBlank(super.getOfficerThree());
    }

    public boolean showOfficerFour() {
        return StringUtils.isNotBlank(super.getOfficerFour());
    }

    public boolean showEACMemeberOne() {
        return StringUtils.isNotBlank(super.getEacMemberOne());
    }

    public boolean showEACMemberTwo() {
        return StringUtils.isNotBlank(super.getEacMemberTwo());
    }

    public boolean showEACMemberThree() {
        return StringUtils.isNotBlank(super.getEacMemberThree());
    }

    public boolean showEACMemberFour() {
        return StringUtils.isNotBlank(super.getEacMemberFour());
    }

    public boolean showNote() {
        return StringUtils.isNotBlank(super.getNote());
    }

    public boolean showCoverngTopic() {
        return super.getPaperType() == DomainConstants.BCCPaperType.BCCC;
    }

    public boolean showReportingTopic() {
        return super.getPaperType() == DomainConstants.BCCPaperType.BCC || super.getPaperType() == DomainConstants.BCCPaperType.EEBCC;
    }

    public boolean showEnhanceTopic() {
        return super.getPaperType() == DomainConstants.BCCPaperType.EEBCC;
    }

    public BigDecimal getMillionValue(BigDecimal value) {
        if (value != null) {
            return DecimalCalculator.divide(value, 1000000);
        } else {
            return DecimalCalculator.getDefaultZero();
        }
    }

    public String getDetailedFacilityType(BccFacilityDTO bccFacility) {
        List<String> facilityTags = new ArrayList<>();
        StringBuilder facilityType = new StringBuilder();
        facilityType.append(bccFacility.getType());
        if (bccFacility.getIsOneOff() == AppsConstants.YesNo.Y) {
            facilityTags.add("One-Off");
        }
        if (bccFacility.getDirectFacility() == AppsConstants.YesNo.Y) {
            facilityTags.add("Direct");
        } else {
            facilityTags.add("Indirect");
        }

        if (bccFacility.getSeriesOfLoans() == AppsConstants.YesNo.Y) {
            facilityTags.add("Series of Loans");
        }

        if (bccFacility.getReduction() == AppsConstants.YesNo.Y) {
            facilityTags.add("Reduction");
        }

        if (bccFacility.getEnhancement() == AppsConstants.YesNo.Y) {
            facilityTags.add("Enhancement");
        }

        if (bccFacility.getRevolving() == AppsConstants.YesNo.Y) {
            facilityTags.add("Revolving");
        }

        if (!facilityTags.isEmpty()) {
            facilityType.append("\n").append("(");
            String prefix = "";
            for (String tag : facilityTags) {
                facilityType.append(prefix);
                prefix = "/";
                facilityType.append(tag);
            }
            facilityType.append(")").append("\n");
        }

        if (bccFacility.getParentFacilityID() != null) {
            facilityType
                    .append("(Sub Limit of : ")
                    .append(bccFacility.getParentCreditFacilityName())
                    .append(" : ")
                    .append(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(bccFacility.getParentFacilityAmount())))
                    .append(" Rs.Mn")
                    .append(")");
        }
        return facilityType.toString();
    }

    public String getFormattedFacilityAmount(BccFacilityDTO bccFacility) {
        String subLimitAmount = "";
        if (bccFacility.getParentFacilityID() != null) {
            subLimitAmount = subLimitAmount.concat("(").concat(getFormattedFigure(NumberUtil.getMillionValue(bccFacility.getAmount()))).concat(")");
        } else {
            subLimitAmount = getFormattedFigure(NumberUtil.getMillionValue(bccFacility.getAmount()));
        }
        return subLimitAmount;
    }

    private List<CompReportingData> compReportingData;

    public List<CompReportingData> getCompReportingData() {
        return compReportingData;
    }

    public void setCompReportingData(List<CompReportingData> compReportingData) {
        this.compReportingData = compReportingData;
    }


    public String getFormattedFigure(Object value) {
        String result = "";
        DecimalFormat df = new DecimalFormat("#,###,###,###,##0.000");
        try {
            Double decimalValue = new Double(value.toString());
            if (decimalValue < 0){
                Object unFormattedAmount = new BigDecimal(decimalValue.toString()).abs();
                return result.concat("(").concat(df.format(unFormattedAmount)).concat(")");
            }else {
                Object unFormattedAmount = new BigDecimal(decimalValue.toString());
                return df.format(unFormattedAmount);
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }
}
