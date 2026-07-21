package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;

public class SystemParamSearchRQ extends PagedParamDTO implements Serializable {

    private static final long serialVersionUID = 8912870185766468012L;

    private String paramKey;

    private String paramName;

    private String description;

    private String paramValue;

    private String paramType;

    private AppsConstants.Status status;


    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "SystemParamSearchRQ{" +
                "paramKey='" + paramKey + '\'' +
                ", paramName='" + paramName + '\'' +
                ", description='" + description + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", paramType='" + paramType + '\'' +
                ", status=" + status +
                '}';
    }
}
