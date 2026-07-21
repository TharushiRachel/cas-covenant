package com.itechro.cas.model.dto.facilitypaper.request;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FacilityCovenantReqDTO {

    private Integer applicationCovenantId;
    private String requestUUID;
    private Customer customer;
    private FacilityPaper facilityPaper;
    private String covenant_Code;
    private String covenant_Description;
    private String covenant_Frequency;
    private Date covenant_Due_Date;
    private AppsConstants.CovenantStatus status;
    private String createdUserDisplayName;
    private Date createdDate;
    private String createdBy;
    private List<FacilitySetDTO> FacilitySetDTO;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    @Data
    private static class Customer{
        private String customerFinancialID;
    }

    @Data
    private static class FacilityPaper{
        private String fpRefNumber;
    }

//    public String getCovanentKey() {
//        ArrayList<Integer> keyArray = new ArrayList<>();
//        ArrayList<String> displayTextArray = new ArrayList<>();
//        String covKey = "";
//
//        if(FacilitySetDTO != null){
//            for (FacilitySetDTO acf : FacilitySetDTO) {
//                if(acf != null){
//                    keyArray.add(acf.getFacilityID());
//                    displayTextArray.add(acf.getDisplayText());
//                }
//            }
//        }
//
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
