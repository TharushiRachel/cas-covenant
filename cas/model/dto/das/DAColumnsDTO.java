package com.itechro.cas.model.dto.das;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DAColumnsDTO {
    private Integer columnId;

    private Double columnValue;

    public DAColumnsDTO(Integer columnId, Double columnValue) {
        this.columnId = columnId;
        this.columnValue = columnValue;
    }
}
