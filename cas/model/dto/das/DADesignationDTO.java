package com.itechro.cas.model.dto.das;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DADesignationDTO implements Serializable {

    private Integer id;//

    private String description;//

    private String createdBy;//

    private Date createdDate;//

    private String modifiedBy;//

    private Date modifiedDate;//

    private String designation;//

    private Integer parentRecId;//

    private String status;//

    private String approvedBy;

    private Date approvedDate;

    private String authorizedDisplayName;

    private String approveStatus;
    private String designationCode;

    private String isCommittee;


}
