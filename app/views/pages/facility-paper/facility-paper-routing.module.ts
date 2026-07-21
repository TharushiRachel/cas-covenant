import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FacilityPapersService} from "./services/facility-papers.service";
import {FacilityPapersComponent} from "./components/facility-papers/facility-papers.component";
import {FacilityPaperAddEditService} from "./services/facility-paper-add-edit.service";
import {FacilityPaperAddEditComponent} from "./components/facility-paper-add-edit/facility-paper-add-edit.component";
import {FpAuditDetailComponent} from "./components/facility-paper-add-edit/components/fp-audit-detail/fp-audit-detail.component";
import {AuditService} from "../audit/services/audit.service";
import { FacilityPaperSearchComponent } from './components/facility-paper-search/facility-paper-search.component';
import { FacilityPaperSearchService } from './services/facility-paper-search.service';
import { CustomerCovenantComponent } from '../customer-360/components/customer-base/components/customer-covenant/customer-covenant.component';
import { BccAttachmentsComponent } from './attachments/bcc-attachments/bcc-attachments.component';
import { BccDocumentUploadComponent } from './attachments/bcc-document-upload/bcc-document-upload.component';
import { CustomerLimitsOutstandingDataComponent } from './components/facility-paper-add-edit/components/facility-wrapper/fp-facilities/finacle-data/customer-limits-outstanding-data/customer-limits-outstanding-data.component';

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: FacilityPapersService
    },
    component: FacilityPapersComponent
  },
  {
    path: 'edit',
    resolve: {
      data: FacilityPaperAddEditService
    },
    component: FacilityPaperAddEditComponent
  },

  {
    path: 'fp-audit-detail',
    resolve: {
      data: AuditService
    },
    component: FpAuditDetailComponent
  },

  {
    path: 'search',
    resolve: {
      data: FacilityPaperSearchService
    },
    component: FacilityPaperSearchComponent
  },

  {
    path: 'customer-covenant',
    resolve: {
      data: CustomerCovenantComponent
    },
    component: CustomerCovenantComponent
  },

  {
    path: 'bcc-attachments',
    resolve: {
      data: BccAttachmentsComponent
    },
    component: BccAttachmentsComponent
  },
  
  {
    path: 'bcc-attachments-upload',
    resolve: {
      data: BccDocumentUploadComponent
    },
    component: BccDocumentUploadComponent
  },

  {
    path: 'customer-limits-outstanding-data',
    resolve: {
      data: CustomerLimitsOutstandingDataComponent
    },
    component: CustomerLimitsOutstandingDataComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacilityPaperRoutingModule {
}
