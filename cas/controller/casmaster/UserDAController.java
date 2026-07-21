package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Response;
import com.itechro.cas.model.domain.casmaster.UserDa;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.dto.casmaster.UserDaDTO;
import com.itechro.cas.model.dto.master.UserDaSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import com.itechro.cas.commons.constants.PrivilegeCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/userDA")
public class UserDAController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(UserDAController.class);

    @Autowired
    private MasterDataService masterDataService;
    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedUserDA",headers = "Accept=application/json",method = RequestMethod.POST)
    public ResponseEntity<Page<UserDaDTO>> getPagedUserDA(@RequestBody UserDaSearchRQ userDaSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search UserDA : {} , user {}", userDaSearchRQ, credentialsDTO.getUserID());

        Page<UserDaDTO> pagedUserDAs = masterDataService.getPagedUserDaDTO(userDaSearchRQ);

        LOG.info("END : Search UserDA : {} , user {}", userDaSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedUserDAs, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUserDaUpdateDTO/{userDaID}",headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<UserDaDTO>  getUserDaUpdateDTO(@PathVariable Integer userDaID)throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get UserDA : {} , user {}", userDaID, credentialsDTO.getUserID());

        UserDaDTO userDaUpdateDTO = masterDataService.getUserDaByID(userDaID);

        LOG.info("END : Get UserDA : {} , user {}", userDaID, credentialsDTO.getUserID());

        return new ResponseEntity<>(userDaUpdateDTO,HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateUserDa", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<UserDaDTO> saveOrUpdateUserDa(@RequestBody UserDaDTO userDaDTO)throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update UserDA : {} , user {}", userDaDTO, credentialsDTO.getUserID());

        UserDaDTO UserDaUpdateDTOResponse = masterDataService.saveOrUpdateUserDaDTO(userDaDTO,credentialsDTO);

        LOG.info("END : Save or Update UserDA : {} , user {}", userDaDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(UserDaUpdateDTOResponse,HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/approveOrRejectUserDa",headers = "Accept=application/json", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ADMIN_USER_DELEGATED_AUTHORITY_APPROVE_OR_REJECT+ "')")
    public ResponseEntity<UserDaDTO> approveOrRejectUserDa(@RequestBody ApproveRejectRQ approveRejectRQ) throws  AppsException{
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject UserDA : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        UserDaDTO userDaApproveOrRejectResponse = masterDataService.approveUserDaDTO(approveRejectRQ,credentialsDTO);

        LOG.info("END : Aprrove or Reject UserDA : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(userDaApproveOrRejectResponse,HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllDAUsers",headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UserDaDTO>> getAllDAUsers()throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get UserDA : user {}", credentialsDTO.getUserID());

        List<UserDaDTO> daUsers = masterDataService.getAllDAUsers();

        LOG.info("END : Get UserDA : user {}", credentialsDTO.getUserID());
        return new ResponseEntity<>(daUsers,HttpStatus.OK);
    }
}
