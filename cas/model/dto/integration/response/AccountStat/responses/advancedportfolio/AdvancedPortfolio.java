package com.itechro.cas.model.dto.integration.response.AccountStat.responses.advancedportfolio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AdvancedPortfolio implements Serializable {

    @JsonProperty("pro_name")
    private String proName;

    @JsonProperty("sub_classification_system")
    private String assetClassification;

    @JsonProperty("int_rate")
    private String intRate;

    @JsonProperty("var_crncy_units")
    private String varCrncyUnits;

    @JsonProperty("emp_sol_desc")
    private String empSolDesc;

    @JsonProperty("sf2_balance")
    private String sf2;

    @JsonProperty("schm_type")
    private String schmType;

    @JsonProperty("allow_provision")
    private String allowProvision;

    @JsonProperty("region_name")
    private String regionName;

    @JsonProperty("schm_desc")
    private String schmDescription;

    @JsonProperty("nature")
    private String nature;

    @JsonProperty("pay_atm")
    private String pyaATM;

    @JsonProperty("my_type")
    private String myType;

    @JsonProperty("emp_sol")
    private String empSol;

    @JsonProperty("lkr_bal_amt")
    private String outstandingAomunt;

    @JsonProperty("final_region_name")
    private String finalRegionName;

    @JsonProperty("acct_mgr_user_id")
    private String acctMgrUserID;

    @JsonProperty("system_classification_date")
    private String systemClassificationDate;

    @JsonProperty("sf_foracid")
    private String sfForacid;

    @JsonProperty("sf_balance")
    private String sfBalance;

    @JsonProperty("gl_sub_head_code")
    private String glSubHeadCode;

    @JsonProperty("oper_acid")
    private String operAcid;

    @JsonProperty("foracid")
    private String facilityAccount;

    @JsonProperty("oper_foracid")
    private String operativeAccount;

    @JsonProperty("unearn_amt")
    private String unearnAmt;

    @JsonProperty("acct_name")
    private String acctName;

    @JsonProperty("acct_crncy_code")
    private String currency;

    @JsonProperty("cust_id")
    private String custId;

    @JsonProperty("nature_code")
    private String natureCode;

    @JsonProperty("int_prov_amt")
    private String intProvAmt;

    @JsonProperty("our_product_group")
    private String ourProductGroup;

    @JsonProperty("lkr_bal_after_deduct")
    private String lkrBalAfterDeduct;

    @JsonProperty("acid")
    private String acid;

    @JsonProperty("sf_bacid")
    private String sfBacid;

    @JsonProperty("emp_name")
    private String empName;

    @JsonProperty("clr_bal_amt")
    private String clrBalAmt;

    @JsonProperty("sol_id")
    private String solID;

    @JsonProperty("sanct_lim")
    private String sanctionLimit;

    @JsonProperty("sol_desc")
    private String solDesc;

    @JsonProperty("deposit_atm")
    private String depositATM;

    @JsonProperty("finacle_product_group")
    private String finacleProductGroup;

    @JsonProperty("schm_code")
    private String schmCode;

    @JsonProperty("emp_region_name")
    private String empRegionName;

    @JsonProperty("pro_name")
    public String getProName() {
        return proName;
    }

    @JsonProperty("pro_name")
    public void setProName(String proName) {
        this.proName = proName;
    }

    @JsonProperty("sub_classification_system")
    public String getAssetClassification() {
        return assetClassification;
    }

    @JsonProperty("sub_classification_system")
    public void setAssetClassification(String assetClassification) {
        this.assetClassification = assetClassification;
    }

    @JsonProperty("int_rate")
    public String getIntRate() {
        return intRate;
    }

    @JsonProperty("int_rate")
    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    @JsonProperty("var_crncy_units")
    public String getVarCrncyUnits() {
        return varCrncyUnits;
    }

    @JsonProperty("var_crncy_units")
    public void setVarCrncyUnits(String varCrncyUnits) {
        this.varCrncyUnits = varCrncyUnits;
    }

    @JsonProperty("emp_sol_desc")
    public String getEmpSolDesc() {
        return empSolDesc;
    }

    @JsonProperty("emp_sol_desc")
    public void setEmpSolDesc(String empSolDesc) {
        this.empSolDesc = empSolDesc;
    }

    @JsonProperty("sf2_balance")
    public String getSf2() {
        return sf2;
    }

    @JsonProperty("sf2_balance")
    public void setSf2(String sf2) {
        this.sf2 = sf2;
    }

    @JsonProperty("schm_type")
    public String getSchmType() {
        return schmType;
    }

    @JsonProperty("schm_type")
    public void setSchmType(String schmType) {
        this.schmType = schmType;
    }

    @JsonProperty("allow_provision")
    public String getAllowProvision() {
        return allowProvision;
    }

    @JsonProperty("allow_provision")
    public void setAllowProvision(String allowProvision) {
        this.allowProvision = allowProvision;
    }

    @JsonProperty("region_name")
    public String getRegionName() {
        return regionName;
    }

    @JsonProperty("region_name")
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @JsonProperty("schm_desc")
    public String getSchmDescription() {
        return schmDescription;
    }

    @JsonProperty("schm_desc")
    public void setSchmDescription(String schmDescription) {
        this.schmDescription = schmDescription;
    }

    @JsonProperty("nature")
    public String getNature() {
        return nature;
    }

    @JsonProperty("nature")
    public void setNature(String nature) {
        this.nature = nature;
    }

    @JsonProperty("pay_atm")
    public String getPyaATM() {
        return pyaATM;
    }

    @JsonProperty("pay_atm")
    public void setPyaATM(String pyaATM) {
        this.pyaATM = pyaATM;
    }

    @JsonProperty("my_type")
    public String getMyType() {
        return myType;
    }

    @JsonProperty("my_type")
    public void setMyType(String myType) {
        this.myType = myType;
    }

    @JsonProperty("emp_sol")
    public String getEmpSol() {
        return empSol;
    }

    @JsonProperty("emp_sol")
    public void setEmpSol(String empSol) {
        this.empSol = empSol;
    }

    @JsonProperty("lkr_bal_amt")
    public String getOutstandingAomunt() {
        return outstandingAomunt;
    }

    @JsonProperty("lkr_bal_amt")
    public void setOutstandingAomunt(String outstandingAomunt) {
        this.outstandingAomunt = outstandingAomunt;
    }

    @JsonProperty("final_region_name")
    public String getFinalRegionName() {
        return finalRegionName;
    }

    @JsonProperty("final_region_name")
    public void setFinalRegionName(String finalRegionName) {
        this.finalRegionName = finalRegionName;
    }

    @JsonProperty("acct_mgr_user_id")
    public String getAcctMgrUserID() {
        return acctMgrUserID;
    }

    @JsonProperty("acct_mgr_user_id")
    public void setAcctMgrUserID(String acctMgrUserID) {
        this.acctMgrUserID = acctMgrUserID;
    }

    @JsonProperty("system_classification_date")
    public String getSystemClassificationDate() {
        return systemClassificationDate;
    }

    @JsonProperty("system_classification_date")
    public void setSystemClassificationDate(String systemClassificationDate) {
        this.systemClassificationDate = systemClassificationDate;
    }

    @JsonProperty("sf_foracid")
    public String getSfForacid() {
        return sfForacid;
    }

    @JsonProperty("sf_foracid")
    public void setSfForacid(String sfForacid) {
        this.sfForacid = sfForacid;
    }

    @JsonProperty("sf_balance")
    public String getSfBalance() {
        return sfBalance;
    }

    @JsonProperty("sf_balance")
    public void setSfBalance(String sfBalance) {
        this.sfBalance = sfBalance;
    }

    @JsonProperty("gl_sub_head_code")
    public String getGlSubHeadCode() {
        return glSubHeadCode;
    }

    @JsonProperty("gl_sub_head_code")
    public void setGlSubHeadCode(String glSubHeadCode) {
        this.glSubHeadCode = glSubHeadCode;
    }

    @JsonProperty("oper_acid")
    public String getOperAcid() {
        return operAcid;
    }

    @JsonProperty("oper_acid")
    public void setOperAcid(String operAcid) {
        this.operAcid = operAcid;
    }

    @JsonProperty("foracid")
    public String getFacilityAccount() {
        return facilityAccount;
    }

    @JsonProperty("foracid")
    public void setFacilityAccount(String facilityAccount) {
        this.facilityAccount = facilityAccount;
    }

    @JsonProperty("oper_foracid")
    public String getOperativeAccount() {
        return operativeAccount;
    }

    @JsonProperty("oper_foracid")
    public void setOperativeAccount(String operativeAccount) {
        this.operativeAccount = operativeAccount;
    }

    @JsonProperty("unearn_amt")
    public String getUnearnAmt() {
        return unearnAmt;
    }

    @JsonProperty("unearn_amt")
    public void setUnearnAmt(String unearnAmt) {
        this.unearnAmt = unearnAmt;
    }

    @JsonProperty("acct_name")
    public String getAcctName() {
        return acctName;
    }

    @JsonProperty("acct_name")
    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    @JsonProperty("acct_crncy_code")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("acct_crncy_code")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("cust_id")
    public String getCustId() {
        return custId;
    }

    @JsonProperty("cust_id")
    public void setCustId(String custId) {
        this.custId = custId;
    }

    @JsonProperty("nature_code")
    public String getNatureCode() {
        return natureCode;
    }

    @JsonProperty("nature_code")
    public void setNatureCode(String natureCode) {
        this.natureCode = natureCode;
    }

    @JsonProperty("int_prov_amt")
    public String getIntProvAmt() {
        return intProvAmt;
    }

    @JsonProperty("int_prov_amt")
    public void setIntProvAmt(String intProvAmt) {
        this.intProvAmt = intProvAmt;
    }

    @JsonProperty("our_product_group")
    public String getOurProductGroup() {
        return ourProductGroup;
    }

    @JsonProperty("our_product_group")
    public void setOurProductGroup(String ourProductGroup) {
        this.ourProductGroup = ourProductGroup;
    }

    @JsonProperty("lkr_bal_after_deduct")
    public String getLkrBalAfterDeduct() {
        return lkrBalAfterDeduct;
    }

    @JsonProperty("lkr_bal_after_deduct")
    public void setLkrBalAfterDeduct(String lkrBalAfterDeduct) {
        this.lkrBalAfterDeduct = lkrBalAfterDeduct;
    }

    @JsonProperty("acid")
    public String getAcid() {
        return acid;
    }

    @JsonProperty("acid")
    public void setAcid(String acid) {
        this.acid = acid;
    }

    @JsonProperty("sf_bacid")
    public String getSfBacid() {
        return sfBacid;
    }

    @JsonProperty("sf_bacid")
    public void setSfBacid(String sfBacid) {
        this.sfBacid = sfBacid;
    }

    @JsonProperty("emp_name")
    public String getEmpName() {
        return empName;
    }

    @JsonProperty("emp_name")
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @JsonProperty("clr_bal_amt")
    public String getClrBalAmt() {
        return clrBalAmt;
    }

    @JsonProperty("clr_bal_amt")
    public void setClrBalAmt(String clrBalAmt) {
        this.clrBalAmt = clrBalAmt;
    }

    @JsonProperty("sol_id")
    public String getSolID() {
        return solID;
    }

    @JsonProperty("sol_id")
    public void setSolID(String solID) {
        this.solID = solID;
    }

    @JsonProperty("sanct_lim")
    public String getSanctionLimit() {
        return sanctionLimit;
    }

    @JsonProperty("sanct_lim")
    public void setSanctionLimit(String sanctionLimit) {
        this.sanctionLimit = sanctionLimit;
    }

    @JsonProperty("sol_desc")
    public String getSolDesc() {
        return solDesc;
    }

    @JsonProperty("sol_desc")
    public void setSolDesc(String solDesc) {
        this.solDesc = solDesc;
    }

    @JsonProperty("deposit_atm")
    public String getDepositATM() {
        return depositATM;
    }

    @JsonProperty("deposit_atm")
    public void setDepositATM(String depositATM) {
        this.depositATM = depositATM;
    }

    @JsonProperty("finacle_product_group")
    public String getFinacleProductGroup() {
        return finacleProductGroup;
    }

    @JsonProperty("finacle_product_group")
    public void setFinacleProductGroup(String finacleProductGroup) {
        this.finacleProductGroup = finacleProductGroup;
    }

    @JsonProperty("schm_code")
    public String getSchmCode() {
        return schmCode;
    }

    @JsonProperty("schm_code")
    public void setSchmCode(String schmCode) {
        this.schmCode = schmCode;
    }

    @JsonProperty("emp_region_name")
    public String getEmpRegionName() {
        return empRegionName;
    }

    @JsonProperty("emp_region_name")
    public void setEmpRegionName(String empRegionName) {
        this.empRegionName = empRegionName;
    }
}

