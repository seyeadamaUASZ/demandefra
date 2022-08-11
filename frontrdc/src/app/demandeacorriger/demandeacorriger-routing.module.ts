import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentComponent } from './components/main-content/main-content.component';
import { DemandeacorrigerComponent } from './demandeacorriger.component';

const routes: Routes = [
  {
    path: '', component: DemandeacorrigerComponent,
    children: [
      {
        path: '', component: MainContentComponent
      }
    ],

  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DemandeacorrigerRoutingModule { }
