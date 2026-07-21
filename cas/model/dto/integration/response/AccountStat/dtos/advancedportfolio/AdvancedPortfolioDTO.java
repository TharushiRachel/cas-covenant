package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.advancedportfolio;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.advancedportfolio.AdvancedPortfolio;

import java.io.Serializable;

public class AdvancedPortfolioDTO implements Serializable {

    private String proName;

    private String assetClassification;

    private String intRate;

    private String varCrncyUnits;

    private String empSolDesc;

    private String sf2;

    private String schmType;

    private String allowProvision;

    private String regionName;

    private String schmDescription;

    private String nature;

    private String pyaATM;

    private String myType;

    private String empSol;

    private String outstandingAomunt;

    private String finalRegionName;

    private String acctMgrUserID;

    private String systemClassificationDate;

    private String sfForacid;

    private String sfBalance;

    private String glSubHeadCode;

    private String operAcid;

    private String facilityAccount;

    private String operativeAccount;

    private String unearnAmt;

    private String acctName;

    private String currency;

    private String custId;

    private String natureCode;

    private String intProvAmt;

    private String ourProductGroup;

    private String lkrBalAfterDeduct;

    private String acid;

    private String sfBacid;

    private String empName;

    private String clrBalAmt;

    private String solID;

    private String sanctionLimit;

    private String solDesc;

    private String depositATM;

    private String finacleProductGroup;

    private String schmCode;

    private String empRegionName;

    public AdvancedPortfolioDTO(AdvancedPortfolio advancedPortfolio) {
        this.nature = advancedPortfolio.getNature();
        this.sanctionLimit = advancedPortfolio.getSanctionLimit();
        this.operativeAccount = advancedPortfolio.getOperativeAccount();
        this.facilityAccount = advancedPortfolio.getFacilityAccount();
        this.intRate = advancedPortfolio.getIntRate();
        this.outstandingAomunt = advancedPortfolio.getOutstandingAomunt();
        this.assetClassification = advancedPortfolio.getAssetClassification();
        this.currency = advancedPortfolio.getCurrency();

        //TODO map remaining fields if needed
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getAssetClassification() {
        return assetClassification;
    }

    public void setAssetClassification(String assetClassification) {
        this.assetClassification = assetClassification;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getVarCrncyUnits() {
        return varCrncyUnits;
    }

    public void setVarCrncyUnits(String varCrncyUnits) {
        this.varCrncyUnits = varCrncyUnits;
    }

    public String getEmpSolDesc() {
        return empSolDesc;
    }

    public void setEmpSolDesc(String empSolDesc) {
        this.empSolDesc = empSolDesc;
    }

    public String getSf2() {
        return sf2;
    }

    public void setSf2(String sf2) {
        this.sf2 = sf2;
    }

    public String getSchmType() {
        return schmType;
    }

    public void setSchmType(String schmType) {
        this.schmType = schmType;
    }

    public String getAllowProvision() {
        return allowProvision;
    }

    public void setAllowProvision(String allowProvision) {
        this.allowProvision = allowProvision;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getSchmDescription() {
        return schmDescription;
    }

    public void setSchmDescription(String schmDescription) {
        this.schmDescription = schmDescription;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getPyaATM() {
        return pyaATM;
    }

    public void setPyaATM(String pyaATM) {
        this.pyaATM = pyaATM;
    }

    public String getMyType() {
        return myType;
    }

    public void setMyType(String myType) {
        this.myType = myType;
    }

    public String getEmpSol() {
        return empSol;
    }

    public void setEmpSol(String empSol) {
        this.empSol = empSol;
    }

    public String getOutstandingAomunt() {
        return outstandingAomunt;
    }

    public void setOutstandingAomunt(String outstandingAomunt) {
        this.outstandingAomunt = outstandingAomunt;
    }

    public String getFinalRegionName() {
        return finalRegionName;
    }

    public void setFinalRegionName(String finalRegionName) {
        this.finalRegionName = finalRegionName;
    }

    public String getAcctMgrUserID() {
        return acctMgrUserID;
    }

    public void setAcctMgrUserID(String acctMgrUserID) {
        this.acctMgrUserID = acctMgrUserID;
    }

    public String getSystemClassificationDate() {
        return systemClassificationDate;
    }

    public void setSystemClassificationDate(String systemClassificationDate) {
        this.systemClassificationDate = systemClassificationDate;
    }

    public String getSfForacid() {
        return sfForacid;
    }

    public void setSfForacid(String sfForacid) {
        this.sfForacid = sfForacid;
    }

    public String getSfBalance() {
        return sfBalance;
    }

    public void setSfBalance(String sfBalance) {
        this.sfBalance = sfBalance;
    }

    public String getGlSubHeadCode() {
        return glSubHeadCode;
    }

    public void setGlSubHeadCode(String glSubHeadCode) {
        this.glSubHeadCode = glSubHeadCode;
    }

    public String getOperAcid() {
        return operAcid;
    }

    public void setOperAcid(String operAcid) {
        this.operAcid = operAcid;
    }

    public String getFacilityAccount() {
        return facilityAccount;
    }

    public void setFacilityAccount(String facilityAccount) {
        this.facilityAccount = facilityAccount;
    }

    public String getOperativeAccount() {
        return operativeAccount;
    }

    public void setOperativeAccount(String operativeAccount) {
        this.operativeAccount = operativeAccount;
    }

    public String getUnearnAmt() {
        return unearnAmt;
    }

    public void setUnearnAmt(String unearnAmt) {
        this.unearnAmt = unearnAmt;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getNatureCode() {
        return natureCode;
    }

    public void setNatureCode(String natureCode) {
        this.natureCode = natureCode;
    }

    public String getIntProvAmt() {
        return intProvAmt;
    }

    public void setIntProvAmt(String intProvAmt) {
        this.intProvAmt = intProvAmt;
    }

    public String getOurProductGroup() {
        return ourProductGroup;
    }

    public void setOurProductGroup(String ourProductGroup) {
        this.ourProductGroup = ourProductGroup;
    }

    public String getLkrBalAfterDeduct() {
        return lkrBalAfterDeduct;
    }

    public void setLkrBalAfterDeduct(String lkrBalAfterDeduct) {
        this.lkrBalAfterDeduct = lkrBalAfterDeduct;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public String getSfBacid() {
        return sfBacid;
    }

    public void setSfBacid(String sfBacid) {
        this.sfBacid = sfBacid;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getClrBalAmt() {
        return clrBalAmt;
    }

    public void setClrBalAmt(String clrBalAmt) {
        this.clrBalAmt = clrBalAmt;
    }

    public String getSolID() {
        return solID;
    }

    public void setSolID(String solID) {
        this.solID = solID;
    }

    public String getSanctionLimit() {
        return sanctionLimit;
    }

    public void setSanctionLimit(String sanctionLimit) {
        this.sanctionLimit = sanctionLimit;
    }

    public String getSolDesc() {
        return solDesc;
    }

    public void setSolDesc(String solDesc) {
        this.solDesc = solDesc;
    }

    public String getDepositATM() {
        return depositATM;
    }

    public void setDepositATM(String depositATM) {
        this.depositATM = depositATM;
    }

    public String getFinacleProductGroup() {
        return finacleProductGroup;
    }

    public void setFinacleProductGroup(String finacleProductGroup) {
        this.finacleProductGroup = finacleProductGroup;
    }

    public String getSchmCode() {
        return schmCode;
    }

    public void setSchmCode(String schmCode) {
        this.schmCode = schmCode;
    }

    public String getEmpRegionName() {
        return empRegionName;
    }

    public void setEmpRegionName(String empRegionName) {
        this.empRegionName = empRegionName;
    }

    @Override
    public String toString() {
        return "AdvancedPortfolioDTO{" +
                "proName='" + proName + '\'' +
                ", assetClassification='" + assetClassification + '\'' +
                ", intRate='" + intRate + '\'' +
                ", varCrncyUnits='" + varCrncyUnits + '\'' +
                ", empSolDesc='" + empSolDesc + '\'' +
                ", sf2='" + sf2 + '\'' +
                ", schmType='" + schmType + '\'' +
                ", allowProvision='" + allowProvision + '\'' +
                ", regionName='" + regionName + '\'' +
                ", schmDescription='" + schmDescription + '\'' +
                ", nature='" + nature + '\'' +
                ", pyaATM='" + pyaATM + '\'' +
                ", myType='" + myType + '\'' +
                ", empSol='" + empSol + '\'' +
                ", outstandingAomunt='" + outstandingAomunt + '\'' +
                ", finalRegionName='" + finalRegionName + '\'' +
                ", acctMgrUserID='" + acctMgrUserID + '\'' +
                ", systemClassificationDate='" + systemClassificationDate + '\'' +
                ", sfForacid='" + sfForacid + '\'' +
                ", sfBalance='" + sfBalance + '\'' +
                ", glSubHeadCode='" + glSubHeadCode + '\'' +
                ", operAcid='" + operAcid + '\'' +
                ", facilityAccount='" + facilityAccount + '\'' +
                ", operativeAccount='" + operativeAccount + '\'' +
                ", unearnAmt='" + unearnAmt + '\'' +
                ", acctName='" + acctName + '\'' +
                ", currency='" + currency + '\'' +
                ", custId='" + custId + '\'' +
                ", natureCode='" + natureCode + '\'' +
                ", intProvAmt='" + intProvAmt + '\'' +
                ", ourProductGroup='" + ourProductGroup + '\'' +
                ", lkrBalAfterDeduct='" + lkrBalAfterDeduct + '\'' +
                ", acid='" + acid + '\'' +
                ", sfBacid='" + sfBacid + '\'' +
                ", empName='" + empName + '\'' +
                ", clrBalAmt='" + clrBalAmt + '\'' +
                ", solID='" + solID + '\'' +
                ", sanctionLimit='" + sanctionLimit + '\'' +
                ", solDesc='" + solDesc + '\'' +
                ", depositATM='" + depositATM + '\'' +
                ", finacleProductGroup='" + finacleProductGroup + '\'' +
                ", schmCode='" + schmCode + '\'' +
                ", empRegionName='" + empRegionName + '\'' +
                '}';
    }
}
