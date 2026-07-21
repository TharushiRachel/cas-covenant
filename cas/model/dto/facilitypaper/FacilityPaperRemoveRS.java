package com.itechro.cas.model.dto.facilitypaper;

public class FacilityPaperRemoveRS {

    private String message;

    private boolean successfullyRemoved;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessfullyRemoved() {
        return successfullyRemoved;
    }

    public void setSuccessfullyRemoved(boolean successfullyRemoved) {
        this.successfullyRemoved = successfullyRemoved;
    }

    @Override
    public String toString() {
        return "FacilityPaperRemoveRS{" +
                "message='" + message + '\'' +
                ", successfullyRemoved=" + successfullyRemoved +
                '}';
    }
}
