package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.CustomerFP;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerFPDTO {

    private String refNo;

    private String customerName;

    private String busProf;

    private String accNo;

    private Date accOpDate;

    private Date appDate;

    private String newAp;

    private String additional;

    private String renewal;

    private String termsAmended;

    private String connectionOwner;

    private String riskGrading;

    private String shareHolding;

    private String branch;

    public CustomerFPDTO(){
    }

    public CustomerFPDTO (CustomerFP customerFPDTO){

        this.refNo = customerFPDTO.getRefNo();
        this.customerName = customerFPDTO.getCustomerName();
        this.busProf = customerFPDTO.getBusProf();
        this.accNo = customerFPDTO.getAccNo();
        this.accOpDate = customerFPDTO.getAccOpDate();
        this.appDate = customerFPDTO.getAppDate();
        this.newAp = customerFPDTO.getNewAp();
        this.additional = customerFPDTO.getAdditional();
        this.renewal = customerFPDTO.getRenewal();
        this.termsAmended = customerFPDTO.getTermsAmended();
        this.connectionOwner = customerFPDTO.getConnectionOwner();
        this.riskGrading = customerFPDTO.getRiskGrading();
        this.shareHolding = customerFPDTO.getShareHolding();
        this.branch = customerFPDTO.getBranch();
    }
}
