package com.itechro.cas.model.domain.finacle;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "EXPORT_TURNOVERS")
@ToString
public class ExportTurnovers implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_F_EXPORT_TURNOVERS")
    @SequenceGenerator(name = "SEQ_F_EXPORT_TURNOVERS", sequenceName = "SEQ_F_EXPORT_TURNOVERS", allocationSize = 1)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperId;

    @Column(name = "CUSTOMER_FINACLE_ID")
    private String customerFinacleId;

    @Column(name = "CURRENCY")
    private String curencyCode;

    @Column(name = "BILL_AMOUNT")
    private Double billAmount;

    @Column(name = "CONVERTED_BILL_AMOUNT")
    private Double convertedAmount;

    @Column(name = "ACCOUNT_NUMBER")
    private String foracid;

    @Column(name = "YEAR")
    private String year;

    @Column(name = "TURNOVER_TYPE")
    private String turnOverType;

    @Column(name = "CREATED_DATE")
    private String createdDate;

}
