import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AgentsService} from "./services/agents.service";
import {AgentsViewComponent} from "./components/agents-view/agents-view.component";
import {AgentAddEditService} from "./services/agent-add-edit.service";
import {AgentAddEditComponent} from "./components/agent-add-edit/agent-add-edit.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: AgentsService
    },
    component: AgentsViewComponent
  },

  {
    path: 'add-edit',
    resolve: {
      data: AgentAddEditService
    },
    component: AgentAddEditComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgentRoutingModule {
}
