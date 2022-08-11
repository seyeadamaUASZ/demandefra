import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-ajout-menu',
  templateUrl: './ajout-menu.component.html',
  styleUrls: ['./ajout-menu.component.scss']
})
export class AjoutMenuComponent implements OnInit {

 
  profiles: any[];

  registreForm = this.formbuild.group({
    code: ['', Validators.required],
    libelle: ['', Validators.required],
    icon: ['', Validators.required],
    password: ['', Validators.required],
    
  });
  constructor(private formbuild: FormBuilder, private router: Router, 
    private userService: UserService,
    public dialogRef: MatDialogRef<AjoutMenuComponent>) {

  }

  ngOnInit() {
    
  }


  onSubmit() {
    this.userService.createItem(this.registreForm.value).subscribe(data => {
      console.log(data);
      if (data.statut) {
        alert(data.description);
        this.registreForm.reset();
        this.closeDialog();
      }
    }, error => {
      alert('Formulaire invalide');
    });
  }


  closeDialog() {
    this.dialogRef.close();
  }
}
