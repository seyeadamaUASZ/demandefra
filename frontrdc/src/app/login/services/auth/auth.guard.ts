import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, NavigationExtras } from '@angular/router';
import { Observable } from 'rxjs';
import { isNull } from 'util';
import { AuthService } from 'src/app/utilisateur/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(public auth: AuthService, public router: Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    //console.log("------------Menus authorised---------------"+sessionStorage.getItem('menus'));
    if (!this.auth.isAuthenticated()) {
      localStorage.removeItem('token');
      this.router.navigate(['login']);
      return false;
    }

     //console.log("---------------user logged url---------------"+JSON.stringify(route.url));
     //console.log("---------------user logged route---------------"+JSON.stringify(route));
    if(sessionStorage.getItem('accesmenus') && !this.verifAccess(state.url)){
       //alert("---------------user logged url---------------"+state.url);
       this.router.navigate(['exception/unauthorized']);
      return false;
    }
    return true;
  }

  verifAccess(url){
    //console.log("------------Menus authorised---------------"+JSON.stringify(sessionStorage.getItem('accesmenus')));
    let menuList = JSON.parse(sessionStorage.getItem('accesmenus'));
    for (let menu of menuList) {
      //alert("--------------Menu path------------"+menu.menPath);
      if(url.startsWith(menu.menPath)){
        return true
      }
    }
    return false;
  }

}
