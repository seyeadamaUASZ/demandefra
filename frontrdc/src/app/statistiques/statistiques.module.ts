import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { DndModule } from 'ngx-drag-drop';
import { FlexLayoutModule } from '@angular/flex-layout';
import { StatistiquesComponent } from './statistiques.component';
import { StatistiquesRoutingModule } from './statistiques-routing.module';
import { ListStatsComponent } from './list-stats/list-stats.component';
import { FilterpopupComponent } from './filterpopup/filterpopup.component';

@NgModule({ 
declarations: [
  StatistiquesComponent,
  ListStatsComponent,
  FilterpopupComponent
  ],
 imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    StatistiquesRoutingModule,
    SweetAlert2Module.forRoot(),
    DndModule
  ],schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class StatistiquesModule { }
