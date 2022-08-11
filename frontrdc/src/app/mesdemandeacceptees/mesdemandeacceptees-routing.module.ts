import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentComponent } from './components/main-content/main-content.component';
import { MesdemandeaccepteesComponent } from './mesdemandeacceptees.component';

const routes: Routes = [
  {
    path: '', component: MesdemandeaccepteesComponent,
    children: [
      {
        path: '',
        component: MainContentComponent
      }
    ],

  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MesdemandeaccepteesRoutingModule { }
