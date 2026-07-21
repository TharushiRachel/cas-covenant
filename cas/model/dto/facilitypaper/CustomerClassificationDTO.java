package com.itechro.cas.model.dto.facilitypaper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerClassificationDTO {

    private Long id;
    private String description;
    private List<CustomerClassificationDTO> children = new ArrayList<>();
    private String selected;
    private String comment;

    public CustomerClassificationDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }

}
