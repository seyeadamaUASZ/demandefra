import { Component, Inject, OnInit, QueryList, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import * as FileSaver from 'file-saver';
import { AntenneService } from 'src/app/antenne/service/antenne.service';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import { InfosPayeurComponent } from 'src/app/paiement/components/infos-payeur/infos-payeur.component';
import { RegionService } from 'src/app/region/service/region.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { StyleManagerService } from 'src/app/shared/style-manager.service';
import { OptionPaiementComponent } from 'src/app/sharedcomponent/option-paiement/option-paiement.component';
import { DemandeAutFraService } from '../../service/demandeAutFra.service';
import { MotifrejetourenvoiService } from '../../service/motifrejetourenvoi.service';
import { AddDemandeAutFraComponent } from '../add-demandeAutFra/add-demandeAutFra.component';
import { ViewProduitComponent } from '../view-produit/view-produit.component';

@Component({
  selector: 'app-suivredemande',
  templateUrl: './suivredemande.component.html',
  styleUrls: ['./suivredemande.component.scss']
})
export class SuivredemandeComponent implements OnInit {
  result: any;
  donnee: any;
  dataSourceProduits: MatTableDataSource<any>;
  produits: any[] = [];
  demandeAutFra: any;
  antenneRegionaleDepartementale: any;
  region: any;
  openDialog: any;
  produit: any;
  status: any;
  historique: any;
  motifRejet: any;
  statut: any;

  displayedColumns = ['Nature', 'Marque', 'Contenance', 'Description étiquette', 'Numero FRA', 'Statut', 'Action'];
  @ViewChild(MatPaginator) paginator: QueryList<MatPaginator> = new QueryList<MatPaginator>();
  @ViewChild(MatSort) sort: MatSort;

  constructor(private demandeAutFraService: DemandeAutFraService,
    private motifService: MotifrejetourenvoiService,
    private antenneService: AntenneService,
    private regionService: RegionService,
    private fichierService: FichierService,
    private dialog: MatDialog,
    private router: Router, private formBuilder: FormBuilder,
    private dialogRef: MatDialog,
    private translate: TranslateService,
    private notification: NotificationService,
    private styleManager: StyleManagerService) { }

  data: any;

  searchForm = this.formBuilder.group({
    emaildemandeur: [''],
    numerodemandeautfra: ['']
  })

  EditForm = this.formBuilder.group({
    id: [''],
    raisonsociale: [''],
    adresseEntreprise: [''],
    emailEntreprise: [''],
    ninea: [''],
    registrecommerce: [''],
    statutJuridique: [''],
    telephoneEntreprise: [''],
    prenomResponsable: [''],
    nomResponsable: [''],
    civiliteResponsable: [''],
    adresseResponsable: [''],
    telephoneResponsable: [''],
    emailResponsable: [''],
    certificatanalysesproduit: [''],
    registrecommerceetcreditmobilier: [''],
    juridique: [''],
    processusfabrication: [''],
    copieninea: [''],
    cnipasseport: [''],
    objetDemande: [''],
    antenneRegionaleDepartementale: [''],
    region: [''],
    produits: [''],
    numdemande: [''],
    dateSoumission: [''],
    numeroFacture: [''],
    paiementManuel:[null],
    recuPaiment:[null],
    recuFileType:[null],
    poOwner: [''],
    status: [''],
    owner: [''],
    idLink: ['']
  });

  profilLibelle = localStorage.getItem('profileLibelle')
  
  theme;
  installTheme(themeName: string) {
    this.styleManager.setStyle('theme', themeName);
  }

  langue="fr";
  ngOnInit() {
    this.theme = 'pink-grey';
    this.installTheme(this.theme);
    this.donnee = this.data?.data;        
    this.motifService.getMotifRejet(this.donnee?.id, this.donnee?.status).subscribe((data: any) => {
      this.motifRejet = data?.data?.motif;
    })  

    this.langue = localStorage.getItem('langue') ? localStorage.getItem('langue') : 'fr';
      
      this.translate.setDefaultLang(this.langue);
      this.translate.use(this.langue);
  }
  load:boolean = false;
  onSumbit3() {
    this.load =  true;
    this.demandeAutFraService.suivreDemandeAutFra(this.searchForm.value)
      .subscribe((data: any) => {
        if (data.statut) {
          if (data.data.antenneRegionaleDepartementale && data.data.region) {
            this.antenneRegionaleDepartementale = data.data.antenneRegionaleDepartementale.nomAntenne;
            this.region = data.data.region.nomRegion;
          }
          this.donnee = data.data;
          this.statut = this.donnee.status;
          this.EditForm.setValue(this.donnee);
          this.dataSourceProduits = new MatTableDataSource(this.donnee.produits);
          this.motifService.getHistoriqueCommentaire(this.donnee.id, this.donnee.status).subscribe((data: any) => {
            this.historique = data.data;
          })
        } else {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.error(res);
          });
        }
        this.load = false;
      })
  }
  //comment old paiement
  payer(data) {
    
    // const dialog1 = this.dialogRef.open(InfosPayeurComponent, {
    //   width: '700px',
    //   data: { id: data.id, typeProcess: 'demandeAutFra', status: this.status }
    // }).afterClosed().subscribe(result => {
    //   if (result.typeProcess == 'demandeAutFra' && result.status == true) {}
    // });

    const dialog1 = this.dialogRef.open(OptionPaiementComponent,{
      width:'700px',
      data:data
    });

    dialog1.afterClosed().subscribe((resp)=>{
      if(resp.status){
        this.onSumbit3();
      }
    })
  }

  openCertificatAnalysesProduit(donnee) {
    let doc = donnee.certificatanalysesproduit;
    let blob = this.b64toBlob(doc, 'application/pdf', 512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openRegistreCommerce(donnee) {
    let doc = donnee.registrecommerceetcreditmobilier;
    let blob = this.b64toBlob(doc, 'application/pdf', 512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openJuridique(donnee) {
    let doc = donnee.juridique;
    let blob = this.b64toBlob(doc, 'application/pdf', 512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openProcessusFabrication(donnee) {
    let doc = donnee.processusfabrication;
    let blob = this.b64toBlob(doc, 'application/pdf', 512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openCopieNinea(donnee) {
    let doc = donnee.copieninea;
    let blob = this.b64toBlob(doc, 'application/pdf', 512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openCniPasseport(donnee) {
    let doc = donnee.cnipasseport;
    let blob = this.b64toBlob(doc, 'application/pdf', 512);
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

  previsualiser(data) {
    const dialog1 = this.dialogRef.open(ViewProduitComponent, {
			width: '2500px',
			data: { data: data, status: this.status }
		}).afterClosed().subscribe(result => {
		});
  }

  downloadFraPro(id) {
    this.fichierService.downloadAutFraPro(id).subscribe(resp => {
      this.saveFile(resp.body, "fichier:" + id, id);
    });
  }

  downloadFraDef(id) {
    this.fichierService.downloadAutFraDef(id).subscribe(resp => {
      this.saveFile(resp.body, "fichier:" + id, id);
    });
  }

  saveFile(data: any, filename?: string, extention?: any) {
    let blob = new Blob([data], { type: 'application/pdf' });
    FileSaver.saveAs(blob, filename);
  }

  openAddDemande(data) {
    const dialog1 = this.dialogRef.open(AddDemandeAutFraComponent, {
      width: '1500px',
      data: { data: data, openDialog: true }
    }).afterClosed().subscribe(result => {
    });
  }

  onClose() {
    this.router.navigateByUrl('/landing');
  }
}
