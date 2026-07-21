package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.security.CredentialsDTO;

import java.io.Serializable;
import java.util.Date;

public class UpdateLowerLevelRQ implements Serializable {

    private Integer previousEntityID;

    private Integer newEntityID;

    private CredentialsDTO credentialsDTO;

    private Date date;

    public UpdateLowerLevelRQ() {
    }

    public UpdateLowerLevelRQ(Integer newEntityID, Integer previousEntityID, CredentialsDTO credentialsDTO, Date date) {
        this.previousEntityID = previousEntityID;
        this.newEntityID = newEntityID;
        this.credentialsDTO = credentialsDTO;
        this.date = date;
    }

    public Integer getPreviousEntityID() {
        return previousEntityID;
    }

    public void setPreviousEntityID(Integer previousEntityID) {
        this.previousEntityID = previousEntityID;
    }

    public Integer getNewEntityID() {
        return newEntityID;
    }

    public void setNewEntityID(Integer newEntityID) {
        this.newEntityID = newEntityID;
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
