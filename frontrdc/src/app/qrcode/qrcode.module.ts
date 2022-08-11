import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MaterialModule } from '../shared/material.module';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { QrcodeRoutingModule } from 'src/app/qrcode/qrcode-routing'
import { QrcodeComponent } from './components/qrcode.component';
import { QrcodeAppComponent } from 'src/app/qrcode/qrcode-app-component';
import { AjoutQrcodeComponent } from './components/ajout-qrcode/ajout-qrcode.component';
import { EditQrcodeComponent } from './components/edit-qrcode/edit-qrcode.component';
import { NgxQRCodeModule } from '@techiediaries/ngx-qrcode';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { ModeliseQrcodeComponent } from './components/modelise-qrcode/modelise-qrcode.component';
import { DndModule } from 'ngx-drag-drop';
import { QrcodeGenereComponent } from './components/qrcode-genere/qrcode-genere.component';




@NgModule({
  declarations: [QrcodeComponent,
    QrcodeAppComponent, AjoutQrcodeComponent, 
    EditQrcodeComponent,  
    QrcodeGenereComponent,
    ModeliseQrcodeComponent],
  imports: [
    CommonModule,
    QrcodeRoutingModule,
    MaterialModule, 
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    DndModule,
   NgxQRCodeModule
  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class QrcodeModule { }
