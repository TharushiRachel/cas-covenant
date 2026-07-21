package com.itechro.cas.model.dto.kalyptosystem.request;

import com.itechro.cas.model.dto.kalyptosystem.KalyptoValuesWithNameDTO;
import com.itechro.cas.model.dto.kalyptosystem.kalyptoPeriodValues;
import lombok.Data;

import java.util.List;

@Data
public class KalyptoValuesRQ {

    private String columnName;

    private String customerId;

    private String facilityId;

    private Boolean isEdited;

    private String periodId;

    private List<KalyptoValuesWithNameDTO> kalyptoValues;

    private List<kalyptoPeriodValues> kalyptoPeriodValues;

    private List<KalyptoDataSaveRQ> kalyptoAddedValues;

    private Boolean isOpenInput;

    private String kalyptoId;
}
