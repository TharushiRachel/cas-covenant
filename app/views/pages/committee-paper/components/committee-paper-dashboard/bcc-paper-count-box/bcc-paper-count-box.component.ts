import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";
import {PrivilegeService} from "../../../../../../core/service/authentication/privilege.service";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";

@Component({
  selector: 'bcc-paper-count-box',
  templateUrl: './bcc-paper-count-box.component.html',
  styleUrls: ['./bcc-paper-count-box.component.scss']
})
export class BCCPaperCountBoxComponent implements OnInit {

  @Input('count') count: number = 0;
  @Input('status') status = '';
  @Input('selectedStatus') selectedStatus;
  @Output('onClick') onClick = new EventEmitter();

  committeePaperDashboardStatus = Constants.committeePaperDashboardStatus;
  bccPaperDashboardStatus = Constants.bccPaperDashboardStatus;
  hasPrivilegeToViewBCCPapers = false
  masterDataPrivilege = SETTINGS.PRIVILEGES;

 constructor(
       private privilegeService: PrivilegeService,
   ) {
   }

  ngOnInit() {
       this.hasPrivilegeToViewBCCPapers = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_VIEW_BCC_PAPER);
  }


  loadPageData() {
    this.onClick.emit(this.status);
  }

}

