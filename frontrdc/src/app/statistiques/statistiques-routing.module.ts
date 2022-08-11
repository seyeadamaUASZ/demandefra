import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListStatsComponent } from './list-stats/list-stats.component';
import { StatistiquesComponent } from './statistiques.component';

const routes: Routes = [
  {
    path: '', component: StatistiquesComponent,
    children: [
      {
        path: '', component: ListStatsComponent
      }
    ],
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StatistiquesRoutingModule { }
