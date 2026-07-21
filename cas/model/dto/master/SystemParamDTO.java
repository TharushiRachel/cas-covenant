package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.master.SystemParameter;

import java.io.Serializable;

public class SystemParamDTO implements Serializable {

    private static final long serialVersionUID = 3629582850001302963L;

    private Integer systemParameterID;

    private String paramKey;

    private String paramName;

    private String description;

    private String paramValue;

    private String paramType;

    private AppsConstants.Status status;

    private AppsConstants.YesNo  editable;

    public SystemParamDTO() {
    }

    public SystemParamDTO(SystemParameter systemParameter) {
        this.systemParameterID = systemParameter.getSystemParameterID();
        this.paramKey = systemParameter.getParamKey();
        this.paramName = systemParameter.getParamName();
        this.description = systemParameter.getDescription();
        this.paramValue = systemParameter.getParamValue();
        this.paramType = systemParameter.getParamType();
        this.status = systemParameter.getStatus();
      //  this.editable = systemParameter.getEditable();
    }

    public Integer getSystemParameterID() {
        return systemParameterID;
    }

    public void setSystemParameterID(Integer systemParameterID) {
        this.systemParameterID = systemParameterID;
    }

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


    public AppsConstants.YesNo getEditable() { return editable;}

   public void setEditable(AppsConstants.YesNo editability) { this.editable = editable;  }

    @Override
    public String toString() {
        return "SystemParamDTO{" +
                "systemParameterID=" + systemParameterID +
                ", paramKey='" + paramKey + '\'' +
                ", paramName='" + paramName + '\'' +
                ", description='" + description + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", paramType='" + paramType + '\'' +
                ", status=" + status +
                '}';
    }
}
