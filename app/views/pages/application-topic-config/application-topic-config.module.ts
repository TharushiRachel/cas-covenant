import {NgModule} from '@angular/core';
import {ApplicationTopicConfigRoutingModule} from './application-topic-config-routing.module';
import {ApplicationTopicConfigAddEditComponent} from "./components/application-topic-config-add-edit/application-topic-config-add-edit.component";
import {ApplicationTopicConfigAddEditService} from "./services/application-topic-config-add-edit.service";
import {SharedModule} from "../../../shared/shared.module";

@NgModule({
  declarations: [ApplicationTopicConfigAddEditComponent],
  imports: [
    SharedModule,
    ApplicationTopicConfigRoutingModule
  ],
  providers: [
    ApplicationTopicConfigAddEditService
  ]
})

export class ApplicationTopicConfigModule {
}
