package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.bccpaper.BccExposureGroup;

import java.io.Serializable;
import java.math.BigDecimal;

public class BccExposureGroupDTO implements Serializable {

    private Integer bccExposureGroupID;

    private Integer boardCreditCommitteePaperID;

    private BigDecimal existingExposureDirect;

    private BigDecimal existingExposureIndirect;

    private BigDecimal existingExposureTotal;

    private BigDecimal additionalExposureDirect;

    private BigDecimal additionalExposureIndirect;

    private BigDecimal additionalExposureTotal;

    private BigDecimal totalExposureDirect;

    private BigDecimal totalExposureIndirect;

    private BigDecimal totalExposureTotal;

    private String type;

    private String exposureSecuredBy;

    private String approvedFSV;

    private String againstApprovedFSV;

    private String againstTotalExposure;

    private AppsConstants.Status status;

    public BccExposureGroupDTO() {
    }


    public BccExposureGroupDTO(BccExposureGroup bccExposureGroup) {

        this.bccExposureGroupID = bccExposureGroup.getBccExposureGroupID();
        this.boardCreditCommitteePaperID = bccExposureGroup.getBoardCreditCommitteePaper().getBoardCreditCommitteePaperID();
        this.existingExposureDirect = bccExposureGroup.getExistingExposureDirect();
        this.existingExposureIndirect = bccExposureGroup.getExistingExposureIndirect();
        this.existingExposureTotal = bccExposureGroup.getExistingExposureTotal();
        this.additionalExposureDirect = bccExposureGroup.getAdditionalExposureDirect();
        this.additionalExposureIndirect = bccExposureGroup.getAdditionalExposureIndirect();
        this.additionalExposureTotal = bccExposureGroup.getAdditionalExposureTotal();
        this.totalExposureDirect = bccExposureGroup.getTotalExposureDirect();
        this.totalExposureIndirect = bccExposureGroup.getTotalExposureIndirect();
        this.totalExposureTotal = bccExposureGroup.getTotalExposureTotal();
        this.type = bccExposureGroup.getType();
        this.exposureSecuredBy = bccExposureGroup.getExposureSecuredBy();
        this.approvedFSV = bccExposureGroup.getApprovedFSV();
        this.againstApprovedFSV = bccExposureGroup.getAgainstApprovedFSV();
        this.againstTotalExposure = bccExposureGroup.getAgainstTotalExposure();
        this.status = bccExposureGroup.getStatus();

    }

    public Integer getBccExposureGroupID() {
        return bccExposureGroupID;
    }

    public void setBccExposureGroupID(Integer bccExposureGroupID) {
        this.bccExposureGroupID = bccExposureGroupID;
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
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

    @Override
    public String toString() {
        return "BccExposureGroupDTO{" +
                "bccExposureGroupID=" + bccExposureGroupID +
                ", boardCreditCommitteePaperID=" + boardCreditCommitteePaperID +
                ", existingExposureDirect=" + existingExposureDirect +
                ", existingExposureIndirect=" + existingExposureIndirect +
                ", existingExposureTotal=" + existingExposureTotal +
                ", additionalExposureDirect=" + additionalExposureDirect +
                ", additionalExposureIndirect=" + additionalExposureIndirect +
                ", additionalExposureTotal=" + additionalExposureTotal +
                ", totalExposureDirect=" + totalExposureDirect +
                ", totalExposureIndirect=" + totalExposureIndirect +
                ", totalExposureTotal=" + totalExposureTotal +
                ", type='" + type + '\'' +
                ", exposureSecuredBy='" + exposureSecuredBy + '\'' +
                ", approvedFSV='" + approvedFSV + '\'' +
                ", againstApprovedFSV='" + againstApprovedFSV + '\'' +
                ", againstTotalExposure='" + againstTotalExposure + '\'' +
                ", status=" + status +
                '}';
    }
}
