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
  selector: 'app-editconfigattribut',
  templateUrl: './editconfigattribut.component.html',
  styleUrls: ['./editconfigattribut.component.scss']
})
export class EditconfigattributComponent implements OnInit {
  idwtemplate:any
  idconfigwidget:any
  idparam:any
  widgetTempNoAttr = [];
  widgetTemAttr = []; 
  statusNoAttr = [];
  statusAttr  = [];
  resultwidgetselect = [] 
  profiles: any[];
  status: any[];
  idparamwidget:any
  public registreForm = this.formbuild.group({ 
    wtemplate: ['', Validators.required],
    attributabscisse: ['', Validators.required], 
    variableordonnee: ['', Validators.required],
    idparamwidget: ['', Validators.required],
    idparamattrwidget:['', Validators.required],
});
widgetAllreadyattr = [] 
attributionbackend = [] 
restant1 = []
champsextract:any[] 
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private notification: NotificationService, 
    private translate: TranslateService,  
    private workflowService: WorkflowService,
    private userService: UserService,
    private formbuild: FormBuilder,
    public dialogRef: MatDialogRef<EditconfigattributComponent>,@Inject(MAT_DIALOG_DATA) public element: any) { }

  ngOnInit() {
    this.idwtemplate = this.element.widgetTemplate.wtId
    this.idconfigwidget = this.element  
     
    this.TemplateAllreadyAttr(this.element.parametrageWidget.widget.wdgId)
    this.recupChampsFormParam(this.element.parametrageWidget.workflow.wkfId)  
    this.idparamwidget = this.element.pwattrid 
    this.registreForm.controls['idparamwidget'].setValue(this.element.parametrageWidget.pwId);
    this.registreForm.controls['idparamattrwidget'].setValue(this.idparamwidget);
   // console.log("+++++++++++++++++++++++++++++++++++++r++++++++++++++++"+JSON.stringify(this.idparamwidget));
  }

    
  recupChampsFormParam(wkfId){
    this.workflowService.recupChampsFormParam(this.element.parametrageWidget.workflow.wkfId).subscribe(data => {
      if (data.statut) {
        this.champsextract = data.data;  
         //console.log("+++++++++++++++++++++++++++++++++++++r++++++++++++++++"+JSON.stringify(this.champsextract));
      } else {
        //console.log(data.description);
      }

    })
  }
  TemplateAllreadyAttr(wdgId) {
    this.workflowService.widgetTemplatesAttr(this.element.parametrageWidget.widget.wdgId).subscribe(data => {
      if (data.statut) {
        this.widgetAllreadyattr = data.data;  
        this.attributionbackend = data.data;
        //console.log("+++++++++++widgetAllreadyattr++++++++++++++++"+JSON.stringify(this.widgetAllreadyattr)); 
        for (let i = 0; i < this.widgetAllreadyattr.length; i++) {  
          this.restant1.push(this.widgetAllreadyattr[i]["widgetTemplate"])
         
           
        }  
       // console.log("++++++++++++restant1+++++++++++++++"+JSON.stringify(this.restant1));
      } else {
        //console.log(data.description);
      }

    })
  }
  get gg() { return this.registreForm.controls; }
  
   


 save() { 
  //console.log("++++++++++ ++++++++++++++"+JSON.stringify(this.registreForm.value)); 
  let formData = new FormData();
      formData.append("configform", JSON.stringify(this.registreForm.value)); 
   this.workflowService.updtateAttributionConfigWidget(formData).subscribe(data => { 
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