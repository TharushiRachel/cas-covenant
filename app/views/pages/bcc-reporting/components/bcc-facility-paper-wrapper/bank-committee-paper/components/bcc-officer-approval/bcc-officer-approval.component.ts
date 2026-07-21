import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-bcc-officer-approval',
  templateUrl: './bcc-officer-approval.component.html',
  styleUrls: ['./bcc-officer-approval.component.scss']
})
export class BccOfficerApprovalComponent implements OnInit {

  @Input() officerApprovalGroup: FormGroup;

  constructor() {
  }

  ngOnInit() {
  }

}
