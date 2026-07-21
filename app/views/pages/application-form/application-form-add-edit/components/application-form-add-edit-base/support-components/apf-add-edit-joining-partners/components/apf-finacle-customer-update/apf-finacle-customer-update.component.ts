import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-apf-finacle-customer-update',
  templateUrl: './apf-finacle-customer-update.component.html',
  styleUrls: ['./apf-finacle-customer-update.component.scss']
})
export class ApfFinacleCustomerUpdateComponent implements OnInit {
  @Input() finacleCustomerUpdateForm: FormGroup;

  constructor() {
  }

  ngOnInit() {
  }

}
