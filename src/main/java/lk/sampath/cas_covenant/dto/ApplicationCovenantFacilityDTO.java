package lk.sampath.cas_covenant.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class ApplicationCovenantFacilityDTO {

    private Integer applicationCovenantId;

    private Integer facilityID;
    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private String facilityRefCode;

    private String facilityCurrency;

    private BigDecimal facilityAmount;

    private Integer displayOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationCovenantFacilityDTO that = (ApplicationCovenantFacilityDTO) o;
        return facilityID.equals(that.facilityID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facilityID);
    }

    public String getDisplayText()
    {
        String displayText="";
        displayText= this.displayOrder+". "+this.getCreditFacilityName()+" - "+this.getFacilityCurrency()+" "+this.getFacilityAmount()+"~";
        return displayText;
    }
}
