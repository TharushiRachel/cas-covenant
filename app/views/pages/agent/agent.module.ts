import {NgModule} from '@angular/core';

import {AgentRoutingModule} from './agent-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import { AgentsViewComponent } from './components/agents-view/agents-view.component';
import { AgentAddEditComponent } from './components/agent-add-edit/agent-add-edit.component';
import {AgentsService} from "./services/agents.service";
import {AgentAddEditService} from "./services/agent-add-edit.service";


@NgModule({
  declarations: [AgentsViewComponent, AgentAddEditComponent],
  imports: [
    SharedModule,
    AgentRoutingModule
  ],
  providers : [
    AgentsService,
    AgentAddEditService
  ]
})
export class AgentModule {
}
