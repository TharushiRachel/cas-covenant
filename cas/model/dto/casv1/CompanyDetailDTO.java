package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.CompanyDetail;
import lombok.Data;

@Data
public class CompanyDetailDTO {

    private String refNo;

    private String constitutionOfBusiness;

    private Integer yearOfBusinessCommence;

    public CompanyDetailDTO(){
    }

    public CompanyDetailDTO (CompanyDetail companyDetailDTO){

        this.refNo = companyDetailDTO.getRefNo();
        this.constitutionOfBusiness = companyDetailDTO.getConstitutionOfBusiness();
        this.yearOfBusinessCommence = companyDetailDTO.getYearOfBusinessCommence();
    }

}
