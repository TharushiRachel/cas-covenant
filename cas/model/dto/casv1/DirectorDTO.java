package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.Director;
import lombok.Data;

@Data
public class DirectorDTO {

    private String refNo;

    private String directorName;

    private Number age;

    public DirectorDTO(){
    }

    public DirectorDTO (Director directorDTO){
        this.refNo = directorDTO.getRefNo();
        this.directorName = directorDTO.getDirectorName();
        this.age = directorDTO.getAge();
    }
}
