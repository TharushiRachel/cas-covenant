import {NgModule} from '@angular/core';

import {WorkflowTemplateRoutingModule} from './workflow-template-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import {WorkflowTemplateComponent} from './components/workflow-template/workflow-template.component';
import {WorkflowTemplateAddEditComponent} from './components/workflow-template-add-edit/workflow-template-add-edit.component';
import {WorkflowTemplateService} from "./services/workflow-template.service";
import {WorkflowTemplateAddEditService} from "./services/workflow-template-add-edit.service";
import {WorkflowRoutingComponent} from './components/workflow-template-add-edit/workflow-routing/workflow-routing.component';
import {WorkflowRoutingDataComponent} from './components/workflow-template-add-edit/workflow-routing/workflow-routing-data/workflow-routing-data.component';


@NgModule({
  declarations: [WorkflowTemplateComponent,
    WorkflowTemplateAddEditComponent,
    WorkflowRoutingComponent,
    WorkflowRoutingDataComponent],
  imports: [
    SharedModule,
    WorkflowTemplateRoutingModule
  ],
  providers: [
    WorkflowTemplateService,
    WorkflowTemplateAddEditService
  ]
})
export class WorkflowTemplateModule {
}
