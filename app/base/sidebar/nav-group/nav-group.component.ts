import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import {PrivilegeService} from "../../../core/service/authentication/privilege.service";

@Component({
  selector: 'app-nav-group',
  templateUrl: './nav-group.component.html',
  styleUrls: ['./nav-group.component.scss'],
  changeDetection : ChangeDetectionStrategy.OnPush
})
export class NavGroupComponent implements OnInit {

  @Input('item') item: any = {};

  constructor(private privilegeService : PrivilegeService) {
  }

  ngOnInit() {
  }

  hasPrivileges(item : any) {
    if(!item.privileges) {
      return true;
    }

    return this.privilegeService.hasAnyPrivilege(item.privileges.split(','));
  }

}
