import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MyFacilityPapersComponent} from "./components/my-facility-papers/my-facility-papers.component";
import {MyFacilityPapersService} from "./services/my-facility-paper.service";


const routes: Routes = [
  {
    path: '',
    resolve: {
      data: MyFacilityPapersService
    },
    component: MyFacilityPapersComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyFacilityPapersRoutingModule {
}
