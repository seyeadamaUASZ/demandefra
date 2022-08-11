import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddDemandeComponent } from './add-demandeAutFra/add-demande.component';
import { SoumettredemandeComponent } from './soumettredemande.component';

const routes: Routes = [
  {
    path: '', component: SoumettredemandeComponent,
    children: [
      {
        path: '', component: AddDemandeComponent
      }
    ],
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SoumettredemandeRoutingModule { }
