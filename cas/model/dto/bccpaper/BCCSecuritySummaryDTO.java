package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPSecuritySummaryTopic;
import com.itechro.cas.model.domain.facilitypaper.FPSecuritySummery;
import com.itechro.cas.model.dto.facilitypaper.FPSecuritySummaryTopicDTO;
import com.itechro.cas.model.dto.facilitypaper.FPSecuritySummeryDTO;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.NumberUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BCCSecuritySummaryDTO extends FPSecuritySummeryDTO {

    private List<FPSecuritySummaryTopicDTO> firstClassSecuritySummaryTopics;

    private BigDecimal subTotalFirstClassCompanyPercentage;

    private BigDecimal subTotalFirstClassGroupPercentage;


    private List<FPSecuritySummaryTopicDTO> firstClassOtherSecuritySummaryTopics;

    private BigDecimal subTotalFirstClassOtherCompanyPercentage;

    private BigDecimal subTotalFirstClassOtherGroupPercentage;


    private List<FPSecuritySummaryTopicDTO> defaultSecuritySummaryTopics;

    private BigDecimal totalCompanyPercentage;

    private BigDecimal totalGroupPercentage;

    public BCCSecuritySummaryDTO() {
    }

    public BCCSecuritySummaryDTO(FPSecuritySummery fpSecuritySummery) {
        super(fpSecuritySummery);
        for (FPSecuritySummaryTopic fpSecuritySummaryTopic : fpSecuritySummery.getOrderedFpSecuritySummaryTopics()) {
            if (fpSecuritySummaryTopic.getStatus() == AppsConstants.Status.ACT) {
                switch (fpSecuritySummaryTopic.getSecurityTypeGroup()) {
                    case FIRST_CLASS: {
                        this.getFirstClassSecuritySummaryTopics().add(new FPSecuritySummaryTopicDTO(fpSecuritySummaryTopic));
                        break;
                    }

                    case OTHER: {
                        this.getFirstClassOtherSecuritySummaryTopics().add(new FPSecuritySummaryTopicDTO(fpSecuritySummaryTopic));
                        break;
                    }

                    case DEFAULT: {
                        this.getDefaultSecuritySummaryTopics().add(new FPSecuritySummaryTopicDTO(fpSecuritySummaryTopic));
                        break;
                    }
                }
            }
        }

        if (!this.getCompanyTotal().equals(DecimalCalculator.getDefaultZero())) {
            this.subTotalFirstClassCompanyPercentage = NumberUtil.roundUp(DecimalCalculator.multiply(DecimalCalculator.divide(this.getCompanySubTotalOne(), this.getCompanyTotal()), 100), 2);
            this.subTotalFirstClassOtherCompanyPercentage = NumberUtil.roundUp(DecimalCalculator.multiply(DecimalCalculator.divide(this.getCompanySubTotalTwo(), this.getCompanyTotal()), 100), 2);
            this.totalCompanyPercentage = NumberUtil.roundUp(DecimalCalculator.multiply(DecimalCalculator.divide(this.getCompanyTotal(), this.getCompanyTotal()), 100), 2);
        }

        if (!this.getGroupTotal().equals(DecimalCalculator.getDefaultZero())) {
            this.subTotalFirstClassGroupPercentage = NumberUtil.roundUp(DecimalCalculator.multiply(DecimalCalculator.divide(this.getGroupSubTotalOne(), this.getGroupTotal()), 100), 2);
            this.subTotalFirstClassOtherGroupPercentage = NumberUtil.roundUp(DecimalCalculator.multiply(DecimalCalculator.divide(this.getGroupSubTotalTwo(), this.getGroupTotal()), 100), 2);
            this.totalGroupPercentage = NumberUtil.roundUp(DecimalCalculator.multiply(DecimalCalculator.divide(this.getGroupTotal(), this.getGroupTotal()), 100), 2);
        }

    }

    public BigDecimal getSubTotalFirstClassCompanyPercentage() {
        if (subTotalFirstClassCompanyPercentage == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return subTotalFirstClassCompanyPercentage;
    }

    public void setSubTotalFirstClassCompanyPercentage(BigDecimal subTotalFirstClassCompanyPercentage) {
        this.subTotalFirstClassCompanyPercentage = subTotalFirstClassCompanyPercentage;
    }

    public BigDecimal getSubTotalFirstClassGroupPercentage() {
        if (subTotalFirstClassGroupPercentage == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return subTotalFirstClassGroupPercentage;
    }

    public void setSubTotalFirstClassGroupPercentage(BigDecimal subTotalFirstClassGroupPercentage) {
        this.subTotalFirstClassGroupPercentage = subTotalFirstClassGroupPercentage;
    }

    public BigDecimal getSubTotalFirstClassOtherCompanyPercentage() {
        if (subTotalFirstClassOtherCompanyPercentage == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return subTotalFirstClassOtherCompanyPercentage;
    }

    public void setSubTotalFirstClassOtherCompanyPercentage(BigDecimal subTotalFirstClassOtherCompanyPercentage) {
        this.subTotalFirstClassOtherCompanyPercentage = subTotalFirstClassOtherCompanyPercentage;
    }

    public BigDecimal getSubTotalFirstClassOtherGroupPercentage() {
        if (subTotalFirstClassOtherGroupPercentage == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return subTotalFirstClassOtherGroupPercentage;
    }

    public void setSubTotalFirstClassOtherGroupPercentage(BigDecimal subTotalFirstClassOtherGroupPercentage) {
        this.subTotalFirstClassOtherGroupPercentage = subTotalFirstClassOtherGroupPercentage;
    }


    public BigDecimal getTotalCompanyPercentage() {
        if (totalCompanyPercentage == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return totalCompanyPercentage;
    }

    public void setTotalCompanyPercentage(BigDecimal totalCompanyPercentage) {
        this.totalCompanyPercentage = totalCompanyPercentage;
    }

    public BigDecimal getTotalGroupPercentage() {
        if (totalGroupPercentage == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return totalGroupPercentage;
    }

    public void setTotalGroupPercentage(BigDecimal totalGroupPercentage) {
        this.totalGroupPercentage = totalGroupPercentage;
    }

    public List<FPSecuritySummaryTopicDTO> getFirstClassSecuritySummaryTopics() {
        if (firstClassSecuritySummaryTopics == null) {
            this.firstClassSecuritySummaryTopics = new ArrayList<>();
        }
        return firstClassSecuritySummaryTopics;
    }

    public void setFirstClassSecuritySummaryTopics(List<FPSecuritySummaryTopicDTO> firstClassSecuritySummaryTopics) {
        this.firstClassSecuritySummaryTopics = firstClassSecuritySummaryTopics;
    }

    public List<FPSecuritySummaryTopicDTO> getFirstClassOtherSecuritySummaryTopics() {
        if (firstClassOtherSecuritySummaryTopics == null) {
            this.firstClassOtherSecuritySummaryTopics = new ArrayList<>();
        }
        return firstClassOtherSecuritySummaryTopics;
    }

    public void setFirstClassOtherSecuritySummaryTopics(List<FPSecuritySummaryTopicDTO> firstClassOtherSecuritySummaryTopics) {
        this.firstClassOtherSecuritySummaryTopics = firstClassOtherSecuritySummaryTopics;
    }

    public List<FPSecuritySummaryTopicDTO> getDefaultSecuritySummaryTopics() {
        if (defaultSecuritySummaryTopics == null) {
            this.defaultSecuritySummaryTopics = new ArrayList<>();
        }
        return defaultSecuritySummaryTopics;
    }

    public void setDefaultSecuritySummaryTopics(List<FPSecuritySummaryTopicDTO> defaultSecuritySummaryTopics) {
        this.defaultSecuritySummaryTopics = defaultSecuritySummaryTopics;
    }

    @Override
    public String getLimitSummery() {
        if (super.getLimitSummery() == null) {
            return "No Remark";
        } else {
            return super.getLimitSummery();
        }
    }
}
