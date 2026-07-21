import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-bcc-facility-common-security',
  templateUrl: './bcc-facility-common-security.component.html',
  styleUrls: ['./bcc-facility-common-security.component.scss']
})
export class BccFacilityCommonSecurityComponent implements OnInit {

  @Input() commonSecurityText: FormGroup;

  constructor() {
  }

  ngOnInit() {
  }

}
