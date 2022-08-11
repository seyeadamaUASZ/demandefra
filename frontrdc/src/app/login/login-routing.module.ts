import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AutreConnexionComponent } from './components/autre-connexion/autre-connexion.component';
import { ForgetPwdComponent } from './components/forget-pwd/forget-pwd.component';
import { NewPwdComponent } from './components/new-pwd/new-pwd.component';
import { PremiereConnectComponent } from './components/premiere-connect/premiere-connect.component';
import { LoginComponent } from './login.component';


const routes: Routes = [
  {
    path: '', component: LoginComponent
    
  },
  //{ path: 'newPwd/:reset', component: NewPwdComponent },
  { path: 'premiereConnect', component: PremiereConnectComponent },
  { path: 'forgetPwd', component: ForgetPwdComponent },
  { path: 'newPwd', component: NewPwdComponent },
  { path: 'autre', component: AutreConnexionComponent }


];



@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }
