package com.itechro.cas.service.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.commons.constants.DomainConstants.MasterDataApproveStatus;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.applicationform.AFTopicUpcSectionDao;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.dao.casmaster.*;
import com.itechro.cas.dao.casmaster.jdbc.MasterDataJdbcDao;
import com.itechro.cas.dao.casmaster.jdbc.OFICodeProc;
import com.itechro.cas.dao.facilitypaper.CommitteePaperDao;
import com.itechro.cas.dao.master.RoleDao;
import com.itechro.cas.dao.master.UpcSectionDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.applicationform.AFTopicConfig;
import com.itechro.cas.model.domain.casmaster.*;
import com.itechro.cas.model.domain.facilitypaper.Deviation;
import com.itechro.cas.model.domain.master.Role;
import com.itechro.cas.model.dto.applicationform.AFTTopicConfigDTO;
import com.itechro.cas.model.dto.applicationform.AFTopicConfigRawDataDTO;
import com.itechro.cas.model.dto.applicationform.AFTopicConfigUploadRQ;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.casmaster.*;
import com.itechro.cas.model.dto.facility.PurposeOfAdvancedDTO;
import com.itechro.cas.model.dto.facility.SectorDTO;
import com.itechro.cas.model.dto.facilitypaper.DeviationDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.Formula;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummarySearchRQ;
import com.itechro.cas.model.dto.master.RoleDTO;
import com.itechro.cas.model.dto.master.UpcSectionSearchRQ;
import com.itechro.cas.model.dto.master.UserDaSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.service.casmaster.support.*;
import com.itechro.cas.service.storage.StorageService;
import com.itechro.cas.util.CalculatorUtil;
import com.itechro.cas.util.WebAuditUtils;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.querydsl.core.BooleanBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MasterDataService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataService.class);
    private final Object guard = new Object();
    @Autowired
    UpcSectionDao upcSectionDao;
    @Autowired
    UpcTemplateDao upcTemplateDao;
    @Autowired
    UpcTemplateDataDao upcTemplateDataDao;
    @Autowired
    AFTopicDao afTopicDao;
    @Autowired
    private CasProperties casProperties;
    @Autowired
    private SupportingDocDao supportingDocDao;
    @Autowired
    private CftSupportingDocDao cftSupportingDocDao;
    @Autowired
    private CreditFacilityTypeDao creditFacilityTypeDao;
    @Autowired
    private CreditFacilityTemplateDao creditFacilityTemplateDao;
    @Autowired
    private UserDaDao userDaDao;
    @Autowired
    private MasterDataJdbcDao masterDataJdbcDao;
    @Autowired
    private CftInterestRateDao cftInterestRateDao;
    @Autowired
    private OtherFacilityInfoDao otherFacilityInfoDao;
    @Autowired
    private WorkFlowTemplateDao workFlowTemplateDao;
    @Autowired
    private WorkFlowTemplateDataDao workFlowTemplateDataDao;
    @Autowired
    private UpmGroupDao upmGroupDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private WebAuditService webAuditService;
    @Autowired
    private WebAuditJDBCDao webAuditJDBCDao;
    @Autowired
    private SecuritySummaryTopicDao securitySummaryTopicDao;
    @Autowired
    private StorageService storageService;
    @Autowired
    private AFTopicConfigDao afTopicConfigDao;
    @Autowired
    private AFTopicUpcSectionDao afTopicUpcSectionDao;
    @Autowired
    private OFICodeProc ofiCodeProc;

    private Environment environment;

    @Autowired
    private UserPoolDao userPoolDao;

    @Autowired
    private CommitteeTypeDao committeeTypeDao;

    @Autowired
    private SubCommitteeDao subCommitteeDao;

    @Autowired
    private  SubCommitteeTempDao subCommitteeTempDao;

    @Autowired
    private CALevelDao caLevelDao;

    @Autowired
    private  CALevelTempDao caLevelTempDao;

    @Autowired
    private CAUserDao caUserDao;

    @Autowired
    private  CAUserTempDao caUserTempDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private CACommitteeCommentDao caCommitteeCommentDao;

    @Autowired
    private CACommitteeCommentTempDao caCommitteeCommentTempDao;

    @Autowired
    private UserPoolTempDao userPoolTempDao;

    @Autowired
    private CommitteePaperDao committeePaperDao;

    @Autowired
    private DeviationTypeDao deviationTypeDao;

    @Autowired
    private TempDeviationDao tempDeviationDao;

    @Autowired
    private DeviationDao deviationDao;

    @Value("${apps.cft.template.ccdu.email}")
    private String ccduEmail;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public SupportingDocDTO getSupportingDocByID(Integer supportingDocID) {
        SupportingDoc supportingDoc = supportingDocDao.getOne(supportingDocID);
        return new SupportingDocDTO(supportingDoc);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<SupportingDocDTO> getPagedSupportingDocDTO(SupportingDocSearchRQ searchRQ) {
        return masterDataJdbcDao.getPagedSupportingDocDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public SupportingDocDTO saveOrUpdateSupportingDocDTO(SupportingDocDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update support document :{} by :{}", updateDTO, credentialsDTO.getUserName());
        SupportingDoc supportingDoc = null;
        SupportingDocDTO previousDTO = null;
        Date date = new Date();
        boolean isNewSupportingDoc = (updateDTO.getSupportingDocID() == null);
        Long duplicateCount = masterDataJdbcDao.getDuplicateSupportingDocCountByName(updateDTO.getDocumentName());

        if (duplicateCount > 0 && isNewSupportingDoc) {
            LOG.error("ERROR : Duplicate document Name: {} cannot save {}", updateDTO.getDocumentName(), updateDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_SUPPORTING_DOC_NAME_FOUND);
        } else {
            if (!isNewSupportingDoc) {
                previousDTO = this.getSupportingDocByID(updateDTO.getSupportingDocID());

                SupportingDoc supportingDocOld = supportingDocDao.getOne(updateDTO.getSupportingDocID());
                if (supportingDocOld.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive record cannot modified {}", updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_SUPPORT_DOC_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (supportingDocOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = this.masterDataJdbcDao.getPendingSupportDocCount(updateDTO.getSupportingDocID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending modified recode is there {}", updateDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_SUPPORT_DOC_PENDING_APPROVE_RECORD_EXIST);
                    }
                    supportingDoc = new SupportingDoc();
                    supportingDoc.setParentRecordID(updateDTO.getSupportingDocID());
                    supportingDoc.setCreatedBy(credentialsDTO.getUserName());
                    supportingDoc.setCreatedDate(date);
                    supportingDoc.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    supportingDoc = supportingDocOld;
                    supportingDoc.setModifiedBy(credentialsDTO.getUserName());
                    supportingDoc.setModifiedDate(date);
                }
            } else {
                supportingDoc = new SupportingDoc();
                supportingDoc.setCreatedBy(credentialsDTO.getUserName());
                supportingDoc.setCreatedDate(date);
                supportingDoc.setApproveStatus(MasterDataApproveStatus.PENDING);

            }
            supportingDoc.setDocumentName(updateDTO.getDocumentName());
            supportingDoc.setDescription(updateDTO.getDescription());
            supportingDoc.setSupportDocumentType(updateDTO.getSupportDocumentType());
            supportingDoc.setStatus(updateDTO.getStatus());
            supportingDoc = supportingDocDao.saveAndFlush(supportingDoc);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructSupportingDocAudit(new SupportingDocDTO(supportingDoc), previousDTO, credentialsDTO, date, isNewSupportingDoc);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update support document :{} by :{}", updateDTO, credentialsDTO.getUserName());
            return new SupportingDocDTO(supportingDoc);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LoadChangesRS<SupportingDocDTO> loadChangesByID(Integer supportDocumentID) {
        LoadChangesRS<SupportingDocDTO> loadChangesRS = new LoadChangesRS<>();

        SupportingDoc updatedSupportingDoc = this.supportingDocDao.getOne(supportDocumentID);
        loadChangesRS.setUpdatedDTO(new SupportingDocDTO(updatedSupportingDoc));

        if (updatedSupportingDoc.getParentRecordID() != null) {
            SupportingDoc parentSupportingDoc = this.supportingDocDao.getOne(supportDocumentID);
            loadChangesRS.setPreviousDTO(new SupportingDocDTO(parentSupportingDoc));
        }

        return loadChangesRS;
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public CreditFacilityTypeDTO getCreditFacilityTypeByID(Integer creditFacilityTypeID) {
        CreditFacilityType creditFacilityType = creditFacilityTypeDao.getOne(creditFacilityTypeID);
        return new CreditFacilityTypeDTO(creditFacilityType);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<CreditFacilityTypeDTO> getPagedCreditFacilityTypeDTO(CreditFacilityTypeSearchRQ searchRQ) {
        return masterDataJdbcDao.getPagedCreditFacilityTypeDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CreditFacilityTypeDTO saveOrUpdateCreditFacilityTypeDTO(CreditFacilityTypeDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update credit facility type :{} by :{}", updateDTO, credentialsDTO.getUserName());
        CreditFacilityType creditFacilityType = null;
        CreditFacilityTypeDTO previousDTO = null;
        Date date = new Date();
        boolean isNew = updateDTO.getCreditFacilityTypeID() == null;
        Long duplicateCount = masterDataJdbcDao.getDuplicateCreditFacilityTypeCountByName(updateDTO.getFacilityTypeName());

        if (duplicateCount > 0 && isNew) {
            LOG.error("ERROR : Duplicate document Name: {} cannot save {}", updateDTO.getFacilityTypeName(), updateDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_CREDIT_FACILITY_TYPE_NAME_FOUND);
        } else {

            if (!isNew) {
                previousDTO = this.getCreditFacilityTypeByID(updateDTO.getCreditFacilityTypeID());

                CreditFacilityType creditFacilityTypeOld = creditFacilityTypeDao.getOne(updateDTO.getCreditFacilityTypeID());
                if (creditFacilityTypeOld.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive record cannot modified {}", updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_TYPE_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (creditFacilityTypeOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = this.masterDataJdbcDao.getPendingFacilityTypeCount(updateDTO.getCreditFacilityTypeID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending modified recode is there {}", updateDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_TYPE_PENDING_APPROVE_RECORD_EXIST);
                    }
                    creditFacilityType = new CreditFacilityType();
                    creditFacilityType.setParentRecordID(updateDTO.getCreditFacilityTypeID());
                    creditFacilityType.setCreatedBy(credentialsDTO.getUserName());
                    creditFacilityType.setCreatedDate(date);
                    creditFacilityType.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    creditFacilityType = creditFacilityTypeOld;
                    creditFacilityType.setModifiedBy(credentialsDTO.getUserName());
                    creditFacilityType.setModifiedDate(date);
                }
            } else {
                creditFacilityType = new CreditFacilityType();
                creditFacilityType.setCreatedBy(credentialsDTO.getUserName());
                creditFacilityType.setCreatedDate(date);
                creditFacilityType.setApproveStatus(MasterDataApproveStatus.PENDING);

            }

            creditFacilityType.setFacilityTypeName(updateDTO.getFacilityTypeName());
            creditFacilityType.setDescription(updateDTO.getDescription());
            creditFacilityType.setStatus(updateDTO.getStatus());
            creditFacilityType = creditFacilityTypeDao.saveAndFlush(creditFacilityType);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCreditFacilityTypeAudit(new CreditFacilityTypeDTO(creditFacilityType), previousDTO, credentialsDTO, date, isNew);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update credit facility type :{} by :{}", updateDTO, credentialsDTO.getUserName());
            return new CreditFacilityTypeDTO(creditFacilityType);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LoadChangesRS<CreditFacilityTypeDTO> loadChangesFacilityTypeByID(Integer creditFacilityTypeID) {
        LoadChangesRS<CreditFacilityTypeDTO> loadChangesRS = new LoadChangesRS<>();

        CreditFacilityType updateCreditFacilityType = this.creditFacilityTypeDao.getOne(creditFacilityTypeID);
        loadChangesRS.setUpdatedDTO(new CreditFacilityTypeDTO(updateCreditFacilityType));

        if (updateCreditFacilityType.getParentRecordID() != null) {
            CreditFacilityType parentCreditFacilityType = this.creditFacilityTypeDao.getOne(creditFacilityTypeID);
            loadChangesRS.setPreviousDTO(new CreditFacilityTypeDTO(parentCreditFacilityType));
        }

        return loadChangesRS;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<UserDaDTO> getPagedUserDaDTO(UserDaSearchRQ userDaSearchRQ) {
        return masterDataJdbcDao.getPagedUserDaDTO(userDaSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<UserDaDTO> getAllDAUsers() {
        List<UserDaDTO> userDaDTOList = new ArrayList();
        List<UserDa> userDaList = this.userDaDao.findByStatus(AppsConstants.Status.ACT);

        for (UserDa userDA : userDaList) {
            UserDaDTO userDaDTO = new UserDaDTO(userDA);
            userDaDTOList.add(userDaDTO);
        }
        return userDaDTOList;
        //return userDaDao.findByStatus(AppsConstants.Status.ACT);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LoadChangesRS<UserDaDTO> loadChangesUserDaByID(Integer userdaID) {
        LoadChangesRS<UserDaDTO> loadChangesRS = new LoadChangesRS<>();

        UserDa updatedUserDa = this.userDaDao.getOne(userdaID);
        loadChangesRS.setUpdatedDTO(new UserDaDTO(updatedUserDa));


        if (updatedUserDa.getParentRecordID() != null) {
            UserDa parentUserDa = this.userDaDao.getOne(userdaID);
            loadChangesRS.setPreviousDTO(new UserDaDTO(parentUserDa));
        }

        return loadChangesRS;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public UserDaDTO getUserDaByID(Integer userDaID) {
        UserDa userDa = userDaDao.getOne(userDaID);
        return new UserDaDTO(userDa);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UserDaDTO saveOrUpdateUserDaDTO(UserDaDTO userDaDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update UserDA :{} by :{}", userDaDTO, credentialsDTO.getUserName());
        if (userDaDTO.getCleanAmount() != null && userDaDTO.getCleanAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new AppsException("Clean amount should be positive!");
        }
        UserDa userDa = null;
        UserDaDTO previousDTO = null;
        Date date = new Date();
        boolean isNewUserDa = (userDaDTO.getUserDaID() == null);

        if(userDaDTO.getUserName().isEmpty()){
            throw new AppsException("User name is empty!");
        }

        String stringCheckUsername =
                userDaDTO.getUserName().replaceAll("\\s+", "").toLowerCase();

        Long duplicateCount = masterDataJdbcDao.getDuplicateUserDaCountByName(stringCheckUsername);

        if (duplicateCount > 0 && isNewUserDa) {
            LOG.error("ERROR : Duplicate userDa Name: {} cannot save {}", userDaDTO.getUserName(), userDaDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_USER_DA_NAME_FOUND);
        } else {
            if (!isNewUserDa) {
                previousDTO = this.getUserDaByID(userDaDTO.getUserDaID());

                UserDa userDaOld = userDaDao.getOne(userDaDTO.getUserDaID());
                if (userDaOld.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive UserDA cannot modified {}", userDaDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_USER_DA_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (userDaOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = masterDataJdbcDao.getPendingUserDaCount(userDaDTO.getUserDaID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending UserDA modified record  there {}", userDaDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_USER_DA_PENDING_APPROVE_RECORD_EXIST);

                    }
                    userDa = new UserDa();
                    userDa.setParentRecordID(userDaDTO.getUserDaID());
                    userDa.setCreatedBy(credentialsDTO.getUserName());
                    userDa.setCreatedDate(date);
                    userDa.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    userDa = userDaOld;
                    userDa.setModifiedBy(credentialsDTO.getUserName());
                    userDa.setModifiedDate(date);
                }
            } else {
                userDa = new UserDa();
                userDa.setCreatedBy(credentialsDTO.getUserName());
                userDa.setCreatedDate(date);
                userDa.setApproveStatus(MasterDataApproveStatus.PENDING);
            }

            userDa.setUserName(userDaDTO.getUserName());
            userDa.setMaxAmount(userDaDTO.getMaxAmount());
            userDa.setCleanAmount(userDaDTO.getCleanAmount());
            userDa.setDescription(userDaDTO.getDescription());
            userDa.setStatus(userDaDTO.getStatus());
            userDa = userDaDao.saveAndFlush(userDa);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructUserDaAudit(new UserDaDTO(userDa), previousDTO, credentialsDTO, date, isNewUserDa);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update UserDA :{} by :{}", userDaDTO, credentialsDTO.getUserName());
            return new UserDaDTO(userDa);
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public CreditFacilityTemplateDTO getCreditFacilityTemplateByID(Integer creditFacilityTemplateID) {
        CreditFacilityTemplate creditFacilityTemplate = creditFacilityTemplateDao.getOne(creditFacilityTemplateID);
        return new CreditFacilityTemplateDTO(creditFacilityTemplate);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<CreditFacilityTemplateDTO> getPagedCreditFacilityTemplateDTO(CreditFacilityTemplateSearchRQ searchRQ) {
        return masterDataJdbcDao.getCreditFacilityTemplateDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LoadChangesRS<CreditFacilityTemplateDTO> loadChangesFacilityTemplateByID(Integer creditFacilityTemplateID) {
        LoadChangesRS<CreditFacilityTemplateDTO> loadChangesRS = new LoadChangesRS<>();

        CreditFacilityTemplate creditFacilityTemplate = this.creditFacilityTemplateDao.getOne(creditFacilityTemplateID);
        loadChangesRS.setUpdatedDTO(new CreditFacilityTemplateDTO(creditFacilityTemplate));

        if (creditFacilityTemplate.getParentRecordID() != null) {
            CreditFacilityTemplate parentCreditFacilityTemplate = this.creditFacilityTemplateDao.getOne(creditFacilityTemplateID);
            loadChangesRS.setPreviousDTO(new CreditFacilityTemplateDTO(parentCreditFacilityTemplate));
        }

        return loadChangesRS;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CreditFacilityTemplateDTO saveOrUpdateCreditFacilityTemplateDTO(CreditFacilityTemplateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update credit facility template: {} by :{}", updateDTO, credentialsDTO.getUserName());
        CreditFacilityTemplate creditFacilityTemplate = null;
        CreditFacilityTemplateDTO previousDTO = null;
        Date date = new Date();

        boolean isNewCreditFacilityTemplate = updateDTO.getCreditFacilityTemplateID() == null;
        Long duplicateCount = masterDataJdbcDao.getDuplicateCftCountByNameAndFacilityType(updateDTO.getCreditFacilityName(), updateDTO.getCreditFacilityTypeID());

        if (duplicateCount > 0 && isNewCreditFacilityTemplate) {
            LOG.error("ERROR : Duplicate credit Facility Name: {} and Facility Type: {} cannot save {}", updateDTO.getCreditFacilityName(), updateDTO.getFacilityTypeName(), updateDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_CREDIT_FACILITY_TEMPLATE_NAME_FOUND);
        } else {

            if (!isNewCreditFacilityTemplate) {
                previousDTO = this.getCreditFacilityTemplateByID(updateDTO.getCreditFacilityTemplateID());

                CreditFacilityTemplate creditFacilityTemplateOld = creditFacilityTemplateDao.getOne(updateDTO.getCreditFacilityTemplateID());
                if (creditFacilityTemplateOld.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive record cannot modified {}", updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_TEMPLATE_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (creditFacilityTemplateOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = this.masterDataJdbcDao.getPendingFacilityTemplateCount(updateDTO.getCreditFacilityTemplateID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending modified recode is there {}", updateDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_TEMPLATE_PENDING_APPROVE_RECORD_EXIST);
                    }
                    creditFacilityTemplate = new CreditFacilityTemplate();
                    creditFacilityTemplate.setParentRecordID(updateDTO.getCreditFacilityTemplateID());
                    creditFacilityTemplate.setCreatedBy(credentialsDTO.getUserName());
                    creditFacilityTemplate.setCreatedDate(date);
                    creditFacilityTemplate.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    creditFacilityTemplate = creditFacilityTemplateOld;
                    creditFacilityTemplate.setModifiedBy(credentialsDTO.getUserName());
                    creditFacilityTemplate.setModifiedDate(date);
                }
            } else {
                creditFacilityTemplate = new CreditFacilityTemplate();
                creditFacilityTemplate.setCreatedBy(credentialsDTO.getUserName());
                creditFacilityTemplate.setCreatedDate(date);
                creditFacilityTemplate.setApproveStatus(MasterDataApproveStatus.PENDING);

            }

            creditFacilityTemplate.setCreditFacilityName(updateDTO.getCreditFacilityName());
            creditFacilityTemplate.setDescription(updateDTO.getDescription());
            creditFacilityTemplate.setMaxFacilityAmount(updateDTO.getMaxFacilityAmount());
            creditFacilityTemplate.setMinFacilityAmount(updateDTO.getMinFacilityAmount());
            creditFacilityTemplate.setShowCondition(updateDTO.getShowCondition());
            creditFacilityTemplate.setShowPurpose(updateDTO.getShowPurpose());
            creditFacilityTemplate.setShowRemark(updateDTO.getShowRemark());
            creditFacilityTemplate.setShowCalculator(updateDTO.getShowCalculator());
            creditFacilityTemplate.setShowRentalData(updateDTO.getShowRentalData());
            creditFacilityTemplate.setShowRepayment(updateDTO.getShowRepayment());
            creditFacilityTemplate.setStatus(updateDTO.getStatus());
            creditFacilityTemplate.setShowInLead(updateDTO.getShowInLead());

            if (updateDTO.getCreditFacilityTypeID() != null) {
                CreditFacilityType creditFacilityType = creditFacilityTypeDao.getOne(updateDTO.getCreditFacilityTypeID());
                creditFacilityTemplate.setCreditFacilityType(creditFacilityType);
            }

            if ((creditFacilityTemplate.getCreditFacilityType() != null && creditFacilityTemplate.getCreditFacilityType().getFacilityTypeName().equals("Lease")) ||
                    (creditFacilityTemplate.getCreditFacilityName() != null && (creditFacilityTemplate.getCreditFacilityName().equals("Samachara Loan With Facility")
                            || creditFacilityTemplate.getCreditFacilityName().equals("Samachara Loan Without Facility")))) {
                creditFacilityTemplate.setNewFacilityEmail(this.ccduEmail);
            }

            for (CftInterestRateDTO cftInterestRateDTO : updateDTO.getCftInterestRateDTOList()) {
                boolean isNewInterestRate = cftInterestRateDTO.getCftInterestRateID() == null;
                boolean isNewCFT = creditFacilityTemplate.getCreditFacilityTemplateID() == null;
                CftInterestRate interestRate = null;
                CftInterestRateDTO previousInterestRateDTO = null;

                Long duplicateRateCount = masterDataJdbcDao.getDuplicateCftInterestRateByNameAndCftID(cftInterestRateDTO.getRateName(), creditFacilityTemplate.getCreditFacilityTemplateID());

                if (duplicateRateCount > 0 && isNewInterestRate) {
                    LOG.error("ERROR : Duplicate Credit Facility Type InterestRate Name: {} and credit Facility TemplateID: {} cannot save {}", cftInterestRateDTO.getRateName(), cftInterestRateDTO.getCreditFacilityTemplateID(), updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_CFT_RATE_NAME_FOUND);
                } else {

                    if (isNewInterestRate || isNewCFT) {
                        interestRate = new CftInterestRate();
                        interestRate.setCreatedBy(credentialsDTO.getUserName());
                        interestRate.setCreatedDate(date);
                        interestRate.setCreditFacilityTemplate(creditFacilityTemplate);
                        interestRate.setApproveStatus(MasterDataApproveStatus.PENDING);
                        creditFacilityTemplate.getCftInterestRates().add(interestRate);
                    } else {
                        previousInterestRateDTO = this.getCftInterestRateByID(cftInterestRateDTO.getCftInterestRateID());
                        interestRate = creditFacilityTemplate.getCftInterestRateByID(cftInterestRateDTO.getCftInterestRateID());
                        interestRate.setCreatedBy(credentialsDTO.getUserName());
                        interestRate.setCreatedDate(date);
                        interestRate.setApproveStatus(MasterDataApproveStatus.PENDING);

                    }
                    interestRate.setRateName(cftInterestRateDTO.getRateName());
                    interestRate.setRateCode(cftInterestRateDTO.getRateCode());
                    interestRate.setIsDefault(cftInterestRateDTO.getIsDefault());
                    interestRate.setValue(cftInterestRateDTO.getValue());
                    interestRate.setStatus(cftInterestRateDTO.getStatus());
                    interestRate.setInterestRatingSubCategory(cftInterestRateDTO.getInterestRatingSubCategory());
                    interestRate.setIsEditable(cftInterestRateDTO.getIsEditable());

                    //audit
                    WebAuditDTO webAuditDTO = WebAuditUtils.constructCftInterestRateAudit(new CftInterestRateDTO(interestRate), previousInterestRateDTO, credentialsDTO, date, isNewInterestRate, webAuditJDBCDao);
                    webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
                }
            }

            for (CftVitalInfoDTO cftVitalInfoDTO : updateDTO.getCftVitalInfoDTOList()) {
                boolean isNewVitalInfoDTO = cftVitalInfoDTO.getCftVitalInfoID() == null || creditFacilityTemplate.getCreditFacilityTemplateID() == null;
                CftVitalInfo cftVitalInfo = null;
                CftInterestRateDTO previousInterestRateDTO = null;

                //TODO: find Dulicate Vital Names
                Long duplicateVtInfoCount = 0L;
                //masterDataJdbcDao.getDuplicateCftInterestRateByNameAndCftID(cftInterestRateDTO.getRateName(), cftInterestRateDTO.getCreditFacilityTemplateID());

                if (duplicateVtInfoCount > 0 && isNewVitalInfoDTO) {
                    LOG.error("ERROR : Duplicate Credit Facility Type vital info Name: {} and credit Facility TemplateID: {} cannot save {}", cftVitalInfoDTO.getVitalInfoName(), cftVitalInfoDTO.getCreditFacilityTemplateID(), updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_CFT_RATE_NAME_FOUND);
                } else {

                    if (isNewVitalInfoDTO) {
                        cftVitalInfo = new CftVitalInfo();
                        cftVitalInfo.setCreatedBy(credentialsDTO.getUserName());
                        cftVitalInfo.setCreatedDate(date);
                        cftVitalInfo.setCreditFacilityTemplate(creditFacilityTemplate);
                        creditFacilityTemplate.getCftVitalInfos().add(cftVitalInfo);
                    } else {
                        cftVitalInfo = creditFacilityTemplate.getCftVitalInfoByID(cftVitalInfoDTO.getCftVitalInfoID());
                        cftVitalInfo.setCreatedBy(credentialsDTO.getUserName());
                        cftVitalInfo.setCreatedDate(date);

                    }
                    cftVitalInfo.setVitalInfoName(cftVitalInfoDTO.getVitalInfoName());
                    cftVitalInfo.setMandatory(cftVitalInfoDTO.getMandatory());
                    cftVitalInfo.setDisplayOrder(cftVitalInfoDTO.getDisplayOrder());
                    cftVitalInfo.setStatus(cftVitalInfoDTO.getStatus());
                    //TODO audit need to update
                }
            }

            for (CftCustomFacilityInfoDTO cftCustomFacilityInfoDTO : updateDTO.getCftCustomFacilityInfoDTOList()) {
                boolean isNewCustomFacilityInfoDTO = cftCustomFacilityInfoDTO.getCftCustomFacilityInfoID() == null || creditFacilityTemplate.getCreditFacilityTemplateID() == null;
                CftCustomFacilityInfo cftCustomFacilityInfo = null;

                //TODO: find Dulicate Custom facility Names
                Long duplicateCFCount = 0L;

                if (duplicateCFCount > 0 && isNewCustomFacilityInfoDTO) {
                    LOG.error("ERROR : Duplicate Credit Facility Type custom facility info Name: {} and credit Facility TemplateID: {} cannot save {}", cftCustomFacilityInfo.getCustomFacilityInfoName(), cftCustomFacilityInfoDTO.getCreditFacilityTemplateID(), updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_CFT_RATE_NAME_FOUND);
                } else {
                    if (isNewCustomFacilityInfoDTO) {
                        cftCustomFacilityInfo = new CftCustomFacilityInfo();
                        cftCustomFacilityInfo.setCreatedBy(credentialsDTO.getUserName());
                        cftCustomFacilityInfo.setCreatedDate(date);
                        cftCustomFacilityInfo.setCreditFacilityTemplate(creditFacilityTemplate);
                        creditFacilityTemplate.getCftCustomFacilityInfos().add(cftCustomFacilityInfo);
                    } else {
                        cftCustomFacilityInfo = creditFacilityTemplate.getCftCustomFacilityInfoByID(cftCustomFacilityInfoDTO.getCftCustomFacilityInfoID());
                        cftCustomFacilityInfo.setCreatedBy(credentialsDTO.getUserName());
                        cftCustomFacilityInfo.setCreatedDate(date);
                    }

                    cftCustomFacilityInfo.setCustomFacilityInfoName(cftCustomFacilityInfoDTO.getCustomFacilityInfoName());
                    cftCustomFacilityInfo.setCustomFacilityInfoCode(cftCustomFacilityInfoDTO.getCustomFacilityInfoCode());
                    cftCustomFacilityInfo.setDescription(cftCustomFacilityInfoDTO.getDescription());
                    cftCustomFacilityInfo.setFieldType(cftCustomFacilityInfoDTO.getFieldType());
                    cftCustomFacilityInfo.setMandatory(cftCustomFacilityInfoDTO.getMandatory());
                    cftCustomFacilityInfo.setStatus(cftCustomFacilityInfoDTO.getStatus());
                    cftCustomFacilityInfo.setDisplayOrder(cftCustomFacilityInfoDTO.getDisplayOrder());
                }
            }

            for (CftSupportingDocDTO cftSupportingDocDTO : updateDTO.getCftSupportingDocDTOList()) {
                boolean isNewSupDoc = cftSupportingDocDTO.getCftSupportingDocID() == null || creditFacilityTemplate.getCreditFacilityTemplateID() == null;
                ;
                CftSupportingDoc cftSupportingDoc = null;
                CftSupportingDocDTO previousCftSupportingDocDTO = null;
                CftSupportingDoc previousCftSupportingDoc = null;
                if (isNewSupDoc) {
                    cftSupportingDoc = new CftSupportingDoc();
                    cftSupportingDoc.setCreatedBy(credentialsDTO.getUserName());
                    cftSupportingDoc.setCreatedDate(date);
                    cftSupportingDoc.setCreditFacilityTemplate(creditFacilityTemplate);
                    creditFacilityTemplate.getCftSupportingDocs().add(cftSupportingDoc);
                } else {
                    previousCftSupportingDoc = cftSupportingDocDao.getOne(cftSupportingDocDTO.getCftSupportingDocID());
                    previousCftSupportingDocDTO = new CftSupportingDocDTO(previousCftSupportingDoc);

                    cftSupportingDoc = creditFacilityTemplate.getCftSupportingDocByID(cftSupportingDocDTO.getCftSupportingDocID());
                    cftSupportingDoc.setCreatedBy(credentialsDTO.getUserName());
                    cftSupportingDoc.setCreatedDate(date);
                    cftSupportingDoc.setApproveStatus(MasterDataApproveStatus.PENDING);

                }
                cftSupportingDoc.setMandatory(cftSupportingDocDTO.getMandatory());
                cftSupportingDoc.setSupportingDoc(this.supportingDocDao.getOne(cftSupportingDocDTO.getSupportingDocID()));
                cftSupportingDoc.setStatus(cftSupportingDocDTO.getStatus());

                //Audit
                WebAuditDTO webAuditDTO = WebAuditUtils.constructCftSupportingDocAudit(new CftSupportingDocDTO(cftSupportingDoc), previousCftSupportingDocDTO, credentialsDTO, date, isNewSupDoc, webAuditJDBCDao);
                webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            }

            for (CftOtherFacilityInfoDTO facilityInfoDTO : updateDTO.getCftOtherFacilityInfoDTOList()) {
                boolean isNewFacInfo = facilityInfoDTO.getCftOtherFacilityInfoID() == null || creditFacilityTemplate.getCreditFacilityTemplateID() == null;

                CftOtherFacilityInformation facilityInformation = null;
                CftOtherFacilityInfoDTO previousFacInfo = null;

                Long duplicateFacCount = masterDataJdbcDao.getDuplicateOtherFacilityInfoByNameAndCftID
                        (facilityInfoDTO.getOtherFacilityInfoName(), creditFacilityTemplate.getCreditFacilityTemplateID());

                if (duplicateFacCount > 0 && isNewFacInfo) {
                    LOG.error("ERROR : Duplicate Other Facility Information Name Found: {} and credit Facility TemplateID: {} cannot save {}",
                            facilityInfoDTO.getOtherFacilityInfoName(), facilityInfoDTO.getCreditFacilityTemplateID(), updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_CFT_RATE_NAME_FOUND);
                } else {

                    if (isNewFacInfo) {

                        facilityInformation = new CftOtherFacilityInformation();
                        facilityInformation.setCreatedDate(date);
                        facilityInformation.setCreatedBy(credentialsDTO.getUserName());
                        facilityInformation.setCreditFacilityTemplate(creditFacilityTemplate);
                        facilityInformation.setOtherFacilityInfoCode(facilityInfoDTO.getOtherFacilityInfoCode());
                        creditFacilityTemplate.getCftOtherFacilityInformations().add(facilityInformation);
                    } else {

                        facilityInformation = this.getOtherInfoDTOByID(facilityInfoDTO.getCftOtherFacilityInfoID());
                        facilityInformation.setModifiedBy(credentialsDTO.getUserName());
                        facilityInformation.setModifiedDate(date);

                        previousFacInfo = new CftOtherFacilityInfoDTO(facilityInformation);
                    }

                    facilityInformation.setOtherFacilityInfoName(facilityInfoDTO.getOtherFacilityInfoName());
                    facilityInformation.setDescription(facilityInfoDTO.getDescription());
                    facilityInformation.setOtherFacilityInfoFieldType(facilityInfoDTO.getOtherFacilityInfoFieldType());
                    facilityInformation.setDefaultValue(facilityInfoDTO.getDefaultValue());
                    facilityInformation.setDisplayOrder(facilityInfoDTO.getDisplayOrder());
                    facilityInformation.setMandatory(facilityInfoDTO.getMandatory());
                    facilityInformation.setStatus(facilityInfoDTO.getStatus());

                    //TODO audit need to update
                }
            }

            creditFacilityTemplate = this.creditFacilityTemplateDao.saveAndFlush(creditFacilityTemplate);
            updateDTO = new CreditFacilityTemplateDTO(creditFacilityTemplate);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCreditFacilityTemplateAudit(new CreditFacilityTemplateDTO(creditFacilityTemplate), previousDTO, credentialsDTO, date, isNewCreditFacilityTemplate);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update credit facility template :{} by :{}", updateDTO, credentialsDTO.getUserName());
            return new CreditFacilityTemplateDTO(creditFacilityTemplate);
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public String getOFICode() throws AppsException {
        String ref = null;
        synchronized (guard) {
            ref = ofiCodeProc.executeFunction();
        }
        return ref;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public CftInterestRateDTO getCftInterestRateByID(Integer cftInterestRateID) {
        CftInterestRate cftInterestRate = cftInterestRateDao.getOne(cftInterestRateID);
        return new CftInterestRateDTO(cftInterestRate);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public CftOtherFacilityInformation getOtherInfoDTOByID(Integer otherInfoID) {
        return otherFacilityInfoDao.getOne(otherInfoID);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<CftInterestRateDTO> getPagedCftInterestRateDTO(CftInterestRateSearchRQ searchRQ) {
        return masterDataJdbcDao.getPagedCftInterestRateDTO(searchRQ);
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public UpmGroupDTO getUpmGroupByID(Integer upmGroupID) {
        UpmGroup upmGroup = upmGroupDao.getOne(upmGroupID);
        return new UpmGroupDTO(upmGroup);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<UpmGroupDTO> getPagedUpmGroupDTO(UpmGroupSearchRQ searchRQ) {
        return masterDataJdbcDao.getPagedUpmGroupDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpmGroupDTO saveOrUpdateUpmGroupDTO(UpmGroupDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Upm group :{} by :{}", updateDTO, credentialsDTO.getUserName());
        UpmGroup upmGroup = null;
        UpmGroupDTO previousDTO = null;
        Date date = new Date();
        boolean isNewUpmGroup = updateDTO.getUpmGroupID() == null;
        Long duplicateCount = masterDataJdbcDao.getDuplicateUpmGroupCountByCode(updateDTO.getGroupCode());

        if (duplicateCount > 0 && isNewUpmGroup) {
            LOG.error("ERROR : Duplicate group code: {} cannot save {}", updateDTO.getGroupCode(), updateDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_UPM_GROUP_CODE_FOUND);
        } else {

            if (!isNewUpmGroup) {
                previousDTO = this.getUpmGroupByID(updateDTO.getUpmGroupID());

                UpmGroup upmGroupOld = upmGroupDao.getOne(updateDTO.getUpmGroupID());
                if (upmGroupOld.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive record cannot modified {}", updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_UPM_GROUP_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (upmGroupOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = this.masterDataJdbcDao.getPendingUpmGroupCount(updateDTO.getUpmGroupID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending modified recode is there {}", updateDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_UPM_GROUP_PENDING_APPROVE_RECORD_EXIST);
                    }
                    upmGroup = new UpmGroup();
                    upmGroup.setParentRecordID(updateDTO.getUpmGroupID());
                    upmGroup.setCreatedBy(credentialsDTO.getUserName());
                    upmGroup.setCreatedDate(date);
                    upmGroup.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    upmGroup = upmGroupOld;
                    upmGroup.setModifiedBy(credentialsDTO.getUserName());
                    upmGroup.setModifiedDate(date);
                }
            } else {
                upmGroup = new UpmGroup();
                upmGroup.setCreatedBy(credentialsDTO.getUserName());
                upmGroup.setCreatedDate(date);
                upmGroup.setApproveStatus(MasterDataApproveStatus.PENDING);

            }

            upmGroup.setGroupCode(updateDTO.getGroupCode());
            upmGroup.setReferenceName(updateDTO.getReferenceName());
            upmGroup.setWorkFlowLevel(updateDTO.getWorkFlowLevel());
            upmGroup.setStatus(updateDTO.getStatus());
            upmGroup = upmGroupDao.saveAndFlush(upmGroup);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructUpmGroupAudit(new UpmGroupDTO(upmGroup), previousDTO, credentialsDTO, date, isNewUpmGroup);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update Upm group :{} by :{}", updateDTO, credentialsDTO.getUserName());
            return new UpmGroupDTO(upmGroup);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LoadChangesRS<UpmGroupDTO> loadChangesUpmGroupByID(Integer upmGroupID) {
        LoadChangesRS<UpmGroupDTO> loadChangesRS = new LoadChangesRS<>();

        UpmGroup updatedUpmGroup = this.upmGroupDao.getOne(upmGroupID);
        loadChangesRS.setUpdatedDTO(new UpmGroupDTO(updatedUpmGroup));

        if (updatedUpmGroup.getParentRecordID() != null) {
            UpmGroup parentUpmGroup = this.upmGroupDao.getOne(upmGroupID);
            loadChangesRS.setPreviousDTO(new UpmGroupDTO(parentUpmGroup));
        }

        return loadChangesRS;
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public WorkFlowTemplateDTO getWorkFlowTemplateByID(Integer WorkFlowTemplateID) {
        WorkFlowTemplate workFlowTemplate = workFlowTemplateDao.getOne(WorkFlowTemplateID);
        return new WorkFlowTemplateDTO(workFlowTemplate);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<WorkFlowTemplateDTO> getPagedWorkFlowTemplateDTO(WorkFlowTemplateSearchRQ searchRQ) {
        return masterDataJdbcDao.getPagedWorkFlowTemplateDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public WorkFlowTemplateDTO saveOrUpdateWorkFlowTemplateDTO(WorkFlowTemplateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update work Flow template :{} by :{}", updateDTO, credentialsDTO.getUserName());
        WorkFlowTemplate workFlowTemplate = null;
        WorkFlowTemplateDTO previousDTO = null;
        Date date = new Date();
        boolean isNewWorkFlowTemplate = updateDTO.getWorkFlowTemplateID() == null;
        Long duplicateCount = masterDataJdbcDao.getDuplicateWorkFlowTemplateCountByName(updateDTO.getWorkFlowTemplateName());

        if (duplicateCount > 0 && isNewWorkFlowTemplate) {
            LOG.error("ERROR : Duplicate template name: {} cannot save {}", updateDTO.getWorkFlowTemplateName(), updateDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_WORK_FLOW_TEMPLATE_NAME_FOUND);
        } else {

            if (!isNewWorkFlowTemplate) {
                previousDTO = this.getWorkFlowTemplateByID(updateDTO.getWorkFlowTemplateID());

                WorkFlowTemplate workFlowTemplateOld = workFlowTemplateDao.getOne(updateDTO.getWorkFlowTemplateID());
                if (workFlowTemplateOld.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive record cannot modified {}", updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_WORK_FLOW_TEMPLATE_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (workFlowTemplateOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = this.masterDataJdbcDao.getPendingWorkFlowTemplateCount(updateDTO.getWorkFlowTemplateID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending modified recode is there {}", updateDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_WORK_FLOW_TEMPLATE_PENDING_APPROVE_RECORD_EXIST);
                    }
                    workFlowTemplate = new WorkFlowTemplate();
                    workFlowTemplate.setParentRecordID(updateDTO.getWorkFlowTemplateID());
                    workFlowTemplate.setCreatedBy(credentialsDTO.getUserName());
                    workFlowTemplate.setCreatedDate(date);
                    workFlowTemplate.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    workFlowTemplate = workFlowTemplateOld;
                    workFlowTemplate.setModifiedBy(credentialsDTO.getUserName());
                    workFlowTemplate.setModifiedDate(date);
                }
            } else {
                workFlowTemplate = new WorkFlowTemplate();
                workFlowTemplate.setCreatedBy(credentialsDTO.getUserName());
                workFlowTemplate.setCreatedDate(date);
                workFlowTemplate.setApproveStatus(MasterDataApproveStatus.PENDING);

            }

            for (WorkFlowTemplateDataDTO dataDTO : updateDTO.getWorkFlowTemplateDataDTOList()) {
                boolean isNewWFData = workFlowTemplate.getWorkFlowTemplateID() == null || dataDTO.getWorkFlowTemplateDataID() == null;
                WorkFlowTemplateData templateData = null;
                if (isNewWFData && !dataDTO.isRemoved()) {
                    templateData = new WorkFlowTemplateData();
                    workFlowTemplate.addWorkFlowTemplateData(templateData);
                } else if (dataDTO.isRemoved()) {
                    templateData = workFlowTemplate.getWorkFlowTemplateDataByID(dataDTO.getWorkFlowTemplateDataID());
                    workFlowTemplate.getWorkFlowTemplateDataSet().remove(templateData);
                    continue;
                } else {
                    templateData = workFlowTemplate.getWorkFlowTemplateDataByID(dataDTO.getWorkFlowTemplateDataID());
                }
                templateData.setDisplayOrder(dataDTO.getDisplayOrder());
                templateData.setUpmGroup(this.upmGroupDao.getOne(dataDTO.getUpmGroupID()));
                if (dataDTO.getNextUPMGroupID() != null) {
                    templateData.setNextUPMGroup(this.upmGroupDao.getOne(dataDTO.getNextUPMGroupID()));
                }
                if (dataDTO.getPreviousUPMGroupID() != null) {
                    templateData.setPreviousUPMGroup(this.upmGroupDao.getOne(dataDTO.getPreviousUPMGroupID()));
                }
            }

            workFlowTemplate.setCode(updateDTO.getCode());
            workFlowTemplate.setDescription(updateDTO.getDescription());
            workFlowTemplate.setWorkFlowTemplateName(updateDTO.getWorkFlowTemplateName());
            workFlowTemplate.setStatus(updateDTO.getStatus());
            workFlowTemplate = workFlowTemplateDao.saveAndFlush(workFlowTemplate);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructWorkFlowTemplateAudit(new WorkFlowTemplateDTO(workFlowTemplate), previousDTO, credentialsDTO, date, isNewWorkFlowTemplate);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update work Flow template :{} by :{}", updateDTO, credentialsDTO.getUserName());
            return new WorkFlowTemplateDTO(workFlowTemplate);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LoadChangesRS<WorkFlowTemplateDTO> loadChangesWorkFlowTemplateByID(Integer workFlowTemplateID) {
        LoadChangesRS<WorkFlowTemplateDTO> loadChangesRS = new LoadChangesRS<>();

        WorkFlowTemplate updatedWorkFlowTemplate = this.workFlowTemplateDao.getOne(workFlowTemplateID);
        loadChangesRS.setUpdatedDTO(new WorkFlowTemplateDTO(updatedWorkFlowTemplate));

        if (updatedWorkFlowTemplate.getParentRecordID() != null) {
            WorkFlowTemplate parentWorkFlowTemplate = this.workFlowTemplateDao.getOne(workFlowTemplateID);
            loadChangesRS.setPreviousDTO(new WorkFlowTemplateDTO(parentWorkFlowTemplate));
        }

        return loadChangesRS;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpcSectionDTO saveOrUpdateUpcSectionDTO(UpcSectionDTO upcSectionDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update UPC_SECTION :{} by :{}", upcSectionDTO, credentialsDTO.getUserName());
        UpcSection upcSection = null;
        UpcSectionDTO previousDTO = null;
        Date date = new Date();

        boolean isNewUpcSection = upcSectionDTO.getUpcSectionID() == null;
        Long duplicateCount = masterDataJdbcDao.getDuplicateUpcSectionCountByName(upcSectionDTO.getUpcSectionName());

        if (duplicateCount > 0 && isNewUpcSection) {
            LOG.error("ERROR : Duplicate template name: {} cannot save {}", upcSectionDTO.getUpcSectionName(), upcSectionDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_UPC_SECTION_NAME_FOUND);
        } else {

            if (!isNewUpcSection) {
                previousDTO = this.getUpcSectionDTOByID(upcSectionDTO.getUpcSectionID());
                UpcSection oldUpcSection = upcSectionDao.getOne(upcSectionDTO.getUpcSectionID());
                if (oldUpcSection.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive UPC_SECTION cannot modified {}", upcSectionDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_UPC_SECTION_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (oldUpcSection.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = masterDataJdbcDao.getPendingUpcSectionCount(upcSectionDTO.getUpcSectionID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending UPC_SECTION modified record  there {}", upcSectionDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_UPC_SECTION_PENDING_APPROVE_RECORD_EXIST);

                    }
                    upcSection = new UpcSection();
                    upcSection.setParentRecordID(upcSectionDTO.getUpcSectionID());
                    upcSection.setCreatedBy(credentialsDTO.getUserName());
                    upcSection.setCreatedDate(date);
                    upcSection.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    upcSection = oldUpcSection;
                    upcSection.setModifiedBy(credentialsDTO.getUserName());
                    upcSection.setModifiedDate(date);
                }
            } else {
                upcSection = new UpcSection();
                upcSection.setCreatedBy(credentialsDTO.getUserName());
                upcSection.setCreatedDate(date);
                upcSection.setApproveStatus(MasterDataApproveStatus.PENDING);
            }
            upcSection.setUpcSectionName(upcSectionDTO.getUpcSectionName());
            upcSection.setUpcSectionDescription(upcSectionDTO.getUpcSectionDescription());
            upcSection.setStatus(upcSectionDTO.getStatus());
            upcSection = upcSectionDao.saveAndFlush(upcSection);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructUpcSectionDataAudit(new UpcSectionDTO(upcSection), previousDTO, credentialsDTO, date, isNewUpcSection);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update UPC_SECTION :{} by :{}", upcSectionDTO, credentialsDTO.getUserName());
            return new UpcSectionDTO(upcSection);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<UpcSectionDTO> getPagedUpcSectionDTO(UpcSectionSearchRQ upcSectionSearchRQ) {
        return masterDataJdbcDao.getPagedUpcSectionDTO(upcSectionSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpcSectionDTO getUpcSectionDTOByID(Integer upcSectionID) {
        UpcSection upcSection = upcSectionDao.getOne(upcSectionID);
        return new UpcSectionDTO(upcSection);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LoadChangesRS<UpcSectionDTO> loadChangesUpcSectionByID(Integer upcSectionID) {
        LoadChangesRS<UpcSectionDTO> loadChangesRS = new LoadChangesRS<>();

        UpcSection updatedUpcSection = this.upcSectionDao.getOne(upcSectionID);
        loadChangesRS.setUpdatedDTO(new UpcSectionDTO(updatedUpcSection));

        if (updatedUpcSection.getParentRecordID() != null) {
            UpcSection parentUpcSection = this.upcSectionDao.getOne(upcSectionID);
            loadChangesRS.setPreviousDTO(new UpcSectionDTO(parentUpcSection));
        }
        return loadChangesRS;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpcTemplateDTO getUpcTemplateDTOByID(Integer upcTemplateID) {
        UpcTemplate upcTemplate = upcTemplateDao.getOne(upcTemplateID);
        return new UpcTemplateDTO(upcTemplate);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<UpcTemplateDTO> getPagedUpcTemplateDTO(UpcTemplateSearchRQ searchRQ) {
        return masterDataJdbcDao.getPagedUpcTemplateDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpcTemplateDTO saveOrUpdateUpcTemplateDTO(UpcTemplateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update UpcTemplate :{} by :{}", updateDTO, credentialsDTO.getUserName());
        UpcTemplate upcTemplate = null;
        UpcTemplateDTO previousDTO = null;
        Date date = new Date();

        boolean isNewUpcTemplate = updateDTO.getUpcTemplateID() == null;
        Long duplicateCount = masterDataJdbcDao.getDuplicateUpcTemplateCountByName(updateDTO.getTemplateName());

        if (duplicateCount > 0 && isNewUpcTemplate) {
            LOG.error("ERROR : Duplicate template name: {} cannot save {}", updateDTO.getTemplateName(), updateDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_UPC_TEMPLATE_NAME_FOUND);
        } else {

            if (!isNewUpcTemplate) {
                previousDTO = this.getUpcTemplateDTOByID(updateDTO.getUpcTemplateID());

                UpcTemplate upcTemplateOld = upcTemplateDao.getOne(updateDTO.getUpcTemplateID());
                if (upcTemplateOld.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive record cannot modified {}", updateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_UPC_TEMPLATE_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (upcTemplateOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = this.masterDataJdbcDao.getPendingUpcTemplateCount(updateDTO.getUpcTemplateID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending modified recode is there {}", updateDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_UPC_TEMPLATE_PENDING_APPROVE_RECORD_EXIST);
                    }
                    upcTemplate = new UpcTemplate();
                    upcTemplate.setParentRecordID(updateDTO.getUpcTemplateID());
                    upcTemplate.setCreatedBy(credentialsDTO.getUserName());
                    upcTemplate.setCreatedDate(date);
                    upcTemplate.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    upcTemplate = upcTemplateOld;
                    upcTemplate.setModifiedBy(credentialsDTO.getUserName());
                    upcTemplate.setModifiedDate(date);
                }
            } else {
                upcTemplate = new UpcTemplate();
                upcTemplate.setCreatedBy(credentialsDTO.getUserName());
                upcTemplate.setCreatedDate(date);
                upcTemplate.setApproveStatus(MasterDataApproveStatus.PENDING);
            }

            for (UpcTemplateDataDTO dataDTO : updateDTO.getUpcTemplateDataDTOList()) {
                boolean isNewUtData = dataDTO.getUpcTemplateDataID() == null;
                UpcTemplateData templateData = null;
                if (isNewUtData || upcTemplate.getUpcTemplateID() == null) {
                    templateData = new UpcTemplateData();
                    upcTemplate.addUpcTemplateData(templateData);
                } else if (dataDTO.isRemoved()) {
                    templateData = upcTemplate.getUpcTemplateDataByID(dataDTO.getUpcTemplateDataID());
                    upcTemplate.getUpcTemplateDataSet().remove(templateData);
                    continue;
                } else {
                    templateData = upcTemplate.getUpcTemplateDataByID(dataDTO.getUpcTemplateDataID());
                }
                templateData.setDisplayOrder(dataDTO.getDisplayOrder());
                templateData.setParentSectionID(dataDTO.getParentSectionID());
                templateData.setSectionLevel(dataDTO.getSectionLevel());
                templateData.setUpcSection(this.upcSectionDao.getOne(dataDTO.getUpcSectionID()));
            }

            upcTemplate.setTemplateName(updateDTO.getTemplateName());
            upcTemplate.setDescription(updateDTO.getDescription());
            upcTemplate.setStatus(updateDTO.getStatus());
            upcTemplate.setUpcLabel(updateDTO.getUpcLabel());
            upcTemplate.setUpcLabelBackgroundColor(updateDTO.getUpcLabelBackgroundColor());
            upcTemplate.setUpcLabelFontColor(updateDTO.getUpcLabelFontColor());
            upcTemplate.setNotifyEmails(updateDTO.getEmails());
            upcTemplate = upcTemplateDao.saveAndFlush(upcTemplate);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructUpcTemplateAudit(new UpcTemplateDTO(upcTemplate), previousDTO, credentialsDTO, date, isNewUpcTemplate);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            LOG.info("END: Save update UpcTemplate :{} by :{}", updateDTO, credentialsDTO.getUserName());
            return new UpcTemplateDTO(upcTemplate);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LoadChangesRS<UpcTemplateDTO> loadChangesUpcTemplateByID(Integer upcTemplateID) {
        LoadChangesRS<UpcTemplateDTO> loadChangesRS = new LoadChangesRS<>();

        UpcTemplate updatedUpcTemplate = this.upcTemplateDao.getOne(upcTemplateID);
        loadChangesRS.setUpdatedDTO(new UpcTemplateDTO(updatedUpcTemplate));

        if (updatedUpcTemplate.getParentRecordID() != null) {
            UpcTemplate parentUpcTemplate = this.upcTemplateDao.getOne(upcTemplateID);
            loadChangesRS.setPreviousDTO(new UpcTemplateDTO(parentUpcTemplate));
        }
        return loadChangesRS;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CreditFacilityTypeDTO> getApprovedCreditFacilityType() throws AppsException {
        return masterDataJdbcDao.getApprovedCreditFacilityType();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<SupportingDocDTO> getApprovedSupportingDoc() throws AppsException {
        return masterDataJdbcDao.getApprovedSupportingDoc();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<GlobalSupportingDocDTO> getApprovedGlobalSupportingDoc() throws AppsException {
        return masterDataJdbcDao.getApprovedGlobalSupportingDoc();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<PurposeOfAdvancedDTO> getAllPurposeOfAdvanced() throws AppsException {
        return masterDataJdbcDao.getAllPurposeOfAdvanced();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<SectorDTO> getAllSectorData() throws AppsException {
        return masterDataJdbcDao.getAllSectorData();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<UpcSectionDTO> getAllUpcSectionData() throws AppsException {
        return masterDataJdbcDao.getAllUpcSectionData();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public SubSectorDataRS getAllSubSectorData() throws AppsException {
        return masterDataJdbcDao.getAllSubSectorData();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<UpmGroupDTO> getAllApprovedUPMGroups() throws AppsException {

        List<UpmGroupDTO> approvedUpmGroupDTOList = new ArrayList<>();
        List<UpmGroup> approvedUPMGroup = upmGroupDao.findAllByApproveStatus(MasterDataApproveStatus.APPROVED);

        if (approvedUPMGroup != null && !approvedUPMGroup.isEmpty()) {
            for (UpmGroup entity : approvedUPMGroup) {
                approvedUpmGroupDTOList.add(new UpmGroupDTO(entity));
            }
        }
        return approvedUpmGroupDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<UpmGroupDTO> getAllowApprovedUPMGroupsForLoginUser(FPReviewSummarySearchRQ searchRQ) throws AppsException {
        List<UpmGroupDTO> upmGroupDTOS = new ArrayList<>();
        for (String s : masterDataJdbcDao.getAllowApprovedUPMGroupsForLoginUser(searchRQ)) {
            UpmGroupDTO upmGroupDTO = new UpmGroupDTO();
            upmGroupDTO.setGroupCode(s);
            upmGroupDTOS.add(upmGroupDTO);
        }
        return upmGroupDTOS;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<CreditFacilityTemplateDTO> getApprovedCreditFacilityTemplates() throws AppsException, IOException, SAXException {
        List<CreditFacilityTemplateDTO> approvedCreditFacilityTemplateList = new ArrayList<>();

//        List<CreditFacilityTemplate> allCreditFacilityTemplateList = creditFacilityTemplateDao.findAll();

//        if (!(allCreditFacilityTemplateList.isEmpty())) {
//            for (CreditFacilityTemplate template : allCreditFacilityTemplateList) {
//                if (template.getCreditFacilityType() != null) {
//                    if (template.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
//                        approvedCreditFacilityTemplateList.add(new CreditFacilityTemplateDTO(template));
//                    }
//                }
//            }
//        }

        List<CreditFacilityTemplate> allCreditFacilityTemplateList = creditFacilityTemplateDao.findAllByAndApproveStatus(DomainConstants.MasterDataApproveStatus.APPROVED);

        if (!(allCreditFacilityTemplateList.isEmpty())) {
            for (CreditFacilityTemplate template : allCreditFacilityTemplateList) {
                List<Formula> formulaList = new CalculatorUtil().parseXml(environment, AppsConstants.FacilityType.LEASE.getType(), AppsConstants.CalculatorType.STRUCTURED.getType());
                approvedCreditFacilityTemplateList.add(new CreditFacilityTemplateDTO(template, formulaList));
            }
        }
        return approvedCreditFacilityTemplateList;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpcTemplateDTO approveUpcTemplateDTO(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Approve/Reject update UpcTemplate :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        UpcTemplateDTO previousDTO = new UpcTemplateDTO(upcTemplateDao.getOne(updateDTO.getApproveRejectDataID()));

        UPCTemplateApproveHandler approveHandler = new UPCTemplateApproveHandler();
        approveHandler.setApproveRejectRQ(updateDTO);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setUpcTemplateDao(upcTemplateDao);
        approveHandler.transitStatus();
        UpcTemplate result = (UpcTemplate) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectUpcTemplateAudit(new UpcTemplateDTO(result), previousDTO, credentialsDTO, date, updateDTO.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Approve/Reject update UpcTemplate :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return new UpcTemplateDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpcSectionDTO approveUpcSectionDTO(ApproveRejectRQ approveRejectRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Upc Section :{} by :{}", approveRejectRQ, credentialsDTO.getUserName());
        Date date = new Date();
        UpcSectionDTO previousDTO = new UpcSectionDTO(upcSectionDao.getOne(approveRejectRQ.getApproveRejectDataID()));

        UPCSectionApproveHandler approveHandler = new UPCSectionApproveHandler();
        approveHandler.setApproveRejectRQ(approveRejectRQ);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setUpcSectionDao(upcSectionDao);
        approveHandler.setUpcTemplateDao(upcTemplateDao);
        approveHandler.setUpcTemplateDataDao(upcTemplateDataDao);
        approveHandler.transitStatus();
        UpcSection result = (UpcSection) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectUpcSectionAudit(new UpcSectionDTO(result), previousDTO,
                credentialsDTO, date, approveRejectRQ.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update  UPC_SECTION:{} by :{}", approveRejectRQ, credentialsDTO.getUserName());
        return new UpcSectionDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public WorkFlowTemplateDTO approveWorkFlowTemplateDTO(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update work Flow template :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        WorkFlowTemplateDTO previousDTO = new WorkFlowTemplateDTO(workFlowTemplateDao.getOne(updateDTO.getApproveRejectDataID()));

        WorkFlowApproveHandler approveHandler = new WorkFlowApproveHandler();
        approveHandler.setApproveRejectRQ(updateDTO);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setWorkFlowTemplateDao(workFlowTemplateDao);
        approveHandler.transitStatus();
        WorkFlowTemplate result = (WorkFlowTemplate) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectWorkFlowTemplateAudit(new WorkFlowTemplateDTO(result),
                previousDTO, credentialsDTO, date, updateDTO.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update work Flow template :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return new WorkFlowTemplateDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UpmGroupDTO approveUpmGroupDTO(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Upm group :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        UpmGroupDTO previousDTO = new UpmGroupDTO(upmGroupDao.getOne(updateDTO.getApproveRejectDataID()));

        UPMGroupApproveHandler approveHandler = new UPMGroupApproveHandler();
        approveHandler.setApproveRejectRQ(updateDTO);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setUpmGroupDao(upmGroupDao);
        approveHandler.setWorkFlowTemplateDao(workFlowTemplateDao);
        approveHandler.setWorkFlowTemplateDataDao(workFlowTemplateDataDao);
        approveHandler.transitStatus();
        UpmGroup result = (UpmGroup) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectUpmGroupAudit(new UpmGroupDTO(result), previousDTO,
                credentialsDTO, date, updateDTO.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update Upm group :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return new UpmGroupDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CreditFacilityTemplateDTO approveCreditFacilityTemplateDTO(ApproveRejectRQ approveRejectRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update credit facility template :{} by :{}", approveRejectRQ, credentialsDTO.getUserName());
        Date date = new Date();
        CreditFacilityTemplateDTO previousDTO = new CreditFacilityTemplateDTO(creditFacilityTemplateDao.getOne(approveRejectRQ.getApproveRejectDataID()));

        CFTemplateApproveHandler approveHandler = new CFTemplateApproveHandler();
        approveHandler.setApproveRejectRQ(approveRejectRQ);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setCreditFacilityTemplateDao(creditFacilityTemplateDao);
        approveHandler.transitStatus();
        CreditFacilityTemplate result = (CreditFacilityTemplate) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectCreditFacilityTemplateAudit(new CreditFacilityTemplateDTO(result),
                previousDTO, credentialsDTO, date, approveRejectRQ.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update credit facility template:{} by :{}", approveRejectRQ, credentialsDTO.getUserName());

        return new CreditFacilityTemplateDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UserDaDTO approveUserDaDTO(ApproveRejectRQ approveRejectRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update UserDA :{} by :{}", approveRejectRQ, credentialsDTO.getUserName());
        Date date = new Date();
        UserDaDTO previousDTO = new UserDaDTO(userDaDao.getOne(approveRejectRQ.getApproveRejectDataID()));

        UserDaApproveHandler approveHandler = new UserDaApproveHandler();
        approveHandler.setApproveRejectRQ(approveRejectRQ);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setUserDaDao(userDaDao);
        approveHandler.transitStatus();
        UserDa result = (UserDa) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectUserDaAudit(new UserDaDTO(result), previousDTO, credentialsDTO, date, approveRejectRQ.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update UserDA :{} by :{}", approveRejectRQ, credentialsDTO.getUserName());
        return new UserDaDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CreditFacilityTypeDTO approveCreditFacilityTypeDTO(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update credit facility type :{} by :{}", updateDTO, credentialsDTO.getUserName());

        Date date = new Date();
        CreditFacilityTypeDTO previousDTO = new CreditFacilityTypeDTO(creditFacilityTypeDao.getOne(updateDTO.getApproveRejectDataID()));

        CreditFacilityTypeApproveHandler approveHandler = new CreditFacilityTypeApproveHandler();
        approveHandler.setApproveRejectRQ(updateDTO);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setCreditFacilityTemplateDao(creditFacilityTemplateDao);
        approveHandler.setSupportingDocDao(supportingDocDao);
        approveHandler.setCreditFacilityTypeDao(creditFacilityTypeDao);
        approveHandler.transitStatus();
        CreditFacilityType result = (CreditFacilityType) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectCreditFacilityTypeAudit(new CreditFacilityTypeDTO(result), previousDTO, credentialsDTO, date, updateDTO.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update credit facility type:{} by :{}", updateDTO, credentialsDTO.getUserName());

        return new CreditFacilityTypeDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public SupportingDocDTO approveSupportingDocDTO(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update support document :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        SupportingDocDTO previousDTO = new SupportingDocDTO(supportingDocDao.getOne(updateDTO.getApproveRejectDataID()));

        SupportingDocApproveHandler approveHandler = new SupportingDocApproveHandler();
        approveHandler.setApproveRejectRQ(updateDTO);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setCftSupportingDocDao(cftSupportingDocDao);
        approveHandler.setCreditFacilityTemplateDao(creditFacilityTemplateDao);
        approveHandler.setCreditFacilityTypeDao(creditFacilityTypeDao);
        approveHandler.setSupportingDocDao(supportingDocDao);
        approveHandler.transitStatus();
        SupportingDoc supportingDoc = (SupportingDoc) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectSupportingDocAudit(new SupportingDocDTO(supportingDoc), previousDTO, credentialsDTO, date, updateDTO.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update support document :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return new SupportingDocDTO(supportingDoc);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public RoleDTO approveRoleDTO(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update support document :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        RoleDTO previousDTO = new RoleDTO(roleDao.getOne(updateDTO.getApproveRejectDataID()));

        RoleApproveHandler approveHandler = new RoleApproveHandler();
        approveHandler.setApproveRejectRQ(updateDTO);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setRoleDao(roleDao);
        approveHandler.transitStatus();
        Role role = (Role) approveHandler.getResponse();

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructApproveRejectRoleAudit(new RoleDTO(role), previousDTO, credentialsDTO, date, updateDTO.getApproveStatus());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update support document :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return new RoleDTO(role);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<WorkFlowTemplateDTO> getAllWorkFlowTemplates() throws AppsException {
        return masterDataJdbcDao.getAllWorkFlowTemplates();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<UpmGroupDTO> getUpmGroupByWorkFlowTemplateID(Integer workFlowTemplateID) throws AppsException {
        return masterDataJdbcDao.getUpmGroupByWorkFlowTemplateID(workFlowTemplateID);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<UpmGroupDTO> getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(LoggedInUserWorkFlowRQ loggedInUserWorkFlowRQ) throws AppsException {
        return masterDataJdbcDao.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(loggedInUserWorkFlowRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CftInterestRateDTO> getAllCftInterestRateDTOS() throws AppsException {

        List<CftInterestRateDTO> cftInterestRateDTOS = new ArrayList<>();
        List<CftInterestRate> cftInterestRateList = cftInterestRateDao.findAll();

        for (CftInterestRate cftInterestRate : cftInterestRateList) {
            if (cftInterestRate.getCftInterestRateID() != null) {
                if (cftInterestRate.getStatus() == AppsConstants.Status.ACT) {
                    cftInterestRateDTOS.add(new CftInterestRateDTO(cftInterestRate));
                }
            }
        }
        return cftInterestRateDTOS;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<SecuritySummaryTopicDTO> getActiveSecuritySummaryTopics() throws AppsException {
        return masterDataJdbcDao.getActiveSecuritySummaryTopics();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<AFTopicDTO> getApprovedApplicationFormTopics() throws AppsException {

        List<AFTopicDTO> afTopicDTOS = new ArrayList<>();
        List<AFTopic> afTopics = afTopicDao.findAllByApproveStatusAndStatus(MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT);

        for (AFTopic afTopic : afTopics) {
            afTopicDTOS.add(new AFTopicDTO(afTopic));
        }
        return afTopicDTOS;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<AFTopicDTO> getPagedAFTopics(AFTopicSearchRQ searchRQ) {
        return masterDataJdbcDao.getPagedAFTopics(searchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CASBranchDepartmentDTO> getCasActiveBranchDepartmentList() {
        return masterDataJdbcDao.getCasActiveBranchDepartmentList();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public AFTopicDTO getAFTopicByID(Integer afTopicID) {
        AFTopic afTopic = afTopicDao.getOne(afTopicID);
        return new AFTopicDTO(afTopic);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public AFTopicDTO saveOrUpdateAFTopic(AFTopicDTO afTopicDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Application Form Topic :{} by :{}", afTopicDTO, credentialsDTO.getUserName());
        AFTopic afTopic = null;
        Date date = new Date();

        boolean isNewTopic = afTopicDTO.getTopicID() == null;
        Long duplicateCount = masterDataJdbcDao.getDuplicateAFTopicsCountByTopicORCode(afTopicDTO.getTopic(), afTopicDTO.getTopicCode());

        if (duplicateCount > 0 && isNewTopic) {
            LOG.error("ERROR : Duplicate Application Form Topic Name Or Code : {} cannot save {}", afTopicDTO.getTopic(), afTopicDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATE_APPLICATION_FORM_TOPIC_FOUND);
        } else {

            if (!isNewTopic) {
                AFTopic oldAfTopic = afTopicDao.getOne(afTopicDTO.getTopicID());
                if (oldAfTopic.getStatus() == AppsConstants.Status.INA) {
                    LOG.error("ERROR : Inactive Topic cannot modified {}", afTopicDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INACTIVE_RECODE_CANNOT_MODIFIED);
                }
                if (oldAfTopic.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                    boolean hasAlreadyPendingRecords = masterDataJdbcDao.getPendingAFTopicCount(afTopicDTO.getTopicID()) > 0;
                    if (hasAlreadyPendingRecords) {
                        LOG.error("ERROR : Already pending Topic modified record there {}", afTopicDTO);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_PENDING_APPROVE_RECORD_EXIST);
                    }
                    afTopic = new AFTopic();
                    afTopic.setParentRecordID(afTopicDTO.getTopicID());
                    afTopic.setCreatedBy(credentialsDTO.getUserName());
                    afTopic.setCreatedDate(date);
                    afTopic.setApproveStatus(MasterDataApproveStatus.PENDING);
                } else {
                    afTopic = oldAfTopic;
                    afTopic.setModifiedBy(credentialsDTO.getUserName());
                    afTopic.setModifiedDate(date);
                }
            } else {
                afTopic = new AFTopic();
                afTopic.setCreatedBy(credentialsDTO.getUserName());
                afTopic.setCreatedDate(date);
                afTopic.setApproveStatus(MasterDataApproveStatus.PENDING);
            }

            for (AFTopicUpcSectionDTO afTopicUpcSectionDTO : afTopicDTO.getAfTopicUpcSectionDTOList()) {
                boolean isNewTopicSectionMapping = afTopicUpcSectionDTO.getTopicUpcSectionID() == null;
                AFTopicUpcSection afTopicUpcSection;

                if (isNewTopicSectionMapping) {
//                    UPC Section with a UPC template Has only One mapped topic (1 to 1)
//                    The Following is for restrict the adding Same UPC Template and Same UPC Section to Multiple Topics
                    if (!isNewTopic && afTopicUpcSectionDTO.getStatus() == AppsConstants.Status.ACT) {
                        afTopicUpcSection = afTopicUpcSectionDao.findByUpcTemplateIDAndUpcSectionIDAndStatus(afTopicUpcSectionDTO.getUpcTemplateID(), afTopicUpcSectionDTO.getUpcSectionID(), AppsConstants.Status.ACT);
                        if (afTopicUpcSection != null) {
                            LOG.error("ERROR : Already Mapped UPC section  {} for the Topic {} modified record there {}", afTopicUpcSectionDTO.getUpcSectionName(), afTopic.getTopic(), afTopicUpcSectionDTO);
                            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_ALREADY_MAPPED_UPC_SECTION);
                        }
                    }
                    afTopicUpcSection = new AFTopicUpcSection();
                } else {
                    afTopicUpcSection = afTopicUpcSectionDao.getOne(afTopicUpcSectionDTO.getTopicUpcSectionID());
                }
                afTopicUpcSection.setStatus(afTopicUpcSectionDTO.getStatus());
                afTopicUpcSection.setUpcSectionID(afTopicUpcSectionDTO.getUpcSectionID());
                afTopicUpcSection.setUpcSectionName(afTopicUpcSectionDTO.getUpcSectionName());
                afTopicUpcSection.setUpcTemplateID(afTopicUpcSectionDTO.getUpcTemplateID());
                afTopicUpcSection.setUpcTemplateName(afTopicUpcSectionDTO.getUpcTemplateName());
                afTopic.addTopicUpcSection(afTopicUpcSection);
            }

            afTopic.setPage(afTopicDTO.getPage());
            afTopic.setTopic(afTopicDTO.getTopic());
            afTopic.setTopicCode(afTopicDTO.getTopicCode());
            afTopic.setTopicData(afTopicDTO.getTopicData());
            afTopic.setDescription(afTopicDTO.getDescription());
            afTopic.setStatus(afTopicDTO.getStatus());
            afTopic = afTopicDao.saveAndFlush(afTopic);

            //audit TODO
            LOG.info("END: Save update UPC_SECTION :{} by :{}", afTopicDTO, credentialsDTO.getUserName());
            return new AFTopicDTO(afTopic);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public AFTopicDTO approveAFTopic(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Application Form Topic :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();

        AFTopicApproveHandler approveHandler = new AFTopicApproveHandler();
        approveHandler.setApproveRejectRQ(updateDTO);
        approveHandler.setCredentialsDTO(credentialsDTO);
        approveHandler.setApproveRestrictedForOwnUser(casProperties.getApproveRestrictedForOwnUser());
        approveHandler.setDate(date);

        approveHandler.setAfTopicDao(afTopicDao);
        approveHandler.transitStatus();
        AFTopic result = (AFTopic) approveHandler.getResponse();

        //audit TODO

        LOG.info("END: Save update Application Form Topic :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return new AFTopicDTO(result);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AFTTopicConfigDTO> uploadApplicationTopicConfigFile(AFTopicConfigUploadRQ afTopicConfigUploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Upload Application Form Topic Configurations : {} : {}", afTopicConfigUploadRQ, credentialsDTO);

        List<AFTTopicConfigDTO> aftTopicConfigDTOS = new ArrayList<>();
        List<AFTopicConfigRawDataDTO> afTopicConfigRawDataDTOS;
        List<String> topicCodeList = new ArrayList<>();
        try {
            afTopicConfigRawDataDTOS = convertFileToTargetObject(afTopicConfigUploadRQ.getMultipartFile(), AFTopicConfigRawDataDTO.class);

            List<AFTopicConfig> afTopicConfigs = new ArrayList<>();

            for (AFTopicConfig afTopicConfig : afTopicConfigDao.findAllByStatus(AppsConstants.Status.ACT)) {
                afTopicConfig.setStatus(AppsConstants.Status.INA);
                afTopicConfigs.add(afTopicConfig);
            }

            for (AFTopicConfigRawDataDTO rawDataDTO : afTopicConfigRawDataDTOS) {
                if (topicCodeList.contains(rawDataDTO.getTopicCode())) {
                    LOG.error("ERROR : Duplicate Topic Codes {} : by {}", rawDataDTO.getTopicCode(), credentialsDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_DUPLICATED_TOPIC_CODES);
                } else {
                    topicCodeList.add(rawDataDTO.getTopicCode());
                }

                AFTopicConfig afTopicConfig = new AFTopicConfig();
                afTopicConfig.setTopicCode(rawDataDTO.getTopicCode());
                afTopicConfig.setPage(rawDataDTO.getPage());
                afTopicConfig.setStatus(AppsConstants.Status.ACT);
                afTopicConfig.setCreatedBy(credentialsDTO.getUserName());
                afTopicConfig.setCreatedDate(new Date());
                afTopicConfigs.add(afTopicConfig);
            }

            afTopicConfigDao.saveAll(afTopicConfigs).forEach(e -> {
                if (e.getStatus() == AppsConstants.Status.ACT) {
                    aftTopicConfigDTOS.add(new AFTTopicConfigDTO(e));
                }
            });

            LOG.info("END : Upload Application Form Topic Configurations {} : {} : {} ", topicCodeList, afTopicConfigRawDataDTOS, credentialsDTO);
        } catch (Exception e) {
            LOG.error("ERROR : Upload Application Form Topic Configurations : {} : {}", afTopicConfigUploadRQ, credentialsDTO, e);
            throw new AppsException();
        }
        return aftTopicConfigDTOS;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<AFTTopicConfigDTO> getApplicationTopicConfigs() throws AppsException {
        LOG.info("START : Get Application Topic Configs:");

        List<AFTTopicConfigDTO> result = new ArrayList<>();
        for (AFTopicConfig afTopicConfig : this.afTopicConfigDao.findAllByStatus(AppsConstants.Status.ACT)) {
            result.add(new AFTTopicConfigDTO(afTopicConfig));
        }

        LOG.info("END : Get Application Topic Configs: : {} ", result);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<UpcSectionDTO> getActiveApprovedUpcSectionListByTemplateID(UpcTemplateDTO upcTemplateDTO) throws AppsException {
        return masterDataJdbcDao.getActiveApprovedUpcSectionListByTemplateID(upcTemplateDTO);
    }

    public <T> List<T> convertFileToTargetObject(MultipartFile file, Class<T> target) throws Exception {

        try {
            InputStreamReader streamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(streamReader)
                    .withType(target)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
            LOG.error("Can't convert file to target class: {}", target.getSimpleName(), e);
            throw new AppsException();
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CACommitteeDTO> getCommittees() {
        return masterDataJdbcDao.getCommittees();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CAUserDTO> getCommitteeUsers() {
        return masterDataJdbcDao.getCommitteeUsers();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CALevelDTO> getCommitteeLevels() {
        return masterDataJdbcDao.getCommitteeLevels();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public UserPoolDTO getUserPoolByID(Integer userPoolId) {
        UserPool userPool = userPoolDao.getOne(userPoolId);
        return new UserPoolDTO(userPool);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UserPoolDTO saveUserPool(UserPoolDTO poolDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update UserDA :{} by :{}", poolDTO, credentialsDTO.getUserName());

        boolean isNewUser = (poolDTO.getUserId() == null || poolDTO.getUserId() == 0);
        Long duplicateCount = masterDataJdbcDao.getDuplicateUserPoolCountByName(poolDTO.getUserName());

        if (duplicateCount > 0 && isNewUser) {
            LOG.error("ERROR : Duplicate User Name: {} cannot save {}", poolDTO.getUserName(), poolDTO);
            throw new AppsException("This user already exist in the pool.");
        } else {

            UserPool prevUser = userPoolDao.getOne(poolDTO.getUserId());
            if (prevUser.getUserId() == 0 && isNewUser){
                addApprovedPoolUser(poolDTO, credentialsDTO);
            } else {
               int parentIdExist = masterDataJdbcDao.isTempPoolUserExist(poolDTO.getParentRecordID());
               if (parentIdExist == 0){
                   addTempPoolUser(poolDTO, credentialsDTO);
               } else {
                   LOG.error("ERROR : Pool user already exist in temp table: {}", parentIdExist);
                   throw new AppsException("This pool user already exist in pending approval list.");
               }
            }

            return poolDTO;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void addApprovedPoolUser(UserPoolDTO poolDTO, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();
        UserPool userPool = new UserPool();

        userPool.setPoolId(poolDTO.getPoolId());
        userPool.setUserName(poolDTO.getUserName());
        userPool.setUserDisplayName(poolDTO.getUserDisplayName());
        userPool.setUserStatus(poolDTO.getUserStatus());
        userPool.setGroupCode(poolDTO.getGroupCode());
        userPool.setReferenceName(poolDTO.getReferenceName());
        userPool.setCreatedBy(credentialsDTO.getUserName());
        userPool.setCreatedDate(date);
        userPool.setUserStatus(AppsConstants.Status.ACT);
        userPool.setApproveStatus(MasterDataApproveStatus.APPROVED);

        userPoolDao.saveAndFlush(userPool);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void addTempPoolUser(UserPoolDTO poolDTO, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();
        UserPoolTemp userPool = new UserPoolTemp();

        userPool.setPoolId(poolDTO.getPoolId());
        userPool.setUserName(poolDTO.getUserName());
        userPool.setUserDisplayName(poolDTO.getUserDisplayName());
        userPool.setUserStatus(poolDTO.getUserStatus());
        userPool.setGroupCode(poolDTO.getGroupCode());
        userPool.setReferenceName(poolDTO.getReferenceName());
        userPool.setCreatedBy(credentialsDTO.getUserName());
        userPool.setCreatedDate(date);
        userPool.setUserStatus(poolDTO.getUserStatus());

        if (poolDTO.getUserStatus() == AppsConstants.Status.RMV){
            userPool.setApproveStatus(MasterDataApproveStatus.PENDING_RMV);
        } else {
            userPool.setApproveStatus(MasterDataApproveStatus.PENDING);
        }

        userPool.setParentRecordID(poolDTO.getParentRecordID());

        userPoolTempDao.saveAndFlush(userPool);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UserPoolDTO approveCommitteePoolUsers(UserPoolDTO poolDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update UserDA :{} by :{}", poolDTO, credentialsDTO.getUserName());

        if (poolDTO.getApproveStatus() == MasterDataApproveStatus.APPROVED) {

            if (poolDTO.getUserStatus() == AppsConstants.Status.RMV){
                List<CACommittee> committees = masterDataJdbcDao.getCommitteesByUserName(poolDTO.getUserName());

                if (committees.isEmpty()){
                    userPoolDao.deleteById(poolDTO.getParentRecordID());
                } else {
                    List<String> nameList = new ArrayList<>();

                    for (CACommittee committee: committees){
                        nameList.add(committee.getCommitteeName());
                    }

                    String committee_names = String.join(", ", nameList);

                    LOG.error("ERROR : Pool user exist in committee: {}", poolDTO.getUserName());
                    throw new AppsException("This pool user is a member of the committee. These are the committee(s): "+committee_names);
                }
            } else {
                updateApprovedPoolUser(poolDTO, credentialsDTO);

                if (poolDTO.getGroupCode().equals("80")){
                    if (poolDTO.getUserStatus() == AppsConstants.Status.ACT){
                        changeCurrentPath(poolDTO, credentialsDTO, AppsConstants.CAPathType.REG);
                    } else {
                        changeCurrentPath(poolDTO, credentialsDTO, AppsConstants.CAPathType.ALT);
                    }
                }
            }
        }

        userPoolTempDao.deleteById(poolDTO.getUserId());

        return poolDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void updateApprovedPoolUser(UserPoolDTO poolDTO, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();
        UserPoolTemp userPoolTemp = userPoolTempDao.getOne(poolDTO.getUserId());

        if (userPoolTemp.getUserId() != 0){
            UserPool userPool = userPoolDao.getOne(userPoolTemp.getParentRecordID());

            userPool.setModifiedBy(credentialsDTO.getUserName());
            userPool.setModifiedDate(date);
            userPool.setUserStatus(userPoolTemp.getUserStatus());
            userPool.setApproveStatus(MasterDataApproveStatus.APPROVED);

            userPoolDao.saveAndFlush(userPool);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void changeCurrentPath(UserPoolDTO poolDTO, CredentialsDTO credentialsDTO, AppsConstants.CAPathType path) throws AppsException {

        Date date = new Date();

        List<CACommittee> committees = masterDataJdbcDao.getCommitteesByUserName(poolDTO.getUserName());
        if (!committees.isEmpty()){
            for (CACommittee committee : committees){
                CACommittee committeeRow = subCommitteeDao.getOne(committee.getCommitteeId());
                if (committeeRow.getCommitteeId() != 0){
                    committeeRow.setCurrentPath(path);
                    committeeRow.setModifiedBy(credentialsDTO.getUserName());
                    committeeRow.setModifiedDate(date);
                    subCommitteeDao.save(committeeRow);
                }
                if (path.equals(AppsConstants.CAPathType.ALT)) {
                 changeCommitteePaperCurrentLevel(committee.getCommitteeId(), path);
                }
            }
        }
    }

    public List<UserPool> getUserList() {

        List<UserPool> userPools = userPoolDao.findAll();
        List<UserPoolTemp> userPoolTemps = userPoolTempDao.findAll();
        List<UserPool> pendingUsers = userPoolTemps.stream().map(userPool -> modelMapper.map(userPool, UserPool.class)).collect(Collectors.toList());

        userPools.addAll(pendingUsers);

        return userPools;
    }

    public CommitteeType saveCommitteeType(CommitteeType committeeType, CredentialsDTO credentialsDTO) throws AppsException {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Date date = new Date();
        booleanBuilder.and(QCommitteeType.committeeType.committeeTypeName.eq(committeeType.getCommitteeTypeName()));
        List<CommitteeType> committeeTypes = (List<CommitteeType>) committeeTypeDao.findAll(booleanBuilder);

        if (!committeeTypes.isEmpty()) {
            throw new AppsException("Committee type is already exists.");
        }

        committeeType.setIsSystem(committeeType.getIsSystem());
        committeeType.setCreatedBy(credentialsDTO.getUserName());
        committeeType.setCreatedDate(date);

        return committeeTypeDao.save(committeeType);
    }

    public List<CommitteeType> getCommitteeTypes() {
        return committeeTypeDao.findAll();
    }

    public CommitteeType updateCommitteeType(CommitteeType committeeType, CredentialsDTO credentialsDTO) throws AppsException {
        Date date = new Date();
        CommitteeType committeeTypeDb = committeeTypeDao.findById(committeeType.getCommitteeTypeId())
                .orElseThrow(() -> new AppsException("Committee type with " + committeeType.getCommitteeTypeId() + " does not exist."));

        List<CommitteeType> duplicateTypes = masterDataJdbcDao.getDuplicateCommitteeType(committeeType.getCommitteeTypeName());
        CommitteeType duplicateType = !duplicateTypes.isEmpty() ? duplicateTypes.get(0) : null;

        if (!duplicateTypes.isEmpty()) {
            if (!Objects.equals(duplicateType.getCommitteeTypeId(), committeeType.getCommitteeTypeId())){
                throw new AppsException("Committee type is already exists");
            }
        }

        if (committeeType.getStatus() == AppsConstants.Status.INA){

            List<CACommittee> committees = masterDataJdbcDao.getCommitteesByType(committeeType.getCommitteeTypeId());
            List<CACommittee> tempCommittees = masterDataJdbcDao.getTempCommitteesByType(committeeType.getCommitteeTypeId());

            if (committees.isEmpty() && tempCommittees.isEmpty()){
                
                committeeTypeDb.setCommitteeTypeName(committeeType.getCommitteeTypeName());
                committeeTypeDb.setCommitteeDescription(committeeType.getCommitteeDescription());
                committeeTypeDb.setStatus(committeeType.getStatus());
                committeeTypeDb.setCreatedUserDisplayName(committeeType.getCreatedUserDisplayName());
                committeeTypeDb.setModifiedBy(credentialsDTO.getUserName());
                committeeTypeDb.setModifiedDate(date);
                committeeTypeDb.setIsSystem(committeeType.getIsSystem());

                committeeTypeDao.save(committeeTypeDb);
            }else {
                throw new AppsException("Couldn't inactive this committee type since type already exists in sub committee.");
            }
        } else {
            committeeTypeDb.setCommitteeTypeName(committeeType.getCommitteeTypeName());
            committeeTypeDb.setCommitteeDescription(committeeType.getCommitteeDescription());
            committeeTypeDb.setStatus(committeeType.getStatus());
            committeeTypeDb.setCreatedUserDisplayName(committeeType.getCreatedUserDisplayName());
            committeeTypeDb.setModifiedBy(credentialsDTO.getUserName());
            committeeTypeDb.setModifiedDate(date);
            committeeTypeDb.setIsSystem(committeeType.getIsSystem());

            committeeTypeDao.save(committeeTypeDb);
        }

        return committeeType;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<CACommitteeDTO> getSubCommittees() {

        List<CACommitteeDTO> caCommittees = new ArrayList<>();

        List<CACommittee> approvedCommittees = subCommitteeDao.findAll();
        List<CACommitteeTemp> tempCommittees = subCommitteeTempDao.findAll();
        List<CACommittee> pendingCommittees = tempCommittees.stream().map(committee -> modelMapper.map(committee, CACommittee.class)).collect(Collectors.toList());

        approvedCommittees.addAll(pendingCommittees);

        caCommittees = approvedCommittees.stream().map(committee -> modelMapper.map(committee, CACommitteeDTO.class)).collect(Collectors.toList());

        return caCommittees;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CACommitteeDTO saveCommittee(CACommitteeDTO subCommitteeDTO, CredentialsDTO credentialsDTO) throws AppsException {

        boolean isNewSubCommittee = subCommitteeDTO.getCommitteeId() == null || subCommitteeDTO.getCommitteeId() == 0;
        Long duplicateCount = masterDataJdbcDao.getDuplicateSbCountByNameAndCommitteeType(subCommitteeDTO.getCommitteeName(), subCommitteeDTO.getCommitteeId());

        if (duplicateCount > 0 && isNewSubCommittee) {
            LOG.info("ERROR : Duplicate committee names found : {}", subCommitteeDTO.getCommitteeName());
        } else {
            if (!subCommitteeDTO.getTableType().isEmpty()){

                // get temp record id if parent id not null
                int parentExistCommitteeId = 0;

                if (subCommitteeDTO.getParentRecordID() != 0){
                    parentExistCommitteeId = masterDataJdbcDao.isTempCommitteeExist(subCommitteeDTO.getParentRecordID());
                }

                if (subCommitteeDTO.getTableType().equals("TEMP")){
                    CACommitteeTemp prevTempCommittee = subCommitteeTempDao.getOne(subCommitteeDTO.getCommitteeId());

                    if (prevTempCommittee.getCommitteeId() == 0 && isNewSubCommittee) {

                        if(parentExistCommitteeId == 0){
                            //add new temp committee
                            addTempCommittee(subCommitteeDTO,credentialsDTO);
                        } else {
                            LOG.info("ERROR : Record already exist in temp committee table : {}", parentExistCommitteeId);
                            throw new AppsException("This committee already exist in pending approval list.");
                        }
                    } else {
                        //update existing temp committee
                        updateTempCommittee(subCommitteeDTO,credentialsDTO);
                    }
                } else {
                    CACommittee prevConfigCommittee = subCommitteeDao.getOne(subCommitteeDTO.getCommitteeId());
                    if (prevConfigCommittee.getCommitteeId() != 0) {

                        if(parentExistCommitteeId == 0){
                            //add new committee
                            addTempCommittee(subCommitteeDTO,credentialsDTO);
                        } else {
                            LOG.info("ERROR : Record already exist in temp committee table : {}", parentExistCommitteeId);
                            throw new AppsException("This committee already exist in pending approval list.");
                        }
                    }
                }
            } else {
                LOG.info("ERROR : Table type is empty : {}", subCommitteeDTO.getCommitteeName());
                throw new AppsException("An error occurred. Please try again later.");
            }
        }

        return subCommitteeDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CACommitteeDTO addTempCommittee(CACommitteeDTO subCommitteeDTO, CredentialsDTO credentialsDTO) throws AppsException {
        Date date = new Date();
        CACommitteeTemp subCommittee = new CACommitteeTemp();

            if (subCommitteeDTO.getCommitteeTypeId() != 0){
                CommitteeType committeeType = committeeTypeDao.findById(subCommitteeDTO.getCommitteeTypeId())
                        .orElseThrow(() -> new AppsException("CommitteeType not found"));

                subCommittee.setCommitteeType(committeeType);
            }

            subCommittee.setCommitteeName(subCommitteeDTO.getCommitteeName());
            subCommittee.setDelegatedAuthority(subCommitteeDTO.getDelegatedAuthority());
            subCommittee.setCreatedDate(date);
            subCommittee.setCreatedBy(credentialsDTO.getUserName());
            subCommittee.setStatus(subCommitteeDTO.getStatus());
            subCommittee.setReviewer(subCommitteeDTO.getReviewer());
            subCommittee.setCurrentPath(subCommitteeDTO.getCurrentPath());
            subCommittee.setCommitteeStatus(subCommitteeDTO.getCommitteeStatus());
            subCommittee.setApproveStatus(MasterDataApproveStatus.PENDING);
            subCommittee.setParentRecordID(subCommitteeDTO.getParentRecordID());

            subCommittee = subCommitteeTempDao.saveAndFlush(subCommittee);

            List<CALevelTemp> caLevelList = new ArrayList<>();
            List<CAUserTemp> caUserList = new ArrayList<>();

            for(CALevelDTO caLevelDTO : subCommitteeDTO.getCaLevelDTOList()) {
                CALevelTemp caLevel = new CALevelTemp();

                if (caLevelDTO.getActionStatus() != AppsConstants.CACommitteeStatus.DELETE) {

                    caLevel.setCaCommitteeTemp(subCommittee);
                    caLevel.setLevelId(caLevelDTO.getLevelId());
                    caLevel.setCombination(caLevelDTO.getCombination());
                    caLevel.setStatus(caLevelDTO.getStatus());
                    caLevel.setPathType(caLevelDTO.getPathType());
                    caLevel.setUserCount(caLevelDTO.getUserCount());
                    caLevel.setCreatedBy(credentialsDTO.getUserName());
                    caLevel.setCreatedDate(date);

                    caLevelList.add(caLevel);

                    for (CAUserDTO caUserDTO : caLevelDTO.getCaUserDTOList()) {
                        if (caUserDTO.getActionStatus() != AppsConstants.CACommitteeStatus.DELETE) {

                            UserPool userPool = userPoolDao.findById(caUserDTO.getUserId())
                                .orElseThrow(() -> new AppsException("Pool not found"));

                            CAUserTemp caUser = new CAUserTemp();

                            caUser.setCaCommitteeTemp(subCommittee);
                            caUser.setUserPool(userPool);
                            caUser.setCaLevelTemp(caLevel);
                            caUser.setUserStatus(caUserDTO.getUserStatus());
                            caUser.setCreatedBy(credentialsDTO.getUserName());
                            caUser.setCreatedDate(date);

                            boolean isUserAlreadyInLevel = caUserList.stream()
                                .anyMatch(caUser1 ->
                                    caUser1.getCaLevelTemp().getPathType().equals(caLevelDTO.getPathType()) &&
                                            caUser1.getUserPool().getUserName().equals(caUserDTO.getUserName()));

                            LOG.info("isUserAlreadyInLevel {}", isUserAlreadyInLevel);

                            if (isUserAlreadyInLevel) {
                                throw new AppsException("A user cannot be in two levels in the same path type.");
                            } else {
                                caUserList.add(caUser);
                            }
                        }
                    }
                }
            }

            if(!caLevelList.isEmpty()){

                caLevelList = caLevelTempDao.saveAll(caLevelList);
                subCommittee.setCaLevelTempList(caLevelList);

                if(!caUserList.isEmpty()){
                    caUserTempDao.saveAll(caUserList);
                }
            }

            //save committee comment
            CACommitteeCommentDTO comment = new CACommitteeCommentDTO();

            comment.setCreatedBy(credentialsDTO.getUserName());
            comment.setCreatedDate(date);
            if(subCommitteeDTO.getCommitteeStatus() == AppsConstants.CACommitteeStatus.DRAFT){
                comment.setComment(subCommitteeDTO.getCommitteeName() +" save as draft");
            }else {
                comment.setComment(subCommitteeDTO.getCommitteeName() + " created");
            }
            comment.setUserDisplayName(subCommitteeDTO.getUserDisplayName());
            comment.setCommentStatus(MasterDataApproveStatus.PENDING.toString());

            saveCACommentTemp(comment,credentialsDTO,subCommittee);
            return new CACommitteeDTO(subCommittee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CACommitteeDTO updateTempCommittee(CACommitteeDTO subCommitteeDTO, CredentialsDTO credentialsDTO) throws AppsException {
        Date date = new Date();
        CACommitteeTemp prevCommittee = subCommitteeTempDao.getOne(subCommitteeDTO.getCommitteeId());

            CommitteeType committeeType = committeeTypeDao.findById(subCommitteeDTO.getCommitteeTypeId())
                    .orElseThrow(() -> new AppsException("CommitteeType not found"));

            prevCommittee.setCommitteeType(committeeType);
            prevCommittee.setCommitteeName(subCommitteeDTO.getCommitteeName());
            prevCommittee.setDelegatedAuthority(subCommitteeDTO.getDelegatedAuthority());
            prevCommittee.setReviewer(subCommitteeDTO.getReviewer());
            prevCommittee.setModifiedBy(credentialsDTO.getUserName());
            prevCommittee.setModifiedDate(date);
            prevCommittee.setStatus(subCommitteeDTO.getStatus());
            prevCommittee.setApproveStatus(MasterDataApproveStatus.PENDING);
            prevCommittee.setCommitteeStatus(subCommitteeDTO.getCommitteeStatus());

            List<CAUserTemp> caUserList = new ArrayList<>();

            //add all previous level users
            for (CALevelTemp caLevel: prevCommittee.getCaLevelTempList()){
                caUserList.addAll(caLevel.getCaUserTempList());
            }

            //remove level deleted users
            if (subCommitteeDTO.getDeletedLevelUsers() != null && !subCommitteeDTO.getDeletedLevelUsers().isEmpty()) {
                for (CAUserDTO caUserDTO : subCommitteeDTO.getDeletedLevelUsers()) {
                    caUserTempDao.deleteById(caUserDTO.getUserConfigId());
                    caUserList.removeIf(cu -> cu.getUserConfigId().equals(caUserDTO.getUserConfigId()));
                }
            }

            for(CALevelDTO caLevelDTO : subCommitteeDTO.getCaLevelDTOList()){

                //Remove level data
                if (caLevelDTO.getActionStatus() == AppsConstants.CACommitteeStatus.DELETE) {

                    //remove level
                    caLevelTempDao.deleteById(caLevelDTO.getLevelConfigId());
                }

                //Update existing level data
                if (caLevelDTO.getActionStatus() == AppsConstants.CACommitteeStatus.UPDATE){

                    CALevelTemp caLevel = caLevelTempDao.findById(caLevelDTO.getLevelConfigId())
                            .orElseThrow(() -> new AppsException("Level is not found."));

                    caLevel.setLevelId(caLevelDTO.getLevelId());
                    caLevel.setCombination(caLevelDTO.getCombination());
                    caLevel.setUserCount(caLevelDTO.getUserCount());
                    caLevel.setModifiedBy(caLevelDTO.getModifiedBy());
                    caLevel.setModifiedDate(caLevelDTO.getModifiedDate());

                    //loop level users
                    for(CAUserDTO caUserDTO: caLevelDTO.getCaUserDTOList()) {

                        //Add new level user data
                        if (caUserDTO.getActionStatus() == AppsConstants.CACommitteeStatus.NEW) {

                            UserPool userPool = userPoolDao.findById(caUserDTO.getUserId())
                                    .orElseThrow(() -> new AppsException("Pool not found"));

                            CAUserTemp caUser = new CAUserTemp();
                            caUser.setCaCommitteeTemp(prevCommittee);
                            caUser.setUserPool(userPool);
                            caUser.setCaLevelTemp(caLevel);
                            caUser.setUserStatus(caUserDTO.getUserStatus());
                            caUser.setCreatedBy(credentialsDTO.getUserName());
                            caUser.setCreatedDate(date);

                            boolean isUserAlreadyInLevel = caUserList.stream()
                                    .anyMatch(caUser1 ->
                                            caUser1.getCaLevelTemp().getPathType().equals(caLevelDTO.getPathType()) &&
                                                    caUser1.getUserPool().getUserName().equals(caUserDTO.getUserName()));

                            LOG.info("isUserAlreadyInLevel {}", isUserAlreadyInLevel);

                            if (isUserAlreadyInLevel) {
                                throw new AppsException("A user cannot be in two levels in the same path type.");
                            } else {
                                caUserTempDao.save(caUser);
                            }
                        }
                    }

                    caLevelTempDao.save(caLevel);
                }

                //Add new level data
                if (caLevelDTO.getActionStatus() == AppsConstants.CACommitteeStatus.NEW){
                    List<CAUserTemp> newCAUserList = new ArrayList<>();
                    CALevelTemp caLevel = new CALevelTemp();

                    caLevel.setCaCommitteeTemp(prevCommittee);
                    caLevel.setLevelId(caLevelDTO.getLevelId());
                    caLevel.setCombination(caLevelDTO.getCombination());
                    caLevel.setStatus(caLevelDTO.getStatus());
                    caLevel.setPathType(caLevelDTO.getPathType());
                    caLevel.setUserCount(caLevelDTO.getUserCount());
                    caLevel.setCreatedBy(credentialsDTO.getUserName());
                    caLevel.setCreatedDate(date);

                    caLevelTempDao.save(caLevel);

                    //loop level users
                    for(CAUserDTO caUserDTO: caLevelDTO.getCaUserDTOList()) {

                        //Add new level user data
                        if (caUserDTO.getActionStatus() == AppsConstants.CACommitteeStatus.NEW) {

                            UserPool userPool = userPoolDao.findById(caUserDTO.getUserId())
                                    .orElseThrow(() -> new AppsException("Pool not found"));

                            CAUserTemp caUser = new CAUserTemp();

                            caUser.setCaCommitteeTemp(prevCommittee);
                            caUser.setUserPool(userPool);
                            caUser.setCaLevelTemp(caLevel);
                            caUser.setUserStatus(caUserDTO.getUserStatus());
                            caUser.setCreatedBy(credentialsDTO.getUserName());
                            caUser.setCreatedDate(date);

                            boolean isUserAlreadyInLevel = caUserList.stream()
                                    .anyMatch(caUser1 ->
                                            caUser1.getCaLevelTemp().getPathType().equals(caLevelDTO.getPathType()) &&
                                                    caUser1.getUserPool().getUserName().equals(caUserDTO.getUserName()));

                            LOG.info("isUserAlreadyInLevel {}", isUserAlreadyInLevel);

                            if (isUserAlreadyInLevel) {
                                throw new AppsException("A user cannot be in two levels in the same path type.");
                            } else {
                                caUserList.add(caUser);
                                newCAUserList.add(caUser);
                            }
                        }
                    }
                    caUserTempDao.saveAll(newCAUserList);
                }
            }

            subCommitteeTempDao.save(prevCommittee);

            //save committee comment
            CACommitteeCommentDTO caCommitteeCommentDTO = new CACommitteeCommentDTO();

            caCommitteeCommentDTO.setCommitteeId(prevCommittee.getCommitteeId());
            if(subCommitteeDTO.getCommitteeStatus() == AppsConstants.CACommitteeStatus.DRAFT){
                caCommitteeCommentDTO.setComment(subCommitteeDTO.getCommitteeName() +" update as draft");
            }else {
                caCommitteeCommentDTO.setComment(subCommitteeDTO.getCommitteeName() +" updated");
            }
            caCommitteeCommentDTO.setUserDisplayName(subCommitteeDTO.getUserDisplayName());
            caCommitteeCommentDTO.setCommentStatus(MasterDataApproveStatus.PENDING.toString());

            saveCACommentTemp(caCommitteeCommentDTO,credentialsDTO,prevCommittee);

            return new CACommitteeDTO(prevCommittee);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public CACommitteeDTO getSubCommitteeByID(Integer committeeId) throws AppsException {
        CACommittee caCommittee = subCommitteeDao.findById(committeeId).orElseThrow(() -> new AppsException("Committee with " + committeeId + " does not exist"));

        CACommitteeDTO caCommitteeDTO = new CACommitteeDTO();
        caCommitteeDTO.setCommitteeId(caCommittee.getCommitteeId());
        caCommitteeDTO.setCommitteeName(caCommittee.getCommitteeName());

        if (caCommittee.getCommitteeType() != null){
            caCommitteeDTO.setCommitteeTypeId(caCommittee.getCommitteeType().getCommitteeTypeId());
            caCommitteeDTO.setCommitteeTypeName(caCommittee.getCommitteeType().getCommitteeTypeName());
        }

        caCommitteeDTO.setDelegatedAuthority(caCommittee.getDelegatedAuthority());
        caCommitteeDTO.setReviewer(caCommittee.getReviewer());
        caCommitteeDTO.setCurrentPath(caCommittee.getCurrentPath());
        caCommitteeDTO.setCreatedBy(caCommittee.getCreatedBy());
        caCommitteeDTO.setModifiedBy(caCommittee.getModifiedBy());
        caCommitteeDTO.setCommitteeStatus(caCommittee.getCommitteeStatus());
        caCommitteeDTO.setApproveStatus(caCommittee.getApproveStatus());
        caCommitteeDTO.setStatus(caCommittee.getStatus());

        List<CALevel> caLevels = caLevelDao.findByCaCommitteeCommitteeId(caCommittee.getCommitteeId());
        List<CALevelDTO> caLevelDTOList = new ArrayList<>();

        for(CALevel caLevel : caLevels){
            CALevelDTO caLevelDTO = new CALevelDTO();
            caLevelDTO.setLevelConfigId(caLevel.getLevelConfigId());
            caLevelDTO.setLevelId(caLevel.getLevelId());
            caLevelDTO.setCombination(caLevel.getCombination());
            caLevelDTO.setStatus(caLevel.getStatus());
            caLevelDTO.setPathType(caLevel.getPathType());
            caLevelDTO.setUserCount(caLevel.getUserCount());
            caLevelDTO.setCreatedBy(caLevel.getCreatedBy());
            caLevelDTO.setCreatedDate(caLevel.getCreatedDate());
            caLevelDTO.setModifiedBy(caLevel.getModifiedBy());
            caLevelDTO.setModifiedDate(caLevel.getModifiedDate());

            List<CAUser> caUsers = caUserDao.findByCaLevelLevelConfigId(caLevel.getLevelConfigId());
            List<CAUserDTO> caUserList = new ArrayList<>();

            for(CAUser caUser : caUsers){
                CAUserDTO caUserDTO = new CAUserDTO();
                caUserDTO.setUserConfigId(caUser.getUserConfigId());
                caUserDTO.setUserName(caUser.getUserPool().getUserName());
                caUserDTO.setUserStatus(caUser.getUserStatus());
                caUserList.add(caUserDTO);
            }

            caLevelDTO.setCaUserDTOList(caUserList);

            caLevelDTOList.add(caLevelDTO);
        }

        caCommitteeDTO.setCaLevelDTOList(caLevelDTOList);

        return caCommitteeDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public CACommitteeDTO getTempCommitteeByID(Integer committeeId) throws AppsException {
        CACommitteeTemp caCommittee = subCommitteeTempDao.findById(committeeId).orElseThrow(() -> new AppsException("Committee with " + committeeId + " does not exist"));

        CACommitteeDTO caCommitteeDTO = new CACommitteeDTO();
        caCommitteeDTO.setCommitteeId(caCommittee.getCommitteeId());
        caCommitteeDTO.setCommitteeName(caCommittee.getCommitteeName());

        if (caCommittee.getCommitteeType() != null){
            caCommitteeDTO.setCommitteeTypeId(caCommittee.getCommitteeType().getCommitteeTypeId());
            caCommitteeDTO.setCommitteeTypeName(caCommittee.getCommitteeType().getCommitteeTypeName());
        }

        caCommitteeDTO.setDelegatedAuthority(caCommittee.getDelegatedAuthority());
        caCommitteeDTO.setReviewer(caCommittee.getReviewer());
        caCommitteeDTO.setCurrentPath(caCommittee.getCurrentPath());
        caCommitteeDTO.setCreatedBy(caCommittee.getCreatedBy());
        caCommitteeDTO.setModifiedBy(caCommittee.getModifiedBy());
        caCommitteeDTO.setCommitteeStatus(caCommittee.getCommitteeStatus());
        caCommitteeDTO.setApproveStatus(caCommittee.getApproveStatus());
        caCommitteeDTO.setStatus(caCommittee.getStatus());
        caCommitteeDTO.setParentRecordID(caCommittee.getParentRecordID());

        List<CALevelTemp> caLevels = caLevelTempDao.findByCaCommitteeTempCommitteeId(caCommittee.getCommitteeId());
        List<CALevelDTO> caLevelDTOList = new ArrayList<>();

        for(CALevelTemp caLevel : caLevels){
            CALevelDTO caLevelDTO = new CALevelDTO();
            caLevelDTO.setLevelConfigId(caLevel.getLevelConfigId());
            caLevelDTO.setLevelId(caLevel.getLevelId());
            caLevelDTO.setCombination(caLevel.getCombination());
            caLevelDTO.setStatus(caLevel.getStatus());
            caLevelDTO.setPathType(caLevel.getPathType());
            caLevelDTO.setUserCount(caLevel.getUserCount());
            caLevelDTO.setCreatedBy(caLevel.getCreatedBy());
            caLevelDTO.setCreatedDate(caLevel.getCreatedDate());
            caLevelDTO.setModifiedBy(caLevel.getModifiedBy());
            caLevelDTO.setModifiedDate(caLevel.getModifiedDate());

            List<CAUserTemp> caUsers = caUserTempDao.findByCaLevelTempLevelConfigId(caLevel.getLevelConfigId());
            List<CAUserDTO> caUserList = new ArrayList<>();

            for(CAUserTemp caUser : caUsers){
                CAUserDTO caUserDTO = new CAUserDTO();
                caUserDTO.setUserConfigId(caUser.getUserConfigId());
                caUserDTO.setUserName(caUser.getUserPool().getUserName());
                caUserDTO.setUserStatus(caUser.getUserStatus());
                caUserList.add(caUserDTO);
            }

            caLevelDTO.setCaUserDTOList(caUserList);

            caLevelDTOList.add(caLevelDTO);
        }

        caCommitteeDTO.setCaLevelDTOList(caLevelDTOList);

        return caCommitteeDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CACommitteeDTO approveRejectCommittee(CACommitteeDTO caCommitteeDTO, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();

        CACommitteeTemp subCommittee = subCommitteeTempDao.findById(caCommitteeDTO.getCommitteeId()).orElseThrow(() -> new AppsException("Committee with " + caCommitteeDTO.getCommitteeId() + " does not exist"));
        CACommitteeDTO committee = getTempCommitteeByID(caCommitteeDTO.getCommitteeId());

        if (subCommittee.getCommitteeId() != 0){

            if (caCommitteeDTO.getApproveStatus() == MasterDataApproveStatus.APPROVED){

                if(caCommitteeDTO.getStatus() == AppsConstants.Status.INA){
                    int activeCount = masterDataJdbcDao.isCommitteeUsed(committee.getParentRecordID());
                    if(activeCount != 0){
                        LOG.info("Committee is used. {}", activeCount);
                        throw new AppsException("There are ongoing committee papers for this committee.");
                    }
                }

                subCommittee.setModifiedDate(date);
                subCommittee.setModifiedBy(credentialsDTO.getUserName());
                subCommittee.setApproveStatus(caCommitteeDTO.getApproveStatus());
                subCommittee.setStatus(caCommitteeDTO.getStatus());
                subCommitteeTempDao.save(subCommittee);

                boolean isApproved = false;
                //move temp data to config table
                if (committee.getParentRecordID() != null && committee.getParentRecordID() != 0){

                    //update exiting committee in config table
                    isApproved = updateApprovedCommittee(caCommitteeDTO, credentialsDTO);
                }else {

                    //save approved committee to config table
                    isApproved = addApprovedCommittee(caCommitteeDTO, credentialsDTO);
                }

                //remove previous temp level and level user data
                if(isApproved) {
                   deleteTempCommittee(committee);
                }
            }else {
                subCommittee.setModifiedDate(date);
                subCommittee.setModifiedBy(credentialsDTO.getUserName());
                subCommittee.setApproveStatus(caCommitteeDTO.getApproveStatus());
                subCommitteeTempDao.save(subCommittee);
            }

        } else {
            throw new AppsException("Committee not found.");
        }

        return new CACommitteeDTO(subCommittee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public boolean updateApprovedCommittee(CACommitteeDTO committee, CredentialsDTO credentialsDTO) throws AppsException {

        boolean isSuccess = false;

        try {

            Date date = new Date();
            CACommittee prevCommittee = subCommitteeDao.getOne(committee.getParentRecordID());
            CACommitteeDTO prevCommitteeDTO = getSubCommitteeByID(committee.getParentRecordID());

            CommitteeType committeeType = committeeTypeDao.findById(committee.getCommitteeTypeId())
                    .orElseThrow(() -> new AppsException("CommitteeType not found"));

            prevCommittee.setCommitteeType(committeeType);
            prevCommittee.setCommitteeName(committee.getCommitteeName());
            prevCommittee.setDelegatedAuthority(committee.getDelegatedAuthority());
            prevCommittee.setReviewer(committee.getReviewer());
            prevCommittee.setModifiedBy(credentialsDTO.getUserName());
            prevCommittee.setModifiedDate(date);
            prevCommittee.setStatus(committee.getStatus());
            prevCommittee.setApproveStatus(MasterDataApproveStatus.APPROVED);
            prevCommittee.setCommitteeStatus(committee.getCommitteeStatus());

            //delete previous committee level
            boolean isDeleted = deleteCommitteeLevel(prevCommitteeDTO);

            if (isDeleted) {

                List<CALevel> caLevelList = new ArrayList<>();
                List<CAUser> caUserList = new ArrayList<>();

                for (CALevelDTO caLevelDTO : committee.getCaLevelDTOList()) {
                    CALevel caLevel = new CALevel();

                    caLevel.setCaCommittee(prevCommittee);
                    caLevel.setLevelId(caLevelDTO.getLevelId());
                    caLevel.setCombination(caLevelDTO.getCombination());
                    caLevel.setStatus(caLevelDTO.getStatus());
                    caLevel.setPathType(caLevelDTO.getPathType());
                    caLevel.setUserCount(caLevelDTO.getUserCount());
                    caLevel.setCreatedBy(credentialsDTO.getUserName());
                    caLevel.setCreatedDate(date);

                    caLevelList.add(caLevel);

                    for (CAUserDTO caUserDTO : caLevelDTO.getCaUserDTOList()) {

                        UserPool userPool = userPoolDao.findById(caUserDTO.getUserId())
                                .orElseThrow(() -> new AppsException("Pool user not found"));

                        CAUser caUser = new CAUser();

                        caUser.setCaCommittee(prevCommittee);
                        caUser.setUserPool(userPool);
                        caUser.setCaLevel(caLevel);
                        caUser.setUserStatus(caUserDTO.getUserStatus());
                        caUser.setCreatedBy(credentialsDTO.getUserName());
                        caUser.setCreatedDate(date);

                        caUserList.add(caUser);
                    }
                }

                if (!caLevelList.isEmpty()) {

                    caLevelList = caLevelDao.saveAll(caLevelList);
                    prevCommittee.setCaLevels(caLevelList);

                    if (!caUserList.isEmpty()) {
                        caUserDao.saveAll(caUserList);
                    }
                }

                subCommitteeDao.save(prevCommittee);

                //save committee comment
//                CACommitteeCommentDTO caCommitteeCommentDTO = new CACommitteeCommentDTO();
//
//                caCommitteeCommentDTO.setCommitteeId(prevCommittee.getCommitteeId());
//                caCommitteeCommentDTO.setComment(prevCommittee.getCommitteeName() + " updated");
//                caCommitteeCommentDTO.setUserDisplayName(committee.getUserDisplayName());
//                caCommitteeCommentDTO.setCommentStatus(MasterDataApproveStatus.APPROVED.toString());
//
//                saveCAComment(caCommitteeCommentDTO, credentialsDTO, prevCommittee);

                isSuccess = true;
            }else {
                throw new AppsException("Failed to update committee.");
            }

        }catch (Exception e){
            throw new AppsException("Failed to update committee.");
        }

        return isSuccess;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public boolean addApprovedCommittee(CACommitteeDTO subCommitteeDTO, CredentialsDTO credentialsDTO) throws AppsException {

        boolean isSuccess = false;

        try {
            Date date = new Date();
            CACommittee subCommittee = new CACommittee();

            if (subCommitteeDTO.getCommitteeTypeId() != 0) {
                CommitteeType committeeType = committeeTypeDao.findById(subCommitteeDTO.getCommitteeTypeId())
                        .orElseThrow(() -> new AppsException("CommitteeType not found"));

                subCommittee.setCommitteeType(committeeType);
            }

            subCommittee.setCommitteeName(subCommitteeDTO.getCommitteeName());
            subCommittee.setDelegatedAuthority(subCommitteeDTO.getDelegatedAuthority());
            subCommittee.setCreatedDate(date);
            subCommittee.setCreatedBy(credentialsDTO.getUserName());
            subCommittee.setStatus(subCommitteeDTO.getStatus());
            subCommittee.setReviewer(subCommitteeDTO.getReviewer());
            subCommittee.setCurrentPath(subCommitteeDTO.getCurrentPath());
            subCommittee.setCommitteeStatus(subCommitteeDTO.getCommitteeStatus());
            subCommittee.setApproveStatus(MasterDataApproveStatus.APPROVED);
            subCommittee.setParentRecordID(0);

            subCommittee = subCommitteeDao.saveAndFlush(subCommittee);

            List<CALevel> caLevelList = new ArrayList<>();
            List<CAUser> caUserList = new ArrayList<>();

            for (CALevelDTO caLevelDTO : subCommitteeDTO.getCaLevelDTOList()) {
                CALevel caLevel = new CALevel();

                caLevel.setCaCommittee(subCommittee);
                caLevel.setLevelId(caLevelDTO.getLevelId());
                caLevel.setCombination(caLevelDTO.getCombination());
                caLevel.setStatus(caLevelDTO.getStatus());
                caLevel.setPathType(caLevelDTO.getPathType());
                caLevel.setUserCount(caLevelDTO.getUserCount());
                caLevel.setCreatedBy(credentialsDTO.getUserName());
                caLevel.setCreatedDate(date);

                caLevelList.add(caLevel);

                for (CAUserDTO caUserDTO : caLevelDTO.getCaUserDTOList()) {

                    UserPool userPool = userPoolDao.findById(caUserDTO.getUserId())
                            .orElseThrow(() -> new AppsException("Pool not found"));

                    CAUser caUser = new CAUser();

                    caUser.setCaCommittee(subCommittee);
                    caUser.setUserPool(userPool);
                    caUser.setCaLevel(caLevel);
                    caUser.setUserStatus(caUserDTO.getUserStatus());
                    caUser.setCreatedBy(credentialsDTO.getUserName());
                    caUser.setCreatedDate(date);

                    caUserList.add(caUser);
                }
            }

            if (!caLevelList.isEmpty()) {

                caLevelList = caLevelDao.saveAll(caLevelList);
                subCommittee.setCaLevels(caLevelList);

                if (!caUserList.isEmpty()) {
                    caUserDao.saveAll(caUserList);
                }
            }

            //save committee comment
            CACommitteeCommentDTO caCommitteeCommentDTO = new CACommitteeCommentDTO();

            caCommitteeCommentDTO.setCommitteeId(subCommitteeDTO.getCommitteeId());
            caCommitteeCommentDTO.setComment(subCommitteeDTO.getCommitteeName() + " created");
            caCommitteeCommentDTO.setUserDisplayName(subCommitteeDTO.getUserDisplayName());
            caCommitteeCommentDTO.setCommentStatus(MasterDataApproveStatus.APPROVED.toString());

            saveCAComment(caCommitteeCommentDTO, credentialsDTO, subCommittee);
            isSuccess = true;

        }catch (Exception e){
            throw new AppsException("Failed to add committee.");
        }
        return isSuccess;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public void deleteTempCommittee(CACommitteeDTO committee) throws AppsException {
        try {

            for (CALevelDTO caLevelDTO : committee.getCaLevelDTOList()) {
                caUserTempDao.deleteByCaLevelTempLevelConfigId(caLevelDTO.getLevelConfigId());
                caLevelTempDao.deleteById(caLevelDTO.getLevelConfigId());
            }
            subCommitteeTempDao.deleteById(committee.getCommitteeId());

            //delete all temp comments by committeeId
            caCommitteeCommentTempDao.deleteByCaCommitteeTempCommitteeId(committee.getCommitteeId());

//            int isSuccess = this.masterDataJdbcDao.removeTempCommittee(committee.getCommitteeId());
//            if(isSuccess == 0){
//                throw new AppsException("Failed to remove temp committee.");
//            }

        } catch (Exception e) {
            LOG.info("Failed to delete temporary committee.", e);
            throw new AppsException("Failed to save committee.", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public boolean deleteCommitteeLevel(CACommitteeDTO committee) throws AppsException {

        boolean result = false;
        try {
            int isSuccess = this.masterDataJdbcDao.removeCommitteeLevel(committee.getCommitteeId());
            if(isSuccess == 0){
                throw new AppsException("Failed to remove committee level.");
            }

            result = true;
        } catch (Exception e) {
            LOG.info("Failed to delete committee level.", e);
            throw new AppsException("Failed to save committee.", e);
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CACommitteeCommentDTO saveCACommentTemp(CACommitteeCommentDTO caCommitteeCommentDTO, CredentialsDTO credentialsDTO, CACommitteeTemp caCommittee) throws AppsException {
        CACommitteeCommentTemp caCommitteeComment = new CACommitteeCommentTemp();
        Date date = new Date();

        caCommitteeComment.setCommentId(caCommitteeCommentDTO.getCommentId());

        caCommitteeComment.setCreatedBy(credentialsDTO.getUserName());
        caCommitteeComment.setCreatedDate(date);
        caCommitteeComment.setComment(caCommitteeCommentDTO.getComment());
        caCommitteeComment.setUserDisplayName(caCommitteeCommentDTO.getUserDisplayName());
        caCommitteeComment.setCaCommitteeTemp(caCommittee);
        caCommitteeComment.setCommentStatus(caCommitteeCommentDTO.getCommentStatus());

        caCommitteeComment = caCommitteeCommentTempDao.save(caCommitteeComment);

        return new CACommitteeCommentDTO(caCommitteeComment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CACommitteeCommentDTO saveCAComment(CACommitteeCommentDTO caCommitteeCommentDTO, CredentialsDTO credentialsDTO,CACommittee caCommittee) throws AppsException {
        CACommitteeComment caCommitteeComment = new CACommitteeComment();
        Date date = new Date();

        caCommitteeComment.setCreatedBy(credentialsDTO.getUserName());
        caCommitteeComment.setCreatedDate(date);
        caCommitteeComment.setComment(caCommitteeCommentDTO.getComment());
        caCommitteeComment.setUserDisplayName(caCommitteeCommentDTO.getUserDisplayName());
        caCommitteeComment.setCaCommittee(caCommittee);
        caCommitteeComment.setCommentStatus(caCommitteeCommentDTO.getCommentStatus());

        caCommitteeComment = caCommitteeCommentDao.save(caCommitteeComment);

        return new CACommitteeCommentDTO(caCommitteeComment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public List<CACommitteeCommentDTO> getCACommentListByCommitteeId(Integer committeeId){

        List<CACommitteeComment> caCommitteeComments = caCommitteeCommentDao.findByCaCommitteeCommitteeId(committeeId);

        return caCommitteeComments.stream().map(caCommitteeComment -> modelMapper.map(caCommitteeComment, CACommitteeCommentDTO.class)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public List<CACommitteeCommentDTO> getCACommentTempListByCommitteeId(Integer committeeId){
        List<CACommitteeCommentTemp> caCommitteeComments = caCommitteeCommentTempDao.findByCaCommitteeTempCommitteeId(committeeId);

        return caCommitteeComments.stream().map(caCommitteeComment -> modelMapper.map(caCommitteeComment, CACommitteeCommentDTO.class)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void changeCommitteePaperCurrentLevel( Integer committeeID, AppsConstants.CAPathType path) throws AppsException {
        LOG.info("START: changeCommitteePaperCurrentLevel");
        Integer returnCode = 1;

        LOG.info("Committee ID : {}", committeeID);
        LOG.info("Current path : {}", path);

        returnCode = masterDataJdbcDao.changeCommitteePaperCurrentLevel(committeeID, path);

        LOG.info("DB Return Code : {}", returnCode);
        LOG.info("END: changeCommitteePaperCurrentLevel");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FacilityTypeDTO> getFacilityTypes() throws AppsException {
        LOG.info("START: getFacilityTypes");

        List<CreditFacilityType> facilityTypeList = creditFacilityTypeDao.findByApproveStatusAndStatus(
                DomainConstants.MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT);

        List<FacilityTypeDTO> facilityTypes = new ArrayList<>();

        for (CreditFacilityType facilityType : facilityTypeList) {
            FacilityTypeDTO typeDTO = new FacilityTypeDTO();
            typeDTO.setFacilityTypeID(facilityType.getCreditFacilityTypeID());
            typeDTO.setFacilityTypeName(facilityType.getFacilityTypeName());
            typeDTO.setDescription(facilityType.getDescription());

            // Fetch templates for this facility type
            List<CreditFacilityTemplate> facilityTemplates = creditFacilityTemplateDao
                    .findAllByCreditFacilityType_CreditFacilityTypeIDAndApproveStatusAndStatus(
                            facilityType.getCreditFacilityTypeID(),
                            DomainConstants.MasterDataApproveStatus.APPROVED,
                            AppsConstants.Status.ACT
                    );

            // Map templates to DTOs
            List<FacilityTemplateDTO> facilityTemplateDTOList = facilityTemplates.stream()
                    .filter(template -> template.getShowInLead() != null && template.getShowInLead().equals(AppsConstants.YesNo.Y))
                    .map(FacilityTemplateDTO::new)
                    .collect(Collectors.toList());

            typeDTO.setFacilityTemplates(facilityTemplateDTOList);
            facilityTypes.add(typeDTO);
        }

        LOG.info("END: getFacilityTypes");
        return facilityTypes;
    }

    public List<CreditFacilityTemplateDTO> getApprovedActiveCreditFacilityTemplates() throws AppsException, IOException, SAXException {
        List<CreditFacilityTemplateDTO> approvedCreditFacilityTemplateList = new ArrayList<>();


        List<CreditFacilityTemplate> allCreditFacilityTemplateList = creditFacilityTemplateDao.findAllByApproveStatusAndStatus(DomainConstants.MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT);

        if (!(allCreditFacilityTemplateList.isEmpty())) {
            for (CreditFacilityTemplate template : allCreditFacilityTemplateList) {
                List<Formula> formulaList = new CalculatorUtil().parseXml(environment, AppsConstants.FacilityType.LEASE.getType(), AppsConstants.CalculatorType.STRUCTURED.getType());
                approvedCreditFacilityTemplateList.add(new CreditFacilityTemplateDTO(template, formulaList));
            }
        }
        return approvedCreditFacilityTemplateList;
    }

    @Transactional(readOnly = true)
    public List<DeviationTypeDTO> getAllDeviationTypes() throws AppsException {
        List<DeviationTypeDTO> response = new ArrayList<>();

        try {
            List<DeviationType> types = deviationTypeDao.findAll();
            if (!types.isEmpty()){
                response = types.stream().map(DeviationTypeDTO::new).collect(Collectors.toList());
            }
        }catch (Exception e){
            LOG.info("ERROR: Get All Deviation Types: ", e);
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<DeviationTypeDTO> saveDeviationType(DeviationTypeDTO deviationTypeDTO, CredentialsDTO credentialsDTO) throws AppsException {
        List<DeviationTypeDTO> response = new ArrayList<>();

        try {
            Date date = new Date();

            DeviationType deviationType = deviationTypeDao.findById(deviationTypeDTO.getDeviationTypeId()).orElse(new DeviationType());

            deviationType.setDeviationType(deviationTypeDTO.getDeviationType());
            deviationType.setStatus(deviationTypeDTO.getStatus());

            if (deviationTypeDTO.getDeviationTypeId() != null && deviationTypeDTO.getDeviationTypeId() != 0){
                deviationType.setModifiedDate(date);
                deviationType.setModifiedBy(credentialsDTO.getUserName());
            } else {
                deviationType.setCreatedDate(date);
                deviationType.setCreatedBy(credentialsDTO.getUserName());
            }

            deviationTypeDao.save(deviationType);

            List<DeviationType> types = deviationTypeDao.findAll();
            if (!types.isEmpty()){
                response = types.stream().map(DeviationTypeDTO::new).collect(Collectors.toList());
            }
        }catch (Exception e){
            LOG.info("ERROR: Save Deviation Type: ", e);
        }
        return response;
    }


    @Transactional(readOnly = true)
    public List<DeviationDTO> getAllDeviations() throws AppsException {
        List<DeviationDTO> response = new ArrayList<>();

        try {
            List<TempDeviation> temps = tempDeviationDao.findAll();
            List<Deviation> master = deviationDao.findAll();
            if (!master.isEmpty()){
                List<DeviationDTO> masterDeviations = master.stream().map(DeviationDTO::new).collect(Collectors.toList());
                response.addAll(masterDeviations);
            }
            if (!temps.isEmpty()){
                List<DeviationDTO> tempDeviations = temps.stream().map(DeviationDTO::new).collect(Collectors.toList());
                response.addAll(tempDeviations);
            }
        }catch (Exception e){
            LOG.info("ERROR: Get All Deviation: ", e);
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<DeviationDTO> saveDeviation(DeviationDTO deviationDTO, CredentialsDTO credentialsDTO) throws AppsException {
        List<DeviationDTO> response = new ArrayList<>();

        try {
            Date date = new Date();
            TempDeviation deviation = new TempDeviation();

            if (deviationDTO.getParentId() != null && deviationDTO.getParentId() != 0){
                deviation.setParentId(deviationDTO.getParentId());
            } else {
                deviation.setParentId(0);
            }

            deviation.setDeviationType(deviationDTO.getDeviationType());
            deviation.setDescription(deviationDTO.getDescription());
            deviation.setStatus(deviationDTO.getStatus());
            deviation.setCreatedDate(date);
            deviation.setCreatedBy(credentialsDTO.getUserName());

            tempDeviationDao.save(deviation);

            List<TempDeviation> temps = tempDeviationDao.findAll();
            List<Deviation> master = deviationDao.findAll();
            if (!master.isEmpty()){
                List<DeviationDTO> masterDeviations = master.stream().map(DeviationDTO::new).collect(Collectors.toList());
                response.addAll(masterDeviations);
            }
            if (!temps.isEmpty()){
                List<DeviationDTO> tempDeviations = temps.stream().map(DeviationDTO::new).collect(Collectors.toList());
                response.addAll(tempDeviations);
            }

        } catch (Exception e){
            LOG.info("ERROR: Save Deviation: ", e);
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<DeviationDTO> authorizedDeviation(DeviationDTO deviationDTO, CredentialsDTO credentialsDTO) throws AppsException {
        List<DeviationDTO> response = new ArrayList<>();

        try {
            Date date = new Date();

            if (deviationDTO.getApproveStatus().equals(MasterDataApproveStatus.APPROVED.toString())){

                Deviation deviation = deviationDao.findById(deviationDTO.getParentId()).orElse(new Deviation());

                deviation.setDeviationType(deviationDTO.getDeviationType());
                deviation.setDescription(deviationDTO.getDescription());
                deviation.setStatus(deviationDTO.getStatus());
                deviation.setAthorizedDate(date);
                deviation.setAuthorizedBy(credentialsDTO.getUserName());

                if (deviation.getDeviationId() != null && deviation.getDeviationId() != 0){
                    deviation.setModifiedDate(date);
                    deviation.setModifiedBy(credentialsDTO.getUserName());
                }else {
                    deviation.setDeviationId(deviationDTO.getDeviationId());
                    deviation.setCreatedDate(date);
                    deviation.setCreatedBy(credentialsDTO.getUserName());
                }

               deviationDao.save(deviation);

                tempDeviationDao.findById(deviationDTO.getDeviationId()).ifPresent(data -> {
                    tempDeviationDao.delete(data);
                });
            } else {
                tempDeviationDao.findById(deviationDTO.getDeviationId()).ifPresent(data -> {
                    tempDeviationDao.delete(data);
                });
            }

            List<TempDeviation> temps = tempDeviationDao.findAll();
            List<Deviation> master = deviationDao.findAll();
            if (!master.isEmpty()){
                List<DeviationDTO> masterDeviations = master.stream().map(DeviationDTO::new).collect(Collectors.toList());
                response.addAll(masterDeviations);
            }
            if (!temps.isEmpty()){
                List<DeviationDTO> tempDeviations = temps.stream().map(DeviationDTO::new).collect(Collectors.toList());
                response.addAll(tempDeviations);
            }

        }catch (Exception e){
            LOG.info("ERROR: Authorized Deviation: ", e);
        }
        return response;
    }
}
