package com.itechro.cas.util;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.facility.FacilityRentalInformationDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mariuszgromada.math.mxparser.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalculatorUtil {
    private static final Logger LOG = LoggerFactory.getLogger(CalculatorUtil.class);
    private static final String FLAT_RATE = "flatRate";
    private static final String SANCTION_AMOUNT = "sanctionAmount";
    private static final String GROSS_AMOUNT = "grossAmount";
    private static final String IRR = "irr";
    private static final String RATE = "rate";

    public static double calculate(String mathematicsExpression) {

        Expression expression = new Expression(mathematicsExpression);
        return expression.calculate();
    }

    public static String calculateGeneralFormula(String expression) {
        String value = null;
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        try {
            boolean isNumeric = NumberUtil.isNumeric(expression);
            value = isNumeric ? scriptEngine.eval(expression).toString() : expression;
        } catch (ScriptException e) {
            LOG.error("ERROR : ", e);
        }
        return value;
    }

    public static double calculateCustomFormula(String expression) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellFormula(expression);

        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = formulaEvaluator.evaluate(cell);

        workbook.close();
        return cellValue.getNumberValue();
    }

    public static double calculateMethodFormula(String methodName, List<CreditScheduleDTO> creditScheduleDTOList, List<CalculationDTO> calculationDTOList) {
        double value = 0.0;
        if (methodName.equals(FLAT_RATE)) {
            double totalInterest = creditScheduleDTOList.stream().mapToDouble(c -> Double.valueOf(c.getInterest())).sum();
            double period = 0;
            double leaseCapital = 0;
            for (CalculationDTO calculationDTO : calculationDTOList) {
                if ("OUT002".equals(calculationDTO.getOutputCode())) {
                    period = Double.parseDouble(calculationDTO.getValue());
                }

                if ("OUT006".equals(calculationDTO.getOutputCode())) {
                    leaseCapital = Double.parseDouble(calculationDTO.getValue());
                }
            }
            String expression = "(" + totalInterest + "/(" + period + "/12)" + "/" + leaseCapital + ")*100";
            try {
                value = calculateCustomFormula(expression);
            } catch (IOException e) {
                LOG.error("ERROR : ", e);
            }
        } else if (methodName.equals(SANCTION_AMOUNT)) {
            value = creditScheduleDTOList.stream().mapToDouble(c -> Double.parseDouble(c.getRentalWithoutVat())).sum();
        } else if (methodName.equals(GROSS_AMOUNT)) {
            value = creditScheduleDTOList.stream().mapToDouble(c -> Double.parseDouble(c.getRentalWithVat())).sum();
        }
        return value;
    }

    public static double calculateSpecialFormula(String formulaValue, List<CalculationDTO> calculationDTOList, CalculatorInputDTO calculatorInputDTO) {
        double value = 0.0;
        double period = 0;
        double effectiveRate = 0.0;
        double leaseCapital = 0.0;
        double rentalWithoutVAT = 0.0;
        for (CalculationDTO calculationDTO : calculationDTOList) {
            if (calculationDTO.getOutputCode().equals("OUT002")) {
                period = Double.parseDouble(calculationDTO.getValue());
            }

            if (calculationDTO.getOutputCode().equals("OUT003")) {
                effectiveRate = Double.parseDouble(calculationDTO.getValue());
            }

            if (calculationDTO.getOutputCode().equals("OUT006")) {
                leaseCapital = Double.parseDouble(calculationDTO.getValue());
            }

            if (calculationDTO.getOutputCode().equals("OUT011")) {
                rentalWithoutVAT = Double.parseDouble(calculationDTO.getValue());
            }
        }
        if (formulaValue.equalsIgnoreCase(IRR)) {
            double[] cashFlow = expandRentalData(calculatorInputDTO.getRentalData(), (int) Math.round(period));
            value = getIrrValue(cashFlow, effectiveRate, leaseCapital);
        } else if (formulaValue.equalsIgnoreCase(RATE)) {
            value = getRateValue(rentalWithoutVAT, leaseCapital, (int) period, effectiveRate);
        }
        return value;
    }

    public static double getIrrValue(double[] cashFlow, double effectiveRate, double leaseCapital) {
        return iror.IRR.calcIRR(cashFlow, effectiveRate, leaseCapital);
    }

    public static double getRateValue(double rentalWithoutVAT, double leaseCapital, Integer period, double effectiveRate) {
        return iror.Rate.getRate(rentalWithoutVAT, leaseCapital, Math.round(period), effectiveRate);
    }

    public static List<CreditScheduleDTO> getCreditScheduleData(String type, List<CalculationDTO> calculationDTOList, List<FacilityRentalInformationDTO> rentalData) {
        List<CreditScheduleDTO> response = new ArrayList<>();
        double period = 0;
        double leaseCapital = 0.0;
        double vatOnRental = 0.0;
        double grossAmount = 0.0;
        double sanctionAmount = 0.0;
        double effectiveRate = 0.0;
        if (type.equals(AppsConstants.CalculatorType.NORMAL.getType())) {
            double rentalWithVAT = 0.0;
            double rentalWithoutVAT = 0.0;
            double leaseAmount = 0.0;

            for (CalculationDTO calculationDTO : calculationDTOList) {
                if (calculationDTO.getOutputCode().equals("OUT002")) {
                    period = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT012")) {
                    rentalWithVAT = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT011")) {
                    rentalWithoutVAT = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT010")) {
                    grossAmount = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT009")) {
                    sanctionAmount = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT005")) {
                    vatOnRental = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT001")) {
                    leaseAmount = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT006")) {
                    leaseCapital = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT003")) {
                    effectiveRate = Double.parseDouble(calculationDTO.getValue());
                }
            }
            response = addIndexes((int) Math.round(period));
            response = addRentalWithVAT(type, response, rentalWithVAT, null);
            response = addRentalWithoutVAT(type, response, rentalWithoutVAT);
            response = addGrossRentalOutstanding(response, grossAmount);
            response = addSanctionOutstanding(response, sanctionAmount);
            response = addVat(response, vatOnRental);
            response = addInterestAndCapitalAndCapitalOutstanding(type, response, leaseAmount, (int) Math.round(period), leaseCapital, rentalWithoutVAT, effectiveRate);
        } else if (type.equals(AppsConstants.CalculatorType.STRUCTURED.getType())) {
            for (CalculationDTO calculationDTO : calculationDTOList) {
                if (calculationDTO.getOutputCode().equals("OUT002")) {
                    int i = Integer.parseInt(calculationDTO.getValue());
                    period += i;
                }

                if (calculationDTO.getOutputCode().equals("OUT006")) {
                    double i = Double.parseDouble(calculationDTO.getValue());
                    leaseCapital += i;
                }

                if (calculationDTO.getOutputCode().equals("OUT005")) {
                    vatOnRental = Double.parseDouble(calculationDTO.getValue());
                }

                if (calculationDTO.getOutputCode().equals("OUT003")) {
                    effectiveRate = Double.parseDouble(calculationDTO.getValue());
                }
            }
            response = addIndexes((int) Math.round(period));

            double[] rentals = expandRentalData(rentalData, (int) Math.round(period));
            response = addRentalWithVAT(type, response, 0, rentals);
            grossAmount = response.stream().mapToDouble(r -> Double.parseDouble(r.getRentalWithVat())).sum();
            response = addGrossRentalOutstanding(response, grossAmount);

            response = addVat(response, vatOnRental);
            response = addInterestAndCapitalAndCapitalOutstanding(type, response, 0, (int) Math.round(period), leaseCapital, 0, effectiveRate);

            response = addRentalWithoutVAT(type, response, 0);
            sanctionAmount = response.stream().mapToDouble(r -> Double.parseDouble(r.getRentalWithoutVat())).sum();
            response = addSanctionOutstanding(response, sanctionAmount);
        }
        return response;
    }

    public static List<CreditScheduleDTO> addIndexes(int period) {
        List<CreditScheduleDTO> response = new ArrayList<>();
        for (int i = 1; i <= period; i++) {
            CreditScheduleDTO dto = new CreditScheduleDTO();
            dto.setIndex(i);
            response.add(dto);
        }
        return response;
    }

    public static List<CreditScheduleDTO> addRentalWithVAT(String type, List<CreditScheduleDTO> response, double rentalWithVAT, double[] rentals) {
        if (type.equalsIgnoreCase(AppsConstants.CalculatorType.NORMAL.getType())) {
            response = response.stream().map(i -> {
                i.setRentalWithVat(String.valueOf(rentalWithVAT));
                return i;
            }).collect(Collectors.toList());
        } else if (type.equalsIgnoreCase(AppsConstants.CalculatorType.STRUCTURED.getType())) {
            response = response.stream().map(i -> {
                i.setRentalWithVat(String.valueOf(rentals[i.getIndex() - 1]));
                return i;
            }).collect(Collectors.toList());
        }
        return response;
    }

    public static List<CreditScheduleDTO> addRentalWithoutVAT(String type, List<CreditScheduleDTO> response, double rentalWithoutVAT) {
        if (type.equalsIgnoreCase(AppsConstants.CalculatorType.NORMAL.getType())) {
            response = response.stream().map(i -> {
                i.setRentalWithoutVat(String.valueOf(rentalWithoutVAT));
                return i;
            }).collect(Collectors.toList());
        } else if (type.equalsIgnoreCase(AppsConstants.CalculatorType.STRUCTURED.getType())) {
            response = response.stream().map(i -> {
                double rental = Double.parseDouble(i.getInterest()) + Double.parseDouble(i.getCapital());
                i.setRentalWithoutVat(String.valueOf(rental));
                return i;
            }).collect(Collectors.toList());
        }
        return response;
    }

    public static List<CreditScheduleDTO> addGrossRentalOutstanding(List<CreditScheduleDTO> response, double grossAmount) {
        double temp = 0;
        for (int i = 0; i < response.size(); i++) {
            double value = 0;
            if (response.get(i).getIndex() == 1) {
                value = grossAmount - Double.parseDouble(response.get(i).getRentalWithVat());
            } else {
                value = temp - Double.parseDouble(response.get(i).getRentalWithVat());
            }
            temp = value;
            response.get(i).setGrossRentalOutstanding(String.valueOf(value));
        }
        return response;
    }

    public static List<CreditScheduleDTO> addSanctionOutstanding(List<CreditScheduleDTO> response, double sanctionAmount) {
        double temp = 0;
        for (int i = 0; i < response.size(); i++) {
            double value = 0;
            if (response.get(i).getIndex() == 1) {
                value = sanctionAmount - Double.parseDouble(response.get(i).getRentalWithoutVat());
            } else {
                value = temp - Double.parseDouble(response.get(i).getRentalWithoutVat());
            }
            temp = value;
            response.get(i).setSanctionOutstanding(String.valueOf(value));
        }
        return response;
    }

    public static List<CreditScheduleDTO> addVat(List<CreditScheduleDTO> response, double vatOnRental) {
        for (int i = 0; i < response.size(); i++) {
            double value = 0;
            if (response.get(i).getIndex() == 1) {
                try {
                    value = calculateCustomFormula("ROUND(" + response.get(i).getRentalWithVat() + "/(1+" + vatOnRental + ")*" + vatOnRental + ",2)");
                } catch (IOException e) {
                    LOG.error("ERROR : ", e);
                }
            } else {
                try {
                    value = calculateCustomFormula("(" + response.get(i).getRentalWithVat() + "/(1+" + vatOnRental + ")*" + vatOnRental + ")");
                } catch (IOException e) {
                    LOG.error("ERROR : ", e);
                }
            }
            response.get(i).setVat(String.valueOf(value));
        }
        return response;
    }

    public static List<CreditScheduleDTO> addInterestAndCapitalAndCapitalOutstanding(String type, List<CreditScheduleDTO> response, double leaseAmount, int period, double leaseCapital, double rentalWithoutVAT, double effectiveRate) {
        if (type.equalsIgnoreCase(AppsConstants.CalculatorType.NORMAL.getType())) {
            double tempOutstanding = 0;
            for (int i = 0; i < response.size(); i++) {
                double interest = 0;
                double capital = 0;
                double outstanding = 0;
                if (response.get(i).getIndex() == 1) {
                    try {
                        interest = calculateCustomFormula("ROUND(" + leaseAmount + "*(" + getRateValue(rentalWithoutVAT, leaseCapital, period, effectiveRate) + "/100)/12,2)");
                        capital = calculateCustomFormula("ROUND(" + response.get(i).getRentalWithVat() + "-" + interest + "-" + response.get(i).getVat() + ",2)");
                        outstanding = calculateCustomFormula("ROUND(" + leaseCapital + "-" + capital + ",2)");
                    } catch (IOException e) {
                        LOG.error("ERROR : ", e);
                    }
                } else {
                    try {
                        if (response.get(i).getIndex() == period) {
                            capital = tempOutstanding;
                            interest = Double.valueOf(response.get(i).getRentalWithoutVat()) - capital;
                            outstanding = calculateCustomFormula("ROUND(" + tempOutstanding + "-" + capital + ",2)");
                        } else {
                            interest = calculateCustomFormula("ROUND(" + tempOutstanding + "*(" + getRateValue(rentalWithoutVAT, leaseCapital, period, effectiveRate) + "/100)/12,2)");
                            capital = calculateCustomFormula("ROUND(" + response.get(i).getRentalWithVat() + "-" + interest + "-" + response.get(i).getVat() + ",2)");
                            outstanding = calculateCustomFormula("ROUND(" + tempOutstanding + "-" + capital + ",2)");
                        }
                    } catch (IOException e) {
                        LOG.error("ERROR : ", e);
                    }
                }
                tempOutstanding = outstanding;
                response.get(i).setInterest(String.valueOf(interest));
                response.get(i).setCapital(String.valueOf(capital));
                response.get(i).setCapitalOutstanding(String.valueOf(outstanding));
            }
        } else if (type.equalsIgnoreCase(AppsConstants.CalculatorType.STRUCTURED.getType())) {
            double tempOutstanding = 0;
            for (int i = 0; i < response.size(); i++) {
                double interest = 0;
                double capital = 0;
                double outstanding = 0;
                if (response.get(i).getIndex() == 1) {
                    interest = 0;
                    capital = Double.valueOf(response.get(i).getRentalWithVat()) - Double.valueOf(interest) - Double.valueOf(response.get(i).getVat());
                    outstanding = Double.valueOf(leaseCapital) - capital;
                } else {
                    try {
                        interest = calculateCustomFormula("ROUND((" + tempOutstanding + "*(" + effectiveRate + "/100)/12),2)");
                        capital = Double.valueOf(response.get(i).getRentalWithVat()) - Double.valueOf(interest) - Double.valueOf(response.get(i).getVat());
                        outstanding = tempOutstanding - capital;
                    } catch (IOException e) {
                        LOG.error("ERROR : ", e);
                    }
                }
                tempOutstanding = outstanding;
                response.get(i).setInterest(String.valueOf(interest));
                response.get(i).setCapital(String.valueOf(capital));
                response.get(i).setCapitalOutstanding(String.valueOf(outstanding));
            }
        }
        return response;
    }

    public static double[] expandRentalData(List<FacilityRentalInformationDTO> rentalData, int period) {
        double[] rentals = new double[period];
        for (int i = 0; i < period; ) {
            for (int j = 0; j < rentalData.size(); j++) {
                for (int k = 0; k < rentalData.get(j).getNoOfRentals(); k++) {
                    rentals[i] = rentalData.get(j).getRentalAmount();
                    i++;
                }
            }
        }
        return rentals;
    }

    public static List<StipulatedLossValueDTO> calculateStipulatedLossValueData(String facilityType, String calculatorType, List<CalculationDTO> systemOutputResponse, List<FacilityRentalInformationDTO> facilityRentalData) {
        List<StipulatedLossValueDTO> stipulatedLossValueResponseData = new ArrayList<>();

        double period = 0;
        double leaseCapital = 0;
        double reducingRate = 0;
        double rentalWithoutVAT = 0;
        double[] rentals = null;

        if (facilityType.equals(AppsConstants.FacilityType.LEASE.getType())) {
            for (CalculationDTO systemOutput : systemOutputResponse) {
                if (systemOutput.getOutputCode().equals("OUT002")) {
                    period = Double.parseDouble(systemOutput.getValue());
                }

                if (systemOutput.getOutputCode().equals("OUT006")) {
                    leaseCapital = Double.parseDouble(systemOutput.getValue());
                }

                if (systemOutput.getOutputCode().equals("OUT007")) {
                    reducingRate = Double.parseDouble(systemOutput.getValue());
                }

                if (calculatorType.equals(AppsConstants.CalculatorType.NORMAL.getType())) {
                    if (systemOutput.getOutputCode().equals("OUT011")) {
                        rentalWithoutVAT = Double.parseDouble(systemOutput.getValue());
                    }
                } else if (calculatorType.equals(AppsConstants.CalculatorType.STRUCTURED.getType())) {
                    rentals = expandRentalData(facilityRentalData, (int) Math.round(period));
                }
            }

            stipulatedLossValueResponseData = addTerms((int) Math.round(period));
            stipulatedLossValueResponseData = addStipulatedLossValueAndInterestAndCapital(calculatorType, stipulatedLossValueResponseData, leaseCapital, reducingRate, rentalWithoutVAT, rentals);
        }
        return stipulatedLossValueResponseData;
    }

    public static List<StipulatedLossValueDTO> addTerms(int period) {
        List<StipulatedLossValueDTO> response = new ArrayList<>();

        List<Integer> coloredTerms = new ArrayList<>();
        for (int a = 1; a <= period; a++) {
            int val = 1 + (a - 1) * 6;
            coloredTerms.add(val);
        }

        for (int i = 1; i <= period; i++) {
            StipulatedLossValueDTO dto = new StipulatedLossValueDTO();
            dto.setTerm(i);
            dto.setColored(false);
            for (Integer term : coloredTerms) {
                if (term == i) {
                    dto.setColored(true);
                    break;
                }
            }
            response.add(dto);
        }
        return response;
    }

    public static List<StipulatedLossValueDTO> addStipulatedLossValueAndInterestAndCapital(String calculatorType, List<StipulatedLossValueDTO> response, double leaseCapital, double reducingRate, double rentalWithoutVAT, double[] rentals) {
        double tempSLV = 0;
        double tempCapital = 0;
        for (int i = 0; i < response.size(); i++) {
            double slv;
            if (response.get(i).getTerm() == 1) {
                slv = leaseCapital * 1.1;
                tempSLV = slv;
            } else {
                slv = tempSLV - tempCapital;
                tempSLV = slv;
            }
            double interest = (slv * (reducingRate / 100)) / 12;
            double capital = 0;

            if (calculatorType.equals(AppsConstants.CalculatorType.NORMAL.getType())) {
                capital = rentalWithoutVAT - interest;
                tempCapital = capital;
            } else if (calculatorType.equals(AppsConstants.CalculatorType.STRUCTURED.getType())) {
                capital = rentals[i] - interest;
                tempCapital = capital;
            }

            response.get(i).setStipulatedLossValue(slv);
            response.get(i).setInterest(interest);
            response.get(i).setCapital(capital);
        }
        return response;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 36; i++) {
            int val = 1 + (i - 1) * 6;
            System.out.println(val);
        }
    }

    public List<Formula> parseXml(Environment environment, String facilityType, String calculatorType) throws IOException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        FormulaUtil formulaUtil = new FormulaUtil();

        String xmlPath = environment.getProperty("apps.print.html.template.path") + File.separator + "xml" + File.separator + "formula" + ".xml";
        File file = new File(xmlPath);
        saxParser.parse(file, formulaUtil);

        //List<Formula> formulaList = FormulaUtil.getFormulaList().stream().filter(f -> f.getFacilityType().equals(facilityType) && f.getCalculatorType().equals(calculatorType)).collect(Collectors.toList());
        List<Formula> safeList = new ArrayList<>(FormulaUtil.getFormulaList());List<Formula> formulaList = safeList.stream()
                .filter(f -> f.getFacilityType().equals(facilityType) && f.getCalculatorType().equals(calculatorType))
                .collect(Collectors.toList());
        for (Formula formula : formulaList) {
            LOG.info("INFO : Formulas : {} ", formula.toString());
        }
        return formulaList;
    }
}
