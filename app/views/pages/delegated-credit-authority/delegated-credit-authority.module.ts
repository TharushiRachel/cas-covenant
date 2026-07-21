import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DelegatedCreditAuthorityRoutingModule } from './delegated-credit-authority-routing.module';
import { DaTableComponent } from './components/da-table/da-table.component';
import { FormsModule } from '@angular/forms';
import { MDBBootstrapModule, ModalModule } from 'ng-uikit-pro-standard';
import { MatCheckboxModule, MatExpansionModule, MatSelectModule } from '@angular/material';
import { DaService } from './services/da.service';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';




@NgModule({


  declarations: [DaTableComponent],

  imports: [
    SharedModule,
    CommonModule,
    DelegatedCreditAuthorityRoutingModule,
    MDBBootstrapModule.forRoot(),
    ModalModule.forRoot(),
    MDBBootstrapModule.forRoot(),
    MatExpansionModule,
    MatSelectModule,
    MatCheckboxModule
  ], 
  providers: [
    DaService
  ],
  entryComponents: [
  ]
})
export class DelegatedCreditAuthorityModule { }
