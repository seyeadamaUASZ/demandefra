import { Component, OnInit, Inject, Input } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import { FichierService } from '../../services/fichier.service';
import { Rapport } from '../../models/rapport';

@Component({
  selector: 'app-edit-rapport-file',
  templateUrl: './edit-rapport-file.component.html',
  styleUrls: ['./edit-rapport-file.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true}
  }]
})
export class EditRapportFileComponent implements OnInit {
  rapport: Rapport = null;
  isUpload: boolean;
  @Input() accept = '.jrxml';
  public fichierjrxml: Array<File>;

  FormulaireForm = this.formbuild.group({
    rptJrxmlFile: ['', Validators.required]
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private fichierService: FichierService,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<EditRapportFileComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any) {
    this.isUpload = false;
  }

  ngOnInit() {
    
  }

  onSubmit() {
    this.rapport = this.donnee;
    this.fichierService.editRapportFile(this.fichierjrxml[0], this.rapport).subscribe(data => {
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

  uploadJrxmlFile(event: any) {
    if (event.target.files[0]) {
      this.fichierjrxml = event.target.files;
      this.isUpload = true;
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
