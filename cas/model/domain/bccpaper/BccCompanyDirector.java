package com.itechro.cas.model.domain.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_BCC_COMPANY_DIRECTOR")
public class BccCompanyDirector extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BCC_COMPANY_DIRECTOR")
    @SequenceGenerator(name = "SEQ_T_BCC_COMPANY_DIRECTOR", sequenceName = "SEQ_T_BCC_COMPANY_DIRECTOR", allocationSize = 1)
    @Column(name = "BCC_COMPANY_DIRECTOR_ID")
    private Integer bccCompanyDirectorID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCC_PAPER_ID")
    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    @Column(name = "COMPANY_DIRECTOR_NAME")
    private String companyDirectorName;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "NIC_BRN")
    private String nicOrBRN;

    @Column(name = "SHARE_HOLDING")
    private Double shareHolding;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONSTITUTION_TYPE")
    private DomainConstants.ConstitutionType constitutionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "CREDIT_CARD")
    private AppsConstants.YesNo creditCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getBccCompanyDirectorID() {
        return bccCompanyDirectorID;
    }

    public void setBccCompanyDirectorID(Integer bccCompanyDirectorID) {
        this.bccCompanyDirectorID = bccCompanyDirectorID;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public void setBoardCreditCommitteePaper(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        this.boardCreditCommitteePaper = boardCreditCommitteePaper;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public DomainConstants.ConstitutionType getConstitutionType() {
        return constitutionType;
    }

    public void setConstitutionType(DomainConstants.ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
    }

    public AppsConstants.YesNo getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(AppsConstants.YesNo creditCard) {
        this.creditCard = creditCard;
    }
}
