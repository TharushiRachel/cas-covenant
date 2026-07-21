import {Component, OnDestroy, OnInit} from '@angular/core';
import {ApfAddTopicsComponent} from "../../support-components/apf-add-topics/apf-add-topics.component";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../../../../core/setting/constants";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import * as _ from "lodash";

@Component({
  selector: 'app-apf-repayment-screen',
  templateUrl: './apf-repayment-screen.component.html',
  styleUrls: ['./apf-repayment-screen.component.scss']
})
export class ApfRepaymentScreenComponent implements OnInit, OnDestroy {

  modalRef: MDBModalRef;
  onApplicationFormChangeSub = new Subscription();
  applicationForm: any = {};
  topics: any = [];
  repaymentTopics: any = [];
  applicationFormTopicPageConst = Constants.applicationFormTopicPageConst;

  constructor(private mdbModalService: MDBModalService,
              private applicationFormAddEditService: ApplicationFormAddEditService,) {
  }

  ngOnInit() {
    this.onApplicationFormChangeSub = this.applicationFormAddEditService.onApplicationFormTopicsChange.subscribe((data: any) => {
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
      }
      this.repaymentTopics = [];
      data.afTopicDataDTOList.forEach(e => {
        if (e.page == this.applicationFormTopicPageConst.REPAYMENT) {
          this.repaymentTopics.push(e);
        }
      });
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
