package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "T_AF_BORROWER_GUARANTOR")
public class AFBorrowerGuarantor extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_BORROWER_GUARANTOR")
    @SequenceGenerator(name = "SEQ_T_AF_BORROWER_GUARANTOR", sequenceName = "SEQ_T_AF_BORROWER_GUARANTOR", allocationSize = 1)
    @Column(name = "BORROWER_GUARANTOR_ID")
    private Integer borrowerGuarantorID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASIC_INFORMATION_ID")
    private BasicInformation basicInformation;

    @Column(name = "BANK_AND_BRANCH")
    private String bankAndBranch;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_GRANTED")
    private Date dateGranted;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "BORROWER_NAME")
    private String borrowerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getBorrowerGuarantorID() {
        return borrowerGuarantorID;
    }

    public void setBorrowerGuarantorID(Integer borrowerGuarantorID) {
        this.borrowerGuarantorID = borrowerGuarantorID;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

    public String getBankAndBranch() {
        return bankAndBranch;
    }

    public void setBankAndBranch(String bankAndBranch) {
        this.bankAndBranch = bankAndBranch;
    }

    public Date getDateGranted() {
        return dateGranted;
    }

    public void setDateGranted(Date dateGranted) {
        this.dateGranted = dateGranted;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
