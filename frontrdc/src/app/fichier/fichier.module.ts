import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { FichierRoutingModule } from './fichier-routing.module';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { MainContentComponent } from './components/main-content/main-content.component';
import { FichierComponent } from './fichier.component';
import { FormulaireRoutingModule } from '../formulaire/formulaire-routing.module';
import { AjouterFichierComponent } from './components/ajouter-fichier/ajouter-fichier.component';
import { EditFormComponent } from './components/edit-form/edit-form.component';
import { EditFichierComponent } from './components/edit-fichier/edit-fichier.component';
import { EditRapportFileComponent } from './components/edit-rapport-file/edit-rapport-file.component';
import { DndModule } from 'ngx-drag-drop';
import { FichierExportformComponent } from './components/fichier-exportform/fichier-exportform.component';
//import { DynamicFormFieldComponent } from '../sharedcomponent/dynamicform/components/dynamic-form-field/dynamic-form-field.component';
import { RapportComponent } from './components/rapport/rapport.component';
import { AjoutSousMenuComponent } from './components/ajout-sous-menu/ajout-sous-menu.component';
import { AttriSousMenuComponent } from './components/attri-sous-menu/attri-sous-menu.component';
import { ViewListRapportGenComponent } from './components/view-list-rapport-gen/view-list-rapport-gen.component';
import { ViewListSousMenuComponent } from './components/view-list-sous-menu/view-list-sous-menu.component';
import { EditSousMenuComponent } from './components/edit-sous-menu/edit-sous-menu.component';
import { RapportSousMenuComponent } from './components/rapport-sous-menu/rapport-sous-menu.component';
import { RouteReuseStrategy } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { MonCompteComponent } from '../utilisateur/components/mon-compte/mon-compte.component';
import { EditCompteComponent } from '../utilisateur/components/edit-compte/edit-compte.component';
import { AvatarModule } from 'ngx-avatar';
import { EditLogoCompteComponent } from '../utilisateur/components/edit-logo-compte/edit-logo-compte.component';
import { NgxQRCodeModule } from '@techiediaries/ngx-qrcode';

@NgModule({
  declarations: [
    MainContentComponent,
    FichierComponent,
    AjouterFichierComponent,
    EditFormComponent,
    FichierExportformComponent,
  // DynamicFormFieldComponent,
     EditFichierComponent,
     EditRapportFileComponent,
     RapportComponent,
     AjoutSousMenuComponent,
     AttriSousMenuComponent,
     ViewListRapportGenComponent,
     ViewListSousMenuComponent,
     EditSousMenuComponent,
     RapportSousMenuComponent,
    
  ],
  imports: [
    CommonModule,
    FichierRoutingModule,
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    FormulaireRoutingModule,
    ReactiveFormsModule,
    DndModule,
    AvatarModule,
    NgxQRCodeModule
  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
  
})
export class FichierModule { }
