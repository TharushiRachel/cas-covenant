package com.itechro.cas.model.domain.facilitypaper.securityDocument;

import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_SECURITY_DOCUMENT_ELEMENT")
public class SecurityDocumentElement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SECURITY_DOCUMENT_ELEMENT")
    @SequenceGenerator(name = "SEQ_T_SECURITY_DOCUMENT_ELEMENT", sequenceName = "SEQ_T_SECURITY_DOCUMENT_ELEMENT", allocationSize = 1)
    @Column(name = "ELEMENT_ID")
    private Integer elementID;

    @Column(name = "ELEMENT_NAME")
    private String elementName;

    @Column(name = "CREDIT_FACILITY_NAME")
    private String creditFacilityName;

    @Column(name = "DOCUMENT_FILE_NAME")
    private String documentFileName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SAVED_BY")
    private String savedBy;

    @Column(name = "SAVED_DATE")
    private Date savedDate;
}
