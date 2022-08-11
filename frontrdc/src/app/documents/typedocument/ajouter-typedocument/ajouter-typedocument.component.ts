import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-ajouter-typedocument',
  templateUrl: './ajouter-typedocument.component.html',
  styleUrls: ['./ajouter-typedocument.component.scss']
})
export class AjouterTypedocumentComponent implements OnInit {

  FormulaireForm = this.formbuild.group({
    tydLibelle: ['', Validators.required],
    tydDescription:['', Validators.required],
  });
  constructor(private formbuild: FormBuilder, 	private translate:TranslateService,
    private router: Router,
    private usersService:UserService,
    private documentService: DocumentService,
    private notification: NotificationService,
    public dialogRef: MatDialogRef<AjouterTypedocumentComponent>,) { }

  ngOnInit() {
  }
  onSubmit() {
    if (this.FormulaireForm.valid) {
      this.documentService.addTypeDocument(this.FormulaireForm.value).subscribe(data => {
        if (data.statut) {
          let ReportSaveSuccess;
          this.translate.get(data.description).subscribe((res: string) => {
            ReportSaveSuccess = res;
          });
          this.translate.get(ReportSaveSuccess).subscribe((res: string) => {
            this.notification.success(res);
          });

          this.FormulaireForm.reset();
          this.closeDialog();
        }
      }, error => {
        let ReportSaveError;
        this.translate.get('fichier.erreurEnr').subscribe((res: string) => {
          ReportSaveError = res;
        });
        this.translate.get(ReportSaveError).subscribe((res: string) => {
          this.notification.error(res);
        });

      });
    } else {
      let errorChamps;
      let form;
      this.translate.get('typedocument.required').subscribe((res: string) => {
        form = res;
      });

      this.translate.get('typedocument.required').subscribe((res: string) => {
        errorChamps = res;
      });
      this.translate.get(errorChamps).subscribe((res: string) => {
        this.notification.error(res);
      });

    }
  }
  closeDialog() {
    this.dialogRef.close();
  }
}
