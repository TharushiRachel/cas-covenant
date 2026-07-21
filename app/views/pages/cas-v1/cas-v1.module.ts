import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { CasV1RoutingModule } from "./cas-v1-routing.module";
import { V1DashboardComponent } from "./components/v1-dashboard/v1-dashboard.component";
import { SharedModule } from "src/app/shared/shared.module";
import { MDBBootstrapModule, MDBModalRef } from "ng-uikit-pro-standard";
import { FacilityPaperTabsComponent } from "./components/facility-paper-tabs/facility-paper-tabs.component";
import { FacilityWrapperV1Component } from "./components/facility-wrapper-v1/facility-wrapper-v1.component";
import { SecurityWrapperV1Component } from "./components/security-wrapper-v1/security-wrapper-v1.component";
import { UpcCommentsWrapperV1Component } from "./components/upc-comments-wrapper-v1/upc-comments-wrapper-v1.component";
import { FullPaperWrapperV1Component } from "./components/full-paper-wrapper-v1/full-paper-wrapper-v1.component";
import { CustomerDetailsComponent } from "./components/customer-details/customer-details.component";
import { CommentsDetailsComponent } from "./components/comments-details/comments-details.component";
import { AttachmentDetailModalComponent } from "./components/attachment-detail-modal/attachment-detail-modal.component";
import { ViewPaperModalComponent } from "./components/view-paper-modal/view-paper-modal.component";

@NgModule({
  declarations: [
    V1DashboardComponent,
    CustomerDetailsComponent,
    FacilityPaperTabsComponent,
    FacilityWrapperV1Component,
    SecurityWrapperV1Component,
    UpcCommentsWrapperV1Component,
    FullPaperWrapperV1Component,
    CommentsDetailsComponent,
    AttachmentDetailModalComponent,
    ViewPaperModalComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    CasV1RoutingModule,
    MDBBootstrapModule.forRoot(),
  ],
  entryComponents: [AttachmentDetailModalComponent, ViewPaperModalComponent],
  providers: [MDBModalRef],
})
export class CasV1Module {}
