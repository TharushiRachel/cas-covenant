package com.itechro.cas.model.dto.casmaster;

import lombok.Data;

@Data
public class LoggedInUserWorkFlowByStatusRQ {
    private String loggedInUserUpmGroupCode;// Upm AccessLevel level ==> 10 , 20 , 50 , 71 ...

    private String loggedInUserSolID;

    private Integer workFlowTemplateID;

    private Level level; // Get low or high Upm levels according to loggedInUserUpmGroupCode ===> high , low

    public enum Level {
        LOW,
        HIGH
    }
}
