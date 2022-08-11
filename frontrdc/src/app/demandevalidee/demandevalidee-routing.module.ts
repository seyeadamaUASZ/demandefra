import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './components/main/main.component';
import { DemandevalideeComponent } from './demandevalidee.component';


const routes: Routes = [
  {
    path: '', component: DemandevalideeComponent,
    children: [
      {
        path: '',
        component: MainComponent
      }
    ],

  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DemandevalideeRoutingModule { }
