package com.itechro.cas.controller.acae;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.acae.request.*;
import com.itechro.cas.model.dto.acae.response.*;
import com.itechro.cas.model.dto.casmaster.LoggedInUserWorkFlowByStatusRQ;
import com.itechro.cas.model.dto.casmaster.UpmGroupDTO;
import com.itechro.cas.model.dto.integration.request.ACAEAcctClassificationDataRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerAccountStatRQ;
import com.itechro.cas.model.dto.integration.response.ACAEAcctClassificationDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.acae.ACAEPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/acae/paper")
public class ACAEPaperController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEPaperController.class);
    private final ACAEPaperService acaePaperService;

    @Autowired
    public ACAEPaperController(ACAEPaperService acaePaperService) {
        this.acaePaperService = acaePaperService;
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAECustomerDetails", headers = "Accept=application/json")
    public ResponseEntity<ACAECustomerDetailsDTO> getACAECustomerDetailsController(@RequestBody ACAECustomerDetailsRQ acaeCustomerDetailsRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET ACAE Paper Customer Details  : {} , user {}", acaeCustomerDetailsRQ, credentialsDTO);

        ACAECustomerDetailsDTO acaeCustomerDetailsDTO = acaePaperService.getCustomerDetailsService(acaeCustomerDetailsRQ, credentialsDTO);

        LOG.info("END : GET ACAE Paper Customer Details  : {} , user {}", acaeCustomerDetailsRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeCustomerDetailsDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getAccountBalanceDetails", headers = "Accept=application/json")
    public ResponseEntity<Object> getAccountBalanceDetailsController(@RequestBody ACAECustomerDetailsRQ acaeCustomerDetailsRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET ACAE Paper Customer Details  : {} , user {}", acaeCustomerDetailsRQ, credentialsDTO);

        Object acaeCustomerDetailsDTO = acaePaperService.getAccountBalanceDetailsService(acaeCustomerDetailsRQ, credentialsDTO);

        LOG.info("END : GET ACAE Paper Customer Details  : {} , user {}", acaeCustomerDetailsRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeCustomerDetailsDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAERelatedAccounts", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEAcctBalByAcctDataDTO>> getACAERelatedAccountController(@RequestBody ACAEAccountTypeRQ ACAEAccountTypeRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START :GET ACAE Related Accounts :{} , user {}", ACAEAccountTypeRQ, credentialsDTO);

        List<ACAEAcctBalByAcctDataDTO> result = acaePaperService.getACAERelatedAccountsService(ACAEAccountTypeRQ, credentialsDTO);

        LOG.info("END :GET ACAE Related Accounts: {} , user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getBasicACAEOutstanding", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEOutstandingDTO>> getBasicACAEOutstandingController(@RequestBody ACAEAccountTypeRQ acaeAccountTypeRQ) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START :GET ACAE Basic Outstanding :{} , user {}", acaeAccountTypeRQ, credentialsDTO);

        List<ACAEOutstandingDTO> result = acaePaperService.getBasicACAEOutstandingService(acaeAccountTypeRQ, credentialsDTO);

        LOG.info("END : GET ACAE Basic Outstanding : {} , user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getAdvanceACAEOutstanding", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEStatByAcctDataDTO>> getAdvanceACAEOutstandingController(@RequestBody ACAEAccountTypeRQ acaeAccountTypeRQ) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START :GET Advance ACAE Outstanding :{} , user {}", acaeAccountTypeRQ, credentialsDTO);

        List<ACAEStatByAcctDataDTO> result = acaePaperService.getAdvanceACAEOutstandingService(acaeAccountTypeRQ, credentialsDTO);

        LOG.info("END : GET Advance ACAE Outstanding  : {} , user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAELoanAccounts", headers = "Accept=application/json")
    public ResponseEntity<ACAEAcctClassificationDTO> getACAELoanAccountController(@RequestBody ACAEAcctClassificationDataRQ acaeAcctClassificationDataRQ) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Loan Account :{} , user {}", acaeAcctClassificationDataRQ, credentialsDTO);

        ACAEAcctClassificationDTO result = acaePaperService.getACAELoanAccountService(acaeAcctClassificationDataRQ, credentialsDTO);

        LOG.info("END : ACAE Loan Account : {} , user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getAccountTypes", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEOutstandingDTO>> getAccountTypeController(@RequestBody ACAEAccountTypeRQ acaeAccountTypeRQ) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START :GET ACAE AcctBalByAccts  :{} , user {}", acaeAccountTypeRQ, credentialsDTO);

        List<ACAEOutstandingDTO> result = acaePaperService.getAccountTypeService(acaeAccountTypeRQ, credentialsDTO);

        LOG.info("END : GET ACAE AcctBalByAccts : {} , user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAEBalanceAfterPayment", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEBalanceAfterPaymentDTO>> getACAEBalanceAfterPaymentController(@RequestBody ACAEBalanceAfterPaymentRQ acaeBalanceAfterPaymentRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Balance After Payment :{} , user {}", acaeBalanceAfterPaymentRQ, credentialsDTO);

        List<ACAEBalanceAfterPaymentDTO> acaeBalanceAfterPaymentDTOList =
                acaePaperService.getACAEBalanceAfterPaymentService(acaeBalanceAfterPaymentRQ, credentialsDTO);

        LOG.info("END : ACAE Balance After Payment :{} , user {}", acaeBalanceAfterPaymentRQ, credentialsDTO);

        return new ResponseEntity<>(acaeBalanceAfterPaymentDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAEUserComments", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEUserCommentsDTO>> getACAEUserCommentsController(@RequestBody ACAEUserCommentsRQ acaeUserCommentsRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE User Comments :{} , user {}", acaeUserCommentsRQ, credentialsDTO);

        List<ACAEUserCommentsDTO> acaeUserCommentsDTO =
                acaePaperService.getACAEUserCommentService(acaeUserCommentsRQ, credentialsDTO);

        LOG.info("END : ACAE User Comments :{} , user {}", acaeUserCommentsRQ, credentialsDTO);

        return new ResponseEntity<>(acaeUserCommentsDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAELowerOrHigherUserGroupLOV", headers = "Accept=application/json")
    public ResponseEntity<List<UpmGroupDTO>> getACAELowerOrHigherUserGroupLOVController(@RequestBody LoggedInUserWorkFlowByStatusRQ loggedInUserWorkFlowByStatusRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Lower Or Higher User Group LOV: {} , user {} ", loggedInUserWorkFlowByStatusRQ, credentialsDTO);

        List<UpmGroupDTO> upmGroupDTOLOVList =
                acaePaperService.getACAELowerOrHigherUserGroupLOVService(loggedInUserWorkFlowByStatusRQ, credentialsDTO);

        LOG.info("END : ACAE Lower Or Higher User Group LOV: {} , user {} ", loggedInUserWorkFlowByStatusRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(upmGroupDTOLOVList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/approveACAEPaper", headers = "Accept=application/json")
    public ResponseEntity<Boolean> approveACAEPaperController(@RequestBody ACAEPaperApproveRQ acaePaperApproveRQ)
            throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Approve ACAE Paper : {} , user {}", acaePaperApproveRQ, credentialsDTO);

        Boolean approveACAEPaper =
                acaePaperService.approveACAEPaperService(acaePaperApproveRQ, credentialsDTO);

        LOG.info("END : Approve ACAE Paper : {} , user {}", acaePaperApproveRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveACAEPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/forwardACAEPaper", headers = "Accept=application/json")
    public ResponseEntity<Boolean> forwardACAEPaperController(@RequestBody ACAEPaperTransferRQ acaePaperTransferRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Forward ACAE Paper {}, user {}", acaePaperTransferRQ, credentialsDTO);

        Boolean forwardACAEPaper =
                acaePaperService.forwardACAEPaperService(acaePaperTransferRQ, credentialsDTO);

        LOG.info("END : Forward ACAE Paper : {} , user {}", acaePaperTransferRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(forwardACAEPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/forwardACAEBatch", headers = "Accept=application/json")
    public ResponseEntity<Boolean> forwardACAEBatchController(@RequestBody ACAEBatchForwardRQ acaeBatchForwardRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Forward ACAE Batch ", acaeBatchForwardRQ, credentialsDTO);

        Boolean forwardACAEBatch = acaePaperService.forwardACAEBatchService(acaeBatchForwardRQ, credentialsDTO);

        LOG.info("END : Forward ACAE Batch : {} , user {}", acaeBatchForwardRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(forwardACAEBatch, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/rejectACAEPaper", headers = "Accept=application/json")
    public ResponseEntity<Boolean> rejectACAEPaperController(@RequestBody ACAEPaperTransferRQ acaePaperTransferRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("END :  Reject ACAE Paper : {} , user {}", acaePaperTransferRQ, credentialsDTO);

        Boolean rejectACAEPaper =
                acaePaperService.rejectACAEPaperService(acaePaperTransferRQ, credentialsDTO);

        LOG.info("END :  Reject ACAE Paper : {} , user {}", acaePaperTransferRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(rejectACAEPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getPreviousUsers", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEPreviousUserDTO>> getPreviousUsersController(@RequestBody ACAEPreviousUserRQ acaePreviousUserRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("END :  Previous Users : {} , user {}", acaePreviousUserRQ, credentialsDTO);

        List<ACAEPreviousUserDTO> acaePreviousUserDTOList =
                acaePaperService.getPreviousUsersService(acaePreviousUserRQ, credentialsDTO);

        LOG.info("END :  Previous Users : {} , user {}", acaePreviousUserRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaePreviousUserDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/toBeResubmittedACAEPaper", headers = "Accept=application/json")
    public ResponseEntity<Boolean> toBeResubmittedACAEPaperController(@RequestBody ACAEPaperTransferRQ acaePaperTransferRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Resubmitted ACAE Paper : {} , user {}", acaePaperTransferRQ, credentialsDTO);

        Boolean rejectACAEPaper =
                acaePaperService.toBeResubmittedACAEPaperService(acaePaperTransferRQ, credentialsDTO);

        LOG.info("END : Resubmitted ACAE Paper : {} , user {}", acaePaperTransferRQ, credentialsDTO);

        return new ResponseEntity<>(rejectACAEPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAEActiveComment", headers = "Accept=application/json")
    public ResponseEntity<String> getActiveCommentController(@RequestBody ACAEUserCommentsRQ acaeUserCommentsRQ) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Active User Comment : {} , user {}", acaeUserCommentsRQ, credentialsDTO);

        String comment = acaePaperService.getActiveCommentService(acaeUserCommentsRQ, credentialsDTO);

        LOG.info("END : ACAE Active User Comment : {} , user {}", acaeUserCommentsRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveACAEComment", headers = "Accept=application/json")
    public ResponseEntity<String> saveACAECommentController(@RequestBody ACAECommentRQ acaeCommentRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save ACAE User Remarks : {} , user {}", acaeCommentRQ, credentialsDTO);

        String responseMsg = acaePaperService.saveACAECommentService(acaeCommentRQ, credentialsDTO);

        LOG.info("END : Save ACAE User Remarks : {} , user {}", acaeCommentRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(responseMsg, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/isACAEAttended", headers = "Accept=application/json")
    public ResponseEntity<Boolean> isACAEAttendedController(@RequestBody ACAEAttendedRQ attendedRQ) {

        LOG.info("START : Is ACAE Attended : {}", attendedRQ);

        Boolean isACAEAttended = acaePaperService.isACAEAttendedService(attendedRQ);

        LOG.info("END : Is ACAE Attended : {} ", attendedRQ);

        return new ResponseEntity<>(isACAEAttended, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/initializeReview", headers = "Accept=application/json")
    public ResponseEntity<Boolean> initializeReviewController(@RequestBody ACAEInitializeReviewRQ acaeInitializeReviewRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Initialize Review : {} , user {}", acaeInitializeReviewRQ, credentialsDTO);

        Boolean isACAEAttended = acaePaperService.initializeReviewService(acaeInitializeReviewRQ, credentialsDTO);

        LOG.info("END : Initialize Review : {} , user {}", acaeInitializeReviewRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(isACAEAttended, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAERangeInquiry", headers = "Accept=application/json")
    public ResponseEntity<ACAERangeInquiryResponse> getACAERangeInquiryController(@RequestBody ACAERangeInquiryRQ acaeRangeInquiryRQ)
            throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Range Inquiry: {} , user {} ", acaeRangeInquiryRQ, credentialsDTO);

        ACAERangeInquiryResponse acaeRangeInquiryDTOList = acaePaperService.getACAERangeInquiryService(acaeRangeInquiryRQ, credentialsDTO);

        LOG.info("END : ACAE Range Inquiry: {} , user {} ", acaeRangeInquiryRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeRangeInquiryDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getFinacleIdFromAccountNumber", headers = "Accept=application/json")
    public ResponseEntity<ACAEFinacleIdDTO> getFinacleIdFromAccountNumberController(@RequestBody CustomerAccountStatRQ customerAccountStatRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET ACAE FinacleId :{} , user {}", customerAccountStatRQ, credentialsDTO);

        ACAEFinacleIdDTO aCAEFinacleIdDTO = acaePaperService.getFinacleIdFromAccountNumberService(customerAccountStatRQ);

        LOG.info("END : GET ACAE FinacleId  : {} , user {}", aCAEFinacleIdDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(aCAEFinacleIdDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getClassificationData", headers = "Accept=application/json")
    public ResponseEntity<Object> getClassificationDataController(@RequestBody ACAECustomerDetailsRQ acaeCustomerDetailsRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET ACAE Classification Details  : {} , user {}", acaeCustomerDetailsRQ, credentialsDTO);

        Object acaeCustomerDetailsDTO = acaePaperService.getClassificationDataService(acaeCustomerDetailsRQ, credentialsDTO);

        LOG.info("END : GET ACAE Classification Details  : {} , user {}", acaeCustomerDetailsRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeCustomerDetailsDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getDAClearBalance", headers = "Accept=application/json")
    public ResponseEntity<String> getDAClearBalanceController() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET DA Clear Balance Details : {} ,user {}", credentialsDTO);

        String clearDABalance = acaePaperService.getDAClearBalanceService(credentialsDTO);

        LOG.info("END : GET DA Clear Balance Details  :{} , user {}", clearDABalance, credentialsDTO);

        return new ResponseEntity<>(clearDABalance, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getCurrentUser", headers = "Accept=application/json")
    public ResponseEntity<ACAECurrentUserDTO> getCurrentUser(@RequestBody ACAEAccountTypeRQ acaeAccountTypeRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET ACAE Current User Details :{}, user : {}", acaeAccountTypeRQ, credentialsDTO);

        ACAECurrentUserDTO currentUserDTO = acaePaperService.getCurrentUserService(acaeAccountTypeRQ);

        LOG.info("END : GET ACAE Current User Details :{} ,user : {}", currentUserDTO, credentialsDTO);

        return new ResponseEntity<>(currentUserDTO, HttpStatus.OK);
    }
}
