package com.itechro.cas.model.dto.lead;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class SDFacilityPaperPreviewDTO {
    private static final Logger LOG = LoggerFactory.getLogger(SDFacilityPaperPreviewDTO.class);
    private String documentName;

    private String branchName;

    private String accountNo;

    private List<FPSecurityDocumentTagDTO> fpSecurityDocumentTags;

    private List<FacilityDTO> facilityDetails = new ArrayList<>();

    private List<ApplicantDetailsDTO> applicantsDetails = new ArrayList<>();

    public List<ApplicantDetailsDTO> getApplicantsDetails() { // keep your spelling
        return applicantsDetails;
    }

    public void setApplicantsDetails(List<ApplicantDetailsDTO> applicantsDetails) {
        this.applicantsDetails = applicantsDetails;
    }

    public String getDocumentName() {
        if (documentName == null){
            return "";
        }
        return documentName;
    }
    public List<FacilityDTO> getFacilityDetails() {
        return facilityDetails;
    }

    public void setFacilityDetails(List<FacilityDTO> facilityDetails) {
        this.facilityDetails = facilityDetails;
    }

    public String NumberToWordsConverter(double number) {
        if (number == 0) return "Zero";

        int rupee = (int) number;
        int cents = (int) Math.round((number - rupee) * 100);

        StringBuilder result = new StringBuilder();

        if (rupee > 0) {
            result.append(convertRecursive(rupee)).append(" Rupee");
            if (rupee > 1) result.append("s");
        }

        if (cents > 0) {
            if (rupee > 0) result.append(" and ");
            result.append(convertRecursive(cents)).append(" Cent");
            if (cents > 1) result.append("s");
        }

        return result.toString().trim();
    }

    public String convertRecursive(int number) {
        String[] units = {
                "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
                "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };

        String[] tens = {
                "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        };

        if (number < 20) return units[number];
        if (number < 100) return tens[number / 10] + " " + units[number % 10];
        if (number < 1000) return units[number / 100] + " Hundred " + convertRecursive(number % 100);
        if (number < 1000000) return convertRecursive(number / 1000) + " Thousand " + convertRecursive(number % 1000);
        if (number < 1000000000)
            return convertRecursive(number / 1000000) + " Million " + convertRecursive(number % 1000000);
        return convertRecursive(number / 1000000000) + " Billion " + convertRecursive(number % 1000000000);
    }

    public List<FPSecurityDocumentTagDTO> getFpSecurityDocumentTags() {
        if (fpSecurityDocumentTags == null){
            return new ArrayList<>();
        }
        return fpSecurityDocumentTags;
    }

    public String getTagValue(String key){

        if (key == null){
            return "";
        }

        String value = this.getFpSecurityDocumentTags().stream()
                .filter(tag -> key.equals(tag.getTag()))
                .map(tag -> tag.getTagValue() != null ? tag.getTagValue() : "")
                .findFirst()
                .orElse("");
        if (key.equals("Signing-Officer-2-Name")){
            return value.isEmpty() ? "AUTHORIZED OFFICER" : value;
        }
        return value;
    }

    public String getAccountNo() {
        if (accountNo == null){
            return "";
        }
        return accountNo.replaceAll("(.{4})", "$1 ").trim();
    }


}
