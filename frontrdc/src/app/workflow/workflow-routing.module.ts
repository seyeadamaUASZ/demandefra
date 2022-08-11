import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WorkflowAppComponent } from './workflow-app.component';
import { MainContentWorkflowComponent } from './component/main-content-workflow/main-content-workflow.component';
import { ListeFormulaireComponent } from './component/liste-formulaire/liste-formulaire.component';
import { GestionTaskComponent } from './component/gestion-task/gestion-task.component';
import { GestionWidgetComponent } from './component/gestion-widget/gestion-widget.component';
import { ListeFormulairev2Component } from './component/liste-formulairev2/liste-formulairev2.component';

const routes: Routes = [
  {
    path: '', component: WorkflowAppComponent,
    children: [
      {path: 'liste-formulaire/:idwrkf/:containerid', component: ListeFormulaireComponent },
      {path: 'liste-formulaire', component: ListeFormulaireComponent },
      //{path: 'liste-formulairev2/:idwrkf/:containerid', component: ListeFormulairev2Component },
      {path: 'liste-formulairev2/:idwrkf/:nomworkflow', component: ListeFormulairev2Component },
      {path: 'liste-formulairev2', component: ListeFormulairev2Component },
      //{path: 'gestion-task/:idwrkf/:containerid/:processid', component: GestionTaskComponent },
      {path: 'gestion-task/:idwrkf/:nomworkflow', component: GestionTaskComponent },
      {path: 'gestion-task', component: GestionTaskComponent },
      //{path: 'gestion-widget/:idwrkf/:containerid/:processid', component: GestionWidgetComponent },
      {path: 'gestion-widget/:idwrkf/:nomworkflow', component: GestionWidgetComponent },
      {path: 'gestion-widget', component: GestionWidgetComponent },
     {path: '', component: MainContentWorkflowComponent},
    ]
  },
 { path: '**', redirectTo: '' }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkflowRoutingModule { }
