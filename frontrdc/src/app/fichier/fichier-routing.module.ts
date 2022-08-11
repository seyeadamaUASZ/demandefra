import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FichierComponent } from './fichier.component';
import { MainContentComponent } from './components/main-content/main-content.component';
import { FichierExportformComponent } from './components/fichier-exportform/fichier-exportform.component';
import { RapportComponent } from './components/rapport/rapport.component';
import { RapportSousMenuComponent } from './components/rapport-sous-menu/rapport-sous-menu.component';

const routes: Routes = [
  {
    path: '', component: FichierComponent,
    children: [
      { path: '', component: MainContentComponent },
      { path: 'export', component: FichierExportformComponent },
      { 
        path: 'rapport', component: RapportComponent
      },
      { path: 'rapport/:id', component: RapportSousMenuComponent }
    ],
    
  },
  { path: '**', redirectTo: '' }
  
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FichierRoutingModule { }
