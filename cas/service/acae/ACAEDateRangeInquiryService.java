package com.itechro.cas.service.acae;

import com.itechro.cas.dao.acae.jdbc.ACAEDateRangeInquiryJdbcDAO;
import com.itechro.cas.model.dto.acae.request.ACAEDateRangeInquiryRQ;
import com.itechro.cas.model.dto.acae.response.ACAESearchByStatusDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ACAEDateRangeInquiryService {

    private final ACAEDateRangeInquiryJdbcDAO acaeDateRangeInquiryJdbcDAO;

    @Autowired
    public ACAEDateRangeInquiryService(ACAEDateRangeInquiryJdbcDAO acaeDateRangeInquiryJdbcDAO) {
        this.acaeDateRangeInquiryJdbcDAO = acaeDateRangeInquiryJdbcDAO;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ACAEDateRangeInquiryService.class);
    public List<ACAESearchByStatusDTO> getACAEDateRangeInquiryService(ACAEDateRangeInquiryRQ acaeDateRangeInquiryRQ,
                                                                          CredentialsDTO credentialsDTO)  {
        LOG.info("START : ACAE Date Range Inquiry Service: {} , user {}", acaeDateRangeInquiryRQ, credentialsDTO);

        List<ACAESearchByStatusDTO> acaeDateRangeInquiryRQList;
        String solId = String.valueOf(acaeDateRangeInquiryRQ.getSolId());
        Date fromDate = acaeDateRangeInquiryRQ.getFromDate();
        Date toDate = acaeDateRangeInquiryRQ.getToDate();

        acaeDateRangeInquiryRQList = acaeDateRangeInquiryJdbcDAO.getACAEDateRangeInquiryRepository(solId,fromDate,toDate);

        LOG.info("END : ACAE Date Range Inquiry Service: {} , user {}", acaeDateRangeInquiryRQList, credentialsDTO);

        return acaeDateRangeInquiryRQList;
    }
}
