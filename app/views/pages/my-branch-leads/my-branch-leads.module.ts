import {NgModule} from '@angular/core';

import {MyBranchLeadsRoutingModule} from './my-branch-leads-routing.module';
import {MyBranchLeadsComponent} from './components/my-branch-leads/my-branch-leads.component';
import {LeadsService} from "../lead/services/leads.service";
import {SharedModule} from "../../../shared/shared.module";


@NgModule({
  declarations: [MyBranchLeadsComponent],
  imports: [
    SharedModule,
    MyBranchLeadsRoutingModule
  ],
  providers: [LeadsService]
})
export class MyBranchLeadsModule {
}
