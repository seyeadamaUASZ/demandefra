import { Component, OnInit } from '@angular/core';
import { MonCompteService } from './services/mon-compte.service';
import { User } from '../../models/user';
import { MatDialog } from '@angular/material';
import { EditUtilisComponent } from '../edit-utilis/edit-utilis.component';
import { EditCompteComponent } from '../edit-compte/edit-compte.component';
import { DatePipe } from '@angular/common';
import { EditLogoCompteComponent } from '../edit-logo-compte/edit-logo-compte.component';
import { ChangePwdComponent } from '../change-pwd/change-pwd.component';
import { StyleManagerService } from 'src/app/shared/style-manager.service';
//import { DatePipe } from '@angular/common';
import { ParametreService } from 'src/app/utilisateur/services/parametre.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';

@Component({
  selector: 'app-mon-compte',
  templateUrl: './mon-compte.component.html',
  styleUrls: ['./mon-compte.component.scss']
})
export class MonCompteComponent implements OnInit {

  profil:any;
  prerogatives:any;
  datePipeString;
  logo: any;
  Themes;
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


  compte: User;
  constructor(private compteService: MonCompteService, private datePipe: DatePipe,
  private dialogRef: MatDialog,
  
  public styleManager: StyleManagerService,
  private router: Router,
  public paramService: ParametreService,
  private formbuild: FormBuilder, private monCompteService: MonCompteService,public userService: UserService

 ) { }

  ngOnInit() {
    this.infosDuCompte();
    this.listTheme();
  }
  infosDuCompte() {
    this.compteService.infoCompte().subscribe(data => {
      this.compte = data;
      this.logo = "data:image/png;base64," + this.compte.utiLogo;
      //localStorage.setItem('logo',this.logo);
      this.datePipeString = this.datePipe.transform(this.compte.utiDateCreation, 'dd-MM-yyyy à hh:mm');
    })
  }
	openDialogUpdate(username) {
		//console.log(username);
		const dialog1 = this.dialogRef.open(EditCompteComponent, {
      disableClose: true,
			width: '400px',
			data: username
		}).afterClosed().subscribe(result => {
			//location.reload();
      this.infosDuCompte();
    });

  }

  installerTheme(themeName: string) {
    this.styleManager.setStyle('theme', themeName);
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
  getThemeId(theme) {
    this.userForm.value.uti_thm_id = theme;
    this.userForm.value.uti_lng_id = null;
    this.fonctionUpdateTheme();
  }
  fonctionUpdateTheme() {
    this.userService.updateThemeUser(this.userForm.value).subscribe(data => {
    
    });
  }

  listTheme() {
    this.paramService.getTheme().subscribe(data => {
      this.Themes = data.data;
    });
  }

	openDialogUpdateLogo(username) {
		const dialog1 = this.dialogRef.open(EditLogoCompteComponent, {
      disableClose: true,
			data: username
		}).afterClosed().subscribe(result => {
      this.infosDuCompte();
    });

  }
  openPassword(){
    const dialog1 = this.dialogRef.open(ChangePwdComponent, {
      disableClose: true,
      width: '500px',
    });
  }
  close(){
    this.dialogRef.closeAll();
  }
  
}
