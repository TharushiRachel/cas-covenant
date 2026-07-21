package com.itechro.cas.exception.impl;

public abstract class ICasErrorCode {
    public static final String APPS_INVALID_ARGUMENTS = "apps.invalid.arguments";
    public static final String APPS_EXCEPTION_USER_ALREADY_EXISTS = "apps.exception.user.already.exist";
    public static final String APPS_EXCEPTION_EMAIL_ALREADY_EXISTS = "apps.exception.email.already.exist";
    public static final String APPS_EXCEPTION_ROLE_ALREADY_EXISTS = "apps.exception.role.already.exist";
    public static final String APPS_EXCEPTION_MURAPADAYA_MISMATCH = "apps.exception.murapadaya.mismatch";
    public static final String APPS_EXCEPTION_MURAPADAYA_CHANGE_FAILED = "apps.exception.murapadaya.change.failed";
    public static final String APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE = "apps.exception.invalid.approve.status.change";
    public static final String APPS_EXCEPTION_INVALID_APPROVE_USER_CANNOT_APPROVE_OWN_CHANGES = "apps.exception.invalid.approve.user.cannot.approve.own.changes";

    public static final String APPS_EXCEPTION_ROLE_PENDING_APPROVE_RECORD_EXIST = "apps.exception.role.pending.approve.record.exist";

    public static final String APPS_EXCEPTION_SUPPORT_DOC_PENDING_APPROVE_RECORD_EXIST = "apps.exception.support.doc.pending.approve.record.exist";
    public static final String APPS_EXCEPTION_SUPPORT_DOC_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.support.doc.modification.not.allowed.for.inactive.rec";

    public static final String APPS_EXCEPTION_FACILITY_TYPE_PENDING_APPROVE_RECORD_EXIST = "apps.exception.facility.type.pending.approve.record.exist";
    public static final String APPS_EXCEPTION_FACILITY_TYPE_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.facility.type.modification.not.allowed.for.inactive.rec";
    public static final String APPS_EXCEPTION_FACILITY_TEMPLATE_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.facility.template.modification.not.allowed.for.inactive.rec";
    public static final String APPS_EXCEPTION_FACILITY_TEMPLATE_PENDING_APPROVE_RECORD_EXIST = "apps.exception.facility.template.pending.approve.record.exist";

    public static final String APPS_EXCEPTION_USER_DA_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.user.da.modification.not.allowed.for.inactive.rec";
    public static final String APPS_EXCEPTION_USER_DA_PENDING_APPROVE_RECORD_EXIST = "apps.exception.user.da.pending.approve.record.exist";

    public static final String APPS_EXCEPTION_WORK_FLOW_TEMPLATE_PENDING_APPROVE_RECORD_EXIST = "apps.exception.work.flow.template.pending.approve.record.exist";
    public static final String APPS_EXCEPTION_WORK_FLOW_TEMPLATE_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.work.flow.template.modification.not.allowed.for.inactive.rec";

    public static final String APPS_EXCEPTION_UPM_GROUP_PENDING_APPROVE_RECORD_EXIST = "apps.exception.upm.group.pending.approve.record.exist";
    public static final String APPS_EXCEPTION_UPM_GROUP_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.upm.group.modification.not.allowed.for.inactive.rec";

    public static final String APPS_EXCEPTION_UPC_SECTION_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.upc.section.modification.not.allowed.for.inactive.rec";
    public static final String APPS_EXCEPTION_UPC_SECTION_PENDING_APPROVE_RECORD_EXIST = "apps.exception.upc.section.pending.approve.record.exist";
    public static final String APPS_EXCEPTION_UPC_TEMPLATE_PENDING_APPROVE_RECORD_EXIST = "apps.exception.upc.template.pending.approve.record.exist";
    public static final String APPS_EXCEPTION_UPC_TEMPLATE_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.upc.template.modification.not.allowed.for.inactive.rec";

    public static final String APPS_EXCEPTION_LEAD_LEAD_DOCUMENT_NULL_SUPPORT_DOC = "apps.exception.lead.doc.document.cannot.be.null";
    public static final String APPS_EXCEPTION_INVALID_LEAD_STATUS_CHANGE = "apps.exception.invalid.lead.status.change";

    public static final String APPS_EXCEPTION_FP_DIRECTOR_DETAILS_CANNOT_APPLIED_TO_NON_COOPERATE = "apps.exception.fp.cannot.applied.to.non.cooperate.facility";
    public static final String APPS_EXCEPTION_FP_COMPANY_ROA_CANNOT_APPLIED_TO_NON_COOPERATE = "apps.exception.fp.company.roa.cannot.applied.to.non.cooperate.facility";
    public static final String APPS_EXCEPTION_FP_FP_DOCUMENT_NULL_SUPPORT_DOC = "apps.exception.fp.doc.document.cannot.be.null";
    public static final String APPS_EXCEPTION_FP_FP_CUSTOMER_DOCUMENT_NULL_SUPPORT_DOC = "apps.exception.fp.customer.doc.document.cannot.be.null";
    public static final String APPS_EXCEPTION_FP_FACILITY_REQUESTED_AMOUNT_NOT_ALLOWED_FOR_THIS_FACILITY = "apps.exception.fp.requested.amount.not.allowed.for.this.facility";
    public static final String APPS_EXCEPTION_FP_FACILITY_PARENT_FACILITY_AMOUNT_LESS_THAN_REQUESTED_AMOUNT = "apps.exception.fp.parent.facility.amount.less.than.requested.amount";

    public static final String APPS_EXCEPTION_FACILITY_CANNOT_INITIAL_FACILITY_WITHOUT_TEMPLATE = "apps.exception.facility.caanot.initiate.without.facility.template";
    public static final String APPS_EXCEPTION_FACILITY_DOCUMENT_NULL_FACILITY_DOC = "apps.exception.facility.doc.document.cannot.be.null";
    public static final String APPS_EXCEPTION_FACILITY_DOCUMENT_NOT_REQUIRED_FACILITY_DOC = "apps.exception.facility.doc.cannot.upload.not.required.facility.doc";

    public static final String APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE = "apps.exception.cannot.apply.not.approved.template";

    public static final String APPS_EXCEPTION_SYSTEM_EMAIL_TO_ADDRESS_NOT_FOUND = "apps.exception.system.email.to.address.not.found";
    public static final String APPS_EXCEPTION_SYSTEM_EMAIL_FAILED = "apps.exception.system.email.failed";

    public static final String APPS_EXCEPTION_CUSTOMER360_CUSTOMER_CANNOT_FIND = "apps.exception.customer360.no.customer.found";
    public static final String APPS_EXCEPTION_FINACLE_CUSTOMER_CANNOT_FIND = "apps.exception.finacle.no.customer.found";
    public static final String APPS_EXCEPTION_CUSTOMER360_INVALID_CUSTOMER_RESPONSE = "apps.exception.customer360.invalid.customer.response";

    public static final String APPS_EXCEPTION_FP_NO_AUTHORITY_TO_STATUS_CHANGE = "apps.exception.fp.no.authority.to.status.change";
    public static final String APPS_EXCEPTION_FP_NO_AUTHORITY_TO_APPROVE = "apps.exception.fp.no.authority.to.approve";

    public static final String APPS_EXCEPTION_DUPLICATE_SUPPORTING_DOC_NAME_FOUND = "apps.exception.duplicate.supporting.doc.name.found";
    public static final String APPS_EXCEPTION_DUPLICATE_CREDIT_FACILITY_TYPE_NAME_FOUND = "apps.exception.duplicate.credit.facility.type.name.found";
    public static final String APPS_EXCEPTION_DUPLICATE_CREDIT_FACILITY_TEMPLATE_NAME_FOUND = "apps.exception.duplicate.credit.facility.template.name.found";
    public static final String APPS_EXCEPTION_DUPLICATE_UPM_GROUP_CODE_FOUND = "apps.exception.duplicate.upm.group.code.found";
    public static final String APPS_EXCEPTION_DUPLICATE_WORK_FLOW_TEMPLATE_NAME_FOUND = "apps.exception.duplicate.work.flow.template.name.found";
    public static final String APPS_EXCEPTION_DUPLICATE_UPC_SECTION_NAME_FOUND = "apps.exception.duplicate.upc.section.name.found";
    public static final String APPS_EXCEPTION_DUPLICATE_UPC_TEMPLATE_NAME_FOUND = "apps.exception.duplicate.upc.template.name.found";
    public static final String APPS_EXCEPTION_DUPLICATE_CFT_RATE_NAME_FOUND = "apps.exception.duplicate.cft.interest.rate.name.found";
    public static final String APPS_EXCEPTION_DUPLICATE_USER_DA_NAME_FOUND = "apps.exception.duplicate.user.da.name.found";

    public static final String APPS_EXCEPTION_INTEGRATION_INVALID_REQUEST = "apps.exception.integration.invalid.request";

    public static final String APPS_EXCEPTION_USER_EMAIL_ALREADY_EXIST = "apps.exception.user.email.already.exist";
    public static final String APPS_EXCEPTION_USER_USER_NAME_ALREADY_EXIST = "apps.exception.agent.user.name.already.exist";
    public static final String APPS_EXCEPTION_AGENT_FACILITY_DEFAULT_WF_NOT_FOUND = "apps.exception.agent.facility.default.wf.not.found";

    public static final String APPS_EXCEPTION_INVALID_LEAD_REFERENCE = "apps.exception.invalid.lead.reference";
    public static final String APPS_EXCEPTION_ALREADY_LEAD_CREATED_BY_NIC = "apps.exception.already.lead.created.by.nic";
    public static final String APPS_EXCEPTION_INVALID_EXTERNAL_LEAD_BANK_ACCOUNT = "apps.exception.invalid.external.lead.bank.account";

    public static final String APPS_EXCEPTION_FACILITY_PAPER_NOT_FOUND = "apps.exception.facility.paper.not.found";
    public static final String APPS_EXCEPTION_BCC_PAPER_NOT_FOUND = "apps.exception.bcc.paper.not.found";

    public static final String APPS_EXCEPTION_RETAIL_CRIB_REPORT_EMPTY_RESPONSE = "apps.exception.retail.crib.report.empty.response";
    public static final String APPS_EXCEPTION_CRIB_REPORT_INVALID_RESPONSE = "apps.exception.crib.report.invalid.response";

    public static final String APPS_EXCEPTION_APPLICATION_FORM_NOT_FOUND = "apps.exception.application.form.not.found";
    public static final String APPS_EXCEPTION_BASIC_INFORMATION_NOT_FOUND = "apps.exception.basic.information.not.found";
    public static final String APPS_EXCEPTION_AF_DOCUMENT_NULL_SUPPORT_DOC = "apps.exception.af.doc.document.cannot.be.null";

    public static final String APPS_EXCEPTION_DUPLICATE_APPLICATION_FORM_SUB_TOPIC_FOUND = "apps.exception.duplicate.application.form.sub.topic.found";
    public static final String APPS_EXCEPTION_DUPLICATE_APPLICATION_FORM_TOPIC_FOUND = "apps.exception.duplicate.application.form.topic.found";
    public static final String APPS_EXCEPTION_INACTIVE_RECODE_CANNOT_MODIFIED = "apps.exception.modification.not.allowed.for.inactive.rec";
    public static final String APPS_EXCEPTION_PENDING_APPROVE_RECORD_EXIST = "apps.exception.pending.approve.record.exist";
    public static final String APPS_EXCEPTION_APPLICATION_FORM_INVALID_TOPIC_REFERENCE = "apps.exception.application.form.invalid.topic.reference";

    public static final String APPS_EXCEPTION_FILE_STORAGE_FAILED_TO_READ = "apps.exception.storage.failed.to.read";

    public static final String APPS_EXCEPTION_INVALID_APPLICATION_FORM_STATUS_CHANGE = "apps.exception.invalid.application.form.status.change";
    public static final String APPS_EXCEPTION_ALREADY_MAPPED_UPC_SECTION = "apps.exception.already.mapped.upc.section";
    public static final String APPS_EXCEPTION_DUPLICATED_TOPIC_CODES = "apps.exception.duplicated.topic.codes";

    public static final String APPS_EXCEPTION_CANNOT_INVALID_FACILITY_PAPER_UPDATE_REQUEST = "apps.exception.invalid.facility.paper.update.request";
    public static final String APPS_EXCEPTION_CANNOT_INVALID_APPLICATION_FORM_UPDATE_REQUEST = "apps.exception.invalid.application.form.update.request";

    public static final String APPS_EXCEPTION_FACILITY_PAPER_TRANSFER_INVALID_UPM_GROUP = "apps.exception.facility.paper.transfer.invalid.upm.group.code";
    public static final String APPS_EXCEPTION_FACILITY_PAPER_TRANSFER_INVALID_DIV_CODE = "apps.exception.facility.paper.transfer.invalid.div.code";
    public static final String APPS_EXCEPTION_FACILITY_PAPER_TRANSFER_INVALID_STATUS = "apps.exception.facility.paper.transfer.invalid.status";

    public static final String APPS_EXCEPTION_APPLICATION_FORM_TRANSFER_INVALID_STATUS = "apps.exception.application.form.transfer.invalid.status";
    public static final String APPS_EXCEPTION_APPLICATION_FORM_TRANSFER_INVALID_UPM_GROUP = "apps.exception.application.form.transfer.invalid.upm.group.code";
    public static final String APPS_EXCEPTION_APPLICATION_FORM_TRANSFER_INVALID_DIV_CODE = "apps.exception.application.form.transfer.invalid.div.code";

    public static final String APPS_EXCEPTION_INVALID_COVERING_APPROVAL_STATUS_CHANGE = "apps.exception.invalid.covering.approval.status.change";

    public static final String APPS_EXCEPTION_DUPLICATE_USER_POOL_NAME_FOUND = "apps.exception.duplicate.user.pool.name.found";
    public static final String APPS_EXCEPTION_USER_POOL_PENDING_APPROVE_RECORD_EXIST = "apps.exception.user.pool.pending.approve.record.exist";
}
