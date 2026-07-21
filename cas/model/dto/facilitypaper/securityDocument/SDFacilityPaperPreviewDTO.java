package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.securityDocument.FPSecurityDocumentTag;
import com.itechro.cas.model.dto.casmaster.FacilityCustomInfoDataDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerIdentificationDTO;
import com.itechro.cas.model.dto.facilitypaper.PaymentDetailsObject;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculationDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.StipulatedLossDetailDTO;
import com.itechro.cas.model.dto.facilitypaper.securityDocument.SDCustomerDTO;
import com.itechro.cas.service.faclititypaper.SecurityDocumentService;
import com.itechro.cas.util.DecimalCalculator;
import lombok.Data;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class SDFacilityPaperPreviewDTO implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(SDFacilityPaperPreviewDTO.class);

    private String documentName;

    private String branchName;

    private String accountNo;

    private SDCustomerDTO primaryCustomer;

    private List<SDCustomerDTO> joinCustomers;

    private List<String> cusApplicableCovenants;

    private List<String> facilitySecurities;

    private List<FacilityCustomInfoDataDTO> facilityCustomInfoDataList;

    private List<CalculationDTO> systemOutputResponseData;

    private List<StipulatedLossDetailDTO> stipulatedLossDetails;

    private List<FPSecurityDocumentTag> fpSecurityDocumentTags;

    private SDFacilityDTO sdFacility;

    public String getDocumentName() {
        if (documentName == null){
            return "";
        }
        return documentName;
    }

    public boolean isCustomInfoValueExist(String code) {
        if (getFacilityCustomInfoDataList() != null && !getFacilityCustomInfoDataList().isEmpty()) {
            Optional<FacilityCustomInfoDataDTO> optionalInfo = getFacilityCustomInfoDataList().stream().
                    filter(data -> data.getCustomFacilityInfoCode().trim().equals(code)).findFirst();

            return optionalInfo.isPresent();
        }

        return false;
    }

    public String getCustomInfoValue(String code) {
        String value = "";

        if (getFacilityCustomInfoDataList() != null && !getFacilityCustomInfoDataList().isEmpty()) {
            Optional<FacilityCustomInfoDataDTO> optionalInfo = getFacilityCustomInfoDataList().stream().
                    filter(data -> data.getCustomFacilityInfoCode().trim().equals(code)).findFirst();

            if (optionalInfo.isPresent()) {
                value = optionalInfo.get().getCustomInfoData();
            }
        }
        return value;
    }

    public Object getSystemOutputResponseDataValue(String code) {
        Object value = "";

        String searchCode;
        if (code.contains("_W")){
            searchCode = code.replace("_W","");
        } else {
            searchCode = code;
        }

        if (getSystemOutputResponseData() != null && !getSystemOutputResponseData().isEmpty()) {
            Optional<CalculationDTO> optionalInfo = getSystemOutputResponseData().stream().
                    filter(data -> data.getCode().equals(searchCode)).findFirst();

            if (optionalInfo.isPresent()) {
                switch (code) {
                    case "OUT_001":
                    case "OUT_004":
                    case "OUT_007":
                        value = getFormattedAmount(optionalInfo.get().getValue());
                        break;
                    case "OUT_001_W":
                    case "OUT_007_W":
                    case "OUT_004_W":
                        value = this.NumberToWordsConverter(Double.parseDouble(optionalInfo.get().getValue()));
                        break;
                    case "CFT_OFI24":
                        if (optionalInfo.get().getValue().equals("1")) {
                            value = "First Month Rental Only";
                        } else {
                            if (optionalInfo.get().getValue().equals("2")) {
                                value = "First and Last Month Rentals Only";
                            } else {
                                value = "First and Last " + this.NumberToWordsConverter(Double.parseDouble(optionalInfo.get().getValue()) - 1) + " Month Rentals Only";
                            }
                        }
                        break;
                    default:
                        value = optionalInfo.get().getValue();
                        break;
                }
            }
        }

        return value;
    }

    public String NumberToWordsConverter(double number) {
        if (number == 0) return "Zero";

        int rupee = (int) number;
        int cents = (int) Math.round((number - rupee) * 100);

        StringBuilder result = new StringBuilder();

        if (rupee > 0) {
            result.append("Rupees ").append(convertRecursive(rupee));
        }

        if (cents > 0) {
            if (rupee > 0) result.append(" and ");

            if (cents > 1) {
                result.append("Cents ").append(convertRecursive(cents));
            } else {
                result.append("Cent ").append(convertRecursive(cents));
            }
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

    public String getPrepayments() {
        if (getSystemOutputResponseData() != null && !getSystemOutputResponseData().isEmpty()) {
            Optional<CalculationDTO> optionalInfo = getSystemOutputResponseData().stream().
                    filter(data -> data.getCode().equals("CFT_OFI24")).findFirst();

            if (optionalInfo.isPresent()) {
                double upFronts = Double.parseDouble(optionalInfo.get().getValue()) - 1;
                return "01 + 0" + (int)upFronts;
            }
        }
        return "";
    }

    public String getFormattedAmount(Object amount) {
        if (amount != null) {
            return getFormattedFigure(amount);
        } else {
            return "";
        }
    }

    public String getFormattedFigure(Object value) {
        String result = "";
        DecimalFormat df = new DecimalFormat("#,###,###,###,##0.00");
        try {
            Double decimalValue = new Double(value.toString().replaceAll(",",""));
            if (decimalValue < 0) {
                Object unFormattedAmount = new BigDecimal(decimalValue.toString()).abs();
                return result.concat("(").concat(df.format(unFormattedAmount)).concat(")");
            } else {
                Object unFormattedAmount = new BigDecimal(decimalValue.toString());
                return df.format(unFormattedAmount);
            }
        } catch (NumberFormatException e) {
            return value.toString();
        }
    }

    public List<SDCustomerDTO> getJoinCustomers() {
        if (joinCustomers == null){
            return new ArrayList<>();
        }
        return joinCustomers;
    }

    public void addJoinCustomers(SDCustomerDTO sdCustomerDTO) {
        joinCustomers = getJoinCustomers();
        joinCustomers.add(sdCustomerDTO);
    }

    public SDCustomerAddressDTO getPrimaryCustomerAddressByType(String type){

        if (this.primaryCustomer != null && type != null){
            SDCustomerAddressDTO address = this.primaryCustomer.getCustomerAddress().stream()
                    .filter(item -> item.getAddressType().equals(type))
                    .findAny()
                    .orElse(null);

            if (address != null){
                return address;
            }
        }

        return new SDCustomerAddressDTO();
    }

    public SDCustomerAddressDTO getJoinCustomerAddressByType(String key, String type) {

        if (key == null || type == null) {
            return new SDCustomerAddressDTO();
        }

        return this.getJoinCustomers().stream()
                .filter(customer -> customer.getCustomerKey().equals(key))
                .flatMap(customer -> customer.getCustomerAddress().stream())
                .filter(address -> address.getAddressType().equals(type))
                .findFirst()
                .orElse(new SDCustomerAddressDTO());
    }

    public PaymentDetailsObject getPaymentDetailsObject() {

        int fromMonth =0;
        int toMonth =1;
        int noOfMonths =0;
        double amount = 0;
        double totalAmount=0;
        double deposit = 0;
        double defaultRate= 0;
        PaymentDetailsObject paymentDetailsObject = new PaymentDetailsObject();
        try {
            if (getSystemOutputResponseData() != null && !getSystemOutputResponseData().isEmpty()) {

                noOfMonths = toMonth - fromMonth;

                paymentDetailsObject.setMonthNoRow1(fromMonth + "-" + toMonth);
                paymentDetailsObject.setNoOfMonthsRow1(String.valueOf(noOfMonths));

                Optional<CalculationDTO> rental = getSystemOutputResponseData().stream().
                        filter(data -> data.getCode().equals("OUT_007")).findFirst();

                Optional<CalculationDTO> upfronts = getSystemOutputResponseData().stream().
                        filter(data -> data.getCode().equals("CFT_OFI24")).findFirst();

                Optional<CalculationDTO> period = getSystemOutputResponseData().stream().
                        filter(data -> data.getCode().equals("CFT_OFI23")).findFirst();

                Optional<CalculationDTO> defaultRateOpt = getSystemOutputResponseData().stream().
                        filter(data -> data.getCode().equals("CFT_OFI25")).findFirst();

                if (rental.isPresent() && upfronts.isPresent() && period.isPresent()) {
                    amount = Double.parseDouble(rental.get().getValue()) * noOfMonths;
                    totalAmount = totalAmount + amount;
                    paymentDetailsObject.setRentalPerMonthRow1(getFormattedFigure(rental.get().getValue()));
                    paymentDetailsObject.setAmountRow1(getFormattedFigure(amount));
                    paymentDetailsObject.setTotalAmount(getFormattedFigure(totalAmount));

                    //row2
                    if ((int) Double.parseDouble(upfronts.get().getValue()) == 1) {
                        noOfMonths = (int) (Double.parseDouble(period.get().getValue()) - Double.parseDouble(upfronts.get().getValue()));
                        fromMonth = toMonth + 1;
                        toMonth = noOfMonths + 1;
                        amount = Double.parseDouble(rental.get().getValue()) * noOfMonths;
                        totalAmount = totalAmount + amount;

                        paymentDetailsObject.setMonthNoRow2(fromMonth + "-" + toMonth);
                        paymentDetailsObject.setNoOfMonthsRow2(String.valueOf(noOfMonths));
                        paymentDetailsObject.setRentalPerMonthRow2(getFormattedFigure(rental.get().getValue()));
                        paymentDetailsObject.setAmountRow2(getFormattedFigure(amount));
                        paymentDetailsObject.setTotalAmount(getFormattedFigure(totalAmount));
                        paymentDetailsObject.setDeposit(getFormattedFigure(deposit));

                        String staticSecurity = "Lease Agreement for Rs." + getFormattedAmount(totalAmount) +
                                " together with 01 upfront rental of Rs." + getFormattedAmount(rental.get().getValue());
                        paymentDetailsObject.setSecurityStatement1(staticSecurity);
                    } else {
                        noOfMonths = (int) (Double.parseDouble(period.get().getValue()) - Double.parseDouble(upfronts.get().getValue()));
                        fromMonth = toMonth + 1;
                        toMonth = noOfMonths + 1;
                        amount = Double.parseDouble(rental.get().getValue()) * noOfMonths;
                        totalAmount = totalAmount + amount;

                        paymentDetailsObject.setMonthNoRow2(fromMonth + "-" + toMonth);
                        paymentDetailsObject.setNoOfMonthsRow2(String.valueOf(noOfMonths));
                        paymentDetailsObject.setRentalPerMonthRow2(getFormattedFigure(rental.get().getValue()));
                        paymentDetailsObject.setAmountRow2(getFormattedFigure(amount));
                        paymentDetailsObject.setTotalAmount(getFormattedFigure(totalAmount));
                        paymentDetailsObject.setDeposit(getFormattedFigure(getSystemOutputResponseDataValue("OUT_007")));

                        fromMonth = toMonth + 1;
                        toMonth = (int) Double.parseDouble(period.get().getValue());
                        noOfMonths = (int) (Double.parseDouble(upfronts.get().getValue()) - 1);
                        amount = Double.parseDouble(rental.get().getValue()) * noOfMonths;
                        totalAmount = totalAmount + amount;

                        paymentDetailsObject.setMonthNoRow3(fromMonth == toMonth ? String.valueOf(fromMonth) : fromMonth + "-" + toMonth);
                        paymentDetailsObject.setNoOfMonthsRow3(String.valueOf(noOfMonths));
                        paymentDetailsObject.setRentalPerMonthRow3(getFormattedFigure(rental.get().getValue()));
                        paymentDetailsObject.setAmountRow3(getFormattedFigure(amount));
                        paymentDetailsObject.setTotalAmount(getFormattedFigure(totalAmount));

                        Double rentalUpfront = Double.parseDouble(rental.get().getValue()) * Double.parseDouble(upfronts.get().getValue());
                        String staticSecurity = "Lease Agreement for Rs." + getFormattedAmount(totalAmount) +
                                " together with 0" + (int) Double.parseDouble(upfronts.get().getValue()) + " upfront rentals totaling to  Rs." + getFormattedAmount(rentalUpfront);
                        paymentDetailsObject.setSecurityStatement1(staticSecurity);
                    }
                } else {
                    paymentDetailsObject.setRentalPerMonthRow1("");
                    paymentDetailsObject.setAmountRow1("");
                }

                if (defaultRateOpt.isPresent()) {
                    defaultRate = Double.parseDouble(defaultRateOpt.get().getValue()) + 2;
                    paymentDetailsObject.setDefaultRate(String.valueOf(defaultRate));
                } else {
                    paymentDetailsObject.setDefaultRate("");
                }

                return paymentDetailsObject;
            }
            return new PaymentDetailsObject();
        }
        catch (Exception ex)
        {
            LOG.info("ERROR Security Document Content - get payment details object: ", ex);
            return new PaymentDetailsObject();
        }
    }

    public CASCustomerIdentificationDTO getPrimaryCustomerIdentificationByType(DomainConstants.CustomerIdentificationType type){

        if (this.primaryCustomer != null && type != null){
            CASCustomerIdentificationDTO identification = this.primaryCustomer.getCustomerIdentifications().stream()
                    .filter(item -> item.getIdentificationType().equals(type))
                    .findAny()
                    .orElse(null);

            if (identification != null){
                return identification;
            }
        }

        return new CASCustomerIdentificationDTO();
    }

    public String getCostOfInitialPayments() {
        Object rental = null;
        Object upFronts = null;

        if (getSystemOutputResponseData() != null && !getSystemOutputResponseData().isEmpty()) {
            Optional<CalculationDTO> optionalRentalInfo = getSystemOutputResponseData().stream().
                    filter(data -> data.getCode().equals("OUT_007")).findFirst();

            Optional<CalculationDTO> optionalUpfrontInfo = getSystemOutputResponseData().stream().
                    filter(data -> data.getCode().equals("CFT_OFI24")).findFirst();

            if (optionalUpfrontInfo.isPresent()) {
                upFronts = optionalUpfrontInfo.get().getValue();
            }

            if (optionalRentalInfo.isPresent()) {
                rental = optionalRentalInfo.get().getValue();
            }
        }
        if (rental != null && upFronts != null) {
            BigDecimal costOfInitialPayment = DecimalCalculator.multiply(DecimalCalculator.getFormattedActualValue(rental), DecimalCalculator.getFormattedActualValue(upFronts));
            return getFormattedAmount(costOfInitialPayment);
        }

        return "";
    }

    public String getMonthlyRentalClause() {
        Object rental = null;
        Object period = null;
        Object upFronts = null;
        if (getSystemOutputResponseData() != null && !getSystemOutputResponseData().isEmpty()) {
            Optional<CalculationDTO> optionalRentalInfo = getSystemOutputResponseData().stream().
                    filter(data -> data.getCode().equals("OUT_007")).findFirst();

            Optional<CalculationDTO> optionalUpfrontInfo = getSystemOutputResponseData().stream().
                    filter(data -> data.getCode().equals("CFT_OFI24")).findFirst();

            Optional<CalculationDTO> optionalPeriodInfo = getSystemOutputResponseData().stream().
                    filter(data -> data.getCode().equals("CFT_OFI23")).findFirst();

            if (optionalRentalInfo.isPresent()) {
                rental = optionalRentalInfo.get().getValue();
            }

            if (optionalUpfrontInfo.isPresent()) {
                upFronts = optionalUpfrontInfo.get().getValue();
            }

            if (optionalPeriodInfo.isPresent()) {
                period = optionalPeriodInfo.get().getValue();
            }
        }
        if (rental != null && upFronts != null && period != null) {
            double periodMinusUpFronts = Double.parseDouble(period.toString()) - Double.parseDouble(upFronts.toString());
            int periodMinusUpFrontsInt = (int) Math.round(periodMinusUpFronts);
            return getFormattedAmount(DecimalCalculator.getFormattedActualValue(rental)).concat(" X ").concat(String.valueOf(periodMinusUpFrontsInt));
        }

        return "";
    }

    public String getStipulatedLossValueByKey(String key){

        if (getStipulatedLossDetails() != null && !getStipulatedLossDetails().isEmpty()){
            Optional<StipulatedLossDetailDTO> optionalInfo = getStipulatedLossDetails().stream().
                    filter(data -> data.getKey().equals(key)).findFirst();

            if (optionalInfo.isPresent()){
                return optionalInfo.get().getValue();
            }
        }

        return DecimalCalculator.getDefaultZero().toString();
    }

    public SDCustomerDTO getJoinCustomerByKey(String key){

        Optional<SDCustomerDTO> optionalInfo = this.getJoinCustomers().stream().
                filter(data -> data.getCustomerKey().equals(key)).findFirst();

        return optionalInfo.orElseGet(SDCustomerDTO::new);
    }

    public CASCustomerIdentificationDTO getJoinCustomerIdentificationByType(String key, DomainConstants.CustomerIdentificationType type) {

        if (key == null || type == null) {
            return new CASCustomerIdentificationDTO();
        }

        return this.getJoinCustomers().stream()
                .filter(customer -> customer.getCustomerKey().equals(key))
                .flatMap(customer -> customer.getCustomerIdentifications().stream())
                .filter(identification -> identification.getIdentificationType().equals(type))
                .findFirst()
                .orElse(new CASCustomerIdentificationDTO());
    }

    public List<FPSecurityDocumentTag> getFpSecurityDocumentTags() {
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

    public String getFacilityAmount() {

        try {
            return getFormattedAmount(sdFacility.getFacilityAmount());
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public String getCompletionPeriod(){
        if (!getTagValue("Completion-Period").isEmpty()){
            return getTagValue("Completion-Period");
        }
        return "one month";
    }

    public String getSumEquivalent(){
        if (!getTagValue("Sum-Equivalent-To").isEmpty()){
            return getTagValue("Sum-Equivalent-To");
        }
        return "0.25% (Minimum of Rs.5,000/-)";
    }

    public String getProcessingFee(){
        String value = getCustomInfoValue("PROCESSING_FEE");
        if (value != null && !value.isEmpty()){
            return getFormattedAmount(value);
        }

        return "";
    }

    public String getAccountNo() {
        if (accountNo == null){
            return "";
        }
        return accountNo.replaceAll("(.{4})", "$1 ").trim();
    }

    public boolean isJoinCustomerExist(String key){

        Optional<SDCustomerDTO> optionalInfo = this.getJoinCustomers().stream().
                filter(data -> data.getCustomerKey().equals(key)).findFirst();

        return optionalInfo.isPresent();
    }

    public void setCusApplicableCovenants(List<String> cusApplicableCovenants) {
        if (cusApplicableCovenants != null && !cusApplicableCovenants.isEmpty()) {
            this.cusApplicableCovenants = cusApplicableCovenants.stream().map(this::cleanText)
                    .collect(Collectors.toList());
        }
    }

    private String cleanText(String text){

        String decoded = StringEscapeUtils.unescapeHtml4(text);
        decoded = decoded.replace('\u00A0', ' ');
        return decoded.replaceAll(" +", " ").trim();

    }
}
