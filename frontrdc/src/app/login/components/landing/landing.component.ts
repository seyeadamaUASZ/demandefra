import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar, MatDialog, DateAdapter } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { ParametreService } from 'src/app/utilisateur/services/parametre.service';

import { StyleManagerService } from 'src/app/shared/style-manager.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ApplicationService } from 'src/app/application/services/application.service';
import { UserService } from 'src/app/utilisateur/services/user.service';
import Swal from 'sweetalert2';
import { ContactService } from '../../services/contact.service';
declare var myExtObject: any;


@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
  appName;
  langue;
  theme;
  Langues;
  appsPubliees;
  appLogo;
  selectedFile1: File;
  selectedFile2: File;
  selectedFile3: File;
  selectedFile4: File;
  selectedFile5: File;
  selectedFile6: File;
  selectedFile7: File;
  selectedFile8: File;
  selectedFile9: File;
  selectedFile10: File;

  retreivedImage1: any;
  retreivedImage2: any;
  retreivedImage3: any;
  retreivedImage4: any;
  retreivedImage5: any;
  retreivedImage6: any;
  retreivedImage7: any;
  retreivedImage8: any;
  retreivedImage9: any;
  retreivedImage10: any;

  base64Data5: any;
  base64Data1: any;
  base64Data2: any;
  base64Data3: any;
  base64Data4: any;
  base64Data6: any;
  base64Data7: any;
  base64Data8: any;
  base64Data9: any;
  base64Data10: any;

  retreivedResponse: any;
  retreivedResponse1: any;
  retreivedResponse2: any;
  retreivedResponse3: any;
  retreivedResponse5: any;
  retreivedResponse6: any;
  retreivedResponse7: any;
  retreivedResponse8: any;
  retreivedResponse9: any;
  retreivedResponse10: any;

  idLangue;
  currentLangue;
  partner;
  colorPrimary;
  colorSecondary;

  userForm = this.formbuild.group({
    utiId: ['', Validators.required],
    utiPrenom: ['', Validators.required],
    utiNom: ['', Validators.required],
    utiUsername: [localStorage.getItem('username')],
    utiTelephone: [''],
    utiEmail: [''],
    utiAdresse: [''],
    uti_lng_id: [''],
    uti_thm_id: [''],
  });

  contactForm = this.formbuild.group({
    nom: ['', Validators.required],
    email: ['', Validators.required],
    message: ['', Validators.required]
  });

  constructor(public router: Router, private dialogRef: MatDialog,
    private contactService: ContactService,
    private route: ActivatedRoute,
    public translate: TranslateService,
    private paramService: ParametreService,
    private paramServic: ParametreService,
    public dateAdapter: DateAdapter<Date>,
    private formbuild: FormBuilder,
    public styleManager: StyleManagerService,
    public notification: NotificationService,
    private appService: ApplicationService,
    public userService: UserService,
    private dialog:MatDialog
  ) {
    this.currentLangue = localStorage.getItem('langue') ? localStorage.getItem('langue') : 'fr';
    this.colorSecondary = localStorage.getItem('colorSecondary') ? localStorage.getItem('colorSecondary') : 'white';

    this.colorPrimary = localStorage.getItem('colorPrimary') ? localStorage.getItem('colorPrimary') : 'bleu';
  }
  ngAfterViewInit() {
    this.useLang(localStorage.getItem('langue'));
  }

  ngOnInit() {
    this.listLangue();
    this.getListParam();
    this.getImagelanding();
    this.getPart();
    this.appService.listeAppPubliee2().subscribe(data => {
      this.appsPubliees = data.data;
    });
  }

  getImagelanding() {
    this.paramService.getImageLanding().subscribe(data => {
      if (data.statut) {
        this.retreivedResponse = data?.data;
        //alert(''+this.base64Data1);
        this.base64Data1 = this.retreivedResponse.lndIm1;
        this.base64Data2 = this.retreivedResponse?.lndIm2;
        this.base64Data3 = this.retreivedResponse?.lndIm3;
        this.base64Data4 = this.retreivedResponse?.lndIm4;
        this.base64Data5 = this.retreivedResponse?.lndIm5;
        this.base64Data6 = this.retreivedResponse?.lndIm6;
        this.base64Data7 = this.retreivedResponse?.lndIm7;

        this.base64Data8 = this.retreivedResponse?.lndIm8;
        this.base64Data9 = this.retreivedResponse?.lndIm9;
        this.base64Data10 = this.retreivedResponse?.lndIm10;

        this.retreivedImage1 = 'data:image/png;base64,' + this.base64Data1;
        this.retreivedImage2 = 'data:image/png;base64,' + this.base64Data2;
        this.retreivedImage3 = 'data:image/png;base64,' + this.base64Data3;
        this.retreivedImage4 = 'data:image/png;base64,' + this.base64Data4;
        this.retreivedImage5 = 'data:image/png;base64,' + this.base64Data5;
        this.retreivedImage6 = 'data:image/png;base64,' + this.base64Data6;
        this.retreivedImage7 = 'data:image/png;base64,' + this.base64Data7;
        this.retreivedImage8 = 'data:image/png;base64,' + this.base64Data8;
        this.retreivedImage9 = 'data:image/png;base64,' + this.base64Data9;
        this.retreivedImage10 = 'data:image/png;base64,' + this.base64Data10;


        localStorage.setItem('image', this.retreivedImage1);
        localStorage.setItem('imageInscription', this.retreivedImage6);
        localStorage.setItem('imageConnexion', this.retreivedImage7);
      } else {
        this.retreivedResponse = false;
      }
    })
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  fonctionUpdatelangue() {
    this.userService.updateLangueUser(this.userForm.value).subscribe(data => {
    });
  }

  getLangueId(langue) {
    for (let index = 0; index < this.Langues.length; index++) {
      if (langue == this.Langues[index].lngLangue) {
        this.idLangue = this.Langues[index].lngId;
        this.userForm.value.uti_lng_id = this.idLangue;
        this.userForm.value.uti_thm_id = null;
        this.fonctionUpdatelangue();
      }
    }
  }

  compareLangue(l1, l2): boolean {
    return l1 && l2 ? l1.lnglangue === l2.lnglangue : false;
  }

  switchLang(lang: string) {
    localStorage.setItem('langue', lang);
    this.useLang(lang);
  }
  useLang(lang: string) {
    this.translate.use(lang);
    this.dateAdapter.setLocale(lang);
  }
  listLangue() {
    this.paramService.getLangue().subscribe(data => {
      this.Langues = data.data;
    });
  }

  openDialogInscription() {
    this.router.navigate(['/demande']);
  }

  openSuivreDemande(){
    this.router.navigate(['/suivredemande']);
  }

  getListParam() {
    this.langue = localStorage.getItem("langue");
    this.appLogo=localStorage.getItem("logo");
    this.paramService.getDefautParametre().subscribe(data => {
      //console.log(data);
      this.langue = this.langue ? this.langue : data?.data[0]?.param_lng_id?.lngLangue;
      // this.theme = this.theme ? this.theme : data.data[0].param_thm_id.thmName;
      localStorage.setItem('langue', this.langue==null?'fr':this.langue);
      localStorage.setItem('theme', this.theme);
      this.langue = localStorage.getItem('langue') ? localStorage.getItem('langue') : 'fr';
      // this.theme = localStorage.getItem('theme') ? localStorage.getItem('theme') : 'gainde-green';
      this.theme = 'pink-grey';
      this.installTheme(this.theme);
      this.translate.setDefaultLang(this.langue);
      this.translate.use(this.langue);
      this.appName = data.data[0]?.param_nom_app;
      localStorage.setItem("appName", this.appName);
      if (data.data[0]?.param_img_id) {
        this.getLogo(data.data[0]?.param_img_id.imgId);
      }
    }
      , error => {
        this.langue = 'fr';
        // this.theme = 'gainde-green';
        this.theme = 'pink-grey';
        this.installTheme(this.theme);
        this.translate.setDefaultLang(this.langue);
        this.translate.use(this.langue);
        this.translate.get('Error.internalservererror').subscribe((res: string) => {
          this.notification.error(res);
        });
      }
    );
  }

  getLogo(logoRef) {
    this.paramService.getImage(logoRef)
      .subscribe(
        res => {
          var base64Data = res.data.imgLogoByte;
          this.appLogo = `data:image/png;base64,${base64Data}`
          localStorage.setItem('logo', this.appLogo);
        }
      );
  }

  installTheme(themeName: string) {
    this.styleManager.setStyle('theme', themeName);
  }

  getPart() {
    this.paramService.getPartenaire().subscribe(data => {
      if (data.statut) {
        this.partner = data.data;
      } else {
        this.partner = false;
      }
    })
  }

  contact() {
    const formData: FormData = new FormData();
    if (this.contactForm.value.nom)
      formData.append('nom', this.contactForm.value.nom);
    if (this.contactForm.value.email)
      formData.append('email', this.contactForm.value.email);
    if (this.contactForm.value.message)
      formData.append('message', this.contactForm.value.message);
    this.contactService.messageContact(formData).subscribe(
      (data: any) => {
        if (data.statut) {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: data.message,
            showConfirmButton: false,
            timer: 1500
          });
          this.contactForm.patchValue({
            nom: '',
            email: '',
            message: '',
          });

        } else if (data.status == "INVALID_EMAIL") {
          Swal.fire({
            icon: 'warning',
            title: 'Oops...',
            text: "Veuillez saisir un bon format de mail",
          })
        } else {
          Swal.fire({
            icon: 'warning',
            title: 'Oops...',
            text: "Une Erreur a survenu",
          })
        }
      }, (error) => {
      }, () => {
      }
    )
  }
}
