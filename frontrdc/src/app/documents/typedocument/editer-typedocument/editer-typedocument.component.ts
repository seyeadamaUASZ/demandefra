import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-editer-typedocument',
  templateUrl: './editer-typedocument.component.html',
  styleUrls: ['./editer-typedocument.component.scss']
})
export class EditerTypedocumentComponent implements OnInit {

  FormulaireForm= this.formbuild.group({
    tydId: ['', Validators.required],
    tydLibelle: ['', Validators.required],
    tydDescription: ['', Validators.required],
  });

  constructor( public dialogRef: MatDialogRef<EditerTypedocumentComponent>,  @Inject(MAT_DIALOG_DATA) public document: any, private documentService: DocumentService,
    private formbuild: FormBuilder, private router: Router,
    private notification: NotificationService, private translate:TranslateService) { }

    ngOnInit() {
      this.initView();

    }

    initView() {

      this.FormulaireForm.setValue({
        tydId: this.document.tydId,
        tydLibelle: this.document.tydLibelle ? this.document.tydLibelle : null,
        tydDescription: this.document.tydDescription ? this.document.tydDescription : null,

      });
    }

    onSubmit() {
      this.documentService.addTypeDocument(this.FormulaireForm.value).subscribe(data => {
        if (data.statut) {
          this.translate.get('document.success-edit').subscribe((res: string) => {
            this.notification.success(res);
          });

          this.FormulaireForm.reset();
          this.closeDialog();
        }
      }, error => {let errorEdit;
        this.translate.get('document.error-edit').subscribe((res: string) => {
          this.notification.error(res);
        });

      });
    }





    closeDialog() {
      this.dialogRef.close();
    }

}
