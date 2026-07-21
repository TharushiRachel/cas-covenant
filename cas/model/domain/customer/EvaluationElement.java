package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPReviewerComment;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 *
 *
 * @author tharushi
 */

@Entity
@Table(name = "CEF_EVALUATION_ELEMENT")
public class EvaluationElement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CEF_EVALUATION_ELEMENT")
    @SequenceGenerator(name = "CEF_EVALUATION_ELEMENT", sequenceName = "CEF_EVALUATION_ELEMENT", allocationSize = 1)
    @Column(name = "EE_ID")
    private Integer EvaluationElementId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "CAPTION")
    private String caption;

    @Enumerated(EnumType.STRING)
    @Column(name = "ELE_TYPE")
    private AppsConstants.EvaluationElement evaluationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.CustomerEvaluation status;

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

    @Column(name = "GROUP_BY")
    private Integer groupBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "REQUIRED")
    private AppsConstants.YesNo isRequired;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "evaluationElement")
    private Set<EvaluationAnswers> answersSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "evaluationElement")
    private Set<CustomerEvaluationResults> customerEvaluationResultsSet;

    public Integer getEvaluationElementId() {
        return EvaluationElementId;
    }

    public void setEvaluationElementId(Integer evaluationElementId) {
        EvaluationElementId = evaluationElementId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public AppsConstants.EvaluationElement getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(AppsConstants.EvaluationElement evaluationType) {
        this.evaluationType = evaluationType;
    }

    public AppsConstants.CustomerEvaluation getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.CustomerEvaluation status) {
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

    public Integer getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(Integer groupBy) {
        this.groupBy = groupBy;
    }

    public AppsConstants.YesNo getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(AppsConstants.YesNo isRequired) {
        this.isRequired = isRequired;
    }

    public Set<EvaluationAnswers> getAnswersSet() {
        return answersSet;
    }

    public void setAnswersSet(Set<EvaluationAnswers> answersSet) {
        this.answersSet = answersSet;
    }

    public Set<CustomerEvaluationResults> getCustomerEvaluationResultsSet() {
        return customerEvaluationResultsSet;
    }

    public void setCustomerEvaluationResultsSet(Set<CustomerEvaluationResults> customerEvaluationResultsSet) {
        this.customerEvaluationResultsSet = customerEvaluationResultsSet;
    }
}
