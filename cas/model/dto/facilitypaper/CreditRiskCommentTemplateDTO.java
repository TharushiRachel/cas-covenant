package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;

import java.util.ArrayList;
import java.util.List;

public class CreditRiskCommentTemplateDTO {

    private List<CASCustomer> casCustomerDTOList;

    private String branchCode;

    private String branchName;

    private String totalExposureNew;

    private CustomerRatings customerRatings;

    private String groupTotalExposureNew;

    private DomainConstants.CRPaperType paperType;

    public boolean showRiskOpinionFormat() {
        return getPaperType() == DomainConstants.CRPaperType.ROF;
    }

    public boolean showDescriptiveFormat() {
        return getPaperType() == DomainConstants.CRPaperType.DF;
    }

    public boolean showReviewFormat() {
        return getPaperType() == DomainConstants.CRPaperType.RF2;
    }

    public List<CASCustomer> getCasCustomerDTOList() {
        if (casCustomerDTOList == null) {
            casCustomerDTOList = new ArrayList<>();
        }
        return casCustomerDTOList;
    }

    public void setCasCustomerDTOList(List<CASCustomer> casCustomerDTOList) {
        this.casCustomerDTOList = casCustomerDTOList;
    }

    public void addCASCustomer(CASCustomer casCustomer) {
        getCasCustomerDTOList().add(casCustomer);
    }

    public DomainConstants.CRPaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(DomainConstants.CRPaperType paperType) {
        this.paperType = paperType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getTotalExposureNew() {
        return totalExposureNew;
    }

    public void setTotalExposureNew(String totalExposureNew) {
        this.totalExposureNew = totalExposureNew;
    }

    public String getGroupTotalExposureNew() {
        return groupTotalExposureNew;
    }

    public void setGroupTotalExposureNew(String groupTotalExposureNew) {
        this.groupTotalExposureNew = groupTotalExposureNew;
    }

    public CustomerRatings getCustomerRatings() {
        return customerRatings;
    }

    public void setCustomerRatings(CustomerRatings customerRatings) {
        this.customerRatings = customerRatings;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
