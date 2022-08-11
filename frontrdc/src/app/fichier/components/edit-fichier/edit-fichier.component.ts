import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import { FichierService } from '../../services/fichier.service';
import { Rapport } from '../../models/rapport';

@Component({
  selector: 'app-edit-fichier',
  templateUrl: './edit-fichier.component.html',
  styleUrls: ['./edit-fichier.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true}
  }]
})
export class EditFichierComponent implements OnInit {
  accept:any;
  rapport: Rapport = null;
  public fichierjrxml: Array<File>;

  FormulaireForm = this.formbuild.group({
    rptId: ['', Validators.required],
    rptNom: ['', Validators.required],
    rptDescription: ['', Validators.required]
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private fichierService: FichierService,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<EditFichierComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any) {

  }

  ngOnInit() {
    this.detail();
  }


  detail() {
    this.rapport = this.donnee;
   
    this.FormulaireForm.setValue({
      rptId: this.rapport.rptId,
      rptDescription: this.rapport.rptDescription ? this.rapport.rptDescription : null,
      rptNom: this.rapport.rptNom ? this.rapport.rptNom : null
    });
  }
  onSubmit() {
    
    this.fichierService.editRapport(this.FormulaireForm.value).subscribe(data => {
      if (data.statut) {
        this._snackBar.open(data.description, 'Verification', {
          duration: 2000,
        });

        this.FormulaireForm.reset();
        this.closeDialog();
      }
    }, error => {
      this._snackBar.open("Rapport invalide", 'Verification', {
        duration: 2000,
      });
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
