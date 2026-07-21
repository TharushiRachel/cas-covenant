package com.itechro.cas.model.dto.facilitypaper.request;

import com.itechro.cas.commons.constants.AppsConstants;
//import javafx.util.Pair;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
public class FacilitySetDTO {

    private Integer facilityCovenantId;
    private Integer facilityID;
    private Integer creditFacilityTemplateID;
    private String creditFacilityName;
    private String facilityRefCode;
    private String facilityCurrency;
    private BigDecimal facilityAmount;
    private Integer displayOrder;

    private ApplicationLevelCovenant applicationLevelCovenant;

    @Data
    private static class ApplicationLevelCovenant{
        private Integer applicationCovenantId;
        private String requestUUID;
        private String customerFinancialID;
        private String fpRefNumber;
        private String covenant_Code;
        private String covenant_Description;
        private String covenant_Frequency;
        private Date covenant_Due_Date;
        private AppsConstants.CovenantStatus status;
        private String createdUserDisplayName;
        private Date createdDate;
        private String createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacilitySetDTO that = (FacilitySetDTO) o;
        return facilityID.equals(that.facilityID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facilityID);
    }

    public String getDisplayText()
    {
        String displayText="";
        displayText=this.displayOrder+" "+this.getCreditFacilityName()+" "+this.getFacilityCurrency()+" "+this.getFacilityAmount()+"~";
        return displayText;
    }

//    public String getCovanentKey() {
//        ArrayList<Integer> keyArray = new ArrayList<>();
//        ArrayList<String> displayTextArray = new ArrayList<>();
//        String covKey = "";
//
//        List<Pair<Integer, String>> pairs = new ArrayList<>();
//        for (int i = 0; i < keyArray.size(); i++) {
//            pairs.add(new Pair<>(keyArray.get(i), displayTextArray.get(i)));
//        }
//        pairs.sort(Comparator.comparingInt(Pair::getKey));
//
//        for (Pair<Integer, String> pair : pairs) {
//            covKey = covKey + "-" + pair.getValue();
//        }
//
//        if (!covKey.isEmpty()) {
//            covKey = covKey.substring(1);
//        }
//
//        return covKey;
//    }
}
