import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar, MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { Profile } from '../../models/profile';
import { User } from '../../models/user';
import { DetailUtilisComponent } from '../detail-utilis/detail-utilis.component';
import { UserService } from '../../services/user.service';
import { MonCompteService } from '../mon-compte/services/mon-compte.service';
import { ParametreService } from '../../services/parametre.service';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { MonCompteComponent } from '../mon-compte/mon-compte.component';

@Component({
  selector: 'app-edit-compte',
  templateUrl: './edit-compte.component.html',
  styleUrls: ['./edit-compte.component.scss']
})
export class EditCompteComponent implements OnInit {
  profiles: any[];
  Langues;
  Themes;
  user: User = null;
  dataSource: MatTableDataSource<User>;
  isDisabled: boolean = false;
  userForm = this.formbuild.group({
    utiId: ['', Validators.required],
    utiPrenom: ['', Validators.required],
    utiNom: ['', Validators.required],
    // utiPassword: ['', Validators.required],
    utiUsername: ['', Validators.required],
    utiTelephone: ['', Validators.required],
    utiEmail: ['', Validators.required],
    utiAdresse: ['', Validators.required],
    // uti_lng_id: ['', Validators.required],
    // uti_thm_id: ['', Validators.required],
  });
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private formbuild: FormBuilder, private router: Router, private userService: UserService,
    public paramService: ParametreService, 


    private route: ActivatedRoute, public dialogRef: MatDialogRef<DetailUtilisComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any, private _snackBar: MatSnackBar, private usersService: UserService, private monCompteService: MonCompteService, 
		private translate: TranslateService,
    private notification:NotificationService) {

  }


  ngOnInit() {
    // this.listLangue();
    // this.listTheme();
    
    this.detail();
  }

  get f() { return this.userForm.controls }
  
  detail() {
    this.user = this.donnee;
    this.userForm.setValue({
      utiId: this.user.utiId,
      utiPrenom: this.user.utiPrenom ? this.user.utiPrenom : null,
      utiNom: this.user.utiNom ? this.user.utiNom : null,
      utiUsername: this.user.utiUsername ? this.user.utiUsername : null,
      utiAdresse: this.user.utiAdresse ? this.user.utiAdresse : null,
      //utiPassword:this.user.utiPassword,
      utiEmail: this.user.utiEmail ? this.user.utiEmail : null,
      utiTelephone: this.user.utiTelephone ? this.user.utiTelephone : null,
      // departement: this.user.departement ? this.user.departement : null,
      // fonction: this.user.fonction ? this.user.fonction : null,
      // uti_pro_id: this.user.uti_pro_id ? this.user.uti_pro_id : null,
      // pays: this.user.pays ? this.user.pays : nul
      // uti_lng_id: this.user.uti_lng_id ? this.user.uti_lng_id : null,
      // uti_thm_id: this.user.uti_thm_id ? this.user.uti_thm_id : null,
    });

  }
  // listLangue() {
  //   this.paramService.getLangue().subscribe(data => {
  //     this.Langues = data.data;
  //   });
  // }



  // listTheme() {
  //   this.paramService.getTheme().subscribe(data => {
  //     this.Themes = data.data;
  //   });
  // }

  onSubmit() {
    if (this.userForm.valid) {
      this.monCompteService.updateCompte(this.userForm.value).subscribe(data => {
        if (data.statut) {
          let successEdit;
          this.translate.get('utilisateur.success-edit').subscribe((res: string) => {           
            this.notification.success(res);
          });         
          this.closeDialog();
        } else {
          let errorEdit;
          this.translate.get('utilisateur.error-edit').subscribe((res: string) => {          
            this.notification.error(res);
          });          
        }

      });
    } else {
      let invalidForm;
      this.translate.get('utilisateur.invalid-form').subscribe((res: string) => {       
         this.notification.error(res);
      });
    }
  }


  closeDialog() {
    this.dialogRef.close();
  }
 
}
