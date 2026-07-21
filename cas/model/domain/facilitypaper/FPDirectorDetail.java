package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_FP_DIRECTOR_DETAIL")
public class FPDirectorDetail extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_DIRECTOR_DETAIL")
    @SequenceGenerator(name = "SEQ_T_FP_DIRECTOR_DETAIL", sequenceName = "SEQ_T_FP_DIRECTOR_DETAIL", allocationSize = 1)
    @Column(name = "FP_DIRECTOR_DETAIL_ID")
    private Integer fpDirectorDetailID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "DIRECTOR_NAME")
    private String directorName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "NIC")
    private String nic;

    @Column(name = "SHARE_HOLDING")
    private Double shareHolding;

    @Column(name = "ADDRESS")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "CIVIL_STATUS")
    private DomainConstants.CivilStatus civilStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    //    This is for the purpose of converting AF to FP and FP to BCC
    @Enumerated(EnumType.STRING)
    @Column(name = "CONSTITUTION_TYPE")
    private DomainConstants.ConstitutionType constitutionType;

    //    This is for the purpose of converting AF to FP and FP to BCC
    @Enumerated(EnumType.STRING)
    @Column(name = "CREDIT_CARD")
    private AppsConstants.YesNo creditCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFpDirectorDetailID() {
        return fpDirectorDetailID;
    }

    public void setFpDirectorDetailID(Integer fpDirectorDetailID) {
        this.fpDirectorDetailID = fpDirectorDetailID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DomainConstants.CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(DomainConstants.CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Double getShareHolding() {
        return shareHolding;
    }

    public void setShareHolding(Double shareHolding) {
        this.shareHolding = shareHolding;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.ConstitutionType getConstitutionType() {
        return constitutionType;
    }

    public void setConstitutionType(DomainConstants.ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
    }

    public AppsConstants.YesNo getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(AppsConstants.YesNo creditCard) {
        this.creditCard = creditCard;
    }
}
