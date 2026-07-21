package com.itechro.cas.model.dto.das;

import lombok.Data;

import java.util.List;

@Data
public class DADesignationListDTO {
    List<DADesignationCodeDTO> committeeDesignations;

    List<DADesignationCodeDTO> individualDesignations;
}
