package com.itechro.cas.model.dto.acae.request;

import java.sql.Date;

public class ACAERejectACAEPaperRQ {

    private String searchReference; //REF_NUM,
    private String accountNumber; //ACCT_NO,
    private String previousUser; //PRE_USER,
    private String currentUser; //CURRENT_USER,
    private Date recievedDate; //RECEIVED_DATE,
    private String acaeStatus; //ACAE_STATUS,
    private Date approvedDate; //APPROVED_DATE
    private String activeComment;
    private Date negDate;

    private String rejectUser;


    public String getSearchReference() {
        return searchReference;
    }

    public void setSearchReference(String searchReference) {
        this.searchReference = searchReference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPreviousUser() {
        return previousUser;
    }

    public void setPreviousUser(String previousUser) {
        this.previousUser = previousUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Date getRecievedDate() {
        return recievedDate;
    }

    public void setRecievedDate(Date recievedDate) {
        this.recievedDate = recievedDate;
    }

    public String getAcaeStatus() {
        return acaeStatus;
    }

    public void setAcaeStatus(String acaeStatus) {
        this.acaeStatus = acaeStatus;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getActiveComment() {
        return activeComment;
    }

    public void setActiveComment(String activeComment) {
        this.activeComment = activeComment;
    }

    public Date getNegDate() {
        return negDate;
    }

    public void setNegDate(Date negDate) {
        this.negDate = negDate;
    }

    public String getRejectUser() {
        return rejectUser;
    }

    public void setRejectUser(String rejectUser) {
        this.rejectUser = rejectUser;
    }
}
