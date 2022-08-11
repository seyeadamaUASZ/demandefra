import { DemandeAutFraService } from '../../service/demandeAutFra.service';
import { Component, OnInit, Inject, ViewChild, QueryList } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import * as fileSaver from 'file-saver';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { AddMotifrejetourenvoiComponent } from '../add-motifrejetourenvoi/add-motifrejetourenvoi.component';
import { SignerDocumentComponent } from '../signer-document/signer-document.component';
import { MotifrejetourenvoiService } from '../../service/motifrejetourenvoi.service';
import { AntenneService } from 'src/app/antenne/service/antenne.service';
import { RegionService } from 'src/app/region/service/region.service';
import { AddcommentairevaliderComponent } from '../addcommentairevalider/addcommentairevalider.component';
import { ViewProduitComponent } from '../view-produit/view-produit.component';
import { AddRapportanalyseComponent } from '../add-rapportanalyse/add-rapportanalyse.component';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import * as FileSaver from 'file-saver';
import { ProduitsService } from '../../service/produits.service';
import { StyleManagerService } from 'src/app/shared/style-manager.service';
import { ValidationComponent } from '../validation/validation.component';

@Component({
  selector: 'app-view-demandeAutFra',
  templateUrl: './view-demandeAutFra.component.html',
  styleUrls: ['./view-demandeAutFra.component.scss']
})
export class ViewDemandeAutFraComponent implements OnInit {
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
  motif: any;
  statut: any;
  active: boolean = false;

  displayedColumns = ['Nature', 'Marque', 'Contenance', 'Numero FRA', 'Statut', 'Action'];
  @ViewChild(MatPaginator) paginator: QueryList<MatPaginator> = new QueryList<MatPaginator>();
  @ViewChild(MatSort) sort: MatSort;

  constructor(private demandeAutFraService: DemandeAutFraService, 
    private motifService: MotifrejetourenvoiService,
    private fichierService: FichierService,
    private produitService: ProduitsService,
    private dialog: MatDialog,
    private router: Router, private formBuilder: FormBuilder,
    private dialogRef: MatDialog,
    private dialogRefview:  MatDialogRef<ViewDemandeAutFraComponent>,
    private translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notification: NotificationService,
    private styleManager: StyleManagerService) { }

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

  profilLibelle = localStorage.getItem('profileLibelle');
  theme;

  installTheme(themeName: string) {    
    this.styleManager.setStyle('theme', themeName);
  }

  ngOnInit() {
    this.theme = 'pink-grey';
    this.installTheme(this.theme);
    this.antenneRegionaleDepartementale = this.data.data.antenneRegionaleDepartementale.nomAntenne;
    this.region = this.data.data.region.nomRegion;
    this.motifService.getHistoriqueCommentaire(this.data.data.id, this.data.data.status).subscribe((data: any) => {
      this.historique = data.data;
    })
    this.motifService.getMotifRenvoiChefDivision(this.data.data.id).subscribe((data: any) => {
      this.motif = data.data;
    })
    this.statut = this.data.data.status;
    this.loadDonnee();    
  }

  loadDonnee() {
    this.openDialog = this.data.openDialog;
    if(this.openDialog){
      this.donnee = this.data.data; 
      this.EditForm.setValue(this.donnee);
      this.listeProduits();
                 
    }    
  }

  openCertificatAnalysesProduit(){
    let doc = this.donnee?this.donnee.certificatanalysesproduit:this.demandeAutFra.certificatanalysesproduit;
    let blob = this.b64toBlob(doc,'application/pdf',512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openRegistreCommerce(){
    let doc = this.donnee?this.donnee.registrecommerceetcreditmobilier:this.demandeAutFra.registrecommerceetcreditmobilier;
    let blob = this.b64toBlob(doc,'application/pdf',512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openJuridique(){
    let doc = this.donnee?this.donnee.juridique:this.demandeAutFra.juridique;
    let blob = this.b64toBlob(doc,'application/pdf',512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openProcessusFabrication(){
    let doc = this.donnee?this.donnee.processusfabrication:this.demandeAutFra.processusfabrication;
    let blob = this.b64toBlob(doc,'application/pdf',512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openCopieNinea(){
    let doc = this.donnee?this.donnee.copieninea:this.demandeAutFra.copieninea;
    let blob = this.b64toBlob(doc,'application/pdf',512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openCniPasseport(){
    let doc = this.donnee?this.donnee.cnipasseport:this.demandeAutFra.cnipasseport;
    let blob = this.b64toBlob(doc,'application/pdf',512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  openRecuPaiement(){
    let doc = this.donnee?this.donnee.recuPaiment:this.demandeAutFra.recuPaiment;
    let type = this.donnee?this.donnee.recuFileType:this.demandeAutFra.recuFileType;
    type = type=="pdf"?'application/pdf':`image/${type}`;
    let blob = this.b64toBlob(doc,type,512);
    let fileURL = window.URL.createObjectURL(blob);
    window.open(fileURL);
  }

  rejetRecu(){
    let id = this.donnee?this.donnee.id:this.demandeAutFra.id;
    this.demandeAutFraService.rejetRecu(id).subscribe((data)=>{
      if(data.statut){
        this.notification.success('demandeautfra.rejetok');
      }else{
        this.notification.warn('demandeautfra.echecrejet');
      }
      if(this.donnee){
        this.dialogRefview.close({status:true});
      }
    })
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

  rejetAutProvisoire(): void {
    const dialog = this.dialogRef.open(AddMotifrejetourenvoiComponent, {
			width: '700px',
      data: { data: this.data },
			disableClose: true
		}).afterClosed().subscribe(result => {
		});
  }

  delivrerAutProvisoire(): void {
    const dialog1 = this.dialogRef.open(SignerDocumentComponent, {
			width: '800px',
			data: { data: this.data }
		}).afterClosed().subscribe(result => {
      this.dialogRefview.close();
      location.reload();
      //this.router.navigate(['/demandeautfradelivrepro']);
		});
  }

  autoriserAutFraDefinitive(data): void {
    const dialog1 = this.dialogRef.open(SignerDocumentComponent, {
			width: '800px',
			data: { data: this.data, produit: data }
		}).afterClosed().subscribe(result => {
      this.listeProduits();
		});
  }

  refuserAutFraDefinitive(data): void {
    const dialog1 = this.dialogRef.open(AddMotifrejetourenvoiComponent, {
      width: '800px',
			data: { data: this.data, produit: data }
    }).afterClosed().subscribe(result => {
      this.listeProduits();
		});
  }

  rapportAnalyse(data): void {
    const dialog1 = this.dialogRef.open(AddRapportanalyseComponent, {
      width: '800px',
			data: { data: this.data, produit: data }
    }).afterClosed().subscribe(result => {
      this.listeProduits();
		});
  }

  listeProduits() {
    this.produitService.getByIdLink(this.data.data.id).subscribe((data: any) => {
      this.dataSourceProduits = new MatTableDataSource(data.data);
      this.produits = data.data;
      
      for(let i = 0; i < this.produits.length; i++) {
        if((this.produits[i].status == 13) && this.profilLibelle == 'agent lanac') {
          this.active = true;            
        } else if((this.produits[i].status == 14) && this.profilLibelle == 'chef division') {
          this.active = true;
        } else {
          this.active = false;
        }
      } 
    });
  }

  terminer() {
    if(this.profilLibelle == 'chef division') {
      this.demandeAutFraService.updateTaskDemandeAutFra(this.data.data.id, 7).subscribe((data) => {});
      location.reload();
    } else if(this.profilLibelle == 'agent lanac') {
      this.demandeAutFraService.updateTaskDemandeAutFra(this.data.data.id, 6).subscribe((data) => {});
      location.reload();
    }    
  }

  openValiderChefDeBureau(){
    const dialog1 = this.dialogRef.open(ValidationComponent,{
      width:'700px',
      height:'250px',
      data:{ data: this.data},
    }).afterClosed().subscribe(result=>{
    });
  }

  openRenvoiChefDeBureau(){
    const dialog1 = this.dialogRef.open(AddcommentairevaliderComponent,{
      width:'700px',
      height:'250px',
      data:{ data: this.data},
    }).afterClosed().subscribe(result=>{
    });
  }

  openRenvoiChefDivision() {
    const dialog1 = this.dialogRef.open(AddcommentairevaliderComponent,{
      width:'700px',
      height:'250px',
      data:{ data: this.data},
    }).afterClosed().subscribe(result=>{
    });
  }

  previsualiser(data) {   
    const dialog1 = this.dialogRef.open(ViewProduitComponent, {
			width: '2500px',
			data: { data: data, demande: this.data }
		}).afterClosed().subscribe(result => {
		});
  }

  retirerAutFraDefinitive(data) {
    this.produitService.updateTaskProduits(data.id, 15).subscribe(data => {
      this.listeProduits();
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
}
