package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;
import java.util.ArrayList;

public class BranchLoadResponseListDTO implements Serializable {

    private ArrayList<BranchLoadResponseDTO> branchLoadResponseDTOArrayList;

    private boolean success;

    public BranchLoadResponseListDTO(BranchLoadResponseList branchLoadResponseList) {

        if (branchLoadResponseList != null) {
            if (branchLoadResponseList.getBranchLoadResponseArrayList() != null) {
                for (BranchLoadResponse branchLoadResponse : branchLoadResponseList.getBranchLoadResponseArrayList()) {
                    this.getBranchLoadResponseDTOArrayList().add(new BranchLoadResponseDTO(branchLoadResponse));
                }
            }
        }
    }

    public ArrayList<BranchLoadResponseDTO> getBranchLoadResponseDTOArrayList() {
        if (branchLoadResponseDTOArrayList == null) {
            branchLoadResponseDTOArrayList = new ArrayList<>();
        }
        return branchLoadResponseDTOArrayList;
    }

    public void setBranchLoadResponseDTOArrayList(ArrayList<BranchLoadResponseDTO> branchLoadResponseDTOArrayList) {
        this.branchLoadResponseDTOArrayList = branchLoadResponseDTOArrayList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BranchLoadResponseDTO getBranchResponse(String branchCode) {
        return this.getBranchLoadResponseDTOArrayList().stream().filter(branchResponse -> {
            return branchResponse.getBranchCode().equals(branchCode);
        }).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "BranchLoadResponseListDTO{" +
                "branchLoadResponseDTOArrayList=" + branchLoadResponseDTOArrayList +
                '}';
    }
}
