package com.itechro.cas.commons.constants;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DomainConstants {

    public enum Title {
        MR("Mr"), MRS("Mrs"), MS("Ms"), DR("Dr");
        private String description;

        Title(String description) {
            this.description = description;
        }

        public static Title resolveTitle(String titleStr) {
            Title result = null;

            if (StringUtils.isNotEmpty(titleStr)) {
                for (Title title : Title.values()) {
                    if (title.toString().equals(titleStr.toUpperCase())) {
                        result = title;
                        break;
                    }
                }
            }

            return result;
        }

        public static Map<String, String> getTitleMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(Title.values()).forEach(title -> {
                result.put(title.toString(), title.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum Gender {
        M("Male"), F("Female");

        private String gender;

        Gender(String gender) {
            this.gender = gender;
        }

        public static Gender resolveGender(String genderStr) {
            Gender result = null;
            if (StringUtils.isNotEmpty(genderStr)) {
                for (Gender gender : Gender.values()) {
                    if (gender.name().equals(genderStr.toUpperCase())) {
                        result = gender;
                        break;
                    }
                }
            }
            return result;
        }

        public static Map<String, String> getGenderMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(Gender.values()).forEach(gender -> {
                result.put(gender.toString(), gender.getGender());
            });
            return result;
        }

        public String getGender() {
            return gender;
        }
    }

    public enum MasterDataApproveStatus {
        DRAFT("Draft", "DRAFT"),
        PENDING("Pending", "PENDING"),
        APPROVED("Approved", "APPROVED"),
        REJECTED("Rejected", "REJECTED"),
        PENDING_RMV("Remove Pending", "PENDING_RMV");

        private String description;
        private String value;

        MasterDataApproveStatus(String description, String value) {
            this.description = description;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        public String getDescription() {
            return description;
        }

        public static MasterDataApproveStatus resolve(String specifyAs) {
            MasterDataApproveStatus result = null;
            if (StringUtils.isNotEmpty(specifyAs)) {
                result = MasterDataApproveStatus.valueOf(specifyAs);
            }
            return result;
        }
    }

    public enum PaperReviewStatus {
        ACTION_REQUIRED("Action Required"),
        SAVED("Saved"),
        APPROVED("Approved"),
        REJECTED("Rejected"),
        REPLIED("Replied");

        private String description;

        PaperReviewStatus(String description) {
            this.description = description;
        }

        public static PaperReviewStatus resolve(String value) {
            PaperReviewStatus result = null;
            if (StringUtils.isNotEmpty(value)) {
                result = PaperReviewStatus.valueOf(value);
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ApplicationFormStatus {
        DRAFT("Draft"),
        IN_PROGRESS("In Progress"),
        DECLINED("Declined"),
        RETURNED("Returned"),
        PAPER_CREATED("Paper created"),
        FORWARDED("Forwarded"),
        FORWARDED_TO_USER_GROUP("Forwarded"),
        RETURNED_TO_USER_GROUP("Returned"),
        FACILITY_PAPER("Facility Paper");

        private String description;

        ApplicationFormStatus(String description) {
            this.description = description;
        }

        public static ApplicationFormStatus resolve(String value) {
            ApplicationFormStatus result = null;
            if (StringUtils.isNotEmpty(value)) {
                result = ApplicationFormStatus.valueOf(value);
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ApplicationFormForwardType {
        DIRECT("Direct"),
        CLUSTER("Cluster");

        private String description;

        ApplicationFormForwardType(String description) {
            this.description = description;
        }

        public static ApplicationFormForwardType resolve(String value) {
            ApplicationFormForwardType result = null;
            if (StringUtils.isNotEmpty(value)) {
                result = ApplicationFormForwardType.valueOf(value);
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ForwardType {
        DIRECT_USER("Direct User"),
        SAME_SOL_USER_GROUP("User Group"),
        OTHER_SOL_USER_GROUP("Other Branch/Department");

        private String description;

        ForwardType(String description) {
            this.description = description;
        }

        public static ForwardType resolve(String value) {
            ForwardType result = null;
            if (StringUtils.isNotEmpty(value)) {
                result = ForwardType.valueOf(value);
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum SecuritySummaryTypeGroupType {
        FIRST_CLASS("First Class"),
        OTHER("Other"),
        DEFAULT("Default");

        private String description;

        SecuritySummaryTypeGroupType(String description) {
            this.description = description;
        }

        public static SecuritySummaryTypeGroupType resolve(String value) {
            SecuritySummaryTypeGroupType result = null;
            if (StringUtils.isNotEmpty(value)) {
                result = SecuritySummaryTypeGroupType.valueOf(value);
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum FacilitySecuritySummaryType {
        INDIVIDUAL("Individual"),
        GROUP("Group");

        private String description;

        FacilitySecuritySummaryType(String description) {
            this.description = description;
        }

        public static FacilitySecuritySummaryType resolve(String value) {
            FacilitySecuritySummaryType result = null;
            if (StringUtils.isNotEmpty(value)) {
                result = FacilitySecuritySummaryType.valueOf(value);
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum PasswordUpdateAction {
        UPDATE, RESET, FORGET
    }

    public enum EmailSendType {
        TEXT, HTML
    }

    public enum EmailPurpose {
        OTHER, INVOICE, REPORT
    }

    public enum PrivilegeModule {
        WEB("Web"), MOBILE("Mobile");

        private String description;

        PrivilegeModule(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum WebAuditMainCategory {

        CUSTOMER("Customer"),
        USER("User"),
        DELIVERY_ZONE("Delivery Zone"),
        DRIVER("Driver"),
        MERCHANT("Merchant"),
        MERCHANT_USER("Merchant User"),
        ROLE("Role"),
        CONFIGURATIONS("Configurations"),
        PRICING_TEMPLATE("Pricing Template"),
        VOUCHER("Voucher"),
        PROMO_CODES("Promo Code"),
        REPORT("Report"),
        DISTANCE_TIME("Distance Time"),
        ORDER("Order"),
        OTHER("Other"),
        LEAD("Lead"),
        MASTER_DATA("Master Data"),
        FACILITY_PAPER("Facility Paper"),
        FACILITY("Facility"),
        DOCUMENT("Document"),
        CUSTOMER_RATINGS("Customer Ratings");

        private String description;

        WebAuditMainCategory(String description) {
            this.description = description;
        }

        public static Map<String, String> getWebAuditMainCategoryMap() {
            Map<String, String> mainCategoryMap = new HashMap<>();
            for (WebAuditMainCategory webAuditMainCategory : WebAuditMainCategory.values()) {
                mainCategoryMap.putIfAbsent(webAuditMainCategory.toString(), webAuditMainCategory.getDescription());
            }
            return mainCategoryMap;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum BCCPaperType {
        BCC("BCC REPORTING"),
        BCCC("BCC COVERING"),
        EEBCC("BCC EXPOSURE ENHANCEMENT"),
        BRPTR("BOARD RELATED PARTY TRANSACTION REVIEW COMMITTEE"),
        BRPGG("BOARD RELATED PARTY TRANSACTION REVIEW COMMITTEE GOOD GOVERNANCE");

        private String description;

        BCCPaperType(String description) {
            this.description = description;
        }

        public static BCCPaperType resolveBCCPaperType(String str) {
            BCCPaperType bccPaperType = null;
            if (!StringUtils.isEmpty(str)) {
                bccPaperType = BCCPaperType.valueOf(str.trim());
            }
            return bccPaperType;
        }

        public static Map<String, String> getBCCPaperTypeMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(BCCPaperType.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ConstitutionType {
        CHAIRMAN("Chairman"),
        MANAGING_DIRECTOR("Managing Director"),
        DIRECTOR("Director"),
        PARTNER("Partner");

        private String description;

        ConstitutionType(String description) {
            this.description = description;
        }

        public static ConstitutionType resolveConstitutionType(String str) {
            ConstitutionType constitutionType = null;
            if (!StringUtils.isEmpty(str)) {
                constitutionType = ConstitutionType.valueOf(str.trim());
            }
            return constitutionType;
        }

        public static Map<String, String> getConstitutionTypeMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(ConstitutionType.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum BCCFacilityType {
        PROPOSED("PROPOSED FACILITY"),
        EXISTING("EXISTING FACILITY");

        private String description;

        BCCFacilityType(String description) {
            this.description = description;
        }

        public static BCCFacilityType resolveBCCFacilityType(String str) {
            BCCFacilityType bccPaperType = null;
            if (!StringUtils.isEmpty(str)) {
                bccPaperType = BCCFacilityType.valueOf(str.trim());
            }
            return bccPaperType;
        }

        public static Map<String, String> getBCCFacilityTypeMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(BCCFacilityType.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum GRORecommendation {
        RECOMMENDED_BY_GRO("Recommended By GCRO"),
        NOT_RECOMMENDED_BY_GRO("Not Recommended By GCRO"),
        CONDITION_OF_IMPOSED("Recommended By GCRO Subject to Fulfillment of Conditions Imposed");

        private String description;

        GRORecommendation(String description) {
            this.description = description;
        }

        public static GRORecommendation resolveGRORecommendation(String str) {
            GRORecommendation groRecommendation = null;
            if (!StringUtils.isEmpty(str)) {
                groRecommendation = GRORecommendation.valueOf(str.trim());
            }
            return groRecommendation;
        }

        public static Map<String, String> getGRORecommendation() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(GRORecommendation.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum WebAuditSubCategory {

        CUSTOMER_ADD("Add Customer", "CUSTOMER"),
        CUSTOMER_EDIT("Edit Customer", "CUSTOMER"),
        CUSTOMER_360_SEARCH("Customer Search", "CUSTOMER"),
        CUSTOMER_ADDRESS_ADD("Add Customer Address", "CUSTOMER"),
        CUSTOMER_ADDRESS_EDIT("Edit Customer Address", "CUSTOMER"),
        CUSTOMER_BANK_DETAIL_ADD("Add Customer Bank Detail", "CUSTOMER"),
        CUSTOMER_BANK_DETAIL_EDIT("Edit Customer Bank Detail", "CUSTOMER"),
        CUSTOMER_IDENTIFICATION_ADD("Add Customer Identification", "CUSTOMER"),
        CUSTOMER_IDENTIFICATION_EDIT("Edit Customer Identification", "CUSTOMER"),
        CUSTOMER_TELEPHONE_ADD("Add Customer Telephone", "CUSTOMER"),
        CUSTOMER_TELEPHONE_EDIT("Edit Customer Telephone", "CUSTOMER"),
        CUSTOMER_JOINING_PARTNER_ADD("Add Customer Joining Partner", "CUSTOMER"),
        CUSTOMER_JOINING_PARTNER_EDIT("Edit Customer Joining Partner ", "CUSTOMER"),

        USER_LOGIN("User Login", "User"),
        USER_ADD("User Add", "User"),
        USER_EDIT("User Edit", "User"),
        SUPPORTING_DOC_ADD("Add Supporting Document ", "SupportingDoc"),
        SUPPORTING_DOC_EDIT("Edit Supporting Document ", "SupportingDoc"),
        SUPPORTING_DOC_APPROVE("Approve Supporting Document ", "SupportingDoc"),
        SUPPORTING_DOC_REJECT("Reject Supporting Document ", "SupportingDoc"),
        CREDIT_FACILITY_TYPE_ADD("Add Credit Facility Group", "CreditFacilityType"),
        CREDIT_FACILITY_TYPE_EDIT("Edit Credit Facility Group", "CreditFacilityType"),
        CREDIT_FACILITY_TYPE_APPROVE("Approve Credit Facility Group", "CreditFacilityType"),
        CREDIT_FACILITY_TYPE_REJECT("Reject Credit Facility Group", "CreditFacilityType"),
        USER_DA_ADD("Add User Delegated Authority", "UserDA"),
        USER_DA_EDIT("Edit User Delegated Authority", "UserDA"),
        USER_DA_APPROVE("Approve User Delegated Authority", "UserDA"),
        USER_DA_REJECT("Reject User Delegated Authority", "UserDA"),
        CREDIT_FACILITY_TEMPLATE_ADD("Add Credit Facility Template", "CreditFacilityTemplate"),
        CREDIT_FACILITY_TEMPLATE_EDIT("Edit Credit Facility Template", "CreditFacilityTemplate"),
        CREDIT_FACILITY_TEMPLATE_APPROVE("Approve Credit Facility Template", "CreditFacilityTemplate"),
        CREDIT_FACILITY_TEMPLATE_REJECT("Reject Credit Facility Template", "CreditFacilityTemplate"),
        UPM_GROUP_ADD("Add Upm Group", "UpmGroup"),
        UPM_GROUP_EDIT("Edit Upm Group", "UpmGroup"),
        UPM_GROUP_APPROVE("Approve Upm Group", "UpmGroup"),
        UPM_GROUP_REJECT("Reject Upm Group", "UpmGroup"),
        ROLE_ADD("Add Role", "Role"),
        ROLE_EDIT("Edit Role", "Role"),
        ROLE_APPROVE("Approve Role", "Role"),
        ROLE_REJECT("Reject Role", "Role"),
        WORK_FLOW_TEMPLATE_ADD("Add Work Flow Template", "WorkFlowTemplate"),
        WORK_FLOW_TEMPLATE_EDIT("Edit Work Flow Template", "WorkFlowTemplate"),
        WORK_FLOW_TEMPLATE_APPROVE("Approve Work Flow Template", "WorkFlowTemplate"),
        WORK_FLOW_TEMPLATE_REJECT("Reject Work Flow Template", "WorkFlowTemplate"),
        UPC_SECTION_ADD("Add Upc section", "UpcSection"),
        UPC_SECTION_EDIT("Edit Upc section", "UpcSection"),
        UPC_SECTION_APPROVE("Approve Upc section", "UpcSection"),
        UPC_SECTION_REJECT("Reject Upc section", "UpcSection"),
        UPC_TEMPLATE_ADD("Add Upc Template", "UpcTemplate"),
        UPC_TEMPLATE_EDIT("Edit Upc Template", "UpcTemplate"),
        UPC_TEMPLATE_APPROVE("Approve Upc Template", "UpcTemplate"),
        UPC_TEMPLATE_REJECT("Reject Upc Template", "UpcTemplate"),
        CFT_INTEREST_RATE_ADD("Add CFT Interest Rate", "InterestRate"),
        CFT_INTEREST_RATE_EDIT("Edit CFT Interest Rate", "InterestRate"),
        CFT_SUPPORTING_DOC_ADD("Add CFT Supporting Doc", "SupportingDoc"),
        CFT_SUPPORTING_DOC_EDIT("Edit CFT Supporting Doc", "SupportingDoc"),
        FACILITY_ADD("Add Facility ", "Facility"),
        FACILITY_EDIT("Edit Facility ", "Facility"),
        REPLICATE_FACILITY_PAPER("Replicate Facility Paper ", "Facility"),
        FACILITY_DOCUMENT_ADD("Add Facility Document", "Facility"),
        FACILITY_DOCUMENT_EDIT("Edit Facility Document", "Facility"),
        FACILITY_PURPOSE_OF_ADVANCE_ADD("Add Facility Purpose of Advance", "Facility"),
        FACILITY_PURPOSE_OF_ADVANCE_EDIT("Edit Facility Purpose of Advance", "Facility"),
        FACILITY_REPAYMENT_ADD("Add Facility Repayment", "Facility"),
        FACILITY_REPAYMENT_EDIT("Edit Facility Repayment", "Facility"),
        FACILITY_INTEREST_RATE_ADD("Add Facility Interest Rate", "Facility"),
        FACILITY_INTEREST_RATE_EDIT("Edit Facility Interest Rate", "Facility"),

        LEAD_ADD("Lead Add", "LEAD"),
        LEAD_EDIT("Lead Edit", "LEAD"),
        LEAD_DOC_ADD("Add Lead Document ", "LEAD"),
        LEAD_DOC_EDIT("Edit Lead Document ", "LEAD"),
        LEAD_STATUS_EDIT("Edit Lead Status ", "LEAD"),
        DOC_STORAGE_ADD("Add Document Storage ", "DOCUMENT"),
        DOC_STORAGE_EDIT("Edit Document Storage ", "DOCUMENT"),
        FACILITY_PAPER_ADD("Add Facility Paper", "FacilityPaper"),
        FACILITY_PAPER_EDIT("Edit Facility Paper", "FacilityPaper"),
        FACILITY_PAPER_DOC_ADD("Add Facility Paper document", "FacilityPaper"),
        FACILITY_PAPER_DOC_EDIT("Edit Facility Paper document ", "FacilityPaper"),
        FACILITY_PAPER_CUSTOMER_DOC_ADD("Add Facility Paper customer document", "FacilityPaper"),
        FACILITY_PAPER_CUSTOMER_DOC_EDIT("Edit Facility Paper customer document", "FacilityPaper"),
        FACILITY_PAPER_CRIB_DETAIL_ADD("Add Facility Paper customer crib detail", "FacilityPaper"),
        FACILITY_PAPER_CRIB_DETAIL_EDIT("Edit Facility Paper customer crib detail", "FacilityPaper"),
        FACILITY_PAPER_DIRECTOR_DETAIL_ADD("Add Facility Paper customer crib detail", "FacilityPaper"),
        FACILITY_PAPER_DIRECTOR_DETAIL_EDIT("Edit Facility Paper director detail", "FacilityPaper"),
        FACILITY_PAPER_COMMENT_ADD("Add Facility Paper comment", "FacilityPaper"),
        FACILITY_PAPER_COMMENT_EDIT("Edit Facility Paper comment", "FacilityPaper"),
        FACILITY_PAPER_OTHER_BANK_FACILITY_ADD("Add Facility Paper other bank facility", "FacilityPaper"),
        FACILITY_PAPER_OTHER_BANK_FACILITY_EDIT("Edit Facility Paper other bank facility", "FacilityPaper"),
        FACILITY_PAPER_CUSTOMER_ADD("Add Facility Paper customer", "FacilityPaper"),
        FACILITY_PAPER_CUSTOMER_EDIT("Edit Facility Paper customer", "FacilityPaper"),
        FACILITY_PAPER_UPC_SECTION_DATA_ADD("Add Facility Paper Upc section data", "FacilityPaper"),
        FACILITY_PAPER_UPC_SECTION_DATA_EDIT("Edit Facility Paper Upc section data", "FacilityPaper"),
        CUSTOMER_RATINGS_ADD("Add Customer Ratings", "CustomerRatings"),
        CUSTOMER_RATINGS_EDIT("Edit Customer Ratings", "CustomerRatings");


        private String description;
        private String categoryName;

        WebAuditSubCategory(String description, String categoryName) {
            this.description = description;
            this.categoryName = categoryName;
        }

        public static Map<String, String> getWebAuditSubCategoryMap() {
            Map<String, String> subCategoryMap = new HashMap<>();
            for (WebAuditSubCategory webAuditSubCategory : WebAuditSubCategory.values()) {
                subCategoryMap.putIfAbsent(webAuditSubCategory.toString(), webAuditSubCategory.getDescription());
            }
            return subCategoryMap;
        }

        public static Map<String, String> getWebAuditSubCategoryCategoryMap() {
            Map<String, String> subCategoryMap = new HashMap<>();
            for (WebAuditSubCategory webAuditSubCategory : WebAuditSubCategory.values()) {
                subCategoryMap.putIfAbsent(webAuditSubCategory.toString(), webAuditSubCategory.getCategoryName());
            }
            return subCategoryMap;
        }

        public String getDescription() {
            return description;
        }

        public String getCategoryName() {
            return categoryName;
        }
    }

    public enum AuditType {

        CUSTOMER("Customer"),
        FACILITY_PAPER("Facility Paper"),
        FACILITY("Facility"),
        LEAD("Lead"),
        SUPPORTING_DOC("Supporting Document"),
        CREDIT_FACILITY_GROUP("Credit Facility Group"),
        USER_DA("User Da"),
        CREDIT_FACILITY_TEMPLATE("Credit Facility Template"),
        UPM_GROUP("Upm Group"),
        ROLE("Role"),
        WORK_FLOW_TEMPLATE("Work Flow Template"),
        UPC_SECTION("Upc Section"),
        UPC_TEMPLATE("Upc Template"),
        CFT_INTEREST_RATE("Cft Interest Rate"),
        CFT_SUPPORTING_DOC("Cft Supporting Document"),
        DOCUMENT("Document"),
        USER("User"),
        CUSTOMER_RATINGS("Customer Ratings");

        private String description;

        AuditType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum VoucherStatus {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        CONSUMED("Consumed"),
        EXPIRED("Expired");

        private String description;

        VoucherStatus(String description) {
            this.description = description;
        }

        public static VoucherStatus resolveVoucherStatus(String status) {
            VoucherStatus result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (VoucherStatus voucherStatus : VoucherStatus.values()) {
                    if (voucherStatus.name().equals(status.toUpperCase())) {
                        result = voucherStatus;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum PromoCodeStatus {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        EXPIRED("Expired");

        private String description;

        PromoCodeStatus(String description) {
            this.description = description;
        }

        public static PromoCodeStatus resolveStatus(String status) {
            PromoCodeStatus result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (PromoCodeStatus promocodeStatus : PromoCodeStatus.values()) {
                    if (promocodeStatus.name().equals(status.toUpperCase())) {
                        result = promocodeStatus;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum CivilStatus {
        MARRIED("Married"),
        SINGLE("Single"),
        UNKNOWN("Unknown");

        private String description;

        CivilStatus(String description) {
            this.description = description;
        }

        public static CivilStatus resolveStatus(String status) {
            CivilStatus result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (CivilStatus civilStatus : CivilStatus.values()) {
                    if (civilStatus.name().equals(status.toUpperCase())) {
                        result = civilStatus;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }


    public enum DateRange {

        THIS_MONTH("This Month"),
        LAST_30_DAYS("Last 30 Days"),
        CUSTOM("Custom");

        private String description;

        DateRange(String description) {
            this.description = description;
        }

        public static DateRange resolveStatus(String status) {
            DateRange result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (DateRange dateRange : DateRange.values()) {
                    if (dateRange.name().equals(status.toUpperCase())) {
                        result = dateRange;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum LeadCreationType {
        PERSONAL("Personal"),

        BUSINESS("Business"),
        CORPORATE("Corporate");

        private String description;

        LeadCreationType(String description) {
            this.description = description;
        }

        public static LeadCreationType resolveStatus(String status) {
            LeadCreationType result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (LeadCreationType leadCreationType : LeadCreationType.values()) {
                    if (leadCreationType.name().equals(status.toUpperCase())) {
                        result = leadCreationType;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum LeadStatus {
        PENDING("Pending"),
        SUBMITTED("Submitted"),
        APPROVED("Approved"),
        ACCEPTED("Accepted"),
        RETURNED("Returned"),
        CLOSED("Closed"),
        DECLINED("Declined"),
        PAPER_CREATED("Paper created"),
        APPLICATION_CREATED("Application created");

        private String description;

        LeadStatus(String description) {
            this.description = description;
        }

        public static LeadStatus resolveStatus(String status) {
            LeadStatus result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (LeadStatus leadStatus : LeadStatus.values()) {
                    if (leadStatus.name().equals(status.toUpperCase())) {
                        result = leadStatus;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum LeadType {
        INTERNAL("Internal"),
        EXTERNAL("External");

        private String description;

        LeadType(String description) {
            this.description = description;
        }

        public static LeadType resolveStatus(String status) {
            LeadType result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (LeadType leadType : LeadType.values()) {
                    if (leadType.name().equals(status.toUpperCase())) {
                        result = leadType;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum PromotionStatus {
        ACTIVE("Active"), EXPIRED("Expired"), CANCELLED("Cancelled");

        private String description;

        PromotionStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum FacilityPaperRoutingStatus {
        REOPEN("Re Open"),
        REASSIGN("Re Assign"),
        RECOMMENDED("Recommended"),
        CLOSED("Closed"),
        BACK("Back"),
        NEXT("Next");

        private String description;

        FacilityPaperRoutingStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ApplicationFormRoutingStatus {
        REOPEN("Re Open"),
        REASSIGN("Re Assign"),
        RECOMMENDED("Recommended"),
        CLOSED("Closed"),
        BACK("Back"),
        NEXT("Next");

        private String description;

        ApplicationFormRoutingStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


    public enum WorkflowRoutingLevel {
        LEVEL_0("Start", 0),
        LEVEL_1("Level 1", 1),
        LEVEL_2("Level 2", 2),
        LEVEL_3("Level 3", 3),
        LEVEL_4("Level 4", 4),
        LEVEL_5("Level 5", 5),
        LEVEL_6("Level 6", 6),
        LEVEL_7("Level 7", 7),
        LEVEL_8("Level 8", 8),
        LEVEL_9("Level 9", 9);

        private String levelName;

        private Integer level;

        WorkflowRoutingLevel(String levelName, Integer level) {
            this.levelName = levelName;
            this.level = level;
        }

        public static WorkflowRoutingLevel resolveRoutingLevel(int level) {
            WorkflowRoutingLevel routingLevel = null;

            if (level >= 0 && level < 10) {
                routingLevel = WorkflowRoutingLevel.valueOf("LEVEL_" + level);
            } else {
                throw new UnsupportedOperationException();
            }

            return routingLevel;
        }

        public String getLevelName() {
            return levelName;
        }

        public Integer getLevel() {
            return level;
        }
    }

    public enum FacilityPaperStatus {
        DRAFT("Draft"),
        IN_PROGRESS("In Progress"),
        PENDING("Pending"),
        REJECTED("Rejected"),
        APPROVED("Approved"),
        REMOVED("Removed"),
        CANCEL("Returned"),
        RECOMMENDED("Recommended"),

        COMMENTED("Commented"),
        SUBMITTED("Submitted"),
        DECLINED("Declined");

        private String description;

        FacilityPaperStatus(String description) {
            this.description = description;
        }

        public static FacilityPaperStatus resolveStatus(String status) {
            FacilityPaperStatus result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (FacilityPaperStatus facilityPaperStatus : FacilityPaperStatus.values()) {
                    if (facilityPaperStatus.name().equals(status.toUpperCase())) {
                        result = facilityPaperStatus;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    //    This is for build the email Subject with proper paper status
    public enum FacilityPaperStatusForEmailSubject {
        IN_PROGRESS("Forwarded"),
        REJECTED("Declined"),
        APPROVED("Approved"),
        REMOVED("Removed"),
        CANCEL("Returned");
        private String description;

        FacilityPaperStatusForEmailSubject(String description) {
            this.description = description;
        }

        public static FacilityPaperStatusForEmailSubject resolveStatus(String status) {
            FacilityPaperStatusForEmailSubject result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (FacilityPaperStatusForEmailSubject facilityPaperStatusForEmailSubject : FacilityPaperStatusForEmailSubject.values()) {
                    if (facilityPaperStatusForEmailSubject.name().equals(status.toUpperCase())) {
                        result = facilityPaperStatusForEmailSubject;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }


    public enum CustomerIdentificationType {
        OLD_NIC("OLD_NIC", "Old National Identity Card"),
        NEW_NIC("NEW_NIC", "New National Identity Card"),
        NIC("NIC", "National Identity Card"),
        BRC("BRC", "Business Registration Certificate"),
        PP("PP", "Pass Port"),
        DL("DL", "Driving License"),
        OTHER("OTHER", "Other");

        private String code;
        private String description;

        CustomerIdentificationType(String code, String description) {
            this.description = code;
            this.description = description;
        }

        public static CustomerIdentificationType resolveStatus(String status) {
            CustomerIdentificationType result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (CustomerIdentificationType type : CustomerIdentificationType.values()) {
                    if (type.name().equals(status.toUpperCase())) {
                        result = type;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }

        public String getCode() {
            return code;
        }
    }

    public enum CribStatusType {
        POSITIVE("Positive"),
        NEGATIVE("Negative"),
        NOT_ENTERED("Not Entered"),
        PENDING("Pending"),
        NO_IRREGULAR_ADVANCES("No Irregular Advances"),
        REPORTED_AS_IRREGULAR("Reported as Irregular (Refer Comments)"),
        SERVICE_NOT_AVAILABLE("Crib Service Unavailable"),
        SKIP_CRIB_REPORT("Skip Crib Report");

        private String description;

        CribStatusType(String description) {
            this.description = description;
        }

        public static CribStatusType resolve(String status) {
            CribStatusType result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (CribStatusType cribStatusType : CribStatusType.values()) {
                    if (cribStatusType.name().equals(status.toUpperCase())) {
                        result = cribStatusType;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum RepaymentType {
        EMI("EMI"),
        RB("Reducing Balance"),
        BP("Bullet Payment");

        private String description;

        RepaymentType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public enum PaymentFrequency {
        MONTHLY("Monthly", 12),
        SEMI_MONTHLY("Semi Monthly", 24),
        BI_WEEKLY("Bi-Weekly", 26),
        WEEKLY("Weekly", 52),
        DAILY("Daily", 365),
        BI_MONTHLY("Bi-Monthly", 6),
        QUARTERLY("Quarterly", 4),
        SEMI_ANNUAL("Semi Annual", 2),
        ANNUAL("Annual", 1);

        private String description;

        private Integer intValue;

        PaymentFrequency(String description, Integer intValue) {
            this.description = description;
            this.intValue = intValue;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIntValue() {
            return intValue;
        }

        public void setIntValue(Integer intValue) {
            this.intValue = intValue;
        }
    }

    public enum UserType {
        ADMIN, AGENT;
    }

    public enum BasicInformationType {
        PERSONAL("Personal"),
        BUSINESS("Business"),
        CORPORATE("Corporate");

        private String description;

        BasicInformationType(String description) {
            this.description = description;
        }

        public static BasicInformationType resolve(String status) {
            BasicInformationType result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (BasicInformationType basicInformationType : BasicInformationType.values()) {
                    if (basicInformationType.name().equals(status.toUpperCase())) {
                        result = basicInformationType;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ApplicationFormTopicPage {
        REPAYMENT("Repayment"),
        ASSETS("Assets"),
        EXECUTIVE_SUMMARY("Executive Summary");

        private String description;

        ApplicationFormTopicPage(String description) {
            this.description = description;
        }

        public static ApplicationFormTopicPage resolve(String status) {
            ApplicationFormTopicPage result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (ApplicationFormTopicPage applicationFormTopicPage : ApplicationFormTopicPage.values()) {
                    if (applicationFormTopicPage.name().equals(status.toUpperCase())) {
                        result = applicationFormTopicPage;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum InputFieldValueType {
        TEXT("Text"),
        NUMBER("Number"),
        CURRENCY("CURRENCY"),
        DATE("Date"),
        PERCENTAGE("Percentage");

        private String description;

        InputFieldValueType(String description) {
            this.description = description;
        }

        public static InputFieldValueType resolve(String status) {
            InputFieldValueType result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (InputFieldValueType inputFieldValueType : InputFieldValueType.values()) {
                    if (inputFieldValueType.name().equals(status.toUpperCase())) {
                        result = inputFieldValueType;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum InterestRatingSubCategory {
        EFFECTIVE("EFFECTIVE"),
        REDUCING("REDUCING"),
        FLAT("FLAT"),
        OTHER("OTHER");

        private String description;

        InterestRatingSubCategory(String description) {
            this.description = description;
        }

        public static InterestRatingSubCategory resolve(String status) {
            InterestRatingSubCategory result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (InterestRatingSubCategory interestRatingSubCategory : InterestRatingSubCategory.values()) {
                    if (interestRatingSubCategory.name().equals(status.toUpperCase())) {
                        result = interestRatingSubCategory;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }


    public enum ApplicationFormStatusForEmailSubject {
        IN_PROGRESS("Forwarded"),
        DRAFT("Draft"),
        DECLINED("Declined"),
        RETURNED("Returned"),
        RETURNED_TO_USER_GROUP("Returned"),
        PAPER_CREATED("Paper created"),
        FORWARDED("Forwarded"),
        FORWARDED_TO_USER_GROUP("Forwarded"),
        FACILITY_PAPER("Facility Paper");

        private String description;

        ApplicationFormStatusForEmailSubject(String description) {
            this.description = description;
        }

        public static ApplicationFormStatusForEmailSubject resolveStatus(String status) {
            ApplicationFormStatusForEmailSubject result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (ApplicationFormStatusForEmailSubject applicationFormStatusForEmailSubject : ApplicationFormStatusForEmailSubject.values()) {
                    if (applicationFormStatusForEmailSubject.name().equals(status.toUpperCase())) {
                        result = applicationFormStatusForEmailSubject;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum LeadStatusForEmailSubject {
        SUBMITTED("Submitted"),
        RETURNED("Returned"),
        DECLINED("Declined"),
        APPLICATION_CREATED("Application Form created"),
        APPROVED("Approved"),
        PAPER_CREATED("Application Form created"),
        ACCEPTED("Accepted");

        private String description;

        LeadStatusForEmailSubject(String description) {
            this.description = description;
        }

        public static LeadStatusForEmailSubject resolveStatus(String status) {
            LeadStatusForEmailSubject result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (LeadStatusForEmailSubject ladStatusForEmailSubject : LeadStatusForEmailSubject.values()) {
                    if (ladStatusForEmailSubject.name().equals(status.toUpperCase())) {
                        result = ladStatusForEmailSubject;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum CRPaperType {
        ROF("Risk Opinion Format"),
        DF("Descriptive Format"),
        RF1("Review Format 1"),
        RF2("Review Format 2");

        private String description;

        CRPaperType(String description) {
            this.description = description;
        }

        public static CRPaperType resolveBCCPaperType(String str) {
            CRPaperType crPaperType = null;
            if (!StringUtils.isEmpty(str)) {
                crPaperType = CRPaperType.valueOf(str.trim());
            }
            return crPaperType;
        }

        public static Map<String, String> getCRPaperTypeMap() {
            Map<String, String> result = new HashMap<>();
            Arrays.asList(CRPaperType.values()).forEach(status -> {
                result.put(status.toString(), status.getDescription());
            });
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum DAType {
        ACT("ACT"),
        INA("INA"),
        P("P"),
        A("A");

        private String type;

        DAType(String act) {
            this.type = act;
        }

        public static DAType resolve(String type) {
            DAType result = null;
            if (StringUtils.isNotEmpty(type)) {
                for (DAType daType : DAType.values()) {
                    if (daType.name().equals(type.toUpperCase())) {
                        result = daType;
                        break;
                    }
                }
            }
            return result;
        }
    }



    public enum CoveringApprovalStatus {
        DRAFT("Draft"),
        IN_PROGRESS("In Progress"),
        PENDING("Pending"),
        REJECTED("Rejected"),
        APPROVED("Approved"),
        REMOVED("Removed"),
        CANCEL("Returned"),
        FORWARDED("Forwarded"),
        DECLINED("Declined"),
        RETURNED("Returned"),
        DELETE("Delete"),

        NOT_APPROVED("Not Approved");

        private String description;

        CoveringApprovalStatus(String description) {
            this.description = description;
        }

        public static CoveringApprovalStatus resolveStatus(String status) {
            CoveringApprovalStatus result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (CoveringApprovalStatus coveringApprovalStatus : CoveringApprovalStatus.values()) {
                    if (coveringApprovalStatus.name().equals(status.toUpperCase())) {
                        result = coveringApprovalStatus;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum CoveringApprovalStatusForEmailSubject {
        IN_PROGRESS("Forwarded"),
        DRAFT("Draft"),
        DECLINED("Declined"),
        RETURNED("Returned"),
        RETURNED_TO_USER_GROUP("Returned"),
        PAPER_CREATED("Paper created"),
        FORWARDED("Forwarded"),
        FORWARDED_TO_USER_GROUP("Forwarded"),
        COVERING_APPROVAL("Covering Approval"),
        APPROVED("Approved"),
        REJECTED("Rejected");

        private String description;

        CoveringApprovalStatusForEmailSubject(String description) {
            this.description = description;
        }

        public static CoveringApprovalStatusForEmailSubject resolveStatus(String status) {
            CoveringApprovalStatusForEmailSubject result = null;
            if (StringUtils.isNotEmpty(status)) {
                for (CoveringApprovalStatusForEmailSubject coveringApprovalStatusForEmailSubject : CoveringApprovalStatusForEmailSubject.values()) {
                    if (coveringApprovalStatusForEmailSubject.name().equals(status.toUpperCase())) {
                        result = coveringApprovalStatusForEmailSubject;
                        break;
                    }
                }
            }
            return result;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum BccDocsApproveStatus {
        DRAFT("Draft", "DRAFT"),
        PENDING("Pending", "PENDING"),
        APPROVED("Approved", "APPROVED"),
        REJECTED("Rejected", "REJECTED");

        private String description;
        private String value;

        BccDocsApproveStatus(String description, String value) {
            this.description = description;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        public String getDescription() {
            return description;
        }

        public static MasterDataApproveStatus resolve(String specifyAs) {
            MasterDataApproveStatus result = null;
            if (StringUtils.isNotEmpty(specifyAs)) {
                result = MasterDataApproveStatus.valueOf(specifyAs);
            }
            return result;
        }
    }

    public static List<String> excludeProductGroups() {
        return Arrays.asList("CBSL", "CBSLQ", "ETBLK", "FDMAT", "FDMON", "FEEA", "IP", "MYBNK", "SANHI", "STAFF", "STFCR", "XSET", "XSTAF");
    }

    public enum MasterDataActionStatus {
        NEW("New", "NEW"),
        UPDATE("Update", "UPDATE"),
        DELETE("Delete", "DELETE"),
        DRAFT("Draft", "DRAFT"),
        SUBMITTED("Submitted", "SUBMITTED");

        private String description;
        private String value;

        MasterDataActionStatus(String description, String value) {
            this.description = description;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        public String getDescription() {
            return description;
        }

        public static MasterDataApproveStatus resolve(String specifyAs) {
            MasterDataApproveStatus result = null;
            if (StringUtils.isNotEmpty(specifyAs)) {
                result = MasterDataApproveStatus.valueOf(specifyAs);
            }
            return result;
        }
    }

    public enum ACAEActionStatus {
        DRAFT("Draft", "14"),
        FORWARD_TO_ME("Update", "UPDATE"),
        RETURN_TO_ME("Delete", "DELETE"),
        RESUBMIT_TO_ME_50("Draft", "DRAFT"),
        SUBMITTED("Submitted", "SUBMITTED");

        private String description;
        private String value;

        ACAEActionStatus(String description, String value) {
            this.description = description;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        public String getDescription() {
            return description;
        }

        public static MasterDataApproveStatus resolve(String specifyAs) {
            MasterDataApproveStatus result = null;
            if (StringUtils.isNotEmpty(specifyAs)) {
                result = MasterDataApproveStatus.valueOf(specifyAs);
            }
            return result;
        }
    }

    public enum PartyType {
        BORROWER,
        NON_BORROWER,
        RELATED
    }
}
