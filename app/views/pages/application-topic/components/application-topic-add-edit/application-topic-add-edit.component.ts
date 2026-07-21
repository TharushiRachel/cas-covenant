import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../core/setting/constants";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import * as _ from "lodash";
import {AppUtils} from "../../../../../shared/app.utils";
import {AfTopicDto} from "../../dto/af-topic-dto";
import {ApplicationFormTopicAddEditService} from "../../services/application-form-topic-add-edit.service";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormTopicAddDataComponent} from "./application-form-topic-add-data/application-form-topic-add-data.component";
import {ApplicationFormUpcTopicAddEditComponent} from "./application-form-upc-topic-add-edit/application-form-upc-topic-add-edit.component";
import {ConfirmationDialogComponent} from "../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {NumberValidator} from "../../../../../shared/validators/number.validator";

@Component({
  selector: 'app-application-topic-add-edit',
  templateUrl: './application-topic-add-edit.component.html',
  styleUrls: ['./application-topic-add-edit.component.scss']
})
export class ApplicationTopicAddEditComponent implements OnInit {
  @ViewChild('link', {static: false}) private link: ElementRef;
  componentForm: FormGroup;
  modalRef: MDBModalRef;
  formErrors: any;
  selectedUpdateDTO: AfTopicDto = new AfTopicDto({});
  onSelectedItemChange: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();

  pageType: string = 'new';
  approveStatus = Constants.approveStatusConst;
  applicationFormTopicPage = Constants.applicationFormTopicPage;
  applicationFormTopicPageConst = Constants.applicationFormTopicPageConst;
  statusConst = Constants.statusConst;
  status = Constants.status;

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  yesNo = [
    {value: 'Y', label: 'Yes'},
    {value: 'N', label: 'No - (Optional Topic)'},
  ];

  pageOptionSelect = [
    {value: this.applicationFormTopicPageConst.REPAYMENT, label: this.applicationFormTopicPage.REPAYMENT},
    {value: this.applicationFormTopicPageConst.ASSETS, label: this.applicationFormTopicPage.ASSETS},
    {value: this.applicationFormTopicPageConst.EXECUTIVE_SUMMARY, label: this.applicationFormTopicPage.EXECUTIVE_SUMMARY},
  ];

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  systemPrivileges: any = {};
  privilegeCategories: any = [];
  checkedPrivilegeIDs: any = [];

  constructor(private formBuilder: FormBuilder,
              private applicationFormTopicAddEditService: ApplicationFormTopicAddEditService,
              private applicationService: ApplicationService,
              private mdbModalService: MDBModalService,) {
  }

  ngOnInit() {

    this.formErrors = {
      page: {},
      topic: {},
      description: {},
      status: {},
      topicCode: {}
    };

    this.onSelectedItemChange = this.applicationFormTopicAddEditService.onSelectedItemChange
      .subscribe(item => {
        if (_.isEmpty(item)) {
          this.pageType = 'new';
          this.selectedUpdateDTO = new AfTopicDto({});
        } else {
          this.pageType = 'edit';
          this.selectedUpdateDTO = new AfTopicDto(item);
        }

        this.componentForm = this.createRoleForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
          this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
        });

      });
  }

  ngOnDestroy(): void {
    this.onSelectedItemChange.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  createRoleForm() {
    return this.formBuilder.group({
      page: [this.selectedUpdateDTO.page, [Validators.required, Validators.maxLength(50)]],
      topic: [this.selectedUpdateDTO.topic, [Validators.required]],
      topicCode: [this.selectedUpdateDTO.topicCode],
      description: [this.selectedUpdateDTO.description],
      status: [{value: this.selectedUpdateDTO.status, disabled: this.pageType == 'new'}, [Validators.required]]
    });
  }

  isApproveOrRejectValid() {
    return this.selectedUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  saveUpdate() {
    let saveData = Object.assign({}, this.selectedUpdateDTO, this.componentForm.getRawValue(),
      {
        approveStatus: this.selectedUpdateDTO.approveStatus,
        approvedBy: this.selectedUpdateDTO.approvedBy,
        approvedDateStr: this.selectedUpdateDTO.approvedDateStr
      }
    );
    this.applicationFormTopicAddEditService.saveUpdateItem(saveData);
  }

  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.selectedUpdateDTO.topicID},
      {approveStatus: this.approveStatus.APPROVED});
    this.applicationFormTopicAddEditService.approveOrReject(data);
  }

  reject() {

    let data = Object.assign({},
      {approveRejectDataID: this.selectedUpdateDTO.topicID},
      {approveStatus: this.approveStatus.REJECTED});
    this.applicationFormTopicAddEditService.approveOrReject(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.selectedUpdateDTO.modifiedBy ? this.selectedUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.selectedUpdateDTO.createdBy ? this.selectedUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  openModalTemplateData($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApplicationFormTopicAddDataComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-lg',
      containerClass: '',
      animated: false,
      data: {
        heading: "comming dto",
        content: {dataToEdit: this.selectedUpdateDTO.topicData},
      }
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        this.selectedUpdateDTO.topicData = result;
        this.componentForm.markAsDirty({onlySelf: true});
      }
    });
  }


  openModalAddUPCTemplate($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApplicationFormUpcTopicAddEditComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-lg',
      containerClass: '',
      animated: false,
      data: {
        heading: "Link UPC Topic",
        content: {updateDTO: this.selectedUpdateDTO},
      }
    });
    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        this.selectedUpdateDTO.afTopicUpcSectionDTOList.push(result);
        this.componentForm.markAsDirty({onlySelf: true});
        this.link.nativeElement.click();
      }
    });
  }

  remove(topicUpcSection) {

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Confirm Remove",
        message: "Do you want to remove this mapping with " + topicUpcSection.upcSectionName + " ? ",
      }
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        if (topicUpcSection.cftVitalInfoID == '') {
          this.selectedUpdateDTO.afTopicUpcSectionDTOList.forEach((item, index) => {
            if (topicUpcSection === item) this.selectedUpdateDTO.afTopicUpcSectionDTOList.splice(index, 1);
          });
        }
        if (topicUpcSection.status == Constants.statusConst.ACT) {
          topicUpcSection.status = Constants.statusConst.INA;
        }
        this.componentForm.markAsDirty({onlySelf: true});
      }
    });
  }

  getIcon() {
    return this.selectedUpdateDTO.topicData ? "pencil-alt" : "fas fa-plus";
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }
}
