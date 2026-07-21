package com.itechro.cas.model.dto.email;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InquiryEmailDTO implements Serializable {

    private Integer facilityPaperId;

    private String comment;

    private String commentedBy;

    private String assignTo;

    private AppsConstants.InquiryType commentType;

    private String committeeName;

    public String getCommitteeName() {
        if (this.committeeName == null){
            this.committeeName = "Committee";
        }
        return committeeName;
    }
}
