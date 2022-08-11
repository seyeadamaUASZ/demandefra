import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormulaireComponent } from './formulaire.component';
import { MainContentFormComponent } from './components/main-content-form/main-content-form.component';

const routes: Routes = [
  {
    path: '', component: FormulaireComponent,
    children: [
      { path: '', component: MainContentFormComponent },
    ],
  },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FormulaireRoutingModule { }
