import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationFormTopicService} from "./services/application-form-topic.service";
import {ApplicationTopicComponent} from "./components/application-topic/application-topic.component";
import {ApplicationTopicAddEditComponent} from "./components/application-topic-add-edit/application-topic-add-edit.component";
import {ApplicationFormTopicAddEditService} from "./services/application-form-topic-add-edit.service";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationFormTopicService
    },
    component: ApplicationTopicComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: ApplicationFormTopicAddEditService
    },
    component: ApplicationTopicAddEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationTopicRoutingModule {
}
