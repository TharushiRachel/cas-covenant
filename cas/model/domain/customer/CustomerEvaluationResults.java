package com.itechro.cas.model.domain.customer;


import javax.persistence.*;

/**
 *
 *
 * @author tharushi
 */

@Entity
@Table(name = "CEF_CUSTOMER_EVALUATION_RESULT")
public class CustomerEvaluationResults {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CEF_CUSTOMER_EVALUATION_RESULT")
    @SequenceGenerator(name = "SEQ_CEF_CUSTOMER_EVALUATION_RESULT", sequenceName = "SEQ_CEF_CUSTOMER_EVALUATION_RESULT", allocationSize = 1)
//    @Column(name = "C_E_ID")
    private Integer customerEvaluationResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_E_ID")
    private CustomerEvaluation customerEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EE_ID")
    private EvaluationElement evaluationElement;

    @Column(name = "SCORE")
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "A_ID")
    private EvaluationAnswers evaluationAnswers;

    public CustomerEvaluation getCustomerEvaluation() {
        return customerEvaluation;
    }

    public void setCustomerEvaluation(CustomerEvaluation customerEvaluation) {
        this.customerEvaluation = customerEvaluation;
    }

    public EvaluationElement getEvaluationElement() {
        return evaluationElement;
    }

    public void setEvaluationElement(EvaluationElement evaluationElement) {
        this.evaluationElement = evaluationElement;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public EvaluationAnswers getEvaluationAnswers() {
        return evaluationAnswers;
    }

    public void setEvaluationAnswers(EvaluationAnswers evaluationAnswers) {
        this.evaluationAnswers = evaluationAnswers;
    }
}
