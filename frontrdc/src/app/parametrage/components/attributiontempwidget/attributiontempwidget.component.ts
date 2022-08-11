import { Component, Inject, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core'; 
import { WidgetService } from '../../services/widget.service';
import { TemplateWidget } from '../../models/templatewidget';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-attributiontempwidget',
  templateUrl: './attributiontempwidget.component.html',
  styleUrls: ['./attributiontempwidget.component.scss']
})
export class AttributiontempwidgetComponent implements OnInit {
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
  widgetTempNoAttr = [];
  widgetTemAttr = []; 
  resultwidgetselect = [] 
  profiles: any[];
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
    public dialogRef: MatDialogRef<AttributiontempwidgetComponent>,@Inject(MAT_DIALOG_DATA) public element: any) { }

  ngOnInit() {
   
    this.idwidget = this.element 
    this.listTemplateWidget()
    this.userService.listprofil().subscribe(res => {
      this.profiles = res.data;

});
  }
  get gg() { return this.registreForm.controls; }
  listTemplateWidget() {
    this.widgetService.listeTemplateWidget().subscribe(data => {
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
  }



  save() { 
    console.log(JSON.stringify(this.widgetTemAttr))
    for (let i = 0; i < this.widgetTemAttr.length; i++) {   
       
        this.resultwidgetselect[i] = this.widgetTemAttr[i]
    }
    console.log("++++++++++ ++++++++++++++"+JSON.stringify(this.resultwidgetselect)); 
      let formData = new FormData();
      formData.append("idprofil", JSON.stringify(this.registreForm.value.uti_pro_id));
    formData.append("idwidget", JSON.stringify(this.idwidget));
    formData.append("widgetTemAttr", JSON.stringify(this.resultwidgetselect));
    console.log("++++++++++ profil++++++++++++++"+JSON.stringify(this.registreForm.value.uti_pro_id));
   this.widgetService.attrTemplateWidget(formData).subscribe(data => { 
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