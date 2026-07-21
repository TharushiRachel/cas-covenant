import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-cas-print-button',
  templateUrl: './cas-print-button.component.html',
  styleUrls: ['./cas-print-button.component.scss']
})
export class CasPrintButtonComponent implements OnInit {

  @Input() styleSheetFileUrl: any;
  @Input() useExistingCss = true;
  @Input() printSectionId: any;
  @Input() actionMessage: any;
  @Input() PDFPaperTitle: any;
  @Input() toolTipMessage: any;

  constructor() {
  }

  ngOnInit() {
  }

}
