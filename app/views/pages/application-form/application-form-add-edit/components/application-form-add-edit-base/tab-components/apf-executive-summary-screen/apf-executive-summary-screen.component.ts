import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../../../../core/setting/constants";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import {ApfAddTopicsComponent} from "../../support-components/apf-add-topics/apf-add-topics.component";

@Component({
  selector: 'app-apf-executive-summary-screen',
  templateUrl: './apf-executive-summary-screen.component.html',
  styleUrls: ['./apf-executive-summary-screen.component.scss']
})
export class ApfExecutiveSummaryScreenComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  onApplicationFormChangeSub = new Subscription();
  applicationForm: any = {};
  applicationFormTopicPageConst = Constants.applicationFormTopicPageConst;
  topics: any = [];
  lpsTopics: any = [];

  constructor(private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,) {
  }

  ngOnInit() {
    this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormTopicsChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
        this.lpsTopics = [];
        data.afTopicDataDTOList.forEach(e => {
          if (e.page == this.applicationFormTopicPageConst.EXECUTIVE_SUMMARY) {
            this.lpsTopics.push(e);
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
