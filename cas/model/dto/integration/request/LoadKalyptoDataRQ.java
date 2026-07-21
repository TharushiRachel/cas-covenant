package com.itechro.cas.model.dto.integration.request;

import java.io.Serializable;

public class LoadKalyptoDataRQ implements Serializable {

    private String uniqueIdentifier;

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    @Override
    public String toString() {
        return "LoadKalyptoDataRQ{" +
                "uniqueIdentifier='" + uniqueIdentifier + '\'' +
                '}';
    }
}
