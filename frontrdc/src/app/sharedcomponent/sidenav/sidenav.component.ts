import { Component, OnInit, NgZone } from '@angular/core';
//import { EmployeeService } from '../../services/employee.service';
import { Observable } from 'rxjs';
//import { Employee } from '../../models/employee';
import { Router } from '@angular/router';
import { RoleService } from 'src/app/utilisateur/services/role.service';
import { AuthService } from 'src/app/utilisateur/services/auth.service';
import { NavItem } from './nav-item';
import { Menu } from 'src/app/utilisateur/models/menu';
import { Action } from 'src/app/utilisateur/models/action';
import { ParametreService } from 'src/app/utilisateur/services/parametre.service';
// import { ParametreService } from '../../services/parametre.service';

const SMALL_WIDTH_BREAKPOINT = 720;

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})

export class SidenavComponent implements OnInit {
  private mediaMatcher: MediaQueryList = matchMedia(`(max-width: ${SMALL_WIDTH_BREAKPOINT}px)`);
  //employees: Observable<Employee[]>;
  isIndigoTheme: boolean = false;
  menus: any;
  accesmenus: Menu[] = [];
  //accesactions: Action[] = [];
  //accesactions = [];
  accesactionsitems = {};
  retreivedLogo: any;
  nom_app;
  logo;
  base64Data: any;
  retreivedResponse: any;
  imageId: any;
  mimeType: any;
  loading = false;
  dir: string = sessionStorage.getItem("dir") ? sessionStorage.getItem("dir") : 'ltr';
  constructor(zone: NgZone, private router: Router, private authService: AuthService, public paramService: ParametreService) {
  //localStorage.getItem('logo');
    //alert("--------------------constructor bar------------------");
    this.mediaMatcher.addListener(mql =>
      zone.run(() => this.mediaMatcher = matchMedia(`(max-width: ${SMALL_WIDTH_BREAKPOINT}px)`)));
  }

  getListParam() {
    this.paramService.getDefautParametre().subscribe(data => { 
      // console.log("-----------------"+data.data.param_img_id);
    // alert(JSON.stringify(data.data.param_img_id.imgId));
      this.nom_app=data.data[0].param_nom_app;
      this.getImage(data.data[0].param_img_id?.imgId);
    });

  }
  getImage(imageId) {
    // alert("------------------getImage--- parametre component-------------------- "+this.imageId);
    //Make a call to Spring Boot to get the Image Bytes.
    this.paramService.getImage(imageId)
      .subscribe(
        res => {
          this.retreivedResponse = res;
          this.base64Data = this.retreivedResponse.data.imgLogoByte;
          this.retreivedLogo = 'data:image/png;base64,' + this.base64Data;
          //localStorage.setItem('logo', this.retreivedImage);
        }
      );
  }
  ngOnInit() {
    this.getListParam();
    this.retreivedLogo = localStorage.getItem('logo');  
    //alert("--------------------side bar------------------");
    if (!sessionStorage.getItem('menus')) {
      this.authService.getMenusProfil(localStorage.getItem('profile')).subscribe(data => {
        this.menus = data.data;

        //alert(JSON.stringify(this.menus));               
        sessionStorage.setItem('menus', JSON.stringify(this.menus));       
        
        this.extractAccessMenus();
      });
    } else {
      this.menus = JSON.parse(sessionStorage.getItem('menus'));     
      this.extractAccessMenus();     
    }

  }

  extractAccessMenus() {
    let m = new Menu();
    m.menPath = "/home";
    m.menNom = "home";
    this.accesmenus.push(m);
    let m1 = new Menu();
    m1.menPath = "/login";
    m1.menNom = "login";
    this.accesmenus.push(m1);

    let m2 = new Menu();
    m2.menPath = "/login/premiereConnect";
    m2.menNom = "login.premiereConnect";
    this.accesmenus.push(m2);
    let m3 = new Menu();
    m3.menPath = "/login/forgetPwd";
    m3.menNom = "login.forgetPwd";
    this.accesmenus.push(m3);
    let m4 = new Menu();
    m4.menPath = "/login/newPwd";
    m4.menNom = "login.newPwd";
    this.accesmenus.push(m4);
    let m5 = new Menu();
    m5.menPath = "/utilisateur/parametre";
    m5.menNom = "utilisateur.parametre";
    this.accesmenus.push(m5);
    let m6 = new Menu();
    m6.menPath = "/utilisateur/monCompte";
    m6.menNom = "utilisateur.monCompte";
    this.accesmenus.push(m6);
    let m7 = new Menu();
    m7.menPath = "/error-page";
    m7.menNom = "error-page";
    this.accesmenus.push(m7);


    // this.accesmenus = [{"menPath":"/home","menNom":"home"},{"menPath":"/login","menNom":"login"}] //.push(m1);
    let listMenus = this.menus;
    for (let menu of listMenus) {
      this.extractMenuList(menu)
    }
    sessionStorage.setItem('accesmenus', JSON.stringify(this.accesmenus));    
    //this.accesactions.push(this.accesactionsitems);
    sessionStorage.setItem('actions', JSON.stringify(this.accesactionsitems));
    //alert("-------------------Menus access------------------"+JSON.stringify(this.menus))
    //console.log("---------------Actions access--------------"+JSON.stringify(this.accesactions));
    //console.log("---------------Actions access items--------------"+JSON.stringify(this.accesactions));
  }


  extractMenuList(menu: Menu) {
    let m = new Menu();
    m.menId = menu.menId;
    m.menPath = menu.menPath;
    m.menNom = menu.menNom;
    this.accesmenus.push(m);
    if (menu.sousMenus.length > 0) {
      for (let men of menu.sousMenus) {
        this.extractMenuList(men);
      }
    }

    if (menu.actions.length > 0) {
      for (let act of menu.actions) {
        //this.accesactions.push(act);
        this.accesactionsitems[act.actCode]=true;
        //alert("----------------Code---------------"+act.actCode+"---------------Nom---------------"+act.actNom);
      }
    }
  }

  ngAfterViewInit() {
  }

  isScreenSmall(): boolean {
    return this.mediaMatcher.matches;
  }

  toggleDir() {
    this.dir = this.dir == 'ltr' ? 'rtl' : 'ltr';
    sessionStorage.setItem("dir", this.dir);
  }
}
