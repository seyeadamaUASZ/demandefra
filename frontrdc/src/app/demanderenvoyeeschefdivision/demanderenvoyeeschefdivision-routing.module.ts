import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentComponent } from './components/main-content/main-content.component';
import { DemanderenvoyeeschefdivisionComponent } from './demanderenvoyeeschefdivision.component';

const routes: Routes = [
  {
    path: '', component: DemanderenvoyeeschefdivisionComponent,
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
export class DemanderenvoyeeschefdivisionRoutingModule { }
