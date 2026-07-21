package com.itechro.cas.model.dto.finacle;

import java.util.List;

public class CompReportCusLoan {

    private String acid;

    private String solId;

    private String foracid;

    private String acctName;

    private String custId;

    private String glSubhead;

    private String schmCode;

    private String schmDesc;

    private String productGroup;

    private String acctOpnDate;

    private String acctClsFlg;

    private String acctClsDate;

    private String acctCrncyCode;

    private String sanctLim;

    private String clrBalAmt;

    private String interestRate;

    private String intTbleCode;

    private String prefRate;

    private String iimitB2kId;

    private String sectorCode;

    private String sectorDesc;

    private String riskRating;

    private String facilityType;

    private String commission;

    private String recordType;

    private List<String> collateral;

    private List<String> covenants;

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public String getSolId() {
        return solId;
    }

    public void setSolId(String solId) {
        this.solId = solId;
    }

    public String getForacid() {
        return foracid;
    }

    public void setForacid(String foracid) {
        this.foracid = foracid;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getGlSubhead() {
        return glSubhead;
    }

    public void setGlSubhead(String glSubhead) {
        this.glSubhead = glSubhead;
    }

    public String getSchmCode() {
        return schmCode;
    }

    public void setSchmCode(String schmCode) {
        this.schmCode = schmCode;
    }

    public String getSchmDesc() {
        return schmDesc;
    }

    public void setSchmDesc(String schmDesc) {
        this.schmDesc = schmDesc;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getAcctOpnDate() {
        return acctOpnDate;
    }

    public void setAcctOpnDate(String acctOpnDate) {
        this.acctOpnDate = acctOpnDate;
    }

    public String getAcctClsFlg() {
        return acctClsFlg;
    }

    public void setAcctClsFlg(String acctClsFlg) {
        this.acctClsFlg = acctClsFlg;
    }

    public String getAcctClsDate() {
        return acctClsDate;
    }

    public void setAcctClsDate(String acctClsDate) {
        this.acctClsDate = acctClsDate;
    }

    public String getAcctCrncyCode() {
        return acctCrncyCode;
    }

    public void setAcctCrncyCode(String acctCrncyCode) {
        this.acctCrncyCode = acctCrncyCode;
    }

    public String getSanctLim() {
        return sanctLim;
    }

    public void setSanctLim(String sanctLim) {
        this.sanctLim = sanctLim;
    }

    public String getClrBalAmt() {
        return clrBalAmt;
    }

    public void setClrBalAmt(String clrBalAmt) {
        this.clrBalAmt = clrBalAmt;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getIntTbleCode() {
        return intTbleCode;
    }

    public void setIntTbleCode(String intTbleCode) {
        this.intTbleCode = intTbleCode;
    }

    public String getIimitB2kId() {
        return iimitB2kId;
    }

    public void setIimitB2kId(String iimitB2kId) {
        this.iimitB2kId = iimitB2kId;
    }

    public List<String> getCollateral() {
        return collateral;
    }

    public void setCollateral(List<String> collateral) {
        this.collateral = collateral;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getPrefRate() {
        return prefRate;
    }

    public void setPrefRate(String prefRate) {
        this.prefRate = prefRate;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getSectorDesc() {
        return sectorDesc;
    }

    public void setSectorDesc(String sectorDesc) {
        this.sectorDesc = sectorDesc;
    }

    public String getRiskRating() {
        return riskRating;
    }

    public void setRiskRating(String riskRating) {
        this.riskRating = riskRating;
    }

    public List<String> getCovenants() {
        return covenants;
    }

    public void setCovenants(List<String> covenants) {
        this.covenants = covenants;
    }

    public boolean isCollateralExist() {
        return !this.collateral.isEmpty();
    }

    public boolean isCovenantsExist() {
        return !this.covenants.isEmpty();
    }

    public boolean isForeignCurrency(){ return !this.acctCrncyCode.isEmpty() && !this.acctCrncyCode.equals("LKR"); }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
