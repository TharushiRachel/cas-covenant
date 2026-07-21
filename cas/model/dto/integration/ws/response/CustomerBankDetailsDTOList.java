
package com.itechro.cas.model.dto.integration.ws.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "schmCode",
    "joiningPartnerList",
    "accountStatus",
    "schmType",
    "AccountNo",
    "branchCode",
    "acctCrncyCode"
})
public class CustomerBankDetailsDTOList {

    @JsonProperty("schmCode")
    private String schmCode;
    @JsonProperty("joiningPartnerList")
    private List<JoiningPartnerList> joiningPartnerList = null;
    @JsonProperty("accountStatus")
    private String accountStatus;
    @JsonProperty("schmType")
    private String schmType;
    @JsonProperty("AccountNo")
    private String accountNo;
    @JsonProperty("branchCode")
    private String branchCode;
    @JsonProperty("acctCrncyCode")
    private String acctCrncyCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("schmCode")
    public String getSchmCode() {
        return schmCode;
    }

    @JsonProperty("schmCode")
    public void setSchmCode(String schmCode) {
        this.schmCode = schmCode;
    }

    @JsonProperty("joiningPartnerList")
    public List<JoiningPartnerList> getJoiningPartnerList() {
        return joiningPartnerList;
    }

    @JsonProperty("joiningPartnerList")
    public void setJoiningPartnerList(List<JoiningPartnerList> joiningPartnerList) {
        this.joiningPartnerList = joiningPartnerList;
    }

    @JsonProperty("accountStatus")
    public String getAccountStatus() {
        return accountStatus;
    }

    @JsonProperty("accountStatus")
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @JsonProperty("schmType")
    public String getSchmType() {
        return schmType;
    }

    @JsonProperty("schmType")
    public void setSchmType(String schmType) {
        this.schmType = schmType;
    }

    @JsonProperty("AccountNo")
    public String getAccountNo() {
        return accountNo;
    }

    @JsonProperty("AccountNo")
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @JsonProperty("branchCode")
    public String getBranchCode() {
        return branchCode;
    }

    @JsonProperty("branchCode")
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @JsonProperty("acctCrncyCode")
    public String getAcctCrncyCode() {
        return acctCrncyCode;
    }

    @JsonProperty("acctCrncyCode")
    public void setAcctCrncyCode(String acctCrncyCode) {
        this.acctCrncyCode = acctCrncyCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
