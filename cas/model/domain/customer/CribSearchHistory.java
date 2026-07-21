package com.itechro.cas.model.domain.customer;

import com.itechro.cas.model.domain.storage.DocStorage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_CRIB_SEARCH_HISTORY")
public class CribSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CRIB_SEARCH_HISTORY")
    @SequenceGenerator(name = "SEQ_T_CRIB_SEARCH_HISTORY", sequenceName = "SEQ_T_CRIB_SEARCH_HISTORY", allocationSize = 1)
    @Column(name = "CRIB_SEARCH_HISTORY_ID")
    private Integer cribSearchHistoryId;

    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;

    @Column(name = "IDENTIFICATION_NO")
    private String identificationNo;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "FACILITY_AMOUNT")
    private BigDecimal facilityAmount;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "INQUIRY_REASON")
    private String inquiryReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @Column(name = "UPLOADED_SOL_ID")
    private String uploadedDivCode;

    @Column(name = "UPLOADED_USER_DISPLAY_NAME")
    private String uploadedUserDisplayName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
}