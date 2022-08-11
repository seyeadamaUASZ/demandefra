import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { WorkflowService } from '../../services/workflow.service';

@Component({
    selector: 'app-widget-secteur-activite',
    templateUrl: './widget-secteur-activite.component.html',
    styleUrls: ['./widget-secteur-activite.component.scss']
})
export class WidgetSecteurActiviteComponent implements OnInit {
    secteurs:any;
    nomdemarer:any=0;
    complete:any=0;
    encoucours:any=0;
    terminer:any=0;
    donnee2:any; 
    username:any=[localStorage.getItem('username')];
    constructor(private route: ActivatedRoute,private formbuild: FormBuilder,
        private dialog: MatDialog,
        private translate: TranslateService,
        private workflowService:WorkflowService
        ) { }
    
      ngOnInit() {
        //this.listeSecteurs();
        // this.listOftask();
      }

      listeSecteurs() {
        
        this.workflowService.listSpace().subscribe(data => { 
          this.secteurs =  data;
          //console.log('+++++++++++++++++++++++++++++'+ JSON.stringify(data)); 
        })
        
      }

      listOftask() { 
        
        this.workflowService.listOftask(this.username).subscribe(data => {  
          
          this.donnee2 = data['task-summary']; 
          
          
          for (var i = 0; i < this.donnee2.length; i++){ 
            if(this.donnee2[i]['task-status']=="Completed")this.complete++; 
            if(this.donnee2[i]['task-status']=="Reserved")this.nomdemarer++; 
            if(this.donnee2[i]['task-status']=="InProgress") this.encoucours++;
          
        }
        console.log('+++++++++++++get count++++++++++++++++++++++++++++'+ this.complete)
          console.log('+++++++++++++get All Task++++++++++++++++++++++++++++'+ JSON.stringify(this.donnee2))
        })
        
      }
}

