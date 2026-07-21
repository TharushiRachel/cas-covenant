import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CommitteePoolComponent } from "./components/committee-pool/committee-pool.component";
import { CommitteeTypeComponent } from "./components/committee-type/committee-type.component";
import { SaveCommitteeTypeComponent } from "./components/committee-type/save-committee-type/save-committee-type.component";
import { CommitteesComponent } from "./components/committees/committees.component";
import { CommitteePoolAddEditComponent } from "./components/committee-pool/committee-pool-add-edit/committee-pool-add-edit.component";
import { CommitteeAddEditComponent } from "./components/committees/committee-add-edit/committee-add-edit.component";

const routes: Routes = [
  {
    path: "all",
    component: CommitteesComponent,
    data: {
      title: "Committee",
    },
  },
  {
    path: "add-edit",
    component: CommitteeAddEditComponent,
    data: {
      title: "Add New Committee",
    },
  },
  {
    path: "pool",
    component: CommitteePoolComponent,
    data: {
      title: "Committee Pool List",
    },
  },
  {
    path: "pool-add-edit",
    component: CommitteePoolAddEditComponent,
    data: {
      title: "Add New Committee Pool",
    },
  },
  {
    path: "type",
    component: CommitteeTypeComponent,
    data: {
      title: "Committee Type",
    },
  },
  {
    path: "type-add-edit",
    component: SaveCommitteeTypeComponent,
    data: {
      title: "Save Committee Type",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CommitteeRoutingModule {}
