package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CACommittee;
import com.itechro.cas.model.domain.casmaster.CAUser;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class CALevelDTO implements Serializable {

    private Integer levelConfigId;

    private Integer levelId;

    private String combination;

    private AppsConstants.Status status;

    private AppsConstants.CAPathType pathType;

    private Integer committeeId;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private Integer userCount;

    private AppsConstants.CACommitteeStatus actionStatus;

    private List<CAUserDTO> caUserDTOList;
}
