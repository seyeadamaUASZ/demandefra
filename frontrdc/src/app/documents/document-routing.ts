import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DocumentComponent } from './components/document.component';
import { TypedocumentComponent } from './typedocument/typedocument.component';
import { AllocationtypedocumentComponent } from './typedocument/allocationtypedocument/allocationtypedocument.component';
import { DocumentChargerComponent } from './components/documentCharger/documentCharger.component';
import { DocumentRecuComponent } from './components/documentRecu/documentRecu.component';
import { DocumentAppComponent } from './document-app.component';




const routes: Routes = [
  {
    path: '', component: DocumentAppComponent,
    children: [
      { path: '', component: DocumentComponent },
      { path: 'typedocument', component: TypedocumentComponent},
      { path: 'typedocument/allocation', component: AllocationtypedocumentComponent},
      { path: 'documentCharger', component: DocumentChargerComponent},
      { path: 'documentRecu', component: DocumentRecuComponent},
    ],

  },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentRoutingModule { }
