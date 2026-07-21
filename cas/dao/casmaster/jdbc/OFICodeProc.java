package com.itechro.cas.dao.casmaster.jdbc;

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
public class OFICodeProc extends StoredProcedure {

    @Autowired
    private DataSource dataSource;

    private static final Logger LOG = LoggerFactory.getLogger(OFICodeProc.class);

    private static final String GET_OFI_CODE = "get_cft_ofi_code";

    private static final String PARAM_OUT_OFI_CODE = "OFI_CODE";

    @PostConstruct
    public void initialize() {
        //Compiling the SP
        setDataSource(dataSource);
        setSql(GET_OFI_CODE);
        setFunction(true);

        declareParameter(new SqlOutParameter(PARAM_OUT_OFI_CODE, Types.VARCHAR));

        compile();

        LOG.info("Other facility information ref proc initialized and compiled");
    }

    public String executeFunction() throws AppsException {
        Map<String, Object> inputs = new HashMap<>();

        Map<String, Object> execute = super.execute(inputs);
        String result = (String) execute.get(PARAM_OUT_OFI_CODE);

        LOG.info("Other facility information code generated {} ", result);

        return result;
    }

}
