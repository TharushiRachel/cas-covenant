package com.itechro.cas.model.dto.integration.ws.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accList",
})
public class AccountDetailListWSRS implements Serializable {

    @JsonProperty("accList")
    private List<AccountDataWSDTO> accountList;

    public List<AccountDataWSDTO> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountDataWSDTO> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "AccountDetailListWSRS{" +
                "accountList=" + accountList +
                '}';
    }
}
