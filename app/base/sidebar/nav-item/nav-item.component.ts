import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import {PrivilegeService} from "../../../core/service/authentication/privilege.service";

@Component({
  selector: 'app-nav-item',
  templateUrl: './nav-item.component.html',
  styleUrls: ['./nav-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavItemComponent implements OnInit {

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
