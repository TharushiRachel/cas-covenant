package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 *
 *
 * @author tharushi
 */

@Entity
@Table(name = "CEF_ANSWER")
public class EvaluationAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CEF_ANSWER")
    @SequenceGenerator(name = "CEF_ANSWER", sequenceName = "CEF_ANSWER", allocationSize = 1)
    @Column(name = "A_ID")
    private Integer  answerId;

    @Column(name = "SCORE")
    private Integer score;

    @Column(name = "CAPTION")
    private String caption;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Answers status;

    @Column(name = "SAVED_BY")
    private String savedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SAVED_ON")
    private Date savedOn;

    @Column(name = "AUTH_BY")
    private String authorizedBy;

    @Column(name = "AUTH_ON")
    private String authorizedOn;

    @Column(name = "ORDER_BY")
    private Integer orderBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EE_ID")
    private EvaluationElement evaluationElement;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "evaluationAnswers")
    private Set<CustomerEvaluationResults> customerEvaluationResultsSet;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public AppsConstants.Answers getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Answers status) {
        this.status = status;
    }

    public String getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(String savedBy) {
        this.savedBy = savedBy;
    }

    public Date getSavedOn() {
        return savedOn;
    }

    public void setSavedOn(Date savedOn) {
        this.savedOn = savedOn;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public String getAuthorizedOn() {
        return authorizedOn;
    }

    public void setAuthorizedOn(String authorizedOn) {
        this.authorizedOn = authorizedOn;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public EvaluationElement getEvaluationElement() {
        return evaluationElement;
    }

    public void setEvaluationElement(EvaluationElement evaluationElement) {
        this.evaluationElement = evaluationElement;
    }

    public Set<CustomerEvaluationResults> getCustomerEvaluationResultsSet() {
        return customerEvaluationResultsSet;
    }

    public void setCustomerEvaluationResultsSet(Set<CustomerEvaluationResults> customerEvaluationResultsSet) {
        this.customerEvaluationResultsSet = customerEvaluationResultsSet;
    }
}
