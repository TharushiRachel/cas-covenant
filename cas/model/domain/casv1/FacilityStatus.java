package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Getter
@Setter
@Table(name = "CASV1_FACILITY_STATUS_TABLE")
public class FacilityStatus {

    @Column(name = "REF_NUM")
    private String refNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACILITY_DATE")
    private Date facilityDate;

    @Column(name = "PRE_USER")
    private String preUser;

    @Column(name = "CURRENT_USER")
    private String currentUser;

    @Column(name = "CURRENT_USER_STATUS")
    private Integer currentUserStatus;

    @Column(name = "DIRECT_TOTAL")
    private Double directTotal;

    @Column(name = "INDIRECT_TOTAL")
    private Double indirectTotal;

    @Column(name = "TOTAL_EXPOSURE")
    private Double totalExposure;

    @Column(name = "PRE_DIRECT_TOTAL")
    private Double preDirectTotal;

    @Column(name = "PRE_INDIRECT_TOTAL")
    private Double preIndirectTotal;

    @Column(name = "PRE_TOTAL_EXPOSURE")
    private Double preTotalExposure;

    @Column(name = "PERMITTED_USER")
    private String permittedUser;

    @Column(name = "APPROVED_USER_NAME")
    private String approvedUserName;

    @Column(name = "GRP_PRE_DIRECT_TOTAL")
    private Double grpPreDirectTotal;

    @Column(name = "GRP_PRE_INDIRECT_TOTAL")
    private Double grpPreIndirectTotal;

    @Column(name = "GRP_DIRECT_TOTAL")
    private Double grpDirectTotal;

    @Column(name = "GRP_INDIRECT_TOTAL")
    private Double grpIndirectTotal;

    @Column(name = "GRP_PRE_TOTAL_EXPOSURE")
    private Double grpPreTotalExposure;

    @Column(name = "GRP_TOTAL_EXPOSURE")
    private Double grpTotalExposure;

    @Column(name = "UPC_FORMAT_NUM")
    private String upcFormatNum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PICKED_DATE")
    private Date pickedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APP_REC_DATE")
    private Date appRecDate;

    @Column(name = "LOS_REF_NO")
    private String losRefNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_DISBURSEMENT")
    private Date dateOfDisbursement;
}
