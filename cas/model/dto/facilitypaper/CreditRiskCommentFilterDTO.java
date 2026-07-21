package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskComment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreditRiskCommentFilterDTO implements Serializable {

    private FPCreditRiskCommentDTO activeRiskComment;

    private List<FPCreditRiskCommentDTO> previousLockedComments;

    private boolean isLoad;

    private boolean isLoadHistory;

    public FPCreditRiskCommentDTO getActiveRiskComment() {
        return activeRiskComment;
    }

    public void setActiveRiskComment(FPCreditRiskCommentDTO activeRiskComment) {
        this.activeRiskComment = activeRiskComment;
    }

    public List<FPCreditRiskCommentDTO> getPreviousLockedComments() {
        if (previousLockedComments == null) {
            previousLockedComments = new ArrayList<>();
        }
        return previousLockedComments;
    }

    public void setPreviousLockedComments(List<FPCreditRiskCommentDTO> previousLockedComments) {
        this.previousLockedComments = previousLockedComments;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public void addPreviousLockedComments(FPCreditRiskCommentDTO fpCreditRiskComment) {
        this.getPreviousLockedComments().add(fpCreditRiskComment);
    }

    public boolean isLoadHistory() {
        return isLoadHistory;
    }

    public void setLoadHistory(boolean loadHistory) {
        isLoadHistory = loadHistory;
    }

    @Override
    public String toString() {
        return "CreditRiskCommentFilterDTO{" +
                "activeRiskComment=" + activeRiskComment +
                ", previousLockedComments=" + previousLockedComments +
                ", isLoad=" + isLoad +
                ", isLoadHistory=" + isLoadHistory +
                '}';
    }
}
