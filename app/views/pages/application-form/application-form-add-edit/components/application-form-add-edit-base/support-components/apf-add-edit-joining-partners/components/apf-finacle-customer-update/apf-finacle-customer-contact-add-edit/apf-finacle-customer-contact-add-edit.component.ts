import {Component, Input, OnInit} from '@angular/core';
import {FormArray} from "@angular/forms";

@Component({
  selector: 'app-apf-finacle-customer-contact-add-edit',
  templateUrl: './apf-finacle-customer-contact-add-edit.component.html',
  styleUrls: ['./apf-finacle-customer-contact-add-edit.component.scss']
})
export class ApfFinacleCustomerContactAddEditComponent implements OnInit {

  @Input() finacleCustomerTelephoneForm: FormArray;

  constructor() {
  }

  ngOnInit() {
  }

}
