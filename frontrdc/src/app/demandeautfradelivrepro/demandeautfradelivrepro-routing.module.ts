import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentComponent } from './components/main-content/main-content.component';
import { DemandeautfradelivreproComponent } from './demandeautfradelivrepro.component';


const routes: Routes = [
  {
    path: '', component: DemandeautfradelivreproComponent,
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
export class DemandeautfradelivreproRoutingModule { }
