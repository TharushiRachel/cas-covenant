package com.itechro.cas.model.domain.acae;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
        name = "ACAEMock.saveACAECommentRepository",
        procedureName = "ACAE_OPERATIONS.SAVE_REMARK",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "STRING_REFERENCE_ID", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "STRING_ACCT_NO", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "STRING_REMARK", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "DATE_NEG_DATE", type = java.util.Date.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "DATE_PRE_NEG_DATE", type = java.util.Date.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_STATUS", type = String.class)
        }
)
public class ACAEMock {
    @Id
    Integer id;
}
