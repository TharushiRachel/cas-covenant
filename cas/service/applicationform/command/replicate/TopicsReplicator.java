package com.itechro.cas.service.applicationform.command.replicate;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.AFTopicData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TopicsReplicator extends CommandExecutor<ApplicationFormReplicateContext> {

    private static final Logger LOG = LoggerFactory.getLogger(TopicsReplicator.class);

    @Override
    public ApplicationFormReplicateContext execute(ApplicationFormReplicateContext context) throws AppsException {

        for (AFTopicData afOriginalTopicData : context.getCurrentApplicationForm().getOrderedAFTopicData().stream().filter(afTopicData ->
                afTopicData.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {
            AFTopicData replicatedTopicData = new AFTopicData();
            replicatedTopicData.setCreatedDate(context.getDate());
            replicatedTopicData.setPage(afOriginalTopicData.getPage());
            replicatedTopicData.setTopic(afOriginalTopicData.getTopic());
            replicatedTopicData.setRemark(afOriginalTopicData.getRemark());
            replicatedTopicData.setStatus(afOriginalTopicData.getStatus());
            replicatedTopicData.setTopicCode(afOriginalTopicData.getTopicCode());
            replicatedTopicData.setTopicData(afOriginalTopicData.getTopicData());
            replicatedTopicData.setCreatedBy(context.getCredentialsDto().getUserName());

            context.getNewApplicationForm().addAFTopicData(replicatedTopicData);
        }
        LOG.info("Application Form Default Topics replicated");

        return context;
    }

}
