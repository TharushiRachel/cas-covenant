package com.itechro.cas.model.domain.kalyptosystem;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_KALYPTO_PARAMS")
public class kalyptoData {

        @Id
        @Column(name = "ID")
        private Integer id;

        @Column(name = "PARAMETER_ID")
        private String parameterId;

        @Column(name = "VALUE_NUMERIC")
        private String valueNumeric;

        @Column(name = "VALUE_PERCENTAGE")
        private String valuePercentage;

        @Column(name = "VALUE_TEXT")
        private String valueText;

        @Column(name = "PERIOD_ID")
        private String periodId;

        @Column(name = "PARAMETER_NAME")
        private String parameterName;

        @Column(name = "CUSTOMER_ID")
        private String customerId;

        @Column(name = "FACILITY_ID")
        private String facilityId;

        @Column(name = "IS_NEW_ADDED")
        private String isAddedNew;

}
