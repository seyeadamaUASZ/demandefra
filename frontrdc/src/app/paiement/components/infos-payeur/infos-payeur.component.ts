import { Component, OnInit, Inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { InscriptionService } from 'src/app/inscription/services/inscription.service';
import { PaiementService } from '../../service/paiement.service';
import { DemandeAutFraService } from 'src/app/demandeAutFra/service/demandeAutFra.service';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { ProduitsService } from 'src/app/demandeAutFra/service/produits.service';

@Component({
  selector: 'app-infos-payeur',
  templateUrl: './infos-payeur.component.html',
  styleUrls: ['./infos-payeur.component.scss']
})
export class InfosPayeurComponent implements OnInit {
  url;

  constructor(public router: Router,
    private route: ActivatedRoute,
    private formbuild: FormBuilder,
    private _snackBar: MatSnackBar,
    private translate: TranslateService,
    private inscriptionService: InscriptionService,
    public dialogRef: MatDialog,
    private notification: NotificationService,
    private paiementService: PaiementService,
    private demandeService: DemandeAutFraService,
    private produitService: ProduitsService,
    private userService: UserService,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

    factureForm = this.formbuild.group({
      idFacture: [''],
      montantFacture: [''],
      nomPayeur: [''],
      prenomPayeur: [''],
      usernamePayeur: [''],
    });

  ngOnInit() {
    this.demandeService.getDemandeById(this.data.id).subscribe((dataDemande: any) => {
      this.produitService.getByIdLink(dataDemande.data.id).subscribe((dataProduits: any) => {        
        let total = 0;
        for(var i = 0; i < dataProduits.data.length; i++) {
          if(dataProduits.data[i].categorie.prix) {
            total += dataProduits.data[i].categorie.prix;
            if(total > 500000 && total <= 1000000) {
              total = total - ((total * 10) / 100);
            }
            if(total > 1000000) {
              total = total - ((total * 15) / 100);
            }
            this.factureForm.setValue({
              idFacture: dataDemande.data.numeroFacture,
              montantFacture: total,
              nomPayeur: dataDemande.data.nomResponsable,
              prenomPayeur: dataDemande.data.prenomResponsable,
              usernamePayeur: null,
            });
          }           
        }
      })      
    })
  }

  get f() { return this.factureForm.controls; }

  onSubmit() {
    if (this.factureForm.valid) {
      this.paiementService.payer(this.factureForm.value).subscribe(data => {        
        if (data.statut) {
          this.url = data.data;
          this.closeDialog();
          window.open(this.url,'_blank');
          this.closeDialog();
        }
      })
    }
    else {
      this.translate.get('invalid-form').subscribe((res: string) => {
        this.notification.warn(res);
      });
    }
  }
  
  closeDialog() {
    this.dialogRef.closeAll();
  }
}
