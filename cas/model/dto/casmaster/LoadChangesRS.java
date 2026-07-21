package com.itechro.cas.model.dto.casmaster;

import java.io.Serializable;

public class LoadChangesRS<T> implements Serializable {

    private T previousDTO;

    private T updatedDTO;

    public T getPreviousDTO() {
        return previousDTO;
    }

    public void setPreviousDTO(T previousDTO) {
        this.previousDTO = previousDTO;
    }

    public T getUpdatedDTO() {
        return updatedDTO;
    }

    public void setUpdatedDTO(T updatedDTO) {
        this.updatedDTO = updatedDTO;
    }
}
