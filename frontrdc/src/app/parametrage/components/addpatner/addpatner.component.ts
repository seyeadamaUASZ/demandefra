import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ParametreService } from '../../services/parametre.service';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarRef, SimpleSnackBar, DateAdapter, MatDialog } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
@Component({
  selector: 'app-addpatner',
  templateUrl: './addpatner.component.html',
  styleUrls: ['./addpatner.component.scss']
})
export class AddpatnerComponent implements OnInit {
  selectedFile1: File;
  retreivedImage1: any;
  base64Data1: any;

  retreivedResponse: any;
  imageId: any;
  mimeType: any;
  loading = false;
 
  app;
  //constructor(public paramService: ParametreService, public formBuilder: FormBuilder) { }

  constructor(public paramService: ParametreService, public formBuilder: FormBuilder,
    private snackBar: MatSnackBar, private notification: NotificationService, private translate: TranslateService,
    public dateAdapter: DateAdapter<Date>, private route: Router,public dialog:MatDialog

  ) { }
  PartnerForm = this.formBuilder.group({
    partId: [],
    partNom: [],
    partIm: []
  });
  ngOnInit() {
   // this.getImagelanding();

  }
  uploadImageData = new FormData();

  updateParametre() {
    this.uploadImageData.append('nomPartner', this.PartnerForm.value.partNom);
    //alert("file name"+this.selectedFile1)
    if(!this.PartnerForm.invalid){
      this.paramService.chargerPartenaire(this.uploadImageData).subscribe(data => {
        this.translate.get('landing.imgCharger').subscribe((res: string) => {
          this.notification.info(res);
          this.close();
          //localStorage.setItem('logo', 'data:image/png;base64,' + this.base64Data);
        });
      });
    }else{
      this.translate.get("invalidForm").subscribe((res: string) => {
        this.notification.warn(res);
      });
    }
    
  }

  getImagelanding() {
    this.paramService.getImageLanding().subscribe(data => {
      console.log(data);
      if (data.statut) {
        this.retreivedResponse = data.data;
        this.base64Data1 = this.retreivedResponse.lndIm1;
        //alert('hello'+this.retreivedResponse);
        this.retreivedImage1 = 'data:image/png;base64,' + this.base64Data1;

      } else {
        this.retreivedResponse = false;
      }
    })
  }

  public onFileChanged1(even) {
    this.selectedFile1 = even.target.files[0];
    this.uploadImageData.append('imageFile1', this.selectedFile1, this.selectedFile1.name);
    //alert("-------------"+ this.selectedFile1 );
    if (even.target.files.length === 0)
      return;
    if (even.target.files[0].size > 2000000) {
      //this.openSnackBar("la taille de logo depasse la taille attendue!!", 'Erreur');
      this.translate.get("la taille de logo depasse la taille attendue!!").subscribe((res: string) => {
        this.notification.warn(res);
      });
      return;

    }
    this.mimeType = even.target.files[0].type;
    //alert(this.mimeType);
    if (this.mimeType.match(/image\/*/) == null) {
      //alert("seul les images sont supportées!!!.");
      this.translate.get("seul les images sont supportées!!!.").subscribe((res: string) => {
        this.notification.warn(res);
      });
      return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(even.target.files[0]);
    reader.onload = (_event) => {
      this.retreivedImage1 = reader.result;
    }
    
  }
  close(){
    this.dialog.closeAll();
  }

}
