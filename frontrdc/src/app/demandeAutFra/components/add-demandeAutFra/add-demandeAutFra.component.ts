import { Component, OnInit, Inject, ViewChild, QueryList } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { DemandeAutFraService } from '../../service/demandeAutFra.service';
import { AntenneService } from 'src/app/antenne/service/antenne.service';
import { RegionService } from 'src/app/region/service/region.service';
import { AddProduitsComponent } from '../add-produits/add-produits.component';
import { UserService } from 'src/app/utilisateur/services/user.service';
import Swal from 'sweetalert2';
import { StyleManagerService } from 'src/app/shared/style-manager.service';
import { MotifrejetourenvoiService } from '../../service/motifrejetourenvoi.service';

@Component({
  selector: 'app-add-demandeAutFra',
  templateUrl: './add-demandeAutFra.component.html',
  styleUrls: ['./add-demandeAutFra.component.scss']
})
export class AddDemandeAutFraComponent implements OnInit {
  status: any;
  donnee: any;
  dataSourceProduits: MatTableDataSource<any>;
  produits: any[] = [];
  antennes: any;
  regions: any;
  openDialog: any;
  user: any;
  url: boolean = true;
  statut: any;
  historique: any;

  displayedColumns = ['Nature', 'Marque', 'Contenance', 'Type emballage', 'Description étiquette', 'Action'];
  @ViewChild(MatPaginator) paginator: QueryList<MatPaginator> = new QueryList<MatPaginator>();
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('produit') produit;

  constructor(
    private demandeAutFraService: DemandeAutFraService,
    private antenneService: AntenneService,
    private regionService: RegionService,
    private userService: UserService,
    private motifService: MotifrejetourenvoiService,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<AddDemandeAutFraComponent>,
    private styleManager: StyleManagerService) {
  }

  EditForm = this.formBuilder.group({
    id: [''],
    raisonsociale: ['',Validators.required],
    adresseEntreprise: [''],
    emailEntreprise: ['',Validators.required],
    ninea: ['',Validators.required],
    registrecommerce: ['', Validators.required],
    statutJuridique: ['',Validators.required],
    telephoneEntreprise: ['',Validators.required],
    prenomResponsable: [''],
    nomResponsable: [''],
    civiliteResponsable: [''],
    adresseResponsable: [''],
    telephoneResponsable: [''],
    emailResponsable: ['',Validators.required],
    certificatanalysesproduit: [''],
    registrecommerceetcreditmobilier: [''],
    juridique: [''],
    processusfabrication: [''],
    copieninea: [''],
    cnipasseport: [''],
    objetDemande: ['Demande Autorisation FRA'],
    antenneRegionaleDepartementale: [],
    region: [],
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

  profileLibelle = localStorage.getItem('profileLibelle');

  theme;
  installTheme(themeName: string) {
    this.styleManager.setStyle('theme', themeName);
  }

  ngOnInit() {
    this.theme = 'pink-grey';
    this.installTheme(this.theme);
    this.url = (this.router.url == "/soumettredemande" ||
     this.router.url == "/demandeenbrouillon" || 
     this.router.url == "/demandeacorriger" || 
     this.router.url == '/demanderenvoyeeschefdivision' ||
      this.router.url == "/suivredemande") ? false : true;  
    if(this.profileLibelle == "chef antenne") {
      this.getInfoAgentAntenne();
    }
    if(this.profileLibelle == "demandeur") {
      this.getInfoDemandeur();
    }
    this.antenneService.getAllAntenne().subscribe((data: any) => {
      this.antennes = data.data;
    })

    this.regionService.getAllRegion().subscribe((data: any) => {
      this.regions = data.data;
    })

    this.openDialog = this.data.openDialog;
    if (this.openDialog) {
      this.donnee = this.data.data;
      this.certificatanalysesproduit = this.donnee?this.donnee.certificatanalysesproduit:this.donnee.certificatanalysesproduit;
      this.registrecommerceetcreditmobilier = this.donnee?this.donnee.registrecommerceetcreditmobilier:this.donnee.registrecommerceetcreditmobilier;
      this.juridique = this.donnee?this.donnee.juridique:this.donnee.juridique;
      this.processusfabrication = this.donnee?this.donnee.processusfabrication:this.donnee.processusfabrication;
      this.copieninea = this.donnee?this.donnee.copieninea:this.donnee.copieninea;
      this.cnipasseport = this.donnee?this.donnee.cnipasseport:this.donnee.cnipasseport;
      this.EditForm.setValue(this.donnee);
      this.produits = this.donnee.produits;
      this.dataSourceProduits = new MatTableDataSource(this.produits);
      this.statut = this.donnee.status;
    }  
  }

  openDialogAddProduits(produit): void {
    const dialog = this.dialog.open(AddProduitsComponent, {
      width: '700px',
      data: { produit: produit, user: this.user },
      disableClose: true
    }).afterClosed().subscribe(result => {
      if (result) {
        const file = result.etiquetteouemballage;
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          result.etiquetteouemballage = reader.result;
          this.produits.push(result);
          this.dataSourceProduits = new MatTableDataSource(this.produits);
        }
      }
    });
  }

  onChange() {
    let rate;
    this.demandeAutFraService.getAntennesByRegion(this.EditForm.value.region.id).subscribe((data: any) => {
      rate = data.data;
      this.antennes = rate;
    })
  }

  getInfoAgentAntenne() {
    this.userService.detailUser(localStorage.getItem('username')).subscribe((data: any) => {
      this.user = data.data;
      this.EditForm.controls['emailResponsable'].setValue(this.user.utiEmail);
    })
  }

  getInfoDemandeur() {
    this.userService.detailUser(localStorage.getItem('username')).subscribe((data: any) => {
      this.user = data.data;
      this.EditForm.controls['prenomResponsable'].setValue(this.user.utiPrenom);
      this.EditForm.controls['nomResponsable'].setValue(this.user.utiNom);
      this.EditForm.controls['emailResponsable'].setValue(this.user.utiEmail);
      this.EditForm.controls['telephoneResponsable'].setValue(this.user.utiTelephone);
      this.EditForm.controls['adresseResponsable'].setValue(this.user.utiAdresse);
    })
  }
  pLoad = false;
  onSubmit(statusDemande) {
    if(this.EditForm.invalid){
      this.translate.get("demandeautfra.remplirChampsObligatoires").subscribe((res: string) => {
        this.notification.warn(res);
      });
      return;
    }
    this.EditForm.value.objetDemande = "Demande Autorisation FRA";
    this.EditForm.value.produits = this.produits;
    this.EditForm.value.status = statusDemande;
    if(this.profileLibelle == 'chef antenne' || this.profileLibelle == 'demandeur') {
      this.EditForm.value.poOwner = localStorage.getItem('profil');
      this.EditForm.value.owner = localStorage.getItem('id');
    }
    console.log(statusDemande);
    if (statusDemande == 1) {
      this.EditForm.value.numeroFacture = null;
      
    } else {
      if (!this.certificatanalysesproduit || !this.registrecommerceetcreditmobilier || !this.juridique) {
        this.translate.get("demandeautfra.selectAllFilesNotif").subscribe((res: string) => {
          this.notification.warn(res);
        });
        return;
      }
    }
    this.pLoad= true;
    this.demandeAutFraService.createDemandeAutFra(this.EditForm.value, this.certificatanalysesproduit, this.registrecommerceetcreditmobilier, this.juridique, this.processusfabrication, this.copieninea, this.cnipasseport).subscribe((data: any) => {
      if (data.statut) {
        this.pLoad=false;
        switch (statusDemande) {
          case 1:
            this.translate.get('demande.brouillon').subscribe(data=>{
              this.notification.success(data);
            })
            if(this.url == true) {
              this.router.navigate(['/landing']);
             
            } if (!this.url && this.router.url=="/suivredemande"){
              this.closeDialog();

              this.router.navigate(['/landing']);

            } else {
              this.closeDialog();
              this.router.navigate(['/demandeenbrouillon']);
            }            
            break;
          case 11:
            this.userService.getUserByEmail(this.EditForm.value.emailResponsable).subscribe((dataUser: any) => {
              if (dataUser.data === null) {                
                Swal.fire({
                  icon: 'success',
                  title: 'Votre demande a été soumise avec succès.',
                  text: 'Un mail vous est envoyé à l’adresse indiquée. Pour pouvoir payer les frais et suivre votre dossier, veuillez utiliser ce numéro de dossier N˚ ' + data.data.numdemande + '\n \n \n \n',
                  confirmButtonText: 'OUI',
                  confirmButtonColor: "GREEN",
                }).then((result) => {
                  if (result.isConfirmed) {
                    Swal.fire({
                      title: 'Information',
                      text: 'Souhaiteriez-vous disposer d’un compte ?',
                      icon: 'info',
                      showCancelButton: true,
                      confirmButtonText: 'Oui',
                      confirmButtonColor: 'green',
                      cancelButtonColor: "red",
                      cancelButtonText: 'Non'
                    }).then((result) => {
                      if (result.value) {
                        localStorage.setItem('numero', data.data.numdemande);
                        this.router.navigate(['/inscription']);
                      } else if (result.dismiss === Swal.DismissReason.cancel) {
                        this.router.navigate(['/landing']);
                      }
                    });
                  } else if (result.isDenied) {
                    Swal.fire('Changes are not saved', '', 'info')
                  }
                })
              } else {
                const profile = localStorage.getItem('profile');
                console.log('status 11 *****************');
                if(profile === '7' )  { 
                  Swal.fire({
                    title: 'Votre demande a été corrigé avec succès.',
                    text: 'Un mail vous est envoyé à l’adresse indiquée. Pour pouvoir suivre votre dossier, veuillez utiliser ce numéro de dossier N˚ ' + data.data.numdemande + '\n \n \n \n',
                    icon: 'success',
                    confirmButtonColor: "GREEN",
                    confirmButtonText: 'OUI',
                  }).then((result) => {
                    if (result.value) {
                      if(this.profileLibelle == "demandeur") {
                        this.router.navigate(['/demandeencoursdcsc']); 
                      } else {
                        this.router.navigate(['/landing']); 
                      }                    
                      this.closeDialog();
                    }
                  });
                  return;
                  }          
                Swal.fire({
                  title: 'Votre demande a été soumise avec succès.',
                  text: 'Un mail vous est envoyé à l’adresse indiquée. Pour pouvoir payer les frais et suivre votre dossier, veuillez utiliser ce numéro de dossier N˚ ' + data.data.numdemande + '\n \n \n \n',
                  icon: 'success',
                  confirmButtonColor: "GREEN",
                  confirmButtonText: 'OUI',
                }).then((result) => {
                  if (result.value) {
                    if(this.url == true) {
                      this.router.navigate(['/landing']);
                    } else {
                      this.router.navigate(['/demandeapayer']);
                    }       
                  }
                });
              }
            })            
            break;
          case 2:
            this.demandeAutFraService.getDemandeById(this.data.data.id).subscribe((res: any) => {
              this.EditForm.value.poOwner = res.poOwner;
              this.EditForm.value.owner = res.owner;
            });
            this.userService.getUserByEmail(this.EditForm.value.emailResponsable).subscribe((dataUser: any) => {
              Swal.fire({
                title: 'Votre demande a été corrigé avec succès.',
                text: 'Un mail vous est envoyé à l’adresse indiquée. Pour pouvoir suivre votre dossier, veuillez utiliser ce numéro de dossier N˚ ' + data.data.numdemande + '\n \n \n \n',
                icon: 'success',
                confirmButtonColor: "GREEN",
                confirmButtonText: 'OUI',
              }).then((result) => {
                if (result.value) {
                  if(this.profileLibelle == "demandeur") {
                    this.router.navigate(['/demandeencoursdcsc']); 
                  } else {
                    this.router.navigate(['/landing']); 
                  }                    
                  this.closeDialog();
                }
              });
            })  
            break; 
          case 3:
            this.demandeAutFraService.getDemandeById(this.data.data.id).subscribe((res: any) => {
              this.EditForm.value.poOwner = res.poOwner;
              this.EditForm.value.owner = res.owner;
            });
            this.router.navigate(['/demandeatraiterchefdivision']);   
            this.closeDialog();
            // this.userService.getUserByEmail(this.EditForm.value.emailResponsable).subscribe((dataUser: any) => {
            //   Swal.fire({
            //     title: 'Votre demande a été corrigé avec succès.',
            //     text: 'Un mail vous est envoyé à l’adresse indiquée. Pour pouvoir suivre votre dossier, veuillez utiliser ce numéro de dossier N˚ ' + data.data.numdemande + '\n \n \n \n',
            //     icon: 'success',
            //     confirmButtonColor: "GREEN",
            //     confirmButtonText: 'OUI',
            //   }).then((result) => {
            //     if (result.value) {
            //       this.router.navigate(['/demandeatraiterchefdivision']);   
            //       this.closeDialog();
            //     }
            //   });
            // }) 
            break;            
        }
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Une Erreur est survenue',
        });
      }
    }, error => {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Une Erreur est survenue',
      });
    });
  }

  @ViewChild('filecertificatanalysesproduit') filecertificatanalysesproduit;

  certificatanalysesproduit: File;

  addFilecertificatanalysesproduit() {
    this.filecertificatanalysesproduit.nativeElement.click();
  }

  onFileAddedcertificatanalysesproduit() {
    this.certificatanalysesproduit = this.filecertificatanalysesproduit.nativeElement.files[0];
    const extension = this.certificatanalysesproduit.name.split('.')[1].toLowerCase();
    if ('pdf' != extension) {
      this.translate.get('demandeAutFra.fileExtenxionNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.certificatanalysesproduit = null;
      return;
    }
    if (this.certificatanalysesproduit.size > 3000000) {
      this.translate.get('demandeAutFra.fileSizeNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.certificatanalysesproduit = null;
      return;
    }
  }

  @ViewChild('fileregistrecommerceetcreditmobilier') fileregistrecommerceetcreditmobilier;

  registrecommerceetcreditmobilier: File;

  addFileregistrecommerceetcreditmobilier() {
    this.fileregistrecommerceetcreditmobilier.nativeElement.click();
  }

  onFileAddedregistrecommerceetcreditmobilier() {
    this.registrecommerceetcreditmobilier = this.fileregistrecommerceetcreditmobilier.nativeElement.files[0];
    const extension = this.registrecommerceetcreditmobilier.name.split('.')[1].toLowerCase();
    if ('pdf' != extension) {
      this.translate.get('demandeAutFra.fileExtenxionNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.registrecommerceetcreditmobilier = null;
      return;
    }
    if (this.registrecommerceetcreditmobilier.size > 3000000) {
      this.translate.get('demandeAutFra.fileSizeNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.registrecommerceetcreditmobilier = null;
      return;
    }
  }

  @ViewChild('filejuridique') filejuridique;

  juridique: File;

  addFilejuridique() {
    this.filejuridique.nativeElement.click();
  }

  onFileAddedjuridique() {
    this.juridique = this.filejuridique.nativeElement.files[0];
    const extension = this.juridique.name.split('.')[1].toLowerCase();
    if ('pdf' != extension) {
      this.translate.get('demandeAutFra.fileExtenxionNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.juridique = null;
      return;
    }
    if (this.juridique.size > 3000000) {
      this.translate.get('demandeAutFra.fileSizeNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.juridique = null;
      return;
    }
  }

  @ViewChild('fileprocessusfabrication') fileprocessusfabrication;

  processusfabrication: File;

  addFileprocessusfabrication() {
    this.fileprocessusfabrication.nativeElement.click();
  }

  onFileAddedprocessusfabrication() {
    this.processusfabrication = this.fileprocessusfabrication.nativeElement.files[0];
    const extension = this.processusfabrication.name.split('.')[1].toLowerCase();
    if ('pdf' != extension) {
      this.translate.get('demandeAutFra.fileExtenxionNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.processusfabrication = null;
      return;
    }
    if (this.processusfabrication.size > 3000000) {
      this.translate.get('demandeAutFra.fileSizeNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.processusfabrication = null;
      return;
    }
  }

  @ViewChild('filecopieninea') filecopieninea;

  copieninea: File;

  addFilecopieninea() {
    this.filecopieninea.nativeElement.click();
  }

  onFileAddedcopieninea() {
    this.copieninea = this.filecopieninea.nativeElement.files[0];
    const extension = this.copieninea.name.split('.')[1].toLowerCase();
    if ('pdf' != extension) {
      this.translate.get('demandeAutFra.fileExtenxionNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.copieninea = null;
      return;
    }
    if (this.copieninea.size > 3000000) {
      this.translate.get('demandeAutFra.fileSizeNotif').subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.copieninea = null;
      return;
    }
  }

  @ViewChild('filecnipasseport') filecnipasseport;

  cnipasseport: File;

  addFilecnipasseport() {
    this.filecnipasseport.nativeElement.click();
  }

  onFileAddedcnipasseport() {
    this.cnipasseport = this.filecnipasseport.nativeElement.files[0];
      const extension = this.cnipasseport.name.split('.')[1].toLowerCase();
      if ('pdf' != extension) {
        this.translate.get('demandeAutFra.fileExtenxionNotif').subscribe((res: string) => {
          this.notification.warn(res);
        });
        this.cnipasseport = null;
        return;
      }
      if (this.cnipasseport.size > 3000000) {
        this.translate.get('demandeAutFra.fileSizeNotif').subscribe((res: string) => {
          this.notification.warn(res);
        });
        this.cnipasseport = null;
        return;
      }
  }

  deleteProduit(data) {
    this.produits = this.produits.filter((produit) => {
      return produit.nature !== data.nature || produit.categorie !== data.categorie || produit.marque !== data.marque || produit.autFra !== data.autFra
        || produit.contenance !== data.contenance || produit.typeemballage !== data.typeemballage || produit.descriptionEtiquette !== data.descriptionEtiquette
        || produit.etiquetteouemballage != data.etiquetteouemballage

    });
    this.dataSourceProduits = new MatTableDataSource(this.produits);
  }

  closeDialog() {
		this.dialogRef.close();
	}

  compRegion(a:any,b:any){
    return a?.id == b?.id
  }

  compAntenne(a:any,b:any){
    return a?.id == b?.id
  }
}
