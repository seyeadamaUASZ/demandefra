import { Component, OnInit, Input, Inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import {SuivredemandeService} from '../../service/suivredemande.service';
import {DemandeAutFraService} from '../../service/demandeAutFra.service';

@Component({
	selector: 'app-add-suivredemande',
	templateUrl: './add-suivredemande.component.html',
	styleUrls: ['./add-suivredemande.component.scss']
})
export class AddSuivredemandeComponent implements OnInit {

	status:any;
	donnee:any;
	
	
	constructor(private suivredemandeService: SuivredemandeService,
		private demandeAutFraService: DemandeAutFraService,
    	private router: Router, private formBuilder: FormBuilder,
    	private translate: TranslateService,
    	private notification: NotificationService,
   		@Inject(MAT_DIALOG_DATA) public data: any,
    	public dialog: MatDialogRef<AddSuivredemandeComponent> ) { }
	
	EditForm = this.formBuilder.group({
		id: [''],
    	numerodemande:['',Validators.required],
		email:['',Validators.required],

    	poOwner: [''],
    	status: [''],
    	owner: [''],
    	idLink: ['']
    });

	ngOnInit() {		
		if(this.data){
			this.donnee = this.data.data;
			this.status = this.data.status;
			this.EditForm.setValue(this.donnee);
		}
  	}
  	
  	onSubmit(){
  		if (this.donnee) {
      		this.EditForm.value.idLink = this.donnee.id
    	} else { 
    		this.EditForm.value.idLink = null 
    	}
  		this.EditForm.value.poOwner = localStorage.getItem("profile")
  this.EditForm.value.owner = localStorage.getItem("id")
  this.EditForm.value.status = this.status
if(this.EditForm.invalid){
      this.translate.get('suivredemande.remplirTousLesChampsNotif').subscribe(data=>{
        this.notification.warn(data);
      })
      return;
    }
      this.suivredemandeService.createSuivredemande(this.EditForm.value ).subscribe((data:any) => {
        if (data.statut) {
          this.translate.get('suivredemande.confirmEnr').subscribe((res: string) => {
            this.notification.success(res);
          });

          
this.suivredemandeService.updateTaskSuivredemande(this.donnee.id, this.status).subscribe(data => {

          })
this.demandeAutFraService.updateTaskDemandeAutFra(this.donnee.id, this.status).subscribe(data => {

          })
					   this.EditForm.reset();
          this.dialog.close({status:true});
        }
      }, error => {
        let ReportSaveError;
        this.translate.get('suivredemande.erreurEnr').subscribe((res: string) => {
          this.notification.error(res);
        });
      });

  	}
  	
  	closeDialog(){
  		this.dialog.close({status:false});
  	}
  	
  	
  	
}
