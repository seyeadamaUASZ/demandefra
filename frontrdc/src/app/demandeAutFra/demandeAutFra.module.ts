import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { DndModule } from 'ngx-drag-drop';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DemandeAutFraComponent } from './demandeAutFra.component';
import { DemandeAutFraRoutingModule } from './demandeAutFra-routing.module';
import { MainContentComponent } from './components/main-content/main-content.component';
import { AddSuivredemandeComponent } from './components/add-suivredemande/add-suivredemande.component';
import { SuivredemandeComponent } from './components/suivredemande/suivredemande.component';

@NgModule({
  declarations: [
    DemandeAutFraComponent,
    MainContentComponent,
    AddSuivredemandeComponent,
    SuivredemandeComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    DemandeAutFraRoutingModule,
    SweetAlert2Module,
    DndModule
  ], schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemandeAutFraModule { }
