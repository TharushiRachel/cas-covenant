package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.depositdetails;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.depositdetails.DepositDetail;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.depositdetails.DepositDetailResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DepositDetailResponseDTO implements Serializable {

    private List<DepositDetailDTO> depositDetailDTOS;

    private boolean success;

    public DepositDetailResponseDTO() {
    }

    public DepositDetailResponseDTO(DepositDetailResponse depositDetailResponse) {
        for (DepositDetail depositDetail : depositDetailResponse.getDepositDetails()) {
            DepositDetailDTO depositDetailDTO = new DepositDetailDTO(depositDetail);
            this.getDepositDetailDTOS().add(depositDetailDTO);
        }
    }

    public List<DepositDetailDTO> getDepositDetailDTOS() {
        if (depositDetailDTOS == null) {
            this.depositDetailDTOS = new ArrayList<>();
        }
        return depositDetailDTOS;
    }

    public void setDepositDetailDTOS(List<DepositDetailDTO> depositDetailDTOS) {
        this.depositDetailDTOS = depositDetailDTOS;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "DepositDetailResponseDTO{" +
                "depositDetailDTOS=" + depositDetailDTOS +
                ", success=" + success +
                '}';
    }
}
