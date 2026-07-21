import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationTopicConfigAddEditService} from "./services/application-topic-config-add-edit.service";
import {ApplicationTopicConfigAddEditComponent} from "./components/application-topic-config-add-edit/application-topic-config-add-edit.component";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationTopicConfigAddEditService
    },
    component: ApplicationTopicConfigAddEditComponent
  },
  {
    path: 'edit',
    resolve: {
      data: ApplicationTopicConfigAddEditService
    },
    component: ApplicationTopicConfigAddEditComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationTopicConfigRoutingModule {
}
