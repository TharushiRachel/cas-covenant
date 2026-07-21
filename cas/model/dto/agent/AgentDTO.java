package com.itechro.cas.model.dto.agent;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.agent.Agent;
import com.itechro.cas.model.domain.master.User;
import com.itechro.cas.model.dto.master.UserDTO;

import java.io.Serializable;

public class AgentDTO implements Serializable {

    private static final long serialVersionUID = 3713861196282252613L;

    private Integer agentID;

    private String mobileNumber;

    private String supervisorADUserID;

    private String nic;

    private String remark;

    private AppsConstants.Status status;

    private UserDTO userDTO;

    public AgentDTO() {
    }

    public AgentDTO(Agent agent, UserDTO userDTO) {
        this.agentID = agent.getAgentID();
        this.mobileNumber = agent.getMobileNumber();
        this.supervisorADUserID = agent.getSupervisorADUserID();
        this.nic = agent.getNic();
        this.remark = agent.getRemark();
        this.status = agent.getStatus();
        this.userDTO = userDTO;
    }

    public AgentDTO(Agent agent, User user) {
        this.agentID = agent.getAgentID();
        this.mobileNumber = agent.getMobileNumber();
        this.supervisorADUserID = agent.getSupervisorADUserID();
        this.nic = agent.getNic();
        this.remark = agent.getRemark();
        this.status = agent.getStatus();
        this.userDTO = new UserDTO(user);
    }

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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
                "agentID=" + agentID +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", supervisorADUserID='" + supervisorADUserID + '\'' +
                ", nic='" + nic + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", userDTO=" + userDTO +
                '}';
    }
}
