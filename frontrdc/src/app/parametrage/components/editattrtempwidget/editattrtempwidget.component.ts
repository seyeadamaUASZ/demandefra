import { Component, Inject, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core'; 
import { WidgetService } from '../../services/widget.service';
import { TemplateWidget } from '../../models/templatewidget';
import { field, value} from 'src/app/global.model';
import { UserService } from '../../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Widget } from 'src/app/home/models/widget';
@Component({
  selector: 'app-editattrtempwidget',
  templateUrl: './editattrtempwidget.component.html',
  styleUrls: ['./editattrtempwidget.component.scss']
})
export class EditattrtempwidgetComponent implements OnInit {
  items = [
    'Carrots',
    'Tomatoes',
    'Onions',
    'Apples',
    'Avocados'
  ];

  basket = [
    'Oranges',
    'Bananas',
    'Cucumbers'
  ];
  idwidget:any
  idwdg:any
  widget : Widget
  widgetTempNoAttr = [];
  widgetTemAttr = []; 
  resultwidgetselect = []
  widgetAllreadyattr = [] 
  widgetAllNotattr = [] 
  getwidgetAllreadyattr = [] 
  templatebywidget = []
  restant = []
  restant1 = []
  attributionbackend= []
  profiles: any[];
  instanceprofil:any[]
  public registreForm = this.formbuild.group({ 
    uti_pro_id: ['', Validators.required]
});
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private notification: NotificationService, 
    private translate: TranslateService,  
    private widgetService: WidgetService,
    private userService: UserService,
    private formbuild: FormBuilder,
    public dialogRef: MatDialogRef<EditattrtempwidgetComponent>,@Inject(MAT_DIALOG_DATA) public element: any) { }

  ngOnInit() {
   
    this.idwidget = this.element
    this.widget = this.element
    this.idwdg = this.element.wdgId
    this.listTemplateWidget() 
     this.TemplateAllreadyAttr(this.idwdg) 
     this.TemplateAllNotAttr(this.idwdg)
     this.userService.listprofil().subscribe(res => {
      this.profiles = res.data;

});
  }
  get g() { return this.registreForm.controls; }
  listTemplateWidget() {
    this.widgetService.listeTemplateWidget().subscribe(data => {
      if (data.statut) {
        this.widgetTempNoAttr = data.data; 
        this.widgetTemAttr = []
        console.log("------------------widgetTempNoAttr------------------------"+JSON.stringify(this.widgetTempNoAttr));
      } else {
        //console.log(data.description);
      }

    })
  }

   
 TemplateAllreadyAttr(idwdg) {
    this.widgetService.widgetTemplatesAttr(this.idwdg).subscribe(data => {
      if (data.statut) {
        this.widgetAllreadyattr = data.data;  
        this.attributionbackend = data.data;
        console.log("+++++++++++widgetAllreadyattr++++++++++++++++"+JSON.stringify(this.widgetAllreadyattr)); 
        for (let i = 0; i < this.widgetAllreadyattr.length; i++) {  
          this.restant1.push(this.widgetAllreadyattr[i]["widgetTemplate"])
         
           
        }  
        console.log("++++++++++++restant1+++++++++++++++"+JSON.stringify(this.restant1));
      } else {
        //console.log(data.description);
      }

    })
  }

  TemplateAllNotAttr(idwdg) {
    this.widgetService.widgetTemplatesNotAttr(this.idwdg).subscribe(data => {
      if (data.statut) {
        this.widgetAllNotattr = data.data;   
        this.restant = this.difference(this.widgetTempNoAttr, this.restant1);      
        console.log("------------------restant------------------------"+JSON.stringify(this.restant));
       
       /* for (let i = 0; i < this.widgetAllreadyattr.length; i++) {  
          console.log("+++++++++++++++++++++++++++"+JSON.stringify(this.widgetAllreadyattr[i]["widgetTemplate"]));
           
        } */
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
    result = wedgets1.filter(item1 => !wedgets2.some(item2 => (item2.wtId === item1.wtId)));
    return result;
  }


   
  update() { 
    console.log(JSON.stringify(this.attributionbackend))
    for (let i = 0; i < this.attributionbackend.length; i++) {   
       
        this.resultwidgetselect[i] = this.attributionbackend[i]["widgetTemplate"]
    }
    console.log("++++++++++ ++++++++++++++"+JSON.stringify(this.resultwidgetselect)); 
    //this.instanceprofil = this.registreForm.value.uti_pro_id
      let formData = new FormData();
     /* formData.append("idprofil", JSON.stringify(this.registreForm.value.uti_pro_id));
      formData.append("idwidget", JSON.stringify(this.widget));
    formData.append("removed", JSON.stringify(this.restant));
    formData.append("added", JSON.stringify(this.restant1)); */
    formData.append("idprofil", JSON.stringify(this.registreForm.value.uti_pro_id));
      formData.append("idwidget", JSON.stringify(this.widget)); 
    formData.append("widgetTemAttr", JSON.stringify(this.restant1));
  this.widgetService.allocateTemplate(formData).subscribe(data => { 
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