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
  selector: 'app-addconfigattribut',
  templateUrl: './addconfigattribut.component.html',
  styleUrls: ['./addconfigattribut.component.scss']
})
export class AddconfigattributComponent implements OnInit {
 
  idwidget:any
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
    public dialogRef: MatDialogRef<AddconfigattributComponent>,@Inject(MAT_DIALOG_DATA) public element: any) { }

  ngOnInit() {
   
    this.idwidget = this.element  
    this.extractStatusId()
    this.TemplateAllreadyAttr(this.element.wdgId)
    this.recupChampsFormParam(this.element.workflow.wkfId)  
    this.idparamwidget = this.element.pwId
    this.registreForm.controls['idparamwidget'].setValue(this.idparamwidget);
    this.userService.listprofil().subscribe(res => {
      this.profiles = res.data; 
}); 
  }

    
  recupChampsFormParam(wkfId){
    this.workflowService.recupChampsFormParam(this.element.workflow.wkfId).subscribe(data => {
      if (data.statut) {
        this.champsextract = data.data;  
         //console.log("+++++++++++++++++++++++++++++++++++++r++++++++++++++++"+JSON.stringify(this.champsextract));
      } else {
        //console.log(data.description);
      }

    })
  }
  TemplateAllreadyAttr(wdgId) {
    this.workflowService.widgetTemplatesAttr(this.element.widget.wdgId).subscribe(data => {
      if (data.statut) {
        this.widgetAllreadyattr = data.data;  
        this.attributionbackend = data.data;
        //console.log("+++++++++++widgetAllreadyattr++++++++++++++++"+JSON.stringify(this.widgetAllreadyattr)); 
        for (let i = 0; i < this.widgetAllreadyattr.length; i++) {  
          this.restant1.push(this.widgetAllreadyattr[i]["widgetTemplate"])
         
           
        }  
        //console.log("++++++++++++restant1+++++++++++++++"+JSON.stringify(this.restant1));
      } else {
        //console.log(data.description);
      }

    })
  }
  get gg() { return this.registreForm.controls; }
 /* listTemplateWidget() {
    this.workflowService.listeTemplateWidget().subscribe(data => {
      if (data.statut) {
        this.widgetTempNoAttr = data.data; 
        this.widgetTemAttr = []
      } else {
        //console.log(data.description);
      }

    })
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
                        event.container.data,
                        event.previousIndex,
                        event.currentIndex);
    }
  }


  intersect(wedgets1: any[], wedgets2: any[]) {
    let result = [];
    wedgets1.map(function (item1) {
      wedgets2.map(function (item2) {
        if (item1.wdgId === item2.wdgId) {
          result.push(item1);
        }
      })
    });
    return result;
  }

  difference(wedgets1: any[], wedgets2: any[]) {
    let result = [];
    result = wedgets1.filter(item1 => !wedgets2.some(item2 => (item2.wdgId === item1.wdgId)));
    return result;
  }*/

  /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
  extractStatusId(){
    this.workflowService.taskStatusAll().subscribe(data => {
      
      if (data.statut) {
        this.statusNoAttr = data.data; 
        this.statusAttr = []
      } else {
        //console.log(data.description);
      }
  
  });
  }
  dropStatus(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
                        event.container.data,
                        event.previousIndex,
                        event.currentIndex);
    }
  }


  intersectStatus(wedgets1: any[], wedgets2: any[]) {
    let result = [];
    wedgets1.map(function (item1) {
      wedgets2.map(function (item2) {
        if (item1.wdgId === item2.wdgId) {
          result.push(item1);
        }
      })
    });
    return result;
  }

  differencesStatus(wedgets1: any[], wedgets2: any[]) {
    let result = [];
    result = wedgets1.filter(item1 => !wedgets2.some(item2 => (item2.wdgId === item1.wdgId)));
    return result;
  }


 save() { 
   /*  console.log(JSON.stringify(this.widgetTemAttr))
    for (let i = 0; i < this.widgetTemAttr.length; i++) {   
       
        this.resultwidgetselect[i] = this.widgetTemAttr[i]
    }*/
    //console.log("++++++++++ ++++++++++++++"+JSON.stringify(this.registreForm.value)); 
    let formData = new FormData();
      formData.append("configform", JSON.stringify(this.registreForm.value)); 
   this.workflowService.attributionConfigWidget(formData).subscribe(data => { 
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