import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LeadUpdateDto} from "../../dto/lead-update-dto";
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {LeadAddEditService} from "../../services/lead-add-edit.service";
import {AppUtils} from "../../../../../shared/app.utils";
import * as _ from 'lodash';
import {IMyDate, IMyOptions, MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Router} from "@angular/router";
import {LeadFacilityDetailComponent} from "../lead-facility-detail/lead-facility-detail.component";
import {Constants} from "../../../../../core/setting/constants";
import {LeadFacilityDetailUpdateDto} from "../../dto/lead-facility-detail-update-dto";
import {LeadDocumentComponent} from "../lead-document/lead-document.component";
import {LeadDocumentUpdateDto} from "../../dto/lead-document-update-dto";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {AlertService} from "../../../../../core/service/common/alert.service";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {LocalStorage} from "ngx-webstorage";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {LeadCustomerDetailComponent} from "../lead-customer-detail/lead-customer-detail.component";
import {CurrencyPipe} from "@angular/common";
import {ConfirmationDialogComponent} from "../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {ExternalLeadApproveComponent} from "../dialogs/external-lead-approve/external-lead-approve.component";
import {ExternalLeadCloseComponent} from "../dialogs/external-lead-close/external-lead-close.component";
import {ExternalLeadDeclineComponent} from "../dialogs/external-lead-decline/external-lead-decline.component";
import {ExternalLeadReturnComponent} from "../dialogs/external-lead-return/external-lead-return.component";
import {InternalLeadApproveComponent} from "../dialogs/internal-lead-approve/internal-lead-approve.component";
import {LeadExistingMessageComponent} from "../lead-existing-message/lead-existing-message.component";
import {InternalLeadAcceptComponent} from "../dialogs/internal-lead-accept/internal-lead-accept.component";
import {IdentificationNumberValidator} from "../../../../../shared/validators/identification-number.validator";
import {InternalLeadStartPaperComponent} from "../dialogs/internal-lead-start-paper/internal-lead-start-paper.component";
import {NumberValidator} from "../../../../../shared/validators/number.validator";
import {FpPersonalDetailTabViewComponent} from '../../../facility-paper/components/facility-paper-add-edit/components/basic-wrapper/fp-personal-details/fp-personal-detail-tab-view/fp-personal-detail-tab-view.component';
import { LeadNewService } from '../../services/lead-new.service';
import { LeadComprehensiveCreateComponent } from '../lead-comprehensive-create/lead-comprehensive-create.component';

@Component({
  selector: 'app-lead-create',
  templateUrl: './lead-create.component.html',
  styleUrls: ['./lead-create.component.scss']
})
export class LeadCreateComponent implements OnInit, OnDestroy {

   @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
    selectedLeadID;

    @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
    selectedFacilityPaperID;

    modalRef: MDBModalRef;
    identificationType = false;
    leadCreationTypeConst = Constants.leadCreationTypeConst;
    leadFsTypeConst = Constants.leadFsTypeConst;

    facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
    leadCreationType = Constants.leadCreationType;
    leadFsType = Constants.leadFsType;
    customerIdentificationTypeConst = Constants.customerIdentificationTypeConst;
    leadTypeConst = Constants.leadTypeConst;
    masterDataPrivilege = SETTINGS.PRIVILEGES;


    public myDatePickerOptions: IMyOptions = {
      dateFormat: 'dd/mm/yyyy',
      minYear: 1900
    };

    identityOptionSelect = Constants.customerIdentificationTypeOptionsSelect;
    statusConst = Constants.statusConst;
    status = Constants.status;
    leadStatusConst = Constants.leadStatusConst;
    leadActionConst = Constants.leadActionConst;
    leadStatus = Constants.leadStatus;


    tableColumns = ['Facility Template Name', 'Facility Type', 'Amount', 'Description', 'Status', 'Action'];

    tableColumnsForLeadDocument = ['Document Name', 'Remark', 'Status', 'Actions'];

    tableColumnSForLeadAction = ['User', 'Action', 'Date', 'Remark'];

    dataSource: LeadFacilityDetailUpdateDto[] = [];
    dataSourceForLeadDocument: LeadDocumentUpdateDto[] = [];
    dataSourceForCustomers: any = {};


    customer: any = {};
    identificationList = [];
    activeCustomers = [];
    facilityPaperStatusHistory: any = {};
    allBranches = [];
    branchName = {};
    branchCode = {};
    customerID: any = {};
    results: Subject<any>;
    customerResults: Subject<any>;


    componentForm: FormGroup;
    formErrors: any;
    inActivated: any = false;
    dataSourceHasValue: any = false;
    leadUpdateDTO: LeadUpdateDto = new LeadUpdateDto({});
    selectedCustomer: any = {};
    onSelectedLeadChangeSub: Subscription = new Subscription();
    onFormValueChangeSub: Subscription = new Subscription();
    onLoadActiveCustomerListChangeSub: Subscription = new Subscription();
    onCustomerNameChangeSub: Subscription = new Subscription();
    onBranchNameChangeSub: Subscription = new Subscription();
    onIdentificationTypeChangeSub: Subscription = new Subscription();
    onLeadCreationTypeChangeSub: Subscription = new Subscription();
    onLoadBranchListChangeSub = new Subscription();
    ondownloadLinkChangeSub = new Subscription();
    onSelectedCustomerChangeSub = new Subscription();
    onAgentFacilityPaperDraftedSubs = new Subscription();
    onLeadFacilityPaperStatusHistoryChangeSub = new Subscription();

    pageType: string = 'new';

    optionsCivilStatusSelect = Constants.optionsCivilStatusSelectOpt;

    optionsLeadStatusSelect = [
      {value: 'PENDING', label: 'PENDING'},
      {value: 'ASSIGNED', label: 'ASSIGNED'},
      {value: 'PROCESSING', label: 'PROCESSING'},
      {value: 'PROCESSED', label: 'PROCESSED'},
      {value: 'ClOSED', label: 'ClOSED'}
    ];

    disableSinceDate: IMyDate = {
      year: new Date().getFullYear() - 18,
      month: new Date().getMonth() + 1,
      day: new Date().getDate(),
    };

    myDatePickerOptionsForBirthDay: IMyOptions = {
      dateFormat: 'dd/mm/yyyy',
      minYear: new Date().getFullYear() - 138,
      showTodayBtn: false,
      closeAfterSelect: true,
      firstDayOfWeek: 'mo',
      disableSince: this.disableSinceDate,
    };

    isFormDisabled: any = false;

    identificationNumberLabel = 'Identification Number';
    
    isAddedNIC : boolean = false;
    isSelectNIC : boolean = true;

    @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;

    constructor(
      private leadAddEditService: LeadAddEditService,
      private formBuilder: FormBuilder,
      private router: Router,
      private  mdbModalService: MDBModalService,
      private applicationService: ApplicationService,
      private cacheService: CacheService,
      private alertService: AlertService,
      private urlEncodeService: UrlEncodeService,
      private currencyPipe: CurrencyPipe,
    ) {
    }

    ngOnInit() {
      //this.activeCustomers = this.cacheService.getData(Constants.masterDataKey.CAS_CUSTOMERS);
      //this.customerResults = new BehaviorSubject(this.activeCustomers);
      this.allBranches = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
      this.results = new BehaviorSubject(this.allBranches);

      this.formErrors = {
        //customerName: {},
        leadName: {},
        leadRefNumber: {},
        fullName: {},
        accountNumber: {},
        mobileNo: {},
        dateOfBirthStr: {},
        address: {},
        civilStatus: {},
        identificationType: {},
        identificationNumber: {},
        //branchCode: {},
        branchName: {},
        nationality: {},
        //leadStatus: {},
        leadDocumentDTOList: {},
        isExistingCustomer: {},
        typeOfBusiness: {},
        designation: {},
        leadCreationType: {},
        leadFsType: {},
        contactPerson: {}
      };


      this.onSelectedLeadChangeSub = this.leadAddEditService.onSelectedLeadChange
        .subscribe((lead: any) => {
          if (_.isEmpty(lead)) {
            this.pageType = 'new';
            this.leadUpdateDTO = new LeadUpdateDto({});
          } else {
            ////this.pageType = 'edit';
            // if (lead.customerID != null) {
            //   this.customer = AppUtils.getCustomerFromCustomerID(this.activeCustomers, lead.customerID);
            //   lead.customerName = this.customer.customerName;
            // } else {
            //   lead.customerName = "";
            // }
            ////this.leadUpdateDTO = new LeadUpdateDto(lead);
            ////this.dataSource = _.cloneDeep(lead.leadFacilityDetailDTOList);
            ///this.dataSourceForLeadDocument = lead.leadDocumentDTOList.splice(0);
            // if (this.leadUpdateDTO.customerID != null && this.leadUpdateDTO.customerID != "") {
            //   this.leadAddEditService.getCustomerById(this.leadUpdateDTO.customerID)
            // }
          }
          this.isFormDisabled = this.leadUpdateDTO.leadID && !((this.leadUpdateDTO.leadStatus == this.leadStatusConst.PENDING || this.leadUpdateDTO.leadStatus == this.leadStatusConst.RETURNED) && this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.createdBy);
          this.componentForm = this.createLeadForm();
          this.onFormValueChangeSub.unsubscribe();
          this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe((form) => {
            this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
          });
          this.onSelectedCustomerChangeSub = this.leadAddEditService.onSelectedCustomerChange
            .subscribe(data => {
              this.selectedCustomer = data || {};
              this.componentForm.controls.fullName.setValue(this.selectedCustomer.customerName);
              this.componentForm.controls.civilStatus.setValue(this.selectedCustomer.civilStatus);
              this.componentForm.controls.dateOfBirthStr.setValue(this.selectedCustomer.dateOfBirth);
              this.componentForm.controls.mobileNo.setValue(this.selectedCustomer.telephoneNumber);
            });

          this.onIDTypeChange(this.leadUpdateDTO.identificationType);

          this.results = new BehaviorSubject(this.allBranches);
          this.onBranchNameChangeSub.unsubscribe();
          this.onBranchNameChangeSub = this.componentForm.controls.branchName.valueChanges
            .subscribe((value: any) => {
              this.results.next(this.filterBranchNameValueChange(value));
            });

          this.onIdentificationTypeChangeSub.unsubscribe();
          this.onIdentificationTypeChangeSub = this.componentForm.controls.identificationType.valueChanges
            .subscribe((value: any) => {
              console.log("test",value)
              this.componentForm.controls.identificationNumber.setValue('');
              if (value == Constants.customerIdentificationTypeConst.NIC) {
                this.isSelectNIC = true;
                this.identificationNumberLabel = 'National Identity Card'
              }
              if (value == Constants.customerIdentificationTypeConst.BRC) {
                 this.isSelectNIC = false;
                this.identificationNumberLabel = 'Business Registration Number'
              }
              if (value == Constants.customerIdentificationTypeConst.PP) {
                   this.isSelectNIC = false;
                this.identificationNumberLabel = 'Passport Number'
              }
            });

          this.onLeadCreationTypeChangeSub.unsubscribe();
          this.onLeadCreationTypeChangeSub = this.componentForm.get('leadCreationType').valueChanges
            .subscribe((value: any) => {
              if ((value == this.leadCreationTypeConst.BUSINESS) || (value == this.leadCreationTypeConst.CORPORATE)) {
                this.componentForm.get('identificationType').setValue('BRC');
              } else {
                this.componentForm.get('identificationType').setValue('NIC');
              }
              this.leadUpdateDTO.leadCreationType = value;
              this.componentForm = this.createDynamicForm();
            });

        });


      // this.onCustomerNameChangeSub = this.componentForm.controls.customerName.valueChanges
      //   .subscribe((value: any) => {
      //     this.customerResults.next(this.filterCustomer(value))
      //   });

      this.ondownloadLinkChangeSub = this.leadAddEditService.onDownloadLinkChanged
        .subscribe(data => {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data.data);
          downloadLink.download = data.fileName;
          downloadLink.click();
          this.alertService.showToaster("Lead document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
        });

      this.onAgentFacilityPaperDraftedSubs = this.leadAddEditService.onAgentFacilityPaperDrafted
        .subscribe((facilityPaper: any) => {
          this.selectedFacilityPaperID = this.urlEncodeService.encode(facilityPaper.facilityPaperID);
          this.router.navigate(['/facility-paper/edit']);
          if (facilityPaper.facilityDTOList.length > 0) {
            this.alertService.showToaster("Added Facilities Should be Completed ", SETTINGS.TOASTER_MESSAGES.warning);
          }
        });

      this.onLeadFacilityPaperStatusHistoryChangeSub = this.leadAddEditService.onLeadFacilityPaperStatusHistoryChange.subscribe((res: any) => {
        this.facilityPaperStatusHistory = res;
      });

      if (this.leadUpdateDTO.leadStatus == this.leadStatusConst.PAPER_CREATED) {
        this.leadAddEditService.getFacilityPaperHistoryForLead(this.leadUpdateDTO);
      }

    }

    ngOnDestroy(): void {

      this.onFormValueChangeSub.unsubscribe();
      this.onSelectedCustomerChangeSub.unsubscribe();
      this.onSelectedLeadChangeSub.unsubscribe();
      this.onLoadActiveCustomerListChangeSub.unsubscribe();
      this.onLoadBranchListChangeSub.unsubscribe();
      this.ondownloadLinkChangeSub.unsubscribe();
      this.onCustomerNameChangeSub.unsubscribe();
      this.onBranchNameChangeSub.unsubscribe();
      this.onAgentFacilityPaperDraftedSubs.unsubscribe();
      this.onIdentificationTypeChangeSub.unsubscribe();
      this.onLeadCreationTypeChangeSub.unsubscribe();
      this.onLeadFacilityPaperStatusHistoryChangeSub.unsubscribe();
    }

    addIdentificationNumber=()=>{
       this.isAddedNIC = !this.isAddedNIC;
    }



    createLeadForm() {

      let identificationType = this.leadUpdateDTO.identificationType ? this.leadUpdateDTO.identificationType : this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.PERSONAL ? Constants.customerIdentificationTypeConst.NIC : Constants.customerIdentificationTypeConst.BRC;
      let leadCreationType = this.leadUpdateDTO.leadCreationType ? this.leadUpdateDTO.leadCreationType : Constants.leadCreationTypeConst.PERSONAL;
      let leadFsTypeDefault = Constants.leadFsTypeConst.OTHER;
      this.componentForm = this.formBuilder.group({
        leadName: [{value: this.leadUpdateDTO.leadName, disabled: this.isFormDisabled}, [Validators.required]],
        name: [{value: this.leadUpdateDTO.name, disabled: this.isFormDisabled}, [Validators.required]],
        email: [{value: this.leadUpdateDTO.email, disabled: this.isFormDisabled}, [Validators.email]],
        accountNumber: [{value: this.leadUpdateDTO.accountNumber, disabled: this.isFormDisabled}],
        civilStatus : [{value: this.leadUpdateDTO.civilStatus, disabled: this.isFormDisabled}],
        mobileNo: [{value: this.leadUpdateDTO.mobileNo, disabled: this.isFormDisabled},
          [Validators.required, Validators.pattern("^((\\+94-?)|0)?[0-9]{10}$")]],
        address: [{value: this.leadUpdateDTO.address, disabled: this.isFormDisabled}, Validators.required],
        identificationType: [{value: identificationType, disabled: this.isFormDisabled},
          [Validators.required]],
        identificationNumber: [{value: this.leadUpdateDTO.identificationNumber, disabled: this.isFormDisabled},
          [Validators.required]],
        branchName: [{value: this.leadUpdateDTO.branchName, disabled: this.isFormDisabled}, [Validators.required]],
        isExistingCustomer: [{
          value: this.leadUpdateDTO.isExistingCustomer == 'Y',
          disabled: this.isFormDisabled
        }],
        // incomeType: [{
        //   value: false,
        //   disabled: this.isFormDisabled
        // }],
        
       // leadFsType: [Validators.required],
       leadFsType: [{
            value: leadFsTypeDefault,
            disabled: this.isFormDisabled
        }],
        leadCreationType: [{
          value: leadCreationType,
          disabled: !!this.leadUpdateDTO.leadID
        }, [Validators.required]],
      });


      this.componentForm = this.createDynamicForm();
      this.componentForm.setValidators(IdentificationNumberValidator.validateIdentificationNumber);
      return this.componentForm;
    }

    isValid() {
      return this.componentForm.valid;
    }

    isDirty() {
      return this.componentForm.dirty || this.inActivated || this.dataSourceHasValue;
    }

    isActiveFacilitiesAvailable() {
      //this returns true at lease one active facility available
      let numberOfActiveItems = 0;
      this.dataSource.forEach(item => {
        if (item.status == 'ACT') {
          numberOfActiveItems += 1;
        }
      });
      return numberOfActiveItems > 0;
    }

    filterBranchNameValueChange(value: string): string[] {
      if (value) {
        const filterValue = value.toLowerCase();
        return this.allBranches.filter((item: any) => item.branchName.toLowerCase().includes(filterValue));
      } else {
        return this.allBranches;
      }
    }

    filterCustomer(value: string): string[] {
      const filterValue = value.toLowerCase();
      return this.activeCustomers.filter((item: any) => item.customerName.toLowerCase().includes(filterValue));
    }

    hasCustomer() {
      return this.selectedCustomer.customerID != null && this.leadUpdateDTO.customerID != "undefined"
    }

    openModalLeadFacility() {
      this.modalRef = this.mdbModalService.show(LeadFacilityDetailComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-60-p modal-dialog-scrollable',
        containerClass: '',
        animated: false
      });

      this.modalRef.content.action.subscribe((result: any) => {
        this.dataSource.push(result);
        this.dataSourceHasValue = true;
      });
    }

    createSaveObject() {

      let branch = AppUtils.getBranchFromBranchName(this.allBranches, this.componentForm.value.branchName);
      //let customer = AppUtils.getCustomerFromCustomerName(this.activeCustomers, this.componentForm.value.customerName);

      let saveData = Object.assign({},
        this.leadUpdateDTO,
        {customerID: this.selectedCustomer.customerID},
        this.componentForm.getRawValue(),
        {isExistingCustomer: this.componentForm.get('isExistingCustomer').value ? 'Y' : 'N'},
        {branchCode: branch.branchCode ? branch.branchCode : ''},
        {createdUserDisplayName:  this.applicationService.getLoggedInUserDisplayName()},
        {assignUserDisplayName:  this.applicationService.getLoggedInUserDisplayName()},
      );

      if (!this.leadUpdateDTO.leadID) {
        saveData.leadFacilityDetailDTOList = [];
      }

      this.dataSource.forEach((leadFacility: any) => {
        if (!leadFacility.leadFacilityDetailID) {
          saveData.leadFacilityDetailDTOList.push(leadFacility);
        } else {
          let index = _.findIndex(saveData.leadFacilityDetailDTOList, (l: any) => l.leadFacilityDetailID === leadFacility.leadFacilityDetailID);
          if (index !== -1) {
            saveData.leadFacilityDetailDTOList[index] = leadFacility;
          }
        }
      });
      return saveData;
    }

    saveUpdate() {
      let saveData = this.createSaveObject();
     // saveData.civilStatus = saveData.civilStatus ? saveData.civilStatus : null;
      this.leadAddEditService.saveUpdateLead(saveData).subscribe((lead: any) => {
        if (lead.isLast3MonthsLeadFound && lead.isLast3MonthsLeadFound === 'Y') {
          this.showMessagePopUp(lead)
        } else {
          this.leadAddEditService.onSelectedLeadChange.next(lead);
          this.alertService.showToaster("Lead saved successfully", SETTINGS.TOASTER_MESSAGES.success)
          this.loadLead(lead.leadID);
        }
      }, (error) => {
        //console.log(error);
        this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error);
      });
    }

    openModalLeadDocument() {

      const initialState = {
        list: [
          {"tag": 'Count', "value": this.leadUpdateDTO}
        ]
      };

      this.modalRef = this.mdbModalService.show(LeadDocumentComponent, {
        initialState,
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p modal-margin-center",
        containerClass: 'right',
        animated: false,
        data: {
          heading: "comming dto",
          content: {dto: this.leadUpdateDTO}
        }
      });


      this.modalRef.content.action.subscribe((result: any) => {
        if (result) {
          this.dataSourceForLeadDocument.push(result)
        }
      });
    }

    unAssign(selectedLeadID) {

      let data = Object.assign({},
        {leadID: selectedLeadID},
        {leadStatus: this.leadStatusConst.PENDING},
        {assignUserID: ''}
      );
      this.leadAddEditService.updateLeadStatusOrAssignee(data);
    }

    Assign(selectedLeadID) {
      let data = Object.assign({},
        {leadID: selectedLeadID},
        {leadStatus: this.leadStatusConst.SUBMITTED},
        {assignUserID: this.applicationService.getLoggedInUserUserName()}
      );
      this.leadAddEditService.updateLeadStatusOrAssignee(data);
    }


    showExternalLeadActions() {

      if (!this.leadUpdateDTO.leadID) {
        return true;
      }

      if (this.leadUpdateDTO.leadType == this.leadTypeConst.EXTERNAL) {
        return true
      }

      if (this.leadUpdateDTO.leadStatus == this.leadStatusConst.PENDING || this.leadUpdateDTO.leadStatus == this.leadStatusConst.RETURNED) {
        return true;
      }

      if (this.leadUpdateDTO.leadStatus == this.leadStatusConst.SUBMITTED &&
        this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.assignUserID) {
        return true;
      }

      return false;

    }

    confirmExternalLeadAssign() {
      //here we update the lead first and then success response pop up shows
      let saveData = this.createSaveObject();
      this.leadAddEditService.updateLead(saveData).subscribe((response: any) => {
        if (response.leadID) {
          this.leadAddEditService.onSelectedLeadChange.next(response);

          this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
            backdrop: true,
            keyboard: true,
            focus: true,
            show: false,
            ignoreBackdropClick: true,
            class: 'modal-width-30-p modal-margin-center ',
            containerClass: '',
            animated: false,
            data: {
              heading: "Submit Lead",
              message: "Do you want to submit the lead to your supervisor ?",
            }
          });
          this.modalRef.content.action.subscribe((isYes: any) => {
            if (isYes) {
              let data = Object.assign({},
                {leadID: this.leadUpdateDTO.leadID},
                {leadStatus: this.leadStatusConst.SUBMITTED},
                {assignUserID: this.applicationService.getAgentSupervisorADUserID()},
                {actionedByDisplayName: this.applicationService.getUserDisplayName()},
                {action: this.leadActionConst.SUBMIT},
              );
              this.leadAddEditService.updateLeadStatusOrAssignee(data);
              this.router.navigate(['/leads']);
            }
          });
        }
      }, () => {
        console.log("Error In Updating the Lead");
      });
    }

    confirmInternalLeadAssign() {
      //here we update the lead first and then success response pop up shows
      let saveData = this.createSaveObject();
      this.leadAddEditService.updateLead(saveData).subscribe((response: any) => {
        if (response.leadID) {
          this.leadAddEditService.onSelectedLeadChange.next(response);

          this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
            backdrop: true,
            keyboard: true,
            focus: true,
            show: false,
            ignoreBackdropClick: true,
            class: 'modal-width-30-p modal-margin-center ',
            containerClass: '',
            animated: false,
            data: {
              heading: "Submit Lead",
              message: "Do you want to submit the lead ?",
            }
          });
          this.modalRef.content.action.subscribe((isYes: any) => {
            if (isYes) {
              let data = Object.assign({},
                {leadID: this.leadUpdateDTO.leadID},
                {leadStatus: this.leadStatusConst.SUBMITTED},
                {assignUserID: ''},
                {actionedByDisplayName: this.applicationService.getUserDisplayName()},
                {action: this.leadActionConst.SUBMIT},
              );
              this.leadAddEditService.updateLeadStatusOrAssignee(data);
              this.router.navigate(['/leads']);
            }
          });
        }
      }, () => {
        console.log("Error In Updating the Lead");
      });
    }

    onChangeStatus(itemFromTable) {
      if (itemFromTable.leadFacilityDetailID == '') {
        this.dataSource.forEach((item, index) => {
          if (itemFromTable === item) this.dataSource.splice(index, 1);
        });
      }
      if (itemFromTable.status == Constants.statusConst.ACT) {
        itemFromTable.status = Constants.statusConst.INA;
        this.inActivated = true;
      }
    }

    onDownLoadDoc(item) {
      if (item.docStorageDTO.docStorageID != null) {
        const extension = item.docStorageDTO.fileName.substring(item.docStorageDTO.fileName.lastIndexOf("."));

        if (extension == '.pdf') {
          this.leadAddEditService.viewLeadDocument(item.docStorageDTO).then((data: any) =>{
            this.viewInNewTab(data, item.docStorageDTO.fileName);
          });

        } else {
          this.leadAddEditService.downloadLeadDocument(item.docStorageDTO).then((data: any) =>{
            let downloadLink = this.downloadLink.nativeElement;
             downloadLink.href = window.URL.createObjectURL(data);
             downloadLink.download = item.docStorageDTO.fileName;
             downloadLink.click();
             this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
         });
        }


      }
    }

    onViewLeadAuditClick(leadID) {
      if (leadID == null) {
        this.selectedLeadID = null;
      } else {
        this.selectedLeadID = this.urlEncodeService.encode(leadID);
      }
      this.router.navigate(['/leads/lead-audit-detail']);
    }

    onIDTypeChange(value) {
      if (_.isEmpty(value)) {
        this.identificationType = false;
      } else {
        this.identificationType = true;
      }
    }

    openModalCustomerSearch() {

      this.modalRef = this.mdbModalService.show(LeadCustomerDetailComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-85-p audit-modal-margin-center',
        containerClass: 'right',
        animated: false
      });

      this.modalRef.content.action.subscribe((result: any) => {
        this.dataSourceForCustomers = result;
        this.selectedCustomer = result || {};
      });
    }

    getFormattedValue(amount) {
      if (amount != null) {
        return this.currencyPipe.transform(amount, '', '')
      }
    }

    showAgentLeadActions() {
      if (this.leadUpdateDTO.leadType == Constants.leadTypeConst.INTERNAL) {
        return this.leadUpdateDTO.leadID && (
          this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.createdBy
        );
      } else {

      }
    }

    showInternalLeadActionButtons() {
      return this.leadUpdateDTO.leadID &&
        this.applicationService.getLoggedInUserUserName() !== this.leadUpdateDTO.createdBy
        && this.leadUpdateDTO.leadStatus == this.leadStatusConst.SUBMITTED
    }

    showExternalLeadActionButtons() {
      return this.leadUpdateDTO.leadID
        && this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.assignUserID && this.leadUpdateDTO.leadStatus == this.leadStatusConst.SUBMITTED
    }

    acceptLeadConfirmation() {
      this.modalRef = this.mdbModalService.show(InternalLeadAcceptComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-60-p modal-margin-center audit-modal-margin-center',
        containerClass: '',
        animated: false,
        data: {
          leadID: this.leadUpdateDTO.leadID,
          createdBy: this.leadUpdateDTO.createdBy,
          accountNumber: this.leadUpdateDTO.accountNumber
        }
      });
      this.modalRef.content.action.subscribe((response: any) => {
        if (response) {
          this.router.navigate(['/leads']);
        }
      });
    }

    approveLeadConfirmation(isAccept) {

      if (this.leadUpdateDTO.leadType == Constants.leadTypeConst.INTERNAL) {
        this.modalRef = this.mdbModalService.show(InternalLeadApproveComponent, {
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: 'modal-width-60-p modal-margin-center audit-modal-margin-center',
          containerClass: '',
          animated: false,
          data: {
            leadID: this.leadUpdateDTO.leadID,
            createdBy: this.leadUpdateDTO.createdBy,
            isAccept: isAccept,
            accountNumber: this.leadUpdateDTO.accountNumber
          }
        });
      } else {
        this.modalRef = this.mdbModalService.show(ExternalLeadApproveComponent, {
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: 'modal-width-60-p modal-margin-center audit-modal-margin-center',
          containerClass: '',
          animated: false,
          data: {
            leadID: this.leadUpdateDTO.leadID,
            createdBy: this.leadUpdateDTO.createdBy,
            accountNumber: this.leadUpdateDTO.accountNumber
          }
        });
      }

      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.router.navigate(['/leads']);
        }
      });
    }

    returnLeadConfirmation() {
      this.modalRef = this.mdbModalService.show(ExternalLeadReturnComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-45-p modal-margin-center ',
        containerClass: '',
        animated: false,
        data: {
          leadID: this.leadUpdateDTO.leadID,
          createdBy: this.leadUpdateDTO.createdBy
        }
      });

      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.router.navigate(['/leads']);
        }
      });
    }

    closeLeadConfirmation() {
      this.modalRef = this.mdbModalService.show(ExternalLeadCloseComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-45-p modal-margin-center ',
        containerClass: '',
        animated: false,
        data: {
          leadID: this.leadUpdateDTO.leadID,
          createdBy: this.leadUpdateDTO.createdBy
        }
      });

      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.router.navigate(['/leads']);
        }
      });
    }

    declineLeadConfirmation() {
      this.modalRef = this.mdbModalService.show(ExternalLeadDeclineComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-45-p modal-margin-center ',
        containerClass: '',
        animated: false,
        data: {
          leadID: this.leadUpdateDTO.leadID,
          createdBy: this.leadUpdateDTO.createdBy
        }
      });

      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.router.navigate(['/leads']);
        }
      });
    }

    isActionsActivated() {
      if (this.leadUpdateDTO.leadID) {
        return this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.createdBy
          && (this.leadUpdateDTO.leadStatus == this.leadStatusConst.PENDING || this.leadUpdateDTO.leadStatus == this.leadStatusConst.RETURNED);
      } else {
        return true; // this for new
      }
    }

    showInternalLeadSubmitBtn() {
      return this.leadUpdateDTO.leadID
        && this.leadUpdateDTO.isInternalLead()
        && this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.createdBy
        && this.leadUpdateDTO.leadStatus == this.leadStatusConst.PENDING
    }

    showSubmitExternalLead() {
      return this.leadUpdateDTO.leadID
        && (this.leadUpdateDTO.leadStatus == this.leadStatusConst.PENDING || this.leadUpdateDTO.leadStatus == this.leadStatusConst.RETURNED)
        && this.applicationService.isAgent();
    }

    showAgentStartPaperBtn() {
      return this.leadUpdateDTO.leadID
        && this.leadUpdateDTO.externalLead
        && this.leadUpdateDTO.leadStatus == this.leadStatusConst.APPROVED
        && this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.createdBy;
    }

    showCASStartPaperBtn() {
      return this.leadUpdateDTO.leadID
        && this.leadUpdateDTO.isInternalLead()
        && this.leadUpdateDTO.leadStatus == this.leadStatusConst.ACCEPTED
        && this.applicationService.getLoggedInUserUserName() == this.leadUpdateDTO.assignUserID;
    }

    agentConfirmStartPaper() {

      this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-30-p modal-margin-center ',
        containerClass: '',
        animated: false,
        data: {
          heading: "Start Paper",
          message: "Do you want to start a paper using this lead ?",
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {

          let submitData: any = {
            branchCode: this.leadUpdateDTO.branchCode,
            createdUserBranchCode: this.leadUpdateDTO.branchCode,
            bankAccountID: this.leadUpdateDTO.customerBankAccountNumber,
            isCooperate: 'N',
            leadRefNumber: this.leadUpdateDTO.leadRefNumber,
            leadID: this.leadUpdateDTO.leadID,
            leadType: this.leadTypeConst.EXTERNAL,
          };

          submitData.casCustomerDTOList = [{
            customerID: this.leadUpdateDTO.customerID,
            isPrimary: true,
            status: 'ACT',
          }];

          let facilityDTOList = [];
          this.leadUpdateDTO.leadFacilityDetailDTOList.forEach(
            (data, index) => {
              if (data.status == Constants.statusConst.ACT) {
                facilityDTOList.push({
                  creditFacilityTemplateID: data.facilityTemplateID,
                  creditFacilityTypeID: data.creditFacilityTypeID,
                  facilityAmount: data.amount,
                  facilityCurrency: data.facilityCurrency,
                  status: data.status,
                  displayOrder: index + 1
                });
              }
            }
          );
          submitData.facilityDTOList = facilityDTOList;
          submitData.currentAssignUser = this.applicationService.getLoggedInUserUserName();
          submitData.currentAssignUserID = this.applicationService.getLoggedInUserUserID();
          submitData.assignUserDisplayName = this.applicationService.getLoggedInUserDisplayName();
          this.leadAddEditService.draftFacilityPaperByLead(AppUtils.trim(submitData));
        }
      });
    }

    supervisorConfirmStartPaper() {
      this.modalRef = this.mdbModalService.show(InternalLeadStartPaperComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: 'modal-width-60-p',
        containerClass: '',
        animated: false,
        data: {
          heading: "Start Paper",
          message: "Do you want to start a paper using this lead ?",
          accountNumber: this.leadUpdateDTO.accountNumber,
          customerID: this.leadUpdateDTO.customerID
        }
      });
      this.modalRef.content.action.subscribe((data: any) => {
        if (data) {

          let submitData: any = {
            branchCode: this.leadUpdateDTO.branchCode,
            createdUserBranchCode: this.leadUpdateDTO.branchCode,
            bankAccountID: data.customerBankAccountNumber ? data.customerBankAccountNumber : this.leadUpdateDTO.customerBankAccountNumber,
            isCooperate: 'N',
            leadRefNumber: this.leadUpdateDTO.leadRefNumber,
            leadID: this.leadUpdateDTO.leadID,
            leadType: this.leadTypeConst.INTERNAL,
          };

          submitData.casCustomerDTOList = [{
            customerID: this.leadUpdateDTO.customerID,
            isPrimary: true,
            status: 'ACT',
          }];

          let facilityDTOList = [];
          this.leadUpdateDTO.leadFacilityDetailDTOList.forEach(
            (data, index) => {
              if (data.status == Constants.statusConst.ACT) {
                facilityDTOList.push({
                  creditFacilityTemplateID: data.facilityTemplateID,
                  creditFacilityTypeID: data.creditFacilityTypeID,
                  facilityAmount: data.amount,
                  facilityCurrency: data.facilityCurrency,
                  status: data.status,
                  displayOrder: index + 1
                });
              }
            }
          );
          submitData.facilityDTOList = facilityDTOList;
          submitData.currentAssignUser = this.applicationService.getLoggedInUserUserName();
          submitData.currentAssignUserID = this.applicationService.getLoggedInUserUserID();
          submitData.assignUserUpmID = this.applicationService.getLoggedInUserUserID();
          submitData.currentAssignUserDivCode = this.applicationService.getLoggedInUserDivCode();
          submitData.assignUserUpmGroupCode = this.applicationService.getLoggedInUserUPMGroupCode();
          submitData.assignUserDisplayName = this.applicationService.getLoggedInUserDisplayName();
          submitData.createdUserDisplayName = this.applicationService.getLoggedInUserDisplayName();
          this.leadAddEditService.draftFacilityPaperByLead(AppUtils.trim(submitData));
        }
      });
    }


    createDynamicForm() {
      if (this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.PERSONAL) {
        this.componentForm.addControl("dateOfBirthStr", new FormControl({
          value: this.leadUpdateDTO.dateOfBirthStr,
          disabled: this.isFormDisabled
        }, Validators.required));
      } else {
        this.componentForm.removeControl("dateOfBirthStr");
      }

      if (this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.PERSONAL) {
        this.componentForm.addControl("civilStatus", new FormControl({
          //value: this.leadUpdateDTO.civilStatus ? this.leadUpdateDTO.civilStatus : null,
          value: this.leadUpdateDTO.civilStatus,
          disabled: this.isFormDisabled
        }, Validators.required));
      } else {
        this.componentForm.removeControl("civilStatus");
      }

      if ((this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.BUSINESS) || (this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.CORPORATE)) {
        this.componentForm.addControl("typeOfBusiness", new FormControl({
          value: this.leadUpdateDTO.typeOfBusiness,
          disabled: this.isFormDisabled
        }, [Validators.required]));
      } else {
        this.componentForm.removeControl("typeOfBusiness");
      }

      if ((this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.BUSINESS) || (this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.CORPORATE))  {
        this.componentForm.addControl("designation", new FormControl({
          value: this.leadUpdateDTO.designation,
          disabled: this.isFormDisabled
        }));
      } else {
        this.componentForm.removeControl("designation");
      }

      if ((this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.BUSINESS) || (this.leadUpdateDTO.leadCreationType == Constants.leadCreationTypeConst.CORPORATE)) {
        this.componentForm.addControl("contactPerson", new FormControl({
          value: this.leadUpdateDTO.contactPerson,
          disabled: this.isFormDisabled
        }, [Validators.required]));
      } else {
        this.componentForm.removeControl("contactPerson");
      }
      return this.componentForm;
    }

    validateNumber(event) {
      NumberValidator.validateNumber(event);
    }

    navigateToFacilityPaper(facilityPaperID) {
      this.selectedFacilityPaperID = this.urlEncodeService.encode(facilityPaperID);
      this.router.navigate(['/facility-paper/edit']);
    }

    showMessagePopUp(lead) {
      const initialState = {
        list: [
          {"tag": 'Count', "value": lead}
        ]
      };

      this.modalRef = this.mdbModalService.show(LeadExistingMessageComponent, {
        initialState,
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p modal-margin-center",
        containerClass: 'right',
        animated: false,
        data: {
          heading: "comming dto",
          lead: {
            leadData: lead,
          }
        }
      });
    }

     loadLead(leadID) {
        if (leadID == null) {
          this.selectedLeadID = null;
        } else {
          this.selectedLeadID = this.urlEncodeService.encode(leadID);
        }
       this.router.navigate(['/leads/dashboard']);

     }


     downloadDocument(item) {
      if (item.docStorageDTO.docStorageID != null) {

          this.leadAddEditService.downloadLeadDocument(item.docStorageDTO).then((data: any) =>{
            let downloadLink = this.downloadLink.nativeElement;
             downloadLink.href = window.URL.createObjectURL(data);
             downloadLink.download = item.docStorageDTO.fileName;
             downloadLink.click();
             this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
         });
        
      }
    }

    viewInNewTab(data, fileName){

      const pdfBlob = new Blob([data], { type: 'application/pdf' });
      const pdfFile = new File([pdfBlob], fileName, { type: 'application/pdf' });
      const fileUrl = URL.createObjectURL(pdfFile);

      const newWindow = window.open();
          newWindow.document.write(
          `<html>
            <head>
              <title>${fileName}</title>
              <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
              <style>
                .menu-bar {
                  position: absolute;
                  top: 8px;
                  right: 80px;
                  
                  padding: 10px;
                  display: flex;
                  align-items: center;
                }

                .menu-bar button {
                  margin-right: 10px;
                }

                .menu-bar .download-button {
                  margin-left: auto;
                  background-color: transparent;
                  color: white;
                  border : none;
                  text-align: center;
                  text-decoration: none;
                  display: inline-block;
                  font-size: 16px;
                  border-radius: 500px;
                  cursor: pointer;
                  
                }


              </style>
            </head>
            <body>
              <div style="position: relative;">
                <embed src="${fileUrl}" id="pdfViewer" class="pdfjs-viewer" height="100%" width="100%" type="application/pdf" />
                <div class="menu-bar">
                  <button id="downloadButton" class = "download-button" title="Download PDF">
                    <i class='fas fa-download' style='color: white'></i> 
                  </button>
                </div>
              </div>

              <script>
                const downloadButton = document.getElementById('downloadButton');
                downloadButton.addEventListener('click', function() {
                  const pdfViewer = document.getElementById('pdfViewer');
                  const a = document.createElement('a');
                  a.href = pdfViewer.src;
                  a.download = '${data.fileName}'; // Set the desired download name here
                  a.style.display = 'none';
                  document.body.appendChild(a);
                  a.click();
                  document.body.removeChild(a);
                });
              </script>
            </body>
          </html>`
          );
          newWindow.document.close();

    }


    isCheckedPdf(item: any) {

      if (item.docStorageDTO.fileName.substring(item.docStorageDTO.fileName.lastIndexOf(".")) == '.pdf') {
        return true;
      } else{
        return false;
      }

    }


}
