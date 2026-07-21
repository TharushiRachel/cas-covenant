package com.itechro.cas.model.dto.acae.response;

import lombok.Data;
import java.io.Serializable;

@Data
public class ACAEBranchDTO implements Serializable {
    private String BranchCode;
    private String BranchName;
}
