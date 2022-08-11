import { Component, OnInit} from '@angular/core';
import {MatDialog, MatSnackBar } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import {WorkflowService} from '../services/workflow.service';
import { UserService } from 'src/app/utilisateur/services/user.service';
import {AddsecteurComponent } from 'src/app/parametrage/components/groupeservice/addsecteur/addsecteur.component';
import {Space} from '../models/space';

@Component({
  selector: 'app-procedures',
  templateUrl: './procedures.component.html',
  styleUrls: ['./procedures.component.scss']
})
export class ProceduresComponent implements OnInit {
  spaces:any;

  constructor(    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,private workflowService:WorkflowService,private usersService: UserService, private translate: TranslateService) { }

  ngOnInit() {
    this.listSpace();
  }

  listSpace() {
    this.workflowService.listSpace().subscribe(data => {
      this.spaces =  data;

       // console.log('++++++++++++space+++++++++++++++++'+ JSON.stringify(data)); /

    })
  }

  accessprocess(spacename){
    this.router.navigate(['/procedures/secteurs/'+spacename]);
};

  openDialogAddSecteur(): void {
    const dialog1 = this.dialog.open(AddsecteurComponent , {
      disableClose:true,
      width: '700px',
    }).afterClosed().subscribe(result => {
      this.listSpace();

    });
  }

}
