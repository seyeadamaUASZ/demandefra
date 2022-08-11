import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { FormulaireRoutingModule } from './formulaire-routing.module';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { MainContentFormComponent } from './components/main-content-form/main-content-form.component';
import { FormulaireComponent } from './formulaire.component';
import { AjouterFormComponent } from './components/ajouter-form/ajouter-form.component';
import { DndModule } from 'ngx-drag-drop';
import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { ViewFormComponent } from './components/view-form/view-form.component';
import { EditFormComponent } from './components/edit-form/edit-form.component';
import { MonCompteComponent } from '../utilisateur/components/mon-compte/mon-compte.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { EditCompteComponent } from '../utilisateur/components/edit-compte/edit-compte.component';
import { EditLogoCompteComponent } from '../utilisateur/components/edit-logo-compte/edit-logo-compte.component';



@NgModule({
  declarations: [FormulaireComponent, 
    MainContentFormComponent, 
    AjouterFormComponent, 
    ViewFormComponent, 
    EditFormComponent],
  imports: [
    CommonModule,
    FormulaireRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    SweetAlert2Module.forRoot(),
    DndModule
  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class FormulaireModule { }
