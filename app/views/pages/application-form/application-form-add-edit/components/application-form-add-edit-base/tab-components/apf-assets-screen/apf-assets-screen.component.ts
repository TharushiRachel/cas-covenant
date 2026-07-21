import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {ApfAddTopicsComponent} from "../../support-components/apf-add-topics/apf-add-topics.component";
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {Constants} from "../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-assets-screen',
  templateUrl: './apf-assets-screen.component.html',
  styleUrls: ['./apf-assets-screen.component.scss']
})
export class ApfAssetsScreenComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  onApplicationFormChangeSub = new Subscription();
  applicationForm: any = {};
  applicationFormTopicPageConst = Constants.applicationFormTopicPageConst;
  topics: any = [];
  assetsTopics: any = [];

  constructor(private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,) {
  }

  ngOnInit() {
    this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormTopicsChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
        this.assetsTopics = [];
        data.afTopicDataDTOList.forEach(e => {
          if (e.page == this.applicationFormTopicPageConst.ASSETS) {
            this.assetsTopics.push(e);
          }
        });
      }
    });

  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

  openModalTopics($event, pageCategory) {

    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(ApfAddTopicsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-70-p modal-dialog-scrollable',
      containerClass: 'right',
      animated: false,
      data: {
        heading: "comming dto",
        content: {
          applicationForm: this.applicationForm,
          pageCategory: pageCategory
        },
      }
    });
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }


}
