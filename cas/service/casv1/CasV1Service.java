package com.itechro.cas.service.casv1;

import com.itechro.cas.controller.casv1.CasV1FacilityPaperController;
import com.itechro.cas.dao.casv1.CasV1CustomerDao;
import com.itechro.cas.dao.casv1.jdbc.CasV1FacilityPaperJDBC;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.casv1.CustomerDetail;
import com.itechro.cas.model.dto.casv1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CasV1Service implements EnvironmentAware {

    private Environment environment;

    @Autowired
    CasV1CustomerDao casV1CustomerDao;

    @Autowired
    CasV1FacilityPaperJDBC casV1FacilityPaperJDBC;

    private static final Logger LOG = LoggerFactory.getLogger(CasV1FacilityPaperController.class);

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<CustomerDetailDTO> getCustomersDetailByAcc(String accNo) throws AppsException{
        List<CustomerDetailDTO> customerDetailDTOList = new ArrayList<>();
        List<CustomerDetail> customerDetailList = casV1CustomerDao.findAllByAccNo(accNo);

        for (CustomerDetail customerDetail : customerDetailList) {
            if (customerDetail != null) {

                CustomerDetailDTO  customerDetailDTO = new CustomerDetailDTO(customerDetail);
                String refNo = customerDetailDTO.getRefNo();

                List<DirectorDTO> directors = casV1FacilityPaperJDBC.getCustomerDirectors(refNo);
                if (directors != null && !directors.isEmpty()) {
                    customerDetailDTO.setDirectors(directors);
                }

                List<CompanyDetailDTO> companies = casV1FacilityPaperJDBC.getCustomerCompanies(refNo);
                if (companies != null && !companies.isEmpty()) {
                    customerDetailDTO.setCompanies(companies);
                }

                List<FacilityStatusDTO> facilityPapers = casV1FacilityPaperJDBC.getCustomerFacilityPapers(refNo);
                if (facilityPapers != null && !facilityPapers.isEmpty()) {
                    customerDetailDTO.setFacilityPapers(facilityPapers);
                }

                List<OtherFacilityDTO> otherFacilities = casV1FacilityPaperJDBC.getCustomerOtherFacilities(refNo);
                if (otherFacilities != null && !otherFacilities.isEmpty()) {
                    customerDetailDTO.setOtherFacilities(otherFacilities);
                }

                if (customerDetail.getCribFavourablity() != null && !customerDetail.getCribFavourablity().isEmpty()) {
                    String cribDescription = casV1FacilityPaperJDBC.getCommonCodeDescription(10, Integer.valueOf(customerDetail.getCribFavourablity()));
                    customerDetailDTO.setCribDescription(cribDescription);
                }

                if (customerDetail.getFinancialAudit() != null && !customerDetail.getFinancialAudit().isEmpty()) {
                    String financialDescription = casV1FacilityPaperJDBC.getCommonCodeDescription(11, Integer.valueOf(customerDetail.getFinancialAudit()));
                    customerDetailDTO.setFinancialDescription(financialDescription);
                }

                customerDetailDTOList.add(customerDetailDTO);
            }
        }

        LOG.info("START : Get customer data : {}, user {}", customerDetailDTOList);

        return customerDetailDTOList;
    }


    public FacilityPaperDataDTO getFacilityPaperDetails(FPRequestDTO fpRequestDTO) throws AppsException{
        FacilityPaperDataDTO facilityPaperDataDTO = new FacilityPaperDataDTO();

        List<FacilityTotalDTO> facilities = casV1FacilityPaperJDBC.getFPFacilities(fpRequestDTO);
        if (facilities != null && !facilities.isEmpty()) {
            facilityPaperDataDTO.setFacilities(facilities);

            List<String> facilityTexts = casV1FacilityPaperJDBC.getFPFacilityTexts(fpRequestDTO);
            if (facilityTexts != null && !facilityTexts.isEmpty()) {
                facilityPaperDataDTO.setFacilityTexts(facilityTexts);
            }

            List<String> securities = casV1FacilityPaperJDBC.getFPSecurityTexts(fpRequestDTO);
            if (securities != null && !securities.isEmpty()) {
                facilityPaperDataDTO.setSecurityTexts(securities);
            }
        }else {
            throw new AppsException("Facility paper data not found.");
        }

        return facilityPaperDataDTO;
    }


    public AttachmentDetailDTO getAttachmentDetailsByRef(String refNo){
        AttachmentDetailDTO attachmentDetailDTO = null;

        List<AttachmentDetailDTO> attachmentDetails = casV1FacilityPaperJDBC.getAttachmentDetails(refNo);
        if(attachmentDetails != null && !attachmentDetails.isEmpty()){
            attachmentDetailDTO = attachmentDetails.get(0);
        }

        return attachmentDetailDTO;
    }

    public List<SectionDetailDTO> getDropdownSections(String upcFormatNum) {
        return casV1FacilityPaperJDBC.getDropdownSections(upcFormatNum);
    }

    public List<AttachmentDetailDTO> getAttachments(String refNo, String paperDate, String upcFormat) {
        return casV1FacilityPaperJDBC.getAttachments(refNo, paperDate, upcFormat);
    }

    public List<CommentTableDTO> getComments(String refNo, String paperDate) {
        return casV1FacilityPaperJDBC.getComments(refNo, paperDate);
    }
}
