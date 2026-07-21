import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {SETTINGS} from "../../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../../core/service/common/alert.service";

@Component({
  selector: 'app-apf-lps-screen',
  templateUrl: './apf-lps-screen.component.html',
  styleUrls: ['./apf-lps-screen.component.scss']
})
export class ApfLpsScreenComponent implements OnInit, OnDestroy {

  onApplicationFormChangeSub = new Subscription();
  onDownLoadLinkChangeSub = new Subscription();
  applicationForm: any = {};
  @ViewChild('lpsDownloadLink', {static: false}) private downloadLink: ElementRef;

  constructor(private applicationFormAddEditService: ApplicationFormAddEditService,
              private alertService: AlertService,) {
  }

  ngOnInit() {
    this.applicationFormAddEditService.getApplicationFormByID().then((data: any) => {
      this.applicationForm = data;
      if (!_.isEmpty(data)) {
        this.applicationForm = data;
      }
    });

    this.onDownLoadLinkChangeSub = this.applicationFormAddEditService.onDownloadLinkChangeAPSupportDoc
      .subscribe(data => {
        this.downloadFile(data);
      });

    this.onDownLoadLinkChangeSub = this.applicationFormAddEditService.onDownloadLinkChangeAFCribAttachments
      .subscribe(data => {
        this.downloadFile(data);
      });
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
    this.onDownLoadLinkChangeSub.unsubscribe();
  }

  downloadFile(data) {
    let downloadLink = this.downloadLink.nativeElement;
    downloadLink.href = window.URL.createObjectURL(data.data);
    downloadLink.download = data.fileName;
    downloadLink.click();
    this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
  }

}
