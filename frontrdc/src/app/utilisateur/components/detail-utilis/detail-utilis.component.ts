import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { User } from '../../models/user';

/**
 * @title Data table with sorting, pagination, and filtering.
 */
@Component({
  selector: 'app-detail-utilis',
  styleUrls: ['./detail-utilis.component.scss'],
  templateUrl: './detail-utilis.component.html',
})
export class DetailUtilisComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialogRef: MatDialogRef<DetailUtilisComponent>) {
    
  }
  
  user: User = null;
  ngOnInit() {
    this.detail();
  }

  detail() {
    this.user = this.donnee;
  }

  closeDialog() {
    this.dialogRef.close();
  }

}