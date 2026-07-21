import {NgModule} from '@angular/core';

import {SectionSubSectionRoutingModule} from './section-sub-section-routing.module';
import {SharedModule} from "../../../shared/shared.module";
import {SectionSubSectionComponent} from './components/section-sub-section/section-sub-section.component';
import {SectionSubSectionAddEditComponent} from './components/section-sub-section-add-edit/section-sub-section-add-edit.component';
import {SectionSubSectionService} from "./services/section-sub-section.service";
import {SectionSubSectionAddEditService} from "./services/section-sub-section-add-edit.service";


@NgModule({
  declarations: [SectionSubSectionComponent,
    SectionSubSectionAddEditComponent],
  imports: [
    SharedModule,
    SectionSubSectionRoutingModule
  ],
  providers: [
    SectionSubSectionService,
    SectionSubSectionAddEditService
  ]
})
export class SectionSubSectionModule {
}
