import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {WorkflowTemplateService} from "./services/workflow-template.service";
import {WorkflowTemplateAddEditService} from "./services/workflow-template-add-edit.service";
import {WorkflowTemplateAddEditComponent} from "./components/workflow-template-add-edit/workflow-template-add-edit.component";
import {WorkflowTemplateComponent} from "./components/workflow-template/workflow-template.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: WorkflowTemplateService
    },
    component: WorkflowTemplateComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: WorkflowTemplateAddEditService
    },
    component: WorkflowTemplateAddEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkflowTemplateRoutingModule {
}
