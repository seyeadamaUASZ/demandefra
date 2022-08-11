import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar, MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../models/user';
import { DetailUtilisComponent } from '../detail-utilis/detail-utilis.component';
import { UserService } from '../../services/user.service';
import { MonCompteService } from '../mon-compte/services/mon-compte.service';
import { ParametreService } from '../../services/parametre.service';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { ImageCroppedEvent } from 'ngx-image-cropper';
import { MonCompteComponent } from '../mon-compte/mon-compte.component';

@Component({
  selector: 'app-edit-logo-compte',
  templateUrl: './edit-logo-compte.component.html',
  styleUrls: ['./edit-logo-compte.component.scss']
})
export class EditLogoCompteComponent implements OnInit {
  user: User = null;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  logo: any;
  files: any;
  accept: any;
  dataSource: MatTableDataSource<User>;
  isDisabled: boolean = false;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private router: Router, private userService: UserService,
    public paramService: ParametreService, public dialog: MatDialog,

    private route: ActivatedRoute, public dialogRef: MatDialogRef<DetailUtilisComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any, private _snackBar: MatSnackBar, private usersService: UserService, private monCompteService: MonCompteService,
    private translate: TranslateService,
    private notification: NotificationService) {

  }


  ngOnInit() {
    this.detail();
  }
  openUser() {
    this.router.navigateByUrl('/utilisateur')
  }

  detail() {
    this.user = this.donnee;
    this.logo = "data:image/png;base64," + this.user.utiLogo;
    localStorage.setItem('logo', this.logo);

  }

  onSubmit() {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.croppedImage);
    if (this.croppedImage.size > 2000000) {
      this.translate.get('utilisateur.error-img-size').subscribe((res: string) => {
        this.notification.error(res);
      });
    } else {
      this.monCompteService.updateLogoUser(uploadImageData).subscribe(data => {
        if (data.statut) {
          let successEdit;
          this.translate.get('utilisateur.success-edit').subscribe((res: string) => {
            this.notification.success(res);
            location.reload();
          });
          this.closeDialog();
        } else {
          let errorEdit;
          this.translate.get('utilisateur.error-img-size').subscribe((res: string) => {
            this.notification.error(res);
          });
        }

      });
    }
  }

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent) {
    this.logo = event.base64;
    this.croppedImage = this.b64toBlob(event.base64);
  }


  updateParametre() {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.croppedImage, this.croppedImage.name);
    this.paramService.uplaodImage(uploadImageData).subscribe(data => {

    });
  }

  b64toBlob(logo) {

    var byteString = atob(logo.split(',')[1]);
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);

    for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], { type: 'image/jpeg' });
  }


  closeDialog() {
    this.dialogRef.close();
  }
}
