package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerRelationshipResponse;

import java.io.Serializable;

public class ConsumerRelationshipResponseDTO implements Serializable {

    private RelationshipDetailsDTO relationshipDetailsDTO;

    public ConsumerRelationshipResponseDTO() {
    }

    public ConsumerRelationshipResponseDTO(ConsumerRelationshipResponse consumerRelationshipResponse) {
        if (consumerRelationshipResponse != null) {
            this.relationshipDetailsDTO = new RelationshipDetailsDTO(consumerRelationshipResponse.getRelationshipDetails());
        }
    }

    public RelationshipDetailsDTO getRelationshipDetailsDTO() {
        return relationshipDetailsDTO;
    }

    public void setRelationshipDetailsDTO(RelationshipDetailsDTO relationshipDetailsDTO) {
        this.relationshipDetailsDTO = relationshipDetailsDTO;
    }
}
