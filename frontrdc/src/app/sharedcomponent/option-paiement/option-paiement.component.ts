import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { DemandeAutFraService } from 'src/app/demandeAutFra/service/demandeAutFra.service';
import { NotificationService } from 'src/app/shared/services/notification.service';

@Component({
  selector: 'app-option-paiement',
  templateUrl: './option-paiement.component.html',
  styleUrls: ['./option-paiement.component.scss']
})
export class OptionPaiementComponent implements OnInit {
  manuel: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) private demande,
  private dialogRef:MatDialogRef<OptionPaiementComponent>,
  private translate: TranslateService,
  private notification:NotificationService,
  private demandeAutFraService:DemandeAutFraService) { }

  ngOnInit() {
  }

  @ViewChild('filerecupaiement') filerecupaiement;

  recupaiement: File;

  addFilerecupaiement() {
    this.filerecupaiement.nativeElement.click();
  }
  extension
  onFileAddedrecupaiement() {
    this.recupaiement = this.filerecupaiement.nativeElement.files[0];
      this.extension = this.recupaiement.name.split('.')[1].toLowerCase();
      if ('pdf' != this.extension && 'png'!= this.extension && "jpeg"!=this.extension && "jpg"!=this.extension) {
        this.translate.get('demandeAutFra.fileExtenxionNotif').subscribe((res: string) => {
          this.notification.warn(res);
        });
        this.recupaiement = null;
        return;
      }
      if (this.recupaiement.size > 3000000) {
        this.translate.get('demandeAutFra.fileSizeNotif').subscribe((res: string) => {
          this.notification.warn(res);
        });
        this.recupaiement = null;
        return;
      }
  }

  selectTypePaiementManuel(manuel:boolean){
    this.manuel = manuel;
    if(this.manuel){

    }else{
      this.recupaiement=null;
    }
  }

  paiementManuel(){
    this.demandeAutFraService.paiementManuel(this.demande.id,this.extension,this.recupaiement).subscribe((data)=>{
      if(data.statut){
        this.notification.success('demandeautfra.enregistrementok');
        this.dialogRef.close({status:true});
      }else{
        this.notification.warn('demandeautfra.echecenregistrement');
      }
    })
  }
}
