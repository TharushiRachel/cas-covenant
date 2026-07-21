package com.itechro.cas.commons.constants;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeConstants;

import java.util.*;

public class AppsConstants {
    public enum ResponseStatus {
        SUCCESS, FAILED;

        public static ResponseStatus resolveStatus(String statusStr) {
            ResponseStatus matchingStatus = null;
            if (!StringUtils.isEmpty(statusStr)) {
                matchingStatus = ResponseStatus.valueOf(statusStr.trim());
            }
            return matchingStatus;
        }
    }

    public enum YesNo {

        Y("Yes", true, 1), N("No", false, 0);

        private String strVal;

        private Boolean boolVal;

        private Integer intVal;

        YesNo(String strVal, Boolean boolVal, Integer intVal) {
            this.strVal = strVal;
            this.boolVal = boolVal;
            this.intVal = intVal;
        }

        public static YesNo valueOf(Boolean boolVal) {
            YesNo matchingStatus = null;
            for (YesNo yesno : YesNo.values()) {
                if (yesno.getBoolVal().equals(boolVal)) {
                    matchingStatus = yesno;
                    break;
                }
            }
            return matchingStatus;
        }

        public static YesNo resolver(String val) {
            YesNo matchingStatus = null;
            if (val != null) {
                matchingStatus = YesNo.valueOf(val);
            }
            return matchingStatus;
        }

        public static Map<String, String> getYesNoMap() {
            Map<String, String> map = new LinkedHashMap<String, String>();
            for (YesNo yesNo : YesNo.values()) {
                map.put(yesNo.toString(), yesNo.getStrVal());
            }
            return map;
        }

        public String getStrVal() {
            return strVal;
        }

        public Boolean getBoolVal() {
            return boolVal;
        }

        public Integer getIntVal() {
            return intVal;
        }
    }

    public enum Status {
        ACT("Active"),
        INA("Inactive"),
        RMV("Remove"),
        SUCCESS("SUCCESS"),
        Success("Success");

        private String description;

        Status(String description) {
            this.description = description;
        }

        public static Status resolveStatus(String statusStr) {
            Status matchingStatus = null;
            if (!StringUtils.isEmpty(statusStr)) {
                matchingStatus = Status.valueOf(statusStr.trim());
            }
            return matchingStatus;
        }

        public static Map<String, String> getStatusMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(Status.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum CovenantStatus {
        Active("Active"),
        Inactive("Inactive"),
        MODIFIED("Modified"); // Add the new enum value

        private String name;

        CovenantStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum EntityOperationType {
        ADDED, UPDATED, REMOVED;
    }

    public enum DaysOfWeek {

        Su(Calendar.SUNDAY, DateTimeConstants.SUNDAY, "Sun"),
        Mo(Calendar.MONDAY, DateTimeConstants.MONDAY, "Mon"),
        Tu(Calendar.TUESDAY, DateTimeConstants.TUESDAY, "Tue"),
        We(Calendar.WEDNESDAY, DateTimeConstants.WEDNESDAY, "Wed"),
        Th(Calendar.THURSDAY, DateTimeConstants.THURSDAY, "Thu"),
        Fr(Calendar.FRIDAY, DateTimeConstants.FRIDAY, "Fri"),
        Sa(Calendar.SATURDAY, DateTimeConstants.SATURDAY, "Sat");

        private Integer dayOfWeek;
        private Integer dayOfWeekJoda;
        private String shortCodeOfDay;

        DaysOfWeek(Integer dayOfWeek, Integer dayOfWeekJoda, String shortCodeOfDay) {
            this.dayOfWeek = dayOfWeek;
            this.dayOfWeekJoda = dayOfWeekJoda;
            this.shortCodeOfDay = shortCodeOfDay;
        }

        public static DaysOfWeek resolveDayOfWeek(Integer dayOfWeek) {
            DaysOfWeek matchingDay = null;
            if (dayOfWeek != null) {
                DaysOfWeek[] values = DaysOfWeek.values();
                for (DaysOfWeek day : values) {
                    if (day.getDayOfWeek().equals(dayOfWeek)) {
                        matchingDay = day;
                    }
                }
            }
            return matchingDay;
        }


        public static String resolveShortCodeOfDay(Integer dayOfWeek) {
            String matchingDay = null;
            if (dayOfWeek != null) {
                DaysOfWeek[] values = DaysOfWeek.values();
                for (DaysOfWeek day : values) {
                    if (day.getDayOfWeek().equals(dayOfWeek)) {
                        matchingDay = day.getShortCodeOfDay();
                    }
                }
            }
            return matchingDay;
        }

        public Integer getDayOfWeek() {
            return dayOfWeek;
        }

        public Integer getDayOfWeekJoda() {
            return dayOfWeekJoda;
        }

        public String getShortCodeOfDay() {
            return shortCodeOfDay;
        }
    }

    public enum AuthorizationError {
        USER_UNAUTHORIZED("User is unauthorized for action");

        private String description;

        AuthorizationError(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum CreditCalculationType {
        GENERAL("general"),
        CUSTOM("custom"),
        METHOD("method"),
        SPECIAL("special");

        private String type;

        CreditCalculationType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public enum CalculatorType {
        NORMAL("Normal"),
        STRUCTURED("Structured");

        private String type;

        CalculatorType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public enum FacilityType {
        LOAN("Loan"),
        LEASE("Lease");

        private String type;

        FacilityType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public enum CustomerEvaluation {
        C("Completed"),
        I("Incompleted");

        private String type;

        CustomerEvaluation(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public enum EvaluationElement {
        S("Section"),
        SS("SubSection"),
        Q("Question");

        private String type;

        EvaluationElement(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public enum Answers {
        E("Entered"),
        D("Deleted"),
        V("Verifird"),
        M("Modified");

        private String type;

        Answers(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public enum EvaluationForm{
        ACTIVE("Active", "A"),
        DELETED("Deleted", "D");

        private String label;
        private String value;

        EvaluationForm(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public static EvaluationForm getEnum(String value) {
            for (EvaluationForm clusterStatus : EvaluationForm.values()) {
                if (clusterStatus.getValue().equalsIgnoreCase(value)) {
                    return clusterStatus;
                }
            }
            return null;
        }

    }

    public enum CAPathType {
        REG("Regular"),
        ALT("Alternative");

        private String name;

        CAPathType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum CACommitteeStatus {
        DRAFT("Draft"),
        SUBMITTED("Submitted"),
        NEW("New"),
        UPDATE("Update"),
        DELETE("Delete");

        private String name;

        CACommitteeStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

        public enum BccStatus {

            APPROVED("Approved", "APPROVED"),
            DECLINED("Declined", "DECLINED");

            private String label;
            private String value;

            BccStatus(String label, String value) {
                this.label = label;
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public String getLabel() {
                return label;
            }

            public static BccStatus getEnum(String value) {
                for (BccStatus clusterStatus : BccStatus.values()) {
                    if (clusterStatus.getValue().equalsIgnoreCase(value)) {
                        return clusterStatus;
                    }
                }
                return null;
            }

        }
    
    public enum FusTraceFlage {
        FAC("FAC"),
        FACSE("FACSE"),
        UPCT("UPCT"),
        ALL("ALL"),
        UPCTALL("UPCTALL");

        private String flag;

        FusTraceFlage(String flag) {
            this.flag = flag;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
    
    public enum CovenantStatusOnDisbursement {
        PRE("Pre"),
        POST("Post");

        private String description;

        CovenantStatusOnDisbursement(String description) {
            this.description = description;
        }

        public static CovenantStatusOnDisbursement resolveStatus(String statusStr) {
            CovenantStatusOnDisbursement matchingStatus = null;
            if (!StringUtils.isEmpty(statusStr)) {
                matchingStatus = CovenantStatusOnDisbursement.valueOf(statusStr.trim());
            }
            return matchingStatus;
        }

        public static Map<String, String> getCovenantStatusOnDisbursementMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(CovenantStatusOnDisbursement.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum SchmType {
        ODA("ODA");

        private String schmType;

        SchmType(String schmType) {
            this.schmType = schmType;
        }

        public String getSchmType() {
            return schmType;
        }

        public void setSchmType(String schmType) {
            this.schmType = schmType;
        }
    }

    public enum AcctType {
        SAVING_ACCOUNT("Savings Account"),
        CURRENT_ACCOUNT("Current Account");

        private String acctType;

        AcctType(String acctType) {
            this.acctType = acctType;
        }

        public String getAcctTType() {
            return acctType;
        }

        public void setAcctTType(String acctType) {
            this.acctType = acctType;
        }
    }

    public enum AnswerType {
        DESCRIPTIVE("DESCRIPTIVE"),
        DESCRIPTIVE_WITH_SUB_QUESTION("DESCRIPTIVE_WITH_SUB_QUESTION"),
        MCQ_WITH_SINGLE("MCQ_WITH_SINGLE"),
        MCQ_WITH_MULTIPLE("MCQ_WITH_MULTIPLE"),
        DESCRIPTIVE_WITH_MCQ_QUESTION("DESCRIPTIVE_WITH_MCQ_QUESTION");

        private String answerType;

        AnswerType(String answerType) {
            this.answerType = answerType;
        }

        public String getAnswerType() {
            return answerType;
        }

        public void setAnswerType(String answerType) {
            this.answerType = answerType;
        }
    }

    public enum InquiryStatus {
        REQ_DRAFTED("REQ_DRAFTED"),
        REQ_SUBMITTED("REQ_SUBMITTED"),
        RES_DRAFTED("RES_DRAFTED"),
        RES_SUBMITTED("RES_SUBMITTED"),
        RES_CONFIRMED("RES_CONFIRMED"),
        REQ_CLOSED("REQ_CLOSED"),;

        private String inquiryStatus;

        InquiryStatus(String inquiryStatus) {
            this.inquiryStatus = inquiryStatus;
        }

        public String getInquiryStatus() {
            return inquiryStatus;
        }

        public void setInquiryStatus(String inquiryStatus) {
            this.inquiryStatus = inquiryStatus;
        }
    }

    public enum InquiryType {
        REQ("REQUEST"),
        RES("RESPONSE");

        private String inquiryType;

        InquiryType(String inquiryType) {
            this.inquiryType = inquiryType;
        }

        public String getInquiryType() {
            return inquiryType;
        }

        public void setInquiryType(String inquiryType) {
            this.inquiryType = inquiryType;
        }
    }

    public enum CovenantApplicableType {
        ABU("ABU"),
        AC("AC");

        private String description;

        CovenantApplicableType(String description) {
            this.description = description;
        }

        public static CovenantApplicableType resolveStatus(String statusStr) {
            CovenantApplicableType matchingStatus = null;
            if (!StringUtils.isEmpty(statusStr)) {
                matchingStatus = CovenantApplicableType.valueOf(statusStr.trim());
            }
            return matchingStatus;
        }

        public static Map<String, String> getCovenantApplicableTypeMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(CovenantApplicableType.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum DigitalApplicationStatus {
        NEW("NEW"), SUBMIT("SUBMIT"), DRAFT("DRAFT"), DELETE("DELETE"), RETURN("RETURN"), APPROVE("APPROVE"), PRINT("PRINT"), RECOMMEND_RETURN("RECOMMEND_RETURN");
        private String documentStatus;

        DigitalApplicationStatus(String documentStatus) {
            this.documentStatus = documentStatus;
        }

        public String getDigitalApplicationStatus() {
            return documentStatus;
        }

        public void setDigitalApplicationStatus(String documentStatus) {
            this.documentStatus = documentStatus;
        }
    }

    public enum SecurityDocumentStatus {
        NEW("NEW"), SUBMIT("SUBMIT"), DRAFT("DRAFT"), DELETE("DELETE"), RETURN("RETURN"), APPROVE("APPROVE"), PRINT("PRINT"), RECOMMEND_RETURN("RECOMMEND_RETURN");

        private String documentStatus;

        SecurityDocumentStatus(String documentStatus) {
            this.documentStatus = documentStatus;
        }

        public String getSecurityDocumentStatus() {
            return documentStatus;
        }

        public void setSecurityDocumentStatus(String documentStatus) {
            this.documentStatus = documentStatus;
        }
    }

    public enum ErrorEnums {
        SUCCESS_CODE("Success", "200", true),
        ERROR_CODE("Error", "500", false);
        private String label;
        private String code;

        private Boolean status;

        ErrorEnums(String label, String code, Boolean status) {
            this.label = label;
            this.code = code;
            this.status = status;
        }

        public String getLabel() {
            return label;
        }

        public String getCode() {
            return code;
        }

        public Boolean getStatus() {
            return status;
        }
    }
}

