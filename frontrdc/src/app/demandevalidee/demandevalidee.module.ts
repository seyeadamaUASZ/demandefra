import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { DndModule } from 'ngx-drag-drop';
import { FlexLayoutModule } from '@angular/flex-layout';

import { DemandevalideeComponent } from './demandevalidee.component';
import { MainComponent } from './components/main/main.component';
import { DemandevalideeRoutingModule } from './demandevalidee-routing.module';

@NgModule({
declarations: [
  DemandevalideeComponent,
  MainComponent
  ],
 imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    DemandevalideeRoutingModule,
    SweetAlert2Module.forRoot(),
    DndModule
  ],schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DemandevalideeModule { }
