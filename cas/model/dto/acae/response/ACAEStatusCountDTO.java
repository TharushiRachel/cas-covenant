package com.itechro.cas.model.dto.acae.response;
import lombok.Data;
import java.io.Serializable;

@Data
public class ACAEStatusCountDTO implements Serializable {

    Integer newACAECount;

    Integer draftCount;

    Integer forwardToMeCount;

    Integer returnToMeCount;

    Integer forwardCount;

    Integer returnCount;

    Integer toBeResubmitCount;

    Integer resubmitToMeCount;

    Integer approvedCount;

    Integer transferToMeCount;

}
