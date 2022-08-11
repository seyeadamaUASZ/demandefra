import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddDemandeAutFraComponent } from './demandeAutFra/components/add-demandeAutFra/add-demandeAutFra.component';
import { SuivredemandeComponent } from './demandeAutFra/components/suivredemande/suivredemande.component';
import { AppPublishedComponent } from './login/components/app-published/app-published.component';
import { LandingComponent } from './login/components/landing/landing.component';
import { AuthGuard } from './login/services/auth/auth.guard';
import { PaiementRedirectComponent } from './paiement/components/paiement-redirect/paiement-redirect.component';
import { StatusPaiementComponent } from './status-paiement/status-paiement.component';

const routes: Routes = [
  { path: 'login',loadChildren: './login/login.module#LoginModule' },
  { path: 'landing',loadChildren: './login/login.module#LoginModule',component:LandingComponent },
  { path: 'applicationpubliees',loadChildren: './login/login.module#LoginModule',component:AppPublishedComponent },
  { path: 'home',canActivate: [AuthGuard], loadChildren: './home/home.module#HomeModule' },
  { path: 'application',canActivate: [AuthGuard], loadChildren: './application/application.module#ApplicationModule' },
  { path: 'utilisateur',canActivate: [AuthGuard], loadChildren: './utilisateur/utilisateur.module#UtilisateurModule' },
  { path: 'workflow', canActivate: [AuthGuard], loadChildren: './workflow/workflow.module#WorkflowModule' },
  { path: 'parametrage', canActivate: [AuthGuard], loadChildren: './parametrage/parametrage.module#ParametrageModule' },
  { path: 'formulaire', canActivate: [AuthGuard], loadChildren: './formulaire/formulaire.module#FormulaireModule' },
  { path: 'fichier', canActivate: [AuthGuard], loadChildren: './fichier/fichier.module#FichierModule' },
  { path: 'procedures', canActivate: [AuthGuard], loadChildren: './procedures/procedures.module#ProceduresModule' },
  { path: 'exception',loadChildren: './exception/exception.module#ExceptionModule' },
  { path: 'paiement/redirect',loadChildren: './paiement/paiement.module#PaiementModule', component: PaiementRedirectComponent },
  { path: 'paiement', canActivate: [AuthGuard], loadChildren: './paiement/paiement.module#PaiementModule' },
  { path: 'qrcode', canActivate: [AuthGuard], loadChildren: './qrcode/qrcode.module#QrcodeModule' },
  { path: 'document',canActivate: [AuthGuard], loadChildren: './documents/document.module#DocumentModule' },
  { path: 'configuration', canActivate: [AuthGuard], loadChildren: './configuration/configuration.module#ConfigurationModule' },
  { path: 'inscription', loadChildren: './inscription/inscription.module#InscriptionModule'},
  { path: 'generateurotp', loadChildren: './generateurotp/generateurotp.module#GenerateurotpModule'},
  { path: 'formulairev2', canActivate: [AuthGuard], loadChildren: './formulaire-v2/formulaire-v2.module#FormulaireV2Module' },
  { path: 'demande', loadChildren: './demandeAutFra/demandeAutFra.module#DemandeAutFraModule', component: AddDemandeAutFraComponent },
  { path: 'demandeatraiterchefbureau', canActivate: [AuthGuard], loadChildren: './demandeencours/demandeencours.module#DemandeencoursModule' },
  { path: 'demandeatraiterchefdivision', canActivate: [AuthGuard], loadChildren: './demandeatraiter/demandeatraiter.module#DemandeatraiterModule' },
  { path: 'demandechefdivision', canActivate: [AuthGuard], loadChildren: './demandeatraiter/demandeatraiter.module#DemandeatraiterModule' },
  { path: 'demanderejetees', canActivate: [AuthGuard], loadChildren: './demanderenvoyees/demanderenvoyees.module#DemanderenvoyeesModule' },
  { path: 'demandeautfradelivrepro', canActivate: [AuthGuard], loadChildren: './demandeautfradelivrepro/demandeautfradelivrepro.module#DemandeautfradelivreproModule' },
  { path: 'demandeatraiteranac', canActivate: [AuthGuard], loadChildren: './demandeatraiteranac/demandeatraiteranac.module#DemandeatraiteranacModule' },
  { path: 'demandetraiteranac', canActivate: [AuthGuard], loadChildren: './demandetraiteranac/demandetraiteranac.module#DemandetraiteranacModule' },
  { path: 'demandeterminee', canActivate: [AuthGuard], loadChildren: './demandeterminee/demandeterminee.module#DemandetermineeModule' },
  {path:  'gestioncategorie', canActivate: [AuthGuard], loadChildren: './gestioncategorie/gestioncategorie.module#GestioncategorieModule'},
  { path: 'demandevalidee', canActivate: [AuthGuard], loadChildren: './demandevalidee/demandevalidee.module#DemandevalideeModule'},
  { path: 'suivredemande',loadChildren:'./demandeAutFra/demandeAutFra.module#DemandeAutFraModule',component:SuivredemandeComponent},
  { path: 'mesdemandes', canActivate: [AuthGuard], loadChildren: './mesdemande/mesdemande.module#MesdemandeModule' },
  { path: 'mesdemandeacceptees', canActivate: [AuthGuard], loadChildren: './mesdemandeacceptees/mesdemandeacceptees.module#MesdemandeaccepteesModule' },
  { path: 'mesdemanderejetees', canActivate: [AuthGuard], loadChildren: './mesdemanderejetees/mesdemanderejetees.module#MesdemanderejeteesModule' },
  { path: 'region', canActivate: [AuthGuard], loadChildren: './region/region.module#RegionModule'},
  { path: 'antenneregionale', canActivate: [AuthGuard], loadChildren: './antenne/antenne.module#AntenneModule'},
  { path: 'soumettredemande', canActivate: [AuthGuard], loadChildren: './soumettredemande/soumettredemande.module#SoumettredemandeModule'},
  { path: 'statistiques', canActivate: [AuthGuard], loadChildren: './statistiques/statistiques.module#StatistiquesModule'},
  { path: 'demandeenbrouillon', canActivate: [AuthGuard], loadChildren: './brouillon/brouillon.module#BrouillonModule'},
  { path: 'demandeacorriger', canActivate: [AuthGuard], loadChildren: './demandeacorriger/demandeacorriger.module#DemandeacorrigerModule'},
  { path: 'demandeencoursdcsc', canActivate: [AuthGuard], loadChildren: './demandecours/demandecours.module#DemandecoursModule'},
  { path: 'demandeapayer', canActivate: [AuthGuard], loadChildren: './demandeapayer/demandeapayer.module#DemandeapayerModule'},
  { path: 'demandeautfraprodelivre', canActivate: [AuthGuard], loadChildren: './autprodelivre/autprodelivre.module#AutprodelivreModule'},
  { path: 'demandedefinitive', canActivate: [AuthGuard], loadChildren: './autdefdelivre/autdefdelivre.module#AutdefdelivreModule'},
  { path: 'demanderejeteesdscs', canActivate: [AuthGuard], loadChildren: './demanderejetees/demanderejetees.module#DemanderejeteesModule'},
  { path: 'demanderenvoyeeschefdivision', canActivate: [AuthGuard], loadChildren: './demanderenvoyeeschefdivision/demanderenvoyeeschefdivision.module#DemanderenvoyeeschefdivisionModule'},
  { path: 'succes', component: StatusPaiementComponent },
  { path: '**', redirectTo: '/landing', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{
    scrollPositionRestoration:'enabled',
    anchorScrolling:'enabled',
    useHash:true
  })],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }
