import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MaterialModule } from '../shared/material.module';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DocumentRoutingModule } from './document-routing';
import { from } from 'rxjs';
import { MatGridListModule } from '@angular/material/grid-list';
import { TypedocumentComponent } from './typedocument/typedocument.component';
import { AjouterTypedocumentComponent } from './typedocument/ajouter-typedocument/ajouter-typedocument.component';
import { EditerTypedocumentComponent } from './typedocument/editer-typedocument/editer-typedocument.component';
import { AllocationtypedocumentComponent } from './typedocument/allocationtypedocument/allocationtypedocument.component';
import { SignatureComponent } from './components/signature/signature.component';
import { DocumentComponent } from './components/document.component';

import { SelectAutocompleteModule } from 'mat-select-autocomplete';
import { AjoutDocumentComponent } from './components/ajout-document/ajout-document.component';
import { EditDocumentComponent } from './components/edit-document/edit-document.component';
import { ConsultDocumentComponent } from './components/consult-document/consult-document.component';
import { DocumentChargerComponent } from './components/documentCharger/documentCharger.component';
import { DocumentRecuComponent } from './components/documentRecu/documentRecu.component';
import { DetailSignatureComponent } from './components/detailSignature/detailSignature.component';
import { AjoutBlockchainComponent } from './components/ajout-blockchain/ajout-blockchain.component';
import { VerifyBlockchainComponent } from './components/verify-blockchain/verify-blockchain.component';

import { ApprobationComponent } from './components/approbation/approbation.component';
import { DocumentAppComponent } from './document-app.component';


@NgModule({
  declarations: [
    DocumentAppComponent,
    AjoutDocumentComponent,
    EditDocumentComponent,
    DocumentComponent,
     ConsultDocumentComponent,
     TypedocumentComponent,
     DocumentChargerComponent,
     DocumentRecuComponent,
     AjouterTypedocumentComponent,
     EditerTypedocumentComponent,
     AllocationtypedocumentComponent,
     SignatureComponent,
     DetailSignatureComponent,
     AjoutBlockchainComponent,
     VerifyBlockchainComponent,
     ApprobationComponent
    ],


  imports: [
    CommonModule,
    DocumentRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatGridListModule,
    HttpClientModule,
    SharedcomponentModule,
    SelectAutocompleteModule


  ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }],
})

export class DocumentModule { }
