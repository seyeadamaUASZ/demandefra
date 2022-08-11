import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { Langue } from '../../models/langue';
import { LangueService } from '../../services/langue.service';

@Component({
  selector: 'app-edit-langue',
  templateUrl: './edit-langue.component.html',
  styleUrls: ['./edit-langue.component.scss']
})
export class EditLangueComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private langueService: LangueService,
    private notification: NotificationService,public dialogRef: MatDialogRef<EditLangueComponent>,
    private translate:TranslateService,@Inject(MAT_DIALOG_DATA) public data: Langue) { }

  langueForm = this.formBuilder.group({
    lngId: [this.data?.lngId],
    lngCode: [this.data?.lngCode, Validators.required],
    lngLibelleComplet: [this.data?.lngLibelleComplet, Validators.required],
    // lngDispositionText: ['', ],
    lngLangue: [this.data?.lngLangue, Validators.required],
    lngIcone: [this.data?.lngIcone]
  });
  editLangueTitle;
  ngOnInit() {
    this.editLangueTitle = this.data.lngLibelleComplet != undefined? 'configuration.editLangueTitleUpdate':'configuration.editLangueTitleCreation'
  }

  onSubmit() {
    if (this.langueForm.valid) {
    this.langueService.addLangue(this.langueForm.value).subscribe(data => {
      if (data.statut) {
        this.notification.success(data.description);

        this.langueForm.reset();
        this.closeDialog();
      }else{
        this.notification.warn(data.description);
      }
    }, error => {
      this.translate.get('Error.internalservererror').subscribe((res: string) => {
       this.notification.error(res);     
      });
    });
    } else {
      this.translate.get('invalid-form').subscribe((res: string) => {
       this.notification.warn(res);     
      });
  }
  }
  closeDialog(){
    this.dialogRef.close();
  }
}
