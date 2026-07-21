package com.itechro.cas.model.dto.coveringApproval;


import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CovAppLoadOptionDTO implements Serializable {

    private boolean loadComments;

    private boolean loadBasicInformation;

    private boolean loadStatusHistory;

    public void loadAllData(){
        this.loadComments();
        this.loadBasicInformation();
        this.loadStatusHistory();
    }

    private void loadComments() {
        this.loadComments = true;
    }

    private void loadBasicInformation() {
        this.loadBasicInformation = true;
    }

    private void loadStatusHistory(){
        this.loadStatusHistory = true;
    }
}
