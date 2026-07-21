package com.itechro.cas.model.dto.das;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeDADisplayOrderRQ {
    private List<DADisplayOrderDTO> daDisplayOrderDTOList;

}
