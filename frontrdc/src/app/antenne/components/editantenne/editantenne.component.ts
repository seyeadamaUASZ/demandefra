import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { RegionService } from 'src/app/region/service/region.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { AntenneService } from '../../service/antenne.service';

@Component({
  selector: 'app-edit-antenne',
  templateUrl: './editantenne.component.html',
  styleUrls: ['./editantenne.component.scss']
})
export class EditantenneComponent implements OnInit {
  regions: any;

  EditForm = this.formBuilder.group({
    codeAntenne:[this.donnee.codeAntenne,Validators.required],
    nomAntenne:[this.donnee.nomAntenne,Validators.required],
    region: [this.donnee.region.nomRegion, Validators.required]
  });

  constructor(private antenneService: AntenneService,
    private regionService: RegionService,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialog: MatDialogRef<EditantenneComponent>) { }

  ngOnInit() {
    console.log(this.donnee)
    this.regionService.getAllRegion().subscribe((data: any) => {
      this.regions = data.data;
    })
  }

  onSubmit(){
    this.antenneService.updateAntenne(this.donnee.id,this.EditForm.value).subscribe((data:any) => {
      if (data.statut) {
        this.translate.get('antenne.confirmEnr').subscribe((res: string) => {
          this.notification.success(res);

        });
        this.dialog.close({status:true});
        this.EditForm.reset();
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
