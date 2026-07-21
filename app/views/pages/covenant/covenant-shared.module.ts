import { NgModule } from "@angular/core";
import { CurrencyPipe, DatePipe } from "@angular/common";
import {
  MDBBootstrapModule,
  MDBModalRef,
  ModalModule,
} from "ng-uikit-pro-standard";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatExpansionModule,
  MatIconModule,
  MatSelectModule,
} from "@angular/material";

import { SharedModule } from "src/app/shared/shared.module";

import { CustomerCovenantComponent } from "./components/customer-covenant/customer-covenant.component";
import { CustomerCovenantListComponent } from "./components/customer-covenant/add-customer-covenant/customer-covenant-list.component";
import { AddCovenantCommentComponent } from "./components/customer-covenant/add-covenant-comment/add-covenant-comment.component";
import { ViewCovenantAccountDetailsComponent } from "./components/customer-covenant/view-covenant-account-details/view-covenant-account-details.component";
import { AccountCovenantComponent } from "./components/add-account-covenant/account-covenant.component";
import { FacilitySelectModalComponent } from "./components/facility-select-modal/facility-select-modal.component";

import { CoveringApprovalService } from "../covering-approval/services/covering-approval.service";
import { UrlEncodeService } from "src/app/core/service/application/url-encode.service";

const COVENANT_COMPONENTS = [
  CustomerCovenantComponent,
  CustomerCovenantListComponent,
  AddCovenantCommentComponent,
  ViewCovenantAccountDetailsComponent,
  AccountCovenantComponent,
  FacilitySelectModalComponent,
];

@NgModule({
  declarations: [...COVENANT_COMPONENTS],
  imports: [
    SharedModule,
    ModalModule.forRoot(),
    MDBBootstrapModule.forRoot(),
    MatExpansionModule,
    MatSelectModule,
    MatCheckboxModule,
    MatIconModule,
    MatButtonModule,
  ],
  entryComponents: [
    CustomerCovenantListComponent,
    AddCovenantCommentComponent,
    ViewCovenantAccountDetailsComponent,
    AccountCovenantComponent,
    FacilitySelectModalComponent,
  ],
  providers: [
    CoveringApprovalService,
    UrlEncodeService,
    MDBModalRef,
    CurrencyPipe,
    DatePipe,
  ],
  exports: [...COVENANT_COMPONENTS],
})
export class CovenantSharedModule {}
