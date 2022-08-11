import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { DndModule } from 'ngx-drag-drop';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MainContentComponent } from './components/main-content/main-content.component';
import { MesdemanderejeteesRoutingModule } from './mesdemanderejetees-routing.module';
import { MesdemanderejeteesComponent } from './mesdemanderejetees.component';

@NgModule({ 
declarations: [
	MesdemanderejeteesComponent,
	MainContentComponent
  ],
 imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    MesdemanderejeteesRoutingModule,
    SweetAlert2Module.forRoot(),
    DndModule
  ],schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class MesdemanderejeteesModule { }
