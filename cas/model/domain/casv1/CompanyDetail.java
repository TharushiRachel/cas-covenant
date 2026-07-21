package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "CASV1_COMPANY_DETAILS_TABLE")
public class CompanyDetail {

    @Column(name = "REF_NUM")
    private String refNo;

    @Column(name = "CONSTITUTION_OF_BUSSINESS")
    private String constitutionOfBusiness;

    @Column(name = "YEAR_OF_BUSSINES_COMMENCE")
    private Integer yearOfBusinessCommence;
}
