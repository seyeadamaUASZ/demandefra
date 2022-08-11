import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core'; 
import { NotificationService } from 'src/app/shared/services/notification.service'; 
import { WidgetService } from '../../services/widget.service';

@Component({
  selector: 'app-addtemplatewidget',
  templateUrl: './addtemplatewidget.component.html',
  styleUrls: ['./addtemplatewidget.component.scss']
})
export class AddtemplatewidgetComponent implements OnInit {
  addForm = this.formbuild.group({
    wtNom: ['', Validators.required],     
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private translate:TranslateService,private notification:NotificationService,private widgetService:WidgetService,
    public dialogRef: MatDialogRef<AddtemplatewidgetComponent>) {

  }

  ngOnInit() {
    
  }

   
  get f() {   return this.addForm.controls;}
  onSubmit() { 
    if (this.addForm.valid) { 
     
      this.widgetService.createTemplateWidget(this.addForm.value).subscribe(data => { 
          
      if (data) {
        // console.log(data);
        this.translate.get('widget.creation').subscribe((res: string) => {           
         this.notification.success(res);
       });  
         this.addForm.reset();
         this.closeDialog();
       }
     }, error => {
       this.translate.get('widget.creation').subscribe((res: string) => {           
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
