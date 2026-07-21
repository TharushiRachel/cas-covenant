import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApplicationFormSearchComponent } from './components/application-form-search/application-form-search.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { MDBBootstrapModulesPro } from 'ng-uikit-pro-standard';
import { ApplicationFormSearchRoutingModule } from './application-form-search-routing.module';
import { ApplicationFormSearchService } from './services/application-form-search.service';


@NgModule({
  declarations: [ApplicationFormSearchComponent],
    providers: [ApplicationFormSearchService],
    imports: [
      SharedModule,
      MDBBootstrapModulesPro.forRoot(),
      ApplicationFormSearchRoutingModule,
      //CommonModule
    ]
})
export class ApplicationFormSearchModule { }
