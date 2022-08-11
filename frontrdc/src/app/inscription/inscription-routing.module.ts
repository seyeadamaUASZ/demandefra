import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InscriptionComponent } from './components/inscription.component';
import { InscriptionAppComponent } from './inscription-app.component';




const routes: Routes = [
  {
    path: '', component: InscriptionComponent
    
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InscriptionRoutingModule { }

