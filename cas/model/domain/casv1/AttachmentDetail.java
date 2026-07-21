package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "CASV1_ATTACHMENTS_TABLE")
public class AttachmentDetail {

    @Column(name = "REF_NUM")
    private String refNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACILITY_DATE")
    private Date facilityDate;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "TYPE")
    private Integer type;

    @Lob
    @Column(name = "FILE_CONTENT", columnDefinition = "BLOB")
    private byte[] fileContent;

    @Column(name = "SECTION_ID")
    private Integer sectionID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;

    @Column(name = "PAGE_ORIENTATION")
    private Integer pageOrientation;

    @Column(name = "PAGE_BREAK_AFTER")
    private Integer pageBreakAfter;

    @Column(name = "MODIFIED_USER")
    private String modifiedUser;
}
