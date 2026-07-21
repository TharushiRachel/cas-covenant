package com.itechro.cas.model.dto.das;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FullDATable {

    private List<DATableDTO> daTableDTO;

//    public FullDATable(List<DATableDTO> daTableDTO) {
//        this.daTableDTO = daTableDTO;
//    }

    public FullDATable() {}
}
