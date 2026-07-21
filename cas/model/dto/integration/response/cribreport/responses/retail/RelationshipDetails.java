package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipDetails implements Serializable {

    @JsonProperty("RELATION_RUID")
    private String relationRUIO;

    @JsonProperty("SNO")
    private String sno;

    @JsonProperty("ID_VALUE")
    private String idValue;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("NATURE")
    private String nature;

    @JsonProperty("BLOCK_FLAG")
    private String blockFlag;

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
