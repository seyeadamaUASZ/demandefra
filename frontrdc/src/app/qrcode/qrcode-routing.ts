import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import{ QrcodeAppComponent} from 'src/app/qrcode/qrcode-app-component'
import { from } from 'rxjs';
import { QrcodeComponent } from './components/qrcode.component';
import { AuthGuard } from '../login/services/auth/auth.guard';
import { QrcodeGenereComponent } from './components/qrcode-genere/qrcode-genere.component';


const routes: Routes = [
  {
    path: '', component: QrcodeAppComponent, 
    children: [{ path: '', component: QrcodeComponent }, 
    { path: 'genere', component: QrcodeGenereComponent },
    ],
     
  },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QrcodeRoutingModule { }
