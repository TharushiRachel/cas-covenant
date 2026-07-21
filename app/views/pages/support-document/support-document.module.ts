import { NgModule } from '@angular/core';


import { SupportDocumentRoutingModule } from './support-document-routing.module';
import { SupportDocumentsComponent } from './components/support-documents/support-documents.component';
import {SupportDocumentsService} from "./services/support-documents.service";
import {SharedModule} from "../../../shared/shared.module";
import { SupportDocumentAddEditComponent } from './components/support-document-add-edit/support-document-add-edit.component';
import {SupportDocumentAddEditService} from "./services/support-document-add-edit.service";


@NgModule({
  declarations: [SupportDocumentsComponent, SupportDocumentAddEditComponent],
  imports: [
    SharedModule,
    SupportDocumentRoutingModule
  ],
  providers:[
    SupportDocumentsService,
    SupportDocumentAddEditService]
})
export class SupportDocumentModule { }
