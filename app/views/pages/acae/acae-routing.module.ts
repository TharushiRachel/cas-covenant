import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ACAEDetailsComponent } from "./components/acae-details/acae-details.component";
import { ACAEService } from "./services/acae-base.service";
import { ACAEDetailsSearchComponent } from "./components/acae-details-search/acae-details-search.component";
import { ACAESearchService } from "./services/acae-search.service";
import { ACAEDetailsDateRangeInquiryComponent } from "./components/acae-details-date-range-inquiry/acae-details-date-range-inquiry.component";
import { ACAEDateRangeInquiryService } from "./services/acae-date-range-inquiry.service";
import { ACAEDetailsStatusInquiryComponent } from "./components/acae-details-status-inquiry/acae-details-status-inquiry.component";
import { ACAEStatusInquiryService } from "./services/acae-status-inquiry.service";
import { ACAEDetailsMonitorComponent } from "./components/acae-details-monitor/acae-details-monitor.component";
import { ACAEMonitorService } from "./services/acae-monitor.service";
import { ACAEPaperDetailsComponent } from "./components/acae-paper-details/acae-paper-details.component";
import { ACAEPaperService } from "./services/acae-paper.service";
import { ACAEEditStatusModelService } from "./services/acae-edit-status-model.service";
import { ACAEPaperTransferModelComponent } from "./components/acae-paper-details/acae-paper-transfer-model/acae-paper-transfer-model.component";
import { ACAEDetailsTransferSearchService } from "./services/acae-details-transfer-search.service";
import { AcaeDetailsTransferSearchComponent } from "./components/acae-details-transfer-search/acae-details-transfer-search.component";

const routes: Routes = [
  {
    path: "",
    resolve: {
      data: ACAEService,
    },
    component: ACAEDetailsComponent,
  },
  {
    path: "dashboard",
    resolve: {
      data: ACAEService,
    },
    component: ACAEDetailsComponent,
  },
  {
    path: "inquiry-by-date-range",
    resolve: {
      data: ACAEDateRangeInquiryService,
    },
    component: ACAEDetailsDateRangeInquiryComponent,
  },
  {
    path: "monitor",
    resolve: {
      data: ACAEMonitorService,
    },
    component: ACAEDetailsMonitorComponent,
  },
  {
    path: "status-inquiry",
    resolve: {
      data: ACAEStatusInquiryService,
    },
    component: ACAEDetailsStatusInquiryComponent,
  },
  {
    path: "search",
    resolve: {
      data: ACAESearchService,
    },
    component: ACAEDetailsSearchComponent,
  },
  {
    path: "transfer",
    resolve: {
      data: ACAEDetailsTransferSearchService,
    },
    component: AcaeDetailsTransferSearchComponent,
  },
  {
    path: "edit",
    resolve: {
      data: ACAEPaperService,
    },
    component: ACAEPaperDetailsComponent,

  },
  {
    path: "edit-status-model",
    resolve: {
      data: ACAEEditStatusModelService,
    },
    component: ACAEPaperTransferModelComponent,

  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ACAERoutingModule {}
