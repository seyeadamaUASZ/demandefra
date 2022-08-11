import { Component, OnInit, RendererStyleFlags2 } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MatSnackBar } from '@angular/material';
import { ToolbarComponent } from 'src/app/sharedcomponent/toolbar/toolbar.component';
import { TranslateService } from '@ngx-translate/core';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
declare var myExtObject: any;
declare var webGlObject: any;

@Component({
  selector: 'app-change-css',
  templateUrl: './change-css.component.html',
  styleUrls: ['./change-css.component.scss']
})
export class ChangeCssComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<ToolbarComponent>, private userService: UserService,
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    private translate: TranslateService,
    private notification: NotificationService) { 
     // webGlObject.init();
    }
  user;
  hide = true;
  r;
  colorPrimary :any;
  colorSecondary;
  
  changeForm = this.formBuilder.group({
    colorPrimary: ['', Validators.required],
    colorSecondary: ['', Validators.required],
  });

  ngOnInit() {
    this.colorPrimary= this.colorPrimary?localStorage.getItem('colorPrimary'): 'blue';
    this.colorSecondary= this.colorSecondary?localStorage.getItem('colorSecondary'): 'white';
    this.changeForm.setValue({
      colorPrimary:this.colorPrimary,
      colorSecondary:this.colorSecondary,

    })

  }
 

  dialogClose() {
    this.dialogRef.close();
    console.log('fermé');
  }

onSubmit() {
  this.changeForm.value.colorPrimary;
  this.changeForm.value.colorSecondary;
  //let file = "../../../assets/pink-grey.css";
  this.colorPrimary=this.changeForm.value.colorPrimary;
  this.colorSecondary=this.changeForm.value.colorSecondary;
  localStorage.setItem('colorPrimary',this.changeForm.value.colorPrimary);
  localStorage.setItem('colorSecondary',this.changeForm.value.colorSecondary);

  //var r = document.querySelector(':root');
  //var rs = getComputedStyle(r);
  myExtObject.func1();
  myExtObject.func2(this.colorPrimary,this.colorSecondary);
  this.dialogClose();

}

}
