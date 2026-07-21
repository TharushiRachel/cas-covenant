package com.itechro.cas.controller.casmaster;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.casmaster.CACommittee;
import com.itechro.cas.model.domain.casmaster.CACommitteeComment;
import com.itechro.cas.model.domain.casmaster.CommitteeType;
import com.itechro.cas.model.domain.casmaster.UserPool;
import com.itechro.cas.model.dto.casmaster.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "${api.prefix}/committee")
public class CommitteeController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(UserDAController.class);

    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    ModelMapper modelMapper;

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveUserPool", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> saveUserPool(@Validated @RequestBody UserPoolDTO poolDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or update committee pool: {} by user {}", poolDTO, credentialsDTO.getUserID());

        UserPoolDTO poolDTOResponse = masterDataService.saveUserPool(poolDTO, credentialsDTO);

        LOG.info("END : Save or update committee pool: {} by user {}", poolDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(poolDTOResponse, HttpStatus.CREATED);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUserPool", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UserPoolDTO>> getUserPool() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search committee pool: {} by user {}",  credentialsDTO.getUserID());

        List<UserPool> userPools = masterDataService.getUserList();

        List<UserPoolDTO> poolDTOList = userPools.stream().map(userPool -> modelMapper.map(userPool, UserPoolDTO.class)).collect(Collectors.toList());

        LOG.info("END : Search committee pool: {} by user {}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(poolDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveCommitteeType", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> saveCommitteeType(@Validated @RequestBody CommitteeTypeDTO committeeTypeDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or update committee pool: {} by user {}", committeeTypeDTO, credentialsDTO.getUserID());

        CommitteeType committeeType = modelMapper.map(committeeTypeDTO, CommitteeType.class);

        CommitteeType saveCommitteeType = masterDataService.saveCommitteeType(committeeType, credentialsDTO);

        LOG.info("END : Save or update committee pool: {} by user {}", saveCommitteeType, credentialsDTO.getUserID());

        return new ResponseEntity<>(saveCommitteeType, HttpStatus.CREATED);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommitteeTypes", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CommitteeTypeDTO>> getCommitteeTypes() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search committee pool: {} by user {}",  credentialsDTO.getUserID());

        List<CommitteeType> committeeTypeList = masterDataService.getCommitteeTypes();

        List<CommitteeTypeDTO> committeeTypeDTOS = committeeTypeList.stream().map(committeeType -> modelMapper.map(committeeType, CommitteeTypeDTO.class)).collect(Collectors.toList());

        LOG.info("END : Search committee pool: {} by user {}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(committeeTypeDTOS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/approveOrRejectUserPool",headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<UserPoolDTO> approveOrRejectUserPool(@RequestBody UserPoolDTO approveRejectRQ) throws  AppsException{
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject User Pool : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        UserPoolDTO userPoolApproveOrRejectResponse = masterDataService.approveCommitteePoolUsers(approveRejectRQ,credentialsDTO);

        LOG.info("END : Aprrove or Reject User Pool : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(userPoolApproveOrRejectResponse,HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateCommitteeType/{committeeId}",headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CommitteeTypeDTO> updateCommitteeType(@PathVariable Integer committeeId, @Validated @RequestBody CommitteeTypeDTO committeeTypeDTO) throws  AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update Committee Type : {} , user {}", committeeTypeDTO, credentialsDTO.getUserID());

        CommitteeType committeeType = modelMapper.map(committeeTypeDTO, CommitteeType.class);

        committeeType.setCommitteeTypeId(committeeId);

        CommitteeType updateCommitteeTYpe = masterDataService.updateCommitteeType(committeeType, credentialsDTO);

        CommitteeTypeDTO committeeTypeUpdateDTO = modelMapper.map(updateCommitteeTYpe, CommitteeTypeDTO.class);

        LOG.info("END : Update Committee Type : {} , user {}", committeeTypeUpdateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(committeeTypeUpdateDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommittees", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CACommitteeDTO>> getCommittees() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search committees {}",  credentialsDTO.getUserID());

        List<CACommitteeDTO> caCommitteeDTOS = masterDataService.getSubCommittees();

        LOG.info("END : Search committees {}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(caCommitteeDTOS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommitteeById/{committeeId}/{tableType}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<CACommitteeDTO> getCommitteeById(@PathVariable Integer committeeId, @PathVariable String tableType) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search committee by Id {}",  credentialsDTO.getUserID());
        CACommitteeDTO committee = new CACommitteeDTO();

        if (tableType.equals("TEMP")) {
            committee = masterDataService.getTempCommitteeByID(committeeId);
        }else {
            committee = masterDataService.getSubCommitteeByID(committeeId);
        }

        LOG.info("END : Search committees by Id{}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(committee, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveSubCommittee", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> saveSubCommittee(@Validated @RequestBody CACommitteeDTO subCommitteeDTO) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save committee{}",  credentialsDTO.getUserID());

        CACommitteeDTO saveSubCommittee = masterDataService.saveCommittee(subCommitteeDTO, credentialsDTO);

        LOG.info("END : Save committee{}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(saveSubCommittee, HttpStatus.CREATED);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/approveOrRejectCommittee", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CACommitteeDTO> approveRejectCommittee(@Validated @RequestBody CACommitteeDTO caCommitteeDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : " + caCommitteeDTO.getApproveStatus() + " committee by Id {}",  credentialsDTO.getUserID());

        CACommitteeDTO committee = masterDataService.approveRejectCommittee(caCommitteeDTO,credentialsDTO);

        LOG.info("END : " + caCommitteeDTO.getApproveStatus() + " committees by Id{}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(committee, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommitteeCommentList/{committeeId}/{tableType}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CACommitteeCommentDTO>> getCommitteeCommentList(@PathVariable Integer committeeId, @PathVariable String tableType) throws AppsException{

         CredentialsDTO credentialsDTO = getCredentialsDTO();

         LOG.info("START : Get committee comment list: {} by user {}",  credentialsDTO.getUserID());
        List<CACommitteeCommentDTO> caCommitteeCommentDTOS = new ArrayList<>();
         if (tableType.equals("TEMP")){
             caCommitteeCommentDTOS = masterDataService.getCACommentTempListByCommitteeId(committeeId);
         } else {
             caCommitteeCommentDTOS = masterDataService.getCACommentListByCommitteeId(committeeId);
         }

         LOG.info("END : Get committee comment list: {} by user {}",  credentialsDTO.getUserID());
         
         return new ResponseEntity<>(caCommitteeCommentDTOS, HttpStatus.OK);
    }

}
