import { Component, OnInit } from '@angular/core';
import { MDBModalRef } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-risk-opinion-history-viewer',
  templateUrl: './risk-opinion-history-viewer.component.html',
  styleUrls: ['./risk-opinion-history-viewer.component.scss']
})
export class RiskOpinionHistoryViewerComponent implements OnInit {

  riskComment: string;
  createdUserName: string;
  createdDateStr: Date;
  modifiedUserName: string;
  modifiedDateStr: Date;


  constructor(public mdbModalRef: MDBModalRef) { }

  ngOnInit() {
  }

  getContent(data) {
    return `<pre class="credit-risk-comment-pre-tag">${data || '-'}</pre>`;
  }

  printCreditRiskComment() {

    
      const printWindow = window.open('', '_blank');
      printWindow.document.open();
      printWindow.document.write(`
        <html>
        <head>
          <title>Print</title>
        </head>
        <body>
          ${this.riskComment}
        </body>
        </html>
      `);
      printWindow.document.close();
      printWindow.print();
      printWindow.close();
    
    


  }

}
