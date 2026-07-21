import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MasterDataService} from "../../../../../../core/service/data/master-data.service";
import * as _ from "lodash";
import {Subject, Subscription} from "rxjs";
import {ApplicationFormTopicAddEditService} from "../../../services/application-form-topic-add-edit.service";
import {Constants} from "../../../../../../core/setting/constants";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../core/service/common/alert.service";

@Component({
  selector: 'app-application-form-upc-topic-add-edit',
  templateUrl: './application-form-upc-topic-add-edit.component.html',
  styleUrls: ['./application-form-upc-topic-add-edit.component.scss']
})
export class ApplicationFormUpcTopicAddEditComponent implements OnInit, OnDestroy {
  action: Subject<any> = new Subject<any>();
  onUpcTemplateLoadChangeSub = new Subscription();
  onSelectedTemplateUPCSectionsChangeSub = new Subscription();
  onSelectTemplateIDChangeSub = new Subscription();
  componentForm: FormGroup;
  content: any;
  heading: any = '';
  upcTemplateList = [];
  upcTemplateListOpt = [];
  upcSectionList = [];
  upcSectionOptionList = [];

  constructor(public  mdbModalRef: MDBModalRef,
              private applicationFormTopicAddEditService: ApplicationFormTopicAddEditService,
              private formBuilder: FormBuilder,
              private masterDataService: MasterDataService,
              private alertService: AlertService) {
  }

  ngOnInit() {

    this.componentForm = this.createForm();

    this.onUpcTemplateLoadChangeSub = this.applicationFormTopicAddEditService.onUpcTemplateList
      .subscribe((data: any) => {
        this.upcTemplateList = data;
        _.forEach(this.upcTemplateList, template => {
          this.upcTemplateListOpt.push({
            value: template.upcTemplateID,
            label: template.templateName
          })
        })
      });

    this.onSelectTemplateIDChangeSub = this.componentForm.controls.upcTemplateID.valueChanges.subscribe((templateID: any) => {
      if (templateID) {
        this.applicationFormTopicAddEditService.getActiveApprovedUpcSectionListByTemplateID({upcTemplateID: templateID});
      }
    });

    this.onSelectedTemplateUPCSectionsChangeSub = this.applicationFormTopicAddEditService.onSelectedTemplateUPCSections.subscribe((res: any) => {
      this.upcSectionList = res;
      this.upcSectionOptionList = [];
      _.forEach(this.upcSectionList, upcSection => {

        this.upcSectionOptionList.push({
          value: upcSection.upcSectionID,
          label: upcSection.upcSectionName
        })
      })
    })
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.onUpcTemplateLoadChangeSub.unsubscribe();
    this.onSelectedTemplateUPCSectionsChangeSub.unsubscribe();
    this.onSelectTemplateIDChangeSub.unsubscribe();

  }

  linkTopic($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let upcTemplateID = this.componentForm.getRawValue().upcTemplateID;
    let upcSectionID = this.componentForm.getRawValue().uspSectionID;

    let upcTemplate = _.find(this.upcTemplateList, {upcTemplateID: upcTemplateID});
    let upcSection = _.find(this.upcSectionList, {upcSectionID: upcSectionID});

    let addedSection = _.find(this.content.updateDTO.afTopicUpcSectionDTOList, {
      upcTemplateID: upcTemplateID,
      upcSectionID: upcSectionID,
      status: Constants.statusConst.ACT
    });

    if (_.isEmpty(addedSection)) {
      let obj = Object.assign({}, {
        topicID: this.content.updateDTO.topicID,
        topic: this.content.updateDTO.topic,
        upcTemplateID: upcTemplateID,
        upcSectionID: upcSectionID,
        upcSectionName: upcSection.upcSectionName,
        upcTemplateName: upcTemplate.templateName,
        status: Constants.statusConst.ACT
      });
      this.action.next(obj);
      this.mdbModalRef.hide();
    } else {
      this.alertService.showToaster("Already Added UPC Section", SETTINGS.TOASTER_MESSAGES.warning);
    }

  }

  createForm() {
    return this.formBuilder.group({
      upcTemplateID: ['', Validators.required],
      uspSectionID: ['', Validators.required]
    })
  }

}
