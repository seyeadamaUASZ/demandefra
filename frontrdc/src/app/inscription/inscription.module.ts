import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MaterialModule } from '../shared/material.module';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { from } from 'rxjs';
import { InscriptionRoutingModule } from './inscription-routing.module';
import { InscriptionAppComponent } from './inscription-app.component';
import { InscriptionComponent } from './components/inscription.component';
//import { DesinscrireComponent } from './components/desinscrire/desinscrire.component';

@NgModule({
  declarations: [
      InscriptionAppComponent, InscriptionComponent
    ],
  imports: [
    CommonModule,
    InscriptionRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,   
    SharedcomponentModule
  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class InscriptionModule { }
