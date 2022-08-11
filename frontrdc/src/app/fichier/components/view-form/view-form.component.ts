import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialog } from '@angular/material';
import { FormulaireServiceService } from 'src/app/formulaire/components/service/formulaireService.service';

@Component({
  selector: 'app-view-form',
  templateUrl: './view-form.component.html',
  styleUrls: ['./view-form.component.scss']
})
export class ViewFormComponent implements OnInit {
 champs:any;
 fields:any;
 buttons:any;
  constructor(private formbuild: FormBuilder, private router: Router, private formService: FormulaireServiceService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public donnee: any,private _snackBar: MatSnackBar) {

  }

  ngOnInit() {
    this.formService.champsByForm(this.donnee).subscribe(data=>{
      this.champs = data.data;
    })
    this.getButton();
    this.getField();
  }

  getField(){
    this.formService.fieldChampsByForm(this.donnee).subscribe(data=>{
      this.fields = data.data;
    })
  }
  getButton(){
    this.formService.buttonChampsByForm(this.donnee).subscribe(data=>{
      this.buttons = data.data;
      console.log(this.buttons)
    })
  }
  fermer(){
      this.dialog.closeAll();
  }

}
