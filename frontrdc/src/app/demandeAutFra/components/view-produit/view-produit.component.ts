import { Component, OnInit, Inject, ViewChild, QueryList } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import * as fileSaver from 'file-saver';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { MotifrejetourenvoiService } from '../../service/motifrejetourenvoi.service';
import { ProduitsService } from '../../service/produits.service';
import { base64StringToBlob } from 'blob-util';
import { RapportanalyseService } from '../../service/rapportanalyse.service';

@Component({
  selector: 'app-view-produit',
  templateUrl: './view-produit.component.html',
  styleUrls: ['./view-produit.component.scss']
})
export class ViewProduitComponent implements OnInit {
  result: any;
  donnee: any;
  openDialog: any;
  produit: any;
  status: any;
  categorie: any;
  motifRejet: any;
  rapportanalyse: any;
  statut: any;
  stats: any;

  constructor(private produitsService: ProduitsService,
    private analyseService: RapportanalyseService,
    private motifService: MotifrejetourenvoiService,
    private dialog: MatDialog,
    private router: Router,
    private formBuilder: FormBuilder,
    private dialogRef: MatDialog,
    private translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notification: NotificationService) { }

    EditForm = this.formBuilder.group({
      id: [''],
      nature: [''],
      marque: [''],
      contenance: [''],
      typeemballage: [''],
      descriptionEtiquette: [''],
      etiquetteouemballage: [],
      autFra: [''],
      categorie: [''],
      status: ['']
    });

    profilLibelle = localStorage.getItem('profileLibelle')

  ngOnInit() {
    this.donnee = this.data.data;
    this.categorie = this.data.data.categorie.nom;
    this.EditForm.setValue(this.donnee);
    if(this.profilLibelle != null){
      this.historique();    
      this.statut = this.data.demande.data.status;    
    } else {
      this.motif();
      this.statut = this.data.data.status;  
      this.analyseService.getRapportAnalayseSuivi(this.donnee.id).subscribe((data: any) => {
        this.rapportanalyse = data.data;
      });
    }       
  }

  historique() {
    this.motifService.getHistoriqueCommentaire(this.donnee.id, this.donnee.status).subscribe((data: any) => {
      this.motifRejet = data.data;
    });

    this.analyseService.getRapportAnalayseParProduit(this.donnee.id).subscribe((data: any) => {
      this.rapportanalyse = data.data;
    });
  }

  motif() {
    this.motifService.getMotifRejet(this.donnee.id, this.donnee.status).subscribe((data: any) => {
      this.motifRejet = data.data;
    });
  }

  openEtiquetteouemballage(){
    let doc = this.donnee?this.donnee.etiquetteouemballage:this.produit.etiquetteouemballage;
    doc = doc.split(',')[1];
    const blob = base64StringToBlob(doc, 'application/pdf');
    let fileURL = URL.createObjectURL(new Blob([blob] , {type:'application/pdf'}));
    window.open(fileURL);
  }

  openAnalyse() {
    let doc = this.rapportanalyse?this.rapportanalyse.analyse:this.rapportanalyse.analyse;
    let blob = this.b64toBlob(doc,'application/pdf',512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  b64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    sliceSize = sliceSize || 512;

    const byteCharacters = atob(b64Data);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      const slice = byteCharacters.slice(offset, offset + sliceSize);

      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);

      byteArrays.push(byteArray);
    }

    const blob = new Blob(byteArrays, { type: contentType });
    return blob;
  }
}
