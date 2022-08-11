import { Component, Inject, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';  
import { UserService } from 'src/app/utilisateur/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { WorkflowService } from '../../services/workflow.service';

@Component({
  selector: 'app-add-reglegestion',
  templateUrl: './add-reglegestion.component.html',
  styleUrls: ['./add-reglegestion.component.scss']
})
export class AddReglegestionComponent implements OnInit {
 
  idwidget:any
  widgetTempNoAttr = [];
  widgetTemAttr = []; 
  statusNoAttr = [];
  statusAttr  = [];
  resultwidgetselect = [] 
  profiles: any[];
  status: any[];
  idparamwidget:any
  idwrkf:any
  tskId:any
  public registreForm = this.formbuild.group({ 
    titrereglegestion: ['', Validators.required],
    wrkformdonnee: ['', Validators.required],
    variablecondition: ['', Validators.required], 
    condition: ['', Validators.required],
    donneecondition: ['', Validators.required],
    workflowid: ['', Validators.required],
    taskid: ['', Validators.required],
});
widgetAllreadyattr = [] 
attributionbackend = [] 
restant1 = []
champsextract:any[] 
wrkform:any
idform:any
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private notification: NotificationService, 
    private translate: TranslateService,  
    private workflowService: WorkflowService,
    private userService: UserService,
    private formbuild: FormBuilder,
    public dialogRef: MatDialogRef<AddReglegestionComponent>,@Inject(MAT_DIALOG_DATA) public elementform: any) { 
    this.idwrkf = this.elementform.wkfid
    this.tskId = this.elementform.tskId
     }

  ngOnInit() {
     
    //this.recupChampsFormParam(this.idwrkf)  
    this.listFormAjouterv2()
    this.registreForm.controls['workflowid'].setValue(this.idwrkf);
    this.registreForm.controls['taskid'].setValue(this.tskId);
  }


  onChange(value) {
    //alert("++++++"+JSON.stringify(value));
    this.idform = value
    this.workflowService.recupChampsFormParam(this.idform).subscribe(data => {
      if (data.statut) {
        this.champsextract = data.data;  
         console.log("+++++++++++++++++++++++++++++++++++++r++++++++++++++++"+JSON.stringify(this.champsextract));
      } else {
        //console.log(data.description);
      }

    })
} 
  getForm() {
    return this.wrkform
    }
     
    getChamps() {
    return  this.champsextract
    }

    listFormAjouterv2() {
    this.workflowService.listFormAjouterv2().subscribe(data => {
    if(data.statut){
      this.wrkform = data.data; 
      console.log("+++++++++++++++++++++++++++++++++++++r++++++++++++++++"+JSON.stringify(this.wrkform)) 
    }

    })
  } 


  /*recupChampsFormParam(wkfId){
    this.workflowService.recupChampsFormParam(this.idwrkf).subscribe(data => {
      if (data.statut) {
        this.champsextract = data.data;  
         //console.log("+++++++++++++++++++++++++++++++++++++r++++++++++++++++"+JSON.stringify(this.champsextract));
      } else {
        //console.log(data.description);
      }

    })
  }*/
  
 

 save() { 
    let formData = new FormData();
      formData.append("reglegestionform", JSON.stringify(this.registreForm.value));  
   this.workflowService.addreglegestion(formData).subscribe(data => { 
      if (data) { 
        
        this.closeDialog();
      }
    }, error => {
      this.translate.get('Error.internalservererror').subscribe((res: string) => {
         this.notification.warn(res);
      }); 
    }) 
  } 
 

  closeDialog() {
    this.dialogRef.close();
  }

}