package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;

public class BranchLoadResponseDTO implements Serializable {

    private String BranchCode;

    private String BranchName;

    public BranchLoadResponseDTO(){}

    public BranchLoadResponseDTO(BranchLoadResponse branchLoadResponse){

        this.BranchCode = branchLoadResponse.getBranchCode();
        this.BranchName = branchLoadResponse.getBranchName();
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }
}
