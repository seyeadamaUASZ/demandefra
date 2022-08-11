import { Component, OnInit} from '@angular/core';
import {MatDialog, MatSnackBar } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router'; 
import { TranslateService } from '@ngx-translate/core';
import {WorkflowService} from '../../services/workflow.service'; 
import { UserService } from 'src/app/utilisateur/services/user.service';
import { AddsecteurComponent } from './addsecteur/addsecteur.component';
 
@Component({
  selector: 'app-groupeservice',
  templateUrl: './groupeservice.component.html',
  styleUrls: ['./groupeservice.component.scss']
})
export class GroupeserviceComponent implements OnInit {
  spaces:any;
  
  constructor(    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,private workflowService:WorkflowService,private usersService: UserService, private translate: TranslateService) { }

  ngOnInit() {
    this.WorkflowsListSecteur();
  }

  WorkflowsListSecteur() {
    this.workflowService.WorkflowsListSecteur().subscribe(data => { 
      this.spaces =  data.data;
      
        console.log('++++++++++++space+++++++++++++++++'+ JSON.stringify(this.spaces));  

    })
  }

  openDialogAddSecteur(): void {
    
    const dialog1 = this.dialog.open(AddsecteurComponent , {
      disableClose:true,
      width: '700px',
    }).afterClosed().subscribe(result => {
      this.WorkflowsListSecteur();

    });
  }

  accessprocess(spacename){ 
    this.router.navigate(['/groupeservice/'+spacename]);
};

 

}
