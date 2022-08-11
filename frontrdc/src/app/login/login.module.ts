import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import { AvatarModule } from 'ngx-avatar';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from '../shared/material.module';
import { LoginComponent } from './login.component';

import { AppLoginComponent } from './login-app.component';
import { MonCompteComponent } from '../utilisateur/components/mon-compte/mon-compte.component';
import { EditCompteComponent } from '../utilisateur/components/edit-compte/edit-compte.component';
import { EditLogoCompteComponent } from '../utilisateur/components/edit-logo-compte/edit-logo-compte.component';
import { PremiereConnectComponent } from './components/premiere-connect/premiere-connect.component';
import { ForgetPwdComponent } from './components/forget-pwd/forget-pwd.component';
import { NewPwdComponent } from './components/new-pwd/new-pwd.component';
import { LandingComponent } from './components/landing/landing.component';
import { MatGridListModule } from '@angular/material';
import { AutreConnexionComponent } from './components/autre-connexion/autre-connexion.component';
import { AppPublishedComponent } from './components/app-published/app-published.component';



@NgModule({
  declarations: [AppLoginComponent,
    LoginComponent,
    PremiereConnectComponent,
    ForgetPwdComponent,
    NewPwdComponent, 
    LandingComponent, AutreConnexionComponent, AppPublishedComponent, 
    ],
  imports: [
    CommonModule,
    LoginRoutingModule,
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    MatGridListModule,
    AvatarModule

  ]
})
export class LoginModule { }
