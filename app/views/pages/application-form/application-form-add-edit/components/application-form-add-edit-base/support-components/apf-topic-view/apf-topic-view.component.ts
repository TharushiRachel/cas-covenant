import {Component, Input, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import {ConfirmationDialogComponent} from "../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {ApfAddEditTopicDataComponent} from "../apf-add-edit-topic-data/apf-add-edit-topic-data.component";

@Component({
  selector: 'app-apf-topic-view',
  templateUrl: './apf-topic-view.component.html',
  styleUrls: ['./apf-topic-view.component.scss']
})
export class ApfTopicViewComponent implements OnInit {
  @Input('selectedTopic') selectedTopic: any = {};
  modalRef: MDBModalRef;


  constructor(private applicationFormAddEditService: ApplicationFormAddEditService, private mdbModalService: MDBModalService,) {
  }

  ngOnInit() {
  }

  update(data) {
  }

  openModalTemplateData($event, data) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfAddEditTopicDataComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-lg',
      containerClass: '',
      animated: false,
      data: {
        heading: "",
        content: {
          dataToEdit: data.topicData,
          header: data.topic
        },
      }
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        data.topicData = result;
        this.applicationFormAddEditService.saveOrUpdateApplicationFormTopics(AppUtils.trim(data));
      }
    });
  }


  remove(item) {
    if (!_.isEmpty(item)) {
      let data = Object.assign({}, {...item},
        {status: 'INA'});

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
          heading: "Confirm Remove Topic",
          message: `Do you want to remove ${item.topic} ?`,
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.applicationFormAddEditService.deactivateApplicationFormTopic(AppUtils.trim(data));
        }
      });
    }
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }


}
