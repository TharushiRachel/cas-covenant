import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {BccFacilityPapersComponent} from "./components/bcc-facility-papers/bcc-facility-papers.component";
import {BccFacilityPaperWrapperComponent} from "./components/bcc-facility-paper-wrapper/bcc-facility-paper-wrapper.component";
import {BccFacilityPaperService} from "./services/bcc-facility-paper.service";
import {BccReportingService} from "./services/bcc-reporting.service";

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: BccFacilityPaperService
    },
    component: BccFacilityPapersComponent
  },
  {
    path: 'bcc-facility-paper',
    resolve: {
      data: BccReportingService
    },
    component: BccFacilityPaperWrapperComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BccReportingRoutingModule {
}
