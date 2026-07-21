import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {CurrencyService} from "../../../../../../../../core/service/common/currency.service";

@Component({
  selector: 'app-bcc-justification',
  templateUrl: './bcc-justification.component.html',
  styleUrls: ['./bcc-justification.component.scss']
})
export class BccJustificationComponent implements OnInit {

  @Input() justificationGroup: FormGroup;

  constructor(public currencyService: CurrencyService) {
  }

  ngOnInit() {
  }

}
