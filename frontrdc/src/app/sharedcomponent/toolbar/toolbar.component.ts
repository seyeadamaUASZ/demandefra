import { Component, OnInit, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { MatDialog, MatSnackBar, MatSnackBarRef, SimpleSnackBar, DateAdapter } from '@angular/material';
import { Router } from '@angular/router';
import { StyleManagerService } from 'src/app/shared/style-manager.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmDialogComponent, ConfirmDialogModel } from '../confirm-dialog/confirm-dialog.component';
import { AuthService } from 'src/app/utilisateur/services/auth.service';
import { ParametreService } from 'src/app/utilisateur/services/parametre.service';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { MonCompteService } from 'src/app/utilisateur/components/mon-compte/services/mon-compte.service';
import { JsonpInterceptor } from '@angular/common/http';
import { User } from 'src/app/utilisateur/models/user';
import { ChangePwdComponent } from 'src/app/utilisateur/components/change-pwd/change-pwd.component';
import { MonCompteComponent } from 'src/app/utilisateur/components/mon-compte/mon-compte.component';
import { DesinscrireComponent } from 'src/app/inscription/components/desinscrire/desinscrire.component';
import { ParametreComponent } from 'src/app/utilisateur/components/parametre/parametre.component';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { NotificationPushService } from 'src/app/parametrage/services/notification-push.service';
import { test } from 'src/app/sharedcomponent/test';
import { ChangeCssComponent } from 'src/app/utilisateur/components/change-css/change-css.component';
@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit, AfterViewInit {
  @Output() toggleSidenav = new EventEmitter<void>();
  @Output() toggleDir = new EventEmitter<void>();
  parametre: any;
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

  date = new Date;
  id;
  prenom;
  nom;
  profil;
  profileLibelle;
  langueDefault;
  themeDefault;
  session;
  Langues;
  Themes;
  currentLang;
  AfficheApparence: boolean = true;
  idLangue;
  idTheme;
  logo;
  user: User;
  currentLangue;
  nombreNotif = 4;
  AllNotif;
  
  constructor(private dialog: MatDialog,
    public styleManager: StyleManagerService,
    private snackBar: MatSnackBar,
    private router: Router,
    public translate: TranslateService,
    public dateAdapter: DateAdapter<Date>,
    public paramService: ParametreService,
    public authService: AuthService,
    public userService: UserService,
    public notifPushService: NotificationPushService,
    private formbuild: FormBuilder, private monCompteService: MonCompteService,
    private compteService: MonCompteService, private notification:NotificationService
  ) {
    this.currentLangue = localStorage.getItem('langue');
    this.getLogoUser();
  }
  ngAfterViewInit() {
    this.installerTheme(localStorage.getItem('theme'));
    this.useLang(localStorage.getItem('langue'));
  }
  
  ngOnInit() {
    this.getAllNotification();
    this.id = localStorage.getItem('id');
    this.prenom = localStorage.getItem('prenom');
    this.nom = localStorage.getItem('nom');
    this.profil = localStorage.getItem('profil');
    this.profileLibelle = localStorage.getItem('profileLibelle');

    if (this.profileLibelle == 'Administrateur' || this.profileLibelle == 'administrateur') {
      this.AfficheApparence = true
    } else {
      this.AfficheApparence = false;
    }

    this.session = localStorage.getItem('session');
    this.listLangue();

  }

  switchLang(lang: string) {
    localStorage.setItem('langue', lang);
    this.useLang(lang);
  }
  openUser() {
    const dialogReg = this.dialog.open(MonCompteComponent, {
      disableClose: true,
      width: '500px'
    });
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

  installerTheme(themeName: string) {
    this.styleManager.setStyle('theme', themeName);
  }


  openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar> {
    return this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  openDialog() {
    const dialogReg = this.dialog.open(ChangePwdComponent, {
      disableClose: true,
      width: '500px'
    });
  }

  logout() {
    const message = 'Alert.deconnexion';
    const dialogData = new ConfirmDialogModel('utilisateur.deconnexion', message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      width: '500px',
      data: dialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      //alert(result);

      if (result) {
        //console.log("la session" + this.session);

        if (!this.session || this.session === 'undefined') {
          // console.log('jentre pas dans logout');

          localStorage.removeItem('token');
          localStorage.removeItem('session');
          sessionStorage.removeItem('menus');
          sessionStorage.removeItem('accesmenus');
          this.router.navigate(['/login']);
        } else {
          this.authService.deconnecter(this.session).subscribe(data => {
            //console.log("les donnes" + data);
            if (data.statut) {
              localStorage.removeItem('profile');
              localStorage.removeItem('profileLibelle');
              localStorage.removeItem('password');
              localStorage.removeItem('prenom');
              localStorage.removeItem('nom');
              localStorage.removeItem('token');
              // localStorage.removeItem('username');
              localStorage.removeItem('id');
              localStorage.removeItem('session');
              sessionStorage.removeItem('menus');
              sessionStorage.removeItem('accesmenus')
              this.router.navigate(['/login']);
            }
          }, error => {
            console.log(error);

          });

        }

      }
    });
  }

  switchTheme(themeName: string) {
    localStorage.setItem('theme', themeName);
    this.installTheme(themeName);
  }

  installTheme(themeName: string) {
    const theme = this.Themes.find(currentTheme => currentTheme.thmName === themeName);
    if (!theme) {
      return;
    }
    else {
      this.styleManager.setStyle('theme', theme.thmName);

    }

  }
  compareLangue(l1, l2): boolean {
    return l1 && l2 ? l1.lnglangue === l2.lnglangue : false;
  }

  fonctionUpdatelangue() {
    this.userService.updateLangueUser(this.userForm.value).subscribe(data => {
      if (data.statut) {
        //alert(data.description);

      } else {
        //alert(data.description);
      }
    });
  }

  fonctionUpdateTheme() {
    this.userService.updateThemeUser(this.userForm.value).subscribe(data => {
      if (data.statut) {
        //alert(data.description);

      } else {
        //alert(data.description);
      }
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

  getThemeId(theme) {
    this.userForm.value.uti_thm_id = theme;
    this.userForm.value.uti_lng_id = null;
    this.fonctionUpdateTheme();
  }

  getLogoUser() {
    this.compteService.infoCompte().subscribe(data => {
      this.user = data;
      if(this.user.utiLogo){
        this.logo = "data:image/png;base64," + this.user.utiLogo;
      }else{
        this.logo=localStorage.getItem('logo')
      }

    });
  }

  desinscrireDialog() {
    const dialogReg = this.dialog.open(DesinscrireComponent, {
      disableClose: true,
      width: '500px'
    });
  }
  openSetting() {
    const dialogReg = this.dialog.open(ParametreComponent, {
      disableClose: true,
      width: '600px'
    });
  }


  openNotif() {
    this.getAllNotification();
  
  }

 
  supprimerNotif(notif) {
    this.notifPushService.deleteNotifPush(notif).subscribe(data => {
      this.AllNotif.pop(notif);
      this.playAudio();
      this.translate.get('notification.success-delete').subscribe((res: string) => {           
        this.notification.success(res);
      }); 
    })
  }
  ajouterNotif(){
    this.AllNotif.push();
      this.playAudio();
      this.translate.get('notification.success-ajout').subscribe((res: string) => {           
        this.notification.success(res);
      }); 
  }
  getAllNotification() {
    this.notifPushService.getNotifPush().subscribe(data => {
      this.AllNotif = data.data.reverse();
      test.myNotif= this.AllNotif.length;

    })
  }
  changeCss(){
    const dialogReg = this.dialog.open(ChangeCssComponent, {
      disableClose: true,
      width: '600px'
    });  }
  

  
  playAudio(){
    let audio = new Audio();
    audio.src = "../../../assets/audio/sound.mp3";
    audio.load();
    audio.play();
  }

}
