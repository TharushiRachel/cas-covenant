package com.itechro.cas.dao.facilitypaper.committee;

import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInqReqRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitteeInqReqResDao extends JpaRepository<CommitteeInqReqRes, Integer> {

    List<CommitteeInqReqRes> findByCommitteeInquiryMaster_CommitteeInquiryId(Integer committeeInquiryId);
}
