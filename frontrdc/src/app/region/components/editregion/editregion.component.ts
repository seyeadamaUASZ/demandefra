import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { RegionService } from '../../service/region.service';

@Component({
  selector: 'app-edit-region',
  templateUrl: './editregion.component.html',
  styleUrls: ['./editregion.component.scss']
})
export class EditregionComponent implements OnInit {

  EditForm = this.formBuilder.group({
    codeRegion:[this.donnee.codeRegion,Validators.required],
    nomRegion:[this.donnee.nomRegion,Validators.required],
  });

  constructor(private regionService: RegionService,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialog: MatDialogRef<EditregionComponent>) { }

  ngOnInit() {
  }

  onSubmit(){
    console.log(this.donnee.id)
    this.regionService.updateRegion(this.donnee.id,this.EditForm.value).subscribe((data:any) => {
      if (data.statut) {
        this.translate.get('region.confirmEnr').subscribe((res: string) => {
          this.notification.success(res);

        });
        this.dialog.close({status:true});
        this.EditForm.reset();
        location.reload();
      }
    }, error => {
      let ReportSaveError;
      this.translate.get('region.erreurEnr').subscribe((res: string) => {
        this.notification.error(res);
      });
    });

  }

  closeDialog(){
    this.dialog.close({status:false});
  }

}
