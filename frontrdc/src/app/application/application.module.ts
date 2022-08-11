import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatePipe } from '@angular/common';
import { ApplicationRoutingModule } from './application-routing.module';
import { MainContentComponent } from './components/main-content/main-content.component';
import { AjoutAppComponent } from './components/ajout-app/ajout-app.component';
import { MatExpansionModule, MatDialogModule, MAT_DIALOG_DATA, MatDialogRef, MatStepperModule, MatListModule } from '@angular/material';
import { ApplicationAppComponent } from './application-app.component';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { AjoutModuleComponent } from './components/ajout-module/ajout-module.component';
import { DetailModuleComponent } from './components/detail-module/detail-module.component';
import { EditModuleComponent } from './components/edit-module/edit-module.component';

import { EditLogoCompteComponent } from '../utilisateur/components/edit-logo-compte/edit-logo-compte.component';
import { FonctionnaliteComponent } from './components/fonctionnalite/fonctionnalite.component';
import { AjoutFoncComponent } from './components/fonctionnalite/ajout-fonc/ajout-fonc.component';
import { ChoixFoncComponent } from './components/fonctionnalite/choix-fonc/choix-fonc.component';
import { EditFoncComponent } from './components/fonctionnalite/edit-fonc/edit-fonc.component';
import { DetailsFoncComponent } from './components/fonctionnalite/detail-fonc/details-fonc.component';
import { EtapeCreationAppComponent } from './components/edit-app/etape-creation-app.component';
import { EditAppComponent } from './components/detail-app/edit-app.component';
import { AvatarModule } from 'ngx-avatar';
import { FormPublicationComponent } from './components/form-publication/form-publication.component';



@NgModule({

  declarations: [ApplicationAppComponent,
    MainContentComponent, 
    EditAppComponent,
    AjoutAppComponent,
    EtapeCreationAppComponent,
    AjoutModuleComponent,
    DetailModuleComponent,
    EditModuleComponent,
    EditAppComponent,
    FonctionnaliteComponent,
    AjoutFoncComponent,
    ChoixFoncComponent,
    EditFoncComponent,
    DetailsFoncComponent,
    FormPublicationComponent,
  ],
  imports: [
    ApplicationRoutingModule,
    MatExpansionModule,
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    MatStepperModule,
    MatListModule,
    MatDialogModule,
    AvatarModule

  ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})
export class ApplicationModule { }
