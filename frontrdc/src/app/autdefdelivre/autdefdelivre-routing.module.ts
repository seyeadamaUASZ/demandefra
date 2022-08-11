import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AutdefdelivreComponent } from './autdefdelivre.component';
import { MainContentComponent } from './components/main-content/main-content.component';

const routes: Routes = [
  {
    path: '', component: AutdefdelivreComponent,
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
export class AutdefdelivreRoutingModule { }
