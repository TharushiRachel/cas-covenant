import {NgModule} from '@angular/core';
import {ApplicationTopicRoutingModule} from './application-topic-routing.module';
import {ApplicationTopicComponent} from './components/application-topic/application-topic.component';
import {ApplicationTopicAddEditComponent} from './components/application-topic-add-edit/application-topic-add-edit.component';
import {MDBBootstrapModulesPro} from "ng-uikit-pro-standard";
import {SharedModule} from "../../../shared/shared.module";
import {ApplicationFormTopicService} from "./services/application-form-topic.service";
import {ApplicationFormTopicAddEditService} from "./services/application-form-topic-add-edit.service";
import {ApplicationFormTopicAddDataComponent} from './components/application-topic-add-edit/application-form-topic-add-data/application-form-topic-add-data.component';
import {ApplicationFormUpcTopicAddEditComponent} from './components/application-topic-add-edit/application-form-upc-topic-add-edit/application-form-upc-topic-add-edit.component';


@NgModule({
  declarations: [
    ApplicationTopicComponent,
    ApplicationTopicAddEditComponent,
    ApplicationFormTopicAddDataComponent,
    ApplicationFormUpcTopicAddEditComponent
  ],
  imports: [
    ApplicationTopicRoutingModule,
    MDBBootstrapModulesPro.forRoot(),
    SharedModule
  ],
  entryComponents: [
    ApplicationFormTopicAddDataComponent,
    ApplicationFormUpcTopicAddEditComponent
  ],
  providers: [
    ApplicationFormTopicService,
    ApplicationFormTopicAddEditService
  ]
})
export class ApplicationTopicModule {
}
