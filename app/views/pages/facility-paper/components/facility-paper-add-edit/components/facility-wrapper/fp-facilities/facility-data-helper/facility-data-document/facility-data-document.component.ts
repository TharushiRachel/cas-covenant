import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {SETTINGS} from "../../../../../../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../../../../../../core/setting/constants";
import {FacilityDataDocUploadComponent} from "../facility-data-doc-upload/facility-data-doc-upload.component";
import * as _ from "lodash";
import {FacilityPaperAddEditService} from "../../../../../../../services/facility-paper-add-edit.service";
import {AlertService} from "../../../../../../../../../../core/service/common/alert.service";
import {ConfirmationDialogComponent} from "../../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {ApplicationService} from "../../../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-facility-data-document',
  templateUrl: './facility-data-document.component.html',
  styleUrls: ['./facility-data-document.component.scss']
})
export class FacilityDataDocumentComponent implements OnInit, OnDestroy {

  heading: string;
  content: any = {};

  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;

  facilityData: any = {};
  facilityPaper: any = {};

  onDownLoadLinkChangeSub = new Subscription();

  count: number = 0;
  resizedfpDocList: any[] = [];
  fpDocument = "fpDocument";

  modalRef: MDBModalRef;
  yesNo = Constants.yesNo;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  tableColumnsForFacilityDocument = [];

  constructor(
    public  mdbModalRef: MDBModalRef,
    private mdbModalService: MDBModalService,
    private applicationService: ApplicationService,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private alertService: AlertService,
  ) {
  }

  ngOnInit() {
    this.facilityData = this.content.facilityData;
    this.facilityPaper = this.content.facilityPaper;

    if (this.content.isPreviewMode) {
      this.tableColumnsForFacilityDocument = ['Supporting Document', 'Mandatory', 'Remark'];
    } else {
      this.tableColumnsForFacilityDocument = ['Supporting Document', 'Mandatory', 'Remark', 'Actions'];
    }

    if (this.facilityData.facilityDocumentList.length > 3) {
      for (this.count = 0; this.count < 3; this.count++) {
        this.resizedfpDocList.push(this.facilityData.facilityDocumentList[this.count])
      }
    } else {
      for (this.count = 0; this.count < this.facilityData.facilityDocumentList.length; this.count++) {
        this.resizedfpDocList.push(this.facilityData.facilityDocumentList[this.count])
      }
    }

    this.onDownLoadLinkChangeSub = this.facilityPaperAddEditService.onDownloadLinkChage
      .subscribe((data: any) => {
        if (this.downloadLink) {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data.data);
          downloadLink.download = data.fileName;
          downloadLink.click();
          this.alertService.showToaster("FpDocument downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
        }
      })
  }

  openModalFacilityDocumentUpload(facilityData, documentItem?) {
    const initialState = {
      list: [
        {"tag": 'Count', "value": facilityData}
      ]
    };

    this.modalRef = this.mdbModalService.show(FacilityDataDocUploadComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p modal-margin-center ',
      containerClass: 'right',
      animated: true,
      data: {
        heading: "Add/ Edit Facility",
        content: {
          facilityData: facilityData,
          facilityPaperID: this.facilityPaper.facilityPaperID,
          documentItem: documentItem
        },
      }
    });
    this.mdbModalRef.hide();
  }

  onDownLoadDoc(item) {
    if (item.docStorageDTO.docStorageID != null) {
      this.facilityPaperAddEditService.downloadFpDocument(item.docStorageDTO);
    }
  }

  onRemoveFacilityDocument(documentItem) {
    if (!_.isEmpty(documentItem)) {
      let data = Object.assign({}, {
          facilityDocumentID: documentItem.facilityDocumentID,
          facilityPaperID: this.facilityPaper.facilityPaperID,
          facilityID: documentItem.facilityID
        }
      );

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
          heading: "Confirm Remove Document",
          message: "Do you want to remove this document ?",
        }
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.facilityPaperAddEditService.deactivateFacilityDocuments(data);
        }
      });
      this.mdbModalRef.hide();
    }
  }

  onLoadResizedList(listFromEvent) {
    if (listFromEvent.outputArrayType === this.fpDocument) {
      this.resizedfpDocList = [];
      _.forEach(listFromEvent.outputArray, item => {
        this.resizedfpDocList.push(item)
      })

    }
  }

  ngOnDestroy(): void {
    this.onDownLoadLinkChangeSub.unsubscribe();
  }

  isUploadedDiv(data) {
    return this.applicationService.getLoggedInUserDivCode() == data.uploadedDivCode;
  }
}
