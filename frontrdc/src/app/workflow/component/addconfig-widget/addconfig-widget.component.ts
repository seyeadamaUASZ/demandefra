import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';
import { UserService } from '../../../utilisateur/services/user.service';

@Component({
  selector: 'app-addconfig-widget',
  templateUrl: './addconfig-widget.component.html',
  styleUrls: ['./addconfig-widget.component.scss']
})
export class AddconfigWidgetComponent implements OnInit {
  loading:any;  
  workflowform: any[];
  selectedFile: File;
  containerId:any
  process_id:any
  processdef:any
  idwrkf:any 
  marked = false;
  theCheckbox = false;
  profiles: any[];

  status: any[];
  widget: any[];
  widgettemplate: any[];
  idwrktsk:any
  wrkform:any
  addConfWidgetForm = this.formbuild.group({
    nomparam: ['', Validators.required],
    workflowid: ['', Validators.required], 
    wdgId:  ['', Validators.required],  
    proId:  ['', Validators.required],  
  });
  constructor(private formbuild: FormBuilder, private router: Router,private userService: UserService, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<AddconfigWidgetComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any) {
      this.containerId = this.elementform.wkfConteneur
      this.process_id = this.elementform.wkfProcess_id
      this.idwrkf  = this.elementform.wkfid
  }
  ngOnInit() { 
    this.extractProfilId() 
    this.extractStatusId()
    this.listFormulaireworkflow(this.containerId)
    this.extractwidget()
    //this.extractTemplateWidget()
    this.addConfWidgetForm.controls['workflowid'].setValue(this.idwrkf);
  }
  extractProfilId(){
  this.userService.listprofil().subscribe(res => {
    this.profiles = res.data;
 
});
}
listFormulaireworkflow(containerId) {
  this.workflowService.listFormulaireworkflow(containerId).subscribe(data => {
  if(data.statut){
    this.wrkform = data.data;
   
  }

  })
}
extractStatusId(){
  this.workflowService.taskStatusAll().subscribe(res => {
    this.status =  res.data;
   // console.log("+++++++++++++"+JSON.stringify(this.status));


});
}

extractwidget(){
  this.workflowService.listeWidget().subscribe(res => {
    this.widget =  res.data; 


});
}
extractTemplateWidget() {
  this.workflowService.listeTemplateWidget().subscribe(res => {
    this.widgettemplate =  res.data; 


});
} 


  onSubmit(){  
     let formData = new FormData(); 
    formData.append("paramwidgetgenform",JSON.stringify(this.addConfWidgetForm.value));   
   this.workflowService.createparamwidget(formData).subscribe(data=>{
        this.notification.success('parametre enregistrer');
        this.closeDialog();
       }); 
  }

  toggleVisibility(e){
    this.marked= e.target.checked;
  }
  closeDialog() {
    this.dialogRef.close();
  }




}
