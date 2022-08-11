import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../login/services/auth/auth.guard';
import { MainContentComponent } from './components/main-content/main-content.component';
import { ApplicationAppComponent } from './application-app.component';
import { AjoutAppComponent } from './components/ajout-app/ajout-app.component';
import { EditAppComponent } from './components/detail-app/edit-app.component';
import { EtapeCreationAppComponent } from './components/edit-app/etape-creation-app.component';
import { FonctionnaliteComponent } from './components/fonctionnalite/fonctionnalite.component';

const routes: Routes = [
  {
    path: '', component: ApplicationAppComponent,
    children: [
      { path: 'fonctionnalite/:id',  canActivate: [AuthGuard], component: FonctionnaliteComponent },
      { path: 'detail/:id',  canActivate: [AuthGuard], component: EditAppComponent },
      { path: 'edit/:id',  canActivate: [AuthGuard], component: EtapeCreationAppComponent },
      { path: 'creation',  canActivate: [AuthGuard], component: AjoutAppComponent },
      { path: '', component: MainContentComponent },


    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule { }
