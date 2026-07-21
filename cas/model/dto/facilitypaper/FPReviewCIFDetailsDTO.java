package com.itechro.cas.model.dto.facilitypaper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FPReviewCIFDetailsDTO  implements Serializable, Comparable<FPReviewCIFDetailsDTO> {

    private Integer customerId;

    private Integer customerFinancialId;

    public int compareTo(FPReviewCIFDetailsDTO other) {
        // Compare instances based on the customerId field
        return this.customerId.compareTo(other.customerId);
    }
}
