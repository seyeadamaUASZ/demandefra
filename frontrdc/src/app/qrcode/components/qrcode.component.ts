import { Component, OnInit} from '@angular/core';
import {MatDialog, MatSnackBar } from '@angular/material';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router'; 
import { TranslateService } from '@ngx-translate/core';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { AjoutQrcodeComponent } from './ajout-qrcode/ajout-qrcode.component';
import { EditQrcodeComponent } from 'src/app/qrcode/components/edit-qrcode/edit-qrcode.component';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { NotificationService } from 'src/app/shared/services/notification.service';
import * as fileSaver from 'file-saver';
import { filter } from 'rxjs/operators';
import { ModeliseQrcodeComponent } from './modelise-qrcode/modelise-qrcode.component';
import { QrcodeService } from '../services/qrcode.service';

@Component({
  selector: 'app-qrcode',
  templateUrl: './qrcode.component.html',
  styleUrls: ['./qrcode.component.scss']
})
export class QrcodeComponent implements OnInit {
  id
  listeQrcode:any;
  images;
  href:any
  base64Data: any;
  tabIndex = 0;
  based
  qrcQrcodeByte:any
  retreivedResponse:any;
  retreivedImage:any;
  qrcodeModeliser: any;
  loading:any;
  previousUrl: String;
  
  constructor(private router: Router, private qrcodeService: QrcodeService, private _snackBar: MatSnackBar,  private dialog: MatDialog,
    private notification: NotificationService,
    private dialogRef: MatDialog,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar, private usersService: UserService, private translate: TranslateService) {
      this.id = this.route.snapshot.paramMap.get('id');

      router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.previousUrl = event.url;
      });
     }

  ngOnInit() {
    this.getAllQrcode()
    this.listeQrcodeGenerer()
   
  }

  openDialogAddQrcodes() {
    const dialog2 = this.dialog.open(AjoutQrcodeComponent, {
      disableClose: true,
      //  width: '700px',
    }).afterClosed().subscribe(result => {
      this.getAllQrcode();
    });
  }
  openDialogEditQrcodes(q){
    const dialog2 = this.dialog.open(EditQrcodeComponent, {
      disableClose: true,
    //  width: '700px',
      data:q
    }).afterClosed().subscribe(result => {

      this.getAllQrcode();
    });
  }
  
  openDialogDeleteQrcode(qrcode) {
    const message = "qrcodes.alert-suppression";
    const dialogData = new ConfirmDialogModel("qrcodes.delete", message);
    const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.delete(qrcode);
      }
    });
  }
  
  
  delete(qrcode) {
    this.qrcodeService.deleteQrcode(qrcode).subscribe(data => {
      if (data.statut) {
        let invalidForm;
        this.translate.get('qrcodes.application-supprime').subscribe((res: string) => {
           this.notification.success(res);
        });
  
      } else {
        this.translate.get('qrcodes.error').subscribe((res: string) => {
          this.notification.error(res);
       });
      }
      this.getAllQrcode();
  
    });
  
  }

  
  getAllQrcode(){
    this.qrcodeService.getallQrcode().subscribe(data=>{
      this.listeQrcode=data.data
      })
  }
  openDialogEditRapport(qrcode): void {
    const dialog = this.dialogRef.open(ModeliseQrcodeComponent, {
      disableClose: true,
      width: '1000px',
      data:qrcode
    }).afterClosed().subscribe(result => {
      this.listeQrcodeGenerer();
      this.getAllQrcode();
  
    });
  
  }

  listeQrcodeGenerer(){
    this.qrcodeService.getallQrcodeModeliser().subscribe(res=>{
      this.qrcodeModeliser=res.data;
    })
  }

  gotoExprotFile(Id): void{   
    this.router.navigate(['/qrcode/genere'], { 
      state: { 
        data:Id,
        previousUrl: this.previousUrl
      } 
    });    
  }

  openDialogDelete(q) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("fichier suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
    maxWidth: "400px",
    data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if(dialogResult === true){
      this.deleteQrcode(q);
    }
    });
  }
  deleteQrcode(q) {
    let messageSuccess;
    let messageError;
    this.translate.get('fichier.confirmSupp').subscribe((res: string) => {
      messageSuccess = res;
    });
    this.translate.get('fichier.erreurSupp').subscribe((res: string) => {
      messageError = res;
    });
    this.qrcodeService.supprimerQrcode(q).subscribe(data => {
      this.listeQrcodeGenerer();
      this.getAllQrcode();
      if (data.statut) {
        this._snackBar.open(messageSuccess, 'Verification', {
          duration: 2000,
        });
      } else {
        this._snackBar.open(messageError, 'Verification', {
          duration: 2000,
        });
      }
    });
  }

  modeliserQrcode(data){
    this.qrcodeService.modeliserQrcode(data).subscribe(res=>{
      this._snackBar.open(res.description, 'Verification', {
        duration: 2000,
      });
      this.listeQrcodeGenerer();
      this.getAllQrcode();
    })
  }


}
