package com.itechro.cas.model.dto.covenants;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CovenantLevelDTO {

    @JsonProperty("customerLevel")
    private List<CustomerCovenantDetailsDTO> customerLevel;

    @JsonProperty("accountLevel")
    private List<CustomerCovenantDetailsDTO> accountLevel;

    @JsonProperty("student")
    private List<CustomerCovenantDetailsDTO> student;

    @JsonProperty("greenEnergy")
    private List<CustomerCovenantDetailsDTO> greenEnergy;

    @JsonProperty("housing")
    private List<CustomerCovenantDetailsDTO> housing;

    @JsonProperty("insurance")
    private List<CustomerCovenantDetailsDTO> insurance;

    @JsonProperty("leasing")
    private List<CustomerCovenantDetailsDTO> leasing;

    @JsonProperty("medical")
    private List<CustomerCovenantDetailsDTO> medical;

    @JsonProperty("professional")
    private List<CustomerCovenantDetailsDTO> professional;

    @JsonProperty("rstRsh")
    private List<CustomerCovenantDetailsDTO> rstRsh;

    @JsonProperty("samachara")
    private List<CustomerCovenantDetailsDTO> samachara;

    @JsonProperty("stocks")
    private List<CustomerCovenantDetailsDTO> stocks;

    @JsonProperty("vehicle")
    private List<CustomerCovenantDetailsDTO> vehicle;

    @JsonProperty("pledge")
    private List<CustomerCovenantDetailsDTO> pledge;
}
