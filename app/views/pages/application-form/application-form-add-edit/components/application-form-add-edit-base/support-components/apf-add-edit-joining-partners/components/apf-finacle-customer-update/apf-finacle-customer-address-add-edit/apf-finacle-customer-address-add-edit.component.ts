import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-finacle-customer-address-add-edit',
  templateUrl: './apf-finacle-customer-address-add-edit.component.html',
  styleUrls: ['./apf-finacle-customer-address-add-edit.component.scss']
})
export class ApfFinacleCustomerAddressAddEditComponent implements OnInit {

  @Input() finacleCustomerAddressForm: FormGroup;
  addressType = Constants.addressType;

  constructor() {
  }

  ngOnInit() {
  }

}
