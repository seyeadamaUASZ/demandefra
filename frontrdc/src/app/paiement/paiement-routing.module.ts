import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentPaiementComponent } from './components/main-content-paiement/main-content-paiement.component';
import { PaiementRedirectComponent } from './components/paiement-redirect/paiement-redirect.component';
import { PaiementAppComponent } from './paiement-app.component';
import { MestransactionsComponent } from './components/mestransactions/mestransactions.component';

const routes: Routes = [
  {
    path: '', component: PaiementAppComponent,
    children: [
      { path: '', component: MainContentPaiementComponent },
      { path: 'mesTransactions', component: MestransactionsComponent },

    ],

  },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaiementRoutingModule { }
