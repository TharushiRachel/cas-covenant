import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CreditFacilityTemplateUpdateDto} from "../../dto/credit-facility-template-update-dto";
import {CreditFacilityTemplateAddEditService} from "../../services/credit-facility-template-add-edit.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {AppUtils} from "../../../../../shared/app.utils";
import {CftSupportingDocComponent} from "../cft-supporting-doc/cft-supporting-doc.component"
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {CftSupportingDocUpdateDto} from "../../dto/cft-supporting-doc-update-dto";
import {Constants} from "../../../../../core/setting/constants";
import * as _ from 'lodash';
import {CftInterestRateComponent} from "../cft-interest-rate/cft-interest-rate.component";
import {CftInterestRateUpdateDto} from "../../dto/cft-interest-Rate-update-dto";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {CftVitalQuestionComponent} from "../cft-vital-question/cft-vital-question.component";
import {CftVitalQuestionUpdateDto} from "../../dto/cft-vital-question-update-dto";
import {NumberValidator} from "../../../../../shared/validators/number.validator";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {OtherFacilityInformationComponent} from "../other-facility-information/other-facility-information.component";
import {CftOtherFacilityInformationDto} from "../../dto/cft-other-facility-information-dto";
import {ChangeOrderComponent} from "../../../../../shared/components/change-order/change-order.component";
import { CustomFacilityInformationComponent } from '../custom-facility-information/custom-facility-information.component';
import { CftCustomFacilityInformationDto } from '../../dto/cft-custom-facility-information-dto';

@Component({
  selector: 'app-credit-facility-template-add-edit',
  templateUrl: './credit-facility-template-add-edit.component.html',
  styleUrls: ['./credit-facility-template-add-edit.component.scss']
})
export class CreditFacilityTemplateAddEditComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;

  tableColumnsForCftsupportDoc = ['Supporting Doc', 'Mandatory', 'Status', 'Action'];
  tableColumnsForCftInterestRate = ['Rate Name', 'Sub Category', 'Rate Code', 'Value', 'Is Editable', 'Is Default', 'Status', 'Action'];
  tableColumnsForCftVitalQuestions = ['Vital Question', 'Mandatory', 'Status', 'Action'];
  tableColumnForOtherFacilityInformations = ['Order', 'Name', 'Code', 'Description', 'Value Type', 'Default Value', 'Status', 'Action'];
  tableColumnForCustomFacilityInformations = ['Order', 'Name', 'Code', 'Description', 'Value Type', 'Status', 'Action'];

  keyCodes = Constants.keyCodes;
  statusConst = Constants.statusConst;
  status = Constants.status;
  yesNoStatus = Constants.yesNo;
  yeNoConst = Constants.yesNoConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  approveStatus = Constants.approveStatusConst;

  pageType: String = 'new';
  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];
  yesNo = [
    {value: 'Y', label: 'Yes'},
    {value: 'N', label: 'No'},
  ];

  listStyle = {
    width: '100%', //width of the list defaults to 300,
    height: '100%', //height of the list defaults to 250,
    dropZoneHeight: '60px' // height of the dropzone indicator defaults to 50
  };

  facilityTypeList = [];
  supportDocumentList = [];
  componentForm: FormGroup;
  formErrors: any;
  result: Subject<any>;

  onFormValueChange: Subscription = new Subscription();
  onCreditFacilityTemplateChangeSub: Subscription = new Subscription();
  onCreditFacilityTypeListChangeSub: Subscription = new Subscription();
  onLoadSupportingDocListChangeSub: Subscription = new Subscription();
  facilityTypeListSub = new Subscription();

  someArray: { key: number, value: string }[] = [];
  dataSourceForCftSupportingDocHasValue: any = false;
  dataSourceForCftInterestRateHasValue: any = false;
  dataSourceForCftVitalQuestionHasValue: any = false;
  dataSourceForOtherFacilityInformationHasValue = false;
  dataSourceForCustomFacilityInformationHasValue = false;

  inActivated: any = false;
  dataSourceForCftSupportingDoc: CftSupportingDocUpdateDto[] = [];
  dataSourceForCftInterestRate: CftInterestRateUpdateDto[] = [];
  dataSourceForCftVitalQuestion: CftVitalQuestionUpdateDto[] = [];
  dataSourceForCftOtherFacilityInformation: CftOtherFacilityInformationDto[] = [];
  dataSourceForCftCustomFacilityInformation: CftCustomFacilityInformationDto[] = [];
  creditFacilityTemplateUpdateDTO: CreditFacilityTemplateUpdateDto = new CreditFacilityTemplateUpdateDto({});


  constructor(
    private creditFacilityTemplateAddEditService: CreditFacilityTemplateAddEditService,
    private urlEncodeService: UrlEncodeService,
    private cacheService: CacheService,
    private formBuilder: FormBuilder,
    private  mdbModalService: MDBModalService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {
    this.formErrors = {
      creditFacilityName: {},
      FacilityTypeName: {},
      description: {},
      maxFacilityAmount: {},
      minFacilityAmount: {},
      status: {},
    };

    this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES) ? this.cacheService.getData(Constants.masterDataKey.CAS_CREDIT_FACILITY_TYPES).forEach(data => {
      if (data.status == 'ACT' && data.approveStatus == 'APPROVED') {
        this.facilityTypeList.push(data);
      }
    }) : this.facilityTypeList = [];

    this.onLoadSupportingDocListChangeSub = this.creditFacilityTemplateAddEditService.onLoadSupportingDocListChange
      .subscribe(response => {
        this.supportDocumentList = this.creditFacilityTemplateAddEditService.supportingDocList;
      });

    this.onCreditFacilityTemplateChangeSub = this.creditFacilityTemplateAddEditService.onSelectCreditFacilityTemplateChange
      .subscribe(template => {
        if (_.isEmpty(template)) {
          this.pageType = 'new';
          this.creditFacilityTemplateUpdateDTO = new CreditFacilityTemplateUpdateDto({});
        } else {
          this.pageType = 'edit';
          this.creditFacilityTemplateUpdateDTO = new CreditFacilityTemplateUpdateDto(template);
          this.dataSourceForCftSupportingDoc = template.cftSupportingDocDTOList.splice(0);
          this.dataSourceForCftInterestRate = template.cftInterestRateDTOList.splice(0);
          this.dataSourceForCftVitalQuestion = template.cftVitalInfoDTOList.splice(0);
          this.dataSourceForCftOtherFacilityInformation = template.cftOtherFacilityInfoDTOList.splice(0);
          this.dataSourceForCftCustomFacilityInformation = template.cftCustomFacilityInfoDTOList.splice(0);

          this.dataSourceForCftCustomFacilityInformation.sort((a, b) => {
            return a.displayOrder - b.displayOrder;
          });
        }
        this.componentForm = this.createCreditFacilityTemplateForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges
          .subscribe(form => {
            this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
          });

        this.dataSourceForCftVitalQuestionHasValue = false;
      });

    this.result = new BehaviorSubject(this.facilityTypeList);
    this.onCreditFacilityTypeListChangeSub = this.componentForm.controls.FacilityTypeName.valueChanges
      .subscribe((value: any) => {
        this.result.next(this.filter(value));
      });
  }

  ngOnDestroy(): void {

    this.onCreditFacilityTemplateChangeSub.unsubscribe();
    this.onCreditFacilityTypeListChangeSub.unsubscribe();
    this.facilityTypeListSub.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  createCreditFacilityTemplateForm() {
    return this.formBuilder.group({
      showPurpose: [this.creditFacilityTemplateUpdateDTO.showPurpose],
      showRepayment: [this.creditFacilityTemplateUpdateDTO.showRepayment],
      showCondition: [this.creditFacilityTemplateUpdateDTO.showCondition],
      showRemark: [this.creditFacilityTemplateUpdateDTO.showRemark],
      showCalculator: [this.creditFacilityTemplateUpdateDTO.showCalculator],
      showRentalData: [this.creditFacilityTemplateUpdateDTO.showRentalData],
      creditFacilityName: [this.creditFacilityTemplateUpdateDTO.creditFacilityName, [Validators.required, Validators.maxLength(200)]],
      FacilityTypeName: [this.creditFacilityTemplateUpdateDTO.facilityTypeName, [Validators.required]],
      description: [this.creditFacilityTemplateUpdateDTO.description, [Validators.required]],
      maxFacilityAmount: [this.creditFacilityTemplateUpdateDTO.maxFacilityAmount, [Validators.required, NumberValidator.isCommaSeparatedValue]],
      minFacilityAmount: [this.creditFacilityTemplateUpdateDTO.minFacilityAmount, [Validators.required, NumberValidator.isCommaSeparatedValue]],
      status: [{
        value: this.creditFacilityTemplateUpdateDTO.status,
        disabled: this.pageType == 'new'
      }, [Validators.required]],
      showInLead: [this.creditFacilityTemplateUpdateDTO.showInLead],
    })

  }

  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.facilityTypeList.filter((item: any) => item.facilityTypeName.toLowerCase().includes(filterValue));
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty || this.dataSourceForCftSupportingDocHasValue || this.dataSourceForCftInterestRateHasValue
      || this.dataSourceForCftVitalQuestionHasValue || this.dataSourceForOtherFacilityInformationHasValue || this.inActivated;
  }

  listOrderChanged(event: any) {
    // console.log(event);
  }

  saveUpdate() {

    let facility = AppUtils.getFacilityTypeFromFacilityName(this.facilityTypeList, this.componentForm.controls.FacilityTypeName.value);
    let orderedVitalQuestions = this.dataSourceForCftVitalQuestion.map((data, index) => {
      data.displayOrder = index + 1;
      return data;
    });

    let saveData = Object.assign({},
      this.creditFacilityTemplateUpdateDTO,
      this.componentForm.getRawValue(),
      {creditFacilityTypeID: facility.creditFacilityTypeID ? facility.creditFacilityTypeID : null},
      {facilityTypeName: facility.facilityTypeName ? facility.facilityTypeName : ''},
      {cftSupportingDocDTOList: this.dataSourceForCftSupportingDoc},
      {cftInterestRateDTOList: this.dataSourceForCftInterestRate},
      {cftVitalInfoDTOList: orderedVitalQuestions},
      {
        approveStatus: this.creditFacilityTemplateUpdateDTO.approveStatus,
        approvedBy: this.creditFacilityTemplateUpdateDTO.approvedBy,
        approvedDateStr: this.creditFacilityTemplateUpdateDTO.approvedDateStr
      },
      {
        cftOtherFacilityInfoDTOList: this.dataSourceForCftOtherFacilityInformation.map((data: any, index) => {
          if (data.status == this.statusConst.ACT) {
            data.displayOrder = index;
          } else {
            data.displayOrder = -1;
          }
          return data;
        })
      },
      {
        cftCustomFacilityInfoDTOList: this.dataSourceForCftCustomFacilityInformation.map((data: any, index) => {
          if (data.status == this.statusConst.ACT) {
            data.displayOrder = index;
          } else {
            data.displayOrder = -1;
          }
          return data;
        })
      },
      //{cftCustomFacilityInfoDTOList: this.dataSourceForCftCustomFacilityInformation},
      {showPurpose: this.componentForm.get('showPurpose').value ? 'Y' : 'N'},
      {showRepayment: this.componentForm.get('showRepayment').value ? 'Y' : 'N'},
      {showCondition: this.componentForm.get('showCondition').value ? 'Y' : 'N'},
      {showRemark: this.componentForm.get('showRemark').value ? 'Y' : 'N'},
      {showCalculator: this.componentForm.get('showCalculator').value ? 'Y' : 'N'},
      {showRentalData: this.componentForm.get('showRentalData').value ? 'Y' : 'N'},
      {showInLead: this.componentForm.get("showInLead").value ? 'Y' : 'N'}
    );
    this.creditFacilityTemplateAddEditService.saveUpdateCreditFacilityTemplate(AppUtils.trim(saveData));
  }


  isApproveOrRejectValid() {
    return this.creditFacilityTemplateUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }

  openModalCftSupportingDoc() {
    this.modalRef = this.mdbModalService.show(CftSupportingDocComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center audit-modal-margin-center',
      containerClass: '',
      animated: true
    });
    this.modalRef.content.action.subscribe((result: any) => {
      this.dataSourceForCftSupportingDoc.push(result);
      this.dataSourceForCftSupportingDocHasValue = true;
    });
  }

  openModalCftVitalQuestion() {
    this.modalRef = this.mdbModalService.show(CftVitalQuestionComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center audit-modal-margin-center',
      containerClass: '',
      animated: true
    });
    this.modalRef.content.action.subscribe((result: any) => {
      this.dataSourceForCftVitalQuestion.push(result);
      this.dataSourceForCftVitalQuestionHasValue = true;
    });
  }

  onChangeStatus(itemFromTable) {

    if (itemFromTable.cftVitalInfoID == '') {
      this.dataSourceForCftVitalQuestion.forEach((item, index) => {
        if (itemFromTable === item) this.dataSourceForCftVitalQuestion.splice(index, 1);
      });
    }
    if (itemFromTable.cftSupportingDocID == '') {
      this.dataSourceForCftSupportingDoc.forEach((item, index) => {
        if (itemFromTable === item) this.dataSourceForCftSupportingDoc.splice(index, 1);
      });
    }
    if (itemFromTable.cftInterestRateID == '') {
      this.dataSourceForCftInterestRate.forEach((item, index) => {
        if (itemFromTable === item) this.dataSourceForCftInterestRate.splice(index, 1);
      })
    }
    if (itemFromTable.cftVitalInfoID == '') {
      this.dataSourceForCftInterestRate.forEach((item, index) => {
        if (itemFromTable === item) this.dataSourceForCftOtherFacilityInformation.splice(index, 1);
      })
    }
    if (itemFromTable.cftVitalInfoID == '') {
      this.dataSourceForCftInterestRate.forEach((item, index) => {
        if (itemFromTable === item) this.dataSourceForCftCustomFacilityInformation.splice(index, 1);
      })
    }
    if (itemFromTable.status == Constants.statusConst.ACT) {
      itemFromTable.status = Constants.statusConst.INA;
      this.inActivated = true;
    }
  }

  openModalCftInterestRate() {
    this.modalRef = this.mdbModalService.show(CftInterestRateComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center audit-modal-margin-center',
      containerClass: '',
      animated: true
    });
    this.modalRef.content.action.subscribe((result: any) => {
      this.dataSourceForCftInterestRate.push(result);
      this.dataSourceForCftInterestRateHasValue = true;
      this.inActivated = true;
    });
  }

  openModalOtherFacilityInformation() {
    this.modalRef = this.mdbModalService.show(OtherFacilityInformationComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-40-p modal-dialog-scrollable',
      containerClass: '',
      animated: true
    });
    this.modalRef.content.action.subscribe((result: any) => {
      result.displayOrder = this.dataSourceForCftOtherFacilityInformation.length + 1;
      this.dataSourceForCftOtherFacilityInformation.push(result);
      this.dataSourceForOtherFacilityInformationHasValue = true;
      this.inActivated = true;
    });
  }

  openModalUpdateOrder(dataList) {
    this.modalRef = this.mdbModalService.show(ChangeOrderComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        heading: "Change Order",
        actionName: "Ok",
        dataList: dataList,
        keyData: {
          keyOne: 'otherFacilityInfoName',
          keyTwo: 'otherFacilityInfoCode'
        }
      }
    });
    this.modalRef.content.action.subscribe((result: any) => {
      if (!_.isEmpty(result)) {
        this.inActivated = true;
      }
    });
  }

  openModalUpdateCustomInfoOrder(dataList){
    this.modalRef = this.mdbModalService.show(ChangeOrderComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        heading: "Change Order",
        actionName: "Ok",
        dataList: dataList,
        keyData: {
          keyOne: 'customFacilityInfoName',
          keyTwo: 'customFacilityInfoCode'
        }
      }
    });
    this.modalRef.content.action.subscribe((result: any) => {
      if (!_.isEmpty(result)) {
        this.inActivated = true;
      }
    });
  }

  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.creditFacilityTemplateUpdateDTO.creditFacilityTemplateID},
      {approveStatus: this.approveStatus.APPROVED});
    this.creditFacilityTemplateAddEditService.approveOrRejectCreditFacilityTemplate(data);
  }

  reject() {
    let data = Object.assign({},
      {approveRejectDataID: this.creditFacilityTemplateUpdateDTO.creditFacilityTemplateID},
      {approveStatus: this.approveStatus.REJECTED});
    this.creditFacilityTemplateAddEditService.approveOrRejectCreditFacilityTemplate(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.creditFacilityTemplateUpdateDTO.modifiedBy ? this.creditFacilityTemplateUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.creditFacilityTemplateUpdateDTO.createdBy ? this.creditFacilityTemplateUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }

  //new codes for custom facilities
  openModalCustomFacilityInformation() {
    this.modalRef = this.mdbModalService.show(CustomFacilityInformationComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-40-p modal-dialog-scrollable',
      containerClass: '',
      animated: true
    });
    this.modalRef.content.action.subscribe((result: any) => {
      result.displayOrder = this.dataSourceForCftCustomFacilityInformation.length + 1;
      this.dataSourceForCftCustomFacilityInformation.push(result);
      this.dataSourceForCustomFacilityInformationHasValue = true;
      this.inActivated = true;
    });
  }

}
