import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { DateAdapter } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ApplicationService } from 'src/app/application/services/application.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { StyleManagerService } from 'src/app/shared/style-manager.service';
import { ParametreService } from 'src/app/utilisateur/services/parametre.service';
import { UserService } from 'src/app/utilisateur/services/user.service';

@Component({
  selector: 'app-app-published',
  templateUrl: './app-published.component.html',
  styleUrls: ['./app-published.component.scss']
})
export class AppPublishedComponent implements OnInit {
  appName;
  langue;
  theme;
  Langues;
  appsPubliees;
  appLogo;
  idLangue;
  currentLangue;
  constructor(public router: Router,
    private route: ActivatedRoute,
    public translate: TranslateService,
    private paramService: ParametreService,
    public dateAdapter: DateAdapter<Date>,
    private formbuild: FormBuilder,
    public styleManager: StyleManagerService,
    public notification: NotificationService,
    private appService: ApplicationService,
    public userService: UserService) { }

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
  ngOnInit() {
    this.listLangue();
    this.getListParam();
    this.appService.listeAppPubliee().subscribe(data => {
      this.appsPubliees = data.data;
    });
  }

  goLink(appLien){
    window.open(appLien);
  }
  

  
  
  getListParam() {
    this.langue = localStorage.getItem("langue");
    this.appName = localStorage.getItem("logo");

    this.paramService.getDefautParametre().subscribe(data => {
      //console.log(data);
      this.langue = this.langue ? this.langue : data.data.param_lng_id.lngLangue;
      this.theme = this.theme ? this.theme : data.data.param_thm_id.thmName;
      localStorage.setItem('langue', this.langue);
      localStorage.setItem('theme', this.theme);
      this.langue = localStorage.getItem('langue') ? localStorage.getItem('langue') : 'fr';
      this.theme = localStorage.getItem('theme') ? localStorage.getItem('theme') : 'gainde-green';
      this.installTheme(this.theme);
      this.translate.setDefaultLang(this.langue);
      this.translate.use(this.langue);
      this.appName = data.data?.param_nom_app;
      localStorage.setItem("appName", this.appName);
      if (data.data?.param_img_id) {
        this.getLogo(data.data?.param_img_id.imgId);
        // var base64Data = data.data.param_img_id.imgLogoByte;
        // this.appLogo = `data:image/png;base64,${base64Data}`         
        // localStorage.setItem('logo', this.appLogo);
      }
    }
      , error => {
        this.langue = 'fr';
        this.theme = 'gainde-green';
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
    //Make a call to Sprinf Boot to get the Image Bytes.
    //localStorage.setItem('logo', 'data:image/png;base64,' + logoRef);    
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

  openDialogInscription() {
    this.router.navigate(['/inscription']);
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
}
