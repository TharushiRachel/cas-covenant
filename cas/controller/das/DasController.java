package com.itechro.cas.controller.das;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.das.DADesignationMasterData;
import com.itechro.cas.model.dto.das.*;
import com.itechro.cas.model.dto.integration.request.UpmDetailLoadRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.das.DasService;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/das")

public class DasController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(DasController.class);

    @Autowired
    private DasService dasService;

    @Autowired
    private IntegrationService integrationService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/createAndUpdateDALimits", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DADesignationAndDALimitsDTO> addDALimitsToTemp(@RequestBody DALimitListDTO request) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Create And Update DA Limits : {} , user {}", request, credentialsDTO.getUserID());

        DADesignationAndDALimitsDTO dasListDTO = this.dasService.createAndUpdateDALimitsToTemp(request, credentialsDTO, null);

        LOG.info("END : Create And Update DA Limits : {} , user {}", request, credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/authorizedDALimits", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DADesignationAndDALimitsDTO> authorizeDALimits(@RequestBody DALimitsAuthorizeRQ request) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Authorize DA Details : {} , user {}", request, credentialsDTO.getUserID());

        DADesignationAndDALimitsDTO dasListDTO = dasService.authorizeDALimits(request,credentialsDTO);

        LOG.info("END : Authorize DA Details : {} , user {}", request, credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllDALimits", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<FullDATable> getAllDALimits() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All DA Limits : user {}", credentialsDTO.getUserID());

        FullDATable dasListDTO = dasService.getAllDALimits(credentialsDTO);

        LOG.info("END : Get All DA Limits : user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllTempDALimits", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<FullDATable> getAllTempDALimits() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All Temp DA Limits : user {}", credentialsDTO.getUserID());

        FullDATable dasListDTO = dasService.getAllTempDALimits(credentialsDTO);

        LOG.info("END : Get All Temp DA Limits : user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/saveDADetailsToMaster", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DADesignationAndDALimitsDTO> saveDADetailsToMaster(@RequestBody DADesignationIntegratedDTO daDesignationIntegratedDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : save DA Details : {} , user {}", daDesignationIntegratedDTO, credentialsDTO.getUserID());

        DADesignationAndDALimitsDTO daLimitListDTO= dasService.saveDADetailsToMaster(daDesignationIntegratedDTO,credentialsDTO);

        LOG.info("END : save DA Details : {} , user {}", daDesignationIntegratedDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(daLimitListDTO,HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllIndividualDesignationCode", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<DADesignationCodeDTO>> getAllIndividualDesignationCode() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get all DA Details {}",  credentialsDTO.getUserID());

        List<DADesignationCodeDTO> addDesignationDTO = dasService.getDADesignationDropDownList();

        LOG.info("END : get all DA Details {}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(addDesignationDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUserUPMDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<UpmDetailResponse> getUserUPMDetails(@RequestBody UpmDetailLoadRQ rq) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All Temp DA Limits : user {}", credentialsDTO.getUserID());

        UpmDetailResponse dasListDTO = integrationService.getUpmDetailsByUserIdAndAppCode(rq);

        LOG.info("END : Get All Temp DA Limits : user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "admin/getAllDALimits", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<DALimitsWithApproveStatusDTO>> getAllDALimitsForAdmin() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<DALimitsWithApproveStatusDTO> dasListDTO = dasService.getAllDALimitsForAdmin(credentialsDTO);

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "admin/getPendingAndApprovedDALimitsByDesignationId/{designationId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<DALimitsWithApproveStatusDTO>> getPendingAndApprovedDALimitsByDesignationId(@PathVariable Integer designationId) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All DA Limits : user {}", credentialsDTO.getUserID());

        List<DALimitsWithApproveStatusDTO> dasListDTO = dasService.getAllPendingAndApprovedDALimitsByDesignationId(credentialsDTO, designationId);

        LOG.info("END : Get All DA Limits : user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "admin/getAllDALimitsWithApproveStatus", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<FullDATableWithTypeDTO> getAllDALimitsWithApproveStatus() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All DA Limits With Approve Status : user {}", credentialsDTO.getUserID());

        FullDATableWithTypeDTO dasListDTO = dasService.getAllDALimitsWithApproveStatus(credentialsDTO);

        LOG.info("END : Get All DA Limits With Approve Status : user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "getAllDALimitsAndDesignations", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity <DADesignationAndDALimitsDTO> getAllDALimitsAndDesignations() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All DA Limits With Designations : user {}", credentialsDTO.getUserID());

        DADesignationAndDALimitsDTO allDALimitsAndDesignationsList = dasService.getAllDALimitsAndDesignations(credentialsDTO);

        LOG.info("END : Get All DA Limits With Designations: user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(allDALimitsAndDesignationsList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/changeDisplayOrder", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DADesignationAndDALimitsDTO> changeDisplayOrder(@RequestBody ChangeDADisplayOrderRQ request) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : changeDisplayOrder : {} , user {}", request, credentialsDTO.getUserID());

        DADesignationAndDALimitsDTO dasListDTO = this.dasService.changeDisplayOrder(request, credentialsDTO);

        LOG.info("END : changeDisplayOrder : {} , user {}", request, credentialsDTO.getUserID());

        return new ResponseEntity<>(dasListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllCommitteeDesignationsWithCode", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<DADesignationCodeDTO>> getAllCommitteeDesignationCode() throws Exception {


        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : changeDisplayOrder : {} , user {}",  credentialsDTO.getUserID());
        List<DADesignationCodeDTO> designations = dasService.getCommitteeDesignations(credentialsDTO);


        LOG.info("END : changeDisplayOrder : {} , user {}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(designations, HttpStatus.OK);
    }

}
