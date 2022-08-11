import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentComponent } from './components/main-content/main-content.component';
import { FormulaireV2Component } from './formulaire-v2.component';

const routes: Routes = [
  {
    path: '',
    component: FormulaireV2Component,
    children: [
      {
        path: '',
        component: MainContentComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FormulaireV2RoutingModule { }
