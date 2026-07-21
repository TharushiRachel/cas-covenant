package com.itechro.cas.service.acae;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;


@Service
public class ACAECommonService {
    private static final Logger LOG = LoggerFactory.getLogger(ACAEStatusInquiryService.class);
    public ArrayList getRecordListAsArrayList(String text) {
        ArrayList solIdList = new ArrayList();

        StringTokenizer st = new StringTokenizer(text, ",");
        while (st.hasMoreTokens()) {

            String element = st.nextToken();
            Integer elm = new Integer(element);
            int solIdElm = elm.intValue();
            String pattern = "000";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            String output = myFormatter.format(solIdElm);
            if (solIdList.contains(output)) {
            } else {
                solIdList.add(output);
            }
        }
        for (int i = 0; solIdList.size() > i; i++) {
            LOG.info(solIdList.get(i) + " , ");
        }
        return solIdList;
    }

}
