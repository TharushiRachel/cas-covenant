package com.itechro.cas.dao.lead;

import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.dto.lead.LeadIdentificationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LeadDao extends JpaRepository<Lead, Integer> {

    public Lead getByLeadRefNumber(String leadRefNumber);

    public List<Lead> findAllByIdentificationNumberAndCreatedDateAfter(String identificationNumber, Date date);

    public Set<Lead> findAllByCustomerID(Integer customerID);

    @Query(
            value =
            "SELECT " +
            "  i.identification_number AS identificationNumber, " +
            "  i.identification_type   AS identificationType " +
            "FROM t_lead l " +
            "JOIN t_comp_lead c ON l.lead_id = c.lead_id " +
            "JOIN t_comp_parties p ON c.comp_lead_id = p.comp_lead_id " +
            "JOIN t_comp_party_identifications i ON p.comp_party_id = i.comp_party_id " +
            "WHERE l.lead_id = :leadId order by p.comp_party_id asc",
            nativeQuery = true
    )
    List<LeadIdentificationDTO> findIdentificationsByLeadId(@Param("leadId") Integer leadId);

    @Query(
            value =
                    "SELECT " +
                            "  i.identification_number AS identificationNumber, " +
                            "  i.identification_type   AS identificationType " +
                            "FROM t_comp_parties p " +
                            "JOIN t_comp_party_identifications i ON p.comp_party_id = i.comp_party_id " +
                            "WHERE p.comp_party_id = :partyId order by i.identification_id asc",
            nativeQuery = true
    )
    List<LeadIdentificationDTO> findIdentificationsByPartyId(@Param("partyId") Integer partyId);
}
