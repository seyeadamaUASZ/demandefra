import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UtilisateurAppComponent } from './utilisateur-app.component';
import { DetailUtilisComponent } from './components/detail-utilis/detail-utilis.component';
import { MainContentUtilComponent } from './components/main-content-util/main-content-util.component';
import { DroitAccesComponent } from './components/droit-acces/droit-acces.component';
import { MonCompteComponent } from './components/mon-compte/mon-compte.component';
import { RolesComponent } from './components/roles/roles.component';
import { AllocateroleComponent } from './components/roles/allocaterole/allocaterole.component';
import { ParametreComponent } from './components/parametre/parametre.component';
import { ChangePwdComponent } from './components/change-pwd/change-pwd.component';
import { ChangeCssComponent } from './components/change-css/change-css.component';

const routes: Routes = [
  {
    path: '', component: UtilisateurAppComponent,
    children: [
      { path: 'changePwd', component: ChangePwdComponent },
      { path: 'acces', component: DroitAccesComponent },
      { path: 'accesrights', component: AllocateroleComponent },
      { path: 'roles', component: RolesComponent },
      { path: 'details', component: DetailUtilisComponent },
      { path: 'monCompte', component: MonCompteComponent },
      { path: 'changecss', component: ChangeCssComponent },
      { path: '', component: MainContentUtilComponent },


    ],
    
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UtilisateurRoutingModule { }
