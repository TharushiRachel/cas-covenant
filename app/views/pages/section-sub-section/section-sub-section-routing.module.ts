import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SectionSubSectionService} from "./services/section-sub-section.service";
import {SectionSubSectionComponent} from "./components/section-sub-section/section-sub-section.component";
import {SectionSubSectionAddEditService} from "./services/section-sub-section-add-edit.service";
import {SectionSubSectionAddEditComponent} from "./components/section-sub-section-add-edit/section-sub-section-add-edit.component";

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: SectionSubSectionService
    },
    component: SectionSubSectionComponent
  },
  {
    path: 'add-edit',
    resolve: {
      data: SectionSubSectionAddEditService
    },
    component: SectionSubSectionAddEditComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SectionSubSectionRoutingModule {
}
