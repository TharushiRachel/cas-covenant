package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bccpaper.BccCompanyDirector;

import java.io.Serializable;

public class BccCompanyDirectorDTO implements Serializable {

    private Integer bccCompanyDirectorID;

    private Integer boardCreditCommitteePaperID;

    private String companyDirectorName;

    private Integer age;

    private String address;

    private String nicOrBRN;

    private Double shareHolding;

    private DomainConstants.ConstitutionType constitutionType;

    private AppsConstants.YesNo creditCard;

    private AppsConstants.Status status;

    public BccCompanyDirectorDTO(BccCompanyDirector bccCompanyDirector) {
        this.bccCompanyDirectorID = bccCompanyDirector.getBccCompanyDirectorID();
        this.boardCreditCommitteePaperID = bccCompanyDirector.getBoardCreditCommitteePaper().getBoardCreditCommitteePaperID();
        this.companyDirectorName = bccCompanyDirector.getCompanyDirectorName();
        this.age = bccCompanyDirector.getAge();
        this.address = bccCompanyDirector.getAddress();
        this.nicOrBRN = bccCompanyDirector.getNicOrBRN();
        this.shareHolding = bccCompanyDirector.getShareHolding();
        this.constitutionType = bccCompanyDirector.getConstitutionType();
        this.status = bccCompanyDirector.getStatus();
        this.creditCard = bccCompanyDirector.getCreditCard();
    }

    public BccCompanyDirectorDTO() {
    }

    public Integer getBccCompanyDirectorID() {
        return bccCompanyDirectorID;
    }

    public void setBccCompanyDirectorID(Integer bccCompanyDirectorID) {
        this.bccCompanyDirectorID = bccCompanyDirectorID;
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
    }

    public String getCompanyDirectorName() {
        return companyDirectorName;
    }

    public void setCompanyDirectorName(String companyDirectorName) {
        this.companyDirectorName = companyDirectorName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNicOrBRN() {
        return nicOrBRN;
    }

    public void setNicOrBRN(String nicOrBRN) {
        this.nicOrBRN = nicOrBRN;
    }

    public Double getShareHolding() {
        return shareHolding;
    }

    public void setShareHolding(Double shareHolding) {
        this.shareHolding = shareHolding;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DomainConstants.ConstitutionType getConstitutionType() {
        return constitutionType;
    }

    public void setConstitutionType(DomainConstants.ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
    }

    public String getConstitutionTypeValue() {
        if (this.getConstitutionType() != null) {
            return this.getConstitutionType().getDescription();
        } else {
            return "";
        }
    }

    public AppsConstants.YesNo getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(AppsConstants.YesNo creditCard) {
        this.creditCard = creditCard;
    }

    public String getCreditCardValue() {
        if (this.getCreditCard() != null) {
            return this.getCreditCard().getStrVal();
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return "BccCompanyDirectorDTO{" +
                "bccCompanyDirectorID=" + bccCompanyDirectorID +
                ", boardCreditCommitteePaperID=" + boardCreditCommitteePaperID +
                ", companyDirectorName='" + companyDirectorName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", nicOrBRN='" + nicOrBRN + '\'' +
                ", shareHolding=" + shareHolding +
                ", constitutionType=" + constitutionType +
                ", creditCard=" + creditCard +
                ", status=" + status +
                '}';
    }
}
