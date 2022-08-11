import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { RegionService } from '../../service/region.service';

@Component({
  selector: 'app-add-region',
  templateUrl: './addregion.component.html',
  styleUrls: ['./addregion.component.scss']
})
export class AddregionComponent implements OnInit {

  constructor(private regionService:RegionService ,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialog: MatDialogRef<AddregionComponent> ) { }

  ngOnInit() {
  }

  EditForm = this.formBuilder.group({
    id: [''],
    codeRegion:[''],
    nomRegion:[''],
    poOwner: [''],
    owner: [''],
    status: ['']
  });


  onSubmit(){
      this.EditForm.value.owner = localStorage.getItem('id');
      this.EditForm.value.poOwner = localStorage.getItem('profil');
      this.regionService.createRegion(this.EditForm.value ).subscribe((data:any) => {
        if (data.statut) {
          this.translate.get('region.confirmEnr').subscribe((res: string) => {
            this.notification.success(res);
          });

          this.EditForm.reset();
          this.dialog.close({status:false});
        }
      }, error => {
        let ReportSaveError;
        this.translate.get('categorie.erreurEnr').subscribe((res: string) => {
          this.notification.error(res);
        });
      });

  	}

  	closeDialog(){
  		this.dialog.close({status:false});
  	}

}
