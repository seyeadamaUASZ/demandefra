import { Component, OnInit, Input, Inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { ProduitsService } from '../../service/produits.service';
import { DemandeAutFraService } from '../../service/demandeAutFra.service';
import { CategorieService } from 'src/app/gestioncategorie/service/categorie.service';
import { StyleManagerService } from 'src/app/shared/style-manager.service';

@Component({
  selector: 'app-add-produits',
  templateUrl: './add-produits.component.html',
  styleUrls: ['./add-produits.component.scss']
})
export class AddProduitsComponent implements OnInit {

  status: any;
  donnee: any;
  categories: any;

  constructor(private produitsService: ProduitsService,
    private demandeAutFraService: DemandeAutFraService,
    private categorieService: CategorieService,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialogRef<AddProduitsComponent>) { }

  EditForm = this.formBuilder.group({
    id: [''],
    nature: [''],
    marque: [''],
    contenance: [''],
    typeemballage: [''],
    descriptionEtiquette: [''],
    etiquetteouemballage: [''],
    autFra: [''],
    categorie: [''],
    status: ['']
  });

  ngOnInit() {
    this.categorieService.allCategories().subscribe((data: any) => {
      this.categories = data.data;
    })
   }

  onSubmit() {
    if (!this.etiquetteouemballage) {
      this.translate.get("produit.selectAllFilesNotif").subscribe((res: string) => {
        this.notification.warn(res);
      });
      return;
    }
    if (this.EditForm.valid) {
      this.EditForm.value.status = 13;     
      this.EditForm.value.etiquetteouemballage = this.etiquetteouemballage;
      this.dialog.close(this.EditForm.value);
    } 
  }

  closeDialog() {
    this.dialog.close({status: false});
  }

  @ViewChild('fileetiquetteouemballage') fileetiquetteouemballage;

  etiquetteouemballage: File;

  addFileetiquetteouemballage() {
    this.fileetiquetteouemballage.nativeElement.click();
  }

  onFileAddedetiquetteouemballage() {
    this.etiquetteouemballage = this.fileetiquetteouemballage.nativeElement.files[0];
    const extension = this.etiquetteouemballage.name.split('.')[1].toLowerCase();
    if ('pdf' !== extension) {
      this.translate.get('produits.fileExtenxionNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.etiquetteouemballage = null;
      return;
    }
    if (this.etiquetteouemballage.size > 3000000) {
      this.translate.get('produits.fileSizeNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.etiquetteouemballage = null;
      return;
    }
  }
}
