import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentComponent } from './components/main-content/main-content.component';
import { ParametrageAppComponent } from './parametrage-app.component';
import { HomeDesignComponent } from './components/home-design/home-design.component';
import { MenuComponent } from '../configuration/components/param-menu/menu/menu.component';
import { ActionComponent } from '../configuration/components/param-menu/action/action.component';

const routes: Routes = [
  {
    path: '', component: ParametrageAppComponent,
    children: [
      // { path: ':id', component: DetailCriterePwdComponent},
      //{ path: 'joindre/:id', canActivate: [AuthGuard], component: ListFormulaireComponent},
      // { path: 'contenueQrcode/:id', component: ContenuQrcodeComponent},
      { path: '', component: MainContentComponent },
      { path: '', component: HomeDesignComponent },
      { path: 'menu', component: MenuComponent },
      { path: 'action', component: ActionComponent },
      
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ParametrageRoutingModule { }
