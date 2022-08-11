import { Component, Inject, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core'; 
import { NotificationService } from 'src/app/shared/services/notification.service'; 
import { WidgetService } from '../../services/widget.service';

@Component({
  selector: 'app-editwidget',
  templateUrl: './editwidget.component.html',
  styleUrls: ['./editwidget.component.scss']
})
export class EditwidgetComponent implements OnInit {
  widgetelement:any
  addForm = this.formbuild.group({
    wdgId:['', Validators.required],
    wdgNom: ['', Validators.required],  
    wdgCode: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9_]*$')]],  
    wdgDescription:['', Validators.required],  
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private translate:TranslateService,private notification:NotificationService,private widgetService:WidgetService,
    public dialogRef: MatDialogRef<EditwidgetComponent>,@Inject(MAT_DIALOG_DATA) public element: any) {

  }

  ngOnInit() {
    this.detailwidget()
  }

  detailwidget() {
    this.widgetelement = this.element;
    this.addForm.setValue({
      wdgId: this.widgetelement.wdgId,
      wdgCode: this.widgetelement.wdgCode,
      wdgNom: this.widgetelement.wdgNom,
      wdgDescription: this.widgetelement.wdgDescription,
    });

  }
  get f() {   return this.addForm.controls;}
  onSubmit() {     
    if (this.addForm.valid) { 
      this.widgetService.updateWidget(this.addForm.value).subscribe(data => { 
          
      if (data) {
        // console.log(data);
        this.translate.get('widget.edit').subscribe((res: string) => {           
         this.notification.success(res);
       });  
         this.addForm.reset();
    

         this.closeDialog();
       }
     }, error => {
       this.translate.get('widget.edit').subscribe((res: string) => {           
         this.notification.success(res);
       }); 
           
     })
  }else {
    this.notification.warn('Formulaire invalide');    
  }
  }

  closeDialog() {
    this.dialogRef.close();
  }

  

}
