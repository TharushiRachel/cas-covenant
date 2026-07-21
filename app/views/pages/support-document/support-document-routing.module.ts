import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SupportDocumentsService} from "./services/support-documents.service";
import {SupportDocumentsComponent} from "./components/support-documents/support-documents.component";
import {SupportDocumentAddEditComponent} from "./components/support-document-add-edit/support-document-add-edit.component";
import {SupportDocumentAddEditService} from "./services/support-document-add-edit.service";


const routes: Routes = [
  {
    path: '',
    resolve:{
      data: SupportDocumentsService
    },
    component: SupportDocumentsComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: SupportDocumentAddEditService
    },
    component: SupportDocumentAddEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupportDocumentRoutingModule { }
