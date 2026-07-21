import {NgModule} from '@angular/core';

import {SharedModule} from "../../../shared/shared.module";
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import { CommitteePaperDashboardComponent } from './components/committee-paper-dashboard/committee-paper-dashboard.component';
import { CommitteePaperCountBoxComponent } from './components/committee-paper-dashboard/committee-paper-count-box/committee-paper-count-box.component';
import {CommitteePaperRoutingModule} from './committee-paper-routing.module';
import {CommitteePaperService} from "./services/committee-paper.service";
import { BCCPaperCountBoxComponent } from './components/committee-paper-dashboard/bcc-paper-count-box/bcc-paper-count-box.component';

@NgModule({
  declarations: [
    //  ApplicationFormComponent,
      CommitteePaperDashboardComponent,
      CommitteePaperCountBoxComponent,
      BCCPaperCountBoxComponent],
  imports: [
    SharedModule,
    MDBBootstrapModulesPro.forRoot(),
    CommitteePaperRoutingModule
  ],
  providers: [
    CommitteePaperService
  ],
})
export class CommitteePaperModule {
}


