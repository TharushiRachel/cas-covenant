package com.itechro.cas.model.dto.integration.response.CustomerStatistics;

import java.io.Serializable;
import java.util.*;

public class ExecuteFinacleScriptCustomDataResponseDTO implements Serializable {

    private List<ExecuteFinacleScriptCustomDataDTO> executeFinacleScriptCustomDataDTOS;

    private List<ExecuteFinacleScriptCustomDataTreemapDTO> executeFinacleScriptCustomDataTreemapDTO;
    private boolean success;

    public ExecuteFinacleScriptCustomDataResponseDTO() {
    }

    public ExecuteFinacleScriptCustomDataResponseDTO(ExecuteFinacleScriptCustomDataResponse executeFinacleScriptCustomDataResponse) {
        TreeMap<String, List<ExecuteFinacleScriptCustomDataDTO>> outerMap = new TreeMap<>();

        for (AccTrainData accTrainData : executeFinacleScriptCustomDataResponse.getExecuteFinacleScriptCustomData().getAccTrain()) {
            ExecuteFinacleScriptCustomDataDTO executeFinacleScriptCustomDataDTO = new ExecuteFinacleScriptCustomDataDTO(accTrainData);

            executeFinacleScriptCustomDataDTO.setCrTotal(calculateCrTotal(accTrainData));
            executeFinacleScriptCustomDataDTO.setDrTotal(calculateDrTotal(accTrainData));

            getExecuteFinacleScriptCustomDataDTOS().add(executeFinacleScriptCustomDataDTO);

            List<ExecuteFinacleScriptCustomDataDTO> existingList = outerMap.get(accTrainData.getAccNum());

            if (existingList != null) {
                existingList.add(executeFinacleScriptCustomDataDTO);
            } else {
                existingList = new ArrayList<>();
                existingList.add(executeFinacleScriptCustomDataDTO);
            }

            outerMap.put(accTrainData.getAccNum(), existingList);

        }

        Set<String> keySet = outerMap.keySet();

        for (String x : keySet) {

            ExecuteFinacleScriptCustomDataTreemapDTO executeFinacleScriptCustomDataTreemapDTO = new ExecuteFinacleScriptCustomDataTreemapDTO();
            executeFinacleScriptCustomDataTreemapDTO.setAccNum(x);
            executeFinacleScriptCustomDataTreemapDTO.setAccCrncy(outerMap.get(x).get(0).getAccCrncy());
            executeFinacleScriptCustomDataTreemapDTO.setTranData(outerMap.get(x));

            getExecuteFinacleScriptCustomDataTreemapDTO().add(executeFinacleScriptCustomDataTreemapDTO);

        }
    }

    public List<ExecuteFinacleScriptCustomDataTreemapDTO> getExecuteFinacleScriptCustomDataTreemapDTO() {
        if (executeFinacleScriptCustomDataTreemapDTO == null) {
            this.executeFinacleScriptCustomDataTreemapDTO = new ArrayList<>();
        }
        return executeFinacleScriptCustomDataTreemapDTO;
    }

    public String calculateCrTotal(AccTrainData accTrainData) {

        double sum = 0;
        try {
             sum = Double.parseDouble(accTrainData.getCrCash())+
                    Double.parseDouble(accTrainData.getCrInstrument()) +
                    Double.parseDouble(accTrainData.getCrCard()) +
                    Double.parseDouble(accTrainData.getCrFc()) +
                    Double.parseDouble(accTrainData.getCrExport()) +
                    Double.parseDouble(accTrainData.getCrLoan()) +
                    Double.parseDouble(accTrainData.getCrError()) +
                    Double.parseDouble(accTrainData.getCrSweeps()) +
                    Double.parseDouble(accTrainData.getCrChgAndInt()) +
                    Double.parseDouble(accTrainData.getCrOther());
        } catch (NumberFormatException e) {
            System.err.println("Invalid numeric value for Account: " + accTrainData.getAccNum());
            System.err.println(e);
        }

        return String.valueOf(sum);
    }

    public String calculateDrTotal(AccTrainData accTrainData) {

        double sum = 0;
        try {
            sum = Double.parseDouble(accTrainData.getDrCash()) +
                    Double.parseDouble(accTrainData.getDrInstrument()) +
                    Double.parseDouble(accTrainData.getDrCard()) +
                    Double.parseDouble(accTrainData.getDrFc()) +
                    Double.parseDouble(accTrainData.getDrExport()) +
                    Double.parseDouble(accTrainData.getDrLoan()) +
                    Double.parseDouble(accTrainData.getDrError()) +
                    Double.parseDouble(accTrainData.getDrSweeps()) +
                    Double.parseDouble(accTrainData.getDrChgAndInt()) +
                    Double.parseDouble(accTrainData.getDrOther());
        } catch (NumberFormatException e) {
            System.err.println("Invalid numeric value for Account: " + accTrainData.getAccNum());
            System.err.println(e);
        }


        return String.valueOf(sum);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ExecuteFinacleScriptCustomDataDTO> getExecuteFinacleScriptCustomDataDTOS() {
        if (executeFinacleScriptCustomDataDTOS == null) {
            this.executeFinacleScriptCustomDataDTOS = new ArrayList<>();
        }
        return executeFinacleScriptCustomDataDTOS;
    }

    @Override
    public String toString() {
        return "ExecuteFinacleScriptCustomDataResponseDTO{" +
                "executeFinacleScriptCustomDataDTOS=" + executeFinacleScriptCustomDataDTOS +
                ", executeFinacleScriptCustomDataTreemapDTO=" + executeFinacleScriptCustomDataTreemapDTO +
                ", success=" + success +
                '}';
    }
}
