import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ProceduresComponent } from './components/procedures.component';
import { MaterialModule } from '../shared/material.module';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProdeduresRoutingModule } from './procedures-routing';
import { ProceduresAppComponent } from './procedures-app-component';
import { ExecutionComponent } from './execution/execution.component';
import { TaskComponent } from './task/task.component';
import { TaskTableComponent } from './tasktable/tasktable.component';
import { ViewdetailtaskComponent } from './viewdetailtask/viewdetailtask.component';
import { ProcessComponent } from './process/process.component';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material';
import { MonCompteComponent } from '../utilisateur/components/mon-compte/mon-compte.component';
import { EditCompteComponent } from '../utilisateur/components/edit-compte/edit-compte.component';


@NgModule({

  declarations: [ProceduresAppComponent, ExecutionComponent,  TaskComponent,TaskTableComponent,ViewdetailtaskComponent, ProcessComponent],

  imports: [
    CommonModule,
    ProdeduresRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    MatDialogModule

  ],
  exports:[],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class ProceduresModule { }
