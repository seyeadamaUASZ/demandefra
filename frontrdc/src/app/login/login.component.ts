import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from '../utilisateur/services/auth.service';
import { StyleManagerService } from 'src/app/shared/style-manager.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ParametreService } from '../utilisateur/services/parametre.service';
import { UserService } from '../utilisateur/services/user.service';
import { NotificationService } from '../shared/services/notification.service';
import { Observable, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  model: any = {};
  tokkens;
  langue;
  theme;
  prenom;
  compteur = 0;
  compte;
  bloquerInput: Boolean = false;
  state: boolean;
  errorList;
  type = 'password';
  lasse = 'visibility';
  icone = 'checked';
  typeConnexion;
  isBioActive: Boolean;
  isFaceActive: Boolean;
  loginForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });
  loading: boolean;
  appName;
  appLogo
  imageAccueil;
  currentLangue;
  tentative;
 
  constructor(private router: Router, private route: ActivatedRoute, private formBuilder: FormBuilder,
    public translate: TranslateService,
    public userService: UserService,
    private auth: AuthService,
    public styleManager: StyleManagerService,
    public paramService: ParametreService,
    private _snackBar: MatSnackBar,
    public notification: NotificationService,
    private usersService: UserService) {
    // translate.addLangs(['en', 'fr', 'es']);
    // this.translate.setDefaultLang('fr');
    this.langue = localStorage.getItem('langue');
    this.theme = localStorage.getItem('theme');
    this.prenom = localStorage.getItem('prenom');
    
    //this.getListParam();
    this.loading = false;

  }

  openSnackBar() {
    this.translate.get('Welcome\t\t ' + this.prenom).subscribe((res: string) => {
      this.notification.info(res);
    });
  }

  ngOnInit() {
    this.imageAccueil = localStorage.getItem('imageConnexion');
    this.tokkens = localStorage.getItem('token');
    this.appLogo = localStorage.getItem('logo');
    this.tentative = localStorage.getItem('tentative');
    if (this.tentative) {
      this.compteur = this.tentative;
    }else{
      this.compteur=0;
    }
    //alert('alert'+this.compteur);

    this.getListParamUser();
    if (this.tokkens) {
      let message;
      this.openSnackBar();
      this.router.navigate(['/home']);
    } else {
      this.router.navigate(['/login']);
    }
    this.getTypeConnexion();
  }

  switchLang(lang: string) {
    this.translate.use(lang);
  }

  regarder() {
    if (this.icone) {
      this.type = 'text';
      this.lasse = 'visibility_off';
      this.icone = '';
      this.myFunction();
    } else {
      this.type = 'password';
      this.lasse = 'visibility';
      this.icone = 'checked';
    }
  }

  myFunction() {
    setTimeout(() => {
      this.alertFunc();
    }, 400);
  }
  bloquerConnexion() {
    setTimeout(() => {
      this.alertFunc();
    }, 1000);
  }

  alertFunc() {
    this.icone = '';
    this.type = 'password';
    this.lasse = 'visibility';
    this.icone = 'checked';
  }
  getTypeConnexion() {
    this.paramService.getTypeConnexion().subscribe(data => {
      this.typeConnexion = data.data;
      this.isBioActive = data.data[1].typIsActive;
      this.isFaceActive = data.data[0].typIsActive;
      // alert("-------------" +this.isBioActive);
      // alert("-------------" +this.isFaceActive);   
    });
  }

  // verifySession(): boolean {
  //   this.auth.VerifierConnexion(this.loginForm.controls['username'].value).subscribe(data => {
  //     console.log(data.statut);
  //     this.state = data.statut;
  //   });
  //   return this.state;
  // }

  getListParamUser() {

    this.userService.getParamUser(localStorage.getItem('username')).subscribe(data => {
      localStorage.setItem('langue', data.data.uti_lng_id.lngLangue);
      localStorage.setItem('theme', data.data.uti_thm_id.thmName);
      this.langue = localStorage.getItem('langue') ? localStorage.getItem('langue') : 'fr';
      this.theme = localStorage.getItem('theme') ? localStorage.getItem('theme') : 'gainde-green';
      this.installTheme(this.theme);
      this.translate.setDefaultLang(this.langue);
      this.translate.use(localStorage.getItem('langue'));

    }, error => {
      this.getListParam();

    })
  }

  getListParam() {
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
      if (data.data?.param_img_id)
        this.getLogo(data.data?.param_img_id.imgId);

      this.appName = data.data?.param_nom_app;
      localStorage.setItem("appName", this.appName);
      //console.log('Le theme'+ localStorage.getItem('theme'));
      // this.translate.setDefaultLang(localStorage.getItem('langue'));
      //location.reload();      
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
          localStorage.setItem('logo', 'data:image/png;base64,' + base64Data);
        }
      );

  }


  onSubmit() {
    this.loading = true;
    this.auth.login(this.loginForm.controls['username'].value, this.loginForm.controls['password'].value).subscribe(data => {
      if (data.statut) {
        this.loading = false;
        if (data.premiereConnec || data.expiredpwd) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.info(res);
          });
          localStorage.setItem('prenom', data.data.prenom);
          localStorage.setItem('nom', data.data.nom);
          localStorage.setItem('token', data.data.token);
          localStorage.setItem('profile', data.data.profile);
          localStorage.setItem('profileLibelle', data.data.profileLib);
          localStorage.setItem('username', data.data.username);
          localStorage.setItem('password', data.data.password);
          localStorage.setItem('id', data.data.utiId);
          localStorage.setItem('session', data.data.session);
          localStorage.setItem('email', data.data.utiEmail);
          localStorage.removeItem('tentative');
          this.getListParamUser();
          this.router.navigate(['/login/premiereConnect']);
        } else {
          //window.alert(data.description);                   
          // console.log('data:' + data.data.username);
          localStorage.setItem('prenom', data.data?.prenom);
          localStorage.setItem('nom', data.data?.nom);
          localStorage.setItem('token', data.data?.token);
          localStorage.setItem('profile', data.data?.profile);
          localStorage.setItem('username', data.data?.username);
          localStorage.setItem('profil', data.data?.profile);
          localStorage.setItem('profileLibelle', data.data?.profileLib);
          localStorage.setItem('id', data.data?.id);
          localStorage.setItem('session', data.data?.session);
          localStorage.removeItem('tentative');
          this.getListParamUser();
          this.router.navigate(['/home']);
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
        }
      } else {
        this.compteur++;
        let n = this.compteur.toString();
        let m=0;
        localStorage.setItem('tentative', n);
        if (this.compteur == 3) {
          this.bloquerInput = true;
          this.loading = false;
          localStorage.setItem('tentative', m.toString());
          this.desactived(this.loginForm.controls['username'].value);
          this.translate.get("messageBlocageSession").subscribe((res: string) => {
            this.notification.error(res);
          });

        }
        else if ((data.Tempssession) > 0) {
          this.loading = false;
          // this.compteur=0;
          this.bloquerInput = false;
          localStorage.removeItem('tentative');
          //let messageTemps;
          this.translate.get("messageTempsSession").subscribe((res: string) => {
            this.notification.warn(res + data.Tempssession + " mn");
          });
        }
        else {
          this.loading = false;
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.warn(res);
          });

        }
      }
    }, (err) => {
      this.loading = false;
      this.translate.get('Error.internalservererror').subscribe((res: string) => {
        this.notification.error(res);
      });
    });

  }


  //}
  installTheme(themeName: string) {
    this.styleManager.setStyle('theme', themeName);
  }
  desactived(username: any) {
    this.usersService.desactiverByusername(username).subscribe(res => {
      //alert(res);
    })
  }

}
