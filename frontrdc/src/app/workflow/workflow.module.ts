import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { WorkflowRoutingModule } from './workflow-routing.module';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { WorkflowAppComponent } from './workflow-app.component';
import { MainContentWorkflowComponent } from './component/main-content-workflow/main-content-workflow.component';
import { AddworkflowComponent} from './component/addworkflow/addworkflow.component';
import { from } from 'rxjs';
import { ViewworkflowComponent } from './component/viewworkflow/viewworkflow.component';
import { EditworkflowComponent } from './component/editworkflow/editworkflow.component';

import { BpmComponent } from './component/bpm/bpm.component';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material';
import { MonCompteComponent } from '../utilisateur/components/mon-compte/mon-compte.component';
import { EditCompteComponent } from '../utilisateur/components/edit-compte/edit-compte.component';
import { EditLogoCompteComponent } from '../utilisateur/components/edit-logo-compte/edit-logo-compte.component';
import { ListeFormulaireComponent } from './component/liste-formulaire/liste-formulaire.component';
import { AddformulaireComponent } from './component/addformulaire/addformulaire.component';
import { VisualiserFormComponent } from './component/visualiserForm/visualiserForm.component';
import { AssemblerformComponent } from './component/assemblerform/assemblerform.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { EditformulaireComponent } from './component/editformulaire/editformulaire.component';
import { GestionTaskComponent } from './component/gestion-task/gestion-task.component';
import { AddtaskComponent } from './component/addtask/addtask.component';
import { EditworkflowtaskComponent } from './component/editworkflowtask/editworkflowtask.component';
import { AddTransitionComponent } from './component/add-transition/add-transition.component';

import { AddstatustaskComponent } from './component/addstatustask/addstatustask.component';
import { GestionWidgetComponent } from './component/gestion-widget/gestion-widget.component';
import { AddconfigWidgetComponent } from './component/addconfig-widget/addconfig-widget.component';
import { AddconfigattributComponent } from './component/addconfigattribut/addconfigattribut.component';
import { EditconfigattributComponent } from './component/editconfigattribut/editconfigattribut.component';
import { AddconfigTimerComponent } from './component/addconfig-timer/addconfig-timer.component';
import { SimulertimerComponent } from './component/simulertimer/simulertimer.component';
import { AddReglegestionComponent } from './component/add-reglegestion/add-reglegestion.component';
import { ListeFormulairev2Component } from './component/liste-formulairev2/liste-formulairev2.component';
import { Addformulairev2Component } from './component/addformulairev2/addformulairev2.component';


@NgModule({
  declarations: [WorkflowAppComponent, MainContentWorkflowComponent, AddworkflowComponent,

    ViewworkflowComponent, EditworkflowComponent,BpmComponent, ListeFormulaireComponent, AddformulaireComponent,VisualiserFormComponent,GestionTaskComponent, AssemblerformComponent, EditformulaireComponent, GestionTaskComponent, AddtaskComponent, EditworkflowtaskComponent, AddTransitionComponent, AddstatustaskComponent, GestionWidgetComponent, AddconfigWidgetComponent, AddconfigattributComponent, EditconfigattributComponent, AddconfigTimerComponent, SimulertimerComponent, AddReglegestionComponent, ListeFormulairev2Component, Addformulairev2Component],

  imports: [
    CommonModule,
    WorkflowRoutingModule,
    WorkflowRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    MatDialogModule,
    DragDropModule
  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class WorkflowModule { }
