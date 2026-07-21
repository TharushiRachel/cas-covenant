package com.itechro.cas.model.dto.acae.response;

import lombok.Data;
import java.util.List;
@Data
public class ACAERecordsForTransferDTO {

    String userId;
    String firstName;
    String lastName;
    String userNo;
    Boolean isSelectEnable;
    List<ACAETransferRefRecordDTO> refRecords;

}
