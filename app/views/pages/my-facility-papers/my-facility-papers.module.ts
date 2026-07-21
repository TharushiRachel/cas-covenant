import {NgModule} from '@angular/core';

import {MyFacilityPapersRoutingModule} from './my-facility-papers-routing.module';
import {MyFacilityPapersComponent} from './components/my-facility-papers/my-facility-papers.component';
import {SharedModule} from "../../../shared/shared.module";
import {MyFacilityPapersService} from "./services/my-facility-paper.service";
import { CountBoxComponent } from './components/my-facility-papers/count-box/count-box.component';
import { FacilityPaperAddEditService } from '../facility-paper/services/facility-paper-add-edit.service';


@NgModule({
  declarations: [MyFacilityPapersComponent, CountBoxComponent],
  imports: [
    SharedModule,
    MyFacilityPapersRoutingModule
  ],
  providers: [
    MyFacilityPapersService,
    FacilityPaperAddEditService
  ]
})
export class MyFacilityPapersModule {
}
