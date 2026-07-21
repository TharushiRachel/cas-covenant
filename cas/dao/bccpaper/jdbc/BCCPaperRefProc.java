package com.itechro.cas.dao.bccpaper.jdbc;

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
public class BCCPaperRefProc extends StoredProcedure {

    @Autowired
    private DataSource dataSource;

    private static final Logger LOG = LoggerFactory.getLogger(BCCPaperRefProc.class);

    private static final String GET_BCC_PAPER_REF = "GET_BCC_REFERANCE";

    private static final String PARAM_OUT_F_PAPER_REF = "BCC_REF_NUMBER";

    @PostConstruct
    public void initialize() {
        //Compiling the SP
        setDataSource(dataSource);
        setSql(GET_BCC_PAPER_REF);
        setFunction(true);

        declareParameter(new SqlOutParameter(PARAM_OUT_F_PAPER_REF, Types.VARCHAR));

        compile();

        LOG.info("Bcc Paper ref proc initialized and compiled");
    }

    public String executeFunction() throws AppsException {
        Map<String, Object> inputs = new HashMap<>();

        Map<String, Object> execute = super.execute(inputs);
        String result = (String) execute.get(PARAM_OUT_F_PAPER_REF);

        LOG.info("BCC paper ref generated {} ", result);

        return result;
    }
}
