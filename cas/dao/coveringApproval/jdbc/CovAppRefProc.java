package com.itechro.cas.dao.coveringApproval.jdbc;

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


/**
 *
 *
 * @author tharushi
 */
@Component
public class CovAppRefProc extends StoredProcedure {
    @Autowired
    private DataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(CovAppRefProc.class);

    private static final String GET_COVAPP_REF = "GET_COVAPP_REFERANCE";

    private static final String PARAM_OUT_COVAPP_REF = "COVAPP_REF_NUMBER";

    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
        setSql(GET_COVAPP_REF);
        setFunction(true);

        declareParameter(new SqlOutParameter(PARAM_OUT_COVAPP_REF, Types.VARCHAR));

        compile();

        LOG.info("Covering Approval ref proc initialized and compiled");
    }

    public String executeFunction() throws AppsException {
        LOG.info("3333333333333");
        Map<String, Object> inputs = new HashMap<>();

        Map<String, Object> execute = super.execute(inputs);
        String result = (String) execute.get(PARAM_OUT_COVAPP_REF);

        LOG.info("Covering Approval ref generated {} ", result);

        return result;
    }
}
