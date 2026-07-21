import { Component, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { ACAEPaperService } from '../../../services/acae-paper.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-acae-paper-range-inquiry-details',
  templateUrl: './acae-paper-range-inquiry-details.component.html',
  styleUrls: ['./acae-paper-range-inquiry-details.component.scss']
})
export class AcaePaperRangeInquiryDetailsComponent implements OnInit {

  rangeInquiryDetails: [];
  content: any;
  isDataAvailable: boolean = false;
  accountNo:Number = 0;
  date :string  = "";
  isRangeInquiryLoading:boolean= false;
  constructor(
    public acaeRangeInquiryDetailsmodalRef: MDBModalRef,
    private alertService: AlertService,
    private acaePaperService: ACAEPaperService,
    private currencyPipe: CurrencyPipe,
  ) { }

  ngOnInit() {
    this.loadACAERangeInquiryDetail();
    this.accountNo = this.content.initialState.paperInfo.accountNumber;
    this.date = this.content.initialState.toDate;
  }

  loadACAERangeInquiryDetail() {
    let dataRQ = {
      "ReqId": "df.gnsdfl;1516077882977",
      "accno": this.content.initialState.paperInfo.accountNumber,
      "fromDate": this.content.initialState.toDate,
      "toDate": this.content.initialState.toDate,
      "lowerAmt": "",
      "higherAmt": "",
      "beginChq": "",
      "endChq": "",
      "numRec": "",
      "partTranType": "",
      "ccyCode": "",
      "solId": "",
      "sortOrder": "A"
    }
    this.isRangeInquiryLoading=true;
    this.acaePaperService.getACAERangeInquiryService(dataRQ).subscribe((response: any) => {
      this.isRangeInquiryLoading=false;
      if (response["tranDetails"]) {
        let dataList:any = [];
        response["tranDetails"].map((r: any, i: number) => {
          const [datePart, timePart] = r.postedDate.split(' ');
          let data = {
            acctBalance: r.acctBalance,
            drcrInd: r.drcrInd,
            expMessage: r.expMessage,
            instNumb: r.instNumb,
            partTranSrl: r.partTranSrl,
            postedDate: datePart,
            postedTime: timePart,
            tranAmount: r.tranAmount,
            tranCrncy: r.tranCrncy,
            tranDate: r.tranDate,
            tranId: r.tranId,
            tranParticular: r.tranParticular,
            tranRemarks: r.tranRemarks,
            tranSol: r.tranSol,
            tranSubType: r.tranSubType,
            tranType: r.tranType,
            valueDate: r.valueDate,
          }
          dataList.push(data);
        });
       
        this.rangeInquiryDetails = dataList
        this.isDataAvailable = true;
      } else {
        this.rangeInquiryDetails = [];
        this.isDataAvailable = false;
      }
    }, (error) => {
      this.isRangeInquiryLoading=false;
      this.isDataAvailable = false;
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error);
    });
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  onCloseModel() {
    this.acaeRangeInquiryDetailsmodalRef.hide();
  }
}