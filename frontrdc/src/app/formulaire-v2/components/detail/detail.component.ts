import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { Formulairev2Service } from '../../services/formulairev2.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private dialog: MatDialog,
    private dialogRef: MatDialog,
    private notification: NotificationService, 
    private formulaireService: Formulairev2Service, 
    private translate: TranslateService, 
    @Inject(MAT_DIALOG_DATA) public donnee: any) { }

    formChampsList;
  ngOnInit() {
    this.formChampsList = this.donnee
    this.formChampsList.steps.sort((a:any,b:any) => a.id-b.id);
          
    for (let i =0 ; i < this.formChampsList.steps.length;i++){

        this.formChampsList.steps[i].champs.sort((a:any,b:any)=>a.chpId-b.chpId)
      
    }
    
  }

}
