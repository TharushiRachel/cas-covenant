package com.itechro.cas.dao.lead.jdbc;

import com.itechro.cas.exception.impl.AppsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Component
public class LeadRefProc extends StoredProcedure {

    @Autowired
    private DataSource dataSource;

    private static final Logger LOG = LoggerFactory.getLogger(LeadRefProc.class);

    private static final String GET_LEAD_REF = "get_lead_referance";

    private static final String PARAM_OUT_LEAD_REF = "lead_ref_number";

    @PostConstruct
    public void initialize() {
        //Compiling the SP
        setDataSource(dataSource);
        setSql(GET_LEAD_REF);
        setFunction(true);

        declareParameter(new SqlOutParameter(PARAM_OUT_LEAD_REF, Types.VARCHAR));

        compile();

        LOG.info("Lead ref proc initialized and compiled");
    }

    public String executeFunction() throws AppsException {
        Map<String, Object> inputs = new HashMap<>();

        Map<String, Object> execute = super.execute(inputs);
        String result = (String) execute.get(PARAM_OUT_LEAD_REF);

        LOG.info("Lead ref generated {} ", result);

        return result;
    }
}
