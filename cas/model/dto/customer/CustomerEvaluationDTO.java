package com.itechro.cas.model.dto.customer;


import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.util.logging.Log;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@Data
@Setter
@Getter
public class CustomerEvaluationDTO implements Serializable, Comparable<CustomerEvaluationDTO> {
    private Integer evaluationElementId;

    private Integer parentId;

    private String elementCaption;

    private Integer answerId;

    private String answerCaption;
    private List<CustomerEvaluationDTO> LstEva;

    private String eleType;

//    private boolean answered;


    @Override
    public int compareTo(@NotNull CustomerEvaluationDTO o) {
        return 0;
    }

//    public boolean Answered() {
//
//        if (this.eleType.equalsIgnoreCase("Q")) {
//
//            if (this.answerId == null) {
//
//                answered = false;
//
//            } else if (this.answerId == 0) {
//                answered = false;
//            } else {
//                answered = true;
//            }
//        } else {
//            System.out.println("LstEva" +LstEva);
//            System.out.println("elementCaption" +elementCaption);
//            System.out.println("evaluationElementId" +evaluationElementId);
//            for (CustomerEvaluationDTO c : LstEva) {
//                answered = c.Answered();
//                System.out.println("answered" +answered);
//                if (answered) {
//                    break;
//                }
//            }
//        }
//
//        return answered;
//    }

//    public boolean Answered() {
//        if (this.eleType.equalsIgnoreCase("Q")) {
//            if (this.answerId == null || this.answerId == 0) {
//                return false; // Question without an answer
//            } else {
//                return true; // Question with an answer
//            }
//        } else if (LstEva != null) { // Ensure LstEva is not null
//            for (CustomerEvaluationDTO child : LstEva) {
//                if (child != null && child.Answered()) {
//                    return true; // At least one child is answered
//                }
//            }
//        }
//
//        return false; // Default case: Not a question and no child is answered
//    }
}







