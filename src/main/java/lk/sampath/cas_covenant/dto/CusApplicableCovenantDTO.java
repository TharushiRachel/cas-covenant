package lk.sampath.cas_covenant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lk.sampath.cas_covenant.enums.CovenantApplicableType;
import lk.sampath.cas_covenant.enums.CovenantStatusOnDisbursement;
import lombok.Data;

import java.util.Date;

@Data
public class CusApplicableCovenantDTO {

    @JsonProperty("covenant_Code")
    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    private CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    private CovenantApplicableType applicableType;
}
