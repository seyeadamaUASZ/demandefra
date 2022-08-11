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
import { GenerateurotpRoutingModule } from './generateurotp-routing.module';
import { GenerateurotpAppComponent } from './generateurotp-app.component';
import { MainContentComponent } from './components/main-content/main-content.component';
import { AjoutParametreComponent } from './components/ajout-parametre/ajout-parametre.component';
import { EditParametreComponent } from './components/edit-parametre/edit-parametre.component';
import { TestconfigurationComponent } from './components/testconfiguration/testconfiguration.component';

@NgModule({
    declarations: [GenerateurotpAppComponent, MainContentComponent, AjoutParametreComponent, EditParametreComponent, TestconfigurationComponent],
    imports: [
      CommonModule,
      GenerateurotpRoutingModule,
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
  export class GenerateurotpModule { }