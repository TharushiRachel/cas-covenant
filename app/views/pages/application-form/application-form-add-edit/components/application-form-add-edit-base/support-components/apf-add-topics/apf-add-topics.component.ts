import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {Constants} from "../../../../../../../../core/setting/constants";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {AfTopicDto} from "../../../../../../application-topic/dto/af-topic-dto";
import {Subscription} from "rxjs";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";

@Component({
  selector: 'app-apf-add-topics',
  templateUrl: './apf-add-topics.component.html',
  styleUrls: ['./apf-add-topics.component.scss']
})
export class ApfAddTopicsComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  onFormValueChangeSub = new Subscription();
  applicationFormTopics: any = [];
  selectedTopic: AfTopicDto = new AfTopicDto({});
  formErrors: any = {};
  heading: string;
  content: any;


  constructor(public  mdbModalRef: MDBModalRef,
              private formBuilder: FormBuilder,
              private cacheService: CacheService,
              private applicationFormCreateService: ApplicationFormAddEditService,
              private alertService: AlertService,) {
  }

  ngOnInit() {

    this.formErrors = {
      topic: {}
    };

    this.applicationFormTopics = _.filter(this.cacheService.getData(Constants.masterDataKey.CAS_APPLICATION_FORM_TOPICS), {page: this.content.pageCategory});
    this.componentForm = this.createForm();

    this.onFormValueChangeSub = this.componentForm.controls.topic.valueChanges.subscribe((topic: any) => {
      if (topic) {
        if (this.content.applicationForm.afTopicDataDTOList.length) {
          this.content.applicationForm.afTopicDataDTOList.forEach(e => {
            if (e.topic === topic) {
              this.alertService.showToaster("Already Added Topic", SETTINGS.TOASTER_MESSAGES.warning);
              this.componentForm.controls.topic.setValue('', {onlySelf: true, emitEvent: false})
            } else {
              let topicData = AppUtils.getApplicationFormTopicByTopic(this.applicationFormTopics, topic);
              this.selectedTopic = topicData ? topicData : new AfTopicDto({});
            }
          });
        } else {
          let topicData = AppUtils.getApplicationFormTopicByTopic(this.applicationFormTopics, topic);
          this.selectedTopic = topicData ? topicData : new AfTopicDto({});
        }
      } else {
        this.selectedTopic = new AfTopicDto({});
      }
    });
  }

  createForm() {
    return this.formBuilder.group({
      topic: [this.selectedTopic.topic, [Validators.required]],
      remark: [''],
    });
  }

  addTopic() {
    let saveObj = Object.assign({}, this.content.applicationForm, this.selectedTopic, {
      remark: this.componentForm.controls.remark.value,
    });

    this.applicationFormCreateService.saveOrUpdateApplicationFormTopics(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();

  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
  }

}
