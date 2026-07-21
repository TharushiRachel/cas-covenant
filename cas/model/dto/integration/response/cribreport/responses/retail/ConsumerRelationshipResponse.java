package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerRelationshipResponse implements Serializable {

    @JsonProperty("RELATIONSHIP_DETAILS_VER4")
    private RelationshipDetails relationshipDetails;

    @JsonProperty("RELATIONSHIP_DETAILS_VER4")
    public RelationshipDetails getRelationshipDetails() {
        return relationshipDetails;
    }

    @JsonProperty("RELATIONSHIP_DETAILS_VER4")
    public void setRelationshipDetails(RelationshipDetails relationshipDetails) {
        this.relationshipDetails = relationshipDetails;
    }
}
