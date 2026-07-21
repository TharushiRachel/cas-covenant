package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_FACILITY_REPAYMENT")
public class FacilityRepayment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_REPAYMENT")
    @SequenceGenerator(name = "SEQ_T_FACILITY_REPAYMENT", sequenceName = "SEQ_T_FACILITY_REPAYMENT", allocationSize = 1)
    @Column(name = "FACILITY_REPAYMENT_ID")
    private Integer facilityRepaymentID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Enumerated(EnumType.STRING)
    @Column(name = "REPAYMENT_TYPE")
    private DomainConstants.RepaymentType repaymentType;

    @Column(name = "LOAN_TERM")
    private Integer loanTerm;

    @Column(name = "PAYMENT_DETAIL")
    private String paymentDetail;

    @Column(name = "DOWN_PAYMENT")
    private String downPayment;

    @Column(name = "REPAYMENT_COMMENT")
    private String repaymentComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_FREQUECNY")
    private DomainConstants.PaymentFrequency paymentFrequency;

    public Integer getFacilityRepaymentID() {
        return facilityRepaymentID;
    }

    public void setFacilityRepaymentID(Integer facilityRepaymentID) {
        this.facilityRepaymentID = facilityRepaymentID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public DomainConstants.RepaymentType getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(DomainConstants.RepaymentType repaymentType) {
        this.repaymentType = repaymentType;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(String paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getRepaymentComment() {
        return repaymentComment;
    }

    public void setRepaymentComment(String repaymentComment) {
        this.repaymentComment = repaymentComment;
    }

    public DomainConstants.PaymentFrequency getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(DomainConstants.PaymentFrequency paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }
}
