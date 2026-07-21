package com.itechro.cas.util;

import com.itechro.cas.model.dto.facilitypaper.creditcalculator.Formula;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class FormulaUtil extends DefaultHandler {
    private static final String FORMULA = "formula";
    private static final String FACILITY_TYPE = "facilityType";
    private static final String CALCULATOR_TYPE = "calculatorType";
    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String VALUE = "value";
    private static final String OUTPUT = "output";
    private static final String OUTPUT_CODE = "outputCode";
    private static final String OUTPUT_NAME = "outputName";
    private static final String OUTPUT_ORDER = "outputOrder";
    private static final String PERCENTAGE = "percentage";
    private static final String CURRENCY = "currency";
    private static List<Formula> formulaList = null;
    boolean bCode = false;
    boolean bFacilityType = false;
    boolean bCalculatorType = false;
    boolean bName = false;
    boolean bType = false;
    boolean bValue = false;
    boolean bOutput = false;
    boolean bOutputCode = false;
    boolean bOutputName = false;
    boolean bOutputOrder = false;
    boolean bPercentage = false;
    boolean bCurrency = false;
    private StringBuilder data = null;
    private Formula formula = null;

    public FormulaUtil() {
        formulaList = null;
    }

    public static List<Formula> getFormulaList() {
        return formulaList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(FORMULA)) {
            formula = new Formula();
            if (formulaList == null) {
                formulaList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase(FACILITY_TYPE)) {
            bFacilityType = true;
        } else if (qName.equalsIgnoreCase(CALCULATOR_TYPE)) {
            bCalculatorType = true;
        } else if (qName.equalsIgnoreCase(CODE)) {
            bCode = true;
        } else if (qName.equalsIgnoreCase(NAME)) {
            bName = true;
        } else if (qName.equalsIgnoreCase(TYPE)) {
            bType = true;
        } else if (qName.equalsIgnoreCase(VALUE)) {
            bValue = true;
        } else if (qName.equalsIgnoreCase(OUTPUT)) {
            bOutput = true;
        } else if (qName.equalsIgnoreCase(OUTPUT_CODE)) {
            bOutputCode = true;
        } else if (qName.equalsIgnoreCase(OUTPUT_NAME)) {
            bOutputName = true;
        } else if (qName.equalsIgnoreCase(OUTPUT_ORDER)) {
            bOutputOrder = true;
        } else if (qName.equalsIgnoreCase(PERCENTAGE)) {
            bPercentage = true;
        } else if (qName.equalsIgnoreCase(CURRENCY)) {
            bCurrency = true;
        }
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bCode) {
            formula.setCode(data.toString());
            bCode = false;
        } else if (bFacilityType) {
            formula.setFacilityType(data.toString());
            bFacilityType = false;
        } else if (bCalculatorType) {
            formula.setCalculatorType(data.toString());
            bCalculatorType = false;
        } else if (bName) {
            formula.setName(data.toString());
            bName = false;
        } else if (bType) {
            formula.setType(data.toString());
            bType = false;
        } else if (bValue) {
            formula.setValue(data.toString());
            bValue = false;
        } else if (bOutput) {
            formula.setOutput(Boolean.valueOf(data.toString()));
            bOutput = false;
        } else if (bOutputCode) {
            formula.setOutputCode(data.toString());
            bOutputCode = false;
        } else if (bOutputName) {
            formula.setOutputName(data.toString());
            bOutputName = false;
        } else if (bOutputOrder) {
            formula.setOutputOrder(Integer.parseInt(data.toString()));
            bOutputOrder = false;
        } else if (bPercentage) {
            formula.setPercentage(Boolean.valueOf(data.toString()));
            bPercentage = false;
        } else if (bCurrency) {
            formula.setCurrency(Boolean.valueOf(data.toString()));
            bCurrency = false;
        }

        if (qName.equalsIgnoreCase(FORMULA)) {
            formulaList.add(formula);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
