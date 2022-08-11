import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExceptionAppComponent } from './exception-app.component';
import { UnauthorizedPageComponent } from './components/unauthorized-page/unauthorized-page.component';


const routes: Routes = [
  {
    path: '', component: ExceptionAppComponent,
    children: [
      { path: 'unauthorized', component: UnauthorizedPageComponent },
     /* { path: 'acces', component: DroitAccesComponent },
      { path: 'accesrights', component: AllocateroleComponent },
      { path: 'roles', component: RolesComponent },
      { path: 'details', component: DetailUtilisComponent },
      { path: 'monCompte', component: MonCompteComponent },
      { path: 'parametre', component: ParametreComponent },
  { path: '', component: MainContentUtilComponent },*/


    ],
    
  },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExceptionRoutingModule { }
