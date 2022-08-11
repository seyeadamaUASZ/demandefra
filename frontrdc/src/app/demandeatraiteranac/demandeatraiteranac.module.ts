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
import { DemandeatraiteranacComponent } from './demandeatraiteranac.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import { DemandeatraiteranacRoutingModule } from './demandeatraiteranac-routing.module';

@NgModule({
declarations: [
  DemandeatraiteranacComponent,
	MainContentComponent,
  ],
 imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    MatPaginatorModule,
    MatSortModule,
    DemandeatraiteranacRoutingModule,
    SharedcomponentModule,
    SweetAlert2Module.forRoot(),
    DndModule
  ],schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DemandeatraiteranacModule { }
