import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {SharedModule} from "../../../shared/shared.module";

import { CommitteeRoutingModule } from './committee-routing.module';
import { CommitteePoolComponent } from './components/committee-pool/committee-pool.component';
import { ACAEPaperService } from '../acae/services/acae-paper.service';
import { MDBBootstrapModule, MDBModalRef } from 'ng-uikit-pro-standard';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { SaveStatusModalComponent } from 'src/app/shared/components/save-status-modal/save-status-modal.component';
import { CommitteeTypeComponent } from './components/committee-type/committee-type.component';
import { SaveCommitteeTypeComponent } from './components/committee-type/save-committee-type/save-committee-type.component';
import { CommitteesComponent } from './components/committees/committees.component';
import { CommitteePoolAddEditComponent } from './components/committee-pool/committee-pool-add-edit/committee-pool-add-edit.component';
import { CommitteeAddEditComponent } from './components/committees/committee-add-edit/committee-add-edit.component';
import { CommitteeApproveRejectModalComponent } from 'src/app/shared/components/committee-approve-reject-modal/committee-approve-reject-modal.component';


@NgModule({
  declarations: [
    CommitteePoolAddEditComponent, 
    CommitteePoolComponent, CommitteeTypeComponent, SaveCommitteeTypeComponent, CommitteesComponent, CommitteeAddEditComponent
  ],
  imports: [
    SharedModule,
    CommitteeRoutingModule,
    MatSlideToggleModule,
    MDBBootstrapModule.forRoot(),
  ],
  entryComponents: [
    SaveStatusModalComponent,
    CommitteeApproveRejectModalComponent
  ],
  providers:[
    CommitteePoolAddEditComponent,
    ACAEPaperService,
    MDBModalRef
  ]
})
export class CommitteeModule { }
