import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA,MatDialogRef } from '@angular/material';
import { RoleService } from '../../../services/role.service';
import { Profil } from 'src/app/home/models/profil';

@Component({
  selector: 'app-viewrole',
  templateUrl: './viewrole.component.html',
  styleUrls: ['./viewrole.component.scss']
})
export class ViewroleComponent implements OnInit {
 form = this.formbuild.group({
    proId: ['', Validators.required],
    proLibelle: ['', Validators.required],
    proDescription: ['', Validators.required]
  });
  roles:Profil=null
  constructor(private formbuild: FormBuilder, private router: Router, private roleService: RoleService,
    public dialogRef: MatDialogRef<ViewroleComponent>, @Inject(MAT_DIALOG_DATA) public role: any) { }

  ngOnInit() {
    this.initView()

  }

  initView() {
   this.roles=this.role;
  }

  closeDialog() {
    this.dialogRef.close();
  }


}
