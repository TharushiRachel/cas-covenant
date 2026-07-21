import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Constants} from "../../../../../../../core/setting/constants";
import {ApplicationFormAddEditService} from "../../../../application-form-add-edit/services/application-form-add-edit.service";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {Subscription} from "rxjs";
import {AlertService} from "../../../../../../../core/service/common/alert.service";
import {MDBModalRef} from "ng-uikit-pro-standard";

@Component({
  selector: 'app-apf-facility-document-preview',
  templateUrl: './apf-facility-document-preview.component.html',
  styleUrls: ['./apf-facility-document-preview.component.scss']
})
export class ApfFacilityDocumentPreviewComponent implements OnInit, OnDestroy {

  content: any = {};
  heading: string;
  documentList = [];
  yesNo = Constants.yesNo;
  onDownLoadLinkChangeSub = new Subscription();
  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;

  constructor(private applicationFormAddEditService: ApplicationFormAddEditService,
              public  mdbModalRef: MDBModalRef,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.documentList = this.content.facilityData.afFacilityDocumentDTOList;
    this.onDownLoadLinkChangeSub = this.applicationFormAddEditService.onDownloadLinkChangeAPSupportDoc
      .subscribe(data => {
        let downloadLink = this.downloadLink.nativeElement;
        downloadLink.href = window.URL.createObjectURL(data.data);
        downloadLink.download = data.fileName;
        downloadLink.click();
        this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
      });
  }

  ngOnDestroy(): void {
    this.onDownLoadLinkChangeSub.unsubscribe();
  }

  onDownLoadDoc(item) {
    if (item.docStorageDTO.docStorageID != null) {
      this.applicationFormAddEditService.downloadAFSupportDocument(item.docStorageDTO).then((data: any) =>{
        let downloadLink = this.downloadLink.nativeElement;
         downloadLink.href = window.URL.createObjectURL(data);
         downloadLink.download = item.docStorageDTO.fileName;
         downloadLink.click();
         this.alertService.showToaster("Document downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
     });
    }
  }

}
