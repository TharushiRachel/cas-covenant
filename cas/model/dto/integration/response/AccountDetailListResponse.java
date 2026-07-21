package com.itechro.cas.model.dto.integration.response;

import com.itechro.cas.model.dto.integration.ws.response.AccountDataWSDTO;
import com.itechro.cas.model.dto.integration.ws.response.AccountDetailListWSRS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountDetailListResponse implements Serializable {

    private boolean success;

    private List<AccountDataDTO> accountDataDTOList;

    public AccountDetailListResponse() {
    }

    public AccountDetailListResponse(AccountDetailListWSRS accountDetailListWSRS) {
        if (accountDetailListWSRS != null) {
            if (accountDetailListWSRS.getAccountList() != null) {
                for (AccountDataWSDTO accountDataWSDTO : accountDetailListWSRS.getAccountList()) {
                    this.getAccountDataDTOList().add(new AccountDataDTO(accountDataWSDTO));
                }
            }
        }
    }

    public List<AccountDataDTO> getAccountDataDTOList() {
        if (accountDataDTOList == null) {
            accountDataDTOList = new ArrayList<>();
        }
        return accountDataDTOList;
    }

    public void setAccountDataDTOList(List<AccountDataDTO> accountDataDTOList) {
        this.accountDataDTOList = accountDataDTOList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AccountDetailListResponse{" +
                "accountDataDTOList=" + accountDataDTOList +
                '}';
    }
}
