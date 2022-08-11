import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Profile } from '../../models/profile';
import { Validators, FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { ParametreService } from '../../services/parametre.service';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { AntenneService } from 'src/app/antenne/service/antenne.service';

@Component({
  selector: 'app-edit-utilis',
  templateUrl: './edit-utilis.component.html',
  styleUrls: ['./edit-utilis.component.scss']
})
export class EditUtilisComponent implements OnInit {
  profiles: any[];
  antennes: any[];

  user: any = null;
  Langues;
  Themes;
  dataSource: MatTableDataSource<User>;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private formbuild: FormBuilder, private router: Router,
    public paramService: ParametreService,
    private userService: UserService,
    private route: ActivatedRoute, public dialogRef: MatDialogRef<EditUtilisComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any, private _snackBar: MatSnackBar, private usersService: UserService,
    private translate: TranslateService,
    private notification: NotificationService,
    private antenneService: AntenneService) {

  }

  userForm = this.formbuild.group({
    utiId: ['', Validators.required],
    utiPrenom: ['', Validators.required],
    utiNom: ['', Validators.required],
    // utiPassword: ['', Validators.required],
    utiUsername: ['', Validators.required],
    utiTelephone: ['', Validators.required],
    utiEmail: ['', Validators.required],
    utiAdresse: ['', Validators.required],
    uti_pro_id: ['', Validators.required],
    //uti_lng_id: ['', Validators.required],
    //uti_thm_id: ['', Validators.required]
    antenne: ['']

  });
  profileset: Profile = null;

  ngOnInit() {
    this.userService.listprofil().subscribe(data => {
      this.profiles = data.data;
    });
    this.detail();

    this.antenneService.getAllAntenne().subscribe((res: any) => {
      this.antennes = res.data;
    })
  }

  get f() { return this.userForm.controls; }
  detail() {
    this.user = this.donnee;
    this.profileset = this.donnee.uti_pro_id;
    this.userForm.setValue({
      utiId: this.user.utiId,
      utiPrenom: this.user.utiPrenom ? this.user.utiPrenom : null,
      utiNom: this.user.utiNom ? this.user.utiNom : null,
      utiUsername: this.user.utiUsername ? this.user.utiUsername : null,
      utiAdresse: this.user.utiAdresse ? this.user.utiAdresse : null,
      utiEmail: this.user.utiEmail ? this.user.utiEmail : null,
      utiTelephone: this.user.utiTelephone ? this.user.utiTelephone : null,
      uti_pro_id: this.user.uti_pro_id ? this.user.uti_pro_id : null,

      antenne: (this.user?.antenne != null && this.user?.antenne!=undefined) ? this.user?.antenne : ''
    });
  }
  listUsers() {
    this.usersService.listeUser().subscribe(data => {
      if (data.statut) {
        this.user = data.data;
        this.dataSource = new MatTableDataSource<User>(data.data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.warn(res);
        });
      }
    })
  }

  onSubmit() {
    if (this.userForm.valid) {
      if(this.userForm.value.antenne == "") {
        this.userForm.value.antenne = null;
      }
      this.userService.updateUser(this.userForm.value).subscribe(data => {
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
  
  compareProfil(p1, p2): boolean {
    return p1 && p2 ? p1.proId === p2.proId : false;
  }
  compareLangue(l1, l2): boolean {
    return l1 && l2 ? l1.lngLangue === l2.lngLangue : false;
  }
  compareTheme(t1, t2): boolean {
    return t1 && t2 ? t1.thmName === t2.thmName : false;
  }


  closeDialog() {
    this.dialogRef.close();
  }
}
