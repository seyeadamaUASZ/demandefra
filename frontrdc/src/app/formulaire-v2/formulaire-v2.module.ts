import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';

import { FormulaireV2RoutingModule } from './formulaire-v2-routing.module';
import { MainContentComponent } from './components/main-content/main-content.component';
import { EditFormComponent } from './components/edit-form/edit-form.component';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DndModule } from 'ngx-drag-drop';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormulaireV2Component } from './formulaire-v2.component';
import { DetailComponent } from './components/detail/detail.component';

@NgModule({
  declarations: [FormulaireV2Component,MainContentComponent, EditFormComponent, DetailComponent],
  imports: [
    CommonModule,
    FormulaireV2RoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    DndModule
  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class FormulaireV2Module { }
