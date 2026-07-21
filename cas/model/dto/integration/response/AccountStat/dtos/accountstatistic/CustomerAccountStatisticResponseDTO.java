package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.accountstatistic;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.accountstatistic.AccStatResponse;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.accountstatistic.CustomerAccountStatisticResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerAccountStatisticResponseDTO implements Serializable {

    private List<AccStatResponseDTO> accStatResponsesDTOArrayList;

    private boolean success;

    public CustomerAccountStatisticResponseDTO() {
    }

    public CustomerAccountStatisticResponseDTO(CustomerAccountStatisticResponse customerAccountStatisticResopone) {

        for (AccStatResponse accStatResponse : customerAccountStatisticResopone.getAccStatResponses()) {
            AccStatResponseDTO accStatResponseDTO = new AccStatResponseDTO(accStatResponse);
            this.getAccStatResponsesDTOArrayList().add(accStatResponseDTO);
        }
    }

    public List<AccStatResponseDTO> getAccStatResponsesDTOArrayList() {
        if (accStatResponsesDTOArrayList == null) {
            accStatResponsesDTOArrayList = new ArrayList<>();
        }
        return accStatResponsesDTOArrayList;
    }

    public void setAccStatResponsesDTOArrayList(List<AccStatResponseDTO> accStatResponsesDTOArrayList) {
        this.accStatResponsesDTOArrayList = accStatResponsesDTOArrayList;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CustomerAccountStatisticResponseDTO{" +
                "accStatResponsesDTOArrayList=" + accStatResponsesDTOArrayList +
                ", success=" + success +
                '}';
    }
}
