package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.RelationshipDetails;

import java.io.Serializable;

public class RelationshipDetailsDTO implements Serializable {

    private String relationRUIO;

    private String sno;

    private String idValue;

    private String name;

    private String nature;

    private String blockFlag;

    public RelationshipDetailsDTO() {
    }

    public RelationshipDetailsDTO(RelationshipDetails relationshipDetails) {
        if (relationshipDetails != null) {
            this.relationRUIO = relationshipDetails.getRelationRUIO();
            this.sno = relationshipDetails.getSno();
            this.idValue = relationshipDetails.getIdValue();
            this.name = relationshipDetails.getName();
            this.nature = relationshipDetails.getNature();
            this.blockFlag = relationshipDetails.getBlockFlag();
        }
    }

    public String getRelationRUIO() {
        return relationRUIO;
    }

    public void setRelationRUIO(String relationRUIO) {
        this.relationRUIO = relationRUIO;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }
}
