import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationFromCopyService} from "./services/application-from-copy.service";
import {ApplicationFormCopyBaseComponent} from "./components/application-form-copy-base/application-form-copy-base.component";


const routes: Routes = [{
  path: '',
  resolve: {
    data: ApplicationFromCopyService
  },
  component: ApplicationFormCopyBaseComponent
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationFormCopyRoutingModule {
}
