import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProceduresComponent } from './components/procedures.component';
import { ProceduresAppComponent } from './procedures-app-component';
import { SecteursComponent } from '../parametrage/components/groupeservice/secteurs/secteurs.component';
import { ExecutionComponent } from './execution/execution.component';
const routes: Routes = [
  {
    path: '', component: ProceduresAppComponent, 
   
    children: [
      {path: '', component: ProceduresComponent }, 
      {path: 'secteurs/:id/execution/:idname', component: ExecutionComponent },
      {path: 'secteurs/:id', component: SecteursComponent },
      {path: 'secteurs', component: SecteursComponent }, 

    ],
    
     
  },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProdeduresRoutingModule { }
