import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FacilitiesService} from "./services/facilities.service";
import {FacilitiesComponent} from "./components/facilities/facilities.component";
import {FacilityDetailsComponent} from "./components/facility-details/facility-details.component";
import {FacilityDetailsService} from "./services/facility-details.service";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: FacilitiesService
    },
    component: FacilitiesComponent
  },
  {
    path: 'details',
    resolve: {
      data: FacilityDetailsService
    },
    component: FacilityDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacilityRoutingModule {
}
