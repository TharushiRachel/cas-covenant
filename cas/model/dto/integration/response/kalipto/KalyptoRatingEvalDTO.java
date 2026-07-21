package com.itechro.cas.model.dto.integration.response.kalipto;

import com.itechro.cas.model.dto.integration.ws.response.kalypto.KalyptoRatingEval;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KalyptoRatingEvalDTO implements Serializable {

    private String counterpartyName;

    private String uniqueIdentifier;

    private String model;

    private String ratingscore;

    public KalyptoRatingEvalDTO(KalyptoRatingEval kalyptoRatingEval){
        this.counterpartyName = kalyptoRatingEval.getCounterpartyName();
        this.uniqueIdentifier = kalyptoRatingEval.getUniqueIdentifier();
        this.model = kalyptoRatingEval.getModel();
        this.ratingscore = kalyptoRatingEval.getRatingscore();
    }

    public String getCounterpartyName() {
        return counterpartyName;
    }

    public void setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRatingscore() {
        return ratingscore;
    }

    public void setRatingscore(String ratingscore) {
        this.ratingscore = ratingscore;
    }

}
