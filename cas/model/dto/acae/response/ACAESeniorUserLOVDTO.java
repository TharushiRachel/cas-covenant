package com.itechro.cas.model.dto.acae.response;
import lombok.Data;
import java.io.Serializable;

@Data
public class ACAESeniorUserLOVDTO  implements Serializable {
    Integer userId;
    String firstName;
    String lastName;
}
