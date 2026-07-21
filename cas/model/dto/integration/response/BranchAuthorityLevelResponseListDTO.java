package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;
import java.util.ArrayList;

public class BranchAuthorityLevelResponseListDTO implements Serializable {


    private ArrayList<BranchAuthorityLevelResponseDTO> branchAuthorityLevelResponseDTOList;

    private boolean success;

    public BranchAuthorityLevelResponseListDTO(){}

    public BranchAuthorityLevelResponseListDTO(BranchAuthorityLevelResponse[] branchAuthorityLevelResponses){
        if(branchAuthorityLevelResponses != null) {
            for (BranchAuthorityLevelResponse branchAuthorityLevelResponse : branchAuthorityLevelResponses) {
                this.getBranchAuthorityLevelResponseDTOList().add(new BranchAuthorityLevelResponseDTO(branchAuthorityLevelResponse));
            }
        }
    }

    public ArrayList<BranchAuthorityLevelResponseDTO> getBranchAuthorityLevelResponseDTOList() {
        if (branchAuthorityLevelResponseDTOList == null) {
            branchAuthorityLevelResponseDTOList = new ArrayList<>();
        }
        return branchAuthorityLevelResponseDTOList;
    }

    public void setBranchAuthorityLevelResponseDTOList(ArrayList<BranchAuthorityLevelResponseDTO> branchAuthorityLevelResponseDTOList) {
        this.branchAuthorityLevelResponseDTOList = branchAuthorityLevelResponseDTOList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BranchAuthorityLevelResponseListDTO{" +
                "branchAuthorityLevelResponseDTOList=" + branchAuthorityLevelResponseDTOList +
                '}';
    }

}
