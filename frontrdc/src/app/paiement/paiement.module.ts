import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { PaiementRoutingModule } from './paiement-routing.module';
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
import { MainContentPaiementComponent } from './components/main-content-paiement/main-content-paiement.component';
import { PaiementAppComponent } from './paiement-app.component';
import { DetailPaiementComponent } from './components/detail-paiement/detail-paiement.component';
import { PaiementRedirectComponent } from './components/paiement-redirect/paiement-redirect.component';
import { MestransactionsComponent } from './components/mestransactions/mestransactions.component';

@NgModule({
  declarations: [MainContentPaiementComponent,PaiementAppComponent, DetailPaiementComponent, PaiementRedirectComponent, MestransactionsComponent],
  imports: [
    CommonModule,
    PaiementRoutingModule,
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
    CdkTreeModule
  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class PaiementModule { }
