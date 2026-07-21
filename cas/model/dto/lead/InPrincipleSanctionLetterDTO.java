package com.itechro.cas.model.dto.lead;


import com.itechro.cas.model.domain.lead.ComprehensiveFacility;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Data
public class InPrincipleSanctionLetterDTO implements Serializable {


    private String purpose;
    private BigDecimal requestedLeaseAmount;
    private Integer requestedTenure;
    private String repaymentMode;
    private BigDecimal indicativeLeaseRental;
    private BigDecimal upfront;
    private BigDecimal processingFee;
    private BigDecimal insurance;
    private String validityOfThisOffer;
    private String make;
    private String model;
    private String leadRefNo;
    private String date;
    private List<InPrincipalPartDetails> inPrincipalPartDetails = new ArrayList<>();

    public InPrincipleSanctionLetterDTO (){}
    public InPrincipleSanctionLetterDTO (ComprehensiveFacility comprehensiveFacility){

        this.requestedLeaseAmount = comprehensiveFacility.getLeaseAmount();
        this.requestedTenure = comprehensiveFacility.getRequestedTenure();
        this.repaymentMode = comprehensiveFacility.getRepaymentMode();
        this.indicativeLeaseRental = comprehensiveFacility.getLeaseRental();
        this.upfront = comprehensiveFacility.getUpfront();
        this.processingFee = comprehensiveFacility.getProcessingFee();
        this.insurance = comprehensiveFacility.getInsurance();
        this.validityOfThisOffer = comprehensiveFacility.getValidityOfOffer();
        this.make = comprehensiveFacility.getMake();
        this.model = comprehensiveFacility.getModel();

    }

    public String getRequestedLeaseAmount() {

        String requestedLeaseAmountW = "";

        try{
            requestedLeaseAmountW = formatWithCommasKeepDecimals(requestedLeaseAmount) !=null? formatWithCommasKeepDecimals(requestedLeaseAmount) + " ("+ amountToWord(requestedLeaseAmount) +")" : "";
        }
        catch (Exception exception){
            requestedLeaseAmountW ="";
        }

        return requestedLeaseAmountW;
    }

    public String getIndicativeLeaseRental() {

        String formattedIndicativeLeaseRental = "";

        try{
            formattedIndicativeLeaseRental = formatWithCommasKeepDecimals(indicativeLeaseRental);
        }
        catch (Exception exception){
            formattedIndicativeLeaseRental = "";
        }

        return formattedIndicativeLeaseRental;
    }

    public String getProcessingFee() {

        String formattedProcessingFee = "";

        try{
            formattedProcessingFee = formatWithCommasKeepDecimals(processingFee);
        }
        catch (Exception exception){
            formattedProcessingFee = "";
        }
        return formattedProcessingFee;
    }


    public String getInsurance() {

        String formattedInsurance = "";

        try{
            formattedInsurance = formatWithCommasKeepDecimals(insurance);
        }
        catch (Exception exception){
            formattedInsurance = "";
        }

        return formattedInsurance;
    }

    public static String amountToWord(BigDecimal amount) {
        if (amount == null) return "";

        // Normalize to 2 decimals for cents (round half up)
        amount = amount.setScale(2, RoundingMode.HALF_UP);

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return "Rupees Zero Only";
        }

        boolean negative = amount.signum() < 0;
        amount = amount.abs();

        BigInteger rupees = amount.toBigInteger(); // integer part
        int cents = amount.remainder(BigDecimal.ONE)
                .movePointRight(2)      // fractional * 100
                .intValueExact();       // safe because scale=2

        StringBuilder out = new StringBuilder();

        if (negative) out.append("Minus ");

        out.append("Rupees ").append(convertNumber(rupees));

        if (cents > 0) {
            out.append(" and ");
            out.append(cents == 1 ? "Cent " : "Cents ");
            out.append(convertUpTo999(cents));
        }

        out.append(" Only");
        return out.toString().replaceAll("\\s+", " ").trim();
    }

    private static String convertNumber(BigInteger n) {
        if (n.equals(BigInteger.ZERO)) return "Zero";

        // Support up to billions (can extend easily)
        BigInteger billion = BigInteger.valueOf(1_000_000_000L);
        BigInteger million = BigInteger.valueOf(1_000_000L);
        BigInteger thousand = BigInteger.valueOf(1_000L);

        StringBuilder sb = new StringBuilder();

        if (n.compareTo(billion) >= 0) {
            BigInteger count = n.divide(billion);
            sb.append(convertNumber(count)).append(" Billion ");
            n = n.mod(billion);
        }

        if (n.compareTo(million) >= 0) {
            BigInteger count = n.divide(million);
            sb.append(convertUpTo999(count.intValue())).append(" Million ");
            n = n.mod(million);
        }

        if (n.compareTo(thousand) >= 0) {
            BigInteger count = n.divide(thousand);
            sb.append(convertUpTo999(count.intValue())).append(" Thousand ");
            n = n.mod(thousand);
        }

        if (n.compareTo(BigInteger.ZERO) > 0) {
            sb.append(convertUpTo999(n.intValue()));
        }

        return sb.toString().trim();
    }

    private static final String[] ONES = {
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
            "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] TENS = {
            "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    private static String convertUpTo999(int number) {
        if (number == 0) return "Zero";

        StringBuilder sb = new StringBuilder();

        if (number >= 100) {
            sb.append(ONES[number / 100]).append(" Hundred");
            number = number % 100;
            if (number > 0) sb.append(" "); // change to " and " if you prefer
        }

        if (number >= 20) {
            sb.append(TENS[number / 10]);
            if (number % 10 != 0) sb.append(" ").append(ONES[number % 10]);
        } else if (number > 0) {
            sb.append(ONES[number]);
        }

        return sb.toString().trim();
    }
    

    public static String formatWithCommasKeepDecimals(BigDecimal value) {
        if (value == null) return null;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        df.setGroupingUsed(true);

        return df.format(value);
    }




    public void addInprincipalParty (InPrincipalPartDetails inPrincipalPartDetails){
        this.inPrincipalPartDetails.add(inPrincipalPartDetails);
    }

    public InPrincipalPartDetails getPrimaryParty(){

        if (inPrincipalPartDetails == null || inPrincipalPartDetails.isEmpty()) {
            return new InPrincipalPartDetails();
        }
        return inPrincipalPartDetails.get(0);
    }

    public InPrincipalPartDetails getSecondParty(){

        if (inPrincipalPartDetails == null || inPrincipalPartDetails.isEmpty()) {
            return new InPrincipalPartDetails();
        }
        return inPrincipalPartDetails.get(1);
    }

    public boolean isSecondaryPartyExist (){
        if (inPrincipalPartDetails == null || inPrincipalPartDetails.isEmpty() || inPrincipalPartDetails.size() < 2) {
            return false;
        }
        return true;
    }

    public boolean isAddress1Exist(String partyType){

        if(partyType.equals("primary"))
            return getPrimaryParty().getAddress1() != null && !getPrimaryParty().getAddress1().isEmpty();

        if(partyType.equals("secondary"))
            return getSecondParty().getAddress1() != null && !getSecondParty().getAddress1().isEmpty();

        return false;
    }

    public boolean isAddress2Exist(String partyType){
        if(partyType.equals("primary"))
            return getPrimaryParty().getAddress2() != null && !getPrimaryParty().getAddress2().isEmpty();

        if(partyType.equals("secondary"))
            return getSecondParty().getAddress2() != null && !getSecondParty().getAddress2().isEmpty();

        return false;
    }

    public boolean isCityExist(String partyType){
        if(partyType.equals("primary"))
            return getPrimaryParty().getCity() != null && !getPrimaryParty().getCity().isEmpty();

        if(partyType.equals("secondary"))
            return getSecondParty().getCity() != null && !getSecondParty().getCity().isEmpty();

        return false;
    }

}
