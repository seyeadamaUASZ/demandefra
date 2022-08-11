import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { RegionService } from 'src/app/region/service/region.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { AntenneService } from '../../service/antenne.service';

@Component({
  selector: 'app-add-antenne',
  templateUrl: './addantenne.component.html',
  styleUrls: ['./addantenne.component.scss']
})
export class AddantenneComponent implements OnInit {
  regions: any;

  constructor(private antenneService:AntenneService,
    private regionService: RegionService,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialog: MatDialogRef<AddantenneComponent> ) { }

  EditForm = this.formBuilder.group({
    id: [''],
    codeAntenne:[''],
    nomAntenne:[''],
    poOwner: [''],
    owner: [''],
    status: [''],
    region: ['']
  });

  ngOnInit() {
    this.regionService.getAllRegion().subscribe((data: any) => {
      this.regions = data.data;
    })
  }

  onSubmit(){
    this.EditForm.value.owner = localStorage.getItem('id');
    this.EditForm.value.poOwner = localStorage.getItem('profil');
    this.antenneService.createAntenne(this.EditForm.value ).subscribe((data:any) => {
      if (data.statut) {
          this.translate.get('antenne.confirmEnr').subscribe((res: string) => {
            this.notification.success(res);
          });

          this.EditForm.reset();
          this.dialog.close({status:false});
        }
      }, error => {
        let ReportSaveError;
        this.translate.get('antenne.erreurEnr').subscribe((res: string) => {
          this.notification.error(res);
        });
      });

  	}

  	closeDialog(){
  		this.dialog.close({status:false});
  	}

}
