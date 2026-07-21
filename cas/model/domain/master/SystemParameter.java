package com.itechro.cas.model.domain.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.Persistent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_SYSTEM_PARAMETER")
public class SystemParameter extends Persistent {

    private static final long serialVersionUID = -6126749147802128554L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SYSTEM_PARAMETER")
    @SequenceGenerator(name = "SEQ_T_SYSTEM_PARAMETER", sequenceName = "SEQ_T_SYSTEM_PARAMETER", allocationSize = 1)
    @Column(name = "system_parameter_id")
    private Integer systemParameterID;

    @Column(name = "param_key")
    private String paramKey;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "description")
    private String description;

    @Column(name = "param_value")
    private String paramValue;

    @Column(name = "param_type")
    private String paramType;

    @Enumerated(EnumType.STRING)
    @Column(name = "editable")
    private AppsConstants.YesNo editable;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppsConstants.Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

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

    public AppsConstants.YesNo getEditable() {
        return editable;
    }

    public void setEditable(AppsConstants.YesNo editable) {
        this.editable = editable;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
