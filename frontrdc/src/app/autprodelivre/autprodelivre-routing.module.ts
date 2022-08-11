import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AutprodelivreComponent } from './autprodelivre.component';
import { MainContentComponent } from './components/main-content/main-content.component';

const routes: Routes = [
  {
    path: '', component: AutprodelivreComponent,
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
export class AutprodelivreRoutingModule { }
