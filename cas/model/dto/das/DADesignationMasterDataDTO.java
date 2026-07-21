package com.itechro.cas.model.dto.das;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
@Data

public class DADesignationMasterDataDTO {


    private Integer id;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private Integer parentRecId;
    private String status;
    private String designation;
    private String description;
    private String approvedBy;
    private Date approvedDate;
    private String authorizedDisplayName;
    private String approveStatus;
    private String designationCode;
    private Integer displayOrder;
    private String isCommittee;

}
