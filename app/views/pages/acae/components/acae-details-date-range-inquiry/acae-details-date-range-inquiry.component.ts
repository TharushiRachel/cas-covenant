import { Component, OnInit, Input, OnChanges, OnDestroy, SimpleChanges, } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IMyOptions, MDBModalService } from 'ng-uikit-pro-standard';
import { ACAEDateRangeInquiryService } from '../../services/acae-date-range-inquiry.service';
import { PageSize } from 'src/app/core/dto/page.size';
import { ACAEPaperDetailsComponent } from '../acae-paper-details/acae-paper-details.component';

@Component({
  selector: 'app-acae-details-date-range-inquiry',
  templateUrl: './acae-details-date-range-inquiry.component.html',
  styleUrls: ['./acae-details-date-range-inquiry.component.scss']
})
export class ACAEDetailsDateRangeInquiryComponent implements OnInit, OnChanges, OnDestroy {
  constructor(
    private formBuilder: FormBuilder,
    private acaeDateRangeInquiryService: ACAEDateRangeInquiryService,
    private mdbModalService: MDBModalService,
  ) { }

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy-mm-dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };
  pageSize = new PageSize({ pageSize: 10 });
  rangeForm: FormGroup;
  pageIndex = 0;
  acaeSearchData: any[] = [];
  isAcaeDateRageSearch: boolean;
  fromDate="";
  toDate="";

  ngOnChanges(changes: SimpleChanges): void {
  }

  ngOnDestroy(): void {
  }

  ngOnInit() {
    this.rangeForm = this.createRangeForm();
  }

  createRangeForm() {
    return this.formBuilder.group({
      solId: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
    });
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
  }

  clearSearch() {
    this.rangeForm.reset({
      fromDate: [{}],
    });
    this.rangeForm.reset({
      toDate: [{}],
    });
    this.rangeForm.reset({
      solId: '',
    }, { onlySelf: false, emitEvent: true });
  }

  searchData() {
    let { solId, fromDate, toDate } = this.rangeForm.getRawValue();
    let request = {
      "fromDate": fromDate,
      "solId": solId,
      "toDate": toDate
    }
    this.acaeDateRangeInquiryService.getInquiryByDateRange(request).then((res: any) => {
      this.acaeSearchData = res;
      this.isAcaeDateRageSearch = true;
    });
  }

  openModal(item:any){
    let dataRQ = {
      "accountName": item.customerName,
      "accountNumber": item.accountNumber,
      "receivedDate": item.recievedDate,
      "refNumber": item.refNumber,
    }
    const initialState = {
      recordIndex: item.recordIndex,
      list: [{ tag: "Count", value: item }],
      gridData: dataRQ,
    
      pageSize: this.pageSize,
      acaeSearchData: this.acaeSearchData,
      condition: "inquiry",
    };
  this.mdbModalService.show(ACAEPaperDetailsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      ignoreBackdropClick: true,
      class: "modal-width-90-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "12",
        content: {
          initialState: initialState,
          // acaeDetailEditModelRef: this.acaeDetailEditModelRef
        },
      },
    });
    // this.acaeDetailEditModelRef.content.refreshAction.subscribe((data: any) => {
    //   if (data) {
    //     this.loadPageData(this.acaeSharedService.getACAEDashboardStatus(), this.paginationData, false);
    //     this.loadACAECountData(false);
    //   }
    // })
  }
}
