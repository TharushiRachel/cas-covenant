package com.itechro.cas.model.domain.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_BCC_EXPOSURE_COMPANY")
public class BccExposureCompany extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BCC_EXPOSURE_COMPANY")
    @SequenceGenerator(name = "SEQ_T_BCC_EXPOSURE_COMPANY", sequenceName = "SEQ_T_BCC_EXPOSURE_COMPANY", allocationSize = 1)
    @Column(name = "BCC_EXPOSURE_COMPANY_ID")
    private Integer bccExposureCompanyID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCC_PAPER_ID")
    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    @Column(name = "EXISTING_EXPOSURE_DIRECT")
    private BigDecimal existingExposureDirect;

    @Column(name = "EXISTING_EXPOSURE_INDIRECT")
    private BigDecimal existingExposureIndirect;

    @Column(name = "EXISTING_EXPOSURE_TOTAL")
    private BigDecimal existingExposureTotal;

    @Column(name = "ADDITIONAL_EXPOSURE_DIRECT")
    private BigDecimal additionalExposureDirect;

    @Column(name = "ADDITIONAL_EXPOSURE_INDIRECT")
    private BigDecimal additionalExposureIndirect;

    @Column(name = "ADDITIONAL_EXPOSURE_TOTAL")
    private BigDecimal additionalExposureTotal;

    @Column(name = "TOTAL_EXPOSURE_DIRECT")
    private BigDecimal totalExposureDirect;

    @Column(name = "TOTAL_EXPOSURE_INDIRECT")
    private BigDecimal totalExposureIndirect;

    @Column(name = "TOTAL_EXPOSURE_TOTAL")
    private BigDecimal totalExposureTotal;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "EXPOSURE_SECURED_BY")
    private String exposureSecuredBy;

    @Column(name = "APPROVED_FSV")
    private String approvedFSV;

    @Column(name = "AGAINST_APPROVED_FSV")
    private String againstApprovedFSV;

    @Column(name = "AGAINST_TOTAL_EXPOSURE")
    private String againstTotalExposure;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getBccExposureCompanyID() {
        return bccExposureCompanyID;
    }

    public void setBccExposureCompanyID(Integer bccExposureCompanyID) {
        this.bccExposureCompanyID = bccExposureCompanyID;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public void setBoardCreditCommitteePaper(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        this.boardCreditCommitteePaper = boardCreditCommitteePaper;
    }

    public BigDecimal getExistingExposureDirect() {
        return existingExposureDirect;
    }

    public void setExistingExposureDirect(BigDecimal existingExposureDirect) {
        this.existingExposureDirect = existingExposureDirect;
    }

    public BigDecimal getExistingExposureIndirect() {
        return existingExposureIndirect;
    }

    public void setExistingExposureIndirect(BigDecimal existingExposureIndirect) {
        this.existingExposureIndirect = existingExposureIndirect;
    }

    public BigDecimal getExistingExposureTotal() {
        return existingExposureTotal;
    }

    public void setExistingExposureTotal(BigDecimal existingExposureTotal) {
        this.existingExposureTotal = existingExposureTotal;
    }

    public BigDecimal getAdditionalExposureDirect() {
        return additionalExposureDirect;
    }

    public void setAdditionalExposureDirect(BigDecimal additionalExposureDirect) {
        this.additionalExposureDirect = additionalExposureDirect;
    }

    public BigDecimal getAdditionalExposureIndirect() {
        return additionalExposureIndirect;
    }

    public void setAdditionalExposureIndirect(BigDecimal additionalExposureIndirect) {
        this.additionalExposureIndirect = additionalExposureIndirect;
    }

    public BigDecimal getAdditionalExposureTotal() {
        return additionalExposureTotal;
    }

    public void setAdditionalExposureTotal(BigDecimal additionalExposureTotal) {
        this.additionalExposureTotal = additionalExposureTotal;
    }

    public BigDecimal getTotalExposureDirect() {
        return totalExposureDirect;
    }

    public void setTotalExposureDirect(BigDecimal totalExposureDirect) {
        this.totalExposureDirect = totalExposureDirect;
    }

    public BigDecimal getTotalExposureIndirect() {
        return totalExposureIndirect;
    }

    public void setTotalExposureIndirect(BigDecimal totalExposureIndirect) {
        this.totalExposureIndirect = totalExposureIndirect;
    }

    public BigDecimal getTotalExposureTotal() {
        return totalExposureTotal;
    }

    public void setTotalExposureTotal(BigDecimal totalExposureTotal) {
        this.totalExposureTotal = totalExposureTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExposureSecuredBy() {
        return exposureSecuredBy;
    }

    public void setExposureSecuredBy(String exposureSecuredBy) {
        this.exposureSecuredBy = exposureSecuredBy;
    }

    public String getApprovedFSV() {
        return approvedFSV;
    }

    public void setApprovedFSV(String approvedFSV) {
        this.approvedFSV = approvedFSV;
    }

    public String getAgainstApprovedFSV() {
        return againstApprovedFSV;
    }

    public void setAgainstApprovedFSV(String againstApprovedFSV) {
        this.againstApprovedFSV = againstApprovedFSV;
    }

    public String getAgainstTotalExposure() {
        return againstTotalExposure;
    }

    public void setAgainstTotalExposure(String againstTotalExposure) {
        this.againstTotalExposure = againstTotalExposure;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
