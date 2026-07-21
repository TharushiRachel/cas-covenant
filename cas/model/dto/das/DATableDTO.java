package com.itechro.cas.model.dto.das;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DATableDTO {

    private Integer designationId;
    private DAColumnsDTO daColumnsDTO;
    private String modifiedBy;
    private String createdBy;
    private Date modifiedDate;

    private Date createdDate;

    public DATableDTO(Integer designationId, DAColumnsDTO daColumnsDTO, String modifiedBy, String createdBy, Date modifiedDate, Date createdDate) {
        this.designationId = designationId;
        this.daColumnsDTO = daColumnsDTO;
        this.modifiedBy = modifiedBy;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }
}
