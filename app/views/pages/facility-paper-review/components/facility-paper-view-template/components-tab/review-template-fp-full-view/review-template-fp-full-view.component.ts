import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FacilityPaperReviewTemplateService} from "../../../../services/facility-paper-review-template.service";
import {Subscription} from "rxjs";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {AlertService} from "../../../../../../../core/service/common/alert.service";
import {CasDocumentStorageService} from "../../../../../../../core/service/data/cas-document-storage.service";

@Component({
  selector: 'app-review-template-fp-full-view',
  templateUrl: './review-template-fp-full-view.component.html',
  styleUrls: ['./review-template-fp-full-view.component.scss']
})
export class ReviewTemplateFpFullViewComponent implements OnInit, OnDestroy {

  @Input('facilityPaper') facilityPaper: any = {};
  @Input('primaryCustomer') primaryCustomer: any = {};
  customerList = [];
  upcTemplateList = [];
  fpUpcSectionData: any = [];
  onTreeUpdateChangeSub = new Subscription();
  onDownLoadLinkChangeSub = new Subscription();
  onUpcTemplateLoadChangeSub = new Subscription();
  @ViewChild('downloadLink', {static: false}) private downloadLink: ElementRef;

  onCreditRiskCommentListChange = new Subscription();
  riskCommentList: any[] = [];

  constructor(private facilityPaperReviewTemplateService: FacilityPaperReviewTemplateService,
              private casDocumentStorageService: CasDocumentStorageService,
              private alertService: AlertService) {
  }

  ngOnInit() {

    this.customerList = this.facilityPaper.casCustomerDTOList.sort((x, y) => {
      return (x.isPrimary == y.isPrimary) ? 0 : x.isPrimary ? -1 : 1
    });

    this.onUpcTemplateLoadChangeSub = this.facilityPaperReviewTemplateService.onUpcTemplateListLoad
      .subscribe((data: any) => {
        this.upcTemplateList = data;
      });

    this.onTreeUpdateChangeSub = this.facilityPaperReviewTemplateService.onFpUpcSectionChange
      .subscribe((data: any) => {
        this.fpUpcSectionData = data.fpUpcSectionDataDTOList;
      });

    this.onDownLoadLinkChangeSub = this.casDocumentStorageService.onDocumentStorageChange
      .subscribe((data: any) => {
        if (this.downloadLink) {
          let downloadLink = this.downloadLink.nativeElement;
          downloadLink.href = window.URL.createObjectURL(data.data);
          downloadLink.download = data.fileName;
          downloadLink.click();
          this.alertService.showToaster("FpDocument downloaded successfully", SETTINGS.TOASTER_MESSAGES.success)
        }
      });

      this.onCreditRiskCommentListChange = this.facilityPaperReviewTemplateService.onCreditRiskCommentListChange
            .subscribe((data: any) => {
              if (data) {
                this.riskCommentList = [];
                this.riskCommentList = data.fpCreditRiskCommentList;
              }
            });
  }

  ngOnDestroy(): void {
    this.onUpcTemplateLoadChangeSub.unsubscribe();
    this.onDownLoadLinkChangeSub.unsubscribe();
    this.onTreeUpdateChangeSub.unsubscribe();
  }

}
