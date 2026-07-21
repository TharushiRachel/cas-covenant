package com.itechro.cas.controller.agent;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.agent.AgentDTO;
import com.itechro.cas.model.dto.agent.AgentSearchRQ;
import com.itechro.cas.model.dto.master.PasswordUpdateDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.agent.AgentService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.master.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.prefix}/agent")
public class AgentController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    private AgentService agentService;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private UserService userService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedAgents", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<AgentDTO>> getPagedAgents(@RequestBody AgentSearchRQ agentSearchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search agents : {} , user {}", agentSearchRQ, credentialsDTO.getUserID());

        Page<AgentDTO> pagedRoles = this.agentService.getPagedAgents(agentSearchRQ);

        LOG.info("END : Search agents : {} , user {}", agentSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedRoles, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAgentUpdateDTO/{agentID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<AgentDTO> getRoleUpdateDTO(@PathVariable Integer agentID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get agent : {}, user {} ", agentID, credentialsDTO.getUserID());

        AgentDTO agentDTO = this.agentService.getAgentDTO(agentID);

        LOG.info("END : get agent : {}, user {} ", agentID, credentialsDTO.getUserID());

        return new ResponseEntity<>(agentDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAgentUserDetailsByUserName/{userName}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<AgentDTO> getAgentUserDetailsByUserName(@PathVariable String userName) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get agent by user name : {}, user {} ", userName, credentialsDTO.getUserID());

        AgentDTO agentDTO = this.agentService.getAgentUserDetailsByUserName(userName);

        LOG.info("END : get agent by user name : {}, user {} ", userName, credentialsDTO.getUserID());

        return new ResponseEntity<>(agentDTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/addAgent", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AgentDTO> addAgent(@RequestBody AgentDTO agentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : add agent : {}, user {}", agentDTO, credentialsDTO.getUserID());

        AgentDTO response = this.agentService.addAgent(agentDTO, credentialsDTO);

        LOG.info("END : add agent : {}, user {}", agentDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateAgent", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AgentDTO> updateAgent(@RequestBody AgentDTO agentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : update agent : {}, user {}", agentDTO, credentialsDTO.getUserID());

        AgentDTO response = this.agentService.updateAgent(agentDTO, credentialsDTO);

        LOG.info("END : update agent : {}, user {}", agentDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateUserPassword", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> updateUserPassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : update user password : {} by {}", passwordUpdateDTO, credentialsDTO.getUserID());
        String status = null;
        try {
            this.userService.updateUserPassword(passwordUpdateDTO, credentialsDTO);
            status = "SUCCESS";
        } catch (AppsException e) {
            status = "FAILED";
            LOG.info("ERROR : update user password : {} by {}", passwordUpdateDTO, credentialsDTO.getUserID(), e);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_MURAPADAYA_CHANGE_FAILED);
        }
        LOG.info("END : update user password : {} by {}", passwordUpdateDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
