import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UtilisateurRoutingModule } from './utilisateur-routing.module';
import { DetailUtilisComponent } from './components/detail-utilis/detail-utilis.component';
import { EditUtilisComponent } from './components/edit-utilis/edit-utilis.component';
import { AjoutUtilisComponent } from './components/ajout-utilis/ajout-utilis.component';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { UtilisateurAppComponent } from './utilisateur-app.component';
import { MainContentUtilComponent } from './components/main-content-util/main-content-util.component';
import { DroitAccesComponent } from './components/droit-acces/droit-acces.component';
import { AjoutMenuComponent } from './components/ajout-menu/ajout-menu.component';
import { MonCompteComponent } from './components/mon-compte/mon-compte.component';
import { RolesComponent } from './components/roles/roles.component';
import { AddroleComponent } from './components/roles/addrole/addrole.component';
import { EditroleComponent } from './components/roles/editrole/editrole.component';
import { AllocateroleComponent } from './components/roles/allocaterole/allocaterole.component';
import { ViewroleComponent } from './components/roles/viewrole/viewrole.component';
import { AvatarModule } from 'ngx-avatar';
import { ParametreComponent } from './components/parametre/parametre.component';
import { ChartsModule } from 'ng2-charts';
import { DatePipe } from '@angular/common';
import { ImageCropperModule } from 'ngx-image-cropper';
import { ChangeCssComponent } from './components/change-css/change-css.component';

@NgModule({
  declarations: [UtilisateurAppComponent, DroitAccesComponent,
    DetailUtilisComponent, EditUtilisComponent, AjoutUtilisComponent,
    MainContentUtilComponent, DroitAccesComponent, AjoutMenuComponent, RolesComponent, AddroleComponent, EditroleComponent, AllocateroleComponent, ViewroleComponent],
  imports: [
    ChartsModule,
    CommonModule,
    UtilisateurRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    AvatarModule,
    ImageCropperModule

  ],
  providers:[DatePipe]
})
export class UtilisateurModule { }
