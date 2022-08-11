import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DndModule } from 'ngx-drag-drop';
import { ChartsModule } from 'ng2-charts';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { CdkTreeModule } from '@angular/cdk/tree';


import { GestioncategorieAppComponent } from './gestioncategorie-app.component';
import { MainComponent } from './components/main/main.component';
import { GestioncategorieRoutingModule } from './gestioncategorie-routing.module';
import { AddcategorieComponent } from './components/addcategorie/addcategorie.component';
import { EditcategorieComponent } from './components/editcategorie/editcategorie.component';

@NgModule({
    declarations: [GestioncategorieAppComponent, MainComponent, AddcategorieComponent, EditcategorieComponent],
    imports: [
      CommonModule,
      GestioncategorieRoutingModule,
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
      FlexLayoutModule,
      HttpClientModule,
      SharedcomponentModule,
      MatDialogModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
      FlexLayoutModule,
      HttpClientModule,
      SharedcomponentModule,
      DndModule,
      ChartsModule,
      DragDropModule,
      CdkTreeModule,


    ],
    providers: [DatePipe,
      { provide: MAT_DIALOG_DATA, useValue: {} },
      { provide: MatDialogRef, useValue: {} }],
  })
  export class GestioncategorieModule { }
