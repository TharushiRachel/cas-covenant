package com.itechro.cas.model.domain.agent;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AGENT")
public class Agent extends UserTrackableEntity {

    private static final long serialVersionUID = 971098011251720245L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AGENT")
    @SequenceGenerator(name = "SEQ_T_AGENT", sequenceName = "SEQ_T_AGENT", allocationSize = 1)
    @Column(name = "AGENT_ID")
    private Integer agentID;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "SUPERVISOR_AD_USER_ID")
    private String supervisorADUserID;

    @Column(name = "NIC")
    private String nic;

    @Column(name = "REMARK")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getAgentID() {
        return agentID;
    }

    public void setAgentID(Integer agentID) {
        this.agentID = agentID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSupervisorADUserID() {
        return supervisorADUserID;
    }

    public void setSupervisorADUserID(String supervisorADUserID) {
        this.supervisorADUserID = supervisorADUserID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
