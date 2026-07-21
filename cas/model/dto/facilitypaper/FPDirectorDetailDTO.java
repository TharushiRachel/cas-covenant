package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPDirectorDetail;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPDirectorDetailDTO implements Serializable {

    private Integer fpDirectorDetailID;

    private Integer facilityPaperID;

    private String directorName;

    private String fullName;

    private String address;

    private DomainConstants.CivilStatus civilStatus;

    private String dateOfBirthStr;

    private String nic;

    private Double shareHolding;

    //    This is for the purpose of converting AF to FP and FP to BCC
    private DomainConstants.ConstitutionType constitutionType;

    //    This is for the purpose of converting AF to FP and FP to BCC
    private AppsConstants.YesNo creditCard;

    private AppsConstants.Status status;

    public FPDirectorDetailDTO() {
    }

    public FPDirectorDetailDTO(FPDirectorDetail fpDirectorDetail) {
        this.fpDirectorDetailID = fpDirectorDetail.getFpDirectorDetailID();
        this.facilityPaperID = fpDirectorDetail.getFacilityPaper().getFacilityPaperID();
        this.directorName = fpDirectorDetail.getDirectorName();
        this.fullName = fpDirectorDetail.getFullName();
        this.address = fpDirectorDetail.getAddress();
        this.civilStatus = fpDirectorDetail.getCivilStatus();
        if (fpDirectorDetail.getDateOfBirth() != null) {
            this.dateOfBirthStr = CalendarUtil.getDefaultFormattedDateOnly(fpDirectorDetail.getDateOfBirth());
        }
        this.status = fpDirectorDetail.getStatus();
        this.nic = fpDirectorDetail.getNic();
        this.shareHolding = fpDirectorDetail.getShareHolding();
        this.constitutionType = fpDirectorDetail.getConstitutionType();
        this.creditCard = fpDirectorDetail.getCreditCard();
    }

    public Integer getFpDirectorDetailID() {
        return fpDirectorDetailID;
    }

    public void setFpDirectorDetailID(Integer fpDirectorDetailID) {
        this.fpDirectorDetailID = fpDirectorDetailID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
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

    public String getDateOfBirthStr() {
        return dateOfBirthStr;
    }

    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "FPDirectorDetailDTO{" +
                "fpDirectorDetailID=" + fpDirectorDetailID +
                ", facilityPaperID=" + facilityPaperID +
                ", directorName='" + directorName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", civilStatus=" + civilStatus +
                ", dateOfBirthStr='" + dateOfBirthStr + '\'' +
                ", nic='" + nic + '\'' +
                ", shareHolding=" + shareHolding +
                ", constitutionType=" + constitutionType +
                ", creditCard=" + creditCard +
                ", status=" + status +
                '}';
    }
}
