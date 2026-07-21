package com.itechro.cas.model.dto.facilitypaper.request;

import com.itechro.cas.model.dto.facilitypaper.response.WalletShareDTO;
import lombok.Data;

import java.util.List;

@Data
public class WalletShareReq {

    private Integer facilityPaperId;

    private List<WalletShareDTO> walletShares;
}