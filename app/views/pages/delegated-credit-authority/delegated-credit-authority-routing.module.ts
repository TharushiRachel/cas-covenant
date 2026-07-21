import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DaTableComponent } from './components/da-table/da-table.component';
import { MatTableModule } from '@angular/material';


const routes: Routes = [{
  path: 'da-table',
  
  component: DaTableComponent
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule,MatTableModule]
})


export class DelegatedCreditAuthorityRoutingModule { }
