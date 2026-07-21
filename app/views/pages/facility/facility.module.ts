import {NgModule} from '@angular/core';

import {FacilityRoutingModule} from './facility-routing.module';
import {FacilitiesComponent} from './components/facilities/facilities.component';
import {FacilitiesService} from "./services/facilities.service";
import {SharedModule} from "../../../shared/shared.module";
import {FacilityDetailsComponent} from './components/facility-details/facility-details.component';
import {FacilityDetailsService} from "./services/facility-details.service";


@NgModule({
  declarations: [FacilitiesComponent, FacilityDetailsComponent],
  imports: [
    SharedModule,
    FacilityRoutingModule
  ],
  providers: [FacilitiesService,
    FacilityDetailsService]
})
export class FacilityModule {
}
