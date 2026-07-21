package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Date;

public class RemarkDTO implements Serializable, Comparable<RemarkDTO> {

    private Integer facilityPaperID;

    private Integer fpWorkflowRoutingID;

    private String assignUser;

    private String dateStr;

    private String comment;

    private String actionBy;

    private String action;

    private String actionMessage;

    public RemarkDTO() {
    }

    public RemarkDTO(Integer facilityPaperID, Integer fpWorkflowRoutingID, String assignUser, String dateStr, String comment) {
        this.facilityPaperID = facilityPaperID;
        this.fpWorkflowRoutingID = fpWorkflowRoutingID;
        this.assignUser = assignUser;
        this.dateStr = dateStr;
        this.comment = comment;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getFpWorkflowRoutingID() {
        return fpWorkflowRoutingID;
    }

    public void setFpWorkflowRoutingID(Integer fpWorkflowRoutingID) {
        this.fpWorkflowRoutingID = fpWorkflowRoutingID;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    @Override
    public int compareTo(RemarkDTO remarkDTO) {

        try {
            Date dateOne = CalendarUtil.getDefaultParsedDateTime(this.dateStr);
            Date date = CalendarUtil.getDefaultParsedDateTime(remarkDTO.dateStr);
            //int x = dateOne.compareTo(date);
            return dateOne.compareTo(date);
        } catch (Exception e) {
            return 0;
        }

    }
}
