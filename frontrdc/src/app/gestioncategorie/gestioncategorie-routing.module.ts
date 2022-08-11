import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './components/main/main.component';
import { GestioncategorieAppComponent } from './gestioncategorie-app.component';


const routes: Routes = [
  {
    path: '', component: GestioncategorieAppComponent,
    children: [
      { path: '', component: MainComponent}


    ],

  },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioncategorieRoutingModule { }
