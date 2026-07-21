package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CAUserTemp;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CAUserDTO implements Serializable {

    private Integer userConfigId;

    private AppsConstants.Status userStatus;

    private AppsConstants.Status status;

    private Integer committeeId;

    private Integer levelConfigId;

    private AppsConstants.CAPathType pathType;

    private Integer userId;

    private String userName;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private AppsConstants.CACommitteeStatus actionStatus;

    public CAUserDTO(){
    }

    public CAUserDTO(CAUserTemp caUserTemp){
        this.userConfigId = caUserTemp.getUserConfigId();
        this.userStatus = caUserTemp.getUserStatus();
        this.committeeId = caUserTemp.getCaCommitteeTemp().getCommitteeId();
        this.levelConfigId = caUserTemp.getCaLevelTemp().getLevelConfigId();
        this.userId = caUserTemp.getUserPool().getUserId();
    }
}
