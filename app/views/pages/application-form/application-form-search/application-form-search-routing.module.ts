import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { ApplicationFormSearchService } from './services/application-form-search.service';
import { ApplicationFormSearchComponent } from './components/application-form-search/application-form-search.component';

const routes: Routes = [
  {
    path: '',
    resolve: {
      data: ApplicationFormSearchService
    },
    component: ApplicationFormSearchComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationFormSearchRoutingModule {
}
