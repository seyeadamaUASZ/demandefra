import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Menu } from 'src/app/parametrage/models/menu';

@Component({
  selector: 'app-view-menu',
  templateUrl: './view-menu.component.html',
  styleUrls: ['./view-menu.component.scss']
})
export class ViewMenuComponent implements OnInit {
  menu:Menu;
  constructor(private router: Router,
    private route: ActivatedRoute,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialogRef: MatDialogRef<ViewMenuComponent>) { }

  ngOnInit() {      
    this.menu = this.donnee.menu;
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
